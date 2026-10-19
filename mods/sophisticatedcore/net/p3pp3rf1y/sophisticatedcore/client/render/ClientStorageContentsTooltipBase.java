package net.p3pp3rf1y.sophisticatedcore.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TextureBlitData;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TranslationHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.UV;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeWrapper;
import net.p3pp3rf1y.sophisticatedcore.util.CountAbbreviator;
import net.p3pp3rf1y.sophisticatedcore.util.InventoryHelper;

public abstract class ClientStorageContentsTooltipBase implements ClientTooltipComponent {
   private static final int REFRESH_INTERVAL = 20;
   private static final String STORAGE_ITEM = "storage";
   protected static long lastRequestTime = 0L;
   @Nullable
   private static UUID storageUuid = null;
   private static List<IUpgradeWrapper> upgrades = new ArrayList<>();
   private static List<ItemStack> sortedContents = new ArrayList<>();
   private static final List<Component> tooltipLines = new ArrayList<>();
   private static int height = 0;
   private static int width = 0;
   private static boolean shouldRefreshContents = true;
   private static final TextureBlitData UPGRADE_ON = new TextureBlitData(GuiHelper.ICONS, Dimension.SQUARE_256, new UV(4, 128), Dimension.RECTANGLE_4_10);
   private static final TextureBlitData UPGRADE_OFF = new TextureBlitData(GuiHelper.ICONS, Dimension.SQUARE_256, new UV(0, 128), Dimension.RECTANGLE_4_10);
   private static final int MAX_STACKS_ON_LINE = 9;
   private static final int DEFAULT_STACK_WIDTH = 18;
   private static final int COUNT_PADDING = 2;

   public static void refreshContents() {
      shouldRefreshContents = true;
   }

   private void initContents(LocalPlayer player, IStorageWrapper wrapper) {
      UUID newUuid = wrapper.getContentsUuid().orElse(null);
      if (storageUuid == null && newUuid != null || storageUuid != null && !storageUuid.equals(newUuid)) {
         this.setLastRequestTime(0L);
         storageUuid = newUuid;
         this.setShouldRefreshContents(true);
      }

      if (storageUuid != null) {
         this.requestContents(player, wrapper);
      }

      this.refreshContents(wrapper);
   }

   protected void setLastRequestTime(long lastRequestTime) {
      ClientStorageContentsTooltipBase.lastRequestTime = lastRequestTime;
   }

   protected long getLastRequestTime() {
      return lastRequestTime;
   }

   private void requestContents(LocalPlayer player, IStorageWrapper wrapper) {
      if (this.getLastRequestTime() + 20L < player.level().getGameTime()) {
         this.setLastRequestTime(player.level().getGameTime());
         wrapper.getContentsUuid().ifPresent(this::sendInventorySyncRequest);
      }
   }

   protected abstract void sendInventorySyncRequest(UUID var1);

   private void refreshContents(IStorageWrapper wrapper) {
      if (this.shouldRefreshContents()) {
         this.setShouldRefreshContents(false);
         sortedContents.clear();
         upgrades.clear();
         tooltipLines.clear();
         if (storageUuid != null) {
            wrapper.onContentsNbtUpdated();
            sortedContents = InventoryHelper.getCompactedStacksSortedByCount(wrapper.getInventoryHandler());
            upgrades = new ArrayList<>(wrapper.getUpgradeHandler().getSlotWrappers().values());
            this.addMultiplierTooltip(wrapper);
            this.addFluidTooltip(wrapper);
            this.addEnergyTooltip(wrapper);
         }

         if (upgrades.isEmpty() && sortedContents.isEmpty()) {
            tooltipLines.add(Component.translatable(TranslationHelper.INSTANCE.translItemTooltip("storage") + ".empty").withStyle(ChatFormatting.YELLOW));
         }

         this.calculateHeight();
         this.calculateWidth();
      }
   }

   protected void setShouldRefreshContents(boolean shouldRefreshContents) {
      ClientStorageContentsTooltipBase.shouldRefreshContents = shouldRefreshContents;
   }

