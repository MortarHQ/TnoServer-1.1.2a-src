package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.converter.tag.TagToDataConverter;
import com.blamejared.crafttweaker.api.data.visitor.DataToStringVisitor;
import com.blamejared.crafttweaker.api.data.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.nbt.CollectionTag;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.openzen.zencode.java.ZenCodeType;
import org.openzen.zenscript.codemodel.OperatorType;
import org.openzen.zenscript.codemodel.type.BasicTypeID;

@ZenRegister(loaders = {"crafttweaker", "tags"})
@ZenCodeType.Name("crafttweaker.api.data.IData")
@Document("vanilla/api/data/IData")
public interface IData extends Comparable<IData>, Iterable<IData> {
   @ZenCodeType.Method
   static IData listOf(IData... members) {
      if (members == null) {
         return new ListData();
      }

      int type = 0;
      int byteIndex = 1;
      int intIndex = 2;
      int longIndex = 4;
      int otherIndex = 8;

      for (IData member : members) {
         if (member instanceof ByteData) {
            type |= 1;
         } else if (member instanceof IntData || member instanceof ShortData) {
            type |= 2;
         } else if (member instanceof LongData) {
            type |= 4;
         } else {
            type |= 8;
         }
      }

      if ((type & 8) != 0) {
         return new ListData(members);
      }

      if ((type & 4) != 0) {
         long[] result = new long[members.length];

         for (int i = 0; i < members.length; i++) {
            result[i] = members[i].asLong();
         }

         return new LongArrayData(result);
      } else if ((type & 2) != 0) {
         int[] result = new int[members.length];

         for (int i = 0; i < members.length; i++) {
            result[i] = members[i].asInt();
         }

         return new IntArrayData(result);
      } else {
         if ((type & 1) == 0) {
            return new ListData();
         }

         byte[] result = new byte[members.length];

         for (int i = 0; i < members.length; i++) {
            result[i] = members[i].asByte();
         }

         return new ByteArrayData(result);
      }
   }

   @ZenCodeType.Operator(ZenCodeType.OperatorType.ADD)
   @ZenCodeType.Method
   default IData add(IData other) {
      return this.notSupportedOperator(OperatorType.ADD);
   }

   @ZenCodeType.Operator(ZenCodeType.OperatorType.SUB)
   default IData sub(IData other) {
      return this.notSupportedOperator(OperatorType.SUB);
   }

   @ZenCodeType.Operator(ZenCodeType.OperatorType.MUL)
   default IData mul(IData other) {
      return this.notSupportedOperator(OperatorType.MUL);
   }

   @ZenCodeType.Operator(ZenCodeType.OperatorType.DIV)
   default IData div(IData other) {
      return this.notSupportedOperator(OperatorType.DIV);
   }

   @ZenCodeType.Operator(ZenCodeType.OperatorType.MOD)
   default IData mod(IData other) {
      return this.notSupportedOperator(OperatorType.MOD);
   }

   @ZenCodeType.Operator(ZenCodeType.OperatorType.CAT)
   default IData cat(IData other) {
      return this.notSupportedOperator(OperatorType.CAT);
   }

   @ZenCodeType.Operator(ZenCodeType.OperatorType.OR)
   default IData or(IData other) {
      return this.notSupportedOperator(OperatorType.OR);
   }

   @ZenCodeType.Operator(ZenCodeType.OperatorType.AND)
   default IData and(IData other) {
      return this.notSupportedOperator(OperatorType.AND);
   }

   @ZenCodeType.Operator(ZenCodeType.OperatorType.XOR)
   default IData xor(IData other) {
      return this.notSupportedOperator(OperatorType.XOR);
   }

   @ZenCodeType.Operator(ZenCodeType.OperatorType.NEG)
   default IData neg() {
      return this.notSupportedOperator(OperatorType.NEG);
   }

   @ZenCodeType.Operator(ZenCodeType.OperatorType.NOT)
   default IData not() {
      return this.notSupportedOperator(OperatorType.NOT);
   }

   @ZenCodeType.Method
   @ZenCodeType.Operator(ZenCodeType.OperatorType.INDEXSET)
   default void put(String index, @ZenCodeType.Nullable IData value) {
      this.notSupportedOperator(OperatorType.INDEXSET);
   }

   @ZenCodeType.Operator(ZenCodeType.OperatorType.INDEXGET)
   default IData getAt(int index) {
      return this.notSupportedOperator(OperatorType.INDEXGET);
   }

   @ZenCodeType.Operator(ZenCodeType.OperatorType.INDEXGET)
   default IData getAt(String key) {
      return this.notSupportedOperator(OperatorType.INDEXGET);
   }

   @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
   default boolean contains(IData other) {
      return this.<Boolean>notSupportedOperator(OperatorType.CONTAINS);
   }

   @ZenCodeType.Method
   @ZenCodeType.Operator(ZenCodeType.OperatorType.COMPARE)
   default int compareTo(@NotNull IData other) {
      return this.<Integer>notSupportedOperator(OperatorType.COMPARE);
   }

   @ZenCodeType.Method
   default void remove(int index) {
      this.doesNot("support removal by index");
   }

   @ZenCodeType.Method
   default void remove(String key) {
      this.doesNot("support removal by key");
   }

