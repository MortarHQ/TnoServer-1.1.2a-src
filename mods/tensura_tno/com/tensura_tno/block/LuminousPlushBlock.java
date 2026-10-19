package com.tensura_tno.block;

import com.mojang.serialization.MapCodec;
import com.tensura_tno.block.entity.RimuruPlushBlockEntity;
import com.tensura_tno.registry.TensuraTNOBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class LuminousPlushBlock extends RimuruPlushBlock {
   public static final MapCodec<LuminousPlushBlock> CODEC = simpleCodec(LuminousPlushBlock::new);
   public static final float MODEL_SCALE = 0.56F;

   public LuminousPlushBlock() {
      this(Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).sound(SoundType.WOOL).strength(0.8F).noOcclusion());
   }

   private LuminousPlushBlock(Properties properties) {
      super(properties);
   }

   @Override
   protected MapCodec<? extends RimuruPlushBlock> codec() {
      return CODEC;
   }

   @Override
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new RimuruPlushBlockEntity((BlockEntityType<?>)TensuraTNOBlockEntities.LUMINOUS_PLUSH.get(), pos, state, "animation.luminous.");
   }

   @Override
   protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return LuminousPlushShapes.get(getRotationStep(state), (Boolean)state.getValue(SITTING));
   }
}
