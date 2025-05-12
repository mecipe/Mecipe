package org.mecipe.server.model.request.mcp;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MCPQueryDTO {

    private String name;

    private String type;

    private String authorId;

    private String authorName;

    private Integer status = 1;

}
