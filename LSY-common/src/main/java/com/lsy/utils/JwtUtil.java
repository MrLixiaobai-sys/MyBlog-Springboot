package com.lsy.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * JWT工具类
 * 功能：
 * 1. 生成JWT，支持默认和自定义有效期。
 * 2. 解析JWT，验证其有效性，并提取数据。
 * 3. 生成加密秘钥，用于签名和验证。
 */
public class JwtUtil {

    // 默认JWT有效期为24小时（单位：毫秒）。
    public static final Long JWT_TTL = 24 * 60 * 60 * 1000L;

    // 定义JWT签名秘钥，建议使用复杂的随机字符串。
    public static final String JWT_KEY = "sangeng";

    /**
     * 生成一个唯一标识符(UUID)，用于设置JWT的ID（jti）。
     *
     * @return 去掉"-"后的UUID字符串
     */
    public static String getUUID() {
        // 使用Java的UUID生成唯一标识符，并去掉其中的"-"字符。
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成JWT（使用默认有效期）。
     *
     * @param subject 要存储在JWT中的数据（通常为JSON格式字符串）。
     * @return 生成的JWT字符串
     */
    public static String createJWT(String subject) {
        // 调用getJwtBuilder方法，生成一个带默认有效期的JWT构建器对象。
        JwtBuilder builder = getJwtBuilder(subject, null, getUUID());
        // 调用compact方法生成最终的JWT字符串。
        return builder.compact();
    }

    /**
     * 生成JWT（支持自定义有效期）。
     *
     * @param subject   要存储在JWT中的数据（通常为JSON格式字符串）。
     * @param ttlMillis 自定义有效期（单位：毫秒）。
     * @return 生成的JWT字符串
     */
    public static String createJWT(String subject, Long ttlMillis) {
        // 调用getJwtBuilder方法，传入自定义有效期。
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, getUUID());
        return builder.compact();
    }

    /**
     * 创建JWT构建器对象，配置JWT的主要内容。
     *
     * @param subject   要存储在JWT中的数据（通常为JSON格式字符串）。
     * @param ttlMillis JWT的有效期（单位：毫秒），如果为null，则使用默认值。
     * @param uuid      唯一标识符（JWT的jti字段）。
     * @return 配置完成的JwtBuilder对象
     */
    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        // 设置签名算法为HMAC-SHA256。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 获取加密秘钥。
        SecretKey secretKey = generalKey();

        // 获取当前时间戳，并转换为Date对象。
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 如果未指定有效期，使用默认的24小时有效期。
        if (ttlMillis == null) {
            ttlMillis = JwtUtil.JWT_TTL;
        }

        // 计算过期时间，并转换为Date对象。
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);

        // 配置JWT的内容，包括jti、主题、签发者、签发时间、过期时间等。
        return Jwts.builder()
                .setId(uuid) // 设置唯一标识符（JWT的jti字段）。
                .setSubject(subject) // 设置主题（JWT存储的主要数据）。
                .setIssuer("sg") // 设置签发者。
                .setIssuedAt(now) // 设置签发时间。
                .signWith(signatureAlgorithm, secretKey) // 设置签名算法和加密秘钥。
                .setExpiration(expDate); // 设置过期时间。
    }

    /**
     * 解析JWT，验证其有效性，并提取其中的载荷数据。
     *
     * @param jwt 要解析的JWT字符串。
     * @return JWT的载荷数据（Claims对象）。
     * @throws Exception 如果JWT无效或解析失败，抛出异常。
     */
    public static Claims parseJWT(String jwt) throws Exception {
        // 获取解密秘钥。
        SecretKey secretKey = generalKey();

        // 使用解析器解析JWT字符串，并返回其中的载荷数据。
        return Jwts.parser()
                .setSigningKey(secretKey) // 设置用于验证签名的秘钥。
                .parseClaimsJws(jwt) // 解析JWT字符串。
                .getBody(); // 获取解析结果中的载荷数据。
    }

    /**
     * 生成加密秘钥，用于JWT的签名和解密。
     *
     * @return 对称加密的SecretKey对象
     */
    public static SecretKey generalKey() {
        // 将Base64编码的秘钥字符串解码为字节数组。
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);

        // 创建一个AES加密的秘钥对象。
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * 测试方法：解析示例JWT字符串并打印其中的载荷数据。
     */
    public static void main(String[] args) throws Exception {
        // 示例JWT字符串（需要替换为有效的JWT）。
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjM0NTY3ODkwIiwic3ViIjoiVXNlckRhdGEiLCJpc3MiOiJzZyIsImlhdCI6MTYzODEwNjcxMiwiZXhwIjoxNjM4MTEwMzEyfQ.fWEqcJYoMveRLdPtgUciXOdFBl7RCjSPtxrvODvdXjM";

        // 调用parseJWT方法解析Token。
        Claims claims = parseJWT(token);

        // 打印解析结果。
        System.out.println(claims);
    }
}
