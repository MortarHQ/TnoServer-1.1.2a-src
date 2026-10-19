package com.teamresourceful.resourcefullib.common.utils;

import com.google.gson.JsonObject;
import com.teamresourceful.resourcefullib.common.exceptions.UtilityClassException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Optional;
import org.jetbrains.annotations.Nullable;

public final class WebUtils {
   private static final HttpClient CLIENT = HttpClient.newBuilder()
      .version(Version.HTTP_2)
      .followRedirects(Redirect.NORMAL)
      .connectTimeout(Duration.ofSeconds(10L))
      .build();

   private WebUtils() throws UtilityClassException {
      throw new UtilityClassException();
   }

   @Nullable
   public static String get(String url, boolean onlySuccess) {
      return get(url, BodyHandlers.ofString())
         .filter(response -> !onlySuccess || response.statusCode() >= 200 && response.statusCode() < 300)
         .map(HttpResponse::body)
         .orElse(null);
   }

   @Nullable
   public static String get(String url) {
      return get(url, false);
   }

   @Nullable
   public static JsonObject getJson(String url, boolean onlySuccess) {
      return GsonHelpers.parseJson(get(url, onlySuccess)).orElse(null);
   }

   @Nullable
   public static JsonObject getJson(String url) {
      return getJson(url, false);
   }

   public static <T> Optional<HttpResponse<T>> get(String url, BodyHandler<T> handler) {
      try {
         HttpRequest request = HttpRequest.newBuilder(new URI(url)).GET().version(Version.HTTP_2).header("User-Agent", "Minecraft Mod").build();
         return Optional.of(CLIENT.send(request, handler));
      } catch (Exception var3) {
         return Optional.empty();
      }
   }
}
