package com.coudevi.service;

import com.coudevi.model.DetalleFactura;

import java.util.List;

public interface IDetalleFacturaService {
    void crear(DetalleFactura detalle);
    List<DetalleFactura> listarPorFactura(Long nroFactura);
}
