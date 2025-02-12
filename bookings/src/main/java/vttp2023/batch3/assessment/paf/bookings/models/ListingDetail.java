package vttp2023.batch3.assessment.paf.bookings.models;

import java.util.List;

public class ListingDetail {

    private String accommodationId; 
    private String description; 
    private List<String> address; 
    private String imageUrl;
    private float price; 
    private List<String> amenities;
    public ListingDetail() {
    }
    public ListingDetail(String accommodationId, String description, List<String> address, String imageUrl, float price,
            List<String> amenities) {
        this.accommodationId = accommodationId;
        this.description = description;
        this.address = address;
        this.imageUrl = imageUrl;
        this.price = price;
        this.amenities = amenities;
    }
    public String getAccommodationId() {
        return accommodationId;
    }
    public void setAccommodationId(String accommodationId) {
        this.accommodationId = accommodationId;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<String> getAddress() {
        return address;
    }
    public void setAddress(List<String> address) {
        this.address = address;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public List<String> getAmenities() {
        return amenities;
    }
    public void setAmenities(List<String> amenities) {
        this.amenities = amenities;
    } 
    
}
