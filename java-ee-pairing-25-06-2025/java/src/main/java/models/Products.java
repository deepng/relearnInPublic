package models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Products {
    public String status;
    public ArrayList<Data> data;

    public String getStatus() {
        return status;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public static class Data {
        public int id;
        public String name;
        public String description;
        public double price;
        public double deluxePrice;
        public String image;
        public String createdAt;
        public String updatedAt;
        public Object deletedAt;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }
    }
}
