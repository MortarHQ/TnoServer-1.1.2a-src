package dev.ftb.mods.ftbchunks.client.gui;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;
import dev.ftb.mods.ftbchunks.api.client.icon.MapType;
import dev.ftb.mods.ftbchunks.client.FTBChunksClient;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import dev.ftb.mods.ftbchunks.client.map.MapChunk;
import dev.ftb.mods.ftbchunks.client.map.MapManager;
import dev.ftb.mods.ftbchunks.client.map.RenderMapImageTask;
import dev.ftb.mods.ftbchunks.net.RequestChunkChangePacket;
import dev.ftb.mods.ftbchunks.net.RequestMapDataPacket;
import dev.ftb.mods.ftbchunks.net.UpdateForceLoadExpiryPacket;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.icon.FaceIcon;
import dev.ftb.mods.ftblibrary.icon.ImageIcon;
import dev.ftb.mods.ftblibrary.math.MathUtils;
import dev.ftb.mods.ftblibrary.math.XZ;
import dev.ftb.mods.ftblibrary.ui.Button;
import dev.ftb.mods.ftblibrary.ui.GuiHelper;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.ScreenWrapper;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.util.TimeUtils;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import dev.ftb.mods.ftbteams.api.Team;
import dev.ftb.mods.ftbteams.api.property.TeamProperties;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;

public class ChunkScreenPanel extends Panel {
   private static final ImageIcon FORCE_LOAD_ICON = new ImageIcon(FTBChunksAPI.rl("textures/force_loaded.png"));
   private static final ImageIcon CHECKERED_ICON = new ImageIcon(FTBChunksAPI.rl("textures/checkered.png"));
   private final List<ChunkScreenPanel.ChunkButton> chunkButtons = new ArrayList<>();
   private XZ firstSelectedChunk = null;
   private final Set<XZ> selectedChunks = new HashSet<>();
   private final List<ChunkScreenPanel.ChunkButtonPos> chunkedPosList = new ArrayList<>();
   public boolean isAdminEnabled;
   private ChunkScreenPanel.ChunkUpdateInfo updateInfo;
   public int tileSizeX = 16;
   public int tileSizeY = 16;
   private final ChunkScreen chunkScreen;
   private Button lastButtonDragged = null;

   public ChunkScreenPanel(ChunkScreen panel) {
      super(panel);
      this.chunkScreen = panel;
      RenderMapImageTask.setAlwaysRenderChunksOnMap(true);
      this.isAdminEnabled = Minecraft.getInstance().isSingleplayer();
      MapManager.getInstance().ifPresent(m -> m.updateAllRegions(false));
      this.alignWidgets();
   }

   public static void notifyChunkUpdates(int totalChunks, int changedChunks, Map<String, Integer> problems) {
      if (Minecraft.getInstance().screen instanceof ScreenWrapper sw && sw.getGui() instanceof ChunkScreen cs) {
         cs.getChunkScreen().updateInfo = new ChunkScreenPanel.ChunkUpdateInfo(
            totalChunks, changedChunks, problems, Minecraft.getInstance().level.getGameTime()
         );
      }
   }

   public void onClosed() {
      RenderMapImageTask.setAlwaysRenderChunksOnMap(false);
      MapManager.getInstance().ifPresent(m -> m.updateAllRegions(false));
      super.onClosed();
   }

   public void addWidgets() {
      Player player = Minecraft.getInstance().player;
      ChunkPos chunkPos = player.chunkPosition();
      int startX = chunkPos.x - 7;
      int startZ = chunkPos.z - 7;
      this.chunkButtons.clear();

      for (int z = 0; z < 15; z++) {
         for (int x = 0; x < 15; x++) {
            ChunkScreenPanel.ChunkButton button = new ChunkScreenPanel.ChunkButton(this, XZ.of(startX + x, startZ + z));
            this.chunkButtons.add(button);
            this.chunkedPosList.add(new ChunkScreenPanel.ChunkButtonPos(button, x, z));
         }
      }

      this.addAll(this.chunkButtons);
      NetworkManager.sendToServer(new RequestMapDataPacket(chunkPos.x - 7, chunkPos.z - 7, chunkPos.x + 7, chunkPos.z + 7));
   }

   public void alignWidgets() {
      int maxWidth = this.getWidth() / 15 * 15;
      int maxHeight = this.getHeight() / 15 * 15;
      int xPos = (this.getWidth() - maxWidth) / 2;
      int yPos = (this.getHeight() - maxHeight) / 2;
      this.tileSizeX = maxWidth / 15;
      this.tileSizeY = maxHeight / 15;

      for (ChunkScreenPanel.ChunkButtonPos chunkedPos : this.chunkedPosList) {
         chunkedPos.button.setPos(xPos + this.tileSizeX * chunkedPos.x, yPos + this.tileSizeY * chunkedPos.y);
         chunkedPos.button.setSize(this.tileSizeX, this.tileSizeY);
      }
   }

