package com.tno_tensura_extras.event;

import com.tno_tensura_extras.entity.DiabloDecoyEntity;
import com.tno_tensura_extras.entity.DiabloEntity;
import com.tno_tensura_extras.registry.ExtrasEntityTypes;
import io.github.manasmods.tensura.registry.entity.MonsterEntityTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;

public final class DiabloSpawnEvents {
   private static final ResourceLocation UNDERWORLD = ResourceLocation.fromNamespaceAndPath("tensura", "hell");
   private static final int SPAWN_DENOMINATOR = 20;

   private DiabloSpawnEvents() {
   }

   public static void onCreateAttributes(EntityAttributeCreationEvent event) {
      event.put(ExtrasEntityTypes.DIABLO.get(), DiabloEntity.createAttributes().build());
      event.put(ExtrasEntityTypes.DIABLO_DECOY.get(), DiabloDecoyEntity.createAttributes().build());
   }

   public static void onFinalizeSpawn(FinalizeSpawnEvent event) {
      Mob original = event.getEntity();
      if (!event.isCanceled()
         && !event.isSpawnCancelled()
         && original.getType() == MonsterEntityTypes.ARCH_DAEMON.get()
         && UNDERWORLD.equals(event.getLevel().getLevel().dimension().location())) {
         MobSpawnType reason = event.getSpawnType();
         if ((reason == MobSpawnType.NATURAL || reason == MobSpawnType.CHUNK_GENERATION) && original.getRandom().nextInt(20) == 0) {
            DiabloEntity diablo = (DiabloEntity)ExtrasEntityTypes.DIABLO.get().create(event.getLevel().getLevel());
            if (diablo != null) {
               diablo.moveTo(original.getX(), original.getY(), original.getZ(), original.getYRot(), original.getXRot());
               EventHooks.finalizeMobSpawn(diablo, event.getLevel(), event.getDifficulty(), reason, null);
               if (!diablo.isSpawnCancelled() && event.getLevel().addFreshEntity(diablo)) {
                  event.setSpawnCancelled(true);
                  event.setCanceled(true);
               }
            }
         }
      }
   }
}
