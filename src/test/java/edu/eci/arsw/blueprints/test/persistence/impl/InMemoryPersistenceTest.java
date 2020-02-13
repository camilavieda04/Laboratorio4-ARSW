/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author hcadavid
 */
public class InMemoryPersistenceTest {

    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Point[] pts0 = new Point[]{new Point(40, 40), new Point(15, 15)};
        Blueprint bp0 = new Blueprint("mack", "mypaint", pts0);

        ibpp.saveBlueprint(bp0);

        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint", pts);

        ibpp.saveBlueprint(bp);

        assertNotNull("Loading a previously stored blueprint returned null.", ibpp.getBlueprint(bp.getAuthor(), bp.getName()));

        assertEquals("Loading a previously stored blueprint returned a different blueprint.", ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);

    }

    @Test
    public void saveExistingBpTest() {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint", pts);

        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }

        Point[] pts2 = new Point[]{new Point(10, 10), new Point(20, 20)};
        Blueprint bp2 = new Blueprint("john", "thepaint", pts2);

        try {
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and autor");
        } catch (BlueprintPersistenceException ex) {

        }

    }

    @Test
    public void deberiaDarElBluePrintSegunAutorYNombre() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        BlueprintsServices ibpp = ac.getBean(BlueprintsServices.class);
        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("sarah", "thearsw", pts);
        ibpp.addNewBlueprint(bp);
        try {
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
    
    //@Test
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
}
