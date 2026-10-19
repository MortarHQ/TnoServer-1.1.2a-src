package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import org.jetbrains.annotations.Nullable;
import twilightforest.client.model.TFModelLayers;
import twilightforest.client.model.entity.RisingZombieModel;
import twilightforest.entity.monster.RisingZombie;

public class RisingZombieRenderer extends TFBipedRenderer<RisingZombie, RisingZombieModel> {
   public RisingZombieRenderer(Context context) {
      super(
         context,
         new RisingZombieModel(context.bakeLayer(TFModelLayers.RISING_ZOMBIE)),
         new RisingZombieModel(context.bakeLayer(ModelLayers.ZOMBIE_INNER_ARMOR)),
         new RisingZombieModel(context.bakeLayer(ModelLayers.ZOMBIE_OUTER_ARMOR)),
         0.5F,
         "textures/entity/zombie/zombie.png"
      );
   }

   protected float getShadowRadius(RisingZombie entity) {
      return 0.5F * (entity.getRisingTicks() / 130.0F);
   }

   @Nullable
   protected RenderType getRenderType(RisingZombie entity, boolean bodyVisible, boolean translucent, boolean glowing) {
      return translucent
         ? ((RisingZombieModel)this.model).renderType(this.getTextureLocation(entity))
         : super.getRenderType(entity, bodyVisible, translucent, glowing);
   }

   protected void setupRotations(RisingZombie entity, PoseStack stack, float bob, float yBodyRot, float partialTick, float scale) {
      super.setupRotations(entity, stack, bob, yBodyRot, partialTick, scale);
      int tick = entity.getRisingTicks();
      stack.translate(0.0F, -(80.0F - Math.min(80.0F, tick)) / 80.0F, 0.0F);
      stack.translate(0.0F, -(40.0F - Math.min(40.0F, Math.max(0.0F, tick - 80.0F))) / 40.0F, 0.0F);
      float yOff = 1.0F;
      stack.translate(0.0F, 1.0F, 0.0F);
      stack.mulPose(Axis.XP.rotationDegrees(120.0F * (80.0F - Math.min(80.0F, tick)) / 80.0F));
      stack.mulPose(Axis.XP.rotationDegrees(-30.0F * (40.0F - Math.min(40.0F, Math.max(0.0F, tick - 80.0F))) / 40.0F));
      stack.translate(0.0F, -1.0F, 0.0F);
   }

   protected float getFlipDegrees(RisingZombie livingEntity) {
      return 0.0F;
   }
}
