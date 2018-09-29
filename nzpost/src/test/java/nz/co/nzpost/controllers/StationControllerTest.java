package nz.co.nzpost.controllers;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import nz.co.nzpost.domain.Station;
import nz.co.nzpost.domain.StationDTO;
import nz.co.nzpost.repositories.StationRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StationControllerTest {

	MockMvc mockMvc;

	@Autowired
	private StationController controller;

	@MockBean
	private StationRepository repository;

	private List<Station> stations;

	private List<StationDTO> dtos;

	@Before
	public void setup() {

		this.mockMvc = standaloneSetup(this.controller).build();

		stations = new ArrayList<Station>();
		Station station = new Station("A");
		stations.add(station);
		station = new Station("B");
		stations.add(station);
		station = new Station("C");
		stations.add(station);

		dtos = controller.convertStationsResult(stations);

	}

	@Test
	public void testFindAllStations() {

		when(repository.findAll()).thenReturn(stations);

		List<StationDTO> result = controller.getAllStations();
		assertEquals(dtos.size(), result.size());

	}

	@Test
	public void testFindAllStationsRestAPI() throws Exception {
		
//		mockMvc.perform(get("/stations/").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
//				.andExpect(jsonPath("$[0].name", is("A"))).andExpect(jsonPath("$[1].name", is("B")));
	}

}
