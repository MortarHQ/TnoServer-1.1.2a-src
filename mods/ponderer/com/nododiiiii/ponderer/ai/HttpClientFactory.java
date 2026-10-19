package com.nododiiiii.ponderer.ai;

import com.mojang.logging.LogUtils;
import com.nododiiiii.ponderer.Config;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Builder;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.time.Duration;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.slf4j.Logger;

public final class HttpClientFactory {
   private static final Logger LOGGER = LogUtils.getLogger();
   private static volatile HttpClient cachedClient;
   private static String cachedProxy = "";
   private static boolean cachedTrustAll = false;

   private HttpClientFactory() {
   }

   public static HttpClient get() {
      String proxy = ((String)Config.AI_PROXY.get()).trim();
      boolean trustAll = (Boolean)Config.AI_TRUST_ALL_SSL.get();
      HttpClient existing = cachedClient;
      if (existing != null && proxy.equals(cachedProxy) && trustAll == cachedTrustAll) {
         return existing;
      }

      synchronized (HttpClientFactory.class) {
         if (cachedClient != null && proxy.equals(cachedProxy) && trustAll == cachedTrustAll) {
            return cachedClient;
         }

         Builder builder = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10L));
         if (!proxy.isEmpty()) {
            try {
               String[] parts = proxy.split(":");
               String host = parts[0];
               int port = Integer.parseInt(parts[1]);
               builder.proxy(ProxySelector.of(new InetSocketAddress(host, port)));
               LOGGER.info("AI HttpClient using proxy {}:{}", host, port);
            } catch (Exception e) {
               LOGGER.warn("Invalid proxy format '{}', expected host:port", proxy);
            }
         }

         if (trustAll) {
            try {
               SSLContext sslContext = SSLContext.getInstance("TLS");
               sslContext.init(null, new TrustManager[]{new HttpClientFactory.TrustAllManager()}, new SecureRandom());
               builder.sslContext(sslContext);
               LOGGER.info("AI HttpClient SSL verification disabled (trust-all)");
            } catch (Exception e) {
               LOGGER.warn("Failed to set up trust-all SSL", e);
            }
         }

         cachedClient = builder.build();
         cachedProxy = proxy;
         cachedTrustAll = trustAll;
         return cachedClient;
      }
   }

   private static class TrustAllManager implements X509TrustManager {
      @Override
      public void checkClientTrusted(X509Certificate[] chain, String authType) {
      }

      @Override
      public void checkServerTrusted(X509Certificate[] chain, String authType) {
      }

      @Override
      public X509Certificate[] getAcceptedIssuers() {
         return new X509Certificate[0];
      }
   }
}
