package vttp2023.batch3.assessment.paf.bookings.services;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2023.batch3.assessment.paf.bookings.models.Listing;
import vttp2023.batch3.assessment.paf.bookings.models.SearchObject;
import vttp2023.batch3.assessment.paf.bookings.repositories.ListingsRepository;

@Service
public class ListingsService {

	@Autowired
	private ListingsRepository listingsRepository;
	
	// Task 2
	public List<String> getAllCountries() {

		List<Document> documents = listingsRepository.getAllCountries();
		List<String> countries = new ArrayList<>(); 

		for (Document d : documents) {

			String country = d.getString("_id");
			countries.add(country);

		}

		return countries;

	}

	// Task 3
	public List<Listing> searchForListings(SearchObject so) {

		List<Document> docListings = listingsRepository.searchForListings(
			so.getCountry(), 
			so.getNumPersons(), 
			so.getMinPrice(), 
			so.getMaxPrice()
		);

		List<Listing> listings = new ArrayList<>(); 
		for (Document d: docListings) { 
			listings.add(docToListing(d));
		}

		return listings; 

	}

	// Task 3 helper method 
	// Document --> Listing POJO
	public Listing docToListing(Document doc) {

		Listing l = new Listing(); 

		l.setName(doc.getString("name"));
		l.setPrice(doc.get("price", Number.class).floatValue());
		l.setImageUrl(doc.getString("image_url"));

		return l;

	}

	//TODO: Task 4
	

	//TODO: Task 5


}
