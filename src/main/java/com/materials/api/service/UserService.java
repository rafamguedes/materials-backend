package com.materials.api.service;

import com.materials.api.controller.dto.UserRequestDTO;
import com.materials.api.entity.User;
import com.materials.api.repository.UserRepository;
import com.materials.api.service.dto.UserDTO;
import com.materials.api.service.exceptions.BadRequestException;
import com.materials.api.service.exceptions.NotFoundException;
import com.materials.api.service.rest.PostalCodeRestService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final PostalCodeRestService postalCodeRestService;
  private final ModelMapper modelMapper;

  private static final String USER_NOT_FOUND = "User not found";
  private static final String EMAIL_ALREADY_EXISTS = "Email already exists";
  private static final String POSTAL_CODE_NOT_FOUND = "CEP não encontrado, por favor, verifique o CEP informado";

  public UserDTO create(UserRequestDTO requestDTO) {
    if (userRepository.existsByEmail(requestDTO.getEmail())) {
      throw new BadRequestException(EMAIL_ALREADY_EXISTS);
    }

    if (Objects.nonNull(requestDTO.getPostalCode())) {
      return createWithAddress(requestDTO);
    } else {
      var user = modelMapper.map(requestDTO, User.class);
      var savedUser = userRepository.save(user);
      return modelMapper.map(savedUser, UserDTO.class);
    }
  }

  private UserDTO createWithAddress(UserRequestDTO requestDTO) {
    var address = postalCodeRestService.getAddressByPostalCode(requestDTO.getPostalCode());
    if (Objects.isNull(address.getCep())) {
      throw new BadRequestException(POSTAL_CODE_NOT_FOUND);
    }

    var user = modelMapper.map(requestDTO, User.class);
    user.setAddress(address);
    var savedUser = userRepository.save(user);
    return modelMapper.map(savedUser, UserDTO.class);
  }

  public UserDTO getById(Long id) {
    var user = findEntityById(id);
    return modelMapper.map(user, UserDTO.class);
  }

  public User findEntityById(Long id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
  }

  public UserDTO update(Long id, UserRequestDTO requestDTO) {
    var user = findEntityById(id);
    user.setName(requestDTO.getName());
    user.setEmail(requestDTO.getEmail());
    user.setPhone(requestDTO.getPhone());

    var updatedUser = userRepository.save(user);
    return modelMapper.map(updatedUser, UserDTO.class);
  }

  public void delete(Long id) {
    if (!userRepository.existsById(id)) {
      throw new NotFoundException(USER_NOT_FOUND);
    }
    userRepository.deleteById(id);
  }
}
