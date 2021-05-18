package org.example.common.enums.sys;


//删除状态
public enum  EnumDeleteFlag {

    DELTET_FLAG_NO(1,"已删除"),
    DELTET_FLAG_YES(2,"正常，未删除");

    private int index;
    private String name;


    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    // 构造方法
    EnumDeleteFlag(int index, String name) {
        this.name = name;
        this.index = index;
    }
}
