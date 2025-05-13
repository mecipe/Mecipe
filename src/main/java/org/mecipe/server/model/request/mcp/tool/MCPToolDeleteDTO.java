package org.mecipe.server.model.request.mcp.tool;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MCPToolDeleteDTO {

    private Integer id;

    private Integer mcpId;

}