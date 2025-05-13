package org.mecipe.server.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import jakarta.annotation.Resource;
import org.mecipe.server.model.request.mcp.MCPAddDTO;
import org.mecipe.server.model.request.mcp.MCPDeleteDTO;
import org.mecipe.server.model.request.mcp.MCPQueryDTO;
import org.mecipe.server.model.request.mcp.MCPUpdateDTO;
import org.mecipe.server.model.response.Response;
import org.mecipe.server.model.response.mcp.MCPEntityVO;
import org.mecipe.server.service.MCPService;
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

    @PostMapping("/add")
    Response<Boolean> add(@RequestBody MCPAddDTO addParam) {
        return Response.success(mcpService.add(addParam));
    }

    @PostMapping("/delete")
    Response<Boolean> delete(@RequestBody MCPDeleteDTO deleteParam) {
        return Response.success(mcpService.delete(deleteParam));
    }

    @PostMapping("/update")
    Response<MCPEntityVO> update(@RequestBody MCPUpdateDTO updateParam) {
        return Response.success(mcpService.update(updateParam));
    }

    @PostMapping("/disable")
    Response<Boolean> disable(@RequestBody MCPDeleteDTO disableParam) {
        return Response.success(mcpService.disable(disableParam));
    }

    @SaIgnore
    @PostMapping("/query")
    Response<List<MCPEntityVO>> query(@RequestBody MCPQueryDTO queryParam) {
        return Response.success(mcpService.query(queryParam));
    }

}
