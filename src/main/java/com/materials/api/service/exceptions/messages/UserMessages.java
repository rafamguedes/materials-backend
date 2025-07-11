package com.materials.api.service.exceptions.messages;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserMessages {
  public static final String USER_NOT_FOUND =
      "User not found, please check the user ID: ";
  public static final String USER_EMAIL_ALREADY_EXISTS =
      "Email already exists, please use a different email";
  public static final String USER_POSTAL_CODE_NOT_FOUND =
      "Postal code not found, please check the postal code: ";
  public static final String USER_DELETE_ERROR_MESSAGE =
      "Error deleting user, please check if the user is associated with any reservations or items.";
  public static final String USER_NOT_FOUND_WITH_EMAIL =
      "User not found with email: ";
}
