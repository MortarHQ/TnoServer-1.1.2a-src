package com.nododiiiii.ponderer.ai;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Optional;
import org.slf4j.Logger;

public final class McmodApiClient {
   private static final Logger LOGGER = LogUtils.getLogger();

   private McmodApiClient() {
   }

   public static Optional<String> getItemUrl(String displayName) {
      try {
         HttpClient client = HttpClientFactory.get();
         String encodedName = URLEncoder.encode(displayName, StandardCharsets.UTF_8);
         HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.mcmod.cn/getItem/?regname=" + encodedName))
            .timeout(Duration.ofSeconds(10L))
            .build();
         HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
         String responseBody = response.body();
         int itemId = -1;

         try {
            itemId = Integer.parseInt(responseBody.trim());
         } catch (NumberFormatException e) {
            try {
               JsonElement element = JsonParser.parseString(responseBody);
               if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
                  itemId = element.getAsInt();
               }
            } catch (Exception var9) {
            }
         }

         if (itemId > 0) {
            String mcmodUrl = "https://www.mcmod.cn/item/" + itemId + ".html";
            return Optional.of(mcmodUrl);
         }
      } catch (Exception e) {
         LOGGER.warn("Failed to get MCMod URL for item '{}': {}", displayName, e.getMessage());
      }

      return Optional.empty();
   }
}
