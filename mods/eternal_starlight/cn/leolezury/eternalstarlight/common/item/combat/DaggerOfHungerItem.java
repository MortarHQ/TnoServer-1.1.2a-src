package cn.leolezury.eternalstarlight.common.item.combat;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.registry.ESCriteriaTriggers;
import cn.leolezury.eternalstarlight.common.registry.ESDataComponents;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class DaggerOfHungerItem extends DualWieldingSwordItem {
   public static final ItemAttributeModifiers DEFAULT_ATTRIBUTE = SwordItem.createAttributes(ESItemTiers.TOOTH_OF_HUNGER, 3, -2.4F);
   public static final ItemAttributeModifiers BONUS_ATTRIBUTE = SwordItem.createAttributes(ESItemTiers.TOOTH_OF_HUNGER, 4, -1.9F);
   public static final ItemAttributeModifiers PENALTY_ATTRIBUTE = SwordItem.createAttributes(ESItemTiers.TOOTH_OF_HUNGER, 2, -2.9F);

   public DaggerOfHungerItem(Tier tier, Properties properties) {
      super(tier, properties);
      final DataComponentMap copy = this.components;
      this.components = new DataComponentMap() {
         @Nullable
         public <T> T get(DataComponentType<? extends T> type) {
            return (T)(type != DataComponents.MAX_DAMAGE && type != DataComponents.DAMAGE ? copy.get(type) : null);
         }

         public Set<DataComponentType<?>> keySet() {
            return copy.keySet().stream().filter(type -> type != DataComponents.MAX_DAMAGE && type != DataComponents.DAMAGE).collect(Collectors.toSet());
         }
      };
   }

   public void postHurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity attacker) {
      super.postHurtEnemy(stack, entity, attacker);
      entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 60));
      if (attacker instanceof Player player) {
         player.getFoodData().eat(3, 0.0F);
      }

      float hungerLevel = Mth.clamp((Float)stack.getOrDefault(ESDataComponents.HUNGER_LEVEL.get(), 0.0F), -1.0F, 1.0F);
      float newHungerLevel = Math.min(1.0F, hungerLevel + 0.05F);
      stack.applyComponentsAndValidate(DataComponentPatch.builder().set(ESDataComponents.HUNGER_LEVEL.get(), newHungerLevel).build());
      if (newHungerLevel == 1.0F && attacker instanceof ServerPlayer player) {
         ((PlayerTrigger)ESCriteriaTriggers.SATURATE_DAGGER_OF_HUNGER.get()).trigger(player);
      }
   }

   public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean bl) {
      if (entity.tickCount % 1200 == 0) {
         float hungerLevel = Mth.clamp((Float)stack.getOrDefault(ESDataComponents.HUNGER_LEVEL.get(), 0.0F), -1.0F, 1.0F);
         float newHungerLevel = Math.max(-1.0F, hungerLevel - 0.06F);
         stack.applyComponentsAndValidate(DataComponentPatch.builder().set(ESDataComponents.HUNGER_LEVEL.get(), newHungerLevel).build());
         if (hungerLevel == -1.0F) {
            entity.hurt(ESDamageTypes.getDamageSource(level, ESDamageTypes.DAGGER_OF_HUNGER), 3.0F);
            newHungerLevel = Math.min(1.0F, newHungerLevel + 0.05F);
            stack.applyComponentsAndValidate(DataComponentPatch.builder().set(ESDataComponents.HUNGER_LEVEL.get(), newHungerLevel).build());
         }
      }

      if (entity.tickCount % 600 == 0) {
         float hungerLevel = Mth.clamp((Float)stack.getOrDefault(ESDataComponents.HUNGER_LEVEL.get(), 0.0F), -1.0F, 1.0F);
         int state = Math.min(2, (int)((hungerLevel + 1.0F) * 1.5F));

         ItemAttributeModifiers modifiers = switch (state) {
            case 0 -> PENALTY_ATTRIBUTE;
            case 2 -> BONUS_ATTRIBUTE;
            default -> DEFAULT_ATTRIBUTE;
         };
         stack.applyComponentsAndValidate(DataComponentPatch.builder().set(DataComponents.ATTRIBUTE_MODIFIERS, modifiers).build());
      }

      super.inventoryTick(stack, level, entity, slot, bl);
   }

   public boolean isBarVisible(ItemStack stack) {
      float hungerLevel = Mth.clamp((Float)stack.getOrDefault(ESDataComponents.HUNGER_LEVEL.get(), 0.0F), -1.0F, 1.0F);
      return hungerLevel < 1.0F;
   }

   public int getBarWidth(ItemStack stack) {
      float hungerLevel = Mth.clamp((Float)stack.getOrDefault(ESDataComponents.HUNGER_LEVEL.get(), 0.0F), -1.0F, 1.0F);
      return Mth.clamp(Math.round(13.0F - (1.0F - hungerLevel) * 13.0F / 2.0F), 0, 13);
   }

   public int getBarColor(ItemStack stack) {
      float hungerLevel = Mth.clamp((Float)stack.getOrDefault(ESDataComponents.HUNGER_LEVEL.get(), 0.0F), -1.0F, 1.0F);
      float progress = Mth.clamp((hungerLevel + 1.0F) / 2.0F, 0.0F, 1.0F);
      return ARGB32.colorFromFloat(1.0F, 103.0F * progress / 255.0F, 47.0F * progress / 255.0F, 207.0F * progress / 255.0F);
   }
}
