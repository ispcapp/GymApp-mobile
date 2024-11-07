from django.db import models
from django.contrib.auth.models import AbstractBaseUser, BaseUserManager, User

#class UsuarioManager(BaseUserManager):
#    def create_user(self, email, nombre, apellido, password=None, **extra_fields):
#        if not email:
#            raise ValueError('El email es obligatorio')
#        email = self.normalize_email(email)
#        user = self.model(email=email, nombre=nombre, apellido=apellido, **extra_fields)
#        user.set_password(password)
#        user.save(using=self._db)
#        return user
#
#    def create_superuser(self, email, nombre, apellido, password=None, **extra_fields):
#        extra_fields.setdefault('is_staff', True)
#        extra_fields.setdefault('is_superuser', True)
#        return self.create_user(email, nombre, apellido, password, **extra_fields)

#class Usuario(AbstractBaseUser):
#    nombre = models.CharField(max_length=25)
#    apellido = models.CharField(max_length=16)
#    email = models.EmailField(unique=True)
#    nroDoc = models.CharField(max_length=8, blank=True, null=True)
#    create_time = models.DateTimeField(auto_now_add=True)
#
#    is_active = models.BooleanField(default=True)
#    is_staff = models.BooleanField(default=False)
#    is_superuser = models.BooleanField(default=False)

#    objects = UsuarioManager()

 #   USERNAME_FIELD = 'email'
#    REQUIRED_FIELDS = ['nombre', 'apellido']

#    def __str__(self):
#        return f'{self.nombre} {self.apellido}'


class Cliente(models.Model):
    email = models.ForeignKey(User, on_delete=models.SET_NULL, null=True, blank=True)
    nombre = models.CharField(max_length=100)
    apellido = models.CharField(max_length=100)
    nroDoc = models.CharField(max_length=100)
    telefono = models.CharField(max_length=100)
    #fecha_nacimiento = models.DateField()


class Actividad(models.Model):
    denominacion = models.CharField(max_length=20)
    descripcion = models.TextField(blank=True, null=True)
    create_time = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return self.denominacion

class Clase(models.Model):
    actividad = models.ForeignKey(Actividad, on_delete=models.CASCADE, related_name='clases')
    dia = models.CharField(max_length=10)
    horario = models.PositiveSmallIntegerField()
    cupo = models.PositiveSmallIntegerField(blank=True, null=True)
    create_time = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return f'{self.actividad} - {self.dia} {self.horario}:00'


class Reserva(models.Model):
    clase = models.ForeignKey(Clase, on_delete=models.CASCADE, related_name='reservas')
    usuario = models.ForeignKey(Cliente, on_delete=models.CASCADE, related_name='reservas')
    estado = models.CharField(max_length=45, blank=True, null=True)
    create_time = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return f'Reserva de {self.usuario} en {self.clase}'


class Categoria(models.Model):
    denominacion = models.CharField(max_length=255, blank=True, null=True)
    create_time = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return self.denominacion


class ActividadCategoria(models.Model):
    categoria = models.ForeignKey(Categoria, on_delete=models.CASCADE, related_name='actividades')
    actividad = models.ForeignKey(Actividad, on_delete=models.CASCADE, related_name='categorias')
    create_time = models.DateTimeField(auto_now_add=True)

    class Meta:
        unique_together = ('categoria', 'actividad')

    def __str__(self):
        return f'{self.categoria} - {self.actividad}'
