package com.suburb.demo.repositories;

import com.suburb.demo.entities.Suburb;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SuburbRepo extends CrudRepository<Suburb, Long> {

    @Query(value = "FROM Suburb WHERE postcode >= :fromPostcode AND postcode <= :toPostcode")
    public Iterable<Suburb> findByPostcodeRange(
            @Param("fromPostcode") Integer fromPostcode,
            @Param("toPostcode") Integer toPostcode
    );
}
