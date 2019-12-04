package org.mayanjun.mybatisx.api.entity;

import org.mayanjun.mybatisx.api.annotation.Column;
import org.mayanjun.mybatisx.api.annotation.PrimaryKey;
import org.mayanjun.mybatisx.api.enums.DataType;

public class LongEntity implements Entity<Long> {

    @Column(type = DataType.BIGINT, comment = "ID", unsigned = true)
    @PrimaryKey
    private Long id;

    public LongEntity() {
    }

    public LongEntity(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
