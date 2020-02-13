# Blueprint Management 1


## Part I[](https://eci.gitbook.io/workshops/blueprint-management-1#part-i)

![](https://blobscdn.gitbook.com/v0/b/gitbook-28427.appspot.com/o/assets%2F-LWJN2LirJZqzEmpZ3Gn%2F-LmoBwcRZgykpBDUEYdn%2F-LmoD5BKRb7fFj_aqsS1%2FBluePrint.png?alt=media&token=2f0b638a-15ee-475b-ae09-19cd34321099)

Blueprint Class Diagram

Configure the application to work under a dependency injection scheme, as shown in the previous diagram.

The above requires:

1.  Add the dependencies of Spring. Add the Spring settings. Configure the application - by means of annotations - so that the persistence scheme is injected when the `BlueprintServices` bean is created. Complete the `getBluePrint()` and `getBlueprintsByAuthor()` operations. Implement everything required from the lower layers (for now, the available persistence scheme `InMemoryBlueprintPersistence`) by adding the corresponding tests in `InMemoryPersistenceTest`.

Se completaron los métodos addNewBluePrint(), getAllBlueprints(), getBlueprint() y getBlueprintsByAuthor(), tanto en la clase BlueprintsServices como en la clase InMemoryBlueprintPersistence y también se adicionaron en la interface BlueprintsPersistence.

``` java

public void addNewBlueprint(Blueprint bp) {

	try {

	bpp.saveBlueprint(bp);

	} catch (BlueprintPersistenceException ex) {

	Logger.getLogger(BlueprintsServices.class.getName()).log(Level.SEVERE, null, ex);

}

}

public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException {

	Set<Blueprint> filter = new HashSet<>();

	for (Blueprint blueprint : bpp.getAllBlueprints()) {

	filter.add(bpf.filtering(blueprint));

	}

	return filter;

}

/**

*

* @param author blueprint's author

* @param name blueprint's name

* @return the blueprint of the given name created by the given author

* @throws BlueprintNotFoundException if there is no such blueprint

*/

public Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException {

	System.out.println(bpf.filtering(bpp.getBlueprint(author, name)).getAuthor());

	return bpf.filtering(bpp.getBlueprint(author, name));

}

/**

*

* @param author blueprint's author

* @return all the blueprints of the given author

* @throws BlueprintNotFoundException if the given author doesn't exist

*/

public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {

	Set<Blueprint> filter = new HashSet<>();

	for (Blueprint blueprint : bpp.getBlueprintsByAuthor(author)) {

	filter.add(bpf.filtering(blueprint));

	}

	return filter;

}
```


Y realizamos las respectivas pruebas para probar las funcionalidades implementadas anteriormente.


``` java

@Test

public void deberiaDarElBluePrintSegunAutorYNombre() {

	ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");

	BlueprintsServices ibpp = ac.getBean(BlueprintsServices.class);

	Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};

	Blueprint bp = new Blueprint("sarah", "thearsw", pts);

	ibpp.addNewBlueprint(bp);

	System.out.println("Lo agregue");

	try {

	System.out.println(bp.getPoints().size());

	System.out.println(ibpp.getBlueprint(bp.getAuthor(), bp.getName()).getPoints().size());

	assertTrue(bp.getAuthor().equals(ibpp.getBlueprint(bp.getAuthor(), bp.getName()).getAuthor()));

	assertTrue(bp.getName().equals(ibpp.getBlueprint(bp.getAuthor(), bp.getName()).getName()));

	assertTrue(bp.getPoints().size() == ibpp.getBlueprint(bp.getAuthor(), bp.getName()).getPoints().size());

	} catch (BlueprintNotFoundException ex) {

	Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);

	}

}

@Test

public void deberiaDarTodosLosBlueprintDelAutor() {

	ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");

	BlueprintsServices ibpp = ac.getBean(BlueprintsServices.class);

	Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};

	Blueprint bp = new Blueprint("juan", "thearsw", pts);

	Point[] pts2 = new Point[]{new Point(10, 10), new Point(10, 10)};

	Blueprint bp2 = new Blueprint("juan", "thearep", pts);

	Point[] pts3 = new Point[]{new Point(15, 15), new Point(12, 1)};

	Blueprint bp3 = new Blueprint("juan", "thespti", pts);

	Point[] pts4 = new Point[]{new Point(12, 12), new Point(20, 20)};

	Blueprint bp4 = new Blueprint("armando", "themkt4", pts);

	ibpp.addNewBlueprint(bp);

	ibpp.addNewBlueprint(bp2);

	ibpp.addNewBlueprint(bp3);

	ibpp.addNewBlueprint(bp4);

	try {

	assertTrue(ibpp.getBlueprintsByAuthor("juan").size() == 3);

	assertTrue(ibpp.getBlueprintsByAuthor("armando").size() == 1);

	assertTrue(ibpp.getBlueprintsByAuthor("juan").contains(bp));

	assertTrue(ibpp.getBlueprintsByAuthor("juan").contains(bp2));

	assertTrue(ibpp.getBlueprintsByAuthor("juan").contains(bp3));

	} catch (BlueprintNotFoundException ex) {

	Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);

		}

	}

	@Test

	public void deberiaDarTodosLosBlueprints() {

	try {

	ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");

	BlueprintsServices ibpp = ac.getBean(BlueprintsServices.class);

	Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};

	Blueprint bp = new Blueprint("juan", "thearsw", pts);

	Point[] pts2 = new Point[]{new Point(10, 10), new Point(10, 10)};

	Blueprint bp2 = new Blueprint("juan", "thearep", pts);

	Point[] pts3 = new Point[]{new Point(15, 15), new Point(12, 1)};

	Blueprint bp3 = new Blueprint("juan", "thespti", pts);

	Point[] pts4 = new Point[]{new Point(12, 12), new Point(20, 20)};

	Blueprint bp4 = new Blueprint("armando", "themkt4", pts);

	ibpp.addNewBlueprint(bp);

	ibpp.addNewBlueprint(bp2);

	ibpp.addNewBlueprint(bp3);

	ibpp.addNewBlueprint(bp4);

	assertTrue(ibpp.getAllBlueprints().size() == 5);

	} catch (BlueprintNotFoundException ex) {

	Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);

	}

}
```


    
2.  Make a program in which you create (through Spring) an instance of `BlueprintServices`, and rectify its functionality: register plans, consult plans, register specific plans, etc.

Creamos la clase MainClass() en la cual instaciamos la clase BlueprintServices en donde rectificamos el funcionamiento de las funciones de registrar un Blueprints, consultar Blueprints y consultar Blueprints por autor haciendo uso de Spring.

``` java

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.blueprints.mainProgram;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author sarah.vieda
 */
public class mainClass {

    public static void main(String args[]) throws BlueprintPersistenceException, BlueprintNotFoundException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        BlueprintsServices bps = ac.getBean(BlueprintsServices.class);

        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("juan", "thearsw", pts);
        Point[] pts2 = new Point[]{new Point(10, 10), new Point(10, 10)};
        Blueprint bp2 = new Blueprint("juan", "thearep", pts);
        Point[] pts3 = new Point[]{new Point(15, 15), new Point(12, 1)};
        Blueprint bp3 = new Blueprint("juan", "thespti", pts);
        Point[] pts4 = new Point[]{new Point(12, 12), new Point(20, 20)};
        Blueprint bp4 = new Blueprint("armando", "themkt4", pts);
        Point[] pts5 = new Point[]{new Point(12, 12), new Point(20, 20)};
        Blueprint bp5 = new Blueprint("saris", "thearsw", pts);

        bps.addNewBlueprint(bp);
        bps.addNewBlueprint(bp2);
        bps.addNewBlueprint(bp3);
        bps.addNewBlueprint(bp4);
        bps.addNewBlueprint(bp5);
        System.out.println(bps.getBlueprint("armando", "themkt4"));
        System.out.println(bps.getBlueprintsByAuthor("juan"));
        System.out.println(bps.getAllBlueprints());
        System.out.println(bps.getBlueprintsByAuthor("saris"));
        //Deberia imprimir null ya que la autora saris no tiene una pintura llamada themkt4
        System.out.println(bps.getBlueprint("saris", "themkt4"));

    }

}
```

    
3.  You want the plan query operations to perform a filtering process, before returning the planes consulted. These filters are looking to reduce the size of the plans, removing redundant data or simply sub-sampling, before returning them. Adjust the application (adding the abstractions and implementations you consider) so that the BlueprintServices class is injected with one of two possible 'filters' (or possible future filters). The use of more than one at a time is not contemplated:
    
    1.  (A) Redundancy filtering: deletes consecutive points from the plane that are repeated.
    
    ``` java
    @Service
	public class BlueprintsRedundancy implements BlueprintsFilter {

	    @Override
	    public Blueprint filtering(Blueprint blueprint) {
		Point[] points = new Point[blueprint.getPoints().size()];
		int cont = 0;   
		for(int i=0;i<blueprint.getPoints().size();i++){
		    if(i+1 < blueprint.getPoints().size()){
			if(blueprint.getPoints().get(i).getX()==blueprint.getPoints().get(i+1).getX()){
			    if(blueprint.getPoints().get(i).getY()==blueprint.getPoints().get(i+1).getY()){
				i++;
			    }
			}
			points[cont] = blueprint.getPoints().get(i);
			cont++;
		    }
		}
		Blueprint bp = new Blueprint(blueprint.getAuthor(), blueprint.getName(), points);
		return bp;
	    }
	}
    ```
    
    2.  (B) Subsampling filtering: suppresses 1 out of every 2 points in the plane, interspersed.
    
    ``` java
    @Service
	public class BlueprintsSubsampling implements BlueprintsFilter {
	    @Override
	    public Blueprint filtering(Blueprint blueprint) {
		Point[] points = new Point[blueprint.getPoints().size()];
		int cont = 0;
		for(int i=0;i<blueprint.getPoints().size();i++){
		    if(i+2<=blueprint.getPoints().size()){
			if(i%2==0){
			    points[cont] = blueprint.getPoints().get(i);
			    cont++;
			}
		    }else{
			points[cont] = blueprint.getPoints().get(i);
			cont++;
		    }
		}
		Blueprint bp = new Blueprint(blueprint.getAuthor(), blueprint.getName(), points);
		return bp;
	    } 
	}
    ```
        
    
4.  Add the corresponding tests to each of these filters, and test its operation in the test program, verifying that only by changing the position of the annotations - without changing anything else - the program returns the filtered planes in the way (A) or in the way (B).

	1. Redundancy Filter Test
	
	``` java
	    @Test
	    public void deberiaFiltrarRedundancy() {

		try {
		    ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		    BlueprintsServices ibpp = ac.getBean(BlueprintsServices.class);
		    Point[] pts = new Point[]{new Point(10, 0), new Point(10, 0)};
		    Blueprint bp = new Blueprint("juan", "thearsw", pts);
		    ibpp.addNewBlueprint(bp);
		    int ans=0;
		    for(int i=0;i<ibpp.getBlueprint("juan", "thearsw").getPoints().size();i++){
			assertTrue(bp.getPoints().size() == ibpp.getBlueprint("juan", "thearsw").getPoints().size());
			if(ibpp.getBlueprint("juan", "thearsw").getPoints().get(i)!= null){
			    assertTrue(bp.getPoints().get(i).getX() == ibpp.getBlueprint("juan", "thearsw").getPoints().get(i).getX());
			    assertTrue(bp.getPoints().get(i).getY() == ibpp.getBlueprint("juan", "thearsw").getPoints().get(i).getY());
			    ans++;
			}             
		    }
		    assertFalse(ans == bp.getPoints().size());
		} catch (BlueprintNotFoundException ex) {
		    Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
		}
	    }
	```
	
	2. Subsampling Filter Test
	
	``` java
	    @Test
	    public void deberiFiltrarSubSampling(){
		try {
		    ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		    BlueprintsServices ibpp = ac.getBean(BlueprintsServices.class);
		    Point[] pts = new Point[]{new Point(10, 0), new Point(10, 0),new Point(10, 30),new Point(30, 10)};
		    Blueprint bp = new Blueprint("camila", "thearsw", pts);
		    ibpp.addNewBlueprint(bp);
		    int ans=0;
		    for(int i=0;i<ibpp.getBlueprint("camila", "thearsw").getPoints().size();i++){
			if(ibpp.getBlueprint("camila", "thearsw").getPoints().get(i)!= null){
			    ans++;
			}             
		    }
		    assertTrue(ans == 3);
		    assertEquals(ibpp.getBlueprint("camila", "thearsw").getPoints().get(1).getY(),30);
		    assertEquals(ibpp.getBlueprint("camila", "thearsw").getPoints().get(2).getX(),30);
		} catch (BlueprintNotFoundException ex) {
		    Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
		}
	    }
    ```