   protected boolean shouldRefreshContents() {
      return shouldRefreshContents;
   }

   private void calculateWidth() {
      int upgradesWidth = this.calculateUpgradesWidth();
      int contentsWidth = this.calculateContentsWidth();
      int tooltipContentsWidth = this.calculateTooltipLinesWidth();
      int stacksWidth = Math.max(upgradesWidth, contentsWidth);
      width = Math.max(stacksWidth, tooltipContentsWidth);
   }

   private int calculateTooltipLinesWidth() {
      return tooltipLines.stream().map(this::getTooltipWidth).max(Comparator.naturalOrder()).orElse(0);
   }

   private int calculateUpgradesWidth() {
      int upgradesWidth = 0;

      for (IUpgradeWrapper upgradeWrapper : upgrades) {
         upgradesWidth += (upgradeWrapper.canBeDisabled() ? 4 : 0) + 18;
      }

      return upgradesWidth;
   }

   private int calculateContentsWidth() {
      Font fontRenderer = Minecraft.getInstance().font;
      int contentsWidth = 0;

      for (int i = 0; i < sortedContents.size() && i < 9; i++) {
         int countWidth = this.getStackCountWidth(fontRenderer, sortedContents.get(i));
         contentsWidth += Math.max(countWidth, 18);
      }

      return contentsWidth;
   }

   private int getStackCountWidth(Font fontRenderer, ItemStack stack) {
      return fontRenderer.width(CountAbbreviator.abbreviate(stack.getCount())) + 2;
   }

   private int getTooltipWidth(Component component) {
      return Minecraft.getInstance().font.width(component.getVisualOrderText());
   }

   private void addMultiplierTooltip(IStorageWrapper wrapper) {
      double multiplier = wrapper.getInventoryHandler().getStackSizeMultiplier();
      if (multiplier > 1.0) {
         DecimalFormat df = new DecimalFormat("0.###");
         tooltipLines.add(
            Component.translatable(
                  TranslationHelper.INSTANCE.translItemTooltip("storage") + ".stack_multiplier",
                  new Object[]{Component.literal(df.format(multiplier)).withStyle(ChatFormatting.WHITE)}
               )
               .withStyle(ChatFormatting.GREEN)
         );
      }
   }

   private void addEnergyTooltip(IStorageWrapper wrapper) {
      wrapper.getEnergyStorage()
         .ifPresent(
            energyStorage -> tooltipLines.add(
               Component.translatable(
                     this.getEnergyTooltipTranslation(),
                     new Object[]{Component.literal(CountAbbreviator.abbreviate(energyStorage.getEnergyStored())).withStyle(ChatFormatting.WHITE)}
                  )
                  .withStyle(ChatFormatting.RED)
            )
         );
   }

   protected String getEnergyTooltipTranslation() {
      return TranslationHelper.INSTANCE.translItemTooltip("storage") + ".energy";
   }

   private void addFluidTooltip(IStorageWrapper wrapper) {
      wrapper.getFluidHandler()
         .ifPresent(
            fluidHandler -> {
               for (int tank = 0; tank < fluidHandler.getTanks(); tank++) {
                  FluidStack fluid = fluidHandler.getFluidInTank(tank);
                  if (fluid.isEmpty()) {
                     tooltipLines.add(Component.translatable(this.getEmptyFluidTooltipTranslation()).withStyle(ChatFormatting.BLUE));
                  } else {
                     tooltipLines.add(
                        Component.translatable(
                           this.getFluidTooltipTranslation(),
                           new Object[]{
                              Component.literal(CountAbbreviator.abbreviate(fluid.getAmount())).withStyle(ChatFormatting.WHITE),
                              Component.translatable(fluid.getFluidType().getDescriptionId(fluid)).withStyle(ChatFormatting.BLUE)
                           }
                        )
                     );
                  }
               }
            }
         );
   }

   protected String getFluidTooltipTranslation() {
      return TranslationHelper.INSTANCE.translItemTooltip("storage") + ".fluid";
   }

   protected String getEmptyFluidTooltipTranslation() {
      return TranslationHelper.INSTANCE.translItemTooltip("storage") + ".fluid_empty";
   }

