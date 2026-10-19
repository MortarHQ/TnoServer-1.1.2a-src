package team.lodestar.lodestone.systems.enchanting;

import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;

public class LocatedEnchantmentEffect<T extends EnchantmentEntityEffect> {
   private final T effect;
   private final Holder<Enchantment> enchantment;
   private final int level;

   public LocatedEnchantmentEffect(T effect, Holder<Enchantment> enchantment, int level) {
      this.effect = effect;
      this.enchantment = enchantment;
      this.level = level;
   }

   public boolean isPresent() {
      return this.effect != null;
   }

   public T getEffect() {
      return this.effect;
   }

   public Holder<Enchantment> getEnchantment() {
      return this.enchantment;
   }

   public int getLevel() {
      return this.level;
   }

   public float getValue(Function<T, LevelBasedValue> valueGetter, float fallback) {
      LevelBasedValue value = valueGetter.apply(this.effect);
      return value.calculate(this.level);
   }

   public static class EmptyEnchantmentEffect<T extends EnchantmentEntityEffect> extends LocatedEnchantmentEffect<T> {
      public EmptyEnchantmentEffect() {
         super(null, null, -1);
      }

      @Override
      public float getValue(Function<T, LevelBasedValue> valueGetter, float fallback) {
         return fallback;
      }
   }
}
