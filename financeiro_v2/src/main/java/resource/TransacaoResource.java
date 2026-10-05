package resource;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
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
import model.Categoria;
import model.Conta;
import model.Transacao;
import java.util.List;

@Path("/transacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransacaoResource {

    @GET
    public List<Transacao> listar() {
        return Transacao.listAll();
    }

    @POST
    @Transactional
    public Response criar(Transacao nova) {
        nova.id = null;
        vincular(nova);
        nova.persist();
        return Response.status(Response.Status.CREATED).entity(nova).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Transacao atualizar(@PathParam("id") Long id, Transacao dados) {
        Transacao t = Transacao.findById(id);
        if (t == null) {
            throw new NotFoundException();
        }
        t.descricao = dados.descricao;
        t.valor = dados.valor;
        t.tipo = dados.tipo;
        t.dataTransacao = dados.dataTransacao;
        t.conta = dados.conta;
        t.categoria = dados.categoria;
        vincular(t);
        return t;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response excluir(@PathParam("id") Long id) {
        boolean apagou = Transacao.deleteById(id);
        if (!apagou) {
            throw new NotFoundException();
        }
        return Response.noContent().build();
    }

    private void vincular(Transacao t) {
        if (t.conta == null || t.categoria == null) {
            throw new BadRequestException("Informe conta e categoria");
        }
        Conta conta = Conta.findById(t.conta.id);
        Categoria categoria = Categoria.findById(t.categoria.id);
        if (conta == null || categoria == null) {
            throw new BadRequestException("Conta ou categoria inexistente");
        }
        t.conta = conta;
        t.categoria = categoria;
    }
}