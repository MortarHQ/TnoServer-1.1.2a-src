package servercore_libs.net.kyori.adventure.text;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import servercore_libs.net.kyori.adventure.internal.Internals;
import servercore_libs.net.kyori.examination.ExaminableProperty;

final class TextReplacementConfigImpl implements TextReplacementConfig {
   private final Pattern matchPattern;
   private final BiFunction<MatchResult, TextComponent.Builder, @Nullable ComponentLike> replacement;
   private final TextReplacementConfig.Condition continuer;

   TextReplacementConfigImpl(final TextReplacementConfigImpl.Builder builder) {
      this.matchPattern = builder.matchPattern;
      this.replacement = builder.replacement;
      this.continuer = builder.continuer;
   }

   @NotNull
   @Override
   public Pattern matchPattern() {
      return this.matchPattern;
   }

   TextReplacementRenderer.State createState() {
      return new TextReplacementRenderer.State(this.matchPattern, this.replacement, this.continuer);
   }

   public TextReplacementConfig.@NotNull Builder toBuilder() {
      return new TextReplacementConfigImpl.Builder(this);
   }

   @NotNull
   @Override
   public Stream<? extends ExaminableProperty> examinableProperties() {
      return Stream.of(
         ExaminableProperty.of("matchPattern", this.matchPattern),
         ExaminableProperty.of("replacement", this.replacement),
         ExaminableProperty.of("continuer", this.continuer)
      );
   }

   @Override
   public String toString() {
      return Internals.toString(this);
   }

   static final class Builder implements TextReplacementConfig.Builder {
      @Nullable
      Pattern matchPattern;
      @Nullable
      BiFunction<MatchResult, TextComponent.Builder, ComponentLike> replacement;
      TextReplacementConfig.Condition continuer = (matchResult, index, replacement) -> PatternReplacementResult.REPLACE;

      Builder() {
      }

      Builder(final TextReplacementConfigImpl instance) {
         this.matchPattern = instance.matchPattern;
         this.replacement = instance.replacement;
         this.continuer = instance.continuer;
      }

      @NotNull
      public TextReplacementConfigImpl.Builder match(@NotNull final Pattern pattern) {
         this.matchPattern = Objects.requireNonNull(pattern, "pattern");
         return this;
      }

      @NotNull
      public TextReplacementConfigImpl.Builder condition(final TextReplacementConfig.@NotNull Condition condition) {
         this.continuer = Objects.requireNonNull(condition, "continuation");
         return this;
      }

      @NotNull
      public TextReplacementConfigImpl.Builder replacement(@NotNull final BiFunction<MatchResult, TextComponent.Builder, @Nullable ComponentLike> replacement) {
         this.replacement = Objects.requireNonNull(replacement, "replacement");
         return this;
      }

      @NotNull
      public TextReplacementConfig build() {
         if (this.matchPattern == null) {
            throw new IllegalStateException("A pattern must be provided to match against");
         } else if (this.replacement == null) {
            throw new IllegalStateException("A replacement action must be provided");
         } else {
            return new TextReplacementConfigImpl(this);
         }
      }
   }
}
