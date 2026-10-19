package cn.leolezury.eternalstarlight.neoforge.datagen.provider.tags;

import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESConventionalTags;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider.TagLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags.Blocks;
import net.neoforged.neoforge.common.Tags.Items;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ESItemTagsProvider extends ItemTagsProvider {
   public ESItemTagsProvider(PackOutput output, CompletableFuture<Provider> future, CompletableFuture<TagLookup<Block>> provider, ExistingFileHelper helper) {
      super(output, future, provider, "eternal_starlight", helper);
   }

   protected void addTags(Provider lookupProvider) {
      this.copy(Blocks.BUDDING_BLOCKS, Items.BUDDING_BLOCKS);
      this.copy(Blocks.BUDS, Items.BUDS);
      this.copy(Blocks.CLUSTERS, Items.CLUSTERS);
      this.copy(Blocks.COBBLESTONES, Items.COBBLESTONES);
      this.copy(Blocks.DYED_BLACK, Items.DYED_BLACK);
      this.copy(Blocks.DYED_BLUE, Items.DYED_BLUE);
      this.copy(Blocks.DYED_BROWN, Items.DYED_BROWN);
      this.copy(Blocks.DYED_CYAN, Items.DYED_CYAN);
      this.copy(Blocks.DYED_GRAY, Items.DYED_GRAY);
      this.copy(Blocks.DYED_GREEN, Items.DYED_GREEN);
      this.copy(Blocks.DYED_LIGHT_BLUE, Items.DYED_LIGHT_BLUE);
      this.copy(Blocks.DYED_LIGHT_GRAY, Items.DYED_LIGHT_GRAY);
      this.copy(Blocks.DYED_LIME, Items.DYED_LIME);
      this.copy(Blocks.DYED_MAGENTA, Items.DYED_MAGENTA);
      this.copy(Blocks.DYED_ORANGE, Items.DYED_ORANGE);
      this.copy(Blocks.DYED_PINK, Items.DYED_PINK);
      this.copy(Blocks.DYED_PURPLE, Items.DYED_PURPLE);
      this.copy(Blocks.DYED_RED, Items.DYED_RED);
      this.copy(Blocks.DYED_WHITE, Items.DYED_WHITE);
      this.copy(Blocks.DYED_YELLOW, Items.DYED_YELLOW);
      this.copy(Blocks.FENCE_GATES_WOODEN, Items.FENCE_GATES_WOODEN);
      this.copy(Blocks.FENCES_WOODEN, Items.FENCES_WOODEN);
      this.copy(Blocks.GRAVELS, Items.GRAVELS);
      this.copy(Blocks.ORE_RATES_DENSE, Items.ORE_RATES_DENSE);
      this.copy(Blocks.ORE_RATES_SPARSE, Items.ORE_RATES_SPARSE);
      this.copy(Blocks.ORES, Items.ORES);
      this.copy(Blocks.ORES_REDSTONE, Items.ORES_REDSTONE);
      this.copy(Blocks.SANDS, Items.SANDS);
      this.copy(Blocks.SANDSTONE_BLOCKS, Items.SANDSTONE_BLOCKS);
      this.copy(Blocks.SANDSTONE_SLABS, Items.SANDSTONE_SLABS);
      this.copy(Blocks.SANDSTONE_STAIRS, Items.SANDSTONE_STAIRS);
      this.copy(Blocks.STONES, Items.STONES);
      this.copy(Blocks.STORAGE_BLOCKS, Items.STORAGE_BLOCKS);
      this.copy(Blocks.STRIPPED_LOGS, Items.STRIPPED_LOGS);
      this.copy(Blocks.STRIPPED_WOODS, Items.STRIPPED_WOODS);
      this.tag(Items.FOODS)
         .add(
            new Item[]{
               ESItems.JINGLESTEM_CRISP.get(),
               ESItems.JINGLESTEM_SANDWICH.get(),
               ESItems.CRINOA_BALL.get(),
               ESItems.SHADOW_ESCARGOT.get(),
               ESItems.DOOMEDEN_CARRION.get(),
               ESItems.ROTTEN_HAM.get()
            }
         );
      this.tag(Items.FOODS_FRUIT)
         .add(
            new Item[]{
               ESItems.LUNARIS_CACTUS_FRUIT.get(),
               ESItems.LUNARIS_CACTUS_GEL.get(),
               ESItems.ABYSSAL_FRUIT.get(),
               ESItems.VELVETUMOSS_BALL.get(),
               ESItems.PUNGENCY_FRUIT.get(),
               ESItems.SILVER_PUNGENCY_FRUIT.get()
            }
         );
      this.tag(Items.FOODS_BERRY).add(new Item[]{ESItems.LUNAR_BERRIES.get(), ESItems.ABYSSAL_FRUIT.get(), ESItems.BLOSSOM_OF_STARS.get()});
      this.tag(Items.FOODS_RAW_MEAT)
         .add(new Item[]{ESItems.ROOKFISH.get(), ESItems.AURORA_DEER_STEAK.get(), ESItems.RATLIN_MEAT.get(), ESItems.SHADOW_SNAIL_MEAT.get()});
      this.tag(Items.FOODS_COOKED_MEAT)
         .add(
            new Item[]{
               ESItems.ROTTEN_FLESH_JERKY.get(),
               ESItems.COOKED_ROOKFISH.get(),
               ESItems.COOKED_AURORA_DEER_STEAK.get(),
               ESItems.COOKED_RATLIN_MEAT.get(),
               ESItems.COOKED_SHADOW_SNAIL_MEAT.get()
            }
         );
      this.tag(Items.FOODS_RAW_FISH).add(new Item[]{ESItems.LUMINOFISH.get(), ESItems.LUMINARIS.get()});
      this.tag(Items.FOODS_COOKED_FISH).add(new Item[]{ESItems.COOKED_LUMINOFISH.get(), ESItems.COOKED_LUMINARIS.get()});
      this.tag(Items.FOODS_SOUP).add(new Item[]{ESItems.BOULDERSHROOM_STEW.get(), ESItems.CRINOA_PORRIDGE.get(), ESItems.PUNGENCY_STEW.get()});
      this.tag(Items.FOODS_PIE).add(ESItems.SHADOW_SNAIL_PIE.get());
      this.tag(Items.ANIMAL_FOODS)
         .addTags(
            new TagKey[]{
               ESTags.Items.AURORA_DEER_FOOD,
               ESTags.Items.CRYSTALLIZED_MOTH_FOOD,
               ESTags.Items.ENT_FOOD,
               ESTags.Items.RATLIN_FOOD,
               ESTags.Items.SHADOW_SNAIL_FOOD,
               ESTags.Items.YETI_FOOD
            }
         );
      this.tag(Items.SEEDS)
         .addTags(
            new TagKey[]{ESConventionalTags.Items.SEEDS_CRINOA, ESConventionalTags.Items.SEEDS_PUNGENCY_FRUIT, ESConventionalTags.Items.SEEDS_NOCTURNAL_MILLET}
         );
      this.tag(Items.CROPS)
         .addTags(
            new TagKey[]{ESConventionalTags.Items.CROPS_CRINOA, ESConventionalTags.Items.CROPS_PUNGENCY_FRUIT, ESConventionalTags.Items.CROPS_NOCTURNAL_MILLET}
         );
      this.tag(Items.MUSHROOMS)
         .add(new Item[]{ESItems.GLOWING_MUSHROOM.get(), ESItems.SHINING_MUSHROOM.get(), ESItems.BOULDERSHROOM.get(), ESItems.MARIMOLD.get()});
      this.tag(Items.MUSIC_DISCS)
         .add(
            new Item[]{
               ESItems.MUSIC_DISC_WHISPER_OF_THE_STARS.get(),
               ESItems.MUSIC_DISC_DUSK_O_EREYESTERDAY.get(),
               ESItems.MUSIC_DISC_TRANQUILITY.get(),
               ESItems.MUSIC_DISC_NEST.get(),
               ESItems.MUSIC_DISC_POSTERITY.get(),
               ESItems.MUSIC_DISC_THE_THORNY_REIGN.get(),
               ESItems.MUSIC_DISC_PROFUNDITY.get(),
               ESItems.MUSIC_DISC_WAILING_WELL.get(),
               ESItems.MUSIC_DISC_STARS_SHINING_UPON_THE_SEA.get(),
               ESItems.MUSIC_DISC_OPTIMIZED_OPTION.get(),
               ESItems.MUSIC_DISC_MECHANICAL_FOSSIL.get(),
               ESItems.MUSIC_DISC_FAKE_LIGHT.get(),
               ESItems.MUSIC_DISC_TRANQUILITY_II.get(),
               ESItems.MUSIC_DISC_ATLANTIS.get(),
               ESItems.MUSIC_DISC_SACRED_DESERT.get(),
               ESItems.MUSIC_DISC_SPIRIT.get(),
               ESItems.MUSIC_DISC_ETHER_RAIN.get(),
               ESItems.MUSIC_DISC_BRISK.get(),
               ESItems.MUSIC_DISC_MOONLIGHT.get()
            }
         );
      this.tag(Items.GEMS)
         .add(new Item[]{ESItems.RED_STARLIGHT_CRYSTAL_SHARD.get(), ESItems.BLUE_STARLIGHT_CRYSTAL_SHARD.get()})
         .addTags(
            new TagKey[]{
               ESConventionalTags.Items.GEMS_THIOQUARTZ,
               ESConventionalTags.Items.GEMS_GLACITE,
               ESConventionalTags.Items.GEMS_STARLIT_DIAMOND,
               ESConventionalTags.Items.GEMS_MALARITE,
               ESConventionalTags.Items.GEMS_STARCORE
            }
         );
      this.tag(Items.GEMS_QUARTZ).add(ESItems.THIOQUARTZ_CLUSTER.get());
      this.tag(Items.INGOTS)
         .addTags(
            new TagKey[]{
               ESConventionalTags.Items.INGOTS_AETHERSENT,
               ESConventionalTags.Items.INGOTS_THERMAL_SPRINGSTONE,
               ESConventionalTags.Items.INGOTS_DEEPSILVER,
               ESConventionalTags.Items.INGOTS_UNREALIUM,
               ESConventionalTags.Items.INGOTS_AMARAMBER,
               ESConventionalTags.Items.INGOTS_GOLEM_STEEL,
               ESConventionalTags.Items.INGOTS_OXIDIZED_GOLEM_STEEL
            }
         );
      this.tag(Items.NUGGETS)
         .addTags(
            new TagKey[]{
               ESConventionalTags.Items.NUGGETS_AETHERSENT,
               ESConventionalTags.Items.NUGGETS_DEEPSILVER,
               ESConventionalTags.Items.NUGGETS_UNREALIUM,
               ESConventionalTags.Items.NUGGETS_AMARAMBER,
               ESConventionalTags.Items.NUGGETS_GOLEM_STEEL,
               ESConventionalTags.Items.NUGGETS_OXIDIZED_GOLEM_STEEL
            }
         );
      this.tag(Items.RAW_MATERIALS).addTags(new TagKey[]{ESConventionalTags.Items.RAW_MATERIALS_AETHERSENT, ESConventionalTags.Items.RAW_MATERIALS_AMARAMBER});
      this.tag(Items.DUSTS).addTags(new TagKey[]{ESConventionalTags.Items.DUSTS_SALTPETER});
      this.tag(Items.TOOLS_SHIELD).add(new Item[]{ESItems.GLACITE_SHIELD.get(), ESItems.FLOWGLAZE_SHIELD.get()});
      this.tag(Items.TOOLS_BOW)
         .add(
            new Item[]{
               ESItems.STARFALL_LONGBOW.get(),
               ESItems.FLOWGLAZE_BOW.get(),
               ESItems.GLISTERING_BOW.get(),
               ESItems.MOONRING_BOW.get(),
               ESItems.BOW_OF_BLOOD.get()
            }
         );
      this.tag(Items.TOOLS_CROSSBOW)
         .add(
            new Item[]{
               ESItems.UNREALIUM_CROSSBOW.get(),
               ESItems.STARFIRE_CROSSBOW.get(),
               ESItems.MECHANICAL_CROSSBOW.get(),
               ESItems.CRYSTAL_CROSSBOW.get(),
               ESItems.WILTED_CROSSBOW.get()
            }
         );
      this.tag(Items.TOOLS_SPEAR).add(new Item[]{ESItems.MALARITE_SPEAR.get(), ESItems.PUNGENCY_FRUIT_SPEAR.get(), ESItems.CRESCENT_SPEAR.get()});
      this.tag(Items.TOOLS_BRUSH).add(ESItems.DEEPSILVER_BRUSH.get());
      this.tag(Items.TOOLS_IGNITER).add(ESItems.SALTPETER_MATCHBOX.get());
      this.tag(Items.MELEE_WEAPON_TOOLS)
         .add(
            new Item[]{
               ESItems.RAGE_OF_STARS.get(),
               ESItems.THERMAL_SPRINGSTONE_SWORD.get(),
               ESItems.THERMAL_SPRINGSTONE_AXE.get(),
               ESItems.THERMAL_SPRINGSTONE_SCYTHE.get(),
               ESItems.THERMAL_SPRINGSTONE_HAMMER.get(),
               ESItems.GLACITE_SWORD.get(),
               ESItems.GLACITE_AXE.get(),
               ESItems.GLACITE_SCYTHE.get(),
               ESItems.STARLIT_DIAMOND_SWORD.get(),
               ESItems.STARLIT_DIAMOND_AXE.get(),
               ESItems.DEEPSILVER_SWORD.get(),
               ESItems.DEEPSILVER_AXE.get(),
               ESItems.UNREALIUM_SWORD.get(),
               ESItems.UNREALIUM_AXE.get(),
               ESItems.MALARITE_SWORD.get(),
               ESItems.MALARITE_AXE.get(),
               ESItems.PUNGENCY_FRUIT_AXE.get(),
               ESItems.STARFIRE_SWORD.get(),
               ESItems.STARFIRE_AXE.get(),
               ESItems.STARFIRE_SCYTHE.get(),
               ESItems.STARFIRE_HAMMER.get(),
               ESItems.FLOWGLAZE_SWORD.get(),
               ESItems.FLOWGLAZE_AXE.get(),
               ESItems.FLOWGLAZE_SCYTHE.get(),
               ESItems.AMARAMBER_SWORD.get(),
               ESItems.AMARAMBER_AXE.get(),
               ESItems.SHATTERED_SWORD.get(),
               ESItems.DAGGER_OF_HUNGER.get(),
               ESItems.GLISTERING_SWORD.get(),
               ESItems.GLISTERING_GREATSWORD.get(),
               ESItems.GLISTERING_MORNING_STAR.get(),
               ESItems.ENERGY_SWORD.get(),
               ESItems.GOLEM_STEEL_GREATSWORD.get(),
               ESItems.CRYSTAL_GREATSWORD.get(),
               ESItems.MOONRING_GREATSWORD.get(),
               ESItems.PETAL_SCYTHE.get(),
               ESItems.BONEMORE.get(),
               ESItems.DOOMEDEN_RAPIER.get()
            }
         );
      this.tag(Items.RANGED_WEAPON_TOOLS)
         .add(
            new Item[]{
               ESItems.STARFALL_LONGBOW.get(),
               ESItems.UNREALIUM_CROSSBOW.get(),
               ESItems.MALARITE_SPEAR.get(),
               ESItems.PUNGENCY_FRUIT_SPEAR.get(),
               ESItems.SEEDS_LAUNCHER.get(),
               ESItems.STARFIRE_CROSSBOW.get(),
               ESItems.FLOWGLAZE_BOW.get(),
               ESItems.GLISTERING_BOW.get(),
               ESItems.MECHANICAL_CROSSBOW.get(),
               ESItems.CRYSTAL_CROSSBOW.get(),
               ESItems.ENERGY_BOOMERANG.get(),
               ESItems.WILTED_CROSSBOW.get(),
               ESItems.MOONRING_BOW.get(),
               ESItems.CRESCENT_SPEAR.get(),
               ESItems.BOW_OF_BLOOD.get()
            }
         );
      this.tag(Items.MINING_TOOL_TOOLS)
         .add(
            new Item[]{
               ESItems.THERMAL_SPRINGSTONE_PICKAXE.get(),
               ESItems.GLACITE_PICKAXE.get(),
               ESItems.STARLIT_DIAMOND_PICKAXE.get(),
               ESItems.THERMAL_SPRINGSTONE_HAMMER.get(),
               ESItems.DEEPSILVER_PICKAXE.get(),
               ESItems.UNREALIUM_PICKAXE.get(),
               ESItems.MALARITE_PICKAXE.get(),
               ESItems.STARFIRE_HAMMER.get(),
               ESItems.AMARAMBER_PICKAXE.get(),
               ESItems.GLISTERING_MORNING_STAR.get(),
               ESItems.UNDERMINER.get(),
               ESItems.GRAVITY_PICKAXE.get()
            }
         );
      this.copy(ESConventionalTags.Blocks.STORAGE_BLOCKS_CRINOA, ESConventionalTags.Items.STORAGE_BLOCKS_CRINOA);
      this.copy(ESConventionalTags.Blocks.ORES_STARCORE, ESConventionalTags.Items.ORES_STARCORE);
      this.copy(ESConventionalTags.Blocks.STORAGE_BLOCKS_STARCORE, ESConventionalTags.Items.STORAGE_BLOCKS_STARCORE);
      this.copy(ESConventionalTags.Blocks.STORAGE_BLOCKS_RAW_AETHERSENT, ESConventionalTags.Items.STORAGE_BLOCKS_RAW_AETHERSENT);
      this.copy(ESConventionalTags.Blocks.STORAGE_BLOCKS_AETHERSENT, ESConventionalTags.Items.STORAGE_BLOCKS_AETHERSENT);
      this.copy(ESConventionalTags.Blocks.STORAGE_BLOCKS_GLACITE, ESConventionalTags.Items.STORAGE_BLOCKS_GLACITE);
      this.copy(ESConventionalTags.Blocks.ORES_STARLIT_DIAMOND, ESConventionalTags.Items.ORES_STARLIT_DIAMOND);
      this.copy(ESConventionalTags.Blocks.STORAGE_BLOCKS_STARLIT_DIAMOND, ESConventionalTags.Items.STORAGE_BLOCKS_STARLIT_DIAMOND);
      this.copy(ESConventionalTags.Blocks.ORES_DEEPSILVER, ESConventionalTags.Items.ORES_DEEPSILVER);
      this.copy(ESConventionalTags.Blocks.STORAGE_BLOCKS_RAW_DEEPSILVER, ESConventionalTags.Items.STORAGE_BLOCKS_RAW_DEEPSILVER);
      this.copy(ESConventionalTags.Blocks.STORAGE_BLOCKS_DEEPSILVER, ESConventionalTags.Items.STORAGE_BLOCKS_DEEPSILVER);
      this.copy(ESConventionalTags.Blocks.STORAGE_BLOCKS_UNREALIUM, ESConventionalTags.Items.STORAGE_BLOCKS_UNREALIUM);
      this.copy(ESConventionalTags.Blocks.ORES_MALARITE, ESConventionalTags.Items.ORES_MALARITE);
      this.copy(ESConventionalTags.Blocks.STORAGE_BLOCKS_MALARITE, ESConventionalTags.Items.STORAGE_BLOCKS_MALARITE);
      this.copy(ESConventionalTags.Blocks.ORES_SALTPETER, ESConventionalTags.Items.ORES_SALTPETER);
      this.copy(ESConventionalTags.Blocks.STORAGE_BLOCKS_SALTPETER, ESConventionalTags.Items.STORAGE_BLOCKS_SALTPETER);
      this.copy(ESConventionalTags.Blocks.STORAGE_BLOCKS_RAW_AMARAMBER, ESConventionalTags.Items.STORAGE_BLOCKS_RAW_AMARAMBER);
      this.copy(ESConventionalTags.Blocks.STORAGE_BLOCKS_GOLEM_STEEL, ESConventionalTags.Items.STORAGE_BLOCKS_GOLEM_STEEL);
      this.copy(ESConventionalTags.Blocks.STORAGE_BLOCKS_OXIDIZED_GOLEM_STEEL, ESConventionalTags.Items.STORAGE_BLOCKS_OXIDIZED_GOLEM_STEEL);
      this.copy(ESConventionalTags.Blocks.ORES_IN_GROUND_GRIMSTONE, ESConventionalTags.Items.ORES_IN_GROUND_GRIMSTONE);
      this.copy(ESConventionalTags.Blocks.ORES_IN_GROUND_VOIDSTONE, ESConventionalTags.Items.ORES_IN_GROUND_VOIDSTONE);
      this.copy(ESConventionalTags.Blocks.ORES_IN_GROUND_NIGHTFALL_MUD, ESConventionalTags.Items.ORES_IN_GROUND_NIGHTFALL_MUD);
      this.copy(ESConventionalTags.Blocks.ORES_IN_GROUND_PACKED_NIGHTFALL_MUD, ESConventionalTags.Items.ORES_IN_GROUND_PACKED_NIGHTFALL_MUD);
      this.tag(ESConventionalTags.Items.SEEDS_CRINOA).add(ESItems.CRINOA_SEEDS.get());
      this.tag(ESConventionalTags.Items.CROPS_CRINOA).add(ESItems.CRINOA.get());
      this.tag(ESConventionalTags.Items.SEEDS_NOCTURNAL_MILLET).add(ESItems.NOCTURNAL_MILLET_SEEDS.get());
      this.tag(ESConventionalTags.Items.CROPS_NOCTURNAL_MILLET).add(new Item[]{ESItems.NOCTURNAL_MILLET.get(), ESItems.FORGOTTEN_NOCTURNAL_MILLET.get()});
      this.tag(ESConventionalTags.Items.SEEDS_PUNGENCY_FRUIT).add(ESItems.PUNGENCY_FRUIT_SEEDS.get());
      this.tag(ESConventionalTags.Items.CROPS_PUNGENCY_FRUIT).add(ESItems.PUNGENCY_FRUIT.get());
      this.tag(ESConventionalTags.Items.CAMPFIRES).add(ESItems.TORREYA_CAMPFIRE.get());
      this.tag(ESConventionalTags.Items.RAW_MATERIALS_AETHERSENT).add(ESItems.RAW_AETHERSENT.get());
      this.tag(ESConventionalTags.Items.INGOTS_AETHERSENT).add(ESItems.AETHERSENT_INGOT.get());
      this.tag(ESConventionalTags.Items.NUGGETS_AETHERSENT).add(ESItems.AETHERSENT_NUGGET.get());
      this.tag(ESConventionalTags.Items.INGOTS_THERMAL_SPRINGSTONE).add(ESItems.THERMAL_SPRINGSTONE_INGOT.get());
      this.tag(ESConventionalTags.Items.GEMS_GLACITE).add(ESItems.GLACITE_SHARD.get());
      this.tag(ESConventionalTags.Items.GEMS_STARLIT_DIAMOND).add(ESItems.STARLIT_DIAMOND.get());
      this.tag(ESConventionalTags.Items.RAW_MATERIALS_DEEPSILVER).add(ESItems.RAW_DEEPSILVER.get());
      this.tag(ESConventionalTags.Items.INGOTS_DEEPSILVER).add(ESItems.DEEPSILVER_INGOT.get());
      this.tag(ESConventionalTags.Items.NUGGETS_DEEPSILVER).add(ESItems.DEEPSILVER_NUGGET.get());
      this.tag(ESConventionalTags.Items.INGOTS_UNREALIUM).add(ESItems.UNREALIUM_INGOT.get());
      this.tag(ESConventionalTags.Items.NUGGETS_UNREALIUM).add(ESItems.UNREALIUM_NUGGET.get());
      this.tag(ESConventionalTags.Items.GEMS_MALARITE).add(ESItems.MALARITE.get());
      this.tag(ESConventionalTags.Items.DUSTS_SALTPETER).add(ESItems.SALTPETER_POWDER.get());
      this.tag(ESConventionalTags.Items.GEMS_STARCORE).add(ESItems.STARCORE.get());
      this.tag(ESConventionalTags.Items.RAW_MATERIALS_AMARAMBER).add(ESItems.RAW_AMARAMBER.get());
      this.tag(ESConventionalTags.Items.INGOTS_AMARAMBER).add(ESItems.AMARAMBER_INGOT.get());
      this.tag(ESConventionalTags.Items.NUGGETS_AMARAMBER).add(ESItems.AMARAMBER_NUGGET.get());
      this.tag(ESConventionalTags.Items.GEMS_THIOQUARTZ).add(ESItems.THIOQUARTZ_SHARD.get());
      this.tag(ESConventionalTags.Items.INGOTS_GOLEM_STEEL).add(ESItems.GOLEM_STEEL_INGOT.get());
      this.tag(ESConventionalTags.Items.INGOTS_OXIDIZED_GOLEM_STEEL).add(ESItems.OXIDIZED_GOLEM_STEEL_INGOT.get());
      this.tag(ESConventionalTags.Items.NUGGETS_GOLEM_STEEL).add(ESItems.GOLEM_STEEL_NUGGET.get());
      this.tag(ESConventionalTags.Items.NUGGETS_OXIDIZED_GOLEM_STEEL).add(ESItems.OXIDIZED_GOLEM_STEEL_NUGGET.get());
      this.copy(ESTags.Blocks.LUNAR_LOGS, ESTags.Items.LUNAR_LOGS);
      this.copy(ESTags.Blocks.NORTHLAND_LOGS, ESTags.Items.NORTHLAND_LOGS);
      this.copy(ESTags.Blocks.BANYIN_LOGS, ESTags.Items.BANYIN_LOGS);
      this.copy(ESTags.Blocks.SCARLET_LOGS, ESTags.Items.SCARLET_LOGS);
      this.copy(ESTags.Blocks.TORREYA_LOGS, ESTags.Items.TORREYA_LOGS);
      this.copy(ESTags.Blocks.JINGLESTEM_LOGS, ESTags.Items.JINGLESTEM_LOGS);
      this.copy(ESTags.Blocks.CRADLEWOOD_LOGS, ESTags.Items.CRADLEWOOD_LOGS);
      this.copy(ESTags.Blocks.YETI_FUR, ESTags.Items.YETI_FUR);
      this.copy(ESTags.Blocks.YETI_FUR_CARPETS, ESTags.Items.YETI_FUR_CARPETS);
      this.copy(ESTags.Blocks.TOOTH_OF_HUNGER_BLOCKS, ESTags.Items.TOOTH_OF_HUNGER_BLOCKS);
      this.tag(ESTags.Items.SCYTHES)
         .add(
            new Item[]{
               ESItems.THERMAL_SPRINGSTONE_SCYTHE.get(),
               ESItems.GLACITE_SCYTHE.get(),
               ESItems.STARFIRE_SCYTHE.get(),
               ESItems.FLOWGLAZE_SCYTHE.get(),
               ESItems.PETAL_SCYTHE.get()
            }
         );
      this.tag(ESTags.Items.SICKLES)
         .add(
            new Item[]{
               ESItems.DEEPSILVER_SICKLE.get(),
               ESItems.UNREALIUM_SICKLE.get(),
               ESItems.MALARITE_SICKLE.get(),
               ESItems.AMARAMBER_SICKLE.get(),
               ESItems.AURORA_DEER_ANTLER.get()
            }
         );
      this.tag(ESTags.Items.GREATSWORDS)
         .add(
            new Item[]{
               ESItems.GLISTERING_GREATSWORD.get(),
               ESItems.GOLEM_STEEL_GREATSWORD.get(),
               ESItems.CRYSTAL_GREATSWORD.get(),
               ESItems.MOONRING_GREATSWORD.get(),
               ESItems.BONEMORE.get()
            }
         );
      this.tag(ESTags.Items.HAMMERS)
         .add(new Item[]{ESItems.THERMAL_SPRINGSTONE_HAMMER.get(), ESItems.STARFIRE_HAMMER.get(), ESItems.GLISTERING_MORNING_STAR.get()});
      this.tag(ESTags.Items.BOOMERANGS).add(ESItems.ENERGY_BOOMERANG.get());
      this.tag(ESTags.Items.WHIPS).add(new Item[]{ESItems.CANDLASH.get(), ESItems.TENTACLE_SPIKE.get(), ESItems.COLDSNAP.get()});
      this.tag(ESTags.Items.SMALL_SHIELDS).add(new Item[]{ESItems.GLACITE_SHIELD.get(), ESItems.FLOWGLAZE_SHIELD.get()});
      this.tag(ESTags.Items.ACCESSORIES)
         .add(
            new Item[]{
               ESItems.BATTLEAXE_PENDANT.get(),
               ESItems.WARHAMMER_PENDANT.get(),
               ESItems.BUTTERFLY_WINGS_AMULET.get(),
               ESItems.FUNGUS_AMULET.get(),
               ESItems.PEARL_NECKLACE.get(),
               ESItems.CRESCENT_PENDANT.get()
            }
         );
      this.tag(ESTags.Items.AURORA_DEER_FOOD).add(new Item[]{ESItems.LUNAR_BERRIES.get(), ESItems.ABYSSAL_FRUIT.get(), ESItems.CRINOA.get()});
      this.tag(ESTags.Items.CRYSTALLIZED_MOTH_FOOD).addTag(ItemTags.MEAT);
      this.tag(ESTags.Items.STARFIRE_BIRD_FOOD).addTag(Items.SEEDS).add(ESItems.LUNAR_BERRIES.get());
      this.tag(ESTags.Items.ENT_FOOD).add(new Item[]{ESItems.LUNAR_BERRIES.get(), ESItems.ABYSSAL_FRUIT.get()});
      this.tag(ESTags.Items.RATLIN_FOOD).add(new Item[]{net.minecraft.world.item.Items.GLOW_LICHEN, ESItems.CAVE_MOSS.get(), ESItems.CRINOA.get()});
      this.tag(ESTags.Items.SHADOW_SNAIL_FOOD).addTag(ItemTags.LEAVES).add(new Item[]{ESItems.LUNAR_BERRIES.get(), ESItems.CAVE_MOSS.get()});
      this.tag(ESTags.Items.YETI_FOOD).add(new Item[]{ESItems.LUNAR_BERRIES.get(), ESItems.CAVE_MOSS.get(), ESItems.CRINOA.get()});
      this.tag(ESTags.Items.UNTRIMMABLE_ARMOR)
         .add(
            new Item[]{
               ESItems.THERMAL_SPRINGSTONE_HELMET.get(),
               ESItems.THERMAL_SPRINGSTONE_CHESTPLATE.get(),
               ESItems.THERMAL_SPRINGSTONE_LEGGINGS.get(),
               ESItems.THERMAL_SPRINGSTONE_BOOTS.get(),
               ESItems.STARLIT_DIAMOND_HELMET.get(),
               ESItems.STARLIT_DIAMOND_CHESTPLATE.get(),
               ESItems.STARLIT_DIAMOND_LEGGINGS.get(),
               ESItems.STARLIT_DIAMOND_BOOTS.get(),
               ESItems.DEEPSILVER_LEGGINGS.get(),
               ESItems.DEEPSILVER_BOOTS.get(),
               ESItems.UNREALIUM_HELMET.get(),
               ESItems.UNREALIUM_CHESTPLATE.get(),
               ESItems.UNREALIUM_LEGGINGS.get(),
               ESItems.UNREALIUM_BOOTS.get(),
               ESItems.AMARAMBER_MASK.get(),
               ESItems.AMARAMBER_CHESTPLATE.get(),
               ESItems.ALCHEMIST_MASK.get(),
               ESItems.ALCHEMIST_ROBE.get(),
               ESItems.AETHERSENT_CAPE.get(),
               ESItems.AETHERSENT_BOTTOMS.get(),
               ESItems.AETHERSENT_BOOTS.get(),
               ESItems.AIR_SAC_MASK.get()
            }
         );
      this.tag(ESTags.Items.THERMAL_SPRINGSTONE_WEAPONS)
         .add(
            new Item[]{
               ESItems.THERMAL_SPRINGSTONE_SWORD.get(),
               ESItems.THERMAL_SPRINGSTONE_PICKAXE.get(),
               ESItems.THERMAL_SPRINGSTONE_AXE.get(),
               ESItems.THERMAL_SPRINGSTONE_HOE.get(),
               ESItems.THERMAL_SPRINGSTONE_SHOVEL.get(),
               ESItems.THERMAL_SPRINGSTONE_SCYTHE.get(),
               ESItems.THERMAL_SPRINGSTONE_HAMMER.get()
            }
         );
      this.tag(ESTags.Items.GLACITE_WEAPONS)
         .add(
            new Item[]{
               ESItems.GLACITE_SWORD.get(),
               ESItems.GLACITE_PICKAXE.get(),
               ESItems.GLACITE_AXE.get(),
               ESItems.GLACITE_HOE.get(),
               ESItems.GLACITE_SHOVEL.get(),
               ESItems.GLACITE_SCYTHE.get()
            }
         );
      this.tag(ESTags.Items.MALARITE_WEAPONS)
         .add(
            new Item[]{
               ESItems.MALARITE_SWORD.get(),
               ESItems.MALARITE_PICKAXE.get(),
               ESItems.MALARITE_AXE.get(),
               ESItems.MALARITE_HOE.get(),
               ESItems.MALARITE_SHOVEL.get(),
               ESItems.MALARITE_SICKLE.get(),
               ESItems.MALARITE_SPEAR.get()
            }
         );
      this.tag(ESTags.Items.PUNGENCY_FRUIT_WEAPONS).add(new Item[]{ESItems.PUNGENCY_FRUIT_AXE.get(), ESItems.PUNGENCY_FRUIT_SPEAR.get()});
      this.tag(ESTags.Items.STARFIRE_WEAPONS)
         .add(
            new Item[]{
               ESItems.STARFIRE_SWORD.get(),
               ESItems.STARFIRE_PICKAXE.get(),
               ESItems.STARFIRE_AXE.get(),
               ESItems.STARFIRE_HOE.get(),
               ESItems.STARFIRE_SHOVEL.get(),
               ESItems.STARFIRE_SCYTHE.get(),
               ESItems.STARFIRE_HAMMER.get()
            }
         );
      this.tag(ESTags.Items.FLOWGLAZE_WEAPONS)
         .add(
            new Item[]{
               ESItems.FLOWGLAZE_SWORD.get(),
               ESItems.FLOWGLAZE_PICKAXE.get(),
               ESItems.FLOWGLAZE_AXE.get(),
               ESItems.FLOWGLAZE_HOE.get(),
               ESItems.FLOWGLAZE_SHOVEL.get(),
               ESItems.FLOWGLAZE_SCYTHE.get()
            }
         );
      this.tag(ESTags.Items.MENDS_NATURALLY)
         .add(
            new Item[]{
               ESItems.AMARAMBER_SWORD.get(),
               ESItems.AMARAMBER_PICKAXE.get(),
               ESItems.AMARAMBER_AXE.get(),
               ESItems.AMARAMBER_HOE.get(),
               ESItems.AMARAMBER_SHOVEL.get(),
               ESItems.AMARAMBER_SICKLE.get(),
               ESItems.AMARAMBER_MASK.get(),
               ESItems.AMARAMBER_CHESTPLATE.get()
            }
         );
      this.tag(ESTags.Items.HIDES_WITH_OWNER)
         .add(
            new Item[]{
               ESItems.UNREALIUM_INGOT.get(),
               ESItems.UNREALIUM_NUGGET.get(),
               ESItems.UNREALIUM_BLOCK.get(),
               ESItems.UNREALIUM_BARS.get(),
               ESItems.UNREALIUM_SWORD.get(),
               ESItems.UNREALIUM_PICKAXE.get(),
               ESItems.UNREALIUM_AXE.get(),
               ESItems.UNREALIUM_HOE.get(),
               ESItems.UNREALIUM_SHOVEL.get(),
               ESItems.UNREALIUM_SICKLE.get(),
               ESItems.UNREALIUM_CROSSBOW.get(),
               ESItems.UNREALIUM_HELMET.get(),
               ESItems.UNREALIUM_CHESTPLATE.get(),
               ESItems.UNREALIUM_LEGGINGS.get(),
               ESItems.UNREALIUM_BOOTS.get()
            }
         );
      this.tag(ESTags.Items.ALGALEAVES).add(new Item[]{ESItems.HANGING_ALGALEAVES.get(), ESItems.ALGALEAVES.get()});
      this.tag(ESTags.Items.CRYSTALBORN_CATALYST_FUELS).add(new Item[]{ESItems.RED_STARLIGHT_CRYSTAL_SHARD.get(), ESItems.BLUE_STARLIGHT_CRYSTAL_SHARD.get()});
      this.tag(ESTags.Items.CONSUMABLE_WHEN_WEARING_FUNGUS_AMULET).addTag(Items.MUSHROOMS);
      this.tag(ESTags.Items.REPAIRED_BY_CRESCENT_PENDANT)
         .add(
            new Item[]{
               ESItems.MOONRING_BOW.get(),
               ESItems.MOONRING_GREATSWORD.get(),
               ESItems.PETAL_SCYTHE.get(),
               ESItems.WAND_OF_TELEPORTATION.get(),
               ESItems.CHAIN_OF_SOULS.get(),
               ESItems.CRESCENT_SPEAR.get()
            }
         );
      this.tag(ESTags.Items.LUNAR_MONSTROSITY_IGNITERS)
         .add(
            new Item[]{
               net.minecraft.world.item.Items.FLINT_AND_STEEL,
               net.minecraft.world.item.Items.FIRE_CHARGE,
               net.minecraft.world.item.Items.BLAZE_ROD,
               net.minecraft.world.item.Items.BLAZE_POWDER,
               ESItems.SALTPETER_MATCHBOX.get(),
               ESItems.THERMAL_SPRINGSTONE_INGOT.get()
            }
         );
      this.tag(ESTags.Items.STELLAGMITE_IGNITERS)
         .add(
            new Item[]{
               net.minecraft.world.item.Items.FLINT_AND_STEEL,
               net.minecraft.world.item.Items.FIRE_CHARGE,
               net.minecraft.world.item.Items.BLAZE_ROD,
               net.minecraft.world.item.Items.BLAZE_POWDER,
               ESItems.SALTPETER_MATCHBOX.get(),
               ESItems.THERMAL_SPRINGSTONE_INGOT.get()
            }
         );
      this.tag(ESTags.Items.ENT_FERTILIZERS).add(new Item[]{net.minecraft.world.item.Items.BONE_MEAL, ESItems.SHADOW_SNAIL_SHELL_POWDER.get()});
      this.tag(ESTags.Items.STRANGHOUL_CAN_USE)
         .add(
            new Item[]{
               net.minecraft.world.item.Items.BOW,
               net.minecraft.world.item.Items.WOODEN_SWORD,
               net.minecraft.world.item.Items.WOODEN_PICKAXE,
               net.minecraft.world.item.Items.WOODEN_AXE,
               net.minecraft.world.item.Items.WOODEN_HOE,
               net.minecraft.world.item.Items.STONE_SWORD,
               net.minecraft.world.item.Items.STONE_PICKAXE,
               net.minecraft.world.item.Items.STONE_AXE,
               net.minecraft.world.item.Items.STONE_HOE,
               ESItems.MALARITE_SWORD.get(),
               ESItems.MALARITE_PICKAXE.get(),
               ESItems.MALARITE_AXE.get(),
               ESItems.MALARITE_HOE.get(),
               ESItems.MALARITE_SICKLE.get(),
               ESItems.MALARITE_SPEAR.get(),
               ESItems.PUNGENCY_FRUIT_AXE.get(),
               ESItems.PUNGENCY_FRUIT_SPEAR.get(),
               ESItems.SEEDS_LAUNCHER.get()
            }
         );
      this.tag(ESTags.Items.STRANGHOUL_FOOD)
         .add(new Item[]{net.minecraft.world.item.Items.ROTTEN_FLESH, ESItems.PUNGENCY_FRUIT.get(), ESItems.PUNGENCY_STEW.get()});
      this.tag(ESTags.Items.STRANGHOUL_HIRING_FOOD).add(ESItems.PUNGENCY_STEW.get());
      this.tag(ESTags.Items.STRANGHOUL_CURRENCIES).add(ESItems.MALARITE.get());
      this.tag(ESTags.Items.STRANGHOUL_VULNERABLE_TO)
         .add(new Item[]{ESItems.DEEPSILVER_SWORD.get(), ESItems.DEEPSILVER_PICKAXE.get(), ESItems.DEEPSILVER_AXE.get(), ESItems.DEEPSILVER_SICKLE.get()});
      this.tag(ESTags.Items.SEEDS_LAUNCHER_AMMO).addTag(Items.SEEDS);
      this.tag(ESTags.Items.ALLOY_FURNACES)
         .add(new Item[]{ESItems.ALLOY_FURNACE.get(), ESItems.WAXED_ALLOY_FURNACE.get(), ESItems.OXIDIZED_ALLOY_FURNACE.get()});
      this.tag(ESTags.Items.DOOMEDEN_KEYS).add(new Item[]{ESItems.EYE_OF_DOOM.get(), ESItems.LIVING_ARM.get(), ESItems.DOOMEDEN_RAPIER.get()});
      this.tag(ESTags.Items.SCYTHE_ENCHANTABLE).addTag(ESTags.Items.SCYTHES);
      this.tag(ESTags.Items.GREATSWORD_ENCHANTABLE).addTag(ESTags.Items.GREATSWORDS);
      this.tag(ESTags.Items.HAMMER_ENCHANTABLE).addTag(ESTags.Items.HAMMERS);
      this.tag(ESTags.Items.BOOMERANG_ENCHANTABLE).addTag(ESTags.Items.BOOMERANGS);
      this.tag(ESTags.Items.WHIP_ENCHANTABLE).addTag(ESTags.Items.WHIPS);
      this.tag(ESTags.Items.CHAIN_OF_SOULS_ENCHANTABLE).add(ESItems.CHAIN_OF_SOULS.get());
      this.tag(ESTags.Items.PUNGENCY_FRUIT_SPEAR_ENCHANTABLE).add(ESItems.PUNGENCY_FRUIT_SPEAR.get());
      this.tag(ESTags.Items.SEEDS_LAUNCHER_ENCHANTABLE).add(ESItems.SEEDS_LAUNCHER.get());
      this.tag(ESTags.Items.CHEST_ARMOR_ACCESSORY_APPLICABLE).addTag(ItemTags.CHEST_ARMOR);
      this.tag(ESTags.Items.AXE_ACCESSORY_APPLICABLE).addTag(ItemTags.AXES);
      this.tag(ESTags.Items.HAMMER_ACCESSORY_APPLICABLE).addTag(ESTags.Items.HAMMERS);
      this.tag(ESTags.Items.AFFECTS_PROGRESSION)
         .add(
            new Item[]{
               ESItems.PUNGENCY_FRUIT.get(),
               ESItems.SILVER_PUNGENCY_FRUIT.get(),
               ESItems.GOLEM_STEEL_INGOT.get(),
               ESItems.GOLEM_STEEL_NUGGET.get(),
               ESItems.NOCTURNAL_MILLET_SEEDS.get(),
               ESItems.NOCTURNAL_MILLET.get(),
               ESItems.FORGOTTEN_NOCTURNAL_MILLET.get()
            }
         );
      this.tag(ESTags.Items.GOLEM_FORGE_LOCATORS)
         .add(new Item[]{net.minecraft.world.item.Items.REDSTONE, ESItems.OXIDIZED_GOLEM_STEEL_INGOT.get(), ESItems.GOLEM_STEEL_INGOT.get()});
      this.tag(ESTags.Items.CURSED_GARDEN_LOCATORS)
         .add(
            new Item[]{
               net.minecraft.world.item.Items.VINE,
               ESItems.CAVE_MOSS.get(),
               ESItems.LUNAR_BERRIES.get(),
               ESItems.CAVE_MOSS_BLOCK.get(),
               ESItems.CAVE_MOSS_CARPET.get(),
               ESItems.RED_CRYSTAL_MOSS_BLOCK.get(),
               ESItems.BLUE_CRYSTAL_MOSS_BLOCK.get(),
               ESItems.RED_CRYSTAL_MOSS_CARPET.get(),
               ESItems.BLUE_CRYSTAL_MOSS_CARPET.get(),
               ESItems.RED_CRYSTAL_ROOTS.get(),
               ESItems.BLUE_CRYSTAL_ROOTS.get()
            }
         );
      this.tag(ESTags.Items.ARROW_FEATHERS).addTag(Items.FEATHERS).add(ESItems.SPIRAL_KELP.get());
      this.tag(ESTags.Items.MANA_CRYSTAL_INGREDIENTS).add(ESItems.RED_STARLIGHT_CRYSTAL_SHARD.get());
      this.tag(ESTags.Items.MANA_CRYSTALS)
         .addTags(
            new TagKey[]{
               ESTags.Items.TERRA_CRYSTALS,
               ESTags.Items.WIND_CRYSTALS,
               ESTags.Items.WATER_CRYSTALS,
               ESTags.Items.LUNAR_CRYSTALS,
               ESTags.Items.BLAZE_CRYSTALS,
               ESTags.Items.LIGHT_CRYSTALS
            }
         );
      this.tag(ESTags.Items.TERRA_CRYSTAL_INGREDIENTS)
         .add(
            new Item[]{
               net.minecraft.world.item.Items.DIRT,
               net.minecraft.world.item.Items.MUD,
               net.minecraft.world.item.Items.PODZOL,
               net.minecraft.world.item.Items.COARSE_DIRT,
               net.minecraft.world.item.Items.ROOTED_DIRT,
               ESItems.NIGHTFALL_DIRT.get(),
               ESItems.NIGHTFALL_GRASS_BLOCK.get(),
               ESItems.NIGHTFALL_PODZOL.get(),
               ESItems.TENACIOUS_NIGHTFALL_GRASS_BLOCK.get(),
               ESItems.NIGHTFALL_MUD.get(),
               ESItems.GLOWING_NIGHTFALL_MUD.get(),
               ESItems.FANTASY_GRASS_BLOCK.get(),
               ESItems.CONEBLOOM.get()
            }
         );
      this.tag(ESTags.Items.TERRA_CRYSTALS).add(new Item[]{ESItems.TERRA_CRYSTAL.get(), ESItems.MANA_CRYSTAL.get()});
      this.tag(ESTags.Items.WIND_CRYSTAL_INGREDIENTS)
         .add(new Item[]{net.minecraft.world.item.Items.WIND_CHARGE, net.minecraft.world.item.Items.BREEZE_ROD, ESItems.WHISPERBLOOM.get()});
      this.tag(ESTags.Items.WIND_CRYSTALS).add(new Item[]{ESItems.WIND_CRYSTAL.get(), ESItems.MANA_CRYSTAL.get()});
      this.tag(ESTags.Items.WATER_CRYSTAL_INGREDIENTS)
         .add(
            new Item[]{
               net.minecraft.world.item.Items.ICE,
               net.minecraft.world.item.Items.PACKED_ICE,
               net.minecraft.world.item.Items.BLUE_ICE,
               net.minecraft.world.item.Items.KELP,
               net.minecraft.world.item.Items.SEAGRASS,
               net.minecraft.world.item.Items.SEA_PICKLE,
               ESItems.ABYSSAL_FRUIT.get(),
               ESItems.ORBFLORA.get(),
               ESItems.SPIRAL_KELP.get(),
               ESItems.SEA_ROSA.get(),
               ESItems.WICK_GRASS.get(),
               ESItems.LUMENSTEM.get(),
               ESItems.CIRCULUSH.get(),
               ESItems.MARIMOLD.get(),
               ESItems.STONETT.get(),
               ESItems.LUMINIS.get(),
               ESItems.GLOWLIS.get(),
               ESItems.GLOREED.get(),
               ESItems.STARLIGHT_SEAGRASS.get(),
               ESItems.JINGLING_PICKLE.get(),
               ESItems.MOONLIGHT_DUCKWEED.get(),
               ESItems.MOONLIGHT_LILY_PAD.get(),
               ESItems.STARLIT_LILY_PAD.get()
            }
         );
      this.tag(ESTags.Items.WATER_CRYSTALS).add(new Item[]{ESItems.WATER_CRYSTAL.get(), ESItems.MANA_CRYSTAL.get()});
      this.tag(ESTags.Items.LUNAR_CRYSTAL_INGREDIENTS)
         .add(
            new Item[]{
               net.minecraft.world.item.Items.ENDER_EYE,
               net.minecraft.world.item.Items.END_STONE,
               ESItems.STARLIGHT_FLOWER.get(),
               ESItems.LUNAR_BERRIES.get(),
               ESItems.LUNAR_GRASS.get(),
               ESItems.GLOWING_LUNAR_GRASS.get(),
               ESItems.LUNAR_REED.get(),
               ESItems.LUNARIS_CACTUS.get(),
               ESItems.LUNARIS_CACTUS_GEL_BLOCK.get(),
               ESItems.LUNARIS_CACTUS_FRUIT.get()
            }
         );
      this.tag(ESTags.Items.LUNAR_CRYSTALS).add(new Item[]{ESItems.LUNAR_CRYSTAL.get(), ESItems.MANA_CRYSTAL.get()});
      this.tag(ESTags.Items.BLAZE_CRYSTAL_INGREDIENTS)
         .add(
            new Item[]{
               net.minecraft.world.item.Items.BLAZE_ROD,
               net.minecraft.world.item.Items.BLAZE_POWDER,
               net.minecraft.world.item.Items.FIRE_CHARGE,
               ESItems.SUNSET_THORNBLOOM.get(),
               ESItems.THERMAL_SPRINGSTONE.get(),
               ESItems.THERMAL_SPRINGSTONE_INGOT.get()
            }
         );
      this.tag(ESTags.Items.BLAZE_CRYSTALS).add(new Item[]{ESItems.BLAZE_CRYSTAL.get(), ESItems.MANA_CRYSTAL.get()});
      this.tag(ESTags.Items.LIGHT_CRYSTAL_INGREDIENTS)
         .add(
            new Item[]{
               net.minecraft.world.item.Items.SOUL_TORCH,
               net.minecraft.world.item.Items.TORCHFLOWER,
               ESItems.STARLIGHT_TORCHFLOWER.get(),
               ESItems.ORBFLORA_LIGHT.get()
            }
         );
      this.tag(ESTags.Items.LIGHT_CRYSTALS).add(new Item[]{ESItems.LIGHT_CRYSTAL.get(), ESItems.MANA_CRYSTAL.get()});
      this.tag(ESTags.Items.WIP)
         .add(
            new Item[]{
               ESItems.BOARWARF_SPAWN_EGG.get(),
               ESItems.ASTRAL_GOLEM_SPAWN_EGG.get(),
               ESItems.CRADLEWOOD_SAPLING.get(),
               ESItems.CRADLEWOOD_LEAVES.get(),
               ESItems.CRADLEWOOD_LOG.get(),
               ESItems.CRADLEWOOD_WOOD.get(),
               ESItems.CRADLEWOOD_PLANKS.get(),
               ESItems.STRIPPED_CRADLEWOOD_LOG.get(),
               ESItems.STRIPPED_CRADLEWOOD_WOOD.get(),
               ESItems.CRADLEWOOD_DOOR.get(),
               ESItems.CRADLEWOOD_TRAPDOOR.get(),
               ESItems.CRADLEWOOD_PRESSURE_PLATE.get(),
               ESItems.CRADLEWOOD_BUTTON.get(),
               ESItems.CRADLEWOOD_FENCE.get(),
               ESItems.CRADLEWOOD_FENCE_GATE.get(),
               ESItems.CRADLEWOOD_SLAB.get(),
               ESItems.CRADLEWOOD_STAIRS.get(),
               ESItems.CRADLEWOOD_SIGN.get(),
               ESItems.CRADLEWOOD_HANGING_SIGN.get(),
               ESItems.CRADLEWOOD_BOAT.get(),
               ESItems.CRADLEWOOD_CHEST_BOAT.get(),
               ESItems.RADIANITE.get(),
               ESItems.RADIANITE_SLAB.get(),
               ESItems.RADIANITE_STAIRS.get(),
               ESItems.RADIANITE_WALL.get(),
               ESItems.COBBLED_RADIANITE.get(),
               ESItems.COBBLED_RADIANITE_SLAB.get(),
               ESItems.COBBLED_RADIANITE_STAIRS.get(),
               ESItems.COBBLED_RADIANITE_WALL.get(),
               ESItems.RADIANITE_PILLAR.get(),
               ESItems.POLISHED_RADIANITE.get(),
               ESItems.POLISHED_RADIANITE_SLAB.get(),
               ESItems.POLISHED_RADIANITE_STAIRS.get(),
               ESItems.POLISHED_RADIANITE_WALL.get(),
               ESItems.RADIANITE_BRICKS.get(),
               ESItems.RADIANITE_BRICK_SLAB.get(),
               ESItems.RADIANITE_BRICK_STAIRS.get(),
               ESItems.RADIANITE_BRICK_WALL.get(),
               ESItems.CHISELED_RADIANITE.get(),
               ESItems.FLARE_BRICK.get(),
               ESItems.FLARE_BRICKS.get(),
               ESItems.FLARE_BRICK_SLAB.get(),
               ESItems.FLARE_BRICK_STAIRS.get(),
               ESItems.FLARE_BRICK_WALL.get(),
               ESItems.CUT_FLARE_BRICKS.get(),
               ESItems.CUT_FLARE_BRICK_SLAB.get(),
               ESItems.CUT_FLARE_BRICK_STAIRS.get(),
               ESItems.CUT_FLARE_BRICK_WALL.get(),
               ESItems.FLARE_TILES.get(),
               ESItems.FLARE_TILE_SLAB.get(),
               ESItems.FLARE_TILE_STAIRS.get(),
               ESItems.FLARE_TILE_WALL.get(),
               ESItems.CUT_FLARE_TILES.get(),
               ESItems.CUT_FLARE_TILE_SLAB.get(),
               ESItems.CUT_FLARE_TILE_STAIRS.get(),
               ESItems.CUT_FLARE_TILE_WALL.get(),
               ESItems.CHISELED_FLARE_PILLAR.get(),
               ESItems.SOLAR_EGG.get(),
               ESItems.DUSK_GLASS.get(),
               ESItems.DUSK_LIGHT.get(),
               ESItems.REINFORCED_DUSK_LIGHT.get(),
               ESItems.DUSK_EMITTER.get(),
               ESItems.DUSK_LOCKBOX.get(),
               ESItems.FLARE_SPAWNER.get(),
               ESItems.ECLIPSE_CORE.get(),
               ESItems.BROKEN_DOOMEDEN_BONE.get(),
               ESItems.BONEMORE.get(),
               ESItems.BOW_OF_BLOOD.get(),
               ESItems.LIVING_ARM.get(),
               ESItems.DOOMED_TORCH.get(),
               ESItems.DOOMED_REDSTONE_TORCH.get(),
               ESItems.DOOMEDEN_CARRION.get(),
               ESItems.ROTTEN_HAM.get(),
               ESItems.EYE_OF_DOOM.get(),
               ESItems.DOOMEDEN_RAG.get(),
               ESItems.FLESH_GRINDER.get(),
               ESItems.DOOMEDEN_RAPIER.get(),
               ESItems.DOOMEDEN_BRICKS.get(),
               ESItems.DOOMEDEN_BRICK_SLAB.get(),
               ESItems.DOOMEDEN_BRICK_STAIRS.get(),
               ESItems.DOOMEDEN_BRICK_WALL.get(),
               ESItems.POLISHED_DOOMEDEN_BRICKS.get(),
               ESItems.POLISHED_DOOMEDEN_BRICK_SLAB.get(),
               ESItems.POLISHED_DOOMEDEN_BRICK_STAIRS.get(),
               ESItems.POLISHED_DOOMEDEN_BRICK_WALL.get(),
               ESItems.DOOMEDEN_TILES.get(),
               ESItems.DOOMEDEN_TILE_SLAB.get(),
               ESItems.DOOMEDEN_TILE_STAIRS.get(),
               ESItems.DOOMEDEN_TILE_WALL.get(),
               ESItems.CHISELED_POLISHED_DOOMEDEN_BRICKS.get(),
               ESItems.CHARGED_CHISELED_POLISHED_DOOMEDEN_BRICKS.get(),
               ESItems.DOOMEDEN_LIGHT.get(),
               ESItems.DOOMEDEN_KEYHOLE.get(),
               ESItems.REDSTONE_DOOMEDEN_KEYHOLE.get(),
               ESItems.GOLDEN_GRASS.get(),
               ESItems.TALL_GOLDEN_GRASS.get(),
               ESItems.GOLDEN_GRASS_BLOCK.get(),
               ESItems.STELLAR_RACK.get(),
               ESItems.ENCHANTED_GRIMSTONE_BRICKS.get()
            }
         );
      this.copy(BlockTags.LOGS, ItemTags.LOGS);
      this.copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
      this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
      this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
      this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
      this.copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
      this.copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
      this.copy(BlockTags.SLABS, ItemTags.SLABS);
      this.copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
      this.copy(BlockTags.STAIRS, ItemTags.STAIRS);
      this.copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
      this.copy(BlockTags.WALLS, ItemTags.WALLS);
      this.copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
      this.copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
      this.copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
      this.copy(BlockTags.STANDING_SIGNS, ItemTags.SIGNS);
      this.copy(BlockTags.CEILING_HANGING_SIGNS, ItemTags.HANGING_SIGNS);
      this.copy(BlockTags.SAND, ItemTags.SAND);
      this.copy(BlockTags.SMELTS_TO_GLASS, ItemTags.SMELTS_TO_GLASS);
      this.copy(BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS);
      this.copy(BlockTags.TALL_FLOWERS, ItemTags.TALL_FLOWERS);
      this.copy(BlockTags.CANDLES, ItemTags.CANDLES);
      this.tag(ItemTags.DURABILITY_ENCHANTABLE)
         .add(
            new Item[]{
               ESItems.STARFALL_LONGBOW.get(),
               ESItems.DEEPSILVER_BRUSH.get(),
               ESItems.UNREALIUM_CROSSBOW.get(),
               ESItems.MALARITE_SPEAR.get(),
               ESItems.PUNGENCY_FRUIT_SPEAR.get(),
               ESItems.SEEDS_LAUNCHER.get(),
               ESItems.STARFIRE_CROSSBOW.get(),
               ESItems.FLOWGLAZE_BOW.get(),
               ESItems.SALTPETER_MATCHBOX.get(),
               ESItems.GLISTERING_BOW.get(),
               ESItems.MECHANICAL_CROSSBOW.get(),
               ESItems.CRYSTAL_CROSSBOW.get(),
               ESItems.WILTED_CROSSBOW.get(),
               ESItems.MOONRING_BOW.get(),
               ESItems.WAND_OF_TELEPORTATION.get(),
               ESItems.CHAIN_OF_SOULS.get(),
               ESItems.CRESCENT_SPEAR.get()
            }
         )
         .addTags(
            new TagKey[]{
               ESTags.Items.SCYTHES, ESTags.Items.SICKLES, ESTags.Items.HAMMERS, ESTags.Items.BOOMERANGS, ESTags.Items.WHIPS, ESTags.Items.SMALL_SHIELDS
            }
         );
      this.tag(ItemTags.BOW_ENCHANTABLE)
         .add(
            new Item[]{
               ESItems.STARFALL_LONGBOW.get(),
               ESItems.FLOWGLAZE_BOW.get(),
               ESItems.GLISTERING_BOW.get(),
               ESItems.MOONRING_BOW.get(),
               ESItems.BOW_OF_BLOOD.get()
            }
         );
      this.tag(ItemTags.CROSSBOW_ENCHANTABLE)
         .add(
            new Item[]{
               ESItems.UNREALIUM_CROSSBOW.get(),
               ESItems.STARFIRE_CROSSBOW.get(),
               ESItems.MECHANICAL_CROSSBOW.get(),
               ESItems.CRYSTAL_CROSSBOW.get(),
               ESItems.WILTED_CROSSBOW.get()
            }
         );
      this.tag(ItemTags.SWORD_ENCHANTABLE).add(ESItems.CRESCENT_SPEAR.get()).addTag(ESTags.Items.SCYTHE_ENCHANTABLE);
      this.tag(ItemTags.SHARP_WEAPON_ENCHANTABLE)
         .add(new Item[]{ESItems.MALARITE_SPEAR.get(), ESItems.PUNGENCY_FRUIT_SPEAR.get(), ESItems.CRESCENT_SPEAR.get()})
         .addTag(ESTags.Items.SCYTHE_ENCHANTABLE);
      this.tag(ItemTags.WEAPON_ENCHANTABLE)
         .addTags(new TagKey[]{ESTags.Items.HAMMER_ENCHANTABLE, ESTags.Items.BOOMERANG_ENCHANTABLE, ESTags.Items.WHIP_ENCHANTABLE});
      this.tag(ItemTags.FIRE_ASPECT_ENCHANTABLE)
         .add(ESItems.CRESCENT_SPEAR.get())
         .addTags(new TagKey[]{ESTags.Items.SCYTHE_ENCHANTABLE, ESTags.Items.HAMMER_ENCHANTABLE, ESTags.Items.BOOMERANG_ENCHANTABLE});
      this.tag(ItemTags.BOATS)
         .add(
            new Item[]{
               ESItems.LUNAR_BOAT.get(),
               ESItems.NORTHLAND_BOAT.get(),
               ESItems.BANYIN_BOAT.get(),
               ESItems.SCARLET_BOAT.get(),
               ESItems.TORREYA_BOAT.get(),
               ESItems.JINGLESTEM_RAFT.get(),
               ESItems.CRADLEWOOD_BOAT.get()
            }
         );
      this.tag(ItemTags.CHEST_BOATS)
         .add(
            new Item[]{
               ESItems.LUNAR_CHEST_BOAT.get(),
               ESItems.NORTHLAND_CHEST_BOAT.get(),
               ESItems.BANYIN_CHEST_BOAT.get(),
               ESItems.SCARLET_CHEST_BOAT.get(),
               ESItems.TORREYA_CHEST_BOAT.get(),
               ESItems.JINGLESTEM_CHEST_RAFT.get(),
               ESItems.CRADLEWOOD_CHEST_BOAT.get()
            }
         );
      this.tag(ItemTags.FREEZE_IMMUNE_WEARABLES)
         .add(
            new Item[]{
               ESItems.THERMAL_SPRINGSTONE_HELMET.get(),
               ESItems.THERMAL_SPRINGSTONE_CHESTPLATE.get(),
               ESItems.THERMAL_SPRINGSTONE_LEGGINGS.get(),
               ESItems.THERMAL_SPRINGSTONE_BOOTS.get(),
               ESItems.GLACITE_HELMET.get(),
               ESItems.GLACITE_CHESTPLATE.get(),
               ESItems.GLACITE_LEGGINGS.get(),
               ESItems.GLACITE_BOOTS.get()
            }
         );
      this.tag(ItemTags.MEAT)
         .add(
            new Item[]{
               ESItems.ROTTEN_FLESH_JERKY.get(),
               ESItems.ROOKFISH.get(),
               ESItems.COOKED_ROOKFISH.get(),
               ESItems.LUMINOFISH.get(),
               ESItems.COOKED_LUMINOFISH.get(),
               ESItems.LUMINARIS.get(),
               ESItems.COOKED_LUMINARIS.get(),
               ESItems.AURORA_DEER_STEAK.get(),
               ESItems.COOKED_AURORA_DEER_STEAK.get(),
               ESItems.RATLIN_MEAT.get(),
               ESItems.COOKED_RATLIN_MEAT.get(),
               ESItems.SHADOW_SNAIL_MEAT.get(),
               ESItems.COOKED_SHADOW_SNAIL_MEAT.get(),
               ESItems.DOOMEDEN_CARRION.get()
            }
         );
      this.tag(ItemTags.HEAD_ARMOR)
         .add(
            new Item[]{
               ESItems.THERMAL_SPRINGSTONE_HELMET.get(),
               ESItems.GLACITE_HELMET.get(),
               ESItems.STARLIT_DIAMOND_HELMET.get(),
               ESItems.DEEPSILVER_HELMET.get(),
               ESItems.UNREALIUM_HELMET.get(),
               ESItems.AMARAMBER_MASK.get(),
               ESItems.ALCHEMIST_MASK.get(),
               ESItems.AETHERSENT_HOOD.get(),
               ESItems.AIR_SAC_MASK.get()
            }
         );
      this.tag(ItemTags.CHEST_ARMOR)
         .add(
            new Item[]{
               ESItems.AETHERSENT_CAPE.get(),
               ESItems.THERMAL_SPRINGSTONE_CHESTPLATE.get(),
               ESItems.GLACITE_CHESTPLATE.get(),
               ESItems.STARLIT_DIAMOND_CHESTPLATE.get(),
               ESItems.DEEPSILVER_CHESTPLATE.get(),
               ESItems.UNREALIUM_CHESTPLATE.get(),
               ESItems.AMARAMBER_CHESTPLATE.get(),
               ESItems.ALCHEMIST_ROBE.get()
            }
         );
      this.tag(ItemTags.LEG_ARMOR)
         .add(
            new Item[]{
               ESItems.AETHERSENT_BOTTOMS.get(),
               ESItems.THERMAL_SPRINGSTONE_LEGGINGS.get(),
               ESItems.GLACITE_LEGGINGS.get(),
               ESItems.STARLIT_DIAMOND_LEGGINGS.get(),
               ESItems.DEEPSILVER_LEGGINGS.get(),
               ESItems.UNREALIUM_LEGGINGS.get()
            }
         );
      this.tag(ItemTags.FOOT_ARMOR)
         .add(
            new Item[]{
               ESItems.AETHERSENT_BOOTS.get(),
               ESItems.THERMAL_SPRINGSTONE_BOOTS.get(),
               ESItems.GLACITE_BOOTS.get(),
               ESItems.STARLIT_DIAMOND_BOOTS.get(),
               ESItems.DEEPSILVER_BOOTS.get(),
               ESItems.UNREALIUM_BOOTS.get(),
               ESItems.AIR_SAC_BOOTS.get()
            }
         );
      this.tag(ItemTags.SWORDS)
         .add(
            new Item[]{
               ESItems.RAGE_OF_STARS.get(),
               ESItems.THERMAL_SPRINGSTONE_SWORD.get(),
               ESItems.GLACITE_SWORD.get(),
               ESItems.STARLIT_DIAMOND_SWORD.get(),
               ESItems.DEEPSILVER_SWORD.get(),
               ESItems.UNREALIUM_SWORD.get(),
               ESItems.MALARITE_SWORD.get(),
               ESItems.STARFIRE_SWORD.get(),
               ESItems.FLOWGLAZE_SWORD.get(),
               ESItems.AMARAMBER_SWORD.get(),
               ESItems.SHATTERED_SWORD.get(),
               ESItems.DAGGER_OF_HUNGER.get(),
               ESItems.GLISTERING_SWORD.get(),
               ESItems.ENERGY_SWORD.get(),
               ESItems.DOOMEDEN_RAPIER.get()
            }
         )
         .addTag(ESTags.Items.GREATSWORDS);
      this.tag(ItemTags.PICKAXES)
         .add(
            new Item[]{
               ESItems.THERMAL_SPRINGSTONE_PICKAXE.get(),
               ESItems.THERMAL_SPRINGSTONE_HAMMER.get(),
               ESItems.GLACITE_PICKAXE.get(),
               ESItems.STARLIT_DIAMOND_PICKAXE.get(),
               ESItems.DEEPSILVER_PICKAXE.get(),
               ESItems.UNREALIUM_PICKAXE.get(),
               ESItems.MALARITE_PICKAXE.get(),
               ESItems.STARFIRE_PICKAXE.get(),
               ESItems.STARFIRE_HAMMER.get(),
               ESItems.FLOWGLAZE_PICKAXE.get(),
               ESItems.AMARAMBER_PICKAXE.get(),
               ESItems.GLISTERING_MORNING_STAR.get(),
               ESItems.UNDERMINER.get(),
               ESItems.GRAVITY_PICKAXE.get()
            }
         );
      this.tag(ItemTags.CLUSTER_MAX_HARVESTABLES)
         .add(
            new Item[]{
               ESItems.THERMAL_SPRINGSTONE_PICKAXE.get(),
               ESItems.THERMAL_SPRINGSTONE_HAMMER.get(),
               ESItems.GLACITE_PICKAXE.get(),
               ESItems.STARLIT_DIAMOND_PICKAXE.get(),
               ESItems.DEEPSILVER_PICKAXE.get(),
               ESItems.UNREALIUM_PICKAXE.get(),
               ESItems.MALARITE_PICKAXE.get(),
               ESItems.STARFIRE_PICKAXE.get(),
               ESItems.STARFIRE_HAMMER.get(),
               ESItems.FLOWGLAZE_PICKAXE.get(),
               ESItems.AMARAMBER_PICKAXE.get(),
               ESItems.GLISTERING_MORNING_STAR.get(),
               ESItems.UNDERMINER.get(),
               ESItems.GRAVITY_PICKAXE.get()
            }
         );
      this.tag(ItemTags.AXES)
         .add(
            new Item[]{
               ESItems.THERMAL_SPRINGSTONE_AXE.get(),
               ESItems.GLACITE_AXE.get(),
               ESItems.STARLIT_DIAMOND_AXE.get(),
               ESItems.DEEPSILVER_AXE.get(),
               ESItems.UNREALIUM_AXE.get(),
               ESItems.MALARITE_AXE.get(),
               ESItems.PUNGENCY_FRUIT_AXE.get(),
               ESItems.STARFIRE_AXE.get(),
               ESItems.FLOWGLAZE_AXE.get(),
               ESItems.AMARAMBER_AXE.get()
            }
         );
      this.tag(ItemTags.HOES)
         .add(
            new Item[]{
               ESItems.THERMAL_SPRINGSTONE_HOE.get(),
               ESItems.GLACITE_HOE.get(),
               ESItems.STARLIT_DIAMOND_HOE.get(),
               ESItems.DEEPSILVER_HOE.get(),
               ESItems.UNREALIUM_HOE.get(),
               ESItems.MALARITE_HOE.get(),
               ESItems.STARFIRE_HOE.get(),
               ESItems.FLOWGLAZE_HOE.get(),
               ESItems.AMARAMBER_HOE.get()
            }
         )
         .addTags(new TagKey[]{ESTags.Items.SCYTHES, ESTags.Items.SICKLES});
      this.tag(ItemTags.SHOVELS)
         .add(
            new Item[]{
               ESItems.THERMAL_SPRINGSTONE_SHOVEL.get(),
               ESItems.GLACITE_SHOVEL.get(),
               ESItems.STARLIT_DIAMOND_SHOVEL.get(),
               ESItems.DEEPSILVER_SHOVEL.get(),
               ESItems.UNREALIUM_SHOVEL.get(),
               ESItems.MALARITE_SHOVEL.get(),
               ESItems.STARFIRE_SHOVEL.get(),
               ESItems.FLOWGLAZE_SHOVEL.get(),
               ESItems.AMARAMBER_SHOVEL.get()
            }
         );
      this.tag(ItemTags.ARROWS)
         .add(
            new Item[]{
               ESItems.AETHERSENT_ARROW.get(),
               ESItems.GLACITE_ARROW.get(),
               ESItems.MALARITE_ARROW.get(),
               ESItems.AMARAMBER_ARROW.get(),
               ESItems.THIOQUARTZ_ARROW.get(),
               ESItems.VORACIOUS_ARROW.get(),
               ESItems.AIR_SAC_ARROW.get()
            }
         );
      this.tag(ItemTags.TRIM_MATERIALS)
         .add(
            new Item[]{
               ESItems.STARCORE.get(),
               ESItems.RED_STARLIGHT_CRYSTAL_SHARD.get(),
               ESItems.BLUE_STARLIGHT_CRYSTAL_SHARD.get(),
               ESItems.AETHERSENT_INGOT.get(),
               ESItems.THERMAL_SPRINGSTONE_INGOT.get(),
               ESItems.GLACITE_SHARD.get(),
               ESItems.STARLIT_DIAMOND.get(),
               ESItems.DEEPSILVER_INGOT.get(),
               ESItems.UNREALIUM_INGOT.get(),
               ESItems.MALARITE.get(),
               ESItems.STARFIRE.get(),
               ESItems.FLOWGLAZE.get(),
               ESItems.AMARAMBER_INGOT.get(),
               ESItems.THIOQUARTZ_SHARD.get(),
               ESItems.GOLEM_STEEL_INGOT.get(),
               ESItems.TENACIOUS_PETAL.get(),
               ESItems.BROKEN_DOOMEDEN_BONE.get()
            }
         );
      this.tag(ItemTags.TRIM_TEMPLATES)
         .add(
            new Item[]{
               ESItems.KEEPER_ARMOR_TRIM_SMITHING_TEMPLATE.get(),
               ESItems.FORGE_ARMOR_TRIM_SMITHING_TEMPLATE.get(),
               ESItems.BLOOMING_ARMOR_TRIM_SMITHING_TEMPLATE.get(),
               ESItems.TWINING_ARMOR_TRIM_SMITHING_TEMPLATE.get()
            }
         );
      this.tag(ItemTags.STONE_TOOL_MATERIALS)
         .add(new Item[]{ESItems.GRIMSTONE.get(), ESItems.COBBLED_GRIMSTONE.get(), ESItems.VOIDSTONE.get(), ESItems.COBBLED_VOIDSTONE.get()});
      this.tag(ItemTags.STONE_CRAFTING_MATERIALS)
         .add(new Item[]{ESItems.GRIMSTONE.get(), ESItems.COBBLED_GRIMSTONE.get(), ESItems.VOIDSTONE.get(), ESItems.COBBLED_VOIDSTONE.get()});
      this.tag(ItemTags.CREEPER_IGNITERS).add(ESItems.SALTPETER_MATCHBOX.get());
   }
}
