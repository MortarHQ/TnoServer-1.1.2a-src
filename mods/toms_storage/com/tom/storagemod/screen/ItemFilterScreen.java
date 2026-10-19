package com.tom.storagemod.screen;

import com.tom.storagemod.menu.ItemFilterMenu;
import com.tom.storagemod.screen.widget.ToggleButton;
import java.util.function.Consumer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ItemFilterScreen extends AbstractFilteredScreen<ItemFilterMenu> {
   private static final ResourceLocation DISPENSER_GUI_TEXTURES = ResourceLocation.parse("textures/gui/container/dispenser.png");
   private ToggleButton buttonAllowList;
   private ToggleButton buttonMatchNBT;

   public ItemFilterScreen(ItemFilterMenu container, Inventory playerInventory, Component textComponent) {
      super(container, playerInventory, textComponent);
   }

   protected void init() {
      super.init();
      this.buttonAllowList = (ToggleButton)this.addRenderableWidget(
         ToggleButton.builder(this.leftPos - 18, this.topPos + 5)
            .iconOff(ResourceLocation.tryBuild("toms_storage", "icons/deny"))
            .iconOn(ResourceLocation.tryBuild("toms_storage", "icons/allow"))
            .build(s -> this.click(1, s))
      );
      this.buttonAllowList
         .setTooltip(
            Tooltip.create(Component.translatable("tooltip.toms_storage.denyList")), Tooltip.create(Component.translatable("tooltip.toms_storage.allowList"))
         );
      this.buttonMatchNBT = (ToggleButton)this.addRenderableWidget(
         ToggleButton.builder(this.leftPos - 18, this.topPos + 5 + 18)
            .iconOff(ResourceLocation.tryBuild("toms_storage", "icons/match_tag_off"))
            .iconOn(ResourceLocation.tryBuild("toms_storage", "icons/match_tag_on"))
            .build(s -> this.click(0, s))
      );
      this.buttonMatchNBT
         .setTooltip(
            Tooltip.create(Component.translatable("tooltip.toms_storage.matchNBT_off")),
            Tooltip.create(Component.translatable("tooltip.toms_storage.matchNBT_on"))
         );
   }

   private void click(int id, boolean val) {
      this.minecraft.gameMode.handleInventoryButtonClick(((ItemFilterMenu)this.menu).containerId, id << 1 | (val ? 1 : 0));
   }

   public void render(GuiGraphics matrixStack, int mouseX, int mouseY, float partialTicks) {
      this.buttonMatchNBT.setState(((ItemFilterMenu)this.menu).matchNBT);
      this.buttonAllowList.setState(((ItemFilterMenu)this.menu).allowList);
      super.render(matrixStack, mouseX, mouseY, partialTicks);
      this.renderTooltip(matrixStack, mouseX, mouseY);
   }

   protected void renderBg(GuiGraphics matrixStack, float partialTicks, int x, int y) {
      int i = (this.width - this.imageWidth) / 2;
      int j = (this.height - this.imageHeight) / 2;
      matrixStack.blit(DISPENSER_GUI_TEXTURES, i, j, 0, 0, this.imageWidth, this.imageHeight);
   }

   @Override
   public void getExclusionAreas(Consumer<IScreen.Box> consumer) {
      consumer.accept(new IScreen.Box(this.leftPos - 20, this.topPos, 25, 40));
   }
}
