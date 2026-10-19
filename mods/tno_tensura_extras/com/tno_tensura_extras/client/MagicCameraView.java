package com.tno_tensura_extras.client;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexSorting;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Axis;
import com.tno_tensura_extras.TensuraExtras;
import com.tno_tensura_extras.registry.ExtrasItems;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.GlStateBackup;
import net.neoforged.neoforge.client.event.RenderHandEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.RenderFrameEvent.Post;
import net.neoforged.neoforge.client.event.RenderFrameEvent.Pre;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.lwjgl.opengl.GL11;

public final class MagicCameraView {
   private static final int VIEW_WIDTH = 224;
   private static final int VIEW_HEIGHT = 192;
   private static final ResourceLocation VIEW_TEXTURE = TensuraExtras.id("dynamic/magic_camera_view");
   private static final float TWO_HANDED_RAISE = -0.18F;
   private static final float TWO_HANDED_FORWARD = -0.7F;
   private static final float ARM_YAW = 92.0F;
   private static final float ARM_PITCH = 45.0F;
   private static final float ARM_ROLL = -41.0F;
   private static final float ARM_SPREAD = 0.3F;
   private static final float ARM_DROP = -1.1F;
   private static final float ARM_REACH = 0.45F;
   private static TextureTarget viewTarget;
   private static final Map<InteractionHand, MagicCameraView.DeferredCameraHand> deferredCameraHands = new EnumMap<>(InteractionHand.class);
   private static boolean capturedThisFrame;
   private static boolean afterLevelReached;
   private static boolean handsRenderedThisFrame;

   private MagicCameraView() {
   }

   public static void onRenderFramePre(Pre event) {
      capturedThisFrame = false;
      afterLevelReached = false;
      handsRenderedThisFrame = false;
      deferredCameraHands.clear();
   }

   public static void onRenderHand(RenderHandEvent event) {
      if (!afterLevelReached) {
         Minecraft minecraft = Minecraft.getInstance();
         Player player = minecraft.player;
         if (player != null && isHoldingCamera(player)) {
            boolean camera = event.getItemStack().is(ExtrasItems.MAGIC_CAMERA.get());
            if (camera || isTwoHanded(player)) {
               if (camera) {
                  deferredCameraHands.putIfAbsent(event.getHand(), MagicCameraView.DeferredCameraHand.capture(event));
               }

               event.setCanceled(true);
            }
         }
      }
   }

