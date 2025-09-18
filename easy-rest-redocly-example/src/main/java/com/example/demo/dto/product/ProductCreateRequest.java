package com.example.demo.dto.product;

import com.example.demo.dto.common.ProductCategory;
import com.example.easyrestredoclylib.core.DocsDescription;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

public class ProductCreateRequest {

    @DocsDescription("상품명")
    @NotBlank(message = "상품명은 필수입니다")
    @Size(max = 100, message = "상품명은 100자 이하여야 합니다")
    private String name;

    @DocsDescription("상품 설명")
    @Size(max = 1000, message = "상품 설명은 1000자 이하여야 합니다")
    private String description;

    @DocsDescription("상품 가격")
    @NotNull(message = "가격은 필수입니다")
    @DecimalMin(value = "0.0", inclusive = false, message = "가격은 0보다 커야 합니다")
    private BigDecimal price;

    @DocsDescription("상품 카테고리")
    @NotNull(message = "카테고리는 필수입니다")
    private ProductCategory category;

    @DocsDescription("재고 수량")
    @NotNull(message = "재고 수량은 필수입니다")
    private Integer stockQuantity;

    @DocsDescription("상품 태그 목록 (선택사항)")
    private List<String> tags;

    @DocsDescription("상품 브랜드 (선택사항)")
    private String brand;

    @DocsDescription("상품 무게 (kg, 선택사항)")
    private Double weight;

    @DocsDescription("상품 치수 (선택사항)")
    private ProductDimensions dimensions;

    public ProductCreateRequest() {}

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public ProductDimensions getDimensions() {
        return dimensions;
    }

    public void setDimensions(ProductDimensions dimensions) {
        this.dimensions = dimensions;
    }

    public static class ProductDimensions {
        @DocsDescription("길이 (cm)")
        private Double length;

        @DocsDescription("너비 (cm)")
        private Double width;

        @DocsDescription("높이 (cm)")
        private Double height;

        public ProductDimensions() {}

        public ProductDimensions(Double length, Double width, Double height) {
            this.length = length;
            this.width = width;
            this.height = height;
        }

        // Getters and Setters
        public Double getLength() {
            return length;
        }

        public void setLength(Double length) {
            this.length = length;
        }

        public Double getWidth() {
            return width;
        }

        public void setWidth(Double width) {
            this.width = width;
        }

        public Double getHeight() {
            return height;
        }

        public void setHeight(Double height) {
            this.height = height;
        }
    }
}