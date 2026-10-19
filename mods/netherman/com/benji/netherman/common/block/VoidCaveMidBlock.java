package com.benji.netherman.common.block;

import com.benji.netherman.init.ModSounds;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class VoidCaveMidBlock extends HorizontalDirectionalBlock {
   public static final MapCodec<VoidCaveMidBlock> CODEC = simpleCodec(VoidCaveMidBlock::new);
   private static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   private static long lastSoundTime = 0L;

   public VoidCaveMidBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH));
   }

   protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
      return CODEC;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING});
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
   }

   public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return Shapes.empty();
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      if (random.nextInt(20) == 0) {
         double x = pos.getX() + random.nextDouble();
         double y = pos.getY() + random.nextDouble() * 4.0;
         double z = pos.getZ() + random.nextDouble();
         level.addParticle(ParticleTypes.FLAME, x, y, z, 0.0, 0.01, 0.0);
      }

      long currentTime = level.getGameTime();
      if (currentTime - lastSoundTime > 160L && random.nextInt(5) == 0) {
         lastSoundTime = currentTime;
         level.playLocalSound(
            pos.getX() + 0.5,
            pos.getY() + 0.5,
            pos.getZ() + 0.5,
            (SoundEvent)ModSounds.RIFTSOUND.get(),
            SoundSource.BLOCKS,
            1.0F,
            random.nextFloat() * 0.2F + 0.9F,
            false
         );
      }
   }

   public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
      if (!level.isClientSide() && entity instanceof LivingEntity livingEntity) {
         livingEntity.hurt(level.damageSources().magic(), 10.0F);
         livingEntity.igniteForSeconds(15.0F);
         livingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, 60, 0, false, false));
      }
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.MODEL;
   }
}
