package com.github.L_Ender.cataclysm.client.render.blockentity;

import com.github.L_Ender.cataclysm.blockentities.Door_Of_Seal_BlockEntity;
import com.github.L_Ender.cataclysm.blocks.Door_of_Seal_Block;
import com.github.L_Ender.cataclysm.client.model.block.Door_Of_Seal_Model;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Door_Of_Seal_Renderer implements BlockEntityRenderer<Door_Of_Seal_BlockEntity> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/block/door_of_seal.png");
   private static final Door_Of_Seal_Model MODEL = new Door_Of_Seal_Model();

   public Door_Of_Seal_Renderer(Context rendererDispatcherIn) {
   }

   public boolean shouldRenderOffScreen(Door_Of_Seal_BlockEntity p_112138_) {
      return true;
   }

   public int getViewDistance() {
      return 256;
   }

   public boolean shouldRender(Door_Of_Seal_BlockEntity entity, Vec3 p_173532_) {
      return Vec3.atCenterOf(entity.getBlockPos()).multiply(1.0, 0.0, 1.0).closerThan(p_173532_.multiply(1.0, 0.0, 1.0), this.getViewDistance());
   }

   public AABB getRenderBoundingBox(Door_Of_Seal_BlockEntity blockEntity) {
      BlockPos pos = blockEntity.getBlockPos();
      return new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 3.0, pos.getY() + 8, pos.getZ() + 3.0);
   }

   public void render(Door_Of_Seal_BlockEntity entity, float delta, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int overlay) {
      Door_of_Seal_Block.Door_Of_Seal_Part doubleblockhalf = (Door_of_Seal_Block.Door_Of_Seal_Part)entity.getBlockState().getValue(Door_of_Seal_Block.PART);
      int Y = (Integer)entity.getBlockState().getValue(Door_of_Seal_Block.Y_OFFSET);
      if (doubleblockhalf == Door_of_Seal_Block.Door_Of_Seal_Part.CENTER && Y == 0) {
         poseStack.pushPose();
         Direction dir = (Direction)entity.getBlockState().getValue(Door_of_Seal_Block.FACING);
         if (dir == Direction.NORTH) {
            poseStack.translate(0.5, 1.501F, 0.5);
         } else if (dir == Direction.EAST) {
            poseStack.translate(0.5F, 1.501F, 0.5F);
         } else if (dir == Direction.SOUTH) {
            poseStack.translate(0.5, 1.501F, 0.5);
         } else if (dir == Direction.WEST) {
            poseStack.translate(0.5F, 1.501F, 0.5F);
         }

         poseStack.mulPose(dir.getOpposite().getRotation());
         poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
         MODEL.animate(entity, delta);
         MODEL.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), packedLight, overlay);
         poseStack.popPose();
      }
   }
}
