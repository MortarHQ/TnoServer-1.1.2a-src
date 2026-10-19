package com.github.b4ndithelps.tennogamenolife.effect;

import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class WeakenEffect extends TensuraMobEffect {
   private static final ResourceLocation WEAKEN_SIZE_ID = ResourceLocation.fromNamespaceAndPath("tennogamenolife", "weaken_size");

   public WeakenEffect() {
      super(MobEffectCategory.NEUTRAL, 8421504);
      this.addAttributeModifier(Attributes.SCALE, WEAKEN_SIZE_ID, -0.4, Operation.ADD_MULTIPLIED_TOTAL);
   }
}