   private void calculateHeight() {
      int upgradesHeight = upgrades.isEmpty() ? 0 : 32;
      int inventoryHeight = sortedContents.isEmpty() ? 0 : 12 + (1 + (sortedContents.size() - 1) / 9) * 20;
      int totalHeight = upgradesHeight + inventoryHeight + tooltipLines.size() * 10;
      height = totalHeight > 0 ? totalHeight : 12;
   }

   public int getWidth(Font font) {
      return width;
   }

   public int getHeight() {
      return height;
   }

   protected void renderTooltip(IStorageWrapper wrapper, Font font, int leftX, int topY, GuiGraphics guiGraphics) {
      Minecraft minecraft = Minecraft.getInstance();
      LocalPlayer player = minecraft.player;
      if (player != null) {
         this.initContents(player, wrapper);
         this.renderComponent(font, leftX, topY, guiGraphics, minecraft);
      }
   }

   private void renderComponent(Font font, int leftX, int topY, GuiGraphics guiGraphics, Minecraft minecraft) {
      for (Component tooltipLine : tooltipLines) {
         topY = this.renderTooltipLine(guiGraphics, leftX, topY, font, tooltipLine);
      }

      this.renderContentsTooltip(minecraft, font, leftX, topY, guiGraphics);
   }

   private void renderContentsTooltip(Minecraft minecraft, Font font, int leftX, int topY, GuiGraphics guiGraphics) {
      if (!upgrades.isEmpty()) {
         topY = this.renderTooltipLine(
            guiGraphics,
            leftX,
            topY,
            font,
            Component.translatable(TranslationHelper.INSTANCE.translItemTooltip("storage") + ".upgrades").withStyle(ChatFormatting.YELLOW)
         );
         topY = this.renderUpgrades(guiGraphics, leftX, topY);
      }

      if (!sortedContents.isEmpty()) {
         topY = this.renderTooltipLine(
            guiGraphics,
            leftX,
            topY,
            font,
            Component.translatable(TranslationHelper.INSTANCE.translItemTooltip("storage") + ".inventory").withStyle(ChatFormatting.YELLOW)
         );
         this.renderContents(minecraft, leftX, topY, guiGraphics, font);
      }
   }

   private int renderTooltipLine(GuiGraphics guiGraphics, int leftX, int topY, Font font, Component tooltip) {
      PoseStack poseStack = guiGraphics.pose();
      poseStack.pushPose();
      poseStack.translate(0.0, 0.0, 200.0);
      guiGraphics.drawString(font, tooltip, leftX, topY, 16777215);
      poseStack.translate(0.0, 0.0, -200.0);
      poseStack.popPose();
      return topY + 10;
   }

   private int renderUpgrades(GuiGraphics guiGraphics, int leftX, int topY) {
      int x = leftX;

      for (IUpgradeWrapper upgradeWrapper : upgrades) {
         if (upgradeWrapper.canBeDisabled()) {
            RenderSystem.disableDepthTest();
            GuiHelper.blit(guiGraphics, x, topY + 3, upgradeWrapper.isEnabled() ? UPGRADE_ON : UPGRADE_OFF);
            x += 4;
         }

         guiGraphics.renderItem(upgradeWrapper.getUpgradeStack(), x, topY);
         x += 18;
      }

      return topY + 20;
   }

   private void renderContents(Minecraft minecraft, int leftX, int topY, GuiGraphics guiGraphics, Font font) {
      int x = leftX;

      for (int i = 0; i < sortedContents.size(); i++) {
         int y = topY + i / 9 * 20;
         if (i % 9 == 0) {
            x = leftX;
         }

         ItemStack stack = sortedContents.get(i);
         int stackWidth = Math.max(this.getStackCountWidth(minecraft.font, stack), 18);
         int xOffset = stackWidth - 18;
         guiGraphics.renderItem(stack, x + xOffset, y);
         guiGraphics.renderItemDecorations(font, stack, x + xOffset, y, CountAbbreviator.abbreviate(stack.getCount()));
         x += stackWidth;
      }
   }
}
