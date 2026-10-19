package servercore_libs.net.kyori.adventure.text.minimessage;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.ApiStatus.Internal;
import servercore_libs.net.kyori.adventure.builder.AbstractBuilder;
import servercore_libs.net.kyori.adventure.pointer.Pointered;
import servercore_libs.net.kyori.adventure.text.Component;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import servercore_libs.net.kyori.adventure.text.minimessage.tree.Node;
import servercore_libs.net.kyori.adventure.text.serializer.ComponentSerializer;
import servercore_libs.net.kyori.adventure.util.PlatformAPI;

public interface MiniMessage extends ComponentSerializer<Component, Component, String> {
   @NotNull
   static MiniMessage miniMessage() {
      return MiniMessageImpl.Instances.INSTANCE;
   }

   @NotNull
   String escapeTags(@NotNull final String input);

   @NotNull
   String escapeTags(@NotNull final String input, @NotNull final TagResolver tagResolver);

   @NotNull
   default String escapeTags(@NotNull final String input, @NotNull final TagResolver... tagResolvers) {
      return this.escapeTags(input, TagResolver.resolver(tagResolvers));
   }

   @NotNull
   String stripTags(@NotNull final String input);

   @NotNull
   String stripTags(@NotNull final String input, @NotNull final TagResolver tagResolver);

   @NotNull
   default String stripTags(@NotNull final String input, @NotNull final TagResolver... tagResolvers) {
      return this.stripTags(input, TagResolver.resolver(tagResolvers));
   }

   @NotNull
   Component deserialize(@NotNull final String input, @NotNull final Pointered target);

   @NotNull
   Component deserialize(@NotNull final String input, @NotNull final TagResolver tagResolver);

   @NotNull
   Component deserialize(@NotNull final String input, @NotNull final Pointered target, @NotNull final TagResolver tagResolver);

   @NotNull
   default Component deserialize(@NotNull final String input, @NotNull final TagResolver... tagResolvers) {
      return this.deserialize(input, TagResolver.resolver(tagResolvers));
   }

   @NotNull
   default Component deserialize(@NotNull final String input, @NotNull final Pointered target, @NotNull final TagResolver... tagResolvers) {
      return this.deserialize(input, target, TagResolver.resolver(tagResolvers));
   }

   Node.@NotNull Root deserializeToTree(@NotNull final String input);

   Node.@NotNull Root deserializeToTree(@NotNull final String input, @NotNull final Pointered target);

   Node.@NotNull Root deserializeToTree(@NotNull final String input, @NotNull final TagResolver tagResolver);

   Node.@NotNull Root deserializeToTree(@NotNull final String input, @NotNull final Pointered target, @NotNull final TagResolver tagResolver);

   default Node.@NotNull Root deserializeToTree(@NotNull final String input, @NotNull final TagResolver... tagResolvers) {
      return this.deserializeToTree(input, TagResolver.resolver(tagResolvers));
   }

   default Node.@NotNull Root deserializeToTree(@NotNull final String input, @NotNull final Pointered target, @NotNull final TagResolver... tagResolvers) {
      return this.deserializeToTree(input, target, TagResolver.resolver(tagResolvers));
   }

   boolean strict();

   @NotNull
   TagResolver tags();

   static MiniMessage.Builder builder() {
      return new MiniMessageImpl.BuilderImpl();
   }

   interface Builder extends AbstractBuilder<MiniMessage> {
      @NotNull
      MiniMessage.Builder tags(@NotNull final TagResolver tags);

      @NotNull
      MiniMessage.Builder editTags(@NotNull final Consumer<TagResolver.Builder> adder);

      @NotNull
      MiniMessage.Builder strict(final boolean strict);

      @NotNull
      MiniMessage.Builder debug(@Nullable final Consumer<String> debugOutput);

      @NotNull
      MiniMessage.Builder postProcessor(@NotNull final UnaryOperator<Component> postProcessor);

      @NotNull
      MiniMessage.Builder preProcessor(@NotNull final UnaryOperator<String> preProcessor);

      @NotNull
      MiniMessage build();
   }

   @PlatformAPI
   @Internal
   interface Provider {
      @PlatformAPI
      @Internal
      @NotNull
      MiniMessage miniMessage();

      @PlatformAPI
      @Internal
      @NotNull
      Consumer<MiniMessage.Builder> builder();
   }
}
