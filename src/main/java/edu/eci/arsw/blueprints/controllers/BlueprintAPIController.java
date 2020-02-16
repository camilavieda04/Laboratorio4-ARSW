/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hcadavid
 */
@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {

    @Autowired
    BlueprintsServices bps;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Set<Blueprint>> manejadorGetRecursoAll() {
        ResponseEntity ans;
        try {
            Set<Blueprint> blueprints = bps.getAllBlueprints();
            ans = new ResponseEntity<>(blueprints, HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            ans = new ResponseEntity<>("Error bla bla bla", HttpStatus.NOT_FOUND);
        }
        return ans;
    }

    @RequestMapping(method = RequestMethod.GET, path = "{author}")
    public ResponseEntity<Set<Blueprint>> manejadorGetRecursoAuthor(@PathVariable("author") String author) {
        ResponseEntity ans;
        try {
            Set<Blueprint> blueprints = bps.getBlueprintsByAuthor(author);
            if (blueprints.isEmpty()) {
                ans = new ResponseEntity<>("Error 404", HttpStatus.NOT_FOUND);
            } else {
                ans = new ResponseEntity<>(blueprints, HttpStatus.ACCEPTED);
            }
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            ans = new ResponseEntity<>("Error 404", HttpStatus.NOT_FOUND.NOT_FOUND);
        }
        return ans;
    }

    @RequestMapping(method = RequestMethod.GET, path = "{author}/{name}")
    public ResponseEntity<Blueprint> manejadorGetRecursoAuthorAndBpName(@PathVariable("author") String author, @PathVariable("name") String bpname) {
        ResponseEntity ans;
        try {
            Blueprint bp = bps.getBlueprint(author, bpname);
            ans = new ResponseEntity<>(bp, HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            ans = new ResponseEntity<>("Error 404", HttpStatus.NOT_FOUND.NOT_FOUND);
        }
        return ans;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Blueprint> newBlueprint(@RequestBody Blueprint bp) {
        bps.addNewBlueprint(bp);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @RequestMapping(method = RequestMethod.PUT, path = "{author}/{name}")
    public ResponseEntity<?> newBlueprint(@PathVariable("author") String author, @PathVariable("name") String bpname) {
        
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
