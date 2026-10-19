package net.unusual.block_factorys_bosses.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;
import net.unusual.block_factorys_bosses.BossesRise;

public class Modelhelmet<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BossesRise.prefix("modelhelmet"), "main");
   public final ModelPart root_item;
   public final ModelPart head;

   public Modelhelmet(ModelPart root) {
      this.root_item = root.getChild("root_item");
      this.head = this.root_item.getChild("head");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition root_item = partdefinition.addOrReplaceChild("root_item", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
      PartDefinition head = root_item.addOrReplaceChild(
         "head",
         CubeListBuilder.create()
            .texOffs(0, 0)
            .addBox(-4.2F, -34.5F, -4.5F, 9.0F, 7.0F, 9.0F, new CubeDeformation(0.0F))
            .texOffs(44, 57)
            .addBox(3.8F, -37.5F, 0.5F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
            .texOffs(16, 57)
            .addBox(3.8F, -37.5F, 3.5F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(44, 57)
            .mirror()
            .addBox(-5.2F, -37.5F, 0.5F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
            .mirror(false)
            .texOffs(16, 57)
            .mirror()
            .addBox(-5.2F, -37.5F, 3.5F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
            .mirror(false)
            .texOffs(24, 30)
            .addBox(-2.2F, -34.5F, -10.5F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(32, 57)
            .addBox(2.8F, -36.5F, -10.5F, 0.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(32, 57)
            .addBox(-2.2F, -36.5F, -10.5F, 0.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(56, 11)
            .addBox(-2.2F, -29.5F, -10.5F, 5.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 26.0F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 128, 128);
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgb) {
      this.root_item.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.root_item.yRot = netHeadYaw / (180.0F / (float)Math.PI);
      this.root_item.xRot = headPitch / (180.0F / (float)Math.PI);
   }
}
