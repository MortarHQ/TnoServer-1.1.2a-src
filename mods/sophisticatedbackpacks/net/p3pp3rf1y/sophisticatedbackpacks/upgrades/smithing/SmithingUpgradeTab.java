package net.p3pp3rf1y.sophisticatedbackpacks.upgrades.smithing;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CyclingSlotBackground;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.inventory.SmithingScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SmithingTemplateItem;
import net.p3pp3rf1y.sophisticatedbackpacks.client.gui.SBPTranslationHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.UpgradeSettingsTab;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TextureBlitData;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.UV;

public class SmithingUpgradeTab extends UpgradeSettingsTab<SmithingUpgradeContainer> {
   public static final TextureBlitData ARROW = new TextureBlitData(GuiHelper.GUI_CONTROLS, Dimension.SQUARE_256, new UV(56, 221), new Dimension(14, 15));
   public static final TextureBlitData RED_CROSS = new TextureBlitData(GuiHelper.GUI_CONTROLS, Dimension.SQUARE_256, new UV(113, 216), new Dimension(15, 15));
   private final CyclingSlotBackground templateIcon;
   private final CyclingSlotBackground baseIcon;
   private final CyclingSlotBackground additionalIcon;
   private final ArmorStand armorStandPreview;

   public SmithingUpgradeTab(SmithingUpgradeContainer upgradeContainer, Position position, StorageScreenBase<?> screen) {
      super(
         upgradeContainer,
         position,
         screen,
         SBPTranslationHelper.INSTANCE.translUpgrade("smithing", new Object[0]),
         SBPTranslationHelper.INSTANCE.translUpgradeTooltip("smithing")
      );
      this.openTabDimension = new Dimension(103, 100);
      this.armorStandPreview = new ArmorStand(this.minecraft.level, 0.0, 0.0, 0.0);
      this.armorStandPreview.setNoBasePlate(true);
      this.armorStandPreview.setShowArms(true);
      this.armorStandPreview.yBodyRot = 210.0F;
      this.armorStandPreview.setXRot(25.0F);
      this.armorStandPreview.yHeadRot = this.armorStandPreview.getYRot();
      this.armorStandPreview.yHeadRotO = this.armorStandPreview.getYRot();
      this.updateArmorStandPreview();
      this.templateIcon = new CyclingSlotBackground(((SmithingUpgradeContainer)this.getContainer()).getTemplateSlot().index);
      this.baseIcon = new CyclingSlotBackground(((SmithingUpgradeContainer)this.getContainer()).getBaseSlot().index);
      this.additionalIcon = new CyclingSlotBackground(((SmithingUpgradeContainer)this.getContainer()).getAdditionalSlot().index);
      ((SmithingUpgradeContainer)this.getContainer()).setOnResultChangedHandler(this::updateArmorStandPreview);
   }

   private void updateArmorStandPreview() {
      ItemStack stack = ((SmithingUpgradeContainer)this.getContainer()).getResultSlot().getItem();
      if (this.armorStandPreview != null) {
         for (EquipmentSlot equipmentslot : EquipmentSlot.values()) {
            this.armorStandPreview.setItemSlot(equipmentslot, ItemStack.EMPTY);
         }

         if (!stack.isEmpty()) {
            ItemStack itemstack = stack.copy();
            if (stack.getItem() instanceof ArmorItem armoritem) {
               this.armorStandPreview.setItemSlot(armoritem.getEquipmentSlot(), itemstack);
            } else {
               this.armorStandPreview.setItemSlot(EquipmentSlot.OFFHAND, itemstack);
            }
         }
      }
   }

