package cn.leolezury.eternalstarlight.common.item.misc;

import cn.leolezury.eternalstarlight.common.spell.ManaType;
import cn.leolezury.eternalstarlight.common.util.Color;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;

public class ManaCrystalItem extends Item {
   private final ManaType manaType;

   public ManaCrystalItem(ManaType type, Properties properties) {
      super(properties);
      this.manaType = type;
   }

   public ManaType getManaType() {
      return this.manaType;
   }

   public int getBarColor(ItemStack itemStack) {
      float f = Math.max(0.0F, ((float)itemStack.getMaxDamage() - itemStack.getDamageValue()) / itemStack.getMaxDamage());
      float r = (this.manaType.getColor() >> 16 & 0xFF) / 255.0F;
      float g = (this.manaType.getColor() >> 8 & 0xFF) / 255.0F;
      float b = (this.manaType.getColor() & 0xFF) / 255.0F;
      return new Color(r * f, g * f, b * f, 0.0).rgb();
   }
}
