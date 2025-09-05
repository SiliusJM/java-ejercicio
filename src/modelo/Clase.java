package modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Clase extends EntidadEducativa {
    private LocalDateTime fechaHoraInicio;
    private int duracionMinutos;
    private String enlaceSalaVirtual;
    private double costoInscripcion;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public Clase(String codigo, LocalDateTime fechaHoraInicio, int duracionMinutos, String enlaceSalaVirtual, double costoInscripcion) {
        super(codigo);
        this.fechaHoraInicio = fechaHoraInicio;
        this.duracionMinutos = duracionMinutos;
        this.enlaceSalaVirtual = enlaceSalaVirtual;
        this.costoInscripcion = costoInscripcion;
    }

    // Getters y setters

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public String getEnlaceSalaVirtual() {
        return enlaceSalaVirtual;
    }

    public double getCostoInscripcion() {
        return costoInscripcion;
    }

    @Override
    public String toString() {
        return String.format("Clase[%s] Inicio: %s, Duración: %d min, Costo: $%.2f, Enlace: %s",
                codigo, fechaHoraInicio.format(formatter), duracionMinutos, costoInscripcion, enlaceSalaVirtual);
    }
    
    
}

    
