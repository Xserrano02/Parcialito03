package com.mitocode.service.impl;

import com.mitocode.model.Empleado;
import com.mitocode.repo.IEmpleadoRepo;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.service.IEmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoServiceImpl extends CRUDImpl<Empleado, Integer> implements IEmpleadoService {

    @Autowired
    private IEmpleadoRepo repo;

    @Override
    protected IGenericRepo<Empleado, Integer> getRepo() {
        return repo;
    }
}