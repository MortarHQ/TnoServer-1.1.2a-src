package team.lodestar.lodestone.systems.enchanting;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.MapCodec;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;

public class LodestoneEntityEnchantmentEffectFilter<T extends EnchantmentEntityEffect> {
   private final Class<T> classFilter;
   @Nullable
   private final MapCodec<? extends EnchantmentEntityEffect> typeFilter;
   @Nullable
   private Holder<Enchantment> enchantmentFilter;

   public static <T extends EnchantmentEntityEffect> LodestoneEntityEnchantmentEffectFilter<T> byType(Class<T> effectClass) {
      return new LodestoneEntityEnchantmentEffectFilter<>(effectClass);
   }

   public static LodestoneEntityEnchantmentEffectFilter<EnchantmentEntityEffect> byRegistry(Holder<MapCodec<? extends EnchantmentEntityEffect>> effectType) {
      return byRegistry((MapCodec<? extends EnchantmentEntityEffect>)effectType.value());
   }

   public static LodestoneEntityEnchantmentEffectFilter<EnchantmentEntityEffect> byRegistry(MapCodec<? extends EnchantmentEntityEffect> effectType) {
      return new LodestoneEntityEnchantmentEffectFilter<>(EnchantmentEntityEffect.class, effectType);
   }

   public LodestoneEntityEnchantmentEffectFilter(Class<T> classFilter) {
      this(classFilter, null);
   }

   public LodestoneEntityEnchantmentEffectFilter(Class<T> classFilter, @Nullable MapCodec<? extends EnchantmentEntityEffect> typeFilter) {
      this.classFilter = classFilter;
      this.typeFilter = typeFilter;
   }

   public LodestoneEntityEnchantmentEffectFilter<T> withEnchantmentFilter(Holder<Enchantment> enchantment) {
      this.enchantmentFilter = enchantment;
      return this;
   }

   protected Either<Predicate<TypedDataComponent<?>>, Predicate<T>> asCondition() {
      return Either.right((Predicate<EnchantmentEntityEffect>)e -> this.typeFilter == null || e.codec().equals(this.typeFilter));
   }

   public Class<T> getClassFilter() {
      return this.classFilter;
   }

   @Nullable
   public Holder<Enchantment> getEnchantmentFilter() {
      return this.enchantmentFilter;
   }
}
