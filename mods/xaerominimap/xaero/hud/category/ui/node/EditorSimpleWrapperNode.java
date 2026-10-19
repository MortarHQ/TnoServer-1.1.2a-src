package xaero.hud.category.ui.node;

import com.google.common.base.Objects;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.network.chat.Component;
import xaero.hud.category.ui.entry.EditorListRootEntryFactory;
import xaero.hud.category.ui.node.tooltip.IEditorDataTooltipSupplier;

public class EditorSimpleWrapperNode<S extends Comparable<S>> extends EditorNode implements Comparable<EditorSimpleWrapperNode<S>> {
   private S element;

   protected EditorSimpleWrapperNode(
      @Nonnull S element, boolean movable, EditorListRootEntryFactory listEntryFactory, IEditorDataTooltipSupplier tooltipSupplier
   ) {
      super(movable, listEntryFactory, tooltipSupplier);
      this.element = element;
   }

   public S getElement() {
      return this.element;
   }

   public void setElement(S element) {
      this.element = element;
   }

   @Override
   public List<EditorNode> getSubNodes() {
      return null;
   }

   @Override
   public Component getDisplayName() {
      return Component.literal(this.element.toString());
   }

   @Override
   public boolean equals(Object obj) {
      return obj != null && obj instanceof EditorSimpleWrapperNode<?> otherWrapper ? Objects.equal(this.element, otherWrapper.element) : false;
   }

   public int compareTo(EditorSimpleWrapperNode<S> o) {
      if (this.element == o.element) {
         return 0;
      } else if (this.element == null) {
         return -1;
      } else {
         return o.element == null ? 1 : this.element.compareTo(o.element);
      }
   }

   public abstract static class Builder<S extends Comparable<S>, B extends EditorSimpleWrapperNode.Builder<S, B>> extends EditorNode.Builder<B> {
      protected S element;

      protected Builder() {
      }

      public B setDefault() {
         super.setDefault();
         this.setElement(null);
         return this.self;
      }

      public B setElement(S element) {
         this.element = element;
         return this.self;
      }

      public EditorSimpleWrapperNode<S> build() {
         if (this.element == null) {
            throw new IllegalStateException("required fields not set!");
         } else {
            return (EditorSimpleWrapperNode<S>)super.build();
         }
      }
   }

   public static final class FinalBuilder<S extends Comparable<S>> extends EditorSimpleWrapperNode.Builder<S, EditorSimpleWrapperNode.FinalBuilder<S>> {
      public static <S extends Comparable<S>> EditorSimpleWrapperNode.FinalBuilder<S> begin() {
         return new EditorSimpleWrapperNode.FinalBuilder().setDefault();
      }

      protected EditorSimpleWrapperNode<S> buildInternally() {
         return new EditorSimpleWrapperNode<>(this.element, this.movable, this.listEntryFactory, this.tooltipSupplier);
      }
   }
}
