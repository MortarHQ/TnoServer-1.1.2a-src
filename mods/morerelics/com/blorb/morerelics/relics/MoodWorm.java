package com.blorb.morerelics.relics;

import com.blorb.morerelics.MoreRelics;
import com.blorb.morerelics.dataComponents.DataComponentHandler;
import com.blorb.morerelics.dataComponents.IntegerCounter;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourceData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourcesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootData;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootEntry;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.misc.LootEntries;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Post;
import top.theillusivec4.curios.api.SlotContext;

@EventBusSubscriber
public class MoodWorm extends MoreRelicBase {
   public static int MOOD_SWAP_SECONDS = 600;

   public MoodWorm(Properties properties) {
   }

   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("moody")
                     .stat(
                        StatData.builder("multip")
                           .initialValue(0.8, 0.12)
                           .upgradeModifier(UpgradeOperation.ADD, 0.02)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .stat(
                        StatData.builder("reduction")
                           .initialValue(8.0, 12.0)
                           .upgradeModifier(UpgradeOperation.ADD, 2.0)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(5)
                     .requiredLevel(0)
                     .build()
               )
               .ability(AbilityData.builder("tranquillity").maxLevel(0).requiredLevel(5).build())
               .build()
         )
         .leveling(
            LevelingData.builder()
               .maxLevel(5)
               .initialCost(20)
               .step(20)
               .sources(LevelingSourcesData.builder().source(LevelingSourceData.abilityBuilder("moody").initialValue(1).build()).build())
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.WILDCARD}).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      LivingEntity livingEntity = slotContext.entity();
      if (livingEntity instanceof Player player) {
         if (!player.level().isClientSide) {
            IntegerCounter currentMoodCounter = DataComponentHandler.getAltIntegerCounter(stack);
            IntegerCounter moodTimerCounter = DataComponentHandler.getIntegerCounter(stack);
            boolean unlockedTranquility = this.isAbilityUnlocked(stack, "tranquillity");
            int currentMood = currentMoodCounter.current();
            int moodTimer = moodTimerCounter.current();
            if (0 < moodTimer) {
               moodTimer--;
            } else {
               this.addRelicExperience(livingEntity, stack, 20);
               currentMood = this.swapMood(currentMoodCounter, stack, unlockedTranquility);
               moodTimer = MOOD_SWAP_SECONDS * 20;
            }

            stack.set(DataComponentHandler.INT_COUNTER, moodTimerCounter.getUpdatedCounter(moodTimer));
            float multip = (float)this.getStatValue(stack, "moody", "multip");
            EntityUtils.removeAttribute(livingEntity, stack, Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_TOTAL);
            EntityUtils.removeAttribute(livingEntity, stack, Attributes.ATTACK_DAMAGE, Operation.ADD_MULTIPLIED_TOTAL);
            EntityUtils.removeAttribute(livingEntity, stack, Attributes.LUCK, Operation.ADD_MULTIPLIED_TOTAL);
            EntityUtils.removeAttribute(livingEntity, stack, Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE);
            if (currentMood == 0 || currentMood == 3) {
               EntityUtils.resetAttribute(livingEntity, stack, Attributes.ATTACK_DAMAGE, multip, Operation.ADD_MULTIPLIED_TOTAL);
            }

            if (currentMood == 1 || currentMood == 3) {
               EntityUtils.resetAttribute(livingEntity, stack, Attributes.MOVEMENT_SPEED, multip, Operation.ADD_MULTIPLIED_TOTAL);
               EntityUtils.resetAttribute(livingEntity, stack, Attributes.LUCK, multip, Operation.ADD_MULTIPLIED_TOTAL);
            }

            if (currentMood == 2 || currentMood == 3) {
               EntityUtils.resetAttribute(livingEntity, stack, Attributes.KNOCKBACK_RESISTANCE, 0.5F, Operation.ADD_VALUE);
            }
         }
      }
   }

   public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
      LivingEntity livingEntity = slotContext.entity();
      if (stack.getItem() != newStack.getItem()) {
         EntityUtils.removeAttribute(livingEntity, stack, Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.removeAttribute(livingEntity, stack, Attributes.ATTACK_DAMAGE, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.removeAttribute(livingEntity, stack, Attributes.LUCK, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.removeAttribute(livingEntity, stack, Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE);
      }
   }

   private int swapMood(IntegerCounter moodCounter, ItemStack stack, boolean didUnlockTranquillity) {
      Random rand = new Random();
      int highestMood = didUnlockTranquillity ? 3 : 2;
      int nextMood = rand.nextInt(highestMood + 1);
      if (nextMood == moodCounter.current()) {
         nextMood = nextMood == highestMood ? 0 : nextMood + 1;
      }

      stack.set(DataComponentHandler.ALT_INT_COUNTER, moodCounter.getUpdatedCounter(nextMood));
      return nextMood;
   }

   @SubscribeEvent
   public static void onDamageTaken(LivingIncomingDamageEvent event) {
      if (event.getEntity() instanceof Player player && !player.level().isClientSide) {
         double reduction = EntityUtils.findEquippedCurios(player, (Item)MoreRelics.MOODWORM.get()).stream().mapToDouble(stack -> {
            MoodWorm item = (MoodWorm)stack.getItem();
            int currentMood = DataComponentHandler.getAltIntegerCounter(stack).current();
            return currentMood != 2 && currentMood != 3 ? 0.0 : item.getStatValue(stack, "moody", "reduction");
         }).sum();
         if (reduction > 60.0) {
            reduction = 60.0;
         } else if (reduction < 0.0) {
            reduction = 0.0;
         }

         event.setAmount((float)(event.getAmount() * (100.0 - reduction) / 100.0));
      }
   }

   @SubscribeEvent
   public static void onPlayerAttack(Post event) {
      if (event.getSource().getEntity() instanceof Player player && !player.level().isClientSide) {
         LivingEntity victim = event.getEntity();
         int shouldBurn = EntityUtils.findEquippedCurios(player, (Item)MoreRelics.MOODWORM.get()).stream().mapToInt(stack -> {
            MoodWorm item = (MoodWorm)stack.getItem();
            int currentMood = DataComponentHandler.getAltIntegerCounter(stack).current();
            return currentMood != 0 && currentMood != 3 ? 0 : 1;
         }).sum();
         if (shouldBurn > 0 && victim.getRemainingFireTicks() < 60) {
            victim.setRemainingFireTicks(60);
         }
      }
   }

   public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
      ArrayList<Component> list = new ArrayList<>();
      int currentMood = DataComponentHandler.getAltIntegerCounter(stack).current();
      if (currentMood == 0) {
         list.add(Component.translatable("tooltip.morerelics.moodworm.furious").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC));
      } else if (currentMood == 1) {
         list.add(Component.translatable("tooltip.morerelics.moodworm.joyful").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
      } else if (currentMood == 2) {
         list.add(Component.translatable("tooltip.morerelics.moodworm.gloomy").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.ITALIC));
      } else if (currentMood == 3) {
         list.add(Component.translatable("tooltip.morerelics.moodworm.tranquillity").withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.ITALIC));
      }

      return list;
   }
}
