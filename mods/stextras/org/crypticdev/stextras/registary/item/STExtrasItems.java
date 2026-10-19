package org.crypticdev.stextras.registary.item;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.tensura.item.TensuraToolTiers;
import io.github.manasmods.tensura.item.weapon.SimpleLongSwordItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;

public final class STExtrasItems {
   private static final DeferredRegister<Item> ITEMS = DeferredRegister.create("stextras", Registries.ITEM);
   private static final ResourceKey<CreativeModeTab> TENSURA_GEARS_TAB = ResourceKey.create(
      Registries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath("tensura", "03-gears")
   );
   public static final RegistrySupplier<Item> RIMURU_KATANA = ITEMS.register(
      "rimuru_katana", () -> new SimpleLongSwordItem(TensuraToolTiers.PURE_MAGISTEEL, new Properties().fireResistant().rarity(Rarity.EPIC))
   );

   private STExtrasItems() {
   }

   public static void init() {
      ITEMS.register();
      CreativeTabRegistry.append(TENSURA_GEARS_TAB, new RegistrySupplier[]{RIMURU_KATANA});
   }
}
