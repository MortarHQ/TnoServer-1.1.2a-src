package net.unusual.block_factorys_bosses.item;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.unusual.block_factorys_bosses.client.AnimationTickHolder;

public abstract class AnimatedSwordItem extends SwordItem {
   public static final Map<String, AnimationState> IDLE_STATES = new WeakHashMap<>();
   public static final Map<String, AnimationState> SWING_STATES = new WeakHashMap<>();
   public static final Map<ItemStack, Integer> IS_MINING = new WeakHashMap<>();

   public AnimatedSwordItem(Tier tier, Properties properties) {
      super(tier, properties);
   }

   public abstract int getAttackDelay();

   public abstract SoundEvent getSwingSound();

   public boolean isDualHanded() {
      return false;
   }

   public void onEntitySwingHook(Entity entity, ItemStack stack) {
   }

   protected static int getIsMining(ItemStack stack) {
      return IS_MINING.computeIfAbsent(stack, s -> 0);
   }

   protected static String getItemKey(ItemStack stack) {
      return ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag().getString("itemKey");
   }

   protected static void randomizeItemKey(ItemStack stack) {
      CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putString("itemKey", UUID.randomUUID().toString()));
   }

   protected static double getSwingAnimtime(ItemStack stack) {
      return ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag().getDouble("swing_animtime");
   }

   protected static void setSwingAnimtime(ItemStack stack, double swingAnimtime) {
      CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putDouble("swing_animtime", swingAnimtime));
   }

   public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
      super.inventoryTick(stack, level, entity, slotId, isSelected);
      if (getItemKey(stack).isEmpty()) {
         randomizeItemKey(stack);
      }

      int is_mining = getIsMining(stack);
      double swing_animtime = getSwingAnimtime(stack);
      if (is_mining > 0) {
         IS_MINING.put(stack, is_mining - 1);
      }

      if (swing_animtime > 0.0 && !level.isClientSide()) {
         setSwingAnimtime(stack, swing_animtime - 1.0);
      }
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return false;
   }

   public float getDestroySpeed(ItemStack stack, BlockState state) {
      IS_MINING.put(stack, 5);
      return getSwingAnimtime(stack) == 0.0 ? 1.0F : 0.0F;
   }

   public boolean onEntitySwing(ItemStack stack, LivingEntity entity, InteractionHand hand) {
      boolean retval = super.onEntitySwing(stack, entity, hand);
      if (getIsMining(stack) != 0 && !(entity instanceof Player)) {
         return retval;
      }

      if (getSwingAnimtime(stack) == 0.0) {
         this.playSwingAnimation(entity, stack);
      } else if (this.isDualHanded()) {
         ItemStack offhand = entity.getOffhandItem();
         if (offhand.getItem() == stack.getItem() && getSwingAnimtime(offhand) == 0.0) {
            this.playSwingAnimation(entity, offhand);
         }
      }

      return retval;
   }

   private void playSwingAnimation(Entity entity, ItemStack stack) {
      if (!entity.level().isClientSide()) {
         randomizeItemKey(stack);
         setSwingAnimtime(stack, this.getAttackDelay());
      }

      entity.playSound(this.getSwingSound(), 1.0F, 1.0F);
      this.onEntitySwingHook(entity, stack);
      if (entity.level().isClientSide()) {
         SWING_STATES.computeIfAbsent(getItemKey(stack), s -> new AnimationState()).start(Math.round(AnimationTickHolder.getRenderTime()));
      }
   }

   protected static class AnimatedSwordItemExtension implements IClientItemExtensions {
      @OnlyIn(Dist.CLIENT)
      public boolean applyForgeHandTransform(
         PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess
      ) {
         ItemStack stack = player.getMainHandItem();
         if (AnimatedSwordItem.getIsMining(stack) == 0) {
            int i = arm == HumanoidArm.RIGHT ? 1 : -1;
            poseStack.translate(i * 0.56F, -0.52F, -0.72F);
            if (player.getUseItem() == itemInHand) {
               poseStack.translate(0.05, 0.05, 0.05);
            }

            return true;
         } else {
            return false;
         }
      }
   }
}
