package net.witherspawnanimation.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class WitherSpawnAnimationModSounds {
   public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(Registries.SOUND_EVENT, "wither_spawn_animation");
   public static final DeferredHolder<SoundEvent, SoundEvent> WITHER_1 = REGISTRY.register(
      "wither_1", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("wither_spawn_animation", "wither_1"))
   );
   public static final DeferredHolder<SoundEvent, SoundEvent> WITHER_2 = REGISTRY.register(
      "wither_2", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("wither_spawn_animation", "wither_2"))
   );
   public static final DeferredHolder<SoundEvent, SoundEvent> WITHER_3 = REGISTRY.register(
      "wither_3", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("wither_spawn_animation", "wither_3"))
   );
   public static final DeferredHolder<SoundEvent, SoundEvent> INVERSE_SMASH = REGISTRY.register(
      "inverse_smash", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("wither_spawn_animation", "inverse_smash"))
   );
   public static final DeferredHolder<SoundEvent, SoundEvent> SMASH = REGISTRY.register(
      "smash", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("wither_spawn_animation", "smash"))
   );
   public static final DeferredHolder<SoundEvent, SoundEvent> STAR = REGISTRY.register(
      "star", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("wither_spawn_animation", "star"))
   );
   public static final DeferredHolder<SoundEvent, SoundEvent> TREMBLE = REGISTRY.register(
      "tremble", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("wither_spawn_animation", "tremble"))
   );
   public static final DeferredHolder<SoundEvent, SoundEvent> WITHER_SPAWN_MOD = REGISTRY.register(
      "wither_spawn_mod", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("wither_spawn_animation", "wither_spawn_mod"))
   );
}
