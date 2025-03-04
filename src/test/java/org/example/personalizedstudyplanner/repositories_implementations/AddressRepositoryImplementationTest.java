package org.example.personalizedstudyplanner.repositories_implementations;

import org.example.personalizedstudyplanner.models.Address;
import org.example.personalizedstudyplanner.repositories_implementations.AddressRepositoryImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressRepositoryImplementationTest {

    private AddressRepositoryImplementation addressRepository;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockSelectStmt;

    @Mock
    private PreparedStatement mockInsertStmt;

    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        addressRepository = new AddressRepositoryImplementation(mockConnection);
    }

    @Test
    void testGetOrCreateAddress_ExistingAddress() throws Exception {
        Address address = new Address(1, "Finland", "Helsinki", "Main Street", 10, "00100");

        Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockSelectStmt);
        Mockito.when(mockSelectStmt.executeQuery()).thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next()).thenReturn(true);
        Mockito.when(mockResultSet.getInt("address_id")).thenReturn(1);

        int addressId = addressRepository.getOrCreateAddress(address);

        assertEquals(1, addressId);
        Mockito.verify(mockSelectStmt, Mockito.times(1)).executeQuery();
    }

    @Test
    void testGetOrCreateAddress_NewAddress() throws Exception {
        Address address = new Address(2, "Sweden", "Stockholm", "New Street", 20, "00200");

        Mockito.when(mockConnection.prepareStatement(Mockito.contains("SELECT address_id FROM address"))).thenReturn(mockSelectStmt);
        Mockito.when(mockSelectStmt.executeQuery()).thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next()).thenReturn(false);

        Mockito.when(mockConnection.prepareStatement(Mockito.contains("INSERT INTO address"), Mockito.eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(mockInsertStmt);

        Mockito.when(mockInsertStmt.executeUpdate()).thenReturn(1);

        ResultSet mockGeneratedKeys = Mockito.mock(ResultSet.class);
        Mockito.when(mockInsertStmt.getGeneratedKeys()).thenReturn(mockGeneratedKeys);
        Mockito.when(mockGeneratedKeys.next()).thenReturn(true);
        Mockito.when(mockGeneratedKeys.getInt(1)).thenReturn(2);

        int addressId = addressRepository.getOrCreateAddress(address);

        assertEquals(2, addressId);

        Mockito.verify(mockInsertStmt, Mockito.times(1)).executeUpdate();
        Mockito.verify(mockInsertStmt, Mockito.times(1)).getGeneratedKeys();
        Mockito.verify(mockGeneratedKeys, Mockito.times(1)).next();
        Mockito.verify(mockGeneratedKeys, Mockito.times(1)).getInt(1);
    }
}
