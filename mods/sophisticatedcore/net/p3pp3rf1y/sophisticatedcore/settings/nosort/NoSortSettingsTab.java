package net.p3pp3rf1y.sophisticatedcore.settings.nosort;

import com.google.common.collect.ImmutableList.Builder;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.Slot;
import net.p3pp3rf1y.sophisticatedcore.client.gui.SettingsScreen;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.Button;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinition;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ImageButton;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TextureBlitData;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TranslationHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.UV;
import net.p3pp3rf1y.sophisticatedcore.settings.ColorToggleButton;
import net.p3pp3rf1y.sophisticatedcore.settings.SettingsTab;

public class NoSortSettingsTab extends SettingsTab<NoSortSettingsContainer> {
   private static final TextureBlitData ICON = new TextureBlitData(GuiHelper.ICONS, Dimension.SQUARE_256, new UV(32, 80), Dimension.SQUARE_16);
   private static final TextureBlitData SELECT_ALL_SLOTS_FOREGROUND = new TextureBlitData(
      GuiHelper.ICONS, new Position(1, 1), Dimension.SQUARE_256, new UV(16, 80), Dimension.SQUARE_16
   );
   public static final ButtonDefinition SELECT_ALL_SLOTS = new ButtonDefinition(
      Dimension.SQUARE_16,
      GuiHelper.DEFAULT_BUTTON_BACKGROUND,
      GuiHelper.DEFAULT_BUTTON_HOVERED_BACKGROUND,
      SELECT_ALL_SLOTS_FOREGROUND,
      Component.translatable(TranslationHelper.INSTANCE.translSettingsButton("select_all_slots"))
   );
   private static final TextureBlitData UNSELECT_ALL_SLOTS_FOREGROUND = new TextureBlitData(
      GuiHelper.ICONS, new Position(1, 1), Dimension.SQUARE_256, new UV(48, 80), Dimension.SQUARE_16
   );
   public static final ButtonDefinition UNSELECT_ALL_SLOTS = new ButtonDefinition(
      Dimension.SQUARE_16,
      GuiHelper.DEFAULT_BUTTON_BACKGROUND,
      GuiHelper.DEFAULT_BUTTON_HOVERED_BACKGROUND,
      UNSELECT_ALL_SLOTS_FOREGROUND,
      Component.translatable(TranslationHelper.INSTANCE.translSettingsButton("unselect_all_slots"))
   );

   public NoSortSettingsTab(NoSortSettingsContainer container, Position position, SettingsScreen screen) {
      super(
         container,
         position,
         screen,
         Component.translatable(TranslationHelper.INSTANCE.translSettings("no_sort")),
         new Builder()
            .add(Component.translatable(TranslationHelper.INSTANCE.translSettingsTooltip("no_sort")))
            .addAll(
               TranslationHelper.INSTANCE
                  .getTranslatedLines(TranslationHelper.INSTANCE.translSettingsTooltip("no_sort") + "_detail", null, ChatFormatting.GRAY)
            )
            .build(),
         new Builder()
            .add(Component.translatable(TranslationHelper.INSTANCE.translSettingsTooltip("no_sort")))
            .addAll(
               TranslationHelper.INSTANCE
                  .getTranslatedLines(TranslationHelper.INSTANCE.translSettingsTooltip("no_sort") + "_open_detail", null, ChatFormatting.GRAY)
            )
            .build(),
         onTabIconClicked -> new ImageButton(new Position(position.x() + 1, position.y() + 4), Dimension.SQUARE_16, ICON, onTabIconClicked)
      );
      this.addHideableChild(new Button(new Position(this.x + 3, this.y + 24), SELECT_ALL_SLOTS, button -> container.selectAllSlots()));
      this.addHideableChild(new Button(new Position(this.x + 21, this.y + 24), UNSELECT_ALL_SLOTS, button -> container.unselectAllSlots()));
      this.addHideableChild(new ColorToggleButton(new Position(this.x + 39, this.y + 24), container::getColor, container::setColor));
   }

   @Override
   public Optional<Integer> getSlotOverlayColor(int slotNumber, boolean templateLoadHovered) {
      if (templateLoadHovered) {
         return this.getSettingsContainer()
            .getSettingsContainer()
            .getSelectedTemplatesCategory(NoSortSettingsCategory.class)
            .filter(c -> c.isSlotSelected(slotNumber))
            .map(category -> category.getColor().getTextureDiffuseColor() & 16777215 | 1342177280);
      } else {
         return this.getSettingsContainer().isSlotSelected(slotNumber)
            ? Optional.of(this.getSettingsContainer().getColor().getTextureDiffuseColor() | 1342177280)
            : Optional.empty();
      }
   }

   @Override
   public void handleSlotClick(Slot slot, int mouseButton) {
      if (mouseButton == 0) {
         this.getSettingsContainer().selectSlot(slot.index);
      } else if (mouseButton == 1) {
         this.getSettingsContainer().unselectSlot(slot.index);
      }
   }
}