   public static void onRenderLevelStage(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_LEVEL) {
         afterLevelReached = true;
         Minecraft minecraft = Minecraft.getInstance();
         Player player = minecraft.player;
         if (player != null && isHoldingCamera(player)) {
            if (!capturedThisFrame) {
               ensureInitialized(minecraft);
               copyWorldView(minecraft, minecraft.getMainRenderTarget());
               capturedThisFrame = true;
            }
         }
      }
   }

   public static void onRenderGuiPre(net.neoforged.neoforge.client.event.RenderGuiEvent.Pre event) {
      if (!handsRenderedThisFrame && !deferredCameraHands.isEmpty()) {
         Minecraft minecraft = Minecraft.getInstance();
         Player player = minecraft.player;
         if (minecraft.level != null && player != null && isHoldingCamera(player)) {
            renderDeferredCameraHands(minecraft);
            handsRenderedThisFrame = true;
         }
      }
   }

   public static void onRenderFramePost(Post event) {
      if (!handsRenderedThisFrame && !deferredCameraHands.isEmpty()) {
         Minecraft minecraft = Minecraft.getInstance();
         Player player = minecraft.player;
         if (minecraft.screen == null && minecraft.level != null && player != null && isHoldingCamera(player)) {
            renderDeferredCameraHands(minecraft);
            handsRenderedThisFrame = true;
         }
      }
   }

   public static boolean isReady() {
      return viewTarget != null;
   }

   public static ResourceLocation textureLocation() {
      return VIEW_TEXTURE;
   }

   private static boolean isHoldingCamera(Player player) {
      return player.getMainHandItem().is(ExtrasItems.MAGIC_CAMERA.get()) || player.getOffhandItem().is(ExtrasItems.MAGIC_CAMERA.get());
   }

   private static boolean isTwoHanded(Player player) {
      ItemStack mainHand = player.getMainHandItem();
      ItemStack offHand = player.getOffhandItem();
      return mainHand.is(ExtrasItems.MAGIC_CAMERA.get()) ? offHand.isEmpty() : offHand.is(ExtrasItems.MAGIC_CAMERA.get()) && mainHand.isEmpty();
   }

   private static void ensureInitialized(Minecraft minecraft) {
      if (viewTarget == null) {
         RenderSystem.assertOnRenderThread();
         int previousReadFramebuffer = GlStateManager._getInteger(36010);
         int previousDrawFramebuffer = GlStateManager._getInteger(36006);
         int[] previousViewport = new int[4];
         GL11.glGetIntegerv(2978, previousViewport);
         boolean scissorEnabled = GL11.glIsEnabled(3089);

         try {
            if (scissorEnabled) {
               GlStateManager._disableScissorTest();
            }

            viewTarget = new TextureTarget(224, 192, false, Minecraft.ON_OSX);
            viewTarget.setFilterMode(9729);
            viewTarget.setClearColor(0.11764706F, 0.11764706F, 0.11764706F, 1.0F);
            viewTarget.clear(Minecraft.ON_OSX);
            minecraft.getTextureManager().register(VIEW_TEXTURE, new MagicCameraView.RenderTargetTexture(viewTarget));
         } finally {
            GlStateManager._glBindFramebuffer(36008, previousReadFramebuffer);
            GlStateManager._glBindFramebuffer(36009, previousDrawFramebuffer);
            GlStateManager._viewport(previousViewport[0], previousViewport[1], previousViewport[2], previousViewport[3]);
            if (scissorEnabled) {
               GlStateManager._enableScissorTest();
            }
         }
      }
   }

   private static void copyWorldView(Minecraft minecraft, RenderTarget source) {
      copyFramebuffer(source.frameBufferId, 0, 0, source.viewWidth, source.viewHeight);
      MagicCameraHud.render(minecraft, viewTarget);
      source.bindWrite(true);
   }

   private static void renderDeferredCameraHands(Minecraft minecraft) {
      LocalPlayer player = minecraft.player;
      if (player != null && !player.isScoping()) {
         RenderSystem.assertOnRenderThread();
         int previousReadFramebuffer = GlStateManager._getInteger(36010);
         int previousDrawFramebuffer = GlStateManager._getInteger(36006);
         int[] previousViewport = new int[4];
         GL11.glGetIntegerv(2978, previousViewport);
         int previousActiveTexture = GlStateManager._getInteger(34016);
         Matrix4f previousProjection = new Matrix4f(RenderSystem.getProjectionMatrix());
         VertexSorting previousVertexSorting = RenderSystem.getVertexSorting();
         ShaderInstance previousShader = RenderSystem.getShader();
         int[] previousTextures = new int[12];

         for (int textureUnit = 0; textureUnit < previousTextures.length; textureUnit++) {
            previousTextures[textureUnit] = RenderSystem.getShaderTexture(textureUnit);
         }

         float[] previousShaderColor = (float[])RenderSystem.getShaderColor().clone();
         GlStateBackup stateBackup = new GlStateBackup();
         RenderSystem.backupGlState(stateBackup);
         Matrix4fStack modelViewStack = RenderSystem.getModelViewStack();
         modelViewStack.pushMatrix();

         try {
            minecraft.getMainRenderTarget().bindWrite(true);
            RenderSystem.disableScissor();
            RenderSystem.depthMask(true);
            RenderSystem.colorMask(true, true, true, true);
            RenderSystem.clear(256, Minecraft.ON_OSX);
            RenderSystem.enableDepthTest();
            RenderSystem.depthFunc(515);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            minecraft.gameRenderer.lightTexture().turnOnLightLayer();
            boolean twoHanded = isTwoHanded(player);
            BufferSource bufferSource = minecraft.renderBuffers().bufferSource();

            for (MagicCameraView.DeferredCameraHand hand : deferredCameraHands.values()) {
               RenderSystem.setProjectionMatrix(hand.projection(), hand.vertexSorting());
               modelViewStack.set(hand.modelView());
               RenderSystem.applyModelViewMatrix();
               if (twoHanded) {
                  renderTwoHandedCamera(minecraft, player, bufferSource, hand);
               } else {
                  renderDeferredCameraHand(minecraft, player, bufferSource, hand);
               }
            }

            bufferSource.endBatch();
         } finally {
            minecraft.gameRenderer.lightTexture().turnOffLightLayer();
            modelViewStack.popMatrix();
            RenderSystem.applyModelViewMatrix();
            RenderSystem.setProjectionMatrix(previousProjection, previousVertexSorting);
            RenderSystem.setShaderColor(previousShaderColor[0], previousShaderColor[1], previousShaderColor[2], previousShaderColor[3]);

            for (int textureUnit = 0; textureUnit < previousTextures.length; textureUnit++) {
               RenderSystem.setShaderTexture(textureUnit, previousTextures[textureUnit]);
            }

            RenderSystem.setShader(() -> previousShader);
            RenderSystem.restoreGlState(stateBackup);
            RenderSystem.activeTexture(previousActiveTexture);
            GlStateManager._glBindFramebuffer(36008, previousReadFramebuffer);
            GlStateManager._glBindFramebuffer(36009, previousDrawFramebuffer);
            GlStateManager._viewport(previousViewport[0], previousViewport[1], previousViewport[2], previousViewport[3]);
         }
      }
   }

   private static void renderTwoHandedCamera(Minecraft minecraft, LocalPlayer player, MultiBufferSource bufferSource, MagicCameraView.DeferredCameraHand hand) {
      PoseStack poseStack = hand.createPoseStack();
      poseStack.pushPose();
      float swingRoot = Mth.sqrt(hand.swingProgress());
      float swingDip = -0.2F * Mth.sin(hand.swingProgress() * (float) Math.PI);
      float swingPush = -0.4F * Mth.sin(swingRoot * (float) Math.PI);
      poseStack.translate(0.0F, -swingDip / 2.0F, swingPush);
      poseStack.translate(0.0F, -0.18F + hand.equipProgress() * -1.2F, -0.7F);
      if (!player.isInvisible()) {
         poseStack.pushPose();
         poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
         renderCameraArm(minecraft, player, poseStack, bufferSource, hand.packedLight(), HumanoidArm.RIGHT);
         renderCameraArm(minecraft, player, poseStack, bufferSource, hand.packedLight(), HumanoidArm.LEFT);
         poseStack.popPose();
      }

      minecraft.gameRenderer
         .itemInHandRenderer
         .renderItem(player, hand.stack(), ItemDisplayContext.FIRST_PERSON_RIGHT_HAND, false, poseStack, bufferSource, hand.packedLight());
      poseStack.popPose();
   }

   private static void renderCameraArm(
      Minecraft minecraft, LocalPlayer player, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, HumanoidArm arm
   ) {
      if (minecraft.getEntityRenderDispatcher().getRenderer(player) instanceof PlayerRenderer renderer) {
         int side = arm == HumanoidArm.RIGHT ? 1 : -1;
         poseStack.pushPose();
         poseStack.mulPose(Axis.YP.rotationDegrees(92.0F));
         poseStack.mulPose(Axis.XP.rotationDegrees(45.0F));
         poseStack.mulPose(Axis.ZP.rotationDegrees(side * -41.0F));
         poseStack.translate(side * 0.3F, -1.1F, 0.45F);
         if (arm == HumanoidArm.RIGHT) {
            renderer.renderRightHand(poseStack, bufferSource, packedLight, player);
         } else {
            renderer.renderLeftHand(poseStack, bufferSource, packedLight, player);
         }

         poseStack.popPose();
      }
   }

   private static void renderDeferredCameraHand(
      Minecraft minecraft, LocalPlayer player, MultiBufferSource bufferSource, MagicCameraView.DeferredCameraHand hand
   ) {
      PoseStack poseStack = hand.createPoseStack();
      HumanoidArm arm = hand.hand() == InteractionHand.MAIN_HAND ? player.getMainArm() : player.getMainArm().getOpposite();
      boolean rightArm = arm == HumanoidArm.RIGHT;
      poseStack.pushPose();
      boolean customTransform = IClientItemExtensions.of(hand.stack())
         .applyForgeHandTransform(poseStack, player, arm, hand.stack(), hand.partialTick(), hand.equipProgress(), hand.swingProgress());
      if (!customTransform) {
         if (player.isUsingItem() && player.getUseItemRemainingTicks() > 0 && player.getUsedItemHand() == hand.hand()) {
            applyItemArmTransform(poseStack, arm, hand.equipProgress());
         } else if (player.isAutoSpinAttack()) {
            applyItemArmTransform(poseStack, arm, hand.equipProgress());
            int side = rightArm ? 1 : -1;
            poseStack.translate(side * -0.4F, 0.8F, 0.3F);
            poseStack.mulPose(Axis.YP.rotationDegrees(side * 65.0F));
            poseStack.mulPose(Axis.ZP.rotationDegrees(side * -85.0F));
         } else {
            float swingRoot = Mth.sqrt(hand.swingProgress());
            float x = -0.4F * Mth.sin(swingRoot * (float) Math.PI);
            float y = 0.2F * Mth.sin(swingRoot * (float) (Math.PI * 2));
            float z = -0.2F * Mth.sin(hand.swingProgress() * (float) Math.PI);
            int side = rightArm ? 1 : -1;
            poseStack.translate(side * x, y, z);
            applyItemArmTransform(poseStack, arm, hand.equipProgress());
            applyItemArmAttackTransform(poseStack, arm, hand.swingProgress());
         }
      }

      minecraft.gameRenderer
         .itemInHandRenderer
         .renderItem(
            player,
            hand.stack(),
            rightArm ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND,
            !rightArm,
            poseStack,
            bufferSource,
            hand.packedLight()
         );
      poseStack.popPose();
   }

   private static void applyItemArmTransform(PoseStack poseStack, HumanoidArm arm, float equipProgress) {
      int side = arm == HumanoidArm.RIGHT ? 1 : -1;
      poseStack.translate(side * 0.56F, -0.52F + equipProgress * -0.6F, -0.72F);
   }

   private static void applyItemArmAttackTransform(PoseStack poseStack, HumanoidArm arm, float swingProgress) {
      int side = arm == HumanoidArm.RIGHT ? 1 : -1;
      float swing = Mth.sin(swingProgress * swingProgress * (float) Math.PI);
      poseStack.mulPose(Axis.YP.rotationDegrees(side * (45.0F + swing * -20.0F)));
      float swingRoot = Mth.sin(Mth.sqrt(swingProgress) * (float) Math.PI);
      poseStack.mulPose(Axis.ZP.rotationDegrees(side * swingRoot * -20.0F));
      poseStack.mulPose(Axis.XP.rotationDegrees(swingRoot * -80.0F));
      poseStack.mulPose(Axis.YP.rotationDegrees(side * -45.0F));
   }

   private static void copyFramebuffer(int sourceFramebuffer, int sourceX, int sourceY, int sourceWidth, int sourceHeight) {
      if (sourceWidth > 0 && sourceHeight > 0 && sourceFramebuffer != viewTarget.frameBufferId) {
         double targetAspect = 1.1666666666666667;
         int cropWidth = sourceWidth;
         int cropHeight = sourceHeight;
         if ((double)sourceWidth / sourceHeight > targetAspect) {
            cropWidth = Math.max(1, (int)Math.round(sourceHeight * targetAspect));
         } else {
            cropHeight = Math.max(1, (int)Math.round(sourceWidth / targetAspect));
         }

         int minX = sourceX + (sourceWidth - cropWidth) / 2;
         int minY = sourceY + (sourceHeight - cropHeight) / 2;
         int previousReadFramebuffer = GlStateManager._getInteger(36010);
         int previousDrawFramebuffer = GlStateManager._getInteger(36006);
         int[] previousViewport = new int[4];
         GL11.glGetIntegerv(2978, previousViewport);
         boolean scissorEnabled = GL11.glIsEnabled(3089);
         int sourceReadBuffer = 0;

         try {
            if (scissorEnabled) {
               GlStateManager._disableScissorTest();
            }

            GlStateManager._glBindFramebuffer(36008, sourceFramebuffer);
            sourceReadBuffer = GlStateManager._getInteger(3074);
            GL11.glReadBuffer(sourceFramebuffer == 0 ? 1029 : 36064);
            GlStateManager._glBindFramebuffer(36009, viewTarget.frameBufferId);
            GlStateManager._glBlitFrameBuffer(minX, minY, minX + cropWidth, minY + cropHeight, 0, 0, 224, 192, 16384, 9729);
         } finally {
            GlStateManager._glBindFramebuffer(36008, sourceFramebuffer);
            GL11.glReadBuffer(sourceReadBuffer);
            GlStateManager._glBindFramebuffer(36008, previousReadFramebuffer);
            GlStateManager._glBindFramebuffer(36009, previousDrawFramebuffer);
            GlStateManager._viewport(previousViewport[0], previousViewport[1], previousViewport[2], previousViewport[3]);
            if (scissorEnabled) {
               GlStateManager._enableScissorTest();
            }
         }
      }
   }

   private record DeferredCameraHand(
      InteractionHand hand,
      ItemStack stack,
      int packedLight,
      float partialTick,
      float swingProgress,
      float equipProgress,
      Matrix4f pose,
      Matrix3f normal,
      Matrix4f projection,
      Matrix4f modelView,
      VertexSorting vertexSorting
   ) {
      private static MagicCameraView.DeferredCameraHand capture(RenderHandEvent event) {
         Pose pose = event.getPoseStack().last();
         return new MagicCameraView.DeferredCameraHand(
            event.getHand(),
            event.getItemStack().copy(),
            event.getPackedLight(),
            event.getPartialTick(),
            event.getSwingProgress(),
            event.getEquipProgress(),
            new Matrix4f(pose.pose()),
            new Matrix3f(pose.normal()),
            new Matrix4f(RenderSystem.getProjectionMatrix()),
            new Matrix4f(RenderSystem.getModelViewMatrix()),
            RenderSystem.getVertexSorting()
         );
      }

      private PoseStack createPoseStack() {
         PoseStack poseStack = new PoseStack();
         poseStack.last().pose().set(this.pose);
         poseStack.last().normal().set(this.normal);
         return poseStack;
      }
   }

   private static final class RenderTargetTexture extends AbstractTexture {
      private final RenderTarget target;

      private RenderTargetTexture(RenderTarget target) {
         this.target = target;
      }

      public int getId() {
         RenderSystem.assertOnRenderThreadOrInit();
         return this.target.getColorTextureId();
      }

      public void load(ResourceManager resourceManager) {
      }

      public void releaseId() {
      }

      public void close() {
         this.target.destroyBuffers();
      }
   }
}
