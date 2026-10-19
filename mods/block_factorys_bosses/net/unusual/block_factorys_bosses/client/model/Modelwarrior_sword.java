package net.unusual.block_factorys_bosses.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.client.animation.swordAnimation;
import net.unusual.block_factorys_bosses.item.WarriorSwordItem;

public class Modelwarrior_sword<T extends Entity> extends HierarchicalModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BossesRise.prefix("modelwarrior_sword"), "main");
   public final ModelPart root;
   public final ModelPart bone;
   public final ModelPart main;

   public Modelwarrior_sword(ModelPart root) {
      this.root = root;
      this.bone = root.getChild("bone");
      this.main = this.bone.getChild("main");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition bone = partdefinition.addOrReplaceChild(
         "bone", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, -1.5708F, 0.0F)
      );
      PartDefinition main = bone.addOrReplaceChild(
         "main",
         CubeListBuilder.create()
            .texOffs(6, 0)
            .addBox(-5.0F, -7.0F, 0.0F, 10.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(6, 4)
            .addBox(-1.0F, -5.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(14, 4)
            .addBox(-3.0F, -7.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(0, 0)
            .addBox(-1.5F, -36.0F, 0.0F, 3.0F, 29.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, -5.0F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 32, 32);
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgb) {
      this.bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
   }

   public void setupItemAnim(String stack, int firstPersonContext, float ageInTicks) {
      if (firstPersonContext > 0) {
         AnimationState idleState = WarriorSwordItem.IDLE_STATES.computeIfAbsent(stack, s -> new AnimationState());
         this.animate(idleState, swordAnimation.IDLE, ageInTicks, 1.0F);
         AnimationState swingState = WarriorSwordItem.SWING_STATES.computeIfAbsent(stack, s -> new AnimationState());
         if (firstPersonContext == 1) {
            this.animate(swingState, swordAnimation.ATTACK_1, ageInTicks, 1.0F);
         }

         if (firstPersonContext == 2) {
            this.animate(swingState, swordAnimation.ATTACK_LEFT_1, ageInTicks, 1.0F);
         }
      }
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public ModelPart root() {
      return this.root;
   }
}
