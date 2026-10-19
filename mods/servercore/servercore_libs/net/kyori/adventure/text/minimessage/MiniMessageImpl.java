package servercore_libs.net.kyori.adventure.text.minimessage;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import servercore_libs.net.kyori.adventure.pointer.Pointered;
import servercore_libs.net.kyori.adventure.text.Component;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.serializer.SerializableResolver;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import servercore_libs.net.kyori.adventure.text.minimessage.tree.Node;
import servercore_libs.net.kyori.adventure.util.Services;

final class MiniMessageImpl implements MiniMessage {
   private static final Optional<MiniMessage.Provider> SERVICE = Services.service(MiniMessage.Provider.class);
   static final Consumer<MiniMessage.Builder> BUILDER = SERVICE.map(MiniMessage.Provider::builder).orElseGet(() -> builder -> {});
   static final UnaryOperator<String> DEFAULT_NO_OP = UnaryOperator.identity();
   static final UnaryOperator<Component> DEFAULT_COMPACTING_METHOD = Component::compact;
   private final boolean strict;
   @Nullable
   private final Consumer<String> debugOutput;
   private final UnaryOperator<Component> postProcessor;
   private final UnaryOperator<String> preProcessor;
   final MiniMessageParser parser;

   MiniMessageImpl(
      @NotNull final TagResolver resolver,
      final boolean strict,
      @Nullable final Consumer<String> debugOutput,
      @NotNull final UnaryOperator<String> preProcessor,
      @NotNull final UnaryOperator<Component> postProcessor
   ) {
      this.parser = new MiniMessageParser(resolver);
      this.strict = strict;
      this.debugOutput = debugOutput;
      this.preProcessor = preProcessor;
      this.postProcessor = postProcessor;
   }

   @NotNull
   public Component deserialize(@NotNull final String input) {
      return this.parser.parseFormat(this.newContext(input, null, null));
   }

   @NotNull
   @Override
   public Component deserialize(@NotNull final String input, @NotNull final Pointered target) {
      return this.parser.parseFormat(this.newContext(input, Objects.requireNonNull(target, "target"), null));
   }

   @NotNull
   @Override
   public Component deserialize(@NotNull final String input, @NotNull final TagResolver tagResolver) {
      return this.parser.parseFormat(this.newContext(input, null, Objects.requireNonNull(tagResolver, "tagResolver")));
   }

   @NotNull
   @Override
   public Component deserialize(@NotNull final String input, @NotNull final Pointered target, @NotNull final TagResolver tagResolver) {
      return this.parser.parseFormat(this.newContext(input, Objects.requireNonNull(target, "target"), Objects.requireNonNull(tagResolver, "tagResolver")));
   }

   @Override
   public Node.@NotNull Root deserializeToTree(@NotNull final String input) {
      return this.parser.parseToTree(this.newContext(input, null, null));
   }

   @Override
   public Node.@NotNull Root deserializeToTree(@NotNull final String input, @NotNull final Pointered target) {
      return this.parser.parseToTree(this.newContext(input, Objects.requireNonNull(target, "target"), null));
   }

   @Override
   public Node.@NotNull Root deserializeToTree(@NotNull final String input, @NotNull final TagResolver tagResolver) {
      return this.parser.parseToTree(this.newContext(input, null, Objects.requireNonNull(tagResolver, "tagResolver")));
   }

   @Override
   public Node.@NotNull Root deserializeToTree(@NotNull final String input, @NotNull final Pointered target, @NotNull final TagResolver tagResolver) {
      return this.parser.parseToTree(this.newContext(input, Objects.requireNonNull(target, "target"), Objects.requireNonNull(tagResolver, "tagResolver")));
   }

   @NotNull
   public String serialize(@NotNull final Component component) {
      return MiniMessageSerializer.serialize(component, this.serialResolver(null), this.strict);
   }

   private SerializableResolver serialResolver(@Nullable final TagResolver extraResolver) {
      if (extraResolver == null) {
         if (this.parser.tagResolver instanceof SerializableResolver) {
            return (SerializableResolver)this.parser.tagResolver;
         }
      } else {
         TagResolver combined = TagResolver.resolver(this.parser.tagResolver, extraResolver);
         if (combined instanceof SerializableResolver) {
            return (SerializableResolver)combined;
         }
      }

      return (SerializableResolver)TagResolver.empty();
   }

