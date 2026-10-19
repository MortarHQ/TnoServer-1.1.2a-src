package xaero.hud.category.ui.node.options;

import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.network.chat.Component;
import xaero.hud.category.ui.GuiCategoryEditor;
import xaero.hud.category.ui.entry.EditorListEntryExpandingOption;
import xaero.hud.category.ui.entry.EditorListRootEntry;
import xaero.hud.category.ui.entry.EditorListRootEntryFactory;
import xaero.hud.category.ui.node.EditorNode;
import xaero.hud.category.ui.node.tooltip.IEditorDataTooltipSupplier;

public final class EditorOptionNode<V> extends EditorNode {
   private final V value;
   private final Component displayName;

   public EditorOptionNode(
      V index, Component displayName, boolean movable, @Nonnull EditorListRootEntryFactory listEntryFactory, IEditorDataTooltipSupplier tooltipSupplier
   ) {
      super(movable, listEntryFactory, tooltipSupplier);
      this.value = index;
      this.displayName = displayName;
   }

   public V getValue() {
      return this.value;
   }

   @Override
   public Component getDisplayName() {
      return this.displayName;
   }

   @Override
   public List<EditorNode> getSubNodes() {
      return null;
   }

   public static final class Builder<V> extends EditorNode.Builder<EditorOptionNode.Builder<V>> {
      private V value;
      private Component displayName;

      private Builder() {
      }

      public EditorOptionNode.Builder<V> setDefault() {
         super.setDefault();
         this.setValue(null);
         this.setDisplayName(null);
         return this;
      }

      @Override
      protected EditorListRootEntry.CenteredEntryFactory getCenteredEntryFactory(
         EditorNode data, EditorNode parent, int index, GuiCategoryEditor<?, ?, ?, ?, ?, ?>.SettingRowList rowList
      ) {
         return (x, y, width, height, root) -> {
            EditorExpandingOptionsNode<V> optionsData = (EditorExpandingOptionsNode<V>)parent;
            return new EditorListEntryExpandingOption<>(x, y, width, height, index, rowList, optionsData, root, data.getTooltipSupplier(parent));
         };
      }

      public EditorOptionNode.Builder<V> setValue(V value) {
         this.value = value;
         return this;
      }

      public EditorOptionNode.Builder<V> setDisplayName(Component displayName) {
         this.displayName = displayName;
         return this;
      }

      public EditorOptionNode<V> build() {
         if (this.displayName == null) {
            this.displayName = Component.literal(this.value == null ? "N/A" : this.value.toString());
         }

         return (EditorOptionNode<V>)super.build();
      }

      public static <V> EditorOptionNode.Builder<V> begin() {
         return new EditorOptionNode.Builder<V>().setDefault();
      }

      protected EditorOptionNode<V> buildInternally() {
         return new EditorOptionNode<>(this.value, this.displayName, this.movable, this.listEntryFactory, this.tooltipSupplier);
      }
   }
}
