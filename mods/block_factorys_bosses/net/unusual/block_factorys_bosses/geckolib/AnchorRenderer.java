package net.unusual.block_factorys_bosses.geckolib;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.util.Mth;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.entity.decoration.AnchorEntity;
import net.unusual.block_factorys_bosses.geckolib.util.CustomEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class AnchorRenderer extends GeoEntityRenderer<AnchorEntity> {
   public AnchorRenderer(Context context) {
      super(context, new CustomEntityGeoModel(BossesRise.prefix("anchor")));
   }

   protected void applyRotations(AnchorEntity animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick, float nativeScale) {
      poseStack.mulPose(Axis.YP.rotationDegrees(-animatable.getYRot()));
      float hurtTime = animatable.getHurtTime() - partialTick;
      float damage = animatable.getDamage() - partialTick;
      if (damage < 0.0F) {
         damage = 0.0F;
      }

      if (hurtTime > 0.0F) {
         poseStack.mulPose(Axis.YP.rotationDegrees(Mth.sin(hurtTime) * hurtTime * damage / 25.0F * animatable.getHurtDir()));
      }
   }
}
