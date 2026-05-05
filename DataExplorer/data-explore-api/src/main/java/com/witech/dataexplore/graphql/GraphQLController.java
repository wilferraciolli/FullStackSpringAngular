package com.witech.dataexplore.graphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.witech.dataexplore.graphql.addresses.AddressFilter;
import com.witech.dataexplore.graphql.addresses.AddressSort;
import com.witech.dataexplore.graphql.addresses.AddressView;
import com.witech.dataexplore.graphql.addresses.AddressViewService;
import com.witech.dataexplore.graphql.person.PersonFilter;
import com.witech.dataexplore.graphql.person.PersonSort;
import com.witech.dataexplore.graphql.person.PersonView;
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

    @QueryMapping // This tells Spring: "If someone asks for 'people' in their GraphQL query, run this."
    public Iterable<PersonView> people(
            @Argument PersonFilter filter,
            @Argument PersonSort sort) {
        return personService.findAll(filter, sort);
    }

    @QueryMapping // This tells Spring: "If someone asks for 'addresses' in their GraphQL query, run this."
    public Iterable<AddressView> addresses(
            @Argument AddressFilter filter,
            @Argument AddressSort sort) {
        return addressService.findAll(filter, sort);
    }
}
