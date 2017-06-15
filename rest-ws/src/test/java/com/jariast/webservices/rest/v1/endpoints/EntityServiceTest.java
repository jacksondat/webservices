package com.jariast.webservices.rest.v1.endpoints;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.List;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import com.jariast.webservices.rest.v1.model.Entity;

public class EntityServiceTest extends JerseyTest{
	
	@Override
  public Application configure() {
    enable(TestProperties.LOG_TRAFFIC);
    enable(TestProperties.DUMP_ENTITY);
    return new ResourceConfig(EntityEndpoint.class);
  }
	
	@Test
  public void fetchAll() {
      Response output = target("/v1/entities").request().get();
      
      List<Entity> entities = output.readEntity(List.class);
      
      assertEquals("Should return status 200", 200, output.getStatus());
      assertNotNull("Should return list", entities);
      assertTrue("Should return a not empty list", entities.size() > 0);
  }
	
	@Test
  public void fetchById() {
      Response output = target("/v1/entities/1").request().get();
      
      Entity entity = output.readEntity(Entity.class);
      
      assertEquals("Should return status 200", 200, output.getStatus());
      assertNotNull("Should return an Entity", entity);
      assertEquals("Should return a not empty list", "entity1", entity.getName());
  }
	
	@Test
  public void testFetchByFail_DoesNotHaveDigit(){
      Response output = target("/v1/entities/no-id-digit").request().get();
      assertEquals("Should return status 404", 404, output.getStatus());
  }
	
	@Test
  public void testCreate(){
      Entity entity = new Entity();
      entity.setName("entity1");
      
      Response output = target("/v1/entities")
              .request()
              .post(javax.ws.rs.client.Entity.entity(entity, MediaType.APPLICATION_JSON));
      
      String uri = output.getHeaderString(HttpHeaders.LOCATION);
      
      assertEquals("Should return status 201", 201, output.getStatus());
      assertNotNull("Should return the Entity location", uri);
  }

  @Test
  public void testUpdate(){
    Entity entity = new Entity();
    entity.setName("entity1");
    
    Response output = target("/v1/entities/1")
            .request()
            .put(javax.ws.rs.client.Entity.entity(entity, MediaType.APPLICATION_JSON));
    
    String message = output.readEntity(String.class);
    
    assertEquals("Should return status 202", 202, output.getStatus());
    assertEquals("Should say Entity Updated", "Entity update successfully !!", message);
  }

  @Test
  public void testDelete(){
      Response output = target("/v1/entities/1").request().delete();
      
      String message = output.readEntity(String.class);
      
      assertEquals("Should return status 202", 202, output.getStatus());
      assertEquals("Should say Entity Deleted", "Entity deleted successfully !!", message);
  }

}
