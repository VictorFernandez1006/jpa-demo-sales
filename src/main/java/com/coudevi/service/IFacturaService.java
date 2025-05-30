package com.coudevi.service;

import com.coudevi.model.Factura;

import java.util.List;

public interface IFacturaService {
    void crear(Factura factura);
    List<Factura> listar();
}
