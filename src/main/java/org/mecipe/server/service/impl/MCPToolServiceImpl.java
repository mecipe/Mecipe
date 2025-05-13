package org.mecipe.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.reflect.TypeToken;
import jakarta.annotation.Resource;
import org.mecipe.server.common.utils.BeanConverter;
import org.mecipe.server.common.utils.JSON;
import org.mecipe.server.common.utils.Valider;
import org.mecipe.server.mapper.MCPToolMapper;
import org.mecipe.server.model.entity.MCPToolEntity;
import org.mecipe.server.model.entity.MCPToolParam;
import org.mecipe.server.model.request.mcp.MCPDeleteDTO;
import org.mecipe.server.model.request.mcp.tool.MCPToolAddDTO;
import org.mecipe.server.model.request.mcp.tool.MCPToolDeleteDTO;
import org.mecipe.server.model.request.mcp.tool.MCPToolQueryDTO;
import org.mecipe.server.model.request.mcp.tool.MCPToolUpdateDTO;
import org.mecipe.server.model.response.mcp.MCPToolVO;
import org.mecipe.server.service.MCPToolService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MCPToolServiceImpl implements MCPToolService {

    @Resource
    private MCPToolMapper mcpToolMapper;

    @Override
    public Boolean add(List<MCPToolAddDTO> addDTOList) {
        Valider.validateNullParams(addDTOList);
        List<MCPToolEntity> mcpToolEntities = addDTOList.stream()
                .map(mcpTool -> MCPToolEntity.builder()
                        .mcpId(mcpTool.getMcpId())
                        .toolName(mcpTool.getToolName())
                        .description(mcpTool.getDescription())
                        .params(JSON.toJson(mcpTool.getParams()))
                        .build()
                ).toList();

        return mcpToolMapper.insert(mcpToolEntities).size() == addDTOList.size();
    }

    @Override
    public int delete(MCPToolDeleteDTO deleteDTO) {
        Valider.validateNullParams(deleteDTO);
        var queryWrapper = Wrappers.lambdaQuery(MCPToolEntity.class);
        if (deleteDTO.getId() != null) {
            queryWrapper.eq(MCPToolEntity::getId, deleteDTO.getId());
        }
        if (deleteDTO.getMcpId() != null) {
            queryWrapper.eq(MCPToolEntity::getMcpId, deleteDTO.getMcpId());
        }

        return mcpToolMapper.delete(queryWrapper);
    }

    @Override
    public List<MCPToolVO> update(List<MCPToolUpdateDTO> updateDTOList) {
        Valider.validateNullParams(updateDTOList);
        List<MCPToolEntity> toolEntities = updateDTOList.stream()
                .map(updateDTO -> BeanConverter.toBean(updateDTO, MCPToolEntity.class))
                .toList();
        mcpToolMapper.updateById(toolEntities);
        return toolEntities.stream()
                .map(this::mcpToolEntity2VO)
                .toList();
    }

    @Override
    public List<MCPToolVO> query(MCPToolQueryDTO queryDTO) {
        Valider.validateNullParams(queryDTO);
        return mcpToolMapper.selectList(
                        Wrappers.lambdaQuery(MCPToolEntity.class)
                                .eq(MCPToolEntity::getMcpId, queryDTO.getMcpId())
                )
                .stream()
                .map(this::mcpToolEntity2VO)
                .toList();

    }

    private MCPToolVO mcpToolEntity2VO(MCPToolEntity mcpToolEntity) {
        return MCPToolVO.builder()
                .id(mcpToolEntity.getId())
                .mcpId(mcpToolEntity.getMcpId())
                .toolName(mcpToolEntity.getToolName())
                .description(mcpToolEntity.getDescription())
                .params(JSON.fromJson(mcpToolEntity.getParams(), new TypeToken<List<MCPToolParam>>() {
                }.getType()))
                .build();
    }

}
