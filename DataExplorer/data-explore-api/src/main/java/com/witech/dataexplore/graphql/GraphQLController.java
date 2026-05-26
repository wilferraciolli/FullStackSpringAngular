package com.witech.dataexplore.graphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.witech.dataexplore.graphql.addresses.AddressFilter;
import com.witech.dataexplore.graphql.addresses.AddressPage;
import com.witech.dataexplore.graphql.addresses.AddressSort;
import com.witech.dataexplore.graphql.addresses.AddressViewService;
import com.witech.dataexplore.graphql.person.PersonFilter;
import com.witech.dataexplore.graphql.person.PersonPage;
import com.witech.dataexplore.graphql.person.PersonSort;
import com.witech.dataexplore.graphql.person.PersonViewService;

/**
 * Navigate to http://localhost:8080/graphiql to try out the in memory palyground server
 */
@Controller
public class GraphQLController {
    private final PersonViewService personService;
    private final AddressViewService addressService;

    public GraphQLController(
            PersonViewService personService,
            AddressViewService addressService) {
        this.personService = personService;
        this.addressService = addressService;
    }

    @QueryMapping(name = "people")  // This tells Spring: "If someone asks for 'people' in their GraphQL query, run this."
    public PersonPage people(
            @Argument PersonFilter filter,
            @Argument PersonSort   sort,
            @Argument PageInput    page) {
        return personService.findAll(filter, sort, page);
    }

    @QueryMapping(name = "addresses") // This tells Spring: "If someone asks for 'addresses' in their GraphQL query, run this."
    public AddressPage addresses(
            @Argument AddressFilter filter,
            @Argument AddressSort   sort,
            @Argument PageInput     page) {
        return addressService.findAll(filter, sort, page);
    }
}
