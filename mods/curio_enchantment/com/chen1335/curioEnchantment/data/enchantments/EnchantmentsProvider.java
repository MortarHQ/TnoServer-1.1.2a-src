package com.chen1335.curioEnchantment.data.enchantments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantment.Builder;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

public abstract class EnchantmentsProvider {
   protected Map<ResourceKey<?>, List<ICondition>> thisConditions = new HashMap<>();

   abstract void bootstrap(BootstrapContext<Enchantment> var1);

   public void bootstrap(BootstrapContext<Enchantment> context, Map<ResourceKey<?>, List<ICondition>> conditions) {
      this.bootstrap(context);
      this.thisConditions
         .forEach(
            (resourceKey, iConditions) -> conditions.computeIfAbsent((ResourceKey<?>)resourceKey, resourceKey1 -> new ArrayList<>())
               .addAll((Collection<? extends ICondition>)iConditions)
         );
   }

   abstract String getModId();

   protected void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Builder builder) {
      this.thisConditions.computeIfAbsent(key, key1 -> new ArrayList<>()).add(new ModLoadedCondition(this.getModId()));
      context.register(key, builder.build(key.location()));
   }

   protected static ResourceKey<Enchantment> key(String name) {
      return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath("curio_enchantment", name));
   }
}
