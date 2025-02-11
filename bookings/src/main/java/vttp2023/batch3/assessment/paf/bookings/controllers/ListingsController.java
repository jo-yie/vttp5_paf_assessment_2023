package vttp2023.batch3.assessment.paf.bookings.controllers;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.Valid;
import vttp2023.batch3.assessment.paf.bookings.models.Listing;
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
	public String getSearchResults(@Valid @ModelAttribute SearchObject searchObject, BindingResult bindingResult, Model model) {

		model.addAttribute("countries", listingsService.getAllCountries());

		if (bindingResult.hasErrors() ) {
			return "landing-page";

		} 

		model.addAttribute("country", searchObject.getCountry());

		List<Listing> listings = listingsService.searchForListings(searchObject);
		if (listings == null || listings.isEmpty()) {
			return "no-listings";

		}

		model.addAttribute("listings", listings); 

		return "listings";
		
	}

	//TODO: Task 4
	

	//TODO: Task 5


}
