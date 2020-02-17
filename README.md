# Blueprint Management 2

# Integrantes
- Sarah Camila Vieda
- Juan David Navarro
- Jimmy Armando Chirivi 

## PART I 
- Integrate to the base project supplied the Beans developed in the previous exercise. Just copy the classes, NOT the configuration files. Rectify that the dependency injection scheme is correctly configured with the `@Service` and `@Autowired` annotations.

	Realizamos la integración de nuestro laboratorio No. 3 con el laboratorio No. 4, para esto tuvimos que realizar algunos cambios 	en el POM. 

``` java
	<?xml version="1.0" encoding="UTF-8"?>
	<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
		<modelVersion>4.0.0</modelVersion>
		<groupId>edu.eci.pdsw.examples</groupId>
		<artifactId>blueprints-api</artifactId>
		<version>0.0.1-SNAPSHOT</version>
		<packaging>jar</packaging>

		<name>Blueprints_API</name>
		<description>Demo project for Spring Boot</description>

		<parent>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-parent</artifactId>
			<version>1.4.1.RELEASE</version>
			<relativePath/> <!-- lookup parent from repository -->
		</parent>

		<properties>
			<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
			<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
			<java.version>1.8</java.version>
		</properties>

		<dependencies>
			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-web</artifactId>
			</dependency>
			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-aop</artifactId>
			</dependency>

			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-test</artifactId>
				<scope>test</scope>
			</dependency>
		</dependencies>

		<build>
			<plugins>
				<plugin>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-maven-plugin</artifactId>
				</plugin>
			</plugins>
		</build>
	</project>
```

- Modify the persistence bean InMemoryBlueprintPersistence so that by default it is initialized with at least three other planes, and with two associated with the same author.

	Creamos en el constructor de nuestra clase InMemoryBlueprintPersistence 4 planos en donde 3 de ellos estan asociados al mismo 		autor "sarah".
	
	``` java
	public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts=new Point[]{new Point(140, 140),new Point(115, 123)};
        Blueprint bp=new Blueprint("sarah", "thearsw",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        Point[] pts1=new Point[]{new Point(100, 100),new Point(123, 215)};
        Blueprint bp1=new Blueprint("sarah", "thespti",pts1);
        blueprints.put(new Tuple<>(bp1.getAuthor(),bp1.getName()), bp1);
        Point[] pts2=new Point[]{new Point(100, 120),new Point(20, 32)};
        Blueprint bp2=new Blueprint("sarah", "themkt4",pts2);
        blueprints.put(new Tuple<>(bp2.getAuthor(),bp2.getName()), bp2);
        Point[] pts3=new Point[]{new Point(140, 140),new Point(120, 23)};
        Blueprint bp3=new Blueprint("jimmy", "thearep",pts3);
        blueprints.put(new Tuple<>(bp3.getAuthor(),bp3.getName()), bp3);
    	}
   	
	```

- Configure your application to offer the resource /blueprints, so that when a GET request is made, return in JSON format - all the drawings. For this:
	- Modify the BlueprintAPIController class taking into account the following example of a REST controller 	made with SpringMVC/SpringBoot
	
	Se introdujeron las anotaciones @RestController el cual sirve para simplificar la creación de servicios 	web RESTful y la anotación @RequestMapping para asignar solicitudes web a los métodos de Spring 		Controller. 
	
	- Have the BlueprintServices type bean injected into this class (which, in turn, will be injected with 		its persistence and point filtering dependencies).
	
	Para realizar inyección de dependencias utilizamos @Autowired, esta anotación permite al Spring resolver 	 e inyectar beans de colaboración en su bean. 
	
	
	
	
