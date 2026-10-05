package resource;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Conta;
import java.util.List;

@Path("/contas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContaResource {

    @GET
    public List<Conta> listar() {
        return Conta.listAll();
    }

    @POST
    @Transactional
    public Response criar(Conta nova) {
        nova.id = null;
        nova.persist();
        return Response.status(Response.Status.CREATED).entity(nova).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Conta atualizar(@PathParam("id") Long id, Conta dados) {
        Conta c = Conta.findById(id);
        if (c == null) {
            throw new NotFoundException();
        }
        c.descricao = dados.descricao;
        return c;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response excluir(@PathParam("id") Long id) {
        boolean apagou = Conta.deleteById(id);
        if (!apagou) {
            throw new NotFoundException();
        }
        return Response.noContent().build();
    }
}