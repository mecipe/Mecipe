package org.mecipe.server.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import jakarta.annotation.Resource;
import org.mecipe.server.model.request.mcp.MCPAddDTO;
import org.mecipe.server.model.request.mcp.MCPDeleteDTO;
import org.mecipe.server.model.request.mcp.MCPQueryDTO;
import org.mecipe.server.model.request.mcp.MCPUpdateDTO;
import org.mecipe.server.model.response.Response;
import org.mecipe.server.model.response.mcp.MCPToolVO;
import org.mecipe.server.model.response.mcp.MCPVO;
import org.mecipe.server.service.MCPService;
import org.mecipe.server.service.MCPToolService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SaCheckLogin
@RestController
@RequestMapping("/mcp")
public class MCPController {

    @Resource
    private MCPService mcpService;

    @Resource
    private MCPToolService mcpToolService;

    @PostMapping("/add")
    Response<Boolean> add(@RequestBody MCPAddDTO addDTO) {
        return Response.success(mcpService.add(addDTO));
    }

    @PostMapping("/delete")
    Response<Boolean> delete(@RequestBody MCPDeleteDTO deleteDTO) {
        return Response.success(mcpService.delete(deleteDTO));
    }

    @PostMapping("/update")
    Response<MCPVO> update(@RequestBody MCPUpdateDTO updateDTO) {
        return Response.success(mcpService.update(updateDTO));
    }


    @PostMapping("/disable")
    Response<Boolean> disable(@RequestBody MCPDeleteDTO disableDTO) {
        return Response.success(mcpService.disable(disableDTO));
    }

    @PostMapping("/query")
    Response<List<MCPVO>> query(@RequestBody MCPQueryDTO queryDTO) {
        return Response.success(mcpService.query(queryDTO));
    }

    @PostMapping("/getTools")
    Response<List<MCPToolVO>> getTools(@RequestBody MCPDeleteDTO toolsDTO) {
        return Response.success(mcpToolService.query(toolsDTO));
    }

}
