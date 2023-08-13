package io.hiwepy.cloud.authz.passwd.setup.dto;

public class PwdRetakeField {

    protected String nickname;
    protected String name;
    protected String desc;
    protected boolean required;

    public PwdRetakeField() {
    }

    public PwdRetakeField(String name, String desc, boolean required) {
        this.name = name;
        this.desc = desc;
        this.required = required;
    }

    public String getAlias() {
        return nickname;
    }

    public void setAlias(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

}
