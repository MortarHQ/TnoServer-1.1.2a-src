package com.github.L_Ender.cataclysm.config;

import net.neoforged.fml.event.config.ModConfigEvent.Loading;
import net.neoforged.fml.event.config.ModConfigEvent.Reloading;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import org.apache.commons.lang3.tuple.Pair;

public final class ConfigHolder {
   public static final ModConfigSpec COMMON_SPEC;
   public static final CommonConfig COMMON;
   public static final ModConfigSpec CLIENT_SPEC;
   public static final ClientConfig CLIENT;

   public static void onModConfigLoadingEvent(Loading event) {
      if (event.getConfig().getSpec() == CLIENT_SPEC) {
         CMClientConfig.Clientbake(CLIENT);
      }

      if (event.getConfig().getSpec() == COMMON_SPEC) {
         CMCommonConfig.Commonbake(COMMON);
      }
   }

   public static void onModConfigReloadEvent(Reloading event) {
      if (event.getConfig().getSpec() == CLIENT_SPEC) {
         CMClientConfig.Clientbake(CLIENT);
      }

      if (event.getConfig().getSpec() == COMMON_SPEC) {
         CMCommonConfig.Commonbake(COMMON);
      }
   }

   static {
      Pair<CommonConfig, ModConfigSpec> specPair = new Builder().configure(CommonConfig::new);
      COMMON = (CommonConfig)specPair.getLeft();
      COMMON_SPEC = (ModConfigSpec)specPair.getRight();
      Pair<ClientConfig, ModConfigSpec> clientSpecPair = new Builder().configure(ClientConfig::new);
      CLIENT = (ClientConfig)clientSpecPair.getLeft();
      CLIENT_SPEC = (ModConfigSpec)clientSpecPair.getRight();
   }
}
