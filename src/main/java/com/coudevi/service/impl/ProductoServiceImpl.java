package com.coudevi.service.impl;

import com.coudevi.model.Producto;
import com.coudevi.service.IProductoService;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ProductoServiceImpl implements IProductoService {
    private EntityManager em;

    public ProductoServiceImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void crear(Producto producto) {
        em.getTransaction().begin();
        em.persist(producto);
        em.getTransaction().commit();
    }

    @Override
    public List<Producto> listar() {
        return em.createQuery("SELECT p FROM Producto p", Producto.class).getResultList();
    }
}
