package com.blorb.morerelics.relics;

import com.blorb.morerelics.MoreRelics;
import com.blorb.morerelics.dataComponents.DataComponentHandler;
import com.blorb.morerelics.dataComponents.IntegerCounter;
import com.blorb.morerelics.dataComponents.LocationHolder;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.SlotContext;

public class WhisperingAmulet extends MoreRelicBase {
   public Random rand = new Random();

   public WhisperingAmulet(Properties properties) {
   }

   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(AbilitiesData.builder().ability(AbilityData.builder("ascend_heaven").maxLevel(0).requiredLevel(0).build()).build())
         .leveling(LevelingData.builder().maxLevel(0).build())
         .build();
   }

   public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
      DataComponentHandler.getRandomLocation(stack);
      super.onEquip(slotContext, prevStack, stack);
   }

   public boolean canUnequip(SlotContext slotContext, ItemStack stack) {
      IntegerCounter counter = DataComponentHandler.getIntegerCounter(stack);
      return counter.current() == 0 && super.canUnequip(slotContext, stack);
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player && !player.level().isClientSide) {
         LocationHolder holder = DataComponentHandler.getRandomLocation(stack);
         Vec3 location = new Vec3(holder.x, holder.y, holder.z);
         IntegerCounter counter = DataComponentHandler.getIntegerCounter(stack);
         int ascendStatus = counter.current();
         if (ascendStatus > 0) {
            if (ascendStatus > 300) {
               Component message = Component.translatable("amulet.morerelics.amulet_ascends").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(16766720)));
               player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.TOTEM_USE, SoundSource.MASTER, 1.2F, 0.4F);
               player.sendSystemMessage(message);
               player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 1800));
               EntityUtils.addItem(player, MoreRelics.MADE_IN_HEAVEN.toStack());
               stack.setCount(0);
            } else {
               if (++ascendStatus % 10 == 0) {
                  this.spawnHolyParticles(player.level(), player.getPosition(0.0F));
               }

               stack.set(DataComponentHandler.INT_COUNTER.get(), counter.getUpdatedCounter(ascendStatus));
            }
         } else if (player.level().dimension() == Level.OVERWORLD && location.distanceToSqr(player.getX(), player.getY(), player.getZ()) < 4.0) {
            this.ascend(stack, player);
         } else if (this.rand.nextInt(6000) == 1) {
            this.whisper(player, location);
         }
      }
   }

   private void ascend(ItemStack stack, LivingEntity player) {
      IntegerCounter counter = DataComponentHandler.getIntegerCounter(stack);
      player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 300, 6));
      player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.MASTER, 1.5F, 0.4F);
      int ascendStatus = 1;
      stack.set(DataComponentHandler.INT_COUNTER.get(), counter.getUpdatedCounter(ascendStatus));
   }

   private void whisper(LivingEntity player, Vec3 location) {
      Component message;
      if (player.level().dimension() == Level.OVERWORLD) {
         int randomInt = this.rand.nextInt(5);
         String locString = "(X: " + location.x + " Y: " + location.y + " Z: " + location.z + ")";
         if (randomInt == 0) {
            message = Component.translatable("whisper.morerelics.ascend", new Object[]{locString})
               .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(-4276546)).withItalic(true));
         } else if (randomInt == 1) {
            message = Component.translatable("whisper.morerelics.pull", new Object[]{locString})
               .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(-4276546)).withItalic(true));
         } else if (randomInt == 2) {
            message = Component.translatable("whisper.morerelics.clear_path", new Object[]{player.getName().getString(), locString})
               .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(-4276546)).withItalic(true));
         } else if (randomInt == 3) {
            message = Component.translatable("whisper.morerelics.destiny", new Object[]{locString})
               .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(-4276546)).withItalic(true));
         } else {
            message = Component.translatable("whisper.morerelics.rude", new Object[]{player.getName().getString(), locString})
               .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(-4276546)).withItalic(true));
         }
      } else {
         int wrongIndex = this.rand.nextInt(3);
         if (wrongIndex == 0) {
            message = Component.translatable("whisper.morerelics.wrong_dimension1")
               .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(-4276546)).withItalic(true));
         } else if (wrongIndex == 1) {
            message = Component.translatable("whisper.morerelics.wrong_dimension2")
               .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(-4276546)).withItalic(true));
         } else {
            message = Component.translatable("whisper.morerelics.wrong_dimension3")
               .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(-4276546)).withItalic(true));
         }
      }

      player.sendSystemMessage(message);
   }

   public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
      ArrayList<Component> list = new ArrayList<>();
      list.add(Component.translatable("tooltip.morerelics.whispering_amulet.source").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
      return list;
   }

   private void spawnHolyParticles(Level level, Vec3 position) {
      int particleCount = 10;

      for (int i = 0; i < particleCount; i++) {
         double angle = Math.random() * 2.0 * Math.PI;
         double radius = Math.random() * 1.5 + 0.8;
         double x = position.x + radius * Math.cos(angle);
         double y = position.y + Math.random() * 2.5;
         double z = position.z + radius * Math.sin(angle);
         ServerLevel sevLevel = (ServerLevel)level;

         for (ServerPlayer player : level.players()) {
            sevLevel.sendParticles(player, ParticleTypes.END_ROD, true, x, y, z, particleCount, 0.0, 0.0, 0.0, 0.3);
         }
      }
   }
}
