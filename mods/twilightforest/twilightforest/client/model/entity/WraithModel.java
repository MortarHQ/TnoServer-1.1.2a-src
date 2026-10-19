package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ARGB32;
import twilightforest.entity.monster.Wraith;

public class WraithModel extends HumanoidModel<Wraith> {
   private final ModelPart dress;

   public WraithModel(ModelPart root) {
      super(root, RenderType::entityTranslucent);
      this.dress = root.getChild("dress");
   }

   public static LayerDefinition create() {
      MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
      PartDefinition partdefinition = meshdefinition.getRoot();
      partdefinition.addOrReplaceChild(
         "right_arm", CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-5.0F, 2.0F, 0.0F)
      );
      partdefinition.addOrReplaceChild(
         "left_arm", CubeListBuilder.create().mirror().texOffs(0, 16).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(5.0F, 2.0F, 0.0F)
      );
      partdefinition.addOrReplaceChild("dress", CubeListBuilder.create().texOffs(40, 16).addBox(-4.0F, 12.0F, -2.0F, 8.0F, 12.0F, 4.0F), PartPose.ZERO);
      return LayerDefinition.create(meshdefinition, 64, 32);
   }

   protected Iterable<ModelPart> headParts() {
      return ImmutableList.of(this.head, this.hat);
   }

   protected Iterable<ModelPart> bodyParts() {
      return ImmutableList.of(this.body, this.rightArm, this.leftArm, this.dress);
   }

   public void renderToBuffer(PoseStack stack, VertexConsumer consumer, int light, int overlay, int color) {
      super.renderToBuffer(
         stack, consumer, light, overlay, ARGB32.color((int)(ARGB32.alpha(color) * 0.6F), ARGB32.red(color), ARGB32.green(color), ARGB32.blue(color))
      );
   }

   public void setupAnim(Wraith entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      float var8 = Mth.sin(this.attackTime * (float) Math.PI);
      float var9 = Mth.sin((1.0F - (1.0F - this.attackTime) * (1.0F - this.attackTime)) * (float) Math.PI);
      this.rightArm.zRot = 0.0F;
      this.leftArm.zRot = 0.0F;
      this.rightArm.yRot = -(0.1F - var8 * 0.6F);
      this.leftArm.yRot = 0.1F - var8 * 0.6F;
      this.rightArm.xRot = (float) (-Math.PI / 2);
      this.leftArm.xRot = (float) (-Math.PI / 2);
      this.rightArm.xRot -= var8 * 1.2F - var9 * 0.4F;
      this.leftArm.xRot -= var8 * 1.2F - var9 * 0.4F;
      AnimationUtils.bobArms(this.rightArm, this.leftArm, ageInTicks);
   }
}
