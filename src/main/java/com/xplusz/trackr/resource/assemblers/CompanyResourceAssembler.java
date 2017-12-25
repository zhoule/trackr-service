package com.xplusz.trackr.resource.assemblers;

import com.xplusz.trackr.controllers.ClaimController;
import com.xplusz.trackr.controllers.CompanyController;
import com.xplusz.trackr.model.Company;
import com.xplusz.trackr.model.Festival;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by hank on 4/10/17.
 */
@Component
public class CompanyResourceAssembler implements ResourceAssembler<Company, ResourceSupport> {

    @Override
    public ResourceSupport toResource(Company entity) {
        Link selfLink = linkTo(methodOn(CompanyController.class).getById(entity.getPrimaryKey())).withSelfRel();
        entity.add(selfLink);
        return entity;
    }
}
