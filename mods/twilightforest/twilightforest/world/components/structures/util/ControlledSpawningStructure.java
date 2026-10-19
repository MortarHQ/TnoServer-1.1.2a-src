package twilightforest.world.components.structures.util;

import com.mojang.datafixers.Products.P7;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;

public abstract class ControlledSpawningStructure extends ProgressionStructure implements ConfigurableSpawns {
   protected final ControlledSpawns.ControlledSpawningConfig controlledSpawningConfig;

   protected static <S extends ControlledSpawningStructure> P7<Mu<S>, ControlledSpawns.ControlledSpawningConfig, AdvancementLockedStructure.AdvancementLockConfig, Optional<StructureHints.HintConfig>, Optional<DecorationClearance.DecorationConfig>, Boolean, Optional<Holder<MapDecorationType>>, StructureSettings> controlledSpawningCodec(
      Instance<S> instance
   ) {
      return instance.group(ControlledSpawns.ControlledSpawningConfig.CODEC.fieldOf("controlled_spawns").forGetter(ControlledSpawningStructure::getConfig))
         .and(progressionCodec(instance));
   }

   public ControlledSpawningStructure(
      ControlledSpawns.ControlledSpawningConfig controlledSpawningConfig,
      AdvancementLockedStructure.AdvancementLockConfig advancementLockConfig,
      Optional<StructureHints.HintConfig> hintConfig,
      Optional<DecorationClearance.DecorationConfig> decorationConfig,
      boolean centerInChunk,
      Optional<Holder<MapDecorationType>> structureIcon,
      StructureSettings structureSettings
   ) {
      super(advancementLockConfig, hintConfig, decorationConfig, centerInChunk, structureIcon, structureSettings);
      this.controlledSpawningConfig = controlledSpawningConfig;
   }

   @Override
   public ControlledSpawns.ControlledSpawningConfig getConfig() {
      return this.controlledSpawningConfig;
   }
}
