package org.crypticdev.stextras.integration.lootr;

import com.mojang.serialization.MapCodec;
import dev.architectury.platform.Platform;
import io.github.manasmods.tensura.registry.item.TensuraMobDropItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.crypticdev.stextras.Stextras;
import org.jetbrains.annotations.Nullable;

public class CharybdisLootProcessor extends StructureProcessor {
   public static final MapCodec<CharybdisLootProcessor> CODEC = MapCodec.unit(CharybdisLootProcessor::new);

   @Nullable
   public StructureBlockInfo processBlock(
      LevelReader world,
      BlockPos pos,
      BlockPos pivot,
      StructureBlockInfo originalBlockInfo,
      StructureBlockInfo currentBlockInfo,
      StructurePlaceSettings settings
   ) {
      ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(currentBlockInfo.state().getBlock());
      if (blockId.toString().equals(TensuraMobDropItems.CHARYBDIS_CORE.getId().toString())) {
         Stextras.LOGGER.info("STExtras: FOUND Charybdis Core at " + currentBlockInfo.pos());
         if (!Platform.isModLoaded("lootr")) {
            return currentBlockInfo;
         }

         Stextras.LOGGER.info("STExtras: Lootr detected. Replacing Core with Barrel.");
         BlockState barrelState = (BlockState)Blocks.BARREL.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.UP);
         CompoundTag nbt = new CompoundTag();
         nbt.putString("LootTable", ResourceLocation.fromNamespaceAndPath("stextras", "chests/charybdis_reward").toString());
         nbt.putLong("LootTableSeed", world instanceof Level lvl ? lvl.getRandom().nextLong() : 0L);
         return new StructureBlockInfo(currentBlockInfo.pos(), barrelState, nbt);
      } else {
         return currentBlockInfo;
      }
   }

   protected StructureProcessorType<?> getType() {
      return (StructureProcessorType<?>)LootrIntegration.CHARYBDIS_LOOT_PROCESSOR.get();
   }
}
