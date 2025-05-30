package com.coudevi;

import com.coudevi.model.*;
import com.coudevi.service.IClienteService;
import com.coudevi.service.IDetalleFacturaService;
import com.coudevi.service.IFacturaService;
import com.coudevi.service.IProductoService;
import com.coudevi.service.impl.ClienteServiceImpl;
import com.coudevi.service.impl.DetalleFacturaServiceImpl;
import com.coudevi.service.impl.FacturaServiceImpl;
import com.coudevi.service.impl.ProductoServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("demo-jpa");
    private static EntityManager em = emf.createEntityManager();

    private static IClienteService clienteService = new ClienteServiceImpl(em);
    private static IProductoService productoService = new ProductoServiceImpl(em);
    private static IFacturaService facturaService = new FacturaServiceImpl(em);
    private static IDetalleFacturaService detalleFacturaService = new DetalleFacturaServiceImpl(em);

    public static void main( String[] args )
    {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;
        while (!salir) {
            System.out.println("\nMenú:");
            System.out.println("1. Crear cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Crear producto");
            System.out.println("4. Crear factura");
            System.out.println("5. Listar facturas");
            System.out.println("6. Salir");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    crearCliente(scanner);
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    crearProducto(scanner);
                    listarProductos();
                    break;
                case 4:
                    crearFactura(scanner);
                    break;
                case 5:
                    listarFacturas();
                    break;
                case 6:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }

        em.close();
        emf.close();
    }



    private static void crearCliente(Scanner scanner) {
        System.out.println("\nIngrese el nombre del cliente:");
        String nombre = scanner.nextLine();
        System.out.println("Ingrese la dirección del cliente:");
        String direccion = scanner.nextLine();

        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setDireccion(direccion);

        clienteService.crear(cliente);
        System.out.println("Cliente creado con éxito.");
    }

    private static void listarClientes() {
        List<Cliente> clientes = clienteService.listar();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            System.out.println("\nClientes:");
            for (Cliente cliente : clientes) {
                System.out.println("ID: " + cliente.getIdCliente() + ", Nombre: " + cliente.getNombre() + ", Dirección: " + cliente.getDireccion());
            }
        }
    }

    private static void crearProducto(Scanner scanner) {
        System.out.println("\nIngrese el nombre del producto:");
        String nombre = scanner.nextLine();
        System.out.println("Ingrese el precio del producto:");
        double precio = scanner.nextDouble();
        System.out.println("Ingrese el stock actual del producto:");
        int stock = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        Producto producto = new Producto();
        producto.setNomProd(nombre);
        producto.setPrecio(precio);
        producto.setStockActual(stock);

        productoService.crear(producto);
        System.out.println("Producto creado con éxito.");
    }
    private static void listarProductos() {
        List<Producto> productos = productoService.listar();
        if (productos.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            System.out.println("\nProductos:");
            for (Producto producto : productos) {
                System.out.println("ID: " + producto.getIdProd() + ", Nombre: " + producto.getNomProd()+ ", Precio: " + producto.getPrecio());
            }
        }
    }
    private static void crearFactura(Scanner scanner) {
        System.out.println("\nIngrese el ID del cliente:");
        listarClientes();
        Long idCliente = scanner.nextLong();
        scanner.nextLine();

        Cliente cliente = em.find(Cliente.class, idCliente);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        System.out.println("Ingrese el estado de la factura (EMITIDO, CANCELADO, ANULADO):");
        String estado = scanner.nextLine().toUpperCase();
        if (!estado.equals("EMITIDO") && !estado.equals("CANCELADO") && !estado.equals("ANULADO")) {
            System.out.println("Estado no válido.");
            return;
        }

        Factura factura = new Factura();
        factura.setCliente(cliente);
        factura.setEstado(EstadoFactura.valueOf(estado));
        factura.setTotal(0);

        facturaService.crear(factura);
        System.out.println("Factura creada con éxito.");

        // Agregar detalles de factura
        agregarDetallesFactura(scanner, factura);
    }

    private static void agregarDetallesFactura(Scanner scanner, Factura factura) {
        boolean continuar = true;
        double totalFactura = 0.0;

        while (continuar) {
            System.out.println("\nIngrese el ID del producto:");
            Long idProducto = scanner.nextLong();
            scanner.nextLine();

            Producto producto = em.find(Producto.class, idProducto);
            if (producto == null) {
                System.out.println("Producto no encontrado.");
                return;
            }

            System.out.println("Ingrese la cantidad:");
            int cantidad = scanner.nextInt();
            scanner.nextLine();

            DetalleFactura detalle = new DetalleFactura();
            detalle.setFactura(factura);
            detalle.setProducto(producto);
            detalle.setCantidad(cantidad);

            // Calcular el total de la factura basado en el precio del producto y la cantidad
            totalFactura += producto.getPrecio() * cantidad;

            detalleFacturaService.crear(detalle);
            System.out.println("Detalle agregado.");

            System.out.println("\n¿Desea agregar otro detalle? (S/N)");
            String respuesta = scanner.nextLine().toUpperCase();
            if (respuesta.equals("N")) {
                continuar = false;
            }
        }

        // Después de agregar todos los detalles, actualizamos el total de la factura
        factura.setTotal(totalFactura);
        facturaService.crear(factura);  // Aseguramos que la factura se actualice con el total final
        System.out.println("Factura creada con total calculado: " + totalFactura);
    }

    private static void listarFacturas() {
        List<Factura> facturas = facturaService.listar();
        if (facturas.isEmpty()) {
            System.out.println("No hay facturas registradas.");
        } else {
            System.out.println("\nFacturas:");
            for (Factura factura : facturas) {
                System.out.println("Factura Nº: " + factura.getNroFactura() + ", Cliente: " + factura.getCliente().getNombre() +
                        ", Estado: " + factura.getEstado() + ", Total: " + factura.getTotal());
            }
        }
    }
}
