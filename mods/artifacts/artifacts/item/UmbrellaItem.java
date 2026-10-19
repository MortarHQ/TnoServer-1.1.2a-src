package artifacts.item;

import artifacts.Artifacts;
import artifacts.equipment.EquipmentHelper;
import artifacts.registry.ModDataComponents;
import java.util.List;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class UmbrellaItem extends ArtifactItem {
   public UmbrellaItem() {
      super(new Properties());
      DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
   }

   @Override
   public boolean isCosmetic() {
      return !Artifacts.CONFIG.items.umbrellaIsGlider.get() && !Artifacts.CONFIG.items.umbrellaIsShield.get();
   }

   @Override
   protected void addEffectsTooltip(List<MutableComponent> tooltip) {
      if (Artifacts.CONFIG.items.umbrellaIsGlider.get()) {
         tooltip.add(this.tooltipLine("glider"));
      }

      if (Artifacts.CONFIG.items.umbrellaIsShield.get()) {
         tooltip.add(this.tooltipLine("shield"));
      }
   }

   public UseAnim getUseAnimation(ItemStack stack) {
      return UseAnim.BLOCK;
   }

   public int getUseDuration(ItemStack itemStack, LivingEntity livingEntity) {
      return 72000;
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      if (!Artifacts.CONFIG.items.umbrellaIsShield.get()) {
         return super.use(level, player, hand);
      }

      player.startUsingItem(hand);
      return InteractionResultHolder.consume(stack);
   }

   public static void onLivingUpdate(LivingEntity entity) {
      if (shouldGlide(entity)) {
         entity.fallDistance = 0.0F;
      }
   }

   public static boolean shouldGlide(LivingEntity entity) {
      return !entity.onGround()
         && entity.getDeltaMovement().y < 0.0
         && !entity.hasEffect(MobEffects.SLOW_FALLING)
         && Artifacts.CONFIG.items.umbrellaIsGlider.get()
         && (!entity.isInWater() || EquipmentHelper.hasAbilityActive(ModDataComponents.SINKING.get(), entity, true))
         && isHoldingUmbrellaUpright(entity);
   }

   public static boolean isHoldingUmbrellaUpright(LivingEntity entity, InteractionHand hand) {
      return entity.getItemInHand(hand).getItem() instanceof UmbrellaItem && (!entity.isUsingItem() || entity.getUsedItemHand() != hand);
   }

   public static boolean isHoldingUmbrellaUpright(LivingEntity entity) {
      return isHoldingUmbrellaUpright(entity, InteractionHand.MAIN_HAND) || isHoldingUmbrellaUpright(entity, InteractionHand.OFF_HAND);
   }

   public static boolean isHoldingUmbrellaUpright(Entity entity) {
      return entity instanceof LivingEntity livingEntity && isHoldingUmbrellaUpright(livingEntity);
   }
}
