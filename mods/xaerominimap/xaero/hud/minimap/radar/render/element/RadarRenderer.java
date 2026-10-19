package xaero.hud.minimap.radar.render.element;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import xaero.common.HudMod;
import xaero.common.graphics.CustomRenderTypes;
import xaero.common.graphics.renderer.multitexture.MultiTextureRenderTypeRenderer;
import xaero.common.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider;
import xaero.common.icon.XaeroIcon;
import xaero.common.minimap.element.render.MinimapElementRenderer;
import xaero.common.minimap.render.MinimapRendererHelper;
import xaero.common.misc.Misc;
import xaero.common.settings.ModSettings;
import xaero.hud.minimap.BuiltInHudModules;
import xaero.hud.minimap.Minimap;
import xaero.hud.minimap.common.config.option.MinimapProfiledConfigOptions;
import xaero.hud.minimap.config.primary.option.MinimapPrimaryClientConfigOptions;
import xaero.hud.minimap.element.render.MinimapElementRenderInfo;
import xaero.hud.minimap.element.render.MinimapElementRenderLocation;
import xaero.hud.minimap.module.MinimapSession;
import xaero.hud.minimap.radar.RadarSession;
import xaero.hud.minimap.radar.category.EntityRadarCategory;
import xaero.hud.minimap.radar.category.EntityRadarCategoryManager;
import xaero.hud.minimap.radar.category.setting.EntityRadarCategorySettings;
import xaero.hud.minimap.radar.color.RadarColor;
import xaero.hud.minimap.radar.icon.RadarIconManager;
import xaero.hud.minimap.radar.state.RadarList;
import xaero.hud.minimap.radar.util.RadarUtils;
import xaero.hud.render.util.RenderBufferUtil;
import xaero.lib.client.config.ClientConfigManager;
import xaero.lib.common.config.Config;
import xaero.lib.common.config.single.SingleConfigManager;

public abstract class RadarRenderer extends MinimapElementRenderer<Entity, RadarRenderContext> {
   private final RadarIconManager radarIconManager;
   private final Minimap minimap;
   private RadarSession radarSession;
   private EntityRadarCategoryManager categoryManager;
   private RadarList previousList;
   private double maxDistanceSquared;
   private double labelScale;
   private boolean smoothDots;
   private boolean debugEntityIcons;
   private boolean debugEntityVariantIds;
   private int dotsStyle;
   private int heightLimit;
   private boolean heightBasedFade;
   private int startFadingAt;
   private boolean displayNameWhenIconFails;
   private boolean alwaysNameTags;
   private RadarColor radarColor;
   private RadarColor fallbackColor;
   private int displayY;
   private int nameSettingForCategory;
   private boolean namesForCategory;
   private boolean name;
   private boolean iconsAllowed;
   private boolean labelsAllowed;
   private RenderType dotsRenderType;
   private BufferSource minimapBufferSource;
   private VertexConsumer dotsBufferBuilder;
   private VertexConsumer labelBgBuilder;
   private MultiTextureRenderTypeRenderer iconsRenderer;
   private MinimapRendererHelper helper;
   private final RadarRenderProvider radarRenderProvider;

   protected RadarRenderer(
      RadarIconManager radarIconManager, Minimap minimap, RadarElementReader elementReader, RadarRenderProvider provider, RadarRenderContext context
   ) {
      super(elementReader, provider, context);
      this.radarIconManager = radarIconManager;
      this.minimap = minimap;
      this.radarRenderProvider = provider;
   }

