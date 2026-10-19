package com.nododiiiii.ponderer.ai;

import com.nododiiiii.ponderer.Config;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebPageFetcher {
   private static final int MAX_IMAGES = 5;
   private static final int MAX_TEXT_LENGTH = 30000;
   private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36";
   private static final HttpClient PLAIN_CLIENT = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10L)).followRedirects(Redirect.NORMAL).build();

   private static HttpClient getClient() {
      return Config.AI_WEB_USE_PROXY.get() ? HttpClientFactory.get() : PLAIN_CLIENT;
   }

   public static WebPageFetcher.WebPageContent fetch(String url) throws IOException, InterruptedException {
      HttpClient client = getClient();
      HttpRequest request = HttpRequest.newBuilder()
         .uri(URI.create(url))
         .timeout(Duration.ofSeconds(15L))
         .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
         .GET()
         .build();
      HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
      if (response.statusCode() != 200) {
         throw new IOException("HTTP " + response.statusCode() + " fetching " + url);
      }

      String html = response.body();
      String text = extractText(html);
      if (text.length() > 30000) {
         text = text.substring(0, 30000) + "\n... (truncated)";
      }

      List<WebPageFetcher.ImageData> images = extractAndDownloadImages(html, url, client);
      return new WebPageFetcher.WebPageContent(text, images);
   }

   private static String extractText(String html) {
      String cleaned = html.replaceAll("(?is)<script[^>]*>.*?</script>", "");
      cleaned = cleaned.replaceAll("(?is)<style[^>]*>.*?</style>", "");
      cleaned = cleaned.replaceAll("(?is)<nav[^>]*>.*?</nav>", "");
      cleaned = cleaned.replaceAll("(?is)<header[^>]*>.*?</header>", "");
      cleaned = cleaned.replaceAll("(?is)<footer[^>]*>.*?</footer>", "");
      cleaned = cleaned.replaceAll("(?i)<br\\s*/?>", "\n");
      cleaned = cleaned.replaceAll("(?i)</?(p|div|h[1-6]|li|tr|td|th|blockquote|pre)[^>]*>", "\n");
      cleaned = cleaned.replaceAll("<[^>]+>", "");
      cleaned = cleaned.replace("&amp;", "&").replace("&lt;", "<").replace("&gt;", ">").replace("&quot;", "\"").replace("&nbsp;", " ").replace("&#39;", "'");
      cleaned = cleaned.replaceAll("[ \\t]+", " ");
      cleaned = cleaned.replaceAll("\\n\\s*\\n+", "\n\n");
      return cleaned.trim();
   }

   private static List<WebPageFetcher.ImageData> extractAndDownloadImages(String html, String pageUrl, HttpClient client) {
      Pattern imgPattern = Pattern.compile("<img[^>]+src=[\"']([^\"']+)[\"']", 2);
      Matcher matcher = imgPattern.matcher(html);
      List<String> candidateUrls = new ArrayList<>();

      while (matcher.find() && candidateUrls.size() < 20) {
         String src = matcher.group(1);
         if (!src.startsWith("data:")
            && !src.contains("icon")
            && !src.contains("logo")
            && !src.contains("avatar")
            && !src.contains("1x1")
            && !src.contains("pixel")) {
            if (src.startsWith("//")) {
               src = "https:" + src;
            } else if (src.startsWith("/")) {
               try {
                  URI base = URI.create(pageUrl);
                  src = base.getScheme() + "://" + base.getHost() + src;
               } catch (Exception e) {
                  continue;
               }
            } else if (!src.startsWith("http")) {
               continue;
            }

            candidateUrls.add(src);
         }
      }

      List<CompletableFuture<WebPageFetcher.ImageData>> futures = new ArrayList<>();

      for (String imgUrl : candidateUrls) {
         if (futures.size() >= 10) {
            break;
         }

         HttpRequest imgReq = HttpRequest.newBuilder()
            .uri(URI.create(imgUrl))
            .timeout(Duration.ofSeconds(10L))
            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
            .GET()
            .build();
         futures.add(client.sendAsync(imgReq, BodyHandlers.ofByteArray()).thenApply(imgResp -> {
            if (imgResp.statusCode() != 200) {
               return null;
            }

            byte[] data = imgResp.body();
            if (data.length >= 5000 && data.length <= 5000000) {
               String contentType = imgResp.headers().firstValue("content-type").orElse("image/png");
               if (!contentType.startsWith("image/")) {
                  return null;
               }

               if (contentType.contains(";")) {
                  contentType = contentType.substring(0, contentType.indexOf(59)).trim();
               }

               return new WebPageFetcher.ImageData(Base64.getEncoder().encodeToString(data), contentType);
            } else {
               return null;
            }
         }).exceptionally(ex -> null));
      }

      List<WebPageFetcher.ImageData> images = new ArrayList<>();

      for (CompletableFuture<WebPageFetcher.ImageData> f : futures) {
         if (images.size() >= 5) {
            break;
         }

         try {
            WebPageFetcher.ImageData img = f.join();
            if (img != null) {
               images.add(img);
            }
         } catch (Exception var11) {
         }
      }

      return images;
   }

   public record ImageData(String base64, String mediaType) {
   }

   public record WebPageContent(String text, List<WebPageFetcher.ImageData> images) {
   }
}
