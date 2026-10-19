package dev.ftb.mods.ftbchunks.api.client.minimap;

import dev.ftb.mods.ftbchunks.client.map.MapDimension;
import dev.ftb.mods.ftblibrary.math.XZ;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec3;

public record MinimapContext(
   Minecraft minecraft, LocalPlayer player, MapDimension mapDimension, XZ mapChunksPos, Vec3 playerPos, Map<String, String> infoSettings
) {
   public String getSetting(MinimapInfoComponent infoComponent) {
      return this.infoSettings.getOrDefault(infoComponent.id().toString(), "");
   }
}
