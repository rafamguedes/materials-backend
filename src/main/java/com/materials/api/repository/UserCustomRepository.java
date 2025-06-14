package com.materials.api.repository;

import com.materials.api.controller.dto.UserFilterDTO;
import com.materials.api.controller.dto.UserReportFilterDTO;
import com.materials.api.entity.User;

import java.util.List;
import java.util.Optional;

import com.materials.api.service.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserCustomRepository {
  List<UserDTO> findByFilter(UserFilterDTO filter);
}
