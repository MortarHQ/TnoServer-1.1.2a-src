package artifacts.item;

import java.util.function.Supplier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class EverlastingFoodItem extends ArtifactItem {
   private final Supplier<Integer> eatingCooldown;
   private final Supplier<Boolean> isEnabled;

   public EverlastingFoodItem(FoodProperties food, Supplier<Integer> eatingCooldown, Supplier<Boolean> isEnabled) {
      super(new Properties().food(food));
      this.eatingCooldown = eatingCooldown;
      this.isEnabled = isEnabled;
   }

   @Override
   public boolean isCosmetic() {
      return !this.isEnabled.get();
   }

   @Override
   protected String getTooltipItemName() {
      return "everlasting_food";
   }

   public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
      if (stack.has(DataComponents.FOOD)) {
         entity.eat(world, stack.copy());
         if (this.eatingCooldown.get() > 0 && !entity.level().isClientSide() && entity instanceof Player player) {
            player.getCooldowns().addCooldown(this, this.eatingCooldown.get() * 20);
         }
      }

      return stack;
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      return !this.isEnabled.get() ? InteractionResultHolder.pass(player.getItemInHand(hand)) : super.use(level, player, hand);
   }
}
