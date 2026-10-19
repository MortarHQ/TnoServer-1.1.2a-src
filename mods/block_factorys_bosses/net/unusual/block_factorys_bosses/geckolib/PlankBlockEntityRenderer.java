package net.unusual.block_factorys_bosses.geckolib;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.block.PlankBlock;
import net.unusual.block_factorys_bosses.block.entity.PlankBlockEntity;
import net.unusual.block_factorys_bosses.geckolib.util.CustomBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PlankBlockEntityRenderer extends GeoBlockRenderer<PlankBlockEntity> {
   public PlankBlockEntityRenderer(Context context) {
      super(new CustomBlockGeoModel<PlankBlockEntity>(BossesRise.prefix("plank")) {
         public ResourceLocation getTextureResource(PlankBlockEntity entity) {
            return BossesRise.prefix("textures/block/plank.png");
         }

         public ResourceLocation getModelResource(PlankBlockEntity animatable) {
            return BossesRise.prefix("geo/block/plank_" + animatable.getBlockState().getValue(PlankBlock.PLANKS) + ".geo.json");
         }

         public ResourceLocation getAnimationResource(PlankBlockEntity animatable) {
            return BossesRise.prefix("animations/block/plank_" + animatable.getBlockState().getValue(PlankBlock.PLANKS) + ".animation.json");
         }
      });
   }

   public AABB getRenderBoundingBox(PlankBlockEntity blockEntity) {
      return super.getRenderBoundingBox(blockEntity).inflate(1.0);
   }
}
