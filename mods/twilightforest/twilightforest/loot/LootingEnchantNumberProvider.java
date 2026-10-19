package twilightforest.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Set;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.providers.number.LootNumberProviderType;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
import twilightforest.init.TFLoot;

public class LootingEnchantNumberProvider implements NumberProvider {
   public static final MapCodec<LootingEnchantNumberProvider> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            Enchantment.CODEC.fieldOf("enchantment").forGetter(provider -> provider.enchantment),
            NumberProviders.CODEC.fieldOf("base_value").forGetter(provider -> provider.baseValue)
         )
         .apply(instance, LootingEnchantNumberProvider::new)
   );
   private final Holder<Enchantment> enchantment;
   private final NumberProvider baseValue;

   protected LootingEnchantNumberProvider(Holder<Enchantment> enchantment, NumberProvider baseValue) {
      this.enchantment = enchantment;
      this.baseValue = baseValue;
   }

   public LootNumberProviderType getType() {
      return (LootNumberProviderType)TFLoot.LOOTING_ROLLS.get();
   }

   public static LootingEnchantNumberProvider applyLootingLevelTo(Provider provider, NumberProvider baseValue) {
      RegistryLookup<Enchantment> registrylookup = provider.lookupOrThrow(Registries.ENCHANTMENT);
      return new LootingEnchantNumberProvider(registrylookup.getOrThrow(Enchantments.LOOTING), baseValue);
   }

   public float getFloat(LootContext context) {
      return context.getParamOrNull(LootContextParams.ATTACKING_ENTITY) instanceof LivingEntity living
         ? EnchantmentHelper.getEnchantmentLevel(this.enchantment, living) + this.baseValue.getFloat(context)
         : this.baseValue.getFloat(context);
   }

   public Set<LootContextParam<?>> getReferencedContextParams() {
      return this.baseValue.getReferencedContextParams();
   }
}
