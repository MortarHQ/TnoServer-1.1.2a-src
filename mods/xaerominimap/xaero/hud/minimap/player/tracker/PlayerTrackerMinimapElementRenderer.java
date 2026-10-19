package xaero.hud.minimap.player.tracker;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import xaero.common.HudMod;
import xaero.common.IXaeroMinimap;
import xaero.common.graphics.CustomRenderTypes;
import xaero.common.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider;
import xaero.hud.entity.EntityUtils;
import xaero.hud.minimap.common.config.option.MinimapProfiledConfigOptions;
import xaero.hud.minimap.config.util.MinimapConfigClientUtils;
import xaero.hud.minimap.element.render.MinimapElementRenderInfo;
import xaero.hud.minimap.element.render.MinimapElementRenderLocation;
import xaero.hud.minimap.element.render.MinimapElementRenderer;
import xaero.hud.render.util.RenderBufferUtil;
import xaero.lib.client.config.ClientConfigManager;

public final class PlayerTrackerMinimapElementRenderer extends MinimapElementRenderer<PlayerTrackerMinimapElement<?>, PlayerTrackerMinimapElementRenderContext> {
   private final double WORLD_MINIMUM_DISTANCE = 10.0;
   private final double WORLD_FADING_LENGTH = 10.0;
   private BufferSource minimapBufferSource;
   private final PlayerTrackerMinimapElementCollector elementCollector;
   private final PlayerTrackerIconRenderer playerTrackerIconRenderer;
   private final IXaeroMinimap modMain;
   private float nameScale;

   private PlayerTrackerMinimapElementRenderer(
      PlayerTrackerMinimapElementCollector elementCollector,
      IXaeroMinimap modMain,
      PlayerTrackerMinimapElementRenderContext context,
      PlayerTrackerMinimapElementRenderProvider<PlayerTrackerMinimapElementRenderContext> provider,
      PlayerTrackerMinimapElementReader reader,
      PlayerTrackerIconRenderer playerTrackerIconRenderer
   ) {
      super(reader, provider, context);
      this.elementCollector = elementCollector;
      this.modMain = modMain;
      this.playerTrackerIconRenderer = playerTrackerIconRenderer;
   }

   public ResourceLocation getPlayerSkin(Player player, PlayerInfo info) {
      ResourceLocation skinTextureLocation = player instanceof AbstractClientPlayer
         ? ((AbstractClientPlayer)player).getSkin().texture()
         : info.getSkin().texture();
      if (skinTextureLocation == null) {
         skinTextureLocation = DefaultPlayerSkin.get(player.getUUID()).texture();
      }

      return skinTextureLocation;
   }

   @Override
   public void preRender(MinimapElementRenderInfo renderInfo, BufferSource vanillaBufferSource, MultiTextureRenderTypeRendererProvider rendererProvider) {
      RenderSystem.disableDepthTest();
      vanillaBufferSource.endBatch();
      this.minimapBufferSource = this.modMain.getHudRenderer().getCustomVertexConsumers().getBetterPVPRenderTypeBuffers();
      this.context.coloredBackgroundConsumer = this.minimapBufferSource.getBuffer(CustomRenderTypes.COLORED_WAYPOINTS_BGS);
      this.context.uniqueTextureUIObjectRenderer = rendererProvider.getRenderer(
         t -> RenderSystem.setShaderTexture(0, t), MultiTextureRenderTypeRendererProvider::defaultTextureBind, CustomRenderTypes.GUI_NEAREST
      );
      this.context.renderEntityDimId = renderInfo.renderEntityDimension;
      this.context.mapDimId = renderInfo.mapDimension;
      ClientConfigManager configManager = HudMod.INSTANCE.getHudConfigs().getClientConfigManager();
      float trackedPlayerWorldIconScale = MinimapConfigClientUtils.getUIScale(configManager, MinimapProfiledConfigOptions.TRACKED_PLAYER_WORLD_ICON_SCALE);
      float trackedPlayerMinimapIconScale = MinimapConfigClientUtils.getUIScale(configManager, MinimapProfiledConfigOptions.TRACKED_PLAYER_MINIMAP_ICON_SCALE);
      this.context.iconScale = renderInfo.location == MinimapElementRenderLocation.IN_WORLD ? trackedPlayerWorldIconScale : trackedPlayerMinimapIconScale;
      this.nameScale = MinimapConfigClientUtils.getUIScale(configManager, MinimapProfiledConfigOptions.TRACKED_PLAYER_WORLD_NAME_SCALE);
   }

   @Override
   public void postRender(MinimapElementRenderInfo renderInfo, BufferSource vanillaBufferSource, MultiTextureRenderTypeRendererProvider rendererProvider) {
      rendererProvider.draw(this.context.uniqueTextureUIObjectRenderer);
      this.minimapBufferSource.endBatch();
      RenderSystem.enableDepthTest();
      RenderSystem.depthFunc(515);
      this.elementCollector.resetRenderedOnRadarFlags();
   }

