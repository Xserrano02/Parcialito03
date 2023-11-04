package com.mitocode.controller;

import com.mitocode.dto.DepartamentoDTO;
import com.mitocode.exception.ModelNotFoundException;
import com.mitocode.model.Departamentos;
import com.mitocode.service.IDepartamentoService;
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
@RequestMapping("/departamentos")
public class DepartamentoController {

    @Autowired
    private IDepartamentoService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<DepartamentoDTO>> findAll() {
        List<DepartamentoDTO> list = service.findAll().stream()
                .map(p -> mapper.map(p, DepartamentoDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartamentoDTO> findById(@PathVariable("id") Integer id) {
        Departamentos obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        }
        DepartamentoDTO dtoResponse = mapper.map(obj, DepartamentoDTO.class);
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody DepartamentoDTO dto) {
        Departamentos p = service.save(mapper.map(dto, Departamentos.class));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(p.getDepartamentoId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<Departamentos> update(@Valid @RequestBody DepartamentoDTO dto) {
        Departamentos obj = service.findById(dto.getDepartamentoId());
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + dto.getDepartamentoId());
        }
        Departamentos updatedObj = service.update(mapper.map(dto, Departamentos.class));
        return new ResponseEntity<>(updatedObj, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        Departamentos obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        }
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<DepartamentoDTO> findByIdHateoas(@PathVariable("id") Integer id) {
        Departamentos obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        }
        DepartamentoDTO dtoResponse = mapper.map(obj, DepartamentoDTO.class);
        EntityModel<DepartamentoDTO> resource = EntityModel.of(dtoResponse);
        resource.add(linkTo(methodOn(this.getClass()).findById(id)).withRel("departamento-info"));
        resource.add(linkTo(methodOn(this.getClass()).findAll()).withRel("departamento-all"));

        return resource;
    }
}