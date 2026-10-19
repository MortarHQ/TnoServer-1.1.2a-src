package net.p3pp3rf1y.sophisticatedcore.upgrades.tank;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.UpgradeInventoryPartBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TextureBlitData;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TranslationHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.UV;
import net.p3pp3rf1y.sophisticatedcore.common.gui.StorageContainerMenuBase;
import net.p3pp3rf1y.sophisticatedcore.init.ModFluids;
import net.p3pp3rf1y.sophisticatedcore.util.XpHelper;

public class TankInventoryPart extends UpgradeInventoryPartBase<TankUpgradeContainer> {
   private static final TextureBlitData OVERLAY = new TextureBlitData(GuiHelper.GUI_CONTROLS, Dimension.SQUARE_256, new UV(47, 30), new Dimension(16, 18));
   private final Position pos;
   private final int height;
   private final StorageScreenBase<?> screen;

   public TankInventoryPart(int upgradeSlot, TankUpgradeContainer container, Position pos, int height, StorageScreenBase<?> screen) {
      super(upgradeSlot, container);
      this.pos = pos;
      this.height = height;
      this.screen = screen;
   }

   @Override
   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      GuiHelper.blit(guiGraphics, this.getTankLeft(), this.pos.y(), GuiHelper.BAR_BACKGROUND_TOP, 18, this.height < 36 ? this.height / 2 : 18);
      int yOffset = 18;

      for (int i = 0; i < (this.height - 36) / 18; i++) {
         GuiHelper.blit(guiGraphics, this.getTankLeft(), this.pos.y() + yOffset, GuiHelper.BAR_BACKGROUND_MIDDLE);
         yOffset += 18;
      }

      GuiHelper.blit(
         guiGraphics,
         this.getTankLeft(),
         this.pos.y() + (this.height < 36 ? this.height / 2 : yOffset),
         GuiHelper.BAR_BACKGROUND_BOTTOM,
         18,
         this.height < 36 ? this.height / 2 : 18
      );
      this.renderFluid(guiGraphics);
      guiGraphics.pose().pushPose();
      guiGraphics.pose().translate(0.0F, 0.0F, 100.0F);
      int var6 = 0;

      for (int i = 0; i < this.height / 18; i++) {
         GuiHelper.blit(guiGraphics, this.getTankLeft() + 1, this.pos.y() + var6, OVERLAY);
         var6 += 18;
      }

      guiGraphics.pose().popPose();
   }

   private int getTankLeft() {
      return this.pos.x() + 9;
   }

   @Override
   public boolean handleMouseReleased(double mouseX, double mouseY, int button) {
      if (!(mouseX < this.screen.getGuiLeft() + this.getTankLeft())
         && !(mouseX >= this.screen.getGuiLeft() + this.getTankLeft() + 18)
         && !(mouseY < this.screen.getGuiTop() + this.pos.y())
         && !(mouseY >= this.screen.getGuiTop() + this.pos.y() + this.height)) {
         ItemStack cursorStack = ((StorageContainerMenuBase)this.screen.getMenu()).getCarried();
         if (cursorStack.getCount() > 1) {
            return false;
         }

         PacketDistributor.sendToServer(new TankClickPayload(this.upgradeSlot), new CustomPacketPayload[0]);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void renderErrorOverlay(GuiGraphics guiGraphics) {
      this.screen.renderOverlay(guiGraphics, StorageScreenBase.ERROR_SLOT_COLOR, this.getTankLeft() + 1, this.pos.y() + 1, 16, this.height - 2);
   }

   @Override
   public void renderTooltip(StorageScreenBase<?> screen, GuiGraphics guiGraphics, int mouseX, int mouseY) {
      FluidStack contents = this.container.getContents();
      int capacity = this.container.getTankCapacity();
      if (contents.isEmpty()) {
         contents = FluidStack.EMPTY;
      }

      int screenX = screen.getGuiLeft() + this.pos.x() + 10;
      int screenY = screen.getGuiTop() + this.pos.y() + 1;
      if (mouseX >= screenX && mouseX < screenX + 16 && mouseY >= screenY && mouseY < screenY + this.height - 2) {
         List<Component> tooltip = new ArrayList<>();
         if (!contents.isEmpty()) {
            tooltip.add(contents.getHoverName());
         }

         tooltip.add(this.getContentsTooltip(contents, capacity));
         guiGraphics.renderTooltip(screen.font, tooltip, Optional.empty(), mouseX, mouseY);
      }
   }

   private MutableComponent getContentsTooltip(FluidStack contents, int capacity) {
      if (contents.getFluid().is(ModFluids.EXPERIENCE_TAG)) {
         double contentsLevels = XpHelper.getLevelsForExperience((int)XpHelper.liquidToExperience(contents.getAmount()));
         double tankCapacityLevels = XpHelper.getLevelsForExperience((int)XpHelper.liquidToExperience(capacity));
         return Component.translatable(
            TranslationHelper.INSTANCE.translUpgradeKey("tank.xp_contents_tooltip"),
            new Object[]{String.format("%.1f", contentsLevels), String.format("%.1f", tankCapacityLevels)}
         );
      } else {
         return Component.translatable(
            TranslationHelper.INSTANCE.translUpgradeKey("tank.contents_tooltip"),
            new Object[]{String.format("%,d", contents.getAmount()), String.format("%,d", capacity)}
         );
      }
   }

   private void renderFluid(GuiGraphics guiGraphics) {
      FluidStack contents = this.container.getContents();
      int capacity = this.container.getTankCapacity();
      if (!contents.isEmpty()) {
         Fluid fluid = contents.getFluid();
         int fill = contents.getAmount();
         int displayLevel = (int)((this.height - 2) * ((float)fill / capacity));
         IClientFluidTypeExtensions renderProperties = IClientFluidTypeExtensions.of(fluid);
         ResourceLocation texture = renderProperties.getStillTexture(contents);
         TextureAtlasSprite still = (TextureAtlasSprite)Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(texture);
         GuiHelper.renderTiledFluidTextureAtlas(
            guiGraphics, still, renderProperties.getTintColor(contents), this.pos.x() + 10, this.pos.y() + 1 + this.height - 2 - displayLevel, displayLevel
         );
      }
   }
}
