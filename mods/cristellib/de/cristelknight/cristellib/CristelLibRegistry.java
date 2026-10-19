package de.cristelknight.cristellib;

import com.google.common.collect.ImmutableMap;
import de.cristelknight.cristellib.data.codec.StructureSetData;
import java.util.List;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;

public class CristelLibRegistry {
   protected static ImmutableMap<String, Set<StructureConfig>> configs = ImmutableMap.of();

   protected CristelLibRegistry() {
   }

   public static ImmutableMap<String, Set<StructureConfig>> getConfigs() {
      if (!configs.isEmpty()) {
         return configs;
      } else {
         throw new RuntimeException(CristelLib.getWithPrefix("Tried to access Registry before initialized."));
      }
   }

   public void registerSetToConfig(String modID, String namespace, List<String> sets, StructureConfig... configs) {
      boolean isMC = namespace == null || namespace.equals("minecraft");
      this.registerSetToConfig(
         modID,
         sets.stream().map(string -> isMC ? ResourceLocation.withDefaultNamespace(string) : ResourceLocation.fromNamespaceAndPath(namespace, string)).toList(),
         configs
      );
   }

   public void registerSetToConfig(String modID, List<ResourceLocation> sets, StructureConfig... configs) {
      for (StructureConfig config : configs) {
         config.addSet(new StructureSetData(modID, sets));
      }
   }

   public void registerSetToConfig(String modID, ResourceLocation set, StructureConfig... configs) {
      for (StructureConfig config : configs) {
         config.addSet(new StructureSetData(modID, List.of(set)));
      }
   }
}
