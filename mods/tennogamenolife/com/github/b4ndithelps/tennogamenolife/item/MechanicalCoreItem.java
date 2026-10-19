package com.github.b4ndithelps.tennogamenolife.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;

public class MechanicalCoreItem extends Item {
   public MechanicalCoreItem(Properties properties) {
      super(properties.food(new Builder().nutrition(4).saturationModifier(0.5F).alwaysEdible().build()).fireResistant());
   }

   public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
      if (!level.isClientSide) {
         MobEffect burdenEffect = (MobEffect)BuiltInRegistries.MOB_EFFECT.get(ResourceLocation.fromNamespaceAndPath("tensura", "burden"));
         if (burdenEffect != null) {
            Holder<MobEffect> effectHolder = BuiltInRegistries.MOB_EFFECT.wrapAsHolder(burdenEffect);
            entity.addEffect(new MobEffectInstance(effectHolder, 6000, 0, false, false, true));
         }
      }

      return super.finishUsingItem(stack, level, entity);
   }

   public void appendHoverText(ItemStack stack, @Nullable TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      tooltip.add(Component.translatable("item.tennogamenolife.mechanical_core.desc"));
   }
}
