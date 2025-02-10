package org.example.personalizedstudyplanner.repositories_implementations;

import org.example.personalizedstudyplanner.models.Address;
import org.example.personalizedstudyplanner.repositories.AddressRepository;

import java.sql.*;

public class AddressRepositoryImplementation implements AddressRepository {
    private Connection connection;

    public AddressRepositoryImplementation(Connection connection) {
        this.connection = connection;
    }

    public int getOrCreateAddress(Address address) {
        String selectSql = "SELECT address_id FROM address WHERE country = ? AND city = ? AND street = ? AND number = ? AND postal_code = ?";
        String insertSql = "INSERT INTO address (country, city, street, number, postal_code) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement selectStmt = connection.prepareStatement(selectSql)) {
            selectStmt.setString(1, address.getCountry());
            selectStmt.setString(2, address.getCity());
            selectStmt.setString(3, address.getStreet());
            selectStmt.setInt(4, address.getNumber());
            selectStmt.setString(5, address.getPostalCode());
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("address_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (PreparedStatement insertStmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            insertStmt.setString(1, address.getCountry());
            insertStmt.setString(2, address.getCity());
            insertStmt.setString(3, address.getStreet());
            insertStmt.setInt(4, address.getNumber());
            insertStmt.setString(5, address.getPostalCode());
            insertStmt.executeUpdate();

            ResultSet generatedKeys = insertStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
