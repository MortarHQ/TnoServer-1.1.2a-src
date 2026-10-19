package dev.ftb.mods.ftbxmodcompat.neoforge.ftbchunks.kubejs;

import dev.ftb.mods.ftbchunks.api.ClaimedChunk;
import dev.latvian.mods.kubejs.entity.KubeEntityEvent;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public class AfterEventJS implements KubeEntityEvent {
   public final CommandSourceStack source;
   public final ClaimedChunk chunk;

   public AfterEventJS(CommandSourceStack s, ClaimedChunk c) {
      this.source = s;
      this.chunk = c;
   }

   @Nullable
   public Entity getEntity() {
      return this.source.getEntity();
   }

   public BlockPos getClaimPos() {
      return this.chunk.getPos().chunkPos().getWorldPosition();
   }
}
