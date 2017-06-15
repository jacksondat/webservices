package com.jariast.webservices.rest.v1.endpoints;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

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

import com.jariast.webservices.rest.v1.model.Entity;

@Path("/v1/entities")
public class EntityEndpoint {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Entity> getEntities() {

		List<Entity> entities = new ArrayList<Entity>();
		Entity entityAux = new Entity();
		
		entityAux.setId(1);
		entityAux.setName("entity1");
		entities.add(entityAux);
		
		entityAux = new Entity();
		entityAux.setId(2);
		entityAux.setName("entity2");
		entities.add(entityAux);

		return entities;

	}
	
	@GET
	@Path("{id: \\d+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Entity getEntityById(@PathParam("id") int id ) {

		Entity entityAux = new Entity();
		
		entityAux.setId(id);
		entityAux.setName("entity"+id);

		return entityAux;

	}
	
	@POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response create(Entity entity) throws URISyntaxException {

	  if(entity == null){
      return Response.status(400).entity("Please add entity details !!").build();
    }
     
    if(entity.getName() == null) {
        return Response.status(400).entity("Please provide the entity name !!").build();
    }
    
    //Entity Created
    Entity newEntity = new Entity();
    newEntity.setId(1);
    newEntity.setName(entity.getName());
    
    return Response.created(new URI("/v1/entities/"+newEntity.getId())).build();
  }

  @PUT
  @Path("{id: \\d+}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response update(@PathParam("id") int id, Entity entity) {
    Entity updatedEntity = new Entity();
    
    if(entity.getName() == null) {
        return Response.status(400).entity("Please provide the entity name !!").build();
    }
     
    updatedEntity.setId(id);
    updatedEntity.setName(entity.getName());
     
    return Response.status(202).entity("Entity update successfully !!").build();
  }

  @DELETE
  @Path("{id: \\d+}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response delete(@PathParam("id") int id) {
    return Response.status(202).entity("Entity deleted successfully !!").build();
  }
}
