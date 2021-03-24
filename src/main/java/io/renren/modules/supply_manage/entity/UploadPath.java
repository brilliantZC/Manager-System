package io.renren.modules.supply_manage.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UploadPath {
    @Value("${UploadPath.path}") //上传文件路径
    private String path;

    @Value("${UploadPath.httppath}")
    private String httppath;

    @Value("${UploadPath.xnljdyml}")
    private String xnljdyml;

    @Value("${UploadPath.sbsmbwj}")
    private String sbsmbwj;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHttppath() {
        return httppath;
    }

    public void setHttppath(String httppath) {
        this.httppath = httppath;
    }

    public String getXnljdyml() {
        return xnljdyml;
    }

    public void setXnljdyml(String xnljdyml) {
        this.xnljdyml = xnljdyml;
    }

    public String getSbsmbwj() {
        return sbsmbwj;
    }

    public void setSbsmbwj(String sbsmbwj) {
        this.sbsmbwj = sbsmbwj;
    }
}
