package com.blorb.morerelics.relics;

import com.blorb.morerelics.dataComponents.DataComponentHandler;
import com.blorb.morerelics.dataComponents.IntegerCounter;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

public class EpochApple extends MoreRelicBase {
   public EpochApple(Properties properties) {
      super(new Properties().rarity(Rarity.RARE).food(new Builder().build()).stacksTo(1));
   }

   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("persistent")
                     .stat(
                        StatData.builder("cooldown")
                           .initialValue(16.0, 12.0)
                           .upgradeModifier(UpgradeOperation.ADD, -0.5)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(8)
                     .requiredLevel(0)
                     .build()
               )
               .ability(
                  AbilityData.builder("ripen")
                     .stat(
                        StatData.builder("cooldown")
                           .initialValue(60.0, 52.0)
                           .upgradeModifier(UpgradeOperation.ADD, -3.0)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(4)
                     .requiredLevel(8)
                     .build()
               )
               .build()
         )
         .leveling(LevelingData.builder().maxLevel(12).initialCost(100).step(40).build())
         .build();
   }

   @Nullable
   public FoodProperties getFoodProperties(@NotNull ItemStack stack, @Nullable LivingEntity entity) {
      if (entity instanceof Player player && this.isAbilityUnlocked(stack, "persistent")) {
         int currentState = this.getState(stack);
         Builder builder;
         if (currentState == -1) {
            builder = new Builder().nutrition(0).saturationModifier(0.0F);
         } else if (currentState == 0) {
            builder = new Builder()
               .nutrition(4)
               .saturationModifier(1.2F)
               .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 1), 1.0F)
               .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 2400, 0), 1.0F)
               .alwaysEdible();
         } else {
            builder = new Builder()
               .nutrition(4)
               .saturationModifier(1.2F)
               .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 400, 1), 1.0F)
               .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 0), 1.0F)
               .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0), 1.0F)
               .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 2400, 3), 1.0F)
               .alwaysEdible();
         }

         return builder.build();
      } else {
         return super.getFoodProperties(stack, entity);
      }
   }

   @NotNull
   public InteractionResultHolder<ItemStack> use(@NotNull Level world, Player player, @NotNull InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      if (this.canPlayerUseAbility(player, stack, "persistent") && this.getState(stack) >= 0) {
         player.startUsingItem(hand);
         return InteractionResultHolder.consume(stack);
      } else {
         return InteractionResultHolder.pass(stack);
      }
   }

   @NotNull
   public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
      if (entity instanceof Player player && !player.level().isClientSide) {
         FoodProperties properties = this.getFoodProperties(stack, entity);
         if (properties == null || properties.nutrition() == 0) {
            return stack;
         }

         IntegerCounter didInitCounter = DataComponentHandler.getAltAltIntegerCounter(stack);
         stack.set(DataComponentHandler.ALT_ALT_INT_COUNTER, didInitCounter.getUpdatedCounter(1));
         IntegerCounter valCounter = DataComponentHandler.getIntegerCounter(stack);
         stack.set(DataComponentHandler.INT_COUNTER, valCounter.getUpdatedCounter(0));
         this.spreadRelicExperience(player, stack, 120);
         player.eat(level, stack.copy());
         this.changeProgress(stack, 0);
      }

      return stack;
   }

   public boolean isFoil(ItemStack stack) {
      return this.getState(stack) == 1;
   }

   public int getUseDuration(@NotNull ItemStack stack, LivingEntity entity) {
      return 32;
   }

   @NotNull
   public UseAnim getUseAnimation(@NotNull ItemStack stack) {
      return UseAnim.EAT;
   }

   public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entityIn, int itemSlot, boolean isSelected) {
      if (!level.isClientSide() && entityIn instanceof Player player && entityIn.tickCount % 100 == 0) {
         this.changeProgress(stack, 100);
      }

      super.inventoryTick(stack, level, entityIn, itemSlot, isSelected);
   }

   public void changeProgress(ItemStack stack, int amount) {
      IntegerCounter valCounter = DataComponentHandler.getIntegerCounter(stack);
      boolean persistentUnlocked = this.isAbilityUnlocked(stack, "persistent");
      int persistentCooldown = persistentUnlocked ? (int)(this.getStatValue(stack, "persistent", "cooldown") * 20.0 * 60.0) : 0;
      IntegerCounter didInitCounter = DataComponentHandler.getAltAltIntegerCounter(stack);
      int didInit = didInitCounter.current();
      if (didInit == 0) {
         stack.set(DataComponentHandler.INT_COUNTER, valCounter.getUpdatedCounter(persistentCooldown));
         stack.set(DataComponentHandler.ALT_ALT_INT_COUNTER, didInitCounter.getUpdatedCounter(1));
      } else {
         boolean ripenUnlocked = this.isAbilityUnlocked(stack, "ripen");
         int ripenCooldown = ripenUnlocked ? (int)(this.getStatValue(stack, "ripen", "cooldown") * 20.0 * 60.0) : 0;
         int upperLimit = persistentCooldown + ripenCooldown;
         int currentVal = valCounter.current();
         currentVal = Math.clamp(currentVal + amount, 0, upperLimit);
         stack.set(DataComponentHandler.INT_COUNTER, valCounter.getUpdatedCounter(currentVal));
         IntegerCounter stateCounter = DataComponentHandler.getAltIntegerCounter(stack);
         int currentState = -1;
         if (ripenUnlocked) {
            double ripenTime = ripenCooldown + persistentCooldown;
            if (currentVal >= ripenTime) {
               currentState = 1;
            }
         }

         if (persistentUnlocked && currentState == -1 && currentVal >= persistentCooldown) {
            currentState = 0;
         }

         stack.set(DataComponentHandler.ALT_INT_COUNTER, stateCounter.getUpdatedCounter(currentState));
      }
   }

   public int getState(ItemStack stack) {
      return DataComponentHandler.getAltIntegerCounter(stack).current();
   }

   @Override
   public boolean canEquip(SlotContext slotContext, ItemStack stack) {
      return false;
   }
}
