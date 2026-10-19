package com.smoothchunk.mixin;

import com.smoothchunk.SmoothchunkMod;
import com.smoothchunk.config.CommonConfiguration;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.Queue;
import java.util.function.BooleanSupplier;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkMap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkMap.class)
public class ChunkMapSlowUnload {
   @Shadow
   @Final
   @Mutable
   public LongSet toDrop = new LongLinkedOpenHashSet();
   @Unique
   private int scheduledToUnloadAmount = 0;

   @ModifyConstant(method = "processUnloads", constant = @Constant(intValue = 200))
   private int setLimit(int constant) {
      return ((CommonConfiguration)SmoothchunkMod.config.getCommonConfig()).chunkUnloadLimit + this.toDrop.size() / 200;
   }

   @ModifyConstant(method = "processUnloads", constant = @Constant(intValue = 2000))
   private int dropUntil(int constant) {
      return 20000;
   }

   @Inject(method = "scheduleUnload", at = @At("HEAD"))
   private void onSchedule(long p_140182_, ChunkHolder p_140183_, CallbackInfo ci) {
      this.scheduledToUnloadAmount++;
   }

   @Inject(method = "processUnloads", at = @At(value = "INVOKE", target = "Ljava/lang/Runnable;run()V"))
   private void onRunUnload(BooleanSupplier p_140354_, CallbackInfo ci) {
      this.scheduledToUnloadAmount = Math.max(0, this.scheduledToUnloadAmount - 1);
   }

   @Redirect(method = "processUnloads", at = @At(value = "INVOKE", target = "Ljava/util/Queue;size()I"))
   private int getSize(Queue instance) {
      return this.scheduledToUnloadAmount;
   }
}
