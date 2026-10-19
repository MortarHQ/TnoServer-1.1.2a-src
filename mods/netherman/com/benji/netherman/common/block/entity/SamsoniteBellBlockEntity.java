package com.benji.netherman.common.block.entity;

import com.benji.netherman.common.entity.BellGuardianEntity;
import com.benji.netherman.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BellBlock;
import net.minecraft.world.level.block.entity.BellBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class SamsoniteBellBlockEntity extends BellBlockEntity {
   private int cooldown = 0;

   public SamsoniteBellBlockEntity(BlockPos pos, BlockState state) {
      super(pos, state);
   }

   public BlockEntityType<?> getType() {
      return (BlockEntityType<?>)ModBlockEntities.SAMSONITE_BELL.get();
   }

   public static void tick(Level level, BlockPos pos, BlockState state, SamsoniteBellBlockEntity entity) {
      if (level.isClientSide()) {
         BellBlockEntity.clientTick(level, pos, state, entity);
      } else {
         BellBlockEntity.serverTick(level, pos, state, entity);
         if (entity.cooldown > 0) {
            entity.cooldown--;
            return;
         }

         if (level.getGameTime() % 20L == 0L) {
            AABB playerBox = new AABB(pos).inflate(5.0);
            boolean hasPlayer = !level.getEntitiesOfClass(Player.class, playerBox).isEmpty();
            if (hasPlayer && level.random.nextInt(20) == 0 && state.getBlock() instanceof BellBlock bellBlock) {
               bellBlock.attemptToRing(null, level, pos, null);
               AABB mobBox = new AABB(pos).inflate(50.0);

               for (Player player : level.getEntitiesOfClass(Player.class, playerBox)) {
                  player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 200, 2));
               }

               for (BellGuardianEntity mob : level.getEntitiesOfClass(BellGuardianEntity.class, mobBox)) {
                  mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 2));
               }

               entity.cooldown = 200;
            }
         }
      }
   }
}
