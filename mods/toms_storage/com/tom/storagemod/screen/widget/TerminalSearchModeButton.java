package com.tom.storagemod.screen.widget;

import com.tom.storagemod.util.IAutoFillTerminal;
import com.tom.storagemod.util.PopupMenuManager;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class TerminalSearchModeButton extends IconButton {
   private static final ResourceLocation ICON = ResourceLocation.tryBuild("toms_storage", "icons/search_mode");
   private static final ResourceLocation AUTO = ResourceLocation.tryBuild("toms_storage", "icons/search_mode_auto");
   private static final ResourceLocation KEEP = ResourceLocation.tryBuild("toms_storage", "icons/search_mode_keep");
   private static final ResourceLocation SYNC = ResourceLocation.tryBuild("toms_storage", "icons/search_mode_sync");
   private int searchType = -1;

   public TerminalSearchModeButton(int x, int y, PopupMenuManager menu, boolean canSync, Runnable sendUpdate) {
      super(x, y, Component.translatable(""), ICON, handleClick(menu, canSync, sendUpdate));
   }

   private static OnPress handleClick(PopupMenuManager menu, boolean canSync, Runnable sendUpdate) {
      return b0 -> {
         TerminalSearchModeButton b = (TerminalSearchModeButton)b0;
         String sh = IAutoFillTerminal.getHandlerNameOr(I18n.get("tooltip.toms_storage.recipe_book", new Object[0]));
         menu.open(
            b.getX() + 16,
            b.getY() + 16,
            new PopupMenuManager.ButtonElement(
               () -> Component.translatable(
                  "tooltip.toms_storage.opt.search_auto", new Object[]{(b.searchType & 1) != 0 ? CommonComponents.GUI_YES : CommonComponents.GUI_NO}
               ),
               () -> {
                  b.flipBit(1);
                  sendUpdate.run();
               }
            ),
            new PopupMenuManager.ButtonElement(
               () -> Component.translatable(
                  "tooltip.toms_storage.opt.search_keep", new Object[]{(b.searchType & 2) != 0 ? CommonComponents.GUI_YES : CommonComponents.GUI_NO}
               ),
               () -> {
                  b.flipBit(2);
                  sendUpdate.run();
               }
            ),
            new PopupMenuManager.ButtonElement(
               () -> canSync
                  ? Component.translatable(
                     "tooltip.toms_storage.opt.search_sync", new Object[]{sh, (b.searchType & 4) != 0 ? CommonComponents.GUI_YES : CommonComponents.GUI_NO}
                  )
                  : Component.literal("")
                     .append(
                        Component.translatable("tooltip.toms_storage.opt.search_sync", new Object[]{sh, CommonComponents.GUI_NO})
                           .withStyle(ChatFormatting.DARK_GRAY)
                     ),
               () -> {
                  b.flipBit(4);
                  sendUpdate.run();
               }
            ),
            new PopupMenuManager.ButtonElement(
               () -> canSync
                  ? Component.translatable(
                     "tooltip.toms_storage.opt.search_smart", new Object[]{sh, (b.searchType & 8) == 0 ? CommonComponents.GUI_YES : CommonComponents.GUI_NO}
                  )
                  : Component.literal("")
                     .append(
                        Component.translatable("tooltip.toms_storage.opt.search_smart", new Object[]{sh, CommonComponents.GUI_NO})
                           .withStyle(ChatFormatting.DARK_GRAY)
                     ),
               () -> {
                  b.flipBit(8);
                  sendUpdate.run();
               }
            )
         );
      };
   }

   private void flipBit(int i) {
      int s = this.searchType;
      if ((s & i) != 0) {
         s &= ~i;
      } else {
         s |= i;
      }

      this.setSearchType(s);
   }

   @Override
   protected void drawIcon(GuiGraphics st, int mouseX, int mouseY, float pt) {
      int x = this.getX();
      int y = this.getY();
      st.blitSprite(ICON, x + 1, y + 1, 14, 14);
      if ((this.searchType & 1) > 0) {
         st.blitSprite(AUTO, x + 1, y + 1, 14, 14);
      }

      if ((this.searchType & 2) > 0) {
         st.blitSprite(KEEP, x + 1, y + 1, 14, 14);
      }

      if ((this.searchType & 4) > 0) {
         st.blitSprite(SYNC, x + 1, y + 1, 14, 14);
      }
   }

   public void setSearchType(int searchType) {
      if (this.searchType != searchType) {
         StringBuilder sb = new StringBuilder("tooltip.toms_storage.search");
         if ((searchType & 1) > 0) {
            sb.append("_auto");
         }

         if ((searchType & 2) > 0) {
            sb.append("_keep");
         }

         if ((searchType & 4) > 0) {
            sb.append("_sync");
         }

         this.setTooltip(
            Tooltip.create(
               Component.translatable(
                  sb.toString(), new Object[]{IAutoFillTerminal.getHandlerNameOr(I18n.get("tooltip.toms_storage.recipe_book", new Object[0]))}
               )
            )
         );
      }

      this.searchType = searchType;
   }

   public int getSearchType() {
      return this.searchType;
   }
}
