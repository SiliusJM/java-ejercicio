package modelo;

import excepciones.CupoExcedidoException;
import java.util.ArrayList;
import java.util.List;

public class Curso extends EntidadEducativa {
    private String titulo;
    private String areaTematica;
    private String nivel; // básico, intermedio, avanzado
    private int duracionHoras;
    private String idioma;
    private int cupoMaximo;
    private int cuposDisponibles;
    private List<Clase> clases;

    public Curso(String codigo, String titulo, String areaTematica, String nivel,
                 int duracionHoras, String idioma, int cupoMaximo) {
        super(codigo);
        this.titulo = titulo;
        this.areaTematica = areaTematica;
        this.nivel = nivel;
        this.duracionHoras = duracionHoras;
        this.idioma = idioma;
        this.cupoMaximo = cupoMaximo;
        this.cuposDisponibles = cupoMaximo;
        this.clases = new ArrayList<>();
    }

    // Getters y setters (encapsulamiento)

    public void agregarClase(Clase clase) {
        clases.add(clase);
    }

    public List<Clase> getClases() {
        return clases;
    }

    public int getCuposDisponibles() {
        return cuposDisponibles;
    }

    public void reducirCupo(int cantidad) throws CupoExcedidoException {
        if (cantidad > cuposDisponibles) {
            throw new CupoExcedidoException("No hay cupos suficientes para el curso " + codigo);
        }
        cuposDisponibles -= cantidad;
    }

    public String getNivel() {
        return nivel;
    }

    public String getAreaTematica() {
        return areaTematica;
    }

    @Override
    public String toString() {
        return String.format("Curso[%s] %s - %s - Nivel: %s - Cupos disponibles: %d",
                codigo, titulo, areaTematica, nivel, cuposDisponibles);
    }
}
