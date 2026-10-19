package com.blorb.morerelics.relics;

import com.blorb.morerelics.MoreRelics;
import com.blorb.morerelics.dataComponents.DataComponentHandler;
import com.blorb.morerelics.dataComponents.DoubleCounter;
import com.blorb.morerelics.dataComponents.IntegerCounter;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Pre;

@EventBusSubscriber
public class ConvergingOrb extends MoreRelicBase {
   public static final float requiredDamage = 400.0F;

   public ConvergingOrb(Properties properties) {
   }

   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(AbilitiesData.builder().ability(AbilityData.builder("convergence").maxLevel(1).requiredLevel(0).build()).build())
         .leveling(LevelingData.builder().maxLevel(1).initialCost(1).step(1).build())
         .build();
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void onPlayerAttacked(Pre event) {
      if (event.getEntity() instanceof Player player && !player.level().isClientSide) {
         EntityUtils.findEquippedCurios(player, (Item)MoreRelics.CONVERGING_ORB.get()).forEach(stack -> {
            ConvergingOrb item = (ConvergingOrb)stack.getItem();
            increaseDensity(stack, player, event.getNewDamage());
            if (isFinalQuestEnabled(stack) && event.getSource().is(DamageTypes.SONIC_BOOM)) {
               Component message = Component.translatable("tooltip.morerelics.converging_orb.collapse").withStyle(ChatFormatting.AQUA);
               player.level().playSound(null, player.position().x, player.position().y, player.position().z, MoreRelics.WONDER, SoundSource.MASTER, 1.0F, 1.0F);
               WonderOfU.spawnWonderExplosionParticles(player.level(), player.position(), 4.0F);
               player.sendSystemMessage(message);
               EntityUtils.addItem(player, MoreRelics.WONDER_OF_U.toStack());
               stack.setCount(0);
            }
         });
      }
   }

   @Override
   public boolean canEquipMultipleCopies() {
      return false;
   }

   public static void increaseDensity(ItemStack stack, LivingEntity entity, float damage) {
      DoubleCounter damageCounter = DataComponentHandler.getDoubleCounter(stack);
      double currentDamage = damageCounter.getCurrent();
      currentDamage = Math.clamp(currentDamage + damage, 0.0, 400.0);
      damageCounter.setCurrent(currentDamage);
      IntegerCounter stateCounter = DataComponentHandler.getAltIntegerCounter(stack);
      int currentState;
      if (currentDamage < 100.0) {
         currentState = 0;
      } else if (currentDamage < 200.0) {
         currentState = 1;
      } else if (currentDamage < 400.0) {
         currentState = 2;
      } else {
         currentState = 3;
      }

      stack.set(DataComponentHandler.ALT_INT_COUNTER, stateCounter.getUpdatedCounter(currentState));
      if (currentState == 3) {
         tryProcFinalQuest(stack, entity);
      }
   }

   public static void tryProcFinalQuest(ItemStack stack, LivingEntity entity) {
      IntegerCounter getAlreadyEnableCounter = DataComponentHandler.getIntegerCounter(stack);
      if (getAlreadyEnableCounter.current() != 1) {
         Component message = Component.translatable("amulet.morerelics.orb_final_push").withStyle(ChatFormatting.AQUA);
         entity.sendSystemMessage(message);
         stack.set(DataComponentHandler.INT_COUNTER, getAlreadyEnableCounter.getUpdatedCounter(1));
      }
   }

   public static boolean isFinalQuestEnabled(ItemStack stack) {
      IntegerCounter getAlreadyEnableCounter = DataComponentHandler.getIntegerCounter(stack);
      return getAlreadyEnableCounter.current() == 1;
   }

   public String damageToDensityString(ItemStack stack) {
      DoubleCounter damageCounter = DataComponentHandler.getDoubleCounter(stack);
      double damage = damageCounter.getCurrent();
      double baseDensity = 100.0;
      double maxDensity = 4.0 * Math.pow(10.0, 14.0);
      double k = Math.log(maxDensity / baseDensity) / 400.0;
      double density = baseDensity * Math.exp(k * damage);
      double logDensity = Math.log10(density);
      int exponent = (int)Math.floor(logDensity);
      double coefficient = density / Math.pow(10.0, exponent);
      String result;
      if (exponent >= 3) {
         result = String.format("%.1f * 10^%d kg/m^3", coefficient, exponent);
      } else {
         result = String.format("%.0f kg/m^3", density);
      }

      return result;
   }

   public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
      ArrayList<Component> list = new ArrayList<>();
      String densityString = this.damageToDensityString(stack);
      String tooltipText = Component.translatable("tooltip.morerelics.converging_orb.density").withStyle(ChatFormatting.AQUA).getString()
         + ": "
         + densityString;
      list.add(Component.literal(tooltipText).withStyle(ChatFormatting.AQUA));
      if (isFinalQuestEnabled(stack)) {
         list.add(
            Component.translatable("tooltip.morerelics.converging_orb.final_task").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.ITALIC)
         );
      }

      return list;
   }
}
