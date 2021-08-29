package com.page.server.entity;

import com.page.server.entity.base.BaseTimeEntity;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@Entity
@Table(name = "_VALUE")
public class Value extends BaseTimeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long valueNo;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_no")
    @NotNull
    public Type type;

    @NotNull
    public Long contentNo;

    public String dataValue;
}
