package com.progwml6.ironchest.common.item;

import com.google.common.collect.ImmutableMap;
import java.util.Arrays;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.Items;

public class IronChestsItems {
   public static final Items ITEMS = DeferredRegister.createItems("ironchest");
   public static final ImmutableMap<IronChestsUpgradeType, DeferredItem<ChestUpgradeItem>> UPGRADES = ImmutableMap.copyOf(
      Arrays.stream(IronChestsUpgradeType.values())
         .collect(
            Collectors.toMap(
               Function.identity(),
               type -> register(type.name().toLowerCase(Locale.ROOT) + "_chest_upgrade", () -> new ChestUpgradeItem(type, new Properties().stacksTo(1)))
            )
         )
   );

   private static <T extends Item> DeferredItem<T> register(String name, Supplier<T> sup) {
      return ITEMS.register(name, sup);
   }
}
