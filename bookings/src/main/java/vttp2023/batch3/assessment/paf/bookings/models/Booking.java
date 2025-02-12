package vttp2023.batch3.assessment.paf.bookings.models;

import java.time.LocalDate;
import java.util.Date;

public class Booking {
    
    private String bookingId; 
    private String accommodationId; 
    private String name; 
    private String email; 
    private LocalDate arrival; 
    private int stay;
    public Booking() {

    }

    public Booking(String bookingId, String accommodationId, String name, String email, LocalDate arrival, int stay) {
        this.bookingId = bookingId;
        this.accommodationId = accommodationId;
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
    public LocalDate getArrival() {
        return arrival;
    }
    public void setArrival(LocalDate arrival) {
        this.arrival = arrival;
    }
    public int getStay() {
        return stay;
    }
    public void setStay(int stay) {
        this.stay = stay;
    }

    public String getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(String accommodationId) {
        this.accommodationId = accommodationId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    } 
    
}