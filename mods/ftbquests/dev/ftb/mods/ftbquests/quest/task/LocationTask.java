package dev.ftb.mods.ftbquests.quest.task;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.TeamData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class LocationTask extends AbstractBooleanTask {
   private ResourceKey<Level> dimension = Level.OVERWORLD;
   private boolean ignoreDimension = false;
   private int x;
   private int y;
   private int z;
   private int w;
   private int h;
   private int d;

   public LocationTask(long id, Quest quest) {
      super(id, quest);
      this.x = this.y = this.z = 0;
      this.w = this.h = this.d = 1;
   }

   public void initFromStructure(StructureBlockEntity structure) {
      BlockPos pos = structure.getStructurePos();
      Vec3i size = structure.getStructureSize();
      this.dimension = structure.getLevel().dimension();
      this.x = pos.getX() + structure.getBlockPos().getX();
      this.y = pos.getY() + structure.getBlockPos().getY();
      this.z = pos.getZ() + structure.getBlockPos().getZ();
      this.w = Math.max(1, size.getX());
      this.h = Math.max(1, size.getY());
      this.d = Math.max(1, size.getZ());
   }

   @Override
   public TaskType getType() {
      return TaskTypes.LOCATION;
   }

   @Override
   public void writeData(CompoundTag nbt, Provider provider) {
      super.writeData(nbt, provider);
      nbt.putString("dimension", this.dimension.location().toString());
      nbt.putBoolean("ignore_dimension", this.ignoreDimension);
      nbt.putIntArray("position", new int[]{this.x, this.y, this.z});
      nbt.putIntArray("size", new int[]{this.w, this.h, this.d});
   }

   @Override
   public void readData(CompoundTag nbt, Provider provider) {
      super.readData(nbt, provider);
      this.dimension = ResourceKey.create(Registries.DIMENSION, ResourceLocation.tryParse(nbt.getString("dimension")));
      this.ignoreDimension = nbt.getBoolean("ignore_dimension");
      int[] pos = nbt.getIntArray("position");
      if (pos.length == 3) {
         this.x = pos[0];
         this.y = pos[1];
         this.z = pos[2];
      }

      int[] size = nbt.getIntArray("size");
      if (pos.length == 3) {
         this.w = size[0];
         this.h = size[1];
         this.d = size[2];
      }
   }

   @Override
   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeResourceLocation(this.dimension.location());
      buffer.writeBoolean(this.ignoreDimension);
      buffer.writeVarInt(this.x);
      buffer.writeVarInt(this.y);
      buffer.writeVarInt(this.z);
      buffer.writeVarInt(this.w);
      buffer.writeVarInt(this.h);
      buffer.writeVarInt(this.d);
   }

   @Override
   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.dimension = ResourceKey.create(Registries.DIMENSION, buffer.readResourceLocation());
      this.ignoreDimension = buffer.readBoolean();
      this.x = buffer.readVarInt();
      this.y = buffer.readVarInt();
      this.z = buffer.readVarInt();
      this.w = buffer.readVarInt();
      this.h = buffer.readVarInt();
      this.d = buffer.readVarInt();
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addString(
         "dim",
         this.dimension.location().toString(),
         v -> this.dimension = ResourceKey.create(Registries.DIMENSION, ResourceLocation.tryParse(v)),
         "minecraft:overworld"
      );
      config.addBool("ignore_dim", this.ignoreDimension, v -> this.ignoreDimension = v, false);
      config.addInt("x", this.x, v -> this.x = v, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
      config.addInt("y", this.y, v -> this.y = v, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
      config.addInt("z", this.z, v -> this.z = v, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
      config.addInt("w", this.w, v -> this.w = v, 1, 1, Integer.MAX_VALUE);
      config.addInt("h", this.h, v -> this.h = v, 1, 1, Integer.MAX_VALUE);
      config.addInt("d", this.d, v -> this.d = v, 1, 1, Integer.MAX_VALUE);
   }

   @Override
   public int autoSubmitOnPlayerTick() {
      return 3;
   }

   @Override
   public boolean canSubmit(TeamData teamData, ServerPlayer player) {
      if (this.ignoreDimension || this.dimension == player.level().dimension()) {
         int py = Mth.floor(player.getY());
         if (py >= this.y && py < this.y + this.h) {
            int px = Mth.floor(player.getX());
            if (px >= this.x && px < this.x + this.w) {
               int pz = Mth.floor(player.getZ());
               return pz >= this.z && pz < this.z + this.d;
            }
         }
      }

      return false;
   }
}
