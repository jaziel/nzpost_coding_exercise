package nz.co.nzpost.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nz.co.nzpost.domain.Station;
import nz.co.nzpost.domain.StationDTO;
import nz.co.nzpost.domain.Destination;
import nz.co.nzpost.domain.PathDTO;
import nz.co.nzpost.repositories.StationRepository;

@RestController
@RequestMapping(path = "/stations", produces = "application/json")
@CrossOrigin
public class StationControler {
	
	private StationRepository stationRepository;

	public StationControler(StationRepository stationRepository) {
		this.stationRepository = stationRepository;
	}

	@GetMapping("/path")
	public List<PathDTO> getShortestPath(@RequestParam(value = "source", required = true) String source,
			@RequestParam(value = "destination", required = true) String destination) {
		List<Destination> result = stationRepository.findShortestPath(source, destination);
		return this.convertPathResult(result);
	}

	@GetMapping("/")
	public List<StationDTO> getAllStations() {
		List<Station> result = stationRepository.findAll();
		return this.convertStationsResult(result);
	}

	private List<PathDTO> convertPathResult(List<Destination> result) {
		List<PathDTO> path = result.stream()
				.map(d -> new PathDTO(d.getSource().getName(), d.getDestination().getName(), d.getValue()))
				.collect(Collectors.toList());
		return path;
	}
	
	private List<StationDTO> convertStationsResult(List<Station> result) {
		List<StationDTO> path = result.stream()
				.map(d -> new StationDTO(d.getName()))
				.collect(Collectors.toList());
		return path;
	}

}
