package com.materials.api.service;

import static com.materials.api.service.exceptions.messages.UserMessages.*;

import com.materials.api.controller.dto.UserFilterDTO;
import com.materials.api.controller.dto.UserRequestDTO;
import com.materials.api.controller.dto.UserUpdateRequestDTO;
import com.materials.api.entity.User;
import com.materials.api.pagination.PaginationDTO;
import com.materials.api.repository.UserRepository;
import com.materials.api.security.Role;
import com.materials.api.service.dto.UserDTO;
import com.materials.api.service.exceptions.BadRequestException;
import com.materials.api.service.exceptions.ConflictException;
import com.materials.api.service.exceptions.NotFoundException;
import com.materials.api.service.rest.PostalCodeRestService;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.materials.api.pagination.TokenGenerator.generateNextPageToken;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;
  private final PostalCodeRestService postalCodeRestService;
  private final ModelMapper modelMapper;

  public UserDTO create(UserRequestDTO requestDTO) {
    if (userRepository.existsByEmail(requestDTO.getEmail())) {
      throw new ConflictException(USER_EMAIL_ALREADY_EXISTS);
    }

    if (Objects.nonNull(requestDTO.getPostalCode())) {
      return createWithAddress(requestDTO);
    } else {
      var user = modelMapper.map(requestDTO, User.class);
      user.setPassword(getEncodePassword(requestDTO));
      user.setRole(Role.USER);
      var savedUser = userRepository.save(user);
      return modelMapper.map(savedUser, UserDTO.class);
    }
  }

  private UserDTO createWithAddress(UserRequestDTO requestDTO) {
    var address = postalCodeRestService.getAddressByPostalCode(requestDTO.getPostalCode());
    if (Objects.isNull(address.getCep())) {
      throw new BadRequestException(USER_POSTAL_CODE_NOT_FOUND + requestDTO.getPostalCode());
    }

    var user = modelMapper.map(requestDTO, User.class);
    user.setAddress(address);
    user.setPassword(getEncodePassword(requestDTO));
    user.setRole(Role.USER);
    var savedUser = userRepository.save(user);
    return modelMapper.map(savedUser, UserDTO.class);
  }

  public PaginationDTO<UserDTO> findByFilter(UserFilterDTO filter) {
    var result = userRepository.findByFilter(filter);
    var nextToken =
        generateNextPageToken(
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

  public UserDTO update(Long id, UserUpdateRequestDTO requestDTO) {
    var user = findEntityById(id);
    user.setName(requestDTO.getName());
    user.setEmail(requestDTO.getEmail());
    user.setRegistry(requestDTO.getRegistry());
    user.setActive(requestDTO.getActive());

    if (Objects.nonNull(requestDTO.getPostalCode())) {
      var address = postalCodeRestService.getAddressByPostalCode(requestDTO.getPostalCode());
      if (Objects.isNull(address.getCep())) {
        throw new BadRequestException(USER_POSTAL_CODE_NOT_FOUND + requestDTO.getPostalCode());
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
      throw new BadRequestException(USER_DELETE_ERROR_MESSAGE);
    }
  }

  public void hardDelete(Long id) {
    var user = findEntityById(id);
    try {
      userRepository.delete(user);
      userRepository.flush();
    } catch (DataIntegrityViolationException e) {
      throw new BadRequestException(USER_DELETE_ERROR_MESSAGE);
    }
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository
        .findByEmail(email)
        .orElseThrow(
            () -> new UsernameNotFoundException(USER_NOT_FOUND_WITH_EMAIL + email));
  }

  private static String getEncodePassword(UserRequestDTO requestDTO) {
    return new BCryptPasswordEncoder().encode(requestDTO.getPassword());
  }
}
