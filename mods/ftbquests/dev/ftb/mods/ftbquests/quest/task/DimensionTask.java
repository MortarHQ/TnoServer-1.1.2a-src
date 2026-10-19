package dev.ftb.mods.ftbquests.quest.task;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.config.NameMap;
import dev.ftb.mods.ftblibrary.util.KnownServerRegistries;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.TeamData;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class DimensionTask extends AbstractBooleanTask {
   private ResourceKey<Level> dimension = Level.NETHER;

   public DimensionTask(long id, Quest quest) {
      super(id, quest);
   }

   public DimensionTask withDimension(ResourceKey<Level> dimension) {
      this.dimension = dimension;
      return this;
   }

   @Override
   public TaskType getType() {
      return TaskTypes.DIMENSION;
   }

   @Override
   public void writeData(CompoundTag nbt, Provider provider) {
      super.writeData(nbt, provider);
      nbt.putString("dimension", this.dimension.location().toString());
   }

   @Override
   public void readData(CompoundTag nbt, Provider provider) {
      super.readData(nbt, provider);
      this.dimension = ResourceKey.create(Registries.DIMENSION, ResourceLocation.tryParse(nbt.getString("dimension")));
   }

   @Override
   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeResourceLocation(this.dimension.location());
   }

   @Override
   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.dimension = ResourceKey.create(Registries.DIMENSION, buffer.readResourceLocation());
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      if (KnownServerRegistries.client != null && !KnownServerRegistries.client.dimension().isEmpty()) {
         List<ResourceLocation> dimensions = KnownServerRegistries.client.dimension();
         config.addEnum(
            "dim",
            this.dimension.location(),
            v -> this.dimension = ResourceKey.create(Registries.DIMENSION, v),
            NameMap.of(dimensions.getFirst(), (ResourceLocation[])dimensions.toArray(new ResourceLocation[0])).create()
         );
      } else {
         config.addString(
            "dim",
            this.dimension.location().toString(),
            v -> this.dimension = ResourceKey.create(Registries.DIMENSION, ResourceLocation.tryParse(v)),
            "minecraft:the_nether"
         );
      }
   }

   @OnlyIn(Dist.CLIENT)
   public MutableComponent getAltTitle() {
      return Component.translatable("ftbquests.task.ftbquests.dimension")
         .append(": ")
         .append(Component.literal(this.dimension.location().toString()).withStyle(ChatFormatting.DARK_GREEN));
   }

   @Override
   public int autoSubmitOnPlayerTick() {
      return 100;
   }

   @Override
   public boolean canSubmit(TeamData teamData, ServerPlayer player) {
      return !player.isSpectator() && player.level().dimension() == this.dimension;
   }
}
