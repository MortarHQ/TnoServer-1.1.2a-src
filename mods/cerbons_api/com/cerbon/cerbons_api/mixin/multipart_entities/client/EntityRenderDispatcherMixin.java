package com.cerbon.cerbons_api.mixin.multipart_entities.client;

import com.cerbon.cerbons_api.api.multipart_entities.util.CompoundOrientedBox;
import com.cerbon.cerbons_api.api.multipart_entities.util.OrientedBox;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {
   @Inject(
      method = "renderHitbox(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/world/entity/Entity;FFFF)V",
      at = @At("RETURN")
   )
   private static void drawOrientedBoxes(
      PoseStack poseStack, VertexConsumer buffer, Entity entity, float red, float green, float blue, float alpha, CallbackInfo ci
   ) {
      if (entity.getBoundingBox() instanceof CompoundOrientedBox compoundOrientedBox) {
         poseStack.pushPose();
         poseStack.translate(-entity.getX(), -entity.getY(), -entity.getZ());

         for (OrientedBox orientedBox : compoundOrientedBox) {
            poseStack.pushPose();
            Vec3 center = orientedBox.getCenter();
            poseStack.translate(center.x, center.y, center.z);
            poseStack.mulPose(orientedBox.getRotation().toFloatQuat());
            LevelRenderer.renderLineBox(poseStack, buffer, orientedBox.getExtents(), 0.0F, 0.0F, 1.0F, 1.0F);
            poseStack.popPose();
         }

         compoundOrientedBox.toVoxelShape()
            .forAllBoxes(
               (minX, minY, minZ, maxX, maxY, maxZ) -> LevelRenderer.renderLineBox(
                  poseStack, buffer, minX, minY, minZ, maxX, maxY, maxZ, 0.0F, 1.0F, 0.0F, 1.0F
               )
            );
         poseStack.popPose();
      }
   }
}
