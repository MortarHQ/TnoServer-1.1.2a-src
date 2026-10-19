package com.teamresourceful.resourcefullib.common.nbt.validators;

import it.unimi.dsi.fastutil.bytes.BytePredicate;
import java.util.function.Predicate;
import net.minecraft.nbt.Tag;

public record TagValidationType(BytePredicate type) implements Predicate<Tag> {
   public static final TagValidationType STRING = new TagValidationType(type -> 8 == type);
   public static final TagValidationType LIST = new TagValidationType(type -> 9 == type || 7 == type || 11 == type || 12 == type);
   public static final TagValidationType COMPOUND = new TagValidationType(type -> 10 == type);
   public static final TagValidationType NUMERIC = new TagValidationType(type -> 1 == type || 2 == type || 3 == type || 4 == type || 5 == type || 6 == type);

   public boolean test(Tag t) {
      return this.type.test(t.getId());
   }
}
