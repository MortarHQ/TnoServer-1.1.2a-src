package twilightforest.block;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.neoforged.neoforge.common.ItemAbilities;

public class ArcticFurBlock extends Block {
   private static final MutableComponent TOOLTIP = Component.translatable("block.twilightforest.arctic_fur_block.desc").withStyle(ChatFormatting.GRAY);

   public ArcticFurBlock(Properties properties) {
      super(properties);
   }

   public float getDestroyProgress(BlockState state, Player player, BlockGetter getter, BlockPos pos) {
      return player.getMainHandItem().canPerformAction(ItemAbilities.SHEARS_DIG) ? 0.2F : super.getDestroyProgress(state, player, getter, pos);
   }

   public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
      entity.causeFallDamage(fallDistance, 0.1F, level.damageSources().fall());
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      tooltip.add(TOOLTIP);
   }
}
