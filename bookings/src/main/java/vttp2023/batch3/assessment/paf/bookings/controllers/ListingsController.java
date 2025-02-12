package vttp2023.batch3.assessment.paf.bookings.controllers;

import java.util.List;
import java.util.UUID;

import javax.naming.Binding;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp2023.batch3.assessment.paf.bookings.models.Booking;
import vttp2023.batch3.assessment.paf.bookings.models.Listing;
import vttp2023.batch3.assessment.paf.bookings.models.ListingDetail;
import vttp2023.batch3.assessment.paf.bookings.models.SearchObject;
import vttp2023.batch3.assessment.paf.bookings.services.ListingsService;

@Controller
public class ListingsController {

	@Autowired
	private ListingsService listingsService; 

	// Task 2
	@GetMapping("")
	// @ResponseBody
	public String getLandingPage(Model model) {

		model.addAttribute("countries", listingsService.getAllCountries());
		
		SearchObject searchObject = new SearchObject(); 
		model.addAttribute("searchObject", searchObject);

		return "landing-page";

	}

	// Task 3
	@GetMapping("/search")
	// @ResponseBody
	public String getSearchResults(@Valid @ModelAttribute SearchObject searchObject, BindingResult bindingResult, Model model, HttpSession session) {

		model.addAttribute("countries", listingsService.getAllCountries());

		if (bindingResult.hasErrors()) {
			return "landing-page";

		} 

		model.addAttribute("country", searchObject.getCountry());

		List<Listing> listings = listingsService.searchForListings(searchObject);
		if (listings == null || listings.isEmpty()) {
			return "no-listings";

		}

		model.addAttribute("listings", listings); 

		session.setAttribute("searchObject", searchObject);

		return "listings";
		
	}

	// Task 4
	@GetMapping("/listing/{listing_id}")
	// @ResponseBody
	public String getListingDetail(@PathVariable String listing_id, Model model, HttpSession session) {

		ListingDetail listingDetail = listingsService.test(listing_id);

		model.addAttribute("listingDetail", listingDetail);

		session.setAttribute("listingDetail", listingDetail);

		session.setAttribute("listing_id", listing_id);

		SearchObject s = (SearchObject) session.getAttribute("searchObject"); 
		model.addAttribute("s", s);

		Booking booking = new Booking(); 
		booking.setAccommodationId(listing_id);
		model.addAttribute("booking", booking);

		return "listing";

	}

	//TODO: Task 5
	@PostMapping("/book")
	public String postBooking(@Valid @ModelAttribute Booking booking, BindingResult bindingResult, HttpSession session, Model model) {

		if (!listingsService.checkVacancy(booking)) {

			bindingResult.rejectValue("stay", "error.booking", "Duration of stay is longer than available vacancy");

			ListingDetail listingDetail = (ListingDetail) session.getAttribute("listingDetail");
			model.addAttribute("listingDetail", listingDetail);
	
			SearchObject s = (SearchObject) session.getAttribute("searchObject"); 
			model.addAttribute("s", s);

			return "listing";

		}

		booking.setBookingId(UUID.randomUUID().toString().substring(0, 8));

		try {
			System.out.println(booking.getArrival());
			listingsService.insertBooking(booking);
			model.addAttribute("bookingId", booking.getBookingId());

			return "booking-success";

		} catch (Exception e) {

			model.addAttribute("errorMessage", e.getMessage());
			return "booking-unsuccessful";
		}

	}

}
