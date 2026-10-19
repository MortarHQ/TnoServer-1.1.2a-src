package com.mongoose.royalvariations.common.effects;

import com.mongoose.royalvariations.RoyalVariations;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RVEffects {
   public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, "royalvariations");
   public static final Holder<MobEffect> KNIGHTLY_FORTITUDE = EFFECTS.register(
      "knightly_fortitude",
      () -> new RVBaseEffect(MobEffectCategory.BENEFICIAL, 5485531)
         .addAttributeModifier(Attributes.ARMOR, RoyalVariations.location("effect.knightly_fortitude.armor"), 7.0, Operation.ADD_VALUE)
         .addAttributeModifier(Attributes.ARMOR_TOUGHNESS, RoyalVariations.location("effect.knightly_fortitude.toughness"), 4.0, Operation.ADD_VALUE)
         .addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, RoyalVariations.location("effect.knightly_fortitude.knockback"), 1.0, Operation.ADD_VALUE)
   );
   public static final Holder<MobEffect> ROYAL_BLESSING = EFFECTS.register(
      "royal_blessing",
      () -> new RVBaseEffect(MobEffectCategory.BENEFICIAL, 16764210)
         .addAttributeModifier(Attributes.ATTACK_DAMAGE, RoyalVariations.location("effect.royal_blessing.attack_damage"), 3.5, Operation.ADD_VALUE)
         .addAttributeModifier(Attributes.ATTACK_SPEED, RoyalVariations.location("effect.royal_blessing.attack_speed"), 0.15, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(Attributes.MOVEMENT_SPEED, RoyalVariations.location("effect.royal_blessing.speed"), 0.05, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(Attributes.ARMOR, RoyalVariations.location("effect.royal_blessing.armor"), 5.0, Operation.ADD_VALUE)
   );
   public static final Holder<MobEffect> DAZED = EFFECTS.register(
      "dazed",
      () -> new RVBaseEffect(MobEffectCategory.HARMFUL, 16769152)
         .addAttributeModifier(Attributes.MOVEMENT_SPEED, RoyalVariations.location("effect.dazed.speed"), -0.6, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(Attributes.ATTACK_DAMAGE, RoyalVariations.location("effect.dazed.attack_speed"), -0.6, Operation.ADD_MULTIPLIED_TOTAL)
   );
   public static final Holder<MobEffect> UNDEAD_RUSH = EFFECTS.register(
      "undead_rush",
      () -> new RVBaseEffect(MobEffectCategory.NEUTRAL, 8103478)
         .addAttributeModifier(Attributes.MOVEMENT_SPEED, RoyalVariations.location("effect.undead_rush.movement_speed"), 0.26, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(Attributes.ATTACK_SPEED, RoyalVariations.location("effect.undead_rush.attack_speed"), 0.15, Operation.ADD_MULTIPLIED_TOTAL)
   );
   public static final Holder<MobEffect> CHOSEN_VICTIM = EFFECTS.register("chosen_victim", () -> new RVBaseEffect(MobEffectCategory.HARMFUL, 4420645));
   public static final Holder<MobEffect> MARKED = EFFECTS.register(
      "marked",
      () -> new RVBaseEffect(MobEffectCategory.HARMFUL, 0, false)
         .addAttributeModifier(Attributes.MOVEMENT_SPEED, RoyalVariations.location("effect.marked.movement_speed"), -0.1, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(Attributes.ATTACK_DAMAGE, RoyalVariations.location("effect.marked.attack_damage"), -0.1, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(Attributes.ATTACK_SPEED, RoyalVariations.location("effect.marked.attack_speed"), -0.1, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(Attributes.ARMOR, RoyalVariations.location("effect.marked.armor"), -0.25, Operation.ADD_MULTIPLIED_TOTAL)
   );
   public static final Holder<MobEffect> TRAPPED = EFFECTS.register(
      "trapped",
      () -> new RVBaseEffect(MobEffectCategory.HARMFUL, 0, false)
         .addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, RoyalVariations.location("effect.trapped.knockback_resistance"), 2.0, Operation.ADD_VALUE)
         .addAttributeModifier(NeoForgeMod.SWIM_SPEED, RoyalVariations.location("effect.trapped.swim_speed"), -1.0, Operation.ADD_MULTIPLIED_TOTAL)
         .addAttributeModifier(Attributes.MOVEMENT_SPEED, RoyalVariations.location("effect.trapped.movement_speed"), -1.0, Operation.ADD_MULTIPLIED_TOTAL)
   );
   public static final Holder<MobEffect> TIME_BOMB = EFFECTS.register(
      "time_bomb",
      () -> new RVBaseEffect(MobEffectCategory.HARMFUL, 0, false)
         .addAttributeModifier(Attributes.MOVEMENT_SPEED, RoyalVariations.location("effect.time_bomb.movement_speed"), 0.1, Operation.ADD_MULTIPLIED_TOTAL)
   );
   public static final Holder<MobEffect> PRESSING_GAZE = EFFECTS.register("pressing_gaze", () -> new RVBaseEffect(MobEffectCategory.HARMFUL, 0, false));
   public static final Holder<MobEffect> HEAVINESS_OF_THE_END = EFFECTS.register(
      "heaviness_of_the_end",
      () -> new RVBaseEffect(MobEffectCategory.HARMFUL, 8989110)
         .addAttributeModifier(
            Attributes.MOVEMENT_SPEED, RoyalVariations.location("effect.heaviness_of_the_end.movement_speed"), -0.4, Operation.ADD_MULTIPLIED_TOTAL
         )
         .addAttributeModifier(NeoForgeMod.SWIM_SPEED, RoyalVariations.location("effect.heaviness_of_the_end.swim_speed"), -0.4, Operation.ADD_MULTIPLIED_TOTAL)
   );

   public static void init(IEventBus modEventBus) {
      EFFECTS.register(modEventBus);
   }
}
