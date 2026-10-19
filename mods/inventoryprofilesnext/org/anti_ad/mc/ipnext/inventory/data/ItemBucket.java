package org.anti_ad.mc.ipnext.inventory.data;

import java.util.List;
import org.anti_ad.mc.ipnext.item.ItemStack;
import org.anti_ad.mc.ipnext.util.Bucket;
import org.jetbrains.annotations.NotNull;

public interface ItemBucket extends Bucket {
   @NotNull
   ItemBucket$Companion Companion = ItemBucket$Companion.$$INSTANCE;

   boolean contains(@NotNull ItemStack var1);

   boolean containsAll(@NotNull List var1);

   @NotNull
   ItemBucket copy();

   @NotNull
   MutableItemBucket copyAsMutable();

   @NotNull
   ItemBucket minus(@NotNull ItemBucket var1);
}
