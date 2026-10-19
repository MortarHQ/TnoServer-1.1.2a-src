package fuzs.illagerinvasion.config;

import fuzs.puzzleslib.api.config.v3.Config;
import fuzs.puzzleslib.api.config.v3.ConfigCore;
import java.util.function.Supplier;

public class ServerConfig implements ConfigCore {
   @Config(
      category = "general",
      description = {
            "Will platinum armor trims provide special effects to the player depending on the piece of armor they are applied to.",
            "Helmet: More experience from killing mobs.",
            "Chestplate: Faster block breaking speed when using an incorrect or no tool.",
            "Leggings: Slower hunger drain when performaing actions.",
            "Boots: Farmland can no longer be trampled."
      }
   )
   public boolean platinumTrimEffects = true;
   @Config(category = "invoker", description = "Show a yellow boss bar when fighting an invoker.")
   public boolean invokerBossBar = true;
   @Config
   public final ServerConfig.IllagerConfig basher = new ServerConfig.IllagerConfig(
      RaidWavesConfigHelper.BASHER_RAID_WAVES, RaidWavesConfigHelper::getBasherRaidWaves
   );
   @Config
   public final ServerConfig.IllagerConfig provoker = new ServerConfig.IllagerConfig(
      RaidWavesConfigHelper.PROVOKER_RAID_WAVES, RaidWavesConfigHelper::getProvokerRaidWaves
   );
   @Config
   public final ServerConfig.IllagerConfig necromancer = new ServerConfig.IllagerConfig(
      RaidWavesConfigHelper.NECROMANCER_RAID_WAVES, RaidWavesConfigHelper::getNecromancerRaidWaves
   );
   @Config
   public final ServerConfig.IllagerConfig sorcerer = new ServerConfig.IllagerConfig(
      RaidWavesConfigHelper.SORCERER_RAID_WAVES, RaidWavesConfigHelper::getSorcererRaidWaves
   );
   @Config
   public final ServerConfig.IllagerConfig illusioner = new ServerConfig.IllagerConfig(
      RaidWavesConfigHelper.ILLUSIONER_RAID_WAVES, RaidWavesConfigHelper::getIllusionerRaidWaves
   );
   @Config
   public final ServerConfig.IllagerConfig archivist = new ServerConfig.IllagerConfig(
      RaidWavesConfigHelper.ARCHIVIST_RAID_WAVES, RaidWavesConfigHelper::getArchivistRaidWaves
   );
   @Config
   public final ServerConfig.IllagerConfig marauder = new ServerConfig.IllagerConfig(
      RaidWavesConfigHelper.MARAUDER_RAID_WAVES, RaidWavesConfigHelper::getMarauderRaidWaves
   );
   @Config
   public final ServerConfig.IllagerConfig inquisitor = new ServerConfig.IllagerConfig(
      RaidWavesConfigHelper.INQUISITOR_RAID_WAVES, RaidWavesConfigHelper::getInquisitorRaidWaves
   );
   @Config
   public final ServerConfig.IllagerConfig alchemist = new ServerConfig.IllagerConfig(
      RaidWavesConfigHelper.ALCHEMIST_RAID_WAVES, RaidWavesConfigHelper::getAlchemistRaidWaves
   );
   @Config
   public final ServerConfig.IllagerConfig invoker = new ServerConfig.IllagerConfig(
      RaidWavesConfigHelper.INVOKER_RAID_WAVES, RaidWavesConfigHelper::getInvokerRaidWaves
   );

   public ServerConfig() {
      this.invoker.participateInRaids = false;
   }

   public static class IllagerConfig implements ConfigCore {
      private final int[] raidWaves;
      private final Supplier<int[]> raidWavesSupplier;
      @Config(description = "Does this illager take part in village raids.")
      public boolean participateInRaids = true;

      public IllagerConfig(int[] raidWaves, Supplier<int[]> raidWavesSupplier) {
         this.raidWaves = raidWaves;
         this.raidWavesSupplier = raidWavesSupplier;
      }

      public void afterConfigReload() {
         int[] source = this.participateInRaids ? this.raidWavesSupplier.get() : RaidWavesConfigHelper.getEmptyRaidWaves();
         System.arraycopy(source, 0, this.raidWaves, 0, this.raidWaves.length);
      }
   }
}
