package it.hurts.octostudios.rarcompat.items;

import artifacts.registry.ModItems;
import it.hurts.octostudios.rarcompat.network.packets.RepulsionUmbrellaPacket;
import it.hurts.sskirillss.relics.init.CreativeTabRegistry;
import it.hurts.sskirillss.relics.init.DataComponentRegistry;
import it.hurts.sskirillss.relics.items.misc.CreativeContentConstructor;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourceData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourcesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.GemColor;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.GemShape;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootData;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootEntry;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.misc.LootEntries;
import it.hurts.sskirillss.relics.items.relics.base.data.research.ResearchData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.BeamsData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.StyleData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.TooltipData;
import it.hurts.sskirillss.relics.network.NetworkHandler;
import it.hurts.sskirillss.relics.network.packets.PacketPlayerMotion;
import it.hurts.sskirillss.relics.network.packets.sync.S2CEntityMotionPacket;
import it.hurts.sskirillss.relics.utils.MathUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLivingEvent.Pre;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.LeftClickBlock.Action;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;

public class UmbrellaItem extends WearableRelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("glider")
                     .requiredPoints(2)
                     .stat(
                        StatData.builder("count")
                           .initialValue(1.0, 3.0)
                           .upgradeModifier(UpgradeOperation.ADD, 1.0)
                           .formatValue(value -> (int)MathUtils.round(value, 0))
                           .build()
                     )
                     .stat(
                        StatData.builder("cooldown")
                           .initialValue(2.0, 1.5)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, -0.07)
                           .formatValue(value -> MathUtils.round(value, 1))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 3, 11)
                           .star(1, 11, 5)
                           .star(2, 19, 11)
                           .star(3, 11, 22)
                           .star(4, 15, 22)
                           .star(5, 13, 25)
                           .star(6, 6, 6)
                           .star(7, 16, 6)
                           .star(8, 11, 11)
                           .link(0, 6)
                           .link(6, 1)
                           .link(1, 7)
                           .link(7, 2)
                           .link(3, 5)
                           .link(5, 4)
                           .link(0, 8)
                           .link(8, 2)
                           .link(3, 8)
                           .link(8, 1)
                           .build()
                     )
                     .build()
               )
               .ability(
                  AbilityData.builder("shield")
                     .requiredLevel(5)
                     .stat(
                        StatData.builder("knockback")
                           .initialValue(1.0, 3.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.1)
                           .formatValue(value -> MathUtils.round(value, 1))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 2, 5)
                           .star(1, 11, 3)
                           .star(2, 20, 6)
                           .star(3, 11, 13)
                           .star(4, 3, 19)
                           .star(5, 19, 19)
                           .star(6, 11, 25)
                           .star(7, 6, 14)
                           .star(8, 16, 14)
                           .star(9, 16, 24)
                           .star(10, 6, 24)
                           .star(11, 11, 16)
                           .star(12, 11, 29)
                           .link(0, 3)
                           .link(1, 3)
                           .link(2, 3)
                           .link(3, 7)
                           .link(7, 4)
                           .link(3, 8)
                           .link(5, 8)
                           .link(5, 9)
                           .link(6, 9)
                           .link(4, 10)
                           .link(6, 10)
                           .link(6, 11)
                           .link(6, 12)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-4834773).borderBottom(-10481899).build())
               .beams(BeamsData.builder().startColor(-3198433).endColor(6295317).build())
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(15)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("glider_1", "glider").initialValue(1).gem(GemShape.SQUARE, GemColor.CYAN).build())
                     .source(LevelingSourceData.abilityBuilder("glider_2", "glider").initialValue(1).gem(GemShape.SQUARE, GemColor.BLUE).build())
                     .source(LevelingSourceData.abilityBuilder("shield").initialValue(1).gem(GemShape.SQUARE, GemColor.YELLOW).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.VILLAGE, LootEntries.MOUNTAIN}).build())
         .build();
   }

   @Override
   public void gatherCreativeTabContent(CreativeContentConstructor constructor) {
      ItemStack stack = this.getDefaultInstance();
      this.setCharges(stack, this.getMaxCharges(stack));
      constructor.entry((CreativeModeTab)CreativeTabRegistry.RELICS_TAB.get(), TabVisibility.PARENT_TAB_ONLY, new ItemStack[]{stack});
      constructor.entry((CreativeModeTab)ModItems.CREATIVE_TAB.get(), TabVisibility.PARENT_AND_SEARCH_TABS, new ItemStack[]{stack});
   }

   public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean isSelected) {
      if (entity instanceof Player player && this.canPlayerUseAbility(player, stack, "glider")) {
         boolean isOnGround = player.onGround();
         if (isOnGround && this.getCharges(stack) != this.getMaxCharges(stack)) {
            this.setCharges(stack, this.getMaxCharges(stack));
         }

         boolean hasUmbrella = false;

         for (InteractionHand hand : InteractionHand.values()) {
            if (player.getItemInHand(hand) == stack) {
               hasUmbrella = true;
               break;
            }
         }

         if (hasUmbrella && !player.isInLiquid() && !(player.getDeltaMovement().y > 0.0) && !player.isShiftKeyDown()) {
            Vec3 motion = player.getDeltaMovement();
            player.setDeltaMovement(motion.x(), -0.15, motion.z());
            player.fallDistance = 0.0F;
            if (player.tickCount % 20 == 0 && !isOnGround) {
               this.spreadRelicExperience(player, stack, 1);
            }
         }
      }
   }

   @NotNull
   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      if (this.canPlayerUseAbility(player, player.getItemInHand(hand), "shield")) {
         player.startUsingItem(hand);
         return InteractionResultHolder.consume(player.getItemInHand(hand));
      } else {
         return InteractionResultHolder.fail(player.getItemInHand(hand));
      }
   }

   @NotNull
   public UseAnim getUseAnimation(ItemStack stack) {
      return UseAnim.BLOCK;
   }

   public int getUseDuration(ItemStack stack, LivingEntity livingEntity) {
      return 72000;
   }

   public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
      return false;
   }

   public int getBarWidth(ItemStack stack) {
      return Math.round(13.0F * this.getCharges(stack) / this.getMaxCharges(stack));
   }

   public boolean isBarVisible(@NotNull ItemStack stack) {
      return this.getCharges(stack) != this.getMaxCharges(stack) && this.hasUnlockedAbility(stack);
   }

   public int getBarColor(@NotNull ItemStack stack) {
      return Mth.hsvToRgb(Math.max(0.0F, (float)this.getCharges(stack) / this.getMaxCharges(stack)) / 3.0F, 1.0F, 1.0F);
   }

   public int getMaxCharges(ItemStack stack) {
      return (int)MathUtils.round(this.getStatValue(stack, "glider", "count"), 0);
   }

   public int getCharges(ItemStack stack) {
      return (Integer)stack.getOrDefault(DataComponentRegistry.CHARGE, 0);
   }

   public void setCharges(ItemStack stack, int amount) {
      stack.set(DataComponentRegistry.CHARGE, Math.max(amount, 0));
   }

   public void addCharges(ItemStack stack, int amount) {
      this.setCharges(stack, this.getCharges(stack) + amount);
   }

   public static boolean isHoldingUmbrella(LivingEntity entity, InteractionHand hand) {
      return entity.getItemInHand(hand).getItem() instanceof UmbrellaItem && (!entity.isUsingItem() || entity.getUsedItemHand() != hand);
   }

   @EventBusSubscriber(Dist.CLIENT)
   public static class UmbrellaClientEvents {
      @SubscribeEvent
      public static void onLeftClickEmpty(LeftClickEmpty event) {
         handleLeftClick(event);
      }

      @SubscribeEvent
      public static void onLeftClickBlock(LeftClickBlock event) {
         if (event.getAction() == Action.START && event.getEntity().level().isClientSide()) {
            handleLeftClick(event);
         }
      }

      private static void handleLeftClick(PlayerInteractEvent event) {
         Player player = event.getEntity();
         ItemStack stack = player.getMainHandItem();
         if (stack.getItem() instanceof UmbrellaItem relic
            && relic.canPlayerUseAbility(player, stack, "glider")
            && !player.getCooldowns().isOnCooldown(relic)
            && relic.getCharges(stack) > 0) {
            NetworkHandler.sendToServer(new RepulsionUmbrellaPacket());
            Vec3 angle = player.getLookAngle().scale(-1.15F);
            Vec3 motion = player.getDeltaMovement().add(angle);
            if (angle.y < 0.0) {
               player.setDeltaMovement(new Vec3(motion.x(), 0.0, motion.z()));
            } else {
               player.setDeltaMovement(motion.x(), angle.y(), motion.z());
            }
         }
      }

      @SubscribeEvent
      public static void onLivingRender(Pre<?, ?> event) {
         if (event.getEntity() instanceof Player player && event.getRenderer().getModel() instanceof HumanoidModel<?> humanoidModel) {
            boolean var7 = UmbrellaItem.isHoldingUmbrella(player, InteractionHand.OFF_HAND);
            boolean isHoldingMainHand = UmbrellaItem.isHoldingUmbrella(player, InteractionHand.MAIN_HAND);
            boolean isRightHanded = player.getMainArm() == HumanoidArm.RIGHT;
            if (!player.isShiftKeyDown() || player.onGround() || !isHoldingMainHand && !var7) {
               if (isHoldingMainHand && isRightHanded || var7 && !isRightHanded) {
                  humanoidModel.rightArmPose = ArmPose.THROW_SPEAR;
               }

               if (isHoldingMainHand && !isRightHanded || var7 && isRightHanded) {
                  humanoidModel.leftArmPose = ArmPose.THROW_SPEAR;
               }
            }
         }
      }
   }

   @EventBusSubscriber
   public static class UmbrellaCommonEvents {
      @SubscribeEvent
      public static void onPlayerHurt(LivingIncomingDamageEvent event) {
         if (event.getEntity() instanceof Player player && !player.getCommandSenderWorld().isClientSide()) {
            Level level = player.getCommandSenderWorld();
            ItemStack stack = ItemStack.EMPTY;

            for (InteractionHand hand : InteractionHand.values()) {
               ItemStack entry = player.getItemInHand(hand);
               if (entry.getItem() instanceof UmbrellaItem) {
                  stack = entry;
                  break;
               }
            }

            if (!stack.isEmpty()) {
               UmbrellaItem relic = (UmbrellaItem)stack.getItem();
               if (!level.isClientSide()
                  && player.isUsingItem()
                  && event.getSource().getEntity() instanceof LivingEntity source
                  && !(source.position().subtract(player.position()).normalize().dot(player.getLookAngle().normalize()) < 0.65F)
                  && !player.getCooldowns().isOnCooldown(stack.getItem())) {
                  if (event.getSource().getDirectEntity() instanceof AbstractArrow arrow) {
                     NetworkHandler.sendToClientsTrackingEntityAndSelf(
                        new S2CEntityMotionPacket(arrow.getId(), arrow.getX(), arrow.getY(), arrow.getZ()), player
                     );
                  }

                  if (source.getMainHandItem().getItem() instanceof AxeItem) {
                     player.getCooldowns().addCooldown(relic, 110);
                     player.stopUsingItem();
                     level.playSound(
                        null, player.blockPosition(), SoundEvents.ALLAY_DEATH, SoundSource.MASTER, 0.3F, 1.0F + player.getRandom().nextFloat() * 0.25F
                     );
                  } else {
                     level.playSound(
                        null, player.blockPosition(), SoundEvents.ALLAY_HURT, SoundSource.MASTER, 0.3F, 1.0F + player.getRandom().nextFloat() * 0.25F
                     );
                  }

                  event.setCanceled(true);
                  relic.spreadRelicExperience(player, stack, 1);

                  for (LivingEntity entity : level.getEntitiesOfClass(
                     LivingEntity.class, player.getBoundingBox().inflate(3.0), entityx -> !entityx.getUUID().equals(player.getUUID()) && entityx.isAlive()
                  )) {
                     Vec3 motion = entity.position().subtract(player.position()).normalize().scale(0.4F + relic.getStatValue(stack, "glider", "count") * 0.2F);
                     if (entity instanceof Player serverPlayer) {
                        NetworkHandler.sendToClient(new PacketPlayerMotion(motion.x(), motion.y() / 5.0, motion.z()), (ServerPlayer)serverPlayer);
                     } else {
                        entity.setDeltaMovement(motion.x(), motion.y() / 5.0, motion.z());
                     }

                     Vec3 pos = source.position().add(new Vec3(0.0, source.getBbHeight() / 2.0F, 0.0));
                     Vec3 velocity = motion.normalize().scale(0.5);
                     ((ServerLevel)level).sendParticles(ParticleTypes.CLOUD, pos.x, pos.y, pos.z, 10, velocity.x, velocity.y, velocity.z, 0.1F);
                  }
               }
            }
         }
      }
   }
}
