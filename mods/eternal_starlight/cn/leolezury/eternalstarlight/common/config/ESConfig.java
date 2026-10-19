package cn.leolezury.eternalstarlight.common.config;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class ESConfig {
   public static final Path CONFIG_PATH = ESPlatform.INSTANCE.getConfigDir().resolve("eternal_starlight.json");
   public static ESConfig INSTANCE = new ESConfig();
   public boolean enableDataFixer = false;
   public boolean blockUnknownAttachmentWarning = true;
   public boolean enablePortalShader = true;
   public boolean enableScreenShake = true;
   public boolean enableLootChest = true;
   public boolean enableBossRespawn = true;
   public int bossRespawnCooldown = 36000;
   public boolean spawnInEternalStarlight = false;
   public boolean respawnInEternalStarlight = false;
   public boolean startWithGuidebook = false;
   public float aethersentMeteorDropRate = 5.0E-4F;
   public boolean aethersentMeteorReplaceBlocks = false;
   public int mobMaxTearyTicks = 200;
   public ESConfig.MobsConfig mobsConfig = new ESConfig.MobsConfig();
   public ESConfig.ItemsConfig itemsConfig = new ESConfig.ItemsConfig();

   public static void load() {
      Gson gson = new GsonBuilder().setLenient().setPrettyPrinting().create();
      File file = CONFIG_PATH.toFile();
      if (file.exists()) {
         try (FileReader fileReader = new FileReader(file)) {
            JsonReader reader = new JsonReader(fileReader);

            try {
               INSTANCE = (ESConfig)gson.fromJson(reader, ESConfig.class);
               write(gson, file, INSTANCE);
            } catch (Throwable var8) {
               try {
                  reader.close();
               } catch (Throwable var7) {
                  var8.addSuppressed(var7);
               }

               throw var8;
            }

            reader.close();
         } catch (IOException e) {
            EternalStarlight.LOGGER.error("Failed to load config");
         }
      } else {
         write(gson, file, INSTANCE);
      }
   }

   private static void write(Gson gson, File file, ESConfig config) {
      try (FileWriter writer = new FileWriter(file)) {
         gson.toJson(config, writer);
      } catch (IOException e) {
         EternalStarlight.LOGGER.error("Failed to write config");
      }
   }

   public static class ItemsConfig {
      public double playerAethersentMeteorDamageScale = 1.0;
      public ESConfig.ItemsConfig.ChainOfSoulsConfig chainOfSouls = new ESConfig.ItemsConfig.ChainOfSoulsConfig(64.0, 2.0, 0.5);
      public ESConfig.ItemsConfig.CrystalbornCatalystConfig crystalbornCatalyst = new ESConfig.ItemsConfig.CrystalbornCatalystConfig(128, 50);
      public ESConfig.ItemsConfig.AlloyFurnaceConfig alloyFurnace = new ESConfig.ItemsConfig.AlloyFurnaceConfig(12000, 3);

      public record AlloyFurnaceConfig(int totalOverheatTicks, int explosionRadius) {
      }

      public record ChainOfSoulsConfig(double maxRange, double soulAbsorbDamage, double healPercentage) {
      }

      public record CrystalbornCatalystConfig(int maxRange, int energyPerShard) {
      }
   }

   public static class MobsConfig {
      public ESConfig.MobsConfig.MobConfig boarwarf = new ESConfig.MobsConfig.MobConfig(30.0, 10.0, true);
      public ESConfig.MobsConfig.AttackingMobConfig astralGolem = new ESConfig.MobsConfig.AttackingMobConfig(100.0, 10.0, 10.0, 100.0, true);
      public ESConfig.MobsConfig.AttackingMobConfig gleech = new ESConfig.MobsConfig.AttackingMobConfig(8.0, 0.0, 1.0, 16.0, true);
      public ESConfig.MobsConfig.AttackingMobConfig lonestarSkeleton = new ESConfig.MobsConfig.AttackingMobConfig(20.0, 0.0, 3.2, 16.0, true);
      public ESConfig.MobsConfig.AttackingMobConfig nightfallSpider = new ESConfig.MobsConfig.AttackingMobConfig(10.0, 0.0, 2.0, 16.0, true);
      public ESConfig.MobsConfig.AttackingMobConfig seeker = new ESConfig.MobsConfig.AttackingMobConfig(15.0, 0.0, 3.0, 16.0, true);
      public ESConfig.MobsConfig.AttackingMobConfig thirstWalker = new ESConfig.MobsConfig.AttackingMobConfig(40.0, 0.0, 4.5, 32.0, true);
      public ESConfig.MobsConfig.CreteorConfig creteor = new ESConfig.MobsConfig.CreteorConfig(15.0, 0.0, 5.0, 48.0, true, 0.7);
      public ESConfig.MobsConfig.AttackingMobConfig tinyCreteor = new ESConfig.MobsConfig.AttackingMobConfig(5.0, 0.0, 2.0, 48.0, true);
      public ESConfig.MobsConfig.StranghoulConfig stranghoul = new ESConfig.MobsConfig.StranghoulConfig(30.0, 2.0, 2.0, 32.0, true, 8000);
      public ESConfig.MobsConfig.MobConfig ent = new ESConfig.MobsConfig.MobConfig(10.0, 0.0, true);
      public ESConfig.MobsConfig.MobConfig ratlin = new ESConfig.MobsConfig.MobConfig(15.0, 0.0, true);
      public ESConfig.MobsConfig.AttackingMobConfig zombifiedRatlin = new ESConfig.MobsConfig.AttackingMobConfig(20.0, 2.0, 3.0, 35.0, true);
      public ESConfig.MobsConfig.MobConfig shadowSnail = new ESConfig.MobsConfig.MobConfig(8.0, 6.0, true);
      public ESConfig.MobsConfig.MobConfig yeti = new ESConfig.MobsConfig.MobConfig(20.0, 0.0, true);
      public ESConfig.MobsConfig.AttackingMobConfig auroraDeer = new ESConfig.MobsConfig.AttackingMobConfig(20.0, 0.0, 3.0, 16.0, true);
      public ESConfig.MobsConfig.AttackingMobConfig crystallizedMoth = new ESConfig.MobsConfig.AttackingMobConfig(20.0, 0.0, 1.5, 64.0, true);
      public ESConfig.MobsConfig.MobConfig shimmerLacewing = new ESConfig.MobsConfig.MobConfig(5.0, 0.0, true);
      public ESConfig.MobsConfig.MobConfig starfireBird = new ESConfig.MobsConfig.MobConfig(15.0, 0.0, true);
      public ESConfig.MobsConfig.MobConfig grimstoneGolem = new ESConfig.MobsConfig.MobConfig(20.0, 0.0, true);
      public ESConfig.MobsConfig.MobConfig aethersentGolem = new ESConfig.MobsConfig.MobConfig(40.0, 10.0, true);
      public ESConfig.MobsConfig.AttackingMobConfig luminofish = new ESConfig.MobsConfig.AttackingMobConfig(3.0, 0.0, 3.0, 16.0, true);
      public ESConfig.MobsConfig.AttackingMobConfig luminaris = new ESConfig.MobsConfig.AttackingMobConfig(3.0, 0.0, 3.0, 64.0, true);
      public ESConfig.MobsConfig.AttackingMobConfig twilightGaze = new ESConfig.MobsConfig.AttackingMobConfig(10.0, 0.0, 3.0, 16.0, true);
      public ESConfig.MobsConfig.GatekeeperConfig theGatekeeper = new ESConfig.MobsConfig.GatekeeperConfig(175.0, 15.0, 1.0, 200.0, true, false);
      public ESConfig.MobsConfig.BossConfig starlightGolem = new ESConfig.MobsConfig.BossConfig(200.0, 10.0, 1.0, 200.0, true);
      public ESConfig.MobsConfig.AttackingMobConfig freeze = new ESConfig.MobsConfig.AttackingMobConfig(15.0, 0.0, 3.0, 32.0, true);
      public ESConfig.MobsConfig.BossConfig permafrost = new ESConfig.MobsConfig.BossConfig(120.0, 10.0, 1.0, 100.0, true);
      public ESConfig.MobsConfig.BossConfig lunarMonstrosity = new ESConfig.MobsConfig.BossConfig(200.0, 12.0, 1.0, 200.0, true);
      public ESConfig.MobsConfig.AttackingMobConfig tangled = new ESConfig.MobsConfig.AttackingMobConfig(20.0, 0.0, 5.0, 64.0, true);
      public ESConfig.MobsConfig.AttackingMobConfig tangledSkull = new ESConfig.MobsConfig.AttackingMobConfig(1.0, 0.0, 3.0, 64.0, true);
      public ESConfig.MobsConfig.BossConfig solarCreeper = new ESConfig.MobsConfig.BossConfig(250.0, 10.0, 1.0, 200.0, true);

      public record AttackingMobConfig(double maxHealth, double armor, double attackDamage, double followRange, boolean canSpawn) {
      }

      public record BossConfig(double maxHealth, double armor, double attackDamageScale, double followRange, boolean canSpawn) {
      }

      public record CreteorConfig(double maxHealth, double armor, double attackDamage, double followRange, boolean canSpawn, double spawnChance) {
      }

      public record GatekeeperConfig(
         double maxHealth, double armor, double attackDamage, double followRange, boolean canSpawn, boolean canAlwaysHurtWhenFighting
      ) {
      }

      public record MobConfig(double maxHealth, double armor, boolean canSpawn) {
      }

      public record StranghoulConfig(double maxHealth, double armor, double attackDamage, double followRange, boolean canSpawn, int hiringCooldown) {
      }
   }
}
