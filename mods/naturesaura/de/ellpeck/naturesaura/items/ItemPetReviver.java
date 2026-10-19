package de.ellpeck.naturesaura.items;

import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.packet.PacketHandler;
import de.ellpeck.naturesaura.packet.PacketParticles;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayer.RespawnPosAngle;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.neoforged.neoforge.event.tick.EntityTickEvent.Post;

public class ItemPetReviver extends ItemImpl {
   public ItemPetReviver() {
      super("pet_reviver");
      NeoForge.EVENT_BUS.register(new ItemPetReviver.Events());
   }

   private static Optional<RespawnPosAngle> findRespawnAndUseSpawnBlock(ServerLevel level, BlockPos pos, float angle, boolean forced, boolean keepInventory) {
      BlockState blockstate = level.getBlockState(pos);
      Block block = blockstate.getBlock();
      if (block instanceof RespawnAnchorBlock
         && (forced || (Integer)blockstate.getValue(RespawnAnchorBlock.CHARGE) > 0)
         && RespawnAnchorBlock.canSetSpawn(level)) {
         Optional<Vec3> optional = RespawnAnchorBlock.findStandUpPosition(EntityType.PLAYER, level, pos);
         if (!forced && !keepInventory && optional.isPresent()) {
            level.setBlock(pos, (BlockState)blockstate.setValue(RespawnAnchorBlock.CHARGE, (Integer)blockstate.getValue(RespawnAnchorBlock.CHARGE) - 1), 3);
         }

         return optional.map(p_348139_ -> RespawnPosAngle.of(p_348139_, pos));
      } else {
         if (block instanceof BedBlock && BedBlock.canSetSpawn(level)) {
            return BedBlock.findStandUpPosition(EntityType.PLAYER, level, pos, (Direction)blockstate.getValue(BedBlock.FACING), angle)
               .map(p_348148_ -> RespawnPosAngle.of(p_348148_, pos));
         }

         if (!forced) {
            return blockstate.getRespawnPosition(EntityType.PLAYER, level, pos, angle);
         }

         boolean flag = block.isPossibleToRespawnInThis(blockstate);
         BlockState blockstate1 = level.getBlockState(pos.above());
         boolean flag1 = blockstate1.getBlock().isPossibleToRespawnInThis(blockstate1);
         return flag && flag1 ? Optional.of(new RespawnPosAngle(new Vec3(pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5), angle)) : Optional.empty();
      }
   }

   private static class Events {
      @SubscribeEvent
      public void onEntityTick(Post event) {
         Entity entity = event.getEntity();
         if (!entity.level().isClientSide && entity.level().getGameTime() % 20L == 0L && entity instanceof TamableAnimal tameable) {
            if (tameable.isTame() && tameable.getPersistentData().getBoolean("naturesaura:pet_reviver")) {
               LivingEntity owner = tameable.getOwner();
               if (owner != null && !(owner.distanceToSqr(tameable) > 25.0)) {
                  if (entity.level().random.nextFloat() >= 0.65F) {
                     ((ServerLevel)entity.level())
                        .sendParticles(
                           ParticleTypes.HEART,
                           entity.getX() + entity.level().random.nextGaussian() * 0.25,
                           entity.getEyeY() + entity.level().random.nextGaussian() * 0.25,
                           entity.getZ() + entity.level().random.nextGaussian() * 0.25,
                           entity.level().random.nextInt(2) + 1,
                           0.0,
                           0.0,
                           0.0,
                           0.0
                        );
                  }
               }
            }
         }
      }

      @SubscribeEvent
      public void onEntityInteract(EntityInteractSpecific event) {
         Entity target = event.getTarget();
         if (target instanceof TamableAnimal && ((TamableAnimal)target).isTame()) {
            if (!target.getPersistentData().getBoolean("naturesaura:pet_reviver")) {
               ItemStack stack = event.getEntity().getItemInHand(event.getHand());
               if (stack.getItem() == ModItems.PET_REVIVER) {
                  target.getPersistentData().putBoolean("naturesaura:pet_reviver", true);
                  if (!target.level().isClientSide) {
                     stack.shrink(1);
                  }

                  event.setCancellationResult(InteractionResult.SUCCESS);
                  event.setCanceled(true);
               }
            }
         }
      }

