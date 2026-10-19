package com.tom.storagemod.screen;

import com.tom.storagemod.menu.TagItemFilterMenu;
import com.tom.storagemod.network.NetworkHandler;
import com.tom.storagemod.screen.widget.IconButton;
import com.tom.storagemod.screen.widget.ListWidget;
import com.tom.storagemod.screen.widget.ToggleButton;
import com.tom.storagemod.util.IDataReceiver;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class TagItemFilterScreen extends AbstractFilteredScreen<TagItemFilterMenu> implements IDataReceiver {
   private static final ResourceLocation GUI_TEXTURES = ResourceLocation.tryBuild("toms_storage", "textures/gui/tag_filter.png");
   private ToggleButton buttonAllowList;
   private IconButton buttonAdd;
   private IconButton buttonRemove;
   private List<String> itemTags = new ArrayList<>();
   private List<String> filterTags = new ArrayList<>();
   private TagItemFilterScreen.ListHandler itemTagList;
   private TagItemFilterScreen.ListHandler filterList;

   public TagItemFilterScreen(TagItemFilterMenu container, Inventory playerInventory, Component textComponent) {
      super(container, playerInventory, textComponent);
   }

   protected void init() {
      super.init();
      this.buttonAllowList = (ToggleButton)this.addRenderableWidget(
         ToggleButton.builder(this.leftPos - 18, this.topPos + 5)
            .iconOff(ResourceLocation.tryBuild("toms_storage", "icons/deny"))
            .iconOn(ResourceLocation.tryBuild("toms_storage", "icons/allow"))
            .build(s -> this.click(0, s))
      );
      this.buttonAllowList
         .setTooltip(
            Tooltip.create(Component.translatable("tooltip.toms_storage.denyList")), Tooltip.create(Component.translatable("tooltip.toms_storage.allowList"))
         );
      this.itemTagList = (TagItemFilterScreen.ListHandler)this.addRenderableWidget(new TagItemFilterScreen.ListHandler(this.leftPos + 28, this.topPos + 15));
      this.itemTagList.setList(() -> this.itemTags);
      this.filterList = (TagItemFilterScreen.ListHandler)this.addRenderableWidget(new TagItemFilterScreen.ListHandler(this.leftPos + 109, this.topPos + 15));
      this.filterList.setList(() -> this.filterTags);
      this.buttonAdd = (IconButton)this.addRenderableWidget(
         new IconButton(this.leftPos + 90, this.topPos + 14, Component.translatable(""), ResourceLocation.tryBuild("toms_storage", "icons/add"), b -> {
            String sel = this.itemTagList.getSelected();
            if (sel != null) {
               if (!this.filterTags.contains(sel)) {
                  this.filterTags.add(sel);
               }

               this.itemTagList.setSelected(null);
               this.sync();
            }
         })
      );
      this.buttonRemove = (IconButton)this.addRenderableWidget(
         new IconButton(this.leftPos + 90, this.topPos + 32, Component.translatable(""), ResourceLocation.tryBuild("toms_storage", "icons/deny"), b -> {
            String sel = this.filterList.getSelected();
            if (sel != null) {
               this.filterTags.remove(sel);
               this.filterList.setSelected(null);
               this.sync();
            }
         })
      );
   }

   private void sync() {
      ListTag list = new ListTag();
      this.filterTags.forEach(t -> list.add(StringTag.valueOf(t)));
      CompoundTag tag = new CompoundTag();
      tag.put("l", list);
      NetworkHandler.sendDataToServer(tag);
   }

   protected void containerTick() {
      ItemStack s = ((Slot)((TagItemFilterMenu)this.menu).slots.get(0)).getItem();
      List<String> tags = s.getTags().map(t -> t.location().toString()).toList();
      if (!this.itemTags.equals(tags)) {
         this.itemTags.clear();
         this.itemTags.addAll(tags);
         this.itemTagList.setSelected(null);
         this.itemTagList.setCurrentScroll(0.0F);
      }
   }

   private void click(int id, boolean val) {
      this.minecraft.gameMode.handleInventoryButtonClick(((TagItemFilterMenu)this.menu).containerId, id << 1 | (val ? 1 : 0));
   }

   public void render(GuiGraphics matrixStack, int mouseX, int mouseY, float partialTicks) {
      this.buttonAllowList.setState(((TagItemFilterMenu)this.menu).allowList);
      this.itemTagList.preRender(mouseX, mouseY);
      this.filterList.preRender(mouseX, mouseY);
      this.buttonAdd.active = this.itemTagList.getSelected() != null;
      this.buttonRemove.active = this.filterList.getSelected() != null;
      super.render(matrixStack, mouseX, mouseY, partialTicks);
      this.renderTooltip(matrixStack, mouseX, mouseY);
      this.itemTagList.tooltip(matrixStack, mouseX, mouseY);
      this.filterList.tooltip(matrixStack, mouseX, mouseY);
   }

   protected void renderBg(GuiGraphics matrixStack, float partialTicks, int x, int y) {
      int i = (this.width - this.imageWidth) / 2;
      int j = (this.height - this.imageHeight) / 2;
      matrixStack.blit(GUI_TEXTURES, i, j, 0, 0, this.imageWidth, this.imageHeight);
   }

   @Override
   public void receive(CompoundTag tag) {
      ListTag list = tag.getList("l", 8);
      this.filterTags.clear();

      for (int i = 0; i < list.size(); i++) {
         this.filterTags.add(list.getString(i));
      }

      this.filterList.setSelected(null);
      this.filterList.setCurrentScroll(0.0F);
   }

   @Override
   public void getExclusionAreas(Consumer<IScreen.Box> consumer) {
      consumer.accept(new IScreen.Box(this.leftPos - 20, this.topPos, 25, 25));
   }

   public class ListHandler extends ListWidget<String> {
      public ListHandler(int x, int y) {
         super(x, y, 61, 58, 14, Component.empty());
      }

      @Override
      protected Font getFont() {
         return TagItemFilterScreen.this.font;
      }

      @Override
      protected void addButton(AbstractWidget btn) {
         TagItemFilterScreen.this.addRenderableWidget(btn);
      }

      protected Component toComponent(String data) {
         return Component.literal(data);
      }

      protected void renderTooltip(GuiGraphics graphics, String data, int mouseX, int mouseY) {
         graphics.renderTooltip(TagItemFilterScreen.this.font, Component.literal(data), mouseX, mouseY);
      }
   }
}
