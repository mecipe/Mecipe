package org.mecipe.server.model.request.mcp;

import lombok.*;
import org.mecipe.server.model.request.mcp.tool.MCPToolAddDTO;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MCPAddDTO extends MCPToolAddDTO {

    private String name;

    private String description;

    private String introduce;

    private String icon;

    private String type;

    private Long authorId;

    private String authorName;

    private String mcpVersion;

    private int source;

    private Integer status = 1;

    private List<MCPToolAddDTO> tools;

}
