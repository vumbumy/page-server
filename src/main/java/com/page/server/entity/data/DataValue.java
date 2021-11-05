package com.page.server.entity.data;

import com.page.server.entity.base.BaseTimeEntity;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@Entity
@Table(name = "_DATA_VALUE")
public class DataValue extends BaseTimeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long valueNo;

//    @OneToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "column_no")
//    @NotNull
//    public Type type;

    @NotNull
    public Long columnNo;

    @NotNull
    public Long contentNo;

    public String value;

    @Builder
    public DataValue(Long valueNo, @NotNull Long columnNo, @NotNull Long contentNo, String value) {
        this.valueNo = valueNo;
        this.columnNo = columnNo;
        this.contentNo = contentNo;
        this.value = value;
    }
}
