package xaero.hud.category.ui.entry;

import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import xaero.hud.category.ui.GuiCategoryEditor;
import xaero.hud.category.ui.node.EditorNode;
import xaero.hud.category.ui.node.EditorSimpleDeletableWrapperNode;
import xaero.lib.client.gui.widget.Tooltip;

public class EditorListEntryDeletableListElement extends EditorListRootEntry {
   private final EditorSimpleDeletableWrapperNode.DeletionCallback deletionCallback;
   private final EditorNode parent;
   private static final Tooltip DELETE_TOOLTIP = new Tooltip("gui.xaero_category_delete_list_element", Style.EMPTY, true);

   public EditorListEntryDeletableListElement(
      int screenWidth,
      int index,
      GuiCategoryEditor<?, ?, ?, ?, ?, ?>.SettingRowList rowList,
      ConnectionLineType lineType,
      EditorSimpleDeletableWrapperNode<?> node,
      EditorNode parent,
      EditorSimpleDeletableWrapperNode.DeletionCallback deletionCallback,
      Supplier<Tooltip> tooltipSupplier,
      boolean readOnly
   ) {
      super(screenWidth, index, rowList, lineType, node);
      this.deletionCallback = deletionCallback;
      this.parent = parent;
      this.withSubEntry(
         (x, y, w, h, root) -> new EditorListEntryTextWithAction(x, y, w, h, index, rowList, this, node.getExpandAction(rowList), tooltipSupplier)
      );
      if (!readOnly) {
         this.withSubEntry(
            (x, y, w, h, root) -> new EditorListTextButtonEntry(
               x - 24,
               y + 2,
               index,
               rowList,
               Component.literal("x"),
               -5636096,
               -43691,
               5,
               () -> deletionCallback.delete(parent, node, rowList),
               this,
               DELETE_TOOLTIP
            )
         );
      }
   }

   @Override
   public boolean keyPressed(int i, int j, int k, boolean isRoot) {
      if (i == 261) {
         if (!this.rowList.readOnly && this.deletionCallback.delete(this.parent, (EditorSimpleDeletableWrapperNode<?>)this.node, this.rowList)) {
            this.rowList.restoreScrollAfterUpdate();
            this.rowList.updateEntries();
         }

         return false;
      } else {
         return super.keyPressed(i, j, k, isRoot);
      }
   }

   @Override
   public Component getMessage() {
      return this.node.getDisplayName();
   }
}
