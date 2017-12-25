package com.xplusz.trackr.resource.assemblers;

import com.xplusz.trackr.controllers.ClaimController;
import com.xplusz.trackr.model.Festival;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class FestivalResourceAssembler implements ResourceAssembler<Festival, ResourceSupport>{

    @Override
    public ResourceSupport toResource(Festival entity) {
        Link selfLink = linkTo(methodOn(ClaimController.class).getById(entity.getPrimaryKey())).withSelfRel();
        entity.add(selfLink);
        return entity;
    }
}
