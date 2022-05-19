package org.mayanjun.mybatisx.dal.dao;

public class ThreadLocalDataIsolation {

    private static final ThreadLocal VALUE = new ThreadLocal();

    public static void setIsolationValue(Object value) {
        VALUE.set(value);
    }

    public static Object getIsolationValue() {
        return VALUE.get();
    }

    public static void clear() {
        VALUE.remove();
    }
}
