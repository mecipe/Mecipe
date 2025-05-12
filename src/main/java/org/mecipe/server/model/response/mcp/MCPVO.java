package org.mecipe.server.model.response.mcp;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MCPVO {

    private Integer id;

    private String name;

    private String description;

    private String introduce;

    private String icon;

    private String type;

    private String author;

    private String mcpVersion;

    private int source;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
