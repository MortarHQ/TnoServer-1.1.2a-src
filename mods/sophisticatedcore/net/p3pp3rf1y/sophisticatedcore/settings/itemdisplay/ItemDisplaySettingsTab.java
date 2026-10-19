package net.p3pp3rf1y.sophisticatedcore.settings.itemdisplay;

import com.google.common.collect.ImmutableList.Builder;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.Slot;
import net.p3pp3rf1y.sophisticatedcore.client.gui.SettingsScreen;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.Button;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinition;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinitions;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ImageButton;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ToggleButton;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TextureBlitData;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TranslationHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.UV;
import net.p3pp3rf1y.sophisticatedcore.renderdata.DisplaySide;
import net.p3pp3rf1y.sophisticatedcore.settings.ColorToggleButton;
import net.p3pp3rf1y.sophisticatedcore.settings.SettingsTab;

public class ItemDisplaySettingsTab extends SettingsTab<ItemDisplaySettingsContainer> {
   private static final TextureBlitData ICON = new TextureBlitData(GuiHelper.ICONS, Dimension.SQUARE_256, new UV(112, 64), Dimension.SQUARE_16);
   private static final TextureBlitData SLOT_SELECTION = new TextureBlitData(GuiHelper.GUI_CONTROLS, Dimension.SQUARE_256, new UV(93, 0), Dimension.SQUARE_24);
   private static final List<Component> ROTATE_TOOLTIP = new Builder()
      .add(Component.translatable(TranslationHelper.INSTANCE.translSettingsButton("rotate")))
      .addAll(TranslationHelper.INSTANCE.getTranslatedLines(TranslationHelper.INSTANCE.translSettingsButton("rotate_detail"), null, ChatFormatting.GRAY))
      .build();
   private static final TextureBlitData ROTATE_FOREGROUND = new TextureBlitData(
      GuiHelper.ICONS, new Position(1, 1), Dimension.SQUARE_256, new UV(128, 64), Dimension.SQUARE_16
   );
   public static final ButtonDefinition ROTATE = new ButtonDefinition(
      Dimension.SQUARE_16, GuiHelper.DEFAULT_BUTTON_BACKGROUND, GuiHelper.DEFAULT_BUTTON_HOVERED_BACKGROUND, ROTATE_FOREGROUND
   );
   private static final ButtonDefinition.Toggle<DisplaySide> DISPLAY_SIDE = ButtonDefinitions.createToggleButtonDefinition(
      Map.of(
         DisplaySide.FRONT,
         GuiHelper.getButtonStateData(
            new UV(144, 64),
            Dimension.SQUARE_16,
            new Position(1, 1),
            TranslationHelper.INSTANCE.getTranslatedLines(TranslationHelper.INSTANCE.translSettingsButton("display_side_front"), null)
         ),
         DisplaySide.LEFT,
         GuiHelper.getButtonStateData(
            new UV(160, 64),
            Dimension.SQUARE_16,
            new Position(1, 1),
            TranslationHelper.INSTANCE.getTranslatedLines(TranslationHelper.INSTANCE.translSettingsButton("display_side_left"), null)
         ),
         DisplaySide.RIGHT,
         GuiHelper.getButtonStateData(
            new UV(176, 64),
            Dimension.SQUARE_16,
            new Position(1, 1),
            TranslationHelper.INSTANCE.getTranslatedLines(TranslationHelper.INSTANCE.translSettingsButton("display_side_right"), null)
         )
      )
   );
   private int currentSelectedSlot = -1;

   public ItemDisplaySettingsTab(ItemDisplaySettingsContainer container, Position position, SettingsScreen screen) {
      super(
         container,
         position,
         screen,
         Component.translatable(TranslationHelper.INSTANCE.translSettings("item_display")),
         new Builder()
            .add(Component.translatable(TranslationHelper.INSTANCE.translSettingsTooltip("item_display")))
            .addAll(
               TranslationHelper.INSTANCE
                  .getTranslatedLines(TranslationHelper.INSTANCE.translSettingsTooltip("item_display") + "_detail", null, ChatFormatting.GRAY)
            )
            .build(),
         new Builder()
            .add(Component.translatable(TranslationHelper.INSTANCE.translSettingsTooltip("item_display")))
            .addAll(
               TranslationHelper.INSTANCE
                  .getTranslatedLines(TranslationHelper.INSTANCE.translSettingsTooltip("item_display") + "_open_detail", null, ChatFormatting.GRAY)
            )
            .build(),
         onTabIconClicked -> new ImageButton(new Position(position.x() + 1, position.y() + 4), Dimension.SQUARE_16, ICON, onTabIconClicked)
      );
      this.addHideableChild(new Button(new Position(this.x + 3, this.y + 24), ROTATE, button -> {
         if (button == 0) {
            container.rotateClockwise(this.currentSelectedSlot);
         } else if (button == 1) {
            container.rotateCounterClockwise(this.currentSelectedSlot);
         }
      }) {
         @Override
         protected List<Component> getTooltip() {
            return ItemDisplaySettingsTab.ROTATE_TOOLTIP;
         }
      });
      this.addHideableChild(new ColorToggleButton(new Position(this.x + 21, this.y + 24), container::getColor, container::setColor));
      if (this.showSideSelection()) {
         this.addHideableChild(new ToggleButton<>(new Position(this.x + 39, this.y + 24), DISPLAY_SIDE, button -> {
            if (button == 0) {
               container.setDisplaySide(container.getDisplaySide().next());
            } else if (button == 1) {
               container.setDisplaySide(container.getDisplaySide().previous());
            }
         }, container::getDisplaySide));
      }

      this.currentSelectedSlot = this.getSettingsContainer().getFirstSelectedSlot();
   }

   @Override
   public Optional<Integer> getSlotOverlayColor(int slotNumber, boolean templateLoadHovered) {
      if (templateLoadHovered) {
         return this.getSettingsContainer()
            .getSettingsContainer()
            .getSelectedTemplatesCategory(ItemDisplaySettingsCategory.class)
            .filter(c -> c.getSlots().contains(slotNumber))
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
         if (this.getSettingsContainer().isSlotSelected(slot.index)) {
            this.currentSelectedSlot = slot.index;
         }
      } else if (mouseButton == 1) {
         this.getSettingsContainer().unselectSlot(slot.index);
         if (!this.getSettingsContainer().isSlotSelected(slot.index) && this.currentSelectedSlot == slot.index) {
            this.currentSelectedSlot = this.getSettingsContainer().getFirstSelectedSlot();
         }
      }
   }

   @Override
   public void renderExtra(GuiGraphics guiGraphics, Slot slot) {
      super.renderExtra(guiGraphics, slot);
      if (this.isOpen && slot.index == this.currentSelectedSlot) {
         RenderSystem.disableDepthTest();
         RenderSystem.colorMask(true, true, true, false);
         GuiHelper.blit(guiGraphics, slot.x - 4, slot.y - 4, SLOT_SELECTION);
         RenderSystem.colorMask(true, true, true, true);
         RenderSystem.enableDepthTest();
      }
   }

   @Override
   public int getItemRotation(int slotIndex, boolean templateLoadHovered) {
      return templateLoadHovered
         ? this.getSettingsContainer()
            .getSettingsContainer()
            .getSelectedTemplatesCategory(ItemDisplaySettingsCategory.class)
            .filter(c -> c.getSlots().contains(slotIndex))
            .map(category -> category.getRotation(slotIndex))
            .orElse(0)
         : this.getSettingsContainer().getRotation(slotIndex);
   }

   private boolean showSideSelection() {
      return this.getSettingsContainer().supportsSideSelection();
   }
}
