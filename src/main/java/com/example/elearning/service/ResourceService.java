package com.example.elearning.service;

import com.example.elearning.exception.ResourceNotFoundException;
import com.example.elearning.model.Resource;
import com.example.elearning.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    public Resource save(Resource t) {
        return resourceRepository.save(t);
    }

    public List<Resource> findAll() {
        return resourceRepository.findAll();
    }

    public Resource findById(Long id) {
        return resourceRepository.getById(id);
    }

    public Resource update (Long id, Resource resource) throws ResourceNotFoundException {
        Resource old =
                resourceRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Resource not found on :: " + id));

        // set new values
        old.setResourceName(resource.getResourceName());
        old.setResourceLink(resource.getResourceLink());
        old.setImageUrl(resource.getImageUrl());
        old.setResourceCategory(resource.getResourceCategory());
        old.setVerified(resource.isVerified());
        old.setActive(resource.isActive());

        return save(old);
    }

    public void delete(Long id) throws ResourceNotFoundException {
        Resource resource =
                resourceRepository
                    .findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Resource not found on :: " + id));

        resourceRepository.delete(resource);
    }
}
