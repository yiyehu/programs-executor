package tech.yiyehu.executor.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 系统用户 Token
 */
@Data
@TableName("sys_user_token")
public class SysUserTokenEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@TableId(type = IdType.INPUT)
	private Long userId;
	private String token;
	private Date expireTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;

}
