from django.contrib import admin
from .models import Usuario, Actividad, Clase, Reserva, Categoria, ActividadCategoria

admin.site.register(Usuario)
admin.site.register(Actividad)
admin.site.register(Clase)
admin.site.register(Reserva)
admin.site.register(Categoria)
admin.site.register(ActividadCategoria)