package dev.aika.smsn.client.gui;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class MissingClothConfigScreen extends Screen {
   private static final String CLOTH_CONFIG_MODRINTH = "https://modrinth.com/mod/9s6osm5g";
   private static final String CLOTH_CONFIG_CURSEFORGE = "https://www.curseforge.com/minecraft/mc-mods/cloth-config";
   private final Component message = Component.translatable("gui.smsn.missing_cloth_config.message");
   private final Component curseforgeButton = Component.translatable("gui.smsn.missing_cloth_config.curseforge_download");
   private final Component modrinthButton = Component.translatable("gui.smsn.missing_cloth_config.modrinth_download");
   private final Screen parent;

   public MissingClothConfigScreen(Screen parent) {
      super(Component.translatable("gui.smsn.missing_cloth_config.title"));
      this.parent = parent;
   }

   protected void init() {
      super.init();
      int posX = (this.width - 200) / 2;
      int posY = this.height / 2 - 20;
      posY += this.font.wordWrapHeight(this.message, 300);
      this.addRenderableWidget(
         Button.builder(this.curseforgeButton, this.openLink("https://www.curseforge.com/minecraft/mc-mods/cloth-config")).bounds(posX, posY, 100, 20).build()
      );
      this.addRenderableWidget(
         Button.builder(this.modrinthButton, this.openLink("https://modrinth.com/mod/9s6osm5g")).bounds(posX + 100, posY, 100, 20).build()
      );
      this.addRenderableWidget(Button.builder(CommonComponents.GUI_BACK, this::onBack).bounds(posX, posY + 25, 200, 20).build());
   }

   private void onBack(Button ignoredButton) {
      Minecraft.getInstance().setScreen(this.parent);
   }

   private OnPress openLink(String link) {
      return b -> {
         if (this.minecraft != null) {
            this.minecraft.setScreen(new ConfirmLinkScreen(v -> {
               if (v) {
                  Util.getPlatform().openUri(link);
               }

               this.minecraft.setScreen(this);
            }, link, true));
         }
      };
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      super.render(graphics, mouseX, mouseY, partialTick);
      graphics.drawCenteredString(this.font, this.title, this.width / 2, this.height / 2 - 50, -1);
      MultiLineLabel.create(this.font, this.message, 300).renderCentered(graphics, this.width / 2, this.height / 2 - 30);
   }
}
