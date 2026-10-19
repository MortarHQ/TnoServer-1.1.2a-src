package xaero.hud.category.ui.entry;

import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import xaero.hud.category.ObjectCategory;
import xaero.hud.category.ui.GuiCategoryEditor;
import xaero.hud.category.ui.node.EditorCategoryNode;
import xaero.lib.client.gui.widget.Tooltip;

public class EditorListEntryCategory<C extends ObjectCategory<?, C>, ED extends EditorCategoryNode<C, ?, ED>> extends EditorListRootEntry {
   private static final Tooltip HELP_TOOLTIP = new Tooltip("gui.xaero_category_help2", Style.EMPTY, true);
   private static final Tooltip PROTECTED_TOOLTIP = new Tooltip("gui.xaero_category_protected_category", Style.EMPTY, true);
   private static final Tooltip UP_TOOLTIP = new Tooltip("gui.xaero_category_category_move_up", Style.EMPTY, true);
   private static final Tooltip DOWN_TOOLTIP = new Tooltip("gui.xaero_category_category_move_down", Style.EMPTY, true);

   public EditorListEntryCategory(
      int screenWidth,
      int index,
      GuiCategoryEditor<?, ?, ?, ?, ?, ?>.SettingRowList rowList,
      ConnectionLineType lineType,
      EditorCategoryNode<?, ?, ?> node,
      EditorCategoryNode<?, ?, ?> parent,
      Supplier<Tooltip> tooltipSupplier,
      boolean isFinalExpanded
   ) {
      super(screenWidth, index, rowList, lineType, node);
      int subIndex = parent == null ? -1 : parent.getSubCategories().indexOf(node);
      ED dataCast = (ED)node;
      ED parentCast = (ED)parent;
      GuiCategoryEditor<C, ED, ?, ?, ?, ?>.SettingRowList rowListCast = rowList;
      boolean isCut = rowListCast.isCut(dataCast);
      ED currentCut = rowListCast.getCut();
      this.withSubEntry(this.getCategoryNameEntryFactory(dataCast, rowListCast, isCut, tooltipSupplier));
      EditorListRootEntry.CenteredEntryFactory pasteEntryFactory = this.getPasteEntryFactory(currentCut, isCut, dataCast, rowListCast);
      if (!dataCast.isExpanded() && dataCast.isMovable()) {
         if (!rowListCast.readOnly && !dataCast.getSettingsNode().getProtection()) {
            this.withSubEntry(this.getDuplicateEntryFactory(subIndex, parentCast, rowListCast));
         }

         if (rowListCast.hasCut()) {
            this.withSubEntry(pasteEntryFactory);
         }

         if (dataCast.getSettingsNode().getProtection()) {
            this.withSubEntry(this.getProtectedEntryFactory());
         } else {
            if (!rowListCast.readOnly && !rowListCast.hasCut()) {
               this.withSubEntry(this.getCutEntryFactory(dataCast, parentCast, rowListCast));
            }

            if (!rowListCast.readOnly && parent.getSubCategories().size() > 1) {
               this.withSubEntry(this.getPriorityEntryFactory(-1, parentCast, subIndex));
               this.withSubEntry(this.getPriorityEntryFactory(1, parentCast, subIndex));
            }
         }
      } else {
         if (rowListCast.hasCut()) {
            this.withSubEntry(pasteEntryFactory);
         }

         if (isFinalExpanded) {
            this.addHelpElement(HELP_TOOLTIP);
         }
      }
   }

   private EditorListRootEntry.CenteredEntryFactory getCategoryNameEntryFactory(
      ED dataCast, GuiCategoryEditor<C, ED, ?, ?, ?, ?>.SettingRowList rowListCast, boolean isCut, Supplier<Tooltip> tooltipSupplier
   ) {
      return (x, y, w, h, root) -> {
         Runnable action = isCut ? () -> rowListCast.pasteTo(dataCast) : dataCast.getExpandAction(rowListCast);
         EditorListEntryTextWithAction result = new EditorListEntryTextWithAction(x, y, w, h, this.index, this.rowList, this, action, tooltipSupplier);
         if (isCut) {
            result.setColor(-5636096);
            result.setHoverColor(-43691);
         }

         return result;
      };
   }

