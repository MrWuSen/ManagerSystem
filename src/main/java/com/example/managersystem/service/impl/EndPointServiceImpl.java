package com.example.managersystem.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.managersystem.constant.BaseConstant;
import com.example.managersystem.dto.UserResource;
import com.example.managersystem.enums.UserType;
import com.example.managersystem.interceptor.AuthInterceptor;
import com.example.managersystem.service.EndPointService;
import com.example.managersystem.utils.FileOperateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wusen
 * @Date: 2024/4/29 17:08
 * @Description:
 */
@Service
@Slf4j
public class EndPointServiceImpl implements EndPointService {
    @Override
    public void addUserResource(UserResource userResource) {
        UserResource resource = FileOperateUtils.readLines(userResource.getUserId());
        if (null != resource) {
            resource.setEndpoint(userResource.getEndpoint());
            String resourceDB = JSON.toJSONString(resource);
            FileOperateUtils.modifyLine(resource.getUserLineNo(), resourceDB);
            log.info("用户{}权限修改成功", userResource.getUserId());
            return;
        }
        userResource.setRole(UserType.USER.getRole());
        String resourceDB = JSON.toJSONString(userResource);
        FileOperateUtils.appendLines(new ArrayList<String>() {{
            add(resourceDB);
        }});
        log.info("用户{}权限添加成功", userResource.getUserId());
    }

    @Override
    public boolean permissionQuery(String resource) {
        String userId = AuthInterceptor.requestHeader.get().get(BaseConstant.USER_ID).get(0);
        UserResource userResource = FileOperateUtils.readLines(userId);
        if(null == userResource) {
            return false;
        }
        List<String> resourcePermission = userResource.getEndpoint();
        if (CollectionUtils.isEmpty(resourcePermission) || !resourcePermission.contains(resource)) {
            return false;
        }
        return true;
    }
}
