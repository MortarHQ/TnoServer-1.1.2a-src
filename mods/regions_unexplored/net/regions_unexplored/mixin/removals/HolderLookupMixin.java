package net.regions_unexplored.mixin.removals;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import java.util.stream.Stream;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.Registries;
import net.regions_unexplored.registry.tag.RUBiomeTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HolderLookup.class)
public interface HolderLookupMixin {
   @WrapOperation(
      method = "listElementIds()Ljava/util/stream/Stream;",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/core/HolderLookup;listElements()Ljava/util/stream/Stream;")
   )
   private <T> Stream<Reference<T>> hideRemovedBiomes(HolderLookup<T> $this, Operation<Stream<Reference<T>>> operation) {
      return ((Stream)operation.call(new Object[]{$this})).filter(holder -> !holder.key().isFor(Registries.BIOME) || !holder.is(RUBiomeTags.REMOVED));
   }
}