   private EditorListRootEntry.CenteredEntryFactory getPasteEntryFactory(
      ED currentCut, boolean isCut, ED dataCast, GuiCategoryEditor<C, ED, ?, ?, ?, ?>.SettingRowList rowListCast
   ) {
      Tooltip pasteTooltip = this.getPasteTooltip(currentCut, isCut);
      if (pasteTooltip != null) {
         pasteTooltip.setAutoLinebreak(false);
      }

      return (x, y, w, h, root) -> new EditorListTextButtonEntry(
         x + 248, y + 2, this.index, this.rowList, Component.literal("←"), -5592406, -1, 5, dataCast.getPasteAction(rowListCast), this, pasteTooltip
      );
   }

   private Tooltip getPasteTooltip(ED currentCut, boolean isCut) {
      if (currentCut == null) {
         return null;
      }

      if (isCut) {
         return new Tooltip("gui.xaero_category_paste_cancel", Style.EMPTY, true);
      }

      Component component = Component.translatable("gui.xaero_category_paste", new Object[]{currentCut.getDisplayName(), this.node.getDisplayName()});
      return new Tooltip(component, true);
   }

   private EditorListRootEntry.CenteredEntryFactory getDuplicateEntryFactory(
      int subIndex, ED parentCast, GuiCategoryEditor<C, ED, ?, ?, ?, ?>.SettingRowList rowListCast
   ) {
      Component duplicateTooltipComponent = Component.translatable("gui.xaero_category_duplicate", new Object[]{this.node.getDisplayName()});
      Tooltip duplicateTooltip = new Tooltip(duplicateTooltipComponent, true);
      duplicateTooltip.setAutoLinebreak(false);
      return (x, y, w, h, root) -> new EditorListTextButtonEntry(
         x + 230,
         y + 2,
         this.index,
         this.rowList,
         Component.literal("+"),
         -5592406,
         -1,
         5,
         parentCast.getDuplicateAction(subIndex, rowListCast),
         this,
         duplicateTooltip
      );
   }

   private EditorListRootEntry.CenteredEntryFactory getProtectedEntryFactory() {
      return (x, y, w, h, root) -> new EditorListTextButtonEntry(
         x - 24, y + 2, this.index, this.rowList, Component.literal("!"), -1644980, -171, 5, () -> false, this, PROTECTED_TOOLTIP
      );
   }

   private EditorListRootEntry.CenteredEntryFactory getCutEntryFactory(
      ED dataCast, ED parentCast, GuiCategoryEditor<C, ED, ?, ?, ?, ?>.SettingRowList rowListCast
   ) {
      Component cutTooltipComponent = Component.translatable("gui.xaero_category_cut", new Object[]{this.node.getDisplayName()});
      Tooltip cutTooltip = new Tooltip(cutTooltipComponent, true);
      cutTooltip.setAutoLinebreak(false);
      return (x, y, w, h, root) -> new EditorListTextButtonEntry(
         x + 248, y + 2, this.index, this.rowList, Component.literal("↔"), -5592406, -1, 5, dataCast.getCutAction(parentCast, rowListCast), this, cutTooltip
      );
   }

   private EditorListRootEntry.CenteredEntryFactory getPriorityEntryFactory(int direction, ED parentCast, int subIndex) {
      String label = direction < 0 ? "↑" : "↓";
      Tooltip tooltip = direction < 0 ? UP_TOOLTIP : DOWN_TOOLTIP;
      return (x, y, w, h, root) -> new EditorListTextButtonEntry(
         x - 32 + 8 * direction,
         y + 2,
         this.index,
         this.rowList,
         Component.literal(label),
         -5592406,
         -1,
         5,
         parentCast.getMoveAction(subIndex, direction, this.rowList),
         this,
         tooltip
      );
   }

   @Override
   public Component getMessage() {
      return Component.literal("");
   }
}
