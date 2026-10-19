package net.p3pp3rf1y.sophisticatedcore.upgrades.jukebox;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.UpgradeSettingsTab;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.Button;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinition;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ToggleButton;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TextureBlitData;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TranslationHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.UV;

public abstract class JukeboxUpgradeTab extends UpgradeSettingsTab<JukeboxUpgradeContainer> {
   private static final TextureBlitData PLAY_FOREGROUND = new TextureBlitData(
      GuiHelper.ICONS, new Position(1, 1), Dimension.SQUARE_256, new UV(16, 64), Dimension.SQUARE_16
   );
   private static final ButtonDefinition PLAY = new ButtonDefinition(
      Dimension.SQUARE_18,
      GuiHelper.DEFAULT_BUTTON_BACKGROUND,
      GuiHelper.DEFAULT_BUTTON_HOVERED_BACKGROUND,
      PLAY_FOREGROUND,
      Component.translatable(TranslationHelper.INSTANCE.translUpgradeButton("play"))
   );
   private static final TextureBlitData STOP_FOREGROUND = new TextureBlitData(
      GuiHelper.ICONS, new Position(1, 1), Dimension.SQUARE_256, new UV(0, 64), Dimension.SQUARE_16
   );
   private static final ButtonDefinition STOP = new ButtonDefinition(
      Dimension.SQUARE_18,
      GuiHelper.DEFAULT_BUTTON_BACKGROUND,
      GuiHelper.DEFAULT_BUTTON_HOVERED_BACKGROUND,
      STOP_FOREGROUND,
      Component.translatable(TranslationHelper.INSTANCE.translUpgradeButton("stop"))
   );
   private static final TextureBlitData SHUFFLE_ON_FOREGROUND = new TextureBlitData(
      GuiHelper.ICONS, new Position(1, 1), Dimension.SQUARE_256, new UV(96, 80), Dimension.SQUARE_16
   );
   private static final TextureBlitData SHUFFLE_OFF_FOREGROUND = new TextureBlitData(
      GuiHelper.ICONS, new Position(1, 1), Dimension.SQUARE_256, new UV(112, 80), Dimension.SQUARE_16
   );
   private static final ButtonDefinition.Toggle<Boolean> SHUFFLE = new ButtonDefinition.Toggle<>(
      Dimension.SQUARE_18,
      GuiHelper.DEFAULT_BUTTON_BACKGROUND,
      Map.of(
         true,
         new ToggleButton.StateData(SHUFFLE_ON_FOREGROUND, Component.translatable(TranslationHelper.INSTANCE.translUpgradeButton("shuffle_on"))),
         false,
         new ToggleButton.StateData(SHUFFLE_OFF_FOREGROUND, Component.translatable(TranslationHelper.INSTANCE.translUpgradeButton("shuffle_off")))
      ),
      GuiHelper.DEFAULT_BUTTON_HOVERED_BACKGROUND
   );
   private static final TextureBlitData REPEAT_ALL_FOREGROUND = new TextureBlitData(
      GuiHelper.ICONS, new Position(1, 1), Dimension.SQUARE_256, new UV(128, 80), Dimension.SQUARE_16
   );
   private static final TextureBlitData REPEAT_ONE_FOREGROUND = new TextureBlitData(
      GuiHelper.ICONS, new Position(1, 1), Dimension.SQUARE_256, new UV(144, 80), Dimension.SQUARE_16
   );
   private static final TextureBlitData NO_REPEAT_FOREGROUND = new TextureBlitData(
      GuiHelper.ICONS, new Position(1, 1), Dimension.SQUARE_256, new UV(160, 80), Dimension.SQUARE_16
   );
   private static final ButtonDefinition.Toggle<RepeatMode> REPEAT = new ButtonDefinition.Toggle<>(
      Dimension.SQUARE_18,
      GuiHelper.DEFAULT_BUTTON_BACKGROUND,
      Map.of(
         RepeatMode.ALL,
         new ToggleButton.StateData(REPEAT_ALL_FOREGROUND, Component.translatable(TranslationHelper.INSTANCE.translUpgradeButton("repeat_all"))),
         RepeatMode.ONE,
         new ToggleButton.StateData(REPEAT_ONE_FOREGROUND, Component.translatable(TranslationHelper.INSTANCE.translUpgradeButton("repeat_one"))),
         RepeatMode.NO,
         new ToggleButton.StateData(NO_REPEAT_FOREGROUND, Component.translatable(TranslationHelper.INSTANCE.translUpgradeButton("no_repeat")))
      ),
      GuiHelper.DEFAULT_BUTTON_HOVERED_BACKGROUND
   );
   private static final TextureBlitData PREVIOUS_FOREGROUND = new TextureBlitData(
      GuiHelper.ICONS, new Position(1, 1), Dimension.SQUARE_256, new UV(48, 96), Dimension.SQUARE_16
   );
   private static final ButtonDefinition PREVIOUS = new ButtonDefinition(
      Dimension.SQUARE_18,
      GuiHelper.DEFAULT_BUTTON_BACKGROUND,
      GuiHelper.DEFAULT_BUTTON_HOVERED_BACKGROUND,
      PREVIOUS_FOREGROUND,
      Component.translatable(TranslationHelper.INSTANCE.translUpgradeButton("previous_disc"))
   );
   private static final TextureBlitData NEXT_FOREGROUND = new TextureBlitData(
      GuiHelper.ICONS, new Position(1, 1), Dimension.SQUARE_256, new UV(32, 96), Dimension.SQUARE_16
   );
   private static final ButtonDefinition NEXT = new ButtonDefinition(
      Dimension.SQUARE_18,
      GuiHelper.DEFAULT_BUTTON_BACKGROUND,
      GuiHelper.DEFAULT_BUTTON_HOVERED_BACKGROUND,
      NEXT_FOREGROUND,
      Component.translatable(TranslationHelper.INSTANCE.translUpgradeButton("next_disc"))
   );
   private static final int BUTTON_PADDING = 3;
   public static final int TOP_Y = 24;
   private final int slotsInRow;

