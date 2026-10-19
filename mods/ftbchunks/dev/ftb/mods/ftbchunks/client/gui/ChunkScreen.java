package dev.ftb.mods.ftbchunks.client.gui;

import dev.ftb.mods.ftbchunks.FTBChunks;
import dev.ftb.mods.ftbchunks.FTBChunksWorldConfig;
import dev.ftb.mods.ftbchunks.client.FTBChunksClient;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import dev.ftb.mods.ftbchunks.client.map.MapDimension;
import dev.ftb.mods.ftbchunks.net.SendGeneralDataPacket;
import dev.ftb.mods.ftblibrary.config.manager.ConfigManagerClient;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.ui.Button;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.SimpleButton;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.ToggleableButton;
import dev.ftb.mods.ftblibrary.ui.input.Key;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.ui.misc.AbstractThreePanelScreen;
import dev.ftb.mods.ftblibrary.ui.misc.KeyReferenceScreen;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import dev.ftb.mods.ftbteams.api.Team;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.Nullable;

public class ChunkScreen extends AbstractThreePanelScreen<ChunkScreenPanel> {
   private final MapDimension dimension;
   private final Team openedAs;
   private ChunkScreenPanel chunkScreenPanel;
   private final SimpleButton largeMapButton;
   private Button settingsButton;
   private Button serverSettingsButton;

   private ChunkScreen(MapDimension dimension, @Nullable Team openedAs) {
      this.dimension = dimension;
      this.openedAs = openedAs;
      this.showCloseButton(true);
      this.showScrollBar(false);
      this.largeMapButton = new SimpleButton(this, Component.translatable("ftbchunks.gui.large_map"), Icons.MAP, (btn, mb) -> LargeMapScreen.openMap());
   }

   protected Panel createBottomPanel() {
      return new ChunkScreen.CustomBottomPanel();
   }

   protected int getScrollbarWidth() {
      return -1;
   }

   public boolean onInit() {
      int size = (int)(this.getWindow().getGuiScaledHeight() * 0.85F);
      this.setWidth(Math.min(size + 2, this.getWindow().getGuiScaledWidth() - 2));
      this.setHeight(Math.min(size + this.getTopPanelHeight() + this.getBottomPanelHeight(), this.getWindow().getGuiScaledHeight() - 2));
      return true;
   }

   public void addWidgets() {
      super.addWidgets();
      if (FTBChunksWorldConfig.playerHasMapStage(Minecraft.getInstance().player)) {
         this.add(this.largeMapButton);
      }

      this.add(
         this.settingsButton = new LargeMapScreen.SimpleTooltipButton(
            this,
            Component.translatable("ftbchunks.config.client"),
            Icons.SETTINGS,
            (b, m) -> ConfigManagerClient.editConfig("ftbchunks-client"),
            Component.literal("[S]").withStyle(ChatFormatting.GRAY)
         )
      );
      boolean adminPlayer = Minecraft.getInstance().player.hasPermissions(2);
      this.add(
         this.serverSettingsButton = new LargeMapScreen.SimpleTooltipButton(
            this,
            Component.translatable("ftbchunks.gui.settings.server"),
            Icons.SETTINGS.withTint(Color4I.rgb(10502399)),
            (b, m) -> ConfigManagerClient.editConfig("ftbchunks-world", !adminPlayer),
            Component.literal("[Ctrl + S]").withStyle(ChatFormatting.GRAY)
         )
      );
   }

   public void alignWidgets() {
      super.alignWidgets();
      this.largeMapButton.setPosAndSize(-this.getX() + 2, -this.getY() + 2, 16, 16);
      this.settingsButton.setPosAndSize(-this.getX() + 2, -this.getY() + 20, 16, 16);
      if (this.serverSettingsButton != null) {
         this.serverSettingsButton.setPosAndSize(-this.getX() + 2, -this.getY() + 38, 16, 16);
      }
   }

   public boolean keyPressed(Key key) {
      if (key.is(77) || key.is(67)) {
         return LargeMapScreen.openMap();
      }

      if (key.is(83)) {
         if (Screen.hasControlDown()) {
            if (this.serverSettingsButton != null) {
               this.serverSettingsButton.onClicked(MouseButton.LEFT);
            }
         } else {
            this.settingsButton.onClicked(MouseButton.LEFT);
         }

         return true;
      } else {
         return super.keyPressed(key);
      }
   }

