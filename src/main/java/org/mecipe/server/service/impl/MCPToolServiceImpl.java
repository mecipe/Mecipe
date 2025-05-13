package org.mecipe.server.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.reflect.TypeToken;
import jakarta.annotation.Resource;
import org.mecipe.server.common.utils.BeanConverter;
import org.mecipe.server.common.utils.JSON;
import org.mecipe.server.common.utils.Valider;
import org.mecipe.server.mapper.MCPToolMapper;
import org.mecipe.server.model.entity.MCPToolEntity;
import org.mecipe.server.model.entity.MCPToolParam;
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
    public Boolean add(List<MCPToolAddDTO> addParams) {
        Valider.validateNullParams(addParams);
        List<MCPToolEntity> mcpToolEntities = addParams.stream()
                .map(mcpTool -> MCPToolEntity.builder()
                        .mcpId(mcpTool.getMcpId())
                        .toolName(mcpTool.getToolName())
                        .description(mcpTool.getDescription())
                        .params(JSON.toJson(mcpTool.getParams()))
                        .build()
                ).toList();

        return mcpToolMapper.insert(mcpToolEntities).size() == addParams.size();
    }

    @Override
    public int delete(MCPToolDeleteDTO deleteParam) {
        Valider.validateNullParams(deleteParam);
        var queryWrapper = Wrappers.lambdaQuery(MCPToolEntity.class);
        if (deleteParam.getId() != null) {
            queryWrapper.eq(MCPToolEntity::getId, deleteParam.getId());
        }
        if (deleteParam.getMcpId() != null) {
            queryWrapper.eq(MCPToolEntity::getMcpId, deleteParam.getMcpId());
        }

        return mcpToolMapper.delete(queryWrapper);
    }

    @Override
    public List<MCPToolVO> update(List<MCPToolUpdateDTO> updateParams) {
        Valider.validateNullParams(updateParams);
        List<MCPToolEntity> toolEntities = updateParams.stream()
                .map(updateDTO -> BeanConverter.toBean(updateDTO, MCPToolEntity.class))
                .toList();
        mcpToolMapper.updateById(toolEntities);

        return toolEntities.stream()
                .map(this::mcpToolEntity2VO)
                .toList();
    }

    @Override
    public List<MCPToolVO> query(MCPToolQueryDTO queryParam) {
        Valider.validateNullParams(queryParam);

        return mcpToolMapper.selectList(
                        Wrappers.lambdaQuery(MCPToolEntity.class)
                                .eq(MCPToolEntity::getMcpId, queryParam.getMcpId())
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
