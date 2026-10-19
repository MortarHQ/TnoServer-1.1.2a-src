package com.moepus.byepregen.mixin.compat;

import com.moepus.byepregen.MixinGate;
import com.moepus.byepregen.gcfree.ChunkSaveHookGate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@MixinGate(requiredMods = "c2me_base")
@Mixin(targets = "com.ishland.c2me.base.common.util.HookCompatibility", remap = false)
public abstract class C2MEHookCompatibilityMixin {
   @Overwrite
   public static boolean isChunkSaveEventFree() {
      return ChunkSaveHookGate.CAN_USE_RAW_SAVE;
   }
}
