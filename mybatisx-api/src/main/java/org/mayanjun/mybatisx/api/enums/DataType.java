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

package org.mayanjun.mybatisx.api.enums;


/**
 * DataType
 *
 * @author mayanjun(8/19/15)
 */
public enum DataType {
    BIT("b'0'", false,  1),

    TINYINT("0", true, 1),

    SMALLINT("0", true, 2),

    MEDIUMINT("0", true, 3),

    INT("0", true, 4),

    BIGINT("0", true, 8),
    
    FLOAT("0", true, 4),
    DOUBLE("0", true, 8),
    DECIMAL("0", true, -1),


    CHAR("''", true, 255),
    VARCHAR("''", false, 65536),
    TINYTEXT(null, false, 255),
    TEXT(null, false, 65536),
    MEDIUMTEXT(null, false, 16777215),
    LONGTEXT(null, false, 4294967295l),

    BLOB(null, false, 65536),
    TINYBLOB(null, false, 255),
    MEDIUMBLOB(null, false, 1677215),
    LONGBLOB(null, false, 4294967295l),
    BINARY("''", false, Long.MAX_VALUE),
    VARBINARY("''", false, -1),
    ENUM(null, false, 2),
    SET(null, false, 3),

    DATE("'0000-00-00'", false ,3),
    DATETIME("'0000-00-00 00:00:00'", false, 8),
    TIMESTAMP("'0000-00-00 00:00:00'", false, 4),
    TIME("'00:00:00'", false, 3),
    YEAR("'0000'", false, 1);

    private String defaultValue;
    private boolean numeric;

    private long dateTypeSize;

    DataType(String defaultValue, boolean numeric, long dateTypeSize) {
        this.defaultValue = defaultValue;
        this.numeric = numeric;
        this.dateTypeSize = dateTypeSize;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public boolean isNumeric() {
        return numeric;
    }

    public long getDateTypeSize() {
        return dateTypeSize;
    }
}
