package modelo;

import java.util.List;

public class Inscripcion {
    private String nombreEstudiante;
    private Curso curso;
    private List<Clase> clasesInscritas;

    public Inscripcion(String nombreEstudiante, Curso curso, List<Clase> clasesInscritas) {
        this.nombreEstudiante = nombreEstudiante;
        this.curso = curso;
        this.clasesInscritas = clasesInscritas;
    }

    // Getters

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public Curso getCurso() {
        return curso;
    }

    public List<Clase> getClasesInscritas() {
        return clasesInscritas;
    }

    public double getTotalPagado() {
        return clasesInscritas.stream().mapToDouble(Clase::getCostoInscripcion).sum();
    }
}