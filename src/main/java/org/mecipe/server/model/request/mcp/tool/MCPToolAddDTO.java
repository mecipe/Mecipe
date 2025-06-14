package org.mecipe.server.model.request.mcp.tool;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.mecipe.server.model.entity.MCPToolParam;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MCPToolAddDTO {

    private Integer mcpId;

    private String toolName;

    private String description;

    private List<MCPToolParam> params;

}