``` java 

	@RestController
	@RequestMapping(value = "/blueprints")
	public class BlueprintAPIController {

	    @Autowired
	    BlueprintsServices bps;

	    @RequestMapping(method = RequestMethod.GET)
	    public ResponseEntity<?> manejadorGetRecursoAll() {
		try {
		    Set<Blueprint> blueprints = bps.getAllBlueprints();
		    return new ResponseEntity<>(blueprints,HttpStatus.ACCEPTED);
		} catch (BlueprintNotFoundException ex) {
		    Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
		    return new ResponseEntity<>("Error bla bla bla",HttpStatus.NOT_FOUND);
		}
	    }
	    @RequestMapping(method = RequestMethod.GET , path = "{author}")
	    public ResponseEntity<?> manejadorGetRecursoAuthor(@PathVariable("author") String author) {
		ResponseEntity ans;
		try {
		    Set<Blueprint> blueprints = bps.getBlueprintsByAuthor(author);
		    if(blueprints.size()==0){
			ans = new ResponseEntity<>("Error 404",HttpStatus.NOT_FOUND);
		    }else{
			ans = new ResponseEntity<>(blueprints,HttpStatus.ACCEPTED);
		    }        
		} catch (BlueprintNotFoundException ex) {
		    Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
		    ans = new ResponseEntity<>("Error bla bla bla",HttpStatus.NOT_FOUND.NOT_FOUND);
		}
		return ans;
	    }
```

