package org.example.personalizedstudyplanner.repositories_implementations;

import org.example.personalizedstudyplanner.database.DatabaseUtil;
import org.example.personalizedstudyplanner.models.Address;
import org.example.personalizedstudyplanner.repositories.AddressRepository;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AddressRepositoryImplementationTest {
    private static Connection connection;
    private AddressRepository addressRepository;

    @BeforeAll
    static void setupDatabase() {
        connection = DatabaseUtil.connect();
        assertNotNull(connection, "Failed to connect to PostgreSQL");

        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS address (" +
                    "address_id SERIAL PRIMARY KEY, " +
                    "country VARCHAR(255) NOT NULL, " +
                    "city VARCHAR(255) NOT NULL, " +
                    "street VARCHAR(255) NOT NULL, " +
                    "number INT NOT NULL, " +
                    "postal_code VARCHAR(20) NOT NULL)");
        } catch (SQLException e) {
            fail("Error creating table: " + e.getMessage());
        }
    }

    @BeforeEach
    void setup() {
        addressRepository = new AddressRepositoryImplementation(connection);
    }


    @Test
    void testGetOrCreateAddress_NewAddress() {
        Address address = new Address(0, "USA", "New York", "5th Avenue", 100, "10001"); // 0 as placeholder for new address
        int addressId = addressRepository.getOrCreateAddress(address);

        assertTrue(addressId > 0, "Address ID should be greater than 0 for a new entry");
    }

    @Test
    void testGetOrCreateAddress_ExistingAddress() {
        Address address = new Address(0, "USA", "New York", "5th Avenue", 100, "10001");
        int firstId = addressRepository.getOrCreateAddress(address);
        int secondId = addressRepository.getOrCreateAddress(address);

        assertEquals(firstId, secondId, "Address ID should be the same for the same address");
    }

}
