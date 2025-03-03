package org.example.personalizedstudyplanner.context.models;

import org.example.personalizedstudyplanner.models.Address;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddressTest {
    @Test
    void getAddressId() {
        Address address = new Address(1, "USA", "New York", "5th Avenue", 10, "10001");
        assertEquals(1, address.getAddressId());
    }

    @Test
    void setAddressId() {
        Address address = new Address(1, "USA", "New York", "5th Avenue", 10, "10001");
        address.setAddressId(2);
        assertEquals(2, address.getAddressId());
    }

    @Test
    void getCountry() {
        Address address = new Address(1, "USA", "New York", "5th Avenue", 10, "10001");
        assertEquals("USA", address.getCountry());
    }

    @Test
    void setCountry() {
        Address address = new Address(1, "USA", "New York", "5th Avenue", 10, "10001");
        address.setCountry("Canada");
        assertEquals("Canada", address.getCountry());
    }

    @Test
    void getCity() {
        Address address = new Address(1, "USA", "New York", "5th Avenue", 10, "10001");
        assertEquals("New York", address.getCity());
    }

    @Test
    void setCity() {
        Address address = new Address(1, "USA", "New York", "5th Avenue", 10, "10001");
        address.setCity("Los Angeles");
        assertEquals("Los Angeles", address.getCity());
    }

    @Test
    void getStreet() {
        Address address = new Address(1, "USA", "New York", "5th Avenue", 10, "10001");
        assertEquals("5th Avenue", address.getStreet());
    }

    @Test
    void setStreet() {
        Address address = new Address(1, "USA", "New York", "5th Avenue", 10, "10001");
        address.setStreet("Broadway");
        assertEquals("Broadway", address.getStreet());
    }

    @Test
    void getNumber() {
        Address address = new Address(1, "USA", "New York", "5th Avenue", 10, "10001");
        assertEquals(10, address.getNumber());
    }

    @Test
    void setNumber() {
        Address address = new Address(1, "USA", "New York", "5th Avenue", 10, "10001");
        address.setNumber(20);
        assertEquals(20, address.getNumber());
    }

    @Test
    void getPostalCode() {
        Address address = new Address(1, "USA", "New York", "5th Avenue", 10, "10001");
        assertEquals("10001", address.getPostalCode());
    }

    @Test
    void setPostalCode() {
        Address address = new Address(1, "USA", "New York", "5th Avenue", 10, "10001");
        address.setPostalCode("90001");
        assertEquals("90001", address.getPostalCode());
    }
}
