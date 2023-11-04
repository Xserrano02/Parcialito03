package com.mitocode.service.impl;

import com.mitocode.model.Inactividad;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IInactividadRepo;
import com.mitocode.service.IInactividadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InactividadServiceImpl extends CRUDImpl<Inactividad, Integer> implements IInactividadService {

    @Autowired
    private IInactividadRepo repo;

    @Override
    protected IGenericRepo<Inactividad, Integer> getRepo() {
        return repo;
    }
}