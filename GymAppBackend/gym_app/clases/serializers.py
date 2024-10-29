from rest_framework import serializers
from .models import Clase, Reserva, Usuario, Actividad, ActividadCategoria, Categoria, UsuarioManager

class ClaseSerializer(serializers.ModelSerializer):
    class Meta:
        model = Clase
        fields = '__all__'

class ReservaSerializer(serializers.ModelSerializer):
    class Meta:
        model = Reserva
        fields = '__all__'

class UsuarioSerializer(serializers.ModelSerializer):
    class Meta:
        model = Usuario
        fields = '__all__'

class UsuarioManagerSerializer(serializers.ModelSerializer):
    class Meta:
        model = UsuarioManager
        fields = '__all__'

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
