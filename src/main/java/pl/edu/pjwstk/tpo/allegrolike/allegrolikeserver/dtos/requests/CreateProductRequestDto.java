package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests;

import java.math.BigDecimal;

public class CreateProductRequestDto {

    private String title;

    private String description;

    private BigDecimal price;

    private Integer stockQuantity;

    private Long sellerId;

    public CreateProductRequestDto() {
    }

    public CreateProductRequestDto(String title, String description, BigDecimal price, Integer stockQuantity, Long sellerId) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.sellerId = sellerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }
}
