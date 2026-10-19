package xaero.map.mixin;

import net.minecraft.core.SectionPos;
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import xaero.map.core.IWorldMapSMultiBlockChangePacket;

@Mixin(ClientboundSectionBlocksUpdatePacket.class)
public class MixinChunkDeltaUpdateS2CPacketAccessor implements IWorldMapSMultiBlockChangePacket {
   @Shadow
   private SectionPos sectionPos;

   @Override
   public SectionPos xaero_wm_getSectionPos() {
      return this.sectionPos;
   }
}
