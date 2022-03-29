package com.suburb.demo.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "suburbs")
public class Suburb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String suburb;

    @NonNull
    private Integer postcode;

    // I added this field to handle issue with duplicate entry of suburb and postcode.
    // H2 currently don't support composite-key
    @NonNull
    private String suburb_postcode;

}
