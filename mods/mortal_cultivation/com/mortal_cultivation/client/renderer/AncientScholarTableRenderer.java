package com.mortal_cultivation.client.renderer;

import com.mortal_cultivation.block.entity.AncientScholarTableBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public final class AncientScholarTableRenderer extends GeoBlockRenderer<AncientScholarTableBlockEntity> {
   public AncientScholarTableRenderer(Context context) {
      super(new DefaultedBlockGeoModel(ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "ancient_scholar_table")));
   }

   public AABB getRenderBoundingBox(AncientScholarTableBlockEntity blockEntity) {
      return new AABB(blockEntity.getBlockPos()).inflate(2.0);
   }
}
