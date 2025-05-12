package org.mecipe.server.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MCPToolParam {

    private String fieldName;

    private String fieldDesc;

    private String fieldType;

    private boolean required;

}
