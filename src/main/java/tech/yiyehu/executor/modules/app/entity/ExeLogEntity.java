package tech.yiyehu.executor.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 执行日志
 * 
 * @author yiyehu
 * @email 
 * @date 2020-08-22 18:36:56
 */
@TableName("tb_exe_log")
public class ExeLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long logId;
	/**
	 * 执行算法 ID
	 */
	private Long exeId;
	/**
	 * 用户 ID
	 */
	private Long userId;
	/**
	 * 参数类型1：file 2:其他3：混合
	 */
	private Integer paramType;
	/**
	 * 文件 ID
	 */
	private Long fileId;
	/**
	 * 其他参数类型2时：传入的参数
	 */
	private String params;
	/**
	 * 执行结果
	 */
	private String result;
	/**
	 * 执行时间
	 */
	private Date createTime;

	/**
	 * 设置：
	 */
	public void setLogId(Long logId) {
		this.logId = logId;
	}
	/**
	 * 获取：
	 */
	public Long getLogId() {
		return logId;
	}
	/**
	 * 设置：执行算法 ID
	 */
	public void setExeId(Long exeId) {
		this.exeId = exeId;
	}
	/**
	 * 获取：执行算法 ID
	 */
	public Long getExeId() {
		return exeId;
	}
	/**
	 * 设置：用户 ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户 ID
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：参数类型1：file 2:其他3：混合
	 */
	public void setParamType(Integer paramType) {
		this.paramType = paramType;
	}
	/**
	 * 获取：参数类型1：file 2:其他3：混合
	 */
	public Integer getParamType() {
		return paramType;
	}
	/**
	 * 设置：文件 ID
	 */
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	/**
	 * 获取：文件 ID
	 */
	public Long getFileId() {
		return fileId;
	}
	/**
	 * 设置：其他参数类型2时：传入的参数
	 */
	public void setParams(String params) {
		this.params = params;
	}
	/**
	 * 获取：其他参数类型2时：传入的参数
	 */
	public String getParams() {
		return params;
	}
	/**
	 * 设置：执行结果
	 */
	public void setResult(String result) {
		this.result = result;
	}
	/**
	 * 获取：执行结果
	 */
	public String getResult() {
		return result;
	}
	/**
	 * 设置：执行时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：执行时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
}
