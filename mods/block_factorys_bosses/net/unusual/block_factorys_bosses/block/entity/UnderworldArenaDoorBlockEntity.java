package net.unusual.block_factorys_bosses.block.entity;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.unusual.block_factorys_bosses.init.BossesRiseBlockEntities;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class UnderworldArenaDoorBlockEntity extends HugeDoorBlockEntity {
   public UnderworldArenaDoorBlockEntity(BlockPos pos, BlockState blockState) {
      super((BlockEntityType<? extends HugeDoorBlockEntity>)BossesRiseBlockEntities.UNDERWORLD_ARENA_DOOR.get(), pos, blockState);
   }
}
