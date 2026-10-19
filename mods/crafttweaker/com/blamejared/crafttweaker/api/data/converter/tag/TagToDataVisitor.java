package com.blamejared.crafttweaker.api.data.converter.tag;

import com.blamejared.crafttweaker.api.data.ByteArrayData;
import com.blamejared.crafttweaker.api.data.ByteData;
import com.blamejared.crafttweaker.api.data.DoubleData;
import com.blamejared.crafttweaker.api.data.FloatData;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.IntArrayData;
import com.blamejared.crafttweaker.api.data.IntData;
import com.blamejared.crafttweaker.api.data.ListData;
import com.blamejared.crafttweaker.api.data.LongArrayData;
import com.blamejared.crafttweaker.api.data.LongData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.ShortData;
import com.blamejared.crafttweaker.api.data.StringData;
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagVisitor;
import org.jetbrains.annotations.NotNull;

public class TagToDataVisitor implements TagVisitor {
   private IData value;

   protected TagToDataVisitor() {
   }

   public IData visit(Tag tag) {
      tag.accept(this);
      return this.value;
   }

   private void setValue(IData data) {
      this.value = data;
   }

   public IData getValue() {
      return this.value;
   }

   public void visitString(@NotNull StringTag tag) {
      this.setValue(new StringData(tag));
   }

   public void visitByte(@NotNull ByteTag tag) {
      this.setValue(new ByteData(tag));
   }

   public void visitShort(@NotNull ShortTag tag) {
      this.setValue(new ShortData(tag));
   }

   public void visitInt(@NotNull IntTag tag) {
      this.setValue(new IntData(tag));
   }

   public void visitLong(@NotNull LongTag tag) {
      this.setValue(new LongData(tag));
   }

   public void visitFloat(@NotNull FloatTag tag) {
      this.setValue(new FloatData(tag));
   }

   public void visitDouble(@NotNull DoubleTag tag) {
      this.setValue(new DoubleData(tag));
   }

   public void visitByteArray(@NotNull ByteArrayTag tag) {
      this.setValue(new ByteArrayData(tag));
   }

   public void visitIntArray(@NotNull IntArrayTag tag) {
      this.setValue(new IntArrayData(tag));
   }

   public void visitLongArray(@NotNull LongArrayTag tag) {
      this.setValue(new LongArrayData(tag));
   }

   public void visitList(@NotNull ListTag tag) {
      this.setValue(new ListData(tag));
   }

   public void visitCompound(@NotNull CompoundTag tag) {
      this.setValue(new MapData(tag));
   }

   public void visitEnd(@NotNull EndTag tag) {
      this.setValue(null);
   }
}
