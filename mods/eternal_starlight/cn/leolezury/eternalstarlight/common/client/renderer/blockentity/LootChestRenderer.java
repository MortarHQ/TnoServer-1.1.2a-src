package cn.leolezury.eternalstarlight.common.client.renderer.blockentity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.LootChestBlock;
import cn.leolezury.eternalstarlight.common.block.entity.LootChestBlockEntity;
import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.LootChestAnimation;
import cn.leolezury.eternalstarlight.common.util.Easing;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Axis;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ARGB32;

public class LootChestRenderer implements BlockEntityRenderer<LootChestBlockEntity> {
   private final LootChestRenderer.LootChestModel chestModel;
   private static final ResourceLocation CHEST_TEXTURE = EternalStarlight.id("textures/entity/loot_chest/loot_chest.png");
   private static final ResourceLocation CHEST_OUTLINE_TEXTURE = EternalStarlight.id("textures/entity/loot_chest/loot_chest_outline.png");

   public LootChestRenderer(Context context) {
      this.chestModel = new LootChestRenderer.LootChestModel(context.bakeLayer(LootChestRenderer.LootChestModel.LAYER_LOCATION));
   }

   public void render(LootChestBlockEntity blockEntity, float partialTicks, PoseStack stack, MultiBufferSource bufferSource, int light, int overlay) {
      stack.pushPose();
      stack.scale(-1.0F, -1.0F, 1.0F);
      stack.translate(-0.5F, -1.5F, 0.5F);
      stack.mulPose(Axis.YP.rotationDegrees(((Direction)blockEntity.getBlockState().getValue(LootChestBlock.FACING)).toYRot() + 180.0F));
      this.chestModel.root().getAllParts().forEach(ModelPart::resetPose);
      this.chestModel.animate(blockEntity.openAnimationState, LootChestAnimation.OPEN, blockEntity.clientTickCount + partialTicks);
      this.chestModel.animate(blockEntity.closeAnimationState, LootChestAnimation.CLOSE, blockEntity.clientTickCount + partialTicks);
      if (blockEntity.isEjecting() && !blockEntity.openAnimationState.isStarted() && !blockEntity.closeAnimationState.isStarted()) {
         this.chestModel.lid.xRot = (float) (-Math.PI / 2);
      }

      this.chestModel
         .renderToBuffer(stack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(CHEST_TEXTURE)), light, overlay, ARGB32.opaque(blockEntity.getColor()));
      this.chestModel
         .renderToBuffer(
            stack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(CHEST_OUTLINE_TEXTURE)), light, overlay, ARGB32.opaque(blockEntity.getOutlineColor())
         );
      stack.popPose();
      float flashAnimation = Math.max(blockEntity.clientTickCount + partialTicks - blockEntity.flashStartTickCount, 0.0F);
      VertexConsumer consumer = bufferSource.getBuffer(ESRenderType.DRAGON_RAYS_QUADS);
      Pose pose = stack.last();
      if (flashAnimation < 10.0F) {
         float flashProgress = Easing.IN_OUT_SINE.calculate(1.0F - Math.abs(flashAnimation - 5.0F) / 5.0F);
         float flashDistFromCenter = Mth.lerp(flashProgress, flashAnimation < 5.0F ? 0.2F : 0.25F, 0.15F);
         float flashHeight = 0.6F * flashProgress;
         int baseColor = ARGB32.color(
            Math.round(Mth.lerp(flashProgress, 0.5F, 1.0F) * 255.0F), blockEntity.rareFlash ? blockEntity.getRareFlashColor() : blockEntity.getFlashColor()
         );
         int topColor = ARGB32.color(0, blockEntity.rareFlash ? blockEntity.getRareFlashColor() : blockEntity.getFlashColor());
         consumer.addVertex(pose, 0.5F - flashDistFromCenter, 0.5625F, 0.5F - flashDistFromCenter).setColor(baseColor);
         consumer.addVertex(pose, 0.5F - flashDistFromCenter, 0.5625F, 0.5F + flashDistFromCenter).setColor(baseColor);
         consumer.addVertex(pose, 0.5F - flashDistFromCenter, 0.5625F + flashHeight, 0.5F + flashDistFromCenter).setColor(topColor);
         consumer.addVertex(pose, 0.5F - flashDistFromCenter, 0.5625F + flashHeight, 0.5F - flashDistFromCenter).setColor(topColor);
         consumer.addVertex(pose, 0.5F - flashDistFromCenter, 0.5625F, 0.5F - flashDistFromCenter).setColor(baseColor);
         consumer.addVertex(pose, 0.5F + flashDistFromCenter, 0.5625F, 0.5F - flashDistFromCenter).setColor(baseColor);
         consumer.addVertex(pose, 0.5F + flashDistFromCenter, 0.5625F + flashHeight, 0.5F - flashDistFromCenter).setColor(topColor);
         consumer.addVertex(pose, 0.5F - flashDistFromCenter, 0.5625F + flashHeight, 0.5F - flashDistFromCenter).setColor(topColor);
         consumer.addVertex(pose, 0.5F + flashDistFromCenter, 0.5625F, 0.5F - flashDistFromCenter).setColor(baseColor);
         consumer.addVertex(pose, 0.5F + flashDistFromCenter, 0.5625F, 0.5F + flashDistFromCenter).setColor(baseColor);
         consumer.addVertex(pose, 0.5F + flashDistFromCenter, 0.5625F + flashHeight, 0.5F + flashDistFromCenter).setColor(topColor);
         consumer.addVertex(pose, 0.5F + flashDistFromCenter, 0.5625F + flashHeight, 0.5F - flashDistFromCenter).setColor(topColor);
         consumer.addVertex(pose, 0.5F - flashDistFromCenter, 0.5625F, 0.5F + flashDistFromCenter).setColor(baseColor);
         consumer.addVertex(pose, 0.5F + flashDistFromCenter, 0.5625F, 0.5F + flashDistFromCenter).setColor(baseColor);
         consumer.addVertex(pose, 0.5F + flashDistFromCenter, 0.5625F + flashHeight, 0.5F + flashDistFromCenter).setColor(topColor);
         consumer.addVertex(pose, 0.5F - flashDistFromCenter, 0.5625F + flashHeight, 0.5F + flashDistFromCenter).setColor(topColor);
      }
   }

   public boolean shouldRenderOffScreen(LootChestBlockEntity blockEntity) {
      return true;
   }

   public int getViewDistance() {
      return 256;
   }

   public static final class LootChestModel extends Model implements AnimatedModel {
      public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("loot_chest"), "main");
      public final ModelPart root;
      public final ModelPart lid;

      public LootChestModel(ModelPart root) {
         super(RenderType::entityCutoutNoCull);
         this.root = root.getChild("root");
         this.lid = this.root.getChild("lid");
      }

      public static LayerDefinition createLayer() {
         MeshDefinition meshdefinition = new MeshDefinition();
         PartDefinition partdefinition = meshdefinition.getRoot();
         PartDefinition root = partdefinition.addOrReplaceChild(
            "root",
            CubeListBuilder.create()
               .texOffs(0, 21)
               .addBox(-7.0F, -9.0F, -15.0F, 14.0F, 8.0F, 14.0F, new CubeDeformation(0.0F))
               .texOffs(0, 43)
               .addBox(-8.0F, -10.0F, -16.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
               .texOffs(0, 43)
               .mirror()
               .addBox(5.0F, -10.0F, -16.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
               .mirror(false)
               .texOffs(0, 43)
               .addBox(-8.0F, -10.0F, -3.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
               .texOffs(0, 43)
               .mirror()
               .addBox(5.0F, -10.0F, -3.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
               .mirror(false),
            PartPose.offset(0.0F, 24.0F, 8.0F)
         );
         root.addOrReplaceChild(
            "lid",
            CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -7.0F, -14.0F, 14.0F, 7.0F, 14.0F, new CubeDeformation(0.25F)),
            PartPose.offset(0.0F, -8.0F, -1.0F)
         );
         return LayerDefinition.create(meshdefinition, 64, 64);
      }

      @Override
      public ModelPart root() {
         return this.root;
      }

      public void renderToBuffer(PoseStack stack, VertexConsumer consumer, int light, int overlay, int color) {
         this.root.render(stack, consumer, light, overlay, color);
      }
   }
}
