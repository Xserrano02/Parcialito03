package com.mitocode.service.impl;

import com.mitocode.model.Jugador;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IJugadorRepo;
import com.mitocode.service.IJugadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JugadorServiceImpl extends CRUDImpl<Jugador, Integer> implements IJugadorService {

    @Autowired
    private IJugadorRepo repo;

    @Override
    protected IGenericRepo<Jugador, Integer> getRepo() {
        return repo;
    }
}
