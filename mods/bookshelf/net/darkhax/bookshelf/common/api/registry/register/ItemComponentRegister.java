package net.darkhax.bookshelf.common.api.registry.register;

import java.util.function.UnaryOperator;
import net.minecraft.core.component.DataComponentType.Builder;
import net.minecraft.resources.ResourceLocation;

public interface ItemComponentRegister {
   <T> void accept(String var1, UnaryOperator<Builder<T>> var2);

   <T> void accept(ResourceLocation var1, UnaryOperator<Builder<T>> var2);
}
