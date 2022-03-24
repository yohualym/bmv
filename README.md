# BMV - Desafío

> API REST que valida la fortaleza de un password.

### Estructura de un password válido.

- Longitud mínima de 8 y máxima de 12 caracteres.
- Debe contener al menos 2 caracteres especiales ($%&#”@)
- Debe contener al menos 3 números.
- Debe tener al menos una mayúscula.
- Debe contemplar el carácter ñ.

### Diseño

![Diseño](https://raw.githubusercontent.com/yohualym/bmv/master/disenio.png "Diseño.")

### Requerimientos
- **JDK 1.7**
- **Maven 3.6.3**
- **SonarQube 4.5.7**
- **Wildfly 9.0.2.Final** (No es necesario tenerlo instalado, se descarga como parte del  plugin wildfly-dist de maven)
- **Curl**, **Postman** o cualquier cliente para realizar peticiones RESTful.

### Estructura de directorios

    .
    ├── bmv-services-interface # Mantiene las interface expuesta por el EJB  y consumida por el servicios REST.
    ├── bmv-services           # Contiene la implementación del EJB que solo delega la regla de negocio a un bean de spring.
    ├── bmv-web                # Expone el servicio REST que consumen los clientes.
    └── bmv-ear			    # Construye el archive ear que contiene nuestra aplicación.
	
### Compilar

Desde la carpeta raíz ejecutar:
   
```
mvn clean install
```
   Este comando descarga las dependencias del proyecto, compila las clases y genera los artefactos del proyecto.

El proyecto cuenta con 3 profiles

- **local** (default): Compilación y ejecución de la aplicación en un ambiente local.
- **test**: Ejecuta las pruebas de la aplicación.
- **dev**: Compilación y ejecución de la aplicación en un ambiente de desarrollo.
- **prod**: Compilación y ejecución de la aplicación en un ambiente de producción.

### Calidad del código con [PMD](https://pmd.github.io/)
Desde la carpeta raíz ejecutar:
   
```
mvn pmd:check
```
Este comando realiza un análisis estático de código en busca de defectos de programación tales como imports sin utilizar, variables no utilizadas, etc. En caso de encontrar algún error detiene la compilación y muestra la falla.

Se utiliza la configuración por default de [PMD](https://pmd.github.io/) para java.

### Calidad del código con [SonarQube](https://www.sonarqube.org/)

#### Requiere:
- SonarQube en ejecución en el puerto 9000.
- La ejecución de pruebas previo a ejecutar este comando.


Desde la carpeta raíz ejecutar:
   
```
mvn sonar:sonar
```
Este comando realiza un análisis estático de código en busca de defectos de programación y el cumplimiento de buenas prácticas así como posibles bugs en el código entre otros.

### Ejecución de pruebas

Desde la carpeta raíz ejecutar:
   
```
mvn clean install -Ptest
```

Este comando ejecuta las pruebas de todos los proyectos. 

Como ejemplo se crearon las pruebas de unidad:

- **PasswordManagerServiceUTest**: Valida la regla de negocio aplicable a los passwords.
- **EJBPasswordManagerUTest**: Muestra las pruebas haciendo uso de mocks.

Las pruebas se construyeron con [TestNG](https://testng.org/doc/) y [Mockito](https://site.mockito.org/)

La ejecución de pruebas genera un reporte de cobertura (Generado con [Jacoco](https://www.eclemma.org/jacoco/) ) el cual se encuentra en:
```
.\bmv-services\target\site\jacoco\index.html
```
### Documentación ([JavaDoc](https://docs.oracle.com/javase/8/docs/technotes/tools/windows/javadoc.html))
Desde la carpeta raíz ejecutar:
   
```
mvn javadoc:javadoc
```

La documentación se genera a partir de las anotaciones en las clases haciendo uso de javadoc.

```
.\bmv-services\target\site\apidocs\index.html
```

```
.\bmv-web\target\site\apidocs\index.html
```


### Ejecución
Desde la carpeta raíz ejecutar:
   
```
mvn -pl bmv-ear wildfly:run
```


### Pruebas

- Generar password:   

curl http://localhost:8080/bmv-web/api/password?longitud=12


- Validar password:   

curl http://localhost:8080/bmv-web/api/password -X POST -H "Content-Type: application/json" -d "{\"password\":\"F2M%q9#VUj8h\"}"



### Despliegue a producción 

```
mvn clean install -Pprod
```