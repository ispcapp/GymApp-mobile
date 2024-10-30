package com.ispcapp.gymapp.api;

import com.ispcapp.gymapp.models.Clase;
import com.ispcapp.gymapp.models.Reserva;

import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;

public interface ClaseApi {
    @GET("api/clases/")
    Call<List<Clase>> getClases();

    @GET("api/reservas/")
    Call<List<Reserva>> getReservas();
}