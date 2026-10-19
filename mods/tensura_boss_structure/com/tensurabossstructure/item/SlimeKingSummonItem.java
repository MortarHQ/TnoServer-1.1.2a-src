package com.tensurabossstructure.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap.Types;

public class SlimeKingSummonItem extends Item {
   public SlimeKingSummonItem(Properties properties) {
      super(properties);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      tooltipComponents.add(Component.translatable("item.tensura_boss_structure.slime_king_summon.desc").withStyle(ChatFormatting.GRAY));
      super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack itemInHand = player.getItemInHand(hand);
      if (level.isClientSide()) {
         return InteractionResultHolder.pass(itemInHand);
      }

      ServerLevel serverLevel = (ServerLevel)level;
      EntityType<?> entityType = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.parse("tensura:supermassive_slime"));
      if (entityType.create(serverLevel) instanceof Mob mob) {
         BlockPos spawnPos = this.findSpawnPosition(serverLevel, player.blockPosition());
         mob.moveTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, serverLevel.getRandom().nextFloat() * 360.0F, 0.0F);
         mob.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(spawnPos), MobSpawnType.EVENT, null);
         if (serverLevel.addFreshEntity(mob)) {
            if (!player.getAbilities().instabuild) {
               itemInHand.shrink(1);
            }

            serverLevel.getServer()
               .getPlayerList()
               .broadcastSystemMessage(Component.translatable("item.tensura_boss_structure.slime_king_summon.message"), false);
            return InteractionResultHolder.sidedSuccess(itemInHand, false);
         } else {
            return InteractionResultHolder.fail(itemInHand);
         }
      } else {
         return InteractionResultHolder.fail(itemInHand);
      }
   }

   private BlockPos findSpawnPosition(ServerLevel level, BlockPos center) {
      RandomSource random = level.getRandom();

      for (int attempt = 0; attempt < 20; attempt++) {
         double angle = random.nextDouble() * Math.PI * 2.0;
         int distance = 32 + random.nextInt(33);
         int x = center.getX() + (int)(Math.cos(angle) * distance);
         int z = center.getZ() + (int)(Math.sin(angle) * distance);
         BlockPos surfacePos = level.getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, new BlockPos(x, 0, z));
         if (surfacePos.getY() > level.getMinBuildHeight()) {
            return surfacePos;
         }
      }

      double angle = random.nextDouble() * Math.PI * 2.0;
      int distance = 32 + random.nextInt(33);
      int x = center.getX() + (int)(Math.cos(angle) * distance);
      int z = center.getZ() + (int)(Math.sin(angle) * distance);
      return level.getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, new BlockPos(x, 0, z));
   }
}
