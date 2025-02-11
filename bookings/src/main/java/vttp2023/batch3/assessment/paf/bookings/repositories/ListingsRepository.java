package vttp2023.batch3.assessment.paf.bookings.repositories;

import java.util.List;

import org.apache.tomcat.util.bcel.Const;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ListingsRepository {

	@Autowired
	private MongoTemplate template;

	//TODO: Task 2
	// db.listings.aggregate([
	// 	{ $match : { 'address.country' : { $ne : null } } },
	// 	{ 
	// 		$group : {
	// 			_id : '$address.country'
	// 		}
	// 	}
	// ])
	public List<Document> getAllCountries() {

		Criteria criteria = Criteria.where(Constants.F_COUNTRY)
			.ne(null);	

		MatchOperation matchOperation = Aggregation.match(criteria);

		GroupOperation groupOperation = Aggregation.group(Constants.F_COUNTRY);

		Aggregation pipeline = Aggregation.newAggregation(matchOperation, groupOperation);

		List<Document> documents = template.aggregate(pipeline, Constants.C_LISTINGS, Document.class).getMappedResults();

		return documents;

	}
	
	//TODO: Task 3


	//TODO: Task 4
	

	//TODO: Task 5


}
