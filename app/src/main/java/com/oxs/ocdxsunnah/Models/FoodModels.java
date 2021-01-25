package com.oxs.ocdxsunnah.Models;

import java.util.ArrayList;

public class FoodModels {

    private ArrayList<Data> data;

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FoodModels{" +
                "data=" + data +
                '}';
    }

    public class Data {
        private String id;
        private String image;
        private String qty;
        private String title;
        private String energy;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getEnergy() {
            return energy;
        }

        public void setEnergy(String energy) {
            this.energy = energy;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id='" + id + '\'' +
                    ", image='" + image + '\'' +
                    ", qty='" + qty + '\'' +
                    ", title='" + title + '\'' +
                    ", energy='" + energy + '\'' +
                    '}';
        }
    }
}
