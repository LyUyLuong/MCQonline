package com.javaweb.builder;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestSearchBuilder {

    private String name;
    private Integer status;

    public TestSearchBuilder (Builder builder) {
        this.name = builder.name;
        this.status = builder.status;
    }

    public static class Builder{
        private String name;
        private Integer status;

        public Builder setName(String name){
            this.name = name;
            return this;
        }

        public Builder setStatus(Integer status){
            this.status = status;
            return this;
        }

        public TestSearchBuilder build() {
            return new TestSearchBuilder(this);
        }
    }

    public String getName() {
        return name;
    }

    public Integer getStatus() {
        return status;
    }

}
