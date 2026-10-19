package com.tensura_tno.client.race;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import io.github.manasmods.tensura.client.screen.ReincarnationScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoObjectRenderer;

public final class PlayerSlimeRenderer extends GeoObjectRenderer<PlayerSlimeAnimatable> {
   private static final float MODEL_WIDTH = 1.75F;
   private static final float PLAYER_STANDING_WIDTH = 0.6F;
   private static final float WORLD_SCALE = 0.34285715F;
   private static final float INVENTORY_SCALE = 0.08F;

   public PlayerSlimeRenderer() {
      super(new PlayerSlimeGeoModel());
   }

   public long getInstanceId(PlayerSlimeAnimatable animatable) {
      return animatable.getInstanceId();
   }

   public void preRender(
      PoseStack poseStack,
      PlayerSlimeAnimatable animatable,
      BakedGeoModel model,
      @Nullable MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int colour
   ) {
      this.objectRenderTranslations = new Matrix4f(poseStack.last().pose());
      Player player = animatable.getPlayer();
      Screen screen = Minecraft.getInstance().screen;
      boolean inInventoryLikeGui = screen instanceof AbstractContainerScreen && !(screen instanceof ReincarnationScreen);
      float scale;
      if (inInventoryLikeGui) {
         scale = 0.08F;
      } else if (player != null) {
         float bodyYaw = Mth.lerp(partialTick, player.yBodyRotO, player.yBodyRot);
         poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - bodyYaw));
         scale = 0.34285715F;
      } else {
         scale = 1.0F;
      }

      this.scaleModelForRender(scale, scale, poseStack, animatable, model, isReRender, partialTick, packedLight, packedOverlay);
   }

   public void renderRecursively(
      PoseStack poseStack,
      PlayerSlimeAnimatable animatable,
      GeoBone bone,
      RenderType renderType,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int colour
   ) {
      if (shouldLockDefaultShape(animatable) && "slime".equals(bone.getName())) {
         bone.updateScale(1.0F, 1.0F, 1.0F);
      }

      super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
   }

   private static boolean shouldLockDefaultShape(PlayerSlimeAnimatable animatable) {
      Player player = animatable.getPlayer();
      return player != null && PlayerSlimeAnimatable.shouldKeepDefaultShape(player);
   }
}
