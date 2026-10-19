package twilightforest.world.components.structures.util;

import com.mojang.datafixers.Products.P5;
import com.mojang.datafixers.Products.P6;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import org.jetbrains.annotations.Nullable;

public abstract class ProgressionStructure extends ConquerableStructure implements AdvancementLockedStructure, StructureHints {
   protected final AdvancementLockedStructure.AdvancementLockConfig advancementLockConfig;
   protected final Optional<StructureHints.HintConfig> hintConfig;
   private long lastSpawnedHintMonsterTime;

   protected static <S extends ProgressionStructure> P6<Mu<S>, AdvancementLockedStructure.AdvancementLockConfig, Optional<StructureHints.HintConfig>, Optional<DecorationClearance.DecorationConfig>, Boolean, Optional<Holder<MapDecorationType>>, StructureSettings> progressionCodec(
      Instance<S> instance
   ) {
      return instance.group(
            AdvancementLockedStructure.AdvancementLockConfig.CODEC.fieldOf("advancements_required").forGetter(s -> s.advancementLockConfig),
            StructureHints.HintConfig.CODEC.optionalFieldOf("hint_creature").forGetter(s -> s.hintConfig)
         )
         .and(landmarkCodec(instance));
   }

   protected static <S extends ProgressionStructure> P5<Mu<S>, AdvancementLockedStructure.AdvancementLockConfig, Optional<StructureHints.HintConfig>, Optional<DecorationClearance.DecorationConfig>, Boolean, Optional<Holder<MapDecorationType>>> progressionCodecNoSettings(
      Instance<S> instance
   ) {
      return instance.group(
            AdvancementLockedStructure.AdvancementLockConfig.CODEC.fieldOf("advancements_required").forGetter(s -> s.advancementLockConfig),
            StructureHints.HintConfig.CODEC.optionalFieldOf("hint_creature").forGetter(s -> s.hintConfig)
         )
         .and(landmarkCodecNoSettings(instance));
   }

   public ProgressionStructure(
      AdvancementLockedStructure.AdvancementLockConfig advancementLockConfig,
      Optional<StructureHints.HintConfig> hintConfig,
      Optional<DecorationClearance.DecorationConfig> decorationConfig,
      boolean centerInChunk,
      Optional<Holder<MapDecorationType>> structureIcon,
      StructureSettings structureSettings
   ) {
      super(decorationConfig, centerInChunk, structureIcon, structureSettings);
      this.advancementLockConfig = advancementLockConfig;
      this.hintConfig = hintConfig;
   }

   @Override
   public List<ResourceLocation> getRequiredAdvancements() {
      return this.advancementLockConfig.requiredAdvancements();
   }

   @Override
   public void trySpawnHintMonster(Level world, Player player, BlockPos pos) {
      long currentTime = world.getGameTime();
      if (currentTime < this.lastSpawnedHintMonsterTime) {
         this.lastSpawnedHintMonsterTime = 0L;
      }

      if (currentTime - this.lastSpawnedHintMonsterTime > 1200L) {
         for (int i = 0; i < 20; i++) {
            if (this.didSpawnHintMonster(world, player, pos)) {
               this.lastSpawnedHintMonsterTime = currentTime;
               break;
            }
         }
      }
   }

   @Override
   public ItemStack createHintBook(RegistryAccess registryAccess) {
      return this.hintConfig.<ItemStack>map(config -> config.hintItem().copy()).orElse(ItemStack.EMPTY);
   }

   @Nullable
   @Override
   public Mob createHintMonster(Level world) {
      return this.hintConfig.<Mob>map(config -> (Mob)config.hintMob().create(world)).orElse(null);
   }
}
