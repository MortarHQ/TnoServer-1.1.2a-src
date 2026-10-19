package it.hurts.octostudios.octolib.module.config.cfgbuilder;

import it.hurts.octostudios.octolib.module.config.cfgbuilder.scalar.BinaryEntry;
import it.hurts.octostudios.octolib.module.config.cfgbuilder.scalar.BoolEntry;
import it.hurts.octostudios.octolib.module.config.cfgbuilder.scalar.DoubleEntry;
import it.hurts.octostudios.octolib.module.config.cfgbuilder.scalar.IntEntry;
import it.hurts.octostudios.octolib.module.config.cfgbuilder.scalar.ScalarEntry;
import it.hurts.octostudios.octolib.module.config.cfgbuilder.scalar.StringEntry;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class CompoundEntry extends ConfigEntry implements Map<String, ConfigEntry> {
   public static final CfgTag COMPOUND_CFG_TAG = new CfgTag(CompoundEntry.class);
   public static final Map<CfgTag, CompoundEntry.ScalarFactory> SCALAR_FACTORIES = new HashMap<CfgTag, CompoundEntry.ScalarFactory>() {
      {
         this.put(CfgTag.INT, o -> new IntEntry((Integer)o));
         this.put(CfgTag.STR, o -> new StringEntry((String)o));
         this.put(CfgTag.FLOAT, o -> new DoubleEntry((Double)o));
         this.put(CfgTag.BOOL, o -> new BoolEntry((Boolean)o));
         this.put(CfgTag.BINARY, o -> new BinaryEntry((Byte)o));
         this.put(CfgTag.NULL, o -> new NullEntry());
      }
   };
   protected LinkedHashMap<String, ConfigEntry> map = new LinkedHashMap<>();

   public CompoundEntry() {
      super(COMPOUND_CFG_TAG, EntryId.MAPPING);
   }

   public CompoundEntry(Map<String, ConfigEntry> map) {
      super(COMPOUND_CFG_TAG, EntryId.MAPPING);
      this.map.putAll(map);
   }

   protected CompoundEntry(CfgTag tag) {
      super(tag, EntryId.MAPPING);
   }

   @Override
   public Object getData() {
      return this.map;
   }

   @Override
   public int size() {
      return this.map.size();
   }

   @Override
   public boolean isEmpty() {
      return this.map.isEmpty();
   }

   @Override
   public boolean containsKey(Object key) {
      return false;
   }

   @Override
   public boolean containsValue(Object value) {
      return false;
   }

   public ConfigEntry get(Object key) {
      return this.map.get(key);
   }

   public ConfigEntry put(String key, ConfigEntry entry) {
      this.map.put(key, entry);
      return entry;
   }

   public ConfigEntry remove(Object key) {
      return this.map.remove(key);
   }

   @Override
   public void putAll(@NotNull Map<? extends String, ? extends ConfigEntry> m) {
      this.map.putAll(m);
   }

   @Override
   public void clear() {
      this.map.clear();
   }

   @NotNull
   @Override
   public Set<String> keySet() {
      return this.map.keySet();
   }

   @NotNull
   @Override
   public Collection<ConfigEntry> values() {
      return this.map.values();
   }

   @NotNull
   @Override
   public Set<Entry<String, ConfigEntry>> entrySet() {
      return this.map.entrySet();
   }

   public BinaryEntry putByte(String key, byte value) {
      BinaryEntry entry = new BinaryEntry(value);
      this.map.put(key, new BinaryEntry(value));
      return entry;
   }

   public StringEntry putString(String key, String value) {
      StringEntry entry = new StringEntry(value);
      this.map.put(key, new StringEntry(value));
      return entry;
   }

   public BoolEntry putBoolean(String key, boolean value) {
      BoolEntry entry = new BoolEntry(value);
      this.map.put(key, entry);
      return entry;
   }

   public ObjectEntry putObject(String key, Object value) {
      ObjectEntry entry = new ObjectEntry(value);
      this.map.put(key, entry);
      return entry;
   }

   public ArrayEntry putObjectList(String key, Object... list) {
      return this.putObjectList(key, List.of(list));
   }

   public ArrayEntry putObjectList(String key, List<Object> list) {
      ArrayEntry entry = new ArrayEntry(list.stream().map(ObjectEntry::new).toList());
      this.map.put(key, entry);
      return entry;
   }

   public ArrayEntry putScalarList(String key, CfgTag scalarTag, Object... list) {
      return this.putScalarList(key, scalarTag, List.of(list));
   }

   public ArrayEntry putScalarList(String key, CfgTag scalarTag, List<Object> list) {
      ArrayEntry entry = new ArrayEntry(scalarTag);
      if (!SCALAR_FACTORIES.containsKey(scalarTag)) {
         throw new IllegalArgumentException("Unsupported scalar tag: " + scalarTag);
      }

      CompoundEntry.ScalarFactory factory = SCALAR_FACTORIES.get(scalarTag);

      for (Object element : list) {
         try {
            entry.add(factory.create(element));
         } catch (ClassCastException exception) {
            throw new IllegalArgumentException("Element cannot be cast to " + scalarTag);
         }
      }

      this.map.put(key, entry);
      return entry;
   }

   public ArrayEntry putList(String key, ArrayEntry list) {
      this.map.put(key, list);
      return list;
   }

   public ArrayEntry putList(String key, List<ConfigEntry> list) {
      ArrayEntry entry = new ArrayEntry(list);
      this.map.put(key, new ArrayEntry(list));
      return entry;
   }

   public IntEntry putInt(String key, int value) {
      IntEntry entry = new IntEntry(value);
      this.map.put(key, entry);
      return entry;
   }

   public NullEntry putNull(String key) {
      NullEntry entry = new NullEntry();
      this.map.put(key, entry);
      return entry;
   }

   public DoubleEntry putDouble(String key, double value) {
      DoubleEntry entry = new DoubleEntry(value);
      this.map.put(key, entry);
      return entry;
   }

   public byte getByte(String key) {
      ConfigEntry entry = this.map.get(key);
      this.checkType(entry, CfgTag.BINARY);
      return (Byte)entry.getData();
   }

   public ArrayEntry getList(String key) {
      ConfigEntry entry = this.map.get(key);
      this.checkType(entry, CfgTag.SEQ);
      return (ArrayEntry)entry;
   }

   public boolean getBoolean(String key) {
      ConfigEntry entry = this.map.get(key);
      this.checkType(entry, CfgTag.BOOL);
      return (Boolean)entry.getData();
   }

   public <T> T getObject(String key, Class<T> type) {
      ConfigEntry entry = this.map.get(key);
      this.checkType(entry, new CfgTag(type));
      return (T)entry.getData();
   }

   public String getString(String key) {
      ConfigEntry entry = this.map.get(key);
      this.checkType(entry, CfgTag.STR);
      return (String)entry.getData();
   }

   public double getDouble(String key) {
      ConfigEntry entry = this.map.get(key);
      this.checkType(entry, CfgTag.FLOAT);
      return (Double)entry.getData();
   }

   public int getInt(String key) {
      ConfigEntry entry = this.map.get(key);
      this.checkType(entry, CfgTag.INT);
      return (Integer)entry.getData();
   }

   private void checkType(ConfigEntry entry, CfgTag tag) {
      if (entry != null && entry.getType() != tag && !entry.getTag().equals(tag)) {
         throw new IllegalArgumentException(String.format("Incorrect tag type: %s is expected.", tag));
      }
   }

   public CompoundEntry getWithoutTypes() {
      CompoundEntry compoundEntry = new CompoundEntry(this.map);
      compoundEntry.map.replaceAll((key, value) -> {
         ConfigEntry entry = switch (value.getNodeId()) {
            case SCALAR, ANCHOR -> value;
            case OBJECT -> new ObjectEntry(COMPOUND_CFG_TAG, value.getData());
            case SEQUENCE -> new ArrayEntry((Collection<? extends ConfigEntry>)value.getData());
            case MAPPING -> ((CompoundEntry)value).getWithoutTypes();
         };
         entry.setInlineComment(value.getInlineComment());
         entry.setBlockComment(value.getBlockComment());
         return entry;
      });
      return compoundEntry;
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder(
         this.getClass().getSimpleName().isEmpty() ? this.getClass().getSuperclass().getSimpleName() : this.getClass().getSimpleName()
      );
      if (this.tag != CfgTag.MAP) {
         builder.append("!!").append(this.tag);
      } else {
         builder.append(" ");
      }

      builder.append("{\n");
      this.map.forEach((s, entry) -> builder.append(Arrays.stream((s + ": " + entry).split("\n")).map(ss -> "\t" + ss + "\n").collect(Collectors.joining())));
      builder.append("}");
      return builder.toString();
   }

   public boolean containsKey(String key) {
      return this.map.containsKey(key);
   }

   public interface ScalarFactory {
      ScalarEntry create(Object var1);
   }
}
