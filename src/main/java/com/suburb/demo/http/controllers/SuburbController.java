package com.suburb.demo.http.controllers;

import com.suburb.demo.http.requests.SuburbRequest;
import com.suburb.demo.services.SuburbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("suburbs")
public class SuburbController {

    @Autowired
    private SuburbService suburbService;

    @PostMapping
    String saveSuburbs(@RequestBody List<SuburbRequest> suburbs) {
        return suburbService.saveSuburb(suburbs) ? "success" : "failed";
    }

    @GetMapping
    List<SuburbService.PostcodeWithSuburbs> getSuburbs(@RequestParam Integer from_postcode, @RequestParam Integer to_postcode) {
        return suburbService.getPostcodesWithSuburbs(from_postcode, to_postcode);
    }
}
