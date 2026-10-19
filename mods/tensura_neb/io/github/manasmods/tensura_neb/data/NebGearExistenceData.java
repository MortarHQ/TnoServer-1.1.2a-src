package io.github.manasmods.tensura_neb.data;

import io.github.manasmods.tensura.data.existence.gear.GearExistenceData;
import io.github.manasmods.tensura.enchantment.TensuraEnchantments;
import io.github.manasmods.tensura.registry.data.TensuraCustomData;
import io.github.manasmods.tensura_neb.registry.NebItems;
import java.util.Map;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

public class NebGearExistenceData {
   public static void bootstrap(BootstrapContext<GearExistenceData> context) {
      HolderGetter<Enchantment> enchantment = context.lookup(Registries.ENCHANTMENT);
      Map<Holder<Enchantment>, Integer> nightRoseEngrave = Map.of(enchantment.getOrThrow(TensuraEnchantments.ENERGY_STEAL), 2);
      register(context, GearExistenceData.getDefault(NebItems.NIGHT_ROSE.getId(), 1000000, 0.03, nightRoseEngrave));
      register(context, GearExistenceData.getDefault(NebItems.WHITE_TIGER_AZURE_DRAGON_HALBERD.getId(), 80000, 0.03));
   }

   public static void register(BootstrapContext<GearExistenceData> context, GearExistenceData data) {
      ResourceKey<GearExistenceData> key = ResourceKey.create(TensuraCustomData.GEAR_EXISTENCE, data.gear());
      context.register(key, data);
   }
}
