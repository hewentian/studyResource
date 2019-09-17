package com.hewentian.util;

import com.hewentian.entity.Location;
import com.hewentian.entity.User;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.util.Arrays;

/**
 * <p>
 * <b>ProtostuffUtil</b> 是
 * </p>
 *
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2019-03-11 16:40:18
 * @since JDK 1.8
 */
public class ProtostuffUtil {
    private ProtostuffUtil() {
    }

    /**
     * 序列化
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> byte[] serializer(T t) {
        Schema schema = RuntimeSchema.getSchema(t.getClass());
        return ProtostuffIOUtil.toByteArray(t, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
    }

    /**
     * 反序列化
     *
     * @param bytes
     * @param c
     * @param <T>
     * @return
     */
    public static <T> T deSerializer(byte[] bytes, Class<T> c) {
        T t = null;

        try {
            t = c.newInstance();
            Schema schema = RuntimeSchema.getSchema(c);
            ProtostuffIOUtil.mergeFrom(bytes, t, schema);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return t;
    }

    public static void main(String[] args) {
        User user = new User();
        user.setId(1);
        user.setAge(20);
        user.setName("张三");
        user.setTitles(Arrays.asList("Doctor", "CEO"));
        user.setNumber(new int[]{1, 2, 3});
        user.setLocation(new Location("12.3", "48.2"));

        System.out.println(user);

        byte[] serializer = serializer(user);
        User user2 = deSerializer(serializer, User.class);
        System.out.println(user2);
    }
}
