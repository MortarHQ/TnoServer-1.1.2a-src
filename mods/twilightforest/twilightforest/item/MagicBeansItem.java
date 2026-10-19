package twilightforest.item;

import javax.annotation.Nonnull;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFSounds;

public class MagicBeansItem extends Item {
   public MagicBeansItem(Properties properties) {
      super(properties);
   }

   @Nonnull
   public InteractionResult useOn(UseOnContext context) {
      Level level = context.getLevel();
      BlockPos pos = context.getClickedPos();
      Player player = context.getPlayer();
      ItemStack stack = context.getItemInHand();
      int maxY = Math.max(pos.getY() + 100, 175);
      if (pos.getY() < maxY && level.getBlockState(pos).is(TFBlocks.UBEROUS_SOIL) && level.getBlockState(pos.above()).isAir()) {
         if (!level.isClientSide()) {
            stack.shrink(1);
            level.setBlockAndUpdate(pos.above(), ((Block)TFBlocks.BEANSTALK_GROWER.get()).defaultBlockState());
            level.playSound(null, pos, (SoundEvent)TFSounds.BEANSTALK_GROWTH.get(), SoundSource.BLOCKS, 4.0F, 1.0F);
            if (player instanceof ServerPlayer) {
               player.awardStat(Stats.ITEM_USED.get(this));
               PlayerAdvancements advancements = ((ServerPlayer)player).getAdvancements();
               ServerAdvancementManager manager = ((ServerLevel)player.getCommandSenderWorld()).getServer().getAdvancements();
               AdvancementHolder holder = manager.get(TwilightForestMod.prefix("beanstalk"));
               if (holder != null && !manager.getAllAdvancements().contains(holder)) {
                  advancements.award(holder, "use_beans");
               }
            }
         }

         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }
}
