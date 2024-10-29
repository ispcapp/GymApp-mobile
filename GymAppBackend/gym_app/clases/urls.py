from django.urls import path, include
from rest_framework.routers import DefaultRouter
from .views import ClaseViewSet, ReservaViewSet, UsuarioViewSet,  ActividadViewSet, ActividadCategoriaViewSet, CategoriaViewSet

router = DefaultRouter()
router.register(r'clases', ClaseViewSet)
router.register(r'reservas', ReservaViewSet)
router.register(r'usuario', UsuarioViewSet)
router.register(r'actividad', ActividadViewSet)
router.register(r'actividadcategoria', ActividadCategoriaViewSet)
router.register(r'categoria', CategoriaViewSet)

urlpatterns = [
    path('', include(router.urls)),
]
