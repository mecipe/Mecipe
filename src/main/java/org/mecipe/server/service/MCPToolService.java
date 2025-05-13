package org.mecipe.server.service;

import org.mecipe.server.model.request.mcp.MCPDeleteDTO;
import org.mecipe.server.model.request.mcp.tool.MCPToolAddDTO;
import org.mecipe.server.model.request.mcp.tool.MCPToolDeleteDTO;
import org.mecipe.server.model.request.mcp.tool.MCPToolQueryDTO;
import org.mecipe.server.model.request.mcp.tool.MCPToolUpdateDTO;
import org.mecipe.server.model.response.mcp.MCPToolVO;

import java.util.List;

public interface MCPToolService {

    Boolean add(List<MCPToolAddDTO> addDTOList);

    int delete(MCPToolDeleteDTO deleteDTO);

    List<MCPToolVO> update(List<MCPToolUpdateDTO> updateDTOList);

    List<MCPToolVO> query(MCPToolQueryDTO queryDTO);

}
