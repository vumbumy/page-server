package com.page.server.entity.data;

import com.page.server.entity.base.BaseTimeEntity;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@Entity
@Table(name = "_DATA_CELL")
public class DataCell extends BaseTimeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long cellNo;

//    @OneToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "column_no")
//    @NotNull
//    public Type type;

    @NotNull
    public Long columnNo;

    @NotNull
    public Long contentNo;

    public String cellValue;

    @Builder
    public DataCell(Long cellNo, @NotNull Long columnNo, @NotNull Long contentNo, String cellValue) {
        this.cellNo = cellNo;
        this.columnNo = columnNo;
        this.contentNo = contentNo;
        this.cellValue = cellValue;
    }
}
