package twilightforest.events;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet.Named;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.Tags.EntityTypes;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent.Applicable;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent.Applicable.Result;
import net.neoforged.neoforge.event.level.BlockEvent.BreakEvent;
import twilightforest.beanification.Component;
import twilightforest.beanification.PostConstruct;
import twilightforest.block.GiantBlock;
import twilightforest.components.entity.GiantPickaxeMiningAttachment;
import twilightforest.data.tags.BlockTagGenerator;
import twilightforest.init.TFDataAttachments;
import twilightforest.init.TFItems;
import twilightforest.item.GiantPickItem;
import twilightforest.item.MazebreakerPickItem;
import twilightforest.item.MinotaurAxeItem;
import twilightforest.item.OreMagnetItem;
import twilightforest.loot.modifiers.GiantToolGroupingModifier;

@Component
public class ToolEvents {
   private static final int KNIGHTMETAL_BONUS_DAMAGE = 2;
   private static final int MINOTAUR_AXE_BONUS_DAMAGE = 7;

   @PostConstruct
   private void setup() {
      NeoForge.EVENT_BUS.addListener(this::onEnderBowHit);
      NeoForge.EVENT_BUS.addListener(this::fieryToolSetFire);
      NeoForge.EVENT_BUS.addListener(this::doKnightmetalToolLogic);
      NeoForge.EVENT_BUS.addListener(this::addExtraAxeChargingDamage);
      NeoForge.EVENT_BUS.addListener(this::damageNonMazebreakerToolsMore);
      NeoForge.EVENT_BUS.addListener(this::preventFatigueWithPocketWatch);
      NeoForge.EVENT_BUS.addListener(this::handleGiantPickaxeMining);
      NeoForge.EVENT_BUS.addListener(this::refreshOreMagnetCache);
   }

   private void onEnderBowHit(ProjectileImpactEvent evt) {
      Projectile arrow = evt.getProjectile();
      if (arrow.getOwner() instanceof Player player
         && evt.getRayTraceResult() instanceof EntityHitResult result
         && result.getEntity() instanceof LivingEntity living
         && arrow.getOwner() != result.getEntity()
         && !result.getEntity().getType().is(EntityTypes.BOSSES)
         && arrow.getPersistentData().contains("twilightforest:ender")) {
         double sourceX = player.getX();
         double sourceY = player.getY();
         double sourceZ = player.getZ();
         float sourceYaw = player.getYRot();
         float sourcePitch = player.getXRot();
         Entity playerVehicle = player.getVehicle();
         player.setYRot(living.getYRot());
         player.teleportTo(living.getX(), living.getY(), living.getZ());
         player.invulnerableTime = 40;
         player.level().broadcastEntityEvent(player, (byte)46);
         if (living.isPassenger() && living.getVehicle() != null) {
            player.startRiding(living.getVehicle(), true);
            living.stopRiding();
         }

         player.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
         living.setYRot(sourceYaw);
         living.setXRot(sourcePitch);
         living.teleportTo(sourceX, sourceY, sourceZ);
         living.level().broadcastEntityEvent(player, (byte)46);
         if (playerVehicle != null) {
            living.startRiding(playerVehicle, true);
            player.stopRiding();
         }

         living.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
      }
   }

   private void fieryToolSetFire(LivingIncomingDamageEvent event) {
      if (event.getSource().getEntity() instanceof LivingEntity living
         && (living.getMainHandItem().is((Item)TFItems.FIERY_SWORD.get()) || living.getMainHandItem().is((Item)TFItems.FIERY_PICKAXE.get()))
         && !event.getEntity().fireImmune()) {
         event.getEntity().igniteForSeconds(1.0F);
      }
   }

