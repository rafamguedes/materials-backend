package com.materials.api.repository;

import com.materials.api.controller.dto.UserFilterDTO;
import com.materials.api.controller.dto.UserReportFilterDTO;
import com.materials.api.entity.User;

import java.util.List;

import com.materials.api.service.dto.UserDTO;

public interface UserCustomRepository {
  List<UserDTO> findByFilter(UserFilterDTO filter);

  List<User> generateUsersReport(UserReportFilterDTO filter);
}
