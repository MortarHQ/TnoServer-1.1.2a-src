package dev.ftb.mods.ftblibrary.ui.misc;

import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.ui.Button;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.SimpleButton;
import dev.ftb.mods.ftblibrary.ui.TextField;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.input.Key;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.util.TextComponentUtils;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public abstract class AbstractGroupedButtonListScreen<G, E> extends AbstractButtonListScreen {
   protected final List<AbstractGroupedButtonListScreen.GroupData<G, E>> groups;
   private final Set<G> collapsed = new HashSet();
   private final Button buttonCollapseAll;
   private final Button buttonExpandAll;
   private final Component title;

   public AbstractGroupedButtonListScreen(Component title) {
      this.showBottomPanel(false);
      this.showCloseButton(true);
      this.setHasSearchBox(true);
      this.title = title;
      this.groups = this.getGroups();
      this.groups
         .stream()
         .filter(AbstractGroupedButtonListScreen.GroupData::defaultedCollapsed)
         .map(AbstractGroupedButtonListScreen.GroupData::group)
         .forEach(this.collapsed::add);
      this.buttonExpandAll = new SimpleButton(
         this.topPanel,
         List.of(Component.translatable("gui.expand_all"), TextComponentUtils.hotkeyTooltip("="), TextComponentUtils.hotkeyTooltip("+")),
         Icons.EXPAND,
         (widget, button) -> this.toggleAll(false)
      );
      this.buttonCollapseAll = new SimpleButton(
         this.topPanel,
         List.of(Component.translatable("gui.collapse_all"), TextComponentUtils.hotkeyTooltip("-")),
         Icons.COLLAPSE,
         (widget, button) -> this.toggleAll(true)
      );
   }

   protected abstract List<AbstractGroupedButtonListScreen.GroupData<G, E>> getGroups();

   protected AbstractGroupedButtonListScreen<G, E>.GroupButton createGroupButton(Panel panel, AbstractGroupedButtonListScreen.GroupData<G, E> group) {
      return new AbstractGroupedButtonListScreen.GroupButton(panel, group.group(), group.groupName, group.values());
   }

   protected abstract AbstractGroupedButtonListScreen<G, E>.RowPanel createRowPanel(Panel var1, E var2);

   private void toggleAll(boolean collapsed) {
      boolean allOpen = this.groups.stream().noneMatch(g -> this.isCollapsed(g.group));
      if (!allOpen || collapsed) {
         this.groups.forEach(group -> this.setCollapsed(group.group(), collapsed));
         this.scrollBar.setValue(0.0);
         this.getGui().refreshWidgets();
      }
   }

   @Override
   protected void doCancel() {
   }

   @Override
   protected void doAccept() {
   }

   @Override
   public boolean onInit() {
      this.setWidth(220);
      this.setHeight(this.getWindow().getGuiScaledHeight() * 4 / 5);
      return true;
   }

   @Override
   public boolean keyPressed(Key key) {
      if (super.keyPressed(key)) {
         return true;
      }

      if (key.is(334) || key.is(61)) {
         this.toggleAll(false);
      } else if (key.is(45) || key.is(333)) {
         this.toggleAll(true);
      }

      return false;
   }

   @Override
   protected int getTopPanelHeight() {
      return 22;
   }

   @Override
   protected Panel createTopPanel() {
      return new AbstractGroupedButtonListScreen.CustomTopPanel();
   }

   @Override
   public void addButtons(Panel panel) {
      for (AbstractGroupedButtonListScreen.GroupData<G, E> group : this.groups) {
         AbstractGroupedButtonListScreen<G, E>.GroupButton groupButton = this.createGroupButton(panel, group);
         panel.add(groupButton);
         if (!groupButton.isCollapsed()) {
            panel.addAll(groupButton.collectPanels());
         }
      }
   }

   public boolean isCollapsed(G group) {
      return this.collapsed.contains(group);
   }

   public void setCollapsed(G group, boolean collapsed) {
      if (collapsed) {
         this.collapsed.add(group);
      } else {
         this.collapsed.remove(group);
      }
   }

   protected class CustomTopPanel extends AbstractThreePanelScreen<AbstractButtonListScreen.ButtonPanel>.TopPanel {
      private final TextField titleLabel = new TextField(this).addFlags(2);

      @Override
      public void addWidgets() {
         this.titleLabel.setText(AbstractGroupedButtonListScreen.this.title);
         this.titleLabel.addFlags(32);
         this.add(this.titleLabel);
         if (AbstractGroupedButtonListScreen.this.groups.size() > 1) {
            this.add(AbstractGroupedButtonListScreen.this.buttonExpandAll);
            this.add(AbstractGroupedButtonListScreen.this.buttonCollapseAll);
         }
      }

      @Override
      public void alignWidgets() {
         this.titleLabel.setPosAndSize(4, 0, this.titleLabel.width, this.height);
         if (AbstractGroupedButtonListScreen.this.groups.size() > 1) {
            AbstractGroupedButtonListScreen.this.buttonExpandAll.setPos(this.width - 18, 2);
            AbstractGroupedButtonListScreen.this.buttonCollapseAll.setPos(this.width - 38, 2);
         }
      }
   }

   protected class GroupButton extends Button {
      protected final G group;
      private final Component titleText;
      private final List<AbstractGroupedButtonListScreen<G, E>.RowPanel> rowPanels;

      public GroupButton(Panel panel, G group, Component titleText, List<E> values) {
         super(panel);
         this.group = group;
         this.titleText = titleText;
         this.setCollapsed(this.isCollapsed());
         this.rowPanels = new ArrayList<>();

         for (E value : values) {
            this.rowPanels.add(AbstractGroupedButtonListScreen.this.createRowPanel(panel, value));
         }
      }

      public List<AbstractGroupedButtonListScreen<G, E>.RowPanel> collectPanels() {
         return this.isCollapsed() ? List.of() : List.copyOf(this.rowPanels);
      }

      @Override
      public void onClicked(MouseButton button) {
         this.setCollapsed(!this.isCollapsed());
         this.parent.refreshWidgets();
         AbstractGroupedButtonListScreen.this.refreshWidgets();
         this.playClickSound();
      }

      public boolean isCollapsed() {
         return AbstractGroupedButtonListScreen.this.isCollapsed(this.group);
      }

      public void setCollapsed(boolean collapsed) {
         AbstractGroupedButtonListScreen.this.setCollapsed(this.group, collapsed);
         boolean isCollapsed = this.isCollapsed();
         this.setTitle(
            Component.literal(isCollapsed ? "▶ " : "▼ ").withStyle(isCollapsed ? ChatFormatting.DARK_GREEN : ChatFormatting.GREEN).append(this.titleText)
         );
      }

      @Override
      public void draw(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
         theme.drawWidget(graphics, x, y, w, h, this.getWidgetType());
         theme.drawString(graphics, this.getTitle(), x + 3, y + (this.height + 1 - theme.getFontHeight()) / 2, 2);
         if (this.isMouseOver()) {
            Color4I.WHITE.withAlpha(33).draw(graphics, x, y, w, h);
         }
      }

      @Override
      public void addMouseOverText(TooltipList list) {
      }
   }

   public record GroupData<G, E>(G group, boolean defaultedCollapsed, Component groupName, List<E> values) {
   }

   protected abstract class RowPanel extends Panel {
      protected final E value;

      public RowPanel(Panel panel, E value) {
         super(panel);
         this.value = value;
         this.setHeight(18);
      }

      @Override
      public void draw(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
         super.draw(graphics, theme, x, y, w, h);
         boolean mouseOver = this.getMouseY() >= 20 && this.isMouseOver();
         if (mouseOver) {
            Color4I.WHITE.withAlpha(33).draw(graphics, x, y, w, h);
         }
      }

      @Override
      public boolean mousePressed(MouseButton button) {
         return this.isMouseOver() && button.isRight() ? true : super.mousePressed(button);
      }
   }
}
