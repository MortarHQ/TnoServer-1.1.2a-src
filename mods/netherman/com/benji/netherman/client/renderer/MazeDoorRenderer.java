package com.benji.netherman.client.renderer;

import com.benji.netherman.NetherExp;
import com.benji.netherman.client.model.MazeDoorModel;
import com.benji.netherman.common.block.entity.MazeDoorBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class MazeDoorRenderer extends GeoBlockRenderer<MazeDoorBlockEntity> {
   public MazeDoorRenderer(Context context) {
      super(new MazeDoorModel());
   }

   public ResourceLocation getTextureLocation(MazeDoorBlockEntity animatable) {
      return animatable.requiresKey ? NetherExp.location("textures/block/maze_door_key.png") : NetherExp.location("textures/block/maze_door.png");
   }

   public AABB getRenderBoundingBox(MazeDoorBlockEntity animatable) {
      return new AABB(animatable.getBlockPos()).inflate(6.0);
   }
}
