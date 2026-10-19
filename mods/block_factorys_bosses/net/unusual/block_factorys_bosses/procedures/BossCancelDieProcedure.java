package net.unusual.block_factorys_bosses.procedures;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.entity.boss.AbstractBossEntity;
import net.unusual.block_factorys_bosses.entity.boss.dragon.boss.InfernalDragonEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.YetiEntity;
import org.jetbrains.annotations.NotNull;

public class BossCancelDieProcedure {
   private static void execute(@NotNull AbstractBossEntity boss, int dieAnimtime, @Nullable ResourceLocation deathSound) {
      boss.getEntityData().set(AbstractBossEntity.DATA_DIE_ANIMTIME, dieAnimtime);
      if (deathSound != null) {
         boss.level()
            .playSound(
               null,
               BlockPos.containing(boss.getX(), boss.getY(), boss.getZ()),
               (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(deathSound),
               SoundSource.HOSTILE,
               8.0F,
               1.0F
            );
      }
   }

   public static void execute(@NotNull AbstractBossEntity entity) {
      if (entity.isState("dead") || entity.isState("fake_dead")) {
         switch (entity) {
            case InfernalDragonEntity infernalDragonEntity:
               execute(entity, 88, null);
               break;
            case YetiEntity yetiEntity:
               execute(entity, 60, BossesRise.prefix("yeti_death"));
               break;
            default:
         }
      }
   }
}
