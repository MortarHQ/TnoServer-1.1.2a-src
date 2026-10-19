package dev.ftb.mods.ftbchunks.client.gui;

import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftbchunks.api.client.waypoint.Waypoint;
import dev.ftb.mods.ftbchunks.client.map.MapManager;
import dev.ftb.mods.ftbchunks.client.map.WaypointImpl;
import dev.ftb.mods.ftbchunks.net.TeleportFromMapPacket;
import dev.ftb.mods.ftblibrary.config.ColorConfig;
import dev.ftb.mods.ftblibrary.config.StringConfig;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.icon.ItemIcon;
import dev.ftb.mods.ftblibrary.ui.Button;
import dev.ftb.mods.ftblibrary.ui.ColorSelectorPanel;
import dev.ftb.mods.ftblibrary.ui.ContextMenuItem;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.SimpleButton;
import dev.ftb.mods.ftblibrary.ui.TextField;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.ToggleableButton;
import dev.ftb.mods.ftblibrary.ui.input.Key;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.ui.misc.AbstractGroupedButtonListScreen;
import dev.ftb.mods.ftblibrary.ui.misc.AbstractButtonListScreen.ButtonPanel;
import dev.ftb.mods.ftblibrary.ui.misc.AbstractGroupedButtonListScreen.GroupData;
import dev.ftb.mods.ftblibrary.util.TextComponentUtils;
import dev.ftb.mods.ftblibrary.util.client.ClientTextComponentUtils;
import dev.ftb.mods.ftblibrary.util.client.ClientUtils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.HoverEvent.Action;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class WaypointEditorScreen extends AbstractGroupedButtonListScreen<ResourceKey<Level>, WaypointImpl> {
   private int widestWaypoint = 0;

   public WaypointEditorScreen() {
      super(Component.translatable("ftbchunks.gui.waypoints"));
   }

   protected List<GroupData<ResourceKey<Level>, WaypointImpl>> getGroups() {
      List<GroupData<ResourceKey<Level>, WaypointImpl>> res = new ArrayList<>();
      Player player = Minecraft.getInstance().player;
      MapManager manager = MapManager.getInstance().orElseThrow();
      manager.getDimensions()
         .values()
         .stream()
         .filter(dim -> !dim.getWaypointManager().isEmpty())
         .sorted()
         .forEach(
            dim -> {
               List<WaypointImpl> waypoints = dim.getWaypointManager().stream().sorted(Comparator.comparingDouble(wp -> wp.getDistanceSq(player))).toList();
               Component title = TextComponentUtils.translatedDimension(dim.dimension)
                  .copy()
                  .withStyle(style -> style.withHoverEvent(new HoverEvent(Action.SHOW_TEXT, Component.literal(dim.dimension.location().toString()))));
               res.add(new GroupData(dim.dimension, false, title, waypoints));
            }
         );
      return res;
   }

   protected AbstractGroupedButtonListScreen<ResourceKey<Level>, WaypointImpl>.RowPanel createRowPanel(Panel panel, WaypointImpl value) {
      return new WaypointEditorScreen.RowPanel(panel, value);
   }

   public boolean onInit() {
      this.computeWaypointTextWidth();
      this.setWidth(Mth.clamp(this.widestWaypoint + 80, 220, this.getWindow().getGuiScaledWidth() * 4 / 5));
      this.setHeight(this.getWindow().getGuiScaledHeight() * 4 / 5);
      return true;
   }

   protected Panel createTopPanel() {
      return new WaypointEditorScreen.WaypointTopPanel();
   }

   public void alignWidgets() {
      super.alignWidgets();
      ((ButtonPanel)this.mainPanel).getWidgets().forEach(widget -> {
         if (widget instanceof WaypointEditorScreen.RowPanel row) {
            row.alignWidgets();
         }
      });
   }

   public static void refreshIfOpen() {
      ClientUtils.runLater(() -> {
         WaypointEditorScreen screen = (WaypointEditorScreen)ClientUtils.getCurrentGuiAs(WaypointEditorScreen.class);
         if (screen != null) {
            screen.rebuildGroupData();
            screen.refreshWidgets();
         }
      });
   }

   private void rebuildGroupData() {
      this.groups.clear();
      this.groups.addAll(this.getGroups());
   }

   private void computeWaypointTextWidth() {
      this.widestWaypoint = 0;
      this.groups
         .stream()
         .flatMap(grp -> grp.values().stream())
         .forEach(wp -> this.widestWaypoint = Math.max(this.widestWaypoint, this.getTheme().getStringWidth(wp.getName())));
   }

   private class RowPanel extends AbstractGroupedButtonListScreen<ResourceKey<Level>, WaypointImpl>.RowPanel {
      private static final Component DELETE = Component.translatable("ftbchunks.gui.delete");
      private static final Component QUICK_DELETE = Component.translatable("ftbchunks.gui.quick_delete");
      private final TextField nameField;
      private final TextField distField;
      private final SimpleButton hideButton = new ToggleableButton(
         this, !((WaypointImpl)this.value).isHidden(), (btn, newState) -> ((WaypointImpl)this.value).setHidden(!newState)
      );
      private final SimpleButton deleteButton;

      public RowPanel(Panel parent, WaypointImpl waypoint) {
         super(WaypointEditorScreen.this, parent, waypoint);
         this.nameField = new TextField(this).setTrim().setColor(Color4I.rgb(((WaypointImpl)this.value).getColor())).addFlags(2);
         String distStr = Minecraft.getInstance().level.dimension().equals(((WaypointImpl)this.value).getDimension())
            ? String.format("%.1fm", Math.sqrt(((WaypointImpl)this.value).getDistanceSq(Minecraft.getInstance().player)))
            : "";
         this.distField = new TextField(this).setText(distStr).setColor(Color4I.WHITE);
         this.deleteButton = new SimpleButton(this, DELETE, Icons.BIN, (btn, mb) -> this.deleteWaypoint(!isShiftKeyDown())) {
            public Component getTitle() {
               return isShiftKeyDown() ? WaypointEditorScreen.RowPanel.QUICK_DELETE : WaypointEditorScreen.RowPanel.DELETE;
            }
         };
      }

      public void addWidgets() {
         this.add(this.hideButton);
         this.add(this.nameField);
         this.add(this.distField);
         this.add(this.deleteButton);
      }

      public void alignWidgets() {
         int farRight = this.width - 8;
         int yPos = (this.height - WaypointEditorScreen.this.getTheme().getFontHeight()) / 2 + 1;
         this.hideButton.setPosAndSize(farRight - 8 - 16, 3, 12, 12);
         this.deleteButton.setPosAndSize(farRight - 8, 3, 12, 12);
         this.distField.setPos(this.hideButton.getPosX() - 5 - this.distField.width, yPos);
         this.nameField.setPos(5, yPos);
         this.nameField
            .setText(
               ClientTextComponentUtils.ellipsize(
                     WaypointEditorScreen.this.getTheme().getFont(), ((WaypointImpl)this.value).getDisplayName(), this.distField.getPosX() - 5
                  )
                  .getString()
            );
         this.nameField.setHeight(WaypointEditorScreen.this.getTheme().getFontHeight() + 2);
      }

      public void draw(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
         super.draw(graphics, theme, x, y, w, h);
         boolean mouseOver = this.getMouseY() >= 20 && this.isMouseOver();
         if (mouseOver) {
            Color4I.WHITE.withAlpha(33).draw(graphics, x, y, w, h);
         }
      }

      public boolean mouseDoubleClicked(MouseButton button) {
         if (this.isMouseOver()) {
            this.openWaypointEditPanel();
            return true;
         } else {
            return false;
         }
      }

      private void openWaypointEditPanel() {
         StringConfig configName = new StringConfig();
         configName.setValue(((WaypointImpl)this.value).getName());
         new WaypointAddScreen(
               configName,
               GlobalPos.of(((WaypointImpl)this.value).getDimension(), ((WaypointImpl)this.value).getPos()),
               Color4I.rgb(((WaypointImpl)this.value).getColor()),
               true
            )
            .openGui();
      }

      public boolean mousePressed(MouseButton button) {
         if (this.isMouseOver() && button.isRight()) {
            List<ContextMenuItem> list = new ArrayList<>();
            list.add(this.makeTitleMenuItem());
            list.add(ContextMenuItem.SEPARATOR);
            list.add(new ContextMenuItem(Component.translatable("ftbchunks.gui.edit"), Icons.SETTINGS, btn -> this.openWaypointEditPanel()));
            WaypointShareMenu.makeShareMenu(Minecraft.getInstance().player, (Waypoint)this.value).ifPresent(list::add);
            list.add(new ContextMenuItem(Component.translatable("gui.rename"), ItemIcon.getItemIcon(Items.WRITABLE_BOOK), btn -> {
               StringConfig config = new StringConfig();
               config.setDefaultValue("");
               config.setValue(((WaypointImpl)this.value).getName());
               config.onClicked(btn, MouseButton.LEFT, accepted -> {
                  if (accepted) {
                     ((WaypointImpl)this.value).setName((String)config.getValue());
                  }

                  WaypointEditorScreen.this.computeWaypointTextWidth();
                  this.openGui();
               });
            }));
            if (((WaypointImpl)this.value).getType().canChangeColor()) {
               list.add(new ContextMenuItem(Component.translatable("ftbchunks.gui.change_color"), Icons.COLOR_RGB, btn -> {
                  ColorConfig col = new ColorConfig();
                  col.setValue(Color4I.rgb(((WaypointImpl)this.value).getColor()));
                  ColorSelectorPanel.popupAtMouse(btn.getGui(), col, accepted -> {
                     if (accepted) {
                        ((WaypointImpl)this.value).setColor(((Color4I)col.getValue()).rgba());
                        ((WaypointImpl)this.value).refreshIcon();
                        if (this.widgets.get(1) instanceof TextField tf) {
                           tf.setColor(Color4I.rgb(((WaypointImpl)this.value).getColor()));
                        }
                     }
                  });
               }));
            }

            if (Minecraft.getInstance().player.hasPermissions(2)) {
               list.add(
                  new ContextMenuItem(
                     Component.translatable("ftbchunks.gui.teleport"),
                     ItemIcon.getItemIcon(Items.ENDER_PEARL),
                     b -> {
                        NetworkManager.sendToServer(
                           new TeleportFromMapPacket(((WaypointImpl)this.value).getPos().above(), false, ((WaypointImpl)this.value).getDimension())
                        );
                        this.closeGui(false);
                     }
                  )
               );
            }

            list.add(new ContextMenuItem(DELETE, Icons.BIN, btn -> this.deleteWaypoint(true)));
            this.getGui().openContextMenu(list);
            return true;
         } else {
            return super.mousePressed(button);
         }
      }

      public boolean keyPressed(Key key) {
         if (key.is(261)) {
            this.deleteWaypoint(!isShiftKeyDown());
            return true;
         } else {
            return super.keyPressed(key);
         }
      }

      private void deleteWaypoint(boolean confirm) {
         if (confirm) {
            this.getGui()
               .openYesNo(
                  Component.translatable(
                     "ftbchunks.gui.delete_waypoint",
                     new Object[]{((WaypointImpl)this.value).getDisplayName().copy().withStyle(Style.EMPTY.withColor(((WaypointImpl)this.value).getColor()))}
                  ),
                  Component.empty(),
                  () -> {
                     ((WaypointImpl)this.value).removeFromManager();
                     WaypointEditorScreen.this.rebuildGroupData();
                     this.getGui().refreshWidgets();
                  }
               );
         } else {
            ((WaypointImpl)this.value).removeFromManager();
            WaypointEditorScreen.this.rebuildGroupData();
            this.getGui().refreshWidgets();
         }
      }

      private ContextMenuItem makeTitleMenuItem() {
         return new ContextMenuItem(((WaypointImpl)this.value).getDisplayName(), Icon.empty(), null) {
            public Icon getIcon() {
               return ((WaypointImpl)RowPanel.this.value).getType().getIcon().withTint(Color4I.rgb(((WaypointImpl)RowPanel.this.value).getColor()));
            }
         };
      }
   }

   private class WaypointTopPanel extends AbstractGroupedButtonListScreen<ResourceKey<Level>, WaypointImpl>.CustomTopPanel {
      private final SimpleButton addWaypointButton = new SimpleButton(
         this,
         Component.translatable("ftbchunks.gui.add_waypoint"),
         Icons.ADD,
         (btn, mb) -> new WaypointAddScreen(new StringConfig(), Minecraft.getInstance().player).openGui()
      );

      public WaypointTopPanel() {
         super(WaypointEditorScreen.this);
      }

      public void addWidgets() {
         super.addWidgets();
         this.add(this.addWaypointButton);
      }

      public void alignWidgets() {
         super.alignWidgets();
         this.addWaypointButton.setPos(WaypointEditorScreen.this.groups.size() > 1 ? this.width - 58 : this.width - 18, 2);
      }
   }
}
