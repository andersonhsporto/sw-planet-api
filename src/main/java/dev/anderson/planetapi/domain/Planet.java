package dev.anderson.planetapi.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "planets")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Planet {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	protected Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "climate")
	private String climate;

	@Column(name = "terrain")
	private String terrain;

	@CreatedDate
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	public Planet(String name, String climate, String terrain) {
		this.name = name;
		this.climate = climate;
		this.terrain = terrain;
	}

	public Planet(String climate, String terrain) {
		this.climate = climate;
		this.terrain = terrain;
	}
	
	public Planet(Long id, String name, String climate, String terrain) {
		this.id = id;
		this.name = name;
		this.climate = climate;
		this.terrain = terrain;
	}
	
	public void nullId() {
		this.id = null;
	}

	@Override
	public int hashCode() {
		return Objects.hash(climate, name, terrain, updatedAt);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		Planet rhs = (Planet) obj;
		EqualsBuilder equalsBuilder = new EqualsBuilder();

		equalsBuilder.append(this.name, rhs.getName());
		equalsBuilder.append(this.climate, rhs.getClimate());
		equalsBuilder.append(this.terrain, rhs.getTerrain());
		return equalsBuilder.isEquals();
	}

}