      @SubscribeEvent(priority = EventPriority.LOWEST)
      public void onLivingDeath(LivingDeathEvent event) {
         LivingEntity entity = event.getEntity();
         if (!entity.level().isClientSide && entity instanceof TamableAnimal tameable) {
            if (tameable.isTame() && tameable.getPersistentData().getBoolean("naturesaura:pet_reviver")) {
               ServerLevel spawnLevel = tameable.level().getServer().overworld();
               Vec3 spawn = Vec3.atBottomCenterOf(spawnLevel.getSharedSpawnPos());
               LivingEntity owner = tameable.getOwner();
               if (owner instanceof ServerPlayer player) {
                  BlockPos pos = player.getRespawnPosition();
                  if (pos != null) {
                     float f = player.getRespawnAngle();
                     boolean b = player.isRespawnForced();
                     Optional<RespawnPosAngle> bed = ItemPetReviver.findRespawnAndUseSpawnBlock((ServerLevel)player.level(), pos, f, b, false);
                     if (bed.isPresent()) {
                        spawnLevel = (ServerLevel)player.level();
                        spawn = bed.get().position();
                     }
                  }
               }

               PacketHandler.sendToAllAround(
                  tameable.level(),
                  tameable.blockPosition(),
                  32,
                  new PacketParticles((float)tameable.getX(), (float)tameable.getEyeY(), (float)tameable.getZ(), PacketParticles.Type.PET_REVIVER, 12731933)
               );
               TamableAnimal spawnedPet = tameable;
               if (tameable.level() != spawnLevel) {
                  tameable.remove(RemovalReason.DISCARDED);
                  spawnedPet = (TamableAnimal)tameable.getType().create(spawnLevel);
               }

               spawnedPet.restoreFrom(tameable);
               spawnedPet.setDeltaMovement(0.0, 0.0, 0.0);
               spawnedPet.moveTo(spawn.x, spawn.y, spawn.z, tameable.getYRot(), tameable.getXRot());

               while (!spawnLevel.noCollision(spawnedPet)) {
                  spawnedPet.setPos(spawnedPet.getX(), spawnedPet.getY() + 1.0, spawnedPet.getZ());
               }

               spawnedPet.setHealth(spawnedPet.getMaxHealth());
               spawnedPet.getNavigation().stop();
               spawnedPet.setInSittingPose(true);
               spawnedPet.setJumping(false);
               spawnedPet.setTarget(null);
               if (tameable.level() != spawnLevel) {
                  spawnLevel.addFreshEntity(spawnedPet);
                  tameable.remove(RemovalReason.DISCARDED);
               }

               BlockPos auraPos = IAuraChunk.getHighestSpot(spawnLevel, spawnedPet.blockPosition(), 35, spawnedPet.blockPosition());
               IAuraChunk.getAuraChunk(spawnLevel, auraPos).drainAura(auraPos, 200000);
               PacketHandler.sendToAllAround(
                  spawnedPet.level(),
                  spawnedPet.blockPosition(),
                  32,
                  new PacketParticles(
                     (float)spawnedPet.getX(), (float)spawnedPet.getEyeY(), (float)spawnedPet.getZ(), PacketParticles.Type.PET_REVIVER, 5093935
                  )
               );
               if (owner instanceof Player) {
                  owner.sendSystemMessage(
                     Component.translatable("info.naturesaura.pet_reviver", new Object[]{spawnedPet.getDisplayName()})
                        .withStyle(new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY})
                  );
               }

               event.setCanceled(true);
            }
         }
      }
   }
}