   @NotNull
   @Override
   public String escapeTags(@NotNull final String input) {
      return this.parser.escapeTokens(this.newContext(input, null, null));
   }

   @NotNull
   @Override
   public String escapeTags(@NotNull final String input, @NotNull final TagResolver tagResolver) {
      return this.parser.escapeTokens(this.newContext(input, null, tagResolver));
   }

   @NotNull
   @Override
   public String stripTags(@NotNull final String input) {
      return this.parser.stripTokens(this.newContext(input, null, null));
   }

   @NotNull
   @Override
   public String stripTags(@NotNull final String input, @NotNull final TagResolver tagResolver) {
      return this.parser.stripTokens(this.newContext(input, null, tagResolver));
   }

   @Override
   public boolean strict() {
      return this.strict;
   }

   @NotNull
   @Override
   public TagResolver tags() {
      return this.parser.tagResolver;
   }

   @NotNull
   private ContextImpl newContext(@NotNull final String input, @Nullable final Pointered target, @Nullable final TagResolver resolver) {
      Objects.requireNonNull(input, "input");
      return new ContextImpl(this.strict, this.debugOutput, input, this, target, resolver, this.preProcessor, this.postProcessor);
   }

   static final class BuilderImpl implements MiniMessage.Builder {
      private TagResolver tagResolver = TagResolver.standard();
      private boolean strict = false;
      private Consumer<String> debug = null;
      private UnaryOperator<Component> postProcessor = MiniMessageImpl.DEFAULT_COMPACTING_METHOD;
      private UnaryOperator<String> preProcessor = MiniMessageImpl.DEFAULT_NO_OP;

      BuilderImpl() {
         MiniMessageImpl.BUILDER.accept(this);
      }

      BuilderImpl(final MiniMessageImpl serializer) {
         this();
         this.tagResolver = serializer.parser.tagResolver;
         this.strict = serializer.strict;
         this.debug = serializer.debugOutput;
         this.postProcessor = serializer.postProcessor;
         this.preProcessor = serializer.preProcessor;
      }

      @NotNull
      @Override
      public MiniMessage.Builder tags(@NotNull final TagResolver tags) {
         this.tagResolver = Objects.requireNonNull(tags, "tags");
         return this;
      }

      @NotNull
      @Override
      public MiniMessage.Builder editTags(@NotNull final Consumer<TagResolver.Builder> adder) {
         Objects.requireNonNull(adder, "adder");
         TagResolver.Builder builder = TagResolver.builder().resolver(this.tagResolver);
         adder.accept(builder);
         this.tagResolver = builder.build();
         return this;
      }

      @NotNull
      @Override
      public MiniMessage.Builder strict(final boolean strict) {
         this.strict = strict;
         return this;
      }

      @NotNull
      @Override
      public MiniMessage.Builder debug(@Nullable final Consumer<String> debugOutput) {
         this.debug = debugOutput;
         return this;
      }

      @NotNull
      @Override
      public MiniMessage.Builder postProcessor(@NotNull final UnaryOperator<Component> postProcessor) {
         this.postProcessor = Objects.requireNonNull(postProcessor, "postProcessor");
         return this;
      }

      @NotNull
      @Override
      public MiniMessage.Builder preProcessor(@NotNull final UnaryOperator<String> preProcessor) {
         this.preProcessor = Objects.requireNonNull(preProcessor, "preProcessor");
         return this;
      }

      @NotNull
      @Override
      public MiniMessage build() {
         return new MiniMessageImpl(this.tagResolver, this.strict, this.debug, this.preProcessor, this.postProcessor);
      }
   }

   static final class Instances {
      static final MiniMessage INSTANCE = MiniMessageImpl.SERVICE
         .map(MiniMessage.Provider::miniMessage)
         .orElseGet(() -> new MiniMessageImpl(TagResolver.standard(), false, null, MiniMessageImpl.DEFAULT_NO_OP, MiniMessageImpl.DEFAULT_COMPACTING_METHOD));
   }
}
