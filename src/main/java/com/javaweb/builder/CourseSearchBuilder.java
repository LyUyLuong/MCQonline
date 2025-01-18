package com.javaweb.builder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseSearchBuilder {

    private String name;
    private Long price;
    private String category;
    private String status;

    public CourseSearchBuilder(Builder builder) {
        this.name = builder.name;
        this.price = builder.price;
        this.category = builder.category;
        this.status = builder.status;
    }

    public static class Builder{
        private String name;
        private Long price;
        private String category;
        private String status;

        public Builder setPrice(Long price) {
            this.price = price;
            return this;
        }

        public Builder setCategory(String category) {
            this.category = category;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }


        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public CourseSearchBuilder build() {
            return new CourseSearchBuilder(this);
        }
    }

    public String getName(){
        return name;
    }

    public Long getPrice() {
        return price;
    }
    public String getCategory() {
        return category;
    }
    public String getStatus() {
        return status;
    }
}
