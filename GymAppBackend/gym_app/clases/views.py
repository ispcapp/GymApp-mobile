from django.shortcuts import render

# Create your views here.
from rest_framework import viewsets
from .models import Clase, Reserva, Usuario, Actividad, ActividadCategoria, Categoria
from .serializers import ClaseSerializer, ReservaSerializer, UsuarioSerializer,  ActividadSerializer, ActividadCategoriaSerializer, CategoriaSerializer

class ClaseViewSet(viewsets.ModelViewSet):
    queryset = Clase.objects.all()
    serializer_class = ClaseSerializer

class ReservaViewSet(viewsets.ModelViewSet):
    queryset = Reserva.objects.all()
    serializer_class = ReservaSerializer

class UsuarioViewSet(viewsets.ModelViewSet):
    queryset = Usuario.objects.all()
    serializer_class = UsuarioSerializer


class ActividadViewSet(viewsets.ModelViewSet):
    queryset = Actividad.objects.all()
    serializer_class = ActividadSerializer

class ActividadCategoriaViewSet(viewsets.ModelViewSet):
    queryset = ActividadCategoria.objects.all()
    serializer_class = ActividadCategoriaSerializer

class CategoriaViewSet(viewsets.ModelViewSet):
    queryset = Categoria.objects.all()
    serializer_class = CategoriaSerializer