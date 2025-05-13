package org.mecipe.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import org.mecipe.server.common.enums.ErrorCode;
import org.mecipe.server.common.session.LoginUtils;
import org.mecipe.server.common.utils.BeanConverter;
import org.mecipe.server.common.utils.Valider;
import org.mecipe.server.exception.BusinessException;
import org.mecipe.server.mapper.MCPMapper;
import org.mecipe.server.mapper.UserMCPAuthMapper;
import org.mecipe.server.model.entity.MCPEntity;
import org.mecipe.server.model.entity.UserMCPAuthEntity;
import org.mecipe.server.model.request.mcp.MCPAddDTO;
import org.mecipe.server.model.request.mcp.MCPDeleteDTO;
import org.mecipe.server.model.request.mcp.MCPQueryDTO;
import org.mecipe.server.model.request.mcp.MCPUpdateDTO;
import org.mecipe.server.model.request.mcp.tool.MCPToolAddDTO;
import org.mecipe.server.model.request.mcp.tool.MCPToolDeleteDTO;
import org.mecipe.server.model.response.mcp.MCPEntityVO;
import org.mecipe.server.service.MCPService;
import org.mecipe.server.service.MCPToolService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MCPServiceImpl implements MCPService {

    @Resource
    private MCPMapper mcpMapper;

    @Resource
    private UserMCPAuthMapper userMCPAuthMapper;

    @Resource
    private MCPToolService mcpToolService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean add(MCPAddDTO addParam) {
        Valider.validateNullParams(addParam);
        MCPEntity mcpEntity = BeanConverter.toBean(addParam, MCPEntity.class);
        int inserted = mcpMapper.insert(mcpEntity);
        if (!(inserted > 0)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "MCP服务添加失败");
        }

        // 添加MCP服务后，为用户添加'编辑'权限
        UserMCPAuthEntity authEntity = UserMCPAuthEntity.builder()
                .userId(addParam.getAuthorId())
                .mcpId(mcpEntity.getId())
                .authType(2)
                .expiredAt(null)
                .build();

        int inserted1 = userMCPAuthMapper.insert(authEntity);
        if (!(inserted1 > 0)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "MCP服务添加失败");
        }

        // 添加MCP服务后，为MCP服务添加工具
        List<MCPToolAddDTO> mcpTools = addParam.getTools();
        if (mcpTools != null && !mcpTools.isEmpty()) {
            return mcpToolService.add(mcpTools);
        }
        return true;
    }

    @Override
    public boolean delete(MCPDeleteDTO deleteParam) {
        Valider.validateNullParams(deleteParam);

        // 查询用户是否具有编辑权限
        checkEditAuth(LoginUtils.getUserId(), deleteParam.getId());

        LambdaQueryWrapper<MCPEntity> queryWrapper = Wrappers.lambdaQuery(MCPEntity.class);
        if (deleteParam.getId() != null) {
            queryWrapper.eq(MCPEntity::getId, deleteParam.getId());
        }

        int deleted = mcpMapper.delete(queryWrapper);
        if (!(deleted > 0)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "MCP服务删除失败");
        }

        // 删除MCP服务后，删除对应的权限
        UserMCPAuthEntity authEntity = UserMCPAuthEntity.builder()
                .mcpId(deleteParam.getId())
                .build();

        int deleted1 = userMCPAuthMapper.delete(Wrappers.lambdaQuery(authEntity));
        if (!(deleted1 > 0)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "MCP服务删除失败");
        }

        // 删除MCP服务后，删除对应的工具
        return mcpToolService.delete(MCPToolDeleteDTO.builder().mcpId(deleteParam.getId()).build()) > 0;
    }

    @Override
    public MCPEntityVO update(MCPUpdateDTO updateParam) {
        Valider.validateNullParams(updateParam);
        checkEditAuth(LoginUtils.getUserId(), updateParam.getId());
        MCPEntity mcpEntity = BeanConverter.toBean(updateParam, MCPEntity.class);
        // 更新 MCP 信息
        mcpMapper.updateById(mcpEntity);

        return BeanConverter.toBean(mcpEntity, MCPEntityVO.class);
    }

    @Override
    public boolean disable(MCPDeleteDTO disableDTO) {
        Valider.validateNullParams(disableDTO);
        checkEditAuth(LoginUtils.getUserId(), disableDTO.getId());

        return mcpMapper.update(null, Wrappers.lambdaUpdate(MCPEntity.class)
                .eq(MCPEntity::getId, disableDTO.getId())
                .set(MCPEntity::getStatus, 0)) > 0;
    }

    @Override
    public List<MCPEntityVO> query(MCPQueryDTO queryParam) {
        Valider.validateNullParams(queryParam);
        LambdaQueryWrapper<MCPEntity> queryWrapper = Wrappers.lambdaQuery(MCPEntity.class)
                .eq(MCPEntity::getName, queryParam.getName())
                .eq(MCPEntity::getType, queryParam.getType())
                .eq(MCPEntity::getAuthorId, queryParam.getAuthorId())
                .eq(MCPEntity::getAuthorName, queryParam.getAuthorName())
                .eq(MCPEntity::getStatus, queryParam.getStatus());

        return mcpMapper.selectList(queryWrapper)
                .stream()
                .map(mcpEntity -> BeanConverter.toBean(mcpEntity, MCPEntityVO.class))
                .toList();
    }

    /**
     * 检查用户是否具有编辑权限
     */
    private void checkEditAuth(Integer userId, Integer mcpId) {
        LambdaQueryWrapper<UserMCPAuthEntity> queryWrapper = Wrappers.lambdaQuery(UserMCPAuthEntity.class)
                .eq(UserMCPAuthEntity::getUserId, userId)
                .eq(UserMCPAuthEntity::getMcpId, mcpId);

        // TODO: 管理员权限校验
        if (userMCPAuthMapper.selectOne(queryWrapper) == null) {
            throw new BusinessException(ErrorCode.NO_AUTH, "无编辑权限");
        }
    }

}
