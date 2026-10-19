package twilightforest.item;

import javax.annotation.Nonnull;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.BlockEvent.BreakEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import twilightforest.block.LightableBlock;
import twilightforest.init.TFDataAttachments;
import twilightforest.init.TFSounds;
import twilightforest.network.MovePlayerPacket;
import twilightforest.network.ParticlePacket;
import twilightforest.util.WorldUtil;

public class PeacockFanItem extends Item {
   public PeacockFanItem(Properties properties) {
      super(properties);
   }

   @Nonnull
   public InteractionResultHolder<ItemStack> use(Level level, Player player, @Nonnull InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      boolean flag = !player.onGround() && !player.isSwimming() && !(Boolean)player.getData(TFDataAttachments.FEATHER_FAN);
      if (level.isClientSide()) {
         if (player.isFallFlying()) {
            Vec3 look = player.getLookAngle();
            Vec3 movement = player.getDeltaMovement();
            player.setDeltaMovement(
               movement.add(
                  look.x() * 0.1 + (look.x() * 2.0 - movement.x()) * 0.5,
                  look.y() * 0.1 + (look.y() * 2.0 - movement.y()) * 0.5 + 1.25,
                  look.z() * 0.1 + (look.z() * 2.0 - movement.z()) * 0.5
               )
            );
         }

         if (flag) {
            player.setDeltaMovement(new Vec3(player.getDeltaMovement().x() * 1.05F, 1.5, player.getDeltaMovement().z() * 1.05F));
         }

         return new InteractionResultHolder(InteractionResult.SUCCESS, stack);
      } else {
         int fanned = this.doFan(level, player);
         stack.hurtAndBreak(fanned + 1, player, LivingEntity.getSlotForHand(hand));
         if (flag) {
            player.setData(TFDataAttachments.FEATHER_FAN, true);
         } else {
            AABB fanBox = this.getEffectAABB(player);
            Vec3 lookVec = player.getLookAngle();

            for (ServerPlayer serverplayer : ((ServerLevel)level).players()) {
               if (serverplayer.distanceToSqr(player.position()) < 4096.0) {
                  ParticlePacket packet = new ParticlePacket();

                  for (int i = 0; i < 30; i++) {
                     packet.queueParticle(
                        ParticleTypes.CLOUD,
                        true,
                        fanBox.minX + level.getRandom().nextFloat() * (fanBox.maxX - fanBox.minX),
                        fanBox.minY + level.getRandom().nextFloat() * (fanBox.maxY - fanBox.minY),
                        fanBox.minZ + level.getRandom().nextFloat() * (fanBox.maxZ - fanBox.minZ),
                        lookVec.x(),
                        lookVec.y(),
                        lookVec.z()
                     );
                  }

                  PacketDistributor.sendToPlayer(serverplayer, packet, new CustomPacketPayload[0]);
               }
            }
         }

         level.playSound(
            null,
            player.blockPosition(),
            (SoundEvent)TFSounds.FAN_WHOOSH.get(),
            SoundSource.PLAYERS,
            1.0F + level.getRandom().nextFloat(),
            level.getRandom().nextFloat() * 0.7F + 0.3F
         );
         player.startUsingItem(hand);
         return new InteractionResultHolder(InteractionResult.PASS, stack);
      }
   }

   @Nonnull
   public UseAnim getUseAnimation(ItemStack stack) {
      return UseAnim.BLOCK;
   }

   public int getUseDuration(ItemStack stack, LivingEntity user) {
      return 20;
   }

   private int doFan(Level level, Player player) {
      AABB fanBox = this.getEffectAABB(player);
      return this.fanBlocksInAABB(level, fanBox, player) + this.fanEntitiesInAABB(level, player, fanBox);
   }

   private int fanEntitiesInAABB(Level level, Player player, AABB fanBox) {
      Vec3 moveVec = player.getLookAngle().scale(2.0);
      Item fan = player.getUseItem().getItem();
      int fannedEntities = 0;

      for (Entity entity : level.getEntitiesOfClass(Entity.class, fanBox)) {
         if (entity.isPushable() || entity instanceof ItemEntity || entity instanceof Projectile) {
            entity.setDeltaMovement(moveVec.x(), moveVec.y(), moveVec.z());
            fannedEntities++;
         }

         if (entity instanceof ServerPlayer pushedPlayer && pushedPlayer != player && !pushedPlayer.isShiftKeyDown()) {
            PacketDistributor.sendToPlayer(pushedPlayer, new MovePlayerPacket(moveVec.x(), moveVec.y(), moveVec.z()), new CustomPacketPayload[0]);
            player.getCooldowns().addCooldown(fan, 40);
            fannedEntities += 2;
         }
      }

      return fannedEntities;
   }

   private AABB getEffectAABB(Player player) {
      double range = 3.0;
      double radius = 2.0;
      Vec3 srcVec = new Vec3(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
      Vec3 lookVec = player.getLookAngle().scale(range);
      Vec3 destVec = srcVec.add(lookVec.x(), lookVec.y(), lookVec.z());
      return new AABB(destVec.x() - radius, destVec.y() - radius, destVec.z() - radius, destVec.x() + radius, destVec.y() + radius, destVec.z() + radius);
   }

   private int fanBlocksInAABB(Level level, AABB box, Player player) {
      int fan = 0;

      for (BlockPos pos : WorldUtil.getAllInBB(box)) {
         fan += this.fanBlock(level, pos, player);
      }

      return fan;
   }

   private int fanBlock(Level level, BlockPos pos, Player player) {
      int cost = 0;
      BlockState state = level.getBlockState(pos);
      if (state.getBlock() instanceof FlowerBlock) {
         if (level.getRandom().nextInt(3) == 0 && !((BreakEvent)NeoForge.EVENT_BUS.post(new BreakEvent(level, pos, state, player))).isCanceled()) {
            level.destroyBlock(pos, true);
            cost++;
         }
      } else if (state.getBlock() instanceof AbstractCandleBlock && (Boolean)state.getValue(AbstractCandleBlock.LIT)) {
         AbstractCandleBlock.extinguish(null, state, level, pos);
      } else if (state.getBlock() instanceof LightableBlock lightable
         && (
            state.getValue(LightableBlock.LIGHTING) == LightableBlock.Lighting.NORMAL || state.getValue(LightableBlock.LIGHTING) == LightableBlock.Lighting.DIM
         )) {
         lightable.extinguish(null, state, level, pos);
      }

      return cost;
   }
}
