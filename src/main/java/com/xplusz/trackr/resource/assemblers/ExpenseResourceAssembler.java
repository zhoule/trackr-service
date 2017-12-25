package com.xplusz.trackr.resource.assemblers;

import com.xplusz.trackr.controllers.ExpenseController;
import com.xplusz.trackr.model.Expense;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ExpenseResourceAssembler implements ResourceAssembler<Expense, ResourceSupport> {

    @Override
    public ResourceSupport toResource(Expense entity) {
        Link selfLink = linkTo(methodOn(ExpenseController.class).getById(entity.getPrimaryKey())).withSelfRel();
        entity.add(selfLink);
        return entity;
    }
}
