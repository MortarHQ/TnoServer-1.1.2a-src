package twilightforest.enums;

import java.util.Locale;
import java.util.function.Supplier;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.StringRepresentable.EnumCodec;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.Nullable;
import twilightforest.block.entity.spawner.BossSpawnerBlockEntity;
import twilightforest.init.TFBlockEntities;

public enum BossVariant implements StringRepresentable {
   NAGA(BossVariant.TrophyType.GOLD, TFBlockEntities.NAGA_SPAWNER::get),
   LICH(BossVariant.TrophyType.GOLD, TFBlockEntities.LICH_SPAWNER::get),
   HYDRA(BossVariant.TrophyType.GOLD, TFBlockEntities.HYDRA_SPAWNER::get),
   UR_GHAST(BossVariant.TrophyType.GOLD, TFBlockEntities.UR_GHAST_SPAWNER::get),
   KNIGHT_PHANTOM(BossVariant.TrophyType.IRON, TFBlockEntities.KNIGHT_PHANTOM_SPAWNER::get),
   SNOW_QUEEN(BossVariant.TrophyType.GOLD, TFBlockEntities.SNOW_QUEEN_SPAWNER::get),
   MINOSHROOM(BossVariant.TrophyType.IRON, TFBlockEntities.MINOSHROOM_SPAWNER::get),
   ALPHA_YETI(BossVariant.TrophyType.IRON, TFBlockEntities.ALPHA_YETI_SPAWNER::get),
   QUEST_RAM(BossVariant.TrophyType.IRONWOOD, null),
   FINAL_BOSS(BossVariant.TrophyType.GOLD, TFBlockEntities.FINAL_BOSS_SPAWNER::get);

   public static final EnumCodec<BossVariant> CODEC = StringRepresentable.fromEnum(BossVariant::values);
   private final BossVariant.TrophyType trophyType;
   @Nullable
   private final Supplier<BlockEntityType<? extends BossSpawnerBlockEntity<?>>> blockEntityType;

   BossVariant(BossVariant.TrophyType trophyType, @Nullable Supplier<BlockEntityType<? extends BossSpawnerBlockEntity<?>>> blockEntityType) {
      this.trophyType = trophyType;
      this.blockEntityType = blockEntityType;
   }

   public String getSerializedName() {
      return this.name().toLowerCase(Locale.ROOT);
   }

   public BossVariant.TrophyType getTrophyType() {
      return this.trophyType;
   }

   @Nullable
   public BlockEntityType<? extends BossSpawnerBlockEntity<?>> getType() {
      return this.blockEntityType != null ? this.blockEntityType.get() : null;
   }

   public enum TrophyType {
      GOLD("trophy"),
      IRON("trophy_minor"),
      IRONWOOD("trophy_quest");

      private final String modelName;

      TrophyType(String modelName) {
         this.modelName = modelName;
      }

      public String getModelName() {
         return this.modelName;
      }
   }
}
