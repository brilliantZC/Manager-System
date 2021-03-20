package io.renren.modules.bill_manage.entity;
//用于日账单饼图绘制
public class CharpieEntity {
    Float value;
    String name;

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
