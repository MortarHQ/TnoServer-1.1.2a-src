package com.mortal_cultivation.client.renderer;

import com.mortal_cultivation.block.entity.RuneTableBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public final class RuneTableRenderer extends GeoBlockRenderer<RuneTableBlockEntity> {
   public RuneTableRenderer(Context context) {
      super(new DefaultedBlockGeoModel(ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "rune_table")));
      this.addRenderLayer(new RuneTableOrbLayer(this));
      this.addRenderLayer(new RuneTableRuneLayer(this));
   }

   public AABB getRenderBoundingBox(RuneTableBlockEntity blockEntity) {
      return new AABB(blockEntity.getBlockPos()).inflate(2.0);
   }
}
