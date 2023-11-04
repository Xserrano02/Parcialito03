package com.mitocode.service.impl;

import com.mitocode.model.Departamentos;

import com.mitocode.repo.IDepartamentosRepo;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.service.IDepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartamentoServiceImpl extends CRUDImpl<Departamentos, Integer> implements IDepartamentoService {

    @Autowired
    private IDepartamentosRepo repo;

    @Override
    protected IGenericRepo<Departamentos, Integer> getRepo() {
        return repo;
    }
}