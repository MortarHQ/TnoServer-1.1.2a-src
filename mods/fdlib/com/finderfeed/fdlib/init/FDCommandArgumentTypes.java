package com.finderfeed.fdlib.init;

import com.finderfeed.fdlib.commands.AnimationArgument;
import java.util.function.Supplier;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FDCommandArgumentTypes {
   public static final DeferredRegister<ArgumentTypeInfo<?, ?>> ARGUMENT_TYPES = DeferredRegister.create(BuiltInRegistries.COMMAND_ARGUMENT_TYPE, "fdlib");
   public static final Supplier<ArgumentTypeInfo<?, ?>> ANIMATION_ARGUMENT = ARGUMENT_TYPES.register(
      "animation_argument", () -> ArgumentTypeInfos.registerByClass(AnimationArgument.class, SingletonArgumentInfo.contextFree(AnimationArgument::new))
   );
}
