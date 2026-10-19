package net.p3pp3rf1y.sophisticatedcore.upgrades.alchemy;

import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.serialization.Codec;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

public enum AlchemyCondition implements StringRepresentable {
   NEVER(le -> false),
   ALWAYS(le -> true),
   UNDER_WATER(Entity::isUnderWater),
   ON_FIRE(Entity::isOnFire),
   FALLING(le -> le.fallDistance > 2.0F),
   MINING(le -> le instanceof ServerPlayer serverPlayer && serverPlayer.gameMode.isDestroyingBlock),
   SPRINTING(Entity::isSprinting),
   HURT((le, v) -> le.getHealth() > 0.0F && le.getHealth() < le.getMaxHealth() && le.getHealth() / le.getMaxHealth() < v, 0.75F),
   NEGATIVE_EFFECT(
      (le, v) -> le.getActiveEffects().stream().anyMatch(effect -> ((MobEffect)effect.getEffect().value()).getCategory() == MobEffectCategory.HARMFUL)
   );

   public static final Codec<AlchemyCondition> CODEC = StringRepresentable.fromEnum(AlchemyCondition::values);
   public static final StreamCodec<FriendlyByteBuf, AlchemyCondition> STREAM_CODEC = NeoForgeStreamCodecs.enumCodec(AlchemyCondition.class);
   private final BiPredicate<LivingEntity, Float> predicate;
   private final float defaultValue;
   private static final Map<String, AlchemyCondition> NAME_VALUES;
   private static final AlchemyCondition[] VALUES;

   AlchemyCondition(BiPredicate<LivingEntity, Float> predicate) {
      this(predicate, -1.0F);
   }

   AlchemyCondition(BiPredicate<LivingEntity, Float> predicate, float defaultValue) {
      this.predicate = predicate;
      this.defaultValue = defaultValue;
   }

   AlchemyCondition(Predicate<LivingEntity> predicate) {
      this.predicate = (le, v) -> predicate.test(le);
      this.defaultValue = -1.0F;
   }

   public boolean test(LivingEntity livingEntity, float value) {
      return this.predicate.test(livingEntity, value);
   }

   public String getSerializedName() {
      return this.name().toLowerCase(Locale.ROOT);
   }

   public AlchemyCondition next() {
      return VALUES[(this.ordinal() + 1) % VALUES.length];
   }

   public static AlchemyCondition fromName(String name) {
      return NAME_VALUES.getOrDefault(name, NEVER);
   }

   public float defaultValue() {
      return this.defaultValue;
   }

   static {
      Builder<String, AlchemyCondition> builder = new Builder();

      for (AlchemyCondition value : values()) {
         builder.put(value.getSerializedName(), value);
      }

      NAME_VALUES = builder.build();
      VALUES = values();
   }
}
