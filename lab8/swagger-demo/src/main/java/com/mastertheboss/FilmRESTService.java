package com.mastertheboss;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastertheboss.model.Film;
import com.mastertheboss.repository.exceptions.ElementNotFoundException;
import com.mastertheboss.repository.interfaces.IFilmRepository;
import io.swagger.annotations.*;

import javax.ejb.EJB;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/film")
@Api(value = "Film REST Service")
public class FilmRESTService {

    @EJB(lookup = "java:module/FilmRepository")
    private IFilmRepository filmRepository;

    @GET
    @Path("/films")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Returns list of films", notes = "Returns list of films in JSON format", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful films retrieval(JSON)", response = List.class)})
    public Response returnAllFilms() {
        return Response.ok().entity(filmRepository.findAll()).build();
    }

    @GET
    @Path("/films")
    @Produces("text/uri-list")
    @ApiOperation(value = "Returns list of films", notes = "Returns list of films in test/uri-list mime type", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful films retrieval(text/uri-list", response = String.class)})
    public Response returnAllFilmsUri() {
        String films = filmRepository.findAll().stream()
                .map(Film::getUri)
                .reduce("", (e1,e2) -> e1.concat(e2).concat("\n"));
        return Response.ok().entity(films).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Returns Film", notes = "Returns Film with specified ID", response = Film.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful film retrieval", response = Film.class),
            @ApiResponse(code = 500, message = "UnSuccessful film retrieval"),
            @ApiResponse(code = 404, message = "Resource with specified URI doesn't exist")
    })
    public Response returnFilmById(@PathParam("id") Long id) {
        Film film = filmRepository.findById(id);
        if (film == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        try {
            String serialized = new ObjectMapper().writeValueAsString(film);
            return Response.ok().entity(serialized).build();
        } catch (JsonProcessingException e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Returns Film", notes = "Returns param", response = Film.class)
    @ApiParam(value = "Film title")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful film retrieval", response = Film.class),
            @ApiResponse(code = 500, message = "UnSuccessful film retrieval"),
            @ApiResponse(code = 404, message = "Resource with specified URI doesn't exist")
    })
    public Response returnFilmByTitle(@QueryParam("title") String title) {
        try {
            Film film = filmRepository.findByTitle(title);
            if (film != null) {
                return Response.ok().entity(film).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (ElementNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Returns Film", notes = "Returns Film", response = Film.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful film update", response = Film.class),
            @ApiResponse(code = 500, message = "UnSuccessful film update"),
            @ApiResponse(code = 404, message = "Resource with specified URI doesn't exist"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @Transactional
    public Response updateFilm(Film film) {
        try {
            if (film.getId() == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            filmRepository.update(film.getId(), film);
            return Response.ok().entity(filmRepository.findById(film.getId())).build();
        } catch (ElementNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Transactional
    @ApiOperation(value = "Returns Film", notes = "Returns Film", response = Film.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful created film"),
            @ApiResponse(code = 500, message = "Server error")
    })
    public Response createFilm(Film film) {
        try {
            filmRepository.save(film);
            return Response.status(201).entity(film).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }

    }

    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Returns Film", notes = "Returns Film", response = Film.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Resource successfully deleted", response = Film.class),
            @ApiResponse(code = 500, message = "Server error"),
            @ApiResponse(code = 404, message = "Resource with specified URI doesn't exist")
    })
    @Transactional
    public Response deleteFilm(@QueryParam("id") Long id) {
        try {
            Film toDelete = filmRepository.findById(id);
            if (toDelete == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            filmRepository.delete(toDelete.getId());
            return Response.status(204)
                    .entity(toDelete)
                    .build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }
}
