import json
from jose import jwt
from urllib.request import urlopen
from django.http import JsonResponse
from django.conf import settings

def jwt_authentication_middleware(get_response):
    def middleware(request):
        token = request.headers.get('Authorization', None)
        if token:
            try:
                token = token.split(" ")[1]  # Extraer el token sin 'Bearer'
                # Obtener la clave pública de Auth0
                jsonurl = urlopen(f"https://{settings.AUTH0_DOMAIN}/.well-known/jwks.json")
                jwks = json.loads(jsonurl.read())
                rsa_key = {}
                for key in jwks["keys"]:
                    if key["kid"] == jwt.get_unverified_header(token)["kid"]:
                        rsa_key = {
                            "kty": key["kty"],
                            "kid": key["kid"],
                            "use": key["use"],
                            "n": key["n"],
                            "e": key["e"]
                        }
                if rsa_key:
                    jwt.decode(
                        token,
                        rsa_key,
                        algorithms=settings.ALGORITHMS,
                        audience=settings.API_IDENTIFIER,
                        issuer=f"https://{settings.AUTH0_DOMAIN}/"
                    )
            except jwt.ExpiredSignatureError:
                return JsonResponse({"message": "Token expired"}, status=401)
            except jwt.JWTClaimsError:
                return JsonResponse({"message": "Incorrect claims"}, status=401)
            except Exception:
                return JsonResponse({"message": "Invalid header"}, status=401)
        else:
            return JsonResponse({"message": "Authorization header missing"}, status=401)

        response = get_response(request)
        return response

    return middleware
