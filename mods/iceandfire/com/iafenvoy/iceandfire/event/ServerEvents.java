package com.iafenvoy.iceandfire.event;

import com.iafenvoy.iceandfire.IceAndFire;
import com.iafenvoy.iceandfire.config.IafCommonConfig;
import com.iafenvoy.iceandfire.data.component.ChainData;
import com.iafenvoy.iceandfire.entity.AmphithereEntity;
import com.iafenvoy.iceandfire.entity.CockatriceEntity;
import com.iafenvoy.iceandfire.entity.CyclopsEntity;
import com.iafenvoy.iceandfire.entity.DragonBaseEntity;
import com.iafenvoy.iceandfire.entity.GhostEntity;
import com.iafenvoy.iceandfire.entity.HydraEntity;
import com.iafenvoy.iceandfire.entity.HydraHeadEntity;
import com.iafenvoy.iceandfire.entity.MultipartPartEntity;
import com.iafenvoy.iceandfire.entity.StoneStatueEntity;
import com.iafenvoy.iceandfire.entity.ai.EntitySheepAIFollowCyclopsGoal;
import com.iafenvoy.iceandfire.entity.ai.VillagerAIFearUntamedGoal;
import com.iafenvoy.iceandfire.entity.util.IAnimalFear;
import com.iafenvoy.iceandfire.entity.util.IVillagerFear;
import com.iafenvoy.iceandfire.entity.util.dragon.DragonUtils;
import com.iafenvoy.iceandfire.item.ChainItem;
import com.iafenvoy.iceandfire.item.DragonHornItem;
import com.iafenvoy.iceandfire.item.armor.DragonScaleArmorItem;
import com.iafenvoy.iceandfire.item.armor.DragonSteelArmorItem;
import com.iafenvoy.iceandfire.item.armor.TrollArmorItem;
import com.iafenvoy.iceandfire.item.component.StoneStatusComponent;
import com.iafenvoy.iceandfire.network.payload.PlayerHitMultipartC2SPayload;
import com.iafenvoy.iceandfire.registry.IafBlocks;
import com.iafenvoy.iceandfire.registry.IafDamageTypes;
import com.iafenvoy.iceandfire.registry.IafDataComponents;
import com.iafenvoy.iceandfire.registry.IafEntities;
import com.iafenvoy.iceandfire.registry.IafItems;
import com.iafenvoy.iceandfire.registry.tag.IafEntityTags;
import com.iafenvoy.uranus.object.RegistryHelper;
import com.iafenvoy.uranus.util.RandomHelper;
import dev.architectury.event.EventResult;
import dev.architectury.networking.NetworkManager;
import dev.architectury.utils.value.IntValue;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.CombatEntry;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.AbstractChestBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

public final class ServerEvents {
   public static final UUID ALEX_UUID = UUID.fromString("71363abe-fd03-49c9-940d-aae8b8209b7c");
   public static final String BOLT_DONT_DESTROY_LOOT = "iceandfire.bolt_skip_loot";
   private static final Predicate<LivingEntity> VILLAGER_FEAR = entity -> entity instanceof IVillagerFear fear && fear.shouldFear();

   private static void signalChickenAlarm(LivingEntity chicken, LivingEntity attacker) {
      float d0 = ((Integer)IafCommonConfig.INSTANCE.cockatrice.chickenSearchLength.getValue()).intValue();
      List<CockatriceEntity> list = chicken.level()
         .getEntitiesOfClass(
            CockatriceEntity.class,
            new AABB(chicken.getX(), chicken.getY(), chicken.getZ(), chicken.getX() + 1.0, chicken.getY() + 1.0, chicken.getZ() + 1.0).inflate(d0, 10.0, d0)
         );
      if (!list.isEmpty()) {
         for (CockatriceEntity cockatrice : list) {
            if (!(attacker instanceof CockatriceEntity) && !DragonUtils.hasSameOwner(cockatrice, attacker)) {
               if (attacker instanceof Player player) {
                  if (!player.isCreative() && !cockatrice.isOwnedBy(player)) {
                     cockatrice.setTarget(player);
                  }
               } else {
                  cockatrice.setTarget(attacker);
               }
            }
         }
      }
   }

