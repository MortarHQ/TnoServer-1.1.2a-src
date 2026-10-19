package fzzyhmstrs.emi_loot.mixins;

import java.util.Optional;
import net.minecraft.server.network.Filterable;
import net.minecraft.world.item.component.WrittenBookContent;
import net.minecraft.world.level.storage.loot.functions.SetBookCoverFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SetBookCoverFunction.class)
public interface SetBookCoverLootFunctionAccessor {
   @Accessor
   Optional<String> getAuthor();

   @Accessor
   Optional<Filterable<String>> getTitle();

   @Accessor
   Optional<Integer> getGeneration();

   @Invoker
   WrittenBookContent callApply(WrittenBookContent var1);
}
