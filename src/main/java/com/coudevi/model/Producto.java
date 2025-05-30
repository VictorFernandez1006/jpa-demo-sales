package com.coudevi.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProd;

    @Column(nullable = false, length = 100)
    private String nomProd;

    @Column(nullable = false)
    private double precio;

    @Column(nullable = false)
    private int stockActual;

    @OneToMany(mappedBy = "producto")
    private List<DetalleFactura> detalles;

    public Producto() {
    }

    public Producto(String nomProd, double precio, int stockActual, List<DetalleFactura> detalles) {
        this.nomProd = nomProd;
        this.precio = precio;
        this.stockActual = stockActual;
        this.detalles = detalles;
    }

    public Long getIdProd() {
        return idProd;
    }

    public void setIdProd(Long idProd) {
        this.idProd = idProd;
    }

    public String getNomProd() {
        return nomProd;
    }

    public void setNomProd(String nomProd) {
        this.nomProd = nomProd;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStockActual() {
        return stockActual;
    }

    public void setStockActual(int stockActual) {
        this.stockActual = stockActual;
    }

    public List<DetalleFactura> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleFactura> detalles) {
        this.detalles = detalles;
    }
}
