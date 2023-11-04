package com.mitocode.service.impl;

import com.mitocode.model.Camisa;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.ICamisaRepo;
import com.mitocode.service.ICamisaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CamisaServiceImpl extends CRUDImpl<Camisa, Integer> implements ICamisaService {

    @Autowired
    private ICamisaRepo repo;

    @Override
    protected IGenericRepo<Camisa, Integer> getRepo() {
        return repo;
    }
}
