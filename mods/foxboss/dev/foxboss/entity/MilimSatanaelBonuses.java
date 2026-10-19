package dev.foxboss.entity;

import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

final class MilimSatanaelBonuses {
   private static final ResourceLocation MAGICULE_OUTPUT = id("satanael_stampede_magicule_output");
   private static final ResourceLocation RAMPAGE = id("satanael_rampage");
   private static final ResourceLocation STAMPEDE_ATTACK = id("satanael_stampede_attack");
   private static final ResourceLocation STAMPEDE_WEAKNESS = id("satanael_stampede_weakness");

   static float dragonStarDamage(MilimEntity milim, float base) {
      if (!enabled(milim)) {
         return base;
      }

      double output = additiveAmount(milim.getAttribute(TensuraAttributes.MAGICULE_GAIN), MAGICULE_OUTPUT);
      return (float)(base * (1.0 + Math.min(2.0, Math.max(0.0, output))));
   }

   static float kickDamage(MilimEntity milim, float base) {
      if (!enabled(milim)) {
         return base;
      }

      AttributeInstance attack = milim.getAttribute(Attributes.ATTACK_DAMAGE);
      double bonus = additiveAmount(attack, RAMPAGE) + additiveAmount(attack, STAMPEDE_ATTACK) + additiveAmount(attack, STAMPEDE_WEAKNESS);
      double damage = base + bonus * 2.2;
      return Double.isNaN(damage) ? 0.0F : (float)Math.max(0.0, Math.min(Float.MAX_VALUE, damage));
   }

   private static boolean enabled(MilimEntity milim) {
      return !milim.isDemo() && !milim.isDebugAnimation() && SkillAPI.getSkillsFrom(milim).getSkill(MilimSatanaelCompat.SATANAEL).isPresent();
   }

   private static double additiveAmount(AttributeInstance attribute, ResourceLocation id) {
      AttributeModifier modifier = attribute == null ? null : attribute.getModifier(id);
      return modifier != null && modifier.operation() == Operation.ADD_VALUE && Double.isFinite(modifier.amount()) ? modifier.amount() : 0.0;
   }

   private static ResourceLocation id(String path) {
      return ResourceLocation.fromNamespaceAndPath("foxablazeultimate", path);
   }

   private MilimSatanaelBonuses() {
   }
}
