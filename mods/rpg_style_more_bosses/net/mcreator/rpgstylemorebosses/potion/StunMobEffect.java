package net.mcreator.rpgstylemorebosses.potion;

import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.neoforged.neoforge.common.EffectCure;

public class StunMobEffect extends MobEffect {
   public StunMobEffect() {
      super(MobEffectCategory.HARMFUL, -103);
      this.addAttributeModifier(
         Attributes.JUMP_STRENGTH, ResourceLocation.fromNamespaceAndPath("rpg_style_more_bosses", "effect.stun_0"), -1.0, Operation.ADD_VALUE
      );
      this.addAttributeModifier(
         Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath("rpg_style_more_bosses", "effect.stun_1"), -1.0, Operation.ADD_VALUE
      );
      this.addAttributeModifier(
         Attributes.KNOCKBACK_RESISTANCE, ResourceLocation.fromNamespaceAndPath("rpg_style_more_bosses", "effect.stun_2"), 1.0, Operation.ADD_VALUE
      );
   }

   public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {
   }
}
