package cn.leolezury.eternalstarlight.common.crest;

import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.registry.ESSpells;
import cn.leolezury.eternalstarlight.common.spell.AbstractSpell;
import cn.leolezury.eternalstarlight.common.spell.ManaType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public record Crest(
   ManaType type,
   int maxLevel,
   ResourceLocation texture,
   Optional<Holder<AbstractSpell>> spell,
   Optional<List<Crest.MobEffectWithLevel>> effects,
   Optional<List<Crest.LevelBasedAttributeModifier>> attributeModifiers
) {
   public static final Codec<Crest> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            ManaType.CODEC.fieldOf("type").forGetter(Crest::type),
            Codec.INT.fieldOf("max_level").forGetter(Crest::maxLevel),
            ResourceLocation.CODEC.fieldOf("texture").forGetter(Crest::texture),
            ESSpells.HOLDER_CODEC.optionalFieldOf("spell").forGetter(Crest::spell),
            Crest.MobEffectWithLevel.CODEC.listOf().optionalFieldOf("mob_effects").forGetter(Crest::effects),
            Crest.LevelBasedAttributeModifier.CODEC.listOf().optionalFieldOf("attribute_modifiers").forGetter(Crest::attributeModifiers)
         )
         .apply(instance, Crest::new)
   );

   public Crest(
      ManaType type,
      int maxLevel,
      ResourceLocation texture,
      Holder<AbstractSpell> spell,
      List<Crest.MobEffectWithLevel> effects,
      List<Crest.LevelBasedAttributeModifier> attributeModifiers
   ) {
      this(type, maxLevel, texture, Optional.ofNullable(spell), Optional.of(effects), Optional.of(attributeModifiers));
   }

   public Optional<AbstractSpell> getSpell() {
      return this.spell().map(Holder::value);
   }

   public record Instance(Holder<Crest> crest, int level) {
      public static final Codec<Crest.Instance> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               RegistryFixedCodec.create(ESRegistries.CREST).fieldOf("crest").forGetter(Crest.Instance::crest),
               Codec.INT.fieldOf("level").forGetter(Crest.Instance::level)
            )
            .apply(instance, Crest.Instance::new)
      );
      public static final Codec<List<Crest.Instance>> LIST_CODEC = CODEC.listOf();

      public static Optional<Crest.Instance> of(RegistryAccess access, ResourceKey<Crest> key, int level) {
         Registry<Crest> registry = access.registryOrThrow(ESRegistries.CREST);
         Optional<Reference<Crest>> holder = registry.getHolder(key);
         return holder.map(ref -> new Crest.Instance(ref, Math.min(level, ((Crest)ref.value()).maxLevel())));
      }
   }

   public record LevelBasedAttributeModifier(Holder<Attribute> attribute, ResourceLocation id, double amount, double amountAddition, Operation operation) {
      public static final Codec<Crest.LevelBasedAttributeModifier> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               BuiltInRegistries.ATTRIBUTE.holderByNameCodec().fieldOf("attribute").forGetter(Crest.LevelBasedAttributeModifier::attribute),
               ResourceLocation.CODEC.fieldOf("id").forGetter(Crest.LevelBasedAttributeModifier::id),
               Codec.DOUBLE.fieldOf("amount").forGetter(Crest.LevelBasedAttributeModifier::amount),
               Codec.DOUBLE.fieldOf("amount_addition").forGetter(Crest.LevelBasedAttributeModifier::amountAddition),
               Operation.CODEC.fieldOf("operation").forGetter(Crest.LevelBasedAttributeModifier::operation)
            )
            .apply(instance, Crest.LevelBasedAttributeModifier::new)
      );

      public AttributeModifier getModifier(int level) {
         return new AttributeModifier(this.id(), this.amount() + (level - 1) * this.amountAddition(), this.operation());
      }
   }

   public record MobEffectWithLevel(Holder<MobEffect> effect, int level, int levelAddition) {
      public static final Codec<Crest.MobEffectWithLevel> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               BuiltInRegistries.MOB_EFFECT.holderByNameCodec().fieldOf("effect").forGetter(Crest.MobEffectWithLevel::effect),
               Codec.INT.fieldOf("level").forGetter(Crest.MobEffectWithLevel::level),
               Codec.INT.fieldOf("level_addition").forGetter(Crest.MobEffectWithLevel::levelAddition)
            )
            .apply(instance, Crest.MobEffectWithLevel::new)
      );
   }
}
