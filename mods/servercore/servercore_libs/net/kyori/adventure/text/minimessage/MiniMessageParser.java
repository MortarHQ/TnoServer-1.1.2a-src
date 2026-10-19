package servercore_libs.net.kyori.adventure.text.minimessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import servercore_libs.net.kyori.adventure.text.Component;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.parser.ParsingExceptionImpl;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.parser.Token;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.parser.TokenParser;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.parser.TokenType;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.parser.node.ElementNode;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.parser.node.RootNode;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.parser.node.TagNode;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.parser.node.ValueNode;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.Inserting;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.Modifying;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.Tag;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import servercore_libs.net.kyori.examination.Examinable;
import servercore_libs.net.kyori.examination.string.MultiLineStringExaminer;

final class MiniMessageParser {
   final TagResolver tagResolver;

   MiniMessageParser() {
      this.tagResolver = TagResolver.standard();
   }

   MiniMessageParser(final TagResolver tagResolver) {
      this.tagResolver = tagResolver;
   }

   @NotNull
   String escapeTokens(@NotNull final ContextImpl context) {
      StringBuilder sb = new StringBuilder(context.message().length());
      this.escapeTokens(sb, context);
      return sb.toString();
   }

   void escapeTokens(final StringBuilder sb, @NotNull final ContextImpl context) {
      this.escapeTokens(sb, context.message(), context);
   }

   private void escapeTokens(final StringBuilder sb, final String richMessage, final ContextImpl context) {
      this.processTokens(sb, richMessage, context, (token, builder) -> {
         builder.append('\\').append('<');
         if (token.type() == TokenType.CLOSE_TAG) {
            builder.append('/');
         }

         List<Token> childTokens = token.childTokens();

         for (int i = 0; i < childTokens.size(); i++) {
            if (i != 0) {
               builder.append(':');
            }

            this.escapeTokens(builder, childTokens.get(i).get(richMessage).toString(), context);
         }

         builder.append('>');
      });
   }

   @NotNull
   String stripTokens(@NotNull final ContextImpl context) {
      StringBuilder sb = new StringBuilder(context.message().length());
      this.processTokens(sb, context, (token, builder) -> {});
      return sb.toString();
   }

   private void processTokens(@NotNull final StringBuilder sb, @NotNull final ContextImpl context, final BiConsumer<Token, StringBuilder> tagHandler) {
      this.processTokens(sb, context.message(), context, tagHandler);
   }

   private void processTokens(
      @NotNull final StringBuilder sb, @NotNull final String richMessage, @NotNull final ContextImpl context, final BiConsumer<Token, StringBuilder> tagHandler
   ) {
      TagResolver combinedResolver = TagResolver.resolver(this.tagResolver, context.extraTags());

      for (Token token : TokenParser.tokenize(richMessage, true)) {
         switch (token.type()) {
            case TEXT:
               sb.append(richMessage, token.startIndex(), token.endIndex());
               break;
            case OPEN_TAG:
            case CLOSE_TAG:
            case OPEN_CLOSE_TAG:
               if (token.childTokens().isEmpty()) {
                  sb.append(richMessage, token.startIndex(), token.endIndex());
               } else {
                  String sanitized = TokenParser.TagProvider.sanitizePlaceholderName(token.childTokens().get(0).get(richMessage).toString());
                  if (combinedResolver.has(sanitized)) {
                     tagHandler.accept(token, sb);
                  } else {
                     sb.append(richMessage, token.startIndex(), token.endIndex());
                  }
               }
               break;
            default:
               throw new IllegalArgumentException("Unsupported token type " + token.type());
         }
      }
   }

