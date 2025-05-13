package org.mecipe.server.service;

import org.mecipe.server.model.request.mcp.MCPAddDTO;
import org.mecipe.server.model.request.mcp.MCPDeleteDTO;
import org.mecipe.server.model.request.mcp.MCPQueryDTO;

import org.mecipe.server.model.request.mcp.MCPUpdateDTO;
import org.mecipe.server.model.response.mcp.MCPEntityVO;

import java.util.List;

public interface MCPService {

    boolean add(MCPAddDTO addParam);

    boolean delete(MCPDeleteDTO deleteParam);

    MCPEntityVO update(MCPUpdateDTO updateParam);

    boolean disable(MCPDeleteDTO disableParam);

    List<MCPEntityVO> query(MCPQueryDTO queryParam);

}
