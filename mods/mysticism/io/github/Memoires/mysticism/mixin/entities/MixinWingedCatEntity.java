package io.github.Memoires.mysticism.mixin.entities;

import io.github.Memoires.mysticism.registry.dimension.MysticismDimensions;
import io.github.Memoires.mysticism.registry.world.MysticismBiomes;
import io.github.manasmods.tensura.entity.monster.AkashEntity;
import io.github.manasmods.tensura.entity.monster.WingedCatEntity;
import io.github.manasmods.tensura.entity.template.TensuraTamableEntity;
import io.github.manasmods.tensura.registry.entity.MonsterEntityTypes;
import io.github.manasmods.tensura.registry.entity.TensuraEntityTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WingedCatEntity.class)
public class MixinWingedCatEntity extends TensuraTamableEntity {
   protected MixinWingedCatEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
   }

   @Inject(
      method = "checkSpawnRules(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/entity/MobSpawnType;)Z",
      at = @At("HEAD"),
      cancellable = true
   )
   public void checkSpawnRules(LevelAccessor pLevel, MobSpawnType pSpawnReason, CallbackInfoReturnable<Boolean> cir) {
      if (pLevel instanceof ServerLevel serverLevel && serverLevel.dimension().equals(MysticismDimensions.ELEMENTAL_REALM) && this.blockPosition().getY() < 58) {
         cir.setReturnValue(false);
      }
   }

   @Inject(
      method = "finalizeSpawn(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/world/entity/MobSpawnType;Lnet/minecraft/world/entity/SpawnGroupData;)Lnet/minecraft/world/entity/SpawnGroupData;",
      at = @At("HEAD"),
      cancellable = true
   )
   public void finalizeSpawn(
      ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, SpawnGroupData pSpawnData, CallbackInfoReturnable<SpawnGroupData> cir
   ) {
      if (this.canSpawnSpecialVariant(pReason)
         && pLevel.getBiome(this.blockPosition()).is(MysticismBiomes.SPACE_BIOME)
         && TensuraEntityTypes.rollChance(TensuraEntityTypes.CONFIG.SpecialVariant.greaterSpiritChance, pLevel.getRandom())) {
         AkashEntity spirit = new AkashEntity((EntityType)MonsterEntityTypes.AKASH.get(), this.level());
         spirit.setPos(this.getX(), this.getY(), this.getZ());
         spirit.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData);
         this.level().addFreshEntity(spirit);
         this.setRemoved(RemovalReason.DISCARDED);
         cir.setReturnValue(super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData));
      }
   }
}
