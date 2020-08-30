package tech.yiyehu.executor.modules.sys.form;

import lombok.Data;

/**
 * 登录表单
 */
@Data
public class SysLoginDto {
    private String username;
    private String password;
    /**
     * google captcha
     */
    private String captcha;
    /**
     * captcha's uuid
     */
    private String uuid;
}
