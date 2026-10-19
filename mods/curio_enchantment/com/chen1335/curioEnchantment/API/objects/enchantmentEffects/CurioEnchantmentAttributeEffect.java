package com.chen1335.curioEnchantment.API.objects.enchantmentEffects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.enchantment.LevelBasedValue;

public record CurioEnchantmentAttributeEffect(Holder<Attribute> attribute, LevelBasedValue amount, Operation operation) {
   public static final MapCodec<CurioEnchantmentAttributeEffect> CODEC = RecordCodecBuilder.mapCodec(
      p_350198_ -> p_350198_.group(
            Attribute.CODEC.fieldOf("attribute").forGetter(CurioEnchantmentAttributeEffect::attribute),
            LevelBasedValue.CODEC.fieldOf("amount").forGetter(CurioEnchantmentAttributeEffect::amount),
            Operation.CODEC.fieldOf("operation").forGetter(CurioEnchantmentAttributeEffect::operation)
         )
         .apply(p_350198_, CurioEnchantmentAttributeEffect::new)
   );
}
