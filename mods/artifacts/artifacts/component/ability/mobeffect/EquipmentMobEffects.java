package artifacts.component.ability.mobeffect;

import artifacts.component.ability.CompositeAbility;
import artifacts.component.ability.EntityCondition;
import artifacts.component.ability.EquipmentAbility;
import artifacts.component.ability.TickingAbility;
import artifacts.component.ability.TickingCompositeAbility;
import artifacts.config.value.Value;
import artifacts.registry.ModDataComponents;
import artifacts.registry.ModMobEffects;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public record EquipmentMobEffects(List<EquipmentMobEffects.Entry> entries) implements TickingCompositeAbility<EquipmentMobEffects.Entry> {
   private static final Set<Holder<MobEffect>> CUSTOM_TOOLTIP_MOB_EFFECTS = Set.of(MobEffects.INVISIBILITY, ModMobEffects.MAGNETISM);
   public static final Codec<EquipmentMobEffects> CODEC = CompositeAbility.codec(
      EquipmentMobEffects.Entry.CODEC, EquipmentMobEffects::new, EquipmentMobEffects::entries
   );
   public static final StreamCodec<RegistryFriendlyByteBuf, EquipmentMobEffects> STREAM_CODEC = CompositeAbility.streamCodec(
      EquipmentMobEffects.Entry.STREAM_CODEC, EquipmentMobEffects::new, EquipmentMobEffects::entries
   );

   public record Entry(MobEffectProvider provider) implements TickingAbility {
      public static final Codec<EquipmentMobEffects.Entry> CODEC = MobEffectProvider.codec(false)
         .xmap(EquipmentMobEffects.Entry::new, EquipmentMobEffects.Entry::provider);
      public static final StreamCodec<RegistryFriendlyByteBuf, EquipmentMobEffects.Entry> STREAM_CODEC = MobEffectProvider.STREAM_CODEC
         .map(EquipmentMobEffects.Entry::new, EquipmentMobEffects.Entry::provider);

      @Override
      public void onUnequip(LivingEntity entity) {
         MobEffectInstance instance = entity.getEffect(this.provider.mobEffect());
         if (instance != null
            && instance.getAmplifier() == this.provider.getAmplifier()
            && instance.isVisible() == this.provider.spawnParticles().get()
            && instance.showIcon() == this.provider.showIcon().get()
            && instance.endsWithin(this.provider.getDuration(1) * 20 + 19)) {
            entity.removeEffect(this.provider.mobEffect());
         }
      }

      @Override
      public void wornTick(LivingEntity entity, boolean isOnCooldown, boolean isDisabled) {
         if (!isDisabled && !isOnCooldown && this.provider.canApply(entity)) {
            entity.addEffect(this.provider.createEffect());
         }
      }

      @Override
      public boolean isNonCosmetic() {
         return this.provider().isNonCosmetic();
      }

      @Override
      public void addToTooltip(EquipmentAbility.TooltipWriter writer) {
         if (EquipmentMobEffects.CUSTOM_TOOLTIP_MOB_EFFECTS.contains(this.provider.mobEffect())) {
            ResourceLocation id = BuiltInRegistries.MOB_EFFECT.getKey((MobEffect)this.provider.mobEffect().value());
            writer.add(Objects.requireNonNull(id).getPath());
         }

         if (this.provider.mobEffect().value() == MobEffects.NIGHT_VISION.value()) {
            Value<Double> nightVisionStrength = (Value<Double>)writer.stack().get(ModDataComponents.REDUCED_NIGHT_VISION.get());
            if (nightVisionStrength != null && nightVisionStrength.get() < 0.5) {
               writer.add("night_vision.partial");
            } else {
               writer.add("night_vision.full");
            }
         }

         if (this.provider.mobEffect().value() == MobEffects.WATER_BREATHING.value()) {
            if (this.provider.condition() == EntityCondition.ALWAYS) {
               writer.add("water_breathing.infinite");
            } else {
               writer.add("water_breathing.limited");
            }
         }
      }
   }
}