   private void doKnightmetalToolLogic(LivingIncomingDamageEvent event) {
      if (!event.isCanceled()) {
         LivingEntity target = event.getEntity();
         DamageContainer container = event.getContainer();
         if (!target.level().isClientSide() && container.getSource().getDirectEntity() instanceof LivingEntity living) {
            ItemStack weapon = living.getMainHandItem();
            if (!weapon.isEmpty()) {
               if (target.getArmorValue() > 0 && (weapon.is((Item)TFItems.KNIGHTMETAL_PICKAXE.get()) || weapon.is((Item)TFItems.KNIGHTMETAL_SWORD.get()))) {
                  if (target.getArmorCoverPercentage() > 0.0F) {
                     int moreBonus = (int)(2.0F * target.getArmorCoverPercentage());
                     container.setNewDamage(container.getNewDamage() + moreBonus);
                  } else {
                     container.setNewDamage(container.getNewDamage() + 2.0F);
                  }

                  ((ServerLevel)target.level()).getChunkSource().broadcastAndSend(target, new ClientboundAnimatePacket(target, 5));
               } else if (target.getArmorValue() == 0 && weapon.is((Item)TFItems.KNIGHTMETAL_AXE.get())) {
                  container.setNewDamage(container.getOriginalDamage() + 2.0F);
                  ((ServerLevel)target.level()).getChunkSource().broadcastAndSend(target, new ClientboundAnimatePacket(target, 5));
               }
            }
         }
      }
   }

   private void addExtraAxeChargingDamage(LivingIncomingDamageEvent event) {
      if (!event.isCanceled()) {
         LivingEntity target = event.getEntity();
         DamageContainer container = event.getContainer();
         if (!target.level().isClientSide() && container.getSource().getDirectEntity() instanceof LivingEntity living && living.isSprinting()) {
            ItemStack weapon = living.getMainHandItem();
            if (!weapon.isEmpty() && weapon.getItem() instanceof MinotaurAxeItem) {
               container.setNewDamage(container.getNewDamage() + 7.0F);
               ((ServerLevel)target.level()).getChunkSource().broadcastAndSend(target, new ClientboundAnimatePacket(target, 5));
            }
         }
      }
   }

   private void damageNonMazebreakerToolsMore(BreakEvent event) {
      ItemStack stack = event.getPlayer().getMainHandItem();
      if (event.getState().is(BlockTagGenerator.MAZEBREAKER_ACCELERATED) && stack.isDamageableItem() && !(stack.getItem() instanceof MazebreakerPickItem)) {
         stack.hurtAndBreak(16, event.getPlayer(), EquipmentSlot.MAINHAND);
      }
   }

   private void preventFatigueWithPocketWatch(Applicable event) {
      if (event.getApplicationResult()
         && event.getEffectInstance().is(MobEffects.DIG_SLOWDOWN)
         && event.getEntity().isHolding((Item)TFItems.POCKET_WATCH.get())) {
         event.setResult(Result.DO_NOT_APPLY);
      }
   }

   private void handleGiantPickaxeMining(BreakEvent event) {
      BlockPos pos = event.getPos();
      BlockState state = event.getState();
      if (event.getPlayer() instanceof ServerPlayer player && canHarvestWithGiantPick(player, state, pos)) {
         GiantPickaxeMiningAttachment attachment = (GiantPickaxeMiningAttachment)player.getData(TFDataAttachments.GIANT_PICKAXE_MINING);
         if (shouldBreakGiantBlock(player, attachment)) {
            attachment.setBreaking(true);
            Builder builder = new Builder(player.serverLevel())
               .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
               .withParameter(LootContextParams.BLOCK_STATE, state)
               .withOptionalParameter(LootContextParams.THIS_ENTITY, player)
               .withParameter(LootContextParams.TOOL, player.getMainHandItem());
            List<ItemStack> drops = state.getDrops(builder);
            if (!drops.isEmpty() && drops.getFirst().getItem() instanceof BlockItem block) {
               boolean allTheSame = GiantToolGroupingModifier.CONVERSIONS.containsKey(block.getBlock());
               if (allTheSame) {
                  for (BlockPos offsetPos : GiantBlock.getVolume(pos)) {
                     if (!player.level().getBlockState(offsetPos).is(state.getBlock())) {
                        allTheSame = false;
                        break;
                     }
                  }
               }

               attachment.setGiantBlockConversion(allTheSame ? 64 : 0);
            }

            event.setCanceled(true);
            player.level().levelEvent(2001, pos, Block.getId(state));
            player.gameMode.destroyBlock(pos);

            for (BlockPos offsetPos : GiantBlock.getVolume(pos)) {
               if (!offsetPos.equals(pos) && player.level().getBlockState(offsetPos).is(state.getBlock())) {
                  BlockPos newPos = new BlockPos(offsetPos);
                  player.level().levelEvent(2001, newPos, Block.getId(player.level().getBlockState(newPos)));
                  player.gameMode.destroyBlock(newPos);
               }
            }

            attachment.setBreaking(false);
         }
      }
   }

