package net.blay09.mods.waystones.block.entity;

import net.blay09.mods.balm.api.block.entity.CustomRenderBoundingBox;
import net.blay09.mods.balm.common.BalmBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class PortstoneBlockEntity extends BalmBlockEntity implements CustomRenderBoundingBox {
   public PortstoneBlockEntity(BlockPos worldPosition, BlockState state) {
      super((BlockEntityType)ModBlockEntities.portstone.get(), worldPosition, state);
   }

   public AABB getRenderBoundingBox() {
      return new AABB(
         this.worldPosition.getX(),
         this.worldPosition.getY(),
         this.worldPosition.getZ(),
         this.worldPosition.getX() + 1,
         this.worldPosition.getY() + 2,
         this.worldPosition.getZ() + 1
      );
   }
}
