package xaero.map.mixin;

import net.minecraft.world.level.storage.LevelStorageSource.LevelStorageAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaero.map.core.XaeroWorldMapCore;

@Mixin(LevelStorageAccess.class)
public class MixinLevelStorageAccess {
   @Inject(at = @At("RETURN"), method = "deleteLevel", cancellable = false)
   public void onDeleteLevel(CallbackInfo info) {
      XaeroWorldMapCore.onDeleteWorld((LevelStorageAccess)this);
   }
}
