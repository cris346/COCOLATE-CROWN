# Crown Chocolates - Backend

Este es el backend de la aplicación Crown Chocolates, construido con Spring Boot y empaquetado con Docker. 
Se conecta a una base de datos PostgreSQL en Supabase.

## Requisitos
- Java 17
- Docker (Opcional, para ejecución contenerizada)

## Variables de Entorno (.env)
Para correrlo en local, se requiere un archivo `.env` en la raíz con las siguientes variables:
- `DATABASE_URL`
- `DATABASE_USER`
- `DATABASE_PASSWORD`
- `CORS_ORIGINS`
