package com.ispcapp.gymapp.models;


import java.util.List;

public class Clase {
    private int id;
    private String nombre;
    private String descripcion;
    private String categoria;
    private String horario;
    private int cupo;

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }

    public int getCupo() { return cupo; }
    public void setCupo(int cupo) { this.cupo = cupo; }
}
