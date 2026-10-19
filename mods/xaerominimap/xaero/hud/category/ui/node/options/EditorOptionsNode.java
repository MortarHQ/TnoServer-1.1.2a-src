package xaero.hud.category.ui.node.options;

import java.util.function.Supplier;
import javax.annotation.Nonnull;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import xaero.hud.category.ui.entry.EditorListRootEntryFactory;
import xaero.hud.category.ui.node.EditorNode;
import xaero.hud.category.ui.node.tooltip.IEditorDataTooltipSupplier;

public abstract class EditorOptionsNode<V> extends EditorNode {
   protected EditorOptionNode<V> currentValue;
   protected Supplier<Component> messageSupplier;
   protected final Component displayName;
   private final EditorOptionsNode.IOptionsNodeIsActiveSupplier isActiveSupplier;

   protected EditorOptionsNode(
      @Nonnull Component displayName,
      boolean movable,
      EditorListRootEntryFactory listEntryFactory,
      IEditorDataTooltipSupplier tooltipSupplier,
      EditorOptionsNode.IOptionsNodeIsActiveSupplier isActiveSupplier
   ) {
      super(movable, listEntryFactory, tooltipSupplier);
      this.displayName = displayName;
      this.isActiveSupplier = isActiveSupplier;
   }

   public EditorOptionNode<V> getCurrentValue() {
      return this.currentValue;
   }

   public void setCurrentValue(EditorOptionNode<V> currentValue) {
      this.currentValue = currentValue;
   }

   public final Supplier<Component> getMessageSupplier() {
      if (this.messageSupplier == null) {
         this.messageSupplier = () -> (Component)(this.isExpanded()
            ? this.displayName
            : CommonComponents.optionNameValue(this.displayName, this.currentValue.getDisplayName()));
      }

      return this.messageSupplier;
   }

   public EditorOptionsNode.IOptionsNodeIsActiveSupplier getIsActiveSupplier() {
      return this.isActiveSupplier;
   }

   @Override
   public Component getDisplayName() {
      return this.displayName;
   }

   public abstract static class Builder<V, B extends EditorOptionsNode.Builder<V, B>> extends EditorNode.Builder<B> {
      protected B self = (B)this;
      protected V currentValue;
      protected Component displayName;
      protected EditorOptionsNode.IOptionsNodeIsActiveSupplier isActiveSupplier;

      protected Builder() {
      }

      public B setDefault() {
         super.setDefault();
         this.setCurrentValue(null);
         this.setDisplayName(null);
         this.setIsActiveSupplier((p, d) -> true);
         return this.self;
      }

      public B setCurrentValue(V currentValue) {
         this.currentValue = currentValue;
         return this.self;
      }

      public B setDisplayName(Component displayName) {
         this.displayName = displayName;
         return this.self;
      }

      public B setIsActiveSupplier(EditorOptionsNode.IOptionsNodeIsActiveSupplier isActiveSupplier) {
         this.isActiveSupplier = isActiveSupplier;
         return this.self;
      }

      public EditorOptionsNode<V> build() {
         if (this.displayName == null) {
            throw new IllegalStateException("required fields not set!");
         } else {
            return (EditorOptionsNode<V>)super.build();
         }
      }

      protected abstract EditorOptionsNode<V> buildInternally();
   }

   @FunctionalInterface
   public interface IOptionsNodeIsActiveSupplier {
      boolean get(EditorNode var1, EditorOptionsNode<?> var2);
   }
}
