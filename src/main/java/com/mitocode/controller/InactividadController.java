package com.mitocode.controller;


import com.mitocode.dto.InactividadDTO;
import com.mitocode.exception.ModelNotFoundException;
import com.mitocode.model.Inactividad;
import com.mitocode.service.IInactividadService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/inactividades")
public class InactividadController {

    @Autowired
    private IInactividadService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<InactividadDTO>> findAll() {
        List<InactividadDTO> list = service.findAll().stream()
                .map(p -> mapper.map(p, InactividadDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InactividadDTO> findById(@PathVariable("id") Integer id) {
        Inactividad obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        }
        InactividadDTO dtoResponse = mapper.map(obj, InactividadDTO.class);
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody InactividadDTO dto) {
        Inactividad p = service.save(mapper.map(dto, Inactividad.class));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(p.getInactividadId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<Inactividad> update(@Valid @RequestBody InactividadDTO dto) {
        Inactividad obj = service.findById(dto.getInactividadId());
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + dto.getInactividadId());
        }
        Inactividad updatedObj = service.update(mapper.map(dto, Inactividad.class));
        return new ResponseEntity<>(updatedObj, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        Inactividad obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        }
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<InactividadDTO> findByIdHateoas(@PathVariable("id") Integer id){
        Inactividad obj = service.findById(id);
        if(obj == null){
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        }
        InactividadDTO dtoResponse = mapper.map(obj, InactividadDTO.class);
        EntityModel<InactividadDTO> resource = EntityModel.of(dtoResponse);
        resource.add(linkTo(methodOn(this.getClass()).findById(id)).withRel("inactividad-info"));
        resource.add(linkTo(methodOn(this.getClass()).findAll()).withRel("inactividad-all"));

        return resource;
    }
}