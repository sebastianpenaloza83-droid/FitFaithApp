# Backend Fit Faith
Para ejecutar este servidor:
1. Ejecuta `npm install` para instalar dependencias.
2. Crea un archivo llamado `.env` basado en tus credenciales de MySQL (puedes guiarte de que usa host, user, password y database='gym_app').
3. Ejecuta `node server.js` para iniciar.

//Datos para iniciar sesion//

Usuario=Yolanda Ramirez	
Correo=yolanda@sena.edu.co	
Contrasena=12345	
Rol=instructor
## Estándares de Codificación Aplicados

Para el desarrollo de este backend, se han implementado las siguientes buenas prácticas y estándares de codificación:

* **Arquitectura Modular:** Separación de responsabilidades lógicas (rutas, controladores y configuración de base de datos) para mantener el código escalable y mantenible.
* **Nomenclatura (Naming Conventions):** Uso estricto de *camelCase* para la declaración de variables y funciones (ej. `registroUsuario`, `conectarDB`).
* **Sintaxis Moderna (ES6+):** Implementación de características modernas de JavaScript como *Arrow Functions*, *Template Literals* y desestructuración de objetos.
* **Manejo de Asincronía:** Uso de promesas y la sintaxis `async/await` para gestionar de forma limpia y legible las consultas a la base de datos y evitar el *callback hell*.
* **Seguridad y Entorno:** Gestión de datos sensibles (credenciales de base de datos y puertos) mediante variables de entorno (`.env`), las cuales están excluidas del control de versiones por seguridad, proveyendo un archivo `.env.example` como plantilla.
* **Control de Versiones:** Uso de Git con mensajes de *commit* claros, descriptivos y en español que reflejan el historial de desarrollo del proyecto.
* **Formato:** Indentación consistente y estructuración del código para facilitar la lectura por parte de otros desarrolladores.