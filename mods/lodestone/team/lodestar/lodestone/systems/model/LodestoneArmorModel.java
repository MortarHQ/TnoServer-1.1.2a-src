package team.lodestar.lodestone.systems.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

public class LodestoneArmorModel extends HumanoidModel<LivingEntity> {
   public EquipmentSlot slot;
   public ModelPart root;
   public ModelPart head;
   public ModelPart body;
   public ModelPart leftArm;
   public ModelPart rightArm;
   public ModelPart leggings;
   public ModelPart leftLegging;
   public ModelPart rightLegging;
   public ModelPart leftFoot;
   public ModelPart rightFoot;

   public LodestoneArmorModel(ModelPart root) {
      super(root);
      this.root = root;
      this.head = root.getChild("head");
      this.body = root.getChild("body");
      this.leggings = root.getChild("leggings");
      this.leftArm = root.getChild("left_arm");
      this.rightArm = root.getChild("right_arm");
      this.leftLegging = root.getChild("left_legging");
      this.rightLegging = root.getChild("right_legging");
      this.leftFoot = root.getChild("left_foot");
      this.rightFoot = root.getChild("right_foot");
   }

   public static PartDefinition createHumanoidAlias(MeshDefinition mesh) {
      PartDefinition root = mesh.getRoot();
      root.addOrReplaceChild("body", new CubeListBuilder(), PartPose.ZERO);
      root.addOrReplaceChild("leggings", new CubeListBuilder(), PartPose.ZERO);
      root.addOrReplaceChild("head", new CubeListBuilder(), PartPose.ZERO);
      root.addOrReplaceChild("left_legging", new CubeListBuilder(), PartPose.ZERO);
      root.addOrReplaceChild("left_foot", new CubeListBuilder(), PartPose.ZERO);
      root.addOrReplaceChild("right_legging", new CubeListBuilder(), PartPose.ZERO);
      root.addOrReplaceChild("right_foot", new CubeListBuilder(), PartPose.ZERO);
      root.addOrReplaceChild("left_arm", new CubeListBuilder(), PartPose.ZERO);
      root.addOrReplaceChild("right_arm", new CubeListBuilder(), PartPose.ZERO);
      return root;
   }

   public static LayerDefinition createArmorModel(LodestoneArmorModel.ILodestoneArmorModelBuilder modelBuilder) {
      MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0.0F), 0.0F);
      PartDefinition root = createHumanoidAlias(mesh);
      PartDefinition body = root.getChild("body");
      PartDefinition leggings = root.getChild("leggings");
      PartDefinition right_legging = root.getChild("right_legging");
      PartDefinition left_legging = root.getChild("left_legging");
      PartDefinition right_foot = root.getChild("right_foot");
      PartDefinition left_foot = root.getChild("left_foot");
      PartDefinition right_arm = root.getChild("right_arm");
      PartDefinition left_arm = root.getChild("left_arm");
      PartDefinition head = root.getChild("head");
      return modelBuilder.createArmorLayer(mesh, root, body, leggings, right_legging, left_legging, right_foot, left_foot, right_arm, left_arm, head);
   }

   protected Iterable<ModelPart> headParts() {
      return this.slot == EquipmentSlot.HEAD ? ImmutableList.of(this.head) : ImmutableList.of();
   }

   protected Iterable<ModelPart> bodyParts() {
      if (this.slot == EquipmentSlot.CHEST) {
         return ImmutableList.of(this.body, this.leftArm, this.rightArm);
      } else if (this.slot == EquipmentSlot.LEGS) {
         return ImmutableList.of(this.leftLegging, this.rightLegging, this.leggings);
      } else {
         return this.slot == EquipmentSlot.FEET ? ImmutableList.of(this.leftFoot, this.rightFoot) : ImmutableList.of();
      }
   }

   public void renderToBuffer(PoseStack matrixStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int p_350361_) {
      if (this.slot == EquipmentSlot.LEGS) {
         this.leggings.copyFrom(this.body);
         this.leftLegging.copyFrom(this.leftLeg);
         this.rightLegging.copyFrom(this.rightLeg);
      }

      super.renderToBuffer(matrixStack, vertexConsumer, packedLight, packedOverlay, p_350361_);
   }

   public void copyFromDefault(HumanoidModel model) {
      this.leggings.copyFrom(model.body);
      this.body.copyFrom(model.body);
      this.head.copyFrom(model.head);
      this.leftArm.copyFrom(model.leftArm);
      this.rightArm.copyFrom(model.rightArm);
      this.leftLegging.copyFrom(this.leftLeg);
      this.rightLegging.copyFrom(this.rightLeg);
      this.leftFoot.copyFrom(this.leftLeg);
      this.rightFoot.copyFrom(this.rightLeg);
   }

   public interface ILodestoneArmorModelBuilder {
      LayerDefinition createArmorLayer(
         MeshDefinition var1,
         PartDefinition var2,
         PartDefinition var3,
         PartDefinition var4,
         PartDefinition var5,
         PartDefinition var6,
         PartDefinition var7,
         PartDefinition var8,
         PartDefinition var9,
         PartDefinition var10,
         PartDefinition var11
      );
   }
}
