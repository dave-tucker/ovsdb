package org.opendaylight.ovsdb.northbound;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.opendaylight.controller.northbound.commons.exception.InternalServerErrorException;
import org.opendaylight.controller.sal.core.Node;
import org.opendaylight.controller.sal.utils.ServiceHelper;
import org.opendaylight.ovsdb.lib.OvsdbClient;
import org.opendaylight.ovsdb.lib.schema.DatabaseSchema;
import org.opendaylight.ovsdb.plugin.api.OvsdbConnectionService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Northbound interface for OVSDB Databases
 */
public class DatabaseResource {

    String nodeId;
    ObjectMapper objectMapper;
    DatabaseResource(String id) {
        this.nodeId = id;
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private DatabaseSchema getDatabaseSchema (String databaseName) {
        String csDatabaseName = this.caseSensitiveDatabaseName(databaseName);
        OvsdbClient client = NodeResource.getOvsdbConnection(nodeId, this);
        return client.getDatabaseSchema(csDatabaseName);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDatabases(){
        OvsdbClient client = NodeResource.getOvsdbConnection(nodeId, this);
        try {
            List<String> databases = client.getDatabases().get();
            if (databases == null) {
                return Response.noContent().build();
            }
            String response = objectMapper.writeValueAsString(databases);
            return Response.status(Response.Status.OK)
                    .entity(response)
                    .build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed due to exception " + e.getMessage());
        }
    }

    @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDatabases(@PathParam("name") String name) throws JsonProcessingException {
        DatabaseSchema dbSchema = this.getDatabaseSchema(name);
        String response = objectMapper.writeValueAsString(dbSchema);
        return Response.status(Response.Status.OK)
                .entity(response)
                .build();
    }

    @Path("{name}/table")
    public TableResource getDatabaseTables(@PathParam("name") String name){
        String csDatabaseName = this.caseSensitiveDatabaseName(name);
        return new TableResource(nodeId, csDatabaseName);
    }

    private String caseSensitiveDatabaseName (String ciDatabaseName) {
        Node node = Node.fromString(nodeId);
        OvsdbConnectionService connectionService = (OvsdbConnectionService)ServiceHelper.getGlobalInstance(OvsdbConnectionService.class, this);
        OvsdbClient client = connectionService.getConnection(node).getClient();

        try {
            List<String> databases = client.getDatabases().get();
            if (databases == null) return ciDatabaseName;
            for (String csDatabaseName : databases) {
                if (csDatabaseName.equalsIgnoreCase(ciDatabaseName)) return csDatabaseName;
            }
            return ciDatabaseName;
        } catch (Exception e) {
            return ciDatabaseName;
        }
    }
}