   @Override
   public void preRender(
      MinimapElementRenderInfo renderInfo, BufferSource vanillaBufferSource, MultiTextureRenderTypeRendererProvider multiTextureRenderTypeRenderers
   ) {
      this.radarIconManager.allowPrerender();
      ModSettings settings = HudMod.INSTANCE.getSettings();
      ClientConfigManager configManager = HudMod.INSTANCE.getHudConfigs().getClientConfigManager();
      SingleConfigManager<Config> primaryConfigManager = configManager.getPrimaryConfigManager();
      this.iconsAllowed = true;
      this.labelsAllowed = true;
      this.context.reversedOrder = ModSettings.keyReverseEntityRadar.isDown();
      MinimapSession session = BuiltInHudModules.MINIMAP.getCurrentSession();
      this.radarSession = session.getRadarSession();
      this.categoryManager = this.radarSession.getCategoryManager();
      this.previousList = null;
      this.labelScale = (Double)configManager.getEffective(MinimapProfiledConfigOptions.RADAR_NAME_SCALE)
         * (Minecraft.getInstance().isEnforceUnicode() ? 2.0 : 1.0);
      this.smoothDots = (Boolean)configManager.getEffective(MinimapProfiledConfigOptions.RADAR_SMOOTH_DOTS);
      this.debugEntityIcons = (Boolean)primaryConfigManager.getEffective(MinimapPrimaryClientConfigOptions.DEBUG_ENTITY_ICONS);
      this.debugEntityVariantIds = (Boolean)primaryConfigManager.getEffective(MinimapPrimaryClientConfigOptions.DEBUG_ENTITY_VARIANT_IDS);
      this.dotsStyle = (Integer)configManager.getEffective(MinimapProfiledConfigOptions.RADAR_DOTS_STYLE);
      this.dotsRenderType = this.smoothDots ? CustomRenderTypes.GUI_BILINEAR : CustomRenderTypes.GUI_NEAREST;
      RenderSystem.disableDepthTest();
      vanillaBufferSource.endBatch();
      this.minimapBufferSource = HudMod.INSTANCE.getHudRenderer().getCustomVertexConsumers().getBetterPVPRenderTypeBuffers();
      this.dotsBufferBuilder = null;
      this.labelBgBuilder = this.minimapBufferSource.getBuffer(CustomRenderTypes.RADAR_NAME_BGS);
      this.iconsRenderer = multiTextureRenderTypeRenderers.getRenderer(
         t -> RenderSystem.setShaderTexture(0, t), MultiTextureRenderTypeRendererProvider::defaultTextureBind, CustomRenderTypes.GUI_BILINEAR
      );
      this.helper = HudMod.INSTANCE.getMinimap().getMinimapFBORenderer().getHelper();
      double playerDimDiv = renderInfo.backgroundCoordinateScale / renderInfo.renderEntityDimensionScale;
      int shapeConfig = (Integer)configManager.getEffective(MinimapProfiledConfigOptions.SHAPE);
      this.maxDistanceSquared = RadarUtils.getMaxDistance(session.getProcessor(), shapeConfig == 1) * playerDimDiv * playerDimDiv;
   }

   public boolean renderElement(
      Entity e,
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
      if (renderInfo.location == MinimapElementRenderLocation.IN_MINIMAP) {
         double offX = e.getX() - renderInfo.renderEntityPos.x;
         if (offX * offX > this.maxDistanceSquared) {
            return false;
         }

         double offY = e.getZ() - renderInfo.renderEntityPos.z;
         if (offY * offY > this.maxDistanceSquared) {
            return false;
         }
      }

      if (this.context.radarList == null) {
         EntityRadarCategory rootCategory = this.categoryManager.getRootCategory();
         EntityRadarCategory syncedRootCategory = this.categoryManager.getEffectiveSyncedRootCategory();
         this.context.radarList = RadarList.Builder.getDefault().build();
         this.context.radarList.setClientCategory(this.categoryManager.getRuleResolver().resolve(rootCategory, e, renderInfo.player));
         if (this.context.radarList.getClientCategory() == null) {
            if (!this.context.isMainDot) {
               return false;
            }

            this.context.radarList.setClientCategory(rootCategory);
         }

         if (syncedRootCategory != null) {
            this.context.radarList.setSyncedCategory(this.categoryManager.getRuleResolver().resolve(syncedRootCategory, e, renderInfo.player));
            if (!this.context.isMainDot && this.context.radarList.getSyncedCategory() == null) {
               return false;
            }
         }

         if (this.context.radarList == null) {
            return false;
         }
      }

      if (this.context.radarList != this.previousList) {
         this.setupRenderForList(this.context.radarList);
         this.previousList = this.context.radarList;
      }

      this.setupRenderForEntity(e);
      if (e instanceof Player) {
         this.confirmTrackedPlayerRadarRender((Player)e);
      }

      Entity renderEntity = renderInfo.renderEntity;
      boolean cave = renderInfo.cave;
      float optionalScaleAdjust = renderInfo.location == MinimapElementRenderLocation.OVER_MINIMAP ? 0.5F : 1.0F;
      optionalScale *= optionalScaleAdjust;
      PoseStack matrixStack = guiGraphics.pose();
      matrixStack.pushPose();
      boolean icon = this.iconsAllowed && this.context.icon;
      boolean name = this.name;
      if (highlighted && this.nameSettingForCategory > 0) {
         name = true;
      }

      XaeroIcon entityIcon = null;
      if (icon) {
         entityIcon = this.radarIconManager
            .get(e, (float)this.context.iconScale, this.debugEntityIcons, this.debugEntityVariantIds, guiGraphics, renderInfo.framebuffer);
      }

      if (entityIcon == RadarIconManager.DOT) {
         entityIcon = null;
         icon = false;
      }

      boolean usableIcon = entityIcon != null && entityIcon != RadarIconManager.FAILED;
      float offY = (float)(renderEntity.getY() - e.getY());
      int labelOffsetX = 0;
      int labelOffsetY = 0;
      matrixStack.translate(partialX, partialY, 0.0);
      double figureScale;
      if (usableIcon) {
         figureScale = this.context.iconScale;
         this.renderIcon(entityIcon, optionalScale, figureScale, offY, cave, matrixStack);
      } else {
         boolean smooth = this.smoothDots;
         if (!smooth) {
            optionalScale = (float)Math.ceil(optionalScale);
         }

         double dotActualScale = optionalScale;
         figureScale = this.context.dotScale;
         if (this.dotsStyle == 1) {
            if (!smooth) {
               figureScale = (int)figureScale;
            }

            dotActualScale *= figureScale;
         }

         float dotOffset = this.renderDot(e, renderInfo.player, smooth, optionalScale, figureScale, offY, cave, matrixStack);
         if (!smooth) {
            double dotRadius = -dotOffset * dotActualScale;
            double dotRadiusPartial = dotRadius - (int)dotRadius;
            labelOffsetX = partialX - dotRadiusPartial <= -0.5 ? -1 : 0;
            labelOffsetY = partialY - dotRadiusPartial < -0.5 ? -1 : 0;
         }

         if (icon && this.displayNameWhenIconFails && entityIcon == RadarIconManager.FAILED) {
            name = true;
         }
      }

      matrixStack.popPose();
      if (!this.labelsAllowed) {
         return true;
      }

      if (!name && this.displayY <= 0) {
         return true;
      }

      labelOffsetY += (int)Math.round((usableIcon ? 11 : 5) * figureScale * optionalScale);
      matrixStack.translate(labelOffsetX, labelOffsetY, optionalDepth + 0.1F);
      if (optionalScale < 1.0F) {
         optionalScale = 1.0F;
      }

      this.renderLabel(e, renderEntity, name, optionalScale, matrixStack);
      return true;
   }

