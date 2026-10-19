package net.unusual.block_factorys_bosses.client.renderer.block;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.world.phys.AABB;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.block.entity.RopeRollBlockEntity;
import net.unusual.block_factorys_bosses.geckolib.util.CustomBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RopeRollBlockEntityRenderer extends GeoBlockRenderer<RopeRollBlockEntity> {
   public RopeRollBlockEntityRenderer(Context context) {
      super(new CustomBlockGeoModel(BossesRise.prefix("rope_roll")));
   }

   public AABB getRenderBoundingBox(RopeRollBlockEntity blockEntity) {
      return super.getRenderBoundingBox(blockEntity).inflate(1.0);
   }
}
