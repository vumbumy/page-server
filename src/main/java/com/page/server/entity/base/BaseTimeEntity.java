package com.page.server.entity.base;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @CreatedDate
    @Column(
            name = "created_at",
            insertable = true,
            updatable = false
    )
    protected LocalDateTime createdAt;

    @LastModifiedDate
    @Column(
            name = "updated_at",
            insertable = true,
            updatable = true
    )
    protected LocalDateTime updatedAt;
}
