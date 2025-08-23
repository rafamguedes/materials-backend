package com.materials.api.pagination;

import lombok.experimental.UtilityClass;
import org.apache.commons.codec.binary.Base32;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@UtilityClass
public final class TokenGenerator {
  private static final String SEPARATOR = "sepa###rator";
  private static final Base32 BASE32 = new Base32();

  public static <T, U> String generateNextPageToken(
      List<T> result,
      Integer rows,
      Function<T, Long> primaryKeyExtractor,
      Function<T, U> secondaryKeyExtractor) {
    return (result.size() == rows)
        ? Optional.ofNullable(CollectionUtils.lastElement(result))
            .map(
                entity ->
                    encodeTokenValues(
                        primaryKeyExtractor.apply(entity),
                        String.valueOf(secondaryKeyExtractor.apply(entity))))
            .orElse(null)
        : null;
  }

  public static String encodeTokenValues(Long id, String... fieldsOrder) {
    StringBuilder tokenBuilder = new StringBuilder(id.toString());
    for (String field : fieldsOrder) {
      tokenBuilder.append(SEPARATOR).append(field);
    }
    return BASE32.encodeToString(tokenBuilder.toString().getBytes());
  }
}