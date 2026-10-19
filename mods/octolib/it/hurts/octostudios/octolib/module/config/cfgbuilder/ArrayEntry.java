package it.hurts.octostudios.octolib.module.config.cfgbuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayEntry extends ConfigEntry implements Iterable<ConfigEntry> {
   public static final CfgTag SEQ_I = new CfgTag("tag:yaml.org,2002:seq_seq");
   List<ConfigEntry> list = new ArrayList<>();

   public ArrayEntry() {
      this((CfgTag)null);
   }

   public ArrayEntry(CfgTag tag) {
      super(CfgTag.SEQ, EntryId.SEQUENCE);
      this.type = tag;
   }

   public ArrayEntry(Collection<? extends ConfigEntry> entries) {
      this(null, entries);
   }

   public ArrayEntry(CfgTag tag, Collection<? extends ConfigEntry> entries) {
      super(CfgTag.SEQ, EntryId.SEQUENCE);
      this.list.addAll(entries);
      this.type = tag;
   }

   public <T extends ConfigEntry> ArrayEntry(T... entries) {
      this(null, entries);
   }

   public <T extends ConfigEntry> ArrayEntry(CfgTag tag, T... entries) {
      super(CfgTag.SEQ, EntryId.SEQUENCE);
      this.list.addAll(List.of(entries));
      this.type = tag;
   }

   public void remove() {
      if (this.list.isEmpty()) {
         throw new IllegalStateException("Array entry is empty");
      }

      this.list.remove(this.list.size() - 1);
   }

   public void add(ConfigEntry entry) {
      if (this.getType() != null && entry.getTag() != this.getType()) {
         throw new IllegalArgumentException(
            String.format("Illegal tag type: entry with tag %s cannot be added in list with tag %s.", entry.getTag(), this.getType())
         );
      }

      this.list.add(entry);
   }

   public void add(int index, ConfigEntry entry) {
      if (this.getType() != null && entry.getTag() != this.getType()) {
         throw new IllegalArgumentException(
            String.format("Illegal tag type: entry with tag %s cannot be added in list with tag %s.", entry.getTag(), this.getType())
         );
      }

      this.list.add(index, entry);
   }

   public int size() {
      return this.list.size();
   }

   public boolean isEmpty() {
      return this.list.isEmpty();
   }

   @Override
   public Object getData() {
      return this.list;
   }

   @Override
   public Iterator<ConfigEntry> iterator() {
      return this.list.iterator();
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder("ArrayEntry");
      builder.append(this.getType() == null ? " " : "!!" + this.getType()).append("[\n");
      Iterator<ConfigEntry> iterator = this.list.iterator();

      while (iterator.hasNext()) {
         ConfigEntry value = iterator.next();
         builder.append(
            Arrays.stream((value.getData() + (iterator.hasNext() ? "," : "")).split("\n")).map(ss -> "\t" + ss + "\n").collect(Collectors.joining())
         );
      }

      builder.append("]");
      return builder.toString();
   }
}
