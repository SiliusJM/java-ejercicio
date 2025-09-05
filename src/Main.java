import excepciones.CupoExcedidoException;
import excepciones.DatosInvalidosException;
import servicio.SistemaCursos;
import modelo.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    private static SistemaCursos sistema = new SistemaCursos();
    private static Scanner sc = new Scanner(System.in);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public static void main(String[] args) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Menú Sistema Cursos ---");
            System.out.println("1. Registrar curso");
            System.out.println("2. Registrar clase");
            System.out.println("3. Buscar cursos por nivel");
            System.out.println("4. Filtrar clases por rango de fecha");
            System.out.println("5. Registrar inscripción");
            System.out.println("6. Listar inscripciones por estudiante");
            System.out.println("0. Salir");
            System.out.print("Seleccione opción: ");
            String opcion = sc.nextLine();

            try {
                switch (opcion) {
                    case "1":
                        registrarCurso();
                        break;
                    case "2":
                        registrarClase();
                        break;
                    case "3":
                        buscarCursosPorNivel();
                        break;
                    case "4":
                        filtrarClasesPorFecha();
                        break;
                    case "5":
                        registrarInscripcion();
                        break;
                    case "6":
                        listarInscripcionesPorEstudiante();
                        break;
                    case "0":
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        sc.close();
    }

    private static void registrarCurso() throws Exception {
        System.out.print("Código curso: ");
        String codigo = sc.nextLine();
        System.out.print("Título: ");
        String titulo = sc.nextLine();
        System.out.print("Área temática: ");
        String area = sc.nextLine();
        System.out.print("Nivel (básico/intermedio/avanzado): ");
        String nivel = sc.nextLine();
        System.out.print("Duración total horas: ");
        int duracion = Integer.parseInt(sc.nextLine());
        System.out.print("Idioma: ");
        String idioma = sc.nextLine();
        System.out.print("Cupo máximo: ");
        int cupo = Integer.parseInt(sc.nextLine());

        Curso curso = new Curso(codigo, titulo, area, nivel, duracion, idioma, cupo);
        sistema.registrarCurso(curso);
        System.out.println("Curso registrado.");
    }

    private static void registrarClase() throws Exception {
        System.out.print("Código curso: ");
        String codigoCurso = sc.nextLine();
        System.out.print("Código clase: ");
        String codigoClase = sc.nextLine();
        System.out.print("Fecha y hora inicio (dd-MM-yyyy HH:mm): ");
        LocalDateTime fechaHora = LocalDateTime.parse(sc.nextLine(), formatter);
        System.out.print("Duración minutos: ");
        int duracion = Integer.parseInt(sc.nextLine());
        System.out.print("Enlace sala virtual: ");
        String enlace = sc.nextLine();
        System.out.print("Costo inscripción: ");
        double costo = Double.parseDouble(sc.nextLine());

        Clase clase = new Clase(codigoClase, fechaHora, duracion, enlace, costo);
        sistema.registrarClase(codigoCurso, clase);
        System.out.println("Clase registrada.");
    }

    private static void buscarCursosPorNivel() {
        System.out.print("Nivel a buscar: ");
        String nivel = sc.nextLine();
        sistema.buscarCursosPorNivel(nivel);
    }

    private static void filtrarClasesPorFecha() {
        System.out.print("Fecha inicio (dd-MM-yyyy HH:mm): ");
        LocalDateTime inicio = LocalDateTime.parse(sc.nextLine(), formatter);
        System.out.print("Fecha fin (dd-MM-yyyy HH:mm): ");
        LocalDateTime fin = LocalDateTime.parse(sc.nextLine(), formatter);
        sistema.filtrarClasesPorFecha(inicio, fin);
    }

    private static void registrarInscripcion() {
    try {
        System.out.print("Nombre estudiante: ");
        String estudiante = sc.nextLine();
        System.out.print("Código curso: ");
        String codigoCurso = sc.nextLine();
        System.out.print("Cantidad clases a inscribir: ");
        int cantidad = Integer.parseInt(sc.nextLine());

        sistema.registrarInscripcion(estudiante, codigoCurso, cantidad);

    } catch (CupoExcedidoException | DatosInvalidosException e) {
        System.out.println("Error: " + e.getMessage());
    } catch (NumberFormatException e) {
        System.out.println("Error: Debe ingresar un número válido para cantidad.");
    }
}


    private static void listarInscripcionesPorEstudiante() {
        System.out.print("Nombre estudiante: ");
        String nombre = sc.nextLine();
        sistema.listarInscripcionesPorEstudiante(nombre);
    }
}





