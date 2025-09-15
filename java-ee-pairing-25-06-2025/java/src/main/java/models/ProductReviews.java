package models;

import java.util.ArrayList;

public class ProductReviews {
    public String status;
    public ArrayList<Data> data;

    public static class Data {
        public String message;
        public String author;
        public int product;
        public int likesCount;
        public ArrayList<Object> likedBy;
        public String _id;
        public boolean liked;

        public String getMessage() {
            return message;
        }

        public String getAuthor() {
            return author;
        }

        public int getProduct() {
            return product;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }
}
