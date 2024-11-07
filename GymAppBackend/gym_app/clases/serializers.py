from rest_framework import serializers
from django.contrib.auth.models import User
from .models import Clase, Reserva, Cliente, Actividad, ActividadCategoria, Categoria

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ['id',
                  'email',
                  'nombre',
                  'apellido',
                  'nroDoc',
                  'password']

class ClienteSerializer(serializers.ModelSerializer):
    class Meta:
        model=Cliente
        fields='__all__'


class ClaseSerializer(serializers.ModelSerializer):
    class Meta:
        model = Clase
        fields = '__all__'

class ReservaSerializer(serializers.ModelSerializer):
    class Meta:
        model = Reserva
        fields = '__all__'

#class UsuarioSerializer(serializers.ModelSerializer):
#    class Meta:
#        model = Usuario
#        fields = '__all__'

#class UsuarioManagerSerializer(serializers.ModelSerializer):
#    class Meta:
#        model = UsuarioManager
#        fields = '__all__'

class ActividadSerializer(serializers.ModelSerializer):
    class Meta:
        model = Actividad
        fields = '__all__'

class ActividadCategoriaSerializer(serializers.ModelSerializer):
    class Meta:
        model = ActividadCategoria
        fields = '__all__'

class CategoriaSerializer(serializers.ModelSerializer):
    class Meta:
        model = Categoria
        fields = '__all__'
