package org.mecipe.server.model.request.mcp;

import com.google.gson.JsonObject;
import lombok.Data;

@Data
public class MCPRequest {

    private final String jsonrpc = "2.0";

    private String id;

    private String method;

    private JsonObject params;

}
