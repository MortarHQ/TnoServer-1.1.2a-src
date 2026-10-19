package com.github.L_Ender.cataclysm.client.render.blockentity;

import com.github.L_Ender.cataclysm.blockentities.Statue_Block_Entity;
import com.github.L_Ender.cataclysm.blocks.Statue_Block;
import com.github.L_Ender.cataclysm.client.model.block.Goddess_Statue_Model;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Goddess_Statue_Renderer implements BlockEntityRenderer<Statue_Block_Entity> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/block/goddess_statue.png");
   private static final Goddess_Statue_Model MODEL = new Goddess_Statue_Model();

   public Goddess_Statue_Renderer(Context rendererDispatcherIn) {
   }

   public boolean shouldRenderOffScreen(Statue_Block_Entity p_112138_) {
      return true;
   }

   public int getViewDistance() {
      return 256;
   }

   public boolean shouldRender(Statue_Block_Entity p_173531_, Vec3 p_173532_) {
      return Vec3.atCenterOf(p_173531_.getBlockPos()).multiply(1.0, 0.0, 1.0).closerThan(p_173532_.multiply(1.0, 0.0, 1.0), this.getViewDistance());
   }

   public AABB getRenderBoundingBox(Statue_Block_Entity blockEntity) {
      BlockPos pos = blockEntity.getBlockPos();
      return new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0, pos.getY() + 3.0, pos.getZ() + 1.0);
   }

   public void render(Statue_Block_Entity entity, float delta, PoseStack matrixStackIn, MultiBufferSource buffer, int packedLight, int overlay) {
      DoubleBlockHalf doubleblockhalf = (DoubleBlockHalf)entity.getBlockState().getValue(Statue_Block.HALF);
      if (doubleblockhalf == DoubleBlockHalf.LOWER) {
         matrixStackIn.pushPose();
         float f = ((Direction)entity.getBlockState().getValue(Statue_Block.FACING)).toYRot();
         matrixStackIn.translate(0.5F, 1.5F, 0.5F);
         matrixStackIn.mulPose(Axis.YP.rotationDegrees(-f + 180.0F));
         matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
         MODEL.renderToBuffer(matrixStackIn, buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), packedLight, overlay);
         matrixStackIn.popPose();
      }
   }
}
