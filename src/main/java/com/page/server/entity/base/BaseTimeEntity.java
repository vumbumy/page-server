package com.page.server.entity.base;

import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@MappedSuperclass
@NoArgsConstructor
public class BaseTimeEntity {

    public Timestamp createdAt;

    public Timestamp updatedAt;

    public BaseTimeEntity(Timestamp createdAt, Timestamp updatedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
