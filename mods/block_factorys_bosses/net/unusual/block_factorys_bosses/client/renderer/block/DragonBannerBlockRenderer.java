package net.unusual.block_factorys_bosses.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.block.DragonBannerBlock;
import net.unusual.block_factorys_bosses.block.entity.DragonBannerBlockEntity;
import net.unusual.block_factorys_bosses.client.model.ModelDragonBanner;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class DragonBannerBlockRenderer implements BlockEntityRenderer<DragonBannerBlockEntity> {
   public static final ResourceLocation TEXTURE = BossesRise.prefix("textures/block/dragon_banner.png");
   private final ModelDragonBanner model;

   public DragonBannerBlockRenderer(Context context) {
      this.model = new ModelDragonBanner(context.bakeLayer(ModelDragonBanner.LAYER_LOCATION));
   }

   public void render(DragonBannerBlockEntity entity, float partialTick, PoseStack stack, MultiBufferSource buffer, int light, int overlay) {
      stack.pushPose();
      stack.translate(0.5F, 0.4F, 0.5F);
      stack.mulPose(Axis.YP.rotationDegrees(-((Direction)entity.getBlockState().getValue(DragonBannerBlock.FACING)).toYRot() - 180.0F));
      float swing = entity.getLevel() != null ? (float)entity.getLevel().getGameTime() + partialTick : 0.0F;
      stack.mulPose(Axis.XP.rotationDegrees((float)(Math.sin(swing * 0.03) * 3.0)));
      VertexConsumer consumer = buffer.getBuffer(this.model.renderType(TEXTURE));
      this.model.renderToBuffer(stack, consumer, light, overlay, -1);
      stack.popPose();
   }

   public int getViewDistance() {
      return 256;
   }

   public AABB getRenderBoundingBox(DragonBannerBlockEntity blockEntity) {
      return super.getRenderBoundingBox(blockEntity).inflate(6.0);
   }
}
