package twilightforest.entity.passive;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFEntities;
import twilightforest.init.TFSounds;
import twilightforest.loot.TFLootTables;

public class Bighorn extends Sheep {
   public Bighorn(EntityType<? extends Bighorn> type, Level world) {
      super(type, world);
   }

   public ResourceKey<LootTable> getDefaultLootTable() {
      if (this.isSheared()) {
         return this.getType().getDefaultLootTable();
      }

      return switch (this.getColor()) {
         case ORANGE -> TFLootTables.BIGHORN_SHEEP_ORANGE;
         case MAGENTA -> TFLootTables.BIGHORN_SHEEP_MAGENTA;
         case LIGHT_BLUE -> TFLootTables.BIGHORN_SHEEP_LIGHT_BLUE;
         case YELLOW -> TFLootTables.BIGHORN_SHEEP_YELLOW;
         case LIME -> TFLootTables.BIGHORN_SHEEP_LIME;
         case PINK -> TFLootTables.BIGHORN_SHEEP_PINK;
         case GRAY -> TFLootTables.BIGHORN_SHEEP_GRAY;
         case LIGHT_GRAY -> TFLootTables.BIGHORN_SHEEP_LIGHT_GRAY;
         case CYAN -> TFLootTables.BIGHORN_SHEEP_CYAN;
         case PURPLE -> TFLootTables.BIGHORN_SHEEP_PURPLE;
         case BLUE -> TFLootTables.BIGHORN_SHEEP_BLUE;
         case BROWN -> TFLootTables.BIGHORN_SHEEP_BROWN;
         case GREEN -> TFLootTables.BIGHORN_SHEEP_GREEN;
         case RED -> TFLootTables.BIGHORN_SHEEP_RED;
         case BLACK -> TFLootTables.BIGHORN_SHEEP_BLACK;
         default -> TFLootTables.BIGHORN_SHEEP_WHITE;
      };
   }

   private static DyeColor getRandomFleeceColor(RandomSource random) {
      return random.nextBoolean() ? DyeColor.BROWN : DyeColor.byId(random.nextInt(16));
   }

   @Nullable
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor accessor, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata) {
      livingdata = super.finalizeSpawn(accessor, difficulty, reason, livingdata);
      this.setColor(getRandomFleeceColor(accessor.getRandom()));
      return livingdata;
   }

   public Sheep getBreedOffspring(ServerLevel world, AgeableMob ageable) {
      if (ageable instanceof Bighorn otherParent) {
         Bighorn babySheep = (Bighorn)((EntityType)TFEntities.BIGHORN_SHEEP.get()).create(world);
         if (babySheep != null) {
            babySheep.setColor(this.getOffspringColor(this, otherParent));
         }

         return babySheep;
      } else {
         TwilightForestMod.LOGGER.error("Code was called to breed a Bighorn with a non Bighorn! Cancelling!");
         return null;
      }
   }

   public float getWalkTargetValue(BlockPos pos, LevelReader reader) {
      BlockState state = reader.getBlockState(pos.below());
      return !state.is(Blocks.GRASS_BLOCK) && !state.is(Blocks.PODZOL) ? reader.getPathfindingCostFromLightLevels(pos) : 10.0F;
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)TFSounds.BIGHORN_SHEEP_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)TFSounds.BIGHORN_SHEEP_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)TFSounds.BIGHORN_SHEEP_DEATH.get();
   }

   protected void playStepSound(BlockPos pos, BlockState state) {
      this.playSound((SoundEvent)TFSounds.BIGHORN_SHEEP_STEP.get(), 0.15F, 1.0F);
   }
}
