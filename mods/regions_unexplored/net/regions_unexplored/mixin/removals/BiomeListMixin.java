package net.regions_unexplored.mixin.removals;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import java.util.stream.Stream;
import net.minecraft.client.gui.screens.CreateBuffetWorldScreen.BiomeList;
import net.minecraft.core.Registry;
import net.minecraft.core.Holder.Reference;
import net.minecraft.world.level.biome.Biome;
import net.regions_unexplored.registry.tag.RUBiomeTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BiomeList.class)
public class BiomeListMixin {
   @WrapOperation(
      method = "<init>(Lnet/minecraft/client/gui/screens/CreateBuffetWorldScreen;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Registry;holders()Ljava/util/stream/Stream;")
   )
   private Stream<Reference<Biome>> hideRemovedBiomes(Registry<Biome> registry, Operation<Stream<Reference<Biome>>> operation) {
      return ((Stream)operation.call(new Object[]{registry})).filter(holder -> !holder.is(RUBiomeTags.REMOVED));
   }
}
