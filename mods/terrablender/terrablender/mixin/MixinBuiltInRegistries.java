package terrablender.mixin;

import com.mojang.serialization.Lifecycle;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries.RegistryBootstrap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terrablender.worldgen.surface.NamespacedSurfaceRuleSource;

@Mixin(BuiltInRegistries.class)
public abstract class MixinBuiltInRegistries {
   @Shadow
   private static <T, R extends WritableRegistry<T>> R internalRegister(ResourceKey<? extends Registry<T>> $$0, R $$1, RegistryBootstrap<T> $$2) {
      return null;
   }

   @Inject(
      method = "registerSimple(Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/core/registries/BuiltInRegistries$RegistryBootstrap;)Lnet/minecraft/core/Registry;",
      at = @At("HEAD"),
      cancellable = true
   )
   private static void registerSimple(ResourceKey key, RegistryBootstrap bootstrap, CallbackInfoReturnable<Registry> cir) {
      if (key == Registries.MATERIAL_RULE) {
         cir.setReturnValue(internalRegister(key, new MappedRegistry(key, Lifecycle.stable(), false), registry -> {
            bootstrap.run(registry);
            return Registry.register(registry, ResourceLocation.fromNamespaceAndPath("terrablender", "merged"), NamespacedSurfaceRuleSource.CODEC.codec());
         }));
      }
   }
}
