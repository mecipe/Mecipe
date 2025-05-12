package org.mecipe.server.model.response.mcp;

import lombok.*;
import org.mecipe.server.model.entity.MCPToolParam;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MCPToolVO {

    private Integer id;

    private Integer mcpId;

    private String toolName;

    private String description;

    private List<MCPToolParam> params;

}