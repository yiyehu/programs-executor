package tech.yiyehu.executor.modules.sys.controller;

import tech.yiyehu.executor.modules.sys.entity.SysUserEntity;
import tech.yiyehu.executor.modules.sys.form.SysLoginDto;
import tech.yiyehu.executor.modules.sys.service.SysCaptchaService;
import tech.yiyehu.executor.modules.sys.service.SysUserService;
import tech.yiyehu.executor.modules.sys.service.SysUserTokenService;
import tech.yiyehu.executor.common.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * 登录相关
 */
@RestController
@Api("系统-登录接口")
public class SysLoginController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserTokenService sysUserTokenService;
	@Autowired
	private SysCaptchaService sysCaptchaService;

	/**
	 * 验证码
	 */
	@GetMapping("captcha.jpg")
	@ApiOperation("captcha.jpg 验证码")
	public void captcha(HttpServletResponse response, String uuid)throws IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");

		//获取图片验证码
		BufferedImage image = sysCaptchaService.getCaptcha(uuid);

		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
		IOUtils.closeQuietly(out);
	}

	/**
	 * 登录
	 */
	@PostMapping("/sys/login")
	@ApiOperation("登录")
	public Map<String, Object> login(@RequestBody SysLoginDto form)throws IOException {
		boolean captcha = sysCaptchaService.validate(form.getUuid(), form.getCaptcha());
		if(!captcha){
			return ResultUtil.error("验证码不正确");
		}
		//用户信息
		SysUserEntity user = sysUserService.queryByUserName(form.getUsername());

		//账号不存在、密码错误
		if(user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
			return ResultUtil.error("账号或密码不正确");
		}

		//账号锁定
		if(user.getStatus() == 0){
			return ResultUtil.error("账号已被锁定,请联系管理员");
		}

		//生成token，并保存到数据库
		ResultUtil resultUtil = sysUserTokenService.createToken(user.getUserId());
		return resultUtil;
	}


	/**
	 * 退出
	 */
	@PostMapping("/sys/logout")
	@ApiOperation("登出")
	public ResultUtil logout() {
		sysUserTokenService.logout(getUserId());
		return ResultUtil.ok();
	}
	
}
