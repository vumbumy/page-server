package com.page.server.service;

import com.page.server.entity.UserGroup;
import com.page.server.repository.UserGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserGroupService {
    private final UserGroupRepository userGroupRepository;

    public List<UserGroup> getAllUserGroupList() {
        return userGroupRepository.findAll();
    }

    public UserGroup getUserGroup(Long groupNo) {
        return userGroupRepository.findById(groupNo)
                .orElseThrow(() -> new IllegalArgumentException("Can't Find Group."));
    }

    public UserGroup updateUserGroup(UserGroup userGroup) {
        userGroupRepository.findById(userGroup.groupNo)
                .orElseThrow(() -> new IllegalArgumentException("Can't Find Group."));

        return userGroupRepository.save(userGroup);
    }

    public Boolean deleteUserGroup(Long groupNo) {
        userGroupRepository.findById(groupNo)
                .orElseThrow(() -> new IllegalArgumentException("Can't Find Group."));

        userGroupRepository.deleteById(groupNo);

        return userGroupRepository.findById(groupNo).isPresent();
    }
}
