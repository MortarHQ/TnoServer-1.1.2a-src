package net.darkhax.bookshelf.common.api.registry.register;

import com.mojang.brigadier.arguments.ArgumentType;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfo.Template;

public interface ArgumentRegister {
   <A extends ArgumentType<?>, T extends Template<A>> void accept(String var1, Class var2, ArgumentTypeInfo<A, T> var3);
}
