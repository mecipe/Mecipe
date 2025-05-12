package org.mecipe.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import org.mecipe.server.common.enums.ErrorCode;
import org.mecipe.server.common.session.LoginUtils;
import org.mecipe.server.common.utils.BeanConverter;
import org.mecipe.server.common.utils.JSON;
import org.mecipe.server.common.utils.Valider;
import org.mecipe.server.exception.BusinessException;
import org.mecipe.server.mapper.MCPMapper;
import org.mecipe.server.mapper.MCPToolMapper;
import org.mecipe.server.mapper.UserMCPAuthMapper;
import org.mecipe.server.model.entity.MCPEntity;
import org.mecipe.server.model.entity.MCPToolEntity;
import org.mecipe.server.model.entity.UserMCPAuthEntity;
import org.mecipe.server.model.request.mcp.MCPAddDTO;
import org.mecipe.server.model.request.mcp.MCPDeleteDTO;
import org.mecipe.server.model.request.mcp.MCPQueryDTO;
import org.mecipe.server.model.request.mcp.MCPUpdateDTO;
import org.mecipe.server.model.request.mcp.tool.MCPToolAddDTO;
import org.mecipe.server.model.response.mcp.MCPVO;
import org.mecipe.server.service.MCPService;
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
    private MCPToolMapper mcpToolMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean add(MCPAddDTO addDTO) {
        Valider.validateNullParams(addDTO);
        MCPEntity mcpEntity = BeanConverter.toBean(addDTO, MCPEntity.class);
        int inserted = mcpMapper.insert(mcpEntity);
        if (!(inserted > 0)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "MCP服务添加失败");
        }

        // 添加MCP服务后，为用户添加'编辑'权限
        UserMCPAuthEntity authEntity = UserMCPAuthEntity.builder()
                .userId(addDTO.getAuthorId())
                .mcpId(mcpEntity.getId())
                .authType(2)
                .expiredAt(null)
                .build();

        int inserted1 = userMCPAuthMapper.insert(authEntity);
        if (!(inserted1 > 0)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "MCP服务添加失败");
        }

        // 添加MCP服务后，为MCP服务添加工具
        List<MCPToolAddDTO> mcpTools = addDTO.getTools();
        Valider.validateNullParams(mcpTools);
        List<MCPToolEntity> mcpToolEntities = mcpTools.stream()
                .map(mcpTool -> MCPToolEntity.builder()
                        .mcpId(mcpEntity.getId())
                        .toolName(mcpTool.getToolName())
                        .description(mcpTool.getDescription())
                        .params(JSON.toJson(mcpTool.getParams()))
                        .build()
                ).toList();

        return mcpToolMapper.insert(mcpToolEntities).size() == mcpTools.size();
    }

    @Override
    public boolean delete(MCPDeleteDTO deleteDTO) {
        Valider.validateNullParams(deleteDTO);

        // 查询用户是否具有编辑权限
        checkEditAuth(LoginUtils.getUserId(), deleteDTO.getId());

        LambdaQueryWrapper<MCPEntity> queryWrapper = Wrappers.lambdaQuery(MCPEntity.class);
        if (deleteDTO.getId() != null) {
            queryWrapper.eq(MCPEntity::getId, deleteDTO.getId());
        }

        int deleted = mcpMapper.delete(queryWrapper);
        if (!(deleted > 0)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "MCP服务删除失败");
        }

        // 删除MCP服务后，删除对应的权限
        UserMCPAuthEntity authEntity = UserMCPAuthEntity.builder()
                .mcpId(deleteDTO.getId())
                .build();

        return userMCPAuthMapper.delete(Wrappers.lambdaQuery(authEntity)) > 0;
    }

    @Override
    public MCPVO update(MCPUpdateDTO updateDTO) {
        Valider.validateNullParams(updateDTO);
        checkEditAuth(LoginUtils.getUserId(), updateDTO.getId());
        MCPEntity mcpEntity = BeanConverter.toBean(updateDTO, MCPEntity.class);
        mcpMapper.updateById(mcpEntity);
        return BeanConverter.toBean(mcpEntity, MCPVO.class);
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
    public List<MCPVO> query(MCPQueryDTO queryDTO) {
        Valider.validateNullParams(queryDTO);
        LambdaQueryWrapper<MCPEntity> queryWrapper = Wrappers.lambdaQuery(MCPEntity.class)
                .eq(MCPEntity::getName, queryDTO.getName())
                .eq(MCPEntity::getType, queryDTO.getType())
                .eq(MCPEntity::getAuthorId, queryDTO.getAuthorId())
                .eq(MCPEntity::getAuthorName, queryDTO.getAuthorName())
                .eq(MCPEntity::getStatus, queryDTO.getStatus());

        return mcpMapper.selectList(queryWrapper)
                .stream()
                .map(mcpEntity -> BeanConverter.toBean(mcpEntity, MCPVO.class))
                .toList();
    }

    /**
     * 检查用户是否具有编辑权限
     */
    private void checkEditAuth(Long userId, Integer mcpId) {
        LambdaQueryWrapper<UserMCPAuthEntity> queryWrapper = Wrappers.lambdaQuery(UserMCPAuthEntity.class)
                .eq(UserMCPAuthEntity::getUserId, userId)
                .eq(UserMCPAuthEntity::getMcpId, mcpId);

        // TODO: 管理员权限校验
        if (userMCPAuthMapper.selectOne(queryWrapper) == null) {
            throw new BusinessException(ErrorCode.NO_AUTH, "无编辑权限");
        }
    }

}