- Verify the operation of the application by launching the application with maven. And then sending a GET request to: http://localhost:8080/blueprints. Rectify that, in response, a JSON object is obtained with a list containing the detail of the drawings provided by default, and that the corresponding point filtering has been applied.
	
	Para correr nuestra aplicación realizamos lo siguiente:
	1. mvn package
	2. mvn spring-boot:run
	
	Se creo el metodo manejadorGetRecursoAll() que realiza una petición sobre todos los blueprints.
	
	``` java 
	    @RequestMapping(method = RequestMethod.GET)
	    public ResponseEntity<?> manejadorGetRecursoAll() {
		try {
		    Set<Blueprint> blueprints = bps.getAllBlueprints();
		    return new ResponseEntity<>(blueprints,HttpStatus.ACCEPTED);
		} catch (BlueprintNotFoundException ex) {
		    Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
		    return new ResponseEntity<>("Error bla bla bla",HttpStatus.NOT_FOUND);
		}
	    }	
	```
	
	![Capture1](https://user-images.githubusercontent.com/44879884/74444976-a4a0d300-4e43-11ea-838d-f5b8e4c30d77.PNG)

- Modify the controller so that it now accepts GET requests to the resource /blueprints/{author}, which returns using a JSON representation all the plans made by the author whose name is {author}. If there is no such author, you must respond with the HTTP error code 404. For this, review in the Spring documentation, section 22.3.2, the use of @PathVariable. Again, verify that when making a GET request -for example- to the resource http://localhost:8080/blueprints/juan, the set of planes associated with the author 'juan' is obtained in JSON format (adjust this to the names of author used in point 2).

	Se creo el metodo manejadorGetRecursoAuthor() que realiza una petición sobre todos los blueprints de un autor específico.
	
	``` java 
	    @RequestMapping(method = RequestMethod.GET , path = "{author}")
	    public ResponseEntity<?> manejadorGetRecursoAuthor(@PathVariable("author") String author) {
		ResponseEntity ans;
		try {
		    Set<Blueprint> blueprints = bps.getBlueprintsByAuthor(author);
		    ans = new ResponseEntity<>(blueprints,HttpStatus.ACCEPTED);      
		} catch (BlueprintNotFoundException ex) {
		    Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
		    ans = new ResponseEntity<>("Error 404",HttpStatus.NOT_FOUND.NOT_FOUND);
		}
		return ans;
	    }
	```
	
	![Capture2](https://user-images.githubusercontent.com/44879884/74444968-a4083c80-4e43-11ea-9882-9a9288ca8d2a.PNG)	
	![Capture3](https://user-images.githubusercontent.com/44879884/74444972-a4083c80-4e43-11ea-82aa-c502c2bfe6e9.PNG)
	![Capture4](https://user-images.githubusercontent.com/44879884/74444975-a4a0d300-4e43-11ea-9d12-df45e717bfb1.PNG)
	
- Modify the controller so that it now accepts GET requests to the resource/blueprints/{author}/{bpname}, which returns using a JSON representation only ONE plane, in this case the one made by {author} and whose name is {bpname}. Again, if there is no such author, you must respond with the HTTP 404 error code.
	
	Se creo el metodo manejadorGetRecursoAuthorAndBpName() que realiza una petición sobre un blueprint específico de un autor.
	
	``` java 
	    @RequestMapping(method = RequestMethod.GET , path = "{author}/{name}")
	    public ResponseEntity<?> manejadorGetRecursoAuthorAndBpName(@PathVariable("author") String author, @PathVariable("name") String bpname) {
		ResponseEntity ans;
		try {
		    Blueprint bp = bps.getBlueprint(author, bpname);
		    ans = new ResponseEntity<>(bp,HttpStatus.ACCEPTED);        
		} catch (BlueprintNotFoundException ex) {
		    Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
		    ans = new ResponseEntity<>("Error bla bla bla",HttpStatus.NOT_FOUND.NOT_FOUND);
		}
		return ans;
	    }
	```
	
	![Capture5](https://user-images.githubusercontent.com/44879884/74445781-f26a0b00-4e44-11ea-857d-f8f634921ce6.PNG)

## PART II

- Add the handling of POST requests (creation of new plans), so that an http client can register a new order by making a POST request to the resource planes, and sending as content of the request all the detail of said resource through a JSON document. For this, consider the following example, which considers - by consistency with the HTTP protocol - the handling of HTTP status codes (in case of success or error):

	Se creo el metodo manejadorPostAgregaUnNuevoBlueprint() que agrega un nuevo Blueprint.
	
	``` java 
	    @RequestMapping(method = RequestMethod.POST)
	    public ResponseEntity<Blueprint> manejadorPostAgregaUnNuevoBlueprint(@RequestBody Blueprint bp) {
		bps.addNewBlueprint(bp);
		return new ResponseEntity<>(HttpStatus.CREATED);
	    }
	```

- To test that the planes resource correctly accepts and interprets POST requests, use the Unix curl command. This command has as a parameter the type of content handled (in this case JSON), and the message body that will go with the request, which in this case must be a JSON document equivalent to the Client class (where instead of {JSON Object}, a JSON object corresponding to a new order will be used.

	A continuación utilizaremos el comando curl -i -X POST y agregaremos un documento JSON el cual contiene un nuevo Blueprint llamado "themkt4.0" con su autor "Juan" y sus respectivos puntos. 
	
	``` java
	curl -i -X POST -HContent-Type:application/json -HAccept:application/json http://localhost:8080/blueprints -d "{"""author""":"""juan""","""points""":[{"""x""":10,"""y""":10},{"""x""":15,"""y""":0}],"""name""":"""themkt4"""}"
	```
- With the above, register a new plane (to 'design' a JSON object, you can use this tool). It can be based on the JSON format shown in the browser when consulting an order with the GET method.
	
	Al ejecutar el comando anterior se crea un nuevo blueprint con los atributos que se le dieron anteriormente y al realizar la consulta de todos los blueprints, nos da como resultado todos los anteriores junto con el nuevo que se creo de MKT4.0.
	
	![add](https://user-images.githubusercontent.com/48154086/74679720-a264ae80-518c-11ea-9429-6c6de29e8e88.PNG)
	
- Taking into account the author and name of the registered plan, verify that it can be obtained through a GET request to the corresponding resource /blueprints/{author}/{bpname}.

	![Capture](https://user-images.githubusercontent.com/44879884/74683844-24a6a000-5198-11ea-9e72-cabd093e73d7.PNG)
	

- Add support to the PUT verb for resources of the form /blueprints/{author}/{bpname}, so that it is possible to update a specific plane.

## PART III
