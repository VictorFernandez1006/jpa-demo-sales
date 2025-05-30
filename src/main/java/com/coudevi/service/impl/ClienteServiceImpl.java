package com.coudevi.service.impl;

import com.coudevi.model.Cliente;
import com.coudevi.service.IClienteService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class ClienteServiceImpl implements IClienteService {
    private EntityManager em;

    public ClienteServiceImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void crear(Cliente cliente) {
        em.getTransaction().begin();
        em.persist(cliente);
        em.getTransaction().commit();
    }

    @Override
    public List<Cliente> listar() {
        return em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
    }
}
