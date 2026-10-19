package com.cerbon.bosses_of_mass_destruction.item;

import com.cerbon.bosses_of_mass_destruction.block.BMDBlocks;
import com.cerbon.bosses_of_mass_destruction.item.custom.BrimstoneNectarItem;
import com.cerbon.bosses_of_mass_destruction.item.custom.ChargedEnderPearlItem;
import com.cerbon.bosses_of_mass_destruction.item.custom.CrystalFruitItem;
import com.cerbon.bosses_of_mass_destruction.item.custom.EarthdiveSpear;
import com.cerbon.bosses_of_mass_destruction.item.custom.MaterialItem;
import com.cerbon.bosses_of_mass_destruction.item.custom.SoulStarItem;
import com.cerbon.bosses_of_mass_destruction.structure.structure_repair.GauntletStructureRepair;
import com.cerbon.bosses_of_mass_destruction.structure.structure_repair.LichStructureRepair;
import com.cerbon.bosses_of_mass_destruction.structure.structure_repair.ObsidilithStructureRepair;
import com.cerbon.bosses_of_mass_destruction.structure.structure_repair.VoidBlossomStructureRepair;
import com.cerbon.cerbons_api.api.registry.RegistryEntry;
import com.cerbon.cerbons_api.api.registry.ResourcefulRegistries;
import com.cerbon.cerbons_api.api.registry.ResourcefulRegistry;
import java.util.List;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class BMDItems {
   public static final ResourcefulRegistry<Item> ITEMS = ResourcefulRegistries.create(BuiltInRegistries.ITEM, "bosses_of_mass_destruction");
   public static final RegistryEntry<Item> SOUL_STAR = ITEMS.register("soul_star", () -> new SoulStarItem(new Properties()));
   public static final RegistryEntry<Item> ANCIENT_ANIMA = ITEMS.register("ancient_anima", () -> new MaterialItem(new Properties().rarity(Rarity.RARE)));
   public static final RegistryEntry<Item> BLAZING_EYE = ITEMS.register(
      "blazing_eye", () -> new MaterialItem(new Properties().rarity(Rarity.RARE).fireResistant())
   );
   public static final RegistryEntry<Item> OBSIDIAN_HEART = ITEMS.register(
      "obsidian_heart", () -> new MaterialItem(new Properties().rarity(Rarity.RARE).fireResistant())
   );
   public static final RegistryEntry<Item> EARTHDIVE_SPEAR = ITEMS.register(
      "earthdive_spear", () -> new EarthdiveSpear(new Properties().fireResistant().durability(250))
   );
   public static final RegistryEntry<Item> VOID_THORN = ITEMS.register(
      "void_thorn", () -> new MaterialItem(new Properties().rarity(Rarity.RARE).fireResistant())
   );
   public static final RegistryEntry<Item> CRYSTAL_FRUIT = ITEMS.register(
      "crystal_fruit", () -> new CrystalFruitItem(new Properties().rarity(Rarity.RARE).fireResistant().food(BMDFoods.CRYSTAL_FRUIT))
   );
   public static final RegistryEntry<Item> CHARGED_ENDER_PEARL = ITEMS.register(
      "charged_ender_pearl", () -> new ChargedEnderPearlItem(new Properties().fireResistant().stacksTo(1))
   );
   public static final RegistryEntry<Item> BRIMSTONE_NECTAR = ITEMS.register(
      "brimstone_nectar",
      () -> new BrimstoneNectarItem(
         new Properties().rarity(Rarity.RARE).fireResistant(),
         List.of(new GauntletStructureRepair(), new LichStructureRepair(), new ObsidilithStructureRepair(), new VoidBlossomStructureRepair())
      )
   );
   public static final RegistryEntry<Item> OBSIDILITH_RUNE = ITEMS.register(
      "obsidilith_rune", () -> new BlockItem((Block)BMDBlocks.OBSIDILITH_RUNE.get(), new Properties())
   );
   public static final RegistryEntry<Item> CHISELED_STONE_ALTAR = ITEMS.register(
      "chiseled_stone_altar", () -> new BlockItem((Block)BMDBlocks.CHISELED_STONE_ALTAR.get(), new Properties())
   );
   public static final RegistryEntry<Item> VOID_BLOSSOM = ITEMS.register(
      "void_blossom", () -> new BlockItem((Block)BMDBlocks.VOID_BLOSSOM.get(), new Properties())
   );
   public static final RegistryEntry<Item> VINE_WALL = ITEMS.register("vine_wall", () -> new BlockItem((Block)BMDBlocks.VINE_WALL.get(), new Properties()));
   public static final RegistryEntry<Item> OBSIDILITH_SUMMON_BLOCK = ITEMS.register(
      "obsidilith_end_frame", () -> new BlockItem((Block)BMDBlocks.OBSIDILITH_SUMMON_BLOCK.get(), new Properties())
   );
   public static final RegistryEntry<Item> GAUNTLET_BLACKSTONE = ITEMS.register(
      "gauntlet_blackstone", () -> new BlockItem((Block)BMDBlocks.GAUNTLET_BLACKSTONE.get(), new Properties())
   );
   public static final RegistryEntry<Item> SEALED_BLACKSTONE = ITEMS.register(
      "sealed_blackstone", () -> new BlockItem((Block)BMDBlocks.SEALED_BLACKSTONE.get(), new Properties())
   );
   public static final RegistryEntry<Item> MOB_WARD = ITEMS.register("mob_ward", () -> new BlockItem((Block)BMDBlocks.MOB_WARD.get(), new Properties()));
   public static final RegistryEntry<Item> MONOLITH_BLOCK = ITEMS.register(
      "monolith_block", () -> new BlockItem((Block)BMDBlocks.MONOLITH_BLOCK.get(), new Properties())
   );
   public static final RegistryEntry<Item> LEVITATION_BLOCK = ITEMS.register(
      "levitation_block", () -> new BlockItem((Block)BMDBlocks.LEVITATION_BLOCK.get(), new Properties())
   );
   public static final RegistryEntry<Item> VOID_BLOSSOM_SUMMON_BLOCK = ITEMS.register(
      "void_blossom_block", () -> new BlockItem((Block)BMDBlocks.VOID_BLOSSOM_SUMMON_BLOCK.get(), new Properties())
   );
   public static final RegistryEntry<Item> VOID_LILY = ITEMS.register(
      "void_lily", () -> new BlockItem((Block)BMDBlocks.VOID_LILY_BLOCK.get(), new Properties())
   );

   @OnlyIn(Dist.CLIENT)
   public static void initClient() {
      ItemProperties.register((Item)EARTHDIVE_SPEAR.get(), ResourceLocation.withDefaultNamespace("throwing"), (stack, level, entity, seed) -> {
         if (entity == null) {
            return 0.0F;
         } else {
            return entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;
         }
      });
   }

   public static void register() {
      ITEMS.register();
   }
}
