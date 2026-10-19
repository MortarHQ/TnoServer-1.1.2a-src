package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class AmaramberFireBlock extends BaseFireBlock {
   public static final MapCodec<AmaramberFireBlock> CODEC = simpleCodec(AmaramberFireBlock::new);

   public MapCodec<AmaramberFireBlock> codec() {
      return CODEC;
   }

   public AmaramberFireBlock(Properties properties) {
      super(properties, 1.0F);
   }

   protected void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
      entity.hurt(level.damageSources().inFire(), 1.0F);
      if (entity instanceof LivingEntity living) {
         living.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100));
      }
   }

   protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
      return this.canSurvive(state, level, currentPos) ? this.defaultBlockState() : Blocks.AIR.defaultBlockState();
   }

   protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      return canSurviveOnBlock(level.getBlockState(pos.below()));
   }

   public static boolean canSurviveOnBlock(BlockState state) {
      return state.is(ESTags.Blocks.AMARAMBER_FIRE_SURVIVES_ON);
   }

   protected boolean canBurn(BlockState state) {
      return true;
   }
}
