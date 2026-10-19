package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import twilightforest.entity.monster.UpperGoblinKnight;

public class UpperGoblinKnightRenderer<T extends UpperGoblinKnight, M extends HumanoidModel<T>> extends TFBipedRenderer<T, M> {
   public UpperGoblinKnightRenderer(Context context, M model, float shadowSize) {
      super(context, model, shadowSize, "doublegoblin.png");
   }

   protected void setupRotations(T entity, PoseStack stack, float ageInTicks, float rotationYaw, float partialTicks, float scale) {
      super.setupRotations(entity, stack, ageInTicks, rotationYaw, partialTicks, scale);
      if (entity.heavySpearTimer > 0) {
         stack.mulPose(Axis.XP.rotationDegrees(this.getPitchForAttack(60.0F - entity.heavySpearTimer + partialTicks)));
      }
   }

   private float getPitchForAttack(float attackTime) {
      if (attackTime <= 10.0F) {
         return attackTime * 3.0F;
      } else if (attackTime > 10.0F && attackTime <= 30.0F) {
         return 30.0F;
      } else if (attackTime > 30.0F && attackTime <= 33.0F) {
         return (attackTime - 30.0F) * -25.0F + 30.0F;
      } else if (attackTime > 33.0F && attackTime <= 50.0F) {
         return -45.0F;
      } else {
         return attackTime > 50.0F && attackTime <= 60.0F ? (10.0F - (attackTime - 50.0F)) * -4.5F : 0.0F;
      }
   }
}
