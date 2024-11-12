package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.Datos;
import com.aluracursos.literalura.model.DatosLibros;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();

    public void muestraElMenu() {
        var json = consumoApi.obtenerDatos(URL_BASE);
        
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    \n******** Menú ********
                    
                    1 - Buscar libro por título
                    2 - Listar libros registrados 
                    3 - Listar autores registrados (Copyright)
                    4 - Listar autores vivos antes de un determinado año
                    5 - Listar libros por idioma
                    6 - Listar los 5 libros más descargados

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
                    //listarLibrosRegistrados();
                    break;
                case 3:
                    //listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosPorAnio();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 6:
                    listarLibrosMasDescargados();
                case 0:
                    System.out.println("\nCerrando la aplicación...\n");
                    break;
                default:
                    System.out.println("\nOpción inválida\n");
            }
        }

    }

   //Consumo desde la API de Gutendex
    private void buscarLibroPorTitulo() {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        var datos = conversor.obtenerDatos(json, Datos.class);
        //System.out.println(datos);
        Optional<DatosLibros> librosOptional = datos.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                .findFirst();
        if (librosOptional.isPresent()) {
            System.out.println("Libro encontrado: " + librosOptional.get());
        } else {
            System.out.println("El libro " + nombreLibro + " NO fue encontrado");
        }
    }



    //Consumo desde la DB db_literalura

//    private void listarAutoresRegistrados() {
//        System.out.println("Los autores registrados (Copyright) son: ");
//        var json = consumoApi.obtenerDatos(URL_BASE + "?copyright=true");
//        var datos = conversor.obtenerDatos(json, Datos.class);
//        datos.resultados().stream()
//                .sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
//                .limit(10)
//                .map(d -> d.autor().stream().limit(10).map(a -> a.nombre().toUpperCase()).forEach(System.out::println))
//                .forEach(System.out::println);
//    }

    private void listarAutoresVivosPorAnio() {
        System.out.println("Escribe el año para iniciar la búsqueda");
        var anioAutor = Integer.valueOf(teclado.nextInt()) ;
        var json = consumoApi.obtenerDatos(URL_BASE + "?author_year_end=" +anioAutor);
        var datos = conversor.obtenerDatos(json, Datos.class);
        datos.resultados().stream()
                .sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
                .limit(10)
                .map(l -> l.titulo().toUpperCase())
                .forEach(System.out::println);
    }

    private void listarLibrosPorIdioma() {
        System.out.println("Escribe el idioma del libro que deseas buscar");
        var idiomaLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?languages=" +idiomaLibro);
        var datos = conversor.obtenerDatos(json, Datos.class);
        datos.resultados().stream()
                .sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
                .limit(10)
                .map(l -> l.titulo().toUpperCase())
                .forEach(System.out::println);
    }

    private void listarLibrosMasDescargados() {
        System.out.println("Los libros más descargados son: ");
        var json = consumoApi.obtenerDatos(URL_BASE);
        var datos = conversor.obtenerDatos(json, Datos.class);
        datos.resultados().stream()
                .sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
                .limit(5)
                .map(l -> l.titulo().toUpperCase())
                .forEach(System.out::println);
    }

//    private DatosSerie getDatosSerie() {
//        System.out.println("Escribe el nombre de la serie que deseas buscar");
//        var nombreSerie = teclado.nextLine();
//        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
//        System.out.println(json);
//        DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
//        return datos;
//    }
//    private void buscarEpisodioPorSerie() {
//        DatosSerie datosSerie = getDatosSerie();
//        List<DatosTemporadas> temporadas = new ArrayList<>();
//
//        for (int i = 1; i <= datosSerie.totalTemporadas(); i++) {
//            var json = consumoApi.obtenerDatos(URL_BASE + datosSerie.titulo().replace(" ", "+") + "&season=" + i + API_KEY);
//            DatosTemporadas datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class);
//            temporadas.add(datosTemporada);
//        }
//        temporadas.forEach(System.out::println);
//    }
//    private void buscarSerieWeb() {
//        DatosSerie datos = getDatosSerie();
//        datosSeries.add(datos);
//        System.out.println(datos);
//    }
//
//    private void mostrarSeriesBuscadas() {
//        List<Serie> series = new ArrayList<>();
//        series = datosSeries.stream()
//                .map(d -> new Serie(d))
//                .collect(Collectors.toList());
//
//        series.stream()
//                .sorted(Comparator.comparing(Serie::getGenero))
//                .forEach(System.out::println);
//    }
}

