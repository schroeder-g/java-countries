package com.countrydata.countries.repositories;

//defines methods for accessing and manipulating table

import com.countrydata.countries.models.Country;
import org.springframework.data.repository.CrudRepository;
                                                            //Long class and long primitive play well together
public interface CountryRepository extends CrudRepository<Country, Long>
{

}