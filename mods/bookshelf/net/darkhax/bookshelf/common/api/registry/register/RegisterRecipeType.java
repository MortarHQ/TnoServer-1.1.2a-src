package net.darkhax.bookshelf.common.api.registry.register;

import java.util.function.Consumer;
import net.minecraft.resources.ResourceLocation;

public record RegisterRecipeType(String owner, Consumer<ResourceLocation> registerFunc) {
   public void add(String path) {
      this.add(ResourceLocation.fromNamespaceAndPath(this.owner, path));
   }

   public void add(ResourceLocation id) {
      this.registerFunc.accept(id);
   }
}
