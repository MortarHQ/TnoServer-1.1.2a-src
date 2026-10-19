package com.github.wal_bos.moreskills.client.renderer;

import com.github.wal_bos.moreskills.world.entity.AvalonKnightEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public final class AvalonKnightRenderer extends LivingEntityRenderer<AvalonKnightEntity, PlayerModel<AvalonKnightEntity>> {
   private static final ResourceLocation FALLBACK = ResourceLocation.withDefaultNamespace("textures/entity/player/wide/steve.png");
   private static final ResourceLocation CHEST = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/entity/arthur_knight_chestplate.png");
   private static final ResourceLocation LEGS = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/entity/arthur_knight_leggings.png");
   private static final ResourceLocation BOOTS = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/entity/arthur_knight_boots.png");

   public AvalonKnightRenderer(Context context) {
      super(context, new PlayerModel(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
      ((PlayerModel)this.getModel()).setAllVisible(true);
      this.addLayer(new ItemInHandLayer(this, context.getItemInHandRenderer()));
      this.addLayer(new AvalonKnightRenderer.ArthurArmorLayer(this));
      this.addLayer(new AvalonKnightRenderer.GoldenOuterLayer(this));
   }

   public ResourceLocation getTextureLocation(AvalonKnightEntity entity) {
      return getClientOwner(entity) instanceof AbstractClientPlayer player ? player.getSkin().texture() : FALLBACK;
   }

   protected void scale(AvalonKnightEntity entity, PoseStack poseStack, float partialTickTime) {
      float scale = 1.0F + entity.getVirtue().ordinal() % 3 * 0.015F;
      poseStack.scale(scale, scale, scale);
   }

   private static Player getClientOwner(AvalonKnightEntity entity) {
      UUID uuid = entity.getOwnerUUID();
      if (uuid == null) {
         return null;
      }

      ClientLevel level = Minecraft.getInstance().level;
      return level == null ? null : level.getPlayerByUUID(uuid);
   }

   private static LayerDefinition chestLayer() {
      MeshDefinition mesh = new MeshDefinition();
      PartDefinition root = mesh.getRoot();
      root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
      root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
      root.addOrReplaceChild(
         "body",
         CubeListBuilder.create()
            .texOffs(0, 0)
            .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.76F))
            .texOffs(0, 17)
            .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.8F)),
         PartPose.ZERO
      );
      root.addOrReplaceChild(
         "left_arm",
         CubeListBuilder.create()
            .texOffs(25, 0)
            .mirror()
            .addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F))
            .mirror(false)
            .texOffs(34, 34)
            .mirror()
            .addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.7F))
            .mirror(false)
            .texOffs(17, 34)
            .mirror()
            .addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.81F))
            .mirror(false),
         PartPose.offset(5.0F, 2.0F, 0.0F)
      );
      root.addOrReplaceChild(
         "right_arm",
         CubeListBuilder.create()
            .texOffs(25, 17)
            .addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F))
            .texOffs(42, 0)
            .addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.7F))
            .texOffs(17, 34)
            .addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.81F)),
         PartPose.offset(-5.0F, 2.0F, 0.0F)
      );
      root.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-1.9F, 12.0F, 0.0F));
      root.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(1.9F, 12.0F, 0.0F));
      return LayerDefinition.create(mesh, 64, 64);
   }

   private static LayerDefinition legLayer() {
      MeshDefinition mesh = new MeshDefinition();
      PartDefinition root = mesh.getRoot();
      root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
      root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
      root.addOrReplaceChild(
         "body",
         CubeListBuilder.create()
            .texOffs(0, 0)
            .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.525F))
            .texOffs(0, 17)
            .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.49F))
            .texOffs(25, 0)
            .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.48F)),
         PartPose.ZERO
      );
      root.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-5.0F, 2.0F, 0.0F));
      root.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(5.0F, 2.0F, 0.0F));
      root.addOrReplaceChild(
         "left_leg",
         CubeListBuilder.create()
            .texOffs(42, 17)
            .mirror()
            .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.52F))
            .mirror(false)
            .texOffs(25, 17)
            .mirror()
            .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.51F))
            .mirror(false)
            .texOffs(0, 34)
            .mirror()
            .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .mirror(false),
         PartPose.offset(1.9F, 12.0F, 0.0F)
      );
      root.addOrReplaceChild(
         "right_leg",
         CubeListBuilder.create()
            .texOffs(50, 0)
            .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.52F))
            .texOffs(25, 17)
            .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.51F))
            .texOffs(17, 34)
            .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
         PartPose.offset(-1.9F, 12.0F, 0.0F)
      );
      return LayerDefinition.create(mesh, 128, 128);
   }

   private static LayerDefinition bootsLayer() {
      MeshDefinition mesh = new MeshDefinition();
      PartDefinition root = mesh.getRoot();
      root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
      root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
      root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.ZERO);
      root.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-5.0F, 2.0F, 0.0F));
      root.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(5.0F, 2.0F, 0.0F));
      root.addOrReplaceChild(
         "left_leg",
         CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)).mirror(false),
         PartPose.offset(1.9F, 12.0F, 0.0F)
      );
      root.addOrReplaceChild(
         "right_leg",
         CubeListBuilder.create().texOffs(0, 17).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)),
         PartPose.offset(-1.9F, 12.0F, 0.0F)
      );
      return LayerDefinition.create(mesh, 64, 64);
   }

   private static void sync(HumanoidModel<AvalonKnightEntity> armor, PlayerModel<AvalonKnightEntity> parent) {
      armor.head.copyFrom(parent.head);
      armor.hat.copyFrom(parent.hat);
      armor.body.copyFrom(parent.body);
      armor.rightArm.copyFrom(parent.rightArm);
      armor.leftArm.copyFrom(parent.leftArm);
      armor.rightLeg.copyFrom(parent.rightLeg);
      armor.leftLeg.copyFrom(parent.leftLeg);
   }

   private static final class ArthurArmorLayer extends RenderLayer<AvalonKnightEntity, PlayerModel<AvalonKnightEntity>> {
      private final HumanoidModel<AvalonKnightEntity> chest = new HumanoidModel(AvalonKnightRenderer.chestLayer().bakeRoot());
      private final HumanoidModel<AvalonKnightEntity> legs = new HumanoidModel(AvalonKnightRenderer.legLayer().bakeRoot());
      private final HumanoidModel<AvalonKnightEntity> boots = new HumanoidModel(AvalonKnightRenderer.bootsLayer().bakeRoot());

      private ArthurArmorLayer(AvalonKnightRenderer renderer) {
         super(renderer);
      }

      public void render(
         PoseStack poseStack,
         MultiBufferSource buffer,
         int packedLight,
         AvalonKnightEntity entity,
         float limbSwing,
         float limbSwingAmount,
         float partialTick,
         float ageInTicks,
         float netHeadYaw,
         float headPitch
      ) {
         PlayerModel<AvalonKnightEntity> parent = (PlayerModel<AvalonKnightEntity>)this.getParentModel();
         AvalonKnightRenderer.sync(this.chest, parent);
         AvalonKnightRenderer.sync(this.legs, parent);
         AvalonKnightRenderer.sync(this.boots, parent);
         int overlay = LivingEntityRenderer.getOverlayCoords(entity, 0.0F);
         this.chest.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityCutoutNoCull(AvalonKnightRenderer.CHEST)), packedLight, overlay, -1);
         this.legs.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityCutoutNoCull(AvalonKnightRenderer.LEGS)), packedLight, overlay, -1);
         this.boots.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityCutoutNoCull(AvalonKnightRenderer.BOOTS)), packedLight, overlay, -1);
      }
   }

   private static final class GoldenOuterLayer extends RenderLayer<AvalonKnightEntity, PlayerModel<AvalonKnightEntity>> {
      private final AvalonKnightRenderer renderer;

      private GoldenOuterLayer(AvalonKnightRenderer renderer) {
         super(renderer);
         this.renderer = renderer;
      }

      public void render(
         PoseStack poseStack,
         MultiBufferSource buffer,
         int packedLight,
         AvalonKnightEntity entity,
         float limbSwing,
         float limbSwingAmount,
         float partialTick,
         float ageInTicks,
         float netHeadYaw,
         float headPitch
      ) {
         ResourceLocation texture = this.renderer.getTextureLocation(entity);
         ((PlayerModel)this.getParentModel()).setAllVisible(true);
         ((PlayerModel)this.getParentModel())
            .renderToBuffer(
               poseStack,
               buffer.getBuffer(RenderType.entityTranslucentEmissive(texture)),
               15728880,
               LivingEntityRenderer.getOverlayCoords(entity, 0.0F),
               1124061530
            );
      }
   }
}
