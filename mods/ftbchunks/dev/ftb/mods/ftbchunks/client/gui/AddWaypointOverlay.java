package dev.ftb.mods.ftbchunks.client.gui;

import dev.ftb.mods.ftbchunks.client.map.MapManager;
import dev.ftb.mods.ftblibrary.config.ColorConfig;
import dev.ftb.mods.ftblibrary.config.ConfigCallback;
import dev.ftb.mods.ftblibrary.config.ConfigFromString;
import dev.ftb.mods.ftblibrary.config.ui.EditStringConfigOverlay;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.ui.Button;
import dev.ftb.mods.ftblibrary.ui.ColorSelectorPanel;
import dev.ftb.mods.ftblibrary.ui.ContextMenuItem;
import dev.ftb.mods.ftblibrary.ui.DropDownMenu;
import dev.ftb.mods.ftblibrary.ui.GuiHelper;
import dev.ftb.mods.ftblibrary.ui.IntTextBox;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.SimpleButton;
import dev.ftb.mods.ftblibrary.ui.SimpleTextButton;
import dev.ftb.mods.ftblibrary.ui.TextBox;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.SimpleButton.Callback;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.util.TextComponentUtils;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class AddWaypointOverlay extends EditStringConfigOverlay<String> {
   private final AddWaypointOverlay.ColorButton colorButton;
   private final TextBox dimension;
   private final IntTextBox x;
   private final IntTextBox y;
   private final IntTextBox z;
   private final Button buttonAccept;
   private final Button buttonCancel;
   private final Button dropDownButton;
   private final AddWaypointOverlay.GlobalPosConfig pos;

   public AddWaypointOverlay(
      Panel panel, Component title, AddWaypointOverlay.GlobalPosConfig pos, ConfigFromString<String> config, ColorConfig colorConfig, ConfigCallback callback
   ) {
      super(panel, config, callback, title);
      this.pos = pos;
      this.colorButton = new AddWaypointOverlay.ColorButton(
         (Icon)colorConfig.getValue(), (btn, mb) -> ColorSelectorPanel.popupAtMouse(this.getGui(), colorConfig, accepted -> {
            if (accepted) {
               btn.setIcon((Icon)colorConfig.getValue());
            } else {
               colorConfig.setValue(((AddWaypointOverlay.ColorButton)btn).getIcon());
            }
         })
      );
      this.dimension = new TextBox(this) {
         public boolean allowInput() {
            return false;
         }
      };
      List<ContextMenuItem> contextMenuItems = createDimContextItems(key -> {
         this.dimension.setText(key.location().toString());
         this.getGui().closeContextMenu();
      });
      this.dropDownButton = new SimpleButton(
         this,
         Component.empty(),
         Icons.DROPDOWN_OUT,
         (widget, mouseButton) -> {
            DropDownMenu dropDownMenu = this.getGui().openDropdownMenu(contextMenuItems);
            dropDownMenu.setPos(
               dropDownMenu.getPosX() + 3 + (this.dimension.getX() - this.getMouseX()),
               dropDownMenu.getPosY() + 3 + (this.dimension.getY() + this.dimension.getHeight() - this.getMouseY())
            );
         }
      ) {
         public void drawBackground(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
            theme.drawButton(graphics, x, y, w, h, this.getWidgetType());
         }
      };
      this.x = new AddWaypointOverlay.AddWaypointIntBox(this);
      this.y = new AddWaypointOverlay.AddWaypointIntBox(this);
      this.z = new AddWaypointOverlay.AddWaypointIntBox(this);
      this.textBox.setLabel(Component.translatable("ftbchunks.gui.label.name"));
      this.dimension.setLabel(Component.translatable("ftbchunks.gui.label.dimension"));
      this.x.setLabel(Component.literal("X"));
      this.y.setLabel(Component.literal("Y"));
      this.z.setLabel(Component.literal("Z"));
      this.dimension.setText(((GlobalPos)pos.getValue()).dimension().location().toString());
      this.x.setAmount(((GlobalPos)pos.getValue()).pos().getX());
      this.y.setAmount(((GlobalPos)pos.getValue()).pos().getY());
      this.z.setAmount(((GlobalPos)pos.getValue()).pos().getZ());
      this.x.setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE);
      this.y.setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE);
      this.z.setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE);
      this.buttonAccept = new SimpleTextButton(this, Component.translatable("gui.accept"), Icons.ACCEPT) {
         public void onClicked(MouseButton button) {
            AddWaypointOverlay.this.handleAccepted(button);
         }

         public void addMouseOverText(TooltipList list) {
            if (AddWaypointOverlay.this.isNameValid()) {
               list.add(TextComponentUtils.hotkeyTooltip("⇧ + Enter"));
            } else {
               list.add(Component.translatable("ftbchunks.gui.waypoint.no_name"));
            }
         }

         public void draw(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
            AddWaypointOverlay.this.buttonAccept.setIcon(AddWaypointOverlay.this.isNameValid() ? Icons.ACCEPT : Icons.ACCEPT_GRAY);
            super.draw(graphics, theme, x, y, w, h);
         }
      };
      SimpleTextButton.accept(this, this::handleAccepted, new Component[]{TextComponentUtils.hotkeyTooltip("⇧ + Enter")});
      this.buttonCancel = SimpleTextButton.cancel(this, this::handleCancelled, new Component[]{TextComponentUtils.hotkeyTooltip("ESC")});
      this.setAddAcceptCancelButtons(false);
   }

   private boolean isNameValid() {
      return !this.textBox.getText().isBlank();
   }

   private void handleAccepted(MouseButton mouseButton) {
      if (this.isNameValid()) {
         this.playClickSound();
         this.onAccepted(this.buttonAccept, mouseButton);
      }
   }

   private void handleCancelled(MouseButton mouseButton) {
      this.playClickSound();
      this.onCancelled(this.buttonCancel, mouseButton);
   }

   private void updatePos() {
      ResourceLocation dimension = ResourceLocation.parse(this.dimension.getText());
      ResourceKey<Level> resourceKey = ResourceKey.create(Registries.DIMENSION, dimension);
      if (!this.x.getText().isBlank() && !this.y.getText().isBlank() && !this.z.getText().isBlank()) {
         BlockPos blockPos = new BlockPos(this.x.getIntValue(), this.y.getIntValue(), this.z.getIntValue());
         this.pos.setValue(GlobalPos.of(resourceKey, blockPos));
      }
   }

   public void addWidgets() {
      super.addWidgets();
      this.add(this.colorButton);
      this.add(this.dimension);
      this.add(this.x);
      this.add(this.y);
      this.add(this.z);
      this.add(this.buttonAccept);
      this.add(this.buttonCancel);
      this.add(this.dropDownButton);
   }

   public void alignWidgets() {
      super.alignWidgets();
      int widgetH = this.getGui().getTheme().getFontHeight() + 4;
      this.textBox.setPos(2, widgetH + 6);
      this.textBox.setWidth(this.width - widgetH - 4 - 4);
      this.colorButton.setPosAndSize(this.width - widgetH - 4, widgetH + 6, widgetH + 1, widgetH + 1);
      int w = (this.width - 3) / 3;
      int y1 = this.textBox.posY + this.textBox.getHeight() + widgetH / 2 + 2;
      this.dimension.setPosAndSize(2, y1, this.width - widgetH - 4 - 4, widgetH);
      this.dropDownButton.setPosAndSize(this.width - widgetH - 4, y1, widgetH + 1, widgetH);
      int xyzHeight = this.dimension.posY + this.dimension.getHeight() + widgetH / 2 + 2;
      this.x.setPosAndSize(2, xyzHeight, w - 2, widgetH);
      this.y.setPosAndSize(2 + w, xyzHeight, w - 2, widgetH);
      this.z.setPosAndSize(2 + w * 2, xyzHeight, w - 2, widgetH);
      int buttonW = w + w / 2;
      int buttonHeight = this.x.posY + this.x.getHeight() + 5;
      this.buttonAccept.setPosAndSize(this.width - buttonW * 2 - 2, buttonHeight, buttonW, 20);
      this.buttonCancel.setPosAndSize(this.width - buttonW, buttonHeight, buttonW - 3, 20);
      this.height += 72;
   }

   public void drawBackground(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
      super.drawBackground(graphics, theme, x, y, w, h);
   }

   public static List<ContextMenuItem> createDimContextItems(Consumer<ResourceKey<Level>> onSelected) {
      List<ContextMenuItem> contextMenuItems = new ArrayList<>();
      MapManager manager = MapManager.getInstance().orElseThrow();
      manager.getDimensions()
         .forEach(
            (key, value) -> contextMenuItems.add(
               new ContextMenuItem(TextComponentUtils.translatedDimension(key), Icon.empty(), button -> onSelected.accept(key))
            )
         );
      return contextMenuItems;
   }

   public class AddWaypointIntBox extends IntTextBox {
      public AddWaypointIntBox(Panel panel) {
         super(panel);
      }

      public void onTextChanged() {
         super.onTextChanged();
         AddWaypointOverlay.this.updatePos();
      }
   }

   private class ColorButton extends SimpleButton {
      public ColorButton(Icon icon, Callback c) {
         super(AddWaypointOverlay.this, Component.empty(), icon, c);
      }

      Color4I getIcon() {
         return this.icon instanceof Color4I c ? c : Color4I.empty();
      }

      public void draw(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
         this.icon.draw(graphics, x, y, w, h);
         Color4I shade = this.getIcon().addBrightness(-0.15F);
         GuiHelper.drawHollowRect(graphics, x, y, w, h, shade, false);
      }
   }

   public static class GlobalPosConfig extends ConfigFromString<GlobalPos> {
      public boolean parse(@Nullable Consumer<GlobalPos> callback, String string) {
         return false;
      }
   }
}
