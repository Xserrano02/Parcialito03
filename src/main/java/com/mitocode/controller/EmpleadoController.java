package com.mitocode.controller;

import com.mitocode.dto.EmpleadoDTO;
import com.mitocode.exception.ModelNotFoundException;
import com.mitocode.model.Empleado;
import com.mitocode.service.IEmpleadoService;
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
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private IEmpleadoService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<EmpleadoDTO>> findAll() {
        List<EmpleadoDTO> list = service.findAll().stream()
                .map(p -> mapper.map(p, EmpleadoDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> findById(@PathVariable("id") Integer id) {
        Empleado obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        }
        EmpleadoDTO dtoResponse = mapper.map(obj, EmpleadoDTO.class);
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody EmpleadoDTO dto) {
        Empleado p = service.save(mapper.map(dto, Empleado.class));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(p.getEmpleadoId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<Empleado> update(@Valid @RequestBody EmpleadoDTO dto) {
        Empleado obj = service.findById(dto.getEmpleadoId());
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + dto.getEmpleadoId());
        }
        Empleado updatedObj = service.update(mapper.map(dto, Empleado.class));
        return new ResponseEntity<>(updatedObj, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        Empleado obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        }
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<EmpleadoDTO> findByIdHateoas(@PathVariable("id") Integer id){
        Empleado obj = service.findById(id);
        if(obj == null){
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        }
        EmpleadoDTO dtoResponse = mapper.map(obj, EmpleadoDTO.class);
        EntityModel<EmpleadoDTO> resource = EntityModel.of(dtoResponse);
        resource.add(linkTo(methodOn(this.getClass()).findById(id)).withRel("empleado-info"));
        resource.add(linkTo(methodOn(this.getClass()).findAll()).withRel("empleado-all"));

        return resource;
    }
}
