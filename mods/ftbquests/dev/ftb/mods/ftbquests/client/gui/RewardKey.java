package dev.ftb.mods.ftbquests.client.gui;

import dev.ftb.mods.ftblibrary.icon.Icon;
import java.util.Objects;
import net.minecraft.world.item.ItemStack;

public class RewardKey {
   private final String title;
   private final Icon icon;
   private final ItemStack stack;
   private final boolean disableBlur;

   public RewardKey(String title, Icon icon, boolean disableBlur) {
      this(title, icon, ItemStack.EMPTY, disableBlur);
   }

   public RewardKey(String title, Icon icon, ItemStack stack, boolean disableBlur) {
      this.title = title;
      this.icon = icon;
      this.stack = stack;
      this.disableBlur = disableBlur;
   }

   public String getTitle() {
      return this.title;
   }

   public Icon getIcon() {
      return this.icon;
   }

   public boolean disableBlur() {
      return this.disableBlur;
   }

   @Override
   public int hashCode() {
      return this.stack.isEmpty() ? Objects.hash(this.title, this.icon) : ItemStack.hashItemAndComponents(this.stack);
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (o instanceof RewardKey key) {
         return !this.stack.isEmpty() ? ItemStack.isSameItemSameComponents(this.stack, key.stack) : this.title.equals(key.title) && this.icon.equals(key.icon);
      } else {
         return false;
      }
   }
}
