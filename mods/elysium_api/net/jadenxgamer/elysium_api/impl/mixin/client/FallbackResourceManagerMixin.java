package net.jadenxgamer.elysium_api.impl.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import java.io.InputStream;
import net.jadenxgamer.elysium_api.impl.client.conditional_resource.ConditionalAssetsHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PackResources.ResourceOutput;
import net.minecraft.server.packs.resources.FallbackResourceManager;
import net.minecraft.server.packs.resources.IoSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FallbackResourceManager.class)
public abstract class FallbackResourceManagerMixin {
   @WrapOperation(
      method = "getResource",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/server/packs/PackResources;getResource(Lnet/minecraft/server/packs/PackType;Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/server/packs/resources/IoSupplier;"
      )
   )
   private IoSupplier<InputStream> elysium_api$wrapGetResource(
      PackResources pack, PackType packType, ResourceLocation location, Operation<IoSupplier<InputStream>> original
   ) {
      return ConditionalAssetsHelper.isAssetHidden(pack, packType, location) ? null : (IoSupplier)original.call(new Object[]{pack, packType, location});
   }

   @WrapOperation(
      method = "getResourceStack",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/server/packs/PackResources;getResource(Lnet/minecraft/server/packs/PackType;Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/server/packs/resources/IoSupplier;"
      )
   )
   private IoSupplier<InputStream> elysium_api$wrapGetResourceForStack(
      PackResources pack, PackType packType, ResourceLocation location, Operation<IoSupplier<InputStream>> original
   ) {
      return ConditionalAssetsHelper.isAssetHidden(pack, packType, location) ? null : (IoSupplier)original.call(new Object[]{pack, packType, location});
   }

   @WrapOperation(
      method = "listResources",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/server/packs/PackResources;listResources(Lnet/minecraft/server/packs/PackType;Ljava/lang/String;Ljava/lang/String;Lnet/minecraft/server/packs/PackResources$ResourceOutput;)V"
      )
   )
   private void elysium_api$wrapListResources(
      PackResources pack, PackType packType, String namespace, String path, ResourceOutput originalOutput, Operation<Void> original
   ) {
      ResourceOutput filteredOutput = (location, supplier) -> {
         if (!ConditionalAssetsHelper.isAssetHidden(pack, packType, location)) {
            originalOutput.accept(location, supplier);
         }
      };
      original.call(new Object[]{pack, packType, namespace, path, filteredOutput});
   }

   @WrapOperation(
      method = "listPackResources",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/server/packs/PackResources;listResources(Lnet/minecraft/server/packs/PackType;Ljava/lang/String;Ljava/lang/String;Lnet/minecraft/server/packs/PackResources$ResourceOutput;)V"
      )
   )
   private void elysium_api$wrapListPackResources(
      PackResources pack, PackType packType, String namespace, String path, ResourceOutput originalOutput, Operation<Void> original
   ) {
      ResourceOutput filteredOutput = (location, supplier) -> {
         if (!ConditionalAssetsHelper.isAssetHidden(pack, packType, location)) {
            originalOutput.accept(location, supplier);
         }
      };
      original.call(new Object[]{pack, packType, namespace, path, filteredOutput});
   }
}