   private static void signalAmphithereAlarm(LivingEntity villager, LivingEntity attacker) {
      float d0 = ((Double)IafCommonConfig.INSTANCE.amphithere.villagerSearchLength.getValue()).floatValue();
      List<AmphithereEntity> list = villager.level()
         .getEntitiesOfClass(
            AmphithereEntity.class,
            new AABB(villager.getX() - 1.0, villager.getY() - 1.0, villager.getZ() - 1.0, villager.getX() + 1.0, villager.getY() + 1.0, villager.getZ() + 1.0)
               .inflate(d0, d0, d0)
         );
      if (!list.isEmpty()) {
         for (Entity entity : list) {
            if (entity instanceof AmphithereEntity amphithere && !(attacker instanceof AmphithereEntity) && !DragonUtils.hasSameOwner(amphithere, attacker)) {
               if (attacker instanceof Player player) {
                  if (!player.isCreative() && !amphithere.isOwnedBy(player)) {
                     amphithere.setTarget(player);
                  }
               } else {
                  amphithere.setTarget(attacker);
               }
            }
         }
      }
   }

   public static boolean isRidingOrBeingRiddenBy(Entity first, Entity entityIn) {
      if (first != null && entityIn != null) {
         for (Entity entity : first.getPassengers()) {
            if (entity.equals(entityIn) || isRidingOrBeingRiddenBy(entity, entityIn)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static float onEntityDamage(LivingEntity entity, DamageSource source, float amount) {
      if (source.is(DamageTypeTags.IS_PROJECTILE)) {
         float multi = 1.0F;
         if (entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof TrollArmorItem) {
            multi -= 0.1F;
         }

         if (entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof TrollArmorItem) {
            multi -= 0.3F;
         }

         if (entity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof TrollArmorItem) {
            multi -= 0.2F;
         }

         if (entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof TrollArmorItem) {
            multi -= 0.1F;
         }

         amount *= multi;
      }

      if (source.is(IafDamageTypes.DRAGON_FIRE_TYPE) || source.is(IafDamageTypes.DRAGON_ICE_TYPE) || source.is(IafDamageTypes.DRAGON_LIGHTNING_TYPE)) {
         float multi = 1.0F;
         if (entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof DragonScaleArmorItem
            || entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof DragonSteelArmorItem) {
            multi -= 0.1F;
         }

         if (entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof DragonScaleArmorItem
            || entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof DragonSteelArmorItem) {
            multi -= 0.3F;
         }

         if (entity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof DragonScaleArmorItem
            || entity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof DragonSteelArmorItem) {
            multi -= 0.2F;
         }

         if (entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof DragonScaleArmorItem
            || entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof DragonSteelArmorItem) {
            multi -= 0.1F;
         }

         amount *= multi;
      }

      return amount;
   }

   public static void onLivingSetTarget(Entity tracking, ServerPlayer player) {
      if (tracking instanceof LivingEntity target) {
         if (target.getType().is(IafEntityTags.CHICKENS)) {
            signalChickenAlarm(target, player);
         } else if (DragonUtils.isVillager(target)) {
            signalAmphithereAlarm(target, player);
         }
      }
   }

   public static EventResult onPlayerAttack(Player player, Level world, Entity entity, InteractionHand hand, @Nullable EntityHitResult hitResult) {
      if (entity != null && entity.getType().is(IafEntityTags.SHEEP)) {
         float dist = ((Integer)IafCommonConfig.INSTANCE.cyclops.sheepSearchLength.getValue()).intValue();
         List<Entity> list = entity.level().getEntities(entity, entity.getBoundingBox().inflate(dist, dist, dist));
         if (!list.isEmpty()) {
            for (Entity e : list) {
               if (e instanceof CyclopsEntity cyclops && !cyclops.isBlinded() && !player.isCreative()) {
                  cyclops.setTarget(player);
               }
            }
         }
      }

      if (entity instanceof StoneStatueEntity statue) {
         statue.setHealth(statue.getMaxHealth());
         if (player != null) {
            ItemStack stack = player.getMainHandItem();
            entity.playSound(SoundEvents.STONE_BREAK, 2.0F, 0.5F + (float)(RandomHelper.nextDouble(-1.0, 1.0) * 0.2 + 0.5));
            if (stack.is(ItemTags.PICKAXES)) {
               statue.setCrackAmount(statue.getCrackAmount() + 1);
               if (statue.getCrackAmount() > 9) {
                  CompoundTag writtenTag = new CompoundTag();
                  entity.saveWithoutId(writtenTag);
                  entity.playSound(SoundEvents.STONE_BREAK, 2.0F, (float)(RandomHelper.nextDouble(-1.0, 1.0) * 0.2 + 0.5));
                  entity.remove(RemovalReason.KILLED);
                  if (EnchantmentHelper.getItemEnchantmentLevel(RegistryHelper.getEnchantment(world.registryAccess(), Enchantments.SILK_TOUCH), stack) > 0) {
                     ItemStack statuette = new ItemStack((ItemLike)IafItems.STONE_STATUE.get());
                     statuette.set(
                        (DataComponentType)IafDataComponents.STONE_STATUS.get(),
                        new StoneStatusComponent(
                           statue.getTrappedEntityTypeString().equalsIgnoreCase("minecraft:player"), statue.getTrappedEntityTypeString(), writtenTag
                        )
                     );
                     if (!statue.level().isClientSide) {
                        statue.spawnAtLocation(statuette, 1.0F);
                     }
                  } else if (!statue.level().isClientSide) {
                     statue.spawnAtLocation(Blocks.COBBLESTONE, 2 + player.getRandom().nextInt(4));
                  }

                  statue.remove(RemovalReason.KILLED);
               }

               return EventResult.interruptTrue();
            }
         }

         return EventResult.interruptDefault();
      } else {
         if (entity instanceof MultipartPartEntity mutlipartPart) {
            Entity parent = mutlipartPart.getParent();

            try {
               if (parent != null) {
                  player.attack(parent);
               }
            } catch (Exception e) {
               IceAndFire.LOGGER.warn("Exception thrown while interacting with entity.", e);
            }

            int extraData = 0;
            if (mutlipartPart instanceof HydraHeadEntity hydraHead && parent instanceof HydraEntity hydra) {
               extraData = hydraHead.headIndex;
               hydra.triggerHeadFlags(extraData);
            }

            if (mutlipartPart.level().isClientSide && parent != null) {
               NetworkManager.sendToServer(new PlayerHitMultipartC2SPayload(parent.getId(), extraData));
            }
         }

         if (entity instanceof LivingEntity livingEntity) {
            if (entity.getType().is(IafEntityTags.CHICKENS)) {
               signalChickenAlarm(livingEntity, player);
            } else if (DragonUtils.isVillager(entity)) {
               signalAmphithereAlarm(livingEntity, player);
            }
         }

         return EventResult.pass();
      }
   }

   public static EventResult onEntityDie(LivingEntity entity, DamageSource damageSource) {
      if (entity.level().isClientSide) {
         return EventResult.pass();
      }

      ChainData chainData = ChainData.get(entity);
      if (!chainData.getChainedTo().isEmpty()) {
         ItemEntity entityitem = new ItemEntity(
            entity.level(), entity.getX(), entity.getY() + 1.0, entity.getZ(), new ItemStack((ItemLike)IafItems.CHAIN.get(), chainData.getChainedTo().size())
         );
         entityitem.setDefaultPickUpDelay();
         entity.level().addFreshEntity(entityitem);
         chainData.clearChains();
      }

      if (entity.getUUID().equals(ALEX_UUID)) {
         entity.spawnAtLocation(new ItemStack((ItemLike)IafItems.WEEZER_BLUE_ALBUM.get()), 1.0F);
      }

      if (entity instanceof Player && (Boolean)IafCommonConfig.INSTANCE.ghost.fromPlayerDeaths.getValue()) {
         Entity attacker = entity.getLastHurtByMob();
         if (attacker instanceof Player && entity.getRandom().nextInt(3) == 0) {
            CombatTracker combat = entity.getCombatTracker();
            CombatEntry entry = combat.getMostSignificantFall();
            boolean flag = entry != null
               && (entry.source().is(DamageTypes.FALL) || entry.source().is(DamageTypes.DROWN) || entry.source().is(DamageTypes.LAVA));
            if (entity.hasEffect(MobEffects.POISON)) {
               flag = true;
            }

            if (flag) {
               Level world = entity.level();
               GhostEntity ghost = (GhostEntity)((EntityType)IafEntities.GHOST.get()).create(world);
               assert ghost != null;
               ghost.copyPosition(entity);
               if (world instanceof ServerLevelAccessor serverWorldAccess) {
                  ghost.finalizeSpawn(serverWorldAccess, world.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.SPAWNER, null);
                  world.addFreshEntity(ghost);
               }

               ghost.setDaytimeMode(true);
            }
         }
      }

      return EventResult.pass();
   }

   public static EventResult onEntityInteract(Player player, Entity entity, InteractionHand hand) {
      if (entity instanceof LivingEntity target && !player.isSpectator()) {
         ChainData chainData = ChainData.get(target);
         if (chainData.isChainedTo(entity.getUUID())) {
            chainData.removeChain(entity.getUUID());
            if (!player.level().isClientSide) {
               entity.spawnAtLocation((ItemLike)IafItems.CHAIN.get(), 1);
            }

            return EventResult.interruptTrue();
         }
      }

      if (entity instanceof MultipartPartEntity multipart
         && player.getItemInHand(hand).getItem() instanceof DragonHornItem horn
         && multipart.getParent() instanceof LivingEntity living) {
         horn.interactLivingEntity(player.getItemInHand(hand), player, living, hand);
      }

      return EventResult.pass();
   }

   public static EventResult onPlayerRightClick(Player player, InteractionHand hand, BlockPos pos, Direction face) {
      Level world = player.level();
      if (world.getBlockState(pos).getBlock() instanceof AbstractChestBlock && !player.isCreative()) {
         float dist = ((Integer)IafCommonConfig.INSTANCE.dragon.goldSearchLength.getValue()).intValue();
         List<Entity> list = world.getEntities(player, player.getBoundingBox().inflate(dist, dist, dist));
         if (!list.isEmpty()) {
            for (Entity entity : list) {
               if (entity instanceof DragonBaseEntity dragon && !dragon.isTame() && !dragon.isModelDead() && !dragon.isOwnedBy(player)) {
                  dragon.setInSittingPose(false);
                  dragon.setOrderedToSit(false);
                  dragon.setTarget(player);
               }
            }
         }
      }

      if (world.getBlockState(pos).getBlock() instanceof WallBlock) {
         ChainItem.attachToFence(player, world, pos);
      }

      return EventResult.pass();
   }

   public static EventResult onBreakBlock(Level world, BlockPos pos, BlockState state, Player player, @Nullable IntValue xp) {
      if (player != null
         && (
            state.getBlock() instanceof AbstractChestBlock
               || state.is((Block)IafBlocks.GOLD_PILE.get())
               || state.is((Block)IafBlocks.SILVER_PILE.get())
               || state.is((Block)IafBlocks.COPPER_PILE.get())
         )) {
         float dist = ((Integer)IafCommonConfig.INSTANCE.dragon.goldSearchLength.getValue()).intValue();
         List<Entity> list = world.getEntities(player, player.getBoundingBox().inflate(dist, dist, dist));
         if (list.isEmpty()) {
            return EventResult.pass();
         }

         for (Entity entity : list) {
            if (entity instanceof DragonBaseEntity dragon && !dragon.isTame() && !dragon.isModelDead() && !dragon.isOwnedBy(player) && !player.isCreative()) {
               dragon.setInSittingPose(false);
               dragon.setOrderedToSit(false);
               dragon.setTarget(player);
            }
         }
      }

      return EventResult.pass();
   }

   public static void onPlayerLeaveEvent(Player player) {
      if (player != null && !player.getPassengers().isEmpty()) {
         for (Entity entity : player.getPassengers()) {
            entity.stopRiding();
         }
      }
   }

   public static boolean onEntityJoinWorld(Entity entity, Level world) {
      if (entity instanceof Mob mob) {
         try {
            if (mob.getType().is(IafEntityTags.SHEEP) && mob instanceof Animal animal) {
               animal.goalSelector.addGoal(8, new EntitySheepAIFollowCyclopsGoal(animal, 1.2));
            }

            if (mob.getType().is(IafEntityTags.VILLAGERS) && (Boolean)IafCommonConfig.INSTANCE.dragon.villagersFear.getValue()) {
               mob.goalSelector.addGoal(1, new VillagerAIFearUntamedGoal((PathfinderMob)mob, LivingEntity.class, 8.0F, 0.8, 0.8, VILLAGER_FEAR));
            }

            if (mob.getType().is(IafEntityTags.FEAR_DRAGONS) && (Boolean)IafCommonConfig.INSTANCE.dragon.animalsFear.getValue()) {
               mob.goalSelector
                  .addGoal(
                     1,
                     new VillagerAIFearUntamedGoal(
                        (PathfinderMob)mob, LivingEntity.class, 30.0F, 1.0, 0.5, ex -> ex instanceof IAnimalFear fear && fear.shouldAnimalsFear(mob)
                     )
                  );
            }
         } catch (Exception e) {
            IceAndFire.LOGGER.warn("Tried to add unique behaviors to vanilla mobs and encountered an error");
         }
      }

      return true;
   }

   public static EventResult onLivingHurt(LivingEntity entity, DamageSource source, float amount) {
      return source.is(DamageTypes.LIGHTNING_BOLT)
            && entity.getItemBySlot(EquipmentSlot.HEAD).is((Item)IafItems.DRAGONSTEEL_LIGHTNING_HELMET.get())
            && entity.getItemBySlot(EquipmentSlot.CHEST).is((Item)IafItems.DRAGONSTEEL_LIGHTNING_CHESTPLATE.get())
            && entity.getItemBySlot(EquipmentSlot.LEGS).is((Item)IafItems.DRAGONSTEEL_LIGHTNING_LEGGINGS.get())
            && entity.getItemBySlot(EquipmentSlot.FEET).is((Item)IafItems.DRAGONSTEEL_LIGHTNING_BOOTS.get())
         ? EventResult.interruptFalse()
         : EventResult.pass();
   }
}
