package dev.anderson.planetapi.domain;

import static dev.anderson.planetapi.common.PlanetConstants.PLANET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import dev.anderson.planetapi.common.PlanetConstants;

//@SpringBootTest(classes = PlanetService.class)
@ExtendWith(MockitoExtension.class)
class PlanetServiceTest {

	@InjectMocks
	private PlanetService planetService;

	// @MockBean
	@Mock
	private PlanetRepository planetRepository;

	// operation_state_return
	@Test
	void createPlanet_WithValidData_ReturnsPlanet() {
		when(planetRepository.save(PlanetConstants.PLANET)).thenReturn(PlanetConstants.PLANET);
		
		// sut == system under test
		Planet sut = planetService.create(PlanetConstants.PLANET).getBody();
		
		assertThat(sut).isEqualTo(PlanetConstants.PLANET);
	}

	@Test
	void createPlanet_WithInvalidData_ThrowsException() {
		when(planetRepository.save(PlanetConstants.INVALID_PLANET)).thenThrow(RuntimeException.class);
		
		assertThatThrownBy(() -> planetService.create(PlanetConstants.INVALID_PLANET)).isInstanceOf(RuntimeException.class);
	}

	@Test
	void getPlanet_ByExistingId_ReturnsPlanet() {
		when(planetRepository.findById(1L)).thenReturn(Optional.of(PlanetConstants.PLANET));
		
		Optional<Planet> sut = planetService.get(1L);
		
		assertThat(sut).isNotEmpty();
		assertThat(sut.get()).isEqualTo(PlanetConstants.PLANET);
	}

	@Test
	  void getPlanet_ByUnexistingId_ReturnsEmpty() {
	    when(planetRepository.findById(1L)).thenReturn(Optional.empty());

	    Optional<Planet> sut = planetService.get(1L);

	    assertThat(sut).isEmpty();
	  }

	@Test
	  public void getPlanet_ByExistingName_ReturnsPlanet() {
	    when(planetRepository.findByName(PLANET.getName())).thenReturn(Optional.of(PLANET));

	    Optional<Planet> sut = planetService.getByName(PLANET.getName());

	    assertThat(sut).isNotEmpty();
	    assertThat(sut.get()).isEqualTo(PLANET);
	  }

	@Test
	public void getPlanet_ByUnexistingName_ReturnsEmpty() {
		final String name = "Unexisting name";
		when(planetRepository.findByName(name)).thenReturn(Optional.empty());

		Optional<Planet> sut = planetService.getByName(name);

		assertThat(sut).isEmpty();
	}

	@Test
	public void listPlanets_ReturnsAllPlanets() {
		List<Planet> planets = new ArrayList<>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				add(PLANET);
			}
		};
		Example<Planet> query = QueryBuilder.makeQuery(new Planet(PLANET.getClimate(), PLANET.getTerrain()));
		when(planetRepository.findAll(query)).thenReturn(planets);

		List<Planet> sut = planetService.list(PLANET.getTerrain(), PLANET.getClimate());

		assertThat(sut).isNotEmpty();
		assertThat(sut).hasSize(1);
		assertThat(sut.get(0)).isEqualTo(PLANET);
	}

	@Test
	  public void listPlanets_ReturnsNoPlanets() {
	    when(planetRepository.findAll(any())).thenReturn(Collections.emptyList());

	    List<Planet> sut = planetService.list(PLANET.getTerrain(), PLANET.getClimate());

	    assertThat(sut).isEmpty();
	  }

	@Test
	public void removePlanet_WithExistingId_doesNotThrowAnyException() {
		assertThatCode(() -> planetService.remove(1L)).doesNotThrowAnyException();
	}

	@Test
	public void removePlanet_WithUnexistingId_ThrowsException() {
		doThrow(new RuntimeException()).when(planetRepository).deleteById(99L);

		assertThatThrownBy(() -> planetService.remove(99L)).isInstanceOf(RuntimeException.class);
	}
}
