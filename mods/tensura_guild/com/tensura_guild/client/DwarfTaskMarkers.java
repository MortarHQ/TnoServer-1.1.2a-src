package com.tensura_guild.client;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import io.github.manasmods.tensura.entity.human.DwarfEntity;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.event.RenderNameTagEvent;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public final class DwarfTaskMarkers {
   private static final ResourceLocation TASK_ICON = ResourceLocation.fromNamespaceAndPath("tensura_guild", "textures/entity/dwarf/task_icon.png");
   private static final ResourceLocation MARKER_ARROW = ResourceLocation.fromNamespaceAndPath("tensura_guild", "textures/entity/dwarf/arrow.png");
   private static final String MERCHANT_MARKERS_MOD_ID = "merchantmarkers";
   private static final double MAX_DISTANCE = 64.0;
   private static final float NAMETAG_SCALE = 0.025F;
   private static final int TASK_ICON_X = -8;
   private static final int TASK_ICON_Y = -27;
   private static final int TASK_ICON_SIZE = 16;
   private static final int ARROW_X = -8;
   private static final int ARROW_Y = -10;
   private static final int ARROW_WIDTH = 16;
   private static final int ARROW_HEIGHT = 8;
   private static final CopyOnWriteArrayList<DwarfTaskMarkerPredicate> MARKER_PREDICATES = new CopyOnWriteArrayList<>();
   private static Boolean merchantMarkersLoaded;
   private static Method merchantMarkersShouldShowMarker;
   private static Method merchantMarkersConfigGetter;
   private static Field merchantMarkersKeyField;
   private static Field merchantMarkersAlwaysShowField;
   private static Field merchantMarkersShowArrowField;
   private static Field merchantMarkersIconScaleField;
   private static Field merchantMarkersVerticalOffsetField;

   private DwarfTaskMarkers() {
   }

   public static Runnable registerMarkerPredicate(DwarfTaskMarkerPredicate predicate) {
      DwarfTaskMarkerPredicate checkedPredicate = Objects.requireNonNull(predicate, "predicate");
      MARKER_PREDICATES.add(checkedPredicate);
      return () -> MARKER_PREDICATES.remove(checkedPredicate);
   }

   public static void render(RenderNameTagEvent event) {
      Entity entity = event.getEntity();
      if (shouldShowMarker(entity)) {
         Minecraft minecraft = Minecraft.getInstance();
         EntityRenderDispatcher dispatcher = minecraft.getEntityRenderDispatcher();
         if (!(dispatcher.distanceToSqr(entity) > 4096.0)) {
            PoseStack poseStack = event.getPoseStack();
            poseStack.pushPose();
            poseStack.translate(0.0, entity.getBbHeight() + 0.5, 0.0);
            poseStack.mulPose(dispatcher.cameraOrientation());
            poseStack.scale(0.025F, -0.025F, 0.025F);
            boolean depthTestEnabled = GL11.glIsEnabled(2929);
            boolean blendEnabled = GL11.glIsEnabled(3042);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();
            boolean overlayingMerchantMarker = merchantMarkersWillRender(entity);
            int yOffset = merchantMarkersOverlayOffset(overlayingMerchantMarker);
            renderIcon(TASK_ICON, poseStack, -8, -27 + yOffset, 16, 16, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F);
            if (!overlayingMerchantMarker) {
               renderIcon(MARKER_ARROW, poseStack, -8, -10, 16, 8, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F);
            }

            if (depthTestEnabled) {
               RenderSystem.enableDepthTest();
            } else {
               RenderSystem.disableDepthTest();
            }

            if (blendEnabled) {
               RenderSystem.enableBlend();
            } else {
               RenderSystem.disableBlend();
            }

            poseStack.popPose();
         }
      }
   }

   private static boolean shouldShowMarker(Entity entity) {
      return !(entity instanceof DwarfEntity dwarf) ? false : !dwarf.isInvisible() && !dwarf.isBaby() && !dwarf.isDeadOrDying() && hasMarkerPredicate(dwarf);
   }

   private static boolean hasMarkerPredicate(DwarfEntity dwarf) {
      for (DwarfTaskMarkerPredicate predicate : MARKER_PREDICATES) {
         if (predicate.shouldShowTaskMarker(dwarf)) {
            return true;
         }
      }

      return false;
   }

   private static int merchantMarkersOverlayOffset(boolean overlayingMerchantMarker) {
      if (!overlayingMerchantMarker) {
         return 0;
      }

      Object config = merchantMarkersConfig();
      boolean showArrow = getBooleanConfig(config, merchantMarkersShowArrowField, true);
      int verticalOffset = getIntConfig(config, merchantMarkersVerticalOffsetField, 0);
      double iconScale = getDoubleConfig(config, merchantMarkersIconScaleField, 1.0);
      int merchantIconY = (showArrow ? -27 : -18) - verticalOffset;
      return (int)Math.floor(merchantIconY * iconScale - -27.0);
   }

   private static boolean merchantMarkersWillRender(Entity entity) {
      if (isMerchantMarkersLoaded() && merchantMarkersDisplayEnabled()) {
         Method method = merchantMarkersShouldShowMarker();
         if (method == null) {
            return true;
         }

         try {
            return method.invoke(null, entity) instanceof Boolean visible ? visible : true;
         } catch (ReflectiveOperationException | RuntimeException ignored) {
            return true;
         }
      } else {
         return false;
      }
   }

   private static boolean isMerchantMarkersLoaded() {
      if (merchantMarkersLoaded == null) {
         merchantMarkersLoaded = ModList.get().isLoaded("merchantmarkers");
      }

      return merchantMarkersLoaded;
   }

   private static boolean merchantMarkersDisplayEnabled() {
      Object config = merchantMarkersConfig();
      boolean alwaysShow = getBooleanConfig(config, merchantMarkersAlwaysShowField, true);
      if (alwaysShow) {
         return true;
      }

      if (merchantMarkersKeyField == null) {
         return true;
      }

      try {
         Object keyMapping = merchantMarkersKeyField.get(null);
         return keyMapping.getClass().getMethod("isDown").invoke(keyMapping) instanceof Boolean down ? down : true;
      } catch (ReflectiveOperationException | RuntimeException ignored) {
         return true;
      }
   }

   private static Method merchantMarkersShouldShowMarker() {
      if (merchantMarkersShouldShowMarker != null) {
         return merchantMarkersShouldShowMarker;
      }

      try {
         Class<?> markersClass = Class.forName("com.anthonyhilyard.merchantmarkers.render.Markers");
         merchantMarkersShouldShowMarker = markersClass.getMethod("shouldShowMarker", Entity.class);
         return merchantMarkersShouldShowMarker;
      } catch (ReflectiveOperationException | RuntimeException ignored) {
         return null;
      }
   }

   private static Object merchantMarkersConfig() {
      try {
         if (merchantMarkersConfigGetter == null) {
            Class<?> configClass = Class.forName("com.anthonyhilyard.merchantmarkers.config.MerchantMarkersConfig");
            merchantMarkersConfigGetter = configClass.getMethod("getInstance");
            merchantMarkersAlwaysShowField = configClass.getField("alwaysShow");
            merchantMarkersShowArrowField = configClass.getField("showArrow");
            merchantMarkersIconScaleField = configClass.getField("iconScale");
            merchantMarkersVerticalOffsetField = configClass.getField("verticalOffset");
         }

         if (merchantMarkersKeyField == null) {
            Class<?> merchantMarkersClass = Class.forName("com.anthonyhilyard.merchantmarkers.MerchantMarkers");
            merchantMarkersKeyField = merchantMarkersClass.getField("showMarkers");
         }

         return merchantMarkersConfigGetter.invoke(null);
      } catch (ReflectiveOperationException | RuntimeException ignored) {
         return null;
      }
   }

   private static boolean getBooleanConfig(Object config, Field field, boolean fallback) {
      return getSupplierValue(config, field) instanceof Boolean bool ? bool : fallback;
   }

   private static int getIntConfig(Object config, Field field, int fallback) {
      return getSupplierValue(config, field) instanceof Number number ? number.intValue() : fallback;
   }

   private static double getDoubleConfig(Object config, Field field, double fallback) {
      return getSupplierValue(config, field) instanceof Number number ? number.doubleValue() : fallback;
   }

   private static Object getSupplierValue(Object config, Field field) {
      if (config != null && field != null) {
         try {
            return field.get(config) instanceof Supplier<?> typedSupplier ? typedSupplier.get() : null;
         } catch (ReflectiveOperationException | RuntimeException ignored) {
            return null;
         }
      } else {
         return null;
      }
   }

   private static void renderIcon(
      ResourceLocation icon, PoseStack poseStack, int x, int y, int width, int height, float u0, float u1, float v0, float v1, float alpha
   ) {
      Matrix4f matrix = poseStack.last().pose();
      Lighting.setupForFlatItems();
      Minecraft.getInstance().getTextureManager().getTexture(icon).setFilter(false, false);
      RenderSystem.setShaderTexture(0, icon);
      RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
      BufferBuilder builder = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
      builder.addVertex(matrix, x, y + height, 0.0F).setUv(u0, v1).setColor(1.0F, 1.0F, 1.0F, alpha);
      builder.addVertex(matrix, x + width, y + height, 0.0F).setUv(u1, v1).setColor(1.0F, 1.0F, 1.0F, alpha);
      builder.addVertex(matrix, x + width, y, 0.0F).setUv(u1, v0).setColor(1.0F, 1.0F, 1.0F, alpha);
      builder.addVertex(matrix, x, y, 0.0F).setUv(u0, v0).setColor(1.0F, 1.0F, 1.0F, alpha);
      BufferUploader.drawWithShader(builder.buildOrThrow());
   }
}
