package com.suburb.demo.services;

import com.suburb.demo.http.requests.SuburbRequest;
import com.suburb.demo.entities.Suburb;
import com.suburb.demo.repositories.SuburbRepo;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import static java.util.stream.Collectors.*;

@Service
public class SuburbService {

    @Autowired
    private SuburbRepo suburbRepo;

    static Suburb toSuburbEntity(SuburbRequest s) {
        return new Suburb(s.getSuburb(), s.getPostcode(), s.getSuburb() + "-" + s.getPostcode());
    }

    public static Stream<Suburb> toSuburbStream(Iterable<Suburb> suburbs) {
        return StreamSupport.stream(suburbs.spliterator(), true);
    }

    public Boolean saveSuburb(List<SuburbRequest> suburbs) {
        List<Suburb> suburbEntities = suburbs.stream()
            .map(SuburbService::toSuburbEntity)
            .toList();

        try {
            long saveSuburbs = toSuburbStream(suburbRepo.saveAll(suburbEntities)).count();
            return suburbs.size() == saveSuburbs;
        } catch(Exception e) {
            return false;
        }
    }

    public List<PostcodeWithSuburbs> getPostcodesWithSuburbs(Integer fromPostcode, Integer toPostcode) {
        Stream<Suburb> suburbs = toSuburbStream(suburbRepo.findByPostcodeRange(fromPostcode, toPostcode));

        return suburbs
            .sorted(Comparator.comparing(Suburb::getSuburb))
            .collect(groupingBy(Suburb::getPostcode))
            .entrySet()
                .stream()
                .map(postcodeSuburb -> {
                    int characterCount = postcodeSuburb.getValue().stream()
                            .reduce(0, (total, suburb) -> total + suburb.getSuburb().length(), Integer::sum);
                    return new PostcodeWithSuburbs(
                            postcodeSuburb.getKey(),
                            characterCount,
                            postcodeSuburb.getValue().stream().map(s -> s.getSuburb()).toList()
                    );
                })
                .toList();
    }

    @Data
    @AllArgsConstructor
    public static class PostcodeWithSuburbs {
        private Integer postcode;
        private Integer charCount;
        private List<String> suburbs;
    }
}
