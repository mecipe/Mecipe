package org.mecipe.server.service;

import org.mecipe.server.model.request.mcp.MCPDeleteDTO;
import org.mecipe.server.model.response.mcp.MCPToolVO;

import java.util.List;

public interface MCPToolService {

    List<MCPToolVO> query(MCPDeleteDTO queryDTO);

}
