package com.materials.api.utils;

import com.materials.api.service.exceptions.GeneralException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.binary.Base32;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TokenUtils {
  private static final String INVALID_TOKEN_FORMAT = "Invalid token format, expected at least 2 parts";
  private static final String INVALID_TOKEN = "Invalid token, unable to decode: ";
  private static final String SEPARATOR = "sepa###rator";
  private static final Base32 BASE32 = new Base32();

  public static <T> String getNextToken(List<T> result, int rows, Function<T, Long> id, Function<T, String> name) {
    return (result.size() == rows)
        ? Optional.ofNullable(CollectionUtils.lastElement(result))
            .map(entity -> generateToken(id.apply(entity), name.apply(entity)))
            .orElse(null)
        : null;
  }

  public static String generateToken(Long id, String... fieldsOrder) {
    StringBuilder tokenBuilder = new StringBuilder(id.toString());
    for (String field : fieldsOrder) {
      tokenBuilder.append(SEPARATOR).append(field);
    }
    return BASE32.encodeToString(tokenBuilder.toString().getBytes());
  }

  public static String getTokenName(String token) {
    String[] parts = decodeToken(token).split(SEPARATOR);
    if (parts.length < 2) {
      throw new GeneralException(INVALID_TOKEN_FORMAT);
    }
    return parts[1];
  }

  public static Long getTokenId(String token) {
    return Long.parseLong(decodeToken(token).split(SEPARATOR)[0]);
  }

  private static String decodeToken(String token) {
    try {
      return new String(BASE32.decode(token));
    } catch (Exception e) {
      throw new GeneralException(INVALID_TOKEN + e);
    }
  }
}