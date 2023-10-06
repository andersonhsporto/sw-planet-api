package dev.anderson.planetapi.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PlanetService {

	private final PlanetRepository planetRepository;

	public ResponseEntity<Planet> create(Planet planet) {
		return ResponseEntity.status(HttpStatus.CREATED).body(planetRepository.save(planet));
	}

	public Optional<Planet> get(Long id) {
		return planetRepository.findById(1L);
	}

	public Optional<Planet> getByName(String name) {
		return planetRepository.findByName(name);
	}

	public List<Planet> list(String terrain, String climate) {
		Example<Planet> query = QueryBuilder.makeQuery(new Planet(climate, terrain));

		return planetRepository.findAll(query);
	}

	public void remove(Long id) {
		planetRepository.deleteById(id);
	}

}
