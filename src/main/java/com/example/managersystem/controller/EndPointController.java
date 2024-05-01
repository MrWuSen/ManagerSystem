package com.example.managersystem.controller;

import com.example.managersystem.dto.EmptyBody;
import com.example.managersystem.dto.ResponseModel;
import com.example.managersystem.dto.UserResource;
import com.example.managersystem.service.EndPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: wusen
 * @Date: 2024/4/29 17:03
 * @Description: 暴露接口
 */
@RestController
public class EndPointController {
    @Autowired
    EndPointService endPointService;

    @PostMapping("/admin/addUser")
    public ResponseModel<EmptyBody> addUserResource(@RequestBody UserResource userResource) {
        endPointService.addUserResource(userResource);
        return ResponseModel.empty();
    }

    @GetMapping("/user/{resource}")
    public ResponseModel<EmptyBody> permissionQuery(@PathVariable String resource) {
        if (endPointService.permissionQuery(resource)) {
            return ResponseModel.ok(EmptyBody.INSTANCE);
        }
        return ResponseModel.error(500, "the current user do not have this access", EmptyBody.INSTANCE);
    }

}
