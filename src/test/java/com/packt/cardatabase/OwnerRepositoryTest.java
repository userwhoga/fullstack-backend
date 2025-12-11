package com.packt.cardatabase;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.packt.cardatabase.domain.Owner;
import com.packt.cardatabase.domain.OwnerRepository;

@DataJpaTest
class OwnerRepositoryTest {

    @Autowired
    private OwnerRepository repository;

    @Test
    @DisplayName("Test adding new owner to database")
    void saveOwner() {
        repository.save(new Owner("Lucy", "Smith"));
        assertThat(
            repository.findByFirstname("Lucy").isPresent()
        ).isTrue();
    }

    @Test
    @DisplayName("Test deleting all owners from database")
    void deleteOwners() {
        repository.save(new Owner("Lisa", "Morrison"));
        repository.deleteAll();
        assertThat(repository.count()).isEqualTo(0);
    }
}


