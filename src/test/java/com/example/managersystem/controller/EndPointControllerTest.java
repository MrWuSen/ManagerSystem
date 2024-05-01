package com.example.managersystem.controller;

import com.example.managersystem.dto.EmptyBody;
import com.example.managersystem.dto.ResponseModel;
import com.example.managersystem.dto.UserResource;
import com.example.managersystem.service.EndPointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EndPointControllerTest {

    @Mock
    private EndPointService mockEndPointService;

    private EndPointController endPointControllerUnderTest;

    @BeforeEach
    void setUp() {
        endPointControllerUnderTest = new EndPointController();
        endPointControllerUnderTest.endPointService = mockEndPointService;
    }

    @Test
    void testAddUserResource() {
        // Setup
        final UserResource userResource = new UserResource();
        userResource.setUserId("userId");
        userResource.setRole("role");
        userResource.setEndpoint(Arrays.asList("value"));

        // Run the test
        final ResponseModel<EmptyBody> result = endPointControllerUnderTest.addUserResource(userResource);

        // Verify the results
        // Confirm EndPointService.addUserResource(...).
        final UserResource userResource1 = new UserResource();
        userResource1.setUserId("userId");
        userResource1.setRole("role");
        userResource1.setEndpoint(Arrays.asList("value"));
        verify(mockEndPointService).addUserResource(userResource1);
    }

    @Test
    void testPermissionQuery() {
        // Setup
        when(mockEndPointService.permissionQuery("resource")).thenReturn(false);

        // Run the test
        final ResponseModel<EmptyBody> result = endPointControllerUnderTest.permissionQuery("resource");

        // Verify the results
    }
}
