package com.nododiiiii.ponderer;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

public class Config {
   private static final Builder SERVER_BUILDER = new Builder();
   public static final BooleanValue ENABLE_BLUEPRINT_ITEM = SERVER_BUILDER.comment(
         new String[]{
            "Enable Ponderer's built-in Blueprint item.",
            "This is a server-side setting synced to clients.",
            "When disabled, clients may still use their configured carrier item,",
            "but ponderer:blueprint will not appear in creative tabs and cannot be used as the carrier."
         }
      )
      .define("enableBlueprintItem", false);
   public static final ModConfigSpec SERVER_SPEC = SERVER_BUILDER.build();
   private static final Builder CLIENT_BUILDER = new Builder();
   public static final ConfigValue<String> BLUEPRINT_CARRIER_ITEM = CLIENT_BUILDER.comment(
         new String[]{
            "Which client-side item activates the Blueprint selection tool.",
            "Set to a different item (e.g. 'create:schematic_and_quill') to piggyback on it.",
            "If set to 'ponderer:blueprint', it only works when the server enables the built-in Blueprint item."
         }
      )
      .define("blueprintCarrierItem", "minecraft:paper");
   public static final BooleanValue DEVELOPER_MODE = CLIENT_BUILDER.comment(
         new String[]{"Allow editing scenes that have their 'editable' flag disabled.", "This is intended for pack authors and advanced maintenance."}
      )
      .define("developerMode", false);
   public static final BooleanValue DEFAULT_EDITABLE = CLIENT_BUILDER.comment(
         "Default editable value for newly created scenes and scenes without an explicit editable flag."
      )
      .define("defaultEditable", true);
   public static final ConfigValue<String> AI_PROVIDER = CLIENT_BUILDER.comment(
         new String[]{"LLM provider type: 'anthropic' or 'openai' (OpenAI-compatible).", "Use 'openai' for OpenAI, DeepSeek, Groq, Ollama, LM Studio, etc."}
      )
      .define("ai.provider", "openai");
   public static final ConfigValue<String> AI_API_BASE_URL = CLIENT_BUILDER.comment(
         new String[]{
            "API base URL. Leave empty to use provider defaults.",
            "Anthropic default: https://api.anthropic.com",
            "OpenAI default: https://api.openai.com",
            "For compatible APIs, set to e.g. https://api.deepseek.com"
         }
      )
      .define("ai.apiBaseUrl", "");
   public static final ConfigValue<String> AI_API_KEY = CLIENT_BUILDER.comment("API key for the selected provider.").define("ai.apiKey", "");
   public static final ConfigValue<String> AI_MODEL = CLIENT_BUILDER.comment(
         new String[]{"Model name. Leave empty to use provider defaults.", "Anthropic default: claude-sonnet-4-20250514", "OpenAI default: gpt-4o"}
      )
      .define("ai.model", "");
   public static final ConfigValue<String> AI_PROXY = CLIENT_BUILDER.comment(
         new String[]{"HTTP proxy for AI API calls. Format: host:port (e.g. 127.0.0.1:7890).", "Leave empty for no proxy."}
      )
      .define("ai.proxy", "");
   public static final BooleanValue AI_TRUST_ALL_SSL = CLIENT_BUILDER.comment(
         new String[]{
            "Trust all SSL certificates (disable verification).",
            "Enable this if you use a proxy that does SSL interception.",
            "WARNING: only enable when using a trusted local proxy."
         }
      )
      .define("ai.trustAllSsl", false);
   public static final BooleanValue AI_WEB_USE_PROXY = CLIENT_BUILDER.comment(
         new String[]{
            "Use the AI proxy for web page fetching (reference URLs).",
            "When enabled, reference URL requests go through the proxy configured above.",
            "When disabled, reference URLs are fetched with a direct connection."
         }
      )
      .define("ai.webUseProxy", false);
   public static final IntValue AI_MAX_TOKENS = CLIENT_BUILDER.comment(
         new String[]{
            "Maximum number of tokens the LLM can generate per request.",
            "Increase this if complex scenes are being cut off.",
            "WARNING: Some models have lower limits:",
            "  - Claude 3.5 Haiku: max 8192 tokens",
            "  - Other Claude models: max 4096 tokens",
            "  - GPT-4o / GPT-4o mini: max 4096 tokens",
            "Default: 16384. Range: 1024-65536. Adjust based on your model limits."
         }
      )
      .defineInRange("ai.maxTokens", 16384, 1024, 65536);
   public static final BooleanValue PACK_ORPHAN_PROMPT = CLIENT_BUILDER.comment(
         new String[]{
            "Show a chat prompt when a registered pack's scripts have all been deleted.",
            "The prompt offers to unregister the pack from the registry.",
            "Set to false to suppress these prompts."
         }
      )
      .define("pack.orphanPrompt", true);
   public static final ModConfigSpec CLIENT_SPEC = CLIENT_BUILDER.build();

   public static String getEffectiveBaseUrl() {
      String url = ((String)AI_API_BASE_URL.get()).trim();
      if (!url.isEmpty()) {
         if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
         }

         if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://" + url;
         }

         if (url.endsWith("/v1")) {
            url = url.substring(0, url.length() - 3);
         }

         return url;
      } else {
         return "anthropic".equals(AI_PROVIDER.get()) ? "https://api.anthropic.com" : "https://api.openai.com";
      }
   }

   public static String getEffectiveModel() {
      String model = ((String)AI_MODEL.get()).trim();
      if (!model.isEmpty()) {
         return model;
      } else {
         return "anthropic".equals(AI_PROVIDER.get()) ? "claude-sonnet-4-20250514" : "gpt-4o";
      }
   }
}
