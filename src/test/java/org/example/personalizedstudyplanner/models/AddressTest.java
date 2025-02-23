package org.example.personalizedstudyplanner.models;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @org.junit.jupiter.api.Test
    void getAddressId() {
        Address address = new Address(1, "USA", "New York", "5th Avenue", 10, "10001");
        assertEquals(1, address.getAddressId());
    }

    @org.junit.jupiter.api.Test
    void setAddressId() {
        Address address = new Address(1, "USA", "New York", "5th Avenue", 10, "10001");
        address.setAddressId(123);
        assertEquals(123, address.getAddressId());
    }

    @org.junit.jupiter.api.Test
    void getCountry() {
        Address address = new Address(1, "USA", "New York", "5th Avenue", 10, "10001");
        assertEquals("USA", address.getCountry());
    }

    @org.junit.jupiter.api.Test
    void setCountry() {
        Address address = new Address(1, "USA", "New York", "5th Avenue", 10, "10001");
        address.setCountry("Canada");
        assertEquals("Canada", address.getCountry());
    }

    @org.junit.jupiter.api.Test
    void getCity() {
        Address address = new Address(1, "USA", "New York", "5th Avenue", 10, "10001");
        assertEquals("New York", address.getCity());
    }

    @org.junit.jupiter.api.Test
    void setCity() {
        Address address = new Address(1, "USA", "New York", "5th Avenue", 10, "10001");
        address.setCity("Los Angeles");
        assertEquals("Los Angeles", address.getCity());
    }

    @org.junit.jupiter.api.Test
    void getStreet() {
        Address address = new Address(1, "USA", "New York", "5th Avenue", 10, "10001");
        assertEquals("5th Avenue", address.getStreet());
    }

    @org.junit.jupiter.api.Test
    void setStreet() {
        Address address = new Address(1, "USA", "New York", "5th Avenue", 10, "10001");
        address.setStreet("Broadway");
        assertEquals("Broadway", address.getStreet());
    }

    @org.junit.jupiter.api.Test
    void getNumber() {
        Address address = new Address(1, "USA", "New York", "5th Avenue", 10, "10001");
        assertEquals(10, address.getNumber());
    }

    @org.junit.jupiter.api.Test
    void setNumber() {
        Address address = new Address(1, "USA", "New York", "5th Avenue", 10, "10001");
        address.setNumber(20);
        assertEquals(20, address.getNumber());
    }

    @org.junit.jupiter.api.Test
    void getPostalCode() {
        Address address = new Address(1, "USA", "New York", "5th Avenue", 10, "10001");
        assertEquals("10001", address.getPostalCode());
    }

    @org.junit.jupiter.api.Test
    void setPostalCode() {
        Address address = new Address(1, "USA", "New York", "5th Avenue", 10, "10001");
        address.setPostalCode("90001");
        assertEquals("90001", address.getPostalCode());
    }

}