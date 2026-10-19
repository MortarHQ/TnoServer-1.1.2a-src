package net.jadenxgamer.elysium_api.impl.core.worldgen.structure.processor;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jadenxgamer.elysium_api.impl.registry.ElysiumRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.jetbrains.annotations.Nullable;

public class ProtectNonReplaceableProcessor extends StructureProcessor {
   private final HolderSet<Block> blacklist;
   public static final MapCodec<ProtectNonReplaceableProcessor> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("blacklist").forGetter(p -> p.blacklist))
         .apply(instance, ProtectNonReplaceableProcessor::new)
   );

   public ProtectNonReplaceableProcessor(HolderSet<Block> blacklist) {
      this.blacklist = blacklist;
   }

   @Nullable
   public StructureBlockInfo processBlock(
      LevelReader level, BlockPos offset, BlockPos pos, StructureBlockInfo blockInfo, StructureBlockInfo relativeBlockInfo, StructurePlaceSettings settings
   ) {
      BlockState existingState = level.getBlockState(relativeBlockInfo.pos());
      return !level.getBlockState(relativeBlockInfo.pos()).canBeReplaced() && !this.blacklistedBlock(existingState) ? null : relativeBlockInfo;
   }

   private boolean blacklistedBlock(BlockState state) {
      return this.blacklist.contains(state.getBlockHolder());
   }

   protected StructureProcessorType<?> getType() {
      return ElysiumRegistries.PROTECT_NON_REPLACEABLE.get();
   }
}
