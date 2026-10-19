package com.github.L_Ender.cataclysm.blocks;

import com.github.L_Ender.cataclysm.blockentities.AltarOfVoid_Block_Entity;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import com.mojang.serialization.MapCodec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Altar_Of_Void_Block extends BaseEntityBlock {
   public static final MapCodec<Altar_Of_Void_Block> CODEC = simpleCodec(Altar_Of_Void_Block::new);
   private static final VoxelShape BASE = Block.box(1.0, 0.0, 1.0, 15.0, 4.0, 15.0);
   private static final VoxelShape MID = Block.box(2.0, 4.0, 2.0, 14.0, 10.0, 14.0);
   private static final VoxelShape TOP = Block.box(0.0, 10.0, 0.0, 16.0, 14.0, 16.0);
   private static final VoxelShape AXIS_AABB = Shapes.or(BASE, new VoxelShape[]{MID, TOP});

   public MapCodec<Altar_Of_Void_Block> codec() {
      return CODEC;
   }

   public Altar_Of_Void_Block(Properties p_54257_) {
      super(p_54257_);
   }

   public VoxelShape getShape(BlockState p_48816_, BlockGetter p_48817_, BlockPos p_48818_, CollisionContext p_48819_) {
      return AXIS_AABB;
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new AltarOfVoid_Block_Entity(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_152180_, BlockState p_152181_, BlockEntityType<T> p_152182_) {
      return createTickerHelper(p_152182_, (BlockEntityType)ModTileentites.ALTAR_OF_VOID.get(), AltarOfVoid_Block_Entity::commonTick);
   }
}
