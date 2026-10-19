package com.tensura_tno.mixin.client;

import com.tensura_tno.compat.oneenough.OneEnoughBlockCompat;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "com.sighs.oneenoughblock.init.BlockReplacementCache", remap = false)
public abstract class OneEnoughBlockReplacementCacheMixin {
   @Shadow
   @Final
   private static ConcurrentHashMap<String, String> BlockMapCache;
   @Shadow
   @Final
   private static ConcurrentHashMap<String, String> TagMapCache;
   @Shadow
   private static volatile Map<String, String> ReloadOverrideBlockMap;

   @Inject(
      method = "resolveTarget(Lnet/minecraft/world/level/block/Block;Lnet/minecraft/core/HolderLookup$RegistryLookup;)Ljava/util/Optional;",
      at = @At("HEAD"),
      cancellable = true,
      require = 0
   )
   private static void tensuraTno$resolveSafely(Block source, RegistryLookup<Block> lookup, CallbackInfoReturnable<Optional<Block>> cir) {
      cir.setReturnValue(OneEnoughBlockCompat.CACHE.resolve(source, lookup));
   }

   @Inject(method = "resolveTarget(Lnet/minecraft/world/level/block/Block;)Ljava/util/Optional;", at = @At("HEAD"), cancellable = true, require = 0)
   private static void tensuraTno$resolveDirectSafely(Block source, CallbackInfoReturnable<Optional<Block>> cir) {
      cir.setReturnValue(OneEnoughBlockCompat.CACHE.resolveDirect(source));
   }

   @Inject(method = "resolveTargetByTags", at = @At("HEAD"), cancellable = true, require = 0)
   private static void tensuraTno$resolveTagsSafely(Block source, RegistryLookup<Block> lookup, CallbackInfoReturnable<Optional<Block>> cir) {
      cir.setReturnValue(OneEnoughBlockCompat.CACHE.resolveByTags(source, lookup));
   }

   @Inject(
      method = {
            "<clinit>", "clearCache", "putBlockMapping", "putTagMapping", "putReplacement", "removeReplacements", "beginReloadOverride", "endReloadOverride"
      },
      at = @At("RETURN"),
      require = 0
   )
   private static void tensuraTno$publishRules(CallbackInfo ci) {
      synchronized (OneEnoughBlockCompat.CACHE) {
         OneEnoughBlockCompat.CACHE.replaceRules(ReloadOverrideBlockMap != null ? ReloadOverrideBlockMap : BlockMapCache, TagMapCache);
      }
   }

   @Inject(method = {"removeBlockReplacement", "removeTagReplacement"}, at = @At("RETURN"), require = 0)
   private static void tensuraTno$publishRemovedRules(CallbackInfoReturnable<Boolean> cir) {
      if (cir.getReturnValueZ()) {
         synchronized (OneEnoughBlockCompat.CACHE) {
            OneEnoughBlockCompat.CACHE.replaceRules(ReloadOverrideBlockMap != null ? ReloadOverrideBlockMap : BlockMapCache, TagMapCache);
         }
      }
   }
}
