package com.shin.tensura_kumodesu.wrapper;

import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.Races;
import io.github.manasmods.manascore.race.impl.TickingRace;
import java.util.Optional;
import net.minecraft.network.chat.MutableComponent;

public class PreviewRacesWrapper implements Races {
   private final Races original;
   private final ManasRace previewRace;

   public PreviewRacesWrapper(Races original, ManasRace previewRace) {
      this.original = original;
      this.previewRace = previewRace;
   }

   public Optional<ManasRaceInstance> getRace() {
      return Optional.of(this.previewRace.createDefaultInstance());
   }

   public boolean startHeldAbility() {
      return this.original.startHeldAbility();
   }

   public void interruptHeldAbility() {
   }

   public boolean releaseHeldAbility() {
      return this.original.releaseHeldAbility();
   }

   public TickingRace getHeldAbility() {
      return this.original.getHeldAbility();
   }

   public boolean setRace(ManasRaceInstance instance, boolean evolution, boolean teleportToSpawn, MutableComponent component) {
      return this.original.setRace(instance, evolution, teleportToSpawn, component);
   }

   public void markDirty() {
      this.original.markDirty();
   }
}
