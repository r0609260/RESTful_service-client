/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usd.csci.manufacturerestservice.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.usd.csci.manufacturerestservice.data.ManufacturerEntity;

/**
 *
 * @author ASUS
 */
@Stateless
@Path("manufacturerentity")
public class ManufacturerEntityFacadeREST extends AbstractFacade<ManufacturerEntity> {

    @PersistenceContext(unitName = "ManufactureRESTServicePU")
    private EntityManager em;

    public ManufacturerEntityFacadeREST() {
        super(ManufacturerEntity.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(ManufacturerEntity entity) {
        ManufacturerEntity locManufacturer = findByName(entity.getName());
        if(locManufacturer != null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, ManufacturerEntity entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON})
    public ManufacturerEntity find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({ MediaType.APPLICATION_JSON})
    public List<ManufacturerEntity> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<ManufacturerEntity> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    private ManufacturerEntity findByName(String name){
        if(name == null){
            return null;
        }
        try{
            TypedQuery<ManufacturerEntity> query = em.createNamedQuery("ManufacturerEntity.findByName",ManufacturerEntity.class);
            query.setParameter("name",name);
            return query.getSingleResult();
        }catch(Exception e){
            return null;
        }
    }
}
