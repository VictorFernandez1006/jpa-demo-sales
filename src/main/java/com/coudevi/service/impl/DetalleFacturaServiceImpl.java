package com.coudevi.service.impl;

import com.coudevi.model.DetalleFactura;
import com.coudevi.service.IDetalleFacturaService;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DetalleFacturaServiceImpl implements IDetalleFacturaService {
    private EntityManager em;

    public DetalleFacturaServiceImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void crear(DetalleFactura detalle) {
        em.getTransaction().begin();
        em.persist(detalle);
        em.getTransaction().commit();
    }

    @Override
    public List<DetalleFactura> listarPorFactura(Long nroFactura) {
        return em.createQuery("SELECT d FROM DetalleFactura d WHERE d.factura.nroFactura = :nro", DetalleFactura.class)
                .setParameter("nro", nroFactura)
                .getResultList();
    }
}
