package org.mayanjun.mybatisx.dal.util;

import org.mayanjun.mybatisx.api.entity.Entity;
import org.mayanjun.mybatisx.api.entity.LongEntity;
import org.mayanjun.mybatisx.dal.generator.AnnotationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;

/**
 * Entity utils
 * @since 2022/4/22
 * @author mayanjun
 */
public class EntityUtils {

    private static final Logger LOG = LoggerFactory.getLogger(EntityUtils.class);

    private EntityUtils() {
    }

    public static <T extends Entity> T newInstance(Class<T> cls, Long id) {
        try {
            T bean = cls.getConstructor(Long.class).newInstance(id);
            return bean;
        } catch (Exception e) {
            LOG.error("Can not create instance: " + cls, e);
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * 获取一个实体的实例
     * @param cls
     * @param id
     * @param <T>
     * @return
     */
    public static  <T extends LongEntity> T idInstance(Class<T> cls, Long id) {
        if (id == null) throw new IllegalArgumentException("ID can not be null");
        T instance = null;
        Exception exception = null;
        try {
            Constructor<T> c = cls.getConstructor(Long.class);
            if (!c.isAccessible()) {
                c.setAccessible(true);
            }
            instance = c.newInstance(id);
        } catch (NoSuchMethodException e) {
            try {
                Constructor<T> c = cls.getConstructor();
                if (!c.isAccessible()) {
                    c.setAccessible(true);
                }
                instance = c.newInstance();
                instance.setId(id);
            } catch (Exception e2) {
                exception = e2;
            }
        } catch (Exception e) {
            exception = e;
        }

        if (instance == null) {
            LOG.error("Can't create Entity instance: class={}, id={}, exception={}", cls, id, exception == null ? "null" : exception.getMessage());
            throw new IllegalArgumentException("Can't create entity instance");
        }

        return instance;
    }

    /**
     * 安全返回一个实体的ID
     * @param e
     * @param <E>
     * @return
     */
    private static  <E extends LongEntity> Long entityId(E e) {
        if (e != null) {
            return e.getId();
        }
        return null;
    }

    public static boolean isColumnExists(String name, Class<? extends Entity> entityType) {
        return AnnotationHelper.getAnnotationHolder(name, entityType) != null;
    }
}
