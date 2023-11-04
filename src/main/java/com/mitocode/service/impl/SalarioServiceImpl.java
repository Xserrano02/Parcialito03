package com.mitocode.service.impl;

import com.mitocode.model.Salario;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.ISalarioRepo;
import com.mitocode.service.ISalarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalarioServiceImpl extends CRUDImpl<Salario, Integer> implements ISalarioService {

    @Autowired
    private ISalarioRepo repo;

    @Override
    protected IGenericRepo<Salario, Integer> getRepo() {
        return repo;
    }
}