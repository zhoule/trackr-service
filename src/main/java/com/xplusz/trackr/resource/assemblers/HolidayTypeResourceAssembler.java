package com.xplusz.trackr.resource.assemblers;

import com.xplusz.trackr.controllers.ClaimController;
import com.xplusz.trackr.model.Claim;
import com.xplusz.trackr.model.HolidayType;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class HolidayTypeResourceAssembler implements ResourceAssembler<HolidayType, ResourceSupport>{

    @Override
    public ResourceSupport toResource(HolidayType entity) {
        Link selfLink = linkTo(methodOn(ClaimController.class).getById(entity.getPrimaryKey())).withSelfRel();
        entity.add(selfLink);
        return entity;
    }
}
