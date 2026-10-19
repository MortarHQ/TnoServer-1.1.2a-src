package com.moepus.byepregen.mixin.accessor;

import java.util.List;
import java.util.Map;
import net.minecraft.util.ClassInstanceMultiMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ClassInstanceMultiMap.class, remap = false)
public interface ClassInstanceMultiMapAccessor<T> {
   @Accessor("byClass")
   Map<Class<?>, List<T>> byepregen$getByClass();
}
