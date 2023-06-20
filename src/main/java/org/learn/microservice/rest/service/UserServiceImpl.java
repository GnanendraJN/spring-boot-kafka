package org.learn.microservice.rest.service;

import org.learn.microservice.rest.dto.UserDto;
import org.learn.microservice.rest.exception.EmailAlreadyExistException;
import org.learn.microservice.rest.exception.ResourceNotFoundException;
import org.learn.microservice.rest.model.User;
import org.learn.microservice.rest.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Optional<UserDto> getUserById(int id) {

        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id+"")
        );
        //return Optional.empty();

        UserDto userDto = modelMapper.map(user, UserDto.class);
        return Optional.of(userDto);
    }

    @Override
    public Optional<List<UserDto>> getUsers() {

        List<User> users =  userRepository.findAll();
        List<UserDto> userDtos = users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
        return Optional.of(userDtos);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User existingUser = userRepository.findById(userDto.getId()).orElseThrow(
                ()-> new ResourceNotFoundException("User", "id", String.valueOf(userDto.getId()))
        );

        existingUser.setFirstName(userDto.getFirstName());
        existingUser.setLastName(userDto.getLastName());
        existingUser.setEmail(userDto.getEmail());

        User updatedUser = userRepository.save(existingUser);

        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {

        Optional<User> existingUser = userRepository.findByEmail(userDto.getEmail());
        if(existingUser.isPresent()){
            throw new EmailAlreadyExistException("Email already exists");
        }

        User user = new User(userDto.getId(), userDto.getFirstName(), userDto.getLastName(), userDto.getEmail());

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public void deleteUserById(int id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", String.valueOf(id))
        );

        userRepository.deleteById(id);
    }
}
