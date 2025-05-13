package org.mecipe.server.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import jakarta.annotation.Resource;
import org.mecipe.server.model.request.mcp.tool.MCPToolAddDTO;
import org.mecipe.server.model.request.mcp.tool.MCPToolDeleteDTO;
import org.mecipe.server.model.request.mcp.tool.MCPToolQueryDTO;
import org.mecipe.server.model.request.mcp.tool.MCPToolUpdateDTO;
import org.mecipe.server.model.response.Response;
import org.mecipe.server.model.response.mcp.MCPToolVO;
import org.mecipe.server.service.MCPToolService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SaCheckLogin
@RestController
@RequestMapping("/mcp/tool")
public class MCPToolController {

    @Resource
    private MCPToolService mcpToolService;

    @SaIgnore
    @PostMapping("/getTool")
    Response<List<MCPToolVO>> getTools(@RequestBody MCPToolQueryDTO toolsDTO) {
        return Response.success(mcpToolService.query(toolsDTO));
    }

    @PostMapping("/addTool")
    Response<Boolean> addTool(@RequestBody List<MCPToolAddDTO> addDTO) {
        return Response.success(mcpToolService.add(addDTO));
    }

    @PostMapping("/deleteTool")
    Response<Integer> deleteTool(@RequestBody MCPToolDeleteDTO deleteDTO) {
        return Response.success(mcpToolService.delete(deleteDTO));
    }

    @PostMapping("/updateTool")
    Response<List<MCPToolVO>> updateTool(@RequestBody List<MCPToolUpdateDTO> updateDTOList) {
        return Response.success(mcpToolService.update(updateDTOList));
    }

}
