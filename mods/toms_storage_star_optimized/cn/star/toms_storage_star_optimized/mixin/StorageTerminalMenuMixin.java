package cn.star.toms_storage_star_optimized.mixin;

import cn.star.toms_storage_star_optimized.api.TerminalDirtyState;
import com.tom.storagemod.block.entity.StorageTerminalBlockEntity;
import com.tom.storagemod.menu.StorageTerminalMenu;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StorageTerminalMenu.class)
public abstract class StorageTerminalMenuMixin {
   @Shadow
   protected StorageTerminalBlockEntity te;
   @Shadow
   protected Inventory pinv;
   @Shadow
   public String search;
   @Unique
   private static final int TSSO_ACTIVE_SYNC_INTERVAL = 2;
   @Unique
   private static final int TSSO_IDLE_SYNC_INTERVAL = 8;
   @Unique
   private long tsso$nextSyncTick;
   @Unique
   private boolean tsso$firstBroadcast = true;

   @Inject(method = "broadcastChanges()V", at = @At("HEAD"), cancellable = true, remap = false, require = 0)
   private void tsso$throttleBroadcast(CallbackInfo ci) {
      if (this.te != null && this.pinv.player instanceof ServerPlayer) {
         long time = this.pinv.player.level().getGameTime();
         boolean searchChanged = !this.te.getLastSearch().equals(this.search);
         boolean terminalDirty = this.te instanceof TerminalDirtyState dirty && dirty.tsso$hasPendingSync();
         if (this.tsso$firstBroadcast) {
            this.tsso$firstBroadcast = false;
            this.tsso$nextSyncTick = time + 1L;
         } else if (!searchChanged && !terminalDirty && time < this.tsso$nextSyncTick) {
            ci.cancel();
         } else {
            this.tsso$nextSyncTick = time + (!searchChanged && !terminalDirty ? 8 : 2);
         }
      }
   }
}
