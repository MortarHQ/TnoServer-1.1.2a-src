package com.blamejared.crafttweaker.api.level;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/world/CraftTweakerSavedData")
@ZenCodeType.Name("crafttweaker.api.world.CraftTweakerSavedData")
public class CraftTweakerSavedData extends SavedData {
   private MapData data;

   public CraftTweakerSavedData() {
      this.data = new MapData();
   }

   public CraftTweakerSavedData(@Nonnull MapData data) {
      this.data = data;
   }

   public static CraftTweakerSavedData load(CompoundTag tag, Provider lookup) {
      CompoundTag dataTag = tag.getCompound("data");
      Set<String> booleanKeys = tag.getList("booleanKeys", 8).stream().<String>map(Tag::getAsString).collect(Collectors.toSet());
      return new CraftTweakerSavedData(new MapData(dataTag, booleanKeys));
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("data")
   public IData getData() {
      return this.data;
   }

   @ZenCodeType.Method
   @ZenCodeType.Setter("data")
   public void setData(MapData data) {
      this.data = data;
   }

   @ZenCodeType.Method
   public void updateData(IData data) {
      this.data = (MapData)this.data.merge(data);
   }

   @NotNull
   public CompoundTag save(CompoundTag tag, Provider lookup) {
      ListTag booleanKeys = new ListTag();
      this.data.boolDataKeys().stream().map(StringTag::valueOf).forEach(booleanKeys::add);
      tag.put("data", this.data.getInternal());
      tag.put("booleanKeys", booleanKeys);
      return tag;
   }

   public boolean isDirty() {
      return true;
   }
}
