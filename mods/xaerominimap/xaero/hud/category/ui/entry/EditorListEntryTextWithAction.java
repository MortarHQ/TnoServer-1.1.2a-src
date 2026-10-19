package xaero.hud.category.ui.entry;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import xaero.hud.category.ui.GuiCategoryEditor;
import xaero.lib.client.gui.widget.Tooltip;

public class EditorListEntryTextWithAction extends EditorListEntryWithIconAndText {
   private final Runnable action;

   public EditorListEntryTextWithAction(
      int entryX,
      int entryY,
      int entryW,
      int entryH,
      int index,
      GuiCategoryEditor<?, ?, ?, ?, ?, ?>.SettingRowList rowList,
      EditorListRootEntry root,
      Runnable action,
      Supplier<Tooltip> tooltipSupplier
   ) {
      this(entryX, entryY, entryW, entryH, index, rowList, root.node.getDisplayName(), root, action, tooltipSupplier);
   }

   public EditorListEntryTextWithAction(
      int entryX,
      int entryY,
      int entryW,
      int entryH,
      int index,
      GuiCategoryEditor<?, ?, ?, ?, ?, ?>.SettingRowList rowList,
      Component text,
      EditorListRootEntry root,
      Runnable action,
      Supplier<Tooltip> tooltipSupplier
   ) {
      super(entryX, entryY, entryW, entryH, index, rowList, text, root, tooltipSupplier);
      this.action = action;
   }

   @Override
   public boolean selectAction() {
      this.action.run();
      Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
      return false;
   }
}
