package com.example.app_banhangonl.model;

public class LoaiSp {
    public int id;
    public  String TenLoaiSP;
    public  String HinhAnhSP;

    public LoaiSp(int id, String tenLoaiSP, String hinhAnhSP) {
        this.id = id;
        TenLoaiSP = tenLoaiSP;
        HinhAnhSP = hinhAnhSP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenLoaiSP() {
        return TenLoaiSP;
    }

    public void setTenLoaiSP(String tenLoaiSP) {
        TenLoaiSP = tenLoaiSP;
    }

    public String getHinhAnhSP() {
        return HinhAnhSP;
    }

    public void setHinhAnhSP(String hinhAnhSP) {
        HinhAnhSP = hinhAnhSP;
    }


}
