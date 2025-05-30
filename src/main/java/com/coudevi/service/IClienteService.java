package com.coudevi.service;

import com.coudevi.model.Cliente;

import java.util.List;

public interface IClienteService {
    void crear(Cliente cliente);
    List<Cliente> listar();
}
