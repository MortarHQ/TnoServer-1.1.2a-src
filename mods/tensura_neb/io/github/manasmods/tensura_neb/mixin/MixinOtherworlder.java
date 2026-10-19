package io.github.manasmods.tensura_neb.mixin;

import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.tensura.entity.human.KyoyaTachinbanaEntity;
import io.github.manasmods.tensura.entity.human.OtherworlderEntity;
import io.github.manasmods.tensura_neb.NebConfig;
import io.github.manasmods.tensura_neb.entity.CarrionEntity;
import io.github.manasmods.tensura_neb.entity.LuminousValentineEntity;
import io.github.manasmods.tensura_neb.entity.RimuruOgreFightEntity;
import io.github.manasmods.tensura_neb.registry.NebEntityTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KyoyaTachinbanaEntity.class)
public abstract class MixinOtherworlder extends OtherworlderEntity {
   @Shadow
   protected abstract boolean canSpawnSpecialVariant(MobSpawnType var1);

   public MixinOtherworlder(EntityType<? extends OtherworlderEntity> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
   }

   @Inject(at = @At("HEAD"), method = "finalizeSpawn", cancellable = true, remap = false)
   private void finalizeSpawn(
      ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, SpawnGroupData pSpawnData, CallbackInfoReturnable<SpawnGroupData> cir
   ) {
      if (this.canSpawnSpecialVariant(pReason)) {
         NebConfig CONFIG = (NebConfig)ConfigRegistry.getConfig(NebConfig.class);
         if (CONFIG == null) {
            return;
         }

         if (this.tensura_neb$rollChance(CONFIG.rimuruChance, pLevel.getRandom())) {
            RimuruOgreFightEntity entity = new RimuruOgreFightEntity(
               (EntityType<? extends RimuruOgreFightEntity>)NebEntityTypes.RIMURU_OGRE_FIGHT.get(), pLevel.getLevel()
            );
            entity.setPos(this.getX(), this.getY(), this.getZ());
            entity.finalizeSpawn(pLevel, pLevel.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.NATURAL, null);
            pLevel.addFreshEntity(entity);
            this.discard();
            cir.setReturnValue(super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData));
            cir.cancel();
         } else if (this.tensura_neb$rollChance(CONFIG.carrionChance, pLevel.getRandom())) {
            CarrionEntity entity = new CarrionEntity((EntityType<? extends CarrionEntity>)NebEntityTypes.CARRION.get(), pLevel.getLevel());
            entity.setPos(this.getX(), this.getY(), this.getZ());
            entity.finalizeSpawn(pLevel, pLevel.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.NATURAL, null);
            pLevel.addFreshEntity(entity);
            this.discard();
            cir.setReturnValue(super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData));
            cir.cancel();
         } else if (this.tensura_neb$rollChance(CONFIG.luminousChance, pLevel.getRandom())) {
            LuminousValentineEntity entity = new LuminousValentineEntity(
               (EntityType<? extends LuminousValentineEntity>)NebEntityTypes.LUMINOUS_VALENTINE.get(), pLevel.getLevel()
            );
            entity.setPos(this.getX(), this.getY(), this.getZ());
            entity.finalizeSpawn(pLevel, pLevel.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.NATURAL, null);
            pLevel.addFreshEntity(entity);
            this.discard();
            cir.setReturnValue(super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData));
            cir.cancel();
         }
      }
   }

   @Unique
   private boolean tensura_neb$rollChance(int rolls, RandomSource randomSource) {
      return rolls <= 0 ? false : randomSource.nextInt(rolls) == 0;
   }
}
