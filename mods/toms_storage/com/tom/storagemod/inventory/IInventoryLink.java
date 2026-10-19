package com.tom.storagemod.inventory;

import com.tom.storagemod.block.entity.IInventoryConnector;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public interface IInventoryLink {
   IInventoryConnector getConnector();

   boolean isAccessibleFrom(ServerLevel var1, BlockPos var2, int var3);

   UUID getChannel();
}
