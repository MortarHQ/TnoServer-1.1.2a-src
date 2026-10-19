package com.github.wal_bos.moreskills.registry;

import com.github.wal_bos.moreskills.ability.skill.unique.ExterminationBladeItem;
import com.github.wal_bos.moreskills.item.AsmodayPrisonCubeItem;
import com.github.wal_bos.moreskills.item.AwakenedKeyOfSealingItem;
import com.github.wal_bos.moreskills.item.ExcaliburItem;
import com.github.wal_bos.moreskills.item.FeldwayAngelSummonerItem;
import com.github.wal_bos.moreskills.item.GeneticSyringeItem;
import com.github.wal_bos.moreskills.item.KeyOfSealingItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.Item.Properties;

public final class TenaddexItems {
   public static final DeferredRegister<Item> ITEMS = DeferredRegister.create("tensuramoreskills", Registries.ITEM);
   public static final RegistrySupplier<Item> EXTERMINATION_BLADE = ITEMS.register("extermination_blade", ExterminationBladeItem::new);
   public static final RegistrySupplier<Item> FELDWAY_ANGEL_SUMMONER = ITEMS.register(
      "feldway_angel_summoner", () -> new FeldwayAngelSummonerItem(new Properties().rarity(Rarity.EPIC))
   );
   public static final RegistrySupplier<Item> ASMODAY_PRISON_CUBE = ITEMS.register(
      "asmoday_prison_cube", () -> new AsmodayPrisonCubeItem(new Properties().rarity(Rarity.EPIC).fireResistant())
   );
   public static final RegistrySupplier<Item> GENETIC_SYRINGE = ITEMS.register(
      "genetic_syringe", () -> new GeneticSyringeItem(new Properties().rarity(Rarity.RARE).stacksTo(1))
   );
   public static final RegistrySupplier<Item> CAEDROS_ASHEN_HALBERD = ITEMS.register(
      "caedros_ashen_halberd",
      () -> new SwordItem(
         Tiers.NETHERITE,
         new Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC).attributes(SwordItem.createAttributes(Tiers.NETHERITE, 800000000, -3.0F))
      )
   );
   public static final RegistrySupplier<Item> KEY_OF_SEALING = ITEMS.register(
      "key_of_sealing",
      () -> new KeyOfSealingItem(
         new Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC).attributes(SwordItem.createAttributes(Tiers.NETHERITE, 70, -2.4F))
      )
   );
   public static final RegistrySupplier<Item> AWAKENED_KEY_OF_SEALING = ITEMS.register(
      "awakened_key_of_sealing",
      () -> new AwakenedKeyOfSealingItem(
         new Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC).attributes(SwordItem.createAttributes(Tiers.NETHERITE, 100, -2.2F))
      )
   );
   public static final RegistrySupplier<Item> EXCALIBUR = ITEMS.register(
      "excalibur",
      () -> new ExcaliburItem(
         Tiers.NETHERITE,
         new Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC).attributes(SwordItem.createAttributes(Tiers.NETHERITE, 40, -2.6F)),
         ExcaliburItem.Stage.EXCALIBUR
      )
   );
   public static final RegistrySupplier<Item> TRUE_EXCALIBUR = ITEMS.register(
      "true_excalibur",
      () -> new ExcaliburItem(
         Tiers.NETHERITE,
         new Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC).attributes(SwordItem.createAttributes(Tiers.NETHERITE, 70, -2.4F)),
         ExcaliburItem.Stage.TRUE_EXCALIBUR
      )
   );
   public static final RegistrySupplier<Item> DIVINE_EXCALIBUR = ITEMS.register(
      "divine_excalibur",
      () -> new ExcaliburItem(
         Tiers.NETHERITE,
         new Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC).attributes(SwordItem.createAttributes(Tiers.NETHERITE, 100, -2.2F)),
         ExcaliburItem.Stage.DIVINE_EXCALIBUR
      )
   );

   public static void init() {
      ITEMS.register();
   }
}
