package org.mayanjun.mybatisx.api.entity;

import org.mayanjun.mybatisx.api.annotation.Column;
import org.mayanjun.mybatisx.api.annotation.PrimaryKey;
import org.mayanjun.mybatisx.api.enums.DataType;

public class IntegerEntity implements Entity<Integer> {

    @Column(type = DataType.INT, comment = "ID", unsigned = true)
    @PrimaryKey
    private Integer id;

    public IntegerEntity() {
    }

    public IntegerEntity(Integer id) {
        this.id = id;
    }


    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
