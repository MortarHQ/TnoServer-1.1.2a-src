package net.p3pp3rf1y.sophisticatedbackpacks.upgrades.anvil;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.inventory.Slot;
import net.p3pp3rf1y.sophisticatedbackpacks.client.gui.SBPTranslationHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.UpgradeSettingsTab;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.TextBox;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TextureBlitData;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.UV;

public class AnvilUpgradeTab extends UpgradeSettingsTab<AnvilUpgradeContainer> {
   public static final TextureBlitData EDIT_ITEM_NAME_BACKGROUND_DISABLED = new TextureBlitData(
      GuiHelper.GUI_CONTROLS, Dimension.SQUARE_256, new UV(28, 115), new Dimension(100, 16)
   );
   public static final TextureBlitData EDIT_ITEM_NAME_BACKGROUND = new TextureBlitData(
      GuiHelper.GUI_CONTROLS, Dimension.SQUARE_256, new UV(28, 99), new Dimension(100, 16)
   );
   public static final TextureBlitData PLUS_SIGN = new TextureBlitData(GuiHelper.GUI_CONTROLS, Dimension.SQUARE_256, new UV(113, 203), new Dimension(13, 13));
   public static final TextureBlitData ARROW = new TextureBlitData(GuiHelper.GUI_CONTROLS, Dimension.SQUARE_256, new UV(56, 221), new Dimension(14, 15));
   public static final TextureBlitData RED_CROSS = new TextureBlitData(GuiHelper.GUI_CONTROLS, Dimension.SQUARE_256, new UV(113, 216), new Dimension(15, 15));
   private static final Component TOO_EXPENSIVE_TEXT = Component.translatable("container.repair.expensive");
   private final TextBox itemNameTextBox;

