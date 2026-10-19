package net.blay09.mods.waystones.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.blay09.mods.waystones.block.PortstoneBlock;
import net.blay09.mods.waystones.block.entity.PortstoneBlockEntity;
import net.blay09.mods.waystones.client.ModRenderers;
import net.blay09.mods.waystones.config.WaystonesConfig;
import net.blay09.mods.waystones.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public class PortstoneRenderer implements BlockEntityRenderer<PortstoneBlockEntity> {
   private static final Material MATERIAL = new Material(TextureAtlas.LOCATION_BLOCKS, ResourceLocation.withDefaultNamespace("waystone_overlays/portstone"));
   private static ItemStack warpStoneItem;
   private final PortstoneModel model;

   public PortstoneRenderer(Context context) {
      this.model = new PortstoneModel(context.bakeLayer(ModRenderers.portstoneModel));
   }

   public void render(
      PortstoneBlockEntity tileEntity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int combinedLightIn, int combinedOverlayIn
   ) {
      Level level = tileEntity.getLevel();
      BlockState state = tileEntity.getBlockState();
      if (level != null && state.getValue(PortstoneBlock.HALF) == DoubleBlockHalf.LOWER) {
         Direction facing = (Direction)state.getValue(PortstoneBlock.FACING);
         if (warpStoneItem == null) {
            warpStoneItem = new ItemStack(ModItems.warpStone);
            level.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolder(Enchantments.UNBREAKING).ifPresent(it -> warpStoneItem.enchant(it, 1));
         }

         DyeColor color = ((PortstoneBlock)state.getBlock()).getColor();
         if (color != null) {
            poseStack.pushPose();
            poseStack.translate(0.5F, 0.0F, 0.5F);
            poseStack.mulPose(Axis.YN.rotationDegrees(facing.toYRot()));
            poseStack.mulPose(Axis.XN.rotationDegrees(180.0F));
            poseStack.translate(0.0F, -2.0F, 0.0F);
            float scale = 1.01F;
            poseStack.scale(0.5F, 0.5F, 0.5F);
            poseStack.scale(scale, scale, scale);
            VertexConsumer vertexBuilder = MATERIAL.buffer(buffer, RenderType::entityCutout);
            int light = WaystonesConfig.getActive().client.disableTextGlow ? combinedLightIn : 15728880;
            int overlay = WaystonesConfig.getActive().client.disableTextGlow ? combinedOverlayIn : OverlayTexture.NO_OVERLAY;
            this.model.renderToBuffer(poseStack, vertexBuilder, light, overlay, color.getTextureDiffuseColor());
            poseStack.popPose();
         }

         poseStack.pushPose();
         poseStack.translate(0.5F, 1.0F, 0.5F);
         poseStack.mulPose(Axis.YN.rotationDegrees(facing.toYRot()));
         poseStack.translate(0.0F, 0.0F, 0.15F);
         poseStack.mulPose(Axis.XN.rotationDegrees(25.0F));
         poseStack.scale(0.5F, 0.5F, 0.5F);
         poseStack.translate(0.03125F, 0.0F, 0.0F);
         Minecraft.getInstance()
            .getItemRenderer()
            .renderStatic(warpStoneItem, ItemDisplayContext.FIXED, combinedLightIn, combinedOverlayIn, poseStack, buffer, level, 0);
         poseStack.popPose();
      }
   }
}