   protected void renderBg(GuiGraphics guiGraphics, Minecraft minecraft, int mouseX, int mouseY) {
      super.renderBg(guiGraphics, minecraft, mouseX, mouseY);
      if (((SmithingUpgradeContainer)this.getContainer()).isOpen()) {
         this.renderSlotBg(guiGraphics, ((SmithingUpgradeContainer)this.getContainer()).getTemplateSlot());
         this.renderSlotBg(guiGraphics, ((SmithingUpgradeContainer)this.getContainer()).getBaseSlot());
         this.renderSlotBg(guiGraphics, ((SmithingUpgradeContainer)this.getContainer()).getAdditionalSlot());
         this.renderSlotBg(guiGraphics, ((SmithingUpgradeContainer)this.getContainer()).getResultSlot());
         this.templateIcon
            .render(
               ((StorageScreenBase)this.screen).getMenu(),
               guiGraphics,
               0.0F,
               ((StorageScreenBase)this.screen).getLeftX(),
               ((StorageScreenBase)this.screen).getTopY()
            );
         this.baseIcon
            .render(
               ((StorageScreenBase)this.screen).getMenu(),
               guiGraphics,
               0.0F,
               ((StorageScreenBase)this.screen).getLeftX(),
               ((StorageScreenBase)this.screen).getTopY()
            );
         this.additionalIcon
            .render(
               ((StorageScreenBase)this.screen).getMenu(),
               guiGraphics,
               0.0F,
               ((StorageScreenBase)this.screen).getLeftX(),
               ((StorageScreenBase)this.screen).getTopY()
            );
      }
   }

   private void renderSlotBg(GuiGraphics guiGraphics, Slot slot) {
      GuiHelper.renderSlotsBackground(
         guiGraphics, slot.x + ((StorageScreenBase)this.screen).getGuiLeft() - 1, slot.y + ((StorageScreenBase)this.screen).getGuiTop() - 1, 1, 1
      );
   }

   public void renderTooltip(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY) {
      super.renderTooltip(screen, guiGraphics, mouseX, mouseY);
      this.renderOnboardingTooltips(guiGraphics, mouseX, mouseY);
   }

   protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      super.renderWidget(guiGraphics, mouseX, mouseY, partialTicks);
      if (this.isOpen) {
         Slot resultSlot = ((SmithingUpgradeContainer)this.getContainer()).getResultSlot();
         int inputSlotsY = resultSlot.y + ((StorageScreenBase)this.screen).getGuiTop();
         int additionalSlotX = ((SmithingUpgradeContainer)this.getContainer()).getAdditionalSlot().x + ((StorageScreenBase)this.screen).getGuiLeft();
         int resultSlotX = resultSlot.x + ((StorageScreenBase)this.screen).getGuiLeft();
         int arrowX = this.getArrowX(additionalSlotX, resultSlotX);
         int arrowY = this.getArrowY(inputSlotsY);
         GuiHelper.blit(guiGraphics, arrowX, arrowY, ARROW);
         if (this.hasRecipeError()) {
            GuiHelper.blit(guiGraphics, arrowX, arrowY, RED_CROSS);
         }

         InventoryScreen.renderEntityInInventory(
            guiGraphics,
            this.getX() + this.getWidth() / 2.0F,
            this.getTopY() + this.getHeight() - 10,
            25.0F,
            SmithingScreen.ARMOR_STAND_TRANSLATION,
            SmithingScreen.ARMOR_STAND_ANGLE,
            null,
            this.armorStandPreview
         );
      }
   }

   private int getArrowY(int inputSlotsY) {
      return inputSlotsY + 1;
   }

   private int getArrowX(int additionalSlotX, int resultSlotX) {
      return additionalSlotX + 18 + (resultSlotX - (additionalSlotX + 18)) / 2 - ARROW.getWidth() / 2 - 1;
   }

   public void tick() {
      super.tick();
      this.templateIcon.tick(SmithingScreen.EMPTY_SLOT_SMITHING_TEMPLATES);
      ItemStack templateItem = ((SmithingUpgradeContainer)this.getContainer()).getTemplateSlot().getItem();
      if (templateItem.getItem() instanceof SmithingTemplateItem smithingTemplate) {
         this.baseIcon.tick(smithingTemplate.getBaseSlotEmptyIcons());
         this.additionalIcon.tick(smithingTemplate.getAdditionalSlotEmptyIcons());
      } else {
         this.baseIcon.tick(List.of());
         this.additionalIcon.tick(List.of());
      }
   }

   protected void moveSlotsToTab() {
      Slot templateSlot = ((SmithingUpgradeContainer)this.getContainer()).getTemplateSlot();
      templateSlot.x = this.x - ((StorageScreenBase)this.screen).getGuiLeft() + 4;
      templateSlot.y = this.y - ((StorageScreenBase)this.screen).getGuiTop() + 1 + 24;
      Slot baseSlot = ((SmithingUpgradeContainer)this.getContainer()).getBaseSlot();
      baseSlot.x = templateSlot.x + 18;
      baseSlot.y = this.y - ((StorageScreenBase)this.screen).getGuiTop() + 1 + 24;
      Slot additionalSlot = ((SmithingUpgradeContainer)this.getContainer()).getAdditionalSlot();
      additionalSlot.x = baseSlot.x + 18;
      additionalSlot.y = this.y - ((StorageScreenBase)this.screen).getGuiTop() + 1 + 24;
      Slot resultSlot = ((SmithingUpgradeContainer)this.getContainer()).getResultSlot();
      resultSlot.x = this.x - ((StorageScreenBase)this.screen).getGuiLeft() + this.getWidth() - 2 - 3 - 18;
      resultSlot.y = this.y - ((StorageScreenBase)this.screen).getGuiTop() + 1 + 24;
   }

   private boolean isHoveringRedCross(int mouseX, int mouseY) {
      Slot additionalSlot = ((SmithingUpgradeContainer)this.getContainer()).getAdditionalSlot();
      int arrowX = this.getArrowX(
         additionalSlot.x + ((StorageScreenBase)this.screen).getGuiLeft(),
         ((SmithingUpgradeContainer)this.getContainer()).getResultSlot().x + ((StorageScreenBase)this.screen).getGuiLeft()
      );
      int arrowY = this.getArrowY(additionalSlot.y + ((StorageScreenBase)this.screen).getGuiTop());
      return mouseX >= arrowX && mouseX < arrowX + RED_CROSS.getWidth() && mouseY >= arrowY && mouseY < arrowY + RED_CROSS.getHeight();
   }

   private boolean isHoveringEmptySlot(Slot slot, int mouseX, int mouseY) {
      return mouseX >= slot.x + ((StorageScreenBase)this.screen).getGuiLeft()
         && mouseX < slot.x + ((StorageScreenBase)this.screen).getGuiLeft() + 16
         && mouseY >= slot.y + ((StorageScreenBase)this.screen).getGuiTop()
         && mouseY < slot.y + ((StorageScreenBase)this.screen).getGuiTop() + 16
         && slot.getItem().isEmpty();
   }

   private void renderOnboardingTooltips(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      if (this.hasRecipeError() && this.isHoveringRedCross(mouseX, mouseY)) {
         Component tooltip = SmithingScreen.ERROR_TOOLTIP;
         this.renderOnboardingTooltip(guiGraphics, mouseX, mouseY, tooltip);
      } else if (this.isHoveringEmptySlot(((SmithingUpgradeContainer)this.getContainer()).getTemplateSlot(), mouseX, mouseY)) {
         this.renderOnboardingTooltip(guiGraphics, mouseX, mouseY, SmithingScreen.MISSING_TEMPLATE_TOOLTIP);
      } else if (((SmithingUpgradeContainer)this.getContainer()).getTemplateSlot().getItem().getItem() instanceof SmithingTemplateItem smithingTemplate) {
         if (this.isHoveringEmptySlot(((SmithingUpgradeContainer)this.getContainer()).getBaseSlot(), mouseX, mouseY)) {
            this.renderOnboardingTooltip(guiGraphics, mouseX, mouseY, smithingTemplate.getBaseSlotDescription());
         } else if (this.isHoveringEmptySlot(((SmithingUpgradeContainer)this.getContainer()).getAdditionalSlot(), mouseX, mouseY)) {
            this.renderOnboardingTooltip(guiGraphics, mouseX, mouseY, smithingTemplate.getAdditionSlotDescription());
         }
      }
   }

   private void renderOnboardingTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY, Component tooltip) {
      guiGraphics.pose().pushPose();
      guiGraphics.pose().translate(0.0F, 0.0F, 410.0F);
      guiGraphics.renderTooltip(this.font, this.font.split(tooltip, 115), mouseX, mouseY);
      guiGraphics.pose().popPose();
   }

   private boolean hasRecipeError() {
      return ((SmithingUpgradeContainer)this.getContainer()).getTemplateSlot().hasItem()
         && ((SmithingUpgradeContainer)this.getContainer()).getBaseSlot().hasItem()
         && ((SmithingUpgradeContainer)this.getContainer()).getAdditionalSlot().hasItem()
         && !((SmithingUpgradeContainer)this.getContainer()).getResultSlot().hasItem();
   }
}
