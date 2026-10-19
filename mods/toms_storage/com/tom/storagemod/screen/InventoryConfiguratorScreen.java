package com.tom.storagemod.screen;

import com.tom.storagemod.item.IItemFilter;
import com.tom.storagemod.menu.InventoryConfiguratorMenu;
import com.tom.storagemod.menu.slot.ItemFilterSlot;
import com.tom.storagemod.screen.widget.EnumCycleButton;
import com.tom.storagemod.screen.widget.IconButton;
import com.tom.storagemod.screen.widget.ToggleButton;
import com.tom.storagemod.util.Priority;
import java.util.Locale;
import java.util.function.Consumer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class InventoryConfiguratorScreen extends PlatformContainerScreen<InventoryConfiguratorMenu> {
   private static final ResourceLocation GUI_TEXTURES = ResourceLocation.tryBuild("toms_storage", "textures/gui/inventory_configurator.png");
   protected EnumCycleButton<Priority> buttonPriority;
   protected EnumCycleButton<Direction> buttonSide;
   protected IconButton buttonAddConnected;
   protected IconButton buttonRemoveAll;
   protected IconButton buttonRemoveFilter;
   protected ToggleButton buttonSkip;
   protected ToggleButton buttonKeepLast;

   public InventoryConfiguratorScreen(InventoryConfiguratorMenu menu, Inventory p_97742_, Component p_97743_) {
      super(menu, p_97742_, p_97743_);
   }

   protected void init() {
      super.init();
      this.buttonPriority = (EnumCycleButton<Priority>)this.addRenderableWidget(
         new EnumCycleButton<Priority>(
            this.leftPos - 18, this.topPos + 5, Component.translatable("narrator.toms_storage.priority"), "priority", Priority.VALUES, n -> {
               this.buttonPriority.setState(n);
               this.click(n.ordinal() << 3);
            }
         )
      );
      this.buttonAddConnected = (IconButton)this.addRenderableWidget(
         new IconButton(
            this.leftPos - 18,
            this.topPos + 5 + 18,
            Component.translatable("narrator.toms_storage.add"),
            ResourceLocation.tryBuild("toms_storage", "icons/add"),
            b -> this.click(1)
         )
      );
      this.buttonAddConnected.setTooltip(Tooltip.create(Component.translatable("tooltip.toms_storage.inv_config.add_connected")));
      this.buttonRemoveAll = (IconButton)this.addRenderableWidget(
         new IconButton(
            this.leftPos - 18,
            this.topPos + 5 + 36,
            Component.translatable("narrator.toms_storage.removeAll"),
            ResourceLocation.tryBuild("toms_storage", "icons/deny"),
            b -> this.click(2)
         )
      );
      this.buttonRemoveAll.setTooltip(Tooltip.create(Component.translatable("tooltip.toms_storage.inv_config.remove_all_connected")));
      this.buttonSide = (EnumCycleButton<Direction>)this.addRenderableWidget(
         new EnumCycleButton(this.leftPos - 18, this.topPos + 5 + 54, Component.translatable("narrator.toms_storage.side"), "side", Direction.values(), n -> {
            this.buttonSide.setState(n);
            this.click(n.ordinal() << 3 | 3);
         })
      );
      this.buttonSkip = (ToggleButton)this.addRenderableWidget(
         ToggleButton.builder(this.leftPos - 18, this.topPos + 5 + 72)
            .name(Component.translatable("narrator.toms_storage.skip"))
            .iconOff(ResourceLocation.tryBuild("toms_storage", "icons/include_inv"))
            .iconOn(ResourceLocation.tryBuild("toms_storage", "icons/skip_inv"))
            .build(s -> this.click((s ? 1 : 0) << 3 | 4))
      );
      this.buttonSkip
         .setTooltip(
            Tooltip.create(Component.translatable("tooltip.toms_storage.inv_config.include")),
            Tooltip.create(Component.translatable("tooltip.toms_storage.inv_config.skip"))
         );
      this.buttonKeepLast = (ToggleButton)this.addRenderableWidget(
         ToggleButton.builder(this.leftPos - 18, this.topPos + 5 + 90)
            .name(Component.translatable("narrator.toms_storage.skip"))
            .iconOff(ResourceLocation.tryBuild("toms_storage", "icons/keep_last_off"))
            .iconOn(ResourceLocation.tryBuild("toms_storage", "icons/keep_last_1"))
            .build(s -> this.click((s ? 1 : 0) << 3 | 5))
      );
      this.buttonKeepLast
         .setTooltip(
            Tooltip.create(Component.translatable("tooltip.toms_storage.keepLast_off")),
            Tooltip.create(Component.translatable("tooltip.toms_storage.keepLast_on"))
         );
      this.buttonPriority.tooltipFactory = s -> Tooltip.create(Component.translatable("tooltip.toms_storage.priority_" + s.name().toLowerCase(Locale.ROOT)));
      this.buttonSide.tooltipFactory = s -> Tooltip.create(Component.translatable("tooltip.toms_storage.side_" + s.name().toLowerCase(Locale.ROOT)));
      this.buttonRemoveFilter = (IconButton)this.addRenderableWidget(
         new IconButton(
            this.leftPos + this.imageWidth - 22,
            this.topPos - 22,
            Component.translatable("narrator.toms_storage.removeFilter"),
            ResourceLocation.tryBuild("toms_storage", "icons/deny"),
            b -> this.click(7)
         )
      );
      this.buttonRemoveFilter.setTooltip(Tooltip.create(Component.translatable("tooltip.toms_storage.inv_config.remove_filter")));
   }

   public void render(GuiGraphics matrixStack, int mouseX, int mouseY, float partialTicks) {
      this.buttonPriority.setState(((InventoryConfiguratorMenu)this.menu).priority);
      this.buttonSide.setState(((InventoryConfiguratorMenu)this.menu).side);
      this.buttonSkip.setState(((InventoryConfiguratorMenu)this.menu).skip);
      this.buttonKeepLast.setState(((InventoryConfiguratorMenu)this.menu).keepLast);
      super.render(matrixStack, mouseX, mouseY, partialTicks);
      this.renderTooltip(matrixStack, mouseX, mouseY);
   }

   protected void renderBg(GuiGraphics matrixStack, float partialTicks, int x, int y) {
      int i = (this.width - this.imageWidth) / 2;
      int j = (this.height - this.imageHeight) / 2;
      matrixStack.blit(GUI_TEXTURES, i, j, 0, 0, this.imageWidth, this.imageHeight);
   }

   private void click(int id) {
      this.minecraft.gameMode.handleInventoryButtonClick(((InventoryConfiguratorMenu)this.menu).containerId, id);
   }

   public boolean mouseClicked(double x, double y, int btn) {
      if (btn == 1 && this.hoveredSlot instanceof ItemFilterSlot && this.hoveredSlot.getItem().getItem() instanceof IItemFilter) {
         this.click(this.hoveredSlot.index << 3 | 6);
         return true;
      } else {
         return super.mouseClicked(x, y, btn);
      }
   }

   @Override
   public void getExclusionAreas(Consumer<IScreen.Box> consumer) {
      consumer.accept(new IScreen.Box(this.leftPos - 20, this.topPos, 25, 110));
   }
}
