package com.coudevi.service;

import com.coudevi.model.Producto;

import java.util.List;

public interface IProductoService {
    void crear(Producto producto);
    List<Producto> listar();
}
