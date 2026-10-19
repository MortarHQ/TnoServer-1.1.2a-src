package it.hurts.sskirillss.relics.items.relics.feet;

import it.hurts.sskirillss.relics.api.events.common.LivingSlippingEvent;
import it.hurts.sskirillss.relics.entities.ShockwaveEntity;
import it.hurts.sskirillss.relics.init.DataComponentRegistry;
import it.hurts.sskirillss.relics.init.ItemRegistry;
import it.hurts.sskirillss.relics.items.relics.base.RelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicAttributeModifier;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.CastData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastStage;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastType;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.PredicateType;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootData;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.misc.LootEntries;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.MathUtils;
import it.hurts.sskirillss.relics.utils.WorldUtils;
import it.hurts.sskirillss.relics.utils.data.WorldPosition;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import top.theillusivec4.curios.api.SlotContext;

public class IceBreakerItem extends RelicItem {
   @Override
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("sustainability")
                     .stat(
                        StatData.builder("modifier")
                           .initialValue(0.75, 0.5)
                           .upgradeModifier(UpgradeOperation.ADD, -0.05)
                           .formatValue(value -> (int)(MathUtils.round(1.0 - value, 1) * 100.0))
                           .build()
                     )
                     .build()
               )
               .ability(
                  AbilityData.builder("impact")
                     .maxLevel(10)
                     .active(
                        CastData.builder()
                           .type(CastType.INSTANTANEOUS)
                           .predicate(
                              "falling",
                              PredicateType.CAST,
                              (player, stack) -> !player.onGround() && !player.isSpectator() && WorldUtils.getGroundHeight(player, player.position(), 3) <= 3.0
                           )
                           .build()
                     )
                     .stat(
                        StatData.builder("size")
                           .initialValue(2.5, 5.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.3)
                           .formatValue(value -> MathUtils.round(value, 1))
                           .build()
                     )
                     .stat(
                        StatData.builder("damage")
                           .initialValue(2.5, 5.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.25)
                           .formatValue(value -> MathUtils.round(value, 1))
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .leveling(new LevelingData(100, 10, 200))
         .loot(LootData.builder().entry(LootEntries.TAIGA, LootEntries.FROST, LootEntries.MOUNTAIN).build())
         .build();
   }

   @Override
   public RelicAttributeModifier getRelicAttributeModifiers(ItemStack stack) {
      return RelicAttributeModifier.builder()
         .attribute(new RelicAttributeModifier.Modifier(Attributes.KNOCKBACK_RESISTANCE, (float)this.getStatValue(stack, "sustainability", "modifier")))
         .build();
   }

   @Override
   public void castActiveAbility(ItemStack stack, Player player, String ability, CastType type, CastStage stage) {
      if (ability.equals("impact")) {
         stack.set(DataComponentRegistry.WORLD_POSITION, new WorldPosition(player));
      }
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player) {
         WorldPosition position = (WorldPosition)stack.get(DataComponentRegistry.WORLD_POSITION);
         if (position != null) {
            Vec3 pos = position.getPos();
            if (!player.onGround()) {
               Vec3 motion = player.getDeltaMovement();
               if (player.onGround() || player.isSpectator()) {
                  stack.set(DataComponentRegistry.WORLD_POSITION, null);
                  return;
               }

               player.stopFallFlying();
               player.getAbilities().flying = false;
               player.fallDistance = 0.0F;
               player.setDeltaMovement(motion.x(), Math.min(-0.01F, motion.y() * 1.075F), motion.z());
            } else {
               Level level = player.getCommandSenderWorld();
               double distance = pos.y() + Math.abs(level.getMinBuildHeight()) - (player.getY() + Math.abs(level.getMinBuildHeight()));
               stack.set(DataComponentRegistry.WORLD_POSITION, null);
               if (distance <= 0.0) {
                  return;
               }

               int radius = (int)Math.round(Math.min(this.getStatValue(stack, "impact", "size"), distance * 0.25));
               if (radius <= 0) {
                  return;
               }

               this.spreadRelicExperience(player, stack, (int)Math.min(10L, Math.round(distance / 3.0)));
               ShockwaveEntity shockwave = new ShockwaveEntity(level, radius, (float)this.getStatValue(stack, "impact", "damage"));
               BlockPos blockPos = player.getOnPos();
               shockwave.setOwner(player);
               shockwave.setPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());
               level.addFreshEntity(shockwave);
               level.playSound(null, player.blockPosition(), SoundEvents.WITHER_BREAK_BLOCK, SoundSource.MASTER, 0.75F, 1.0F);
               level.addParticle(ParticleTypes.EXPLOSION_EMITTER, player.getX(), player.getY(), player.getZ(), 0.0, 0.0, 0.0);
            }
         }
      }
   }

   public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
      if (stack.getItem() != newStack.getItem()) {
         stack.set(DataComponentRegistry.WORLD_POSITION, null);
      }
   }

   @EventBusSubscriber(modid = "relics")
   public static class Events {
      @SubscribeEvent
      public static void onLivingSlipping(LivingSlippingEvent event) {
         if (!(event.getFriction() <= 0.6F) && event.getEntity() instanceof Player player && !player.isInWater() && !player.isInLava()) {
            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ItemRegistry.ICE_BREAKER.get());
            if (!stack.isEmpty()) {
               event.setFriction(0.6F);
            }
         }
      }
   }
}
