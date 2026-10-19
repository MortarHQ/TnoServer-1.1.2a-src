package it.hurts.octostudios.rarcompat.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SoundRegistry {
   public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, "rarcompat");
   public static final DeferredHolder<SoundEvent, SoundEvent> NIGHT_VISION_TOGGLE = SOUNDS.register(
      "night_vision_toggle", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("rarcompat", "night_vision_toggle"))
   );

   public static void register(IEventBus bus) {
      SOUNDS.register(bus);
   }
}