   public void mouseReleased(MouseButton button) {
      super.mouseReleased(button);
      if (!this.selectedChunks.isEmpty()) {
         Optional<UUID> teamId = Optional.ofNullable(this.chunkScreen.getOpenedAs()).map(Team::getTeamId);
         NetworkManager.sendToServer(
            new RequestChunkChangePacket(
               RequestChunkChangePacket.ChunkChangeOp.create(button.isLeft(), isShiftKeyDown()), this.selectedChunks, this.canChangeAsAdmin(), teamId
            )
         );
         this.selectedChunks.clear();
         this.firstSelectedChunk = null;
         this.lastButtonDragged = null;
         this.playClickSound();
      }
   }

   public void removeAllClaims() {
      Optional<UUID> teamId = Optional.ofNullable(this.chunkScreen.getOpenedAs()).map(Team::getTeamId);
      Set<XZ> allChunks = this.chunkedPosList
         .stream()
         .map(ChunkScreenPanel.ChunkButtonPos::button)
         .map(ChunkScreenPanel.ChunkButton::getChunkPos)
         .collect(Collectors.toSet());
      NetworkManager.sendToServer(new RequestChunkChangePacket(RequestChunkChangePacket.ChunkChangeOp.UNCLAIM, allChunks, this.canChangeAsAdmin(), teamId));
   }

   public void drawBackground(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
      Player player = Minecraft.getInstance().player;
      int maxWidth = this.getWidth() / 15 * 15;
      int maxHeight = this.getHeight() / 15 * 15;
      int xPos = (this.getWidth() - maxWidth) / 2;
      int yPos = (this.getHeight() - maxHeight) / 2;
      int sx = this.getX() + xPos;
      int sy = this.getY() + yPos;
      RenderSystem.setShaderTexture(0, FTBChunksClient.INSTANCE.getMinimapTextureId());
      GuiHelper.drawTexturedRect(graphics, sx, sy, maxWidth, maxHeight, Color4I.WHITE, 0.0F, 0.0F, 1.0F, 1.0F);
      if (!InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 258)) {
         for (int gy = 1; gy < 15; gy++) {
            graphics.hLine(sx, sx + maxWidth - 1, sy + gy * this.tileSizeY, 1682327110);
         }

         for (int gx = 1; gx < 15; gx++) {
            graphics.vLine(sx + gx * this.tileSizeX, sy - 1, sy + maxHeight, 1682327110);
         }
      }

