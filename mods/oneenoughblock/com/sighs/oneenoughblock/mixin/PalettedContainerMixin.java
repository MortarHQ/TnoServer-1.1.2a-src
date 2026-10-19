package com.sighs.oneenoughblock.mixin;

import com.sighs.oneenoughblock.api.IPalettedContainer;
import com.sighs.oneenoughblock.init.BlockReplacementCache;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.util.BitStorage;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.Palette;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.level.chunk.PalettedContainer.Data;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PalettedContainer.class)
@Implements(@Interface(iface = IPalettedContainer.class, prefix = "lazy$"))
public class PalettedContainerMixin implements IPalettedContainer {
   @Shadow
   private volatile Data<?> data;

   @Override
   public void handleReplace() {
      BitStorage storage = this.data.storage();
      Palette<BlockState> palette = this.data.palette();
      Map<Integer, Integer> idMap = new HashMap<>();

      for (int i = 0; i < palette.getSize(); i++) {
         BlockState current = (BlockState)palette.valueFor(i);
         BlockState replacement = this.resolveReplacement(current).orElse(null);
         if (replacement != null) {
            int targetId = palette.idFor(replacement);
            idMap.put(i, targetId);
         }
      }

      for (int i = 0; i < storage.getSize(); i++) {
         int oldId = storage.get(i);
         Integer newId = idMap.get(oldId);
         if (newId != null) {
            storage.set(i, newId);
         }
      }
   }

   private Optional<BlockState> resolveReplacement(BlockState state) {
      return BlockReplacementCache.resolveTarget(state.getBlock(), null).<BlockState>map(Block::defaultBlockState).filter(rs -> !state.is(rs.getBlock()));
   }
}
