package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import java.util.function.Function;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import twilightforest.entity.monster.HostileWolf;

public class HostileWolfModel<T extends HostileWolf> extends AgeableListModel<T> {
   private final ModelPart head;
   private final ModelPart body;
   private final ModelPart rightHindLeg;
   private final ModelPart leftHindLeg;
   private final ModelPart rightFrontLeg;
   private final ModelPart leftFrontLeg;
   private final ModelPart tail;
   private final ModelPart upperBody;

   public HostileWolfModel(ModelPart root) {
      this(RenderType::entityCutoutNoCull, root);
   }

   public HostileWolfModel(Function<ResourceLocation, RenderType> type, ModelPart root) {
      super(type, false, 5.0F, 2.0F, 2.0F, 2.0F, 24.0F);
      this.head = root.getChild("head");
      this.body = root.getChild("body");
      this.upperBody = root.getChild("upper_body");
      this.rightHindLeg = root.getChild("right_hind_leg");
      this.leftHindLeg = root.getChild("left_hind_leg");
      this.rightFrontLeg = root.getChild("right_front_leg");
      this.leftFrontLeg = root.getChild("left_front_leg");
      this.tail = root.getChild("tail");
   }

   protected Iterable<ModelPart> headParts() {
      return ImmutableList.of(this.head);
   }

   protected Iterable<ModelPart> bodyParts() {
      return ImmutableList.of(this.body, this.rightHindLeg, this.leftHindLeg, this.rightFrontLeg, this.leftFrontLeg, this.tail, this.upperBody);
   }

   public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTicks) {
      if (entity.isAggressive()) {
         this.tail.yRot = 0.0F;
      } else {
         this.tail.yRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
      }

      this.body.setPos(0.0F, 14.0F, 2.0F);
      this.body.xRot = (float) (Math.PI / 2);
      this.upperBody.setPos(-1.0F, 14.0F, -3.0F);
      this.upperBody.xRot = this.body.xRot;
      this.tail.setPos(-1.0F, 12.0F, 8.0F);
      this.rightHindLeg.setPos(-2.5F, 16.0F, 7.0F);
      this.leftHindLeg.setPos(0.5F, 16.0F, 7.0F);
      this.rightFrontLeg.setPos(-2.5F, 16.0F, -4.0F);
      this.leftFrontLeg.setPos(0.5F, 16.0F, -4.0F);
      this.rightHindLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
      this.leftHindLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
      this.rightFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
      this.leftFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.head.xRot = headPitch * (float) (Math.PI / 180.0);
      this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0);
      this.tail.xRot = ageInTicks;
   }
}
