package com.notunanancyowen.spears.neoforge;

import com.notunanancyowen.spears.Spears;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;

@EventBusSubscriber(modid = "spears")
public class SpearsConfig {
   private static final Builder BUILDER = new Builder();
   private static final BooleanValue LUNGE_HUNGER_CUTOFF = BUILDER.comment("Stops lunge from working if the player is below 3 hunger points")
      .define("lunge_hunger_cutoff", true);
   private static final BooleanValue SPEAR_CHARGE_ATTACKS = BUILDER.comment("Whether spears have a charge attack").define("spear_charge_attacks", true);
   private static final BooleanValue SPEAR_STABBING_ANIMATION = BUILDER.comment("Whether spears have a stabbing animation")
      .define("spear_stabbing_animation", true);
   private static final BooleanValue TRIDENTS_USE_SPEAR_ATTACK_ANIMATION = BUILDER.comment("Whether tridents attack like spears")
      .define("tridents_use_spear_attack_animation", false);
   private static final BooleanValue CAN_LUNGE_WHILE_GLIDING = BUILDER.comment("Allows lunging while using an Elytra")
      .define("can_use_lunge_while_gliding", false);
   private static final BooleanValue CAN_LUNGE_WHILE_SWIMMING = BUILDER.comment("Allows lunging while underwater")
      .define("can_use_lunge_while_in_water", false);
   private static final BooleanValue ALLOW_ATTRIBUTE_SWAP_FOR_SPEARS = BUILDER.comment("Enables attribute swapping on spears for lunge swapping")
      .define("attribute_swap_bypasses_minimum_attack_charge", true);
   static final ModConfigSpec SPEC = BUILDER.build();

   @SubscribeEvent
   static void onLoad(ModConfigEvent event) {
      Spears.config = Spears.makeConfig();
      Spears.config.put("lunge_hunger_cutoff", LUNGE_HUNGER_CUTOFF.getAsBoolean());
      Spears.config.put("spear_charge_attacks", SPEAR_CHARGE_ATTACKS.getAsBoolean());
      Spears.config.put("spear_stabbing_animation", SPEAR_STABBING_ANIMATION.getAsBoolean());
      Spears.config.put("can_use_lunge_while_gliding", CAN_LUNGE_WHILE_GLIDING.getAsBoolean());
      Spears.config.put("can_use_lunge_while_in_water", CAN_LUNGE_WHILE_SWIMMING.getAsBoolean());
      Spears.config.put("tridents_use_spear_attack_animation", TRIDENTS_USE_SPEAR_ATTACK_ANIMATION.getAsBoolean());
      Spears.config.put("attribute_swap_bypasses_minimum_attack_charge", ALLOW_ATTRIBUTE_SWAP_FOR_SPEARS.getAsBoolean());
   }
}
