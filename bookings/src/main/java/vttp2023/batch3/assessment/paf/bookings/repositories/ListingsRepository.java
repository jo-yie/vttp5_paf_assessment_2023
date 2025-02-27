package vttp2023.batch3.assessment.paf.bookings.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SetOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2023.batch3.assessment.paf.bookings.models.Booking;

@Repository
public class ListingsRepository {

	@Autowired
	private MongoTemplate template;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// Task 2
	// db.listings.aggregate([
	// 	{ $match : { 'address.country' : { $ne : null, $ne : "" } } },
	// 	{ 
	// 		$group : {
	// 			_id : '$address.country'
	// 		}
	// 	}
	// ])
	public List<Document> getAllCountries() {

		Criteria criteria = Criteria.where(Constants.F_COUNTRY)
			.ne(null).ne("");	

		MatchOperation matchOperation = Aggregation.match(criteria);

		GroupOperation groupOperation = Aggregation.group(Constants.F_COUNTRY);

		Aggregation pipeline = Aggregation.newAggregation(matchOperation, groupOperation);

		List<Document> documents = template.aggregate(pipeline, Constants.C_LISTINGS, Document.class).getMappedResults();

		return documents;

	}
	
	// Task 3
	// db.listings.aggregate([
	// 	{
	// 		$match : {
	// 			'address.country' : { $regex: <COUNTRY_NAME>, $options : 'i' },
	// 			accommodates : <NUM_OF_PERSONS>,
	// 			price : { $gte : <MIN_PRICE>, $lte : <MAX_PRICE> }
	// 		}
	// 	}, 
	// 	{
	// 		$sort : { price : -1 }
	// 	}, 
	// 	{
	// 		$set : { image_url : '$images.picture_url' }
	// 	},
	// 	{
	// 		$project : { name : 1, price : 1, 'image_url' : 1 }
	// 	}
	// ])
	public List<Document> searchForListings(String country, int numPersons, int minPrice, int maxPrice) {

		Criteria criteria = new Criteria().andOperator(
			Criteria.where(Constants.F_COUNTRY).regex(country, "i"), 
			Criteria.where(Constants.F_ACCOMMODATES).is(numPersons),
			Criteria.where(Constants.F_PRICE).gte(minPrice).lte(maxPrice)
		);

		MatchOperation matchOperation = Aggregation.match(criteria); 

		SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, Constants.F_PRICE);

		SetOperation setOperation = SetOperation.set("image_url").toValue(Constants.F_IMAGES_PICTURE_URL);

		ProjectionOperation projectionOperation = Aggregation.project(
			Constants.F_NAME, 
			Constants.F_PRICE, 
			"image_url"
			);

		Aggregation pipeline = Aggregation.newAggregation(
			matchOperation, sortOperation, setOperation, projectionOperation);

		return template.aggregate(pipeline, Constants.C_LISTINGS, Document.class).getMappedResults();

	}

	// Task 4
	// db.listings.aggregate([
	// 	{
	// 		$match : { _id : "10108388" }
	// 	}, 
	// 	{
	// 		$set : {
	// 			address : [ '$address.street', '$address.suburb', '$address.country' ], 
	// 			image_url : '$images.picture_url'
	// 		}
	// 	},
	// 	{ $project: { description : 1, address : 1, image_url : 1, price : 1, amenities : 1} }
	// ])
	public Document getListingDetail(String id) {

		Criteria criteria = Criteria.where(Constants.F_ID).is(id); 

		MatchOperation matchOperation = Aggregation.match(criteria);

		SetOperation setOperation = SetOperation
			.set("address")
			.toValue(List.of(
					Constants.F_STREET, 
					Constants.F_SUBURB, 
					Constants.F_COUNTRY_NEW)); 
		
		SetOperation setOperation2 = SetOperation
			.set("image_url")
			.toValue(Constants.F_IMAGES_PICTURE_URL);

		ProjectionOperation projectionOperation = Aggregation.project(
			Constants.F_DESCRIPTION, 
			"address", 
			"image_url", 
			Constants.F_PRICE, 
			Constants.F_AMENITIES
		);

		Aggregation pipeline = Aggregation.newAggregation(
			matchOperation, 
			setOperation, 
			setOperation2,
			projectionOperation
		);

		Document document = template.aggregate(pipeline, Constants.C_LISTINGS, Document.class).getUniqueMappedResult();

		return document; 

	}

	// Task 5
	public int insertBooking(Booking b) {

		return jdbcTemplate.update(Queries.SQL_INSERT_BOOKING, 
			b.getBookingId(),
			b.getName(),
			b.getEmail(), 
			b.getAccommodationId(), 
			b.getArrival(), 
			b.getStay()); 

	}

	public int updateVacancy(Booking b) {

		return jdbcTemplate.update(Queries.SQL_UPDATE_VACANCY,
			b.getStay(),
			b.getAccommodationId());

	}

	// Task 5 helper method 
	public Integer getVacancy(String accommodationId) {

		SqlRowSet rs =jdbcTemplate.queryForRowSet(Queries.SQL_GET_VACANCY, accommodationId);

		Integer vacancy = null;

		if (rs.next()) {
			vacancy = rs.getInt("vacancy");
		} 

		return vacancy;

	}


}
