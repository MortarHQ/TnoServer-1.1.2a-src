package net.p3pp3rf1y.sophisticatedcore.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.NarratableEntry.NarrationPriority;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.Rect2i;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.CompositeWidgetBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import org.apache.commons.lang3.mutable.MutableInt;

public abstract class SettingsTabControl<C extends AbstractContainerScreen<?>, T extends SettingsTabBase<C>> extends CompositeWidgetBase<Tab> {
   private static final int VERTICAL_SPACE = 1;
   @Nullable
   private T openTab = (T)null;

   protected SettingsTabControl(Position position) {
      super(position, new Dimension(0, 0));
   }

   protected <U extends T> U addSettingsTab(Runnable onTabOpenContainerAction, Runnable onTabCloseContainerAction, U tab) {
      U settingsTab = (U)this.addChild(tab);
      settingsTab.setHandlers(
         () -> {
            if (this.openTab != null && this.differentTabIsOpen(settingsTab)) {
               this.openTab.close();
            }

            this.openTab = (T)settingsTab;
            onTabOpenContainerAction.run();
         },
         () -> {
            if (this.openTab != null) {
               this.openTab = null;
               onTabCloseContainerAction.run();
            }
         },
         () -> this.openTab == null || !this.differentTabIsOpen(settingsTab) || this.isNotCovered(this.openTab, settingsTab, true),
         () -> this.openTab == null || this.isNotCovered(this.openTab, settingsTab, false)
      );
      return settingsTab;
   }

   @Override
   protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      PoseStack pose = guiGraphics.pose();
      pose.pushPose();
      pose.translate(0.0F, 0.0F, -11.0F);
      this.children.forEach(child -> {
         if (child != this.openTab) {
            child.render(guiGraphics, mouseX, mouseY, partialTicks);
         }
      });
      pose.popPose();
      if (this.openTab != null) {
         this.openTab.render(guiGraphics, mouseX, mouseY, partialTicks);
      }

      RenderSystem.enableDepthTest();
   }

   private boolean isNotCovered(T open, Tab t, boolean checkFullyCovered) {
      return checkFullyCovered
         ? open.getBottomY() < t.getBottomY() || open.getTopY() > t.getTopY()
         : open.getBottomY() < t.getTopY() || open.getTopY() > t.getTopY();
   }

   private boolean differentTabIsOpen(Tab tab) {
      return this.openTab != tab;
   }

   public Optional<T> getOpenTab() {
      return Optional.ofNullable(this.openTab);
   }

   @Override
   protected void renderBg(GuiGraphics guiGraphics, Minecraft minecraft, int mouseX, int mouseY) {
   }

   @Override
   public void renderTooltip(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY) {
      this.children.forEach(tab -> tab.renderTooltip(screen, guiGraphics, mouseX, mouseY));
   }

   protected int getTopY() {
      return this.y + this.children.size() * 25;
   }

   @Override
   public int getHeight() {
      MutableInt maxY = new MutableInt(0);
      this.children.forEach(tab -> {
         int bottomY = tab.getBottomY();
         if (bottomY > maxY.getValue()) {
            maxY.setValue(bottomY);
         }
      });
      return maxY.getValue() - this.y;
   }

   @Override
   public int getWidth() {
      MutableInt maxWidth = new MutableInt(0);
      this.children.forEach(tab -> {
         int width = tab.getWidth();
         if (width > maxWidth.getValue()) {
            maxWidth.setValue(width);
         }
      });
      return maxWidth.getValue();
   }

   public List<Rect2i> getTabRectangles() {
      List<Rect2i> ret = new ArrayList<>();
      this.children.forEach(child -> child.getTabRectangle().ifPresent(ret::add));
      return ret;
   }

   @Override
   public NarrationPriority narrationPriority() {
      return NarrationPriority.NONE;
   }

   @Override
   public void updateNarration(NarrationElementOutput narrationElementOutput) {
   }
}
