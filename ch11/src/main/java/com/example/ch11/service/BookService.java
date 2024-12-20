package com.example.ch11.service;

import com.example.ch11.entity.Employee;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BookService {

    private Map<String, Employee> records =
            Map.of("emma", new Employee("Emma Thompson",
                            List.of("Karamazov Brothers"),
                            List.of("accountant", "reader")),
                    "natalie",
                    new Employee("Natalie Parker",
                            List.of("Beautiful Paris"),
                            List.of("researcher"))
            );


    //return object is records
    @PostAuthorize("returnObject.roles.contains('reader')")
    public Employee getBookDetails(String name) {
        return records.get(name);
    }
}
