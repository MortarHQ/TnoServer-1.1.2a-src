package artifacts.component.ability.mobeffect;

import artifacts.component.ability.CompositeAbility;
import artifacts.component.ability.EquipmentAbility;
import artifacts.config.value.Value;
import artifacts.config.value.ValueTypes;
import artifacts.equipment.EquipmentHelper;
import artifacts.registry.ModDataComponents;
import artifacts.util.ModCodecs;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public record PostDamageEffects(List<PostDamageEffects.Entry> entries) implements CompositeAbility<PostDamageEffects.Entry> {
   public static final Codec<PostDamageEffects> CODEC = PostDamageEffects.Entry.CODEC.listOf(0, 16).xmap(PostDamageEffects::new, PostDamageEffects::entries);
   public static final StreamCodec<RegistryFriendlyByteBuf, PostDamageEffects> STREAM_CODEC = ByteBufCodecs.list()
      .apply(PostDamageEffects.Entry.STREAM_CODEC)
      .map(PostDamageEffects::new, PostDamageEffects::entries);

   public static void onLivingDamaged(LivingEntity entity, DamageSource damageSource) {
      if (!entity.level().isClientSide()) {
         EquipmentHelper.iterateAbilities(ModDataComponents.POST_DAMAGE_EFFECTS.get(), entity, true, true, (ability, stack) -> {
            for (PostDamageEffects.Entry entry : ability.entries) {
               if (entry.shouldApply(damageSource.type(), entity)) {
                  entity.addEffect(entry.provider.createEffect());
               }
            }
         });
      }
   }

   public record Entry(MobEffectProvider provider, Optional<TagKey<DamageType>> tag, Value<Double> chance) implements EquipmentAbility {
      public static final Codec<PostDamageEffects.Entry> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               MobEffectProvider.codec(true).fieldOf("effect").forGetter(PostDamageEffects.Entry::provider),
               TagKey.codec(Registries.DAMAGE_TYPE).optionalFieldOf("tag").forGetter(PostDamageEffects.Entry::tag),
               ValueTypes.FRACTION.codec().optionalFieldOf("chance", Value.of(1.0)).forGetter(PostDamageEffects.Entry::chance)
            )
            .apply(instance, PostDamageEffects.Entry::new)
      );
      public static final StreamCodec<RegistryFriendlyByteBuf, PostDamageEffects.Entry> STREAM_CODEC = StreamCodec.composite(
         MobEffectProvider.STREAM_CODEC,
         PostDamageEffects.Entry::provider,
         ByteBufCodecs.optional(ModCodecs.tagKeyStreamCodec(Registries.DAMAGE_TYPE)),
         PostDamageEffects.Entry::tag,
         ValueTypes.FRACTION.streamCodec(),
         PostDamageEffects.Entry::chance,
         PostDamageEffects.Entry::new
      );

      public boolean shouldApply(DamageType type, LivingEntity entity) {
         return this.provider.canApply(entity)
            && entity.getRandom().nextDouble() < this.chance.get()
            && (
               this.tag.isEmpty()
                  || entity.level()
                     .registryAccess()
                     .registry(Registries.DAMAGE_TYPE)
                     .flatMap(registry -> registry.getTag(this.tag.get()).map(tag -> tag.contains(registry.wrapAsHolder(type))))
                     .orElseThrow()
            );
      }

      @Override
      public boolean isNonCosmetic() {
         return this.provider.isNonCosmetic() && this.chance.get() > 0.0;
      }

      @Override
      public void addToTooltip(EquipmentAbility.TooltipWriter writer) {
         if (this.provider.mobEffect().equals(MobEffects.FIRE_RESISTANCE)
            && this.tag.isPresent()
            && this.tag.get().equals(DamageTypeTags.IS_FIRE)
            && this.chance.get() == 1.0) {
            writer.add("fire_resistance");
         } else if (this.provider.mobEffect().equals(MobEffects.MOVEMENT_SPEED) && this.tag.isEmpty() && this.chance.get() == 1.0) {
            writer.add("speed");
         }
      }
   }
}