   private static boolean canHarvestWithGiantPick(Player player, BlockState state, BlockPos pos) {
      return player.getMainHandItem().getItem() instanceof GiantPickItem && EventHooks.doPlayerHarvestCheck(player, state, player.level(), pos);
   }

   private static boolean shouldBreakGiantBlock(Player player, GiantPickaxeMiningAttachment attachment) {
      return attachment.getMining() == player.level().getGameTime() && !attachment.getBreaking();
   }

   private void refreshOreMagnetCache(TagsUpdatedEvent event) {
      OreMagnetItem.MAGNET_ORE_TO_BLOCK_REPLACEMENTS.clear();
      OreMagnetItem.TREE_ORE_TO_BLOCK_REPLACEMENTS.clear();

      for (TagKey<Block> tag : BuiltInRegistries.BLOCK.getTagNames().filter(location -> location.location().getNamespace().equals("c")).toList()) {
         if (tag.location().getPath().contains("ores_in_ground/")) {
            String oreground = tag.location().getPath().substring(15);
            if (BuiltInRegistries.BLOCK
               .getTagNames()
               .filter(location -> location.location().getNamespace().equals("c"))
               .anyMatch(blockTagKey -> blockTagKey.location().getPath().equals("ore_bearing_ground/" + oreground))) {
               ((Named)BuiltInRegistries.BLOCK
                     .getTag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "ore_bearing_ground/" + oreground)))
                     .get())
                  .forEach(ground -> ((Named)BuiltInRegistries.BLOCK.getTag(tag).get()).forEach(ore -> {
                     if (!((Block)ore.value()).defaultBlockState().is(BlockTagGenerator.ORE_MAGNET_IGNORE)) {
                        OreMagnetItem.MAGNET_ORE_TO_BLOCK_REPLACEMENTS.put((Block)ore.value(), (Block)ground.value());
                     }

                     if (!((Block)ore.value()).defaultBlockState().is(BlockTagGenerator.MINING_CORE_EXCLUDED)) {
                        OreMagnetItem.TREE_ORE_TO_BLOCK_REPLACEMENTS.put((Block)ore.value(), (Block)ground.value());
                     }
                  }));
            }
         }
      }

      if (!Blocks.ANCIENT_DEBRIS.defaultBlockState().is(BlockTagGenerator.ORE_MAGNET_IGNORE)
         && !OreMagnetItem.MAGNET_ORE_TO_BLOCK_REPLACEMENTS.containsKey(Blocks.ANCIENT_DEBRIS)) {
         OreMagnetItem.MAGNET_ORE_TO_BLOCK_REPLACEMENTS.put(Blocks.ANCIENT_DEBRIS, Blocks.NETHERRACK);
      }

      if (!Blocks.ANCIENT_DEBRIS.defaultBlockState().is(BlockTagGenerator.MINING_CORE_EXCLUDED)
         && !OreMagnetItem.TREE_ORE_TO_BLOCK_REPLACEMENTS.containsKey(Blocks.ANCIENT_DEBRIS)) {
         OreMagnetItem.TREE_ORE_TO_BLOCK_REPLACEMENTS.put(Blocks.ANCIENT_DEBRIS, Blocks.NETHERRACK);
      }
   }
}
