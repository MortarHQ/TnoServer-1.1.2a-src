package com.chronodawn.blocks;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class UnstableFungus extends Block {
   private static final Random RANDOM = new Random();
   private static final int EFFECT_DURATION_TICKS = 100;
   private static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 11.0, 12.0);

   public UnstableFungus(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return Properties.of().mapColor(MapColor.COLOR_PURPLE).strength(0.0F, 0.0F).sound(SoundType.FUNGUS).noCollission();
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
      super.entityInside(state, level, pos, entity);
      if (!level.isClientSide() && entity instanceof LivingEntity livingEntity) {
         if (!livingEntity.hasEffect(MobEffects.MOVEMENT_SPEED) && !livingEntity.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
            MobEffectInstance effect;
            if (RANDOM.nextBoolean()) {
               effect = new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 0);
            } else {
               effect = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 0);
            }

            livingEntity.addEffect(effect);
         }
      }
   }
}
