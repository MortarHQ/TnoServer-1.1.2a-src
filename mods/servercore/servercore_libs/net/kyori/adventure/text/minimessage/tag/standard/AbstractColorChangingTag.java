package servercore_libs.net.kyori.adventure.text.minimessage.tag.standard;

import java.util.Collections;
import java.util.PrimitiveIterator.OfInt;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import servercore_libs.net.kyori.adventure.internal.Internals;
import servercore_libs.net.kyori.adventure.text.Component;
import servercore_libs.net.kyori.adventure.text.TextComponent;
import servercore_libs.net.kyori.adventure.text.flattener.ComponentFlattener;
import servercore_libs.net.kyori.adventure.text.format.TextColor;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.parser.node.TagNode;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.parser.node.ValueNode;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.Inserting;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.Modifying;
import servercore_libs.net.kyori.adventure.text.minimessage.tree.Node;
import servercore_libs.net.kyori.examination.Examinable;
import servercore_libs.net.kyori.examination.ExaminableProperty;

abstract class AbstractColorChangingTag implements Modifying, Examinable {
   private static final ComponentFlattener LENGTH_CALCULATOR = ComponentFlattener.builder()
      .mapper(TextComponent.class, TextComponent::content)
      .unknownMapper(x -> "_")
      .build();
   private boolean visited;
   private int size = 0;
   private int disableApplyingColorDepth = -1;

   protected final int size() {
      return this.size;
   }

   @Override
   public final void visit(@NotNull final Node current, final int depth) {
      if (this.visited) {
         throw new IllegalStateException("Color changing tag instances cannot be re-used, return a new one for each resolve");
      }

      if (current instanceof ValueNode) {
         String value = ((ValueNode)current).value();
         this.size = this.size + value.codePointCount(0, value.length());
      } else if (current instanceof TagNode) {
         TagNode tag = (TagNode)current;
         if (tag.tag() instanceof Inserting) {
            LENGTH_CALCULATOR.flatten(((Inserting)tag.tag()).value(), s -> this.size = this.size + s.codePointCount(0, s.length()));
         }
      }
   }

   @Override
   public final void postVisit() {
      this.visited = true;
      this.init();
   }

   @Override
   public final Component apply(@NotNull final Component current, final int depth) {
      if ((this.disableApplyingColorDepth == -1 || depth <= this.disableApplyingColorDepth) && current.style().color() == null) {
         this.disableApplyingColorDepth = -1;
         if (current instanceof TextComponent && ((TextComponent)current).content().length() > 0) {
            TextComponent textComponent = (TextComponent)current;
            String content = textComponent.content();
            TextComponent.Builder parent = Component.text();
            int[] holder = new int[1];
            OfInt it = content.codePoints().iterator();

            while (it.hasNext()) {
               holder[0] = it.nextInt();
               Component comp = Component.text(new String(holder, 0, 1), current.style().color(this.color()));
               this.advanceColor();
               parent.append(comp);
            }

            return parent.build();
         } else if (!(current instanceof TextComponent)) {
            Component ret = current.children(Collections.emptyList()).colorIfAbsent(this.color());
            this.advanceColor();
            return ret;
         } else {
            return Component.empty().mergeStyle(current);
         }
      } else {
         if (this.disableApplyingColorDepth == -1 || depth < this.disableApplyingColorDepth) {
            this.disableApplyingColorDepth = depth;
         }

         if (current instanceof TextComponent) {
            String content = ((TextComponent)current).content();
            int len = content.codePointCount(0, content.length());

            for (int i = 0; i < len; i++) {
               this.advanceColor();
            }
         }

         return current.children(Collections.emptyList());
      }
   }

   protected abstract void init();

   protected abstract void advanceColor();

   protected abstract TextColor color();

   @NotNull
   @Override
   public abstract Stream<? extends ExaminableProperty> examinableProperties();

   @NotNull
   @Override
   public final String toString() {
      return Internals.toString(this);
   }

   @Override
   public abstract boolean equals(@Nullable final Object other);

   @Override
   public abstract int hashCode();
}
