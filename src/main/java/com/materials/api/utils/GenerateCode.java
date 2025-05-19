package com.materials.api.utils;

/**
 * Utility class to generate a random 8-digit code.
 */
public class GenerateCode {
  public static String generateCode() {
    StringBuilder code = new StringBuilder();
    for (int i = 0; i < 8; i++) {
      int randomDigit = (int) (Math.random() * 10);
      code.append(randomDigit);
    }
    return code.toString();
  }
}
