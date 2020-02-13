/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintsFilter;
import org.springframework.stereotype.Service;

/**
 *
 * @author jimmy.chirivi
 */
//@Service
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
