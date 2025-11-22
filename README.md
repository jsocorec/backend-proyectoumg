# Solución de Gestión de Clientes y Visitas - SkyNet S.A.

## Descripción del Proyecto

Este proyecto es una solución informática integral diseñada para la empresa SkyNet S.A., enfocada en optimizar la gestión de clientes, el registro de oportunidades de negocio y el control de visitas técnicas. Desarrollado con una arquitectura moderna basada en microservicios, la aplicación incluye un dashboard interactivo que proporciona información en tiempo real para diferentes roles de usuario (Administrador, Supervisor y Técnico).

La solución aborda desafíos como la ineficiencia en procesos manuales, cuellos de botella en la toma de decisiones y problemas de seguridad e integridad de la información, proporcionando herramientas para mejorar la planificación, ejecución y seguimiento de las visitas técnicas.

## Tecnologías Utilizadas

*   **Backend:** Java 17, Spring Boot 3.x, Spring Data JPA, Spring Security (JWT), MySQL, Maven.
*   **Frontend:** HTML5, CSS3 (Bootstrap 5), JavaScript.
*   **Base de Datos:** MySQL (gestionado en AWS RDS o similar).
*   **Servicios:** Google Maps API (Geoposicionamiento), Email Service (SMTP).
*   **Despliegue Backend:** AWS Elastic Beanstalk.
*   **Despliegue Frontend (local para presentación):** XAMPP (Apache).
*   **Control de Versiones:** Git, GitHub.

## Funcionalidades Clave

*   **Autenticación y Autorización por Roles:**
    *   Login seguro con JWT.
    *   Roles: Administrador, Supervisor, Técnico.
*   **Módulo de Usuarios:**
    *   CRUD completo de usuarios, con asignación de roles.
*   **Módulo de Clientes:**
    *   CRUD completo de clientes.
    *   Registro de coordenadas geográficas mediante integración con Google Maps.
*   **Módulo de Visitas:**
    *   Planificación y asignación de visitas a técnicos.
    *   Registro de ingreso y egreso (check-in/check-out) con geolocalización.
    *   Generación y envío de informes de visita al cliente en formato PDF.
    *   Visualización de rutas en Google Maps para los técnicos.
*   **Dashboard Interactivo:**
    *   Vistas personalizadas según el rol (Administrador, Supervisor, Técnico).
    *   Muestra de métricas clave y listado de visitas relevantes.
*   **Módulo de Configuraciones:**
    *   Gestión de parámetros del sistema por parte del administrador (ej. API Key de Google Maps, credenciales de email).

## Arquitectura de Despliegue (Para Presentación)

*   **Backend (Microservicios):** Desplegado en AWS Elastic Beanstalk, accesible vía HTTP.
*   **Frontend (Aplicación Web):** Se ejecutará localmente utilizando XAMPP (Apache) para consumir el backend HTTP, evitando conflictos de "Mixed Content" durante la evaluación.

## Prerrequisitos

Asegúrate de tener instalado lo siguiente en tu entorno local:

*   **Java Development Kit (JDK) 17 o superior**
*   **Apache Maven 3.x**
*   **XAMPP** (o cualquier servidor web local compatible con HTML/JS)
*   **Git**

## Configuración y Ejecución del Proyecto

### 1. Clonar los Repositorios

Clona ambos repositorios de GitHub en tu máquina local:

```bash
git clone https://github.com/jsocorec/backend-proyectoumg.git
git clone https://github.com/jsocorec/frotnend-proyectoumg.git

2. Configuración y Despliegue del Backend (Spring Boot)
El backend de Spring Boot ya está desplegado en AWS Elastic Beanstalk. Para efectos de la presentación, se utilizará esta instancia desplegada.
URL de la API desplegada en AWS:
http://ms-clientes-visitas-env2.eba-ie2e5n2t.us-east-1.elasticbeanstalk.com
Notas sobre el despliegue en AWS:
La aplicación backend se ha configurado para escuchar peticiones HTTP.
La base de datos MySQL se encuentra configurada y gestionada dentro del entorno de AWS.
Las configuraciones de Google Maps API Key y las credenciales de email SMTP se gestionan a través del módulo de configuraciones del sistema en la base de datos.
(Opcional) Ejecución local del Backend (solo para desarrollo/testing):
Asegúrate de tener una base de datos MySQL local configurada y actualiza src/main/resources/application.properties con tus credenciales.
Navega a la carpeta backend-proyectoumg.
Compila el proyecto: mvn clean install
Ejecuta la aplicación: mvn spring-boot:run (o desde tu IDE, como IntelliJ IDEA).
El backend estará disponible en http://localhost:8080 (o el puerto configurado).
3. Configuración y Ejecución del Frontend (HTML/JS con XAMPP)
El frontend se ejecutará localmente utilizando Apache de XAMPP para consumir el backend desplegado en AWS.
Copiar el Frontend a XAMPP:
Navega a la carpeta web-frontend que clonaste.
Copia la carpeta completa web-frontend (o solo su contenido si lo prefieres) dentro del directorio htdocs de tu instalación de XAMPP (ej. C:\xampp\htdocs\web-frontend).
Iniciar Apache en XAMPP:
Abre el panel de control de XAMPP e inicia el servicio de Apache.
Acceder al Frontend:
Abre tu navegador web.
Ingresa la siguiente URL para acceder a la página de inicio de sesión: http://localhost/web-frontend
Credenciales de Acceso (Para Pruebas)
Utiliza las siguientes credenciales para probar la aplicación con diferentes roles:
Rol
Usuario
Contraseña
Administrador
admin
admin
Supervisor
super
super
Técnico
tecnico
tecnico



Contacto
Para cualquier consulta o duda sobre el proyecto, puedes contactarme en [jsocorecf@miumg.edu.gt].
