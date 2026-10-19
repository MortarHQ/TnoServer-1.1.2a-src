package com.github.wal_bos.moreskills.ai;

import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TensuraWikiKnowledge {
   private static final HttpClient CLIENT = HttpClient.newBuilder().followRedirects(Redirect.NORMAL).build();
   private static final Map<String, TensuraWikiKnowledge.CachedPage> PAGE_CACHE = new ConcurrentHashMap<>();
   private static final Map<String, TensuraWikiKnowledge.CachedSearch> SEARCH_CACHE = new ConcurrentHashMap<>();

   private TensuraWikiKnowledge() {
   }

   public static String buildContext(String question) {
      String cleanQuestion = question == null ? "" : question.trim();
      if (cleanQuestion.isBlank()) {
         return "";
      }

      List<TensuraWikiKnowledge.ScoredPage> scoredPages = new ArrayList<>();
      Set<String> visited = new LinkedHashSet<>();
      List<TensuraWikiKnowledge.WikiPageRef> forced = forcedPages(cleanQuestion);

      for (TensuraWikiKnowledge.WikiPageRef page : forced) {
         if (visited.add(page.url())) {
            String text = getPageTextByTitle(page.domain(), page.title(), page.url());
            if (!text.isBlank()) {
               int pageScore = score(cleanQuestion, page.title() + " " + page.url() + " " + text) + 250;
               scoredPages.add(new TensuraWikiKnowledge.ScoredPage(page.url(), page.title(), text, pageScore));
            }
         }
      }

      for (String domain : splitList("tensura.wiki.gg;trmysticism.wiki.gg;tensuranightmares.wiki.gg")) {
         String cleanDomain = cleanDomain(domain);
         if (!cleanDomain.isBlank()) {
            for (TensuraWikiKnowledge.WikiPageRef result : searchDomain(cleanDomain, cleanQuestion)) {
               if (visited.size() >= Math.max(1, 5 + forced.size() + 12)) {
                  break;
               }

               if (visited.add(result.url())) {
                  String text = getPageTextByTitle(result.domain(), result.title(), result.url());
                  if (!text.isBlank()) {
                     int pageScore = score(cleanQuestion, result.title() + " " + result.url() + " " + text);
                     if (pageScore > 0) {
                        scoredPages.add(new TensuraWikiKnowledge.ScoredPage(result.url(), result.title(), text, pageScore));
                     }
                  }
               }
            }
         }
      }

      scoredPages.sort(Comparator.comparingInt(TensuraWikiKnowledge.ScoredPage::score).reversed());
      StringBuilder builder = new StringBuilder();

      for (TensuraWikiKnowledge.ScoredPage page : scoredPages) {
         if (builder.length() >= 2400) {
            break;
         }

         String usefulText = trimToUseful(page.text(), cleanQuestion, 2800);
         String part = "\nSource: " + page.url() + "\nTitle: " + page.title() + "\n" + usefulText + "\n";
         if (builder.length() + part.length() > 2400) {
            int remaining = Math.max(0, 2400 - builder.length());
            part = part.substring(0, Math.min(part.length(), remaining));
         }

         builder.append(part);
      }

      String result = builder.toString().trim();
      System.out.println("[RaphaelAI] Wiki context length: " + result.length() + " for question: " + cleanQuestion);
      return result;
   }

   private static List<TensuraWikiKnowledge.WikiPageRef> forcedPages(String question) {
      String q = normalize(question);
      List<TensuraWikiKnowledge.WikiPageRef> pages = new ArrayList<>();
      if (q.contains("spatial motion") || q.contains("spacial motion")) {
         pages.add(new TensuraWikiKnowledge.WikiPageRef("tensura.wiki.gg", "Spatial Motion", "https://tensura.wiki.gg/wiki/Spatial_Motion"));
      }

      if (q.contains("belphegor") || q.contains("lord of sloth") || q.contains("sloth")) {
         pages.add(new TensuraWikiKnowledge.WikiPageRef("trmysticism.wiki.gg", "Belphegor", "https://trmysticism.wiki.gg/wiki/Belphegor"));
         pages.add(new TensuraWikiKnowledge.WikiPageRef("trmysticism.wiki.gg", "Belphegor, Lord Of Sloth", "https://trmysticism.wiki.gg/wiki/Belphegor"));
         pages.add(new TensuraWikiKnowledge.WikiPageRef("trmysticism.wiki.gg", "Belphegor, Lord of Sloth", "https://trmysticism.wiki.gg/wiki/Belphegor"));
      }

      if (q.contains("mammon") || q.contains("lord of greed") || q.contains("greed")) {
         pages.add(
            new TensuraWikiKnowledge.WikiPageRef(
               "tensuranightmares.wiki.gg", "Mammon, Lord Of Greed", "https://tensuranightmares.wiki.gg/wiki/Mammon%2C_Lord_Of_Greed"
            )
         );
         pages.add(
            new TensuraWikiKnowledge.WikiPageRef(
               "tensuranightmares.wiki.gg", "Mammon, Lord of Greed", "https://tensuranightmares.wiki.gg/wiki/Mammon%2C_Lord_Of_Greed"
            )
         );
         pages.add(new TensuraWikiKnowledge.WikiPageRef("tensuranightmares.wiki.gg", "Mammon", "https://tensuranightmares.wiki.gg/wiki/Mammon"));
      }

      if (q.contains("predator")) {
         pages.add(new TensuraWikiKnowledge.WikiPageRef("tensura.wiki.gg", "Predator", "https://tensura.wiki.gg/wiki/Predator"));
      }

      if (q.contains("gluttony")) {
         pages.add(new TensuraWikiKnowledge.WikiPageRef("tensura.wiki.gg", "Gluttony", "https://tensura.wiki.gg/wiki/Gluttony"));
      }

      if (q.contains("beelzebub")) {
         pages.add(new TensuraWikiKnowledge.WikiPageRef("tensura.wiki.gg", "Beelzebub", "https://tensura.wiki.gg/wiki/Beelzebub"));
      }

      if (q.contains("usurper")) {
         pages.add(new TensuraWikiKnowledge.WikiPageRef("tensura.wiki.gg", "Usurper", "https://tensura.wiki.gg/wiki/Usurper"));
      }

      if (q.contains("great sage")) {
         pages.add(new TensuraWikiKnowledge.WikiPageRef("tensura.wiki.gg", "Great Sage", "https://tensura.wiki.gg/wiki/Great_Sage"));
      }

      if (q.contains("raphael")) {
         pages.add(new TensuraWikiKnowledge.WikiPageRef("tensura.wiki.gg", "Raphael", "https://tensura.wiki.gg/wiki/Raphael"));
         pages.add(new TensuraWikiKnowledge.WikiPageRef("tensura.wiki.gg", "Raphael, Lord of Wisdom", "https://tensura.wiki.gg/wiki/Raphael"));
      }

      return pages;
   }

   private static List<TensuraWikiKnowledge.WikiPageRef> searchDomain(String domain, String question) {
      String cacheKey = domain + "|" + normalize(question);
      long now = System.currentTimeMillis();
      TensuraWikiKnowledge.CachedSearch cached = SEARCH_CACHE.get(cacheKey);
      if (cached != null && now - cached.time() < 86400000L) {
         return cached.pages();
      }

      LinkedHashSet<TensuraWikiKnowledge.WikiPageRef> pages = new LinkedHashSet<>();
      String stripped = stripQuestionWords(normalize(question));
      pages.addAll(searchApi(domain, question));
      pages.addAll(searchApi(domain, stripped));
      pages.addAll(searchOpenSearch(domain, question));
      pages.addAll(searchOpenSearch(domain, stripped));
      pages.addAll(searchSpecial(domain, question));
      pages.addAll(searchSpecial(domain, stripped));
      pages.addAll(fallbackPages(domain, question));
      List<TensuraWikiKnowledge.WikiPageRef> result = new ArrayList<>(pages);
      SEARCH_CACHE.put(cacheKey, new TensuraWikiKnowledge.CachedSearch(result, now));
      return result;
   }

   private static List<TensuraWikiKnowledge.WikiPageRef> searchApi(String domain, String question) {
      List<TensuraWikiKnowledge.WikiPageRef> pages = new ArrayList<>();
      if (question != null && !question.isBlank()) {
         try {
            String encoded = URLEncoder.encode(question, StandardCharsets.UTF_8);
            String url = "https://" + domain + "/api.php?action=query&list=search&srsearch=" + encoded + "&srlimit=" + Math.max(1, 9) + "&format=json";
            HttpResponse<String> response = send(url, "application/json,text/plain,*/*");
            System.out.println("[RaphaelAI] Search API " + domain + " status: " + response.statusCode() + " query: " + question);
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
               pages.addAll(extractSearchResults(domain, response.body()));
            }
         } catch (Throwable throwable) {
            System.out.println("[RaphaelAI] Search API failed for " + domain + ": " + throwable.getClass().getSimpleName());
         }

         return pages;
      } else {
         return pages;
      }
   }

   private static List<TensuraWikiKnowledge.WikiPageRef> searchOpenSearch(String domain, String question) {
      List<TensuraWikiKnowledge.WikiPageRef> pages = new ArrayList<>();
      if (question != null && !question.isBlank()) {
         try {
            String encoded = URLEncoder.encode(question, StandardCharsets.UTF_8);
            String url = "https://" + domain + "/api.php?action=opensearch&search=" + encoded + "&limit=" + Math.max(1, 9) + "&namespace=0&format=json";
            HttpResponse<String> response = send(url, "application/json,text/plain,*/*");
            System.out.println("[RaphaelAI] OpenSearch " + domain + " status: " + response.statusCode() + " query: " + question);
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
               pages.addAll(extractOpenSearchResults(domain, response.body()));
            }
         } catch (Throwable throwable) {
            System.out.println("[RaphaelAI] OpenSearch failed for " + domain + ": " + throwable.getClass().getSimpleName());
         }

         return pages;
      } else {
         return pages;
      }
   }

   private static List<TensuraWikiKnowledge.WikiPageRef> searchSpecial(String domain, String question) {
      List<TensuraWikiKnowledge.WikiPageRef> pages = new ArrayList<>();
      if (question != null && !question.isBlank()) {
         try {
            String encoded = URLEncoder.encode(question, StandardCharsets.UTF_8);
            String url = "https://" + domain + "/wiki/Special:Search?search=" + encoded;
            HttpResponse<String> response = send(url, "text/html,application/xhtml+xml,text/plain,*/*");
            System.out.println("[RaphaelAI] Special search " + domain + " status: " + response.statusCode() + " query: " + question);
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
               pages.addAll(extractSpecialSearchResults(domain, response.body()));
            }
         } catch (Throwable throwable) {
            System.out.println("[RaphaelAI] Special search failed for " + domain + ": " + throwable.getClass().getSimpleName());
         }

         return pages;
      } else {
         return pages;
      }
   }

   private static List<TensuraWikiKnowledge.WikiPageRef> extractSearchResults(String domain, String json) {
      List<TensuraWikiKnowledge.WikiPageRef> pages = new ArrayList<>();
      if (json != null && !json.isBlank()) {
         try {
            Matcher matcher = Pattern.compile("\"title\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\"").matcher(json);

            while (matcher.find() && pages.size() < Math.max(1, 11)) {
               String title = unescapeJson(matcher.group(1)).trim();
               if (!title.isBlank()) {
                  pages.add(new TensuraWikiKnowledge.WikiPageRef(domain, title, "https://" + domain + "/wiki/" + encodeTitle(title)));
               }
            }
         } catch (Throwable throwable) {
            System.out.println("[RaphaelAI] Failed to extract search results: " + throwable.getClass().getSimpleName());
         }

         return pages;
      } else {
         return pages;
      }
   }

   private static List<TensuraWikiKnowledge.WikiPageRef> extractOpenSearchResults(String domain, String json) {
      List<TensuraWikiKnowledge.WikiPageRef> pages = new ArrayList<>();
      if (json != null && !json.isBlank()) {
         try {
            Matcher matcher = Pattern.compile("\"((?:\\\\.|[^\"\\\\])*)\"").matcher(json);
            List<String> strings = new ArrayList<>();

            while (matcher.find()) {
               strings.add(unescapeJson(matcher.group(1)));
            }

            for (int i = 1; i < strings.size() && pages.size() < Math.max(1, 11); i++) {
               String title = strings.get(i).trim();
               if (!title.isBlank() && !title.startsWith("http://") && !title.startsWith("https://") && title.length() <= 120) {
                  pages.add(new TensuraWikiKnowledge.WikiPageRef(domain, title, "https://" + domain + "/wiki/" + encodeTitle(title)));
               }
            }
         } catch (Throwable throwable) {
            System.out.println("[RaphaelAI] Failed to extract OpenSearch results: " + throwable.getClass().getSimpleName());
         }

         return pages;
      } else {
         return pages;
      }
   }

   private static List<TensuraWikiKnowledge.WikiPageRef> extractSpecialSearchResults(String domain, String html) {
      List<TensuraWikiKnowledge.WikiPageRef> pages = new ArrayList<>();
      if (html != null && !html.isBlank()) {
         try {
            Matcher matcher = Pattern.compile("href=\"/wiki/([^\"#:]+)\"").matcher(html);
            Set<String> seen = new LinkedHashSet<>();

            while (matcher.find() && pages.size() < Math.max(1, 11)) {
               String encodedTitle = matcher.group(1);
               if (!encodedTitle.contains("Special")
                  && !encodedTitle.contains("File")
                  && !encodedTitle.contains("Category")
                  && !encodedTitle.contains("Template")
                  && !encodedTitle.contains("Help")
                  && !encodedTitle.contains("User")
                  && !encodedTitle.contains("Talk")) {
                  String title = decodeUrlTitle(encodedTitle);
                  if (!title.isBlank() && title.length() <= 120 && seen.add(title)) {
                     pages.add(new TensuraWikiKnowledge.WikiPageRef(domain, title, "https://" + domain + "/wiki/" + encodedTitle));
                  }
               }
            }
         } catch (Throwable throwable) {
            System.out.println("[RaphaelAI] Failed to extract special search results: " + throwable.getClass().getSimpleName());
         }

         return pages;
      } else {
         return pages;
      }
   }

   private static List<TensuraWikiKnowledge.WikiPageRef> fallbackPages(String domain, String question) {
      List<TensuraWikiKnowledge.WikiPageRef> pages = new ArrayList<>();

      for (String title : candidateTitles(question)) {
         if (pages.size() >= Math.max(1, 27)) {
            break;
         }

         if (!title.isBlank()) {
            pages.add(new TensuraWikiKnowledge.WikiPageRef(domain, title, "https://" + domain + "/wiki/" + encodeTitle(title)));
         }
      }

      return pages;
   }

   private static LinkedHashSet<String> candidateTitles(String question) {
      String q = normalize(question);
      LinkedHashSet<String> titles = new LinkedHashSet<>();
      addKnownAliases(titles, q);
      String stripped = stripQuestionWords(q);
      String compact = stripped.replaceAll("\\s+", " ").trim();
      if (!compact.isBlank()) {
         addTitleVariants(titles, compact);
      }

      String[] words = compact.isBlank() ? new String[0] : compact.split("\\s+");

      for (String word : words) {
         String clean = cleanTitleToken(word);
         if (clean.length() >= 3) {
            addTitleVariants(titles, clean);
         }
      }

      for (int size = Math.min(8, words.length); size >= 2; size--) {
         for (int i = 0; i <= words.length - size; i++) {
            StringBuilder builder = new StringBuilder();

            for (int j = 0; j < size; j++) {
               String clean = cleanTitleToken(words[i + j]);
               if (!clean.isBlank()) {
                  if (!builder.isEmpty()) {
                     builder.append(" ");
                  }

                  builder.append(clean);
               }
            }

            String phrase = builder.toString().trim();
            if (phrase.length() >= 4) {
               addTitleVariants(titles, phrase);
            }
         }
      }

      addLordVariants(titles);
      addCommaVariants(titles);
      addColonVariants(titles);
      addApostropheVariants(titles);
      addNoPunctuationVariants(titles);
      addOfCaseVariants(titles);
      addUnderscoreVariants(titles);
      addCommonSkillSuffixVariants(titles);
      addCommonSkillPrefixVariants(titles);
      addCondensedVariants(titles);
      trimTitleSet(titles, 90);
      return titles;
   }

   private static void addKnownAliases(LinkedHashSet<String> titles, String q) {
      if (q.contains("spatial motion") || q.contains("spacial motion")) {
         titles.add("Spatial Motion");
         titles.add("Spacial Motion");
      }

      if (q.contains("spatial")) {
         titles.add("Spatial Motion");
         titles.add("Spatial Domination");
         titles.add("Spatial Manipulation");
      }

      if (q.contains("usurper")) {
         titles.add("Usurper");
         titles.add("Usurper Skill");
      }

      if (q.contains("predator")) {
         titles.add("Predator");
         titles.add("Predation");
      }

      if (q.contains("great sage")) {
         titles.add("Great Sage");
      }

      if (q.contains("wise one")) {
         titles.add("Wise One");
      }

      if (q.contains("raphael")) {
         titles.add("Raphael");
         titles.add("Raphael, Lord Of Wisdom");
         titles.add("Raphael, Lord of Wisdom");
      }

      if (q.contains("ciel")) {
         titles.add("Ciel");
      }

      if (q.contains("beelzebub")) {
         titles.add("Beelzebub");
         titles.add("Beelzebub, Lord Of Gluttony");
         titles.add("Beelzebub, Lord of Gluttony");
      }

      if (q.contains("gluttony")) {
         titles.add("Gluttony");
         titles.add("Gluttony, Lord Of Gluttony");
         titles.add("Beelzebub");
      }

      if (q.contains("belphegor") || q.contains("lord of sloth") || q.contains("sloth")) {
         titles.add("Belphegor");
         titles.add("Belphegor, Lord Of Sloth");
         titles.add("Belphegor, Lord of Sloth");
         titles.add("Belphegor Lord Of Sloth");
         titles.add("Belphegor Lord of Sloth");
         titles.add("Sloth");
      }

      if (q.contains("mammon") || q.contains("lord of greed") || q.contains("greed")) {
         titles.add("Mammon, Lord Of Greed");
         titles.add("Mammon, Lord of Greed");
         titles.add("Mammon Lord Of Greed");
         titles.add("Mammon Lord of Greed");
         titles.add("Mammon");
         titles.add("Greed");
      }

      if (q.contains("lucifer") || q.contains("pride")) {
         titles.add("Lucifer");
         titles.add("Lucifer, Lord Of Pride");
         titles.add("Lucifer, Lord of Pride");
         titles.add("Pride");
      }

      if (q.contains("satanael") || q.contains("wrath")) {
         titles.add("Satanael");
         titles.add("Satanael, Lord Of Wrath");
         titles.add("Satanael, Lord of Wrath");
         titles.add("Wrath");
      }

      if (q.contains("asmodeus") || q.contains("lust")) {
         titles.add("Asmodeus");
         titles.add("Asmodeus, Lord Of Lust");
         titles.add("Asmodeus, Lord of Lust");
         titles.add("Lust");
      }

      if (q.contains("leviathan") || q.contains("envy")) {
         titles.add("Leviathan");
         titles.add("Leviathan, Lord Of Envy");
         titles.add("Leviathan, Lord of Envy");
         titles.add("Envy");
      }

      if (q.contains("metatron")) {
         titles.add("Metatron");
         titles.add("Metatron, Lord Of Purity");
         titles.add("Metatron, Lord of Purity");
      }

      if (q.contains("uriel")) {
         titles.add("Uriel");
         titles.add("Uriel, Lord Of Vows");
         titles.add("Uriel, Lord of Vows");
      }

      if (q.contains("michael")) {
         titles.add("Michael");
         titles.add("Michael, Lord Of Justice");
         titles.add("Michael, Lord of Justice");
      }

      if (q.contains("azathoth")) {
         titles.add("Azathoth");
         titles.add("Azathoth, God Of Void");
         titles.add("Azathoth, God of Void");
      }

      if (q.contains("shub")) {
         titles.add("Shub-Niggurath");
         titles.add("Shub Niggurath");
      }

      if (q.contains("yog")) {
         titles.add("Yog-Sothoth");
         titles.add("Yog Sothoth");
      }

      if (q.contains("harvest festival")) {
         titles.add("Harvest Festival");
      }

      if (q.contains("demon lord seed")) {
         titles.add("Demon Lord Seed");
      }

      if (q.contains("true demon lord")) {
         titles.add("True Demon Lord");
      }

      if (q.contains("hero egg")) {
         titles.add("Hero's Egg");
         titles.add("Heros Egg");
         titles.add("Hero Egg");
      }

      if (q.contains("spiritual health")) {
         titles.add("Spiritual Health");
      }

      if (q.contains("magicule")) {
         titles.add("Magicule");
         titles.add("Magicules");
      }

      if (q.contains("aura")) {
         titles.add("Aura");
      }

      if (q.contains("ep") || q.contains("existence point")) {
         titles.add("Existence Point");
         titles.add("Existence Points");
         titles.add("EP");
      }
   }

   private static String getPageTextByTitle(String domain, String title, String pageUrl) {
      String cacheKey = domain + "|" + title;
      long now = System.currentTimeMillis();
      TensuraWikiKnowledge.CachedPage cached = PAGE_CACHE.get(cacheKey);
      if (cached != null && now - cached.time() < 86400000L) {
         return cached.text();
      }

      String text = getExtractApi(domain, title);
      if (text.isBlank()) {
         text = getParseWikitextApi(domain, title);
      }

      if (text.isBlank()) {
         text = getRawPage(pageUrl);
      }

      if (text.isBlank()) {
         text = getHtmlPage(pageUrl);
      }

      if (!text.isBlank()) {
         text = cleanWikiText(text);
         PAGE_CACHE.put(cacheKey, new TensuraWikiKnowledge.CachedPage(text, now));
         System.out.println("[RaphaelAI] Loaded page " + domain + " / " + title + " length " + text.length());
      } else {
         System.out.println("[RaphaelAI] Failed page " + domain + " / " + title);
      }

      return text;
   }

   private static String getExtractApi(String domain, String title) {
      try {
         String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8);
         String url = "https://"
            + domain
            + "/api.php?action=query&prop=extracts&explaintext=1&exsectionformat=plain&redirects=1&titles="
            + encodedTitle
            + "&format=json";
         HttpResponse<String> response = send(url, "application/json,text/plain,*/*");
         return response.statusCode() >= 200 && response.statusCode() < 300 ? readJsonString(response.body(), "extract") : "";
      } catch (Throwable ignored) {
         return "";
      }
   }

   private static String getParseWikitextApi(String domain, String title) {
      try {
         String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8);
         String url = "https://" + domain + "/api.php?action=parse&page=" + encodedTitle + "&prop=wikitext&format=json&redirects=1";
         HttpResponse<String> response = send(url, "application/json,text/plain,*/*");
         if (response.statusCode() >= 200 && response.statusCode() < 300) {
            String text = readJsonString(response.body(), "*");
            return !text.isBlank() ? text : readJsonString(response.body(), "wikitext");
         } else {
            return "";
         }
      } catch (Throwable ignored) {
         return "";
      }
   }

   private static String getRawPage(String pageUrl) {
      try {
         String url = pageUrl + "?action=raw";
         HttpResponse<String> response = send(url, "text/plain,text/html,*/*");
         return response.statusCode() >= 200 && response.statusCode() < 300 ? response.body() : "";
      } catch (Throwable ignored) {
         return "";
      }
   }

   private static String getHtmlPage(String pageUrl) {
      try {
         HttpResponse<String> response = send(pageUrl, "text/html,application/xhtml+xml,text/plain,*/*");
         return response.statusCode() >= 200 && response.statusCode() < 300 ? cleanHtml(response.body()) : "";
      } catch (Throwable ignored) {
         return "";
      }
   }

   private static HttpResponse<String> send(String url, String accept) throws IOException, InterruptedException {
      HttpRequest request = HttpRequest.newBuilder()
         .uri(URI.create(url))
         .timeout(Duration.ofSeconds(Math.max(3, 15)))
         .header("User-Agent", "Mozilla/5.0 TensuraMoreSkillsRaphael/1.0")
         .header("Accept", accept)
         .header("Accept-Language", "en-US,en;q=0.9")
         .GET()
         .build();
      return CLIENT.send(request, BodyHandlers.ofString(StandardCharsets.UTF_8));
   }

   private static String readJsonString(String json, String key) {
      if (json != null && !json.isBlank()) {
         try {
            Pattern pattern = Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\"", 32);
            Matcher matcher = pattern.matcher(json);
            return !matcher.find() ? "" : unescapeJson(matcher.group(1)).trim();
         } catch (Throwable throwable) {
            System.out.println("[RaphaelAI] Failed readJsonString for key " + key + ": " + throwable.getClass().getSimpleName());
            return "";
         }
      } else {
         return "";
      }
   }

   private static String cleanWikiText(String text) {
      if (text != null && !text.isBlank()) {
         String cleaned = text;
         cleaned = removeBetweenIgnoreCase(cleaned, "<script", "</script>");
         cleaned = removeBetweenIgnoreCase(cleaned, "<style", "</style>");
         cleaned = stripHtmlTagsSafe(cleaned);
         cleaned = removeSimpleTemplatesSafe(cleaned);
         cleaned = cleanWikiLinksSafe(cleaned);
         cleaned = cleaned.replace("'''", "");
         cleaned = cleaned.replace("''", "");
         cleaned = decodeEntities(cleaned);
         cleaned = collapseSpacesSafe(cleaned);
         if (cleaned.length() > 30000) {
            cleaned = cleaned.substring(0, 30000);
         }

         return cleaned.trim();
      } else {
         return "";
      }
   }

   private static String cleanHtml(String html) {
      if (html != null && !html.isBlank()) {
         String text = html;
         text = removeBetweenIgnoreCase(text, "<script", "</script>");
         text = removeBetweenIgnoreCase(text, "<style", "</style>");
         text = removeBetweenIgnoreCase(text, "<noscript", "</noscript>");
         text = removeBetweenIgnoreCase(text, "<svg", "</svg>");
         text = removeBetweenIgnoreCase(text, "<header", "</header>");
         text = removeBetweenIgnoreCase(text, "<footer", "</footer>");
         text = removeBetweenIgnoreCase(text, "<nav", "</nav>");
         text = removeBetweenIgnoreCase(text, "<aside", "</aside>");
         text = removeBetweenIgnoreCase(text, "<table", "</table>");
         text = removeBetweenIgnoreCase(text, "<sup", "</sup>");
         text = stripHtmlTagsSafe(text);
         text = decodeEntities(text);
         text = collapseSpacesSafe(text);
         int contentStart = lowerIndexOf(text, "contents");
         if (contentStart >= 0 && contentStart < Math.min(text.length(), 5000)) {
            text = text.substring(contentStart);
         }

         if (text.length() > 30000) {
            text = text.substring(0, 30000);
         }

         return text.trim();
      } else {
         return "";
      }
   }

   private static String cleanWikiLinksSafe(String input) {
      if (input != null && !input.isBlank()) {
         StringBuilder output = new StringBuilder(input.length());
         int index = 0;

         while (index < input.length()) {
            int start = input.indexOf("[[", index);
            if (start < 0) {
               output.append(input, index, input.length());
               break;
            }

            output.append(input, index, start);
            int end = input.indexOf("]]", start + 2);
            if (end < 0) {
               output.append(' ');
               index = start + 2;
            } else {
               String inside = input.substring(start + 2, end);
               int pipe = inside.lastIndexOf(124);
               if (pipe >= 0 && pipe + 1 < inside.length()) {
                  output.append(inside.substring(pipe + 1));
               } else {
                  output.append(inside);
               }

               output.append(' ');
               index = end + 2;
            }
         }

         return output.toString();
      } else {
         return "";
      }
   }

   private static String removeBetweenIgnoreCase(String input, String startToken, String endToken) {
      if (input != null && !input.isBlank()) {
         StringBuilder output = new StringBuilder(input.length());
         String lower = input.toLowerCase(Locale.ROOT);
         String startLower = startToken.toLowerCase(Locale.ROOT);
         String endLower = endToken.toLowerCase(Locale.ROOT);
         int index = 0;

         while (index < input.length()) {
            int start = lower.indexOf(startLower, index);
            if (start < 0) {
               output.append(input, index, input.length());
               break;
            }

            output.append(input, index, start);
            int end = lower.indexOf(endLower, start);
            if (end < 0) {
               break;
            }

            index = end + endLower.length();
            output.append(' ');
         }

         return output.toString();
      } else {
         return "";
      }
   }

   private static String stripHtmlTagsSafe(String input) {
      if (input != null && !input.isBlank()) {
         StringBuilder builder = new StringBuilder(input.length());
         boolean insideTag = false;

         for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '<') {
               insideTag = true;
               builder.append(' ');
            } else if (c == '>') {
               insideTag = false;
               builder.append(' ');
            } else if (!insideTag) {
               builder.append(c);
            }
         }

         return builder.toString();
      } else {
         return "";
      }
   }

   private static String removeSimpleTemplatesSafe(String input) {
      if (input != null && !input.isBlank()) {
         StringBuilder builder = new StringBuilder(input.length());
         int depth = 0;

         for (int i = 0; i < input.length(); i++) {
            if (i + 1 < input.length() && input.charAt(i) == '{' && input.charAt(i + 1) == '{') {
               depth++;
               i++;
               builder.append(' ');
            } else if (i + 1 < input.length() && input.charAt(i) == '}' && input.charAt(i + 1) == '}') {
               if (depth > 0) {
                  depth--;
               }

               i++;
               builder.append(' ');
            } else if (depth <= 0) {
               builder.append(input.charAt(i));
            }
         }

         return builder.toString();
      } else {
         return "";
      }
   }

   private static String collapseSpacesSafe(String input) {
      if (input != null && !input.isBlank()) {
         StringBuilder builder = new StringBuilder(input.length());
         boolean lastWasSpace = false;

         for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isWhitespace(c)) {
               if (!lastWasSpace) {
                  builder.append(' ');
                  lastWasSpace = true;
               }
            } else {
               builder.append(c);
               lastWasSpace = false;
            }
         }

         return builder.toString().trim();
      } else {
         return "";
      }
   }

   private static String decodeEntities(String text) {
      return text != null && !text.isBlank()
         ? text.replace("&nbsp;", " ")
            .replace("&amp;", "&")
            .replace("&quot;", "\"")
            .replace("&#39;", "'")
            .replace("&apos;", "'")
            .replace("&lt;", "<")
            .replace("&gt;", ">")
            .replace("&#160;", " ")
         : "";
   }

   private static String unescapeJson(String input) {
      if (input != null && !input.isBlank()) {
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
      } else {
         return "";
      }
   }

   private static int lowerIndexOf(String text, String search) {
      return text != null && search != null ? text.toLowerCase(Locale.ROOT).indexOf(search.toLowerCase(Locale.ROOT)) : -1;
   }

   private static int score(String question, String text) {
      String q = normalize(question);
      String t = normalize(text);
      int score = 0;

      for (String token : q.split(" ")) {
         if (token.length() >= 4 && t.contains(token)) {
            score += 5;
         }
      }

      if (q.contains("spatial") && t.contains("spatial")) {
         score += 25;
      }

      if (q.contains("motion") && t.contains("motion")) {
         score += 15;
      }

      if (q.contains("belphegor") && t.contains("belphegor")) {
         score += 40;
      }

      if (q.contains("sloth") && t.contains("sloth")) {
         score += 25;
      }

      if (q.contains("mammon") && t.contains("mammon")) {
         score += 40;
      }

      if (q.contains("greed") && t.contains("greed")) {
         score += 25;
      }

      if (q.contains("predator") && t.contains("predator")) {
         score += 40;
      }

      if (q.contains("usurper") && t.contains("usurper")) {
         score += 50;
      }

      if (q.contains("cost") && t.contains("cost")) {
         score += 10;
      }

      if (q.contains("cooldown") && t.contains("cooldown")) {
         score += 10;
      }

      if (q.contains("passive") && t.contains("passive")) {
         score += 10;
      }

      if (q.contains("active") && t.contains("active")) {
         score += 10;
      }

      return score;
   }

   private static String trimToUseful(String text, String question, int max) {
      if (text != null && !text.isBlank()) {
         if (text.length() <= max) {
            return text;
         }

         String lowerText = text.toLowerCase(Locale.ROOT);
         String lowerQuestion = question == null ? "" : question.toLowerCase(Locale.ROOT);
         int best = -1;

         for (String token : lowerQuestion.split("\\s+")) {
            String clean = token.replaceAll("[^a-zA-Z0-9]", "").toLowerCase(Locale.ROOT);
            if (clean.length() >= 4) {
               int index = lowerText.indexOf(clean);
               if (index >= 0) {
                  best = index;
                  break;
               }
            }
         }

         if (best < 0) {
            return text.substring(0, max);
         }

         int start = Math.max(0, best - max / 3);
         int end = Math.min(text.length(), start + max);
         return text.substring(start, end);
      } else {
         return "";
      }
   }

   private static String stripQuestionWords(String input) {
      String stripped = " " + normalize(input) + " ";
      String[] phrases = new String[]{
         " what does ",
         " what do ",
         " what is ",
         " what are ",
         " whats ",
         " what's ",
         " how do i get ",
         " how do you get ",
         " how to get ",
         " where do i get ",
         " where to get ",
         " how does ",
         " how can i ",
         " can i ",
         " do i ",
         " does it ",
         " is it ",
         " tell me about ",
         " explain ",
         " show me ",
         " give me ",
         " find ",
         " in tensura ",
         " in the tensura mod ",
         " in mod ",
         " from wiki ",
         " on wiki ",
         " ultimate skill ",
         " unique skill ",
         " intrinsic skill ",
         " extra skill ",
         " common skill ",
         " skill ",
         " ability ",
         " passive ",
         " passives ",
         " active ",
         " actives ",
         " mode ",
         " modes ",
         " cost ",
         " costs ",
         " cooldown ",
         " cooldowns ",
         " requirement ",
         " requirements ",
         " obtain ",
         " obtained ",
         " get ",
         " use ",
         " uses ",
         " work ",
         " works ",
         " do ",
         " does ",
         " the ",
         " a ",
         " an ",
         " and ",
         " or ",
         " of the ",
         " for ",
         " with ",
         " to "
      };

      for (String phrase : phrases) {
         stripped = stripped.replace(phrase, " ");
      }

      return stripped.replaceAll("\\s+", " ").trim();
   }

   private static void addTitleVariants(LinkedHashSet<String> titles, String input) {
      String clean = normalizeTitleInput(input);
      if (!clean.isBlank()) {
         titles.add(toTitleCase(clean));
         titles.add(toSmartTitleCase(clean));
         titles.add(clean);
         titles.add(clean.toUpperCase(Locale.ROOT));
         titles.add(clean.toLowerCase(Locale.ROOT));
         if (clean.contains(" ")) {
            titles.add(toTitleCase(clean).replace(" ", "_"));
            titles.add(toSmartTitleCase(clean).replace(" ", "_"));
            titles.add(clean.replace(" ", "_"));
         }

         if (clean.contains("_")) {
            titles.add(toTitleCase(clean.replace("_", " ")));
            titles.add(toSmartTitleCase(clean.replace("_", " ")));
         }

         if (clean.contains("-")) {
            titles.add(toTitleCase(clean.replace("-", " ")));
            titles.add(toSmartTitleCase(clean.replace("-", " ")));
            titles.add(toTitleCase(clean.replace("-", "")));
         }

         if (clean.endsWith("s") && clean.length() > 4) {
            titles.add(toTitleCase(clean.substring(0, clean.length() - 1)));
         }

         titles.add(toTitleCase(clean) + " Skill");
         titles.add(toTitleCase(clean) + " Ability");
      }
   }

   private static void addLordVariants(LinkedHashSet<String> titles) {
      for (String title : new LinkedHashSet<>(titles)) {
         String n = normalizeTitleInput(title);
         if (!n.isBlank()) {
            if (n.contains("lord of")) {
               String[] parts = n.split("lord of", 2);
               String left = parts[0].trim();
               String right = parts.length > 1 ? parts[1].trim() : "";
               if (!left.isBlank() && !right.isBlank()) {
                  titles.add(toTitleCase(left) + ", Lord Of " + toTitleCase(right));
                  titles.add(toTitleCase(left) + ", Lord of " + toTitleCase(right));
                  titles.add(toTitleCase(left) + " Lord Of " + toTitleCase(right));
                  titles.add(toTitleCase(left) + " Lord of " + toTitleCase(right));
               }
            }

            if (n.contains("god of")) {
               String[] parts = n.split("god of", 2);
               String left = parts[0].trim();
               String right = parts.length > 1 ? parts[1].trim() : "";
               if (!left.isBlank() && !right.isBlank()) {
                  titles.add(toTitleCase(left) + ", God Of " + toTitleCase(right));
                  titles.add(toTitleCase(left) + ", God of " + toTitleCase(right));
                  titles.add(toTitleCase(left) + " God Of " + toTitleCase(right));
                  titles.add(toTitleCase(left) + " God of " + toTitleCase(right));
               }
            }
         }
      }
   }

   private static void addCommaVariants(LinkedHashSet<String> titles) {
      for (String title : new LinkedHashSet<>(titles)) {
         if (title.contains(", ")) {
            titles.add(title.replace(", ", " "));
            titles.add(title.replace(", ", ",_"));
         } else {
            String lower = title.toLowerCase(Locale.ROOT);
            if (lower.contains(" lord of ")) {
               int index = lower.indexOf(" lord of ");
               titles.add(title.substring(0, index) + ", " + title.substring(index + 1));
            }

            if (lower.contains(" god of ")) {
               int index = lower.indexOf(" god of ");
               titles.add(title.substring(0, index) + ", " + title.substring(index + 1));
            }
         }
      }
   }

   private static void addColonVariants(LinkedHashSet<String> titles) {
      for (String title : new LinkedHashSet<>(titles)) {
         if (title.contains(":")) {
            titles.add(title.replace(":", ""));
            titles.add(title.replace(":", " "));
         }
      }
   }

   private static void addApostropheVariants(LinkedHashSet<String> titles) {
      for (String title : new LinkedHashSet<>(titles)) {
         if (title.contains("'")) {
            titles.add(title.replace("'", ""));
            titles.add(title.replace("'", "’"));
         }

         if (title.toLowerCase(Locale.ROOT).contains("hero egg")) {
            titles.add("Hero's Egg");
            titles.add("Hero’s Egg");
            titles.add("Heros Egg");
         }
      }
   }

   private static void addNoPunctuationVariants(LinkedHashSet<String> titles) {
      for (String title : new LinkedHashSet<>(titles)) {
         String clean = title.replace(",", " ").replace(":", " ").replace(";", " ").replace("'", "").replace("’", "").replace("-", " ");
         clean = clean.replaceAll("\\s+", " ").trim();
         if (!clean.isBlank()) {
            titles.add(clean);
            titles.add(toTitleCase(clean));
         }
      }
   }

   private static void addOfCaseVariants(LinkedHashSet<String> titles) {
      for (String title : new LinkedHashSet<>(titles)) {
         titles.add(title.replace(" Of ", " of "));
         titles.add(title.replace(" of ", " Of "));
      }
   }

   private static void addUnderscoreVariants(LinkedHashSet<String> titles) {
      for (String title : new LinkedHashSet<>(titles)) {
         if (title.contains(" ")) {
            titles.add(title.replace(" ", "_"));
         }

         if (title.contains("_")) {
            titles.add(title.replace("_", " "));
         }
      }
   }

   private static void addCommonSkillSuffixVariants(LinkedHashSet<String> titles) {
      for (String title : new LinkedHashSet<>(titles)) {
         String lower = title.toLowerCase(Locale.ROOT);
         if (!lower.endsWith(" skill") && !lower.endsWith(" ability") && !lower.endsWith(" art") && !lower.endsWith(" magic")) {
            titles.add(title + " Skill");
            titles.add(title + " Ability");
            titles.add(title + " Art");
            titles.add(title + " Magic");
         }
      }
   }

   private static void addCommonSkillPrefixVariants(LinkedHashSet<String> titles) {
      for (String title : new LinkedHashSet<>(titles)) {
         String lower = title.toLowerCase(Locale.ROOT);
         if (!lower.startsWith("lord ") && !lower.startsWith("god ") && !lower.startsWith("unique ") && !lower.startsWith("ultimate ")) {
            titles.add("Unique Skill " + title);
            titles.add("Ultimate Skill " + title);
            titles.add("Extra Skill " + title);
            titles.add("Intrinsic Skill " + title);
         }
      }
   }

   private static void addCondensedVariants(LinkedHashSet<String> titles) {
      for (String title : new LinkedHashSet<>(titles)) {
         String condensed = title.replace(" ", "").replace("_", "").replace(",", "").replace("-", "").replace("'", "").replace("’", "").trim();
         if (condensed.length() >= 4) {
            titles.add(condensed);
         }
      }
   }

   private static String cleanTitleToken(String token) {
      return token == null ? "" : token.replaceAll("[^a-zA-Z0-9,'_\\-’]", "").trim();
   }

   private static String normalizeTitleInput(String input) {
      return input == null ? "" : input.replace("_", " ").replaceAll("[^a-zA-Z0-9,'\\-’ ]", " ").replaceAll("\\s+", " ").trim();
   }

   private static void trimTitleSet(LinkedHashSet<String> titles, int max) {
      while (titles.size() > max) {
         String last = null;

         for (String title : titles) {
            last = title;
         }

         if (last == null) {
            break;
         }

         titles.remove(last);
      }
   }

   private static String toTitleCase(String input) {
      if (input != null && !input.isBlank()) {
         String[] words = input.trim().split("\\s+");
         StringBuilder builder = new StringBuilder();

         for (String word : words) {
            if (!word.isBlank()) {
               if (!builder.isEmpty()) {
                  builder.append(" ");
               }

               builder.append(Character.toUpperCase(word.charAt(0)));
               if (word.length() > 1) {
                  builder.append(word.substring(1));
               }
            }
         }

         return builder.toString();
      } else {
         return "";
      }
   }

   private static String toSmartTitleCase(String input) {
      if (input != null && !input.isBlank()) {
         String[] words = input.trim().split("\\s+");
         StringBuilder builder = new StringBuilder();

         for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (!word.isBlank()) {
               if (!builder.isEmpty()) {
                  builder.append(" ");
               }

               String lower = word.toLowerCase(Locale.ROOT);
               if (i <= 0
                  || !lower.equals("of") && !lower.equals("the") && !lower.equals("and") && !lower.equals("or") && !lower.equals("to") && !lower.equals("in")) {
                  builder.append(Character.toUpperCase(lower.charAt(0)));
                  if (lower.length() > 1) {
                     builder.append(lower.substring(1));
                  }
               } else {
                  builder.append(lower);
               }
            }
         }

         return builder.toString();
      } else {
         return "";
      }
   }

   private static String decodeUrlTitle(String input) {
      try {
         return URLDecoder.decode(input.replace("_", " "), StandardCharsets.UTF_8).trim();
      } catch (Throwable ignored) {
         return input == null ? "" : input.replace("_", " ").trim();
      }
   }

   private static String cleanDomain(String domain) {
      String clean = domain == null ? "" : domain.trim().toLowerCase(Locale.ROOT);
      clean = clean.replace("https://", "");
      clean = clean.replace("http://", "");
      int slash = clean.indexOf(47);
      if (slash >= 0) {
         clean = clean.substring(0, slash);
      }

      return !clean.endsWith(".wiki.gg") ? "" : clean;
   }

   private static String encodeTitle(String title) {
      return URLEncoder.encode(title.replace(" ", "_"), StandardCharsets.UTF_8).replace("+", "_");
   }

   private static String normalize(String input) {
      return input == null
         ? ""
         : input.toLowerCase(Locale.ROOT)
            .replace("_", " ")
            .replace("-", " ")
            .replace(",", " ")
            .replace(".", " ")
            .replace("?", " ")
            .replace("!", " ")
            .replace("’", "'")
            .replace("spacial", "spatial")
            .replaceAll("\\s+", " ")
            .trim();
   }

   private static List<String> splitList(String input) {
      List<String> list = new ArrayList<>();
      if (input != null && !input.isBlank()) {
         for (String part : input.split(";")) {
            String clean = part.trim();
            if (!clean.isEmpty()) {
               list.add(clean);
            }
         }

         return list;
      } else {
         return list;
      }
   }

   private record CachedPage(String text, long time) {
   }

   private record CachedSearch(List<TensuraWikiKnowledge.WikiPageRef> pages, long time) {
   }

   private record ScoredPage(String url, String title, String text, int score) {
   }

   private record WikiPageRef(String domain, String title, String url) {
   }
}
