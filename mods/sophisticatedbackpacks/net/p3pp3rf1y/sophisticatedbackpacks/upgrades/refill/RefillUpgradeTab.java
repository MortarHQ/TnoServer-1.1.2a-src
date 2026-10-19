package net.p3pp3rf1y.sophisticatedbackpacks.upgrades.refill;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.p3pp3rf1y.sophisticatedbackpacks.client.gui.SBPTranslationHelper;
import net.p3pp3rf1y.sophisticatedbackpacks.common.gui.BackpackContainer;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.UpgradeSettingsTab;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogic;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogicContainer;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogicControl;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogicControlBase.MatchButton;

public abstract class RefillUpgradeTab extends UpgradeSettingsTab<RefillUpgradeContainer> {
   private static final Component SCROLL_TOOLTIP = SBPTranslationHelper.INSTANCE
      .translUpgrade("refill.scroll.tooltip", new Object[0])
      .withStyle(new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.DARK_GRAY});
   private final FilterLogicControl<FilterLogic, FilterLogicContainer<FilterLogic>> filterLogicControl;
   private int slotBeingChanged = -1;
   private RefillUpgradeWrapper.TargetSlot targetSlotBeingChanged = null;
   private static List<Component> additionalTooltip = new ArrayList<>();

   private static void addToTooltip(ItemTooltipEvent event) {
      if (!additionalTooltip.isEmpty()) {
         LocalPlayer player = Minecraft.getInstance().player;
         if (player != null && player.containerMenu instanceof BackpackContainer) {
            event.getToolTip().addAll(additionalTooltip);
         }
      }
   }

   protected RefillUpgradeTab(RefillUpgradeContainer upgradeContainer, Position position, StorageScreenBase<?> screen, int slotsInRow, String upgradeName) {
      super(
         upgradeContainer,
         position,
         screen,
         SBPTranslationHelper.INSTANCE.translUpgrade(upgradeName, new Object[0]),
         SBPTranslationHelper.INSTANCE.translUpgradeTooltip(upgradeName)
      );
      this.filterLogicControl = (FilterLogicControl<FilterLogic, FilterLogicContainer<FilterLogic>>)this.addHideableChild(
         new RefillUpgradeTab.RefillFilterLogicControl(screen, slotsInRow)
      );
   }

   protected void moveSlotsToTab() {
      this.filterLogicControl.moveSlotsToView();
   }

   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      if (this.shouldRender.getAsBoolean()) {
         super.render(guiGraphics, mouseX, mouseY, partialTicks);
         if (!this.filterLogicControl.isMouseOver(mouseX, mouseY)) {
            this.resetAdditionalTooltip();
            if (this.slotBeingChanged > -1) {
               this.saveTargetSlot();
            }
         }
      }
   }

   private void resetAdditionalTooltip() {
      if (!additionalTooltip.isEmpty()) {
         additionalTooltip = new ArrayList<>();
      }
   }

   protected void onTabClose() {
      super.onTabClose();
      this.resetAdditionalTooltip();
   }

   private void saveTargetSlot() {
      ((RefillUpgradeContainer)this.getContainer()).setTargetSlot(this.slotBeingChanged, this.targetSlotBeingChanged);
      this.slotBeingChanged = -1;
   }

   static {
      NeoForge.EVENT_BUS.addListener(RefillUpgradeTab::addToTooltip);
   }

   public static class Advanced extends RefillUpgradeTab {
      public Advanced(RefillUpgradeContainer upgradeContainer, Position position, StorageScreenBase<?> screen, int slotsInRow) {
         super(upgradeContainer, position, screen, slotsInRow, "advanced_refill");
      }
   }

   public static class Basic extends RefillUpgradeTab {
      public Basic(RefillUpgradeContainer upgradeContainer, Position position, StorageScreenBase<?> screen, int slotsInRow) {
         super(upgradeContainer, position, screen, slotsInRow, "refill");
      }
   }

   private class RefillFilterLogicControl extends FilterLogicControl<FilterLogic, FilterLogicContainer<FilterLogic>> {
      private final int slotsInRow;

      public RefillFilterLogicControl(StorageScreenBase<?> screen, int slotsInRow) {
         super(
            screen,
            new Position(RefillUpgradeTab.this.x + 3, RefillUpgradeTab.this.y + 24),
            ((RefillUpgradeContainer)RefillUpgradeTab.this.getContainer()).getFilterLogicContainer(),
            slotsInRow,
            new MatchButton[0]
         );
         this.slotsInRow = slotsInRow;
      }

      protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
         super.renderWidget(guiGraphics, mouseX, mouseY, partialTicks);
         if (((RefillUpgradeContainer)RefillUpgradeTab.this.getContainer()).allowsTargetSlotSelection()) {
            this.renderTargetSlotAcronyms(guiGraphics);
            this.updateTargetSlotTooltip(mouseX, mouseY);
         }
      }

      private void updateTargetSlotTooltip(int mouseX, int mouseY) {
         if (this.isMouseOver(mouseX, mouseY)) {
            int slot = this.getSlot(mouseX, mouseY);
            if (RefillUpgradeTab.this.slotBeingChanged > -1) {
               this.updateTooltip(RefillUpgradeTab.this.targetSlotBeingChanged);
            } else {
               RefillUpgradeWrapper.TargetSlot targetSlot = ((RefillUpgradeContainer)RefillUpgradeTab.this.getContainer()).getTargetSlot(slot);
               if (RefillUpgradeTab.additionalTooltip.isEmpty() || !RefillUpgradeTab.additionalTooltip.get(0).equals(targetSlot.getDescription())) {
                  this.updateTooltip(targetSlot);
               }
            }
         }
      }

      private void renderTargetSlotAcronyms(GuiGraphics guiGraphics) {
         PoseStack poseStack = guiGraphics.pose();
         poseStack.pushPose();
         poseStack.translate(0.0F, 0.0F, 300.0F);
         ((RefillUpgradeContainer)RefillUpgradeTab.this.getContainer())
            .getSlots()
            .forEach(
               slot -> {
                  if (!slot.getItem().isEmpty()) {
                     int slotIndex = slot.getSlotIndex();
                     RefillUpgradeWrapper.TargetSlot ts = ((RefillUpgradeContainer)RefillUpgradeTab.this.getContainer()).getTargetSlot(slotIndex);
                     RefillUpgradeWrapper.TargetSlot targetSlot = RefillUpgradeTab.this.slotBeingChanged == slotIndex
                        ? RefillUpgradeTab.this.targetSlotBeingChanged
                        : ts;
                     guiGraphics.drawString(
                        this.font,
                        targetSlot.getAcronym(),
                        this.getX() + slotIndex % this.slotsInRow * 18 + 10,
                        this.getY() + slotIndex / this.slotsInRow * 18 + 2,
                        DyeColor.GREEN.getTextColor()
                     );
                  }
               }
            );
         poseStack.popPose();
      }

      private void updateTooltip(RefillUpgradeWrapper.TargetSlot targetSlot) {
         RefillUpgradeTab.this.resetAdditionalTooltip();
         RefillUpgradeTab.additionalTooltip
            .add(
               SBPTranslationHelper.INSTANCE
                  .translUpgrade("refill.target_slot.tooltip", new Object[]{targetSlot.getDescription()})
                  .withStyle(ChatFormatting.GRAY)
            );
         RefillUpgradeTab.additionalTooltip.add(RefillUpgradeTab.SCROLL_TOOLTIP);
      }

      public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
         super.render(guiGraphics, mouseX, mouseY, partialTicks);
         int slot = this.getSlot(mouseX, mouseY);
         if (RefillUpgradeTab.this.slotBeingChanged > -1 && RefillUpgradeTab.this.slotBeingChanged != slot) {
            RefillUpgradeTab.this.saveTargetSlot();
         }
      }

      public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
         int slot = this.getSlot(mouseX, mouseY);
         if (RefillUpgradeTab.this.slotBeingChanged == -1) {
            RefillUpgradeTab.this.slotBeingChanged = slot;
            RefillUpgradeTab.this.targetSlotBeingChanged = ((RefillUpgradeContainer)RefillUpgradeTab.this.getContainer()).getTargetSlot(slot);
         }

         RefillUpgradeTab.this.targetSlotBeingChanged = scrollY > 0.0
            ? RefillUpgradeTab.this.targetSlotBeingChanged.next()
            : RefillUpgradeTab.this.targetSlotBeingChanged.previous();
         return true;
      }

      private int getSlot(double mouseX, double mouseY) {
         return ((int)mouseX - this.getX()) / 18 + this.slotsInRow * (((int)mouseY - this.getY()) / 18);
      }
   }
}
