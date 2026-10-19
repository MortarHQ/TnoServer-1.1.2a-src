package com.github.wal_bos.moreskills.ai;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public final class TensuraAiClient {
   private static final HttpClient CLIENT = HttpClient.newBuilder().followRedirects(Redirect.NORMAL).build();
   private static final URI GROQ_URI = URI.create("https://api.groq.com/openai/v1/chat/completions");

   private TensuraAiClient() {
   }

   public static String askGroq(String question, String wikiContext) throws IOException, InterruptedException {
      TensuraAiDebug.log("askGroq called");
      TensuraAiDebug.log("Question: " + TensuraAiDebug.preview(question, 300));
      TensuraAiDebug.log("Wiki context length: " + (wikiContext == null ? 0 : wikiContext.length()));
      TensuraAiDebug.log("Wiki context preview: " + TensuraAiDebug.preview(wikiContext, 1000));
      if (wikiContext != null && !wikiContext.isBlank()) {
         String apiKey = TensuraAiConfig.getResolvedApiKey();
         TensuraAiDebug.log("API key state: " + TensuraAiDebug.apiKeyState(apiKey));
         if (apiKey != null && !apiKey.isBlank()) {
            String body = buildBody(question, wikiContext);
            TensuraAiDebug.log("Groq model: llama-3.1-8b-instant");
            TensuraAiDebug.log("Groq request body length: " + body.length());
            HttpRequest request = HttpRequest.newBuilder()
               .uri(GROQ_URI)
               .timeout(Duration.ofSeconds(30L))
               .header("Authorization", "Bearer " + apiKey)
               .header("Content-Type", "application/json")
               .POST(BodyPublishers.ofString(body, StandardCharsets.UTF_8))
               .build();
            long startTime = System.currentTimeMillis();

            HttpResponse<String> response;
            try {
               response = CLIENT.send(request, BodyHandlers.ofString(StandardCharsets.UTF_8));
            } catch (IOException exception) {
               TensuraAiDebug.error("Groq request failed with IOException.", exception);
               throw exception;
            } catch (InterruptedException exception) {
               TensuraAiDebug.error("Groq request was interrupted.", exception);
               Thread.currentThread().interrupt();
               throw exception;
            } catch (Exception exception) {
               TensuraAiDebug.error("Groq request crashed unexpectedly.", exception);
               return "Raphael: External thought circuit failed. Request crashed.";
            }

            long elapsed = System.currentTimeMillis() - startTime;
            TensuraAiDebug.log("Groq status: " + response.statusCode());
            TensuraAiDebug.log("Groq elapsed ms: " + elapsed);
            TensuraAiDebug.log("Groq response preview: " + TensuraAiDebug.preview(response.body(), 1200));
            if (response.statusCode() == 401) {
               return "Raphael: External thought circuit failed. Invalid API key.";
            }

            if (response.statusCode() == 402) {
               return "Raphael: External thought circuit failed. Quota or billing limit reached.";
            }

            if (response.statusCode() == 413) {
               return "Raphael: External thought circuit failed. Wiki context was too large.";
            }

            if (response.statusCode() == 429) {
               return "Raphael: External thought circuit failed. Rate limit reached.";
            }

            if (response.statusCode() >= 500) {
               return "Raphael: External thought circuit failed. Groq server error " + response.statusCode() + ".";
            }

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
               String answer = extractContent(response.body());
               TensuraAiDebug.log("Extracted answer preview: " + TensuraAiDebug.preview(answer, 700));
               if (answer != null && !answer.isBlank()) {
                  answer = unescapeJson(answer).trim();
                  if (!answer.startsWith("Raphael:")) {
                     answer = "Raphael: " + answer;
                  }

                  if (answer.length() > 700) {
                     answer = answer.substring(0, 700).trim();
                  }

                  TensuraAiDebug.log("Final answer: " + TensuraAiDebug.preview(answer, 700));
                  return answer;
               } else {
                  return "Raphael: No valid answer was returned.";
               }
            } else {
               return "Raphael: External thought circuit failed. Status " + response.statusCode() + ".";
            }
         } else {
            TensuraAiDebug.warn("Groq skipped because API key is missing.");
            return "Raphael: External thought circuit failed. API key missing.";
         }
      } else {
         TensuraAiDebug.warn("Groq skipped because wiki context is empty.");
         return "Raphael: I do not have enough Tensura records to answer that.";
      }
   }

   private static String buildBody(String question, String wikiContext) {
      String system = "You are Raphael, an in-game Tensura gameplay assistant.\n\nYou must answer using only the supplied wiki context.\nDo not use outside knowledge.\nDo not guess.\nDo not invent values, passives, costs, cooldowns, requirements, effects, skill names, or mechanics.\nIf the wiki context does not contain enough information, say exactly:\n\"Raphael: I do not have enough Tensura records to answer that.\"\n\nOnly answer gameplay questions about Tensura, Tensura addons, skills, races, EP, magicules, aura, spiritual health, bosses, equipment, effects, and survival.\n\nDo not answer coding, Java, APIs, real-world questions, politics, homework, or unrelated topics.\n\nKeep the answer under 120 words.\nSpeak like a calm analytical skill assistant.\n";
      String user = "Wiki context:\n" + wikiContext + "\n\nPlayer question:\n" + question;
      return "{\"model\":\""
         + escapeJson("llama-3.1-8b-instant")
         + "\",\"messages\":[{\"role\":\"system\",\"content\":\""
         + escapeJson(system)
         + "\"},{\"role\":\"user\",\"content\":\""
         + escapeJson(user)
         + "\"}],\"temperature\":0.0,\"max_completion_tokens\":240}";
   }

   private static String extractContent(String json) {
      if (json != null && !json.isBlank()) {
         String key = "\"content\":\"";
         int start = json.indexOf(key);
         if (start < 0) {
            return null;
         }

         start += key.length();
         StringBuilder builder = new StringBuilder();
         boolean escaping = false;

         for (int i = start; i < json.length(); i++) {
            char c = json.charAt(i);
            if (escaping) {
               builder.append('\\').append(c);
               escaping = false;
            } else if (c == '\\') {
               escaping = true;
            } else {
               if (c == '"') {
                  return builder.toString();
               }

               builder.append(c);
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private static String escapeJson(String input) {
      if (input == null) {
         return "";
      }

      StringBuilder builder = new StringBuilder();

      for (int i = 0; i < input.length(); i++) {
         char c = input.charAt(i);
         switch (c) {
            case '\b':
               builder.append("\\b");
               break;
            case '\t':
               builder.append("\\t");
               break;
            case '\n':
               builder.append("\\n");
               break;
            case '\f':
               builder.append("\\f");
               break;
            case '\r':
               builder.append("\\r");
               break;
            case '"':
               builder.append("\\\"");
               break;
            case '\\':
               builder.append("\\\\");
               break;
            default:
               if (c < ' ') {
                  builder.append(String.format("\\u%04x", Integer.valueOf(c)));
               } else {
                  builder.append(c);
               }
         }
      }

      return builder.toString();
   }

   private static String unescapeJson(String input) {
      if (input == null) {
         return "";
      }

      StringBuilder builder = new StringBuilder();
      boolean escaping = false;

      for (int i = 0; i < input.length(); i++) {
         char c = input.charAt(i);
         if (!escaping) {
            if (c == '\\') {
               escaping = true;
            } else {
               builder.append(c);
            }
         } else {
            switch (c) {
               case '"':
                  builder.append('"');
                  break;
               case '/':
                  builder.append('/');
                  break;
               case '\\':
                  builder.append('\\');
                  break;
               case 'b':
                  builder.append('\b');
                  break;
               case 'f':
                  builder.append('\f');
                  break;
               case 'n':
                  builder.append('\n');
                  break;
               case 'r':
                  builder.append('\r');
                  break;
               case 't':
                  builder.append('\t');
                  break;
               case 'u':
                  if (i + 4 < input.length()) {
                     String hex = input.substring(i + 1, i + 5);

                     try {
                        builder.append((char)Integer.parseInt(hex, 16));
                        i += 4;
                     } catch (NumberFormatException ignored) {
                        builder.append("\\u");
                     }
                  } else {
                     builder.append("\\u");
                  }
                  break;
               default:
                  builder.append(c);
            }

            escaping = false;
         }
      }

      if (escaping) {
         builder.append('\\');
      }

      return builder.toString();
   }
}
