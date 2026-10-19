package net.blay09.mods.waystones.client.gui.screen;

import java.util.Locale;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.WaystoneVisibility;
import net.blay09.mods.waystones.client.gui.widget.WaystoneVisbilityButton;
import net.blay09.mods.waystones.menu.WaystoneEditMenu;
import net.blay09.mods.waystones.network.message.EditWaystoneMessage;
import net.blay09.mods.waystones.network.message.RequestManageWaystoneModifiersMessage;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class WaystoneEditScreen extends AbstractContainerScreen<WaystoneEditMenu> {
   private EditBox textField;
   private WaystoneVisbilityButton visibilityButton;
   private ImageButton modifierButton;
   private Button saveButton;

   public WaystoneEditScreen(WaystoneEditMenu container, Inventory playerInventory, Component title) {
      super(container, playerInventory, title);
      this.imageHeight = 210;
      this.titleLabelY = 44;
   }

   public void init() {
      super.init();
      Waystone waystone = ((WaystoneEditMenu)this.menu).getWaystone();
      String oldText = waystone.getName().getString();
      if (this.textField != null) {
         oldText = this.textField.getValue();
      }

      WaystoneVisibility oldVisibility = waystone.getVisibility();
      if (this.visibilityButton != null) {
         oldVisibility = this.visibilityButton.getVisibility();
      }

      int y = this.topPos + this.titleLabelY + 16;
      Component error = ((WaystoneEditMenu)this.menu).getError();
      if (error != null) {
         y += 9;
      }

      this.textField = new EditBox(Minecraft.getInstance().font, this.leftPos, y, 176, 20, this.textField, Component.empty());
      this.textField.setMaxLength(128);
      this.textField.setValue(oldText);
      this.textField.setEditable(((WaystoneEditMenu)this.menu).canEdit());
      this.addRenderableWidget(this.textField);
      if (((WaystoneEditMenu)this.menu).canEdit() && oldText.isEmpty()) {
         this.setInitialFocus(this.textField);
      }

      y += 28;
      this.visibilityButton = new WaystoneVisbilityButton(
         this.leftPos, y, oldVisibility, ((WaystoneEditMenu)this.menu).getVisibilityOptions(), ((WaystoneEditMenu)this.menu).canEdit()
      );
      this.visibilityButton.active = ((WaystoneEditMenu)this.menu).canEdit() && ((WaystoneEditMenu)this.menu).getVisibilityOptions().size() > 1;
      this.addRenderableWidget(this.visibilityButton);
      y += 24;
      WidgetSprites modifierSprites = new WidgetSprites(
         ResourceLocation.withDefaultNamespace("waystones/modifier_button"), ResourceLocation.withDefaultNamespace("waystones/modifier_button_highlighted")
      );
      this.modifierButton = new ImageButton(
         20,
         20,
         modifierSprites,
         button -> {
            Balm.getNetworking()
               .sendToServer(
                  new EditWaystoneMessage(
                     ((WaystoneEditMenu)this.menu).getWaystone().getWaystoneUid(), this.textField.getValue(), this.visibilityButton.getVisibility()
                  )
               );
            Balm.getNetworking().sendToServer(new RequestManageWaystoneModifiersMessage(((WaystoneEditMenu)this.menu).getWaystone().getPos()));
         },
         Component.literal("gui.waystones.waystone_settings.manage_modifiers")
      );
      this.modifierButton.setPosition(this.leftPos, y);
      this.addRenderableWidget(this.modifierButton);
      y += 24;
      this.saveButton = Button.builder(
            ((WaystoneEditMenu)this.menu).canEdit()
               ? Component.translatable("gui.waystones.waystone_settings.save")
               : Component.translatable("gui.waystones.waystone_settings.close"),
            it -> this.onClose()
         )
         .pos(this.leftPos + 88 - 50, y)
         .size(100, 20)
         .build();
      this.addRenderableWidget(this.saveButton);
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (((WaystoneEditMenu)this.menu).canEdit() && this.textField.isMouseOver(mouseX, mouseY) && button == 1) {
         this.textField.setValue("");
         return true;
      } else {
         return super.mouseClicked(mouseX, mouseY, button);
      }
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (!this.textField.keyPressed(keyCode, scanCode, modifiers) && !this.textField.isFocused()) {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }

      if (keyCode == 256 || keyCode == 257) {
         this.onClose();
      }

      return true;
   }

   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      super.render(guiGraphics, mouseX, mouseY, partialTicks);
      this.renderTooltip(guiGraphics, mouseX, mouseY);
      if (this.textField != null && this.textField.getValue().isEmpty()) {
         guiGraphics.drawString(
            Minecraft.getInstance().font, Component.translatable("waystones.untitled_waystone"), this.textField.getX() + 4, this.textField.getY() + 6, 8421504
         );
      }
   }

   protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      guiGraphics.drawCenteredString(this.font, this.title, 88, this.titleLabelY, -1);
      Component error = ((WaystoneEditMenu)this.menu).getError();
      if (error != null) {
         guiGraphics.drawCenteredString(this.font, error, 88, this.titleLabelY + 12, ChatFormatting.RED.getColor());
      }

      guiGraphics.drawString(
         this.font,
         Component.translatable("gui.waystones.waystone_settings.visibility." + this.visibilityButton.getVisibility().name().toLowerCase(Locale.ROOT)),
         24,
         this.visibilityButton.getY() - this.topPos + 6,
         -1,
         true
      );
      MutableComponent modifiersComponent = ((WaystoneEditMenu)this.menu).getModifierCount() > 0
         ? Component.translatable("gui.waystones.waystone_settings.modifiers_active", new Object[]{((WaystoneEditMenu)this.menu).getModifierCount()})
         : Component.translatable("gui.waystones.waystone_settings.no_modifiers_active");
      guiGraphics.drawString(
         this.font,
         modifiersComponent,
         24,
         this.modifierButton.getY() - this.topPos + 6,
         ((WaystoneEditMenu)this.menu).getModifierCount() > 0 ? -11141291 : -5592406,
         true
      );
   }

   protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
   }

   public void onClose() {
      if (this.textField != null && this.visibilityButton != null) {
         Balm.getNetworking()
            .sendToServer(
               new EditWaystoneMessage(
                  ((WaystoneEditMenu)this.menu).getWaystone().getWaystoneUid(), this.textField.getValue(), this.visibilityButton.getVisibility()
               )
            );
      }

      super.onClose();
   }
}
