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
@RequestMapping(path = "/home")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping
    public List<Resource> getAllResources() {
        return resourceService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Resource> getResourceById(@PathVariable Long id) {
        Resource resource = null;
        try {
            resource = resourceService.findById(id);
            return ResponseEntity.ok(resource);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<String> saveResource(@RequestBody Resource resource) {
        resourceService.save(resource);
        return ResponseEntity.status(HttpStatus.CREATED).body("Added Successfully");
    }

    @PutMapping("{id}")
    public ResponseEntity<String> editResource(@PathVariable Long id, @RequestBody Resource resource) {
        try {
            resourceService.update(id, resource);
            return ResponseEntity.ok("Updated Successfully");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteResource(@PathVariable Long id) {
        try {
            resourceService.delete(id);
            return ResponseEntity.ok("Resource Deleted");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
