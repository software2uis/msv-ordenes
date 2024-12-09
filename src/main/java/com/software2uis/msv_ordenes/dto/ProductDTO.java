package com.software2uis.msv_ordenes.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {

    private String id;
    private Category category;
    private String name;
    private String description;
    private Double price;
    private List<Image> images;
    private List<Specification> specifications;
    private Integer quantity;
    private Double score;

    @Data
    public static class Category {
        private String id;
        private String name;
    }

    @Data
    public static class Image {
        private String color;
        private String url;
        private Boolean isMain;
    }

    @Data
    public static class Specification {
        private String name;
        private List<String> values;
    }
}
