package com.page.server.controller;

import com.page.server.entity.UserGroup;
import com.page.server.service.UserGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/secured/admin/groups")
public class UserGroupController {
    private final UserGroupService userGroupService;

    @GetMapping(value = "")
    ResponseEntity<List<UserGroup>> getUserGroupList() {
        return ResponseEntity.ok(
                userGroupService.getAllUserGroupList()
        );
    }

    @GetMapping(value = "/{groupNo}")
    ResponseEntity<UserGroup> getUserGroup(@PathVariable Long groupNo) {
        return ResponseEntity.ok(
                userGroupService.getUserGroup(groupNo)
        );
    }

    @PutMapping(value = "")
    ResponseEntity<UserGroup> updateUserGroup(@RequestBody UserGroup userGroup) {
        return ResponseEntity.ok(
                userGroupService.updateUserGroup(userGroup)
        );
    }

    @DeleteMapping(value = "/{groupNo}")
    ResponseEntity<Boolean> deleteUserGroup(@PathVariable Long groupNo) {
        return ResponseEntity.ok(
                userGroupService.deleteUserGroup(groupNo)
        );
    }
}
