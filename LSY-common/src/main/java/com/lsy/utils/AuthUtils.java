package com.lsy.utils;


import com.lsy.domain.entity.LoginUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AuthUtils {

    private static final Logger logger = LoggerFactory.getLogger(AuthUtils.class);

    /**
     * 获取当前登录用户的 ID
     *
     * @return 当前用户的 ID
     * @throws AuthenticationException 如果用户未登录
     */
    public static Long getCurrentUserId() {
        LoginUser userDetails = getCurrentUserDetails();

            return userDetails.getUser().getId();

    }

    /**
     * 获取当前登录用户的 UserDetails 信息
     *
     * @return 当前登录用户的 UserDetails
     * @throws IllegalStateException 如果用户未登录
     */
    public static LoginUser getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new IllegalStateException("No authenticated user found in SecurityContext");
        }
        return (LoginUser) authentication.getPrincipal(); // 返回 UserDetails 对象
    }

    /**
     * 获取当前登录用户的用户名
     *
     * @return 当前用户的用户名
     * @throws AuthenticationException 如果用户未登录
     */
    public static String getCurrentUsername() {
        return getCurrentUserDetails().getUsername();
    }

    /**
     * 判断当前用户是否已登录
     *
     * @return 如果用户已登录，返回 true；否则返回 false
     */
    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal());
    }

    /**
     * 获取当前用户的权限信息（如果有的话）
     *
     * @return 用户的权限列表
     */
    public static String[] getCurrentUserAuthorities() {
        if (!isAuthenticated()) {
            return new String[0]; // 未登录的用户没有权限
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .toArray(String[]::new);
    }

    /**
     * 判断当前用户是否具有某个权限
     *
     * @param authority 权限标识
     * @return 如果当前用户拥有该权限，返回 true；否则返回 false
     */
    public static boolean hasAuthority(String authority) {
        for (String auth : getCurrentUserAuthorities()) {
            if (auth.equals(authority)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取当前登录用户的角色（如果有的话）
     *
     * @return 用户角色列表
     */
    public static String[] getCurrentUserRoles() {
        return getCurrentUserAuthorities(); // 在许多系统中，角色也是权限的一部分
    }

    /**
     * 通过 JWT 获取用户 ID（如果使用 JWT 认证）
     * 如果你在项目中使用了 JWT，可以实现基于 JWT 的方法来解析当前用户 ID
     *
     * @param token JWT token
     * @return 当前用户的 ID(String)
     */
    public static String getCurrentUserIdFromJwt(String token) throws Exception {
        // 这里你需要根据你项目的 JWT 解码方式来获取用户ID
        // 假设你有 JwtUtil 工具类来解析 token
        return JwtUtil.parseJWT(token).getSubject();
    }

    //是否是管理员
    public static Boolean isAdmin(){
        Long id = getCurrentUserDetails().getUser().getId();
        return id != null && 1L == id;
    }

}

