package earth.terrarium.handcrafted.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.teamresourceful.resourcefullib.client.CloseablePoseStack;
import earth.terrarium.handcrafted.common.blocks.crockery.CrockeryBlockEntity;
import earth.terrarium.handcrafted.common.blocks.crockery.CrockeryComboBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;

public class CrockeryRenderer implements BlockEntityRenderer<CrockeryBlockEntity> {
   public void render(CrockeryBlockEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
      if (!entity.getStack().isEmpty()) {
         CloseablePoseStack ignored = new CloseablePoseStack(poseStack);

         try {
            poseStack.translate(0.5, 0.078125, 0.5);
            poseStack.mulPose(Axis.YN.rotationDegrees(((Direction)entity.getBlockState().getValue(CrockeryComboBlock.FACING)).getOpposite().toYRot()));
            poseStack.mulPose(Axis.XP.rotationDegrees(270.0F));
            Minecraft.getInstance()
               .getItemRenderer()
               .renderStatic(
                  entity.getStack(), ItemDisplayContext.GROUND, packedLight, packedOverlay, poseStack, bufferSource, Minecraft.getInstance().level, 0
               );
         } catch (Throwable var11) {
            try {
               ignored.close();
            } catch (Throwable var10) {
               var11.addSuppressed(var10);
            }

            throw var11;
         }

         ignored.close();
      }
   }
}
