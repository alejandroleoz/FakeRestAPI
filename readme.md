# UTN - Diseño de Sistemas

#### Ejemplo de API REST para pruebas.  
Se expone un servicio que permite hacer CRUD de entidad `Persona` mediante requests HTTP con el respectivo método y payload.
El payload soportado es en formato JSON.  
Ejemplo de serialización de una `Persona`:  
``
{
"dni": 12345678,
"nombre": "Leandro",
"apellido": "Rodriguez,
"edad": 23
}
``

#### Métodos soportados:
* GET: obtener información de una persona o todas las personas
* POST: crear una nueva `Persona`
* PUT: actualizar la información de una `Persona`
* DELETE: eliminar una `Persona`

#### Para ejecutar el servidor de ejemplo:
* Verificar que el puerto 8080 no esté en uso
* Ejecutar `mvn clean spring-boot:run ` desde la terminal
* El servidor estará disponible en `http://localhost:8080`

#### Ejemplos  de requests
* Obtener información de todas las personas:  
`GET http://localhost:8080/Persona/` 

* Obtener información de una persona en particular (por DNI)  
`GET http://localhost:8080/Persona/12345678` 

* Dar de alta una persona  
`POST http://localhost:8080/Persona/` con payload

* Actualizar la información de una persona existente  
`PUT http://localhost:8080/Persona/` con payload 

* Eliminar una persona  
`DELETE http://localhost:8080/Persona/12345678`
