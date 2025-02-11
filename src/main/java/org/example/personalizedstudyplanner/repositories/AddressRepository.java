package org.example.personalizedstudyplanner.repositories;

import org.example.personalizedstudyplanner.models.Address;

public interface AddressRepository {
    int getOrCreateAddress(Address address);
}
