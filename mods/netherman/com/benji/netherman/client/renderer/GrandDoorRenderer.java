package com.benji.netherman.client.renderer;

import com.benji.netherman.NetherExp;
import com.benji.netherman.client.layer.GenericEmissiveLayer;
import com.benji.netherman.client.model.GrandDoorModel;
import com.benji.netherman.common.block.entity.GrandDoorBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class GrandDoorRenderer extends GeoBlockRenderer<GrandDoorBlockEntity> {
   public GrandDoorRenderer(Context context) {
      super(new GrandDoorModel());
      this.addRenderLayer(new GenericEmissiveLayer(this, NetherExp.location("textures/block/grand_door_emissive.png")));
   }

   public AABB getRenderBoundingBox(GrandDoorBlockEntity blockEntity) {
      return new AABB(blockEntity.getBlockPos()).inflate(12.0);
   }
}
