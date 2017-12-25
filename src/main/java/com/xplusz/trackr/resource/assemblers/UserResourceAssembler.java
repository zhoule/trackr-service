package com.xplusz.trackr.resource.assemblers;

import com.xplusz.trackr.controllers.UserController;
import com.xplusz.trackr.model.User;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class UserResourceAssembler implements ResourceAssembler<User, ResourceSupport> {

    @Override
    public ResourceSupport toResource(User entity) {
        Link selfLink = linkTo(methodOn(UserController.class).getUserById(entity.getPrimaryKey())).withSelfRel();
        entity.add(selfLink);
        return entity;
    }
}
