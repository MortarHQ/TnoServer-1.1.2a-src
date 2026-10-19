package com.blorb.morerelics.helper;

import com.blorb.morerelics.MoreRelics;
import com.blorb.morerelics.relics.MoreRelicBase;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import java.util.Arrays;
import java.util.List;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CyberwareHelper {
   public static List<Item> cyberwareRelics = Arrays.asList(
      (Item)MoreRelics.BIONIC_EYE.get(), (Item)MoreRelics.THERMOSEISMIC_HEART.get(), (Item)MoreRelics.BIOJOINT.get(), (Item)MoreRelics.VERTEBRAX.get()
   );

   public static double[] GetCyberwareDominancy(Player player) {
      double cyberpsychosisChance = 0.0;
      double cyberpsychosisDuration = 0.0;

      for (Item relicItem : cyberwareRelics) {
         for (ItemStack stack : EntityUtils.findEquippedCurios(player, relicItem)) {
            MoreRelicBase item = (MoreRelicBase)stack.getItem();
            if (item.isAbilityUnlocked(stack, "cyberpsychosis")) {
               cyberpsychosisDuration += item.getStatValue(stack, "cyberpsychosis", "duration");
               cyberpsychosisChance += item.getStatValue(stack, "cyberpsychosis", "chance");
            }
         }
      }

      return new double[]{cyberpsychosisChance, cyberpsychosisDuration};
   }
}
