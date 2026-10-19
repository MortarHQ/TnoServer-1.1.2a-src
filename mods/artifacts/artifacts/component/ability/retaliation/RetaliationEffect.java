package artifacts.component.ability.retaliation;

import artifacts.component.ability.EquipmentAbility;
import artifacts.config.value.Value;
import artifacts.config.value.ValueTypes;
import artifacts.registry.ModDataComponents;
import artifacts.util.DamageSourceHelper;
import com.mojang.datafixers.Products.P2;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public abstract class RetaliationEffect implements EquipmentAbility {
   private final String name;
   private final Value<Double> strikeChance;
   private final Value<Integer> cooldown;

   protected static <T extends RetaliationEffect> P2<Mu<T>, Value<Double>, Value<Integer>> codecStart(Instance<T> instance) {
      return instance.group(
         ValueTypes.FRACTION.codec().fieldOf("chance").forGetter(RetaliationEffect::strikeChance),
         ValueTypes.cooldownField().forGetter(RetaliationEffect::cooldown)
      );
   }

   public RetaliationEffect(String name, Value<Double> strikeChance, Value<Integer> cooldown) {
      this.name = name;
      this.strikeChance = strikeChance;
      this.cooldown = cooldown;
   }

   public Value<Double> strikeChance() {
      return this.strikeChance;
   }

   public Value<Integer> cooldown() {
      return this.cooldown;
   }

   public void onLivingHurt(LivingEntity entity, ItemStack stack, DamageSource damageSource) {
      LivingEntity attacker = DamageSourceHelper.getAttacker(damageSource);
      if (attacker != null && !stack.has(ModDataComponents.DISABLED_BY_TOGGLE.get()) && entity.getRandom().nextDouble() < this.strikeChance().get()) {
         this.applyEffect(entity, attacker);
         if (entity instanceof Player player && this.cooldown().get() > 0) {
            player.getCooldowns().addCooldown(stack.getItem(), this.cooldown().get() * 20);
         }
      }
   }

   protected abstract void applyEffect(LivingEntity var1, LivingEntity var2);

   @Override
   public boolean isNonCosmetic() {
      return !Mth.equal(this.strikeChance().get(), 0.0);
   }

   @Override
   public void addToTooltip(EquipmentAbility.TooltipWriter writer) {
      if (Mth.equal(this.strikeChance().get(), 1.0)) {
         writer.add(this.name + ".constant");
      } else {
         writer.add(this.name + ".chance", Math.round(this.strikeChance().get() * 100.0));
      }
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else {
         return !(o instanceof RetaliationEffect that) ? false : this.strikeChance.equals(that.strikeChance) && this.cooldown.equals(that.cooldown);
      }
   }

   @Override
   public int hashCode() {
      int result = this.strikeChance.hashCode();
      return 31 * result + this.cooldown.hashCode();
   }
}