   @Override
   public void postRender(
      MinimapElementRenderInfo renderInfo, BufferSource vanillaBufferSource, MultiTextureRenderTypeRendererProvider multiTextureRenderTypeRenderers
   ) {
      if (this.context.reversedOrder && this.dotsBufferBuilder != null) {
         this.minimapBufferSource.endBatch(this.dotsRenderType);
      }

      multiTextureRenderTypeRenderers.draw(this.iconsRenderer);
      if (!this.context.reversedOrder && this.dotsBufferBuilder != null) {
         this.minimapBufferSource.endBatch(this.dotsRenderType);
      }

      this.minimapBufferSource.endBatch();
      RenderSystem.enableDepthTest();
      RenderSystem.depthFunc(515);
      this.iconsRenderer = null;
      this.previousList = null;
   }

   private void renderIcon(XaeroIcon entityIcon, double optionalScale, double figureScale, float offY, boolean cave, PoseStack matrixStack) {
      double clampedScale = Math.max(1.0, figureScale * optionalScale);
      matrixStack.scale((float)clampedScale, (float)clampedScale, 1.0F);
      float brightness = !this.heightBasedFade ? 1.0F : this.radarSession.getColorHelper().getEntityHeightFade(offY, this.heightLimit, this.startFadingAt);
      float opacity = 1.0F;
      if (cave) {
         opacity = brightness;
         brightness = 1.0F;
      }

      this.helper
         .prepareMyTexturedColoredModalRect(
            matrixStack.last().pose(),
            -31.0F,
            -31.0F,
            entityIcon.getOffsetX() + 1,
            entityIcon.getOffsetY() + 1,
            62.0F,
            62.0F,
            62.0F,
            entityIcon.getTextureAtlas().getWidth(),
            entityIcon.getTextureAtlas().getTextureId(),
            brightness,
            brightness,
            brightness,
            opacity,
            this.iconsRenderer
         );
   }

