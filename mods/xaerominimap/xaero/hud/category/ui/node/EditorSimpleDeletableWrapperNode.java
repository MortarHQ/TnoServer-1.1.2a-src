package xaero.hud.category.ui.node;

import javax.annotation.Nonnull;
import xaero.hud.category.ui.GuiCategoryEditor;
import xaero.hud.category.ui.entry.ConnectionLineType;
import xaero.hud.category.ui.entry.EditorListEntryDeletableListElement;
import xaero.hud.category.ui.entry.EditorListRootEntry;
import xaero.hud.category.ui.entry.EditorListRootEntryFactory;
import xaero.hud.category.ui.node.tooltip.IEditorDataTooltipSupplier;

public class EditorSimpleDeletableWrapperNode<S extends Comparable<S>> extends EditorSimpleWrapperNode<S> {
   private final EditorSimpleDeletableWrapperNode.DeletionCallback deletionCallback;

   protected EditorSimpleDeletableWrapperNode(
      @Nonnull S element,
      boolean movable,
      @Nonnull EditorListRootEntryFactory listEntryFactory,
      IEditorDataTooltipSupplier tooltipSupplier,
      @Nonnull EditorSimpleDeletableWrapperNode.DeletionCallback deletionCallback
   ) {
      super(element, movable, listEntryFactory, tooltipSupplier);
      this.deletionCallback = deletionCallback;
   }

   public EditorSimpleDeletableWrapperNode.DeletionCallback getDeletionCallback() {
      return this.deletionCallback;
   }

   public static final class Builder<S extends Comparable<S>> extends EditorSimpleWrapperNode.Builder<S, EditorSimpleDeletableWrapperNode.Builder<S>> {
      private EditorSimpleDeletableWrapperNode.DeletionCallback deletionCallback;

      private Builder() {
      }

      public EditorSimpleDeletableWrapperNode.Builder<S> setDefault() {
         super.setDefault();
         this.setDeletionCallback(null);
         return this.self;
      }

      @Override
      protected EditorListRootEntry mainEntryFactory(
         EditorNode data,
         EditorNode parent,
         int index,
         ConnectionLineType lineType,
         GuiCategoryEditor<?, ?, ?, ?, ?, ?>.SettingRowList rowList,
         int screenWidth,
         boolean isFinalExpanded
      ) {
         EditorSimpleDeletableWrapperNode<?> sdwData = (EditorSimpleDeletableWrapperNode<?>)data;
         return new EditorListEntryDeletableListElement(
            screenWidth, index, rowList, lineType, sdwData, parent, sdwData.getDeletionCallback(), data.getTooltipSupplier(parent), rowList.readOnly
         );
      }

      public EditorSimpleDeletableWrapperNode.Builder<S> setDeletionCallback(EditorSimpleDeletableWrapperNode.DeletionCallback deletionCallback) {
         this.deletionCallback = deletionCallback;
         return this.self;
      }

      public EditorSimpleDeletableWrapperNode<S> build() {
         if (this.deletionCallback == null) {
            throw new IllegalStateException("required fields not set!");
         } else {
            return (EditorSimpleDeletableWrapperNode<S>)super.build();
         }
      }

      @Override
      protected EditorNode buildInternally() {
         return new EditorSimpleDeletableWrapperNode(this.element, this.movable, this.listEntryFactory, this.tooltipSupplier, this.deletionCallback);
      }

      public static <S extends Comparable<S>> EditorSimpleDeletableWrapperNode.Builder<S> begin() {
         return new EditorSimpleDeletableWrapperNode.Builder<S>().setDefault();
      }
   }

   public interface DeletionCallback {
      boolean delete(EditorNode var1, EditorSimpleDeletableWrapperNode<?> var2, GuiCategoryEditor<?, ?, ?, ?, ?, ?>.SettingRowList var3);
   }
}
