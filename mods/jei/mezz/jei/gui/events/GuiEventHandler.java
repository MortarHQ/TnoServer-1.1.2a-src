package mezz.jei.gui.events;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Set;
import java.util.stream.Collectors;
import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.runtime.IScreenHelper;
import mezz.jei.common.config.DebugConfig;
import mezz.jei.common.gui.JeiTooltip;
import mezz.jei.common.platform.IPlatformScreenHelper;
import mezz.jei.common.platform.Services;
import mezz.jei.common.util.ImmutableRect2i;
import mezz.jei.common.util.RectDebugger;
import mezz.jei.gui.overlay.IngredientListOverlay;
import mezz.jei.gui.overlay.bookmarks.BookmarkOverlay;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;

public class GuiEventHandler {
   private final IngredientListOverlay ingredientListOverlay;
   private final IScreenHelper screenHelper;
   private final BookmarkOverlay bookmarkOverlay;

   public GuiEventHandler(IScreenHelper screenHelper, BookmarkOverlay bookmarkOverlay, IngredientListOverlay ingredientListOverlay) {
      this.screenHelper = screenHelper;
      this.bookmarkOverlay = bookmarkOverlay;
      this.ingredientListOverlay = ingredientListOverlay;
   }

   public void onGuiInit(Screen screen) {
      Set<ImmutableRect2i> guiExclusionAreas = this.screenHelper.getGuiExclusionAreas(screen).map(ImmutableRect2i::new).collect(Collectors.toUnmodifiableSet());
      this.ingredientListOverlay.getScreenPropertiesUpdater().updateScreen(screen).updateExclusionAreas(guiExclusionAreas).update();
      this.bookmarkOverlay.getScreenPropertiesUpdater().updateScreen(screen).updateExclusionAreas(guiExclusionAreas).update();
   }

   public void onGuiOpen(Screen screen) {
      this.ingredientListOverlay.getScreenPropertiesUpdater().updateScreen(screen).update();
      this.bookmarkOverlay.getScreenPropertiesUpdater().updateScreen(screen).update();
   }

   public void onDrawForeground(AbstractContainerScreen<?> screen, GuiGraphics guiGraphics, int mouseX, int mouseY) {
      PoseStack poseStack = guiGraphics.pose();
      poseStack.pushPose();
      IPlatformScreenHelper screenHelper = Services.PLATFORM.getScreenHelper();
      poseStack.translate(-screenHelper.getGuiLeft(screen), -screenHelper.getGuiTop(screen), 0.0F);
      this.bookmarkOverlay.drawOnForeground(guiGraphics, mouseX, mouseY);
      this.ingredientListOverlay.drawOnForeground(guiGraphics, mouseX, mouseY);
      poseStack.popPose();
   }

   public void onDrawScreenPost(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY) {
      Minecraft minecraft = Minecraft.getInstance();
      Set<ImmutableRect2i> guiExclusionAreas = this.screenHelper.getGuiExclusionAreas(screen).map(ImmutableRect2i::new).collect(Collectors.toUnmodifiableSet());
      this.ingredientListOverlay.getScreenPropertiesUpdater().updateScreen(screen).updateExclusionAreas(guiExclusionAreas).update();
      this.bookmarkOverlay.getScreenPropertiesUpdater().updateScreen(screen).updateExclusionAreas(guiExclusionAreas).update();
      DeltaTracker deltaTracker = minecraft.getTimer();
      float partialTicks = deltaTracker.getGameTimeDeltaPartialTick(false);
      this.ingredientListOverlay.drawScreen(minecraft, guiGraphics, mouseX, mouseY, partialTicks);
      this.bookmarkOverlay.drawScreen(minecraft, guiGraphics, mouseX, mouseY, partialTicks);
      if (screen instanceof AbstractContainerScreen<?> guiContainer) {
         IPlatformScreenHelper screenHelper = Services.PLATFORM.getScreenHelper();
         int guiLeft = screenHelper.getGuiLeft(guiContainer);
         int guiTop = screenHelper.getGuiTop(guiContainer);
         this.screenHelper
            .getGuiClickableArea(guiContainer, mouseX - guiLeft, mouseY - guiTop)
            .filter(IGuiClickableArea::isTooltipEnabled)
            .findFirst()
            .ifPresent(area -> {
               JeiTooltip tooltip = new JeiTooltip();
               area.getTooltip(tooltip);
               if (tooltip.isEmpty()) {
                  tooltip.add(Component.translatable("jei.tooltip.show.recipes"));
               }

               tooltip.draw(guiGraphics, mouseX, mouseY);
            });
      }

      this.ingredientListOverlay.drawTooltips(minecraft, guiGraphics, mouseX, mouseY);
      this.bookmarkOverlay.drawTooltips(minecraft, guiGraphics, mouseX, mouseY);
      if (DebugConfig.isDebugGuisEnabled()) {
         this.drawDebugInfoForScreen(screen, guiGraphics);
      }
   }

   public boolean renderCompactPotionIndicators() {
      return this.ingredientListOverlay.isListDisplayed();
   }

   private void drawDebugInfoForScreen(Screen screen, GuiGraphics guiGraphics) {
      RectDebugger.INSTANCE.draw(guiGraphics);
      this.screenHelper
         .getGuiProperties(screen)
         .ifPresent(
            guiProperties -> {
               Set<Rect2i> guiExclusionAreas = this.screenHelper.getGuiExclusionAreas(screen).collect(Collectors.toUnmodifiableSet());
               RenderSystem.disableDepthTest();

               for (Rect2i area : guiExclusionAreas) {
                  guiGraphics.fill(RenderType.gui(), area.getX(), area.getY(), area.getX() + area.getWidth(), area.getY() + area.getHeight(), 1157562368);
               }

               guiGraphics.fill(
                  RenderType.gui(),
                  guiProperties.guiLeft(),
                  guiProperties.guiTop(),
                  guiProperties.guiLeft() + guiProperties.guiXSize(),
                  guiProperties.guiTop() + guiProperties.guiYSize(),
                  583846912
               );
               RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            }
         );
   }
}
