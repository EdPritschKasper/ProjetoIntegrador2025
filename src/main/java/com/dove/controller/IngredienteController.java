package com.dove.controller;

import com.dove.model.entities.IngredienteEntity;
import com.dove.model.service.IngredienteService;

import java.util.List;

public class IngredienteController {

    private IngredienteService service;

    public IngredienteController() {
        this.service = new IngredienteService();
    }

    public IngredienteEntity findById(Long id) {
        return service.findById(id);
    }

    public boolean insert(IngredienteEntity ingrediente) {
        return service.insert(ingrediente);
    }

    public boolean update(IngredienteEntity ingrediente) {
        return service.update(ingrediente);
    }

    public boolean delete(IngredienteEntity ingrediente) {
        return service.delete(ingrediente);
    }

    public List<IngredienteEntity> findAll() {
        return service.findAll();
    }

    public IngredienteEntity getMostSelectedIngrediente() {
        return service.getMostSelectedIngrediente();
    }
}
