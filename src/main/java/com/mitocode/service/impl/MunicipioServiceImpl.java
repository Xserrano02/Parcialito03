package com.mitocode.service.impl;

import com.mitocode.model.Municipio;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IMunicipioRepo;
import com.mitocode.service.IMunicipioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MunicipioServiceImpl extends CRUDImpl<Municipio, Integer> implements IMunicipioService {

    @Autowired
    private IMunicipioRepo repo;

    @Override
    protected IGenericRepo<Municipio, Integer> getRepo() {
        return repo;
    }





}