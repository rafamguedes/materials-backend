package com.materials.api.pagination;

import org.springframework.util.CollectionUtils;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public final class PagedHelper {

  private static final String FIELD_SEPARATOR = "sepa###rator";
  private static final String INVALID_TOKEN = "Invalid Token";
  private static final String UTILITY_CLASS = "Utility class";

  private PagedHelper() {
    throw new IllegalStateException(UTILITY_CLASS);
  }

  public static <T> String getNextTokenSeparator(
      PagedFilter filter,
      List<T> result,
      Function<T, String> descriptionExtractor,
      Function<T, Long> idExtractor) {

    return Optional.of(result)
        .filter(list -> list.size() == filter.getRows())
        .map(CollectionUtils::lastElement)
        .map(item -> encodeToken(idExtractor.apply(item), descriptionExtractor.apply(item)))
        .orElse(null);
  }

  public static String encodeToken(Long id, String... fieldsOrder) {
    var tokenBuilder = new StringBuilder(id.toString());

    for (String field : fieldsOrder) {
      tokenBuilder.append(FIELD_SEPARATOR).append(field);
    }

    return Base64.getEncoder().encodeToString(tokenBuilder.toString().getBytes());
  }

  public static String tokenToName(String token) {
    return getTokenComponent(decodeToken(token), 1);
  }

  public static Long tokenToId(String token) {
    return Long.valueOf(getTokenComponent(decodeToken(token), 0));
  }

  private static String getTokenComponent(String decodedToken, int index) {
    return Optional.ofNullable(decodedToken)
        .map(t -> t.split(FIELD_SEPARATOR))
        .filter(components -> components.length > index)
        .map(components -> components[index])
        .orElseThrow(() -> new IllegalArgumentException(INVALID_TOKEN));
  }

  public static String decodeToken(String token) {
    return Optional.ofNullable(token)
        .map(PagedHelper::decodeBase64)
        .orElseThrow(() -> new IllegalArgumentException(INVALID_TOKEN));
  }

  private static String decodeBase64(String token) {
    try {
      return new String(Base64.getDecoder().decode(token));
    } catch (Exception e) {
      throw new IllegalArgumentException(INVALID_TOKEN, e);
    }
  }
}
