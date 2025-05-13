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
    @PostMapping("/get")
    Response<List<MCPToolVO>> get(@RequestBody MCPToolQueryDTO queryParam) {
        return Response.success(mcpToolService.query(queryParam));
    }

    @PostMapping("/add")
    Response<Boolean> add(@RequestBody List<MCPToolAddDTO> addParams) {
        return Response.success(mcpToolService.add(addParams));
    }

    @PostMapping("/delete")
    Response<Integer> delete(@RequestBody MCPToolDeleteDTO deleteParam) {
        return Response.success(mcpToolService.delete(deleteParam));
    }

    @PostMapping("/update")
    Response<List<MCPToolVO>> update(@RequestBody List<MCPToolUpdateDTO> updateParams) {
        return Response.success(mcpToolService.update(updateParams));
    }

}