   public boolean renderElement(
      PlayerTrackerMinimapElement<?> e,
      boolean highlighted,
      boolean outOfBounds,
      double optionalDepth,
      float optionalScale,
      double partialX,
      double partialY,
      MinimapElementRenderInfo renderInfo,
      GuiGraphics guiGraphics,
      BufferSource vanillaBufferSource
   ) {
      if (!outOfBounds && renderInfo.location != MinimapElementRenderLocation.IN_WORLD && e.wasRenderedOnRadar()) {
         return false;
      }

      PoseStack matrixStack = guiGraphics.pose();
      Minecraft mc = Minecraft.getInstance();
      PlayerInfo info = mc.getConnection().getPlayerInfo(e.getPlayerId());
      if (info == null) {
         return false;
      }

      Player clientPlayer = mc.level.getPlayerByUUID(e.getPlayerId());
      double trackedX = clientPlayer == null ? e.getX() : EntityUtils.getEntityX(clientPlayer, renderInfo.partialTicks);
      double trackedY = clientPlayer == null ? e.getY() : EntityUtils.getEntityY(clientPlayer, renderInfo.partialTicks);
      double trackedZ = clientPlayer == null ? e.getZ() : EntityUtils.getEntityZ(clientPlayer, renderInfo.partialTicks);
      double offX = trackedX - renderInfo.renderEntityPos.x;
      double offY = trackedY - renderInfo.renderEntityPos.y;
      double offZ = trackedZ - renderInfo.renderEntityPos.z;
      double distance = Math.sqrt(offX * offX + offY * offY + offZ * offZ);
      if (distance < 10.0) {
         return false;
      }

      matrixStack.pushPose();
      matrixStack.translate(0.0, 0.0, optionalDepth);
      boolean inWorld = renderInfo.location == MinimapElementRenderLocation.IN_WORLD;
      float alpha = inWorld ? 0.5F : 1.0F;
      if (highlighted && inWorld) {
         alpha = 0.8F;
      }

      if (!highlighted && inWorld && distance < 20.0) {
         alpha *= (float)((distance - 10.0) / 10.0);
      }

      matrixStack.translate(0.0, 0.0, 0.01);
      matrixStack.pushPose();
      matrixStack.scale(this.context.iconScale, this.context.iconScale, 1.0F);
      RenderBufferUtil.addColoredRect(matrixStack.last().pose(), this.context.coloredBackgroundConsumer, -5.0F, -5.0F, 10, 10, 1.0F, 1.0F, 1.0F, alpha);
      this.playerTrackerIconRenderer
         .renderIcon(mc, this.context.uniqueTextureUIObjectRenderer, matrixStack, clientPlayer, this.getPlayerSkin(clientPlayer, info), alpha);
      matrixStack.popPose();
      if (highlighted && inWorld) {
         matrixStack.translate(-5.0F * this.context.iconScale, 0.0F, 0.0F);
         matrixStack.scale(this.nameScale, this.nameScale, 1.0F);
         String playerName = info.getProfile().getName();
         int playerNameWidth = mc.font.width(playerName);
         float labelAlpha = 0.3529412F;
         RenderBufferUtil.addColoredRect(
            matrixStack.last().pose(),
            this.context.coloredBackgroundConsumer,
            -playerNameWidth - 1,
            -5.0F,
            playerNameWidth + 1,
            10,
            0.0F,
            0.0F,
            0.0F,
            labelAlpha
         );
         mc.font
            .drawInBatch(playerName, -playerNameWidth, -4.0F, -1, false, matrixStack.last().pose(), this.minimapBufferSource, DisplayMode.NORMAL, 0, 15728880);
      }

      matrixStack.popPose();
      return true;
   }

   @Override
   public boolean shouldRender(MinimapElementRenderLocation location) {
      ClientConfigManager configManager = HudMod.INSTANCE.getHudConfigs().getClientConfigManager();
      return location != MinimapElementRenderLocation.IN_WORLD && (Boolean)configManager.getEffective(MinimapProfiledConfigOptions.TRACKED_PLAYERS_ON_MINIMAP)
         || location == MinimapElementRenderLocation.IN_WORLD && (Boolean)configManager.getEffective(MinimapProfiledConfigOptions.TRACKED_PLAYERS_IN_WORLD);
   }

   @Override
   public int getOrder() {
      return 100;
   }

   public PlayerTrackerMinimapElementCollector getCollector() {
      return this.elementCollector;
   }

   public static final class Builder {
      private final IXaeroMinimap modMain;

      private Builder(IXaeroMinimap modMain) {
         this.modMain = modMain;
      }

      private PlayerTrackerMinimapElementRenderer.Builder setDefault() {
         return this;
      }

      public PlayerTrackerMinimapElementRenderer build() {
         PlayerTrackerMinimapElementCollector collector = new PlayerTrackerMinimapElementCollector(this.modMain.getPlayerTrackerSystemManager());
         return new PlayerTrackerMinimapElementRenderer(
            collector,
            this.modMain,
            new PlayerTrackerMinimapElementRenderContext(),
            new PlayerTrackerMinimapElementRenderProvider<>(collector),
            new PlayerTrackerMinimapElementReader(),
            new PlayerTrackerIconRenderer()
         );
      }

      public static PlayerTrackerMinimapElementRenderer.Builder begin(IXaeroMinimap modMain) {
         return new PlayerTrackerMinimapElementRenderer.Builder(modMain).setDefault();
      }
   }
}
