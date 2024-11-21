package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.*;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository libroRepositorio;
    private AutorRepository autorRepositorio;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepositorio = libroRepository;
        this.autorRepositorio = autorRepository;
    }

    public void muestraElMenu() {
        var json = consumoApi.obtenerDatos(URL_BASE);
        
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    \n******** Menú ********
                    
                    1 - Buscar libro por título
                    2 - Listar libros registrados en la DB
                    3 - Listar autores registrados en la DB
                    4 - Listar autores vivos antes de un determinado año
                    5 - Listar libros por idioma
                    6 - Listar los 5 libros más descargados
                    
                    7 - TEST

                    0 - Salir
                    
                    Opción: 
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
//                    listarLibrosRegistrados();
                    break;
                case 3:
//                    listarAutoresRegistrados();
                    break;
                case 4:
//                    listarAutoresVivosPorAnio();
                    break;
                case 5:
//                    listarLibrosPorIdioma();
                    break;
                case 6:
//                    listarLibrosMasDescargados();
                case 7:
//                    pruebas();
                    break;
                case 0:
                    System.out.println("\nCerrando la aplicación...\n");
                    break;
                default:
                    System.out.println("\nOpción inválida\n");
            }
        }

    }

//    private void pruebas() {
//        var json = consumoApi.obtenerDatos(URL_BASE);
//        var datos = conversor.obtenerDatos(json, Datos.class);
//        List<Libro> libros = datos.resultados().stream()
//                .map(l -> new Libro(l))
//                .collect(Collectors.toList());
//        libros.stream()
//                .forEach(System.out::println);
//    }

    private Libro crearLibro(DatosLibros datosLibros, Autor autor) {
        if (autor != null) {
            return new Libro(datosLibros, autor);
        } else {
            System.out.println("El autor es null, no se puede crear el libro");
            return null;
        }
    }


    //Consumo desde la API de Gutendex
    private void buscarLibroPorTitulo() {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        var datos = conversor.obtenerDatos(json, Datos.class);

        if (!datos.resultados().isEmpty()){
            DatosLibros datosLibros = datos.resultados().get(0);
            DatosAutor datosAutor = datosLibros.autor().get(0);
            Libro libro = null;
            Libro libroRepo = libroRepositorio.findByTitulo(datosLibros.titulo());
            if (libroRepo != null){
                System.out.println("Este libre ya se encuentra ingresado en la base de datos.");
                System.out.println(libroRepo.toString());
            } else {
                Autor autorRepo = autorRepositorio.findByNombreIgnoreCase(datosLibros.autor().get(0).nombre());
                if (autorRepo != null){
                    libro = crearLibro(datosLibros, autorRepo);
                    libroRepositorio.save(libro);
                    System.out.println("Se agregó un nuevo libro a la base de datos. \n");
                    System.out.println(libro);
                } else {
                    Autor autor = new Autor(datosAutor);
                    autor = autorRepositorio.save(autor);
                    libro = crearLibro(datosLibros, autor);
                    libroRepositorio.save(libro);
                    System.out.println("Se agregó un nuevo libro a la base de datos. \n");
                    System.out.println(libro);
                }
            }
        } else {
            System.out.println("<ATENCION> El libro buscado NO existe en la API de Gutendex, ingresa otro");
        }
    }

    //Consumo desde la DB db_literalura


}

