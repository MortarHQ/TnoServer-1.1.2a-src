package com.github.b4ndithelps.tennogamenolife.registry.item;

import com.github.b4ndithelps.tennogamenolife.item.ExMachinasWeaponItem;
import com.github.b4ndithelps.tennogamenolife.item.MagItem;
import com.github.b4ndithelps.tennogamenolife.item.MechanicalCoreItem;
import com.github.b4ndithelps.tennogamenolife.item.SickleItem;
import com.github.b4ndithelps.tennogamenolife.item.StarEssence;
import com.github.b4ndithelps.tennogamenolife.registry.sound.TenreincarnationJukeboxSongs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.Items;

public class Tenreincarnationitems {
   public static final Items ITEMS = DeferredRegister.createItems("tennogamenolife");
   public static final DeferredItem<Item> MAG = ITEMS.register("mag", () -> new MagItem(new Properties().rarity(Rarity.EPIC)));
   public static final DeferredItem<Item> MECHANICAL_CORE = ITEMS.register(
      "mechanical_core", () -> new MechanicalCoreItem(new Properties().rarity(Rarity.EPIC))
   );
   public static final DeferredItem<Item> STAR_ESSENCE = ITEMS.register("star_essence", () -> new StarEssence(new Properties().rarity(Rarity.EPIC)));
   public static final DeferredItem<Item> MUSIC_DISC_GAME_OF_LIFE = ITEMS.register(
      "music_disc_game_of_life", () -> new Item(new Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(TenreincarnationJukeboxSongs.GAME_OF_LIFE))
   );
   public static final DeferredItem<Item> SICKLE = ITEMS.register("sickle", SickleItem::new);
   public static final DeferredItem<Item> EX_MACHINAS_WEAPON = ITEMS.register("ex_machinas_weapon", () -> new ExMachinasWeaponItem());

   public static void register(IEventBus modEventBus) {
      ITEMS.register(modEventBus);
   }
}
