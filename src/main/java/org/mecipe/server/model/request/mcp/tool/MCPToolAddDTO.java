package org.mecipe.server.model.request.mcp.tool;

import lombok.*;
import org.mecipe.server.model.entity.MCPToolParam;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MCPToolAddDTO {

    private String toolName;

    private String description;

    private List<MCPToolParam> params;

}