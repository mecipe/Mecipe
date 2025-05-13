package org.mecipe.server.model.request.mcp.tool;

import lombok.*;
import org.mecipe.server.model.entity.MCPToolParam;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MCPToolUpdateDTO {

    private Integer id;

    private Integer mcpId;

    private String toolName;

    private String description;

    private List<MCPToolParam> params;

}