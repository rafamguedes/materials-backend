package com.materials.api.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.binary.Base32;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TokenHelper {
  private static final String SEPARATOR = "sepa###rator";
  private static final String INVALID_TOKEN_ERROR_MESSAGE =
      "Invalid token, please check the token format";

  public static String generateBase32Token(Long id, String... fieldsOrder) {
    var tokenBuilder = new StringBuilder(id.toString());
    for (String field : fieldsOrder) {
      tokenBuilder.append(SEPARATOR).append(field);
    }

    return new Base32().encodeToString(tokenBuilder.toString().getBytes());
  }

  public static String extractFieldFromToken(String token) {
    return getDecodedTokenComponent(decodeBase32Token(token), 1);
  }

  public static Long extractIdFromToken(String token) {
    return Long.valueOf(getDecodedTokenComponent(decodeBase32Token(token), 0));
  }

  private static String getDecodedTokenComponent(String decodedToken, int index) {
    return Optional.ofNullable(decodedToken)
        .map(t -> t.split(SEPARATOR))
        .filter(components -> components.length > index)
        .map(components -> components[index])
        .orElseThrow(() -> new IllegalArgumentException(INVALID_TOKEN_ERROR_MESSAGE));
  }

  public static String decodeBase32Token(String token) {
    return Optional.ofNullable(token)
        .map(TokenHelper::decodeBase32)
        .orElseThrow(() -> new IllegalArgumentException(INVALID_TOKEN_ERROR_MESSAGE));
  }

  private static String decodeBase32(String token) {
    try {
      return new String(new Base32().decode(token));
    } catch (Exception e) {
      throw new IllegalArgumentException(INVALID_TOKEN_ERROR_MESSAGE, e);
    }
  }
}
