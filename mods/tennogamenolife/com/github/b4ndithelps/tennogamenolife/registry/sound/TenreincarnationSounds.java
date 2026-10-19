package com.github.b4ndithelps.tennogamenolife.registry.sound;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TenreincarnationSounds {
   public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, "tennogamenolife");
   public static final DeferredHolder<SoundEvent, SoundEvent> XIUBI_AMBIENT = registerSoundEvent("entity.xiubi.ambient");
   public static final DeferredHolder<SoundEvent, SoundEvent> XIUBI_HURT = registerSoundEvent("entity.xiubi.hurt");
   public static final DeferredHolder<SoundEvent, SoundEvent> DRAGON_ROAR_EXPLOSION = registerSoundEvent("skill.dragon_roar_explosion");
   public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_DISC_GAME_OF_LIFE = registerSoundEvent("music_disc.game_of_life");

   private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(String name) {
      ResourceLocation id = ResourceLocation.fromNamespaceAndPath("tennogamenolife", name);
      return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
   }

   public static void register(IEventBus eventBus) {
      SOUND_EVENTS.register(eventBus);
   }
}
