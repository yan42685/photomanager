package com.example.photomanager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 已知异常枚举
 * @author alex
 */
@Getter
@AllArgsConstructor
public enum ExceptionEnum {
    /**
     * 异常码规则：
     * 正数表示内部异常
     * 负数表示外部异常，应由调用者处理
     */
    FILE_IO_EXCEPTION(51, "文件读写失败"),
    TO_BE_IMPLEMENTED(50, "该功能还未实现"),
    UNKNOWN_EXCEPTION(99, "服务器未知异常"),
    NOT_REGISTER(-1, "用户未注册"),
    NOT_LOGIN(-2, "用户未登录"),
    NO_PERMISSION(-3, "用户没有足够权限"),
    INVALID_PARAM(-4, "参数校验失败"),
    IMAGE_UPLOAD_FAIL(-5, "图片为空或者不是jpg，png，gif格式"),
    DOWNLOADING_FILE_NOT_EXITS(-6, "要下载的文件不存在");

    private int errorCode;
    private String errorMsg;


}
