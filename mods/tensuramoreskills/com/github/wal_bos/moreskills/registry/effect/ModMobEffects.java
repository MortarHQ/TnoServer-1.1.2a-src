package com.github.wal_bos.moreskills.registry.effect;

import com.github.wal_bos.moreskills.effect.AlbisDiamondDustEffect;
import com.github.wal_bos.moreskills.effect.LifeSeizureEffect;
import com.github.wal_bos.moreskills.effect.NaberiusVisionEffect;
import com.github.wal_bos.moreskills.effect.NaberiusWillEffect;
import com.github.wal_bos.moreskills.effect.OrganDecayEffect;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public final class ModMobEffects {
   public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create("tensuramoreskills", Registries.MOB_EFFECT);
   public static final RegistrySupplier<MobEffect> NABERIUS_WILL = MOB_EFFECTS.register("naberius_will", NaberiusWillEffect::new);
   public static final RegistrySupplier<MobEffect> NABERIUS_VISION = MOB_EFFECTS.register("naberius_vision", NaberiusVisionEffect::new);
   public static final RegistrySupplier<MobEffect> ALBIS_DIAMOND_DUST = MOB_EFFECTS.register("albis_diamond_dust", AlbisDiamondDustEffect::new);
   public static final RegistrySupplier<MobEffect> ORGAN_DECAY = MOB_EFFECTS.register("organ_decay", OrganDecayEffect::new);
   public static final RegistrySupplier<MobEffect> LIFE_SEIZURE = MOB_EFFECTS.register("life_seizure", LifeSeizureEffect::new);
   public static final RegistrySupplier<MobEffect> TEMPORAL_STUTTER = MOB_EFFECTS.register(
      "temporal_stutter",
      () -> new TensuraMobEffect(MobEffectCategory.HARMFUL, 12568533)
         .addAttributeModifier(Attributes.MOVEMENT_SPEED, id("temporal_stutter_speed"), -0.7, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(Attributes.ATTACK_SPEED, id("temporal_stutter_attack"), -0.65, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(Attributes.FLYING_SPEED, id("temporal_stutter_flight"), -0.7, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(TensuraAttributes.CHANT_SPEED, id("temporal_stutter_chant"), -0.55, Operation.ADD_MULTIPLIED_TOTAL)
   );
   public static final RegistrySupplier<MobEffect> BORROWED_SECONDS = MOB_EFFECTS.register(
      "borrowed_seconds",
      () -> new TensuraMobEffect(MobEffectCategory.HARMFUL, 9342620)
         .addAttributeModifier(TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER, id("borrowed_seconds_magicule"), -0.65, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(TensuraAttributes.AURA_REGENERATION_MULTIPLIER, id("borrowed_seconds_aura"), -0.65, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(TensuraAttributes.SPIRITUAL_HEALTH_REGENERATION, id("borrowed_seconds_spiritual"), -0.65, Operation.ADD_MULTIPLIED_TOTAL)
   );
   public static final RegistrySupplier<MobEffect> ETERNAL_MOMENT = MOB_EFFECTS.register(
      "eternal_moment",
      () -> new TensuraMobEffect(MobEffectCategory.BENEFICIAL, 15657160)
         .addAttributeModifier(Attributes.MOVEMENT_SPEED, id("eternal_moment_speed"), 0.12, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(Attributes.ATTACK_SPEED, id("eternal_moment_attack"), 0.18, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(TensuraAttributes.CHANT_SPEED, id("eternal_moment_chant"), 0.25, Operation.ADD_VALUE)
   );
   public static final RegistrySupplier<MobEffect> STILLNESS_OF_THE_KING = MOB_EFFECTS.register(
      "stillness_of_the_king",
      () -> new TensuraMobEffect(MobEffectCategory.BENEFICIAL, 14474470)
         .addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, id("stillness_knockback"), 1.0, Operation.ADD_VALUE)
         .addAttributeModifier(Attributes.ARMOR, id("stillness_armor"), 40.0, Operation.ADD_VALUE)
         .addAttributeModifier(TensuraAttributes.SPIRITUAL_HEALTH_REGENERATION, id("stillness_no_regen"), -1.0, Operation.ADD_MULTIPLIED_TOTAL)
   );
   public static final RegistrySupplier<MobEffect> OBJECT_PERMANENCE = MOB_EFFECTS.register(
      "object_permanence",
      () -> new TensuraMobEffect(MobEffectCategory.HARMFUL, 15592941)
         .addAttributeModifier(Attributes.MOVEMENT_SPEED, id("object_permanence_speed"), -1.0, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(Attributes.FLYING_SPEED, id("object_permanence_fly"), -1.0, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(Attributes.ATTACK_SPEED, id("object_permanence_attack"), -1.0, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, id("object_permanence_knockback"), 1.0, Operation.ADD_VALUE)
         .addAttributeModifier(TensuraAttributes.SPIRITUAL_HEALTH_REGENERATION, id("object_permanence_regen"), -1.0, Operation.ADD_MULTIPLIED_TOTAL)
   );
   public static final RegistrySupplier<MobEffect> DELAYED_VERDICT = MOB_EFFECTS.register(
      "delayed_verdict",
      () -> new TensuraMobEffect(MobEffectCategory.HARMFUL, 14141579)
         .addAttributeModifier(TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER, id("delayed_verdict_magicule"), -0.35, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(TensuraAttributes.AURA_REGENERATION_MULTIPLIER, id("delayed_verdict_aura"), -0.35, Operation.ADD_MULTIPLIED_TOTAL)
   );
   public static final RegistrySupplier<MobEffect> CAUSALITY_LOOP = MOB_EFFECTS.register(
      "causality_loop",
      () -> new TensuraMobEffect(MobEffectCategory.HARMFUL, 11184829)
         .addAttributeModifier(Attributes.MOVEMENT_SPEED, id("causality_loop_speed"), -0.95, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(Attributes.FLYING_SPEED, id("causality_loop_fly"), -0.95, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(Attributes.ATTACK_SPEED, id("causality_loop_attack"), -0.75, Operation.ADD_MULTIPLIED_TOTAL)
   );
   public static final RegistrySupplier<MobEffect> CONDEMNED_REPEAT = MOB_EFFECTS.register(
      "condemned_repeat",
      () -> new TensuraMobEffect(MobEffectCategory.HARMFUL, 12105928)
         .addAttributeModifier(Attributes.MOVEMENT_SPEED, id("condemned_repeat_speed"), -0.65, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(Attributes.FLYING_SPEED, id("condemned_repeat_fly"), -0.65, Operation.ADD_MULTIPLIED_TOTAL)
   );
   public static final RegistrySupplier<MobEffect> TEMPORAL_SCREEN = MOB_EFFECTS.register(
      "temporal_screen", () -> new TensuraMobEffect(MobEffectCategory.BENEFICIAL, 12568533)
   );
   public static final RegistrySupplier<MobEffect> FUTURE_EXILE = MOB_EFFECTS.register(
      "future_exile",
      () -> new TensuraMobEffect(MobEffectCategory.HARMFUL, 6645109)
         .addAttributeModifier(Attributes.MOVEMENT_SPEED, id("future_exile_speed"), -1.0, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(Attributes.FLYING_SPEED, id("future_exile_fly"), -1.0, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(Attributes.ATTACK_SPEED, id("future_exile_attack"), -1.0, Operation.ADD_MULTIPLIED_TOTAL)
   );
   public static final RegistrySupplier<MobEffect> PAST_BURIAL = MOB_EFFECTS.register(
      "past_burial",
      () -> new TensuraMobEffect(MobEffectCategory.HARMFUL, 7829367)
         .addAttributeModifier(Attributes.ATTACK_SPEED, id("past_burial_attack"), -0.85, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(TensuraAttributes.CHANT_SPEED, id("past_burial_chant"), -0.75, Operation.ADD_MULTIPLIED_TOTAL)
   );
   public static final RegistrySupplier<MobEffect> WORLD_OF_STILL_SECONDS = MOB_EFFECTS.register(
      "world_of_still_seconds",
      () -> new TensuraMobEffect(MobEffectCategory.HARMFUL, 14013909)
         .addAttributeModifier(Attributes.MOVEMENT_SPEED, id("world_still_speed"), -0.98, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(Attributes.FLYING_SPEED, id("world_still_fly"), -0.98, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(Attributes.ATTACK_SPEED, id("world_still_attack"), -0.98, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(TensuraAttributes.CHANT_SPEED, id("world_still_chant"), -0.9, Operation.ADD_MULTIPLIED_TOTAL)
   );
   public static final RegistrySupplier<MobEffect> EVENT_SILENCE = MOB_EFFECTS.register(
      "event_silence",
      () -> new TensuraMobEffect(MobEffectCategory.HARMFUL, 13421772)
         .addAttributeModifier(Attributes.MOVEMENT_SPEED, id("event_silence_speed"), -0.55, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(Attributes.ATTACK_SPEED, id("event_silence_attack"), -0.95, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(TensuraAttributes.CHANT_SPEED, id("event_silence_chant"), -0.95, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER, id("event_silence_magicule"), -0.65, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(TensuraAttributes.AURA_REGENERATION_MULTIPLIER, id("event_silence_aura"), -0.65, Operation.ADD_MULTIPLIED_TOTAL)
   );
   public static final RegistrySupplier<MobEffect> FUTURE_REJECTION = MOB_EFFECTS.register(
      "future_rejection",
      () -> new TensuraMobEffect(MobEffectCategory.HARMFUL, 15658710)
         .addAttributeModifier(TensuraAttributes.AUTO_MELEE_DODGE_CHANCE, id("future_rejection_melee_dodge"), -80.0, Operation.ADD_VALUE)
         .addAttributeModifier(TensuraAttributes.AUTO_PROJECTILE_DODGE_CHANCE, id("future_rejection_projectile_dodge"), -80.0, Operation.ADD_VALUE)
         .addAttributeModifier(TensuraAttributes.DODGE_INVULNERABILITY, id("future_rejection_dodge_invuln"), -0.55, Operation.ADD_VALUE)
   );
   public static final RegistrySupplier<MobEffect> TIMELINE_VERDICT = MOB_EFFECTS.register(
      "timeline_verdict",
      () -> new TensuraMobEffect(MobEffectCategory.HARMFUL, 3092277)
         .addAttributeModifier(Attributes.MOVEMENT_SPEED, id("timeline_verdict_speed"), -0.85, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(Attributes.FLYING_SPEED, id("timeline_verdict_fly"), -0.85, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(Attributes.ATTACK_SPEED, id("timeline_verdict_attack"), -0.85, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(TensuraAttributes.CHANT_SPEED, id("timeline_verdict_chant"), -0.85, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER, id("timeline_verdict_magicule"), -0.85, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(TensuraAttributes.AURA_REGENERATION_MULTIPLIER, id("timeline_verdict_aura"), -0.85, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(TensuraAttributes.SPIRITUAL_HEALTH_REGENERATION, id("timeline_verdict_spiritual"), -0.85, Operation.ADD_MULTIPLIED_TOTAL)
   );

   private ModMobEffects() {
   }

   public static void init() {
      MOB_EFFECTS.register();
   }

   public static Holder<MobEffect> getReference(RegistrySupplier<MobEffect> input) {
      return MOB_EFFECTS.getRegistrar().getHolder(input.getId());
   }

   private static ResourceLocation id(String name) {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", name);
   }
}
