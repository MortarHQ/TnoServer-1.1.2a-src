package cc.sighs.oelib.config.model;

import cc.sighs.oelib.config.ui.ConfigUiHint;
import java.util.Optional;

public final class ConfigValueMeta {
   private final String key;
   private final String comment;
   private final ConfigUiHint uiHint;
   private final String translationKey;
   private final String tooltip;
   private final boolean hidden;

   private ConfigValueMeta(ConfigValueMeta.Builder builder) {
      this.key = builder.key;
      this.comment = builder.comment;
      this.uiHint = builder.uiHint;
      this.translationKey = builder.translationKey;
      this.tooltip = builder.tooltip;
      this.hidden = builder.hidden;
   }

   public static ConfigValueMeta.Builder builder(String key) {
      return new ConfigValueMeta.Builder(key);
   }

   public String key() {
      return this.key;
   }

   public Optional<String> comment() {
      return Optional.ofNullable(this.comment);
   }

   public Optional<ConfigUiHint> uiHint() {
      return Optional.ofNullable(this.uiHint);
   }

   public Optional<String> translationKey() {
      return Optional.ofNullable(this.translationKey);
   }

   public Optional<String> tooltip() {
      return Optional.ofNullable(this.tooltip);
   }

   public boolean hidden() {
      return this.hidden;
   }

   public static final class Builder {
      private final String key;
      private String comment;
      private ConfigUiHint uiHint;
      private String translationKey;
      private String tooltip;
      private boolean hidden;

      private Builder(String key) {
         this.key = key;
      }

      public ConfigValueMeta.Builder comment(String comment) {
         this.comment = comment;
         return this;
      }

      public ConfigValueMeta.Builder uiHint(ConfigUiHint uiHint) {
         this.uiHint = uiHint;
         return this;
      }

      public ConfigValueMeta.Builder translationKey(String translationKey) {
         this.translationKey = translationKey;
         return this;
      }

      public ConfigValueMeta.Builder tooltip(String tooltip) {
         this.tooltip = tooltip;
         return this;
      }

      public ConfigValueMeta.Builder hidden(boolean hidden) {
         this.hidden = hidden;
         return this;
      }

      public ConfigValueMeta build() {
         return new ConfigValueMeta(this);
      }
   }
}
