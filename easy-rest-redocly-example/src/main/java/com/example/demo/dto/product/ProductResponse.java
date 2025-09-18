package com.example.demo.dto.product;

import com.example.demo.dto.common.ProductCategory;
import com.example.easyrestredoclylib.core.DocsDescription;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ProductResponse {

    @DocsDescription("상품 고유 ID")
    private Long id;

    @DocsDescription("상품명")
    private String name;

    @DocsDescription("상품 설명")
    private String description;

    @DocsDescription("상품 가격")
    private BigDecimal price;

    @DocsDescription("상품 카테고리")
    private ProductCategory category;

    @DocsDescription("재고 수량")
    private Integer stockQuantity;

    @DocsDescription("상품 태그 목록")
    private List<String> tags;

    @DocsDescription("상품 브랜드")
    private String brand;

    @DocsDescription("상품 무게 (kg)")
    private Double weight;

    @DocsDescription("상품 치수")
    private ProductDimensions dimensions;

    @DocsDescription("상품 등록일시")
    private LocalDateTime createdAt;

    @DocsDescription("마지막 수정일시")
    private LocalDateTime updatedAt;

    @DocsDescription("상품 이미지 URL 목록")
    private List<String> imageUrls;

    @DocsDescription("평균 평점")
    private Double averageRating;

    @DocsDescription("리뷰 수")
    private Integer reviewCount;

    public ProductResponse() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
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