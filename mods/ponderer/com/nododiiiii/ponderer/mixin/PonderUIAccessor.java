package com.nododiiiii.ponderer.mixin;

import java.util.List;
import javax.annotation.Nullable;
import net.createmod.catnip.animation.LerpedFloat;
import net.createmod.ponder.foundation.PonderScene;
import net.createmod.ponder.foundation.ui.PonderUI;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PonderUI.class)
public interface PonderUIAccessor {
   @Accessor(value = "hoveredBlockPos", remap = false)
   @Nullable
   BlockPos ponderer$getHoveredBlockPos();

   @Accessor(value = "identifyMode", remap = false)
   boolean ponderer$getIdentifyMode();

   @Accessor(value = "identifyMode", remap = false)
   void ponderer$setIdentifyMode(boolean var1);

   @Accessor(value = "scenes", remap = false)
   List<PonderScene> ponderer$getScenes();

   @Accessor(value = "index", remap = false)
   int ponderer$getIndex();

   @Accessor(value = "index", remap = false)
   void ponderer$setIndex(int var1);

   @Accessor(value = "lazyIndex", remap = false)
   LerpedFloat ponderer$getLazyIndex();
}
