package cn.leolezury.eternalstarlight.common.mixin.client;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.effect.CrystalInfectionEffect;
import cn.leolezury.eternalstarlight.common.platform.ESClientPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {
   @Unique
   private static final Material ABYSSAL_FIRE_0 = new Material(TextureAtlas.LOCATION_BLOCKS, EternalStarlight.id("block/abyssal_fire_0"));
   @Unique
   private static final Material ABYSSAL_FIRE_1 = new Material(TextureAtlas.LOCATION_BLOCKS, EternalStarlight.id("block/abyssal_fire_1"));
   @Shadow
   private Quaternionf cameraOrientation;

   @Shadow
   private static void fireVertex(Pose pose, VertexConsumer consumer, float x, float y, float z, float texU, float texV) {
   }

   @Inject(
      method = "render(Lnet/minecraft/world/entity/Entity;DDDFFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/renderer/entity/EntityRenderer;render(Lnet/minecraft/world/entity/Entity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"
      )
   )
   private <E extends Entity> void render(
      E entity,
      double xOffset,
      double yOffset,
      double zOffset,
      float delta,
      float yRot,
      PoseStack poseStack,
      MultiBufferSource multiBufferSource,
      int light,
      CallbackInfo ci
   ) {
      if (entity instanceof LivingEntity living && !living.isDeadOrDying()) {
         AttributeInstance instance = living.getAttribute(Attributes.ARMOR);
         if (instance != null && instance.hasModifier(CrystalInfectionEffect.ARMOR_MODIFIER_ID)) {
            AttributeModifier modifier = instance.getModifier(CrystalInfectionEffect.ARMOR_MODIFIER_ID);
            long seed = (long)(Math.pow(living.getId(), 3.0) * 54321.0);
            RandomSource random = RandomSource.create(seed);
            int crystalCount = (int)(living.getBbHeight() / 0.5F) + (modifier == null ? 0 : (int)Math.abs(modifier.amount() * 2.5));

            for (int i = 0; i < crystalCount; i++) {
               poseStack.pushPose();
               float blockX = random.nextFloat() * living.getBbWidth() - living.getBbWidth() / 2.0F;
               float blockY = random.nextFloat() * living.getBbHeight();
               float blockZ = random.nextFloat() * living.getBbWidth() - living.getBbWidth() / 2.0F;
               Vec3 center = new Vec3(0.0, living.getBbHeight() / 2.0F, 0.0);
               Vec3 block = ESMathUtil.lerpVec(0.2F, new Vec3(blockX, blockY, blockZ), center);
               blockX = (float)block.x;
               blockY = (float)block.y;
               blockZ = (float)block.z;
               poseStack.translate(blockX, blockY, blockZ);
               float pitch = ESMathUtil.positionToPitch(center, block);
               float yaw = ESMathUtil.positionToYaw(center, block);
               poseStack.mulPose(new Quaternionf().rotationX((float) (Math.PI / 2)));
               poseStack.mulPose(new Quaternionf().rotationZ((yaw - 90.0F) * (float) (Math.PI / 180.0)));
               poseStack.mulPose(new Quaternionf().rotationX(-pitch * (float) (Math.PI / 180.0)));
               poseStack.scale(living.getBbWidth() / 2.0F, living.getBbWidth() / 2.0F, living.getBbWidth() / 2.0F);
               poseStack.translate(-0.5F, -0.5F, -0.5F);
               ESClientPlatform.INSTANCE
                  .renderBlock(
                     Minecraft.getInstance().getBlockRenderer(),
                     poseStack,
                     multiBufferSource,
                     living.level(),
                     random.nextBoolean()
                        ? ESBlocks.RED_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState()
                        : ESBlocks.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState(),
                     living.blockPosition(),
                     seed
                  );
               poseStack.popPose();
            }
         }
      }
   }

   @Inject(
      method = "render(Lnet/minecraft/world/entity/Entity;DDDFFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;displayFireAnimation()Z", shift = Shift.AFTER)
   )
   private <E extends Entity> void renderFlame(
      E entity, double d, double e, double f, float g, float h, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci
   ) {
      if (ESDataAttachments.ABYSSAL_FIRE_TICKS.getData(entity) > 0) {
         this.renderAbyssalFlame(poseStack, multiBufferSource, entity, Mth.rotationAroundAxis(Mth.Y_AXIS, this.cameraOrientation, new Quaternionf()));
      }
   }

   @Unique
   private void renderAbyssalFlame(PoseStack stack, MultiBufferSource buffer, Entity entity, Quaternionf quaternion) {
      TextureAtlasSprite fireSprite0 = ABYSSAL_FIRE_0.sprite();
      TextureAtlasSprite fireSprite1 = ABYSSAL_FIRE_1.sprite();
      stack.pushPose();
      float f = entity.getBbWidth() * 1.4F;
      stack.scale(f, f, f);
      float f1 = 0.5F;
      float f3 = entity.getBbHeight() / f;
      float f4 = 0.0F;
      stack.mulPose(quaternion);
      stack.translate(0.0F, 0.0F, 0.3F - (int)f3 * 0.02F);
      float f5 = 0.0F;
      int i = 0;
      VertexConsumer consumer = buffer.getBuffer(Sheets.cutoutBlockSheet());
      Pose posestack$pose = stack.last();

      while (f3 > 0.0F) {
         TextureAtlasSprite sprite = i % 2 == 0 ? fireSprite0 : fireSprite1;
         float f6 = sprite.getU0();
         float f7 = sprite.getV0();
         float f8 = sprite.getU1();
         float f9 = sprite.getV1();
         if (i / 2 % 2 == 0) {
            float f10 = f8;
            f8 = f6;
            f6 = f10;
         }

         fireVertex(posestack$pose, consumer, -f1 - 0.0F, 0.0F - f4, f5, f8, f9);
         fireVertex(posestack$pose, consumer, f1 - 0.0F, 0.0F - f4, f5, f6, f9);
         fireVertex(posestack$pose, consumer, f1 - 0.0F, 1.4F - f4, f5, f6, f7);
         fireVertex(posestack$pose, consumer, -f1 - 0.0F, 1.4F - f4, f5, f8, f7);
         f3 -= 0.45F;
         f4 -= 0.45F;
         f1 *= 0.9F;
         f5 -= 0.03F;
         i++;
      }

      stack.popPose();
   }
}
