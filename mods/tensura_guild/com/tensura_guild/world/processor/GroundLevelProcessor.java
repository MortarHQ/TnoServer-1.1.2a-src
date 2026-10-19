package com.tensura_guild.world.processor;

import com.mojang.serialization.MapCodec;
import com.tensura_guild.registry.GuildProcessors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.jetbrains.annotations.Nullable;

public class GroundLevelProcessor extends StructureProcessor {
   public static final GroundLevelProcessor INSTANCE = new GroundLevelProcessor();
   public static final MapCodec<GroundLevelProcessor> CODEC = MapCodec.unit(() -> INSTANCE);

   private GroundLevelProcessor() {
   }

   @Nullable
   public StructureBlockInfo process(
      LevelReader level,
      BlockPos origin,
      BlockPos centerBottom,
      StructureBlockInfo originalBlockInfo,
      StructureBlockInfo modifiedBlockInfo,
      StructurePlaceSettings settings,
      @Nullable StructureTemplate template
   ) {
      return originalBlockInfo.state().isAir() ? null : modifiedBlockInfo;
   }

   protected StructureProcessorType<?> getType() {
      return (StructureProcessorType<?>)GuildProcessors.GROUND_LEVEL.get();
   }
}
