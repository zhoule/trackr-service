package com.xplusz.trackr.controllers;

import com.xplusz.trackr.model.User;
import com.xplusz.trackr.resource.assemblers.UserResourceAssembler;
import com.xplusz.trackr.services.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // *************************************************************//
    // *********************** PROPERTIES **************************//
    // *************************************************************//
    private static final String OWNER = "authentication.name == #userName";

    private static final String ADMIN = "hasRole('ADMIN')";

    private final UserResourceAssembler userResourceAssembler;

    private final UserService userService;

    // *************************************************************//
    // *********************** CONSTRUCTORS ************************//
    // *************************************************************//
    @Autowired
    public UserController(
                          UserService userService,
                          UserResourceAssembler userResourceAssembler
    ) {
        this.userService = userService;
        this.userResourceAssembler = userResourceAssembler;
    }

    // *************************************************************//
    // ********************* REST ENDPOINTS ************************//
    // *************************************************************//

//    @PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(path = "/id/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get user by given user id")
    public ResponseEntity<ResourceSupport> getUserById(@PathVariable String id) {
        User user = userService.findUserByPrimaryKey(id);
        return new ResponseEntity<>(this.userResourceAssembler.toResource(user), HttpStatus.OK);
    }

//    @PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(path = "/username/{userName}", method = RequestMethod.GET)
    @ApiOperation(value = "Get user detail by given user name")
    public ResponseEntity<ResourceSupport> getUser(@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        return new ResponseEntity<>(this.userResourceAssembler.toResource(user), HttpStatus.OK);
    }

//    @PreAuthorize(ADMIN)
    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Get all users")
    public ResponseEntity<?> getUsers(
            @PageableDefault(size = 10, page = 0) Pageable pageable,
            PagedResourcesAssembler<User> assembler
    ) {
        Page<User> users = this.userService.getUsersByPage(pageable);
        return new ResponseEntity<>(assembler.toResource(users, this.userResourceAssembler), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Create a new user")
    public ResponseEntity<?> createUser(@Validated @RequestBody User createUser) {
        User user = userService.save(createUser);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(methodOn(UserController.class, user.getPrimaryKey()).getUserById(user.getPrimaryKey())).toUri());

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

//    @PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(path = "/id/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update user by given user id")
    public ResponseEntity<?> updateUser(@PathVariable String id,
                                                   @Validated @RequestBody User input) {
        User user = this.userService.update(id, input);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(methodOn(UserController.class, user.getPrimaryKey()).getUserById(user.getPrimaryKey())).toUri());

        return new ResponseEntity<>("The resource was updated successful.", httpHeaders, HttpStatus.NO_CONTENT);
    }

//    @PreAuthorize(ADMIN)
    @RequestMapping(path = "/id/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete user by given user id")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        this.userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
