package org.learn.microservice.rest.controller;

import org.learn.microservice.rest.dto.UserDto;
import org.learn.microservice.rest.exception.ErrorDetails;
import org.learn.microservice.rest.exception.ResourceNotFoundException;
import org.learn.microservice.rest.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("{id}")
    public UserDto getUserById(@PathVariable int id){
        UserDto userDto = userService.getUserById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", String.valueOf(id))
        );

        return userDto;
    }

    @GetMapping
    public Optional<List<UserDto>> getUsers(){
        return userService.getUsers();
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){


        UserDto user = userService.createUser(userDto);

        UserDto savedUser = modelMapper.map(user, UserDto.class);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@RequestBody UserDto userDto, @PathVariable int id ){

        userDto.setId(id);
        UserDto updatedUser = userService.updateUser(userDto);

        return updatedUser;
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable int id){
        userService.getUserById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", String.valueOf(id))
        );

        userService.deleteUserById(id);
    }

    /*@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                        WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "USER_NOT_FOUND"
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }*/
}
