package fr.madu59.fism.compat.entityculling;

import com.logisticscraft.occlusionculling.DataProvider;
import net.minecraft.client.GraphicsStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

public class Provider implements DataProvider {
   private final Minecraft client = Minecraft.getInstance();
   private ClientLevel world = null;

   public boolean prepareChunk(int chunkX, int chunkZ) {
      this.world = this.client.level;
      return this.world != null;
   }

   public boolean isOpaqueFullCube(int x, int y, int z) {
      BlockPos pos = new BlockPos(x, y, z);
      BlockState state = this.world.getBlockState(pos);
      return Minecraft.getInstance().options.graphicsMode().get() == GraphicsStatus.FAST && state.getBlock() instanceof LeavesBlock
         ? true
         : state.isSolidRender(Minecraft.getInstance().level, pos);
   }

   public void cleanup() {
      this.world = null;
   }
}
