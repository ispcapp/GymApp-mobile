from django.urls import path, include
from rest_framework import routers
from clases import views
from rest_framework.routers import DefaultRouter
from .views import ClaseViewSet, ReservaViewSet, ClienteViewSet,  ActividadViewSet, ActividadCategoriaViewSet, CategoriaViewSet

router = DefaultRouter()
router.register('clases', ClaseViewSet)
router.register('reservas', ReservaViewSet)
router.register('cliente', ClienteViewSet)
router.register('actividad', ActividadViewSet)
router.register('actividadcategoria', ActividadCategoriaViewSet)
router.register('categoria', CategoriaViewSet)

urlpatterns = [
    path('', include(router.urls)),
    #path('register/', register_user, name='register_user'),
]
