package com.page.server.dao;

import com.page.server.constant.AccessRight;

public interface PermissionDao {
    Long getContentNo();

    AccessRight getAccessRight();
}
