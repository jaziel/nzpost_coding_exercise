package nz.co.nzpost.repositories;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nz.co.nzpost.domain.Destination;
import nz.co.nzpost.domain.Station;

@Repository
public interface StationRepository extends CrudRepository<Station, Integer> {
	
	@Transactional(readOnly = true)
	List<Station> findAll();

	@Transactional(readOnly = true)
	Station findByName(String name);

	@Transactional(readOnly = true)
	@Query("MATCH (start:Station {name: {0}}), (end:Station {name: {1}})\r\n"
			+ "CALL apoc.algo.dijkstra(start, end, 'DISTANCE>', 'value') YIELD path, weight\r\n"
			+ " RETURN path, weight")
	List<Destination> findShortestPath(String source, String destination);
}
