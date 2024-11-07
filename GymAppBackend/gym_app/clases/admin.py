from django.contrib import admin
from .models import Cliente, Actividad, Clase, Reserva, Categoria, ActividadCategoria

admin.site.register(Cliente)
admin.site.register(Actividad)
admin.site.register(Clase)
admin.site.register(Reserva)
admin.site.register(Categoria)
admin.site.register(ActividadCategoria)