   public static void openChunkScreen(@Nullable Team openedAs) {
      MapDimension.getCurrent()
         .ifPresentOrElse(
            mapDimension -> new ChunkScreen(mapDimension, openedAs).openGui(),
            () -> FTBChunks.LOGGER.warn("MapDimension data missing?? not opening chunk screen")
         );
   }

   public static void openChunkScreen() {
      openChunkScreen(null);
   }

   public ChunkScreenPanel getChunkScreen() {
      return this.chunkScreenPanel;
   }

   protected void doCancel() {
      this.closeGui();
   }

   protected void doAccept() {
   }

   protected int getTopPanelHeight() {
      return 16;
   }

   protected int getBottomPanelHeight() {
      return this.openedAs == null ? super.getBottomPanelHeight() - 8 : super.getBottomPanelHeight();
   }

   protected ChunkScreenPanel createMainPanel() {
      this.chunkScreenPanel = new ChunkScreenPanel(this);
      return this.chunkScreenPanel;
   }

   protected Panel createTopPanel() {
      return new ChunkScreen.CustomTopPanel();
   }

   public Team getOpenedAs() {
      return this.openedAs;
   }

   public MapDimension getDimension() {
      return this.dimension;
   }

   private static class ChunkMouseReferenceScreen extends KeyReferenceScreen {
      public ChunkMouseReferenceScreen() {
         super(new String[]{"ftbchunks.gui.chunk_info.text"});
      }

      public Component getTitle() {
         return Component.translatable("ftbchunks.gui.chunk_info");
      }
   }

   private class CustomBottomPanel extends Panel {
      public CustomBottomPanel() {
         super(ChunkScreen.this);
      }

      public void addWidgets() {
      }

      public void alignWidgets() {
      }

      public void drawBackground(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
         theme.drawPanelBackground(graphics, x, y, w, h);
         Color4I.GRAY.withAlpha(64).draw(graphics, x, y, w, 1);
         SendGeneralDataPacket.GeneralChunkData generalChunkData = FTBChunksClient.INSTANCE.getGeneralChunkData();
         int claimed = generalChunkData.claimed();
         int maxClaim = generalChunkData.maxClaimChunks();
         MutableComponent claimedComponent = Component.translatable("ftbchunks.gui.claimed")
            .append(
               Component.literal(": ")
                  .append(
                     Component.literal(claimed + " / " + maxClaim)
                        .withStyle(claimed > maxClaim ? ChatFormatting.RED : (claimed == maxClaim ? ChatFormatting.YELLOW : ChatFormatting.GREEN))
                  )
            );
         theme.drawString(graphics, claimedComponent, x + 4, y + 4);
         int loaded = generalChunkData.loaded();
         int maxLoaded = generalChunkData.maxForceLoadChunks();
         MutableComponent forceLoadComponent = Component.translatable("ftbchunks.gui.force_loaded")
            .append(Component.literal(": "))
            .append(
               Component.literal(loaded + " / " + maxLoaded)
                  .withStyle(loaded > maxLoaded ? ChatFormatting.RED : (loaded == maxLoaded ? ChatFormatting.YELLOW : ChatFormatting.GREEN))
            );
         String forceLoadText = forceLoadComponent.getString();
         int forceLoadX = x + w - theme.getStringWidth(forceLoadText) - 2;
         theme.drawString(graphics, forceLoadComponent, forceLoadX, y + 4);
         if (ChunkScreen.this.openedAs != null) {
            String openAsMessage = Component.translatable("ftbchunks.gui.opened_as", new Object[]{ChunkScreen.this.openedAs.getName()}).getString();
            int openAsX = x + w - theme.getStringWidth(openAsMessage) - 2;
            theme.drawString(graphics, openAsMessage, openAsX, y + h - theme.getFontHeight(), Color4I.WHITE, 2);
         }
      }
   }

