/*
 * Copyright 2016-2018 mayanjun.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
