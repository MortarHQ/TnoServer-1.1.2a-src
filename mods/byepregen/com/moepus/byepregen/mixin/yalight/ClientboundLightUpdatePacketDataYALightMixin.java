package com.moepus.byepregen.mixin.yalight;

import com.moepus.byepregen.yalight.YALightEngineHolder;
import com.moepus.byepregen.yalight.YANibbleArray;
import java.util.BitSet;
import java.util.List;
import net.minecraft.core.SectionPos;
import net.minecraft.network.protocol.game.ClientboundLightUpdatePacketData;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.lighting.LevelLightEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ClientboundLightUpdatePacketData.class)
public abstract class ClientboundLightUpdatePacketDataYALightMixin {
   @Overwrite
   private void prepareSectionData(
      ChunkPos chunkPos, LevelLightEngine lightEngine, LightLayer layer, int sectionIndex, BitSet yMask, BitSet emptyYMask, List<byte[]> updates
   ) {
      SectionPos sectionPos = SectionPos.of(chunkPos, lightEngine.getMinLightSection() + sectionIndex);
      YANibbleArray nibble = ((YALightEngineHolder)lightEngine).byepregen$getYALightEngine().getVisibleSection(layer, sectionPos);
      if (nibble != null && nibble.hasVisibleLayer()) {
         int kind = nibble.visibleSaveKind();
         if (kind == 0) {
            emptyYMask.set(sectionIndex);
         } else {
            yMask.set(sectionIndex);
            updates.add(kind == 1 ? YANibbleArray.FULL_LIGHT_DATA : (byte[])nibble.visibleDataForSave().clone());
         }
      }
   }
}
