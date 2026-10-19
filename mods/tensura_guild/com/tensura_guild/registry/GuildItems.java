package com.tensura_guild.registry;

import com.tensura_guild.item.GuildStructureMapItem;
import com.tensura_guild.item.GuildTaskItem;
import java.awt.Color;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class GuildItems {
   public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, "tensura_guild");
   public static final Supplier<Item> YUUKI_KAGURAZAKA_SPAWN_EGG = ITEMS.register(
      "yuuki_kagurazaka_spawn_egg",
      () -> new DeferredSpawnEggItem(GuildEntityTypes.YUUKI_KAGURAZAKA, new Color(40, 38, 54).getRGB(), new Color(214, 78, 84).getRGB(), new Properties())
   );
   public static final Supplier<Item> LAPLACE_SPAWN_EGG = ITEMS.register(
      "laplace_spawn_egg",
      () -> new DeferredSpawnEggItem(GuildEntityTypes.LAPLACE, new Color(92, 40, 133).getRGB(), new Color(232, 181, 79).getRGB(), new Properties())
   );
   public static final Supplier<Item> YUUKI_KAGURAZAKA_SPAWNER = ITEMS.register(
      "yuuki_kagurazaka_spawner", () -> new BlockItem(GuildBlocks.YUUKI_KAGURAZAKA_SPAWNER.get(), new Properties())
   );
   public static final Supplier<Item> GUILD_TASK = ITEMS.register("guild_task", () -> new GuildTaskItem(new Properties().stacksTo(1)));
   public static final Supplier<Item> ADVENTURER_GUILD_MAP = ITEMS.register(
      "adventurer_guild_map",
      () -> new GuildStructureMapItem(
         new Properties().stacksTo(1), ResourceLocation.fromNamespaceAndPath("tensura_guild", "adventurer_guild"), "filled_map.tensura_guild.adventurer_guild"
      )
   );
}
