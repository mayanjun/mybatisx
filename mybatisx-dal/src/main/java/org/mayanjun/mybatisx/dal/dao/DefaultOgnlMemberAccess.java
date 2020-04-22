package org.mayanjun.mybatisx.dal.dao;

import org.apache.ibatis.ognl.MemberAccess;
import org.apache.ibatis.reflection.Reflector;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Member;
import java.util.Map;

public class DefaultOgnlMemberAccess implements MemberAccess {

    private final boolean canControlMemberAccessible = Reflector.canControlMemberAccessible();

    DefaultOgnlMemberAccess() {
    }

    public Object setup(Map context, Object target, Member member, String propertyName) {
        Object result = null;
        if (this.isAccessible(context, target, member, propertyName)) {
            AccessibleObject accessible = (AccessibleObject)member;
            if (!accessible.isAccessible()) {
                result = Boolean.FALSE;
                accessible.setAccessible(true);
            }
        }

        return result;
    }

    public void restore(Map context, Object target, Member member, String propertyName, Object state) {
    }

    public boolean isAccessible(Map context, Object target, Member member, String propertyName) {
        return this.canControlMemberAccessible;
    }
}
