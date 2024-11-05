from django.shortcuts import render, get_object_or_404

# Create your views here.
from rest_framework import viewsets
from rest_framework.response import Response
from rest_framework.authtoken.models import Token
from rest_framework import status
from django.contrib.auth.models import User
from .models import Clase, Reserva, Cliente, Actividad, ActividadCategoria, Categoria
from .serializers import ClaseSerializer, ReservaSerializer, ClienteSerializer,  ActividadSerializer, ActividadCategoriaSerializer, CategoriaSerializer, UserSerializer
from rest_framework.decorators import api_view, authentication_classes,permission_classes
from rest_framework.permissions import AllowAny, IsAuthenticated
from rest_framework_simplejwt.authentication import JWTAuthentication
from rest_framework_simplejwt.tokens import RefreshToken
from django.contrib.auth.hashers import make_password



class ClaseViewSet(viewsets.ModelViewSet):
   # permission_classes = [AllowAny] 
    queryset = Clase.objects.all()
    serializer_class = ClaseSerializer
    permission_classes = [IsAuthenticated]

class ReservaViewSet(viewsets.ModelViewSet):
   # permission_classes = [AllowAny] 
    queryset = Reserva.objects.all()
    serializer_class = ReservaSerializer
    permission_classes = [IsAuthenticated]

class ClienteViewSet(viewsets.ModelViewSet):
    #permission_classes = [AllowAny] 
    queryset = Cliente.objects.all()
    serializer_class = ClienteSerializer


class ActividadViewSet(viewsets.ModelViewSet):
   # permission_classes = [AllowAny] 
    queryset = Actividad.objects.all()
    serializer_class = ActividadSerializer
    permission_classes = [IsAuthenticated]

class ActividadCategoriaViewSet(viewsets.ModelViewSet):
   # permission_classes = [AllowAny] 
    queryset = ActividadCategoria.objects.all()
    serializer_class = ActividadCategoriaSerializer

class CategoriaViewSet(viewsets.ModelViewSet):
    #permission_classes = [AllowAny] 
    queryset = Categoria.objects.all()
    serializer_class = CategoriaSerializer
    permission_classes = [IsAuthenticated]

@api_view(['POST'])
def register(request):
    serializer = UserSerializer(data=request.data)
    if serializer.is_valid():
        serializer.save()
        user = User.objects.get(email=serializer.data['email'])
        user.set_password(serializer.data['password'])
        user.save()

        token = Token.objects.create(user=user)
        return Response({'token': token.key, "user":serializer.data}, status=status.HTTP_201_CREATED)
    
    return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)



""" @api_view(['POST'])
def register_user(request):
    data = request.data
    try:
        user = User.objects.create(
            username=data['username'],
            password=make_password(data['password']),
            email=data.get('email', ''),
        )
        return Response({"message": "Usuario registrado con éxito"}, status=status.HTTP_201_CREATED)
    except Exception as e:
        return Response({"error": str(e)}, status=status.HTTP_400_BAD_REQUEST) """