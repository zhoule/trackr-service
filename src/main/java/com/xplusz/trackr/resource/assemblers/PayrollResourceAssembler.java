package com.xplusz.trackr.resource.assemblers;


import com.xplusz.trackr.controllers.PayrollController;
import com.xplusz.trackr.model.Payroll;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class PayrollResourceAssembler implements ResourceAssembler<Payroll, ResourceSupport> {

    @Override
    public ResourceSupport toResource(Payroll entity) {
        Link selfLink = linkTo(methodOn(PayrollController.class).getById(entity.getPrimaryKey())).withSelfRel();
        entity.add(selfLink);
        return entity;
    }
}
