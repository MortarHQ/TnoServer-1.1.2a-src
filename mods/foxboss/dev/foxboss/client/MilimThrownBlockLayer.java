package dev.foxboss.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.foxboss.entity.MilimEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

public final class MilimThrownBlockLayer extends BlockAndItemGeoLayer<MilimEntity> {
   public MilimThrownBlockLayer(MilimRenderer renderer) {
      super(renderer);
   }

   protected BlockState getBlockForBone(GeoBone bone, MilimEntity entity) {
      if ("held_item_l".equals(bone.getName()) && entity.action() == 17) {
         BlockState state = entity.getCarriedBlockState();
         return state.isAir() ? null : state;
      } else {
         return null;
      }
   }

   protected void renderBlockForBone(
      PoseStack pose, GeoBone bone, BlockState state, MilimEntity entity, MultiBufferSource source, float partial, int light, int overlay
   ) {
      pose.pushPose();
      pose.translate(-0.5, -0.85, -0.5);
      Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, pose, source, light, overlay);
      pose.popPose();
   }
}
