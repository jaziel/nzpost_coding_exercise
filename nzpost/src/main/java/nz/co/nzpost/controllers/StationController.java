package nz.co.nzpost.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import nz.co.nzpost.domain.Destination;
import nz.co.nzpost.domain.ErrorDetails;
import nz.co.nzpost.domain.PathDTO;
import nz.co.nzpost.domain.Station;
import nz.co.nzpost.domain.StationDTO;
import nz.co.nzpost.repositories.StationRepository;

@ControllerAdvice
@RestController
@RequestMapping(path = "/stations", produces = "application/json")
public class StationController {
	
	private final static Logger log = LoggerFactory.getLogger(StationController.class);

	private StationRepository stationRepository;

	public StationController(StationRepository stationRepository) {
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

	protected List<PathDTO> convertPathResult(List<Destination> result) {
		List<PathDTO> path = result.stream()
				.map(d -> new PathDTO(d.getSource().getName(), d.getDestination().getName(), d.getValue()))
				.collect(Collectors.toList());
		return path;
	}

	protected List<StationDTO> convertStationsResult(List<Station> result) {
		List<StationDTO> path = result.stream().map(d -> new StationDTO(d.getName())).collect(Collectors.toList());
		return path;
	}

	@ExceptionHandler(Exception.class)
	private ResponseEntity<ErrorDetails> handleException(Exception ex, WebRequest request) {
		log.error("Exception on StationController:", ex);
		ErrorDetails error = new ErrorDetails(ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
