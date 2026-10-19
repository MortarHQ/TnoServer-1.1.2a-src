package org.crypticdev.stextras.registary.sounds;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class STExtrasSoundEvents {
   private static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create("stextras", Registries.SOUND_EVENT);
   public static final RegistrySupplier<SoundEvent> SKILLLOCK_LOCK = register("effect.skilllock.lock");
   public static final RegistrySupplier<SoundEvent> SKILLLOCK_UNLOCK = register("effect.skilllock.unlock");

   private static RegistrySupplier<SoundEvent> register(String id) {
      return SOUND_EVENTS.register(id, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("stextras", id)));
   }

   public static void init() {
      SOUND_EVENTS.register();
   }
}
