package com.github.b4ndithelps.tennogamenolife.item;

import com.github.b4ndithelps.tennogamenolife.client.renderer.SickleRenderer;
import io.github.manasmods.tensura.item.weapon.TwoHandedSwordItem;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;

public class SickleItem extends TwoHandedSwordItem implements GeoItem {
   private final AnimatableInstanceCache animationCache = new SingletonAnimatableInstanceCache(this);

   public SickleItem() {
      super(Tiers.NETHERITE, 4, -2.4F, 2.0, 20.0, 0.0, 6.0, 4, -3.2F, 3.0, 50.0, 0.0, 0.0, new Properties().rarity(Rarity.EPIC).fireResistant());
      GeoItem.registerSyncedAnimatable(this);
   }

   public UseAnim getUseAnimation(ItemStack stack) {
      return UseAnim.SPEAR;
   }

   public int getUseDuration(ItemStack stack, LivingEntity entity) {
      return 72000;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      super.appendHoverText(stack, context, tooltip, flag);
      tooltip.add(Component.translatable("item.tennogamenolife.sickle.tooltip").withStyle(ChatFormatting.DARK_PURPLE));
   }

   public void registerControllers(ControllerRegistrar controllers) {
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.animationCache;
   }

   public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
      consumer.accept(new GeoRenderProvider() {
         private final SickleRenderer renderer = new SickleRenderer();

         @Nullable
         public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
            return this.renderer;
         }
      });
   }
}
