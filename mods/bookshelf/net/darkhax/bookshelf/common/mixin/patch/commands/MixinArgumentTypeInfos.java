package net.darkhax.bookshelf.common.mixin.patch.commands;

import com.mojang.brigadier.arguments.ArgumentType;
import net.darkhax.bookshelf.common.api.registry.register.ArgumentRegister;
import net.darkhax.bookshelf.common.api.service.Services;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.ArgumentTypeInfo.Template;
import net.minecraft.core.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArgumentTypeInfos.class)
public class MixinArgumentTypeInfos {
   @Inject(method = "bootstrap(Lnet/minecraft/core/Registry;)Lnet/minecraft/commands/synchronization/ArgumentTypeInfo;", at = @At("RETURN"))
   private static void bootstrap(Registry<ArgumentTypeInfo<?, ?>> registry, CallbackInfoReturnable<ArgumentTypeInfo<?, ?>> cir) {
      Services.CONTENT_PROVIDERS.get().forEach(provider -> {
         ArgumentRegister argRegister = new ArgumentRegister() {
            @Override
            public <A extends ArgumentType<?>, T extends Template<A>> void accept(String id, Class argumentClass, ArgumentTypeInfo<A, T> info) {
               MixinArgumentTypeInfos.register(registry, provider.contentNamespace() + ":" + id, argumentClass, info);
            }
         };
         provider.registerCommandArguments(argRegister);
      });
   }

   @Shadow
   private static <A extends ArgumentType<?>, T extends Template<A>> ArgumentTypeInfo<A, T> register(
      Registry<ArgumentTypeInfo<?, ?>> registry, String id, Class<? extends A> argumentClass, ArgumentTypeInfo<A, T> info
   ) {
      throw new IllegalStateException("Mixin failed to apply.");
   }
}