   protected class CustomTopPanel extends Panel {
      private final Button closeButton = new SimpleButton(this, Component.translatable("gui.close"), Icons.CANCEL, (btn, mb) -> ChunkScreen.this.doCancel())
         .setForceButtonSize(false);
      private final Button removeAllClaims;
      private final Button adminButton;
      private final Button mouseReferenceButton = new SimpleButton(
            this, Component.translatable("ftbchunks.gui.chunk_info"), Icons.INFO, (btn, mb) -> new ChunkScreen.ChunkMouseReferenceScreen().openGui()
         )
         .setForceButtonSize(false);
      private final Button claimModeButton;

      public CustomTopPanel() {
         super(ChunkScreen.this);
         this.removeAllClaims = new SimpleButton(
               this,
               Component.translatable("ftbchunks.gui.unclaim_all"),
               Icons.BIN,
               (btn, mb) -> {
                  if (isShiftKeyDown()) {
                     ChunkScreen.this.chunkScreenPanel.removeAllClaims();
                  } else {
                     this.getGui()
                        .openYesNo(
                           Component.translatable("ftbchunks.gui.unclaim_all"),
                           Component.translatable("ftbchunks.gui.unclaim_all.description"),
                           () -> ChunkScreen.this.chunkScreenPanel.removeAllClaims()
                        );
                  }
               }
            )
            .setForceButtonSize(false);
         this.adminButton = new ChunkScreen.CustomTopPanel.AdminButton().setForceButtonSize(false);
         this.claimModeButton = new ChunkScreen.CustomTopPanel.ClaimModeButton();
      }

      public void addWidgets() {
         this.add(this.closeButton);
         this.add(this.removeAllClaims);
         if (!Minecraft.getInstance().isSingleplayer() && ChunkScreen.this.openedAs == null && Minecraft.getInstance().player.hasPermissions(2)) {
            this.add(this.adminButton);
         }

         this.add(this.mouseReferenceButton);
         this.add(this.claimModeButton);
      }

      public void alignWidgets() {
         this.removeAllClaims.setPosAndSize(2, 2, 12, 12);
         this.adminButton.setPosAndSize(18, 1, 12, 12);
         this.closeButton.setPosAndSize(this.width - 16, 2, 12, 12);
         this.mouseReferenceButton.setPosAndSize(this.width - 32, 2, 12, 12);
         this.claimModeButton.setPosAndSize(this.width - 48, 2, 12, 12);
      }

      public void drawBackground(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
         theme.drawPanelBackground(graphics, x, y, w, h);
         Color4I.BLACK.withAlpha(80).draw(graphics, x, y + h - 1, w, 1);
      }

      private class AdminButton extends ToggleableButton {
         private static final Component DISABLED = Component.translatable("ftbchunks.gui.admin_mode_disabled");
         private static final Component ENABLED = Component.translatable("ftbchunks.gui.admin_mode_enabled");
         private static final Component MORE_INFO = Component.translatable("ftbchunks.gui.admin_mode_info").withStyle(ChatFormatting.GRAY);

         public AdminButton() {
            super(CustomTopPanel.this, false, Icons.LOCK_OPEN, Icons.LOCK, (btn, newState) -> ChunkScreen.this.getChunkScreen().isAdminEnabled = newState);
            this.setEnabledText(ENABLED);
            this.setDisabledText(DISABLED);
         }

         public void addMouseOverText(TooltipList list) {
            super.addMouseOverText(list);
            list.add(MORE_INFO);
         }
      }

      private class ClaimModeButton extends SimpleButton {
         public ClaimModeButton() {
            super(
               CustomTopPanel.this,
               ((GuiClaimMode)FTBChunksClientConfig.CLAIM_MODE.get()).description(),
               ((GuiClaimMode)FTBChunksClientConfig.CLAIM_MODE.get()).icon(),
               (widget, button) -> {
                  GuiClaimMode newClaimMode = ((GuiClaimMode)FTBChunksClientConfig.CLAIM_MODE.get()).next();
                  widget.setIcon(newClaimMode.icon());
                  widget.setTitle(newClaimMode.description());
                  FTBChunksClientConfig.CLAIM_MODE.set(newClaimMode);
               }
            );
         }
      }
   }
}
