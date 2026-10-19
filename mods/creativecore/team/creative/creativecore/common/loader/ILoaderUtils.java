package team.creative.creativecore.common.loader;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface ILoaderUtils {
   int getLifeSpan(ItemEntity var1);

   int fireItemPickupPre(ItemEntity var1, Player var2);

   void fireItemPickupPost(ItemEntity var1, Player var2, ItemStack var3);
}
