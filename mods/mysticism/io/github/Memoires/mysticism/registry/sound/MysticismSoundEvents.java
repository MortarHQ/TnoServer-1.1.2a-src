package io.github.Memoires.mysticism.registry.sound;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class MysticismSoundEvents {
   private static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create("mysticism", Registries.SOUND_EVENT);
   public static final RegistrySupplier<SoundEvent> COUNTER = register("skill.counter");
   public static final RegistrySupplier<SoundEvent> BULLET = register("skill.bullet");
   public static final RegistrySupplier<SoundEvent> BULLET_PUNCH = register("skill.bullet_punch");
   public static final RegistrySupplier<SoundEvent> BUTCHER_GUI_CONFIRM = register("skill.butcher_gui_confirm");
   public static final RegistrySupplier<SoundEvent> BUTCHER_STAB = register("skill.butcher_stab");
   public static final RegistrySupplier<SoundEvent> CLANGOROUS_SOUL = register("skill.clangorous_soul");
   public static final RegistrySupplier<SoundEvent> EYE_OF_INSIGHT = register("skill.eye_of_insight");
   public static final RegistrySupplier<SoundEvent> LIGHTNING_MODE = register("skill.lightning_mode");
   public static final RegistrySupplier<SoundEvent> SCYTHE_SLASH = register("skill.scythe_slash");
   public static final RegistrySupplier<SoundEvent> SENTRY_SHOOT = register("skill.sentry_shoot");
   public static final RegistrySupplier<SoundEvent> MISSILE_SHOOT = register("skill.missile_shoot");
   public static final RegistrySupplier<SoundEvent> DISPENSER_HEAL = register("skill.dispenser_heal");

   private static RegistrySupplier<SoundEvent> register(String id) {
      return SOUND_EVENTS.register(id, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("mysticism", id)));
   }

   public static void init() {
      SOUND_EVENTS.register();
   }
}
