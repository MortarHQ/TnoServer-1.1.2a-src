package artifacts.component.ability.mobeffect;

import artifacts.component.ability.CompositeAbility;
import artifacts.component.ability.EquipmentAbility;
import artifacts.config.value.Value;
import artifacts.config.value.ValueTypes;
import artifacts.equipment.EquipmentHelper;
import artifacts.registry.ModDataComponents;
import artifacts.util.DamageSourceHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public record AttackEffects(List<AttackEffects.Entry> entries) implements CompositeAbility<AttackEffects.Entry> {
   private static final Set<Holder<MobEffect>> CUSTOM_TOOLTIP_MOB_EFFECTS = Set.of(MobEffects.WITHER);
   public static final Codec<AttackEffects> CODEC = AttackEffects.Entry.CODEC.listOf().xmap(AttackEffects::new, AttackEffects::entries);
   public static final StreamCodec<RegistryFriendlyByteBuf, AttackEffects> STREAM_CODEC = ByteBufCodecs.list()
      .apply(AttackEffects.Entry.STREAM_CODEC)
      .map(AttackEffects::new, AttackEffects::entries);

   public static void onLivingHurt(LivingEntity entity, DamageSource damageSource) {
      LivingEntity attacker = DamageSourceHelper.getAttacker(damageSource);
      if (attacker != null && DamageSourceHelper.isMeleeAttack(damageSource) && !entity.level().isClientSide()) {
         EquipmentHelper.iterateAbilities(ModDataComponents.ATTACK_EFFECTS.get(), attacker, true, true, (ability, stack) -> {
            for (AttackEffects.Entry effect : ability.entries) {
               if (effect.shouldApply(entity)) {
                  entity.addEffect(effect.provider().createEffect(), attacker);
                  if (attacker instanceof Player player) {
                     player.getCooldowns().addCooldown(stack.getItem(), effect.cooldown().get() * 20);
                  }
               }
            }
         });
      }
   }

   public record Entry(MobEffectProvider provider, Value<Double> chance, Value<Integer> cooldown) implements EquipmentAbility {
      public static final Codec<AttackEffects.Entry> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               MobEffectProvider.codec(true).fieldOf("effect").forGetter(AttackEffects.Entry::provider),
               ValueTypes.FRACTION.codec().optionalFieldOf("chance", Value.of(1.0)).forGetter(AttackEffects.Entry::chance),
               ValueTypes.DURATION.codec().optionalFieldOf("cooldown", Value.of(0)).forGetter(AttackEffects.Entry::cooldown)
            )
            .apply(instance, AttackEffects.Entry::new)
      );
      public static final StreamCodec<RegistryFriendlyByteBuf, AttackEffects.Entry> STREAM_CODEC = StreamCodec.composite(
         MobEffectProvider.STREAM_CODEC,
         AttackEffects.Entry::provider,
         ValueTypes.FRACTION.streamCodec(),
         AttackEffects.Entry::chance,
         ValueTypes.DURATION.streamCodec(),
         AttackEffects.Entry::cooldown,
         AttackEffects.Entry::new
      );

      public boolean shouldApply(LivingEntity entity) {
         return this.isNonCosmetic() && this.chance().get() > entity.getRandom().nextDouble();
      }

      @Override
      public boolean isNonCosmetic() {
         return this.provider().isNonCosmetic() && this.chance().get() > 0.0;
      }

      @Override
      public void addToTooltip(EquipmentAbility.TooltipWriter writer) {
         for (Holder<MobEffect> mobEffect : AttackEffects.CUSTOM_TOOLTIP_MOB_EFFECTS) {
            if (mobEffect.isBound() && mobEffect.value() == this.provider.mobEffect().value() && this.isNonCosmetic()) {
               String name = Objects.requireNonNull(BuiltInRegistries.MOB_EFFECT.getKey((MobEffect)mobEffect.value())).getPath();
               if (Mth.equal(this.chance().get(), 1.0)) {
                  writer.add(name + ".constant");
               } else {
                  writer.add(name + ".chance");
               }

               return;
            }
         }
      }
   }
}
