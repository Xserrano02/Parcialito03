package com.mitocode.controller;
import com.mitocode.dto.MunicipioDTO;
import com.mitocode.exception.ModelNotFoundException;
import com.mitocode.model.Municipio;
import com.mitocode.service.IMunicipioService;
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
@RequestMapping("/municipios")
public class MunicipioController {

    @Autowired
    private IMunicipioService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<MunicipioDTO>> findAll() {
        List<MunicipioDTO> list = service.findAll().stream()
                .map(p -> mapper.map(p, MunicipioDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MunicipioDTO> findById(@PathVariable("id") Integer id) {
        Municipio obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        }
        MunicipioDTO dtoResponse = mapper.map(obj, MunicipioDTO.class);
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }



    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody MunicipioDTO dto) {
        Municipio p = service.save(mapper.map(dto, Municipio.class));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(p.getMunicipioId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<Municipio> update(@Valid @RequestBody MunicipioDTO dto) {
        Municipio obj = service.findById(dto.getMunicipioId());
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + dto.getMunicipioId());
        }
        Municipio updatedObj = service.update(mapper.map(dto, Municipio.class));
        return new ResponseEntity<>(updatedObj, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        Municipio obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        }
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<MunicipioDTO> findByIdHateoas(@PathVariable("id") Integer id){
        Municipio obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        }
        MunicipioDTO dtoResponse = mapper.map(obj, MunicipioDTO.class);
        EntityModel<MunicipioDTO> resource = EntityModel.of(dtoResponse);
        resource.add(linkTo(methodOn(this.getClass()).findById(id)).withRel("municipio-info"));
        resource.add(linkTo(methodOn(this.getClass()).findAll()).withRel("municipio-all"));

        return resource;
    }
}