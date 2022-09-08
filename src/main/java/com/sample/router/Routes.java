
package com.sample.router;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import com.sample.fruit.Fruit;
import com.sample.person.Person;

public class Routes extends RouteBuilder {

        @Override
        public void configure() throws Exception {

                restConfiguration().bindingMode(RestBindingMode.json);

                rest("/persons")
                                .get()
                                .to("direct:getPersons")

                                .post()
                                .type(Person.class)
                                .to("direct:addPerson");

                from("direct:getPersons")
                                .to("jpa://com.sample.person.Person?resultClass=com.sample.person.Person&namedQuery=Person.findAllPersons")
                                .log("Person List: ");

                from("direct:addPerson")
                                .to("jpa://com.sample.person.Person?usePersist=true");

                rest("/fruits")
                                .get()
                                .to("direct:getFruits")

                                .post()
                                .type(Fruit.class)
                                .to("direct:addFruit");

                from("direct:getFruits")
                                .to("jpa://com.sample.fruit.Fruit?resultClass=com.sample.fruit.Fruit&namedQuery=Fruit.findAllFruits")
                                .log("Fruit List: ");

                from("direct:addFruit")
                                .to("jpa://com.sample.fruit.Fruit?usePersist=true");

        }
}