   private float renderDot(Entity e, Player player, boolean smooth, float optionalScale, double figureScale, float offY, boolean cave, PoseStack matrixStack) {
      matrixStack.scale(optionalScale, optionalScale, 1.0F);
      int color = this.radarSession
         .getColorHelper()
         .getEntityColor(e, offY, cave, this.heightLimit, this.startFadingAt, this.heightBasedFade, this.radarColor, this.fallbackColor);
      float r = (color >> 16 & 0xFF) / 255.0F;
      float g = (color >> 8 & 0xFF) / 255.0F;
      float b = (color & 0xFF) / 255.0F;
      float a = (color >> 24 & 0xFF) / 255.0F;
      int dotTextureX = 0;
      int dotTextureY = 0;
      int dotTextureW = 0;
      int dotTextureH = 0;
      float dotOffset = 0.0F;
      short var20;
      byte var21;
      byte var22;
      if (this.dotsStyle == 1) {
         if (smooth) {
            dotTextureX = 1;
            var20 = 88;
         } else {
            dotTextureX = 9;
            var20 = 77;
         }

         dotOffset = -3.5F;
         var22 = 8;
         var21 = 8;
         matrixStack.scale((float)figureScale, (float)figureScale, 1.0F);
      } else {
         switch (this.context.dotSize) {
            case 1:
               dotOffset = -4.5F;
               var20 = 108;
               var22 = 9;
               var21 = 9;
               break;
            case 2:
            default:
               dotOffset = -5.5F;
               var20 = 117;
               var22 = 11;
               var21 = 11;
               break;
            case 3:
               dotOffset = -7.5F;
               var20 = 128;
               var22 = 15;
               var21 = 15;
               break;
            case 4:
               dotOffset = -10.5F;
               var20 = 160;
               var22 = 21;
               var21 = 21;
         }
      }

      if (this.dotsBufferBuilder == null) {
         this.dotsBufferBuilder = this.minimapBufferSource.getBuffer(this.dotsRenderType);
      }

      RenderBufferUtil.addTexturedColoredRect(
         matrixStack.last().pose(), this.dotsBufferBuilder, dotOffset, dotOffset, dotTextureX, var20, var21, var22, r, g, b, a, 256.0F
      );
      return dotOffset;
   }

   private void renderLabel(Entity e, Entity renderEntity, boolean name, double optionalScale, PoseStack matrixStack) {
      double dotNameScale = this.labelScale * optionalScale;
      matrixStack.scale((float)dotNameScale, (float)dotNameScale, 1.0F);
      String yValueString = null;
      if (this.displayY > 0) {
         int yInt = (int)Math.floor(e.getY());
         int pYInt = (int)Math.floor(renderEntity.getY());
         if (this.displayY == 1) {
            yValueString = yInt + "";
         } else if (this.displayY == 2) {
            yValueString = yInt - pYInt + "";
         } else {
            yValueString = "";
         }

         yValueString = yValueString + (yInt > pYInt ? "↑" : (yInt != pYInt ? "↓" : ""));
         if (yValueString.length() == 0) {
            yValueString = "-";
         }
      }

      Font font = Minecraft.getInstance().font;
      String label = null;
      if (name) {
         Component component = Misc.getFixedDisplayName(e);
         if (component == null) {
            return;
         }

         label = component.getString();
         if (this.displayY > 0) {
            label = label + "(" + yValueString + ")";
         }
      } else if (this.displayY > 0) {
         label = yValueString;
      }

      if (label != null) {
         int labelW = font.width(label);
         RenderBufferUtil.addColoredRect(matrixStack.last().pose(), this.labelBgBuilder, -labelW / 2 - 2, -1.0F, labelW + 3, 10, 0.0F, 0.0F, 0.0F, 0.3529412F);
         Misc.drawNormalText(matrixStack, label, -labelW / 2, 0.0F, -1, false, this.minimapBufferSource);
      }
   }

   private void setupRenderForList(RadarList radarList) {
      if (!this.radarRenderProvider.isUsed()) {
         this.radarRenderProvider.setupContextForList(radarList, this.context);
      }

      this.heightLimit = radarList.getEffective(EntityRadarCategorySettings.HEIGHT_LIMIT).intValue();
      this.heightBasedFade = radarList.getEffective(EntityRadarCategorySettings.HEIGHT_FADE);
      this.startFadingAt = radarList.getEffective(EntityRadarCategorySettings.START_FADING_AT).intValue();
      this.displayNameWhenIconFails = radarList.getEffective(EntityRadarCategorySettings.ICON_NAME_FALLBACK);
      this.alwaysNameTags = radarList.getEffective(EntityRadarCategorySettings.ALWAYS_NAMETAGS);
      this.radarColor = RadarColor.fromIndex(radarList.getEffective(EntityRadarCategorySettings.COLOR).intValue());
      this.fallbackColor = this.radarSession.getColorHelper().getFallbackColor(radarList);
      this.displayY = radarList.getEffective(EntityRadarCategorySettings.DISPLAY_Y).intValue();
      this.nameSettingForCategory = radarList.getEffective(EntityRadarCategorySettings.NAMES).intValue();
      this.namesForCategory = this.nameSettingForCategory == 1 && this.context.playerListDown || this.nameSettingForCategory == 2;
   }

