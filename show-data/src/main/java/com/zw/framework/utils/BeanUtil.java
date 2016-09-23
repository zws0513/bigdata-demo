package com.zw.framework.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.io.IOUtils;

/**
 * 对Bean进行操作的相关工具方法
 * 
 * @author jiangbin
 *
 */
public class BeanUtil {
    /**
     * 将Bean对象转换成Map对象，将忽略掉值为null或size=0的属性
     * 
     * @param obj
     *            对象
     * @return 若给定对象为null则返回size=0的map对象
     */
    public static Map<String, Object> toMap(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (obj == null) {
            return map;
        }
        BeanMap beanMap = new BeanMap(obj);
        Iterator<String> it = beanMap.keyIterator();
        while (it.hasNext()) {
            String name = it.next();
            Object value = beanMap.get(name);
            // 转换时会将类名也转换成属性，此处去掉
            if (value != null && !name.equals("class")) {
                map.put(name, value);
            }
        }
        return map;
    }

    /**
     * 该方法将给定的所有对象参数列表转换合并生成一个Map，对于同名属性，依次由后面替换前面的对象属性
     * 
     * @param objs
     *            对象列表
     * @return 对于值为null的对象将忽略掉
     */
    public static Map<String, Object> toMap(Object... objs) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (Object object : objs) {
            if (object != null) {
                map.putAll(toMap(object));
            }
        }
        return map;
    }

    /**
     * 获取接口的泛型类型，如果不存在则返回null
     * 
     * @param clazz
     * @return
     */
    public static Class<?> getGenericClass(Class<?> clazz) {
        Type t = clazz.getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            Type[] p = ((ParameterizedType) t).getActualTypeArguments();
            return ((Class<?>) p[0]);
        }
        return null;
    }

    /**
     * 将一个JavaBean风格对象的属性值拷贝到另一个对象的同名属性中 (如果不存在同名属性的就不拷贝） 
     * <p>
     * 拷贝元：不会拷贝基类的属性值；
     * 拷贝目标：会赋值给基类的属性。
     * 
     * @param srcObject
     *            复制元
     * @param dstObject
     *            复制目标
     * @return dstObject
     */
    public static <T> T copyProperties(Object srcObject, T dstObject) {
        if (srcObject == null || dstObject == null) {
            return dstObject;
        }

        Class<? extends Object> srcClazz = srcObject.getClass();
        Class<? extends Object> dstClazz = dstObject.getClass();
        Class<? extends Object> superClazz = null;

        // 得到Class对象所表征的类的所有属性(包括私有属性)
        Field[] srcFields = srcClazz.getDeclaredFields();
        for (int i = 0; i < srcFields.length; i++) {
            String srcFieldName = srcFields[i].getName();
            Field dstField = null;

            // 得到targetClz对象所表征的类的名为fieldName的属性，不存在就进入下次循环
            try {
                dstField = dstClazz.getDeclaredField(srcFieldName);
            } catch (NoSuchFieldException e) {
                try {
                    if (superClazz == null) {
                        superClazz = dstClazz.getSuperclass();
                    }
                    dstField = superClazz.getDeclaredField(srcFieldName);
                } catch (Exception e1) {
                    continue;
                }
            }
            Method getMethod = null;
            // 判断sourceClz字段类型和targetClz同名字段类型是否相同
            if (srcFields[i].getType() == dstField.getType()) {
                // 由属性名字得到对应get和set方法的名字
                String fieldName = srcFieldName.substring(0, 1).toUpperCase() + srcFieldName.substring(1);
                try {
                    // 由方法的名字得到get和set方法的Method对象
                    getMethod = srcClazz.getDeclaredMethod("get" + fieldName, new Class[] {});
                    Method setMethod = dstClazz.getDeclaredMethod("set" + fieldName, srcFields[i].getType());
                    // 调用source对象的getMethod方法
                    Object result = getMethod.invoke(srcObject, new Object[] {});
                    // 调用target对象的setMethod方法
                    setMethod.invoke(dstObject, result);
                } catch (NoSuchMethodException e) {
                    try {
                        Method setMethod = superClazz.getDeclaredMethod("set" + fieldName, srcFields[i].getType());
                        // 调用source对象的getMethod方法
                        Object result = getMethod.invoke(srcObject, new Object[] {});
                        // 调用target对象的setMethod方法
                        setMethod.invoke(dstObject, result);
                    } catch (Exception e1) {
                        continue;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
        return dstObject;
    }

    /**
     * 字符串转化为对象
     * <p>
     * 字符串格式：『成员变量=值;成员变量=值;成员变量=值』
     * 例如，value是"guide;guideStartTime=2015-07-18 18:00:00;quaRes=true;count=3"
     * 转化为Guide类的对象，其中等号前的是成员变量，等号后的是对应的值。
     * </p>
     * @param t 待赋值实例
     * @param value 字符串
     * @return 赋值后的实例
     */
    public static <T> T convert(T t, String value) {
        Class<?>  cls  = null;
        try {
            String[] values = value.split(";");
            cls = (Class<?>) t.getClass();
            for (int i = 0; i < values.length; i++) {
                String[] v =values[i].split("=");
//                System.out.println(v[0] + " " + v[1]);
                String fieldName = getMethodName(v[0]);
                Method get_Method = cls.getMethod("get" + fieldName);
                Class<?> fieldType = get_Method.getReturnType();
                Method set_Method = cls.getMethod("set" + fieldName, fieldType);
                String type = fieldType.getName();
                if (type.equals("java.util.Date")) {
                    set_Method.invoke(t, DateUtil.strToDate(v[1], DateUtil.FORMAT_YYYYMMDD_HHMISS));
                } else if (type.equals("java.lang.Integer")) {
                    set_Method.invoke(t, Integer.valueOf(v[1]));
                } else {
                    set_Method.invoke(t, fieldType.cast(v[1]));
                }

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return t;
    }

    // 把一个字符串的第一个字母大写、效率是最高的、
    private static String getMethodName(String fildeName) {
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

    public static String toString(Object obj) {
        ObjectMapper objectMapper = null;
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        String jsonString = "{}";
        if(null==objectMapper){
            objectMapper = new ObjectMapper();
        }
        try {
            JsonGenerator jsonGenerator = new ObjectMapper().getFactory().createGenerator(byteOutput, JsonEncoding.UTF8);
            objectMapper.writeValue(jsonGenerator, obj);
            jsonString = byteOutput.toString(JsonEncoding.UTF8.getJavaName());
        } catch (IOException e) {
        } finally {
            IOUtils.closeQuietly(byteOutput);
        }
        return jsonString;
    }
}