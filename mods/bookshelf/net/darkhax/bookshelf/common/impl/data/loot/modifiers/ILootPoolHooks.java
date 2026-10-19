package net.darkhax.bookshelf.common.impl.data.loot.modifiers;

import org.jetbrains.annotations.Nullable;

public interface ILootPoolHooks {
   void bookshelf$setHash(int var1);

   @Nullable
   Integer bookshelf$getHash();

   default boolean bookshelf$matches(int toMatch) {
      Integer hash = this.bookshelf$getHash();
      return hash != null && hash == toMatch;
   }
}
