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
