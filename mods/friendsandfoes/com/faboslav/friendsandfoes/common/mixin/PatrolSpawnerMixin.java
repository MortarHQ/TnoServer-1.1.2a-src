package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.PatrolSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PatrolSpawner.class)
public final class PatrolSpawnerMixin {
   boolean friendsandfoes$isBiomeSpecificIllagerSpawned = false;

   @ModifyVariable(method = "spawnPatrolMember", ordinal = 0, at = @At("LOAD"))
   private PatrollingMonster friendsandfoes$modifyPatrolEntity(
      PatrollingMonster patrolEntity, ServerLevel world, BlockPos pos, RandomSource random, boolean captain
   ) {
      Holder<Biome> biomeEntry = world.getBiome(pos);
      if (!this.friendsandfoes$isBiomeSpecificIllagerSpawned) {
         if (biomeEntry.is(FriendsAndFoesTags.HAS_ILLUSIONER)) {
            patrolEntity = (PatrollingMonster)((EntityType)FriendsAndFoesEntityTypes.ILLUSIONER.get()).create(world);
         } else if (biomeEntry.is(FriendsAndFoesTags.HAS_ICEOLOGER)) {
            patrolEntity = (PatrollingMonster)((EntityType)FriendsAndFoesEntityTypes.ICEOLOGER.get()).create(world);
         }
      }

      return patrolEntity;
   }

   @Inject(method = "tick", at = @At("RETURN"))
   private void friendsandfoes$resetBiomeSpecificIllagerSpawnFlag(
      ServerLevel world, boolean spawnMonsters, boolean spawnAnimals, CallbackInfoReturnable<Integer> ci
   ) {
      Integer spawnerPatrolMembersCount = (Integer)ci.getReturnValue();
      if (spawnerPatrolMembersCount > 0) {
         this.friendsandfoes$isBiomeSpecificIllagerSpawned = false;
      }
   }
}