   @ZenCodeType.Method
   default void setAt(String name, @ZenCodeType.Nullable IData data) {
      this.put(name, data);
   }

   @ZenCodeType.Operator(ZenCodeType.OperatorType.EQUALS)
   default boolean equalTo(IData other) {
      return this.<Boolean>notSupportedOperator(OperatorType.EQUALS);
   }

   @ZenCodeType.Operator(ZenCodeType.OperatorType.SHL)
   default IData shl(IData other) {
      return this.notSupportedOperator(OperatorType.SHL);
   }

   @ZenCodeType.Operator(ZenCodeType.OperatorType.SHR)
   default IData shr(IData other) {
      return this.notSupportedOperator(OperatorType.SHR);
   }

   @ZenCodeType.Caster
   @ZenCodeType.Method
   default boolean asBool() {
      return this.<Boolean>notSupportedCast(BasicTypeID.BOOL);
   }

   @ZenCodeType.Caster
   @ZenCodeType.Method
   default @ZenCodeType.Unsigned byte asByte() {
      return this.<Byte>notSupportedCast(BasicTypeID.BYTE);
   }

   @ZenCodeType.Caster
   @ZenCodeType.Method
   default short asShort() {
      return this.<Short>notSupportedCast(BasicTypeID.SHORT);
   }

   @ZenCodeType.Caster
   @ZenCodeType.Method
   default int asInt() {
      return this.<Integer>notSupportedCast(BasicTypeID.INT);
   }

   @ZenCodeType.Caster
   @ZenCodeType.Method
   default long asLong() {
      return this.<Long>notSupportedCast(BasicTypeID.LONG);
   }

   @ZenCodeType.Caster
   @ZenCodeType.Method
   default float asFloat() {
      return this.<Float>notSupportedCast(BasicTypeID.FLOAT);
   }

   @ZenCodeType.Caster
   @ZenCodeType.Method
   default double asDouble() {
      return this.<Double>notSupportedCast(BasicTypeID.DOUBLE);
   }

   @ZenCodeType.Method
   default String asString() {
      return this.accept(DataToStringVisitor.ESCAPE);
   }

   @ZenCodeType.Caster
   @ZenCodeType.Method
   default String getAsString() {
      return this.accept(DataToStringVisitor.PLAIN);
   }

   @ZenCodeType.Caster
   @ZenCodeType.Method
   default List<IData> asList() {
      return this.notSupportedCast("IData[]");
   }

   @ZenCodeType.Caster
   @ZenCodeType.Method
   default Map<String, IData> asMap() {
      return this.notSupportedCast("IData[string]");
   }

   @ZenCodeType.Caster
   @ZenCodeType.Method
   default byte[] asByteArray() {
      return this.notSupportedCast("byte[]");
   }

   @ZenCodeType.Caster
   @ZenCodeType.Method
   default int[] asIntArray() {
      return this.notSupportedCast("int[]");
   }

   @ZenCodeType.Caster
   @ZenCodeType.Method
   default long[] asLongArray() {
      return this.notSupportedCast("long[]");
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("length")
   default int length() {
      return this.<Integer>doesNot("have a length");
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("keys")
   default Set<String> getKeys() {
      return this.doesNot("is not indexable by keys");
   }

   @NotNull
   @Override
   default Iterator<IData> iterator() {
      return this.doesNot("support iteration");
   }

   @ZenCodeType.Method
   default IData merge(IData other) {
      return this.doesNot("support merging");
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("isEmpty")
   default boolean isEmpty() {
      return this.length() == 0;
   }

   @ZenCodeType.Method
   default byte getId() {
      return this.getInternal().getId();
   }

   Tag getInternal();

   @ZenCodeType.Method
   IData copy();

   IData copyInternal();

   <T> T accept(DataVisitor<T> var1);

   @ZenCodeType.Method
   default IData map(Function<IData, IData> operation) {
      return operation.apply(this);
   }

   IData.Type getType();

   private <T> T doesNot(String message) {
      throw new UnsupportedOperationException("Data type: '%s' does not %s!".formatted(this.getType(), message));
   }

   private <T> T notSupportedOperator(OperatorType operator) {
      return this.doesNot("support the '%s' ('%s') operator".formatted(operator.compiledName, operator.operator));
   }

   private <T> T notSupportedCast(String toType) {
      return this.doesNot("support being cast to '%s'".formatted(toType));
   }

   private <T> T notSupportedCast(BasicTypeID toType) {
      return this.notSupportedCast(toType.name);
   }

   @Internal
   default boolean isListable() {
      return false;
   }

   @Internal
   default boolean isMappable() {
      return false;
   }

   default boolean containsList(List<IData> dataValues) {
      if (!(this.getInternal() instanceof CollectionTag<?> internal)) {
         return false;
      } else {
         label25:
         for (IData dataValue : dataValues) {
            for (Tag value : internal) {
               if (TagToDataConverter.convert(value).contains(dataValue)) {
                  continue label25;
               }
            }

            return false;
         }

         return true;
      }
   }

   enum Type {
      BOOL,
      BYTE_ARRAY,
      BYTE,
      DOUBLE,
      FLOAT,
      INT_ARRAY,
      INT,
      LIST,
      LONG_ARRAY,
      LONG,
      MAP,
      SHORT,
      STRING,
      EMPTY;
   }
}
