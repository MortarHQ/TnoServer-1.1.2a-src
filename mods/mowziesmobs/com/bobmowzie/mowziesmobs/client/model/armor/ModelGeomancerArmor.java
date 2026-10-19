package com.bobmowzie.mowziesmobs.client.model.armor;

import com.bobmowzie.mowziesmobs.server.item.ItemGeomancerArmor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;

public class ModelGeomancerArmor extends GeoModel<ItemGeomancerArmor> {
   public ResourceLocation getModelResource(ItemGeomancerArmor object) {
      return ResourceLocation.fromNamespaceAndPath("mowziesmobs", "geo/geomancer_armor.geo.json");
   }

   public ResourceLocation getTextureResource(ItemGeomancerArmor object) {
      return ResourceLocation.fromNamespaceAndPath("mowziesmobs", "textures/item/geomancer_armor.png");
   }

   public ResourceLocation getAnimationResource(ItemGeomancerArmor animatable) {
      return ResourceLocation.fromNamespaceAndPath("mowziesmobs", "animations/empty.animation.json");
   }

   public RenderType getRenderType(ItemGeomancerArmor animatable, ResourceLocation texture) {
      return RenderType.entityCutoutNoCull(texture);
   }

   public void setCustomAnimations(ItemGeomancerArmor animatable, long instanceId, AnimationState<ItemGeomancerArmor> animationState) {
      super.setCustomAnimations(animatable, instanceId, animationState);
      Entity entity = (Entity)animationState.getData(DataTickets.ENTITY);
      if (entity instanceof Player playerEntity) {
         GeoBone cloth = (GeoBone)this.getBone("cloth").orElse(null);
         GeoBone backCloth = (GeoBone)this.getBone("backCloth").orElse(null);
         if (cloth != null) {
            boolean flag = playerEntity.getFallFlyingTicks() > 4;
            float f = 1.0F;
            if (flag) {
               f = (float)playerEntity.getDeltaMovement().lengthSqr();
               f /= 0.2F;
               f = f * f * f;
            }

            if (f < 1.0F) {
               f = 1.0F;
            }

            boolean shouldSit = entity.isPassenger() && entity.getVehicle() != null && entity.getVehicle().shouldRiderSit();
            float f8_limbSwingAmount = 0.0F;
            float f5_limbSwing = 0.0F;
            if (!shouldSit && entity.isAlive()) {
               f8_limbSwingAmount = playerEntity.walkAnimation.speed(animationState.getPartialTick());
               f5_limbSwing = playerEntity.walkAnimation.position(animationState.getPartialTick());
               if (playerEntity.isBaby()) {
                  f5_limbSwing *= 3.0F;
               }

               if (f8_limbSwingAmount > 1.0F) {
                  f8_limbSwingAmount = 1.0F;
               }
            }

            cloth.setRotX(Math.abs(Mth.cos(f5_limbSwing * 0.6662F + (float) Math.PI) * 2.0F * f8_limbSwingAmount * 0.5F / f) + f8_limbSwingAmount * 0.5F);
         }

         if (backCloth != null) {
            double d0 = Mth.lerp(animationState.getPartialTick(), playerEntity.xCloakO, playerEntity.xCloak)
               - Mth.lerp(animationState.getPartialTick(), playerEntity.xo, playerEntity.getX());
            double d1 = Mth.lerp(animationState.getPartialTick(), playerEntity.yCloakO, playerEntity.yCloak)
               - Mth.lerp(animationState.getPartialTick(), playerEntity.yo, playerEntity.getY());
            double d2 = Mth.lerp(animationState.getPartialTick(), playerEntity.zCloakO, playerEntity.zCloak)
               - Mth.lerp(animationState.getPartialTick(), playerEntity.zo, playerEntity.getZ());
            float f = Mth.rotLerp(animationState.getPartialTick(), playerEntity.yBodyRotO, playerEntity.yBodyRot);
            double d3 = Mth.sin(f * (float) (Math.PI / 180.0));
            double d4 = -Mth.cos(f * (float) (Math.PI / 180.0));
            float f1 = (float)d1 * 10.0F;
            f1 = Mth.clamp(f1, -6.0F, 32.0F);
            float f2 = (float)(d0 * d3 + d2 * d4) * 100.0F;
            f2 = Mth.clamp(f2, 0.0F, 150.0F);
            float f3 = (float)(d0 * d4 - d2 * d3) * 100.0F;
            f3 = Mth.clamp(f3, -20.0F, 20.0F);
            if (f2 < 0.0F) {
               f2 = 0.0F;
            }

            float f4 = Mth.lerp(animationState.getPartialTick(), playerEntity.oBob, playerEntity.bob);
            f1 += Mth.sin(Mth.lerp(animationState.getPartialTick(), playerEntity.walkDistO, playerEntity.walkDist) * 6.0F) * 32.0F * f4;
            if (playerEntity.isCrouching()) {
               f1 += 25.0F;
            }

            backCloth.setRotX(0.0F - (float)Math.toRadians(6.0F + f2 / 2.0F + f1));
         }
      }
   }
}
