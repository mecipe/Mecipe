package org.mecipe.server.model.request.mcp;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MCPUpdateDTO extends MCPAddDTO {

    private Integer id;

}
