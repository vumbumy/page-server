package com.page.server.controller;

import com.page.server.entity.User;
import com.page.server.entity.UserGroup;
import com.page.server.service.UserGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/secured/admin/groups")
public class UserGroupController {
    private final UserGroupService userGroupService;

    @GetMapping(value = "")
    ResponseEntity<List<UserGroup>> getUserGroupList(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(
                userGroupService.getUserGroupListByUser(user)
        );
    }

    @PostMapping(value = "")
    ResponseEntity<UserGroup> createUserGroup(@AuthenticationPrincipal User user, @RequestBody UserGroup userGroup) {
        return ResponseEntity.ok(
                userGroupService.addUserGroup(user, userGroup)
        );
    }

    @GetMapping(value = "/{groupNo}")
    ResponseEntity<UserGroup> getUserGroup(@AuthenticationPrincipal User user, @PathVariable Long groupNo) {
        return ResponseEntity.ok(
                userGroupService.getUserGroup(user, groupNo)
        );
    }

    @PutMapping(value = "")
    ResponseEntity<UserGroup> updateUserGroup(@AuthenticationPrincipal User user, @RequestBody UserGroup userGroup) {
        return ResponseEntity.ok(
                userGroupService.updateUserGroup(user, userGroup)
        );
    }

    @DeleteMapping(value = "/{groupNo}")
    ResponseEntity<Boolean> deleteUserGroup(@AuthenticationPrincipal User user, @PathVariable Long groupNo) {
        return ResponseEntity.ok(
                userGroupService.deleteUserGroup(user, groupNo)
        );
    }
}
