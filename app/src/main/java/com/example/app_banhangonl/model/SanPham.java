package com.example.app_banhangonl.model;

import java.io.Serializable;

public class SanPham implements Serializable {
    public int ID;
    public String Ten_SP;
    public int Gia_SP;
    public String Anh_SP;
    public String Mota_SP;
    public int ID_LoaiSP;

    public SanPham(int ID, String ten_SP, int gia_SP, String anh_SP, String mota_SP, int ID_LoaiSP) {
        this.ID = ID;
        Ten_SP = ten_SP;
        Gia_SP = gia_SP;
        Anh_SP = anh_SP;
        Mota_SP = mota_SP;
        this.ID_LoaiSP = ID_LoaiSP;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTen_SP() {
        return Ten_SP;
    }

    public void setTen_SP(String ten_SP) {
        Ten_SP = ten_SP;
    }

    public int getGia_SP() {
        return Gia_SP;
    }

    public void setGia_SP(int gia_SP) {
        Gia_SP = gia_SP;
    }

    public String getAnh_SP() {
        return Anh_SP;
    }

    public void setAnh_SP(String anh_SP) {
        Anh_SP = anh_SP;
    }

    public String getMota_SP() {
        return Mota_SP;
    }

    public void setMota_SP(String mota_SP) {
        Mota_SP = mota_SP;
    }

    public int getID_LoaiSP() {
        return ID_LoaiSP;
    }

    public void setID_LoaiSP(int ID_LoaiSP) {
        this.ID_LoaiSP = ID_LoaiSP;
    }
}