   public JukeboxUpgradeTab(
      JukeboxUpgradeContainer upgradeContainer, Position position, StorageScreenBase<?> screen, int slotsInRow, Component tabLabel, Component closedTooltip
   ) {
      super(upgradeContainer, position, screen, tabLabel, closedTooltip);
      this.slotsInRow = slotsInRow;
   }

   @Override
   protected void renderBg(GuiGraphics guiGraphics, Minecraft minecraft, int mouseX, int mouseY) {
      super.renderBg(guiGraphics, minecraft, mouseX, mouseY);
      if (this.getContainer().isOpen()) {
         GuiHelper.renderSlotsBackground(
            guiGraphics,
            this.x + 3,
            this.y + 24,
            this.slotsInRow,
            this.getContainer().getSlots().size() / this.slotsInRow,
            this.getContainer().getSlots().size() % this.slotsInRow
         );
      }
   }

   @Override
   protected void moveSlotsToTab() {
      int slotIndex = 0;

      for (Slot discSlot : this.getContainer().getSlots()) {
         discSlot.x = this.x - this.screen.getGuiLeft() + 4 + slotIndex % this.slotsInRow * 18;
         discSlot.y = this.y - this.screen.getGuiTop() + 24 + 1 + slotIndex / this.slotsInRow * 18;
         slotIndex++;
      }
   }

   protected int getBottomSlotY() {
      return 24 + this.getContainer().getSlots().size() / this.slotsInRow * 18 + (this.getContainer().getSlots().size() % this.slotsInRow > 0 ? 18 : 0);
   }

