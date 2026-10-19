package dev.ftb.mods.ftbquests.client.gui.quests;

import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftblibrary.ui.Button;
import dev.ftb.mods.ftblibrary.ui.ContextMenuItem;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftbquests.net.CreateObjectMessage;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.reward.RewardType;
import dev.ftb.mods.ftbquests.quest.reward.RewardTypes;
import dev.ftb.mods.ftbquests.quest.theme.property.ThemeProperties;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class AddRewardButton extends Button {
   private final Quest quest;

   public AddRewardButton(Panel panel, Quest q) {
      super(panel, Component.translatable("gui.add"), ThemeProperties.ADD_ICON.get());
      this.quest = q;
      this.setSize(18, 18);
   }

   public void onClicked(MouseButton button) {
      this.playClickSound();
      List<ContextMenuItem> contextMenu = new ArrayList<>();

      for (RewardType type : RewardTypes.TYPES.values()) {
         if (type.getGuiProvider() != null) {
            contextMenu.add(
               new ContextMenuItem(
                  type.getDisplayName(),
                  type.getIconSupplier(),
                  b -> {
                     this.playClickSound();
                     type.getGuiProvider()
                        .openCreationGui(
                           this.parent, this.quest, reward -> NetworkManager.sendToServer(CreateObjectMessage.create(reward, type.makeExtraNBT()))
                        );
                  }
               )
            );
         }
      }

      this.getGui().openContextMenu(contextMenu);
   }

   public void drawBackground(GuiGraphics matrixStack, Theme theme, int x, int y, int w, int h) {
      if (this.isMouseOver()) {
         super.drawBackground(matrixStack, theme, x, y, w, h);
      }
   }
}
