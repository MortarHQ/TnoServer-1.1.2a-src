package artifacts.component.ability;

import artifacts.config.value.Value;
import artifacts.config.value.ValueTypes;
import artifacts.registry.ModAttributes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public record AttributeModifiers(List<AttributeModifiers.Entry> entries) implements TickingCompositeAbility<AttributeModifiers.Entry> {
   private static final Set<Holder<Attribute>> POSITIVE_ATTRIBUTES_WITH_TOOLTIP = new HashSet<>();
   private static final Set<Holder<Attribute>> NEGATIVE_ATTRIBUTES_WITH_TOOLTIP = Set.of(Attributes.SCALE, Attributes.FALL_DAMAGE_MULTIPLIER);
   public static final Codec<AttributeModifiers> CODEC = CompositeAbility.codec(
      AttributeModifiers.Entry.CODEC, AttributeModifiers::new, AttributeModifiers::entries
   );
   public static final StreamCodec<RegistryFriendlyByteBuf, AttributeModifiers> STREAM_CODEC = CompositeAbility.streamCodec(
      AttributeModifiers.Entry.STREAM_CODEC, AttributeModifiers::new, AttributeModifiers::entries
   );

   static {
      POSITIVE_ATTRIBUTES_WITH_TOOLTIP.addAll(ModAttributes.PLAYER_ATTRIBUTES);
      POSITIVE_ATTRIBUTES_WITH_TOOLTIP.addAll(ModAttributes.GENERIC_ATTRIBUTES);
      POSITIVE_ATTRIBUTES_WITH_TOOLTIP.add(ModAttributes.SWIM_SPEED);
      POSITIVE_ATTRIBUTES_WITH_TOOLTIP.addAll(
         List.of(
            Attributes.ATTACK_DAMAGE,
            Attributes.ATTACK_KNOCKBACK,
            Attributes.ATTACK_SPEED,
            Attributes.BLOCK_BREAK_SPEED,
            Attributes.JUMP_STRENGTH,
            Attributes.KNOCKBACK_RESISTANCE,
            Attributes.MAX_HEALTH,
            Attributes.SAFE_FALL_DISTANCE,
            Attributes.OXYGEN_BONUS
         )
      );
   }

   public record Entry(Holder<Attribute> attribute, Value<Double> amount, Operation operation, ResourceLocation id, boolean ignoreCooldown)
      implements TickingAbility {
      public static final Codec<AttributeModifiers.Entry> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               BuiltInRegistries.ATTRIBUTE.holderByNameCodec().fieldOf("attribute").forGetter(AttributeModifiers.Entry::attribute),
               ValueTypes.ATTRIBUTE_MODIFIER_AMOUNT.codec().fieldOf("amount").forGetter(AttributeModifiers.Entry::amount),
               Operation.CODEC.fieldOf("operation").forGetter(AttributeModifiers.Entry::operation),
               ResourceLocation.CODEC.fieldOf("id").forGetter(AttributeModifiers.Entry::id),
               Codec.BOOL.optionalFieldOf("ignore_cooldown", true).forGetter(AttributeModifiers.Entry::ignoreCooldown)
            )
            .apply(instance, AttributeModifiers.Entry::new)
      );
      public static final StreamCodec<RegistryFriendlyByteBuf, AttributeModifiers.Entry> STREAM_CODEC = StreamCodec.composite(
         ByteBufCodecs.holderRegistry(Registries.ATTRIBUTE),
         AttributeModifiers.Entry::attribute,
         ValueTypes.ATTRIBUTE_MODIFIER_AMOUNT.streamCodec(),
         AttributeModifiers.Entry::amount,
         Operation.STREAM_CODEC,
         AttributeModifiers.Entry::operation,
         ResourceLocation.STREAM_CODEC,
         AttributeModifiers.Entry::id,
         ByteBufCodecs.BOOL,
         AttributeModifiers.Entry::ignoreCooldown,
         AttributeModifiers.Entry::new
      );

      public AttributeModifier createModifier() {
         return new AttributeModifier(this.id(), this.amount().get(), this.operation());
      }

      private void onAttributeUpdated(LivingEntity entity) {
         if (this.attribute() == Attributes.MAX_HEALTH && entity.getHealth() > entity.getMaxHealth()) {
            entity.setHealth(entity.getMaxHealth());
         }
      }

      @Override
      public void onUnequip(LivingEntity entity) {
         AttributeInstance attributeInstance = entity.getAttribute(this.attribute());
         if (attributeInstance != null && attributeInstance.hasModifier(this.id())) {
            attributeInstance.removeModifier(this.id());
            this.onAttributeUpdated(entity);
         }
      }

      @Override
      public void wornTick(LivingEntity entity, boolean isOnCooldown, boolean isDisabled) {
         AttributeInstance attributeInstance = entity.getAttribute(this.attribute());
         if (attributeInstance != null) {
            AttributeModifier existingModifier = attributeInstance.getModifier(this.id());
            if (!this.ignoreCooldown() && isOnCooldown) {
               if (!isDisabled && this.isNonCosmetic()) {
                  this.onUnequip(entity);
               }
            } else if (!isDisabled && (existingModifier == null || !Mth.equal(this.amount().get(), existingModifier.amount()))) {
               attributeInstance.removeModifier(this.id());
               attributeInstance.addPermanentModifier(this.createModifier());
               this.onAttributeUpdated(entity);
            }
         }
      }

      @Override
      public boolean isNonCosmetic() {
         return !Mth.equal(this.amount().get(), 0.0);
      }

      @Override
      public void addToTooltip(EquipmentAbility.TooltipWriter writer) {
         String attributeName = ((ResourceKey)this.attribute().unwrapKey().orElseThrow()).location().getPath();
         if (attributeName.equals("swim_speed")) {
            attributeName = "generic.swim_speed";
         }

         if (this.amount().get() > 0.0) {
            for (Holder<Attribute> attribute : AttributeModifiers.POSITIVE_ATTRIBUTES_WITH_TOOLTIP) {
               if (attribute.isBound() && attribute.value() == this.attribute().value()) {
                  writer.add(attributeName);
               }
            }
         } else {
            for (Holder<Attribute> attribute : AttributeModifiers.NEGATIVE_ATTRIBUTES_WITH_TOOLTIP) {
               if (attribute.isBound() && attribute.value() == this.attribute().value()) {
                  writer.add(attributeName);
               }
            }
         }
      }
   }
}
