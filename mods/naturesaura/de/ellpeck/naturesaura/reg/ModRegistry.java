package de.ellpeck.naturesaura.reg;

import com.mojang.authlib.GameProfile;
import de.ellpeck.naturesaura.Helper;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.container.ItemAuraContainer;
import de.ellpeck.naturesaura.api.misc.ILevelData;
import de.ellpeck.naturesaura.blocks.BlockAncientLeaves;
import de.ellpeck.naturesaura.blocks.BlockAncientLog;
import de.ellpeck.naturesaura.blocks.BlockAncientSapling;
import de.ellpeck.naturesaura.blocks.BlockAnimalContainer;
import de.ellpeck.naturesaura.blocks.BlockAnimalGenerator;
import de.ellpeck.naturesaura.blocks.BlockAnimalSpawner;
import de.ellpeck.naturesaura.blocks.BlockAuraBloom;
import de.ellpeck.naturesaura.blocks.BlockAuraDetector;
import de.ellpeck.naturesaura.blocks.BlockAuraTimer;
import de.ellpeck.naturesaura.blocks.BlockAutoCrafter;
import de.ellpeck.naturesaura.blocks.BlockBlastFurnaceBooster;
import de.ellpeck.naturesaura.blocks.BlockChorusGenerator;
import de.ellpeck.naturesaura.blocks.BlockChunkLoader;
import de.ellpeck.naturesaura.blocks.BlockDecayedLeaves;
import de.ellpeck.naturesaura.blocks.BlockDimensionRail;
import de.ellpeck.naturesaura.blocks.BlockEndFlower;
import de.ellpeck.naturesaura.blocks.BlockEnderCrate;
import de.ellpeck.naturesaura.blocks.BlockFieldCreator;
import de.ellpeck.naturesaura.blocks.BlockFireworkGenerator;
import de.ellpeck.naturesaura.blocks.BlockFlowerGenerator;
import de.ellpeck.naturesaura.blocks.BlockFlowerPot;
import de.ellpeck.naturesaura.blocks.BlockFurnaceHeater;
import de.ellpeck.naturesaura.blocks.BlockGeneratorLimitRemover;
import de.ellpeck.naturesaura.blocks.BlockGoldPowder;
import de.ellpeck.naturesaura.blocks.BlockGoldenLeaves;
import de.ellpeck.naturesaura.blocks.BlockGratedChute;
import de.ellpeck.naturesaura.blocks.BlockHopperUpgrade;
import de.ellpeck.naturesaura.blocks.BlockImpl;
import de.ellpeck.naturesaura.blocks.BlockItemDistributor;
import de.ellpeck.naturesaura.blocks.BlockLight;
import de.ellpeck.naturesaura.blocks.BlockLowerLimiter;
import de.ellpeck.naturesaura.blocks.BlockMossGenerator;
import de.ellpeck.naturesaura.blocks.BlockNatureAltar;
import de.ellpeck.naturesaura.blocks.BlockNetherGrass;
import de.ellpeck.naturesaura.blocks.BlockOakGenerator;
import de.ellpeck.naturesaura.blocks.BlockOfferingTable;
import de.ellpeck.naturesaura.blocks.BlockPickupStopper;
import de.ellpeck.naturesaura.blocks.BlockPlacer;
import de.ellpeck.naturesaura.blocks.BlockPotionGenerator;
import de.ellpeck.naturesaura.blocks.BlockPowderPlacer;
import de.ellpeck.naturesaura.blocks.BlockProjectileGenerator;
import de.ellpeck.naturesaura.blocks.BlockRFConverter;
import de.ellpeck.naturesaura.blocks.BlockSlimeSplitGenerator;
import de.ellpeck.naturesaura.blocks.BlockSnowCreator;
import de.ellpeck.naturesaura.blocks.BlockSpawnLamp;
import de.ellpeck.naturesaura.blocks.BlockSpring;
import de.ellpeck.naturesaura.blocks.BlockStairsNA;
import de.ellpeck.naturesaura.blocks.BlockTimeChanger;
import de.ellpeck.naturesaura.blocks.BlockWeatherChanger;
import de.ellpeck.naturesaura.blocks.BlockWoodStand;
import de.ellpeck.naturesaura.blocks.ModBlocks;
import de.ellpeck.naturesaura.blocks.Slab;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityAuraBloom;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityEnderCrate;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityImpl;
import de.ellpeck.naturesaura.blocks.tiles.ModBlockEntities;
import de.ellpeck.naturesaura.compat.Compat;
import de.ellpeck.naturesaura.compat.patchouli.PatchouliCompat;
import de.ellpeck.naturesaura.entities.EntityEffectInhibitor;
import de.ellpeck.naturesaura.entities.EntityLightProjectile;
import de.ellpeck.naturesaura.entities.EntityMoverMinecart;
import de.ellpeck.naturesaura.entities.EntityStructureFinder;
import de.ellpeck.naturesaura.entities.ModEntities;
import de.ellpeck.naturesaura.gen.LevelGenAncientTree;
import de.ellpeck.naturesaura.gen.LevelGenAuraBloom;
import de.ellpeck.naturesaura.gen.LevelGenNetherWartMushroom;
import de.ellpeck.naturesaura.gen.ModFeatures;
import de.ellpeck.naturesaura.gui.ContainerEnderCrate;
import de.ellpeck.naturesaura.gui.GuiEnderCrate;
import de.ellpeck.naturesaura.gui.ModContainers;
import de.ellpeck.naturesaura.items.ItemAuraBottle;
import de.ellpeck.naturesaura.items.ItemAuraCache;
import de.ellpeck.naturesaura.items.ItemBirthSpirit;
import de.ellpeck.naturesaura.items.ItemBreakPrevention;
import de.ellpeck.naturesaura.items.ItemCaveFinder;
import de.ellpeck.naturesaura.items.ItemColorChanger;
import de.ellpeck.naturesaura.items.ItemCrimsonMeal;
import de.ellpeck.naturesaura.items.ItemDeathRing;
import de.ellpeck.naturesaura.items.ItemEffectPowder;
import de.ellpeck.naturesaura.items.ItemEnderAccess;
import de.ellpeck.naturesaura.items.ItemEye;
import de.ellpeck.naturesaura.items.ItemGlowing;
import de.ellpeck.naturesaura.items.ItemGoldFiber;
import de.ellpeck.naturesaura.items.ItemImpl;
import de.ellpeck.naturesaura.items.ItemLightStaff;
import de.ellpeck.naturesaura.items.ItemLootFinder;
import de.ellpeck.naturesaura.items.ItemMoverMinecart;
import de.ellpeck.naturesaura.items.ItemMultiblockMaker;
import de.ellpeck.naturesaura.items.ItemNetheriteFinder;
import de.ellpeck.naturesaura.items.ItemPetReviver;
import de.ellpeck.naturesaura.items.ItemRangeVisualizer;
import de.ellpeck.naturesaura.items.ItemShockwaveCreator;
import de.ellpeck.naturesaura.items.ItemStructureFinder;
import de.ellpeck.naturesaura.items.ModItems;
import de.ellpeck.naturesaura.items.tools.ItemArmor;
import de.ellpeck.naturesaura.items.tools.ItemAxe;
import de.ellpeck.naturesaura.items.tools.ItemHoe;
import de.ellpeck.naturesaura.items.tools.ItemPickaxe;
import de.ellpeck.naturesaura.items.tools.ItemShovel;
import de.ellpeck.naturesaura.items.tools.ItemSword;
import de.ellpeck.naturesaura.potion.ModPotions;
import de.ellpeck.naturesaura.potion.PotionBreathless;
import de.ellpeck.naturesaura.recipes.ModRecipes;
import de.ellpeck.naturesaura.renderers.PlayerLayerTrinkets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin.Model;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.capabilities.Capabilities.EnergyStorage;
import net.neoforged.neoforge.capabilities.Capabilities.FluidHandler;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.AddLayers;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;
import net.neoforged.neoforge.registries.RegisterEvent.RegisterHelper;
import vazkii.patchouli.api.PatchouliAPI;

