package fzzyhmstrs.emi_loot.util;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.widget.Bounds;
import dev.emi.emi.api.widget.SlotWidget;
import dev.emi.emi.api.widget.Widget;
import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.EMILootClientAgnos;
import fzzyhmstrs.emi_loot.client.ClientBuiltPool;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Tuple;

public abstract class IconGroupEmiWidget extends Widget {
   private final List<IconEmiWidget> icons;
   private final List<SlotWidget> items;
   private final int x;
   private final int y;
   private final int iconsWidth;
   private final int itemsWidth;
   private final int width;
   private final Bounds bounds;

   public IconGroupEmiWidget(int x, int y, ClientBuiltPool pool, EmiRecipe recipe) {
      this.x = x;
      this.y = y;
      List<IconEmiWidget> list = new LinkedList<>();

      for (int i = 0; i < pool.conditions().size(); i++) {
         Tuple<Integer, Component> pair = pool.conditions().get(i);
         int xOffset = i / 2 * 11;
         int yOffset = i % 2 * 11;
         list.add(EMILootClientAgnos.createIconEmiWidget(x + xOffset, y + yOffset, (Integer)pair.getA(), (Component)pair.getB()));
      }

      this.icons = list;
      this.iconsWidth = 12 + (this.icons.size() - 1) / 2 * 11;
      this.itemsWidth = 2 + pool.stacks().size() * 20;
      this.width = this.iconsWidth + this.itemsWidth;
      this.bounds = new Bounds(x, y, this.width, 23);
      List<SlotWidget> list2 = new LinkedList<>();
      int itemXOffset = this.iconsWidth + 2;

      for (ConditionalStack entry : pool.stacks()) {
         String rounded = FloatTrimmer.trimFloatString(Math.max(entry.weight() / 100.0F, 0.01F), (Integer)EMILoot.config.chanceDecimalPlaces.get());
         SlotWidget widget = new SlotWidget(entry.getIngredient(), itemXOffset + x, y + 3)
            .appendTooltip(LText.translatable("emi_loot.rolls", rounded))
            .recipeContext(recipe);
         itemXOffset += 20;
         list2.add(widget);
      }

      this.items = list2;
   }

   public int getIconsWidth() {
      return this.iconsWidth;
   }

   public int getWidth() {
      return this.width;
   }

   public List<ClientTooltipComponent> getTooltip(int mouseX, int mouseY) {
      for (IconEmiWidget icon : this.icons) {
         if (icon.getBounds().contains(mouseX, mouseY)) {
            return icon.getTooltip(mouseX, mouseY);
         }
      }

      for (SlotWidget slot : this.items) {
         if (slot.getBounds().contains(mouseX, mouseY)) {
            return slot.getTooltip(mouseX, mouseY);
         }
      }

      return List.of();
   }

   public boolean mouseClicked(int mouseX, int mouseY, int button) {
      for (SlotWidget slot : this.items) {
         if (slot.getBounds().contains(mouseX, mouseY) && slot.mouseClicked(mouseX, mouseY, button)) {
            return true;
         }
      }

      return super.mouseClicked(mouseX, mouseY, button);
   }

   public Bounds getBounds() {
      return this.bounds;
   }

   public void renderInternal(GuiGraphics draw, int mouseX, int mouseY, float delta) {
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      int widthRemaining = this.itemsWidth;
      int xNew = this.x + this.iconsWidth;

      do {
         int newWidth = Math.min(64, widthRemaining);
         draw.blit(IconEmiWidget.FRAME_ID, xNew, this.y, newWidth, 1, 0.0F, 0.0F, newWidth, 1, 64, 16);
         xNew += newWidth;
         widthRemaining -= newWidth;
      } while (widthRemaining > 0);

      draw.fill(RenderType.gui(), this.x, this.x + this.width, this.y, this.y + 1, 5592405);

      for (IconEmiWidget icon : this.icons) {
         icon.render(draw, mouseX, mouseY, delta);
      }

      for (SlotWidget slot : this.items) {
         this.renderSlot(slot, draw, mouseX, mouseY, delta);
      }
   }

   public void renderSlot(SlotWidget slot, GuiGraphics draw, int mouseX, int mouseY, float delta) {
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      draw.setColor(1.0F, 1.0F, 1.0F, 1.0F);
      slot.drawBackground(draw, mouseX, mouseY, delta);
      slot.drawStack(draw, mouseX, mouseY, delta);
      RenderSystem.disableDepthTest();
      slot.drawOverlay(draw, mouseX, mouseY, delta);
   }
}
