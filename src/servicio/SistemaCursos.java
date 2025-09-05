package servicio;

import modelo.*;
import excepciones.*;

import java.time.LocalDateTime;
import java.util.*;

public class SistemaCursos {
    // Mapa para organizar cursos por área temática
    private Map<String, List<Curso>> cursosPorArea = new HashMap<>();
    // Para evitar códigos repetidos
    private Set<String> codigosCurso = new HashSet<>();
    private Set<String> codigosClase = new HashSet<>();
    // Lista inscripciones
    private List<Inscripcion> inscripciones = new ArrayList<>();

    // Registrar curso
    public void registrarCurso(Curso curso) throws CodigoRepetidoException {
        if (codigosCurso.contains(curso.getCodigo())) {
            throw new CodigoRepetidoException("Código de curso repetido: " + curso.getCodigo());
        }
        codigosCurso.add(curso.getCodigo());
        cursosPorArea.computeIfAbsent(curso.getAreaTematica(), k -> new ArrayList<>()).add(curso);
    }

    // Registrar clase a curso
    public void registrarClase(String codigoCurso, Clase clase) throws CodigoRepetidoException, DatosInvalidosException {
        if (codigosClase.contains(clase.getCodigo())) {
            throw new CodigoRepetidoException("Código de clase repetido: " + clase.getCodigo());
        }

        Curso curso = buscarCursoPorCodigo(codigoCurso);
        if (curso == null) throw new DatosInvalidosException("Curso no encontrado: " + codigoCurso);

        // Validar que no haya horario duplicado para la misma sala
        for (Curso c : cursosPorArea.getOrDefault(curso.getAreaTematica(), new ArrayList<>())) {
            for (Clase cl : c.getClases()) {
                if (cl.getEnlaceSalaVirtual().equalsIgnoreCase(clase.getEnlaceSalaVirtual()) &&
                    cl.getFechaHoraInicio().equals(clase.getFechaHoraInicio())) {
                    throw new DatosInvalidosException("Horario duplicado en la sala virtual " + clase.getEnlaceSalaVirtual());
                }
            }
        }

        codigosClase.add(clase.getCodigo());
        curso.agregarClase(clase);
    }

    // Buscar curso por código
    public Curso buscarCursoPorCodigo(String codigo) {
        for (List<Curso> lista : cursosPorArea.values()) {
            for (Curso c : lista) {
                if (c.getCodigo().equalsIgnoreCase(codigo)) return c;
            }
        }
        return null;
    }

    // Buscar cursos por nivel de dificultad e imprimir clases
    public void buscarCursosPorNivel(String nivel) {
        System.out.println("Cursos de nivel " + nivel + ":");
        for (List<Curso> lista : cursosPorArea.values()) {
            for (Curso c : lista) {
                if (c.getNivel().equalsIgnoreCase(nivel)) {
                    System.out.println(c);
                    for (Clase cl : c.getClases()) {
                        System.out.println("  " + cl);
                    }
                }
            }
        }
    }

    // Filtrar clases por rango de fechas (inclusive)
    public void filtrarClasesPorFecha(LocalDateTime inicio, LocalDateTime fin) {
        System.out.println("Clases entre " + inicio + " y " + fin + ":");
        for (List<Curso> lista : cursosPorArea.values()) {
            for (Curso c : lista) {
                for (Clase cl : c.getClases()) {
                    if (!cl.getFechaHoraInicio().isBefore(inicio) && !cl.getFechaHoraInicio().isAfter(fin)) {
                        System.out.println(cl);
                    }
                }
            }
        }
    }

    // Registrar inscripción
    public void registrarInscripcion(String nombreEstudiante, String codigoCurso, int cantidadClases)
            throws CupoExcedidoException, DatosInvalidosException {
        Curso curso = buscarCursoPorCodigo(codigoCurso);
        if (curso == null) throw new DatosInvalidosException("Curso no encontrado: " + codigoCurso);

        if (cantidadClases > curso.getClases().size()) {
            throw new DatosInvalidosException("Cantidad de clases solicitadas supera las disponibles");
        }

        if (curso.getCuposDisponibles() < 1) {
            throw new CupoExcedidoException("No hay cupos disponibles para el curso " + codigoCurso);
        }

        // Seleccionar las primeras "cantidadClases" clases para la inscripción
        List<Clase> clasesParaInscribir = curso.getClases().subList(0, cantidadClases);
        curso.reducirCupo(1);

        Inscripcion inscripcion = new Inscripcion(nombreEstudiante, curso, clasesParaInscribir);
        inscripciones.add(inscripcion);
        System.out.println("Inscripción realizada para " + nombreEstudiante);
    }

    // Listar inscripciones de un estudiante
    public void listarInscripcionesPorEstudiante(String nombreEstudiante) {
        System.out.println("Inscripciones para: " + nombreEstudiante);
        for (Inscripcion ins : inscripciones) {
            if (ins.getNombreEstudiante().equalsIgnoreCase(nombreEstudiante)) {
                System.out.println(ins.getCurso());
                System.out.println("Clases inscritas:");
                ins.getClasesInscritas().forEach(cl -> System.out.println("  " + cl));
                System.out.printf("Total pagado: $%.2f%n", ins.getTotalPagado());
            }
        }
    }
}

