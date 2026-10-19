package net.p3pp3rf1y.sophisticatedbackpacks.backpack;

import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public class AccessLogRecord {
   private final ResourceLocation backpackItemRegistryName;
   private final UUID backpackUuid;
   private final String playerName;
   private final String backpackName;
   private final int clothColor;
   private final int trimColor;
   private final long accessTime;
   private final int columnsTaken;

   public AccessLogRecord(
      ResourceLocation backpackItemRegistryName,
      UUID backpackUuid,
      String playerName,
      String backpackName,
      int clothColor,
      int trimColor,
      long accessTime,
      int columnsTaken
   ) {
      this.backpackItemRegistryName = backpackItemRegistryName;
      this.backpackUuid = backpackUuid;
      this.playerName = playerName;
      this.backpackName = backpackName;
      this.clothColor = clothColor;
      this.trimColor = trimColor;
      this.accessTime = accessTime;
      this.columnsTaken = columnsTaken;
   }

   public UUID getBackpackUuid() {
      return this.backpackUuid;
   }

   public String getPlayerName() {
      return this.playerName;
   }

   public String getBackpackName() {
      return this.backpackName;
   }

   public int getClothColor() {
      return this.clothColor;
   }

   public int getTrimColor() {
      return this.trimColor;
   }

   public long getAccessTime() {
      return this.accessTime;
   }

   public int getColumnsTaken() {
      return this.columnsTaken;
   }

   public ResourceLocation getBackpackItemRegistryName() {
      return this.backpackItemRegistryName;
   }

   public CompoundTag serializeToNBT() {
      CompoundTag ret = new CompoundTag();
      ret.putString("backpackItemRegistryName", this.backpackItemRegistryName.toString());
      ret.putUUID("backpackUuid", this.backpackUuid);
      ret.putString("playerName", this.playerName);
      ret.putString("backpackName", this.backpackName);
      ret.putInt("clothColor", this.clothColor);
      ret.putInt("trimColor", this.trimColor);
      ret.putLong("accessTime", this.accessTime);
      ret.putInt("columnsTaken", this.columnsTaken);
      return ret;
   }

   public static AccessLogRecord deserializeFromNBT(CompoundTag nbt) {
      return new AccessLogRecord(
         ResourceLocation.parse(nbt.getString("backpackItemRegistryName")),
         nbt.getUUID("backpackUuid"),
         nbt.getString("playerName"),
         nbt.getString("backpackName"),
         nbt.getInt("clothColor"),
         nbt.getInt("trimColor"),
         nbt.getLong("accessTime"),
         nbt.getInt("columnsTaken")
      );
   }
}
