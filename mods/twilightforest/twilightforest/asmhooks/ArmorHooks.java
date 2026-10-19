package twilightforest.asmhooks;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import twilightforest.beanification.Autowired;
import twilightforest.init.TFDataComponents;
import twilightforest.util.ArmorUtil;

public class ArmorHooks {
   @Autowired
   private static ArmorUtil armorUtil;

   public static float modifyArmorVisibility(float o, LivingEntity entity) {
      return o - armorUtil.getShroudedArmorPercentage(entity);
   }

   public static boolean cancelArmorRendering(boolean o, ItemStack stack) {
      return o && stack.has(TFDataComponents.EMPERORS_CLOTH) ? false : o;
   }

   public static boolean fixCapeRendering(boolean o, ItemStack stack) {
      return o && !stack.has(TFDataComponents.EMPERORS_CLOTH);
   }
}
