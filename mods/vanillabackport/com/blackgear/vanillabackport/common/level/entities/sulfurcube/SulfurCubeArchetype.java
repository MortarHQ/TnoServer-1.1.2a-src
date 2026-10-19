package com.blackgear.vanillabackport.common.level.entities.sulfurcube;

import com.blackgear.vanillabackport.core.util.AdditionalCodecs;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.Item;

public record SulfurCubeArchetype(
   TagKey<Item> items,
   List<SulfurCubeArchetype.AttributeEntry> attributeModifiers,
   boolean buoyant,
   Optional<SulfurCubeArchetype.ExplosionData> explosion,
   Optional<SulfurCubeArchetype.ContactDamage> contactDamage,
   SulfurCubeArchetype.KnockbackModifiers knockbackModifiers
) {
   public static final Codec<SulfurCubeArchetype> DIRECT_CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            TagKey.hashedCodec(Registries.ITEM).fieldOf("items").forGetter(SulfurCubeArchetype::items),
            SulfurCubeArchetype.AttributeEntry.CODEC.listOf().fieldOf("attribute_modifiers").forGetter(SulfurCubeArchetype::attributeModifiers),
            Codec.BOOL.fieldOf("buoyant").forGetter(SulfurCubeArchetype::buoyant),
            SulfurCubeArchetype.ExplosionData.CODEC.optionalFieldOf("explosion").forGetter(SulfurCubeArchetype::explosion),
            SulfurCubeArchetype.ContactDamage.CODEC.optionalFieldOf("contact_damage").forGetter(SulfurCubeArchetype::contactDamage),
            SulfurCubeArchetype.KnockbackModifiers.CODEC.fieldOf("knockback_modifiers").forGetter(SulfurCubeArchetype::knockbackModifiers)
         )
         .apply(instance, SulfurCubeArchetype::new)
   );
   public static final SulfurCubeArchetype.KnockbackModifiers DEFAULT_KNOCKBACK_MODIFIERS = new SulfurCubeArchetype.KnockbackModifiers(0.33F, 0.06F);

   public record AttributeEntry(Holder<Attribute> attribute, AttributeModifier modifier) {
      public static final Codec<SulfurCubeArchetype.AttributeEntry> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               BuiltInRegistries.ATTRIBUTE.holderByNameCodec().fieldOf("attribute").forGetter(SulfurCubeArchetype.AttributeEntry::attribute),
               AttributeModifier.CODEC.fieldOf("modifier").forGetter(SulfurCubeArchetype.AttributeEntry::modifier)
            )
            .apply(instance, SulfurCubeArchetype.AttributeEntry::new)
      );

      public static SulfurCubeArchetype.AttributeEntry add(Holder<Attribute> attribute, double amount, ResourceKey<SulfurCubeArchetype> archetype) {
         ResourceLocation attributeLocation = ((ResourceKey)attribute.unwrapKey().orElseThrow(() -> new IllegalStateException("Attribute has no registry key")))
            .location();
         return new SulfurCubeArchetype.AttributeEntry(
            attribute,
            new AttributeModifier(
               ResourceLocation.withDefaultNamespace(
                  archetype.location().getPath() + "_add_" + attributeLocation.getNamespace() + "_" + attributeLocation.getPath()
               ),
               amount,
               Operation.ADD_VALUE
            )
         );
      }

      public static SulfurCubeArchetype.AttributeEntry multiply(Holder<Attribute> attribute, double amount, ResourceKey<SulfurCubeArchetype> archetype) {
         ResourceLocation attributeLocation = ((ResourceKey)attribute.unwrapKey().orElseThrow(() -> new IllegalStateException("Attribute has no registry key")))
            .location();
         return new SulfurCubeArchetype.AttributeEntry(
            attribute,
            new AttributeModifier(
               ResourceLocation.withDefaultNamespace(
                  archetype.location().getPath() + "_mul_" + attributeLocation.getNamespace() + "_" + attributeLocation.getPath()
               ),
               amount - 1.0,
               Operation.ADD_MULTIPLIED_TOTAL
            )
         );
      }
   }

   public record ContactDamage(ResourceKey<DamageType> damageType, FloatProvider amount, boolean attributeToSource) {
      public static final Codec<SulfurCubeArchetype.ContactDamage> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               ResourceKey.codec(Registries.DAMAGE_TYPE).fieldOf("damage_type").forGetter(SulfurCubeArchetype.ContactDamage::damageType),
               AdditionalCodecs.floatProvider(0.0F).fieldOf("amount").forGetter(SulfurCubeArchetype.ContactDamage::amount),
               Codec.BOOL.fieldOf("attribute_to_source").forGetter(SulfurCubeArchetype.ContactDamage::attributeToSource)
            )
            .apply(instance, SulfurCubeArchetype.ContactDamage::new)
      );
   }

   public record ExplosionData(int power, boolean causesFire, int fuse) {
      public static final Codec<SulfurCubeArchetype.ExplosionData> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               ExtraCodecs.NON_NEGATIVE_INT.fieldOf("power").forGetter(SulfurCubeArchetype.ExplosionData::power),
               Codec.BOOL.fieldOf("causes_fire").forGetter(SulfurCubeArchetype.ExplosionData::causesFire),
               ExtraCodecs.POSITIVE_INT.fieldOf("fuse").forGetter(SulfurCubeArchetype.ExplosionData::fuse)
            )
            .apply(instance, SulfurCubeArchetype.ExplosionData::new)
      );
   }

   public record KnockbackModifiers(float horizontalPower, float verticalPower) {
      public static final Codec<SulfurCubeArchetype.KnockbackModifiers> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               Codec.FLOAT.fieldOf("horizontal_power").forGetter(SulfurCubeArchetype.KnockbackModifiers::horizontalPower),
               Codec.FLOAT.fieldOf("vertical_power").forGetter(SulfurCubeArchetype.KnockbackModifiers::verticalPower)
            )
            .apply(instance, SulfurCubeArchetype.KnockbackModifiers::new)
      );
   }
}
