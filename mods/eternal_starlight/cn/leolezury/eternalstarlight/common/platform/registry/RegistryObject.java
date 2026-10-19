package cn.leolezury.eternalstarlight.common.platform.registry;

import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public interface RegistryObject<R, T extends R> extends Supplier<T> {
   Holder<R> asHolder();

   ResourceKey<T> getResourceKey();

   ResourceLocation getId();

   @Override
   T get();
}
