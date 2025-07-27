# 🎓 Sistema de Gestión de Estudiantes

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.4-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15+-blue.svg)](https://www.postgresql.org/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-red.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

Sistema completo de gestión académica desarrollado con **Spring Boot 3.5.4** y **Java 21**, que permite administrar estudiantes, materias, notas y información financiera de una institución educativa.

## 📋 Tabla de Contenidos

- [Características](#-características)
- [Tecnologías](#-tecnologías)
- [Requisitos Previos](#-requisitos-previos)
- [Instalación](#-instalación)
- [Configuración](#-configuración)
- [Ejecución](#-ejecución)
- [API Documentation](#-api-documentation)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Endpoints Principales](#-endpoints-principales)
- [Despliegue](#-despliegue)
- [Monitoreo](#-monitoreo)
- [Contribución](#-contribución)

## 🚀 Características

### 📚 **Gestión Académica**
- ✅ **Estudiantes**: CRUD completo con información personal y académica
- ✅ **Materias**: Gestión de asignaturas por carrera y semestre
- ✅ **Notas**: Sistema de calificaciones con diferentes tipos de evaluación
- ✅ **Información Financiera**: Control de pensiones, becas y pagos

### 🔐 **Seguridad y Autenticación**
- ✅ **JWT Authentication**: Tokens seguros con expiración configurable
- ✅ **Roles de Usuario**: ADMIN y ESTUDIANTE con permisos diferenciados
- ✅ **Encriptación**: Contraseñas encriptadas con BCrypt
- ✅ **CORS**: Configuración para frontend

### 🏢 **Gestión Institucional**
- ✅ **Información de la Empresa**: Datos institucionales editables
- ✅ **Redes Sociales**: Enlaces y configuración de plataformas
- ✅ **Banners**: Sistema de publicidad y anuncios

### 📊 **API REST Completa**
- ✅ **50+ Endpoints**: API RESTful completa
- ✅ **Documentación Swagger**: Interfaz interactiva para pruebas
- ✅ **DTOs y Validaciones**: Datos estructurados y validados
- ✅ **Manejo de Errores**: Respuestas consistentes y descriptivas

## 🛠 Tecnologías

### **Backend**
- **Java 21** - Lenguaje de programación
- **Spring Boot 3.5.4** - Framework principal
- **Spring Security** - Autenticación y autorización
- **Spring Data JPA** - Acceso a datos
- **JWT** - Tokens de autenticación
- **MapStruct** - Mapeo de DTOs
- **Lombok** - Reducción de código boilerplate

### **Base de Datos**
- **PostgreSQL 15+** - Base de datos principal
- **Hibernate** - ORM

### **Documentación**
- **OpenAPI 3** - Especificación de API
- **Swagger UI** - Interfaz de documentación

### **Herramientas**
- **Maven** - Gestión de dependencias
- **Docker** - Contenedorización (opcional)

## 📋 Requisitos Previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

### **Software Requerido:**
- ☑️ **Java 21** o superior ([Descargar OpenJDK](https://openjdk.java.net/))
- ☑️ **Maven 3.6+** ([Descargar Maven](https://maven.apache.org/download.cgi))
- ☑️ **PostgreSQL 15+** ([Descargar PostgreSQL](https://www.postgresql.org/download/))
- ☑️ **Git** ([Descargar Git](https://git-scm.com/downloads))

### **Verificar Instalación:**
```bash
# Verificar Java
java -version

# Verificar Maven
mvn -version

# Verificar PostgreSQL
psql --version

# Verificar Git
git --version
```

## 🔧 Instalación

### **1. Clonar el Repositorio**
```bash
git clone https://github.com/ErickQuizhpe/estudiantes.git
cd estudiantes
```

### **2. Configurar Base de Datos**
```sql
-- Crear base de datos
CREATE DATABASE estudiantes_db;

-- Crear usuario (opcional)
CREATE USER estudiantes_user WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE estudiantes_db TO estudiantes_user;
```

### **3. Instalar Dependencias**
```bash
# Desde la raíz del proyecto
mvn clean install
```

## ⚙️ Configuración

### **Variables de Entorno**

Crea un archivo `.env` o configura las siguientes variables de entorno:

```bash
# Base de Datos
DATABASE_URL=localhost:5432/estudiantes_db
DATABASE_USERNAME=tu_usuario
DATABASE_PASSWORD=tu_contraseña

# JWT
JWT_SECRET=tu_clave_secreta_muy_larga_y_segura
JWT_EXPIRATION=86400000

# Email (opcional)
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=tu_email@gmail.com
MAIL_PASSWORD=tu_app_password
```

### **Configuración en Windows**
```cmd
set DATABASE_URL=localhost:5432/estudiantes_db
set DATABASE_USERNAME=estudiantes_user
set DATABASE_PASSWORD=tu_contraseña
set JWT_SECRET=clave_secreta_muy_larga_para_jwt_token
set JWT_EXPIRATION=86400000
```

### **Configuración en Linux/Mac**
```bash
export DATABASE_URL=localhost:5432/estudiantes_db
export DATABASE_USERNAME=estudiantes_user
export DATABASE_PASSWORD=tu_contraseña
export JWT_SECRET=clave_secreta_muy_larga_para_jwt_token
export JWT_EXPIRATION=86400000
```

## 🚀 Ejecución

### **Opción 1: Usando Maven**
```bash
# Ejecutar en modo desarrollo
mvn spring-boot:run

# Con perfil específico
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### **Opción 2: Usando JAR**
```bash
# Compilar
mvn clean package

# Ejecutar
java -jar target/estudiantes-0.0.1-SNAPSHOT.jar
```

### **Opción 3: Desde IDE**
1. Importar proyecto en tu IDE (IntelliJ IDEA, Eclipse, VSCode)
2. Configurar variables de entorno
3. Ejecutar `EstudiantesApplication.java`

### **Verificar Ejecución**
- ✅ **Aplicación**: http://localhost:8080/api
- ✅ **Swagger UI**: http://localhost:8080/api/docs/swagger-ui.html
- ✅ **Health Check**: http://localhost:8080/api/actuator/health

## 📖 API Documentation

### **Swagger UI**
La documentación interactiva está disponible en:
```
http://localhost:8080/api/docs/swagger-ui.html
```

### **OpenAPI JSON**
```
http://localhost:8080/api/v3/api-docs
```

### **Credenciales de Prueba**
```json
{
  "admin": {
    "username": "admin",
    "password": "admin"
  },
  "estudiante1": {
    "username": "estudiante1", 
    "password": "123456"
  },
  "estudiante2": {
    "username": "estudiante2",
    "password": "123456"
  }
}
```

## 📁 Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/api/estudiantes/
│   │   ├── EstudiantesApplication.java          # Clase principal
│   │   ├── configuration/                       # Configuraciones
│   │   │   ├── AppConfig.java                  # Datos iniciales
│   │   │   ├── OpenApiConfig.java              # Swagger config
│   │   │   └── SecurityConfig.java             # Seguridad JWT
│   │   ├── controller/                         # Controladores REST
│   │   │   ├── AuthController.java             # Autenticación
│   │   │   ├── StudenController.java           # Estudiantes
│   │   │   ├── MateriaController.java          # Materias
│   │   │   ├── NotaController.java             # Notas
│   │   │   ├── FinancieraController.java       # Finanzas
│   │   │   ├── CompanyController.java          # Empresa
│   │   │   └── dto/                            # DTOs
│   │   ├── entity/                             # Entidades JPA
│   │   │   ├── user/                           # Usuario y roles
│   │   │   ├── studen/                         # Estudiante y relacionados
│   │   │   └── company/                        # Empresa
│   │   ├── repository/                         # Repositorios JPA
│   │   ├── service/                            # Lógica de negocio
│   │   ├── exception/                          # Manejo de errores
│   │   └── utils/                              # Utilidades
│   └── resources/
│       ├── application.yml                     # Configuración principal
│       └── db/migration/                       # Migraciones (futuro)
└── test/                                       # Pruebas unitarias
```

## 🔗 Endpoints Principales

### **🔐 Autenticación**
```http
POST /api/auth/login                # Iniciar sesión
POST /api/auth/register             # Registrar usuario
```

### **👨‍🎓 Estudiantes**
```http
GET    /api/estudiantes             # Listar todos
POST   /api/estudiantes             # Crear estudiante
GET    /api/estudiantes/{id}        # Obtener por ID
PUT    /api/estudiantes/{id}        # Actualizar
DELETE /api/estudiantes/{id}        # Eliminar
GET    /api/estudiantes/carrera/{carrera}  # Por carrera
```

### **📚 Materias**
```http
GET    /api/materias                # Listar todas
POST   /api/materias                # Crear materia
GET    /api/materias/{id}           # Obtener por ID
PUT    /api/materias/{id}           # Actualizar
DELETE /api/materias/{id}           # Eliminar
```

### **📊 Notas**
```http
GET    /api/notas                   # Listar todas
POST   /api/notas                   # Crear nota
GET    /api/notas/estudiante/{id}   # Por estudiante
GET    /api/notas/materia/{id}      # Por materia
```

### **💰 Financiera**
```http
GET    /api/financiera              # Listar todas
POST   /api/financiera              # Crear registro
GET    /api/financiera/estudiante/{id}  # Por estudiante
```

### **🏢 Empresa**
```http
GET    /api/company                 # Información institucional
PUT    /api/company/{id}            # Actualizar información
```

## 🌐 Despliegue

### **🚀 Enlaces de Despliegue**

> **📝 Nota**: Actualiza estos enlaces con tus URLs reales de despliegue

- 🌍 **Producción**: `https://tu-dominio.com/api`
- 🧪 **Staging**: `https://staging.tu-dominio.com/api`
- 📖 **Documentación**: `https://tu-dominio.com/api/docs/swagger-ui.html`

### **☁️ Heroku (Ejemplo)**
```bash
# Crear aplicación
heroku create tu-app-estudiantes

# Configurar variables
heroku config:set DATABASE_URL=tu_postgres_url
heroku config:set JWT_SECRET=tu_clave_secreta

# Desplegar
git push heroku main
```

### **🐳 Docker (Opcional)**
```dockerfile
# Dockerfile
FROM openjdk:21-jdk-slim
COPY target/estudiantes-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```

```bash
# Construir imagen
docker build -t estudiantes-api .

# Ejecutar contenedor
docker run -p 8080:8080 estudiantes-api
```

## 📊 Monitoreo

### **🔍 Métricas Implementadas**

#### **📈 Métricas Básicas (Actuator + Micrometer)**
- **Número de peticiones HTTP**: Total por endpoint y método
- **Tiempo de respuesta**: Promedio, máximo y percentiles (50%, 90%, 95%, 99%)
- **Códigos de estado**: Conteo por código de respuesta HTTP
- **Uso de memoria**: JVM heap, memoria libre/total
- **Threads**: Conteo de hilos activos
- **Base de datos**: Estado de conexión y health checks

#### **📊 Métricas Personalizadas**
- **Estudiantes**: Total y activos
- **Materias**: Total y activas  
- **Notas**: Total de calificaciones
- **Usuarios**: Total y activos
- **Registros financieros**: Conteo total

### **🌐 Endpoints de Monitoreo**

#### **🔧 Actuator (Spring Boot)**
```http
GET /api/actuator/health            # Estado de la aplicación
GET /api/actuator/info             # Información de la aplicación
GET /api/actuator/metrics           # Métricas generales
GET /api/actuator/prometheus        # Métricas en formato Prometheus
GET /api/actuator/env              # Variables de entorno
GET /api/actuator/beans            # Beans de Spring
```

#### **📈 Métricas Personalizadas**
```http
GET /api/metrics/custom            # Métricas del negocio
GET /api/metrics/requests          # Métricas de peticiones detalladas
GET /api/metrics/health-custom     # Estado personalizado
```

### **🚀 Configuración Rápida con Docker**

#### **Opción 1: Solo Monitoreo Local**
```bash
# Variables de entorno necesarias
export DATABASE_URL=localhost:5432/estudiantes_db
export DATABASE_USERNAME=tu_usuario
export DATABASE_PASSWORD=tu_contraseña
export JWT_SECRET=tu_clave_secreta

# Ejecutar aplicación
mvn spring-boot:run
```

#### **Opción 2: Stack Completo (App + Prometheus + Grafana)**
```bash
# Construir y ejecutar todo el stack
chmod +x start-monitoring.sh
./start-monitoring.sh

# O manualmente:
mvn clean package -DskipTests
docker-compose -f docker-compose.monitoring.yml up -d
```

### **📊 URLs de Acceso - Monitoreo**

#### **🔍 Métricas y Monitoreo**
- 📈 **Métricas Personalizadas**: http://localhost:8080/api/metrics/custom
- 💓 **Health Check**: http://localhost:8080/api/actuator/health  
- 📊 **Métricas Prometheus**: http://localhost:8080/api/actuator/prometheus

#### **🛠️ Herramientas de Monitoreo**
- 📊 **Prometheus**: http://localhost:9090
- 📈 **Grafana**: http://localhost:3000 
  - Usuario: `admin` / Contraseña: `admin123`
- 🗄️ **pgAdmin**: http://localhost:5050
  - Usuario: `admin@estudiantes.com` / Contraseña: `admin123`

### **� Configuración de Grafana**

#### **🔧 Datasource (Prometheus)**
1. Ir a Configuration → Data Sources
2. Agregar Prometheus: `http://prometheus:9090`
3. Guardar y probar conexión

#### **📈 Dashboards Principales**

**Dashboard JVM y Sistema:**
- Uso de memoria (heap/non-heap)
- Threads activos
- CPU usage
- Garbage collection

**Dashboard HTTP:**
- Requests por segundo
- Tiempo de respuesta promedio/percentiles  
- Códigos de estado HTTP
- Endpoints más utilizados

**Dashboard Negocio:**
- Total de estudiantes/materias/notas
- Usuarios activos
- Registros financieros
- Estado de la base de datos

### **🔍 Métricas Clave a Monitorear**

#### **📊 Rendimiento**
```
# Peticiones por segundo
rate(http_requests_total[1m])

# Tiempo de respuesta percentil 95
histogram_quantile(0.95, rate(http_request_duration_bucket[5m]))

# Errores 5xx por minuto  
rate(http_responses_total{status_code=~"5.."}[1m])
```

#### **💾 Recursos**
```
# Uso de memoria
jvm_memory_used_bytes / jvm_memory_max_bytes

# Threads activos
jvm_threads_live_threads

# Conexiones DB activas
hikaricp_connections_active
```

#### **🏢 Negocio**
```
# Crecimiento de estudiantes
increase(estudiantes_total[1d])

# Usuarios activos vs totales
usuarios_activos / usuarios_total

# Notas registradas por hora
rate(notas_total[1h])
```

### **� Alertas Recomendadas**

#### **⚠️ Críticas**
- Aplicación down (health check falla)
- Error rate > 5% en 5 minutos
- Tiempo de respuesta p95 > 2 segundos
- Uso de memoria > 90%

#### **⚡ Advertencias**  
- Tiempo de respuesta p95 > 1 segundo
- Error rate > 1% en 15 minutos
- Uso de memoria > 75%
- Conexiones DB > 80% del pool

### **🔗 Enlaces de Monitoreo en Producción**

> **📝 Nota**: Actualiza con tus herramientas reales de monitoreo

#### **☁️ Servicios Cloud**
- 📊 **New Relic**: `https://newrelic.com/accounts/tu-cuenta`
- 📈 **Datadog**: `https://app.datadoghq.com/dashboard/tu-dashboard`
- 🔍 **Grafana Cloud**: `https://tu-org.grafana.net`

#### **🏠 On-Premise**
- 📊 **Prometheus**: `https://prometheus.tu-dominio.com`
- � **Grafana**: `https://grafana.tu-dominio.com`
- 🚨 **AlertManager**: `https://alerts.tu-dominio.com`

## 🤝 Contribución

### **📝 Cómo Contribuir**
1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

### **📋 Estándares de Código**
- ✅ **Java Code Style**: Google Java Style Guide
- ✅ **Commits**: Conventional Commits
- ✅ **Testing**: JUnit 5 + Mockito
- ✅ **Documentation**: JavaDoc para métodos públicos

### **🐛 Reportar Bugs**
Usa las [GitHub Issues](https://github.com/ErickQuizhpe/estudiantes/issues) para reportar bugs.

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## 👨‍💻 Autor

**Erick Quizhpe**
- GitHub: [@ErickQuizhpe](https://github.com/ErickQuizhpe)
- Email: erick.quizhpe@example.com

## 🙏 Agradecimientos

- Spring Framework Team
- PostgreSQL Community
- OpenAPI Specification Contributors

---

⭐ **¡Si este proyecto te fue útil, dale una estrella!** ⭐
