package hazelcast_examples.query;

import com.hazelcast.query.EntryObject;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;
import com.hazelcast.query.Predicates;

public class EmployeePredicate {
	public  static Predicate getWithNameAndAge( String name, int age ) {
		  Predicate namePredicate = Predicates.equal( "name", name );
		  Predicate agePredicate = Predicates.equal( "age", age );
		  Predicate predicate = Predicates.and( namePredicate, agePredicate );
		  return predicate;
		}
	
	public static Predicate getWithNameOrAge( String name, int age ) {
		  Predicate namePredicate = Predicates.equal( "name", name );
		  Predicate agePredicate = Predicates.equal( "age", age );
		  Predicate predicate = Predicates.or( namePredicate, agePredicate );
		  return predicate;
		}
	
	public static Predicate getNotWithName( String name ) {
		  Predicate namePredicate = Predicates.equal( "name", name );
		  Predicate predicate = Predicates.not( namePredicate );
		  return predicate;
		}
	
	public Predicate getWithNameAndAgeSimplified( String name, int age ) {
		  EntryObject e = new PredicateBuilder().getEntryObject();
		  Predicate agePredicate = e.get( "age" ).equal( age );
		  Predicate predicate = e.get( "name" ).equal( name ).and( agePredicate );
		  return predicate;
		}
}