      double hx = sx + this.tileSizeX * 7 + MathUtils.mod(player.getX(), 16.0);
      double hy = sy + this.tileSizeY * 7 + MathUtils.mod(player.getZ(), 16.0);
      new PointerIcon().draw(MapType.LARGE_MAP, graphics, (int)(hx - 4.0), (int)(hy - 4.0), 8, 8, false, 255);
      FaceIcon.getFace(player.getGameProfile()).draw(graphics, (int)(hx - 4.0), (int)(hy - 4.0), 8, 8);
      if (this.updateInfo != null && this.updateInfo.shouldDisplay()) {
         theme.drawString(graphics, this.updateInfo.summary(), sx + 2, sy + 2, 2);
         int line = 1;

         for (Entry<String, Integer> entry : this.updateInfo.problems.entrySet()) {
            MutableComponent problem = Component.translatable(entry.getKey());
            int count = entry.getValue();
            theme.drawString(graphics, problem.append(": " + count), sx + 2, sy + 5 + theme.getFontHeight() * line++, 2);
         }
      }
   }

   private boolean canChangeAsAdmin() {
      return Minecraft.getInstance().player.hasPermissions(2) && this.chunkScreen.getOpenedAs() == null && this.isAdminEnabled;
   }

   private class ChunkButton extends Button {
      private final XZ chunkPos;
      private final MapChunk chunk;
      private long lastAdjust = 0L;

      public ChunkButton(Panel panel, XZ xz) {
         super(panel, Component.empty(), Color4I.empty());
         this.setSize(16, 16);
         this.chunkPos = xz;
         this.chunk = ChunkScreenPanel.this.chunkScreen
            .getDimension()
            .getRegion(XZ.regionFromChunk(this.chunkPos.x(), this.chunkPos.z()))
            .getDataBlocking()
            .getChunk(this.chunkPos);
      }

      public void onClicked(MouseButton mouseButton) {
         if (ChunkScreenPanel.this.selectedChunks.isEmpty()) {
            ChunkScreenPanel.this.firstSelectedChunk = this.chunkPos;
         }

         ChunkScreenPanel.this.selectedChunks.add(this.chunkPos);
      }

      public void drawBackground(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
         if (this.chunk.getForceLoadedDate().isPresent() && !InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 258)) {
            this.chunk.getTeam().ifPresent(team -> {
               Color4I teamColor = (Color4I)team.getProperties().get(TeamProperties.COLOR);
               float[] hsb = Color4I.RGBtoHSB(teamColor.redi(), teamColor.greeni(), teamColor.bluei(), null);
               hsb[0] = (hsb[0] + 0.5F) % 1.0F;
               ChunkScreenPanel.FORCE_LOAD_ICON.withColor(Color4I.hsb(hsb[0], hsb[1], hsb[2])).draw(graphics, x, y, w, h);
            });
         }

         if (this.isMouseOver() || ChunkScreenPanel.this.selectedChunks.contains(this.chunkPos)) {
            Color4I.WHITE.withAlpha(100).draw(graphics, x, y, w, h);
            ChunkScreenPanel.CHECKERED_ICON.withColor(Color4I.GRAY.withAlpha(150)).draw(graphics, x, y, w, h);
         }
      }

      public boolean mouseDragged(int button, double dragX, double dragY) {
         if (this.isMouseOver() && (isMouseButtonDown(MouseButton.LEFT) || isMouseButtonDown(MouseButton.RIGHT))) {
            if (FTBChunksClientConfig.CLAIM_MODE.get() == GuiClaimMode.FREEHAND || ChunkScreenPanel.this.firstSelectedChunk == null) {
               ChunkScreenPanel.this.selectedChunks.add(this.chunkPos);
            } else if (ChunkScreenPanel.this.lastButtonDragged != this) {
               this.addChunksToSelection();
            }

            ChunkScreenPanel.this.lastButtonDragged = this;
         }

         return super.mouseDragged(button, dragX, dragY);
      }

      private void addChunksToSelection() {
         int x1 = Math.min(ChunkScreenPanel.this.firstSelectedChunk.x(), this.chunkPos.x());
         int x2 = Math.max(ChunkScreenPanel.this.firstSelectedChunk.x(), this.chunkPos.x());
         int z1 = Math.min(ChunkScreenPanel.this.firstSelectedChunk.z(), this.chunkPos.z());
         int z2 = Math.max(ChunkScreenPanel.this.firstSelectedChunk.z(), this.chunkPos.z());
         XZ centre = new XZ((x1 + x2) / 2, (z1 + z2) / 2);
         ChunkScreenPanel.this.selectedChunks.clear();

         for (int x = x1; x <= x2; x++) {
            for (int z = z1; z <= z2; z++) {
               XZ xz = new XZ(x, z);
               if (FTBChunksClientConfig.CLAIM_MODE.get() == GuiClaimMode.RECTANGLE
                  || this.distance(xz, centre) - 0.5F <= Math.min(centre.x() - x1, centre.z() - z1)) {
                  ChunkScreenPanel.this.selectedChunks.add(xz);
               }
            }
         }
      }

      private float distance(XZ xz1, XZ xz2) {
         return Mth.sqrt((xz2.x() - xz1.x()) * (xz2.x() - xz1.x()) + (xz2.z() - xz1.z()) * (xz2.z() - xz1.z()));
      }

      public void addMouseOverText(TooltipList list) {
         if (this.chunk != null) {
            this.chunk
               .getTeam()
               .ifPresent(
                  team -> {
                     list.add(team.getName().copy().withStyle(ChatFormatting.WHITE));
                     Date date = new Date();
                     boolean altPressed = Screen.hasAltDown();
                     this.chunk
                        .getClaimedDate()
                        .ifPresent(
                           claimedDate -> {
                              if (altPressed) {
                                 list.add(Component.literal(claimedDate.toLocaleString()).withStyle(ChatFormatting.GRAY));
                              } else {
                                 list.add(
                                    Component.literal(TimeUtils.prettyTimeString((date.getTime() - claimedDate.getTime()) / 1000L) + " ago")
                                       .withStyle(ChatFormatting.GRAY)
                                 );
                              }
                           }
                        );
                     this.chunk
                        .getForceLoadedDate()
                        .ifPresent(
                           forceLoadedDate -> {
                              list.add(Component.translatable("ftbchunks.gui.force_loaded").withStyle(ChatFormatting.YELLOW));
                              String loadStr = altPressed
                                 ? "  " + forceLoadedDate.toLocaleString()
                                 : "  " + TimeUtils.prettyTimeString((date.getTime() - forceLoadedDate.getTime()) / 1000L) + " ago";
                              list.add(Component.literal(loadStr).withStyle(ChatFormatting.GRAY));
                              this.chunk
                                 .getForceLoadExpiryDate()
                                 .ifPresent(
                                    expiryDate -> {
                                       list.add(Component.translatable("ftbchunks.gui.force_load_expires").withStyle(ChatFormatting.GOLD));
                                       String expireStr = altPressed
                                          ? "  " + expiryDate.toLocaleString()
                                          : "  " + TimeUtils.prettyTimeString((expiryDate.getTime() - date.getTime()) / 1000L) + " from now";
                                       list.add(Component.literal(expireStr).withStyle(ChatFormatting.GRAY));
                                    }
                                 );
                              if (!altPressed) {
                                 list.add(Component.translatable("ftbchunks.gui.hold_alt_for_dates").withStyle(ChatFormatting.DARK_GRAY));
                              }

                              if (team.getRankForPlayer(Minecraft.getInstance().player.getUUID()).isMemberOrBetter()) {
                                 list.add(Component.translatable("ftbchunks.gui.mouse_wheel_expiry").withStyle(ChatFormatting.DARK_GRAY));
                              }
                           }
                        );
                     if (!altPressed && (this.chunk.getClaimedDate().isPresent() || this.chunk.getForceLoadedDate().isPresent())) {
                        list.add(Component.translatable("ftbchunks.gui.hold_alt_for_dates").withStyle(ChatFormatting.DARK_GRAY));
                     }
                  }
               );
         }
      }

      public boolean mouseScrolled(double scroll) {
         return this.chunk.getForceLoadedDate().map(forceLoadedDate -> {
            LocalPlayer player = Minecraft.getInstance().player;
            boolean teamMember = this.chunk.isTeamMember(player);
            if (!this.isMouseOver || !ChunkScreenPanel.this.canChangeAsAdmin() && !teamMember) {
               return super.mouseScrolled(scroll);
            }

            int dir = (int)Math.signum(scroll);
            long now = System.currentTimeMillis();
            Date expiry = this.chunk.getForceLoadExpiryDate().orElse(new Date(now));
            long offset = calcOffset(expiry, now, dir);
            this.chunk.updateForceLoadExpiryDate(now, offset * 1000L);
            this.lastAdjust = now;
            return true;
         }).orElse(super.mouseScrolled(scroll));
      }

      private static long calcOffset(Date expiry, long now, int dir) {
         long offset = (expiry.getTime() - now) / 1000L;
         if (dir == 1) {
            if (offset < 86400L) {
               offset += 3600L;
            } else if (offset < 604800L) {
               offset += 86400L;
            } else {
               offset += 604800L;
            }
         } else if (dir == -1) {
            if (offset <= 86400L) {
               offset = Math.max(0L, offset - 3600L);
            } else if (offset <= 604800L) {
               offset = Math.max(86400L, offset - 86400L);
            } else {
               offset = Math.max(604800L, offset - 604800L);
            }
         }

         return offset;
      }

      public void tick() {
         super.tick();
         if (this.lastAdjust > 0L && System.currentTimeMillis() - this.lastAdjust > 1000L) {
            NetworkManager.sendToServer(
               new UpdateForceLoadExpiryPacket(this.chunkPos.dim(Minecraft.getInstance().level), this.chunk.getForceLoadExpiryDate().orElse(null))
            );
            this.lastAdjust = 0L;
         }
      }

      public XZ getChunkPos() {
         return this.chunkPos;
      }
   }

   private record ChunkButtonPos(ChunkScreenPanel.ChunkButton button, int x, int y) {
   }

   public record ChunkUpdateInfo(int totalChunks, int changedChunks, Map<String, Integer> problems, long timestamp) {
      public boolean shouldDisplay() {
         long timeout = 60L + 20L * this.problems.size();
         return Minecraft.getInstance().level.getGameTime() - this.timestamp < timeout;
      }

      public Component summary() {
         ChatFormatting color = this.changedChunks == 0
            ? ChatFormatting.RED
            : (this.changedChunks < this.totalChunks ? ChatFormatting.YELLOW : ChatFormatting.GREEN);
         return Component.translatable("ftbchunks.claim_result", new Object[]{this.changedChunks, this.totalChunks})
            .withStyle(new ChatFormatting[]{ChatFormatting.UNDERLINE, color});
      }
   }
}
