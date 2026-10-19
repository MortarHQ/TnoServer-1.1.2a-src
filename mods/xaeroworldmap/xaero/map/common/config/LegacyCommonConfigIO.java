package xaero.map.common.config;

import com.google.common.collect.Sets;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import xaero.lib.common.config.profile.ConfigProfile;
import xaero.lib.common.util.IOUtils;
import xaero.map.WorldMap;
import xaero.map.common.config.option.WorldMapProfiledConfigOptions;
import xaero.map.common.config.primary.option.WorldMapPrimaryCommonConfigOptions;

public class LegacyCommonConfigIO {
   private final Path configFilePath;
   private boolean allowCaveModeOnServer;
   private boolean allowNetherCaveModeOnServer;
   private boolean shouldEnableEveryoneTracksEveryone;

   public LegacyCommonConfigIO(Path configFilePath) {
      this.configFilePath = configFilePath;
   }

   public void load() {
      ConfigProfile defaultEnforcedProfile = WorldMap.INSTANCE.getConfigs().getServerConfigManager().getDefaultEnforcedProfile();

      try (
         BufferedInputStream bufferedOutput = new BufferedInputStream(new FileInputStream(this.configFilePath.toFile()));
         BufferedReader reader = new BufferedReader(new InputStreamReader(bufferedOutput));
      ) {
         try {
            String line;
            while ((line = reader.readLine()) != null) {
               this.readLine(line.split(":"));
            }

            if (!this.allowCaveModeOnServer || !this.allowNetherCaveModeOnServer) {
               if (!this.allowCaveModeOnServer && !this.allowNetherCaveModeOnServer) {
                  defaultEnforcedProfile.set(WorldMapProfiledConfigOptions.CAVE_MODE_ALLOWED, false);
               } else if (this.allowCaveModeOnServer) {
                  defaultEnforcedProfile.set(
                     WorldMapProfiledConfigOptions.CAVE_MODE_ALLOWED_DIMENSIONS,
                     Sets.newHashSet(new ResourceLocation[]{Level.OVERWORLD.location(), Level.END.location()})
                  );
               } else {
                  defaultEnforcedProfile.set(
                     WorldMapProfiledConfigOptions.CAVE_MODE_ALLOWED_DIMENSIONS, Sets.newHashSet(new ResourceLocation[]{Level.NETHER.location()})
                  );
               }
            }
         } finally {
            WorldMap.INSTANCE.getConfigs().getPrimaryCommonConfigManagerIO().save();
            WorldMap.INSTANCE.getConfigs().getServerConfigProfileIO().save(defaultEnforcedProfile);
            reader.close();
            IOUtils.tryQuickFileBackupMove(this.configFilePath, 10);
         }
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private boolean readLine(String[] args) {
      if (args[0].equals("allowCaveModeOnServer")) {
         this.allowCaveModeOnServer = args[1].equals("true");
         return true;
      } else if (args[0].equals("allowNetherCaveModeOnServer")) {
         this.allowNetherCaveModeOnServer = args[1].equals("true");
         return true;
      } else if (args[0].equals("registerStatusEffects")) {
         WorldMap.INSTANCE
            .getConfigs()
            .getPrimaryCommonConfigManager()
            .getConfig()
            .set(WorldMapPrimaryCommonConfigOptions.REGISTER_EFFECTS, args[1].equals("true"));
         return true;
      } else if (args[0].equals("everyoneTracksEveryone") && args[1].equals("true")) {
         this.shouldEnableEveryoneTracksEveryone = true;
         return true;
      } else {
         return false;
      }
   }

   public boolean shouldEnableEveryoneTracksEveryone() {
      return this.shouldEnableEveryoneTracksEveryone;
   }
}
