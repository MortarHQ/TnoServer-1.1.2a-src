package com.b1n_ry.yigd.client.gui;

import com.b1n_ry.yigd.networking.LightPlayerData;
import com.b1n_ry.yigd.networking.packets.GraveSelectionRequestC2SPacket;
import com.b1n_ry.yigd.networking.packets.PlayerSelectionS2CPacket;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public class PlayerSelectionScreen extends Screen {
   private static final ResourceLocation WINDOW_BG = ResourceLocation.fromNamespaceAndPath("yigd", "window_bg");
   private static final ResourceLocation SLOT = ResourceLocation.fromNamespaceAndPath("yigd", "slot");
   private static final ResourceLocation SCROLL_BAR = ResourceLocation.fromNamespaceAndPath("yigd", "scroll_bar");
   private static final ResourceLocation SCROLL_BAR_PRESSED = ResourceLocation.fromNamespaceAndPath("yigd", "scroll_bar_pressed");
   private static final ResourceLocation CLAIMED_GRAVE = ResourceLocation.fromNamespaceAndPath("yigd", "claimed_grave");
   private static final ResourceLocation DESTROYED_GRAVE = ResourceLocation.fromNamespaceAndPath("yigd", "destroyed_grave");
   private static final ResourceLocation UNCLAIMED_GRAVE = ResourceLocation.fromNamespaceAndPath("yigd", "unclaimed_grave");
   private final List<LightPlayerData> data;
   private final Screen previousScreen;
   private PlayerSelectionScreen.FilterButtonValue activeFilter = PlayerSelectionScreen.FilterButtonValue.WITH_GRAVES;
   private final EditBox searchBox;
   private final Button changeViewButton;
   private final List<Tuple<LightPlayerData, Button>> buttons;
   private ImageWidget scrollBar;
   private double scrollDistance;
   private int scrollContentHeight;
   private boolean scrolling;
   private static final int SCREEN_WIDTH = 226;
   private static final int SCREEN_HEIGHT = 187;
   private static final int SCROLL_MENU_HEIGHT = 128;
   private static final Font FONT = Minecraft.getInstance().font;

   public PlayerSelectionScreen(List<LightPlayerData> data, Screen previousScreen) {
      super(Component.translatable("text.yigd.gui.players_on_server"));
      this.searchBox = new EditBox(FONT, 185, 20, Component.empty());
      this.changeViewButton = Button.builder(
            Component.empty(),
            btn -> {
               this.activeFilter = PlayerSelectionScreen.FilterButtonValue.values()[(this.activeFilter.ordinal() + 1)
                  % PlayerSelectionScreen.FilterButtonValue.values().length];
               btn.setTooltip(Tooltip.create(this.getFilterTooltip()));
               this.reloadButtons();
            }
         )
         .size(20, 20)
         .tooltip(Tooltip.create(this.getFilterTooltip()))
         .build();
      this.buttons = new ArrayList<>();
      this.scrollBar = ImageWidget.sprite(6, 128, SCROLL_BAR);
      this.scrolling = false;
      this.data = data;
      this.previousScreen = previousScreen;
   }

   private void reloadButtons() {
      this.scrollDistance = 0.0;
      this.clearWidgets();
      this.buttons.clear();
      this.addWidget(this.changeViewButton);
      this.addWidget(this.searchBox);

      for (LightPlayerData playerData : this.data) {
         boolean show = switch (this.activeFilter) {
            case WITH_GRAVES -> playerData.graveCount() > 0;
            case WITH_CLAIMED -> playerData.graveCount() - playerData.destroyedCount() - playerData.unclaimedCount() > 0;
            case WITH_UNCLAIMED -> playerData.unclaimedCount() > 0;
            case WITH_DESTROYED -> playerData.destroyedCount() > 0;
         };
         if (show) {
            String searchContent = this.searchBox.getValue();
            if (!searchContent.isEmpty()) {
               Optional<String> name = playerData.playerProfile().name();
               if (name.isEmpty() || !name.get().toLowerCase(Locale.ROOT).contains(searchContent.toLowerCase(Locale.ROOT))) {
                  continue;
               }
            }

            Button button = Button.builder(
                  Component.empty(),
                  btn -> PacketDistributor.sendToServer(new GraveSelectionRequestC2SPacket(playerData.playerProfile()), new CustomPacketPayload[0])
               )
               .size(200, 20)
               .tooltip(
                  Tooltip.create(
                     Component.translatable("text.yigd.gui.unclaimed_count", new Object[]{playerData.unclaimedCount()})
                        .append("\n")
                        .append(Component.translatable("text.yigd.gui.destroyed_count", new Object[]{playerData.destroyedCount()}))
                        .append("\n")
                        .append(Component.translatable("text.yigd.gui.total_count", new Object[]{playerData.graveCount()}))
                  )
               )
               .build();
            this.buttons.add(new Tuple(playerData, button));
            this.addWidget(button);
         }
      }

      this.scrollContentHeight = this.buttons.size() * 20;
      float fraction = 128.0F / this.scrollContentHeight;
      this.scrollBar.setHeight(Math.max(4, (int)(Math.min(1.0F, fraction) * 128.0F)));
   }

   private Component getFilterTooltip() {
      return switch (this.activeFilter) {
         case WITH_GRAVES -> Component.translatable("button.yigd.gui.showing_with_data");
         case WITH_CLAIMED -> Component.translatable("button.yigd.gui.showing_with_claimed");
         case WITH_UNCLAIMED -> Component.translatable("button.yigd.gui.showing_with_unclaimed");
         case WITH_DESTROYED -> Component.translatable("button.yigd.gui.showing_with_destroyed");
      };
   }

   public void init() {
      this.searchBox.setResponder(s -> this.reloadButtons());
      this.reloadButtons();
      super.init();
   }

   private void setScrollDistance(double scrollDistance) {
      this.scrollDistance = Mth.clamp(scrollDistance, 0.0, this.getMaxScrollAmount());
   }

   private int getMaxScrollAmount() {
      return Math.max(0, this.scrollContentHeight - 128);
   }

   public boolean isPauseScreen() {
      return false;
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 259 && this.minecraft != null && !this.searchBox.isFocused()) {
         this.minecraft.setScreen(this.previousScreen);
         return true;
      } else {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
      if (!this.isMouseOver(mouseX, mouseY)) {
         return false;
      }

      this.setScrollDistance(this.scrollDistance - scrollY * 9.0);
      return true;
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      this.clearFocus();
      if (this.scrollBar.isMouseOver(mouseX, mouseY)) {
         this.scrollBar = ImageWidget.sprite(this.scrollBar.getWidth(), this.scrollBar.getHeight(), SCROLL_BAR_PRESSED);
         this.scrolling = true;
         return true;
      } else {
         return super.mouseClicked(mouseX, mouseY, button);
      }
   }

   public boolean mouseReleased(double mouseX, double mouseY, int button) {
      if (this.scrolling) {
         this.scrollBar = ImageWidget.sprite(this.scrollBar.getWidth(), this.scrollBar.getHeight(), SCROLL_BAR);
      }

      this.scrolling = false;
      return super.mouseReleased(mouseX, mouseY, button);
   }

   public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
      if (this.scrolling) {
         int scrollBarHeight = this.scrollBar.getHeight();
         int holderTop = this.height / 2 - 93 + 8;
         if (mouseY < holderTop) {
            this.setScrollDistance(0.0);
         } else if (mouseY > this.scrollBar.getY() + 128) {
            this.setScrollDistance(this.getMaxScrollAmount());
         } else {
            float barMenuRatio = (float)this.getMaxScrollAmount() / (128 - scrollBarHeight);
            this.setScrollDistance(this.scrollDistance + dragY * barMenuRatio);
         }
      }

      return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
   }

   public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      super.render(graphics, mouseX, mouseY, partialTick);
      int leftEdge = this.width / 2 - 113;
      int topEdge = this.height / 2 - 93;
      graphics.blitSprite(WINDOW_BG, leftEdge, topEdge, 226, 187);
      graphics.drawString(FONT, this.title, leftEdge + 8, topEdge + 8, 4210752, false);
      this.searchBox.setPosition(leftEdge + 8, topEdge + 24);
      this.searchBox.render(graphics, mouseX, mouseY, partialTick);
      this.changeViewButton.setPosition(leftEdge + 198, topEdge + 24);
      this.changeViewButton.render(graphics, mouseX, mouseY, partialTick);
      switch (this.activeFilter) {
         case WITH_GRAVES:
            graphics.renderItem(Items.BARRIER.getDefaultInstance(), leftEdge + 200, topEdge + 26);
            break;
         case WITH_CLAIMED:
            graphics.blitSprite(UNCLAIMED_GRAVE, leftEdge + 200, topEdge + 26, 16, 16);
            break;
         case WITH_UNCLAIMED:
            graphics.blitSprite(CLAIMED_GRAVE, leftEdge + 200, topEdge + 26, 16, 16);
            break;
         case WITH_DESTROYED:
            graphics.blitSprite(DESTROYED_GRAVE, leftEdge + 200, topEdge + 26, 16, 16);
      }

      this.renderScrollbar(graphics, mouseX, mouseY, partialTick, leftEdge + 8, topEdge + 49);
   }

   private void renderScrollbar(GuiGraphics graphics, int mouseX, int mouseY, float partialTick, int x, int y) {
      graphics.blitSprite(SLOT, x, y, 202, 130);
      graphics.blitSprite(SLOT, x + 202, y, 8, 130);
      int movedScrollBar = (int)(this.scrollDistance / this.getMaxScrollAmount() * (128 - this.scrollBar.getHeight()));
      this.scrollBar.setPosition(x + 203, y + 1 + movedScrollBar);
      this.scrollBar.render(graphics, mouseX, mouseY, partialTick);
      graphics.enableScissor(0, y + 1, this.width, y + 128 + 1);

      for (int i = 0; i < this.buttons.size(); i++) {
         if (!((i + 1) * 20 < this.scrollDistance)) {
            if (i * 20 - this.scrollDistance > 128.0) {
               break;
            }

            Tuple<LightPlayerData, Button> tuple = this.buttons.get(i);
            LightPlayerData playerData = (LightPlayerData)tuple.getA();
            Button button = (Button)tuple.getB();
            button.setPosition(x + 1, y + 1 + i * 20 - (int)this.scrollDistance);
            button.render(graphics, mouseX, mouseY, partialTick);
            ItemStack stack = Items.PLAYER_HEAD.getDefaultInstance();
            stack.set(DataComponents.PROFILE, playerData.playerProfile());
            String playerName = playerData.playerProfile().name().orElse("PLAYER_NOT_FOUND");
            graphics.renderItem(stack, x + 3, y + 3 + i * 20 - (int)this.scrollDistance);
            graphics.drawString(FONT, playerName, x + 21, y + 5 + i * 20 - (int)this.scrollDistance, 16777215, false);
         }
      }

      graphics.disableScissor();
   }

   public static void openScreen(PlayerSelectionS2CPacket payload) {
      Minecraft client = Minecraft.getInstance();
      client.execute(() -> client.setScreen(new PlayerSelectionScreen(payload.data(), client.screen)));
   }

   private enum FilterButtonValue {
      WITH_GRAVES,
      WITH_CLAIMED,
      WITH_UNCLAIMED,
      WITH_DESTROYED;
   }
}
