package com.b1n_ry.yigd.item;

import com.b1n_ry.yigd.Yigd;
import com.b1n_ry.yigd.components.GraveComponent;
import com.b1n_ry.yigd.config.CommandConfig;
import com.b1n_ry.yigd.config.ExtraFeaturesConfig;
import com.b1n_ry.yigd.config.YigdConfig;
import com.b1n_ry.yigd.data.DeathInfoManager;
import com.b1n_ry.yigd.data.GraveStatus;
import com.b1n_ry.yigd.networking.packets.GraveOverviewS2CPacket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public class DeathScrollItem extends Item {
   private static final int USE_TIME_MARGIN = 3;

   public DeathScrollItem(Properties properties) {
      super(properties);
   }

   public void onCraftedBy(@NotNull ItemStack stack, @NotNull Level level, @NotNull Player player) {
      if (!level.isClientSide) {
         this.bindStackToLatestDeath((ServerPlayer)player, stack);
      }

      super.onCraftedBy(stack, level, player);
   }

   public boolean isEnabled(@NotNull FeatureFlagSet enabledFeatures) {
      return YigdConfig.getConfig().extraFeatures.deathScroll.enabled;
   }

   public int getUseDuration(@NotNull ItemStack ignoredStack, @NotNull LivingEntity ignoredEntity) {
      return YigdConfig.getConfig().extraFeatures.deathScroll.useTime + 3;
   }

   @NotNull
   public UseAnim getUseAnimation(@NotNull ItemStack stack) {
      return UseAnim.BOW;
   }

   public void onUseTick(@NotNull Level level, @NotNull LivingEntity livingEntity, @NotNull ItemStack stack, int remainingUseDuration) {
      if (remainingUseDuration < 3) {
         livingEntity.releaseUsingItem();
      }
   }

   public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity livingEntity, int timeCharged) {
      float f = (float)(this.getUseDuration(stack, livingEntity) - timeCharged) / (this.getUseDuration(stack, livingEntity) - 3);
      Yigd.LOGGER.debug("{}", f);
      if (f >= 1.0F && livingEntity instanceof Player player) {
         InteractionHand hand = player.getItemInHand(InteractionHand.MAIN_HAND).equals(stack) ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
         this.useAction(level, player, hand);
      } else {
         super.releaseUsing(stack, level, livingEntity, timeCharged);
      }
   }

   @NotNull
   public InteractionResultHolder<ItemStack> use(Level world, @NotNull Player user, @NotNull InteractionHand hand) {
      if (world.isClientSide) {
         return super.use(world, user, hand);
      } else {
         ExtraFeaturesConfig.ScrollConfig scrollConfig = YigdConfig.getConfig().extraFeatures.deathScroll;
         ServerPlayer player = (ServerPlayer)user;
         ItemStack scroll = player.getItemInHand(hand);
         CustomData scrollNbtComponent = (CustomData)scroll.get(DataComponents.CUSTOM_DATA);
         CompoundTag scrollNbt = scrollNbtComponent != null ? scrollNbtComponent.copyTag() : null;
         if ((scrollConfig.rebindable && player.isShiftKeyDown() || scrollNbt == null || !scrollNbt.contains("grave"))
            && this.bindStackToLatestDeath(player, scroll)) {
            return InteractionResultHolder.sidedSuccess(scroll, true);
         } else if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(scroll);
         } else if (YigdConfig.getConfig().extraFeatures.deathScroll.useTime > 0) {
            user.startUsingItem(hand);
            return InteractionResultHolder.consume(scroll);
         } else {
            return this.useAction(world, user, hand);
         }
      }
   }

   private InteractionResultHolder<ItemStack> useAction(Level world, @NotNull Player user, @NotNull InteractionHand hand) {
      if (world.isClientSide) {
         return super.use(world, user, hand);
      }

      ExtraFeaturesConfig.ScrollConfig scrollConfig = YigdConfig.getConfig().extraFeatures.deathScroll;
      ServerPlayer player = (ServerPlayer)user;
      ItemStack scroll = player.getItemInHand(hand);
      CustomData scrollNbtComponent = (CustomData)scroll.get(DataComponents.CUSTOM_DATA);
      CompoundTag scrollNbt = scrollNbtComponent != null ? scrollNbtComponent.copyTag() : null;
      ExtraFeaturesConfig.ScrollConfig.ClickFunction clickFunction = scrollConfig.clickFunction;
      if (scrollNbt != null && scrollNbt.contains("clickFunction") && !scrollNbt.getString("clickFunction").equals("default")) {
         clickFunction = ExtraFeaturesConfig.ScrollConfig.ClickFunction.valueOf(scrollNbt.getString("clickFunction"));
      }
      InteractionResultHolder<ItemStack> res = switch (clickFunction) {
         case VIEW_CONTENTS -> this.viewContent(scroll, player);
         case RESTORE_CONTENTS -> this.restoreContent(scroll, player);
         case TELEPORT_TO_LOCATION -> this.teleport(scroll, player);
      };
      if (res.getResult() != InteractionResult.PASS
         && YigdConfig.getConfig().extraFeatures.deathScroll.consumeOnUse
         && res.getResult() != InteractionResult.CONSUME) {
         scroll.shrink(1);
      }

      player.getCooldowns().addCooldown(this, scrollConfig.useCooldown);
      return res;
   }

   public boolean bindStackToLatestDeath(ServerPlayer player, ItemStack scroll) {
      if (player == null) {
         return false;
      } else {
         ResolvableProfile playerProfile = new ResolvableProfile(player.getGameProfile());
         List<GraveComponent> graves = new ArrayList<>(DeathInfoManager.INSTANCE.getBackupData(playerProfile));
         graves.removeIf(componentx -> componentx.getStatus() != GraveStatus.UNCLAIMED);
         int size = graves.size();
         if (size >= 1) {
            GraveComponent component = graves.get(size - 1);
            scroll.update(DataComponents.CUSTOM_DATA, CustomData.EMPTY, comp -> comp.update(nbtCompound -> {
               nbtCompound.putUUID("grave", component.getGraveId());
               nbtCompound.putString("clickFunction", "default");
            }));
            return true;
         } else {
            return false;
         }
      }
   }

   private InteractionResultHolder<ItemStack> viewContent(ItemStack scroll, ServerPlayer player) {
      CustomData scrollNbtComponent = (CustomData)scroll.get(DataComponents.CUSTOM_DATA);
      if (scrollNbtComponent == null) {
         return InteractionResultHolder.pass(scroll);
      }

      CompoundTag scrollNbt = scrollNbtComponent.copyTag();
      UUID graveId = scrollNbt.getUUID("grave");
      Optional<GraveComponent> optional = DeathInfoManager.INSTANCE.getGrave(graveId);
      if (optional.isPresent()) {
         GraveComponent component = optional.get();
         YigdConfig config = YigdConfig.getConfig();
         CommandConfig commandConfig = config.commandConfig;
         PacketDistributor.sendToPlayer(
            player,
            new GraveOverviewS2CPacket(
               component,
               player.hasPermissions(commandConfig.restorePermissionLevel),
               player.hasPermissions(commandConfig.robPermissionLevel),
               player.hasPermissions(commandConfig.deletePermissionLevel),
               player.hasPermissions(commandConfig.unlockPermissionLevel) && config.graveConfig.unlockable,
               config.extraFeatures.graveKeys.enabled && config.extraFeatures.graveKeys.obtainableFromGui,
               config.extraFeatures.graveCompass.cloneRecoveryCompassWithGUI && player.getInventory().countItem(Items.RECOVERY_COMPASS) > 0
            ),
            new CustomPacketPayload[0]
         );
      }

      return InteractionResultHolder.success(scroll);
   }

   private InteractionResultHolder<ItemStack> restoreContent(ItemStack scroll, ServerPlayer player) {
      CustomData scrollNbtComponent = (CustomData)scroll.get(DataComponents.CUSTOM_DATA);
      if (scrollNbtComponent == null) {
         return InteractionResultHolder.pass(scroll);
      } else {
         CompoundTag scrollNbt = scrollNbtComponent.copyTag();
         UUID graveId = scrollNbt.getUUID("grave");
         Optional<GraveComponent> optional = DeathInfoManager.INSTANCE.getGrave(graveId);
         if (optional.isPresent()) {
            GraveComponent component = optional.get();
            InteractionResult res = component.claim(player, player.serverLevel(), null, component.getPos(), scroll);
            return new InteractionResultHolder(res, scroll);
         } else {
            return InteractionResultHolder.pass(scroll);
         }
      }
   }

   private InteractionResultHolder<ItemStack> teleport(ItemStack scroll, ServerPlayer player) {
      CustomData scrollNbtComponent = (CustomData)scroll.get(DataComponents.CUSTOM_DATA);
      if (scrollNbtComponent == null) {
         return InteractionResultHolder.pass(scroll);
      }

      CompoundTag scrollNbt = scrollNbtComponent.copyTag();
      UUID graveId = scrollNbt.getUUID("grave");
      Optional<GraveComponent> optional = DeathInfoManager.INSTANCE.getGrave(graveId);
      if (optional.isPresent()) {
         GraveComponent component = optional.get();
         BlockPos gravePos = component.getPos();
         if (component.getWorld() != null) {
            player.teleportTo(component.getWorld(), gravePos.getX(), gravePos.getY(), gravePos.getZ(), player.getYRot(), player.getXRot());
         }
      }

      return InteractionResultHolder.success(scroll);
   }
}
