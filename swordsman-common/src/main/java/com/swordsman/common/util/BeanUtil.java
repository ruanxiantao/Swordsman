package com.swordsman.common.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @Author DuChao
 * @Date 2019-10-21 11:29
 * 实体属性操作工具类
 */
public class BeanUtil {

    public static final String classz = "class";

    /**
     * 获取类中的空属性字段
     *
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<String>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }


    // 将指定的属性封装成Map
    public static Map<String, Object> convertBeanToMap(Object bean, String[] array) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Class type = bean.getClass();
        Map<String, Object> returnMap = new HashMap<String, Object>();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        String s1 = Arrays.toString(array);
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals(classz)) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (s1.contains(propertyName)) {
                    returnMap.put(propertyName, result);
                }
            }
        }
        return returnMap;
    }

    public static Object map2Object(Map<String, Object> map, Class<?> clazz) {
        if (map == null)
            return null;

        Object obj = null;
        try {
            obj = clazz.newInstance();
            List<Field> list = new ArrayList<>();
            Class classaz = obj.getClass();
            while (classaz != null){
                list.addAll(Arrays.asList(classaz.getDeclaredFields()));
                classaz = classaz.getSuperclass();
            }

            Field[] fields = list.toArray(new Field[list.size()]);
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod))
                    continue;
                // 特殊处理Es时间问题
                if (map.get(field.getName()) == null)
                    continue;
                if(field.getType().equals(Date.class) && map.get(field.getName()).getClass().equals(Long.class)){
                    field.setAccessible(true);
                    field.set(obj,new Date(Long.parseLong( map.get(field.getName()).toString())));
                }else{
                    field.setAccessible(true);
                    field.set(obj, map.get(field.getName()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
