package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.ESModelUtil;
import cn.leolezury.eternalstarlight.common.client.model.entity.ChainOfSoulsModel;
import cn.leolezury.eternalstarlight.common.entity.projectile.ChainOfSouls;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class ChainOfSoulsRenderer extends EntityRenderer<ChainOfSouls> {
   private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/chain_of_souls.png");
   private static final ResourceLocation TIP_TEXTURE = EternalStarlight.id("textures/entity/chain_of_souls_tip.png");
   private final ChainOfSoulsModel<ChainOfSouls> model;

   public ChainOfSoulsRenderer(Context context) {
      super(context);
      this.model = new ChainOfSoulsModel<>(context.bakeLayer(ChainOfSoulsModel.LAYER_LOCATION));
   }

   public void render(ChainOfSouls chain, float yaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light) {
      Player player = chain.getPlayerOwner();
      if (player != null) {
         stack.pushPose();
         Entity target = chain.getTarget();
         boolean attachedToBlock = chain.reachedTarget();
         Vec3 handPos = this.getPlayerHandPos(player, partialTicks);
         Vec3 endPos = new Vec3(
            Mth.lerp(partialTicks, chain.xo, chain.getX()), Mth.lerp(partialTicks, chain.yo, chain.getY()), Mth.lerp(partialTicks, chain.zo, chain.getZ())
         );
         if (chain.isValidTarget(target)) {
            Vec3 targetPos = new Vec3(
               Mth.lerp(partialTicks, target.xo, target.getX()),
               Mth.lerp(partialTicks, target.yo, target.getY()) + target.getBbHeight() / 2.0F,
               Mth.lerp(partialTicks, target.zo, target.getZ())
            );
            Vec3 diff = targetPos.subtract(endPos);
            stack.translate(diff.x(), diff.y(), diff.z());
            endPos = targetPos;
            attachedToBlock = false;
         }

         Vec3 offset = handPos.subtract(endPos);
         float length = (float)offset.length();
         float yRot = ESMathUtil.positionToYaw(offset);
         float xRot = ESMathUtil.positionToPitch(offset);
         if (attachedToBlock) {
            stack.pushPose();
         }

         stack.mulPose(new Quaternionf().rotationX((float) (Math.PI / 2)));
         stack.mulPose(new Quaternionf().rotationZ((yRot - 90.0F) * (float) (Math.PI / 180.0)));
         stack.mulPose(new Quaternionf().rotationX(-xRot * (float) (Math.PI / 180.0)));
         VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(ENTITY_TEXTURE));
         Matrix4f pose = stack.last().pose();
         vertexConsumer.addVertex(pose, -0.5F, 0.0F, 0.0F)
            .setColor(-1)
            .setUv(0.0F, 0.0F)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(15728880)
            .setNormal(0.0F, 1.0F, 0.0F);
         vertexConsumer.addVertex(pose, 0.5F, 0.0F, 0.0F)
            .setColor(-1)
            .setUv(0.5F, 0.0F)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(15728880)
            .setNormal(0.0F, 1.0F, 0.0F);
         vertexConsumer.addVertex(pose, 0.5F, length, 0.0F)
            .setColor(-1)
            .setUv(0.5F, length / 2.0F)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(15728880)
            .setNormal(0.0F, 1.0F, 0.0F);
         vertexConsumer.addVertex(pose, -0.5F, length, 0.0F)
            .setColor(-1)
            .setUv(0.0F, length / 2.0F)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(15728880)
            .setNormal(0.0F, 1.0F, 0.0F);
         vertexConsumer.addVertex(pose, 0.0F, 0.0F, -0.5F)
            .setColor(-1)
            .setUv(0.5F, 0.0F)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(15728880)
            .setNormal(0.0F, 1.0F, 0.0F);
         vertexConsumer.addVertex(pose, 0.0F, 0.0F, 0.5F)
            .setColor(-1)
            .setUv(1.0F, 0.0F)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(15728880)
            .setNormal(0.0F, 1.0F, 0.0F);
         vertexConsumer.addVertex(pose, 0.0F, length, 0.5F)
            .setColor(-1)
            .setUv(1.0F, length / 2.0F)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(15728880)
            .setNormal(0.0F, 1.0F, 0.0F);
         vertexConsumer.addVertex(pose, 0.0F, length, -0.5F)
            .setColor(-1)
            .setUv(0.5F, length / 2.0F)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(15728880)
            .setNormal(0.0F, 1.0F, 0.0F);
         if (attachedToBlock) {
            stack.popPose();
            stack.mulPose(chain.getTipDirection().getOpposite().getRotation());
         }

         stack.scale(-1.0F, 1.0F, 1.0F);
         stack.translate(0.0F, -1.5F, 0.0F);
         RenderType renderType = this.model.renderType(this.getTextureLocation(chain));
         vertexConsumer = buffer.getBuffer(renderType);
         this.model.renderToBuffer(stack, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
         stack.popPose();
      }

      super.render(chain, yaw, partialTicks, stack, buffer, light);
   }

   private Vec3 getPlayerHandPos(Player player, float partialTicks) {
      int arm = player.getMainArm() == HumanoidArm.RIGHT ? 1 : -1;
      ItemStack mainHandItem = player.getMainHandItem();
      if (!mainHandItem.is(ESItems.CHAIN_OF_SOULS.get())) {
         arm = -arm;
      }

      if (this.entityRenderDispatcher.options.getCameraType().isFirstPerson() && player == Minecraft.getInstance().player) {
         double fovFactor = 960.0 / ((Integer)this.entityRenderDispatcher.options.fov().get()).intValue();
         Vec3 vec3 = this.entityRenderDispatcher.camera.getNearPlane().getPointOnPlane(arm * 1.25F, -1.25F).scale(fovFactor);
         return player.getEyePosition(partialTicks).add(vec3);
      }

      Optional<Vec3> handPos = ESModelUtil.getThirdPersonPlayerHandPosition(
         player,
         this.entityRenderDispatcher,
         Mth.lerp(partialTicks, player.yBodyRotO, player.yBodyRot),
         partialTicks,
         mainHandItem.is(ESItems.CHAIN_OF_SOULS.get()) ? player.getMainArm() : player.getMainArm().getOpposite(),
         new Vec3(0.0, 0.6, -0.15)
      );
      if (handPos.isPresent()) {
         return handPos.get();
      }

      float yaw = Mth.lerp(partialTicks, player.yBodyRotO, player.yBodyRot) * (float) (Math.PI / 180.0);
      double sin = Mth.sin(yaw);
      double cos = Mth.cos(yaw);
      float playerScale = player.getScale();
      double sideOffset = arm * 0.35 * playerScale;
      double forwardOffset = 0.35 * playerScale;
      float crouchingFactor = player.isCrouching() ? -0.1875F : 0.0F;
      return player.getEyePosition(partialTicks)
         .add(-cos * sideOffset - sin * forwardOffset, crouchingFactor - 0.85 * playerScale, -sin * sideOffset + cos * forwardOffset);
   }

   public ResourceLocation getTextureLocation(ChainOfSouls chain) {
      return TIP_TEXTURE;
   }
}
