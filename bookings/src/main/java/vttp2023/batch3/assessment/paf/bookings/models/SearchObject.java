package vttp2023.batch3.assessment.paf.bookings.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class SearchObject {

    @NotBlank(message = "Country must not be blank")
    private String country; 

    @Min(value = 1, message = "Number of persons must be between 1 and 10")
    @Max(value = 10, message = "Number of persons must be between 1 and 10")
    private int numPersons; 

    @Min(value = 1, message = "Minimum price must be between 1 and 10000")
    @Max(value = 10000, message = "Minimum price must be between 1 and 10000")
    private int minPrice; 

    // TODO max price must be greater than min price
    @Min(value = 1, message = "Maximum price must be between 1 and 10000")
    @Max(value = 10000, message = "Maximum price must be between 1 and 10000")
    private int maxPrice;
    public SearchObject() {
    }
    public SearchObject(String country, int numPersons, int minPrice, int maxPrice) {
        this.country = country;
        this.numPersons = numPersons;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public int getNumPersons() {
        return numPersons;
    }
    public void setNumPersons(int numPersons) {
        this.numPersons = numPersons;
    }
    public int getMinPrice() {
        return minPrice;
    }
    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }
    public int getMaxPrice() {
        return maxPrice;
    }
    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }
    
}
