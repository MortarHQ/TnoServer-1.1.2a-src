package net.blay09.mods.netherportalfix;

import java.util.UUID;
import net.minecraft.core.BlockPos;

public class ReturnPortal {
   private final UUID uid;
   private final BlockPos pos;

   public ReturnPortal(UUID uid, BlockPos pos) {
      this.uid = uid;
      this.pos = pos;
   }

   public UUID getUid() {
      return this.uid;
   }

   public BlockPos getPos() {
      return this.pos;
   }
}
