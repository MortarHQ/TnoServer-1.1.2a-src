package twilightforest.item;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Nonnull;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import twilightforest.data.tags.BlockTagGenerator;
import twilightforest.init.TFParticleType;
import twilightforest.init.TFSounds;
import twilightforest.network.ParticlePacket;
import twilightforest.util.iterators.VoxelBresenhamIterator;

public class OreMagnetItem extends Item {
   public static final HashMap<Block, Block> MAGNET_ORE_TO_BLOCK_REPLACEMENTS = new HashMap<>();
   public static final HashMap<Block, Block> TREE_ORE_TO_BLOCK_REPLACEMENTS = new HashMap<>();
   private static final float WIGGLE = 10.0F;

   public OreMagnetItem(Properties properties) {
      super(properties);
   }

   public boolean isEnchantable(ItemStack stack) {
      return false;
   }

   public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
      AtomicBoolean badEnchant = new AtomicBoolean();
      book.getEnchantments().entrySet().forEach(enchantment -> {
         if (!Objects.equals(Enchantments.UNBREAKING, enchantment)) {
            badEnchant.set(true);
         }
      });
      return !badEnchant.get() && super.isBookEnchantable(stack, book);
   }

   @Nonnull
   public InteractionResultHolder<ItemStack> use(Level level, Player player, @Nonnull InteractionHand hand) {
      player.startUsingItem(hand);
      return new InteractionResultHolder(InteractionResult.SUCCESS, player.getItemInHand(hand));
   }

   public void releaseUsing(ItemStack stack, Level level, LivingEntity living, int useRemaining) {
      int useTime = this.getUseDuration(stack, living) - useRemaining;
      if (!level.isClientSide() && useTime > 10) {
         int moved = this.doMagnet(level, living, 0.0F, 0.0F);
         if (moved == 0) {
            moved = this.doMagnet(level, living, 10.0F, 0.0F);
         }

         if (moved == 0) {
            moved = this.doMagnet(level, living, 10.0F, 10.0F);
         }

         if (moved == 0) {
            moved = this.doMagnet(level, living, 0.0F, 10.0F);
         }

         if (moved == 0) {
            moved = this.doMagnet(level, living, -10.0F, 10.0F);
         }

         if (moved == 0) {
            moved = this.doMagnet(level, living, -10.0F, 0.0F);
         }

         if (moved == 0) {
            moved = this.doMagnet(level, living, -10.0F, -10.0F);
         }

         if (moved == 0) {
            moved = this.doMagnet(level, living, 0.0F, -10.0F);
         }

         if (moved == 0) {
            moved = this.doMagnet(level, living, 10.0F, -10.0F);
         }

         if (moved > 0) {
            stack.hurtAndBreak(moved, living, LivingEntity.getSlotForHand(living.getUsedItemHand()));
            level.playSound(null, living.getX(), living.getY(), living.getZ(), (SoundEvent)TFSounds.MAGNET_GRAB.get(), living.getSoundSource(), 1.0F, 1.0F);
         }
      }
   }

   @Nonnull
   public UseAnim getUseAnimation(ItemStack stack) {
      return UseAnim.BOW;
   }

   public int getUseDuration(ItemStack stack, LivingEntity user) {
      return 72000;
   }

   private int doMagnet(Level level, LivingEntity living, float yawOffset, float pitchOffset) {
      double range = 32.0;
      Vec3 srcVec = new Vec3(living.getX(), living.getY() + living.getEyeHeight(), living.getZ());
      Vec3 lookVec = this.getOffsetLook(living, yawOffset, pitchOffset);
      Vec3 destVec = srcVec.add(lookVec.x() * range, lookVec.y() * range, lookVec.z() * range);
      return doMagnet(level, BlockPos.containing(srcVec), BlockPos.containing(destVec), false);
   }

   public static int doMagnet(Level level, BlockPos usePos, BlockPos destPos, boolean sourceIsMineCore) {
      int blocksMoved = 0;
      BlockState attactedOreBlock = Blocks.AIR.defaultBlockState();
      BlockState replacementBlock = Blocks.AIR.defaultBlockState();
      BlockPos foundPos = null;
      BlockPos basePos = null;

      for (BlockPos coord : new VoxelBresenhamIterator(usePos, destPos)) {
         BlockState searchState = level.getBlockState(coord);
         if (basePos == null) {
            if (isReplaceable(searchState)) {
               basePos = coord;
            }
         } else if (foundPos == null
            && searchState.getBlock() != Blocks.AIR
            && isOre(searchState.getBlock(), sourceIsMineCore)
            && level.getBlockEntity(coord) == null) {
            attactedOreBlock = searchState;
            replacementBlock = (sourceIsMineCore ? TREE_ORE_TO_BLOCK_REPLACEMENTS : MAGNET_ORE_TO_BLOCK_REPLACEMENTS)
               .getOrDefault(attactedOreBlock.getBlock(), Blocks.STONE)
               .defaultBlockState();
            foundPos = coord;
         }
      }

      if (basePos != null && foundPos != null && attactedOreBlock.getBlock() != Blocks.AIR) {
         Set<BlockPos> veinBlocks = new HashSet<>();
         findVein(level, foundPos, attactedOreBlock, veinBlocks);
         int offX = basePos.getX() - foundPos.getX();
         int offY = basePos.getY() - foundPos.getY();
         int offZ = basePos.getZ() - foundPos.getZ();

         for (BlockPos coord : veinBlocks) {
            BlockPos replacePos = coord.offset(offX, offY, offZ);
            BlockState replaceState = level.getBlockState(replacePos);
            if (isReplaceable(replaceState) || replaceState.canBeReplaced() || replaceState.isAir()) {
               level.setBlock(coord, replacementBlock, 2);
               if (sourceIsMineCore && level instanceof ServerLevel serverLevel) {
                  Vec3 xyz = Vec3.atCenterOf(replacePos);

                  for (ServerPlayer serverplayer : serverLevel.players()) {
                     if (serverplayer.distanceToSqr(xyz) < 4096.0) {
                        ParticlePacket particlePacket = new ParticlePacket();

                        for (int i = 0; i < 16; i++) {
                           Vec3 offset = new Vec3(
                              (level.random.nextDouble() - 0.5) * 1.25, (level.random.nextDouble() - 0.5) * 1.25, (level.random.nextDouble() - 0.5) * 1.25
                           );
                           particlePacket.queueParticle(
                              (ParticleOptions)TFParticleType.LOG_CORE_PARTICLE.get(), false, xyz.add(offset), new Vec3(0.8, 0.9, 0.2)
                           );
                        }

                        PacketDistributor.sendToPlayer(serverplayer, particlePacket, new CustomPacketPayload[0]);
                     }
                  }
               }

               level.setBlock(replacePos, attactedOreBlock, 2);
               blocksMoved++;
            }
         }
      }

      return blocksMoved;
   }

   private Vec3 getOffsetLook(LivingEntity living, float yawOffset, float pitchOffset) {
      float var2x = Mth.cos(-(living.getYRot() + yawOffset) * (float) (Math.PI / 180.0) - (float) Math.PI);
      float var3x = Mth.sin(-(living.getYRot() + yawOffset) * (float) (Math.PI / 180.0) - (float) Math.PI);
      float var4 = -Mth.cos(-(living.getXRot() + pitchOffset) * (float) (Math.PI / 180.0));
      float var5 = Mth.sin(-(living.getXRot() + pitchOffset) * (float) (Math.PI / 180.0));
      return new Vec3(var3x * var4, var5, var2x * var4);
   }

   @Deprecated
   private static boolean isReplaceable(BlockState state) {
      return state.is(BlockTagGenerator.ORE_MAGNET_SAFE_REPLACE_BLOCK);
   }

   private static boolean findVein(Level level, BlockPos here, BlockState oreState, Set<BlockPos> veinBlocks) {
      if (veinBlocks.contains(here)) {
         return false;
      }

      if (veinBlocks.size() >= 24) {
         return false;
      }

      if (level.getBlockState(here) != oreState) {
         return false;
      }

      veinBlocks.add(here);

      for (Direction e : Direction.values()) {
         findVein(level, here.relative(e), oreState, veinBlocks);
      }

      return true;
   }

   private static boolean isOre(Block ore, boolean core) {
      return (core ? TREE_ORE_TO_BLOCK_REPLACEMENTS : MAGNET_ORE_TO_BLOCK_REPLACEMENTS).containsKey(ore);
   }
}
