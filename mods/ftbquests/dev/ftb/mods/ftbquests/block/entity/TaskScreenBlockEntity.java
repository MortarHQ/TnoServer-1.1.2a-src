package dev.ftb.mods.ftbquests.block.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftblibrary.config.BooleanConfig;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.config.ItemStackConfig;
import dev.ftb.mods.ftbquests.api.FTBQuestsAPI;
import dev.ftb.mods.ftbquests.block.TaskScreenBlock;
import dev.ftb.mods.ftbquests.client.FTBQuestsClient;
import dev.ftb.mods.ftbquests.net.BlockConfigResponseMessage;
import dev.ftb.mods.ftbquests.quest.BaseQuestFile;
import dev.ftb.mods.ftbquests.quest.QuestObjectBase;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.quest.task.Task;
import dev.ftb.mods.ftbquests.registry.ModBlockEntityTypes;
import dev.ftb.mods.ftbquests.registry.ModBlocks;
import dev.ftb.mods.ftbquests.registry.ModDataComponents;
import dev.ftb.mods.ftbquests.util.ConfigQuestObject;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponentMap.Builder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntity.DataComponentInput;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TaskScreenBlockEntity extends EditableBlockEntity implements ITaskScreen {
   private long taskId = 0L;
   private Task task = null;
   private boolean indestructible = false;
   private boolean inputOnly = false;
   private boolean textShadow = false;
   private ItemStack inputModeIcon = ItemStack.EMPTY;
   private ItemStack skin = ItemStack.EMPTY;
   @NotNull
   private UUID teamId = Util.NIL_UUID;
   public float[] fakeTextureUV = null;
   private TeamData cachedTeamData = null;

   public TaskScreenBlockEntity(BlockPos blockPos, BlockState blockState) {
      super((BlockEntityType<?>)ModBlockEntityTypes.CORE_TASK_SCREEN.get(), blockPos, blockState);
   }

   public Task getTask() {
      if (this.task == null && this.taskId != 0L || this.task != null && this.task.id != this.taskId) {
         this.task = FTBQuestsAPI.api().getQuestFile(this.level.isClientSide).getTask(this.taskId);
      }

      return this.task;
   }

   public void setTask(Task task) {
      this.task = task;
      this.taskId = task == null ? 0L : task.id;
      this.setChanged();
   }

   @Override
   public boolean isInputOnly() {
      return this.inputOnly;
   }

   public void setInputOnly(boolean inputOnly) {
      this.inputOnly = inputOnly;
      this.setChanged();
   }

   public ItemStack getInputModeIcon() {
      return this.inputModeIcon;
   }

   public void setInputModeIcon(ItemStack inputModeIcon) {
      this.inputModeIcon = inputModeIcon;
      this.setChanged();
   }

   @Override
   public boolean isIndestructible() {
      return this.indestructible;
   }

   public void setIndestructible(boolean indestructible) {
      this.indestructible = indestructible;
      this.setChanged();
   }

   @Override
   public ItemStack getSkin() {
      return this.skin;
   }

   public void setSkin(ItemStack skin) {
      this.skin = skin;
      this.fakeTextureUV = null;
   }

   public boolean isTextShadow() {
      return this.textShadow;
   }

   public void setTextShadow(boolean textShadow) {
      this.textShadow = textShadow;
   }

   public void setTeamId(@NotNull UUID teamId) {
      this.teamId = teamId;
      this.cachedTeamData = null;
   }

   @NotNull
   @Override
   public UUID getTeamId() {
      return this.teamId;
   }

   public TeamData getCachedTeamData() {
      if (this.cachedTeamData == null) {
         BaseQuestFile f = FTBQuestsAPI.api().getQuestFile(this.level.isClientSide);
         this.cachedTeamData = f.getNullableTeamData(this.getTeamId());
      }

      return this.cachedTeamData;
   }

   @Override
   public Optional<TaskScreenBlockEntity> getCoreScreen() {
      return Optional.of(this);
   }

   public void removeAllAuxScreens() {
      if (this.level != null && this.getBlockState().getBlock() instanceof TaskScreenBlock tsb) {
         BlockPos.betweenClosedStream(
               TaskScreenBlock.getMultiblockBounds(this.getBlockPos(), tsb.getSize(), (Direction)this.getBlockState().getValue(TaskScreenBlock.FACING))
            )
            .forEach(pos -> {
               if (this.level.getBlockState(pos).getBlock() == ModBlocks.AUX_SCREEN.get()) {
                  this.level.removeBlock(pos, false);
               }
            });
      }
   }

   @Nullable
   public Packet<ClientGamePacketListener> getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   public CompoundTag getUpdateTag(Provider provider) {
      return this.saveWithoutMetadata(provider);
   }

   public void loadAdditional(CompoundTag compoundTag, Provider provider) {
      super.loadAdditional(compoundTag, provider);
      this.teamId = compoundTag.hasUUID("TeamID") ? compoundTag.getUUID("TeamID") : Util.NIL_UUID;
      TaskScreenBlockEntity.TaskScreenSaveData data = TaskScreenBlockEntity.TaskScreenSaveData.CODEC
         .parse(NbtOps.INSTANCE, compoundTag.getCompound("savedData"))
         .result()
         .orElse(TaskScreenBlockEntity.TaskScreenSaveData.DEFAULT);
      this.applySavedData(data);
      this.task = null;
      this.fakeTextureUV = null;
   }

   protected void saveAdditional(CompoundTag compoundTag, Provider provider) {
      super.saveAdditional(compoundTag, provider);
      if (this.teamId != Util.NIL_UUID) {
         compoundTag.putUUID("TeamID", this.teamId);
      }

      TaskScreenBlockEntity.TaskScreenSaveData.CODEC
         .encodeStart(NbtOps.INSTANCE, TaskScreenBlockEntity.TaskScreenSaveData.fromBlockEntity(this))
         .ifSuccess(tag -> compoundTag.put("savedData", tag));
   }

   protected void applyImplicitComponents(DataComponentInput dataComponentInput) {
      super.applyImplicitComponents(dataComponentInput);
      this.applySavedData(
         (TaskScreenBlockEntity.TaskScreenSaveData)dataComponentInput.getOrDefault(
            (DataComponentType)ModDataComponents.TASK_SCREEN_SAVED.get(), TaskScreenBlockEntity.TaskScreenSaveData.DEFAULT
         )
      );
   }

   protected void collectImplicitComponents(Builder builder) {
      super.collectImplicitComponents(builder);
      builder.set((DataComponentType)ModDataComponents.TASK_SCREEN_SAVED.get(), TaskScreenBlockEntity.TaskScreenSaveData.fromBlockEntity(this));
   }

   private void applySavedData(TaskScreenBlockEntity.TaskScreenSaveData data) {
      this.taskId = data.taskId;
      this.skin = data.skin;
      this.indestructible = data.indestructible;
      this.inputOnly = data.inputOnly;
      this.inputModeIcon = data.inputModeIcon;
      this.textShadow = data.textShadow;
   }

   public ConfigGroup fillConfigGroup(TeamData data) {
      ConfigGroup cg0 = new ConfigGroup("task_screen", accepted -> {
         if (accepted) {
            NetworkManager.sendToServer(new BlockConfigResponseMessage(this.getBlockPos(), this.saveWithoutMetadata(this.getLevel().registryAccess())));
         }
      });
      cg0.setNameKey(this.getBlockState().getBlock().getDescriptionId());
      ConfigGroup cg = cg0.getOrCreateSubgroup("screen");
      ((ConfigQuestObject)cg.add("task", new ConfigQuestObject<>(o -> this.isSuitableTask(data, o), this::formatLine), this.getTask(), this::setTask, null))
         .setNameKey("ftbquests.task");
      ((ItemStackConfig)cg.add("skin", new ItemStackConfig(true, true), this.getSkin(), this::setSkin, ItemStack.EMPTY))
         .setNameKey("block.ftbquests.screen.skin");
      ((BooleanConfig)cg.add("text_shadow", new BooleanConfig(), this.isTextShadow(), this::setTextShadow, false))
         .setNameKey("block.ftbquests.screen.text_shadow");
      ((BooleanConfig)cg.add("indestructible", new BooleanConfig(), this.isIndestructible(), this::setIndestructible, false))
         .setNameKey("block.ftbquests.screen.indestructible");
      ((BooleanConfig)cg.add("input_only", new BooleanConfig(), this.isInputOnly(), this::setInputOnly, false)).setNameKey("block.ftbquests.screen.input_only");
      ((ItemStackConfig)cg.add("input_icon", new ItemStackConfig(true, true), this.getInputModeIcon(), this::setInputModeIcon, ItemStack.EMPTY))
         .setNameKey("block.ftbquests.screen.input_mode_icon");
      return cg0;
   }

   private Component formatLine(Task task) {
      if (task == null) {
         return Component.empty();
      }

      Component questTxt = Component.literal(" [").append(task.getQuest().getTitle()).append("]").withStyle(ChatFormatting.GREEN);
      return ConfigQuestObject.formatEntry(task).copy().append(questTxt);
   }

   private boolean isSuitableTask(TeamData data, QuestObjectBase o) {
      return o instanceof Task t && (data.getCanEdit(FTBQuestsClient.getClientPlayer()) || data.canStartTasks(t.getQuest())) && t.consumesResources();
   }

   public float[] getFakeTextureUV() {
      if (this.fakeTextureUV == null) {
         if (!this.skin.isEmpty() && this.skin.getItem() instanceof BlockItem bi) {
            BlockState state = bi.getBlock().defaultBlockState();
            Direction facing = (Direction)this.getBlockState().getValue(TaskScreenBlock.FACING);
            if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
               state = (BlockState)state.setValue(BlockStateProperties.HORIZONTAL_FACING, facing);
            } else if (state.hasProperty(BlockStateProperties.FACING)) {
               state = (BlockState)state.setValue(BlockStateProperties.FACING, facing);
            }

            this.fakeTextureUV = FTBQuestsClient.getTextureUV(state, facing);
         } else {
            this.fakeTextureUV = new float[0];
         }
      }

      return this.fakeTextureUV;
   }

   @Override
   public boolean hasPermissionToEdit(Player player) {
      return player.getUUID().equals(this.getTeamId())
         ? true
         : FTBTeamsAPI.api().getManager().getTeamByID(this.getTeamId()).map(team -> team.getRankForPlayer(player.getUUID()).isMemberOrBetter()).orElse(false);
   }

   public record TaskScreenSaveData(long taskId, ItemStack skin, boolean indestructible, boolean inputOnly, ItemStack inputModeIcon, boolean textShadow) {
      public static TaskScreenBlockEntity.TaskScreenSaveData DEFAULT = new TaskScreenBlockEntity.TaskScreenSaveData(
         0L, ItemStack.EMPTY, false, false, ItemStack.EMPTY, false
      );
      public static final Codec<TaskScreenBlockEntity.TaskScreenSaveData> CODEC = RecordCodecBuilder.create(
         builder -> builder.group(
               Codec.LONG.optionalFieldOf("taskId", 0L).forGetter(TaskScreenBlockEntity.TaskScreenSaveData::taskId),
               ItemStack.CODEC.optionalFieldOf("skin", ItemStack.EMPTY).forGetter(TaskScreenBlockEntity.TaskScreenSaveData::skin),
               Codec.BOOL.optionalFieldOf("indestructible", false).forGetter(TaskScreenBlockEntity.TaskScreenSaveData::indestructible),
               Codec.BOOL.optionalFieldOf("input_only", false).forGetter(TaskScreenBlockEntity.TaskScreenSaveData::inputOnly),
               ItemStack.CODEC.optionalFieldOf("skin", ItemStack.EMPTY).forGetter(TaskScreenBlockEntity.TaskScreenSaveData::inputModeIcon),
               Codec.BOOL.optionalFieldOf("text_shadow", false).forGetter(TaskScreenBlockEntity.TaskScreenSaveData::textShadow)
            )
            .apply(builder, TaskScreenBlockEntity.TaskScreenSaveData::new)
      );
      public static StreamCodec<RegistryFriendlyByteBuf, TaskScreenBlockEntity.TaskScreenSaveData> STREAM_CODEC = StreamCodec.composite(
         ByteBufCodecs.VAR_LONG,
         TaskScreenBlockEntity.TaskScreenSaveData::taskId,
         ItemStack.OPTIONAL_STREAM_CODEC,
         TaskScreenBlockEntity.TaskScreenSaveData::skin,
         ByteBufCodecs.BOOL,
         TaskScreenBlockEntity.TaskScreenSaveData::indestructible,
         ByteBufCodecs.BOOL,
         TaskScreenBlockEntity.TaskScreenSaveData::inputOnly,
         ItemStack.OPTIONAL_STREAM_CODEC,
         TaskScreenBlockEntity.TaskScreenSaveData::inputModeIcon,
         ByteBufCodecs.BOOL,
         TaskScreenBlockEntity.TaskScreenSaveData::textShadow,
         TaskScreenBlockEntity.TaskScreenSaveData::new
      );

      public static TaskScreenBlockEntity.TaskScreenSaveData fromBlockEntity(TaskScreenBlockEntity b) {
         return new TaskScreenBlockEntity.TaskScreenSaveData(b.taskId, b.skin, b.indestructible, b.inputOnly, b.inputModeIcon, b.textShadow);
      }
   }
}
