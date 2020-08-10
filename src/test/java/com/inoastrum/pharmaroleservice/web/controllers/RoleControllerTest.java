package com.inoastrum.pharmaroleservice.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inoastrum.pharmaroleservice.service.RoleService;
import com.inoastrum.pharmaroleservice.web.model.Permission;
import com.inoastrum.pharmaroleservice.web.model.RoleDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@ExtendWith(MockitoExtension.class)
@WebMvcTest(RoleController.class)
class RoleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RoleService roleService;

    @Autowired
    ObjectMapper objectMapper;

    RoleDto getValidDto() {
        return RoleDto.builder()
                .name("Test Name")
                .pharmacyId(UUID.randomUUID())
                .permissions(List.of(Permission.VIEW_ORDERS))
                .build();
    }

    @Test
    void getRoleById() throws Exception {
        given(roleService.findRoleDtoById(any(UUID.class))).willReturn(getValidDto());

        ConstrainedFields fields = new ConstrainedFields(RoleDto.class);

        mockMvc.perform(get("/api/v1/role/{roleId}", UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("v1/role-get",
                        pathParameters(
                                parameterWithName("roleId").description("UUID of desired role to get")
                        ),
                        responseFields(
                                fields.withPath("id").description("ID of the role"),
                                fields.withPath("version").description("API Version of the role"),
                                fields.withPath("createdDate").description("Creation Date"),
                                fields.withPath("lastModifiedDate").description("Last Modification Date"),
                                fields.withPath("pharmacyId").description("ID of the pharmacy that this role belongs to"),
                                fields.withPath("name").description("Name of the role"),
                                fields.withPath("permissions").description("List of permissions of the role")
                        )));
    }

    @Test
    void getPermissions() throws Exception {
        mockMvc.perform(get("/api/v1/role/permissions", UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("v1/role-permissions-get"));
    }

    @Test
    void createNewRole() throws Exception {
        String roleDtoJson = objectMapper.writeValueAsString(getValidDto());

        ConstrainedFields fields = new ConstrainedFields(RoleDto.class);

        given(roleService.createNewRole(any(RoleDto.class))).willReturn(getValidDto());

        mockMvc.perform(post("/api/v1/role/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(roleDtoJson)
                .characterEncoding("utf-8"))
                .andExpect(status().isCreated())
                .andDo(document("v1/role-post",
                        requestFields(
                                fields.withPath("id").ignored(),
                                fields.withPath("version").ignored(),
                                fields.withPath("createdDate").ignored(),
                                fields.withPath("lastModifiedDate").ignored(),
                                fields.withPath("pharmacyId").description("ID of the pharmacy that this role belongs to"),
                                fields.withPath("name").description("Name of the role"),
                                fields.withPath("permissions").description("List of permissions of the role")
                        )));
    }

    @Test
    void updateRole() throws Exception {
        String roleDtoJson = objectMapper.writeValueAsString(getValidDto());

        ConstrainedFields fields = new ConstrainedFields(RoleDto.class);

        mockMvc.perform(put("/api/v1/role/{roleId}", UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(roleDtoJson)
                .characterEncoding("utf-8"))
                .andExpect(status().isNoContent())
                .andDo(document("v1/role-put",
                        pathParameters(
                                parameterWithName("roleId").description("UUID of desired role to update")
                        ),
                        requestFields(
                                fields.withPath("id").ignored(),
                                fields.withPath("version").ignored(),
                                fields.withPath("createdDate").ignored(),
                                fields.withPath("lastModifiedDate").ignored(),
                                fields.withPath("pharmacyId").description("ID of the pharmacy that this role belongs to"),
                                fields.withPath("name").description("Name of the role"),
                                fields.withPath("permissions").description("List of permissions of the role")
                        )));
    }

    @Test
    void deleteRole() throws Exception {
        ConstrainedFields fields = new ConstrainedFields(RoleDto.class);

        mockMvc.perform(delete("/api/v1/role/{roleId}", UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(document("v1/role-delete",
                        pathParameters(
                                parameterWithName("roleId").description("UUID of desired role to delete")
                        )));
    }

    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }
}