package com.cn.hotel.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.cn.hotel.model.Hotel;

/**
 * Repository Testing
 * @implNote DataJpaTest, TestContainers, AutoConfigureTestDatabase are required for repository & TestDb using Docker
 */
@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class HotelRepositoryTest {

	@Autowired
	private HotelRepository hotelRepository;
	/**
	 * 1. Creating MY_SQL_CONTAINER
	 * 2. Starting CONTAINER
	 * 3. Registering properties
	 * Add Dependency for TestContainer[Mysql, junit]
	 * Open Docker App before testing
	 */
	private static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>("mysql:8.0.33")
			.withDatabaseName("hotel-test-db")
			.withUsername("testUser")
			.withPassword("password");

	static {
		MY_SQL_CONTAINER.start();
	}

	@DynamicPropertySource
	static void registerDatabaseProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
		registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
		registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
	}

	@Test
	public void shouldTestSaveHotelRepo() {
		Hotel hotel = new Hotel();
		hotel.setId(1L);
		hotel.setName("Gorge Hotel");
		hotel.setRating(3L);

		Hotel resultHotel = hotelRepository.save(hotel);
		//testing each field
		assertThat(resultHotel)
				.usingRecursiveComparison()
				.ignoringFields("id")
				.isEqualTo(hotel);
	}
}
