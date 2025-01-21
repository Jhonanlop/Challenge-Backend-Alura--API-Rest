# Challenge-Backend-Alura--API-Rest

Descripción del Proyecto

Este proyecto es un sistema de gestión de usuarios desarrollado como parte del reto para la certificación Back-End en Alura Latam. El sistema implementa la autenticación basada en tokens JWT (JSON Web Tokens) para proteger los endpoints y asegurar el acceso a recursos.


El sistema permite:

Registrar nuevos usuarios.

Autenticar usuarios y generar un token JWT.

Proteger recursos mediante validación del token.



TECNOLOGIAS UTILIZADAS

Java 17: Lenguaje principal.

Spring Boot 3.0.0: Framework para el desarrollo del proyecto.

Hibernate JPA: Para la persistencia de datos.

JWT (JSON Web Tokens): Para la autenticación.

MySQL: Base de datos utilizada.

Maven: Gestión de dependencias y construcción del proyecto.


CONFIGURACION DEL PROYECTO


Dependencias Principales

Asegúrate de que el archivo pom.xml contiene las siguientes dependencias:


<dependencies>
    <!-- Spring Boot -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- MySQL Driver -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- JWT -->
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.11.5</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.11.5</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.11.5</version>
    </dependency>

    <!-- DevTools (opcional) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>


CONFIGURACION DEL ARCHIVO application.properties

# Configuración del servidor
server.port=8080

# Configuración de la base de datos
spring.datasource.url=jdbc:mysql://localhost:3306/nombre_base_datos
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update


ENDPOINTS PRINCIPALES

Autenticación

POST /auth/login: Autentica un usuario y devuelve un token JWT.

Request Body:

{
    "username": "usuario",
    "password": "contraseña"
}

Response:

{
    "token": "<JWT_TOKEN>"
}

Gestión de Usuarios

POST /users/register: Registra un nuevo usuario.

Request Body:

{
    "username": "nuevo_usuario",
    "password": "contraseña",
    "role": "USER"
}

GET /users: Retorna la lista de usuarios (protegido con JWT).

Headers:

Authorization: Bearer <JWT_TOKEN>

Instrucciones para Probar con Insomnia o Postman

Registrar un Usuario:

Endpoint: POST /users/register

Body:

{
    "username": "usuario",
    "password": "contraseña",
    "role": "USER"
}

Autenticar Usuario:

Endpoint: POST /auth/login

Body:

{
    "username": "usuario",
    "password": "contraseña"
}

Recibirás un token JWT como respuesta.


Acceder a Recursos Protegidos:

Agrega el token en el header de autorización:

Authorization: Bearer <JWT_TOKEN>


COMO EJECUTAR EL PROYECTO


Clona este repositorio:

git clone https://github.com/tu-usuario/tu-repositorio.git

Compila y ejecuta el proyecto:

mvn spring-boot:run

Accede a los endpoints usando herramientas como Postman o Insomnia.


CONTRIBUCIONES

Este proyecto está abierto a mejoras. Si deseas contribuir, por favor abre un issue o envía un pull request.


LICENCIA

Este proyecto se encuentra bajo la licencia MIT. Consulta el archivo LICENSE para más detalles.
