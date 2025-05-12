package org.mecipe.server.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.reflect.TypeToken;
import jakarta.annotation.Resource;
import org.mecipe.server.common.utils.JSON;
import org.mecipe.server.common.utils.Valider;
import org.mecipe.server.mapper.MCPToolMapper;
import org.mecipe.server.model.entity.MCPToolEntity;
import org.mecipe.server.model.entity.MCPToolParam;
import org.mecipe.server.model.request.mcp.MCPDeleteDTO;
import org.mecipe.server.model.response.mcp.MCPToolVO;
import org.mecipe.server.service.MCPToolService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MCPToolServiceImpl implements MCPToolService {

    @Resource
    private MCPToolMapper mcpToolMapper;

    @Override
    public List<MCPToolVO> query(MCPDeleteDTO queryDTO) {
        Valider.validateNullParams(queryDTO);
        return mcpToolMapper.selectList(
                        Wrappers.lambdaQuery(MCPToolEntity.class).eq(MCPToolEntity::getMcpId, queryDTO.getId())
                )
                .stream()
                .map(mcpToolEntity -> MCPToolVO.builder()
                        .id(mcpToolEntity.getId())
                        .mcpId(mcpToolEntity.getMcpId())
                        .toolName(mcpToolEntity.getToolName())
                        .description(mcpToolEntity.getDescription())
                        .params(JSON.fromJson(mcpToolEntity.getParams(), new TypeToken<List<MCPToolParam>>() {}.getType()))
                        .build()
                ).toList();

    }

}
