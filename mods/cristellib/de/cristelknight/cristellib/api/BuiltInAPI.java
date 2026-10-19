package de.cristelknight.cristellib.api;

import de.cristelknight.cristellib.CristelLib;
import de.cristelknight.cristellib.CristelLibRegistry;
import de.cristelknight.cristellib.StructureConfig;
import de.cristelknight.cristellib.builtinpacks.BuiltInDataPackLoader;
import de.cristelknight.cristellib.config.ConfigType;
import java.util.List;
import java.util.Set;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

@CristelPlugin
public class BuiltInAPI implements CristelLibAPI {
   public static final StructureConfig MINECRAFT_ED = StructureConfig.createWithDefaultConfigPath(
      "vanilla_structures", "toggle_structure_config", ConfigType.ENABLE_DISABLE
   );
   public static final StructureConfig MINECRAFT_P = StructureConfig.createWithDefaultConfigPath(
      "vanilla_structures", "placement_structure_config", ConfigType.PLACEMENT
   );

   @Override
   public void registerConfigs(Set<StructureConfig> sets) {
      sets.add(MINECRAFT_ED);
      sets.add(MINECRAFT_P);
      MINECRAFT_ED.setHeader(
         "This config makes it possible to switch off any Minecraft structure.\nTo disable a structure, simply set the value of that structure to \"false\".\nTo change the rarity of a structure category use the structure placement config.\n\n=====\nCreated by Cristel Lib\n"
      );
      MINECRAFT_P.setHeader(
         "This config makes it possible to change the spacing, separation, salt (and frequency) of Minecraft's structure sets.\n    SPACING ---  controls how far a structure can be from others of its kind\n\tSEPARATION --- controls how close to each other two structures of the same type can be.\nKEEP IN MIND THAT SPACING ALWAYS NEEDS TO BE HIGHER THAN SEPARATION.\n\n=====\nCreated by Cristel Lib\n"
      );
   }

   @Override
   public void registerStructureSets(CristelLibRegistry registry) {
      registry.registerSetToConfig(
         "minecraft",
         null,
         List.of(
            "ancient_cities",
            "buried_treasures",
            "desert_pyramids",
            "end_cities",
            "igloos",
            "jungle_temples",
            "nether_complexes",
            "nether_fossils",
            "ocean_monuments",
            "ocean_ruins",
            "pillager_outposts",
            "ruined_portals",
            "shipwrecks",
            "swamp_huts",
            "villages",
            "woodland_mansions"
         ),
         MINECRAFT_ED,
         MINECRAFT_P
      );
      registry.registerSetToConfig("minecraft", ResourceLocation.withDefaultNamespace("strongholds"), MINECRAFT_ED);
   }

   @Override
   public void registerBuiltInPacks() {
      BuiltInDataPackLoader.registerPack(CristelLib.RUNTIME_PACK, Component.literal("Cristel Lib Config Pack"), () -> true);
   }
}
