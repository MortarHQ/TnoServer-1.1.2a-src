package com.tensura_tno.race.fox_spirit;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.Clone;

public class SpiritSummonPocketPersistence {
   private static final String TAG_ROOT = "tensura_tno_spirit_summon";

   public static void onPlayerClone(Clone event) {
      if (event.isWasDeath()) {
         Player oldPlayer = event.getOriginal();
         Player newPlayer = event.getEntity();
         CompoundTag oldData = oldPlayer.getPersistentData().getCompound("tensura_tno_spirit_summon");
         if (!oldData.isEmpty()) {
            newPlayer.getPersistentData().put("tensura_tno_spirit_summon", oldData.copy());
         }
      }
   }
}
