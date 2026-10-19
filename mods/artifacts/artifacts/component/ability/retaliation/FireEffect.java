package artifacts.component.ability.retaliation;

import artifacts.component.ability.EquipmentAbility;
import artifacts.config.value.Value;
import artifacts.config.value.ValueTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class FireEffect extends RetaliationEffect {
   public static final Codec<FireEffect> CODEC = RecordCodecBuilder.create(
      instance -> codecStart(instance)
         .and(ValueTypes.DURATION.codec().fieldOf("duration").forGetter(FireEffect::fireDuration))
         .and(ValueTypes.BOOLEAN.codec().optionalFieldOf("grant_fire_resistance", Value.of(true)).forGetter(FireEffect::grantsFireResistance))
         .apply(instance, FireEffect::new)
   );
   public static final StreamCodec<ByteBuf, FireEffect> STREAM_CODEC = StreamCodec.composite(
      ValueTypes.FRACTION.streamCodec(),
      RetaliationEffect::strikeChance,
      ValueTypes.DURATION.streamCodec(),
      RetaliationEffect::cooldown,
      ValueTypes.DURATION.streamCodec(),
      FireEffect::fireDuration,
      ValueTypes.BOOLEAN.streamCodec(),
      FireEffect::grantsFireResistance,
      FireEffect::new
   );
   private final Value<Integer> fireDuration;
   private final Value<Boolean> grantsFireResistance;

   public FireEffect(Value<Double> strikeChance, Value<Integer> cooldown, Value<Integer> fireDuration, Value<Boolean> grantsFireResistance) {
      super("fire", strikeChance, cooldown);
      this.fireDuration = fireDuration;
      this.grantsFireResistance = grantsFireResistance;
   }

   public Value<Integer> fireDuration() {
      return this.fireDuration;
   }

   public Value<Boolean> grantsFireResistance() {
      return this.grantsFireResistance;
   }

   @Override
   public boolean isNonCosmetic() {
      return super.isNonCosmetic() && this.fireDuration().get() > 0;
   }

   @Override
   protected void applyEffect(LivingEntity target, LivingEntity attacker) {
      if (!attacker.fireImmune() && attacker.attackable() && this.fireDuration().get() > 0) {
         if (this.grantsFireResistance().get()) {
            target.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, this.fireDuration().get() * 20, 0, false, false, true));
         }

         attacker.igniteForSeconds(this.fireDuration().get().intValue());
      }
   }

   @Override
   public void addToTooltip(EquipmentAbility.TooltipWriter writer) {
      super.addToTooltip(writer);
      if (this.grantsFireResistance().get()) {
         writer.add("fire.fire_resistance");
      }
   }

   @Override
   public final boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o instanceof FireEffect that) {
         return !super.equals(o) ? false : this.fireDuration.equals(that.fireDuration) && this.grantsFireResistance.equals(that.grantsFireResistance);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int result = super.hashCode();
      result = 31 * result + this.fireDuration.hashCode();
      return 31 * result + this.grantsFireResistance.hashCode();
   }
}