   @NotNull
   RootNode parseToTree(@NotNull final ContextImpl context) {
      TagResolver combinedResolver = TagResolver.resolver(this.tagResolver, context.extraTags());
      String processedMessage = context.preProcessor().apply(context.message());
      Consumer<String> debug = context.debugOutput();
      if (debug != null) {
         debug.accept("Beginning parsing message ");
         debug.accept(processedMessage);
         debug.accept("\n");
      }

      TokenParser.TagProvider transformationFactory;
      if (debug != null) {
         transformationFactory = (name, args, token) -> {
            try {
               debug.accept("Attempting to match node '");
               debug.accept(name);
               debug.accept("'");
               if (token != null) {
                  debug.accept(" at column ");
                  debug.accept(String.valueOf(token.startIndex()));
               }

               debug.accept("\n");
               Tag transformation = combinedResolver.resolve(name, new ArgumentQueueImpl<>(context, args), context);
               if (transformation == null) {
                  debug.accept("Could not match node '");
                  debug.accept(name);
                  debug.accept("'\n");
               } else {
                  debug.accept("Successfully matched node '");
                  debug.accept(name);
                  debug.accept("' to tag ");
                  debug.accept(transformation instanceof Examinable ? ((Examinable)transformation).examinableName() : transformation.getClass().getName());
                  debug.accept("\n");
               }

               return transformation;
            } catch (ParsingException e) {
               if (token != null && e instanceof ParsingExceptionImpl) {
                  ParsingExceptionImpl impl = (ParsingExceptionImpl)e;
                  if (impl.tokens().length == 0) {
                     impl.tokens(new Token[]{token});
                  }
               }

               debug.accept("Could not match node '");
               debug.accept(name);
               debug.accept("' - ");
               debug.accept(e.getMessage());
               debug.accept("\n");
               return null;
            }
         };
      } else {
         transformationFactory = (name, args, token) -> {
            try {
               return combinedResolver.resolve(name, new ArgumentQueueImpl<>(context, args), context);
            } catch (ParsingException ignored) {
               return null;
            }
         };
      }

      Predicate<String> tagNameChecker = name -> {
         String sanitized = TokenParser.TagProvider.sanitizePlaceholderName(name);
         return combinedResolver.has(sanitized);
      };
      String preProcessed = TokenParser.resolvePreProcessTags(processedMessage, transformationFactory);
      context.message(preProcessed);
      RootNode root = TokenParser.parse(transformationFactory, tagNameChecker, preProcessed, processedMessage, context.strict());
      if (debug != null) {
         debug.accept("Text parsed into element tree:\n");
         debug.accept(root.toString());
      }

      return root;
   }

   @NotNull
   Component parseFormat(@NotNull final ContextImpl context) {
      ElementNode root = this.parseToTree(context);
      return Objects.requireNonNull(context.postProcessor().apply(this.treeToComponent(root, context)), "Post-processor must not return null");
   }

   @NotNull
   Component treeToComponent(@NotNull final ElementNode node, @NotNull final ContextImpl context) {
      Component comp = Component.empty();
      Tag tag = null;
      if (node instanceof ValueNode) {
         comp = Component.text(((ValueNode)node).value());
      } else if (node instanceof TagNode) {
         TagNode tagNode = (TagNode)node;
         tag = tagNode.tag();
         if (tag instanceof Modifying) {
            Modifying modTransformation = (Modifying)tag;
            this.visitModifying(modTransformation, tagNode, 0);
            modTransformation.postVisit();
         }

         if (tag instanceof Inserting) {
            comp = ((Inserting)tag).value();
         }
      }

      if (!node.unsafeChildren().isEmpty()) {
         List<Component> children = new ArrayList<>(comp.children().size() + node.children().size());
         children.addAll(comp.children());

         for (ElementNode child : node.unsafeChildren()) {
            children.add(this.treeToComponent(child, context));
         }

         comp = comp.children(children);
      }

      if (tag instanceof Modifying) {
         comp = this.handleModifying((Modifying)tag, comp, 0);
      }

      Consumer<String> debug = context.debugOutput();
      if (debug != null) {
         debug.accept("==========\ntreeToComponent \n");
         debug.accept(node.toString());
         debug.accept("\n");
         debug.accept(comp.examine(MultiLineStringExaminer.simpleEscaping()).collect(Collectors.joining("\n")));
         debug.accept("\n==========\n");
      }

      return comp;
   }

   private void visitModifying(final Modifying modTransformation, final ElementNode node, final int depth) {
      modTransformation.visit(node, depth);

      for (ElementNode child : node.unsafeChildren()) {
         this.visitModifying(modTransformation, child, depth + 1);
      }
   }

   private Component handleModifying(final Modifying modTransformation, final Component current, final int depth) {
      Component newComp = modTransformation.apply(current, depth);

      for (Component child : current.children()) {
         newComp = newComp.append(this.handleModifying(modTransformation, child, depth + 1));
      }

      return newComp;
   }
}
