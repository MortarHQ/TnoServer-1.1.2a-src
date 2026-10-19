package com.bobmowzie.mowziesmobs.server.item;

import com.bobmowzie.mowziesmobs.client.render.item.RenderSculptorStaff;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import java.util.List;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ItemSculptorStaff extends DiggerItem implements GeoItem {
   public static final String CONTROLLER_NAME = "controller";
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private static final RawAnimation DISAPPEAR_ANIM = RawAnimation.begin().thenPlayAndHold("disappear");
   public static final String DISAPPEAR_ANIM_NAME = "disappear";

   public ItemSculptorStaff(Properties properties) {
      super(Tiers.STONE, BlockTags.MINEABLE_WITH_HOE, properties);
      SingletonGeoAnimatable.registerSyncedAnimatable(this);
   }

   public boolean isValidRepairItem(ItemStack tool, ItemStack ingredient) {
      return ingredient.is((Item)ItemHandler.BLUFF_ROD.get());
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      AbilityHandler.INSTANCE.sendAbilityMessage(player, AbilityHandler.ROCK_SLING);
      player.startUsingItem(hand);
      if (!player.getAbilities().instabuild) {
         player.getItemInHand(hand).hurtAndBreak(2, player, LivingEntity.getSlotForHand(hand));
      }

      return new InteractionResultHolder(InteractionResult.SUCCESS, player.getItemInHand(hand));
   }

   public int getUseDuration(ItemStack stack, LivingEntity entity) {
      return 72000;
   }

   public boolean isEnchantable(ItemStack stack) {
      return false;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
      super.appendHoverText(stack, context, tooltip, flagIn);
      tooltip.add(Component.translatable(this.getDescriptionId() + ".text.0").setStyle(ItemHandler.TOOLTIP_STYLE));
      tooltip.add(Component.translatable(this.getDescriptionId() + ".text.1").setStyle(ItemHandler.TOOLTIP_STYLE));
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "controller", 0, state -> PlayState.STOP).triggerableAnim("disappear", DISAPPEAR_ANIM));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public static class ClientExtensions implements IClientItemExtensions {
      private final BlockEntityWithoutLevelRenderer renderer = new RenderSculptorStaff();

      public BlockEntityWithoutLevelRenderer getCustomRenderer() {
         return this.renderer;
      }
   }
}
