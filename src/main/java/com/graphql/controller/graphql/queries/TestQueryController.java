package com.graphql.controller.graphql.queries;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class TestQueryController {

    @QueryMapping
    public String getString() {
        return "Hello";
    }
}
