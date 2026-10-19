package com.blamejared.crafttweaker.api.villager;

import com.blamejared.crafttweaker.api.item.IItemStack;

public class CTTradeObject {
   private final IItemStack buyingStack;
   private final IItemStack buyingStackSecond;
   private final IItemStack sellingStack;

   public CTTradeObject(IItemStack buyingStack, IItemStack buyingStackSecond, IItemStack sellingStack) {
      this.buyingStack = buyingStack;
      this.buyingStackSecond = buyingStackSecond;
      this.sellingStack = sellingStack;
   }

   public IItemStack getBuyingStack() {
      return this.buyingStack;
   }

   public IItemStack getBuyingStackSecond() {
      return this.buyingStackSecond;
   }

   public IItemStack getSellingStack() {
      return this.sellingStack;
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder("{");
      sb.append("\"buyingStack\": \"").append(this.buyingStack.getCommandString());
      sb.append("\", \"buyingStackSecond\": \"").append(this.buyingStackSecond.getCommandString());
      sb.append("\", \"sellingStack\": \"").append(this.sellingStack.getCommandString());
      sb.append("\"}");
      return sb.toString();
   }
}
