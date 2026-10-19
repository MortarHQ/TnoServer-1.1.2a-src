package de.ellpeck.naturesaura.api.aura.container;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.ellpeck.naturesaura.api.aura.type.IAuraType;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;

public class ItemAuraContainer implements IAuraContainer {
   protected final ItemStack stack;
   protected final IAuraType type;
   protected final int maxAura;

   public ItemAuraContainer(ItemStack stack, IAuraType type, int maxAura) {
      this.stack = stack;
      this.type = type;
      this.maxAura = maxAura;
   }

   @Override
   public int storeAura(int amountToStore, boolean simulate) {
      int aura = this.getStoredAura();
      int actual = Math.min(amountToStore, this.getMaxAura() - aura);
      if (!simulate) {
         this.setAura(aura + actual);
      }

      return actual;
   }

   @Override
   public int drainAura(int amountToDrain, boolean simulate) {
      int aura = this.getStoredAura();
      int actual = Math.min(amountToDrain, aura);
      if (!simulate) {
         this.setAura(aura - actual);
      }

      return actual;
   }

   private void setAura(int amount) {
      this.stack.set(ItemAuraContainer.Data.TYPE, new ItemAuraContainer.Data(amount));
   }

   @Override
   public int getStoredAura() {
      return this.stack.has(ItemAuraContainer.Data.TYPE) ? ((ItemAuraContainer.Data)this.stack.get(ItemAuraContainer.Data.TYPE)).auraAmount : 0;
   }

   @Override
   public int getMaxAura() {
      return this.maxAura;
   }

   @Override
   public int getAuraColor() {
      return 4368060;
   }

   @Override
   public boolean isAcceptableType(IAuraType type) {
      return this.type == null || this.type == type;
   }

   public record Data(int auraAmount) {
      public static final Codec<ItemAuraContainer.Data> CODEC = RecordCodecBuilder.create(
         i -> i.group(Codec.INT.fieldOf("aura_amount").forGetter(d -> d.auraAmount)).apply(i, ItemAuraContainer.Data::new)
      );
      public static final DataComponentType<ItemAuraContainer.Data> TYPE = DataComponentType.builder().persistent(CODEC).cacheEncoding().build();
   }
}
