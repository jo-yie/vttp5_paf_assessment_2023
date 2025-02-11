package vttp2023.batch3.assessment.paf.bookings.repositories;

import java.util.List;

import org.apache.tomcat.util.bcel.Const;
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
import org.springframework.stereotype.Repository;

@Repository
public class ListingsRepository {

	@Autowired
	private MongoTemplate template;

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
	// 		$project : { _id : 0, name : 1, price : 1, 'image_url' : 1 }
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
			).andExclude("_id");

		Aggregation pipeline = Aggregation.newAggregation(
			matchOperation, sortOperation, setOperation, projectionOperation);

		return template.aggregate(pipeline, Constants.C_LISTINGS, Document.class).getMappedResults();

	}

	//TODO: Task 4
	

	//TODO: Task 5


}
