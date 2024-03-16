# Autumn-Book

![portada](https://github.com/Carolina0709/Autumn-Book/assets/71415586/314c80bb-cd84-49c5-8163-493c2ab22c3c)

![Estado del Proyecto](https://img.shields.io/badge/Estado-Terminado-brightgreen)

Este proyecto es el resultado de la ultima actividad del curso Backend con Java de Globant y Egg Corporation propuesto como ejercicio para el tema: SpringWebSecurity


## Descripción

Autumn Book es una plataforma desarrollada en Java y Spring Boot que simula un software de libreria, la cual utiliza una arquitectura MVC con las entidades principales: 

- User: Persona que se registra a la plataforma a través de un email y puede tener dos posibles roles (USER, ADMIN) ademas de algunas otras propiedades como (id, address, name, password, image). 
- Book: Entidad que cuenta con las siguientes propiedades (id, foreword, name, price, author, publisher, user, image y sales). Siendo esta última propiedad utilizada para el ordenamiento de la información. 
- Author y Publisher:  Entidades que cuentan con las siguientes propiedades (id, message, name, sales, user, image). 


## Características

- **Autenticación y Autorización:** Implementa un sistema de autenticación y autorización utilizando Spring Security. Los usuarios tienen diferentes permisos según su rol (USER o ADMIN). Se gestionan las restricciones de acceso a las funcionalidades del sistema.

- **Interfaz de Usuario Amigable:** La interfaz de usuario utiliza Thymeleaf para mostrar características personalizadas según el usuario. Se muestra un mensaje de bienvenida personalizado y se ocultan ciertas opciones según los permisos del usuario.

- **Gestión de Entidades:** Permite crear, leer, actualizar libros, autores y editoriales. Se aplican restricciones de permisos para la creación y edición de entidades, limitando estas acciones a los usuarios con rol de administrador.

- **Gestión de Sesiones:** Se implementa un sistema de login y logout. Se verifica que no se repitan correos electrónicos al registrarse. Las contraseñas se cifran utilizando BCryptPasswordEncoder para mayor seguridad.

- **Gestión de Errores:** Incluye páginas personalizadas para manejar errores 404 (página no encontrada) y accesos no autorizados. Se muestra un mensaje de error específico para cada caso.

- **Dashboard de Usuario:** La pestaña de dashboard muestra la información del usuario y permite editarla. Si el usuario es un administrador, se muestran los libros, autores y editoriales que ha creado.

- **Compra de Libros:** Permite a los usuarios comprar libros, incrementando el contador de ventas tanto para el libro, el autor como la editorial correspondiente.

- **Responsividad:** El sistema es 100% responsivo, utilizando bootstrap y algunas propiedades de CSS como flex y grid.

- **Carpeta multimedia:** En el repositorio se encuentra una carpeta con algunas imagenes de prueba que siguen el estilo y formato del sitio y le dan vida al mismo. 


## Principales Tecnologías Utilizadas

- Backend: Java, Spring Boot, Spring Security (securityWeb), JPA, MySQL.
- Frontend: HTML, CSS (Flexbox y Grid), JavaScript, Bootstrap, Thymeleaf.

## Instalación

1. Clona este repositorio en tu máquina local.
2. Crea la base de datos MySQL con nombre library y actualiza las credenciales en el archivo `application.properties` y las dependencias del archivo  `pom.xml`.
3. Ejecuta la aplicación utilizando Maven o tu IDE preferido.

## Uso

1. Corre tu aplicación en un servidor local.
2. Puedes navegar por el index sin haber creado un usuario pero las funciones serán limitadas.
3. Para desbloquear dichas funciones crear un nuevo usuario e inicia sesión.
4. Puedes cambiar los permisos del usuario a ADMIN dando clic en el boton de edit que se despliega al dar clic en la imagen del perfil que se encuentra en la esquina superior derecha.
5. Una vez actualizados los permisos puedes comenzar a crear y editar libros, autores y editoriales. 

## Personas Desarrolladoras

- [Carolina González Martín](https://github.com/Carolina0709)

## Un vistazo a la interfaz principal

![image](https://github.com/Carolina0709/Autumn-Book/assets/71415586/c628bbcb-98c7-4384-9318-c6f08a100a37)
![image](https://github.com/Carolina0709/Autumn-Book/assets/71415586/0f841619-8622-44c2-ac57-74af7303b94a)
![image](https://github.com/Carolina0709/Autumn-Book/assets/71415586/5f9195a6-806c-4089-9e22-2ffd041e056e)
![image](https://github.com/Carolina0709/Autumn-Book/assets/71415586/aef9dca1-efa2-4b3f-92a5-88c4a467a1e1)
![image](https://github.com/Carolina0709/Autumn-Book/assets/71415586/61a1b457-4063-456d-b907-d881abf36520)
![image](https://github.com/Carolina0709/Autumn-Book/assets/71415586/0f063515-cfd1-4710-81cf-fad497028a05)



