package xaero.map.mixin;

import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import xaero.map.mcworld.IWorldMapClientWorld;
import xaero.map.mcworld.WorldMapClientWorldData;

@Mixin(ClientLevel.class)
public class MixinClientWorld implements IWorldMapClientWorld {
   private WorldMapClientWorldData xaero_worldmapData;

   @Override
   public WorldMapClientWorldData getXaero_worldmapData() {
      return this.xaero_worldmapData;
   }

   @Override
   public void setXaero_worldmapData(WorldMapClientWorldData xaero_worldmapData) {
      this.xaero_worldmapData = xaero_worldmapData;
   }
}
