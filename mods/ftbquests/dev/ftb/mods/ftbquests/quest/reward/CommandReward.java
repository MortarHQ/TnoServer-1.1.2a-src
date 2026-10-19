package dev.ftb.mods.ftbquests.quest.reward;

import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftbquests.net.NotifyRewardMessage;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class CommandReward extends Reward {
   private static final String DEFAULT_COMMAND = "/say Hi, @p!";
   private static final Icon REWARD_ICON = Icon.getIcon("minecraft:block/command_block_back");
   public static final Pattern PATTERN = Pattern.compile("[{](\\w+)}");
   private String command = "/say Hi, @p!";
   private int permissionLevel;
   private boolean silent;
   private String feedbackMessage = "";

   public CommandReward(long id, Quest quest) {
      super(id, quest);
      this.permissionLevel = 0;
   }

   @Override
   public RewardType getType() {
      return RewardTypes.COMMAND;
   }

   @Override
   public void writeData(CompoundTag nbt, Provider provider) {
      super.writeData(nbt, provider);
      nbt.putString("command", this.command);
      if (this.permissionLevel > 0) {
         nbt.putInt("permission_level", this.permissionLevel);
      }

      if (this.silent) {
         nbt.putBoolean("silent", true);
      }

      if (!this.feedbackMessage.isEmpty()) {
         nbt.putString("feedback_message", this.feedbackMessage);
      }
   }

   @Override
   public void readData(CompoundTag nbt, Provider provider) {
      super.readData(nbt, provider);
      this.command = nbt.getString("command");
      if (nbt.getBoolean("elevate_perms")) {
         this.permissionLevel = 2;
      } else {
         this.permissionLevel = nbt.getInt("permission_level");
      }

      this.silent = nbt.getBoolean("silent");
      this.feedbackMessage = nbt.getString("feedback_message");
   }

   @Override
   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeUtf(this.command, 32767);
      buffer.writeVarInt(this.permissionLevel);
      buffer.writeBoolean(this.silent);
      buffer.writeUtf(this.feedbackMessage, 32767);
   }

   @Override
   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.command = buffer.readUtf(32767);
      this.permissionLevel = buffer.readVarInt();
      this.silent = buffer.readBoolean();
      this.feedbackMessage = buffer.readUtf(32767);
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addString("command", this.command, v -> this.command = v, "/say Hi, @p!").setNameKey("ftbquests.reward.ftbquests.command");
      config.addInt("permission_level", this.permissionLevel, v -> this.permissionLevel = v, 0, 0, 4);
      config.addBool("silent", this.silent, v -> this.silent = v, false);
      config.addString("feedback_message", this.feedbackMessage, v -> this.feedbackMessage = v, "");
   }

   @Override
   public void claim(ServerPlayer player, boolean notify) {
      Map<String, Object> overrides = new HashMap<>();
      overrides.put("p", player.getGameProfile().getName());
      BlockPos pos = player.blockPosition();
      overrides.put("x", pos.getX());
      overrides.put("y", pos.getY());
      overrides.put("z", pos.getZ());
      if (this.getQuestChapter() != null) {
         overrides.put("chapter", this.getQuestChapter());
      }

      overrides.put("quest", this.quest);
      FTBTeamsAPI.api().getManager().getTeamForPlayer(player).ifPresent(team -> {
         overrides.put("team", team.getName().getString());
         overrides.put("team_id", team.getShortName());
         overrides.put("long_team_id", team.getId().toString());
         overrides.put("member_count", team.getMembers().size());
         overrides.put("online_member_count", team.getOnlineMembers().size());
      });
      String cmd = format(this.command.trim(), overrides);
      CommandSourceStack source = player.createCommandSourceStack();
      if (this.permissionLevel > 0) {
         source = source.withPermission(this.permissionLevel);
      }

      if (this.silent) {
         source = source.withSuppressedOutput();
      }

      player.server.getCommands().performPrefixedCommand(source, cmd);
      if (notify) {
         String key = this.feedbackMessage.isEmpty() ? "ftbquests.reward.ftbquests.command.success" : this.feedbackMessage;
         NetworkManager.sendToPlayer(player, new NotifyRewardMessage(this.id, Component.translatable(key), REWARD_ICON, this.disableRewardScreenBlur));
      }
   }

   @OnlyIn(Dist.CLIENT)
   public MutableComponent getAltTitle() {
      return Component.translatable("ftbquests.reward.ftbquests.command").append(": ").append(Component.literal(this.command).withStyle(ChatFormatting.RED));
   }

   public static String format(String template, Map<String, Object> parameters) {
      StringBuilder newTemplate = new StringBuilder(template);
      List<Object> valueList = new ArrayList<>();
      Matcher matcher = PATTERN.matcher(template);

      while (matcher.find()) {
         String key = matcher.group(1);
         if (parameters.containsKey(key)) {
            String paramName = "{" + key + "}";
            int index = newTemplate.indexOf(paramName);
            if (index != -1) {
               newTemplate.replace(index, index + paramName.length(), "%s");
               valueList.add(parameters.get(key));
            }
         }
      }

      return String.format(newTemplate.toString(), valueList.toArray());
   }
}
