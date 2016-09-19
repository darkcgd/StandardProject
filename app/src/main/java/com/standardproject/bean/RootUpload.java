package com.standardproject.bean;
/**
 * 上传图片实体类
 * @author cgd
 * 2016-2-25
 */
public class RootUpload {

	/**
	 * picUrl : http://58.210.52.234:16101/2016/2/25/PIC20160225104808246.png
	 * no_domain_picUrl : /2016/2/25/PIC20160225104808246.png
	 * msg : 上传成功
	 * success : true
	 */

	private String picUrl;
	private String no_domain_picUrl;
	private String msg;
	private boolean success;

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public void setNo_domain_picUrl(String no_domain_picUrl) {
		this.no_domain_picUrl = no_domain_picUrl;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public String getNo_domain_picUrl() {
		return no_domain_picUrl;
	}

	public String getMsg() {
		return msg;
	}

	public boolean isSuccess() {
		return success;
	}
}
