package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.client.render.IBoneLight;
import com.cerbon.bosses_of_mass_destruction.client.render.IRenderer;
import com.cerbon.cerbons_api.api.static_utilities.MathUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import org.joml.Vector4f;
import software.bernie.geckolib.cache.object.GeoBone;

public class VoidBlossomBoneLight implements IBoneLight, IRenderer<VoidBlossomEntity> {
   private VoidBlossomEntity entity;
   private Float partialTicks;

   @Override
   public int getLightForBone(GeoBone bone, int packedLight) {
      return !bone.getName().contains("Spike") && !bone.getName().contains("Thorn") && !bone.getName().contains("FlowerCenter") ? packedLight : 15728880;
   }

   @Override
   public Vector4f getColorForBone(GeoBone bone, Vector4f rgbaColor) {
      if (this.entity == null) {
         return rgbaColor;
      }

      float partialTicks1 = this.partialTicks != null ? this.partialTicks : 0.0F;
      Vector4f newColor = new Vector4f(rgbaColor.x, rgbaColor.y, rgbaColor.z, rgbaColor.w);
      if (this.entity.isDeadOrDying()) {
         float interceptedTime = MathUtils.ratioLerp(this.entity.deathTime, 0.5F, 70.0F, partialTicks1);
         newColor.mul(1.0F - interceptedTime * 0.5F);
      }

      return newColor;
   }

   public void render(VoidBlossomEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
      this.entity = entity;
      this.partialTicks = partialTicks;
   }
}
