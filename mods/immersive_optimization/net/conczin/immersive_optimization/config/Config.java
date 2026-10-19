package net.conczin.immersive_optimization.config;

import java.util.HashMap;
import java.util.Map;

public final class Config extends JsonConfig {
   private static Config INSTANCE = loadOrCreate(new Config(), Config.class);
   public String _documentation = "https://github.com/Luke100000/ImmersiveOptimization/wiki";
   public boolean enableEntities = true;
   public boolean enableBlockEntities = true;
   public boolean enableDistanceCulling = true;
   public boolean enableTrackingCulling = true;
   public boolean enableViewportCulling = true;
   public int minDistance = 6;
   public int blocksPerLevel = 64;
   public int blocksPerLevelDistanceCulled = 10;
   public int blocksPerLevelTrackingCulled = 10;
   public int blocksPerLevelViewportCulled = 20;
   public int maxLevel = 20;
   public int blocksPerLevelBlockEntities = 32;
   public int stressedThreshold = 45;
   public float minDecreaseFactor = 0.25F;
   public Map<String, Boolean> dimensions = new HashMap<>();
   public Map<String, Boolean> entities;
   public boolean cullProjectiles;

   public Config() {
      super("immersive_optimization");
      this.dimensions.put("minecraft:overworld", true);
      this.dimensions.put("minecraft:the_nether", true);
      this.dimensions.put("minecraft:the_end", true);
      this.entities = new HashMap<>();
      this.entities.put("minecraft:player", false);
      this.entities.put("minecraft:ender_dragon", false);
      this.entities.put("minecraft:arrow", false);
      this.entities.put("minecraft:ender_pearl", false);
      this.entities.put("fromanotherworld:starship", false);
      this.entities.put("create", false);
      this.entities.put("valkyrienskies", false);
      this.cullProjectiles = false;
   }

   public static Config getInstance() {
      return INSTANCE;
   }

   @Override
   int getVersion() {
      return 2;
   }

   public void reload() {
      INSTANCE = loadOrCreate(new Config(), Config.class);
   }
}
