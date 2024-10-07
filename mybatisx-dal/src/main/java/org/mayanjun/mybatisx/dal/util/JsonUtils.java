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

package org.mayanjun.mybatisx.dal.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * JSON util
 * @author mayanjun
 * @since 17/03/2017
 */
public class JsonUtils {

    private JsonUtils() {
    }

    private static final Logger LOG = LoggerFactory.getLogger(JsonUtils.class);

    private static final ObjectMapper MAPPER;

    static {
        MAPPER = createObjectMapper();
    }

    public static class MethodSerializer extends JsonSerializer<Method> {

        @Override
        public void serialize(Method method, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (method == null) {
                jsonGenerator.writeNull();
            } else {
                jsonGenerator.writeString(method.toGenericString());
            }
        }
    }


    /**
     * 创建一个新的ObjectMapper
     * @return
     */
    public static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss.SSS"));

        // 注册全局序列化器
        SimpleModule module = new SimpleModule();
        module.addSerializer(Method.class, new MethodSerializer());
        mapper.registerModule(module);

        return mapper;
    }

    public static String se(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            LOG.error("Serialize object error", e);
        }
        return null;
    }

    public static Map<String, Object> de(String json) {
        return de(json, Map.class);
    }

    public static <T> T de(String json, Class<T> cls) {
        try {
            if (StringUtils.isBlank(json)) return null;
            return MAPPER.readValue(json, cls);
        } catch (IOException e) {
            LOG.error("Deserialize object error", e);
        }
        return null;
    }

    public static <T> T de(String json, TypeReference<T> typeReference) {
        try {
            if (StringUtils.isBlank(json)) return null;
            return MAPPER.readValue(json, typeReference);
        } catch (IOException e) {
            LOG.error("Deserialize object error", e);
        }
        return null;
    }

    public static ObjectMapper mapper() {
        return MAPPER;
    }
}