   public static class Advanced extends JukeboxUpgradeTab {
      public Advanced(JukeboxUpgradeContainer upgradeContainer, Position position, StorageScreenBase<?> screen, int slotsInRow) {
         super(
            upgradeContainer,
            position,
            screen,
            slotsInRow,
            TranslationHelper.INSTANCE.translUpgrade("advanced_jukebox"),
            TranslationHelper.INSTANCE.translUpgradeTooltip("advanced_jukebox")
         );
         int bottomSlotY = this.getBottomSlotY();
         this.addHideableChild(new Button(new Position(this.x + 3, this.y + bottomSlotY + 3), JukeboxUpgradeTab.PREVIOUS, button -> {
            if (button == 0) {
               this.getContainer().previous();
            }
         }));
         this.addHideableChild(new Button(new Position(this.x + 21, this.y + bottomSlotY + 3), JukeboxUpgradeTab.STOP, button -> {
            if (button == 0) {
               this.getContainer().stop();
            }
         }));
         this.addHideableChild(new Button(new Position(this.x + 39, this.y + bottomSlotY + 3), JukeboxUpgradeTab.PLAY, button -> {
            if (button == 0) {
               this.getContainer().play();
            }
         }));
         this.addHideableChild(new Button(new Position(this.x + 57, this.y + bottomSlotY + 3), JukeboxUpgradeTab.NEXT, button -> {
            if (button == 0) {
               this.getContainer().next();
            }
         }));
         this.addHideableChild(new ToggleButton<>(new Position(this.x + 12, this.y + bottomSlotY + 3 + 20), JukeboxUpgradeTab.SHUFFLE, button -> {
            if (button == 0) {
               this.getContainer().toggleShuffle();
            }
         }, () -> this.getContainer().isShuffleEnabled()));
         this.addHideableChild(new ToggleButton<>(new Position(this.x + 48, this.y + bottomSlotY + 3 + 20), JukeboxUpgradeTab.REPEAT, button -> {
            if (button == 0) {
               this.getContainer().toggleRepeat();
            }
         }, () -> this.getContainer().getRepeatMode()));
      }

      @Override
      protected void renderBg(GuiGraphics guiGraphics, Minecraft minecraft, int mouseX, int mouseY) {
         super.renderBg(guiGraphics, minecraft, mouseX, mouseY);
         this.getContainer()
            .getDiscSlotActive()
            .ifPresent(slot -> this.renderPlaytimeOverLay(guiGraphics, 1426115584, this.screen.getLeftX() + slot.x, this.screen.getTopY() + slot.y, 16, 16));
      }

      private float getPlaybackRemainingProgress() {
         long finishTime = this.getContainer().getDiscFinishTime();
         int remaining = (int)(finishTime - this.minecraft.level.getGameTime());
         ItemStack disc = this.getContainer().getUpgradeWrapper().getDisc();
         return DiscHandlerRegistry.getMusicLengthInTicks(disc, this.minecraft.level)
            .map(lengthInTicks -> (float)remaining / lengthInTicks.intValue())
            .orElse(0.0F);
      }

      private void renderPlaytimeOverLay(GuiGraphics guiGraphics, int slotColor, int xPos, int yPos, int width, int height) {
         float remainingProgress = this.getPlaybackRemainingProgress();
         if (!(remainingProgress <= 0.0F)) {
            int progressOver = width - (int)(width * remainingProgress);
            RenderSystem.disableDepthTest();
            RenderSystem.colorMask(true, true, true, false);
            guiGraphics.fillGradient(xPos + progressOver, yPos, xPos + width, yPos + height, 0, slotColor, slotColor);
            RenderSystem.colorMask(true, true, true, true);
            RenderSystem.enableDepthTest();
         }
      }
   }

   public static class Basic extends JukeboxUpgradeTab {
      public Basic(JukeboxUpgradeContainer upgradeContainer, Position position, StorageScreenBase<?> screen) {
         super(
            upgradeContainer,
            position,
            screen,
            4,
            TranslationHelper.INSTANCE.translUpgrade("jukebox"),
            TranslationHelper.INSTANCE.translUpgradeTooltip("jukebox")
         );
         int bottomSlotY = this.getBottomSlotY();
         this.addHideableChild(new Button(new Position(this.x + 3, this.y + bottomSlotY + 3), JukeboxUpgradeTab.STOP, button -> {
            if (button == 0) {
               this.getContainer().stop();
            }
         }));
         this.addHideableChild(new Button(new Position(this.x + 21, this.y + bottomSlotY + 3), JukeboxUpgradeTab.PLAY, button -> {
            if (button == 0) {
               this.getContainer().play();
            }
         }));
      }
   }
}
