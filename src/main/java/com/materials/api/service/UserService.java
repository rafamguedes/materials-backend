package com.materials.api.service;

import com.materials.api.controller.dto.UserFilterDTO;
import com.materials.api.controller.dto.UserRequestDTO;
import com.materials.api.entity.User;
import com.materials.api.pagination.PaginationDTO;
import com.materials.api.repository.UserRepository;
import com.materials.api.service.dto.UserDTO;
import com.materials.api.service.exceptions.BadRequestException;
import com.materials.api.service.exceptions.ConflictException;
import com.materials.api.service.exceptions.NotFoundException;
import com.materials.api.service.rest.PostalCodeRestService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.materials.api.utils.TokenUtils.getNextToken;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final PostalCodeRestService postalCodeRestService;
  private final ModelMapper modelMapper;

  private static final String USER_NOT_FOUND = "User not found, please check the user ID: ";
  private static final String EMAIL_ALREADY_EXISTS = "Email already exists, please use a different email";
  private static final String POSTAL_CODE_NOT_FOUND = "Postal code not found, please check the postal code: ";
  private static final String DELETE_ERROR_MESSAGE = "Error deleting user, please check if the user is associated with any reservations or items.";

  public UserDTO create(UserRequestDTO requestDTO) {
    if (userRepository.existsByEmail(requestDTO.getEmail())) {
      throw new ConflictException(EMAIL_ALREADY_EXISTS);
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
      throw new BadRequestException(POSTAL_CODE_NOT_FOUND + requestDTO.getPostalCode());
    }

    var user = modelMapper.map(requestDTO, User.class);
    user.setAddress(address);
    var savedUser = userRepository.save(user);
    return modelMapper.map(savedUser, UserDTO.class);
  }

  public PaginationDTO<UserDTO> findByFilter(UserFilterDTO filter) {
    var result = userRepository.findByFilter(filter);
    var nextToken =
        getNextToken(
            result,
            filter.getRows(),
            UserDTO::getId,
            user -> filter.getOrderByColumn().getColumnValue(user));

    return new PaginationDTO<>(result, nextToken);
  }

  public UserDTO getById(Long id) {
    var user = findEntityById(id);
    return modelMapper.map(user, UserDTO.class);
  }

  public User findEntityById(Long id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND + id));
  }

  public UserDTO update(Long id, UserRequestDTO requestDTO) {
    var user = findEntityById(id);
    user.setName(requestDTO.getName());
    user.setEmail(requestDTO.getEmail());
    user.setRegistry(requestDTO.getRegistry());
    user.setActive(requestDTO.getActive());

    if (Objects.nonNull(requestDTO.getPostalCode())) {
      var address = postalCodeRestService.getAddressByPostalCode(requestDTO.getPostalCode());
      if (Objects.isNull(address.getCep())) {
        throw new BadRequestException(POSTAL_CODE_NOT_FOUND + requestDTO.getPostalCode());
      }
      user.setAddress(address);
    } else {
      user.setAddress(null);
    }

    user.setUpdatedAt(LocalDateTime.now());

    var updatedUser = userRepository.save(user);
    return modelMapper.map(updatedUser, UserDTO.class);
  }

  public void softDelete(Long id) {
    var user = findEntityById(id);
    try {
      user.setIsDeleted(Boolean.TRUE);
      user.setDeletedAt(LocalDateTime.now());
      user.setActive(Boolean.FALSE);
      userRepository.save(user);
    } catch (DataIntegrityViolationException e) {
      throw new BadRequestException(DELETE_ERROR_MESSAGE);
    }
  }

  public void hardDelete(Long id) {
    var user = findEntityById(id);
    try {
      userRepository.delete(user);
      userRepository.flush();
    } catch (DataIntegrityViolationException e) {
      throw new BadRequestException(DELETE_ERROR_MESSAGE);
    }
  }
}
