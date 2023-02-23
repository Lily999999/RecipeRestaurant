package com.example.dishrecommender.logic.model;

import java.util.List;


public class RestaurantBean {

    public Integer code;
    public List<DataDTO> data;
    public String msg;
    public Boolean ok;

    public static class DataDTO {
        public String id;
        public String name;
        public String alias;
        public String image_url;
        public String url;
        public Boolean is_close;
        public Integer reviewCount;
        public List<CategoriesDTO> categories;
        public Float rating;
        public CoordinatesDTO coordinates;
        public List<?> transactions;
        public String price;
        public LocationDTO location;
        public String phone;
        public String display_phone;
        public Double distance;

        public static class CoordinatesDTO {
            public Double latitude;
            public Double longitude;
        }

        public static class LocationDTO {
            public String address1;
            public String address2;
            public String address3;
            public String city;
            public String zip_code;
            public String country;
            public String state;
            public List<String> display_address;
        }

        public static class CategoriesDTO {
            public String title;
            public String alias;
        }
    }
}
