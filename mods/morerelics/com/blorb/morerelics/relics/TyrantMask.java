package com.blorb.morerelics.relics;

import com.blorb.morerelics.MoreRelics;
import com.blorb.morerelics.dataComponents.DataComponentHandler;
import com.blorb.morerelics.dataComponents.LocationHolder;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootData;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootEntry;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.misc.LootEntries;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.level.BlockEvent.EntityPlaceEvent;
import top.theillusivec4.curios.api.SlotContext;

@EventBusSubscriber
public class TyrantMask extends MoreRelicBase {
   final int requiredDistance = 15000;
   final int requiredKills = 300;
   final int requiredBlocks = 1200;

   public TyrantMask(Properties properties) {
   }

   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(AbilityData.builder("regrets").stat(StatData.builder("debuff").initialValue(20.0, 20.0).build()).build())
               .ability(AbilityData.builder("revenge").build())
               .ability(AbilityData.builder("monotony").build())
               .ability(AbilityData.builder("futility").build())
               .build()
         )
         .leveling(LevelingData.builder().maxLevel(1).initialCost(100).step(30).build())
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.BASTION}).build())
         .build();
   }

   public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
      this.getAbilityComponent(stack, "regrets").toBuilder();
      super.onEquip(slotContext, prevStack, stack);
   }

   @SubscribeEvent
   public static void onKill(LivingDeathEvent event) {
      if (event.getSource().getEntity() instanceof Player player && !player.level().isClientSide) {
         EntityUtils.findEquippedCurios(player, (Item)MoreRelics.TYRANT_MASK.get()).forEach(stack -> {
            if (stack.getItem() instanceof TyrantMask item) {
               LocationHolder holder = DataComponentHandler.getTripleStorage(stack);
               if (holder.y < 300.0) {
                  holder.y++;
                  item.checkAndChange(holder, stack, player);
               }
            }
         });
      }
   }

   @SubscribeEvent
   public static void onPlace(EntityPlaceEvent event) {
      if (event.getEntity() instanceof Player player && !player.level().isClientSide) {
         EntityUtils.findEquippedCurios(player, (Item)MoreRelics.TYRANT_MASK.get()).forEach(stack -> {
            TyrantMask item = (TyrantMask)stack.getItem();
            LocationHolder holder = DataComponentHandler.getTripleStorage(stack);
            if (holder.z < 1200.0) {
               holder.z++;
               item.checkAndChange(holder, stack, player);
            }
         });
      }
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      LocationHolder holder = DataComponentHandler.getTripleStorage(stack);
      LivingEntity entity = slotContext.entity();
      if (entity instanceof Player player) {
         if (!player.level().isClientSide) {
            if (holder.x < 15000.0) {
               LocationHolder location = (LocationHolder)stack.get(DataComponentHandler.LOCATION_HOLDER.get());
               Vec3 playerPos = entity.getPosition(0.0F);
               if (location == null) {
                  LocationHolder newHolder = new LocationHolder(playerPos.x, playerPos.y, playerPos.z);
                  stack.set(DataComponentHandler.LOCATION_HOLDER.get(), newHolder);
               } else {
                  holder.x = holder.x + Math.sqrt(entity.distanceToSqr(location.x, location.y, location.z));
                  location.setX(playerPos.x);
                  location.setY(playerPos.y);
                  location.setZ(playerPos.z);
                  if (this.checkAndChange(holder, stack, player)) {
                     return;
                  }
               }
            }

            EntityUtils.applyAttribute(
               entity, stack, Attributes.MOVEMENT_SPEED, -((float)this.getStatValue(stack, "regrets", "debuff")) / 100.0F, Operation.ADD_MULTIPLIED_TOTAL
            );
            EntityUtils.applyAttribute(
               entity, stack, Attributes.ATTACK_DAMAGE, -((float)this.getStatValue(stack, "regrets", "debuff")) / 100.0F, Operation.ADD_MULTIPLIED_TOTAL
            );
            EntityUtils.applyAttribute(
               entity, stack, Attributes.ATTACK_SPEED, -((float)this.getStatValue(stack, "regrets", "debuff")) / 100.0F, Operation.ADD_MULTIPLIED_TOTAL
            );
         }
      }
   }

   public void onEquipFromUse(SlotContext slotContext, ItemStack stack) {
      LocationHolder location = (LocationHolder)stack.get(DataComponentHandler.LOCATION_HOLDER.get());
      if (location != null) {
         Entity entity = slotContext.entity();
         Vec3 playerPos = entity.getPosition(0.0F);
         location.setX(playerPos.x);
         location.setY(playerPos.y);
         location.setZ(playerPos.z);
      }

      super.onEquipFromUse(slotContext, stack);
   }

   public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
      LivingEntity livingEntity = slotContext.entity();
      if (stack.getItem() != newStack.getItem()) {
         EntityUtils.removeAttribute(livingEntity, stack, Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.removeAttribute(livingEntity, stack, Attributes.ATTACK_SPEED, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.removeAttribute(livingEntity, stack, Attributes.ATTACK_DAMAGE, Operation.ADD_MULTIPLIED_TOTAL);
      }
   }

   private boolean checkAndChange(LocationHolder holder, ItemStack stack, Player player) {
      if (holder.x >= 15000.0 && holder.y >= 300.0 && holder.z >= 1200.0) {
         EntityUtils.addItem(player, MoreRelics.KING_CRIMSON.toStack());
         player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SOUL_SAND_BREAK, SoundSource.MASTER, 1.2F, 0.3F);
         Component message = Component.translatable("tooltip.morerelics.tyrant_mask_freed").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(16766720)));
         player.sendSystemMessage(message);
         this.spawnSoulParticles(player.level(), player.position());
         EntityUtils.removeAttribute(player, stack, Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.removeAttribute(player, stack, Attributes.ATTACK_SPEED, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.removeAttribute(player, stack, Attributes.ATTACK_DAMAGE, Operation.ADD_MULTIPLIED_TOTAL);
         stack.setCount(0);
         return true;
      } else {
         return false;
      }
   }

   public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
      ArrayList<Component> list = new ArrayList<>();
      LocationHolder holder = DataComponentHandler.getTripleStorage(stack);
      list.add(
         Component.translatable("tooltip.morerelics.tyrant_mask_distance")
            .append(String.valueOf((int)holder.x))
            .append("/")
            .append(String.valueOf(15000))
            .withStyle(new ChatFormatting[]{ChatFormatting.LIGHT_PURPLE, ChatFormatting.ITALIC})
      );
      list.add(
         Component.translatable("tooltip.morerelics.tyrant_mask_kill")
            .append(String.valueOf((int)holder.y))
            .append("/")
            .append(String.valueOf(300))
            .withStyle(new ChatFormatting[]{ChatFormatting.LIGHT_PURPLE, ChatFormatting.ITALIC})
      );
      list.add(
         Component.translatable("tooltip.morerelics.tyrant_mask_block")
            .append(String.valueOf((int)holder.z))
            .append("/")
            .append(String.valueOf(1200))
            .withStyle(new ChatFormatting[]{ChatFormatting.LIGHT_PURPLE, ChatFormatting.ITALIC})
      );
      return list;
   }

   private void spawnSoulParticles(Level level, Vec3 position) {
      int particleCount = 50;

      for (int i = 0; i < particleCount; i++) {
         double angle = Math.random() * 2.0 * Math.PI;
         double radius = Math.random() * 1.5 + 0.8;
         double x = position.x + radius * Math.cos(angle);
         double y = position.y + Math.random() * 2.5;
         double z = position.z + radius * Math.sin(angle);
         ServerLevel sevLevel = (ServerLevel)level;

         for (ServerPlayer player : level.players()) {
            sevLevel.sendParticles(player, ParticleTypes.SOUL, true, x, y, z, particleCount, 0.0, 0.0, 0.0, 0.7);
         }
      }
   }
}
