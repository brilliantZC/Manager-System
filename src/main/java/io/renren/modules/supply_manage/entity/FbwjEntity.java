package io.renren.modules.supply_manage.entity;

public class FbwjEntity {
    private String wjlx;//文件类型
    private String wjmc;//文件名称
    private String wjzt;//文件状态
    private String zztdm;//总状态

    public FbwjEntity() {
    }

    public FbwjEntity(String wjlx, String wjmc, String wjzt, String zztdm) {
        this.wjlx = wjlx;
        this.wjmc = wjmc;
        this.wjzt = wjzt;
        this.zztdm = zztdm;
    }

    public String getWjlx() {
        return wjlx;
    }

    public String getWjmc() {
        return wjmc;
    }

    public String getWjzt() {
        return wjzt;
    }

    public String getZztdm() {
        return zztdm;
    }

    public void setWjlx(String wjlx) {
        this.wjlx = wjlx;
    }

    public void setWjmc(String wjmc) {
        this.wjmc = wjmc;
    }

    public void setWjzt(String wjzt) {
        this.wjzt = wjzt;
    }

    public void setZztdm(String zztdm) {
        this.zztdm = zztdm;
    }

    @Override
    public String toString() {
        return "FbwjEntity{" +
                "wjlx='" + wjlx + '\'' +
                ", wjmc='" + wjmc + '\'' +
                ", wjzt='" + wjzt + '\'' +
                ", zztdm='" + zztdm + '\'' +
                '}';
    }
}
