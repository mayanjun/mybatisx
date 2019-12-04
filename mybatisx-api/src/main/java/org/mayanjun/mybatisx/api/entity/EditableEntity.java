package org.mayanjun.mybatisx.api.entity;

import org.mayanjun.mybatisx.api.annotation.Column;
import org.mayanjun.mybatisx.api.enums.DataType;

import java.io.Serializable;
import java.util.Date;

/**
 * Represent a editable entity
 * @param <P>
 */
public abstract class EditableEntity<P extends Serializable> implements Entity<P>  {

    @Column(type = DataType.DATETIME, comment = "Created Time")
    private Date createdTime;

    @Column(type = DataType.DATETIME, comment = "Last Modified Time")
    private Date modifiedTime;

    @Column(type = DataType.VARCHAR, length = "32", comment = "Creator")
    private String creator;

    @Column(type = DataType.VARCHAR, length = "32", comment = "Last editor")
    private String editor;

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }
}
