package com.github.dominickwd04.traddon.effect;

import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import java.awt.Color;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class AtomicAnnihilationEffect extends TensuraMobEffect {
   protected static final ResourceLocation ATOMIC_ANNIHILATION = ResourceLocation.fromNamespaceAndPath("traddon", "atomic_annihilation");

   public AtomicAnnihilationEffect() {
      super(MobEffectCategory.BENEFICIAL, new Color(255, 255, 255).getRGB());
      this.addAttributeModifier(TensuraAttributes.PHYSICAL_RESIST_DEGRADATION, ATOMIC_ANNIHILATION, 1.0, Operation.ADD_VALUE);
   }
}
