package twilightforest.item;

import java.util.UUID;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.Nullable;
import twilightforest.data.tags.BlockTagGenerator;
import twilightforest.data.tags.ItemTagGenerator;
import twilightforest.entity.projectile.ChainBlock;
import twilightforest.init.TFDataComponents;
import twilightforest.init.TFEnchantments;
import twilightforest.init.TFEntities;
import twilightforest.init.TFSounds;

public class ChainBlockItem extends Item {
   public ChainBlockItem(Properties properties) {
      super(properties);
   }

   public void inventoryTick(ItemStack stack, Level level, Entity holder, int slot, boolean isSelected) {
      if (!level.isClientSide() && stack.get(TFDataComponents.THROWN_PROJECTILE) != null && this.getThrownEntity(level, stack) == null) {
         stack.remove(TFDataComponents.THROWN_PROJECTILE);
      }
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      if (stack.get(TFDataComponents.THROWN_PROJECTILE) == null && level.getWorldBorder().isWithinBounds(player.blockPosition())) {
         player.playSound((SoundEvent)TFSounds.BLOCK_AND_CHAIN_FIRED.get(), 0.5F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F));
         if (!level.isClientSide()) {
            ChainBlock launchedBlock = new ChainBlock((EntityType<? extends ChainBlock>)TFEntities.CHAIN_BLOCK.get(), level, player, hand, stack);
            level.addFreshEntity(launchedBlock);
            stack.set(TFDataComponents.THROWN_PROJECTILE, launchedBlock.getUUID());
         }

         player.startUsingItem(hand);
         return new InteractionResultHolder(InteractionResult.SUCCESS, stack);
      } else {
         return new InteractionResultHolder(InteractionResult.PASS, stack);
      }
   }

   @Nullable
   private ChainBlock getThrownEntity(Level level, ItemStack stack) {
      if (level instanceof ServerLevel server) {
         UUID id = (UUID)stack.get(TFDataComponents.THROWN_PROJECTILE);
         if (id != null) {
            Entity e = server.getEntity(id);
            if (e instanceof ChainBlock) {
               return (ChainBlock)e;
            }
         }
      }

      return null;
   }

   public int getUseDuration(ItemStack stack, LivingEntity user) {
      return 72000;
   }

   public UseAnim getUseAnimation(ItemStack stack) {
      return UseAnim.BLOCK;
   }

   public boolean isValidRepairItem(ItemStack stack, ItemStack repairItem) {
      return repairItem.is(ItemTagGenerator.KNIGHTMETAL_INGOTS);
   }

   public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
      if (stack.get(TFDataComponents.THROWN_PROJECTILE) != null && state.is(BlockTagGenerator.MINEABLE_WITH_BLOCK_AND_CHAIN)) {
         MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
         if (server != null) {
            int destruction = stack.getEnchantmentLevel(
               server.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(TFEnchantments.DESTRUCTION)
            );
            if (destruction > 0) {
               return this.getHarvestLevel(destruction).createToolProperties(BlockTagGenerator.MINEABLE_WITH_BLOCK_AND_CHAIN).isCorrectForDrops(state);
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public Tier getHarvestLevel(int destruction) {
      return switch (destruction) {
         case 1 -> Tiers.WOOD;
         case 2 -> Tiers.STONE;
         default -> Tiers.IRON;
      };
   }
}
