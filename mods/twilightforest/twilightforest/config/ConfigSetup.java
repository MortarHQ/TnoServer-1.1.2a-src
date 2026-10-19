package twilightforest.config;

import java.util.List;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.event.config.ModConfigEvent.Loading;
import net.neoforged.fml.event.config.ModConfigEvent.Reloading;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.apache.commons.lang3.tuple.Pair;
import twilightforest.network.SyncUncraftingTableConfigPacket;

public final class ConfigSetup {
   private static final ModConfigSpec CLIENT_SPEC;
   private static final ModConfigSpec COMMON_SPEC;
   static final TFClientConfig CLIENT_CONFIG;
   static final TFCommonConfig COMMON_CONFIG;

   public static void loadConfigs(Loading event) {
      if (event.getConfig().getSpec() == CLIENT_SPEC) {
         TFConfig.rebakeClientOptions(CLIENT_CONFIG);
      } else if (event.getConfig().getSpec() == COMMON_SPEC) {
         TFConfig.rebakeCommonOptions(COMMON_CONFIG);
      }
   }

   public static void reloadConfigs(Reloading event) {
      if (event.getConfig().getSpec() == CLIENT_SPEC) {
         TFConfig.rebakeClientOptions(CLIENT_CONFIG);
      } else if (event.getConfig().getSpec() == COMMON_SPEC) {
         TFConfig.rebakeCommonOptions(COMMON_CONFIG);
      }
   }

   public static void syncUncraftingConfig(PlayerLoggedInEvent event) {
      MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
      if (server != null && server.isDedicatedServer() && event.getEntity() instanceof ServerPlayer player) {
         PacketDistributor.sendToPlayer(
            player,
            new SyncUncraftingTableConfigPacket(
               (Double)COMMON_CONFIG.UNCRAFTING_STUFFS.uncraftingXpCostMultiplier.get(),
               (Double)COMMON_CONFIG.UNCRAFTING_STUFFS.repairingXpCostMultiplier.get(),
               (Boolean)COMMON_CONFIG.UNCRAFTING_STUFFS.allowShapelessUncrafting.get(),
               (Boolean)COMMON_CONFIG.UNCRAFTING_STUFFS.disableIngredientSwitching.get(),
               (Boolean)COMMON_CONFIG.UNCRAFTING_STUFFS.disableUncraftingOnly.get(),
               (Boolean)COMMON_CONFIG.UNCRAFTING_STUFFS.disableEntireTable.get(),
               (List<? extends String>)COMMON_CONFIG.UNCRAFTING_STUFFS.disableUncraftingRecipes.get(),
               (Boolean)COMMON_CONFIG.UNCRAFTING_STUFFS.reverseRecipeBlacklist.get(),
               (List<? extends String>)COMMON_CONFIG.UNCRAFTING_STUFFS.blacklistedUncraftingModIds.get(),
               (Boolean)COMMON_CONFIG.UNCRAFTING_STUFFS.flipUncraftingModIdList.get()
            ),
            new CustomPacketPayload[0]
         );
      }
   }

   static {
      Pair<TFCommonConfig, ModConfigSpec> specPair = new Builder().configure(TFCommonConfig::new);
      ModLoadingContext.get().getActiveContainer().registerConfig(Type.COMMON, COMMON_SPEC = (ModConfigSpec)specPair.getRight());
      COMMON_CONFIG = (TFCommonConfig)specPair.getLeft();
      specPair = new Builder().configure(TFClientConfig::new);
      ModLoadingContext.get().getActiveContainer().registerConfig(Type.CLIENT, CLIENT_SPEC = (ModConfigSpec)specPair.getRight());
      CLIENT_CONFIG = (TFClientConfig)specPair.getLeft();
   }
}
