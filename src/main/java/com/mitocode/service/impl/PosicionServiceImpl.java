package com.mitocode.service.impl;

import com.mitocode.model.Posicion;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IPosicionRepo;
import com.mitocode.service.IPosicionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PosicionServiceImpl extends CRUDImpl<Posicion, Integer> implements IPosicionService {

    @Autowired
    private IPosicionRepo repo;

    @Override
    protected IGenericRepo<Posicion, Integer> getRepo() {
        return repo;
    }
}
