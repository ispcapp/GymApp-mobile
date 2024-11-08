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
from rest_framework.authentication import TokenAuthentication
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
    print("Request Data:", request.data)
    serializer = UserSerializer(data=request.data)
    if serializer.is_valid():
        serializer.save()
        user = User.objects.get(username=serializer.data['email'])
        user.set_password(serializer.data['password'])
        user.save()

        refresh = RefreshToken.for_user(user)
        access_token = str(refresh.access_token)

        #token = Token.objects.create(user=user)
        return Response({"token": access_token,  "user":serializer.data}, status=status.HTTP_201_CREATED)
    
    return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


@api_view(['POST'])
def login(request):
    print("Request Data:", request.data)
    user = get_object_or_404(User, username = request.data['username'])
    if not user.check_password(request.data["password"]):
        return Response({"error": "Contraseña incorrecta"}, status.HTTP_400_BAD_REQUEST)
    
    # Crear token JWT
    refresh = RefreshToken.for_user(user)
    access_token = str(refresh.access_token)

   # token, created = Token.objects.get_or_create(user=user)
    serializer = UserSerializer(instance = user)

    return Response({
        "token": access_token, 
        "user": serializer.data
    }, status=status.HTTP_200_OK)

    #return Response({"token": token.key, "user": serializer.data}, status=status.HTTP_200_OK)
    

@api_view(['GET'])
@authentication_classes([TokenAuthentication])
@permission_classes([IsAuthenticated])
def profile(request):
    user = request.user
    
   
    user_data = {
        'username': user.username,
        'email': user.email,
        'first_name': user.first_name,
        'last_name': user.last_name,
        
    }
    
    
    return Response(user_data)

@api_view(['POST'])
@authentication_classes([JWTAuthentication, TokenAuthentication])
@permission_classes([IsAuthenticated])
def logout(request):
    try:
        if request.user.auth_token:
            request.user.auth_token.delete()
        
        if 'HTTP_AUTHORIZATION' in request.headers:
            refresh_token = request.headers['HTTP_AUTHORIZATION'].split('')[1]
            token = RefreshToken(refresh_token)
            token.blacklist()

        return Response ({"message": "Sesión cerrada"}, status=status.HTTP_200_OK)
    except Exception as e:
        return Response({"error": str(e)}, status=status.HTTP_400_BAD_REQUEST)
        



# @api_view(['POST'])
# def register_user(request):
#     data = request.data
#     try:
#         user = User.objects.create(
#             username=data['username'],
#             password=make_password(data['password']),
#             email=data.get('email', ''),
#         )
#         return Response({"message": "Usuario registrado con éxito"}, status=status.HTTP_201_CREATED)
#     except Exception as e:
#         return Response({"error": str(e)}, status=status.HTTP_400_BAD_REQUEST)