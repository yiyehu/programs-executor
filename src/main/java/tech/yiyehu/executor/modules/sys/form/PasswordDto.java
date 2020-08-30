package tech.yiyehu.executor.modules.sys.form;

import lombok.Data;

/**
 * 密码表单
 */
@Data
public class PasswordDto {
    /**
     * 原密码
     */
    private String password;
    /**
     * 新密码
     */
    private String newPassword;

}
