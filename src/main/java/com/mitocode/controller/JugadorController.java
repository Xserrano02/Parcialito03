package com.mitocode.controller;

import com.mitocode.dto.JugadorDTO;
import com.mitocode.exception.ModelNotFoundException;
import com.mitocode.model.Jugador;
import com.mitocode.service.IJugadorService;
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
@RequestMapping("/jugadores")
public class JugadorController {

    @Autowired
    private IJugadorService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<JugadorDTO>> findAll() {
        List<JugadorDTO> list = service.findAll().stream().map(p -> mapper.map(p, JugadorDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JugadorDTO> findById(@PathVariable("id") Integer id) {
        JugadorDTO dtoResponse;
        Jugador obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            dtoResponse = mapper.map(obj, JugadorDTO.class);
        }
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody JugadorDTO dto) {
        Jugador p = service.save(mapper.map(dto, Jugador.class));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(p.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<Jugador> update(@Valid @RequestBody JugadorDTO dto) {
        Jugador obj = service.findById(dto.getId());
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + dto.getId());
        }
        return new ResponseEntity<>(service.update(mapper.map(dto, Jugador.class)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        Jugador obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            service.delete(id);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<JugadorDTO> findByIdHateoas(@PathVariable("id") Integer id){
        JugadorDTO dtoResponse;
        Jugador obj = service.findById(id);

        if(obj == null){
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            dtoResponse = mapper.map(obj, JugadorDTO.class);
        }

        EntityModel<JugadorDTO> resource = EntityModel.of(dtoResponse);
        resource.add(linkTo(methodOn(this.getClass()).findById(id)).withRel("jugador-info"));
        resource.add(linkTo(methodOn(this.getClass()).findAll()).withRel("jugador-all"));

        return resource;
    }
}
