package xaero.hud.category.ui.node.options;

import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.network.chat.Component;
import xaero.common.misc.ListFactory;
import xaero.hud.category.ui.GuiCategoryEditor;
import xaero.hud.category.ui.entry.EditorListEntryExpandingOptions;
import xaero.hud.category.ui.entry.EditorListRootEntry;
import xaero.hud.category.ui.entry.EditorListRootEntryFactory;
import xaero.hud.category.ui.entry.widget.EditorButton;
import xaero.hud.category.ui.node.EditorNode;
import xaero.hud.category.ui.node.tooltip.IEditorDataTooltipSupplier;

public class EditorExpandingOptionsNode<V> extends EditorOptionsNode<V> {
   protected final List<EditorOptionNode<V>> options;

   protected EditorExpandingOptionsNode(
      @Nonnull Component displayName,
      @Nonnull EditorOptionNode<V> currentValue,
      @Nonnull List<EditorOptionNode<V>> options,
      boolean movable,
      @Nonnull EditorListRootEntryFactory listEntryFactory,
      IEditorDataTooltipSupplier tooltipSupplier,
      EditorOptionsNode.IOptionsNodeIsActiveSupplier isActiveSupplier
   ) {
      super(displayName, movable, listEntryFactory, tooltipSupplier, isActiveSupplier);
      this.options = options;
      this.currentValue = currentValue;
   }

   public boolean onSelected(EditorOptionNode<V> option) {
      this.setCurrentValue(option);
      this.setExpanded(false);
      return true;
   }

   @Override
   public List<EditorNode> getSubNodes() {
      return this.options;
   }

   @Override
   public Component getDisplayName() {
      return Component.literal("");
   }

   public abstract static class Builder<V, B extends EditorExpandingOptionsNode.Builder<V, B>> extends EditorOptionsNode.Builder<V, B> {
      protected final List<EditorOptionNode.Builder<V>> optionBuilders;
      protected final ListFactory listFactory;

      protected Builder(ListFactory listFactory) {
         this.optionBuilders = listFactory.get();
         this.listFactory = listFactory;
      }

      public B setDefault() {
         super.setDefault();
         this.optionBuilders.clear();
         return this.self;
      }

      @Override
      protected EditorListRootEntry.CenteredEntryFactory getCenteredEntryFactory(
         EditorNode data, EditorNode parent, int index, GuiCategoryEditor<?, ?, ?, ?, ?, ?>.SettingRowList rowList
      ) {
         return (x, y, width, height, root) -> {
            EditorExpandingOptionsNode<?> eoData = (EditorExpandingOptionsNode<?>)data;
            boolean isActive = eoData.getIsActiveSupplier().get(parent, eoData);
            EditorButton button = new EditorButton(parent, () -> Component.literal(""), isActive, 216, 20, b -> data.getExpandAction(rowList).run(), rowList);
            if (rowList.readOnly) {
               button.active = false;
            }

            return new EditorListEntryExpandingOptions(
               x, y, width, height, index, rowList, root, button, eoData.getMessageSupplier(), data.getTooltipSupplier(parent)
            );
         };
      }

      public B addOptionBuilderFor(V option) {
         this.optionBuilders.add(EditorOptionNode.Builder.<V>begin().setValue(option));
         return this.self;
      }

      public B addOptionBuilder(EditorOptionNode.Builder<V> optionBuilder) {
         this.optionBuilders.add(optionBuilder);
         return this.self;
      }

      public EditorExpandingOptionsNode<V> build() {
         if (this.listFactory == null) {
            throw new IllegalStateException("required fields not set!");
         } else {
            return (EditorExpandingOptionsNode<V>)super.build();
         }
      }

      @Override
      protected EditorOptionsNode<V> buildInternally() {
         List<EditorOptionNode<V>> options = this.optionBuilders
            .stream()
            .map(EditorOptionNode.Builder::build)
            .collect(this.listFactory::get, List::add, List::addAll);
         EditorOptionNode<V> currentValueData = null;

         for (EditorOptionNode<V> optionData : options) {
            if (optionData.getValue() == this.currentValue) {
               currentValueData = optionData;
               break;
            }
         }

         if (currentValueData == null) {
            throw new IllegalStateException("current value is not one of the options! " + this.currentValue);
         } else {
            return this.buildInternally(currentValueData, options);
         }
      }

      protected abstract EditorOptionsNode<V> buildInternally(EditorOptionNode<V> var1, List<EditorOptionNode<V>> var2);
   }

   public static final class FinalBuilder<V> extends EditorExpandingOptionsNode.Builder<V, EditorExpandingOptionsNode.FinalBuilder<V>> {
      private FinalBuilder(ListFactory listFactory) {
         super(listFactory);
      }

      public static <V> EditorExpandingOptionsNode.FinalBuilder<V> begin(ListFactory listFactory) {
         return new EditorExpandingOptionsNode.FinalBuilder(listFactory).setDefault();
      }

      @Override
      protected EditorOptionsNode<V> buildInternally(EditorOptionNode<V> currentValueData, List<EditorOptionNode<V>> options) {
         return new EditorExpandingOptionsNode<>(
            this.displayName, currentValueData, options, this.movable, this.listEntryFactory, this.tooltipSupplier, this.isActiveSupplier
         );
      }
   }
}
