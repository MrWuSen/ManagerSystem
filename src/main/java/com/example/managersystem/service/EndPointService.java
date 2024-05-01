package com.example.managersystem.service;

import com.example.managersystem.dto.UserResource;

/**
 * @Author: wusen
 * @Date: 2024/4/29 17:07
 * @Description:
 */
public interface EndPointService {

    void addUserResource(UserResource userResource);

    boolean permissionQuery(String resource);
}
