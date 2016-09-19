package com.standardproject.bean;

public class RootAboutUs {

    /**
     * logo : http://127.0.0.1:16101/logo/logo.png
     * description : agagsdfsdf
     * qrcode : https://api.qrserver.com/v1/create-qr-code/?size=150x150&amp;data=http://www.baidu.com
     * success : true
     */

    private String logo;
    private String description;
    private String qrcode;
    private boolean success;

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getLogo() {
        return logo;
    }

    public String getDescription() {
        return description;
    }

    public String getQrcode() {
        return qrcode;
    }

    public boolean isSuccess() {
        return success;
    }
}
