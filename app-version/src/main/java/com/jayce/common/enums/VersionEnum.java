package com.jayce.common.enums;

public enum VersionEnum {
    //版本注释
    VERSION_ENUM_1_0(1.0,1008600,"1.0版本描述"),
    VERSION_ENUM_1_1(1.1,1008601,"1.1版本描述"), ;

    private Double version;
    private Integer code;
    private String value;


    VersionEnum(Double version, Integer code, String value) {
        this.version = version;
        this.code = code;
        this.value = value;
    }

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static VersionEnum getEnumValue(Double version) {
        VersionEnum[] flagEna = VersionEnum.values();
        for (VersionEnum flagEnum : flagEna) {
            if (flagEnum.getVersion().equals(version)) {
                return flagEnum;
            }
        }
        return null;
    }

}
