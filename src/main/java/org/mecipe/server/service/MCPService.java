package org.mecipe.server.service;

import org.mecipe.server.model.request.mcp.MCPAddDTO;
import org.mecipe.server.model.request.mcp.MCPDeleteDTO;
import org.mecipe.server.model.request.mcp.MCPQueryDTO;

import org.mecipe.server.model.request.mcp.MCPUpdateDTO;
import org.mecipe.server.model.response.mcp.MCPVO;

import java.util.List;

public interface MCPService {

    boolean add(MCPAddDTO addDTO);

    boolean delete(MCPDeleteDTO deleteDTO);

    MCPVO update(MCPUpdateDTO updateDTO);

    boolean disable(MCPDeleteDTO disableDTO);

    List<MCPVO> query(MCPQueryDTO queryDTO);

}
