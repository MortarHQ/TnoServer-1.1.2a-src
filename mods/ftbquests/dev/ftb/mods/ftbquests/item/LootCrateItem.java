package dev.ftb.mods.ftbquests.item;

import dev.architectury.platform.Platform;
import dev.ftb.mods.ftbquests.FTBQuests;
import dev.ftb.mods.ftbquests.client.ClientQuestFile;
import dev.ftb.mods.ftbquests.client.gui.RewardNotificationsScreen;
import dev.ftb.mods.ftbquests.quest.loot.LootCrate;
import dev.ftb.mods.ftbquests.quest.loot.WeightedReward;
import dev.ftb.mods.ftbquests.registry.ModDataComponents;
import dev.ftb.mods.ftbquests.registry.ModItems;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class LootCrateItem extends Item {
   public LootCrateItem() {
      super(ModItems.defaultProps().rarity(Rarity.UNCOMMON).component((DataComponentType)ModDataComponents.LOOT_CRATE.get(), ""));
   }

   @Nullable
   public static LootCrate getCrate(ItemStack stack, boolean isClientSide) {
      return FTBQuests.<String>getComponent(stack, ModDataComponents.LOOT_CRATE).map(type -> LootCrate.getLootCrates(isClientSide).get(type)).orElse(null);
   }

   @Nullable
   public static LootCrate getCrate(ItemStack stack) {
      return getCrate(stack, Platform.getEnv() == Dist.CLIENT);
   }

   public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      LootCrate crate = getCrate(stack, player.level().isClientSide);
      if (crate == null) {
         return new InteractionResultHolder(InteractionResult.FAIL, stack);
      }

      int nItems = player.isCrouching() ? stack.getCount() : 1;
      if (!world.isClientSide) {
         for (WeightedReward wr : crate.getTable().generateWeightedRandomRewards(player.getRandom(), nItems, true)) {
            wr.getReward().claim((ServerPlayer)player, true);
         }

         world.playSound(
            null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F + world.random.nextFloat() * 0.4F
         );
      } else {
         new RewardNotificationsScreen().openGui();

         for (int i = 0; i < 5; i++) {
            Vec3 vec3d = new Vec3((world.random.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
            vec3d = vec3d.xRot(-player.getXRot() * (float) (Math.PI / 180.0));
            vec3d = vec3d.yRot(-player.getYRot() * (float) (Math.PI / 180.0));
            double d0 = -world.random.nextFloat() * 0.6 - 0.3;
            Vec3 vec3d1 = new Vec3((world.random.nextFloat() - 0.5) * 0.3, d0, 0.6);
            vec3d1 = vec3d1.xRot(-player.getXRot() * (float) (Math.PI / 180.0));
            vec3d1 = vec3d1.yRot(-player.getYRot() * (float) (Math.PI / 180.0));
            vec3d1 = vec3d1.add(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
            world.addParticle(new ItemParticleOption(ParticleTypes.ITEM, stack), vec3d1.x, vec3d1.y, vec3d1.z, vec3d.x, vec3d.y + 0.05, vec3d.z);
         }
      }

      stack.shrink(nItems);
      return new InteractionResultHolder(InteractionResult.SUCCESS, stack);
   }

   public boolean isFoil(ItemStack stack) {
      LootCrate crate = getCrate(stack, true);
      return crate != null && crate.isGlow();
   }

   public Component getName(ItemStack stack) {
      LootCrate crate = getCrate(stack);
      return (Component)(crate != null && !crate.getItemName().isEmpty() ? Component.translatable(crate.getItemName()) : super.getName(stack));
   }

   @OnlyIn(Dist.CLIENT)
   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      if (context.registries() != null && ClientQuestFile.exists()) {
         LootCrate crate = getCrate(stack, true);
         if (crate != null) {
            if (crate.getItemName().isEmpty()) {
               tooltip.add(Component.literal(crate.getStringID()).withStyle(ChatFormatting.YELLOW));
               tooltip.add(Component.empty());
            }

            tooltip.add(Component.translatable("item.ftbquests.lootcrate.tooltip_1").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable("item.ftbquests.lootcrate.tooltip_2").withStyle(ChatFormatting.GRAY));
         } else {
            String name = (String)stack.getOrDefault((DataComponentType)ModDataComponents.LOOT_CRATE.get(), "");
            if (!name.isEmpty()) {
               tooltip.add(Component.translatable("item.ftbquests.lootcrate.missing", new Object[]{name}).withStyle(ChatFormatting.RED));
            }
         }
      }
   }
}
