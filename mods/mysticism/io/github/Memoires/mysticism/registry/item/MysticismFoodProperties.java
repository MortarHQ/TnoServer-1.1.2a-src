package io.github.Memoires.mysticism.registry.item;

import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.Builder;

public class MysticismFoodProperties {
   public static final FoodProperties CRYPTID_ESSENCE = new Builder()
      .alwaysEdible()
      .nutrition(0)
      .saturationModifier(0.0F)
      .effect(new MobEffectInstance(MobEffects.CONFUSION, 100, 0), 1.0F)
      .build();
   public static final FoodProperties ICE_ESSENCE = new Builder()
      .alwaysEdible()
      .nutrition(0)
      .saturationModifier(0.0F)
      .effect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.FROST), 100, 0), 1.0F)
      .build();
   public static final FoodProperties FLAME_ESSENCE = new Builder()
      .alwaysEdible()
      .nutrition(0)
      .saturationModifier(0.0F)
      .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 100, 0), 1.0F)
      .build();
   public static final FoodProperties LIGHTNING_ESSENCE = new Builder()
      .alwaysEdible()
      .nutrition(0)
      .saturationModifier(0.0F)
      .effect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.PARALYSIS), 100, 0), 1.0F)
      .build();
}
