package com.mitocode.controller;


import com.mitocode.dto.EmpleadoDTO;
import com.mitocode.dto.RegistroSalarioRequestDTO;
import com.mitocode.dto.SalarioDTO;
import com.mitocode.exception.ModelNotFoundException;
import com.mitocode.model.Empleado;
import com.mitocode.model.Salario;
import com.mitocode.service.IEmpleadoService;
import com.mitocode.service.ISalarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/salarios")
public class SalarioController {

    @Autowired
    private ISalarioService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<SalarioDTO>> findAll() {
        List<SalarioDTO> list = service.findAll().stream()
                .map(p -> mapper.map(p, SalarioDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalarioDTO> findById(@PathVariable("id") Integer id) {
        Salario obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        }
        SalarioDTO dtoResponse = mapper.map(obj, SalarioDTO.class);
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }


//    @PostMapping
//    public ResponseEntity<Void> save(@Valid @RequestBody SalarioDTO dto) {
//        Salario p = service.save(mapper.map(dto, Salario.class));
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(p.getSalarioId())
//                .toUri();
//        return ResponseEntity.created(location).build();
//    }

    @PostMapping
    public ResponseEntity<SalarioDTO> save(@Valid @RequestBody SalarioDTO dto) {

        double salarioLiquido = calcularSalarioLiquido(dto.getSalarioBruto());
        dto.setSalarioLiquido(salarioLiquido);

        Salario p = service.save(mapper.map(dto, Salario.class));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(p.getSalarioId())
                .toUri();

        // Retornar el DTO actualizado con el salario líquido
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private double calcularSalarioLiquido(double salarioBruto) {
        // Aquí deberías implementar la lógica de cálculo de deducciones
        // Por ejemplo:
        double ISSS = 0.03;
        double AFP = 0.0725;
        double impuestoTotal = ISSS + AFP;
        double salario = salarioBruto;
        double salarioLiquido = salario - (impuestoTotal*salario);
//.


//        double impuestos = salarioBruto * 0.15; // 15% de impuestos
//        double seguridadSocial = salarioBruto * 0.05; // 5% para la seguridad social
//        double deduccionesTotales = impuestos + seguridadSocial;
        return salarioLiquido;
    }




    @PutMapping
    public ResponseEntity<Salario> update(@Valid @RequestBody SalarioDTO dto) {
        Salario obj = service.findById(dto.getSalarioId());
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + dto.getSalarioId());
        }
        Salario updatedObj = service.update(mapper.map(dto, Salario.class));
        return new ResponseEntity<>(updatedObj, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        Salario obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        }
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<SalarioDTO> findByIdHateoas(@PathVariable("id") Integer id){
        Salario obj = service.findById(id);
        if(obj == null){
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        }
        SalarioDTO dtoResponse = mapper.map(obj, SalarioDTO.class);
        EntityModel<SalarioDTO> resource = EntityModel.of(dtoResponse);
        resource.add(linkTo(methodOn(this.getClass()).findById(id)).withRel("salario-info"));
        resource.add(linkTo(methodOn(this.getClass()).findAll()).withRel("salario-all"));

        return resource;
    }
}