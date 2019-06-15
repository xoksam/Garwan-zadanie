package oksa.marek.eshop.controller;

public class ProductFilter {

    private String prefix;
    private Double minPrice;
    private Double maxPrice;

    public ProductFilter() {
    }

    public ProductFilter(String prefix, Double minPrice, Double maxPrice) {
        this.prefix = prefix;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    @Override
    public String toString() {
        return "ProductFilter{" +
                "prefix='" + prefix + '\'' +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                '}';
    }
}
