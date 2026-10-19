package twilightforest.item;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import twilightforest.advancements.SimpleAdvancementTrigger;
import twilightforest.init.TFAdvancements;

public class HydraChopItem extends Item {
   public HydraChopItem(Properties properties) {
      super(properties);
   }

   public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity living) {
      if (living instanceof ServerPlayer player && player.getFoodData().getFoodLevel() <= 0) {
         ((SimpleAdvancementTrigger)TFAdvancements.CONSUME_HYDRA_CHOP.get()).trigger(player);
      }

      return super.finishUsingItem(stack, level, living);
   }
}
