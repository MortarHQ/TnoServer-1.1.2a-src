package net.p3pp3rf1y.sophisticatedcore.upgrades;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.Button;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinition;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinitions;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.CompositeWidgetBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ToggleButton;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.WidgetBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TranslationHelper;

public abstract class FilterLogicControlBase<F extends FilterLogic, S extends Slot, C extends FilterLogicContainerBase<F, S>>
   extends CompositeWidgetBase<WidgetBase> {
   public static final int TAG_FONT_COLOR = 16383998;
   public static final int MORE_TAGS_FONT_COLOR = 13882323;
   private static final int MAX_TAG_NAME_WIDTH = 68;
   protected final FilterLogicControlBase.MatchButton[] showMatchButtons;
   protected final int slotsTopYOffset;
   protected final int slotsPerRow;
   protected final int slotsInExtraRow;
   protected final int fullSlotRows;
   private final int totalSlotRows;
   private final StorageScreenBase<?> screen;
   protected final C container;
   private final List<Component> addTagTooltip = new ArrayList<>();
   private final List<Component> removeTagTooltip = new ArrayList<>();
   private final List<Component> tagListTooltip = new ArrayList<>();
   @Nullable
   private ToggleButton<Boolean> nbtButton = null;
   @Nullable
   private ToggleButton<Boolean> durabilityButton = null;
   private int tagButtonsYOffset;

   protected FilterLogicControlBase(
      StorageScreenBase<?> screen,
      C container,
      Position position,
      boolean buttonsVisible,
      int slotsPerRow,
      FilterLogicControlBase.MatchButton... showMatchButtons
   ) {
      super(position, new Dimension(0, 0));
      this.screen = screen;
      this.container = container;
      this.slotsTopYOffset = buttonsVisible ? 21 : 0;
      this.slotsPerRow = slotsPerRow;
      this.showMatchButtons = showMatchButtons;
      this.fullSlotRows = container.getFilterSlots().size() / slotsPerRow;
      this.slotsInExtraRow = container.getFilterSlots().size() % slotsPerRow;
      this.totalSlotRows = this.fullSlotRows + (this.slotsInExtraRow > 0 ? 1 : 0);
      if (this.shouldShow(FilterLogicControlBase.MatchButton.ALLOW_LIST)) {
         this.addChild(
            new ToggleButton<>(
               new Position(this.x, this.y), ButtonDefinitions.ALLOW_LIST, button -> container.setAllowList(!container.isAllowList()), container::isAllowList
            )
         );
      }

      if (this.shouldShow(FilterLogicControlBase.MatchButton.PRIMARY_MATCH)) {
         this.addChild(new ToggleButton<>(new Position(this.x + 18, this.y), ButtonDefinitions.PRIMARY_MATCH, button -> {
            PrimaryMatch next = container.getPrimaryMatch().next();
            if (next == PrimaryMatch.TAGS) {
               container.getFilterSlots().forEach(slot -> slot.x = -2000);
               this.onTagsMatchSelected();
            }

            container.setPrimaryMatch(next);
            this.setDurabilityAndNbtButtonsVisibility();
            this.moveSlotsToView();
         }, container::getPrimaryMatch));
         this.addTagButtons();
      }

      if (this.shouldShow(FilterLogicControlBase.MatchButton.DURABILITY)) {
         this.durabilityButton = new ToggleButton<>(
            new Position(this.x + 36, this.y),
            ButtonDefinitions.MATCH_DURABILITY,
            button -> container.setMatchDurability(!container.shouldMatchDurability()),
            container::shouldMatchDurability
         );
         this.addChild(this.durabilityButton);
      }

      if (this.shouldShow(FilterLogicControlBase.MatchButton.NBT)) {
         this.nbtButton = new ToggleButton<>(
            new Position(this.x + 54, this.y),
            ButtonDefinitions.MATCH_NBT,
            button -> container.setMatchNbt(!container.shouldMatchNbt()),
            container::shouldMatchNbt
         );
         this.addChild(this.nbtButton);
      }

      this.updateDimensions(
         Math.max(slotsPerRow * 18, this.getMaxButtonWidth()), (this.fullSlotRows + (this.slotsInExtraRow > 0 ? 1 : 0)) * 18 + this.slotsTopYOffset
      );
      this.setDurabilityAndNbtButtonsVisibility();
   }

   private void setDurabilityAndNbtButtonsVisibility() {
      boolean visible = this.container.getPrimaryMatch() != PrimaryMatch.TAGS;
      if (this.nbtButton != null) {
         this.nbtButton.setVisible(visible);
      }

      if (this.durabilityButton != null) {
         this.durabilityButton.setVisible(visible);
      }
   }

   protected void onTagsMatchSelected() {
   }

   private void addTagButtons() {
      this.tagButtonsYOffset = this.slotsTopYOffset + this.getTagListHeight();
      this.addChild(
         new FilterLogicControlBase<F, S, C>.TagButton(new Position(this.x + 36, this.y + this.tagButtonsYOffset), ButtonDefinitions.REMOVE_TAG, button -> {
            this.container.removeSelectedTag();
            this.updateTagListAndRemoveTooltips();
            this.updateAddTooltip();
         }, delta -> {
            if (delta < 0.0) {
               this.container.selectNextTagToRemove();
            } else {
               this.container.selectPreviousTagToRemove();
            }

            this.updateTagListAndRemoveTooltips();
         }) {
            @Override
            protected List<Component> getTooltip() {
               return FilterLogicControlBase.this.removeTagTooltip;
            }
         }
      );
      this.updateTagListAndRemoveTooltips();
      this.addChild(
         new FilterLogicControlBase<F, S, C>.TagButton(new Position(this.x + 18, this.y + this.tagButtonsYOffset), ButtonDefinitions.ADD_TAG, button -> {
            this.container.addSelectedTag();
            this.updateAddTooltip();
            this.updateTagListAndRemoveTooltips();
         }, delta -> {
            if (delta < 0.0) {
               this.container.selectNextTagToAdd();
            } else {
               this.container.selectPreviousTagToAdd();
            }

            this.updateAddTooltip();
         }) {
            @Override
            protected List<Component> getTooltip() {
               return FilterLogicControlBase.this.addTagTooltip;
            }
         }
      );
      this.updateAddTooltip();
      this.container.getTagSelectionSlot().setOnUpdate(this::updateAddTooltip);
      this.addChild(
         new ToggleButton<Boolean>(
            new Position(this.x + 54, this.y + this.tagButtonsYOffset),
            ButtonDefinitions.MATCH_ANY_TAG,
            button -> this.container.setMatchAnyTag(!this.container.shouldMatchAnyTag()),
            this.container::shouldMatchAnyTag
         ) {
            @Override
            protected void renderBg(GuiGraphics guiGraphics, Minecraft minecraft, int mouseX, int mouseY) {
               if (FilterLogicControlBase.this.container.getPrimaryMatch() == PrimaryMatch.TAGS) {
                  super.renderBg(guiGraphics, minecraft, mouseX, mouseY);
               }
            }

            @Override
            protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
               if (FilterLogicControlBase.this.container.getPrimaryMatch() == PrimaryMatch.TAGS) {
                  super.renderWidget(guiGraphics, mouseX, mouseY, partialTicks);
               }
            }

            @Override
            public boolean isMouseOver(double mouseX, double mouseY) {
               return FilterLogicControlBase.this.container.getPrimaryMatch() == PrimaryMatch.TAGS && super.isMouseOver(mouseX, mouseY);
            }
         }
      );
   }

   private void updateTagListAndRemoveTooltips() {
      this.updateTagListTooltip();
      this.updateRemoveTooltip();
   }

   private void updateTagListTooltip() {
      this.tagListTooltip.clear();
      this.tagListTooltip.add(Component.translatable(TranslationHelper.INSTANCE.translUpgradeKey("tag_list.title")).withStyle(new ChatFormatting[0]));
      Set<TagKey<Item>> tagNames = this.container.getTagNames();
      if (tagNames.isEmpty()) {
         this.tagListTooltip.add(Component.translatable(TranslationHelper.INSTANCE.translUpgradeKey("tag_list.empty")).withStyle(ChatFormatting.DARK_GRAY));
      } else {
         for (TagKey<Item> tagName : tagNames) {
            this.tagListTooltip.add(Component.literal("> " + tagName.location()).withStyle(ChatFormatting.GRAY));
         }
      }
   }

   private void updateRemoveTooltip() {
      this.removeTagTooltip.clear();
      this.removeTagTooltip.add(Component.translatable(TranslationHelper.INSTANCE.translUpgradeButton("remove_tag")));
      Set<TagKey<Item>> tagNames = this.container.getTagNames();
      if (tagNames.isEmpty()) {
         this.removeTagTooltip.add(Component.translatable(TranslationHelper.INSTANCE.translUpgradeButton("remove_tag.empty")).withStyle(ChatFormatting.RED));
      } else {
         int curIndex = 0;

         for (TagKey<Item> tagName : tagNames) {
            if (curIndex == this.container.getSelectedTagToRemove()) {
               this.removeTagTooltip.add(Component.literal("-> " + tagName.location()).withStyle(ChatFormatting.RED));
            } else {
               this.removeTagTooltip.add(Component.literal("> " + tagName.location()).withStyle(ChatFormatting.GRAY));
            }

            curIndex++;
         }

         this.removeTagTooltip
            .add(
               Component.translatable(TranslationHelper.INSTANCE.translUpgradeButton("remove_tag.controls"))
                  .withStyle(new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.DARK_GRAY})
            );
      }
   }

   private void updateAddTooltip() {
      this.addTagTooltip.clear();
      this.addTagTooltip.add(Component.translatable(TranslationHelper.INSTANCE.translUpgradeButton("add_tag")));
      if (this.container.getTagSelectionSlot().getItem().isEmpty()) {
         this.addTagTooltip
            .add(
               Component.translatable(TranslationHelper.INSTANCE.translUpgradeButton("add_tag.no_item"))
                  .withStyle(new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.DARK_GRAY})
            );
      } else {
         Set<TagKey<Item>> tagsToAdd = this.container.getTagsToAdd();
         int curIndex = 0;

         for (TagKey<Item> tagName : tagsToAdd) {
            if (curIndex == this.container.getSelectedTagToAdd()) {
               this.addTagTooltip.add(Component.literal("-> " + tagName.location()).withStyle(ChatFormatting.GREEN));
            } else {
               this.addTagTooltip.add(Component.literal("> " + tagName.location()).withStyle(ChatFormatting.GRAY));
            }

            curIndex++;
         }

         if (tagsToAdd.isEmpty()) {
            this.addTagTooltip
               .add(
                  Component.translatable(TranslationHelper.INSTANCE.translUpgradeButton("add_tag.no_additional_tags"))
                     .withStyle(new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.YELLOW})
               );
         } else {
            this.addTagTooltip
               .add(
                  Component.translatable(TranslationHelper.INSTANCE.translUpgradeButton("add_tag.controls"))
                     .withStyle(new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.DARK_GRAY})
               );
         }
      }
   }

   protected int getMaxButtonWidth() {
      int maxWidth = 0;

      for (WidgetBase w : this.children) {
         int buttonWidth = w.getX() + w.getWidth() - this.x;
         if (buttonWidth > maxWidth) {
            maxWidth = buttonWidth;
         }
      }

      return maxWidth;
   }

   protected boolean shouldShow(FilterLogicControlBase.MatchButton matchButton) {
      for (FilterLogicControlBase.MatchButton showMatchButton : this.showMatchButtons) {
         if (showMatchButton == matchButton) {
            return true;
         }
      }

      return false;
   }

   public void moveSlotsToView() {
      if (this.container.getPrimaryMatch() == PrimaryMatch.TAGS) {
         Slot slot = this.container.getTagSelectionSlot();
         slot.x = this.x - this.screen.getGuiLeft() + 1;
         slot.y = this.y - this.screen.getGuiTop() + this.tagButtonsYOffset + 1;
         this.container.getFilterSlots().forEach(s -> s.x = -2000);
      } else {
         int upgradeSlotNumber = 0;

         for (S slot : this.container.getFilterSlots()) {
            slot.x = this.x - this.screen.getGuiLeft() + 1 + upgradeSlotNumber % this.slotsPerRow * 18;
            slot.y = this.y - this.screen.getGuiTop() + this.slotsTopYOffset + 1 + upgradeSlotNumber / this.slotsPerRow * 18;
            upgradeSlotNumber++;
         }

         this.container.getTagSelectionSlot().x = -2000;
      }
   }

   @Override
   protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      super.renderWidget(guiGraphics, mouseX, mouseY, partialTicks);
      if (this.container.getPrimaryMatch() == PrimaryMatch.TAGS) {
         this.renderTagNames(guiGraphics);
      }
   }

   private void renderTagNames(GuiGraphics guiGraphics) {
      int count = 0;
      int prefixWidth = this.font.width("...");
      Set<TagKey<Item>> tagNames = this.container.getTagNames();
      int maxTagNameLines = this.getTagListHeight() / 10;

      for (TagKey<Item> tagName : tagNames) {
         if (tagNames.size() > maxTagNameLines && count == maxTagNameLines - 1) {
            guiGraphics.drawString(
               this.minecraft.font,
               Component.translatable(
                  TranslationHelper.INSTANCE.translUpgradeKey("tag_list.tag_overflow"), new Object[]{String.valueOf(tagNames.size() - (maxTagNameLines - 1))}
               ),
               this.x + 2,
               this.y + 23 + count * 10,
               13882323,
               false
            );
            break;
         }

         String name = tagName.location().toString();
         String shortened = name;
         if (this.font.width(name) > 68) {
            shortened = this.font.plainSubstrByWidth(name, 68 - prefixWidth, true);
            if (!shortened.equals(name)) {
               shortened = "..." + shortened;
            }
         }

         guiGraphics.drawString(this.minecraft.font, shortened, this.x + 2, this.y + 23 + count * 10, 16383998, false);
         count++;
      }
   }

   @Override
   public void renderTooltip(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY) {
      super.renderTooltip(screen, guiGraphics, mouseX, mouseY);
      if (this.container.getPrimaryMatch() == PrimaryMatch.TAGS && this.isMouseOverTagList(mouseX, mouseY)) {
         guiGraphics.renderTooltip(screen.font, this.tagListTooltip, Optional.empty(), mouseX, mouseY);
      }
   }

   private int getTagListHeight() {
      return (this.totalSlotRows - 1) * 18;
   }

   private boolean isMouseOverTagList(double mouseX, double mouseY) {
      return mouseX >= this.x
         && mouseX < this.x + this.getTagListWidth()
         && mouseY >= this.y + this.slotsTopYOffset
         && mouseY < this.y + this.slotsTopYOffset + this.getTagListHeight();
   }

   @Override
   protected void renderBg(GuiGraphics guiGraphics, Minecraft minecraft, int mouseX, int mouseY) {
      if (this.container.getPrimaryMatch() != PrimaryMatch.TAGS) {
         GuiHelper.renderSlotsBackground(guiGraphics, this.x, this.y + this.slotsTopYOffset, this.slotsPerRow, this.fullSlotRows, this.slotsInExtraRow);
      } else {
         GuiHelper.renderSlotsBackground(guiGraphics, this.x, this.y + this.tagButtonsYOffset, 1, 1, 0);
         GuiHelper.renderControlBackground(guiGraphics, this.x, this.y + this.slotsTopYOffset, this.getTagListWidth(), this.getTagListHeight());
      }
   }

   private int getTagListWidth() {
      return this.slotsPerRow * 18;
   }

   public enum MatchButton {
      ALLOW_LIST,
      PRIMARY_MATCH,
      DURABILITY,
      NBT;
   }

   private class TagButton extends Button {
      private final DoubleConsumer onScroll;

      public TagButton(Position position, ButtonDefinition buttonDefinition, IntConsumer onClick, DoubleConsumer onScroll) {
         super(position, buttonDefinition, onClick);
         this.onScroll = onScroll;
      }

      @Override
      protected void renderBg(GuiGraphics guiGraphics, Minecraft minecraft, int mouseX, int mouseY) {
         if (FilterLogicControlBase.this.container.getPrimaryMatch() == PrimaryMatch.TAGS) {
            super.renderBg(guiGraphics, minecraft, mouseX, mouseY);
         }
      }

      @Override
      protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
         if (FilterLogicControlBase.this.container.getPrimaryMatch() == PrimaryMatch.TAGS) {
            super.renderWidget(guiGraphics, mouseX, mouseY, partialTicks);
         }
      }

      @Override
      public boolean isMouseOver(double mouseX, double mouseY) {
         return FilterLogicControlBase.this.container.getPrimaryMatch() == PrimaryMatch.TAGS && super.isMouseOver(mouseX, mouseY);
      }

      public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
         if (FilterLogicControlBase.this.container.getPrimaryMatch() != PrimaryMatch.TAGS) {
            return false;
         }

         this.onScroll.accept(scrollY);
         return true;
      }
   }
}
