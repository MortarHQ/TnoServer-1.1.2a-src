package cn.star.toms_storage_star_optimized.mixin;

import cn.star.toms_storage_star_optimized.opt.INetworkDirtyReceiver;
import com.tom.storagemod.block.entity.IInventoryConnector;
import com.tom.storagemod.block.entity.InventoryConnectorBlockEntity;
import com.tom.storagemod.inventory.IInventoryAccess;
import com.tom.storagemod.inventory.MultiInventoryAccess;
import com.tom.storagemod.inventory.PlatformInventoryAccess.BlockInventoryAccess;
import com.tom.storagemod.util.BlockFace;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(InventoryConnectorBlockEntity.class)
public abstract class InventoryConnectorBlockEntityMixin implements INetworkDirtyReceiver {
   @Shadow
   private MultiInventoryAccess handler;
   @Shadow
   private Map<BlockFace, BlockInventoryAccess> invAccesses;
   @Shadow
   private Set<IInventoryAccess> connectedInvs;
   @Shadow
   private Set<IInventoryConnector> linkedConnectors;
   @Shadow
   private Set<BlockFace> interfaces;
   @Unique
   private static final int TSSO_FORCE_REBUILD_TICKS = 200;
   @Unique
   private boolean tsso$networkDirty = true;
   @Unique
   private boolean tsso$inventoryDirty = true;
   @Unique
   private boolean tsso$forceFullRebuild = true;
   @Unique
   private long tsso$lastRebuildTick = Long.MIN_VALUE;

   @Shadow
   private void detectTouchingInventories() {
   }

   @Shadow
   private void detectCableNetwork() {
   }

   @Override
   public void tsso$markDirtyAt(BlockPos pos, boolean requireFullRebuild) {
      this.tsso$networkDirty = true;
      this.tsso$inventoryDirty = true;
      this.tsso$forceFullRebuild |= requireFullRebuild;
   }

   @Overwrite
   public void updateServer() {
      InventoryConnectorBlockEntity self = (InventoryConnectorBlockEntity)this;
      Level level = self.getLevel();
      if (level != null) {
         long time = level.getGameTime();
         if (time % 20L == Math.abs(self.getBlockPos().hashCode()) % 20) {
            boolean forcePeriodic = time - this.tsso$lastRebuildTick >= 200L;
            if (this.tsso$networkDirty || this.tsso$inventoryDirty || forcePeriodic) {
               if (this.tsso$inventoryDirty || this.tsso$forceFullRebuild || forcePeriodic) {
                  this.detectTouchingInventories();
                  this.tsso$inventoryDirty = false;
               }

               if (this.tsso$networkDirty || this.tsso$forceFullRebuild || forcePeriodic) {
                  this.detectCableNetwork();
                  this.tsso$networkDirty = false;
               }

               this.handler.build(self, this.linkedConnectors);
               this.tsso$forceFullRebuild = false;
               this.tsso$lastRebuildTick = time;
            }
         }
      }
   }
}
