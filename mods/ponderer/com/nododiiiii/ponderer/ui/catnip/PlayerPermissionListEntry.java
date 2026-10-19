package com.nododiiiii.ponderer.ui.catnip;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.ConfigScreenList.LabeledEntry;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class PlayerPermissionListEntry extends LabeledEntry implements SearchableListEntry {
   private final String subject;
   private final String searchText;
   private final List<ActionStripListEntry.ButtonModel> buttons;

   public PlayerPermissionListEntry(String subject, @Nullable String tooltip, List<ActionStripListEntry.ButtonModel> buttons) {
      super(subject == null ? "" : subject);
      this.subject = subject == null ? "" : subject;
      this.searchText = (this.subject + " " + (tooltip == null ? "" : tooltip)).toLowerCase(Locale.ROOT);
      this.buttons = new ArrayList<>(buttons);
      this.getLabelTooltip().add(Component.literal(this.subject));
      if (tooltip != null && !tooltip.isBlank()) {
         this.getLabelTooltip().add(Component.literal(tooltip));
      }

      for (ActionStripListEntry.ButtonModel button : this.buttons) {
         this.listeners.add(button.widget());
      }
   }

   @Override
   public boolean matchesQuery(String query) {
      return this.searchText.contains(query);
   }

   @Override
   public void highlightEntry() {
      this.annotations.put("highlight", ":)");
   }

   protected int getLabelWidth(int totalWidth) {
      return EntryTextSupport.compactLabelWidth(totalWidth);
   }

   public void tick() {
      super.tick();
      ActionStripListEntry.tickButtons(this.buttons);
   }

   public void render(GuiGraphics graphics, int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovered, float partialTicks) {
      this.label.withText(this.subject);
      super.render(graphics, index, y, x, width, height, mouseX, mouseY, hovered, partialTicks);
      int labelWidth = this.getLabelWidth(width);
      int controlWidth = EntryTextSupport.controlAreaWidth(width, labelWidth);
      int controlX = EntryTextSupport.rightAlignedControlX(x, width, controlWidth);
      ActionStripListEntry.renderButtonCluster(graphics, this.buttons, controlX, controlWidth, y, height, mouseX, mouseY, partialTicks);
   }

   public Component getNarration() {
      return Component.literal(this.subject);
   }
}
