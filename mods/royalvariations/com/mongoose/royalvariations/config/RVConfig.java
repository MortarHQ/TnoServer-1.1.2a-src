package com.mongoose.royalvariations.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import java.io.File;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;

public class RVConfig {
   public static final Builder BUILDER = new Builder();
   public static final ModConfigSpec SPEC = BUILDER.build();
   public static final ConfigValue<Integer> RoyalZombieSpawnWeight = BUILDER.comment("Spawn Weight for Royal Zombie, set to 0 to disable spawn, Default: 9")
      .defineInRange("royalZombieSpawnWeight", 9, 0, Integer.MAX_VALUE);
   public static final ConfigValue<Integer> RoyalZombieSpawnMinCount = BUILDER.comment("Spawn minimum group count for Royal Zombie, Default: 1")
      .defineInRange("royalZombieSpawnMinCount", 1, 1, Integer.MAX_VALUE);
   public static final ConfigValue<Integer> RoyalZombieSpawnMaxCount = BUILDER.comment(
         "Spawn maximum group count for Royal Zombie, must be equal or higher than min count, Default: 1"
      )
      .defineInRange("royalZombieSpawnMaxCount", 1, 1, Integer.MAX_VALUE);
   public static final ConfigValue<Integer> RoyalSkeletonSpawnWeight = BUILDER.comment("Spawn Weight for Royal Skeleton, set to 0 to disable spawn, Default: 9")
      .defineInRange("royalSkeletonSpawnWeight", 9, 0, Integer.MAX_VALUE);
   public static final ConfigValue<Integer> RoyalSkeletonSpawnMinCount = BUILDER.comment("Spawn minimum group count for Royal Skeleton, Default: 1")
      .defineInRange("royalSkeletonSpawnMinCount", 1, 1, Integer.MAX_VALUE);
   public static final ConfigValue<Integer> RoyalSkeletonSpawnMaxCount = BUILDER.comment(
         "Spawn maximum group count for Royal Skeleton, must be equal or higher than min count, Default: 1"
      )
      .defineInRange("royalSkeletonSpawnMaxCount", 1, 1, Integer.MAX_VALUE);
   public static final ConfigValue<Integer> RoyalCreeperSpawnWeight = BUILDER.comment("Spawn Weight for Royal Creeper, set to 0 to disable spawn, Default: 9")
      .defineInRange("royalCreeperSpawnWeight", 9, 0, Integer.MAX_VALUE);
   public static final ConfigValue<Integer> RoyalCreeperSpawnMinCount = BUILDER.comment("Spawn minimum group count for Royal Creeper, Default: 1")
      .defineInRange("royalCreeperSpawnMinCount", 1, 1, Integer.MAX_VALUE);
   public static final ConfigValue<Integer> RoyalCreeperSpawnMaxCount = BUILDER.comment(
         "Spawn maximum group count for Royal Creeper, must be equal or higher than min count, Default: 1"
      )
      .defineInRange("royalCreeperSpawnMaxCount", 1, 1, Integer.MAX_VALUE);
   public static final ConfigValue<Integer> RoyalEndermanSpawnWeight = BUILDER.comment("Spawn Weight for Royal Enderman, set to 0 to disable spawn, Default: 5")
      .defineInRange("royalEndermanSpawnWeight", 5, 0, Integer.MAX_VALUE);
   public static final ConfigValue<Integer> RoyalEndermanSpawnMinCount = BUILDER.comment("Spawn minimum group count for Royal Enderman, Default: 1")
      .defineInRange("royalEndermanSpawnMinCount", 1, 1, Integer.MAX_VALUE);
   public static final ConfigValue<Integer> RoyalEndermanSpawnMaxCount = BUILDER.comment(
         "Spawn maximum group count for Royal Enderman, must be equal or higher than min count, Default: 1"
      )
      .defineInRange("royalEndermanSpawnMaxCount", 1, 1, Integer.MAX_VALUE);
   public static final ConfigValue<Integer> RoyalEndermanEndSpawnChance = BUILDER.comment("Chance for Royal Enderman to spawn in The End, Default: 5")
      .defineInRange("royalEndermanEndSpawnChance", 5, 0, 100);

   public static void loadConfig(ModConfigSpec config, String path) {
      CommentedFileConfig file = (CommentedFileConfig)CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
      file.load();
      config.correct(file);
   }

   static {
      BUILDER.push("Spawning");
      BUILDER.push("Royal Zombie");
      BUILDER.pop();
      BUILDER.push("Royal Skeleton");
      BUILDER.pop();
      BUILDER.push("Royal Creeper");
      BUILDER.pop();
      BUILDER.push("Royal Enderman");
      BUILDER.pop();
      BUILDER.pop();
   }
}
