package com.materials.api.utils;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Utility class to generate a serial number for items.
 *
 * <p>The serial number consists of 3 random uppercase letters followed by a hyphen and an 8-digit
 * UUID.
 */
public class GenerateSerialNumber {
  private static final String LETTERS = "ABCDEFGHJKLMNPQRSTUVWXYZ"; // Exclude I, O, Q

  public static String generateSerialNumber() {
    var randomLetters =
        ThreadLocalRandom.current()
            .ints(3, 0, LETTERS.length())
            .mapToObj(LETTERS::charAt)
            .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
            .toString();

    var uuidPart = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    return String.format("%s-%s", randomLetters, uuidPart);
  }
}
