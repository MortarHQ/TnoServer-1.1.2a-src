package de.cristelknight.cristellib;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;
import de.cristelknight.cristellib.api.CristelLibAPI;
import de.cristelknight.cristellib.builtinpacks.BuiltInDataPackLoader;
import de.cristelknight.cristellib.builtinpacks.BuiltInPackConfig;
import de.cristelknight.cristellib.builtinpacks.RuntimePack;
import de.cristelknight.cristellib.config.simple.datafixer.DataFixer;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.minecraft.SharedConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CristelLib {
   public static final String MOD_ID = "cristellib";
   public static final Logger LOGGER = LogManager.getLogger("Cristel Lib");
   public static final ResourceLocation CRISTEL_LIB_PACK_RL = ResourceLocation.fromNamespaceAndPath("cristellib", "runtime_pack");
   public static final RuntimePack RUNTIME_PACK = new RuntimePack(
      CRISTEL_LIB_PACK_RL,
      SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA),
      "Runtime Pack for built-in features",
      CristelLibExpectPlatform.getResourceDirectory("cristellib", "pack.png")
   );
   private static final CristelLibRegistry REGISTRY = new CristelLibRegistry();

   public static String getWithPrefix(String message) {
      return String.format("[%s] %s", "cristellib", message);
   }

   public static void init() {
   }

   public static void preInit() {
      DataFixer.registerFixer();
      CristelLibRegistry.configs = ImmutableMap.copyOf(CristelLibExpectPlatform.getConfigs(REGISTRY));
      BuiltInDataPackLoader.freeze();
      BuiltInPackConfig.updateConfig();
      UnmodifiableIterator var0 = CristelLibRegistry.getConfigs().values().iterator();

      while (var0.hasNext()) {
         Set<StructureConfig> pack = (Set<StructureConfig>)var0.next();

         for (StructureConfig structureConfig : pack) {
            structureConfig.writeConfig(false);
            structureConfig.addSetsToRuntimePack();
         }
      }
   }

   public static void readAPI(CristelLibRegistry registry, String modID, CristelLibAPI api, Map<String, Set<StructureConfig>> configs) {
      try {
         api.registerBuiltInPacks();
         Set<StructureConfig> set = new HashSet<>();
         api.registerConfigs(set);
         configs.put(modID, set);
         api.registerStructureSets(registry);
         set.forEach(StructureConfig::getDefaultNamespace);
      } catch (Throwable e) {
         LOGGER.error("Mod: {} provides a broken implementation of CristelLibAPI", modID, e);
      }
   }
}
