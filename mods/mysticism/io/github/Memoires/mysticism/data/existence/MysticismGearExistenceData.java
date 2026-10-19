package io.github.Memoires.mysticism.data.existence;

import io.github.Memoires.mysticism.enchantment.MysticismEnchantments;
import io.github.Memoires.mysticism.registry.item.MysticismWeaponItems;
import io.github.manasmods.tensura.data.existence.gear.GearExistenceData;
import io.github.manasmods.tensura.data.existence.gear.UniqueGearEvolutionHelper;
import io.github.manasmods.tensura.enchantment.TensuraEnchantments;
import io.github.manasmods.tensura.registry.data.TensuraCustomData;
import java.util.Map;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

public class MysticismGearExistenceData {
   public static void bootstrap(BootstrapContext<GearExistenceData> context) {
      applyMysticism(context);
   }

   public static void register(BootstrapContext<GearExistenceData> context, GearExistenceData data) {
      ResourceKey<GearExistenceData> key = ResourceKey.create(TensuraCustomData.GEAR_EXISTENCE, data.gear());
      context.register(key, data);
   }

   private static void applyMysticism(BootstrapContext<GearExistenceData> context) {
      HolderGetter<Enchantment> enchantment = context.lookup(Registries.ENCHANTMENT);
      Map<Holder<Enchantment>, Integer> axiomEngrave = Map.of(
         enchantment.getOrThrow(TensuraEnchantments.TSUKUMOGAMI), 1, enchantment.getOrThrow(MysticismEnchantments.HOLY_FLAMES), 1
      );
      register(
         context,
         GearExistenceData.getDefault(MysticismWeaponItems.AXIOM.getId(), 80000, 0.025, axiomEngrave, UniqueGearEvolutionHelper.getPureMagisteelWeapons())
      );
      Map<Holder<Enchantment>, Integer> waltzEngrave = Map.of(
         enchantment.getOrThrow(TensuraEnchantments.TSUKUMOGAMI), 1, enchantment.getOrThrow(MysticismEnchantments.BOREAL_FROST), 1
      );
      register(
         context,
         GearExistenceData.getDefault(MysticismWeaponItems.WALTZ.getId(), 80000, 0.025, waltzEngrave, UniqueGearEvolutionHelper.getPureMagisteelWeapons())
      );
      register(context, GearExistenceData.getDefault(MysticismWeaponItems.RITUAL_SCYTHE.getId(), 10000, 10000, 0.04));
   }
}
