package servercore_libs.net.kyori.adventure.text;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import servercore_libs.net.kyori.adventure.text.event.HoverEvent;
import servercore_libs.net.kyori.adventure.text.format.Style;
import servercore_libs.net.kyori.adventure.text.renderer.ComponentRenderer;

final class TextReplacementRenderer implements ComponentRenderer<TextReplacementRenderer.State> {
   static final TextReplacementRenderer INSTANCE = new TextReplacementRenderer();

   private TextReplacementRenderer() {
   }

   @NotNull
   public Component render(@NotNull final Component component, @NotNull final TextReplacementRenderer.State state) {
      if (!state.running) {
         return component;
      }

      boolean prevFirstMatch = state.firstMatch;
      state.firstMatch = true;
      List<Component> oldChildren = component.children();
      int oldChildrenSize = oldChildren.size();
      Style oldStyle = component.style();
      List<Component> children = null;
      Component modified = component;
      if (component instanceof TextComponent) {
         String content = ((TextComponent)component).content();
         Matcher matcher = state.pattern.matcher(content);
         int replacedUntil = 0;

         while (matcher.find()) {
            PatternReplacementResult result = state.continuer.shouldReplace(matcher, ++state.matchCount, state.replaceCount);
            if (result != PatternReplacementResult.CONTINUE) {
               if (result == PatternReplacementResult.STOP) {
                  state.running = false;
                  break;
               }

               if (matcher.start() == 0) {
                  if (matcher.end() == content.length()) {
                     ComponentLike replacement = state.replacement.apply(matcher, Component.text().content(matcher.group()).style(component.style()));
                     modified = replacement == null ? Component.empty() : replacement.asComponent();
                     if (modified.style().hoverEvent() != null) {
                        oldStyle = oldStyle.hoverEvent(null);
                     }

                     modified = modified.style(modified.style().merge(component.style(), Style.Merge.Strategy.IF_ABSENT_ON_TARGET));
                     if (children == null) {
                        children = new ArrayList<>(oldChildrenSize + modified.children().size());
                        children.addAll(modified.children());
                     }
                  } else {
                     modified = Component.text("", component.style());
                     ComponentLike child = state.replacement.apply(matcher, Component.text().content(matcher.group()));
                     if (child != null) {
                        if (children == null) {
                           children = new ArrayList<>(oldChildrenSize + 1);
                        }

                        children.add(child.asComponent());
                     }
                  }
               } else {
                  if (children == null) {
                     children = new ArrayList<>(oldChildrenSize + 2);
                  }

                  if (state.firstMatch) {
                     modified = ((TextComponent)component).content(content.substring(0, matcher.start()));
                  } else if (replacedUntil < matcher.start()) {
                     children.add(Component.text(content.substring(replacedUntil, matcher.start())));
                  }

                  ComponentLike builder = state.replacement.apply(matcher, Component.text().content(matcher.group()));
                  if (builder != null) {
                     children.add(builder.asComponent());
                  }
               }

               state.replaceCount++;
               state.firstMatch = false;
               replacedUntil = matcher.end();
            }
         }

         if (replacedUntil < content.length() && replacedUntil > 0) {
            if (children == null) {
               children = new ArrayList<>(oldChildrenSize);
            }

            children.add(Component.text(content.substring(replacedUntil)));
         }
      } else if (modified instanceof TranslatableComponent) {
         List<TranslationArgument> args = ((TranslatableComponent)modified).arguments();
         List<TranslationArgument> newArgs = null;
         int i = 0;

         for (int size = args.size(); i < size; i++) {
            TranslationArgument original = args.get(i);
            TranslationArgument replaced = original.value() instanceof Component
               ? TranslationArgument.component(this.render((Component)original.value(), state))
               : original;
            if (replaced != original && newArgs == null) {
               newArgs = new ArrayList<>(size);
               if (i > 0) {
                  newArgs.addAll(args.subList(0, i));
               }
            }

            if (newArgs != null) {
               newArgs.add(replaced);
            }
         }

         if (newArgs != null) {
            modified = ((TranslatableComponent)modified).arguments(newArgs);
         }
      }

      if (state.running) {
         HoverEvent<?> event = oldStyle.hoverEvent();
         if (event != null) {
            HoverEvent<?> rendered = event.withRenderedValue(this, state);
            if (event != rendered) {
               modified = modified.style(s -> s.hoverEvent(rendered));
            }
         }

         boolean first = true;

         for (int i = 0; i < oldChildrenSize; i++) {
            Component child = oldChildren.get(i);
            Component replaced = this.render(child, state);
            if (replaced != child) {
               if (children == null) {
                  children = new ArrayList<>(oldChildrenSize);
               }

               if (first) {
                  children.addAll(oldChildren.subList(0, i));
               }

               first = false;
            }

            if (children != null) {
               children.add(replaced);
               first = false;
            }
         }
      } else if (children != null) {
         children.addAll(oldChildren);
      }

      state.firstMatch = prevFirstMatch;
      return children != null ? modified.children(children) : modified;
   }

   static final class State {
      final Pattern pattern;
      final BiFunction<MatchResult, TextComponent.Builder, @Nullable ComponentLike> replacement;
      final TextReplacementConfig.Condition continuer;
      boolean running = true;
      int matchCount = 0;
      int replaceCount = 0;
      boolean firstMatch = true;

      State(
         @NotNull final Pattern pattern,
         @NotNull final BiFunction<MatchResult, TextComponent.Builder, @Nullable ComponentLike> replacement,
         final TextReplacementConfig.@NotNull Condition continuer
      ) {
         this.pattern = pattern;
         this.replacement = replacement;
         this.continuer = continuer;
      }
   }
}
