package org.example.personalizedstudyplanner.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AddressTest {
    private Address address;
    private static final int TEST_ID = 1;
    private static final String TEST_COUNTRY = "Netherlands";
    private static final String TEST_CITY = "Amsterdam";
    private static final String TEST_STREET = "Test Street";
    private static final int TEST_NUMBER = 42;
    private static final String TEST_POSTAL_CODE = "1234 AB";

    @BeforeEach
    void setUp() {
        address = new Address(TEST_ID, TEST_COUNTRY, TEST_CITY, TEST_STREET, TEST_NUMBER, TEST_POSTAL_CODE);
    }

    @Test
    void testAddressConstructor() {
        assertNotNull(address);
        assertEquals(TEST_ID, address.getAddressId());
        assertEquals(TEST_COUNTRY, address.getCountry());
        assertEquals(TEST_CITY, address.getCity());
        assertEquals(TEST_STREET, address.getStreet());
        assertEquals(TEST_NUMBER, address.getNumber());
        assertEquals(TEST_POSTAL_CODE, address.getPostalCode());
    }

    @Test
    void testSetAddressId() {
        int newId = 2;
        address.setAddressId(newId);
        assertEquals(newId, address.getAddressId());
    }

    @Test
    void testSetCountry() {
        String newCountry = "Belgium";
        address.setCountry(newCountry);
        assertEquals(newCountry, address.getCountry());
    }

    @Test
    void testSetCity() {
        String newCity = "Brussels";
        address.setCity(newCity);
        assertEquals(newCity, address.getCity());
    }

    @Test
    void testSetStreet() {
        String newStreet = "New Street";
        address.setStreet(newStreet);
        assertEquals(newStreet, address.getStreet());
    }

    @Test
    void testSetNumber() {
        int newNumber = 100;
        address.setNumber(newNumber);
        assertEquals(newNumber, address.getNumber());
    }

    @Test
    void testSetPostalCode() {
        String newPostalCode = "5678 CD";
        address.setPostalCode(newPostalCode);
        assertEquals(newPostalCode, address.getPostalCode());
    }
}