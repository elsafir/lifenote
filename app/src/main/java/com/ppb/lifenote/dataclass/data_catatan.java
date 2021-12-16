package com.ppb.lifenote.dataclass;

public class data_catatan {
    private String key, tanggal, namabarang, pengeluaran, pemasukan, keterangan;

    //Membuat Konstuktor kosong untuk membaca data snapshot
    public data_catatan(){
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getNamabarang() {
        return namabarang;
    }

    public void setNamabarang(String namabarang) {
        this.namabarang = namabarang;
    }

    public String getPengeluaran() {
        return pengeluaran;
    }

    public void setPengeluaran(String pengeluaran) {
        this.pengeluaran = pengeluaran;
    }

    public String getPemasukan() {
        return pemasukan;
    }

    public void setPemasukan(String pemasukan) {
        this.pemasukan = pemasukan;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    //Konstruktor dengan beberapa parameter, untuk mendapatkan Input Data dari User
    public data_catatan(String tanggal, String namabarang, String pengeluaran, String pemasukan, String keterangan) {
        this.tanggal = tanggal;
        this.namabarang = namabarang;
        this.pengeluaran = pengeluaran;
        this.pemasukan = pemasukan;
        this.keterangan = keterangan;
    }
}