package com.oxs.ocdxsunnah.Models;

import java.util.ArrayList;

public class MakananModels {
    private ArrayList<Data> data;

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MakananModels{" +
                "data=" + data +
                '}';
    }

    public class Data{
        private String id;
        private String gambar;
        private String nama;
        private String ulasan;

        public String getGambar() {
            return gambar;
        }

        public void setGambar(String gambar) {
            this.gambar = gambar;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getUlasan() {
            return ulasan;
        }

        public void setUlasan(String ulasan) {
            this.ulasan = ulasan;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id='" + id + '\'' +
                    ", gambar='" + gambar + '\'' +
                    ", nama='" + nama + '\'' +
                    ", ulasan='" + ulasan + '\'' +
                    '}';
        }
    }
}
