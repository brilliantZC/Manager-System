package io.renren.modules.supply_manage.entity;

public class FbwjEntity {
    private String name;//发布人
    private String wjlxmc;//文件类型
    private String wjlxdm;//文件类型代码
    private String wjmc;//文件名称
    private String wjdz;//文件地址
    private String ztdm;//文件状态代码
    private String ztmc;//文件状态名称
    private String zztdm;//总状态代码
    private String zztmc;//总状态名称

    public FbwjEntity() {
    }

    public FbwjEntity(String name,String wjlxmc, String wjlxdm, String wjmc, String wjdz, String ztdm, String ztmc, String zztdm, String zztmc) {
        this.name = name;
        this.wjlxmc = wjlxmc;
        this.wjlxdm = wjlxdm;
        this.wjmc = wjmc;
        this.wjdz = wjdz;
        this.ztdm = ztdm;
        this.ztmc = ztmc;
        this.zztdm = zztdm;
        this.zztmc = zztmc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWjlxmc() {
        return wjlxmc;
    }

    public String getWjlxdm() {
        return wjlxdm;
    }

    public String getWjmc() {
        return wjmc;
    }

    public String getWjdz() {
        return wjdz;
    }

    public String getZtdm() {
        return ztdm;
    }

    public String getZtmc() {
        return ztmc;
    }

    public String getZztdm() {
        return zztdm;
    }

    public String getZztmc() {
        return zztmc;
    }

    public void setWjlxmc(String wjlxmc) {
        this.wjlxmc = wjlxmc;
    }

    public void setWjlxdm(String wjlxdm) {
        this.wjlxdm = wjlxdm;
    }

    public void setWjmc(String wjmc) {
        this.wjmc = wjmc;
    }

    public void setWjdz(String wjdz) {
        this.wjdz = wjdz;
    }

    public void setZtdm(String ztdm) {
        this.ztdm = ztdm;
    }

    public void setZtmc(String ztmc) {
        this.ztmc = ztmc;
    }

    public void setZztdm(String zztdm) {
        this.zztdm = zztdm;
    }

    public void setZztmc(String zztmc) {
        this.zztmc = zztmc;
    }

    @Override
    public String toString() {
        return "FbwjEntity{" +
                "name='" + name + '\'' +
                ", wjlxmc='" + wjlxmc + '\'' +
                ", wjlxdm='" + wjlxdm + '\'' +
                ", wjmc='" + wjmc + '\'' +
                ", wjdz='" + wjdz + '\'' +
                ", ztdm='" + ztdm + '\'' +
                ", ztmc='" + ztmc + '\'' +
                ", zztdm='" + zztdm + '\'' +
                ", zztmc='" + zztmc + '\'' +
                '}';
    }
}
