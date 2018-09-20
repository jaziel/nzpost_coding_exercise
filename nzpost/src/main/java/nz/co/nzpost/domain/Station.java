package nz.co.nzpost.domain;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@NodeEntity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Station {
	@Id
	@GeneratedValue
	private Long id;

	@Property(name = "name")
	private String name;

	public Station() {
	};

	public Station(String name) {
		this.name = name;
	}

	@Relationship(type = "DISTANCE")
	public Set<Destination> destinations;

	public void goesTo(Destination destination) {
		if (destinations == null) {
			destinations = new HashSet<>();
		}
		destinations.add(destination);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}