@EventBusSubscriber
public final class ModRegistry {
   public static final List<IModItem> ALL_ITEMS = new ArrayList<>();
   public static final GameProfile FAKE_PLAYER = new GameProfile(UUID.fromString("4ce3b06b-e254-4992-8a5a-d1e56d24601f"), "[NaturesAura]");

   @SubscribeEvent
   public static void register(RegisterEvent event) {
      event.register(
         Registries.BLOCK,
         h -> {
            Block temp;
            BlockImpl var3;
            BlockImpl var4;
            BlockAuraBloom var5;
            BlockAuraBloom var6;
            BlockAuraBloom var7;
            BlockAuraBloom var8;
            BlockAuraBloom var9;
            registerAll(
               h,
               new BlockAncientLog("ancient_log"),
               new BlockAncientLog("ancient_bark"),
               temp = new BlockImpl("ancient_planks", Properties.of().sound(SoundType.WOOD).strength(2.0F)),
               new BlockStairsNA("ancient_stairs", "ancient_planks", temp.defaultBlockState(), Properties.ofFullCopy(temp)),
               new Slab("ancient_slab", "ancient_planks", Properties.ofFullCopy(temp)),
               new BlockAncientLeaves(),
               new BlockAncientSapling(),
               new BlockNatureAltar(),
               new BlockDecayedLeaves(),
               new BlockGoldenLeaves(),
               new BlockGoldPowder(),
               new BlockWoodStand(),
               var3 = new BlockImpl("infused_stone", Properties.of().sound(SoundType.STONE).strength(1.75F)),
               new BlockStairsNA("infused_stairs", "infused_stone", var3.defaultBlockState(), Properties.ofFullCopy(var3)),
               new Slab("infused_slab", "infused_stone", Properties.ofFullCopy(var3)),
               var4 = new BlockImpl("infused_brick", Properties.of().sound(SoundType.STONE).strength(1.5F)),
               new BlockStairsNA("infused_brick_stairs", "infused_brick", var4.defaultBlockState(), Properties.ofFullCopy(var4)),
               new Slab("infused_brick_slab", "infused_brick", Properties.ofFullCopy(var4)),
               new BlockFurnaceHeater(),
               new BlockPotionGenerator(),
               new BlockAuraDetector(),
               new BlockImpl("conversion_catalyst", Properties.of().sound(SoundType.STONE).strength(2.5F)),
               new BlockImpl("crushing_catalyst", Properties.of().sound(SoundType.STONE).strength(2.5F)),
               new BlockFlowerGenerator(),
               new BlockPlacer(),
               new BlockHopperUpgrade(),
               new BlockFieldCreator(),
               new BlockOakGenerator(),
               new BlockImpl("infused_iron_block", Properties.of().sound(SoundType.METAL).strength(3.0F)),
               new BlockOfferingTable(),
               new BlockPickupStopper(),
               new BlockSpawnLamp(),
               new BlockAnimalGenerator(),
               new BlockEndFlower(),
               new BlockGratedChute(),
               new BlockAnimalSpawner(),
               new BlockAutoCrafter(),
               new BlockImpl("gold_brick", Properties.ofFullCopy(Blocks.STONE_BRICKS)),
               new BlockImpl("gold_nether_brick", Properties.ofFullCopy(Blocks.NETHER_BRICKS)),
               new BlockMossGenerator(),
               new BlockTimeChanger(),
               new BlockGeneratorLimitRemover(),
               new BlockEnderCrate(),
               new BlockPowderPlacer(),
               new BlockFireworkGenerator(),
               new BlockProjectileGenerator(),
               new BlockDimensionRail("overworld", Level.OVERWORLD, Level.NETHER, Level.END),
               new BlockDimensionRail("nether", Level.NETHER, Level.OVERWORLD),
               new BlockDimensionRail("end", Level.END, Level.OVERWORLD),
               new BlockBlastFurnaceBooster(),
               new BlockImpl("nether_wart_mushroom", Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK)),
               new BlockAnimalContainer(),
               new BlockSnowCreator(),
               new BlockItemDistributor(),
               var5 = new BlockAuraBloom("aura_bloom", Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.FARMLAND),
               createFlowerPot(var5),
               var6 = new BlockAuraBloom("aura_cactus", Blocks.SAND, Blocks.RED_SAND),
               createFlowerPot(var6),
               var7 = new BlockAuraBloom("warped_aura_mushroom", Blocks.WARPED_NYLIUM),
               createFlowerPot(var7),
               var8 = new BlockAuraBloom("crimson_aura_mushroom", Blocks.CRIMSON_NYLIUM),
               createFlowerPot(var8),
               var9 = new BlockAuraBloom("aura_mushroom", Blocks.MYCELIUM),
               createFlowerPot(var9),
               new BlockImpl("tainted_gold_block", Properties.of().sound(SoundType.METAL).strength(3.0F)),
               new BlockNetherGrass(),
               new BlockLight(),
               new BlockChorusGenerator(),
               new BlockAuraTimer(),
               new BlockSlimeSplitGenerator(),
               new BlockSpring(),
               new BlockWeatherChanger(),
               new BlockRFConverter(),
               new BlockChunkLoader(),
               new BlockLowerLimiter(),
               new BlockImpl("sky_ingot_block", Properties.of().sound(SoundType.METAL).strength(4.0F)),
               new BlockImpl("depth_ingot_block", Properties.of().sound(SoundType.METAL).strength(6.0F))
            );
            Helper.populateObjectHolders(ModBlocks.class, event.getRegistry(), false);
         }
      );
      event.register(
         Registries.ITEM,
         h -> {
            for (IModItem block : ALL_ITEMS) {
               if (block instanceof Block && !(block instanceof INoItemBlock)) {
                  BlockItem item = new BlockItem((Block)block, new net.minecraft.world.item.Item.Properties());
                  h.register(ResourceLocation.fromNamespaceAndPath("naturesaura", block.getBaseName()), item);
               }
            }

            Item temp;
            registerAll(
               h,
               new ItemPickaxe("infused_iron_pickaxe", ModItemTier.INFUSED, 1, -2.8F),
               new ItemAxe("infused_iron_axe", ModItemTier.INFUSED, 6.0F, -3.1F),
               new ItemShovel("infused_iron_shovel", ModItemTier.INFUSED, 1.5F, -3.0F),
               new ItemHoe("infused_iron_hoe", ModItemTier.INFUSED, -1),
               new ItemSword("infused_iron_sword", ModItemTier.INFUSED, 3, -2.4F),
               new ItemArmor("infused_iron_helmet", ModArmorMaterial.INFUSED, Type.HELMET),
               new ItemArmor("infused_iron_chest", ModArmorMaterial.INFUSED, Type.CHESTPLATE),
               new ItemArmor("infused_iron_pants", ModArmorMaterial.INFUSED, Type.LEGGINGS),
               new ItemArmor("infused_iron_shoes", ModArmorMaterial.INFUSED, Type.BOOTS),
               new ItemEye("eye"),
               new ItemEye("eye_improved"),
               new ItemGoldFiber(),
               new ItemImpl("gold_leaf"),
               new ItemImpl("infused_iron"),
               new ItemImpl("ancient_stick"),
               new ItemColorChanger(),
               new ItemAuraCache("aura_cache"),
               new ItemAuraCache("aura_trove"),
               new ItemShockwaveCreator(),
               new ItemMultiblockMaker(),
               temp = new ItemImpl("bottle_two_the_rebottling"),
               new ItemAuraBottle(temp),
               new ItemImpl("farming_stencil"),
               new ItemImpl("sky_ingot"),
               new ItemGlowing("calling_spirit"),
               new ItemEffectPowder(),
               new ItemBirthSpirit(),
               new ItemMoverMinecart(),
               new ItemRangeVisualizer(),
               new ItemImpl("clock_hand"),
               new ItemImpl("token_joy"),
               new ItemImpl("token_fear"),
               new ItemImpl("token_anger"),
               new ItemImpl("token_sorrow"),
               new ItemImpl("token_euphoria"),
               new ItemImpl("token_terror"),
               new ItemImpl("token_rage"),
               new ItemImpl("token_grief"),
               new ItemEnderAccess(),
               new ItemCaveFinder(),
               new ItemCrimsonMeal(),
               new ItemDeathRing(),
               new ItemImpl("tainted_gold"),
               new ItemLootFinder(),
               new ItemLightStaff(),
               new ItemPickaxe("sky_pickaxe", ModItemTier.SKY, 1, -2.8F),
               new ItemAxe("sky_axe", ModItemTier.SKY, 5.0F, -3.0F),
               new ItemShovel("sky_shovel", ModItemTier.SKY, 1.5F, -3.0F),
               new ItemHoe("sky_hoe", ModItemTier.SKY, -1),
               new ItemSword("sky_sword", ModItemTier.SKY, 3, -2.4F),
               new ItemArmor("sky_helmet", ModArmorMaterial.SKY, Type.HELMET),
               new ItemArmor("sky_chest", ModArmorMaterial.SKY, Type.CHESTPLATE),
               new ItemArmor("sky_pants", ModArmorMaterial.SKY, Type.LEGGINGS),
               new ItemArmor("sky_shoes", ModArmorMaterial.SKY, Type.BOOTS),
               new ItemStructureFinder("fortress_finder", BuiltinStructures.FORTRESS, 12199936),
               new ItemStructureFinder("end_city_finder", BuiltinStructures.END_CITY, 13262038),
               new ItemStructureFinder("outpost_finder", BuiltinStructures.PILLAGER_OUTPOST, 11247512),
               new ItemBreakPrevention(),
               new ItemPetReviver(),
               new ItemNetheriteFinder(),
               new ItemImpl("vacuum_bottle"),
               new ItemImpl("depth_ingot"),
               new ItemPickaxe("depth_pickaxe", ModItemTier.DEPTH, 1, -2.8F),
               new ItemAxe("depth_axe", ModItemTier.DEPTH, 5.0F, -3.0F),
               new ItemShovel("depth_shovel", ModItemTier.DEPTH, 1.5F, -3.0F),
               new ItemHoe("depth_hoe", ModItemTier.DEPTH, -1),
               new ItemSword("depth_sword", ModItemTier.DEPTH, 3, -2.4F),
               new ItemArmor("depth_helmet", ModArmorMaterial.DEPTH, Type.HELMET),
               new ItemArmor("depth_chest", ModArmorMaterial.DEPTH, Type.CHESTPLATE),
               new ItemArmor("depth_pants", ModArmorMaterial.DEPTH, Type.LEGGINGS),
               new ItemArmor("depth_shoes", ModArmorMaterial.DEPTH, Type.BOOTS)
            );
            Helper.populateObjectHolders(ModItems.class, event.getRegistry(), false);
         }
      );
      event.register(
         Registries.BLOCK_ENTITY_TYPE,
         h -> {
            ALL_ITEMS.add(
               new ModTileType(BlockEntityAuraBloom::new, "aura_bloom", ALL_ITEMS.stream().filter(i -> i instanceof BlockAuraBloom).toArray(IModItem[]::new))
            );

            for (IModItem item : ALL_ITEMS) {
               if (item instanceof ModTileType<?> type) {
                  h.register(ResourceLocation.fromNamespaceAndPath("naturesaura", type.getBaseName()), type.type);
               }
            }

            Helper.populateObjectHolders(ModBlockEntities.class, event.getRegistry(), false);
         }
      );
      event.register(Registries.MOB_EFFECT, h -> {
         h.register(ResourceLocation.fromNamespaceAndPath("naturesaura", "breathless"), new PotionBreathless());
         Helper.populateObjectHolders(ModPotions.class, event.getRegistry(), true);
      });
      event.register(Registries.MENU, h -> {
         h.register(ResourceLocation.fromNamespaceAndPath("naturesaura", "ender_crate"), IMenuTypeExtension.create((windowId, inv, data) -> {
            BlockEntity tile = inv.player.level().getBlockEntity(data.readBlockPos());
            if (tile instanceof BlockEntityEnderCrate) {
               IItemHandler handler = (IItemHandler)tile.getLevel().getCapability(ItemHandler.BLOCK, tile.getBlockPos(), tile.getBlockState(), tile, null);
               return new ContainerEnderCrate(ModContainers.ENDER_CRATE, windowId, inv.player, handler);
            } else {
               return null;
            }
         }));
         h.register(ResourceLocation.fromNamespaceAndPath("naturesaura", "ender_access"), IMenuTypeExtension.create((windowId, inv, data) -> {
            IItemHandler handler = ILevelData.getOverworldData(inv.player.level()).getEnderStorage(data.readUtf());
            return new ContainerEnderCrate(ModContainers.ENDER_ACCESS, windowId, inv.player, handler);
         }));
         Helper.populateObjectHolders(ModContainers.class, event.getRegistry(), false);
      });
      event.register(
         Registries.ENTITY_TYPE,
         h -> {
            h.register(
               ResourceLocation.fromNamespaceAndPath("naturesaura", "mover_cart"),
               Builder.of(EntityMoverMinecart::new, MobCategory.MISC)
                  .sized(1.0F, 1.0F)
                  .setShouldReceiveVelocityUpdates(true)
                  .setTrackingRange(64)
                  .setUpdateInterval(3)
                  .fireImmune()
                  .build("naturesaura:mover_minecart")
            );
            h.register(
               ResourceLocation.fromNamespaceAndPath("naturesaura", "effect_inhibitor"),
               Builder.of(EntityEffectInhibitor::new, MobCategory.MISC)
                  .sized(1.0F, 1.0F)
                  .setShouldReceiveVelocityUpdates(true)
                  .setTrackingRange(64)
                  .setUpdateInterval(20)
                  .fireImmune()
                  .build("naturesaura:effect_inhibitor")
            );
            h.register(
               ResourceLocation.fromNamespaceAndPath("naturesaura", "light_projectile"),
               Builder.of(EntityLightProjectile::new, MobCategory.MISC)
                  .sized(0.5F, 0.5F)
                  .setShouldReceiveVelocityUpdates(true)
                  .setTrackingRange(64)
                  .setUpdateInterval(3)
                  .fireImmune()
                  .build("naturesaura:light_projectile")
            );
            h.register(
               ResourceLocation.fromNamespaceAndPath("naturesaura", "structure_finder"),
               Builder.of(EntityStructureFinder::new, MobCategory.MISC)
                  .sized(0.5F, 0.5F)
                  .setShouldReceiveVelocityUpdates(true)
                  .setTrackingRange(64)
                  .setUpdateInterval(2)
                  .fireImmune()
                  .build("naturesaura:structure_finder")
            );
            Helper.populateObjectHolders(ModEntities.class, event.getRegistry(), false);
         }
      );
      event.register(
         Registries.FEATURE,
         h -> {
            h.register(ResourceLocation.fromNamespaceAndPath("naturesaura", "aura_bloom"), new LevelGenAuraBloom(ModBlocks.AURA_BLOOM, 60, false));
            h.register(ResourceLocation.fromNamespaceAndPath("naturesaura", "aura_cactus"), new LevelGenAuraBloom(ModBlocks.AURA_CACTUS, 60, false));
            h.register(
               ResourceLocation.fromNamespaceAndPath("naturesaura", "warped_aura_mushroom"), new LevelGenAuraBloom(ModBlocks.WARPED_AURA_MUSHROOM, 10, true)
            );
            h.register(
               ResourceLocation.fromNamespaceAndPath("naturesaura", "crimson_aura_mushroom"), new LevelGenAuraBloom(ModBlocks.CRIMSON_AURA_MUSHROOM, 10, true)
            );
            h.register(ResourceLocation.fromNamespaceAndPath("naturesaura", "aura_mushroom"), new LevelGenAuraBloom(ModBlocks.AURA_MUSHROOM, 20, false));
            h.register(ResourceLocation.fromNamespaceAndPath("naturesaura", "ancient_tree"), new LevelGenAncientTree());
            h.register(ResourceLocation.fromNamespaceAndPath("naturesaura", "nether_wart_mushroom"), new LevelGenNetherWartMushroom());
            Helper.populateObjectHolders(ModFeatures.class, event.getRegistry(), false);
         }
      );
      event.register(Registries.RECIPE_TYPE, h -> {
         h.register(ResourceLocation.fromNamespaceAndPath("naturesaura", "altar"), ModRecipes.ALTAR_TYPE);
         h.register(ResourceLocation.fromNamespaceAndPath("naturesaura", "animal_spawner"), ModRecipes.ANIMAL_SPAWNER_TYPE);
         h.register(ResourceLocation.fromNamespaceAndPath("naturesaura", "offering"), ModRecipes.OFFERING_TYPE);
         h.register(ResourceLocation.fromNamespaceAndPath("naturesaura", "tree_ritual"), ModRecipes.TREE_RITUAL_TYPE);
      });
      event.register(Registries.RECIPE_SERIALIZER, h -> {
         h.register(ResourceLocation.fromNamespaceAndPath("naturesaura", "altar"), ModRecipes.ALTAR_SERIALIZER);
         h.register(ResourceLocation.fromNamespaceAndPath("naturesaura", "animal_spawner"), ModRecipes.ANIMAL_SPAWNER_SERIALIZER);
         h.register(ResourceLocation.fromNamespaceAndPath("naturesaura", "offering"), ModRecipes.OFFERING_SERIALIZER);
         h.register(ResourceLocation.fromNamespaceAndPath("naturesaura", "tree_ritual"), ModRecipes.TREE_RITUAL_SERIALIZER);
      });
      event.register(
         Registries.CREATIVE_MODE_TAB,
         h -> h.register(
            ResourceLocation.fromNamespaceAndPath("naturesaura", "tab"),
            CreativeModeTab.builder()
               .title(Component.translatable("item_group.naturesaura.tab"))
               .icon(() -> new ItemStack(ModItems.GOLD_LEAF))
               .displayItems((params, output) -> {
                  output.accept(PatchouliAPI.get().getBookStack(PatchouliCompat.BOOK));
                  ALL_ITEMS.forEach(i -> {
                     if (i instanceof ICustomCreativeTab c) {
                        output.acceptAll(c.getCreativeTabItems());
                     } else if (i instanceof ItemLike l && l.asItem() != Items.AIR) {
                        output.accept(l);
                     }
                  });
               })
               .build()
         )
      );
      event.register(
         Keys.ATTACHMENT_TYPES, h -> h.register(ResourceLocation.fromNamespaceAndPath("naturesaura", "aura_chunk"), NaturesAuraAPI.AURA_CHUNK_ATTACHMENT)
      );
      event.register(Registries.DATA_COMPONENT_TYPE, h -> {
         h.register(ResourceLocation.fromNamespaceAndPath("naturesaura", "multiblock_maker_data"), ItemMultiblockMaker.Data.TYPE);
         h.register(ResourceLocation.fromNamespaceAndPath("naturesaura", "aura_data"), ItemAuraContainer.Data.TYPE);
         h.register(ResourceLocation.fromNamespaceAndPath("naturesaura", "dropped_item_data"), BlockEntityImpl.DroppedItemData.TYPE);
         h.register(ResourceLocation.fromNamespaceAndPath("naturesaura", "ender_crate_data"), BlockEnderCrate.Data.TYPE);
         h.register(ResourceLocation.fromNamespaceAndPath("naturesaura", "aura_bottle_data"), ItemAuraBottle.Data.TYPE);
         h.register(ResourceLocation.fromNamespaceAndPath("naturesaura", "break_prevention_data"), ItemBreakPrevention.Data.TYPE);
         h.register(ResourceLocation.fromNamespaceAndPath("naturesaura", "color_changer_data"), ItemColorChanger.Data.TYPE);
         h.register(ResourceLocation.fromNamespaceAndPath("naturesaura", "effect_powder_data"), ItemEffectPowder.Data.TYPE);
         h.register(ResourceLocation.fromNamespaceAndPath("naturesaura", "shockwave_creator_data"), ItemShockwaveCreator.Data.TYPE);
         h.register(ResourceLocation.fromNamespaceAndPath("naturesaura", "disableable_tool_data"), Helper.DisableableToolData.TYPE);
      });
   }

   @SubscribeEvent
   public static void registerCapabilities(RegisterCapabilitiesEvent event) {
      event.registerBlockEntity(FluidHandler.BLOCK, ModBlockEntities.SPRING, (e, c) -> e.tank);
      event.registerBlockEntity(EnergyStorage.BLOCK, ModBlockEntities.RF_CONVERTER, (e, c) -> e.storage);
      event.registerBlockEntity(NaturesAuraAPI.AURA_CONTAINER_BLOCK_CAPABILITY, ModBlockEntities.NATURE_ALTAR, (e, c) -> e.container);
      event.registerBlockEntity(NaturesAuraAPI.AURA_CONTAINER_BLOCK_CAPABILITY, ModBlockEntities.ANCIENT_LEAVES, (e, c) -> e.container);
      event.registerBlockEntity(NaturesAuraAPI.AURA_CONTAINER_BLOCK_CAPABILITY, ModBlockEntities.END_FLOWER, (e, c) -> e.container);
      event.registerBlockEntity(ItemHandler.BLOCK, ModBlockEntities.BLAST_FURNACE_BOOSTER, (e, c) -> e.getItemHandler());
      event.registerBlockEntity(ItemHandler.BLOCK, ModBlockEntities.OFFERING_TABLE, (e, c) -> e.items);
      event.registerBlockEntity(ItemHandler.BLOCK, ModBlockEntities.GRATED_CHUTE, (e, c) -> e.items);
      event.registerBlockEntity(ItemHandler.BLOCK, ModBlockEntities.NATURE_ALTAR, (e, c) -> e.items);
      event.registerBlockEntity(ItemHandler.BLOCK, ModBlockEntities.WOOD_STAND, (e, c) -> e.items);
      event.registerBlockEntity(ItemHandler.BLOCK, ModBlockEntities.ENDER_CRATE, (e, c) -> e.canOpen() ? e.wrappedEnderStorage : null);
      event.registerBlockEntity(ItemHandler.BLOCK, ModBlockEntities.AURA_TIMER, (e, c) -> e.itemHandler);
      event.registerItem(NaturesAuraAPI.AURA_CONTAINER_ITEM_CAPABILITY, (s, c) -> new ItemAuraContainer(s, null, 400000), new ItemLike[]{ModItems.AURA_CACHE});
      event.registerItem(NaturesAuraAPI.AURA_CONTAINER_ITEM_CAPABILITY, (s, c) -> new ItemAuraContainer(s, null, 1200000), new ItemLike[]{ModItems.AURA_TROVE});

      for (IModItem item : ALL_ITEMS) {
         if (item instanceof ItemArmor) {
            event.registerItem(NaturesAuraAPI.AURA_RECHARGE_CAPABILITY, Helper.makeRechargeProvider(false), new ItemLike[]{(Item)item});
         } else if (item instanceof ItemAxe
            || item instanceof ItemPickaxe
            || item instanceof ItemShovel
            || item instanceof ItemHoe
            || item instanceof ItemSword) {
            event.registerItem(NaturesAuraAPI.AURA_RECHARGE_CAPABILITY, Helper.makeRechargeProvider(true), new ItemLike[]{(Item)item});
         }
      }

      Compat.addCapabilities(event);
   }

   public static Block createFlowerPot(Block block) {
      Properties props = Properties.of().strength(0.0F);
      Block potted = new BlockFlowerPot(() -> (FlowerPotBlock)Blocks.FLOWER_POT, () -> block, props);
      ((FlowerPotBlock)Blocks.FLOWER_POT).addPlant(BuiltInRegistries.BLOCK.getKey(block), () -> potted);
      return potted;
   }

   @SafeVarargs
   private static <T> void registerAll(RegisterHelper<T> helper, T... items) {
      for (T item : items) {
         helper.register(ResourceLocation.fromNamespaceAndPath("naturesaura", ((IModItem)item).getBaseName()), item);
      }
   }

   @EventBusSubscriber(Dist.CLIENT)
   public static final class Client {
      @SubscribeEvent
      public static void registerMenuScreens(RegisterMenuScreensEvent event) {
         event.register(ModContainers.ENDER_CRATE, GuiEnderCrate::new);
         event.register(ModContainers.ENDER_ACCESS, GuiEnderCrate::new);
      }

      @SubscribeEvent
      public static void registerRenderLayers(AddLayers event) {
         for (PlayerRenderer render : new PlayerRenderer[]{(PlayerRenderer)event.getSkin(Model.WIDE), (PlayerRenderer)event.getSkin(Model.SLIM)}) {
            render.addLayer(new PlayerLayerTrinkets(render));
         }
      }
   }
}
