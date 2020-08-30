package tech.yiyehu.executor.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 算法执行
 * 
 * @author yiyehu
 * @email 
 * @date 2020-08-22 18:20:43
 */
@TableName("sys_exe")
public class ExeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long exeId;
	/**
	 * 执行程序位置
	 */
	private String path;
	/**
	 * 执行程序类型1:python
	 */
	private Integer type;
	/**
	 * 参数的名称
	 */
	private String params;
	/**
	 * 1:文件,2:参数params,3:混合
	 */
	private Integer paramType;
	/**
	 * 算法执行后返回值数据结构
	 */
	private String resultParams;
	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 设置：
	 */
	public void setExeId(Long exeId) {
		this.exeId = exeId;
	}
	/**
	 * 获取：
	 */
	public Long getExeId() {
		return exeId;
	}
	/**
	 * 设置：执行程序位置
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * 获取：执行程序位置
	 */
	public String getPath() {
		return path;
	}
	/**
	 * 设置：执行程序类型1:python
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：执行程序类型1:python
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：参数的名称
	 */
	public void setParams(String params) {
		this.params = params;
	}
	/**
	 * 获取：参数的名称
	 */
	public String getParams() {
		return params;
	}
	/**
	 * 设置：1:文件,2:参数params,3:混合
	 */
	public void setParamType(Integer paramType) {
		this.paramType = paramType;
	}
	/**
	 * 获取：1:文件,2:参数params,3:混合
	 */
	public Integer getParamType() {
		return paramType;
	}
	/**
	 * 设置：算法执行后返回值数据结构
	 */
	public void setResultParams(String resultParams) {
		this.resultParams = resultParams;
	}
	/**
	 * 获取：算法执行后返回值数据结构
	 */
	public String getResultParams() {
		return resultParams;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
}
