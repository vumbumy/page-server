package com.page.server.dao;

import com.page.server.constant.AccessRight;

public interface PermissionDao {
    AccessRight getAccessRight();

    interface Content extends PermissionDao {
        Long getContentNo();
    }

    interface No extends PermissionDao{
        Long getPermissionNo();
    }
}
