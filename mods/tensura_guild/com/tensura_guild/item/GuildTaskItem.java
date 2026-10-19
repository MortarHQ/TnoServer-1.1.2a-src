package com.tensura_guild.item;

import com.tensura_guild.config.GuildConfig;
import com.tensura_guild.data.GuildTaskBoardData;
import com.tensura_guild.data.GuildTaskDefinitions;
import com.tensura_guild.data.GuildTaskService;
import com.tensura_guild.data.GuildTaskTime;
import com.tensura_guild.network.OpenGuildTaskContentPayload;
import com.tensura_guild.registry.GuildAttachments;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public class GuildTaskItem extends Item {
   public GuildTaskItem(Properties properties) {
      super(properties);
   }

   @NotNull
   public InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      if (this.isInvalid(stack)) {
         return InteractionResultHolder.pass(stack);
      }

      if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
         GuildTaskService.expireTasks(serverPlayer);
      }

      GuildTaskBoardData data = (GuildTaskBoardData)player.getData(GuildAttachments.GUILD_TASK_BOARD);
      if (!data.hasActiveTask()) {
         return InteractionResultHolder.pass(stack);
      }

      Optional<ResourceLocation> taskId = this.getTaskId(stack);
      if (!taskId.isEmpty() && data.hasAcceptedTask(taskId.get())) {
         if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(serverPlayer, new OpenGuildTaskContentPayload(taskId.get()), new CustomPacketPayload[0]);
         }

         return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
      } else {
         return InteractionResultHolder.pass(stack);
      }
   }

   public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
      if (entity.level().isClientSide()) {
         return false;
      }

      Optional<ResourceLocation> taskId = this.getTaskId(stack);
      Optional<UUID> owner = this.getOwner(stack);
      if (!taskId.isEmpty() && !owner.isEmpty()) {
         MinecraftServer server = entity.level().getServer();
         if (server == null) {
            return false;
         }

         ServerPlayer player = server.getPlayerList().getPlayer(owner.get());
         if (player == null) {
            return false;
         }

         GuildTaskService.expireTasks(player);
         GuildTaskBoardData data = (GuildTaskBoardData)player.getData(GuildAttachments.GUILD_TASK_BOARD);
         if (!this.isInvalid(stack) && data.hasAcceptedTask(taskId.get())) {
            return false;
         }

         entity.discard();
         return true;
      } else {
         return false;
      }
   }

   public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
      this.getTaskId(stack)
         .flatMap(GuildTaskDefinitions::get)
         .ifPresent(task -> tooltip.add(Component.translatable(task.titleKey()).withStyle(ChatFormatting.GRAY)));
      this.getPartyName(stack)
         .ifPresent(partyName -> tooltip.add(Component.translatable("screen.tensura_guild.task.party", new Object[]{partyName}).withStyle(ChatFormatting.GRAY)));
      if (this.isInvalid(stack)) {
         tooltip.add(Component.translatable("item.tensura_guild.guild_task.invalid").withStyle(ChatFormatting.RED));
      } else {
         this.getTargetCoordinates(stack).ifPresent(value -> tooltip.add(value.copy().withStyle(ChatFormatting.GRAY)));
         this.getRemainingTime(stack, context)
            .ifPresent(
               value -> tooltip.add(Component.translatable("screen.tensura_guild.task.remaining_time", new Object[]{value}).withStyle(ChatFormatting.GRAY))
            );
      }
   }

   private boolean isInvalid(ItemStack stack) {
      CustomData customData = (CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
      CompoundTag tag = customData.copyTag();
      return tag.getBoolean("Invalid");
   }

   private Optional<ResourceLocation> getTaskId(ItemStack stack) {
      CustomData customData = (CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
      CompoundTag tag = customData.copyTag();
      return !tag.contains("TaskId") ? Optional.empty() : Optional.ofNullable(ResourceLocation.tryParse(tag.getString("TaskId")));
   }

   private Optional<UUID> getOwner(ItemStack stack) {
      CustomData customData = (CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
      CompoundTag tag = customData.copyTag();
      return !tag.contains("Owner") ? Optional.empty() : Optional.of(tag.getUUID("Owner"));
   }

   private Optional<String> getPartyName(ItemStack stack) {
      CustomData customData = (CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
      CompoundTag tag = customData.copyTag();
      if (!tag.contains("PartyName")) {
         return Optional.empty();
      }

      String partyName = tag.getString("PartyName");
      return partyName.isBlank() ? Optional.empty() : Optional.of(partyName);
   }

   private Optional<String> getRemainingTime(ItemStack stack, TooltipContext context) {
      CustomData customData = (CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
      CompoundTag tag = customData.copyTag();
      Level level = context.level();
      int timeLimitTicks;
      if (tag.contains("TaskTimeLimitTicks")) {
         timeLimitTicks = tag.getInt("TaskTimeLimitTicks");
      } else {
         timeLimitTicks = this.getTaskId(stack)
            .flatMap(GuildTaskDefinitions::get)
            .map(GuildTaskDefinitions.GuildTaskDefinition::effectiveTimeLimitTicks)
            .orElseGet(GuildConfig::commissionTimeLimitTicks);
      }

      if (timeLimitTicks <= 0) {
         return Optional.empty();
      }

      long currentGameTime = level == null ? 0L : level.getGameTime();
      long startedAt = tag.contains("TaskStartedAt") ? tag.getLong("TaskStartedAt") : currentGameTime;
      long remainingTicks = GuildTaskTime.remainingTicks(currentGameTime, startedAt, timeLimitTicks);
      return Optional.of(GuildTaskTime.formatCommissionDayDurationText(remainingTicks));
   }

   private Optional<Component> getTargetCoordinates(ItemStack stack) {
      CustomData customData = (CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
      CompoundTag tag = customData.copyTag();
      if (!tag.contains("TargetX") || !tag.contains("TargetZ")) {
         return Optional.empty();
      } else if (tag.getBoolean("TaskComplete")) {
         return this.getTaskId(stack).flatMap(GuildTaskDefinitions::get).map(GuildTaskDefinitions.GuildTaskDefinition::isVisitStructureTask).orElse(false)
            ? Optional.of(Component.translatable("screen.tensura_guild.task.target_reached"))
            : Optional.of(Component.translatable("screen.tensura_guild.task.target_defeated"));
      } else {
         return Optional.of(Component.translatable("screen.tensura_guild.task.target_coordinates", new Object[]{tag.getInt("TargetX"), tag.getInt("TargetZ")}));
      }
   }
}
