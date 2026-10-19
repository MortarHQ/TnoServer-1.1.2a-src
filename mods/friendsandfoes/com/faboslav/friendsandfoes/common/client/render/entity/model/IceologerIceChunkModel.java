package com.faboslav.friendsandfoes.common.client.render.entity.model;

import com.faboslav.friendsandfoes.common.entity.IceologerIceChunkEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public final class IceologerIceChunkModel<T extends IceologerIceChunkEntity> extends HierarchicalModel<T> {
   private static final String MODEL_PART_FIRST_FULL_BLOCK = "firstFullBlock";
   private static final String MODEL_PART_SECOND_FULL_BLOCK = "secondFullBlock";
   private static final String MODEL_PART_THIRD_FULL_BLOCK = "thirdFullBlock";
   private static final String MODEL_PART_FIRST_VERTICAL_SLAB = "firstVerticalSlab";
   private static final String MODEL_PART_SECOND_VERTICAL_SLAB = "secondVerticalSlab";
   private final ModelPart root;
   private final ModelPart firstFullBlock;
   private final ModelPart secondFullBlock;
   private final ModelPart thirdFullBlock;
   private final ModelPart firstVerticalSlab;
   private final ModelPart secondVerticalSlab;

   public IceologerIceChunkModel(ModelPart root) {
      this.root = root;
      this.firstFullBlock = this.root.getChild("firstFullBlock");
      this.secondFullBlock = this.root.getChild("secondFullBlock");
      this.thirdFullBlock = this.root.getChild("thirdFullBlock");
      this.firstVerticalSlab = this.root.getChild("firstVerticalSlab");
      this.secondVerticalSlab = this.root.getChild("secondVerticalSlab");
   }

   public static LayerDefinition getTexturedModelData() {
      MeshDefinition modelData = new MeshDefinition();
      PartDefinition root = modelData.getRoot();
      root.addOrReplaceChild(
         "firstFullBlock", CubeListBuilder.create().texOffs(0, 0).addBox(-16.0F, 0.0F, -4.0F, 16.0F, 16.0F, 16.0F), PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      root.addOrReplaceChild(
         "secondFullBlock", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, -4.0F, 16.0F, 16.0F, 16.0F), PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      root.addOrReplaceChild(
         "thirdFullBlock", CubeListBuilder.create().texOffs(0, 0).addBox(-16.0F, 0.0F, -20.0F, 16.0F, 16.0F, 16.0F), PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      root.addOrReplaceChild(
         "firstVerticalSlab", CubeListBuilder.create().texOffs(0, 32).addBox(-16.0F, 0.0F, 12.0F, 16.0F, 16.0F, 8.0F), PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      root.addOrReplaceChild(
         "secondVerticalSlab", CubeListBuilder.create().texOffs(0, 32).addBox(-20.0F, 0.0F, -8.0F, 16.0F, 16.0F, 8.0F), PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      return LayerDefinition.create(modelData, 64, 64);
   }

   public ModelPart root() {
      return this.root;
   }

   public void setupAnim(T glare, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
      this.secondVerticalSlab.setRotation(0.0F, -1.5708F, 0.0F);
   }
}
