package dev.denismasterherobrine.afterdark.registry;

import dev.denismasterherobrine.afterdark.blocks.TeleportBlock;
import dev.denismasterherobrine.afterdark.items.TeleportCatalystItem;
import java.util.OptionalLong;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.dimension.DimensionType.MonsterSettings;

public class AfterdarkRegistry {
   public static final ResourceKey<DimensionType> AFTERDARK_DIMENSION = ResourceKey.create(
      Registries.DIMENSION_TYPE, ResourceLocation.fromNamespaceAndPath("the_afterdark", "afterdark")
   );
   public static final ResourceKey<Biome> AFTERDARK_BIOME_BASIC = ResourceKey.create(
      Registries.BIOME, ResourceLocation.fromNamespaceAndPath("the_afterdark", "blank_biome")
   );
   public static final ResourceKey<Level> AFTERDARK_LEVEL = ResourceKey.create(
      Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath("the_afterdark", "afterdark")
   );
   public static final ResourceKey<LevelStem> AFTERDARK_DIMENSION_OPTIONS = ResourceKey.create(
      Registries.LEVEL_STEM, ResourceLocation.fromNamespaceAndPath("the_afterdark", "afterdark")
   );
   public static final Block TELEPORT_BLOCK = new TeleportBlock();
   public static final Item TELEPORT_BLOCK_ITEM = new BlockItem(TELEPORT_BLOCK, new Properties());
   public static final Item TELEPORT_CATALYST_ITEM = new TeleportCatalystItem();
   public static CreativeModeTab AFTERDARK = CreativeModeTab.builder(null, -1)
      .title(Component.translatable("itemGroup.afterdark"))
      .icon(() -> new ItemStack(TELEPORT_BLOCK_ITEM))
      .displayItems((displayContext, entries) -> {
         entries.accept(TELEPORT_BLOCK_ITEM);
         entries.accept(TELEPORT_CATALYST_ITEM);
      })
      .build();

   public static void bootstrapDimensionType(BootstrapContext<DimensionType> context) {
      context.register(
         AFTERDARK_DIMENSION,
         new DimensionType(
            OptionalLong.of(21000L),
            false,
            false,
            false,
            true,
            1.0,
            false,
            true,
            -128,
            384,
            320,
            BlockTags.INFINIBURN_OVERWORLD,
            BuiltinDimensionTypes.OVERWORLD_EFFECTS,
            0.0F,
            new MonsterSettings(false, false, UniformInt.of(0, 7), 0)
         )
      );
   }
}
