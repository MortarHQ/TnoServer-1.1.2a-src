package net.blay09.mods.waystones.api;

import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public interface MutableWaystone {
   void setName(Component var1);

   void setVisibility(WaystoneVisibility var1);

   void setDimension(ResourceKey<Level> var1);

   void setPos(BlockPos var1);

   void setOwnerUid(UUID var1);

   void setTransient(boolean var1);
}
