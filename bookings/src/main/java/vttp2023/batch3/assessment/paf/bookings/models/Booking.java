package vttp2023.batch3.assessment.paf.bookings.models;

import java.util.Date;

public class Booking {
    
    private String name; 
    private String email; 
    private Date arrival; 
    private int stay;
    public Booking() {
    }
    public Booking(String name, String email, Date arrival, int stay) {
        this.name = name;
        this.email = email;
        this.arrival = arrival;
        this.stay = stay;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Date getArrival() {
        return arrival;
    }
    public void setArrival(Date arrival) {
        this.arrival = arrival;
    }
    public int getStay() {
        return stay;
    }
    public void setStay(int stay) {
        this.stay = stay;
    } 
    
}