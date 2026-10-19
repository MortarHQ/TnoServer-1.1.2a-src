package dev.isxander.yacl3.gui;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.ListOption;
import dev.isxander.yacl3.api.ListOptionEntry;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.utils.Dimension;
import dev.isxander.yacl3.impl.utils.YACLConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.NarratableEntry.NarrationPriority;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OptionListWidget extends ElementListWidgetExt<OptionListWidget.Entry> {
   private final YACLScreen yaclScreen;
   private final ConfigCategory category;
   private ImmutableList<OptionListWidget.Entry> viewableChildren;
   private String searchQuery = "";
   private final Consumer<DescriptionWithName> hoverEvent;
   private DescriptionWithName lastHoveredOption;

   public OptionListWidget(
      YACLScreen screen, ConfigCategory category, Minecraft client, int x, int y, int width, int height, Consumer<DescriptionWithName> hoverEvent
   ) {
      super(client, x, y, width, height, true);
      this.yaclScreen = screen;
      this.category = category;
      this.hoverEvent = hoverEvent;
      this.refreshOptions();
      UnmodifiableIterator var9 = category.groups().iterator();

      while (var9.hasNext()) {
         OptionGroup group = (OptionGroup)var9.next();
         if (group instanceof ListOption<?> listOption) {
            listOption.addRefreshListener(() -> this.refreshListEntries(listOption, category));
         }
      }
   }

   public void refreshOptions() {
      this.clearEntries();
      UnmodifiableIterator var1 = this.category.groups().iterator();

      while (var1.hasNext()) {
         OptionGroup group = (OptionGroup)var1.next();
         OptionListWidget.GroupSeparatorEntry groupSeparatorEntry;
         if (!group.isRoot()) {
            groupSeparatorEntry = group instanceof ListOption<?> listOption
               ? new OptionListWidget.ListGroupSeparatorEntry(listOption, this.yaclScreen)
               : new OptionListWidget.GroupSeparatorEntry(group, this.yaclScreen);
            this.addEntry(groupSeparatorEntry);
         } else {
            groupSeparatorEntry = null;
         }

         List<OptionListWidget.Entry> optionEntries = new ArrayList<>();
         if (groupSeparatorEntry instanceof OptionListWidget.ListGroupSeparatorEntry listGroupSeparatorEntry
            && listGroupSeparatorEntry.listOption.options().isEmpty()) {
            OptionListWidget.EmptyListLabel emptyListLabel = new OptionListWidget.EmptyListLabel(listGroupSeparatorEntry, this.category);
            this.addEntry(emptyListLabel);
            optionEntries.add(emptyListLabel);
         }

         UnmodifiableIterator var9 = group.options().iterator();

         while (var9.hasNext()) {
            Option<?> option = (Option<?>)var9.next();
            OptionListWidget.OptionEntry entry = new OptionListWidget.OptionEntry(
               option, this.category, group, groupSeparatorEntry, option.controller().provideWidget(this.yaclScreen, this.getDefaultEntryDimension())
            );
            this.addEntry(entry);
            optionEntries.add(entry);
         }

         if (groupSeparatorEntry != null) {
            groupSeparatorEntry.setChildEntries(optionEntries);
         }
      }

      this.recacheViewableChildren();
      this.setScrollAmount(0.0);
      this.resetSmoothScrolling();
   }

   private void refreshListEntries(ListOption<?> listOption, ConfigCategory category) {
      OptionListWidget.ListGroupSeparatorEntry groupSeparator = super.children()
         .stream()
         .filter(e -> e instanceof OptionListWidget.ListGroupSeparatorEntry gs && gs.group == listOption)
         .map(OptionListWidget.ListGroupSeparatorEntry.class::cast)
         .findAny()
         .orElse(null);
      if (groupSeparator == null) {
         YACLConstants.LOGGER.warn("Can't find group seperator to refresh list option entries for list option " + listOption.name());
      } else {
         for (OptionListWidget.Entry entry : groupSeparator.childEntries) {
            super.removeEntry(entry);
         }

         groupSeparator.childEntries.clear();
         if (listOption.options().isEmpty()) {
            OptionListWidget.EmptyListLabel emptyListLabel;
            this.addEntryBelow(groupSeparator, emptyListLabel = new OptionListWidget.EmptyListLabel(groupSeparator, category));
            groupSeparator.childEntries.add(emptyListLabel);
         } else {
            OptionListWidget.Entry lastEntry = groupSeparator;
            UnmodifiableIterator var10 = listOption.options().iterator();

            while (var10.hasNext()) {
               ListOptionEntry<?> listOptionEntry = (ListOptionEntry<?>)var10.next();
               OptionListWidget.OptionEntry optionEntry = new OptionListWidget.OptionEntry(
                  listOptionEntry,
                  category,
                  listOption,
                  groupSeparator,
                  listOptionEntry.controller().provideWidget(this.yaclScreen, this.getDefaultEntryDimension())
               );
               this.addEntryBelow(lastEntry, optionEntry);
               groupSeparator.childEntries.add(optionEntry);
               lastEntry = optionEntry;
            }
         }
      }
   }

   public Dimension<Integer> getDefaultEntryDimension() {
      return Dimension.ofInt(this.getRowLeft(), 0, this.getRowWidth(), 20);
   }

   public void expandAllGroups() {
      for (OptionListWidget.Entry entry : super.children()) {
         if (entry instanceof OptionListWidget.GroupSeparatorEntry groupSeparatorEntry) {
            groupSeparatorEntry.setExpanded(true);
         }
      }
   }

   public int getRowLeft() {
      return super.getRowLeft() - 6;
   }

   public int getRowWidth() {
      return this.getWidth() - 6 - 20;
   }

   public void updateSearchQuery(String query) {
      this.searchQuery = query;
      this.expandAllGroups();
      this.recacheViewableChildren();
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      for (OptionListWidget.Entry child : this.children()) {
         if (child != this.getEntryAtPosition(mouseX, mouseY) && child instanceof OptionListWidget.OptionEntry optionEntry) {
            optionEntry.widget.unfocus();
         }
      }

      return super.mouseClicked(mouseX, mouseY, button);
   }

   @Override
   public boolean mouseScrolled(double mouseX, double mouseY, double horizontal, double vertical) {
      super.mouseScrolled(mouseX, mouseY, horizontal, vertical);

      for (OptionListWidget.Entry child : this.children()) {
         if (child.mouseScrolled(mouseX, mouseY, horizontal, vertical)) {
            break;
         }
      }

      return true;
   }

   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
      return this.getFocused() != null && this.isDragging() && this.isValidMouseClick(button)
         ? ((OptionListWidget.Entry)this.getFocused()).mouseDragged(mouseX, mouseY, button, deltaX, deltaY)
         : super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      for (OptionListWidget.Entry child : this.children()) {
         if (child.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
         }
      }

      return super.keyPressed(keyCode, scanCode, modifiers);
   }

   public boolean charTyped(char chr, int modifiers) {
      for (OptionListWidget.Entry child : this.children()) {
         if (child.charTyped(chr, modifiers)) {
            return true;
         }
      }

      return super.charTyped(chr, modifiers);
   }

   public void recacheViewableChildren() {
      this.viewableChildren = ImmutableList.copyOf(super.children().stream().filter(OptionListWidget.Entry::isViewable).toList());
      int i = 0;

      for (UnmodifiableIterator var2 = this.viewableChildren.iterator(); var2.hasNext(); i++) {
         OptionListWidget.Entry entry = (OptionListWidget.Entry)var2.next();
         if (entry instanceof OptionListWidget.OptionEntry optionEntry) {
            optionEntry.widget.setDimension(optionEntry.widget.getDimension().withY(this.getRowTop(i)));
         }
      }
   }

   @NotNull
   public List<OptionListWidget.Entry> children() {
      return this.viewableChildren;
   }

   public void addEntry(int index, OptionListWidget.Entry entry) {
      super.children().add(index, entry);
      this.recacheViewableChildren();
   }

   public void addEntryBelow(OptionListWidget.Entry below, OptionListWidget.Entry entry) {
      int idx = super.children().indexOf(below) + 1;
      if (idx == 0) {
         throw new IllegalStateException("The entry to insert below does not exist!");
      }

      this.addEntry(idx, entry);
   }

   public void addEntryBelowWithoutScroll(OptionListWidget.Entry below, OptionListWidget.Entry entry) {
      double d = this.contentHeight() - this.scrollAmount();
      this.addEntryBelow(below, entry);
      this.setScrollAmount(this.contentHeight() - d);
   }

   public boolean removeEntryFromTop(OptionListWidget.Entry entry) {
      boolean ret = super.removeEntryFromTop(entry);
      this.recacheViewableChildren();
      return ret;
   }

   public boolean removeEntry(OptionListWidget.Entry entry) {
      boolean ret = super.removeEntry(entry);
      this.recacheViewableChildren();
      return ret;
   }

   private void setHoverDescription(DescriptionWithName description) {
      if (description != this.lastHoveredOption) {
         this.lastHoveredOption = description;
         this.hoverEvent.accept(description);
      }
   }

   protected void renderListBackground(GuiGraphics guiGraphics) {
   }

   public class EmptyListLabel extends OptionListWidget.Entry {
      private final OptionListWidget.ListGroupSeparatorEntry parent;
      private final String groupName;
      private final String categoryName;

      public EmptyListLabel(OptionListWidget.ListGroupSeparatorEntry parent, ConfigCategory category) {
         this.parent = parent;
         this.groupName = parent.group.name().getString().toLowerCase();
         this.categoryName = category.name().getString().toLowerCase();
      }

      public void render(
         GuiGraphics graphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta
      ) {
         graphics.drawCenteredString(
            Minecraft.getInstance().font,
            Component.translatable("yacl.list.empty").withStyle(new ChatFormatting[]{ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}),
            x + entryWidth / 2,
            y,
            -1
         );
      }

      @Override
      public boolean isViewable() {
         return this.parent.isExpanded() && (OptionListWidget.this.searchQuery.isEmpty() || this.groupName.contains(OptionListWidget.this.searchQuery));
      }

      @Override
      public int getItemHeight() {
         return 11;
      }

      public List<? extends GuiEventListener> children() {
         return ImmutableList.of();
      }

      public List<? extends NarratableEntry> narratables() {
         return ImmutableList.of();
      }
   }

   public abstract class Entry extends ElementListWidgetExt.Entry<OptionListWidget.Entry> {
      public boolean isViewable() {
         return true;
      }

      protected boolean isHovered() {
         return Objects.equals(OptionListWidget.this.getHovered(), this);
      }
   }

   public class GroupSeparatorEntry extends OptionListWidget.Entry {
      protected final OptionGroup group;
      protected final MultiLineLabel wrappedName;
      protected final MultiLineLabel wrappedTooltip;
      protected final LowProfileButtonWidget expandMinimizeButton;
      protected final Screen screen;
      protected final Font font;
      protected boolean groupExpanded;
      protected List<OptionListWidget.Entry> childEntries;
      private int y;

      private GroupSeparatorEntry(OptionGroup group, Screen screen) {
         this.font = Minecraft.getInstance().font;
         this.childEntries = new ArrayList<>();
         this.group = group;
         this.screen = screen;
         this.wrappedName = MultiLineLabel.create(this.font, group.name(), OptionListWidget.this.getRowWidth() - 45);
         this.wrappedTooltip = MultiLineLabel.create(this.font, group.tooltip(), screen.width / 3 * 2 - 10);
         this.groupExpanded = !group.collapsed();
         this.expandMinimizeButton = new LowProfileButtonWidget(0, 0, 20, 20, Component.empty(), btn -> this.onExpandButtonPress());
         this.updateExpandMinimizeText();
      }

      public void render(
         GuiGraphics graphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta
      ) {
         this.y = y;
         int buttonY = y + entryHeight / 2 - this.expandMinimizeButton.getHeight() / 2 + 1;
         this.expandMinimizeButton.setY(buttonY);
         this.expandMinimizeButton.setX(x);
         this.expandMinimizeButton.render(graphics, mouseX, mouseY, tickDelta);
         this.wrappedName.renderCentered(graphics, x + entryWidth / 2, y + this.getYPadding());
         if (this.isHovered()) {
            OptionListWidget.this.setHoverDescription(DescriptionWithName.of(this.group.name(), this.group.description()));
         }
      }

      public boolean isExpanded() {
         return this.groupExpanded;
      }

      public void setExpanded(boolean expanded) {
         if (this.groupExpanded != expanded) {
            this.groupExpanded = expanded;
            this.updateExpandMinimizeText();
            OptionListWidget.this.recacheViewableChildren();
         }
      }

      protected void onExpandButtonPress() {
         this.setExpanded(!this.isExpanded());
      }

      protected void updateExpandMinimizeText() {
         this.expandMinimizeButton.setMessage(Component.literal(this.isExpanded() ? "▼" : "▶"));
      }

      public void setChildEntries(List<? extends OptionListWidget.Entry> childEntries) {
         this.childEntries.clear();
         this.childEntries.addAll(childEntries);
      }

      @Override
      public boolean isViewable() {
         return OptionListWidget.this.searchQuery.isEmpty() || this.childEntries.stream().anyMatch(OptionListWidget.Entry::isViewable);
      }

      @Override
      public int getItemHeight() {
         return Math.max(this.wrappedName.getLineCount(), 1) * 9 + this.getYPadding() * 2;
      }

      private int getYPadding() {
         return 6;
      }

      public void setFocused(boolean focused) {
         super.setFocused(focused);
         if (focused) {
            OptionListWidget.this.setHoverDescription(DescriptionWithName.of(this.group.name(), this.group.description()));
         }
      }

      public List<? extends NarratableEntry> narratables() {
         return ImmutableList.of(new NarratableEntry() {
            public NarrationPriority narrationPriority() {
               return NarrationPriority.HOVERED;
            }

            public void updateNarration(NarrationElementOutput builder) {
               builder.add(NarratedElementType.TITLE, GroupSeparatorEntry.this.group.name());
               builder.add(NarratedElementType.HINT, GroupSeparatorEntry.this.group.tooltip());
            }
         });
      }

      public List<? extends GuiEventListener> children() {
         return ImmutableList.of(this.expandMinimizeButton);
      }
   }

   public class ListGroupSeparatorEntry extends OptionListWidget.GroupSeparatorEntry {
      private final ListOption<?> listOption;
      private final TextScaledButtonWidget resetListButton;
      private final TooltipButtonWidget addListButton;

      private ListGroupSeparatorEntry(ListOption<?> group, Screen screen) {
         super(group, screen);
         this.listOption = group;
         this.resetListButton = new TextScaledButtonWidget(
            screen, OptionListWidget.this.getRowRight() - 20, -50, 20, 20, 2.0F, Component.literal("↻"), button -> group.requestSetDefault()
         );
         group.addListener((opt, val) -> this.resetListButton.active = !opt.isPendingValueDefault() && opt.available());
         this.resetListButton.active = !group.isPendingValueDefault() && group.available();
         this.addListButton = new TooltipButtonWidget(
            OptionListWidget.this.yaclScreen,
            this.resetListButton.getX() - 20,
            -50,
            20,
            20,
            Component.literal("+"),
            Component.translatable("yacl.list.add_top"),
            btn -> {
               group.insertNewEntry();
               this.setExpanded(true);
            }
         );
         this.updateExpandMinimizeText();
         this.minimizeIfUnavailable();
      }

      @Override
      public void render(
         GuiGraphics graphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta
      ) {
         this.updateExpandMinimizeText();
         super.render(graphics, index, y, x, entryWidth, entryHeight, mouseX, mouseY, hovered, tickDelta);
         int buttonY = this.expandMinimizeButton.getY();
         this.resetListButton.setY(buttonY);
         this.addListButton.setY(buttonY);
         this.resetListButton.render(graphics, mouseX, mouseY, tickDelta);
         this.addListButton.render(graphics, mouseX, mouseY, tickDelta);
      }

      private void minimizeIfUnavailable() {
         if (!this.listOption.available() && this.isExpanded()) {
            this.setExpanded(false);
         }
      }

      @Override
      protected void updateExpandMinimizeText() {
         super.updateExpandMinimizeText();
         this.expandMinimizeButton.active = this.listOption == null || this.listOption.available();
         if (this.addListButton != null) {
            this.addListButton.active = this.expandMinimizeButton.active && this.listOption.numberOfEntries() < this.listOption.maximumNumberOfEntries();
         }
      }

      @Override
      public void setExpanded(boolean expanded) {
         super.setExpanded(this.listOption.available() && expanded);
      }

      @Override
      public List<? extends GuiEventListener> children() {
         return ImmutableList.of(this.expandMinimizeButton, this.addListButton, this.resetListButton);
      }
   }

   public class OptionEntry extends OptionListWidget.Entry {
      public final Option<?> option;
      public final ConfigCategory category;
      public final OptionGroup group;
      @Nullable
      public final OptionListWidget.GroupSeparatorEntry groupSeparatorEntry;
      public final AbstractWidget widget;
      private final TextScaledButtonWidget resetButton;
      private final String categoryName;
      private final String groupName;

      public OptionEntry(
         Option<?> option,
         ConfigCategory category,
         OptionGroup group,
         @Nullable OptionListWidget.GroupSeparatorEntry groupSeparatorEntry,
         AbstractWidget widget
      ) {
         this.option = option;
         this.category = category;
         this.group = group;
         this.groupSeparatorEntry = groupSeparatorEntry;
         this.widget = widget;
         this.categoryName = category.name().getString().toLowerCase();
         this.groupName = group.name().getString().toLowerCase();
         if (option.canResetToDefault() && this.widget.canReset()) {
            this.widget.setDimension(this.widget.getDimension().expanded(-20, 0));
            this.resetButton = new TextScaledButtonWidget(
               OptionListWidget.this.yaclScreen,
               widget.getDimension().xLimit(),
               -50,
               20,
               20,
               2.0F,
               Component.literal("↻"),
               button -> option.requestSetDefault()
            );
            option.addListener((opt, val) -> this.resetButton.active = !opt.isPendingValueDefault() && opt.available());
            this.resetButton.active = !option.isPendingValueDefault() && option.available();
         } else {
            this.resetButton = null;
         }
      }

      public void render(
         GuiGraphics graphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta
      ) {
         this.widget.setDimension(this.widget.getDimension().withY(y));
         this.widget.render(graphics, mouseX, mouseY, tickDelta);
         if (this.resetButton != null) {
            this.resetButton.setY(y);
            this.resetButton.render(graphics, mouseX, mouseY, tickDelta);
         }

         if (this.isHovered()) {
            OptionListWidget.this.setHoverDescription(DescriptionWithName.of(this.option.name(), this.option.description()));
         }
      }

      public boolean mouseScrolled(double mouseX, double mouseY, double horizontal, double vertical) {
         return this.widget.mouseScrolled(mouseX, mouseY, horizontal, vertical);
      }

      public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
         return this.widget.keyPressed(keyCode, scanCode, modifiers);
      }

      public boolean charTyped(char chr, int modifiers) {
         return this.widget.charTyped(chr, modifiers);
      }

      @Override
      public boolean isViewable() {
         return (this.groupSeparatorEntry == null || this.groupSeparatorEntry.isExpanded())
            && (
               OptionListWidget.this.searchQuery.isEmpty()
                  || this.groupName.contains(OptionListWidget.this.searchQuery)
                  || this.widget.matchesSearch(OptionListWidget.this.searchQuery)
            );
      }

      @Override
      public int getItemHeight() {
         return Math.max(this.widget.getDimension().height(), this.resetButton != null ? this.resetButton.getHeight() : 0) + 2;
      }

      public void setFocused(boolean focused) {
         super.setFocused(focused);
         if (focused) {
            OptionListWidget.this.setHoverDescription(DescriptionWithName.of(this.option.name(), this.option.description()));
         }
      }

      public List<? extends NarratableEntry> narratables() {
         return this.resetButton == null ? ImmutableList.of(this.widget) : ImmutableList.of(this.widget, this.resetButton);
      }

      public List<? extends GuiEventListener> children() {
         return this.resetButton == null ? ImmutableList.of(this.widget) : ImmutableList.of(this.widget, this.resetButton);
      }
   }
}