   private void setupRenderForEntity(Entity entity) {
      if (!this.radarRenderProvider.isUsed()) {
         this.radarRenderProvider.setupContextForEntity(entity, this.context);
      }

      boolean name = this.namesForCategory;
      if (!name && !(entity instanceof Player)) {
         name = this.alwaysNameTags && entity.hasCustomName();
      }

      this.name = name;
   }

   private void confirmTrackedPlayerRadarRender(Player e) {
      if (HudMod.INSTANCE.getTrackedPlayerRenderer().getCollector().playerExists(e.getUUID())) {
         HudMod.INSTANCE.getTrackedPlayerRenderer().getCollector().confirmPlayerRadarRender(e);
      }

      if (HudMod.INSTANCE.getSupportMods().worldmap()) {
         HudMod.INSTANCE.getSupportMods().worldmapSupport.confirmPlayerRadarRender(e);
      }
   }

   public void renderSingleEntity(
      Entity entity,
      boolean cave,
      boolean highlighted,
      float optionalScale,
      boolean allowIcon,
      boolean allowLabel,
      MinimapElementRenderLocation location,
      RenderTarget defaultFramebuffer,
      GuiGraphics guiGraphics
   ) {
      this.context.radarList = null;
      MinimapSession session = BuiltInHudModules.MINIMAP.getCurrentSession();
      Player player = Minecraft.getInstance().player;
      MinimapElementRenderInfo renderInfo = new MinimapElementRenderInfo(
         location, entity, player, entity.position(), cave, 1.0F, defaultFramebuffer, 1.0, entity.level().dimension()
      );
      MultiTextureRenderTypeRendererProvider multiTextureRenderTypeRenderers = session.getMultiTextureRenderTypeRenderers();
      BufferSource vanillaBufferSource = guiGraphics.bufferSource();
      this.context.isMainDot = entity == Minecraft.getInstance().getCameraEntity();
      this.preRender(renderInfo, vanillaBufferSource, multiTextureRenderTypeRenderers);
      this.iconsAllowed = allowIcon;
      this.labelsAllowed = allowLabel;
      this.renderElement(entity, highlighted, false, 0.0, optionalScale, 0.0, 0.0, renderInfo, guiGraphics, vanillaBufferSource);
      this.postRender(renderInfo, vanillaBufferSource, multiTextureRenderTypeRenderers);
      this.context.isMainDot = false;
   }

   @Override
   public boolean shouldRender(MinimapElementRenderLocation location) {
      if (!this.minimap.usingFBO()) {
         return false;
      }

      if (location == MinimapElementRenderLocation.WORLD_MAP) {
         return true;
      }

      if (location == MinimapElementRenderLocation.WORLD_MAP_MENU) {
         return true;
      }

      ClientConfigManager configManager = HudMod.INSTANCE.getHudConfigs().getClientConfigManager();
      return (Boolean)configManager.getEffective(MinimapProfiledConfigOptions.DISPLAY_RADAR);
   }

   public static final class Builder {
      private RadarIconManager radarIconManager;
      private Minimap minimap;

      private Builder() {
      }

      public RadarRenderer.Builder setDefault() {
         this.setRadarIconManager(null);
         return this;
      }

      public RadarRenderer.Builder setRadarIconManager(RadarIconManager radarIconManager) {
         this.radarIconManager = radarIconManager;
         return this;
      }

      public RadarRenderer.Builder setMinimap(Minimap minimap) {
         this.minimap = minimap;
         return this;
      }

      public RadarRenderer build() {
         if (this.radarIconManager != null && this.minimap != null) {
            RadarElementReader elementReader = new xaero.common.minimap.render.radar.element.RadarElementReader();
            RadarRenderProvider provider = new xaero.common.minimap.render.radar.element.RadarRenderProvider();
            RadarRenderContext context = new xaero.common.minimap.render.radar.element.RadarRenderContext();
            return new xaero.common.minimap.render.radar.element.RadarRenderer(this.radarIconManager, this.minimap, elementReader, provider, context);
         } else {
            throw new IllegalStateException();
         }
      }

      public static RadarRenderer.Builder begin() {
         return new RadarRenderer.Builder().setDefault();
      }
   }
}
