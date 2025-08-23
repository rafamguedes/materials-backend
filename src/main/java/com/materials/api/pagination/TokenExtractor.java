package com.materials.api.pagination;

import com.materials.api.service.exceptions.GeneralException;
import org.apache.commons.codec.binary.Base32;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TokenExtractor {
  private static final Base32 BASE32 = new Base32();
  private static final String SEPARATOR = "sepa###rator";
  private static final String INVALID_TOKEN_FORMAT = "Invalid token format, expected at least 2 parts";
  private static final String INVALID_TOKEN = "Invalid token, unable to decode: ";

  private static String decodeTokenValues(String token) {
    try {
      return new String(BASE32.decode(token));
    } catch (Exception e) {
      throw new GeneralException(INVALID_TOKEN + e);
    }
  }

  public static Long extractPrimaryKeyAsLong(String token) {
    return Long.parseLong(decodeTokenValues(token).split(SEPARATOR)[0]);
  }

  public static String extractSecondaryKeyAsString(String token) {
    String[] parts = decodeTokenValues(token).split(SEPARATOR);
    if (parts.length < 2) {
      throw new GeneralException(INVALID_TOKEN_FORMAT);
    }
    return parts[1];
  }
}
