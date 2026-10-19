package de.cristelknight.cristellib.config.client;

import de.cristelknight.cristellib.StructureConfig;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public record ClientStructureConfig(
   StructureConfig structureConfig, Map<ResourceLocation, ClientPlacementConfig> clientPlacementConfigs, Map<ResourceLocation, ClientEDConfig> clientEDConfigs
) {
}
