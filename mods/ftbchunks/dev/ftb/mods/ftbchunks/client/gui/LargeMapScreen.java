package dev.ftb.mods.ftbchunks.client.gui;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.networking.NetworkManager;
import dev.architectury.platform.Platform;
import dev.ftb.mods.ftbchunks.FTBChunks;
import dev.ftb.mods.ftbchunks.FTBChunksWorldConfig;
import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;
import dev.ftb.mods.ftbchunks.client.FTBChunksClient;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import dev.ftb.mods.ftbchunks.client.map.MapDimension;
import dev.ftb.mods.ftbchunks.client.map.MapManager;
import dev.ftb.mods.ftbchunks.client.map.MapRegion;
import dev.ftb.mods.ftbchunks.client.map.MapRegionData;
import dev.ftb.mods.ftbchunks.client.map.WaypointImpl;
import dev.ftb.mods.ftbchunks.client.map.WaypointManagerImpl;
import dev.ftb.mods.ftbchunks.client.map.WaypointType;
import dev.ftb.mods.ftbchunks.net.TeleportFromMapPacket;
import dev.ftb.mods.ftblibrary.config.ColorConfig;
import dev.ftb.mods.ftblibrary.config.StringConfig;
import dev.ftb.mods.ftblibrary.config.ui.EditStringConfigOverlay;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.math.MathUtils;
import dev.ftb.mods.ftblibrary.math.XZ;
import dev.ftb.mods.ftblibrary.ui.BaseScreen;
import dev.ftb.mods.ftblibrary.ui.ContextMenuItem;
import dev.ftb.mods.ftblibrary.ui.NordTheme;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.ScreenWrapper;
import dev.ftb.mods.ftblibrary.ui.SimpleButton;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.SimpleButton.Callback;
import dev.ftb.mods.ftblibrary.ui.input.Key;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.ui.misc.KeyReferenceScreen;
import dev.ftb.mods.ftblibrary.util.StringUtils;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class LargeMapScreen extends BaseScreen {
   private static final Color4I BACKGROUND_COLOR = Color4I.rgb(2105893);
   private static final Icon MINIMAP_INFO = Icon.getIcon(FTBChunksAPI.rl("textures/minimap_info.png"));
   private final RegionMapPanel regionPanel;
   private final MapScreenButtonPanel buttonPanel;
   private int zoom = 256;
   MapDimension dimension;
   int scrollWidth = 0;
   int scrollHeight = 0;
   int prevMouseX;
   int prevMouseY;
   int grabbed = 0;
   private boolean movedToPlayer = false;
   private boolean needIconRefresh;
   private final int minZoom;

   private LargeMapScreen(MapDimension dim) {
      this.regionPanel = new RegionMapPanel(this);
      this.dimension = dim;
      this.regionPanel.setScrollX(0.0);
      this.regionPanel.setScrollY(0.0);
      this.buttonPanel = new MapScreenButtonPanel(this);
      this.minZoom = this.determineMinZoom();
   }

   public static boolean openMap() {
      if (FTBChunksWorldConfig.playerHasMapStage(Minecraft.getInstance().player)) {
         MapDimension.getCurrent()
            .ifPresentOrElse(
               mapDimension -> new LargeMapScreen(mapDimension).openGui(), () -> FTBChunks.LOGGER.warn("Missing MapDimension data?? not opening large map")
            );
         return true;
      } else {
         return false;
      }
   }

   public Theme getTheme() {
      return NordTheme.THEME;
   }

   public void onClosed() {
      super.onClosed();
      int autoRelease = (Integer)FTBChunksClientConfig.AUTORELEASE_ON_MAP_CLOSE.get();
      if (autoRelease > 0 && this.dimension != null) {
         this.dimension.getManager().scheduleRegionPurge(this.dimension);
      }
   }

   public ResourceKey<Level> currentDimension() {
      return this.dimension.dimension;
   }

   public int getRegionTileSize() {
      return this.zoom * 2;
   }

   public void addZoom(double up) {
      int prevZoom = this.zoom;
      if (up > 0.0) {
         this.zoom *= 2;
      } else {
         this.zoom /= 2;
      }

      this.zoom = Mth.clamp(this.zoom, this.minZoom, 1024);
      if (this.zoom != prevZoom) {
         this.grabbed = 0;
         double sx = this.regionPanel.regionX;
         double sy = this.regionPanel.regionZ;
         this.regionPanel.resetScroll();
         this.regionPanel.scrollTo(sx, sy);
         Minecraft.getInstance().mouseHandler.mouseGrabbed = true;
         Minecraft.getInstance().mouseHandler.releaseMouse();
      }
   }

   public void addWidgets() {
      this.add(this.regionPanel);
      this.add(this.buttonPanel);
   }

   WaypointManagerImpl getWaypointManager() {
      return MapManager.getInstance().orElseThrow().getDimension(this.dimension.dimension).getWaypointManager();
   }

   void switchToDimension(ResourceKey<Level> key) {
      this.dimension = this.dimension.getManager().getDimension(key);
      this.refreshWidgets();
      this.movedToPlayer = false;
   }

   private void cycleVisibleDimension(MouseButton m) {
      try {
         List<MapDimension> list = new ArrayList<>(this.dimension.getManager().getDimensions().values());
         int i = list.indexOf(this.dimension);
         if (i != -1) {
            this.dimension = list.get(MathUtils.mod(i + (m.isLeft() ? 1 : -1), list.size()));
            this.refreshWidgets();
            this.movedToPlayer = false;
         }
      } catch (Exception var4) {
      }
   }

   public void alignWidgets() {
      this.buttonPanel.setPosAndSize(0, 0, 18, this.height);
      this.buttonPanel.alignWidgets();
   }

   public boolean onInit() {
      return this.setFullscreen();
   }

   public boolean mousePressed(MouseButton button) {
      if (super.mousePressed(button)) {
         return true;
      } else if (button.isLeft()) {
         this.prevMouseX = this.getMouseX();
         this.prevMouseY = this.getMouseY();
         return true;
      } else if (button.isRight()) {
         int fixedY = Math.max(this.regionPanel.blockY, Minecraft.getInstance().level.getMinBuildHeight());
         BlockPos pos = new BlockPos(this.regionPanel.blockX, fixedY, this.regionPanel.blockZ);
         GlobalPos globalPos = GlobalPos.of(this.dimension.dimension, pos);
         List<ContextMenuItem> list = new ArrayList<>();
         Component title = Component.translatable("ftbchunks.gui.add_waypoint");
         list.add(
            new ContextMenuItem(
               title,
               Icons.ADD,
               btn -> {
                  StringConfig name = new StringConfig();
                  name.setValue("");
                  ColorConfig col = new ColorConfig();
                  col.setValue(Color4I.hsb(MathUtils.RAND.nextFloat(), 1.0F, 1.0F));
                  AddWaypointOverlay.GlobalPosConfig globalPosConfig = new AddWaypointOverlay.GlobalPosConfig();
                  globalPosConfig.setValue(globalPos);
                  EditStringConfigOverlay<String> overlay = new AddWaypointOverlay(
                        this.getGui(),
                        title,
                        globalPosConfig,
                        name,
                        col,
                        accepted -> {
                           if (accepted) {
                              MapDimension mapDimension = MapManager.getInstance()
                                 .orElseThrow()
                                 .getDimension(((GlobalPos)globalPosConfig.getValue()).dimension());
                              WaypointImpl waypoint = new WaypointImpl(WaypointType.DEFAULT, mapDimension, ((GlobalPos)globalPosConfig.getValue()).pos())
                                 .setName((String)name.getValue())
                                 .setColor(((Color4I)col.getValue()).rgba());
                              mapDimension.getWaypointManager().add(waypoint);
                              this.refreshWidgets();
                           }
                        }
                     )
                     .atMousePosition();
                  overlay.setWidth(150);
                  overlay.setX(Math.min(overlay.getX(), this.getScreen().getGuiScaledWidth() - 155));
                  this.getGui().pushModalPanel(overlay);
               }
            )
         );
         this.openContextMenu(list);
         return true;
      } else {
         return false;
      }
   }

   public boolean keyPressed(Key key) {
      if (super.keyPressed(key)) {
         return true;
      } else if (key.is(32)) {
         this.movedToPlayer = false;
         return true;
      } else if (key.is(84)) {
         NetworkManager.sendToServer(
            new TeleportFromMapPacket(this.regionPanel.blockPos().above(), this.regionPanel.blockY == -32767, this.dimension.dimension)
         );
         this.closeGui(false);
         return true;
      } else if (key.is(71) && InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 292)) {
         FTBChunksClientConfig.CHUNK_GRID.toggle();
         FTBChunksClientConfig.saveConfig();
         this.dimension.getManager().updateAllRegions(false);
         return true;
      } else if (FTBChunksClient.doesKeybindMatch(FTBChunksClient.INSTANCE.openMapKey, key) && Platform.isForgeLike()) {
         this.closeGui(false);
         return true;
      } else {
         return false;
      }
   }

   public void tick() {
      super.tick();
      if (this.needIconRefresh) {
         this.regionPanel.refreshWidgets();
         this.needIconRefresh = false;
      }
   }

   public boolean drawDefaultBackground(GuiGraphics graphics) {
      if (!this.movedToPlayer) {
         Player p = Minecraft.getInstance().player;
         this.regionPanel.resetScroll();
         this.regionPanel.scrollTo(p.getX() / 512.0, p.getZ() / 512.0);
         this.movedToPlayer = true;
      }

      BACKGROUND_COLOR.draw(graphics, 0, 0, this.width, this.height);
      return false;
   }

   public void drawBackground(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
      if (this.grabbed != 0) {
         int mx = this.getMouseX();
         int my = this.getMouseY();
         if (this.scrollWidth > this.regionPanel.width) {
            this.regionPanel
               .setScrollX(Math.max(Math.min(this.regionPanel.getScrollX() + (this.prevMouseX - mx), this.scrollWidth - this.regionPanel.width), 0.0));
         }

         if (this.scrollHeight > this.regionPanel.height) {
            this.regionPanel
               .setScrollY(Math.max(Math.min(this.regionPanel.getScrollY() + (this.prevMouseY - my), this.scrollHeight - this.regionPanel.height), 0.0));
         }

         this.prevMouseX = mx;
         this.prevMouseY = my;
      }

      if (this.scrollWidth <= this.regionPanel.width) {
         this.regionPanel.setScrollX((this.scrollWidth - this.regionPanel.width) / 2.0);
      }

      if (this.scrollHeight <= this.regionPanel.height) {
         this.regionPanel.setScrollY((this.scrollHeight - this.regionPanel.height) / 2.0);
      }

      int s = this.getRegionTileSize();
      double ox = -this.regionPanel.getScrollX() % s;
      double oy = -this.regionPanel.getScrollY() % s;

      for (int gx = 0; gx <= w / s + 1; gx++) {
         graphics.vLine((int)(x + ox + gx * s), y, y + h, 1682327110);
      }

      for (int gy = 0; gy <= h / s + 1; gy++) {
         graphics.hLine(x, x + w, (int)(y + oy + gy * s), 1682327110);
      }
   }

   public void drawForeground(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
      String coords = "X: "
         + this.regionPanel.blockX
         + ", Y: "
         + (this.regionPanel.blockY == -32767 ? "??" : this.regionPanel.blockY)
         + ", Z: "
         + this.regionPanel.blockZ;
      if (this.regionPanel.blockY != -32767) {
         MapRegion region = this.dimension.getRegion(XZ.regionFromBlock(this.regionPanel.blockX, this.regionPanel.blockZ));
         MapRegionData data = region.getData();
         if (data != null) {
            int waterLightAndBiome = data.waterLightAndBiome[this.regionPanel.blockIndex] & '\uffff';
            ResourceKey<Biome> biome = this.dimension.getManager().getBiomeKey(waterLightAndBiome);
            Block block = this.dimension.getManager().getBlock(data.getBlockIndex(this.regionPanel.blockIndex));
            coords = coords
               + " | "
               + I18n.get("biome." + biome.location().getNamespace() + "." + biome.location().getPath(), new Object[0])
               + " | "
               + I18n.get(block.getDescriptionId(), new Object[0]);
            if ((waterLightAndBiome & 32768) != 0) {
               coords = coords + " (in water)";
            }
         }
      }

      int coordsw = theme.getStringWidth(coords) / 2;
      BACKGROUND_COLOR.withAlpha(150).draw(graphics, x + (w - coordsw) / 2, y + h - 6, coordsw + 4, 6);
      PoseStack poseStack = graphics.pose();
      poseStack.pushPose();
      poseStack.translate(x + (w - coordsw) / 2.0F + 2.0F, y + h - 5, 0.0F);
      poseStack.scale(0.5F, 0.5F, 1.0F);
      theme.drawString(graphics, coords, 0, 0, 2);
      poseStack.popPose();
      if ((Boolean)FTBChunksClientConfig.DEBUG_INFO.get()) {
         long memory = MapManager.getInstance().map(MapManager::estimateMemoryUsage).orElse(0L);
         String memoryUsage = "Estimated Memory Usage: " + StringUtils.formatDouble00(memory / 1024.0 / 1024.0) + " MB";
         int memoryUsagew = theme.getStringWidth(memoryUsage) / 2;
         BACKGROUND_COLOR.withAlpha(150).draw(graphics, x + (w - memoryUsagew) - 2, y, memoryUsagew + 4, 6);
         poseStack.pushPose();
         poseStack.translate(x + (w - memoryUsagew) - 1.0F, y + 1, 0.0F);
         poseStack.scale(0.5F, 0.5F, 1.0F);
         theme.drawString(graphics, memoryUsage, 0, 0, 2);
         poseStack.popPose();
      }

      if (this.zoom == this.minZoom && this.zoom > 1) {
         Component zoomWarn = Component.translatable("ftbchunks.zoom_warning");
         poseStack.pushPose();
         poseStack.translate(x + w / 2.0F, y + 1, 0.0F);
         poseStack.scale(0.5F, 0.5F, 1.0F);
         theme.drawString(graphics, zoomWarn, 0, 0, Color4I.rgb(15777792), 4);
         poseStack.popPose();
      }
   }

   public static void refreshIconsIfOpen() {
      if (Minecraft.getInstance().screen instanceof ScreenWrapper sw && sw.getGui() instanceof LargeMapScreen lms) {
         lms.refreshIcons();
      }
   }

   public void refreshIcons() {
      this.needIconRefresh = true;
   }

   private int determineMinZoom() {
      if (!(Boolean)FTBChunksClientConfig.MAX_ZOOM_CONSTRAINT.get()) {
         return 1;
      } else {
         long potentialUsage = this.dimension.getLoadedRegions().size() * 4194304L;
         long allocatedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
         long freeMem = Runtime.getRuntime().maxMemory() - allocatedMemory;
         long ratio = freeMem / Math.max(1L, potentialUsage);
         FTBChunks.LOGGER.debug("large map: free mem = {}, potential usage = {}, ratio = {}", freeMem, potentialUsage, ratio);
         if (ratio < 8L) {
            return 64;
         } else if (ratio < 16L) {
            return 32;
         } else if (ratio < 32L) {
            return 16;
         } else {
            return ratio < 64L ? 8 : 1;
         }
      }
   }

   private class ClearDeathPointButton extends SimpleButton {
      public ClearDeathPointButton(Panel panel) {
         super(panel, Component.translatable("ftbchunks.gui.clear_deathpoints"), Icons.CANCEL, (b, m) -> {
            if (LargeMapScreen.this.getWaypointManager().removeIf(wp -> wp.getType() == WaypointType.DEATH)) {
               LargeMapScreen.this.refreshWidgets();
            }
         });
      }

      public boolean shouldDraw() {
         return super.shouldDraw() && LargeMapScreen.this.getWaypointManager().hasDeathpoint();
      }

      public boolean isEnabled() {
         return this.shouldDraw();
      }
   }

   private static class MapKeyReferenceScreen extends KeyReferenceScreen {
      public MapKeyReferenceScreen() {
         super(new String[]{"ftbchunks.gui.large_map_info.text"});
      }

      public Component getTitle() {
         return Component.translatable("ftbchunks.gui.large_map_info");
      }
   }

   static class SimpleTooltipButton extends SimpleButton {
      private final List<Component> tooltipLines;

      public SimpleTooltipButton(Panel panel, Component text, Icon icon, Callback c, Component tooltipLine) {
         super(panel, text, icon, c);
         this.tooltipLines = List.of(tooltipLine);
      }

      public void addMouseOverText(TooltipList list) {
         super.addMouseOverText(list);
         this.tooltipLines.forEach(list::add);
      }
   }
}
