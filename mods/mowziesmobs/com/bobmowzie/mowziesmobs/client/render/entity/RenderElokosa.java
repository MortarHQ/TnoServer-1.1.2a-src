package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.client.model.entity.ModelElokosa;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoBone;
import com.bobmowzie.mowziesmobs.client.render.entity.layer.ElokosaHandSymbolGeoLayer;
import com.bobmowzie.mowziesmobs.client.render.entity.layer.ElokosaTransformGeoLayer;
import com.bobmowzie.mowziesmobs.server.entity.elokosa.EntityElokosa;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class RenderElokosa extends MowzieGeoEntityRenderer<EntityElokosa> {
   public RenderElokosa(Context mgr) {
      super(mgr, new ModelElokosa());
      this.renderLayers.addLayer(new AutoGlowingGeoLayer(this));
      this.renderLayers.addLayer(new ElokosaTransformGeoLayer(this));
      this.renderLayers.addLayer(new ElokosaHandSymbolGeoLayer(this));
      this.shadowRadius = 0.9F;
   }

   @NotNull
   public ResourceLocation getTextureLocation(EntityElokosa entity) {
      return this.getMowzieGeoModel().getTextureResource(entity);
   }

   public void render(EntityElokosa entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
      super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
   }

   public void renderUpdates(EntityElokosa entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
      super.renderUpdates(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
      int index = 1;

      for (Vec3[] droolPos : entity.droolPositions) {
         if (droolPos != null && droolPos.length > 0) {
            MowzieGeoBone droolPosBone = this.getMowzieGeoModel().getMowzieBone("droolPos" + index);
            if (droolPosBone != null) {
               Vector3d worldPos = droolPosBone.getWorldPosition();
               droolPos[0] = new Vec3(worldPos.x, worldPos.y, worldPos.z);
            }
         }

         index++;
      }
   }

   public boolean shouldRender(EntityElokosa entity, Frustum p_114492_, double p_114493_, double p_114494_, double p_114495_) {
      boolean result = super.shouldRender(entity, p_114492_, p_114493_, p_114494_, p_114495_);
      if (!result) {
         int index = 0;
         float[] offsets = new float[]{0.1F, 0.0F, -0.13F, 0.04F, 0.082F, -0.023F, -0.068F};

         for (Vec3[] droolPos : entity.droolPositions) {
            if (droolPos != null && droolPos.length > 0) {
               Vec3 centerPos = new Vec3(entity.getX(), entity.getY() + entity.getBbHeight() / 2.0, entity.getZ());
               Vec3 headPos = centerPos.add(new Vec3(0.72, 0.0, 0.0).yRot((float)Math.toRadians(-entity.yBodyRot - 100.0F)));
               Vec3 offsetPos = headPos.add(
                  new Vec3(0.13 + offsets[index % offsets.length], 0.0, 0.0).yRot(index * (float) (Math.PI * 2) / entity.droolPositions.size())
               );
               droolPos[0] = offsetPos;
            }

            index++;
         }
      }

      return result;
   }

   protected float getShadowRadius(EntityElokosa entity) {
      float whichForm = 0.0F;
      if (this.getMowzieGeoModel().isInitialized()) {
         whichForm = -this.getMowzieGeoModel().getControllerValue("whichFormController");
      }

      if (whichForm <= 0.1F) {
         return entity.getNightForm() ? 0.9F : 0.4F;
      } else {
         return Mth.lerp(whichForm, 0.4F, 0.9F);
      }
   }
}
