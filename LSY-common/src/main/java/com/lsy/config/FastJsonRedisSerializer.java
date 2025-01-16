package com.lsy.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.parser.ParserConfig;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

/**
 * Redis 使用 FastJson 实现序列化与反序列化
 *
 * @param <T> 泛型类型，支持任意对象的序列化
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {

    // 默认字符集为 UTF-8
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    // 需要序列化/反序列化的目标类
    private final Class<T> clazz;

    static {
        // 启用全局的自动类型支持
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }

    /**
     * 构造函数
     *
     * @param clazz 目标类型的 Class 对象
     */
    public FastJsonRedisSerializer(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * 序列化方法
     *
     * @param t 要序列化的对象
     * @return 序列化后的字节数组
     * @throws SerializationException 序列化异常
     */
    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        // 使用 FastJson 将对象转换为 JSON 字符串，并记录类型信息
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }

    /**
     * 反序列化方法
     *
     * @param bytes 序列化的字节数组
     * @return 反序列化后的对象
     * @throws SerializationException 反序列化异常
     */
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        // 将字节数组转为字符串
        String str = new String(bytes, DEFAULT_CHARSET);
        // 使用 FastJson 将 JSON 字符串解析为对象
        return JSON.parseObject(str, clazz);
    }
}
