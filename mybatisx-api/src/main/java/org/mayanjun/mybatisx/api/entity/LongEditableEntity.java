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
import org.mayanjun.mybatisx.api.annotation.PrimaryKey;
import org.mayanjun.mybatisx.api.enums.DataType;

import java.util.Date;

/**
 * Represents a entity bean that can be serialized to database.
 * The type of id field is set to Long
 *
 * @author mayanjun
 * @since 0.0.1
 */
public class LongEditableEntity extends LongEntity implements EditableEntity<Long> {

    @Column(type = DataType.DATETIME, comment = "Created Time")
    private Date createdTime;

    @Column(type = DataType.DATETIME, comment = "Last Modified Time")
    private Date modifiedTime;

    @Column(type = DataType.VARCHAR, length = "32", comment = "Creator")
    private String creator;

    @Column(type = DataType.VARCHAR, length = "32", comment = "Last editor")
    private String editor;

    public LongEditableEntity() {
    }

    public LongEditableEntity(Long id) {
        super(id);
    }

    @Override
    public Date getCreatedTime() {
        return createdTime;
    }

    @Override
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public Date getModifiedTime() {
        return modifiedTime;
    }

    @Override
    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    @Override
    public String getCreator() {
        return creator;
    }

    @Override
    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public String getEditor() {
        return editor;
    }

    @Override
    public void setEditor(String editor) {
        this.editor = editor;
    }
}
