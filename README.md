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
