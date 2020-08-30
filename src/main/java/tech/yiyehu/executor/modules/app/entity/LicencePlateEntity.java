package tech.yiyehu.executor.modules.app.entity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 车牌
 * 
 * @author yiyehu
 * @email 
 * @date 2020-08-22 18:36:56
 */
@TableName("tb_licence_plate")
public class LicencePlateEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 车牌号
	 */
	@TableId
	private String plateId;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 设置：车牌号
	 */
	public void setPlateId(String plateId) {
		this.plateId = plateId;
	}
	/**
	 * 获取：车牌号
	 */
	public String getPlateId() {
		return plateId;
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
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
}
