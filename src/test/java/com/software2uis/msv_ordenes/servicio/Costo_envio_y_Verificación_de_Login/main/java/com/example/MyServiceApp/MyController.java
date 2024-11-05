
package com.example.MyServiceApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entities")
public class MyController {
    @Autowired
    private MyEntityRepository repository;

    @GetMapping
    public List<MyEntity> getAllEntities() {
        return repository.findAll();
    }

    @PostMapping
    public MyEntity createEntity(@RequestBody MyEntity entity) {
        return repository.save(entity);
    }
}