   public AnvilUpgradeTab(AnvilUpgradeContainer upgradeContainer, Position position, final StorageScreenBase<?> screen) {
      super(
         upgradeContainer,
         position,
         screen,
         SBPTranslationHelper.INSTANCE.translUpgrade("anvil", new Object[0]),
         SBPTranslationHelper.INSTANCE.translUpgradeTooltip("anvil")
      );
      this.openTabDimension = new Dimension(103, 92);
      this.itemNameTextBox = new TextBox(new Position(this.x + 5, this.y + 25), new Dimension(90, 14)) {
         public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (this.isEditable()) {
               this.setFocused(true);
               screen.setFocused(AnvilUpgradeTab.this.itemNameTextBox);
            }

            return super.mouseClicked(mouseX, mouseY, button);
         }

         protected void renderBg(GuiGraphics guiGraphics, Minecraft minecraft, int mouseX, int mouseY) {
            super.renderBg(guiGraphics, minecraft, mouseX, mouseY);
            TextureBlitData textureBlitData = ((Slot)((AnvilUpgradeContainer)AnvilUpgradeTab.this.getContainer()).getSlots().get(0)).hasItem()
               ? AnvilUpgradeTab.EDIT_ITEM_NAME_BACKGROUND
               : AnvilUpgradeTab.EDIT_ITEM_NAME_BACKGROUND_DISABLED;
            GuiHelper.blit(guiGraphics, this.getX() - 2, this.getY() - 2, textureBlitData, this.getWidth() + 4, this.getHeight() + 2);
         }
      };
      this.itemNameTextBox.setTextColor(-1);
      this.itemNameTextBox.setTextColorUneditable(-1);
      this.itemNameTextBox.setBordered(false);
      this.itemNameTextBox.setMaxLength(50);
      this.itemNameTextBox.setResponder(this::onNameChanged);
      this.itemNameTextBox.setValue(((AnvilUpgradeContainer)this.getContainer()).getItemName());
      this.addHideableChild(this.itemNameTextBox);
      this.itemNameTextBox.setEditable(!((Slot)upgradeContainer.getSlots().get(0)).getItem().isEmpty());
      ((AnvilUpgradeContainer)this.getContainer()).setNameChangeListener(() -> {
         this.itemNameTextBox.setValue(((AnvilUpgradeContainer)this.getContainer()).getItemName());
         this.itemNameTextBox.setEditable(!((Slot)((AnvilUpgradeContainer)this.getContainer()).getSlots().get(0)).getItem().isEmpty());
      });
   }

   private void onNameChanged(String name) {
      if (!((AnvilUpgradeContainer)this.getContainer()).isProcessingOnTakeLogic()) {
         ((AnvilUpgradeContainer)this.getContainer()).setItemName(name);
      }
   }

   protected void renderBg(GuiGraphics guiGraphics, Minecraft minecraft, int mouseX, int mouseY) {
      super.renderBg(guiGraphics, minecraft, mouseX, mouseY);
      if (((AnvilUpgradeContainer)this.getContainer()).isOpen()) {
         this.renderSlotBg(guiGraphics, (Slot)((AnvilUpgradeContainer)this.getContainer()).getSlots().get(0));
         this.renderSlotBg(guiGraphics, (Slot)((AnvilUpgradeContainer)this.getContainer()).getSlots().get(1));
         this.renderSlotBg(guiGraphics, (Slot)((AnvilUpgradeContainer)this.getContainer()).getSlots().get(2));
      }
   }

   private void renderSlotBg(GuiGraphics guiGraphics, Slot slot) {
      GuiHelper.renderSlotsBackground(
         guiGraphics, slot.x + ((StorageScreenBase)this.screen).getGuiLeft() - 1, slot.y + ((StorageScreenBase)this.screen).getGuiTop() - 1, 1, 1
      );
   }

   protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      super.renderWidget(guiGraphics, mouseX, mouseY, partialTicks);
      if (this.isOpen) {
         this.renderCost(guiGraphics, this.x + 3, this.y + 62);
         Slot firstSlot = (Slot)((AnvilUpgradeContainer)this.getContainer()).getSlots().get(0);
         int inputSlotsY = firstSlot.y + ((StorageScreenBase)this.screen).getGuiTop();
         int firstInputSlotX = firstSlot.x + ((StorageScreenBase)this.screen).getGuiLeft();
         int secondInputSlotX = ((Slot)((AnvilUpgradeContainer)this.getContainer()).getSlots().get(1)).x + ((StorageScreenBase)this.screen).getGuiLeft();
         Slot resultSlot = (Slot)((AnvilUpgradeContainer)this.getContainer()).getSlots().get(2);
         int resultSlotX = resultSlot.x + ((StorageScreenBase)this.screen).getGuiLeft();
         GuiHelper.blit(
            guiGraphics, firstInputSlotX + 18 + (secondInputSlotX - (firstInputSlotX + 18)) / 2 - PLUS_SIGN.getWidth() / 2 - 1, inputSlotsY + 2, PLUS_SIGN
         );
         int arrowX = secondInputSlotX + 18 + (resultSlotX - (secondInputSlotX + 18)) / 2 - ARROW.getWidth() / 2 - 1;
         int arrowY = inputSlotsY + 1;
         GuiHelper.blit(guiGraphics, arrowX, arrowY, ARROW);
         if (firstSlot.hasItem() && !resultSlot.hasItem()) {
            GuiHelper.blit(guiGraphics, arrowX, arrowY, RED_CROSS);
         }
      }
   }

   protected void moveSlotsToTab() {
      Slot firstInputSlot = (Slot)((AnvilUpgradeContainer)this.getContainer()).getSlots().get(0);
      firstInputSlot.x = this.x - ((StorageScreenBase)this.screen).getGuiLeft() + 4;
      firstInputSlot.y = this.y + 42 - ((StorageScreenBase)this.screen).getGuiTop() + 1;
      Slot secondInputSlot = (Slot)((AnvilUpgradeContainer)this.getContainer()).getSlots().get(1);
      secondInputSlot.x = this.x - ((StorageScreenBase)this.screen).getGuiLeft() + this.getWidth() / 2 - 9;
      secondInputSlot.y = this.y + 42 - ((StorageScreenBase)this.screen).getGuiTop() + 1;
      Slot resultSlot = (Slot)((AnvilUpgradeContainer)this.getContainer()).getSlots().get(2);
      resultSlot.x = this.x - ((StorageScreenBase)this.screen).getGuiLeft() + this.getWidth() - 2 - 3 - 18;
      resultSlot.y = this.y + 42 - ((StorageScreenBase)this.screen).getGuiTop() + 1;
   }

   protected void renderCost(GuiGraphics guiGraphics, int x, int y) {
      RenderSystem.disableBlend();
      int i = ((AnvilUpgradeContainer)this.getContainer()).getCost();
      if (i > 0) {
         int color = 8453920;
         Component component;
         if (i >= 40 && !this.minecraft.player.getAbilities().instabuild) {
            component = TOO_EXPENSIVE_TEXT;
            color = 16736352;
         } else if (!((Slot)((AnvilUpgradeContainer)this.getContainer()).getSlots().get(2)).hasItem()) {
            component = null;
         } else {
            component = Component.translatable("container.repair.cost", new Object[]{i});
            if (!((Slot)((AnvilUpgradeContainer)this.getContainer()).getSlots().get(2)).mayPickup(this.minecraft.player)) {
               color = 16736352;
            }
         }

         if (component != null) {
            int maxWidth = this.getWidth() - 9;
            List<FormattedCharSequence> lines = this.font.split(component, maxWidth);
            guiGraphics.fill(x, y, x + maxWidth, y + lines.size() * 12, 1325400064);
            int yOffset = 0;

            for (FormattedCharSequence line : lines) {
               int width = this.font.width(line);
               guiGraphics.drawString(this.font, line, x + 2 + (maxWidth - width) / 2.0F, y + 2 + yOffset, color, true);
               yOffset += 12;
            }
         }
      }
   }
}
