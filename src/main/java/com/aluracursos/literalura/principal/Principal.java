package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    //private List<DatosSerie> datosSeries = new ArrayList<>();

    public void muestraElMenu() {
        var json = consumoApi.obtenerDatos(URL_BASE);

        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ******** Menú ********
                    
                    1 - Buscar libro
                    2 - Buscar... 
                    3 - Mostrar...

                    0 - Salir
                    
                    *********************
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                   //buscarSerieWeb();
                    break;
                case 2:
                    //buscarEpisodioPorSerie();
                    break;
                case 3:
                    //mostrarSeriesBuscadas();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

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

