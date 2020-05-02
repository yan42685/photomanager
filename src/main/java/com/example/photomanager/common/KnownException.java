package com.example.photomanager.common;
import com.example.photomanager.enums.ExceptionEnum;
import lombok.Getter;


/**
 * 已知异常
 *
 * @author alex
 */
@Getter
public class KnownException extends RuntimeException {
    private static final long serialVersionUID = 3413470958629677916L;
    /**
     * 异常代码
     */
    private Integer errorCode;

    public KnownException(ExceptionEnum e) {
        super(e.getErrorMsg());
        this.errorCode = e.getErrorCode();
    }
}
