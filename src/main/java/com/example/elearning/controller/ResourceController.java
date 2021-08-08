package com.example.elearning.controller;

import com.example.elearning.exception.ResourceNotFoundException;
import com.example.elearning.model.Resource;
import com.example.elearning.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping("/resources")
    public List<Resource> getAllResources() {
        return resourceService.findAll();
    }

    @GetMapping("/resources/{id}")
    public Resource getResourceById(@PathVariable Long id) {
        return resourceService.findById(id);
    }

    @PostMapping("/resources")
    public ResponseEntity<Resource> saveResource(@RequestBody Resource resource) {
        Resource r = resourceService.save(resource);
        return ResponseEntity.status(HttpStatus.CREATED).body(r);
    }

    @PutMapping("/resources/{id}")
    public ResponseEntity<Resource> editResource(@PathVariable Long id, @RequestBody Resource resource) {
        Resource r = null;
        try {
            r = resourceService.update(id, resource);
            return ResponseEntity.ok(r);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/resources/{id}")
    public ResponseEntity<Boolean> deleteResource(@PathVariable Long id) {
        try {
            resourceService.delete(id);
            return ResponseEntity.ok(Boolean.TRUE);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.ok(Boolean.FALSE);
        }
    }

}
