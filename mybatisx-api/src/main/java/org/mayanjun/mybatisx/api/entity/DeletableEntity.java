package org.mayanjun.mybatisx.api.entity;

import org.mayanjun.mybatisx.api.annotation.Column;
import org.mayanjun.mybatisx.api.enums.DataType;
import java.io.Serializable;

/**
 * Define deleted field. All subclass of this can query use {@link org.mayanjun.mybatisx.api.enums.QueryDeletedMode}
 * @param <P>
 */
public abstract class DeletableEntity<P extends Serializable> implements Entity<P> {

    @Column(type = DataType.BIT, length = "1", comment = "是否被删除")
    private Boolean deleted;

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
