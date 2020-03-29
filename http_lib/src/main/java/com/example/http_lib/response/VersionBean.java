package com.example.http_lib.response;

/**
 *
 */
public class VersionBean {

    //{"configmandatory":1,"description":"111","version":"1.0.0","url":"https://ssyerv1.oss-cn-hangzhou.aliyuncs.com/picture/0c31eb0accd8408eaa02f1423438507c.jpg"}

    private String description;
    private String url;
    private String version;
    private Integer configmandatory;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getConfigmandatory() {
        return configmandatory;
    }

    public void setConfigmandatory(Integer configmandatory) {
        this.configmandatory = configmandatory;
    }

    public boolean isNeedUpdate(String localVersion){
        String[] localStrs = null;
        String[] platformStrs = null;
        if(localVersion.contains(".")){
            localStrs = localVersion.split(".");
        }
        if(version.contains(".")){
            platformStrs = version.split(".");
        }
        if(localStrs!=null && platformStrs!=null && platformStrs.length == 3 && localStrs.length == 3){
            if(Integer.parseInt(platformStrs[0]) > Integer.parseInt(localStrs[0])){
                return true;
            }
            if(Integer.parseInt(platformStrs[1]) > Integer.parseInt(localStrs[1])){
                return true;
            }
            int platformVersionNum = Integer.parseInt(version.replace(".",""));
            int localVersionNum = Integer.parseInt(localVersion.replace(".",""));
            if(platformVersionNum>localVersionNum){
                return true;
            }
        }
        return false;
    }
}
