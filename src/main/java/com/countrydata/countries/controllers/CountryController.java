package com.countrydata.countries.controllers;

import com.countrydata.countries.models.Country;
import com.countrydata.countries.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountryController
{
    @Autowired
    private CountryRepository crepos;

    private List<Country> filterCountries(List<Country> testList, CheckCountry tester)
    {
        List<Country> filteredList = new ArrayList<>();

        for (Country c : testList)
        {
            if (tester.test(c))
            {
                filteredList.add(c);
            }
        }

        return filteredList;
    }

    // http://localhost:2020/names/all
    @GetMapping( value="/names/all", produces = {"application/json"})
    public ResponseEntity<?> ListAllCountries()
    {
        List<Country> countryList = new ArrayList<>();

        crepos.findAll().iterator().forEachRemaining(countryList :: add);

        countryList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));

        for (Country c : countryList)
        {
            System.out.println(c);
        }
        return new ResponseEntity<>(countryList, HttpStatus.OK);
    }
    // http://localhost:2020/names/start/u
    @GetMapping( value="/names/start/{letter}", produces = {"application/json"})
            public ResponseEntity<?> ListCountriesByFirstLetter(@PathVariable char letter)
    {
        List<Country> myList = new ArrayList<>();

        crepos.findAll().iterator().forEachRemaining(myList :: add);

        List<Country> rtnList = filterCountries(myList, c ->
                Character.toLowerCase(c.getName().charAt(0)) == Character.toLowerCase(letter));

        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }
    // http://localhost:2019/population/total
    @GetMapping(value = "/population/total")
        public ResponseEntity<?> GetPopulationTotal()
        {
            List<Country> countryList = new ArrayList<>();

            crepos.findAll().iterator().forEachRemaining(countryList :: add);

            long popTotal = 0;
            for (Country c : countryList)
            {
                popTotal = popTotal + c.getPopulation();
            }
            System.out.println("The total population is: " + popTotal);
            return new ResponseEntity<>(HttpStatus.OK);
        }
}
