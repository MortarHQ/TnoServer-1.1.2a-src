package dev.ftb.mods.ftbquests.block.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.architectury.networking.NetworkManager;
import dev.architectury.platform.Platform;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.config.ItemStackConfig;
import dev.ftb.mods.ftblibrary.util.Lazy;
import dev.ftb.mods.ftbquests.api.FTBQuestsAPI;
import dev.ftb.mods.ftbquests.block.QuestBarrierBlock;
import dev.ftb.mods.ftbquests.client.FTBQuestsClient;
import dev.ftb.mods.ftbquests.net.BlockConfigResponseMessage;
import dev.ftb.mods.ftbquests.registry.ModBlocks;
import dev.ftb.mods.ftbquests.registry.ModDataComponents;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponentMap.Builder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntity.DataComponentInput;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public abstract class BaseBarrierBlockEntity extends EditableBlockEntity {
   private static final Lazy<BlockState> OPEN_BARRIER = Lazy.of(
      () -> (BlockState)((Block)ModBlocks.BARRIER.get()).defaultBlockState().setValue(QuestBarrierBlock.OPEN, true)
   );
   private static final Lazy<BlockState> CLOSED_BARRIER = Lazy.of(
      () -> (BlockState)((Block)ModBlocks.BARRIER.get()).defaultBlockState().setValue(QuestBarrierBlock.OPEN, false)
   );
   protected String objStr = "";
   protected BlockState camo = null;
   private boolean invisibleWhenOpen = false;
   private ItemStack skin = ItemStack.EMPTY;
   private BaseBarrierBlockEntity.TeleportData teleportData = BaseBarrierBlockEntity.TeleportData.NONE;
   private boolean openStatus;
   private long lastOpenCheck = 0L;

   public BaseBarrierBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
      super(blockEntityType, blockPos, blockState);
   }

   public static void tick(Level level, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity) {
      if (blockEntity instanceof BaseBarrierBlockEntity barrier && level.isClientSide && FTBQuestsClient.isClientDataLoaded() && level.getGameTime() % 5L == 0L
         )
       {
         boolean completed = barrier.isOpen(FTBQuestsClient.getClientPlayer());
         if (completed != (Boolean)blockState.getValue(QuestBarrierBlock.OPEN)) {
            level.setBlock(blockPos, (BlockState)blockState.setValue(QuestBarrierBlock.OPEN, completed), 10);
            blockEntity.setChanged();
            barrier.forceAppearanceUpdate();
         }
      }
   }

   public void loadAdditional(CompoundTag tag, Provider provider) {
      super.loadAdditional(tag, provider);
      if (tag.contains("Object")) {
         this.objStr = tag.getString("Object");
         this.skin = ItemStack.EMPTY;
      } else {
         BaseBarrierBlockEntity.BarrierSavedData data = BaseBarrierBlockEntity.BarrierSavedData.CODEC
            .parse(NbtOps.INSTANCE, tag.getCompound("savedData"))
            .result()
            .orElse(BaseBarrierBlockEntity.BarrierSavedData.DEFAULT);
         this.applySavedData(data);
      }
   }

   public void saveAdditional(CompoundTag compoundTag, Provider provider) {
      super.saveAdditional(compoundTag, provider);
      BaseBarrierBlockEntity.BarrierSavedData.CODEC
         .encodeStart(NbtOps.INSTANCE, BaseBarrierBlockEntity.BarrierSavedData.fromBlockEntity(this))
         .ifSuccess(tag -> compoundTag.put("savedData", tag));
   }

   protected void applyImplicitComponents(DataComponentInput dataComponentInput) {
      super.applyImplicitComponents(dataComponentInput);
      this.applySavedData(
         (BaseBarrierBlockEntity.BarrierSavedData)dataComponentInput.getOrDefault(
            (DataComponentType)ModDataComponents.BARRIER_SAVED.get(), BaseBarrierBlockEntity.BarrierSavedData.DEFAULT
         )
      );
   }

   protected void collectImplicitComponents(Builder builder) {
      super.collectImplicitComponents(builder);
      builder.set((DataComponentType)ModDataComponents.BARRIER_SAVED.get(), BaseBarrierBlockEntity.BarrierSavedData.fromBlockEntity(this));
   }

   protected void applySavedData(BaseBarrierBlockEntity.BarrierSavedData data) {
      this.objStr = data.objStr;
      this.setSkin(data.skin);
      this.setInvisibleWhenOpen(data.invisibleWhenOpen);
      this.teleportData = data.teleportData.orElse(BaseBarrierBlockEntity.TeleportData.NONE);
   }

   @Nullable
   public Packet<ClientGamePacketListener> getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   public CompoundTag getUpdateTag(Provider provider) {
      return this.saveWithoutMetadata(provider);
   }

   public void setChanged() {
      if (this.level instanceof ServerLevel) {
         this.level.blockEntityChanged(this.getBlockPos());
      }
   }

   public void updateFromString(String objStr) {
      this.objStr = objStr;
      this.setChanged();
   }

   public boolean isOpen(Player player) {
      long now = Util.getMillis();
      if (now - this.lastOpenCheck > 300L) {
         this.openStatus = this.checkIfOpen(player);
         this.lastOpenCheck = now;
      }

      return this.openStatus;
   }

   protected abstract boolean checkIfOpen(Player var1);

   protected abstract void addConfigEntries(ConfigGroup var1);

   public ConfigGroup fillConfigGroup() {
      ConfigGroup group = new ConfigGroup("ftbquests.barrier", accepted -> {
         if (accepted) {
            NetworkManager.sendToServer(new BlockConfigResponseMessage(this.getBlockPos(), this.saveWithoutMetadata(this.getLevel().registryAccess())));
         }
      });
      group.setNameKey(this.getBlockState().getBlock().getDescriptionId());
      this.addConfigEntries(group);
      if (Platform.isForgeLike()) {
         ConfigGroup appearance = group.getOrCreateSubgroup("appearance").setNameKey("ftbquests.quest.appearance");
         ((ItemStackConfig)appearance.add("skin", new ItemStackConfig(true, true), this.getSkin(), this::setSkin, ItemStack.EMPTY))
            .withFilter(stack -> stack.getItem() instanceof BlockItem)
            .setNameKey("block.ftbquests.screen.skin");
         appearance.addBool("invis_when_open", this.isInvisibleWhenOpen(), this::setInvisibleWhenOpen, false)
            .setNameKey("block.ftbquests.barrier.invis_when_open");
      }

      ConfigGroup teleport = group.getOrCreateSubgroup("teleport").setNameKey("ftbquests.barrier.teleport");
      teleport.addBool("enabled", this.teleportData.enabled, v -> this.teleportData = this.teleportData.withEnabled(v), false);
      teleport.addBool("relative", this.teleportData.relative, v -> this.teleportData = this.teleportData.withRelative(v), false);
      teleport.addInt("dest_x", this.teleportData.dest.getX(), v -> this.teleportData = this.teleportData.withDestX(v), 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
      teleport.addInt("dest_y", this.teleportData.dest.getY(), v -> this.teleportData = this.teleportData.withDestY(v), 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
      teleport.addInt("dest_z", this.teleportData.dest.getZ(), v -> this.teleportData = this.teleportData.withDestZ(v), 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
      teleport.addDouble("pitch", this.teleportData.pitch, v -> this.teleportData = this.teleportData.withPitch(v.floatValue()), 0.0, -90.0, 90.0);
      teleport.addDouble("yaw", this.teleportData.yaw, v -> this.teleportData = this.teleportData.withYaw(v.floatValue()), 0.0, 0.0, 360.0);
      teleport.addString("dimension", this.teleportData.dimStr(), v -> this.teleportData = this.teleportData.withDimId(v), "");
      return group;
   }

   public ItemStack getSkin() {
      return this.skin;
   }

   public void setSkin(ItemStack skin) {
      this.skin = skin;
      this.forceAppearanceUpdate();
      this.setChanged();
   }

   public boolean isInvisibleWhenOpen() {
      return this.invisibleWhenOpen;
   }

   public void setInvisibleWhenOpen(boolean invisibleWhenOpen) {
      this.invisibleWhenOpen = invisibleWhenOpen;
      this.forceAppearanceUpdate();
      this.setChanged();
   }

   public void forceAppearanceUpdate() {
      this.camo = null;
   }

   public BlockState getClientAppearance() {
      if (this.camo == null) {
         if (this.getBlockState().hasProperty(QuestBarrierBlock.OPEN) && (Boolean)this.getBlockState().getValue(QuestBarrierBlock.OPEN)) {
            this.camo = this.invisibleWhenOpen ? Blocks.AIR.defaultBlockState() : (BlockState)OPEN_BARRIER.get();
         } else {
            this.camo = this.skin.getItem() instanceof BlockItem bi ? bi.getBlock().defaultBlockState() : (BlockState)CLOSED_BARRIER.get();
         }
      }

      return this.camo;
   }

   @Override
   public boolean hasPermissionToEdit(Player player) {
      return FTBQuestsAPI.api().getQuestFile(this.level.isClientSide).getTeamData(player).map(team -> team.getCanEdit(player)).orElse(false);
   }

   public Optional<BaseBarrierBlockEntity.TeleportData> optionalTeleportData() {
      return this.teleportData.equals(BaseBarrierBlockEntity.TeleportData.NONE) ? Optional.empty() : Optional.of(this.teleportData);
   }

   public record BarrierSavedData(String objStr, ItemStack skin, boolean invisibleWhenOpen, Optional<BaseBarrierBlockEntity.TeleportData> teleportData) {
      public static final BaseBarrierBlockEntity.BarrierSavedData DEFAULT = new BaseBarrierBlockEntity.BarrierSavedData(
         "", ItemStack.EMPTY, false, Optional.empty()
      );
      public static final Codec<BaseBarrierBlockEntity.BarrierSavedData> CODEC = RecordCodecBuilder.create(
         builder -> builder.group(
               Codec.STRING.optionalFieldOf("object", "").forGetter(BaseBarrierBlockEntity.BarrierSavedData::objStr),
               ItemStack.CODEC.optionalFieldOf("skin", ItemStack.EMPTY).forGetter(BaseBarrierBlockEntity.BarrierSavedData::skin),
               Codec.BOOL.optionalFieldOf("invis_when_open", false).forGetter(BaseBarrierBlockEntity.BarrierSavedData::invisibleWhenOpen),
               BaseBarrierBlockEntity.TeleportData.CODEC.optionalFieldOf("teleport").forGetter(BaseBarrierBlockEntity.BarrierSavedData::teleportData)
            )
            .apply(builder, BaseBarrierBlockEntity.BarrierSavedData::new)
      );
      public static StreamCodec<RegistryFriendlyByteBuf, BaseBarrierBlockEntity.BarrierSavedData> STREAM_CODEC = StreamCodec.composite(
         ByteBufCodecs.STRING_UTF8,
         BaseBarrierBlockEntity.BarrierSavedData::objStr,
         ItemStack.OPTIONAL_STREAM_CODEC,
         BaseBarrierBlockEntity.BarrierSavedData::skin,
         ByteBufCodecs.BOOL,
         BaseBarrierBlockEntity.BarrierSavedData::invisibleWhenOpen,
         ByteBufCodecs.optional(BaseBarrierBlockEntity.TeleportData.STREAM_CODEC),
         BaseBarrierBlockEntity.BarrierSavedData::teleportData,
         BaseBarrierBlockEntity.BarrierSavedData::new
      );

      public static BaseBarrierBlockEntity.BarrierSavedData fromBlockEntity(BaseBarrierBlockEntity b) {
         return new BaseBarrierBlockEntity.BarrierSavedData(b.objStr, b.skin, b.invisibleWhenOpen, b.optionalTeleportData());
      }

      public void addTooltipInfo(BaseBarrierBlockEntity.BarrierSavedData data, List<Component> tooltip, String what) {
         tooltip.add(
            Component.translatable("item.ftbquests.barrier.object." + what, new Object[]{data.objStr().isEmpty() ? "-" : data.objStr})
               .withStyle(ChatFormatting.GRAY)
         );
         if (Platform.isForgeLike() && !data.skin().isEmpty()) {
            tooltip.add(Component.translatable("item.ftbquests.barrier.skin", new Object[]{data.skin().getDisplayName()}).withStyle(ChatFormatting.GRAY));
         }

         tooltip.add(Component.translatable("item.ftbquests.barrier.invis_when_open", new Object[]{data.invisibleWhenOpen()}).withStyle(ChatFormatting.GRAY));
      }
   }

   public record TeleportData(boolean enabled, boolean relative, BlockPos dest, float pitch, float yaw, Optional<ResourceKey<Level>> dimId) {
      public static final Codec<BaseBarrierBlockEntity.TeleportData> CODEC = RecordCodecBuilder.create(
         builder -> builder.group(
               Codec.BOOL.optionalFieldOf("enabled", false).forGetter(BaseBarrierBlockEntity.TeleportData::enabled),
               Codec.BOOL.optionalFieldOf("relative", false).forGetter(BaseBarrierBlockEntity.TeleportData::relative),
               BlockPos.CODEC.fieldOf("dest").forGetter(BaseBarrierBlockEntity.TeleportData::dest),
               Codec.FLOAT.fieldOf("pitch").forGetter(BaseBarrierBlockEntity.TeleportData::pitch),
               Codec.FLOAT.fieldOf("yaw").forGetter(BaseBarrierBlockEntity.TeleportData::yaw),
               ResourceKey.codec(Registries.DIMENSION).optionalFieldOf("dim_id").forGetter(BaseBarrierBlockEntity.TeleportData::dimId)
            )
            .apply(builder, BaseBarrierBlockEntity.TeleportData::new)
      );
      public static final StreamCodec<RegistryFriendlyByteBuf, BaseBarrierBlockEntity.TeleportData> STREAM_CODEC = StreamCodec.composite(
         ByteBufCodecs.BOOL,
         BaseBarrierBlockEntity.TeleportData::enabled,
         ByteBufCodecs.BOOL,
         BaseBarrierBlockEntity.TeleportData::relative,
         BlockPos.STREAM_CODEC,
         BaseBarrierBlockEntity.TeleportData::dest,
         ByteBufCodecs.FLOAT,
         BaseBarrierBlockEntity.TeleportData::pitch,
         ByteBufCodecs.FLOAT,
         BaseBarrierBlockEntity.TeleportData::yaw,
         ByteBufCodecs.optional(ResourceKey.streamCodec(Registries.DIMENSION)),
         BaseBarrierBlockEntity.TeleportData::dimId,
         BaseBarrierBlockEntity.TeleportData::new
      );
      public static final BaseBarrierBlockEntity.TeleportData NONE = new BaseBarrierBlockEntity.TeleportData(
         false, false, BlockPos.ZERO, 0.0F, 0.0F, Optional.empty()
      );

      public BaseBarrierBlockEntity.TeleportData withEnabled(boolean enabled) {
         return new BaseBarrierBlockEntity.TeleportData(enabled, this.relative, this.dest, this.pitch, this.yaw, this.dimId);
      }

      public BaseBarrierBlockEntity.TeleportData withRelative(boolean relative) {
         return new BaseBarrierBlockEntity.TeleportData(this.enabled, relative, this.dest, this.pitch, this.yaw, this.dimId);
      }

      public BaseBarrierBlockEntity.TeleportData withPitch(float pitch) {
         return new BaseBarrierBlockEntity.TeleportData(this.enabled, this.relative, this.dest, pitch, this.yaw, this.dimId);
      }

      public BaseBarrierBlockEntity.TeleportData withYaw(float yaw) {
         return new BaseBarrierBlockEntity.TeleportData(this.enabled, this.relative, this.dest, this.pitch, yaw, this.dimId);
      }

      public BaseBarrierBlockEntity.TeleportData withDimId(String dimStr) {
         return this.withDimId(
            !dimStr.isEmpty() && ResourceLocation.tryParse(dimStr) != null ? ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(dimStr)) : null
         );
      }

      public BaseBarrierBlockEntity.TeleportData withDimId(@Nullable ResourceKey<Level> dimId) {
         return new BaseBarrierBlockEntity.TeleportData(this.enabled, this.relative, this.dest, this.pitch, this.yaw, Optional.ofNullable(dimId));
      }

      public BaseBarrierBlockEntity.TeleportData withDestPos(BlockPos pos) {
         return new BaseBarrierBlockEntity.TeleportData(this.enabled, this.relative, pos, this.pitch, this.yaw, this.dimId);
      }

      public BaseBarrierBlockEntity.TeleportData withDestX(int x) {
         return this.withDestPos(new BlockPos(x, this.dest.getY(), this.dest.getY()));
      }

      public BaseBarrierBlockEntity.TeleportData withDestY(int y) {
         return this.withDestPos(new BlockPos(this.dest.getX(), y, this.dest.getY()));
      }

      public BaseBarrierBlockEntity.TeleportData withDestZ(int z) {
         return this.withDestPos(new BlockPos(this.dest.getX(), this.dest.getY(), z));
      }

      public String dimStr() {
         return this.dimId.<String>map(id -> id.location().toString()).orElse("");
      }

      @Nullable
      public Level getLevel(MinecraftServer server) {
         return this.dimId.<Level>map(server::getLevel).orElse(null);
      }

      public BaseBarrierBlockEntity.TeleportData effectiveDest(BlockPos basePos) {
         return this.relative ? this.withDestPos(basePos.offset(this.dest)) : this;
      }

      public void teleportPlayer(ServerPlayer player) {
         if (player != null && player.getServer() != null && this.enabled) {
            Vec3 dest = this.dest().getBottomCenter();
            Level destLevel = Objects.requireNonNullElse(this.getLevel(player.getServer()), player.level());
            if (destLevel instanceof ServerLevel serverLevel) {
               player.teleportTo(serverLevel, dest.x, dest.y, dest.z, this.yaw(), this.pitch());
               Holder<SoundEvent> sound = BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.PLAYER_TELEPORT);
               player.connection
                  .send(new ClientboundSoundPacket(sound, SoundSource.PLAYERS, dest.x, dest.y, dest.z, 0.5F, 1.0F, serverLevel.getRandom().nextLong()));
            }
         }
      }
   }
}
