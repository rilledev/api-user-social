package io.github.rilledev.quarkussocial.rest;

import io.github.rilledev.quarkussocial.model.Post;
import io.github.rilledev.quarkussocial.model.User;
import io.github.rilledev.quarkussocial.repository.PostRepository;
import io.github.rilledev.quarkussocial.repository.UserRepository;
import io.github.rilledev.quarkussocial.rest.dto.CreatePostRequest;
import io.github.rilledev.quarkussocial.rest.dto.PostResponse;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/users/{userId}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

    private final UserRepository userRepository;
    private final PostRepository repository;

    @Inject
    public PostResource(UserRepository userRepository, PostRepository repository){
        this.userRepository = userRepository;
        this.repository = repository;
    }

    @POST
    @Transactional
    public Response savePost(@PathParam("userId") Long userId, CreatePostRequest request){
        User user = userRepository.findById(userId);
        if (user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Post post = new Post();
        post.setText(request.getText());
        post.setUser(user);

        repository.persist(post);

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public Response listPosts(@PathParam("userId") Long userId){
        User user = userRepository.findById(userId);
        if (user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        PanacheQuery<Post> query = repository.find("user",Sort.by("dateTime", Sort.Direction.Descending), user);
        List<Post> list = query.list();

        var postResponseList = list.stream()
                .map(PostResponse::fromEntity)
                .collect(Collectors.toList());

        return Response.ok(postResponseList).build();
    }
}
