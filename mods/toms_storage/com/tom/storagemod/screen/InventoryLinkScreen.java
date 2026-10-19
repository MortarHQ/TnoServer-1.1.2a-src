package com.tom.storagemod.screen;

import com.tom.storagemod.menu.InventoryLinkMenu;
import com.tom.storagemod.network.NetworkHandler;
import com.tom.storagemod.screen.widget.IconButton;
import com.tom.storagemod.screen.widget.ListWidget;
import com.tom.storagemod.screen.widget.ToggleButton;
import com.tom.storagemod.util.IDataReceiver;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class InventoryLinkScreen extends PlatformContainerScreen<InventoryLinkMenu> implements IDataReceiver {
   private static final ResourceLocation gui = ResourceLocation.tryBuild("toms_storage", "textures/gui/inventory_link.png");
   private static final ResourceLocation privateChannel = ResourceLocation.tryBuild("toms_storage", "icons/lock_on");
   private static final ResourceLocation publicChannel = ResourceLocation.tryBuild("toms_storage", "icons/lock_off");
   private EditBox textF;
   private Map<UUID, InventoryLinkMenu.LinkChannel> connections = new HashMap<>();
   private IconButton createBtn;
   private IconButton deleteBtn;
   private ToggleButton publicBtn;
   private List<InventoryLinkMenu.LinkChannel> sortedList = new ArrayList<>();
   private InventoryLinkScreen.ListHandler channelsList;
   protected float currentScroll;
   protected boolean isScrolling;
   protected boolean wasClicking;

   public InventoryLinkScreen(InventoryLinkMenu p_97741_, Inventory p_97742_, Component p_97743_) {
      super(p_97741_, p_97742_, p_97743_);
   }

   @Override
   public void receive(CompoundTag tag) {
      ListTag list = tag.getList("list", 10);
      InventoryLinkMenu.LinkChannel.loadAll(list, this.connections);
      if (tag.contains("selected")) {
         UUID sel = tag.getUUID("selected");
         InventoryLinkMenu.LinkChannel selC = this.connections.get(sel);
         if (selC != null) {
            this.channelsList.setSelected(selC);
            this.textF.setValue(selC.displayName);
         } else {
            this.channelsList.setSelected(null);
         }
      } else {
         this.channelsList.setSelected(null);
      }

      Comparator<InventoryLinkMenu.LinkChannel> cmp = Comparator.comparing(e -> e.publicChannel);
      cmp = cmp.thenComparing(e -> e.displayName);
      this.sortedList = this.connections.values().stream().sorted(cmp).collect(Collectors.toList());
      this.update();
   }

   protected void renderBg(GuiGraphics matrixStack, float partialTicks, int x, int y) {
      int i = (this.width - this.imageWidth) / 2;
      int j = (this.height - this.imageHeight) / 2;
      matrixStack.blit(gui, i, j, 0, 0, this.imageWidth, this.imageHeight);
   }

   protected void renderLabels(GuiGraphics st, int p_97809_, int p_97810_) {
      st.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
      st.drawString(
         this.font,
         I18n.get("label.toms_storage.inventory_connector.beacon_level", new Object[]{((InventoryLinkMenu)this.menu).beaconLvl}),
         this.titleLabelX,
         this.titleLabelY + 10,
         4210752,
         false
      );
   }

   protected void init() {
      this.clearWidgets();
      super.init();
      this.createBtn = (IconButton)this.addRenderableWidget(
         new IconButton(this.leftPos + 121, this.topPos + 24, Component.translatable(""), ResourceLocation.tryBuild("toms_storage", "icons/add"), b -> {
            String name = this.textF.getValue().trim();
            if (!name.isEmpty()) {
               this.sendEdit(null, new InventoryLinkMenu.LinkChannel(this.publicBtn.getState(), name));
            }
         })
      );
      this.deleteBtn = (IconButton)this.addRenderableWidget(
         new IconButton(
            this.leftPos + 138,
            this.topPos + 24,
            Component.translatable(""),
            ResourceLocation.tryBuild("toms_storage", "icons/deny"),
            b -> this.sendEdit(this.channelsList.getSelected(), null)
         )
      );
      this.publicBtn = (ToggleButton)this.addRenderableWidget(
         ToggleButton.builder(this.leftPos + 155, this.topPos + 24).iconOff(privateChannel).iconOn(publicChannel).build(s -> {
            InventoryLinkMenu.LinkChannel sel = this.channelsList.getSelected();
            if (sel != null && sel.owner.equals(this.minecraft.player.getUUID())) {
               sel.publicChannel = s;
               this.sendEdit(sel, sel);
            }
         })
      );
      this.publicBtn
         .setTooltip(
            Tooltip.create(Component.translatable("tooltip.toms_storage.inv_link.private")),
            Tooltip.create(Component.translatable("tooltip.toms_storage.inv_link.public"))
         );
      this.channelsList = new InventoryLinkScreen.ListHandler(this.leftPos + 12, this.topPos + 42);
      this.channelsList.setList(() -> this.sortedList);
      this.addRenderableWidget(this.channelsList);
      this.textF = new EditBox(this.font, this.leftPos + 13, this.topPos + 28, 105, 9, Component.translatable("narrator.toms_storage.inventory_link_channel"));
      this.textF.setMaxLength(50);
      this.textF.setBordered(false);
      this.textF.setVisible(true);
      this.textF.setTextColor(16777215);
      this.textF.setValue("");
      this.textF.setResponder(t -> {
         this.channelsList.setSelected(null);

         for (Entry<UUID, InventoryLinkMenu.LinkChannel> e : this.connections.entrySet()) {
            if (e.getValue().displayName.equals(t)) {
               this.channelsList.setSelected(e.getValue());
               break;
            }
         }

         this.update();
      });
      this.addRenderableWidget(this.textF);
      this.update();
   }

   private void sendEdit(InventoryLinkMenu.LinkChannel id, InventoryLinkMenu.LinkChannel ch) {
      CompoundTag tag = new CompoundTag();
      if (id != null) {
         tag.putUUID("id", id.id);
      }

      if (ch != null) {
         ch.saveToServer(tag);
      }

      NetworkHandler.sendDataToServer(tag);
   }

   private void sendSelect(UUID id) {
      CompoundTag tag = new CompoundTag();
      tag.putUUID("id", id);
      tag.putBoolean("select", true);
      NetworkHandler.sendDataToServer(tag);
   }

   private void update() {
      InventoryLinkMenu.LinkChannel sel = this.channelsList.getSelected();
      if (sel != null) {
         this.deleteBtn.active = true;
         this.publicBtn.setState(sel.publicChannel);
         boolean owner = sel.owner.equals(this.minecraft.player.getUUID());
         this.publicBtn.active = owner;
         this.createBtn.active = false;
      } else {
         this.deleteBtn.active = false;
         this.publicBtn.setState(false);
         this.createBtn.active = !this.textF.getValue().trim().isEmpty();
      }
   }

   public void render(GuiGraphics st, int mouseX, int mouseY, float partialTicks) {
      this.channelsList.preRender(mouseX, mouseY);
      super.render(st, mouseX, mouseY, partialTicks);
      this.renderTooltip(st, mouseX, mouseY);
      this.channelsList.tooltip(st, mouseX, mouseY);
   }

   public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
      if (p_keyPressed_1_ == 256) {
         this.onClose();
         return true;
      } else if (p_keyPressed_1_ == 258) {
         return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
      } else {
         return !this.textF.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_) && !this.textF.canConsumeInput()
            ? super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_)
            : true;
      }
   }

   public boolean charTyped(char p_charTyped_1_, int p_charTyped_2_) {
      return this.textF.charTyped(p_charTyped_1_, p_charTyped_2_) ? true : super.charTyped(p_charTyped_1_, p_charTyped_2_);
   }

   @Override
   public void getExclusionAreas(Consumer<IScreen.Box> consumer) {
   }

   public class ListHandler extends ListWidget<InventoryLinkMenu.LinkChannel> {
      public ListHandler(int x, int y) {
         super(x, y, 114, 100, 16, Component.empty());
      }

      @Override
      protected Font getFont() {
         return InventoryLinkScreen.this.font;
      }

      @Override
      protected void addButton(AbstractWidget btn) {
         InventoryLinkScreen.this.addRenderableWidget(btn);
      }

      protected Component toComponent(InventoryLinkMenu.LinkChannel data) {
         return Component.literal(data.displayName);
      }

      protected void renderTooltip(GuiGraphics graphics, InventoryLinkMenu.LinkChannel data, int mouseX, int mouseY) {
         List<Component> tt = new ArrayList<>();
         if (!data.ownerName.isEmpty()) {
            boolean owner = data.owner.equals(InventoryLinkScreen.this.minecraft.player.getUUID());
            tt.add(Component.translatable("tooltip.toms_storage.inventory_connector.channel.owner" + (owner ? ".self" : ""), new Object[]{data.ownerName}));
         } else {
            tt.add(Component.translatable("tooltip.toms_storage.inventory_connector.channel.owner.unknown"));
         }

         tt.add(Component.translatable("tooltip.toms_storage.inv_link." + (data.publicChannel ? "public" : "private")));
         graphics.renderTooltip(InventoryLinkScreen.this.font, tt, Optional.empty(), mouseX, mouseY);
      }

      protected void renderEntry(GuiGraphics st, int x, int y, int width, int height, InventoryLinkMenu.LinkChannel id, int mouseX, int mouseY, float pt) {
         st.blitSprite(id.publicChannel ? InventoryLinkScreen.publicChannel : InventoryLinkScreen.privateChannel, x + width - 16, y, 16, 16);
      }

      protected void selectionChanged(InventoryLinkMenu.LinkChannel to) {
         InventoryLinkScreen.this.sendSelect(to.id);
         InventoryLinkScreen.this.update();
      }
   }
}
