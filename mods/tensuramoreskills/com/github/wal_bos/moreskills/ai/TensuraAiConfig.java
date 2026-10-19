package com.github.wal_bos.moreskills.ai;

public final class TensuraAiConfig {
   private static final String EMBEDDED_PRIVATE_GROQ_KEY = "PUT_YOUR_GROQ_KEY_HERE";
   public static final boolean ENABLED = true;
   public static final boolean REQUIRE_SKILL = false;
   public static final boolean USE_WIKI_CONTEXT = true;
   public static final String MODEL = "llama-3.1-8b-instant";
   public static final String SKILL_ID = "tensuramoreskills:raphael";
   public static final String WIKI_DOMAINS = "tensura.wiki.gg;trmysticism.wiki.gg;tensuranightmares.wiki.gg";
   public static final int COOLDOWN_SECONDS = 20;
   public static final int MAX_QUESTION_LENGTH = 300;
   public static final int MAX_ANSWER_LENGTH = 700;
   public static final int MAX_ACTIVE_REQUESTS = 3;
   public static final int WIKI_CACHE_MINUTES = 1440;
   public static final int MAX_WIKI_CONTEXT_LENGTH = 2400;
   public static final int WIKI_FETCH_TIMEOUT_SECONDS = 15;
   public static final int WIKI_SEARCH_RESULTS_PER_DOMAIN = 3;
   public static final int WIKI_MAX_PAGES_PER_QUESTION = 5;

   private TensuraAiConfig() {
   }

   public static String getResolvedApiKey() {
      return "PUT_YOUR_GROQ_KEY_HERE" != null && !"PUT_YOUR_GROQ_KEY_HERE".isBlank() && !"PUT_YOUR_GROQ_KEY_HERE".contains("PUT_YOUR")
         ? "PUT_YOUR_GROQ_KEY_HERE".trim()
         : "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";
   }
}
