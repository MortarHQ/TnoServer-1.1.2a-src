package twilightforest.item;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.level.BlockEvent.BreakEvent;
import twilightforest.init.TFDataMaps;
import twilightforest.init.TFSounds;
import twilightforest.init.TFStats;
import twilightforest.util.WorldUtil;
import twilightforest.util.datamaps.CrumbledBlock;

public class CrumbleHornItem extends Item {
   public CrumbleHornItem(Properties properties) {
      super(properties);
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      player.startUsingItem(hand);
      player.playSound((SoundEvent)TFSounds.QUEST_RAM_AMBIENT.get(), 1.0F, 0.8F);
      return InteractionResultHolder.consume(player.getItemInHand(hand));
   }

   public void onUseTick(Level level, LivingEntity living, ItemStack stack, int count) {
      if (count > 10 && count % 5 == 0 && level instanceof ServerLevel serverLevel) {
         this.doCrumble(serverLevel, living, stack);
         serverLevel.playSound(
            null, living.getX(), living.getY(), living.getZ(), (SoundEvent)TFSounds.QUEST_RAM_AMBIENT.get(), living.getSoundSource(), 1.0F, 0.8F
         );
      }
   }

   public UseAnim getUseAnimation(ItemStack stack) {
      return UseAnim.TOOT_HORN;
   }

   public int getUseDuration(ItemStack stack, LivingEntity user) {
      return 72000;
   }

   public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack) {
      return oldStack.getItem() == newStack.getItem();
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return slotChanged || newStack.getItem() != oldStack.getItem();
   }

   private void doCrumble(ServerLevel serverLevel, LivingEntity living, ItemStack stack) {
      double centerDistance = 3.0;
      int radius = 2;
      Vec3 eyePosition = living.getEyePosition();
      Vec3 lookVec = living.getLookAngle().scale(3.0);
      BlockPos center = BlockPos.containing(eyePosition.add(lookVec));
      AABB crumbleBox = AABB.encapsulatingFullBlocks(center.offset(-2, -2, -2), center.offset(1, 1, 1));
      this.crumbleBlocksInAABB(serverLevel, living, crumbleBox, stack);
   }

   private void crumbleBlocksInAABB(ServerLevel serverLevel, LivingEntity living, AABB box, ItemStack stack) {
      for (BlockPos pos : WorldUtil.getAllInBB(box)) {
         if (this.crumbleBlock(serverLevel, living, pos)) {
            if (living instanceof ServerPlayer player) {
               player.awardStat((ResourceLocation)TFStats.BLOCKS_CRUMBLED.get());
            }

            stack.hurtAndBreak(1, living, LivingEntity.getSlotForHand(living.getUsedItemHand()));
            if (stack.getDamageValue() >= stack.getMaxDamage()) {
               break;
            }
         }
      }
   }

   private boolean crumbleBlock(ServerLevel serverLevel, LivingEntity living, BlockPos pos) {
      BlockState state = serverLevel.getBlockState(pos);
      Block block = state.getBlock();
      CrumbledBlock crumbleMap = (CrumbledBlock)block.builtInRegistryHolder().getData(TFDataMaps.CRUMBLE_HORN);
      if (!state.isAir() && crumbleMap != null) {
         if (living instanceof Player && ((BreakEvent)NeoForge.EVENT_BUS.post(new BreakEvent(serverLevel, pos, state, (Player)living))).isCanceled()) {
            return false;
         }

         if (crumbleMap.result() == Blocks.AIR) {
            if (serverLevel.getRandom().nextFloat() < crumbleMap.chanceToCrumble()) {
               if (living instanceof Player player) {
                  if (block.canHarvestBlock(state, serverLevel, pos, (Player)living)) {
                     serverLevel.removeBlock(pos, false);
                     block.playerDestroy(serverLevel, (Player)living, pos, state, serverLevel.getBlockEntity(pos), ItemStack.EMPTY);
                     serverLevel.levelEvent(2001, pos, Block.getId(state));
                     if (player instanceof ServerPlayer) {
                        player.awardStat(Stats.ITEM_USED.get(this));
                     }

                     return true;
                  }
               } else if (EventHooks.canEntityGrief(serverLevel, living)) {
                  serverLevel.destroyBlock(pos, true);
                  return true;
               }
            }
         } else if (serverLevel.getRandom().nextFloat() < crumbleMap.chanceToCrumble()) {
            serverLevel.setBlock(pos, crumbleMap.result().withPropertiesOf(state), 3);
            serverLevel.levelEvent(2001, pos, Block.getId(state));
            if (living instanceof ServerPlayer player) {
               player.awardStat(Stats.ITEM_USED.get(this));
            }

            return true;
         }

         return false;
      } else {
         return false;
      }
   }
}
