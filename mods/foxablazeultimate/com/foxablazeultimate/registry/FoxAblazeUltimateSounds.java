package com.foxablazeultimate.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class FoxAblazeUltimateSounds {
   public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, "foxablazeultimate");
   public static final DeferredHolder<SoundEvent, SoundEvent> RAPHAEL_FUSION_DECLARATION = SOUND_EVENTS.register(
      "raphael.fusion_declaration",
      () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "raphael.fusion_declaration"))
   );
   public static final DeferredHolder<SoundEvent, SoundEvent> BEELZEBUB_FUSION_DECLARATION = SOUND_EVENTS.register(
      "beelzebub.fusion_declaration",
      () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "beelzebub.fusion_declaration"))
   );
   public static final DeferredHolder<SoundEvent, SoundEvent> URIEL_SKILL_DECLARATION = SOUND_EVENTS.register(
      "uriel.skill_declaration",
      () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "uriel.skill_declaration"))
   );
   public static final DeferredHolder<SoundEvent, SoundEvent> CIEL_AWAKENING_DECLARATION = SOUND_EVENTS.register(
      "ciel.awakening_declaration",
      () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "ciel.awakening_declaration"))
   );
   public static final DeferredHolder<SoundEvent, SoundEvent> HELL_SNIPER_CIRCLE_APPEAR = SOUND_EVENTS.register(
      "hell_sniper.circle_appear",
      () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "hell_sniper.circle_appear"))
   );
   public static final DeferredHolder<SoundEvent, SoundEvent> HELL_SNIPER_FIRING_CHARGE = SOUND_EVENTS.register(
      "hell_sniper.firing_charge",
      () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "hell_sniper.firing_charge"))
   );

   private FoxAblazeUltimateSounds() {
   }

   public static void register(IEventBus modBus) {
      SOUND_EVENTS.register(modBus);
   }
}
