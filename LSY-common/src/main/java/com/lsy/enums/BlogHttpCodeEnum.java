package com.lsy.enums;

//通过将响应结果的状态码和消息定义为枚举类型，可以更好地管理和维护这些状态码和消息
public enum BlogHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    // 登录
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    NO_FILE(405,"文件不合法,请上传有效的文件"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
    PHONENUMBER_EXIST(502,"手机号已存在"), EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    REQUIRE_PASSWORD(506, "必需填写密码"),
    LOGIN_ERROR(505,"用户名或密码错误"),
    NO_CONTENT(507,"评论不能未空"),
    NICKNAME_EXIST(508,"昵称已存在");

    int code;
    String msg;
    BlogHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }
    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
}
