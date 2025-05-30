package com.coudevi.service.impl;

import com.coudevi.model.Factura;
import com.coudevi.service.IFacturaService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class FacturaServiceImpl implements IFacturaService {
    private EntityManager em;

    public FacturaServiceImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void crear(Factura factura) {
        em.getTransaction().begin();
        em.persist(factura);
        em.getTransaction().commit();
    }

    @Override
    public List<Factura> listar() {
        return em.createQuery("SELECT f FROM Factura f", Factura.class).getResultList();
    }
}
