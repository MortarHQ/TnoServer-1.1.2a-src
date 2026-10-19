package cn.leolezury.eternalstarlight.common.item.misc;

import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap.Types;

public class EthericEyeItem extends Item {
   public EthericEyeItem(Properties properties) {
      super(properties);
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      int y = level.getHeight(Types.WORLD_SURFACE, player.getBlockX(), player.getBlockZ());
      if (y == player.getY()) {
         return InteractionResultHolder.pass(stack);
      }

      if (level instanceof ServerLevel serverLevel) {
         RandomSource random = player.getRandom();

         for (int i = 0; i <= 15; i++) {
            double direction = Math.signum(y - player.getY());
            ESPlatform.INSTANCE
               .sendToAllClients(
                  serverLevel,
                  new ParticlePacket(
                     ExplosionShockParticleOptions.ETHER,
                     player.getX() + (random.nextFloat() - 0.5F) * player.getBbWidth() * 5.0F,
                     player.getY() + (0.5 - 0.5 * direction) * player.getBbHeight(),
                     player.getZ() + (random.nextFloat() - 0.5F) * player.getBbWidth() * 5.0F,
                     0.0,
                     direction,
                     0.0
                  )
               );
            ESPlatform.INSTANCE
               .sendToAllClients(
                  serverLevel,
                  new ParticlePacket(
                     ExplosionShockParticleOptions.ETHER,
                     player.getX() + (random.nextFloat() - 0.5F) * player.getBbWidth() * 5.0F,
                     y + (0.5 - 0.5 * direction) * player.getBbHeight(),
                     player.getZ() + (random.nextFloat() - 0.5F) * player.getBbWidth() * 5.0F,
                     0.0,
                     direction,
                     0.0
                  )
               );
         }
      }

      player.resetFallDistance();
      player.teleportTo(player.getX(), y, player.getZ());
      stack.consume(1, player);
      player.awardStat(Stats.ITEM_USED.get(this));
      player.getCooldowns().addCooldown(this, 20);
      return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
   }

   public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> components, TooltipFlag tooltipFlag) {
      super.appendHoverText(itemStack, tooltipContext, components, tooltipFlag);
      components.add(CommonComponents.EMPTY);
      components.add(Component.translatable("tooltip.eternal_starlight.use").withStyle(ChatFormatting.GRAY));
      components.add(Component.literal(" ").append(Component.translatable("tooltip.eternal_starlight.etheric_eye")).withColor(14352369));
   }
}
