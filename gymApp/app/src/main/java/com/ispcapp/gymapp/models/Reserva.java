package com.ispcapp.gymapp.models;

public class Reserva {
    private int id;
    private int usuarioId;
    private int claseId;
    private String fechaReserva;
    private String estado;

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public int getClaseId() { return claseId; }
    public void setClaseId(int claseId) { this.claseId = claseId; }

    public String getFechaReserva() { return fechaReserva; }
    public void setFechaReserva(String fechaReserva) { this.fechaReserva = fechaReserva; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
