package net.p3pp3rf1y.sophisticatedcore.upgrades.pump;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.WidgetBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;

public class FluidFilterControl extends WidgetBase {
   private final FluidFilterContainer container;
   private final List<Position> slotTopLeftPositions = new ArrayList<>();

   protected FluidFilterControl(Position position, FluidFilterContainer container) {
      super(position, new Dimension(container.getNumberOfFluidFilters() * 18, 18));
      this.container = container;

      for (int i = 0; i < container.getNumberOfFluidFilters(); i++) {
         this.slotTopLeftPositions.add(new Position(this.x + i * 18 + 1, this.y + 1));
      }
   }

   @Override
   protected void renderBg(GuiGraphics guiGraphics, Minecraft minecraft, int mouseX, int mouseY) {
      GuiHelper.renderSlotsBackground(guiGraphics, this.x, this.y, this.container.getNumberOfFluidFilters(), 1);
   }

   @Override
   protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      for (int i = 0; i < this.container.getNumberOfFluidFilters(); i++) {
         FluidStack fluid = this.container.getFluid(i);
         if (!fluid.isEmpty()) {
            IClientFluidTypeExtensions renderProperties = IClientFluidTypeExtensions.of(fluid.getFluid());
            ResourceLocation texture = renderProperties.getStillTexture(fluid);
            TextureAtlasSprite still = (TextureAtlasSprite)this.minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(texture);
            GuiHelper.renderTiledFluidTextureAtlas(guiGraphics, still, renderProperties.getTintColor(fluid), this.x + i * 18 + 1, this.y + 1, 16);
         }
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (!this.isMouseOver(mouseX, mouseY)) {
         return false;
      }

      this.getSlotClicked(mouseX, mouseY).ifPresent(this.container::slotClick);
      return true;
   }

   @Override
   public void renderTooltip(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY) {
      this.getSlotClicked(mouseX, mouseY).ifPresent(slot -> {
         FluidStack fluid = this.container.getFluid(slot);
         if (!fluid.isEmpty()) {
            GuiHelper.renderTooltip(screen, guiGraphics, List.of(fluid.getHoverName()), mouseX, mouseY);
         }
      });
   }

   public List<Position> getSlotTopLeftPositions() {
      return this.slotTopLeftPositions;
   }

   private Optional<Integer> getSlotClicked(double mouseX, double mouseY) {
      if (!(mouseY < this.y + 1) && !(mouseY >= this.y + 17)) {
         int index = (int)((mouseX - this.x) / 18.0);
         return index >= 0 && index < this.container.getNumberOfFluidFilters() ? Optional.of(index) : Optional.empty();
      } else {
         return Optional.empty();
      }
   }

   @Override
   public void updateNarration(NarrationElementOutput narrationElementOutput) {
   }

   public void setFluid(int index, FluidStack fluid) {
      this.container.setFluid(index, fluid);
   }
}
