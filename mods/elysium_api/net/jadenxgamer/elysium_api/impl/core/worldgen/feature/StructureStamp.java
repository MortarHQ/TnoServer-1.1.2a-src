package net.jadenxgamer.elysium_api.impl.core.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.jadenxgamer.elysium_api.ElysiumAPI;
import net.jadenxgamer.elysium_api.impl.registry.ElysiumBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.StringRepresentable.StringRepresentableCodec;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.util.random.WeightedEntry.Wrapper;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.Palette;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StructureStamp extends Feature<StructureStamp.Config> {
   public StructureStamp(Codec<StructureStamp.Config> pCodec) {
      super(pCodec);
   }

   public boolean place(FeaturePlaceContext<StructureStamp.Config> context) {
      BlockPos origin = context.origin();
      WorldGenLevel level = context.level();
      RandomSource random = context.random();
      StructureStamp.Config config = (StructureStamp.Config)context.config();
      return config.canPlaceOn().isPresent() && !level.getBlockState(origin.below()).is(config.canPlaceOn().get())
         ? false
         : config.templates()
            .getRandom(random)
            .map(selected -> this.placeTemplate((ResourceLocation)selected.data(), origin, level, random, config))
            .orElseGet(
               () -> {
                  ElysiumAPI.LOGGER
                     .warn(
                        "StructureStamp failed to place at '{}' due to a lack of templates. This could happen if the templates weighted list is empty.", origin
                     );
                  return false;
               }
            );
   }

   private boolean placeTemplate(ResourceLocation templateLocation, BlockPos origin, WorldGenLevel level, RandomSource random, StructureStamp.Config config) {
      StructureTemplate template = level.getLevel().getServer().getStructureManager().getOrCreate(templateLocation);
      Rotation rotation = config.rotation().isEmpty() ? Rotation.getRandom(random) : config.rotation().get();
      BlockPos pivot = BlockPos.ZERO;
      BlockPos placementPos;
      switch (config.originType()) {
         case CENTERED:
            Vec3i size = template.getSize();
            pivot = new BlockPos(size.getX() / 2, 0, size.getZ() / 2);
            placementPos = origin.subtract(pivot);
            break;
         case ANCHORED:
            BlockPos anchorLocal = findAnchor(template);
            if (anchorLocal == null) {
               placementPos = origin;
               ElysiumAPI.LOGGER.warn("No Structure Stamp Anchor was found within '{}', fallback to CORNER origin_type.", templateLocation);
            } else {
               pivot = anchorLocal;
               placementPos = origin.subtract(anchorLocal);
            }
            break;
         default:
            placementPos = origin;
      }

      int offset = config.originOffset().sample(random);
      placementPos = placementPos.offset(0, offset, 0);
      StructurePlaceSettings settings = new StructurePlaceSettings()
         .setRotation(rotation)
         .setRotationPivot(pivot)
         .setRandom(random)
         .setLiquidSettings(config.liquidSettings);

      for (StructureProcessor processor : ((StructureProcessorList)config.processors().value()).list()) {
         settings.addProcessor(processor);
      }

      if (config.originType() == StructureStamp.OriginType.ANCHORED) {
         settings.addProcessor(new StructureStamp.AnchorRemovalProcessor());
      }

      template.placeInWorld(level, placementPos, placementPos, settings, random, 3);
      return true;
   }

   @Nullable
   private static BlockPos findAnchor(StructureTemplate template) {
      for (Palette palette : template.palettes) {
         for (StructureBlockInfo info : palette.blocks()) {
            if (info.state().is(ElysiumBlocks.STRUCTURE_STAMP_ANCHOR.get())) {
               return info.pos();
            }
         }
      }

      return null;
   }

   private static class AnchorRemovalProcessor extends StructureProcessor {
      @Nullable
      public StructureBlockInfo processBlock(
         LevelReader level, BlockPos offset, BlockPos pos, StructureBlockInfo blockInfo, StructureBlockInfo relativeBlockInfo, StructurePlaceSettings settings
      ) {
         return relativeBlockInfo.state().is(ElysiumBlocks.STRUCTURE_STAMP_ANCHOR.get())
            ? null
            : super.processBlock(level, offset, pos, blockInfo, relativeBlockInfo, settings);
      }

      protected StructureProcessorType<?> getType() {
         return StructureProcessorType.BLOCK_IGNORE;
      }
   }

   public record Config(
      WeightedRandomList<Wrapper<ResourceLocation>> templates,
      Optional<HolderSet<Block>> canPlaceOn,
      Holder<StructureProcessorList> processors,
      Optional<Rotation> rotation,
      LiquidSettings liquidSettings,
      StructureStamp.OriginType originType,
      IntProvider originOffset
   ) implements FeatureConfiguration {
      public static final Codec<StructureStamp.Config> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               WeightedRandomList.codec(Wrapper.codec(ResourceLocation.CODEC)).fieldOf("templates").forGetter(StructureStamp.Config::templates),
               RegistryCodecs.homogeneousList(Registries.BLOCK).optionalFieldOf("can_place_on").forGetter(StructureStamp.Config::canPlaceOn),
               StructureProcessorType.LIST_CODEC.fieldOf("processors").forGetter(StructureStamp.Config::processors),
               Rotation.CODEC.optionalFieldOf("rotation").forGetter(StructureStamp.Config::rotation),
               LiquidSettings.CODEC.fieldOf("liquid_settings").orElse(LiquidSettings.APPLY_WATERLOGGING).forGetter(StructureStamp.Config::liquidSettings),
               StructureStamp.OriginType.CODEC.optionalFieldOf("origin_type", StructureStamp.OriginType.CORNER).forGetter(StructureStamp.Config::originType),
               IntProvider.CODEC.optionalFieldOf("origin_offset", ConstantInt.of(0)).forGetter(StructureStamp.Config::originOffset)
            )
            .apply(instance, StructureStamp.Config::new)
      );
   }

   public enum OriginType implements StringRepresentable {
      CORNER("corner"),
      CENTERED("centered"),
      ANCHORED("anchored");

      private final String name;
      public static final StringRepresentableCodec<StructureStamp.OriginType> CODEC = StringRepresentable.fromEnum(StructureStamp.OriginType::values);

      OriginType(String name) {
         this.name = name;
      }

      @NotNull
      public String getSerializedName() {
         return this.name;
      }

      @Override
      public String toString() {
         return this.name;
      }
   }
}
