package vttp2023.batch3.assessment.paf.bookings.services;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2023.batch3.assessment.paf.bookings.repositories.ListingsRepository;

@Service
public class ListingsService {

	@Autowired
	private ListingsRepository listingsRepository;
	
	//TODO: Task 2
	public List<String> getAllCountries() {

		List<Document> documents = listingsRepository.getAllCountries();
		List<String> countries = new ArrayList<>(); 

		for (Document d : documents) {

			String country = d.getString("_id");
			countries.add(country);

		}

		return countries;

	}

	
	//TODO: Task 3


	//TODO: Task 4
	

	//TODO: Task 5


}
