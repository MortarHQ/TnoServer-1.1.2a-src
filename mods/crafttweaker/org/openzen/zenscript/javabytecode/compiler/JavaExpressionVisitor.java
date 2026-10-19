package org.openzen.zenscript.javabytecode.compiler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Type;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.CompareType;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.annotations.NativeTag;
import org.openzen.zenscript.codemodel.definition.ExpansionDefinition;
import org.openzen.zenscript.codemodel.expression.AndAndExpression;
import org.openzen.zenscript.codemodel.expression.ArrayExpression;
import org.openzen.zenscript.codemodel.expression.CallArguments;
import org.openzen.zenscript.codemodel.expression.CallExpression;
import org.openzen.zenscript.codemodel.expression.CallStaticExpression;
import org.openzen.zenscript.codemodel.expression.CapturedClosureExpression;
import org.openzen.zenscript.codemodel.expression.CapturedDirectExpression;
import org.openzen.zenscript.codemodel.expression.CapturedExpression;
import org.openzen.zenscript.codemodel.expression.CapturedLocalVariableExpression;
import org.openzen.zenscript.codemodel.expression.CapturedParameterExpression;
import org.openzen.zenscript.codemodel.expression.CapturedThisExpression;
import org.openzen.zenscript.codemodel.expression.CastExpression;
import org.openzen.zenscript.codemodel.expression.CheckNullExpression;
import org.openzen.zenscript.codemodel.expression.CoalesceExpression;
import org.openzen.zenscript.codemodel.expression.CompareExpression;
import org.openzen.zenscript.codemodel.expression.ConditionalExpression;
import org.openzen.zenscript.codemodel.expression.ConstExpression;
import org.openzen.zenscript.codemodel.expression.ConstantBoolExpression;
import org.openzen.zenscript.codemodel.expression.ConstantByteExpression;
import org.openzen.zenscript.codemodel.expression.ConstantCharExpression;
import org.openzen.zenscript.codemodel.expression.ConstantDoubleExpression;
import org.openzen.zenscript.codemodel.expression.ConstantFloatExpression;
import org.openzen.zenscript.codemodel.expression.ConstantIntExpression;
import org.openzen.zenscript.codemodel.expression.ConstantLongExpression;
import org.openzen.zenscript.codemodel.expression.ConstantSByteExpression;
import org.openzen.zenscript.codemodel.expression.ConstantShortExpression;
import org.openzen.zenscript.codemodel.expression.ConstantStringExpression;
import org.openzen.zenscript.codemodel.expression.ConstantUIntExpression;
import org.openzen.zenscript.codemodel.expression.ConstantULongExpression;
import org.openzen.zenscript.codemodel.expression.ConstantUShortExpression;
import org.openzen.zenscript.codemodel.expression.ConstantUSizeExpression;
import org.openzen.zenscript.codemodel.expression.ConstructorSuperCallExpression;
import org.openzen.zenscript.codemodel.expression.ConstructorThisCallExpression;
import org.openzen.zenscript.codemodel.expression.EnumConstantExpression;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.expression.ExpressionVisitor;
import org.openzen.zenscript.codemodel.expression.FunctionExpression;
import org.openzen.zenscript.codemodel.expression.GetFieldExpression;
import org.openzen.zenscript.codemodel.expression.GetFunctionParameterExpression;
import org.openzen.zenscript.codemodel.expression.GetLocalVariableExpression;
import org.openzen.zenscript.codemodel.expression.GetMatchingVariantField;
import org.openzen.zenscript.codemodel.expression.GetStaticFieldExpression;
import org.openzen.zenscript.codemodel.expression.GetterExpression;
import org.openzen.zenscript.codemodel.expression.GlobalCallExpression;
import org.openzen.zenscript.codemodel.expression.GlobalExpression;
import org.openzen.zenscript.codemodel.expression.InterfaceCastExpression;
import org.openzen.zenscript.codemodel.expression.IsExpression;
import org.openzen.zenscript.codemodel.expression.LambdaClosure;
import org.openzen.zenscript.codemodel.expression.MakeConstExpression;
import org.openzen.zenscript.codemodel.expression.MapExpression;
import org.openzen.zenscript.codemodel.expression.MatchExpression;
import org.openzen.zenscript.codemodel.expression.NewExpression;
import org.openzen.zenscript.codemodel.expression.NullExpression;
import org.openzen.zenscript.codemodel.expression.OrOrExpression;
import org.openzen.zenscript.codemodel.expression.PanicExpression;
import org.openzen.zenscript.codemodel.expression.PostCallExpression;
import org.openzen.zenscript.codemodel.expression.RangeExpression;
import org.openzen.zenscript.codemodel.expression.SameObjectExpression;
import org.openzen.zenscript.codemodel.expression.SetFieldExpression;
import org.openzen.zenscript.codemodel.expression.SetFunctionParameterExpression;
import org.openzen.zenscript.codemodel.expression.SetLocalVariableExpression;
import org.openzen.zenscript.codemodel.expression.SetStaticFieldExpression;
import org.openzen.zenscript.codemodel.expression.SetterExpression;
import org.openzen.zenscript.codemodel.expression.StaticGetterExpression;
import org.openzen.zenscript.codemodel.expression.StaticSetterExpression;
import org.openzen.zenscript.codemodel.expression.SubtypeCastExpression;
import org.openzen.zenscript.codemodel.expression.SupertypeCastExpression;
import org.openzen.zenscript.codemodel.expression.ThisExpression;
import org.openzen.zenscript.codemodel.expression.ThrowExpression;
import org.openzen.zenscript.codemodel.expression.TryConvertExpression;
import org.openzen.zenscript.codemodel.expression.TryRethrowAsExceptionExpression;
import org.openzen.zenscript.codemodel.expression.TryRethrowAsResultExpression;
import org.openzen.zenscript.codemodel.expression.VariantValueExpression;
import org.openzen.zenscript.codemodel.expression.WrapOptionalExpression;
import org.openzen.zenscript.codemodel.expression.switchvalue.VariantOptionSwitchValue;
import org.openzen.zenscript.codemodel.generic.TypeParameter;
import org.openzen.zenscript.codemodel.member.ref.DefinitionMemberRef;
import org.openzen.zenscript.codemodel.member.ref.FieldMemberRef;
import org.openzen.zenscript.codemodel.member.ref.FunctionalMemberRef;
import org.openzen.zenscript.codemodel.statement.ReturnStatement;
import org.openzen.zenscript.codemodel.type.ArrayTypeID;
import org.openzen.zenscript.codemodel.type.AssocTypeID;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.DefinitionTypeID;
import org.openzen.zenscript.codemodel.type.FunctionTypeID;
import org.openzen.zenscript.codemodel.type.OptionalTypeID;
import org.openzen.zenscript.codemodel.type.RangeTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.member.BuiltinID;
import org.openzen.zenscript.javabytecode.JavaBytecodeContext;
import org.openzen.zenscript.javabytecode.JavaLocalVariableInfo;
import org.openzen.zenscript.javashared.JavaClass;
import org.openzen.zenscript.javashared.JavaCompiledModule;
import org.openzen.zenscript.javashared.JavaField;
import org.openzen.zenscript.javashared.JavaMethod;
import org.openzen.zenscript.javashared.JavaNativeTranslator;
import org.openzen.zenscript.javashared.JavaParameterInfo;
import org.openzen.zenscript.javashared.JavaSynthesizedFunctionInstance;
import org.openzen.zenscript.javashared.JavaVariantOption;
import org.openzen.zenscript.javashared.expressions.JavaFunctionInterfaceCastExpression;
import org.openzen.zenscript.javashared.types.JavaFunctionalInterfaceTypeID;

public class JavaExpressionVisitor implements ExpressionVisitor<Void>, JavaNativeTranslator<Void> {
   public static final JavaMethod OBJECT_HASHCODE = JavaMethod.getNativeVirtual(JavaClass.OBJECT, "hashCode", "()I");
   public static final JavaMethod OBJECT_EQUALS = JavaMethod.getNativeVirtual(JavaClass.OBJECT, "equals", "(Ljava/lang/Object)Z");
   public static final JavaMethod OBJECT_CLONE = JavaMethod.getNativeVirtual(JavaClass.OBJECT, "clone", "()Ljava/lang/Object;");
   private static final JavaMethod OBJECTS_TOSTRING = JavaMethod.getNativeStatic(
      new JavaClass("java.util", "Objects", JavaClass.Kind.CLASS), "toString", "(Ljava/lang/Object;)Ljava/lang/String;"
   );
   private static final JavaMethod BOOLEAN_PARSE = JavaMethod.getNativeStatic(JavaClass.BOOLEAN, "parseBoolean", "(Ljava/lang/String;)Z");
   private static final JavaMethod BOOLEAN_TO_STRING = JavaMethod.getNativeStatic(JavaClass.BOOLEAN, "toString", "(Z)Ljava/lang/String;");
   private static final JavaMethod BYTE_PARSE = JavaMethod.getNativeStatic(JavaClass.BYTE, "parseByte", "(Ljava/lang/String;)B");
   private static final JavaMethod BYTE_PARSE_WITH_BASE = JavaMethod.getNativeStatic(JavaClass.BYTE, "parseByte", "(Ljava/lang/String;I)B");
   private static final JavaField BYTE_MIN_VALUE = new JavaField(JavaClass.BYTE, "MIN_VALUE", "B");
   private static final JavaField BYTE_MAX_VALUE = new JavaField(JavaClass.BYTE, "MAX_VALUE", "B");
   private static final JavaMethod BYTE_TO_STRING = JavaMethod.getNativeStatic(JavaClass.BYTE, "toString", "(B)Ljava/lang/String;");
   private static final JavaMethod SHORT_PARSE = JavaMethod.getNativeStatic(JavaClass.SHORT, "parseShort", "(Ljava/lang/String;)S");
   private static final JavaMethod SHORT_PARSE_WITH_BASE = JavaMethod.getNativeStatic(JavaClass.SHORT, "parseShort", "(Ljava/lang/String;I)S");
   private static final JavaField SHORT_MIN_VALUE = new JavaField(JavaClass.SHORT, "MIN_VALUE", "S");
   private static final JavaField SHORT_MAX_VALUE = new JavaField(JavaClass.SHORT, "MAX_VALUE", "S");
   private static final JavaMethod SHORT_TO_STRING = JavaMethod.getNativeStatic(JavaClass.SHORT, "toString", "(S)Ljava/lang/String;");
   private static final JavaMethod INTEGER_COMPARE_UNSIGNED = JavaMethod.getNativeStatic(JavaClass.INTEGER, "compareUnsigned", "(II)I");
   private static final JavaMethod INTEGER_DIVIDE_UNSIGNED = JavaMethod.getNativeStatic(JavaClass.INTEGER, "divideUnsigned", "(II)I");
   private static final JavaMethod INTEGER_REMAINDER_UNSIGNED = JavaMethod.getNativeStatic(JavaClass.INTEGER, "remainderUnsigned", "(II)I");
   private static final JavaMethod INTEGER_NUMBER_OF_TRAILING_ZEROS = JavaMethod.getNativeStatic(JavaClass.INTEGER, "numberOfTrailingZeros", "(I)I");
   private static final JavaMethod INTEGER_NUMBER_OF_LEADING_ZEROS = JavaMethod.getNativeStatic(JavaClass.INTEGER, "numberOfLeadingZeros", "(I)I");
   private static final JavaMethod INTEGER_PARSE = JavaMethod.getNativeStatic(JavaClass.INTEGER, "parseInt", "(Ljava/lang/String;)I");
   private static final JavaMethod INTEGER_PARSE_WITH_BASE = JavaMethod.getNativeStatic(JavaClass.INTEGER, "parseInt", "(Ljava/lang/String;I)I");
   private static final JavaMethod INTEGER_PARSE_UNSIGNED = JavaMethod.getNativeStatic(JavaClass.INTEGER, "parseUnsignedInt", "(Ljava/lang/String;)I");
   private static final JavaMethod INTEGER_PARSE_UNSIGNED_WITH_BASE = JavaMethod.getNativeStatic(
      JavaClass.INTEGER, "parseUnsignedInt", "(Ljava/lang/String;I)I"
   );
   private static final JavaMethod INTEGER_HIGHEST_ONE_BIT = JavaMethod.getNativeStatic(JavaClass.INTEGER, "highestOneBit", "(I)I");
   private static final JavaMethod INTEGER_LOWEST_ONE_BIT = JavaMethod.getNativeStatic(JavaClass.INTEGER, "lowestOneBit", "(I)I");
   private static final JavaMethod INTEGER_BIT_COUNT = JavaMethod.getNativeStatic(JavaClass.INTEGER, "bitCount", "(I)I");
   private static final JavaField INTEGER_MIN_VALUE = new JavaField(JavaClass.INTEGER, "MIN_VALUE", "I");
   private static final JavaField INTEGER_MAX_VALUE = new JavaField(JavaClass.INTEGER, "MAX_VALUE", "I");
   private static final JavaMethod INTEGER_TO_STRING = JavaMethod.getNativeStatic(JavaClass.INTEGER, "toString", "(I)Ljava/lang/String;");
   private static final JavaMethod INTEGER_TO_UNSIGNED_STRING = JavaMethod.getNativeStatic(JavaClass.INTEGER, "toUnsignedString", "(I)Ljava/lang/String;");
   private static final JavaMethod LONG_COMPARE = JavaMethod.getNativeStatic(JavaClass.LONG, "compare", "(JJ)I");
   private static final JavaMethod LONG_COMPARE_UNSIGNED = JavaMethod.getNativeStatic(JavaClass.LONG, "compareUnsigned", "(JJ)I");
   private static final JavaMethod LONG_DIVIDE_UNSIGNED = JavaMethod.getNativeStatic(JavaClass.LONG, "divideUnsigned", "(JJ)J");
   private static final JavaMethod LONG_REMAINDER_UNSIGNED = JavaMethod.getNativeStatic(JavaClass.LONG, "remainderUnsigned", "(JJ)J");
   private static final JavaMethod LONG_NUMBER_OF_TRAILING_ZEROS = JavaMethod.getNativeStatic(JavaClass.LONG, "numberOfTrailingZeros", "(J)I");
   private static final JavaMethod LONG_NUMBER_OF_LEADING_ZEROS = JavaMethod.getNativeStatic(JavaClass.LONG, "numberOfLeadingZeros", "(J)I");
   private static final JavaMethod LONG_PARSE = JavaMethod.getNativeStatic(JavaClass.LONG, "parseLong", "(Ljava/lang/String;)J");
   private static final JavaMethod LONG_PARSE_WITH_BASE = JavaMethod.getNativeStatic(JavaClass.LONG, "parseLong", "(Ljava/lang/String;I)J");
   private static final JavaMethod LONG_PARSE_UNSIGNED = JavaMethod.getNativeStatic(JavaClass.LONG, "parseUnsignedLong", "(Ljava/lang/String;)J");
   private static final JavaMethod LONG_PARSE_UNSIGNED_WITH_BASE = JavaMethod.getNativeStatic(JavaClass.LONG, "parseUnsignedLong", "(Ljava/lang/String;I)J");
   private static final JavaMethod LONG_HIGHEST_ONE_BIT = JavaMethod.getNativeStatic(JavaClass.LONG, "highestOneBit", "(J)J");
   private static final JavaMethod LONG_LOWEST_ONE_BIT = JavaMethod.getNativeStatic(JavaClass.LONG, "lowestOneBit", "(J)J");
   private static final JavaMethod LONG_BIT_COUNT = JavaMethod.getNativeStatic(JavaClass.LONG, "bitCount", "(J)I");
   private static final JavaField LONG_MIN_VALUE = new JavaField(JavaClass.LONG, "MIN_VALUE", "J");
   private static final JavaField LONG_MAX_VALUE = new JavaField(JavaClass.LONG, "MAX_VALUE", "J");
   private static final JavaMethod LONG_TO_STRING = JavaMethod.getNativeStatic(JavaClass.LONG, "toString", "(J)Ljava/lang/String;");
   private static final JavaMethod LONG_TO_UNSIGNED_STRING = JavaMethod.getNativeStatic(JavaClass.LONG, "toUnsignedString", "(J)Ljava/lang/String;");
   private static final JavaMethod FLOAT_COMPARE = JavaMethod.getNativeStatic(JavaClass.FLOAT, "compare", "(FF)I");
   private static final JavaMethod FLOAT_PARSE = JavaMethod.getNativeStatic(JavaClass.FLOAT, "parseFloat", "(Ljava/lang/String;)F");
   private static final JavaMethod FLOAT_FROM_BITS = JavaMethod.getNativeStatic(JavaClass.FLOAT, "intBitsToFloat", "(I)F");
   private static final JavaMethod FLOAT_BITS = JavaMethod.getNativeStatic(JavaClass.FLOAT, "floatToRawIntBits", "(F)I");
   private static final JavaField FLOAT_MIN_VALUE = new JavaField(JavaClass.FLOAT, "MIN_VALUE", "F");
   private static final JavaField FLOAT_MAX_VALUE = new JavaField(JavaClass.FLOAT, "MAX_VALUE", "F");
   private static final JavaMethod FLOAT_TO_STRING = JavaMethod.getNativeStatic(JavaClass.FLOAT, "toString", "(F)Ljava/lang/String;");
   private static final JavaMethod DOUBLE_COMPARE = JavaMethod.getNativeStatic(JavaClass.DOUBLE, "compare", "(DD)I");
   private static final JavaMethod DOUBLE_PARSE = JavaMethod.getNativeStatic(JavaClass.DOUBLE, "parseDouble", "(Ljava/lang/String;)D");
   private static final JavaMethod DOUBLE_FROM_BITS = JavaMethod.getNativeStatic(JavaClass.DOUBLE, "longBitsToDouble", "(J)D");
   private static final JavaMethod DOUBLE_BITS = JavaMethod.getNativeStatic(JavaClass.DOUBLE, "doubleToRawLongBits", "(D)J");
   private static final JavaField DOUBLE_MIN_VALUE = new JavaField(JavaClass.DOUBLE, "MIN_VALUE", "D");
   private static final JavaField DOUBLE_MAX_VALUE = new JavaField(JavaClass.DOUBLE, "MAX_VALUE", "D");
   private static final JavaMethod DOUBLE_TO_STRING = JavaMethod.getNativeStatic(JavaClass.DOUBLE, "toString", "(D)Ljava/lang/String;");
   private static final JavaMethod CHARACTER_TO_LOWER_CASE = JavaMethod.getNativeVirtual(JavaClass.CHARACTER, "toLowerCase", "()C");
   private static final JavaMethod CHARACTER_TO_UPPER_CASE = JavaMethod.getNativeVirtual(JavaClass.CHARACTER, "toUpperCase", "()C");
   private static final JavaField CHARACTER_MIN_VALUE = new JavaField(JavaClass.CHARACTER, "MIN_VALUE", "C");
   private static final JavaField CHARACTER_MAX_VALUE = new JavaField(JavaClass.CHARACTER, "MAX_VALUE", "C");
   private static final JavaMethod CHARACTER_TO_STRING = JavaMethod.getNativeStatic(JavaClass.CHARACTER, "toString", "(C)Ljava/lang/String;");
   private static final JavaMethod STRING_INIT_CHARACTERS = JavaMethod.getNativeConstructor(JavaClass.STRING, "([C)V");
   private static final JavaMethod STRING_INIT_BYTES_CHARSET = JavaMethod.getNativeConstructor(JavaClass.STRING, "([BLjava/nio/charset/Charset;)V");
   private static final JavaMethod STRING_COMPARETO = JavaMethod.getNativeVirtual(JavaClass.STRING, "compareTo", "(Ljava/lang/String;)I");
   private static final JavaMethod STRING_CONCAT = JavaMethod.getNativeVirtual(JavaClass.STRING, "concat", "(Ljava/lang/String;)Ljava/lang/String;");
   private static final JavaMethod STRING_CHAR_AT = JavaMethod.getNativeVirtual(JavaClass.STRING, "charAt", "(I)C");
   private static final JavaMethod STRING_SUBSTRING = JavaMethod.getNativeVirtual(JavaClass.STRING, "substring", "(II)Ljava/lang/String;");
   private static final JavaMethod STRING_TRIM = JavaMethod.getNativeVirtual(JavaClass.STRING, "trim", "()Ljava/lang/String;");
   private static final JavaMethod STRING_TO_LOWER_CASE = JavaMethod.getNativeVirtual(JavaClass.STRING, "toLowerCase", "()Ljava/lang/String;");
   private static final JavaMethod STRING_TO_UPPER_CASE = JavaMethod.getNativeVirtual(JavaClass.STRING, "toUpperCase", "()Ljava/lang/String;");
   private static final JavaMethod STRING_LENGTH = JavaMethod.getNativeVirtual(JavaClass.STRING, "length", "()I");
   private static final JavaMethod STRING_CHARACTERS = JavaMethod.getNativeVirtual(JavaClass.STRING, "toCharArray", "()[C");
   private static final JavaMethod STRING_ISEMPTY = JavaMethod.getNativeVirtual(JavaClass.STRING, "isEmpty", "()Z");
   private static final JavaMethod STRING_GET_BYTES = JavaMethod.getNativeVirtual(JavaClass.STRING, "getBytes", "(Ljava/nio/charset/Charset;)[B");
   private static final JavaMethod ENUM_COMPARETO = JavaMethod.getNativeVirtual(JavaClass.ENUM, "compareTo", "(Ljava/lang/Enum;)I");
   private static final JavaMethod ENUM_NAME = JavaMethod.getNativeVirtual(JavaClass.ENUM, "name", "()Ljava/lang/String;");
   public static final JavaMethod ENUM_ORDINAL = JavaMethod.getNativeVirtual(JavaClass.ENUM, "ordinal", "()I");
   private static final JavaMethod HASHMAP_INIT = JavaMethod.getNativeConstructor(JavaClass.HASHMAP, "()V");
   private static final JavaMethod MAP_GET = JavaMethod.getInterface(JavaClass.MAP, "get", "(Ljava/lang/Object;)Ljava/lang/Object;", true);
   private static final JavaMethod MAP_PUT = JavaMethod.getInterface(JavaClass.MAP, "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", true);
   private static final JavaMethod MAP_PUT_ALL = JavaMethod.getInterface(JavaClass.MAP, "putAll", "(Ljava/util/Map;)V");
   private static final JavaMethod MAP_CONTAINS_KEY = JavaMethod.getInterface(JavaClass.MAP, "containsKey", "(Ljava/lang/Object;)Z");
   private static final JavaMethod MAP_SIZE = JavaMethod.getInterface(JavaClass.MAP, "size", "()I");
   private static final JavaMethod MAP_ISEMPTY = JavaMethod.getInterface(JavaClass.MAP, "isEmpty", "()Z");
   private static final JavaMethod MAP_KEYS = JavaMethod.getInterface(JavaClass.MAP, "keys", "()Ljava/lang/Object;");
   private static final JavaMethod MAP_VALUES = JavaMethod.getInterface(JavaClass.MAP, "values", "()Ljava/lang/Object;");
   private static final JavaMethod ARRAYS_COPY_OF_RANGE_OBJECTS = JavaMethod.getNativeStatic(
      JavaClass.ARRAYS, "copyOfRange", "([Ljava/lang/Object;II)[Ljava/lang/Object;"
   );
   private static final JavaMethod ARRAYS_COPY_OF_RANGE_BOOLS = JavaMethod.getNativeStatic(JavaClass.ARRAYS, "copyOfRange", "([ZII)[Z");
   private static final JavaMethod ARRAYS_COPY_OF_RANGE_BYTES = JavaMethod.getNativeStatic(JavaClass.ARRAYS, "copyOfRange", "([BII)[B");
   private static final JavaMethod ARRAYS_COPY_OF_RANGE_SHORTS = JavaMethod.getNativeStatic(JavaClass.ARRAYS, "copyOfRange", "([SII)[S");
   private static final JavaMethod ARRAYS_COPY_OF_RANGE_INTS = JavaMethod.getNativeStatic(JavaClass.ARRAYS, "copyOfRange", "([III)[I");
   private static final JavaMethod ARRAYS_COPY_OF_RANGE_LONGS = JavaMethod.getNativeStatic(JavaClass.ARRAYS, "copyOfRange", "([JII)[J");
   private static final JavaMethod ARRAYS_COPY_OF_RANGE_FLOATS = JavaMethod.getNativeStatic(JavaClass.ARRAYS, "copyOfRange", "([FII)[F");
   private static final JavaMethod ARRAYS_COPY_OF_RANGE_DOUBLES = JavaMethod.getNativeStatic(JavaClass.ARRAYS, "copyOfRange", "([DII)[D");
   private static final JavaMethod ARRAYS_COPY_OF_RANGE_CHARS = JavaMethod.getNativeStatic(JavaClass.ARRAYS, "copyOfRange", "([CII)[C");
   private static final JavaMethod ARRAYS_EQUALS_OBJECTS = JavaMethod.getNativeStatic(JavaClass.ARRAYS, "equals", "([Ljava/lang/Object[Ljava/lang/Object)Z");
   private static final JavaMethod ARRAYS_EQUALS_BOOLS = JavaMethod.getNativeStatic(JavaClass.ARRAYS, "equals", "([Z[Z)Z");
   private static final JavaMethod ARRAYS_EQUALS_BYTES = JavaMethod.getNativeStatic(JavaClass.ARRAYS, "equals", "([B[B)Z");
   private static final JavaMethod ARRAYS_EQUALS_SHORTS = JavaMethod.getNativeStatic(JavaClass.ARRAYS, "equals", "([S[S)Z");
   private static final JavaMethod ARRAYS_EQUALS_INTS = JavaMethod.getNativeStatic(JavaClass.ARRAYS, "equals", "([I[I)Z");
   private static final JavaMethod ARRAYS_EQUALS_LONGS = JavaMethod.getNativeStatic(JavaClass.ARRAYS, "equals", "([L[L)Z");
   private static final JavaMethod ARRAYS_EQUALS_FLOATS = JavaMethod.getNativeStatic(JavaClass.ARRAYS, "equals", "([F[F)Z");
   private static final JavaMethod ARRAYS_EQUALS_DOUBLES = JavaMethod.getNativeStatic(JavaClass.ARRAYS, "equals", "([D[D)Z");
   private static final JavaMethod ARRAYS_EQUALS_CHARS = JavaMethod.getNativeStatic(JavaClass.ARRAYS, "equals", "([C[C)Z");
   private static final JavaMethod ARRAYS_DEEPHASHCODE = JavaMethod.getNativeStatic(JavaClass.ARRAYS, "deepHashCode", "([Ljava/lang/Object;)");
   private static final JavaMethod ARRAYS_HASHCODE_BOOLS = JavaMethod.getNativeStatic(JavaClass.ARRAYS, "hashCode", "([Z)I");
   private static final JavaMethod ARRAYS_HASHCODE_BYTES = JavaMethod.getNativeStatic(JavaClass.ARRAYS, "hashCode", "([B)I");
   private static final JavaMethod ARRAYS_HASHCODE_SHORTS = JavaMethod.getNativeStatic(JavaClass.ARRAYS, "hashCode", "([S)I");
   private static final JavaMethod ARRAYS_HASHCODE_INTS = JavaMethod.getNativeStatic(JavaClass.ARRAYS, "hashCode", "([I)I");
   private static final JavaMethod ARRAYS_HASHCODE_LONGS = JavaMethod.getNativeStatic(JavaClass.ARRAYS, "hashCode", "([L)I");
   private static final JavaMethod ARRAYS_HASHCODE_FLOATS = JavaMethod.getNativeStatic(JavaClass.ARRAYS, "hashCode", "([F)I");
   private static final JavaMethod ARRAYS_HASHCODE_DOUBLES = JavaMethod.getNativeStatic(JavaClass.ARRAYS, "hashCode", "([D)I");
   private static final JavaMethod ARRAYS_HASHCODE_CHARS = JavaMethod.getNativeStatic(JavaClass.ARRAYS, "hashCode", "([C)I");
   private static final JavaMethod COLLECTION_SIZE = JavaMethod.getNativeVirtual(JavaClass.COLLECTION, "size", "()I");
   private static final JavaMethod COLLECTION_TOARRAY = JavaMethod.getNativeVirtual(JavaClass.COLLECTION, "toArray", "([Ljava/lang/Object;)[Ljava/lang/Object;");
   private static final JavaMethod SHARED_INIT = JavaMethod.getConstructor(JavaClass.SHARED, "(Ljava/lang/Object;)V", 1);
   private static final JavaMethod SHARED_GET = JavaMethod.getNativeVirtual(JavaClass.SHARED, "get", "()Ljava/lang/Object;");
   private static final JavaMethod SHARED_ADDREF = JavaMethod.getNativeVirtual(JavaClass.SHARED, "addRef", "()V");
   private static final JavaMethod SHARED_RELEASE = JavaMethod.getNativeVirtual(JavaClass.SHARED, "release", "()V");
   final JavaWriter javaWriter;
   final JavaBytecodeContext context;
   final JavaCompiledModule module;
   private final JavaBoxingTypeVisitor boxingTypeVisitor;
   private final JavaUnboxingTypeVisitor unboxingTypeVisitor;
   private final JavaCapturedExpressionVisitor capturedExpressionVisitor = new JavaCapturedExpressionVisitor(this);

   public JavaExpressionVisitor(JavaBytecodeContext context, JavaCompiledModule module, JavaWriter javaWriter) {
      this.javaWriter = javaWriter;
      this.context = context;
      this.module = module;
      this.boxingTypeVisitor = new JavaBoxingTypeVisitor(javaWriter);
      this.unboxingTypeVisitor = new JavaUnboxingTypeVisitor(javaWriter);
   }

   private static int calculateMemberPosition(GetLocalVariableExpression localVariableExpression, FunctionExpression expression) {
      int h = 1;

      for (CapturedExpression capture : expression.closure.captures) {
         if (capture instanceof CapturedLocalVariableExpression && ((CapturedLocalVariableExpression)capture).variable == localVariableExpression.variable) {
            return h;
         }

         if (capture instanceof CapturedClosureExpression
            && ((CapturedClosureExpression)capture).value instanceof CapturedLocalVariableExpression
            && ((CapturedLocalVariableExpression)((CapturedClosureExpression)capture).value).variable == localVariableExpression.variable) {
            return h;
         }

         h++;
      }

      throw new RuntimeException(localVariableExpression.position.toString() + ": Captured Statement error");
   }

   private static int calculateMemberPosition(CapturedParameterExpression functionParameterExpression, FunctionExpression expression) {
      int h = 1;

      for (CapturedExpression capture : expression.closure.captures) {
         if (capture instanceof CapturedParameterExpression && ((CapturedParameterExpression)capture).parameter == functionParameterExpression.parameter) {
            return h;
         }

         h++;
      }

      throw new RuntimeException(functionParameterExpression.position.toString() + ": Captured Statement error");
   }

   private static boolean hasNoDefault(MatchExpression switchStatement) {
      for (MatchExpression.Case switchCase : switchStatement.cases) {
         if (switchCase.key == null) {
            return false;
         }
      }

      return true;
   }

   public Void visitAndAnd(AndAndExpression expression) {
      Label end = new Label();
      Label onFalse = new Label();
      expression.left.accept(this);
      this.javaWriter.ifEQ(onFalse);
      expression.right.accept(this);
      this.javaWriter.goTo(end);
      this.javaWriter.label(onFalse);
      this.javaWriter.iConst0();
      this.javaWriter.label(end);
      return null;
   }

   public Void visitArray(ArrayExpression expression) {
      this.javaWriter.constant(expression.expressions.length);
      Type type = this.context.getType(((ArrayTypeID)expression.type).elementType);
      this.javaWriter.newArray(type);

      for (int i = 0; i < expression.expressions.length; i++) {
         this.javaWriter.dup();
         this.javaWriter.constant(i);
         expression.expressions[i].accept(this);
         this.javaWriter.arrayStore(type);
      }

      return null;
   }

   public Void visitCompare(CompareExpression expression) {
      if (expression.operator.getBuiltin() != null) {
         switch (expression.operator.getBuiltin()) {
            case BYTE_COMPARE:
               expression.left.accept(this);
               this.javaWriter.constant(255);
               this.javaWriter.iAnd();
               expression.right.accept(this);
               this.javaWriter.constant(255);
               this.javaWriter.iAnd();
               this.compareInt(expression.comparison);
               break;
            case USHORT_COMPARE:
               expression.left.accept(this);
               this.javaWriter.constant(65535);
               this.javaWriter.iAnd();
               expression.right.accept(this);
               this.javaWriter.constant(65535);
               this.javaWriter.iAnd();
               this.compareInt(expression.comparison);
               break;
            case SBYTE_COMPARE:
            case SHORT_COMPARE:
            case INT_COMPARE:
            case CHAR_COMPARE:
            case USIZE_COMPARE:
               expression.left.accept(this);
               expression.right.accept(this);
               this.compareInt(expression.comparison);
               break;
            case UINT_COMPARE:
            case USIZE_COMPARE_UINT:
               expression.left.accept(this);
               expression.right.accept(this);
               this.javaWriter.invokeStatic(INTEGER_COMPARE_UNSIGNED);
               this.compareGeneric(expression.comparison);
               break;
            case LONG_COMPARE:
               expression.left.accept(this);
               expression.right.accept(this);
               this.javaWriter.invokeStatic(LONG_COMPARE);
               this.compareGeneric(expression.comparison);
               break;
            case ULONG_COMPARE:
               expression.left.accept(this);
               expression.right.accept(this);
               this.javaWriter.invokeStatic(LONG_COMPARE_UNSIGNED);
               this.compareGeneric(expression.comparison);
               break;
            case ULONG_COMPARE_UINT:
               expression.left.accept(this);
               expression.right.accept(this);
               this.javaWriter.i2l();
               this.javaWriter.constant(4294967295L);
               this.javaWriter.lAnd();
               this.javaWriter.invokeStatic(LONG_COMPARE_UNSIGNED);
               this.compareGeneric(expression.comparison);
               break;
            case ULONG_COMPARE_USIZE:
               expression.left.accept(this);
               expression.right.accept(this);
               this.javaWriter.i2l();
               this.javaWriter.invokeStatic(LONG_COMPARE_UNSIGNED);
               this.compareGeneric(expression.comparison);
               break;
            case FLOAT_COMPARE:
               expression.left.accept(this);
               expression.right.accept(this);
               this.javaWriter.invokeStatic(FLOAT_COMPARE);
               this.compareGeneric(expression.comparison);
               break;
            case DOUBLE_COMPARE:
               expression.left.accept(this);
               expression.right.accept(this);
               this.javaWriter.invokeStatic(DOUBLE_COMPARE);
               this.compareGeneric(expression.comparison);
               break;
            case STRING_COMPARE:
               expression.left.accept(this);
               expression.right.accept(this);
               this.javaWriter.invokeVirtual(STRING_COMPARETO);
               this.compareGeneric(expression.comparison);
               break;
            case ENUM_COMPARE:
               expression.left.accept(this);
               expression.right.accept(this);
               this.javaWriter.invokeVirtual(ENUM_COMPARETO);
               this.compareGeneric(expression.comparison);
               break;
            default:
               throw new UnsupportedOperationException("Unknown builtin comparator: " + expression.operator.getBuiltin());
         }
      } else {
         expression.left.accept(this);
         expression.right.accept(this);
         if (!this.checkAndExecuteMethodInfo(expression.operator, expression.type, expression)) {
            throw new IllegalStateException("Call target has no method info!");
         }

         this.compareGeneric(expression.comparison);
      }

      return null;
   }

   private void compareInt(CompareType comparator) {
      Label exit = new Label();
      Label isTrue = new Label();
      switch (comparator) {
         case EQ:
            this.javaWriter.ifICmpEQ(isTrue);
            break;
         case NE:
            this.javaWriter.ifICmpNE(isTrue);
            break;
         case GT:
            this.javaWriter.ifICmpGT(isTrue);
            break;
         case GE:
            this.javaWriter.ifICmpGE(isTrue);
            break;
         case LT:
            this.javaWriter.ifICmpLT(isTrue);
            break;
         case LE:
            this.javaWriter.ifICmpLE(isTrue);
            break;
         default:
            throw new IllegalStateException("Invalid comparator: " + comparator);
      }

      this.javaWriter.iConst0();
      this.javaWriter.goTo(exit);
      this.javaWriter.label(isTrue);
      this.javaWriter.iConst1();
      this.javaWriter.label(exit);
   }

   private void compareGeneric(CompareType comparator) {
      Label exit = new Label();
      Label isTrue = new Label();
      switch (comparator) {
         case EQ:
            this.javaWriter.ifEQ(isTrue);
            break;
         case NE:
            this.javaWriter.ifNE(isTrue);
            break;
         case GT:
            this.javaWriter.ifGT(isTrue);
            break;
         case GE:
            this.javaWriter.ifGE(isTrue);
            break;
         case LT:
            this.javaWriter.ifLT(isTrue);
            break;
         case LE:
            this.javaWriter.ifLE(isTrue);
            break;
         default:
            throw new IllegalStateException("Invalid comparator: " + comparator);
      }

      this.javaWriter.iConst0();
      this.javaWriter.goTo(exit);
      this.javaWriter.label(isTrue);
      this.javaWriter.iConst1();
      this.javaWriter.label(exit);
   }

   public Void visitCall(CallExpression expression) {
      BuiltinID builtin = expression.member.getBuiltin();
      if (builtin == null) {
         expression.target.accept(this);
         this.handleTypeArguments(expression.member, expression.arguments);
         Expression[] arguments = expression.arguments.arguments;
         FunctionParameter[] parameters = expression.instancedHeader.parameters;
         boolean variadic = expression.instancedHeader.isVariadicCall(expression.arguments)
            && (arguments.length != parameters.length || !parameters[parameters.length - 1].type.equals(arguments[arguments.length - 1].type));
         FunctionParameter[] originalParameters = expression.member.getTarget().getHeader().parameters;
         this.handleCallArguments(arguments, parameters, variadic, originalParameters);
         if (!this.checkAndExecuteMethodInfo(expression.member, expression.type, expression)) {
            throw new IllegalStateException("Call target has no method info!");
         } else {
            return null;
         }
      } else {
         switch (builtin) {
            case BYTE_INC:
               this.modify(expression.target, () -> {
                  this.javaWriter.iConst1();
                  this.javaWriter.iAdd();
                  this.javaWriter.constant(255);
                  this.javaWriter.iAnd();
               }, JavaModificationExpressionVisitor.PushOption.AFTER);
               return null;
            case BYTE_DEC:
               this.modify(expression.target, () -> {
                  this.javaWriter.iConst1();
                  this.javaWriter.iSub();
                  this.javaWriter.constant(255);
                  this.javaWriter.iAnd();
               }, JavaModificationExpressionVisitor.PushOption.AFTER);
               return null;
            case SBYTE_INC:
               this.modify(expression.target, () -> {
                  this.javaWriter.iConst1();
                  this.javaWriter.iAdd();
                  this.javaWriter.i2b();
               }, JavaModificationExpressionVisitor.PushOption.AFTER);
               return null;
            case SBYTE_DEC:
               this.modify(expression.target, () -> {
                  this.javaWriter.iConst1();
                  this.javaWriter.iSub();
                  this.javaWriter.i2b();
               }, JavaModificationExpressionVisitor.PushOption.AFTER);
               return null;
            case SHORT_INC:
               this.modify(expression.target, () -> {
                  this.javaWriter.iConst1();
                  this.javaWriter.iAdd();
                  this.javaWriter.i2s();
               }, JavaModificationExpressionVisitor.PushOption.AFTER);
               return null;
            case SHORT_DEC:
               this.modify(expression.target, () -> {
                  this.javaWriter.iConst1();
                  this.javaWriter.iSub();
                  this.javaWriter.i2s();
               }, JavaModificationExpressionVisitor.PushOption.AFTER);
               return null;
            case USHORT_INC:
               this.modify(expression.target, () -> {
                  this.javaWriter.iConst1();
                  this.javaWriter.iAdd();
                  this.javaWriter.constant(65535);
                  this.javaWriter.iAnd();
               }, JavaModificationExpressionVisitor.PushOption.AFTER);
               return null;
            case USHORT_DEC:
               this.modify(expression.target, () -> {
                  this.javaWriter.iConst1();
                  this.javaWriter.iSub();
                  this.javaWriter.constant(65535);
                  this.javaWriter.iAnd();
               }, JavaModificationExpressionVisitor.PushOption.AFTER);
               return null;
            case INT_INC:
            case UINT_INC:
            case USIZE_INC:
               if (expression.target instanceof GetLocalVariableExpression) {
                  JavaLocalVariableInfo local = this.javaWriter.getLocalVariable(((GetLocalVariableExpression)expression.target).variable.variable);
                  this.javaWriter.iinc(local.local);
                  this.javaWriter.load(local);
               } else {
                  this.modify(expression.target, () -> {
                     this.javaWriter.iConst1();
                     this.javaWriter.iAdd();
                  }, JavaModificationExpressionVisitor.PushOption.AFTER);
               }

               return null;
            case INT_DEC:
            case UINT_DEC:
            case USIZE_DEC:
               if (expression.target instanceof GetLocalVariableExpression) {
                  JavaLocalVariableInfo local = this.javaWriter.getLocalVariable(((GetLocalVariableExpression)expression.target).variable.variable);
                  this.javaWriter.iinc(local.local, -1);
                  this.javaWriter.load(local);
               } else {
                  this.modify(expression.target, () -> {
                     this.javaWriter.iConst1();
                     this.javaWriter.iSub();
                  }, JavaModificationExpressionVisitor.PushOption.AFTER);
               }

               return null;
            case LONG_INC:
            case ULONG_INC:
               this.modify(expression.target, () -> {
                  this.javaWriter.constant(1L);
                  this.javaWriter.iAdd();
               }, JavaModificationExpressionVisitor.PushOption.AFTER);
               return null;
            case LONG_DEC:
            case ULONG_DEC:
               this.modify(expression.target, () -> {
                  this.javaWriter.constant(1L);
                  this.javaWriter.iSub();
               }, JavaModificationExpressionVisitor.PushOption.AFTER);
               return null;
            case FLOAT_INC:
               this.modify(expression.target, () -> {
                  this.javaWriter.constant(1.0F);
                  this.javaWriter.iAdd();
               }, JavaModificationExpressionVisitor.PushOption.AFTER);
               return null;
            case FLOAT_DEC:
               this.modify(expression.target, () -> {
                  this.javaWriter.constant(1.0F);
                  this.javaWriter.iSub();
               }, JavaModificationExpressionVisitor.PushOption.AFTER);
               return null;
            case DOUBLE_INC:
               this.modify(expression.target, () -> {
                  this.javaWriter.constant(1.0);
                  this.javaWriter.iAdd();
               }, JavaModificationExpressionVisitor.PushOption.AFTER);
               return null;
            case DOUBLE_DEC:
               this.modify(expression.target, () -> {
                  this.javaWriter.constant(1.0);
                  this.javaWriter.iSub();
               }, JavaModificationExpressionVisitor.PushOption.AFTER);
               return null;
            case OPTIONAL_IS_NULL:
            case OPTIONAL_IS_NOT_NULL:
               expression.target.accept(this);
               Label isFalse = new Label();
               Label end = new Label();
               if (expression.target.type.withoutOptional() == BasicTypeID.USIZE) {
                  this.javaWriter.constant(-1);
                  if (builtin == BuiltinID.OPTIONAL_IS_NULL) {
                     this.javaWriter.ifICmpNE(isFalse);
                  } else {
                     this.javaWriter.ifICmpEQ(isFalse);
                  }
               } else if (builtin == BuiltinID.OPTIONAL_IS_NULL) {
                  this.javaWriter.ifNonNull(isFalse);
               } else {
                  this.javaWriter.ifNull(isFalse);
               }

               this.javaWriter.iConst1();
               this.javaWriter.goTo(end);
               this.javaWriter.label(isFalse);
               this.javaWriter.iConst0();
               this.javaWriter.label(end);
               return null;
            case ASSOC_INDEXSET:
               expression.target.accept(this);
               AssocTypeID typeId = (AssocTypeID)expression.target.type;
               Expression[] arguments = expression.arguments.arguments;
               assert arguments.length == 2;
               arguments[0].accept(this);
               typeId.keyType.accept(typeId.keyType, this.boxingTypeVisitor);
               arguments[1].accept(this);
               typeId.valueType.accept(typeId.valueType, this.boxingTypeVisitor);
               this.javaWriter.invokeVirtual(MAP_PUT);
               this.javaWriter.pop();
               return null;
            default:
               expression.target.accept(this);

               for (Expression argument : expression.arguments.arguments) {
                  argument.accept(this);
               }
            case STRING_RANGEGET:
            case ARRAY_INDEXGETRANGE:
            case ARRAY_INDEXGET:
            case ARRAY_CONTAINS:
               switch (builtin) {
                  case STRING_RANGEGET:
                     expression.target.accept(this);
                     Expression argument = expression.arguments.arguments[0];
                     if (argument instanceof RangeExpression rangeArgument) {
                        rangeArgument.from.accept(this);
                        rangeArgument.to.accept(this);
                     } else {
                        argument.accept(this);
                        this.javaWriter.dup();
                        String owner;
                        if (argument.type instanceof RangeTypeID) {
                           owner = this.context.getInternalName(argument.type);
                        } else {
                           owner = "zsynthetic/IntRange";
                        }

                        int tmp = this.javaWriter.local(Type.getType(owner));
                        this.javaWriter.storeInt(tmp);
                        this.javaWriter.getField(owner, "from", "I");
                        this.javaWriter.loadInt(tmp);
                        this.javaWriter.getField(owner, "to", "I");
                     }

                     this.javaWriter.invokeVirtual(STRING_SUBSTRING);
                     break;
                  case ARRAY_INDEXGETRANGE: {
                     ArrayTypeID type = (ArrayTypeID)expression.target.type;
                     expression.target.accept(this);
                     Expression argument = expression.arguments.arguments[0];
                     if (argument instanceof RangeExpression rangeArgument) {
                        rangeArgument.from.accept(this);
                        rangeArgument.to.accept(this);
                     } else {
                        argument.accept(this);
                        this.javaWriter.dup();
                        String owner;
                        if (argument.type instanceof RangeTypeID) {
                           owner = this.context.getInternalName(argument.type);
                        } else {
                           owner = "zsynthetic/IntRange";
                        }

                        int tmp = this.javaWriter.local(Type.getType(owner));
                        this.javaWriter.storeInt(tmp);
                        this.javaWriter.getField(owner, "from", "I");
                        this.javaWriter.loadInt(tmp);
                        this.javaWriter.getField(owner, "to", "I");
                     }

                     if (type.elementType instanceof BasicTypeID && type.elementType != BasicTypeID.STRING) {
                        switch ((BasicTypeID)type.elementType) {
                           case BOOL:
                              this.javaWriter.invokeStatic(ARRAYS_COPY_OF_RANGE_BOOLS);
                              return null;
                           case BYTE:
                           case SBYTE:
                              this.javaWriter.invokeStatic(ARRAYS_COPY_OF_RANGE_BYTES);
                              return null;
                           case SHORT:
                           case USHORT:
                              this.javaWriter.invokeStatic(ARRAYS_COPY_OF_RANGE_SHORTS);
                              return null;
                           case INT:
                           case UINT:
                              this.javaWriter.invokeStatic(ARRAYS_COPY_OF_RANGE_INTS);
                              return null;
                           case LONG:
                           case ULONG:
                              this.javaWriter.invokeStatic(ARRAYS_COPY_OF_RANGE_LONGS);
                              return null;
                           case FLOAT:
                              this.javaWriter.invokeStatic(ARRAYS_COPY_OF_RANGE_FLOATS);
                              return null;
                           case DOUBLE:
                              this.javaWriter.invokeStatic(ARRAYS_COPY_OF_RANGE_DOUBLES);
                              return null;
                           case CHAR:
                              this.javaWriter.invokeStatic(ARRAYS_COPY_OF_RANGE_CHARS);
                              return null;
                           default:
                              throw new IllegalArgumentException("Unknown basic type: " + type.elementType);
                        }
                     } else {
                        this.javaWriter.invokeStatic(ARRAYS_COPY_OF_RANGE_OBJECTS);
                        this.javaWriter.checkCast(this.context.getType(expression.target.type));
                        break;
                     }
                  }
                  case ARRAY_INDEXGET: {
                     ArrayTypeID type = (ArrayTypeID)expression.target.type;
                     expression.target.accept(this);
                     Expression[] argumentsx = expression.arguments.arguments;
                     Type asmType = this.context.getType(expression.target.type);

                     for (Expression argument : argumentsx) {
                        asmType = Type.getType(asmType.getDescriptor().substring(1));
                        argument.accept(this);
                        this.javaWriter.arrayLoad(asmType);
                     }
                     break;
                  }
                  case ARRAY_CONTAINS:
                     expression.target.accept(this);
                     Label loopStart = new Label();
                     Label loopEnd = new Label();
                     Label isTrue = new Label();
                     Label expressionEnd = new Label();
                     int counterLocation = this.javaWriter.local(int.class);
                     this.javaWriter.iConst0();
                     this.javaWriter.storeInt(counterLocation);
                     this.javaWriter.label(loopStart);
                     this.javaWriter.dup();
                     this.javaWriter.arrayLength();
                     this.javaWriter.loadInt(counterLocation);
                     this.javaWriter.ifICmpLE(loopEnd);
                     this.javaWriter.dup();
                     this.javaWriter.loadInt(counterLocation);
                     TypeID itemType = expression.arguments.arguments[0].type;
                     this.javaWriter.arrayLoad(this.context.getType(itemType));
                     this.javaWriter.iinc(counterLocation);
                     expression.arguments.arguments[0].accept(this);
                     if (CompilerUtils.isPrimitive(itemType)) {
                        if (itemType == BasicTypeID.LONG || itemType == BasicTypeID.ULONG) {
                           this.javaWriter.lCmp();
                           this.javaWriter.ifEQ(loopStart);
                        } else if (itemType == BasicTypeID.FLOAT) {
                           this.javaWriter.fCmp();
                           this.javaWriter.ifEQ(loopStart);
                        } else if (itemType == BasicTypeID.DOUBLE) {
                           this.javaWriter.dCmp();
                           this.javaWriter.ifEQ(loopStart);
                        } else {
                           this.javaWriter.ifICmpNE(loopStart);
                        }
                     } else {
                        this.javaWriter
                           .invokeStatic(
                              new JavaMethod(
                                 JavaClass.fromInternalName("java/util/Objects", JavaClass.Kind.CLASS),
                                 JavaMethod.Kind.STATIC,
                                 "equals",
                                 false,
                                 "(Ljava/lang/Object;Ljava/lang/Object;)Z",
                                 0,
                                 false
                              )
                           );
                        this.javaWriter.ifEQ(loopStart);
                     }

                     this.javaWriter.label(isTrue);
                     this.javaWriter.pop();
                     this.javaWriter.iConst1();
                     this.javaWriter.goTo(expressionEnd);
                     this.javaWriter.label(loopEnd);
                     this.javaWriter.pop();
                     this.javaWriter.iConst0();
                     this.javaWriter.label(expressionEnd);
                     break;
                  case BYTE_INC:
                  case BYTE_DEC:
                  case SBYTE_INC:
                  case SBYTE_DEC:
                  case SHORT_INC:
                  case SHORT_DEC:
                  case USHORT_INC:
                  case USHORT_DEC:
                  case INT_INC:
                  case UINT_INC:
                  case USIZE_INC:
                  case INT_DEC:
                  case UINT_DEC:
                  case USIZE_DEC:
                  case LONG_INC:
                  case ULONG_INC:
                  case LONG_DEC:
                  case ULONG_DEC:
                  case FLOAT_INC:
                  case FLOAT_DEC:
                  case DOUBLE_INC:
                  case DOUBLE_DEC:
                  case OPTIONAL_IS_NULL:
                  case OPTIONAL_IS_NOT_NULL:
                  case ASSOC_INDEXSET:
                  default:
                     throw new UnsupportedOperationException("Unknown builtin: " + builtin);
                  case BOOL_NOT:
                     this.javaWriter.invertBoolean();
                     break;
                  case BOOL_AND:
                     this.javaWriter.iAnd();
                     break;
                  case BOOL_OR:
                     this.javaWriter.iOr();
                     break;
                  case BOOL_XOR:
                     this.javaWriter.iXor();
                     break;
                  case BOOL_EQUALS:
                     this.javaWriter.iXor();
                     this.javaWriter.iConst1();
                     this.javaWriter.iXor();
                     break;
                  case BOOL_NOTEQUALS:
                     this.javaWriter.iXor();
                     break;
                  case BYTE_NOT:
                  case SBYTE_NOT:
                  case SHORT_NOT:
                  case USHORT_NOT:
                  case INT_NOT:
                  case UINT_NOT:
                  case USIZE_NOT:
                     this.javaWriter.iNot();
                     break;
                  case SBYTE_NEG:
                  case SHORT_NEG:
                  case INT_NEG:
                     this.javaWriter.iNeg();
                     break;
                  case BYTE_ADD_BYTE:
                  case SBYTE_ADD_SBYTE:
                  case SHORT_ADD_SHORT:
                  case USHORT_ADD_USHORT:
                  case INT_ADD_INT:
                  case UINT_ADD_UINT:
                  case USIZE_ADD_USIZE:
                     this.javaWriter.iAdd();
                     break;
                  case BYTE_SUB_BYTE:
                  case SBYTE_SUB_SBYTE:
                  case SHORT_SUB_SHORT:
                  case USHORT_SUB_USHORT:
                  case INT_SUB_INT:
                  case UINT_SUB_UINT:
                  case USIZE_SUB_USIZE:
                     this.javaWriter.iSub();
                     break;
                  case BYTE_MUL_BYTE:
                  case SBYTE_MUL_SBYTE:
                  case SHORT_MUL_SHORT:
                  case USHORT_MUL_USHORT:
                  case INT_MUL_INT:
                  case UINT_MUL_UINT:
                  case USIZE_MUL_USIZE:
                     this.javaWriter.iMul();
                     break;
                  case SBYTE_DIV_SBYTE:
                  case SHORT_DIV_SHORT:
                  case INT_DIV_INT:
                  case USIZE_DIV_USIZE:
                     this.javaWriter.iDiv();
                     break;
                  case SBYTE_MOD_SBYTE:
                  case SHORT_MOD_SHORT:
                  case INT_MOD_INT:
                  case USIZE_MOD_USIZE:
                     this.javaWriter.iRem();
                     break;
                  case BYTE_DIV_BYTE:
                  case USHORT_DIV_USHORT:
                  case UINT_DIV_UINT:
                     this.javaWriter.invokeStatic(INTEGER_DIVIDE_UNSIGNED);
                     break;
                  case BYTE_MOD_BYTE:
                  case USHORT_MOD_USHORT:
                  case UINT_MOD_UINT:
                     this.javaWriter.invokeStatic(INTEGER_REMAINDER_UNSIGNED);
                     break;
                  case BYTE_AND_BYTE:
                  case SBYTE_AND_SBYTE:
                  case SHORT_AND_SHORT:
                  case USHORT_AND_USHORT:
                  case INT_AND_INT:
                  case UINT_AND_UINT:
                  case USIZE_AND_USIZE:
                     this.javaWriter.iAnd();
                     break;
                  case BYTE_OR_BYTE:
                  case SBYTE_OR_SBYTE:
                  case SHORT_OR_SHORT:
                  case USHORT_OR_USHORT:
                  case INT_OR_INT:
                  case UINT_OR_UINT:
                  case USIZE_OR_USIZE:
                     this.javaWriter.iOr();
                     break;
                  case BYTE_XOR_BYTE:
                  case SBYTE_XOR_SBYTE:
                  case SHORT_XOR_SHORT:
                  case USHORT_XOR_USHORT:
                  case INT_XOR_INT:
                  case UINT_XOR_UINT:
                  case USIZE_XOR_USIZE:
                     this.javaWriter.iXor();
                     break;
                  case BYTE_SHL:
                  case SBYTE_SHL:
                  case SHORT_SHL:
                  case USHORT_SHL:
                  case INT_SHL:
                  case UINT_SHL:
                  case USIZE_SHL:
                     this.javaWriter.iShl();
                     break;
                  case SBYTE_SHR:
                  case SHORT_SHR:
                  case INT_SHR:
                     this.javaWriter.iShr();
                     break;
                  case BYTE_SHR:
                  case SBYTE_USHR:
                  case USHORT_SHR:
                  case SHORT_USHR:
                  case INT_USHR:
                  case UINT_SHR:
                  case USIZE_SHR:
                     this.javaWriter.iUShr();
                     break;
                  case INT_COUNT_LOW_ZEROES:
                  case UINT_COUNT_LOW_ZEROES:
                  case USIZE_COUNT_LOW_ZEROES:
                     this.javaWriter.invokeStatic(INTEGER_NUMBER_OF_TRAILING_ZEROS);
                     break;
                  case INT_COUNT_HIGH_ZEROES:
                  case UINT_COUNT_HIGH_ZEROES:
                  case USIZE_COUNT_HIGH_ZEROES:
                     this.javaWriter.invokeStatic(INTEGER_NUMBER_OF_LEADING_ZEROS);
                     break;
                  case INT_COUNT_LOW_ONES:
                  case UINT_COUNT_LOW_ONES:
                  case USIZE_COUNT_LOW_ONES:
                     this.javaWriter.iNot();
                     this.javaWriter.invokeStatic(INTEGER_NUMBER_OF_TRAILING_ZEROS);
                     break;
                  case INT_COUNT_HIGH_ONES:
                  case UINT_COUNT_HIGH_ONES:
                  case USIZE_COUNT_HIGH_ONES:
                     this.javaWriter.iNot();
                     this.javaWriter.invokeStatic(INTEGER_NUMBER_OF_LEADING_ZEROS);
                     break;
                  case LONG_NOT:
                  case ULONG_NOT:
                     this.javaWriter.lNot();
                     break;
                  case LONG_NEG:
                     this.javaWriter.lNeg();
                     break;
                  case LONG_ADD_LONG:
                  case ULONG_ADD_ULONG:
                     this.javaWriter.lAdd();
                     break;
                  case LONG_SUB_LONG:
                  case ULONG_SUB_ULONG:
                     this.javaWriter.lSub();
                     break;
                  case LONG_MUL_LONG:
                  case ULONG_MUL_ULONG:
                     this.javaWriter.lMul();
                     break;
                  case LONG_DIV_LONG:
                     this.javaWriter.lDiv();
                     break;
                  case LONG_MOD_LONG:
                     this.javaWriter.lRem();
                     break;
                  case LONG_AND_LONG:
                  case ULONG_AND_ULONG:
                     this.javaWriter.lAnd();
                     break;
                  case LONG_OR_LONG:
                  case ULONG_OR_ULONG:
                     this.javaWriter.lOr();
                     break;
                  case LONG_XOR_LONG:
                  case ULONG_XOR_ULONG:
                     this.javaWriter.lXor();
                     break;
                  case LONG_SHL:
                  case ULONG_SHL:
                     this.javaWriter.lShl();
                     break;
                  case LONG_SHR:
                     this.javaWriter.lShr();
                     break;
                  case LONG_USHR:
                  case ULONG_SHR:
                     this.javaWriter.lUShr();
                     break;
                  case LONG_COUNT_LOW_ZEROES:
                  case ULONG_COUNT_LOW_ZEROES:
                     this.javaWriter.invokeStatic(LONG_NUMBER_OF_TRAILING_ZEROS);
                     break;
                  case LONG_COUNT_HIGH_ZEROES:
                  case ULONG_COUNT_HIGH_ZEROES:
                     this.javaWriter.invokeStatic(LONG_NUMBER_OF_LEADING_ZEROS);
                     break;
                  case LONG_COUNT_LOW_ONES:
                  case ULONG_COUNT_LOW_ONES:
                     this.javaWriter.lNot();
                     this.javaWriter.invokeStatic(LONG_NUMBER_OF_TRAILING_ZEROS);
                     break;
                  case LONG_COUNT_HIGH_ONES:
                  case ULONG_COUNT_HIGH_ONES:
                     this.javaWriter.lNot();
                     this.javaWriter.invokeStatic(LONG_NUMBER_OF_LEADING_ZEROS);
                     break;
                  case ULONG_DIV_ULONG:
                     this.javaWriter.invokeStatic(LONG_DIVIDE_UNSIGNED);
                     break;
                  case ULONG_MOD_ULONG:
                     this.javaWriter.invokeStatic(LONG_REMAINDER_UNSIGNED);
                     break;
                  case FLOAT_NEG:
                     this.javaWriter.fNeg();
                     break;
                  case FLOAT_ADD_FLOAT:
                     this.javaWriter.fAdd();
                     break;
                  case FLOAT_SUB_FLOAT:
                     this.javaWriter.fSub();
                     break;
                  case FLOAT_MUL_FLOAT:
                     this.javaWriter.fMul();
                     break;
                  case FLOAT_DIV_FLOAT:
                     this.javaWriter.fDiv();
                     break;
                  case FLOAT_MOD_FLOAT:
                     this.javaWriter.fRem();
                     break;
                  case DOUBLE_NEG:
                     this.javaWriter.dNeg();
                     break;
                  case DOUBLE_ADD_DOUBLE:
                     this.javaWriter.dAdd();
                     break;
                  case DOUBLE_SUB_DOUBLE:
                     this.javaWriter.dSub();
                     break;
                  case DOUBLE_MUL_DOUBLE:
                     this.javaWriter.dMul();
                     break;
                  case DOUBLE_DIV_DOUBLE:
                     this.javaWriter.dDiv();
                     break;
                  case DOUBLE_MOD_DOUBLE:
                     this.javaWriter.dRem();
                     break;
                  case CHAR_ADD_INT:
                     this.javaWriter.iAdd();
                     break;
                  case CHAR_SUB_INT:
                  case CHAR_SUB_CHAR:
                     this.javaWriter.iSub();
                     break;
                  case CHAR_REMOVE_DIACRITICS:
                     throw new UnsupportedOperationException("Not yet supported!");
                  case CHAR_TO_LOWER_CASE:
                     this.javaWriter.invokeStatic(CHARACTER_TO_LOWER_CASE);
                     break;
                  case CHAR_TO_UPPER_CASE:
                     this.javaWriter.invokeStatic(CHARACTER_TO_UPPER_CASE);
                     break;
                  case STRING_ADD_STRING:
                     this.javaWriter.invokeVirtual(STRING_CONCAT);
                     break;
                  case STRING_INDEXGET:
                     this.javaWriter.invokeVirtual(STRING_CHAR_AT);
                     break;
                  case STRING_REMOVE_DIACRITICS:
                     throw new UnsupportedOperationException("Not yet supported!");
                  case STRING_TRIM:
                     this.javaWriter.invokeVirtual(STRING_TRIM);
                     break;
                  case STRING_TO_LOWER_CASE:
                     this.javaWriter.invokeVirtual(STRING_TO_LOWER_CASE);
                     break;
                  case STRING_TO_UPPER_CASE:
                     this.javaWriter.invokeVirtual(STRING_TO_UPPER_CASE);
                     break;
                  case ASSOC_INDEXGET:
                  case ASSOC_GETORDEFAULT: {
                     AssocTypeID type = (AssocTypeID)expression.target.type;
                     type.keyType.accept(type.keyType, this.boxingTypeVisitor);
                     this.javaWriter.invokeInterface(MAP_GET);
                     this.handleGenericReturnValue(type.valueType);
                     break;
                  }
                  case ASSOC_CONTAINS:
                     this.javaWriter.invokeVirtual(MAP_CONTAINS_KEY);
                     break;
                  case ASSOC_EQUALS:
                     this.javaWriter.invokeVirtual(OBJECT_EQUALS);
                     break;
                  case ASSOC_NOTEQUALS:
                     this.javaWriter.invokeVirtual(OBJECT_EQUALS);
                     this.javaWriter.invertBoolean();
                     break;
                  case ASSOC_SAME:
                  case GENERICMAP_SAME:
                  case ARRAY_SAME:
                  case FUNCTION_SAME:
                  case OBJECT_SAME: {
                     Label exit = new Label();
                     this.javaWriter.iConst0();
                     this.javaWriter.ifACmpNe(exit);
                     this.javaWriter.iConst1();
                     this.javaWriter.label(exit);
                     break;
                  }
                  case ASSOC_NOTSAME:
                  case GENERICMAP_NOTSAME:
                  case ARRAY_NOTSAME:
                  case FUNCTION_NOTSAME:
                  case OBJECT_NOTSAME: {
                     Label exit = new Label();
                     this.javaWriter.iConst0();
                     this.javaWriter.ifACmpEq(exit);
                     this.javaWriter.iConst1();
                     this.javaWriter.label(exit);
                     break;
                  }
                  case GENERICMAP_GETOPTIONAL:
                     this.javaWriter.invokeVirtual(MAP_GET);
                     break;
                  case GENERICMAP_PUT:
                     if (expression.arguments.arguments.length == 1) {
                        this.javaWriter.dup();
                        this.javaWriter.invokeVirtual(JavaMethod.getVirtual(JavaClass.OBJECT, "getClass", "()Ljava/lang/Class;", 0));
                        this.javaWriter.swap();
                     }

                     this.javaWriter.invokeVirtual(MAP_PUT);
                     this.javaWriter.pop();
                     break;
                  case GENERICMAP_CONTAINS:
                     this.javaWriter.invokeVirtual(MAP_CONTAINS_KEY);
                     break;
                  case GENERICMAP_EQUALS:
                     throw new UnsupportedOperationException("Not yet supported!");
                  case GENERICMAP_NOTEQUALS:
                     throw new UnsupportedOperationException("Not yet supported!");
                  case GENERICMAP_ADDALL:
                     this.javaWriter.invokeInterface(MAP_PUT_ALL);
                     break;
                  case ARRAY_INDEXSET: {
                     ArrayTypeID type = (ArrayTypeID)expression.target.type;
                     if (type.elementType == BasicTypeID.BYTE) {
                        this.javaWriter.i2b();
                        this.javaWriter.arrayStore(Type.BYTE_TYPE);
                     } else if (type.elementType == BasicTypeID.USHORT) {
                        this.javaWriter.i2s();
                        this.javaWriter.arrayStore(Type.SHORT_TYPE);
                     } else {
                        this.javaWriter.arrayStore(this.context.getType(type.elementType));
                     }
                     break;
                  }
                  case ARRAY_EQUALS:
                  case ARRAY_NOTEQUALS: {
                     ArrayTypeID type = (ArrayTypeID)expression.target.type;
                     if (type.elementType instanceof BasicTypeID) {
                        switch ((BasicTypeID)type.elementType) {
                           case BOOL:
                              this.javaWriter.invokeStatic(ARRAYS_EQUALS_BOOLS);
                              break;
                           case BYTE:
                           case SBYTE:
                              this.javaWriter.invokeStatic(ARRAYS_EQUALS_BYTES);
                              break;
                           case SHORT:
                           case USHORT:
                              this.javaWriter.invokeStatic(ARRAYS_EQUALS_SHORTS);
                              break;
                           case INT:
                           case UINT:
                              this.javaWriter.invokeStatic(ARRAYS_EQUALS_INTS);
                              break;
                           case LONG:
                           case ULONG:
                              this.javaWriter.invokeStatic(ARRAYS_EQUALS_LONGS);
                              break;
                           case FLOAT:
                              this.javaWriter.invokeStatic(ARRAYS_EQUALS_FLOATS);
                              break;
                           case DOUBLE:
                              this.javaWriter.invokeStatic(ARRAYS_EQUALS_DOUBLES);
                              break;
                           case CHAR:
                              this.javaWriter.invokeStatic(ARRAYS_EQUALS_CHARS);
                              break;
                           default:
                              throw new IllegalArgumentException("Unknown basic type: " + type.elementType);
                        }
                     } else {
                        this.javaWriter.invokeStatic(ARRAYS_EQUALS_OBJECTS);
                     }

                     if (builtin == BuiltinID.ARRAY_NOTEQUALS) {
                        this.javaWriter.invertBoolean();
                     }
                     break;
                  }
                  case FUNCTION_CALL:
                     FunctionTypeID expressionType = (FunctionTypeID)expression.target.type;
                     JavaMethod functionalInterface = this.context.getFunctionalInterface(expressionType);
                     this.javaWriter.invokeInterface(functionalInterface);
                     this.handleReturnValue(this.context.getFunction(expressionType).getHeader().getReturnType(), expression.instancedHeader.getReturnType());
               }

               return null;
         }
      }
   }

   private void handleTypeArguments(FunctionalMemberRef member, CallArguments arguments) {
      List<TypeParameter> parameters = new ArrayList<>();
      if (member.getTarget().definition.isExpansion()) {
         parameters.addAll(Arrays.asList(member.getTarget().definition.typeParameters));
      }

      for (TypeParameter typeParameter : member.getTarget().getHeader().typeParameters) {
         if (!parameters.contains(typeParameter)) {
            parameters.add(typeParameter);
         }
      }

      List<TypeParameter> typeParameters = parameters.stream().distinct().collect(Collectors.toList());
      JavaMethod methodInfo = this.context.getJavaMethod(member);
      if (methodInfo.compile) {
         if (typeParameters.size() == member.getTarget().definition.getNumberOfGenericParameters() + arguments.typeArguments.length) {
            JavaTypeExpressionVisitor javaTypeExpressionVisitor = new JavaTypeExpressionVisitor(this.context);
            if (member.getTarget().definition instanceof ExpansionDefinition) {
               if (((ExpansionDefinition)member.getTarget().definition).target instanceof ArrayTypeID && member.getOwnerType() instanceof ArrayTypeID) {
                  TypeID elementType = ((ArrayTypeID)member.getOwnerType()).elementType;
                  elementType.accept(this.javaWriter, javaTypeExpressionVisitor);
               } else {
                  for (int i = 0; i < member.getTarget().definition.getNumberOfGenericParameters(); i++) {
                     this.javaWriter.aConstNull();
                     this.javaWriter.checkCast("java/lang/Class");
                  }
               }
            }

            for (TypeID typeArgument : arguments.typeArguments) {
               typeArgument.accept(this.javaWriter, javaTypeExpressionVisitor);
            }
         } else {
            for (TypeParameter typeParameter : typeParameters) {
               this.javaWriter.aConstNull();
               this.javaWriter.checkCast("java/lang/Class");
            }
         }
      }
   }

   public Void visitCallStatic(CallStaticExpression expression) {
      Expression[] arguments = expression.arguments.arguments;
      FunctionParameter[] parameters = expression.instancedHeader.parameters;
      boolean variadic = expression.instancedHeader.isVariadicCall(expression.arguments)
         && (arguments.length != parameters.length || !parameters[parameters.length - 1].type.equals(arguments[arguments.length - 1].type));
      this.handleTypeArguments(expression.member, expression.arguments);
      FunctionParameter[] originalParameters = expression.member.getTarget().getHeader().parameters;
      this.handleCallArguments(arguments, parameters, variadic, originalParameters);
      BuiltinID builtin = expression.member.getBuiltin();
      if (builtin == null) {
         if (!this.checkAndExecuteMethodInfo(expression.member, expression.type, expression)) {
            throw new IllegalStateException("Call target has no method info!");
         } else {
            return null;
         }
      } else {
         switch (builtin) {
            case BOOL_PARSE:
               this.javaWriter.invokeStatic(BOOLEAN_PARSE);
               break;
            case BYTE_PARSE:
               this.javaWriter.invokeStatic(INTEGER_PARSE_UNSIGNED);
               break;
            case BYTE_PARSE_WITH_BASE:
               this.javaWriter.invokeStatic(INTEGER_PARSE_UNSIGNED_WITH_BASE);
               break;
            case SBYTE_PARSE:
               this.javaWriter.invokeStatic(BYTE_PARSE);
               break;
            case SBYTE_PARSE_WITH_BASE:
               this.javaWriter.invokeStatic(BYTE_PARSE_WITH_BASE);
               break;
            case SHORT_PARSE:
               this.javaWriter.invokeStatic(SHORT_PARSE);
               break;
            case SHORT_PARSE_WITH_BASE:
               this.javaWriter.invokeStatic(SHORT_PARSE_WITH_BASE);
               break;
            case USHORT_PARSE:
               this.javaWriter.invokeStatic(INTEGER_PARSE_UNSIGNED);
               break;
            case USHORT_PARSE_WITH_BASE:
               this.javaWriter.invokeStatic(INTEGER_PARSE_UNSIGNED_WITH_BASE);
               break;
            case INT_PARSE:
               this.javaWriter.invokeStatic(INTEGER_PARSE);
               break;
            case INT_PARSE_WITH_BASE:
               this.javaWriter.invokeStatic(INTEGER_PARSE_WITH_BASE);
               break;
            case UINT_PARSE:
            case USIZE_PARSE:
               this.javaWriter.invokeStatic(INTEGER_PARSE_UNSIGNED);
               break;
            case UINT_PARSE_WITH_BASE:
            case USIZE_PARSE_WITH_BASE:
               this.javaWriter.invokeStatic(INTEGER_PARSE_UNSIGNED_WITH_BASE);
               break;
            case LONG_PARSE:
               this.javaWriter.invokeStatic(LONG_PARSE);
               break;
            case LONG_PARSE_WITH_BASE:
               this.javaWriter.invokeStatic(LONG_PARSE_WITH_BASE);
               break;
            case ULONG_PARSE:
               this.javaWriter.invokeStatic(LONG_PARSE_UNSIGNED);
               break;
            case ULONG_PARSE_WITH_BASE:
               this.javaWriter.invokeStatic(LONG_PARSE_UNSIGNED_WITH_BASE);
               break;
            case FLOAT_FROM_BITS:
               this.javaWriter.invokeStatic(FLOAT_FROM_BITS);
               break;
            case FLOAT_PARSE:
               this.javaWriter.invokeStatic(FLOAT_PARSE);
               break;
            case DOUBLE_FROM_BITS:
               this.javaWriter.invokeStatic(DOUBLE_FROM_BITS);
               break;
            case DOUBLE_PARSE:
               this.javaWriter.invokeStatic(DOUBLE_PARSE);
               break;
            default:
               throw new UnsupportedOperationException("Unknown builtin: " + builtin);
         }

         return null;
      }
   }

   private void handleCallArguments(Expression[] arguments, FunctionParameter[] parameters, boolean variadic, FunctionParameter[] originalParameters) {
      if (variadic) {
         for (int i = 0; i < parameters.length - 1; i++) {
            arguments[i].accept(this);
            if (originalParameters[i].type.isGeneric() && CompilerUtils.isPrimitive(parameters[i].type)) {
               parameters[i].type.accept(parameters[i].type, this.boxingTypeVisitor);
            }
         }

         int arrayCount = arguments.length - parameters.length + 1;
         this.javaWriter.constant(arrayCount);
         this.javaWriter.newArray(this.context.getType(parameters[parameters.length - 1].type).getElementType());

         for (int i = 0; i < arrayCount; i++) {
            this.javaWriter.dup();
            this.javaWriter.constant(i);
            Expression argument = arguments[i + parameters.length - 1];
            argument.accept(this);
            this.javaWriter.arrayStore(this.context.getType(argument.type));
         }
      } else {
         for (int i = 0; i < arguments.length; i++) {
            arguments[i].accept(this);
            if (originalParameters[i].type.isGeneric() && CompilerUtils.isPrimitive(parameters[i].type)) {
               parameters[i].type.accept(parameters[i].type, this.boxingTypeVisitor);
            }
         }
      }
   }

   private void handleReturnValue(TypeID original, TypeID actual) {
      if (original.isGeneric()) {
         this.handleGenericReturnValue(actual);
      }
   }

   private void handleGenericReturnValue(TypeID actual) {
      if (CompilerUtils.isPrimitive(actual)) {
         this.getJavaWriter().checkCast(this.context.getInternalName(new OptionalTypeID(null, actual)));
         actual.accept(actual, this.unboxingTypeVisitor);
      } else {
         Type asmType = Type.getType(this.context.getType(actual).getDescriptor());
         this.getJavaWriter().checkCast(asmType);
      }
   }

   public Void visitCapturedClosure(CapturedClosureExpression expression) {
      return expression.accept(this.capturedExpressionVisitor);
   }

   public Void visitCapturedDirect(CapturedDirectExpression expression) {
      return expression.accept(this.capturedExpressionVisitor);
   }

   public Void visitCapturedLocalVariable(CapturedLocalVariableExpression expression) {
      return expression.accept(this.capturedExpressionVisitor);
   }

   public Void visitCapturedParameter(CapturedParameterExpression expression) {
      return expression.accept(this.capturedExpressionVisitor);
   }

   public Void visitCapturedThis(CapturedThisExpression expression) {
      return expression.accept(this.capturedExpressionVisitor);
   }

   public Void visitCast(CastExpression expression) {
      expression.target.accept(this);
      ArrayList<TypeParameter> typeParameters = new ArrayList<>(Arrays.asList(expression.member.member.definition.typeParameters));
      expression.member.toType.extractTypeParameters(typeParameters);
      if (expression.member.member.definition.isExpansion()) {
         for (TypeParameter typeParameter : typeParameters) {
            this.javaWriter.aConstNull();
            this.javaWriter.checkCast("java/lang/Class");
         }
      }

      BuiltinID builtin = expression.member.member.builtin;
      if (builtin == null) {
         if (!this.checkAndExecuteMethodInfo(expression.member, expression.type, expression)) {
            throw new IllegalStateException("Call target has no method info!");
         } else {
            return null;
         }
      } else {
         switch (builtin) {
            case BOOL_TO_STRING:
               if (expression.target.type.isOptional()) {
                  this.javaWriter.invokeStatic(OBJECTS_TOSTRING);
               } else {
                  this.javaWriter.invokeStatic(BOOLEAN_TO_STRING);
               }
               break;
            case BYTE_TO_SBYTE:
               this.javaWriter.i2b();
               break;
            case BYTE_TO_SHORT:
               this.javaWriter.constant(255);
               this.javaWriter.iAnd();
               this.javaWriter.i2s();
               break;
            case BYTE_TO_USHORT:
            case BYTE_TO_INT:
            case BYTE_TO_UINT:
            case BYTE_TO_USIZE:
               this.javaWriter.constant(255);
               this.javaWriter.iAnd();
               break;
            case BYTE_TO_LONG:
            case BYTE_TO_ULONG:
               this.javaWriter.constant(255);
               this.javaWriter.iAnd();
               this.javaWriter.i2l();
               break;
            case BYTE_TO_FLOAT:
               this.javaWriter.constant(255);
               this.javaWriter.iAnd();
               this.javaWriter.i2f();
               break;
            case BYTE_TO_DOUBLE:
               this.javaWriter.constant(255);
               this.javaWriter.iAnd();
               this.javaWriter.i2d();
               break;
            case BYTE_TO_CHAR:
               this.javaWriter.constant(255);
               this.javaWriter.iAnd();
               break;
            case BYTE_TO_STRING:
               if (expression.target.type.isOptional()) {
                  this.javaWriter.invokeStatic(OBJECTS_TOSTRING);
               } else {
                  this.javaWriter.constant(255);
                  this.javaWriter.iAnd();
                  this.javaWriter.invokeStatic(INTEGER_TO_STRING);
               }
            case SBYTE_TO_BYTE:
            case SBYTE_TO_SHORT:
            case SBYTE_TO_USHORT:
            case SBYTE_TO_INT:
            case SBYTE_TO_UINT:
            case SBYTE_TO_USIZE:
            case SBYTE_TO_CHAR:
            case SHORT_TO_BYTE:
            case SHORT_TO_USHORT:
            case SHORT_TO_INT:
            case SHORT_TO_UINT:
            case SHORT_TO_USIZE:
            case SHORT_TO_CHAR:
            case USHORT_TO_BYTE:
            case INT_TO_BYTE:
            case USIZE_TO_BYTE:
            case INT_TO_USHORT:
            case USIZE_TO_USHORT:
            case INT_TO_UINT:
            case USIZE_TO_INT:
            case USIZE_TO_UINT:
            case INT_TO_USIZE:
            case UINT_TO_BYTE:
            case UINT_TO_USHORT:
            case UINT_TO_INT:
            case UINT_TO_USIZE:
            case LONG_TO_ULONG:
            case ULONG_TO_LONG:
            case CHAR_TO_BYTE:
            case CHAR_TO_SHORT:
            case CHAR_TO_USHORT:
            case CHAR_TO_INT:
            case CHAR_TO_UINT:
            case CHAR_TO_USIZE:
               break;
            case SBYTE_TO_LONG:
            case SBYTE_TO_ULONG:
               this.javaWriter.i2l();
               break;
            case SBYTE_TO_FLOAT:
               this.javaWriter.i2f();
               break;
            case SBYTE_TO_DOUBLE:
               this.javaWriter.i2d();
               break;
            case SBYTE_TO_STRING:
               if (expression.target.type.isOptional()) {
                  this.javaWriter.invokeStatic(OBJECTS_TOSTRING);
               } else {
                  this.javaWriter.invokeStatic(INTEGER_TO_STRING);
               }
               break;
            case SHORT_TO_SBYTE:
               this.javaWriter.i2b();
               break;
            case SHORT_TO_LONG:
            case SHORT_TO_ULONG:
               this.javaWriter.i2l();
               break;
            case SHORT_TO_FLOAT:
               this.javaWriter.i2f();
               break;
            case SHORT_TO_DOUBLE:
               this.javaWriter.i2d();
               break;
            case SHORT_TO_STRING:
               if (expression.target.type.isOptional()) {
                  this.javaWriter.invokeStatic(OBJECTS_TOSTRING);
               } else {
                  this.javaWriter.invokeStatic(SHORT_TO_STRING);
               }
               break;
            case USHORT_TO_SBYTE:
               this.javaWriter.i2b();
               break;
            case USHORT_TO_SHORT:
               this.javaWriter.i2s();
               break;
            case USHORT_TO_INT:
            case USHORT_TO_UINT:
            case USHORT_TO_USIZE:
               this.javaWriter.constant(65535);
               this.javaWriter.iAnd();
               break;
            case USHORT_TO_LONG:
            case USHORT_TO_ULONG:
               this.javaWriter.constant(65535L);
               this.javaWriter.iAnd();
               break;
            case USHORT_TO_FLOAT:
               this.javaWriter.constant(65535L);
               this.javaWriter.iAnd();
               this.javaWriter.i2f();
               break;
            case USHORT_TO_DOUBLE:
               this.javaWriter.constant(65535L);
               this.javaWriter.iAnd();
               this.javaWriter.i2d();
               break;
            case USHORT_TO_CHAR:
               this.javaWriter.constant(65535L);
               this.javaWriter.iAnd();
               break;
            case USHORT_TO_STRING:
               if (expression.target.type.isOptional()) {
                  this.javaWriter.invokeStatic(OBJECTS_TOSTRING);
               } else {
                  this.javaWriter.constant(65535L);
                  this.javaWriter.iAnd();
                  this.javaWriter.invokeStatic(INTEGER_TO_STRING);
               }
               break;
            case INT_TO_SBYTE:
            case USIZE_TO_SBYTE:
               this.javaWriter.i2b();
               break;
            case INT_TO_SHORT:
            case USIZE_TO_SHORT:
               this.javaWriter.i2s();
               break;
            case INT_TO_LONG:
            case INT_TO_ULONG:
            case USIZE_TO_LONG:
            case USIZE_TO_ULONG:
               this.javaWriter.i2l();
               break;
            case INT_TO_FLOAT:
            case USIZE_TO_FLOAT:
               this.javaWriter.i2f();
               break;
            case INT_TO_DOUBLE:
            case USIZE_TO_DOUBLE:
               this.javaWriter.i2d();
               break;
            case INT_TO_CHAR:
            case USIZE_TO_CHAR:
               this.javaWriter.i2s();
               break;
            case INT_TO_STRING:
               if (expression.target.type.isOptional()) {
                  this.javaWriter.invokeStatic(OBJECTS_TOSTRING);
               } else {
                  this.javaWriter.invokeStatic(INTEGER_TO_STRING);
               }
               break;
            case USIZE_TO_STRING:
               if (expression.target.type.isOptional()) {
                  Label ifNull = new Label();
                  Label exit = new Label();
                  this.javaWriter.dup();
                  this.javaWriter.constant(-1);
                  this.javaWriter.ifICmpEQ(ifNull);
                  this.javaWriter.invokeStatic(INTEGER_TO_STRING);
                  this.javaWriter.goTo(exit);
                  this.javaWriter.label(ifNull);
                  this.javaWriter.pop();
                  this.javaWriter.constant("null");
                  this.javaWriter.label(exit);
               } else {
                  this.javaWriter.invokeStatic(INTEGER_TO_STRING);
               }
               break;
            case UINT_TO_SBYTE:
               this.javaWriter.i2b();
               break;
            case UINT_TO_SHORT:
               this.javaWriter.i2s();
               break;
            case UINT_TO_LONG:
               this.javaWriter.i2l();
               break;
            case UINT_TO_ULONG:
               this.javaWriter.i2l();
               this.javaWriter.constant(4294967295L);
               this.javaWriter.lAnd();
               break;
            case UINT_TO_FLOAT:
               this.javaWriter.i2l();
               this.javaWriter.constant(4294967295L);
               this.javaWriter.lAnd();
               this.javaWriter.l2f();
               break;
            case UINT_TO_DOUBLE:
               this.javaWriter.i2l();
               this.javaWriter.constant(4294967295L);
               this.javaWriter.lAnd();
               this.javaWriter.l2d();
               break;
            case UINT_TO_CHAR:
               this.javaWriter.i2s();
               break;
            case UINT_TO_STRING:
               if (expression.target.type.isOptional()) {
                  this.javaWriter.invokeStatic(OBJECTS_TOSTRING);
               } else {
                  this.javaWriter.invokeStatic(INTEGER_TO_UNSIGNED_STRING);
               }
               break;
            case LONG_TO_BYTE:
               this.javaWriter.l2i();
               break;
            case LONG_TO_SBYTE:
               this.javaWriter.l2i();
               this.javaWriter.i2b();
               break;
            case LONG_TO_SHORT:
               this.javaWriter.l2i();
               this.javaWriter.i2s();
               break;
            case LONG_TO_USHORT:
            case LONG_TO_INT:
            case LONG_TO_UINT:
            case LONG_TO_USIZE:
               this.javaWriter.l2i();
               break;
            case LONG_TO_FLOAT:
               this.javaWriter.l2f();
               break;
            case LONG_TO_DOUBLE:
               this.javaWriter.l2d();
               break;
            case LONG_TO_CHAR:
               this.javaWriter.l2i();
               this.javaWriter.i2s();
               break;
            case LONG_TO_STRING:
               if (expression.target.type.isOptional()) {
                  this.javaWriter.invokeStatic(OBJECTS_TOSTRING);
               } else {
                  this.javaWriter.invokeStatic(LONG_TO_STRING);
               }
               break;
            case ULONG_TO_BYTE:
               this.javaWriter.l2i();
               break;
            case ULONG_TO_SBYTE:
               this.javaWriter.l2i();
               this.javaWriter.i2b();
               break;
            case ULONG_TO_SHORT:
               this.javaWriter.l2i();
               this.javaWriter.i2s();
               break;
            case ULONG_TO_USHORT:
            case ULONG_TO_INT:
            case ULONG_TO_UINT:
            case ULONG_TO_USIZE:
               this.javaWriter.l2i();
               break;
            case ULONG_TO_FLOAT:
               this.javaWriter.l2f();
               break;
            case ULONG_TO_DOUBLE:
               this.javaWriter.l2d();
            case ULONG_TO_CHAR:
               this.javaWriter.l2i();
               this.javaWriter.i2s();
               break;
            case ULONG_TO_STRING:
               if (expression.target.type.isOptional()) {
                  this.javaWriter.invokeStatic(OBJECTS_TOSTRING);
               } else {
                  this.javaWriter.invokeStatic(LONG_TO_UNSIGNED_STRING);
               }
               break;
            case FLOAT_TO_BYTE:
               this.javaWriter.f2i();
               break;
            case FLOAT_TO_SBYTE:
               this.javaWriter.f2i();
               this.javaWriter.i2b();
               break;
            case FLOAT_TO_SHORT:
               this.javaWriter.f2i();
               this.javaWriter.i2s();
               break;
            case FLOAT_TO_USHORT:
            case FLOAT_TO_UINT:
            case FLOAT_TO_INT:
            case FLOAT_TO_USIZE:
               this.javaWriter.f2i();
               break;
            case FLOAT_TO_LONG:
            case FLOAT_TO_ULONG:
               this.javaWriter.f2l();
               break;
            case FLOAT_TO_DOUBLE:
               this.javaWriter.f2d();
               break;
            case FLOAT_TO_STRING:
               if (expression.target.type.isOptional()) {
                  this.javaWriter.invokeStatic(OBJECTS_TOSTRING);
               } else {
                  this.javaWriter.invokeStatic(FLOAT_TO_STRING);
               }
               break;
            case DOUBLE_TO_BYTE:
               this.javaWriter.d2i();
               break;
            case DOUBLE_TO_SBYTE:
               this.javaWriter.d2i();
               this.javaWriter.i2b();
               break;
            case DOUBLE_TO_SHORT:
               this.javaWriter.d2i();
               this.javaWriter.i2s();
               break;
            case DOUBLE_TO_USHORT:
            case DOUBLE_TO_INT:
            case DOUBLE_TO_UINT:
            case DOUBLE_TO_USIZE:
               this.javaWriter.d2i();
               break;
            case DOUBLE_TO_LONG:
            case DOUBLE_TO_ULONG:
               this.javaWriter.d2l();
               break;
            case DOUBLE_TO_FLOAT:
               this.javaWriter.d2f();
               break;
            case DOUBLE_TO_STRING:
               if (expression.target.type.isOptional()) {
                  this.javaWriter.invokeStatic(OBJECTS_TOSTRING);
               } else {
                  this.javaWriter.invokeStatic(DOUBLE_TO_STRING);
               }
               break;
            case CHAR_TO_SBYTE:
               this.javaWriter.i2s();
               break;
            case CHAR_TO_LONG:
            case CHAR_TO_ULONG:
               this.javaWriter.i2l();
               break;
            case CHAR_TO_STRING:
               if (expression.target.type.isOptional()) {
                  this.javaWriter.invokeStatic(OBJECTS_TOSTRING);
               } else {
                  this.javaWriter.invokeStatic(CHARACTER_TO_STRING);
               }
               break;
            case ENUM_TO_STRING:
               this.javaWriter.invokeVirtual(ENUM_NAME);
               break;
            default:
               throw new UnsupportedOperationException("Unknown builtin cast: " + builtin);
         }

         return null;
      }
   }

   public Void visitCheckNull(CheckNullExpression expression) {
      Label end = new Label();
      expression.value.accept(this);
      this.javaWriter.dup();
      this.javaWriter.ifNonNull(end);
      this.javaWriter.pop();
      this.javaWriter.newObject("java/lang/NullPointerException");
      this.javaWriter.dup();
      this.javaWriter.constant("Tried to convert a null value to nonnull type " + this.context.getType(expression.type).getClassName());
      this.javaWriter.invokeSpecial(NullPointerException.class, "<init>", "(Ljava/lang/String;)V");
      this.javaWriter.aThrow();
      this.javaWriter.label(end);
      return null;
   }

   public Void visitCoalesce(CoalesceExpression expression) {
      Label end = new Label();
      expression.left.accept(this);
      this.javaWriter.dup();
      this.javaWriter.ifNonNull(end);
      this.javaWriter.pop();
      expression.right.accept(this);
      expression.right.type.accept(expression.right.type, this.boxingTypeVisitor);
      this.javaWriter.label(end);
      expression.type.accept(expression.type, this.unboxingTypeVisitor);
      return null;
   }

   public Void visitConditional(ConditionalExpression expression) {
      Label end = new Label();
      Label onElse = new Label();
      expression.condition.accept(this);
      this.javaWriter.ifEQ(onElse);
      expression.ifThen.accept(this);
      this.javaWriter.goTo(end);
      this.javaWriter.label(onElse);
      expression.ifElse.accept(this);
      this.javaWriter.label(end);
      return null;
   }

   public Void visitConst(ConstExpression expression) {
      BuiltinID builtin = expression.constant.member.builtin;
      if (builtin == null) {
         this.javaWriter.getStaticField(this.context.getJavaField(expression.constant));
         return null;
      }

      switch (builtin) {
         case BYTE_GET_MIN_VALUE:
            this.javaWriter.iConst0();
            break;
         case BYTE_GET_MAX_VALUE:
            this.javaWriter.constant(255);
            break;
         case SBYTE_GET_MIN_VALUE:
            this.javaWriter.getStaticField(BYTE_MIN_VALUE);
            break;
         case SBYTE_GET_MAX_VALUE:
            this.javaWriter.getStaticField(BYTE_MAX_VALUE);
            break;
         case SHORT_GET_MIN_VALUE:
            this.javaWriter.getStaticField(SHORT_MIN_VALUE);
            break;
         case SHORT_GET_MAX_VALUE:
            this.javaWriter.getStaticField(SHORT_MAX_VALUE);
            break;
         case USHORT_GET_MIN_VALUE:
            this.javaWriter.iConst0();
            break;
         case USHORT_GET_MAX_VALUE:
            this.javaWriter.constant(65535);
            break;
         case INT_GET_MIN_VALUE:
            this.javaWriter.getStaticField(INTEGER_MIN_VALUE);
            break;
         case INT_GET_MAX_VALUE:
            this.javaWriter.getStaticField(INTEGER_MAX_VALUE);
            break;
         case UINT_GET_MIN_VALUE:
            this.javaWriter.iConst0();
            break;
         case UINT_GET_MAX_VALUE:
            this.javaWriter.constant(-1);
            break;
         case LONG_GET_MIN_VALUE:
            this.javaWriter.getStaticField(LONG_MIN_VALUE);
            break;
         case LONG_GET_MAX_VALUE:
            this.javaWriter.getStaticField(LONG_MAX_VALUE);
            break;
         case ULONG_GET_MIN_VALUE:
            this.javaWriter.iConst0();
            break;
         case ULONG_GET_MAX_VALUE:
            this.javaWriter.constant(-1L);
            break;
         case USIZE_GET_MIN_VALUE:
            this.javaWriter.iConst0();
            break;
         case USIZE_GET_MAX_VALUE:
            this.javaWriter.getStaticField(INTEGER_MAX_VALUE);
            break;
         case USIZE_BITS:
            this.javaWriter.constant(32);
            break;
         case FLOAT_GET_MIN_VALUE:
            this.javaWriter.getStaticField(FLOAT_MIN_VALUE);
            break;
         case FLOAT_GET_MAX_VALUE:
            this.javaWriter.getStaticField(FLOAT_MAX_VALUE);
            break;
         case DOUBLE_GET_MIN_VALUE:
            this.javaWriter.getStaticField(DOUBLE_MIN_VALUE);
            break;
         case DOUBLE_GET_MAX_VALUE:
            this.javaWriter.getStaticField(DOUBLE_MAX_VALUE);
            break;
         case CHAR_GET_MIN_VALUE:
            this.javaWriter.getStaticField(CHARACTER_MIN_VALUE);
            break;
         case CHAR_GET_MAX_VALUE:
            this.javaWriter.getStaticField(CHARACTER_MAX_VALUE);
            break;
         case ENUM_VALUES:
            DefinitionTypeID type = (DefinitionTypeID)((ArrayTypeID)expression.type).elementType;
            JavaClass cls = this.context.getJavaClass(type.definition);
            this.javaWriter.invokeStatic(JavaMethod.getNativeStatic(cls, "values", "()[L" + cls.internalName + ";"));
            break;
         default:
            throw new UnsupportedOperationException("Unknown builtin: " + builtin);
      }

      return null;
   }

   public Void visitConstantBool(ConstantBoolExpression expression) {
      if (expression.value) {
         this.javaWriter.iConst1();
      } else {
         this.javaWriter.iConst0();
      }

      return null;
   }

   public Void visitConstantByte(ConstantByteExpression expression) {
      this.getJavaWriter().constant(expression.value);
      return null;
   }

   public Void visitConstantChar(ConstantCharExpression expression) {
      this.getJavaWriter().constant(expression.value);
      return null;
   }

   public Void visitConstantDouble(ConstantDoubleExpression expression) {
      this.getJavaWriter().constant(expression.value);
      return null;
   }

   public Void visitConstantFloat(ConstantFloatExpression expression) {
      this.getJavaWriter().constant(expression.value);
      return null;
   }

   public Void visitConstantInt(ConstantIntExpression expression) {
      this.getJavaWriter().constant(expression.value);
      return null;
   }

   public Void visitConstantLong(ConstantLongExpression expression) {
      this.getJavaWriter().constant(expression.value);
      return null;
   }

   public Void visitConstantSByte(ConstantSByteExpression expression) {
      this.getJavaWriter().constant(expression.value);
      return null;
   }

   public Void visitConstantShort(ConstantShortExpression expression) {
      this.getJavaWriter().siPush(expression.value);
      return null;
   }

   public Void visitConstantString(ConstantStringExpression expression) {
      this.getJavaWriter().constant(expression.value);
      return null;
   }

   public Void visitConstantUInt(ConstantUIntExpression expression) {
      this.getJavaWriter().constant(expression.value);
      return null;
   }

   public Void visitConstantULong(ConstantULongExpression expression) {
      this.getJavaWriter().constant(expression.value);
      return null;
   }

   public Void visitConstantUShort(ConstantUShortExpression expression) {
      this.getJavaWriter().constant(expression.value);
      return null;
   }

   public Void visitConstantUSize(ConstantUSizeExpression expression) {
      this.getJavaWriter().constant((int)expression.value);
      return null;
   }

   public Void visitConstructorThisCall(ConstructorThisCallExpression expression) {
      throw new UnsupportedOperationException("Invalid usage");
   }

   public Void visitConstructorSuperCall(ConstructorSuperCallExpression expression) {
      throw new UnsupportedOperationException("Invalid usage");
   }

   public Void visitEnumConstant(EnumConstantExpression expression) {
      this.javaWriter
         .getStaticField(
            this.context.getInternalName(expression.type),
            this.module.getEnumMapper().getMapping(expression.value).orElseGet(() -> expression.value.name),
            this.context.getDescriptor(expression.type)
         );
      return null;
   }

   public Void visitFunction(final FunctionExpression expression) {
      CompilerUtils.tagMethodParameters(this.context, this.module, expression.header, false, Collections.emptyList());
      String signature;
      String[] interfaces;
      String descriptor;
      if (expression.type instanceof JavaFunctionalInterfaceTypeID type) {
         Method functionalInterfaceMethod = type.functionalInterfaceMethod;
         signature = this.context.getMethodSignature(expression.header, true);
         descriptor = this.context.getMethodDescriptor(expression.header);
         interfaces = new String[]{Type.getInternalName(functionalInterfaceMethod.getDeclaringClass())};
      } else {
         signature = this.context.getMethodSignature(expression.header, true);
         descriptor = this.context.getMethodDescriptor(expression.header);
         interfaces = new String[]{this.context.getInternalName(new FunctionTypeID(null, expression.header))};
      }

      final String className = interfaces[0].replace("java", "j").replace("/", "_") + "_" + this.context.getLambdaCounter();
      JavaMethod m = this.context.getFunctionalInterface(expression.type);
      JavaMethod methodInfo = new JavaMethod(m.cls, m.kind, m.name, m.compile, m.descriptor, m.modifiers & -1025, m.genericResult, m.typeParameterArguments);
      ClassWriter lambdaCW = new JavaClassWriter(2);
      lambdaCW.visit(52, 1, className, null, "java/lang/Object", interfaces);
      final JavaWriter functionWriter;
      if (!Objects.equals(methodInfo.descriptor, descriptor)) {
         JavaMethod bridgeMethodInfo = new JavaMethod(
            methodInfo.cls,
            methodInfo.kind,
            methodInfo.name,
            methodInfo.compile,
            methodInfo.descriptor,
            methodInfo.modifiers | 64 | 4096,
            methodInfo.genericResult,
            methodInfo.typeParameterArguments
         );
         JavaWriter bridgeWriter = new JavaWriter(
            this.context.logger, expression.position, lambdaCW, bridgeMethodInfo, null, methodInfo.descriptor, null, "Ljava/lang/Override;"
         );
         bridgeWriter.start();
         bridgeWriter.loadObject(0);

         for (int i = 0; i < expression.header.parameters.length; i++) {
            FunctionParameter functionParameter = expression.header.parameters[i];
            Type type = this.context.getType(functionParameter.type);
            bridgeWriter.load(type, i + 1);
            if (!CompilerUtils.isPrimitive(functionParameter.type)) {
               bridgeWriter.checkCast(type);
            }
         }

         bridgeWriter.invokeVirtual(
            new JavaMethod(
               JavaClass.fromInternalName(className, JavaClass.Kind.CLASS),
               JavaMethod.Kind.INSTANCE,
               methodInfo.name,
               methodInfo.compile,
               descriptor,
               methodInfo.modifiers,
               methodInfo.genericResult
            )
         );
         TypeID returnType = expression.header.getReturnType();
         if (returnType != BasicTypeID.VOID) {
            Type returnTypeASM = this.context.getType(returnType);
            if (!CompilerUtils.isPrimitive(returnType)) {
               bridgeWriter.checkCast(returnTypeASM);
            }

            bridgeWriter.returnType(returnTypeASM);
         }

         bridgeWriter.ret();
         bridgeWriter.end();
         JavaMethod actualMethod = new JavaMethod(
            methodInfo.cls,
            methodInfo.kind,
            methodInfo.name,
            methodInfo.compile,
            this.context.getMethodDescriptor(expression.header),
            methodInfo.modifiers,
            methodInfo.genericResult,
            methodInfo.typeParameterArguments
         );
         functionWriter = new JavaWriter(this.context.logger, expression.position, lambdaCW, actualMethod, null, signature, null);
      } else {
         functionWriter = new JavaWriter(this.context.logger, expression.position, lambdaCW, methodInfo, null, signature, null, "Ljava/lang/Override;");
      }

      functionWriter.clazzVisitor.visitSource(expression.position.getFilename(), null);
      this.javaWriter.newObject(className);
      this.javaWriter.dup();
      String constructorDesc = this.calcFunctionSignature(expression.closure);
      JavaWriter constructorWriter = new JavaWriter(
         this.context.logger, expression.position, lambdaCW, JavaMethod.getConstructor(this.javaWriter.method.cls, constructorDesc, 1), null, null, null
      );
      constructorWriter.start();
      constructorWriter.loadObject(0);
      constructorWriter.dup();
      constructorWriter.invokeSpecial(Object.class, "<init>", "()V");
      int i = 0;

      for (CapturedExpression capture : expression.closure.captures) {
         constructorWriter.dup();
         Type type = this.context.getType(capture.type);
         lambdaCW.visitField(18, "captured" + ++i, type.getDescriptor(), null, null).visitEnd();
         capture.accept(this);
         constructorWriter.load(type, i);
         constructorWriter.putField(className, "captured" + i, type.getDescriptor());
      }

      constructorWriter.pop();
      this.javaWriter.invokeSpecial(className, "<init>", constructorDesc);
      constructorWriter.ret();
      constructorWriter.end();
      functionWriter.start();
      JavaStatementVisitor CSV = new JavaStatementVisitor(this.context, new JavaExpressionVisitor(this.context, this.module, functionWriter) {
         @Override
         public Void visitGetLocalVariable(GetLocalVariableExpression varExpression) {
            JavaLocalVariableInfo localVariable = functionWriter.tryGetLocalVariable(varExpression.variable.variable);
            if (localVariable != null) {
               Label label = new Label();
               localVariable.end = label;
               functionWriter.label(label);
               functionWriter.load(localVariable);
               return null;
            } else {
               int position = JavaExpressionVisitor.calculateMemberPosition(varExpression, expression);
               functionWriter.loadObject(0);
               functionWriter.getField(className, "captured" + position, this.context.getDescriptor(varExpression.variable.type));
               return null;
            }
         }

         @Override
         public Void visitCapturedParameter(CapturedParameterExpression varExpression) {
            int position = JavaExpressionVisitor.calculateMemberPosition(varExpression, expression);
            functionWriter.loadObject(0);
            functionWriter.getField(className, "captured" + position, this.context.getDescriptor(varExpression.parameter.type));
            return null;
         }
      });
      expression.body.accept(CSV);
      functionWriter.ret();
      functionWriter.end();
      lambdaCW.visitEnd();
      this.context.register(className, lambdaCW.toByteArray());
      return null;
   }

   private String calcFunctionSignature(LambdaClosure closure) {
      StringJoiner joiner = new StringJoiner("", "(", ")V");

      for (CapturedExpression capture : closure.captures) {
         String descriptor = this.context.getDescriptor(capture.type);
         joiner.add(descriptor);
      }

      return joiner.toString();
   }

   public Void visitGetField(GetFieldExpression expression) {
      expression.target.accept(this);
      this.getField(expression.field);
      this.handleReturnValue(expression.field.member.getType(), expression.field.getType());
      return null;
   }

   public Void visitGetFunctionParameter(GetFunctionParameterExpression expression) {
      JavaParameterInfo parameter = this.module.getParameterInfo(expression.parameter);
      if (parameter == null) {
         throw new RuntimeException(expression.position.toString() + ": Could not resolve lambda parameter" + expression.parameter);
      }

      this.javaWriter.load(this.context.getType(expression.parameter.type), parameter.index);
      return null;
   }

   public Void visitGetLocalVariable(GetLocalVariableExpression expression) {
      Label label = new Label();
      JavaLocalVariableInfo tag = this.javaWriter.getLocalVariable(expression.variable.variable);
      tag.end = label;
      this.javaWriter.load(tag.type, tag.local);
      this.javaWriter.label(label);
      return null;
   }

   public Void visitGetMatchingVariantField(GetMatchingVariantField expression) {
      this.javaWriter.loadObject(0);
      TypeID type = expression.value.option.getParameterType(expression.index);
      JavaVariantOption tag = this.context.getJavaVariantOption(expression.value.option);
      this.javaWriter.checkCast(tag.variantOptionClass.internalName);
      this.javaWriter.getField(new JavaField(tag.variantOptionClass, "field" + expression.index, this.context.getDescriptor(type)));
      return null;
   }

   public Void visitGetStaticField(GetStaticFieldExpression expression) {
      this.javaWriter.getStaticField(this.context.getJavaField(expression.field));
      return null;
   }

   public Void visitGetter(GetterExpression expression) {
      expression.target.accept(this);
      BuiltinID builtin = expression.getter.member.builtin;
      if (builtin == null) {
         if (this.context.hasJavaField(expression.getter)) {
            this.javaWriter.getField(this.context.getJavaField(expression.getter));
            this.handleReturnValue(expression.getter.member.getType(), expression.getter.getType());
            return null;
         }

         List<TypeParameter> typeParameters = new ArrayList<>();
         expression.getter.member.getType().extractTypeParameters(typeParameters);
         if (expression.getter.member.definition.isExpansion()) {
            for (TypeParameter typeParameter : typeParameters) {
               this.javaWriter.aConstNull();
               this.javaWriter.checkCast("java/lang/Class");
            }
         }

         if (!this.checkAndExecuteMethodInfo(expression.getter, expression.type, expression)) {
            throw new IllegalStateException("Call target has no method info!");
         }

         this.handleReturnValue(expression.getter.member.getType(), expression.getter.getType());
         return null;
      } else {
         switch (builtin) {
            case INT_HIGHEST_ONE_BIT:
            case UINT_HIGHEST_ONE_BIT:
            case USIZE_HIGHEST_ONE_BIT:
               this.javaWriter.invokeStatic(INTEGER_HIGHEST_ONE_BIT);
               break;
            case INT_LOWEST_ONE_BIT:
            case UINT_LOWEST_ONE_BIT:
            case USIZE_LOWEST_ONE_BIT:
               this.javaWriter.invokeStatic(INTEGER_LOWEST_ONE_BIT);
               break;
            case INT_HIGHEST_ZERO_BIT:
            case UINT_HIGHEST_ZERO_BIT:
            case USIZE_HIGHEST_ZERO_BIT:
               this.javaWriter.iNeg();
               this.javaWriter.invokeStatic(INTEGER_HIGHEST_ONE_BIT);
               break;
            case INT_LOWEST_ZERO_BIT:
            case UINT_LOWEST_ZERO_BIT:
            case USIZE_LOWEST_ZERO_BIT:
               this.javaWriter.iNeg();
               this.javaWriter.invokeStatic(INTEGER_LOWEST_ONE_BIT);
               break;
            case INT_BIT_COUNT:
            case UINT_BIT_COUNT:
            case USIZE_BIT_COUNT:
               this.javaWriter.invokeStatic(INTEGER_BIT_COUNT);
               break;
            case LONG_HIGHEST_ONE_BIT:
            case ULONG_HIGHEST_ONE_BIT:
               this.javaWriter.invokeStatic(LONG_HIGHEST_ONE_BIT);
               break;
            case LONG_LOWEST_ONE_BIT:
            case ULONG_LOWEST_ONE_BIT:
               this.javaWriter.invokeStatic(LONG_LOWEST_ONE_BIT);
               break;
            case LONG_HIGHEST_ZERO_BIT:
            case ULONG_HIGHEST_ZERO_BIT:
               this.javaWriter.lNeg();
               this.javaWriter.invokeStatic(LONG_HIGHEST_ONE_BIT);
               break;
            case LONG_LOWEST_ZERO_BIT:
            case ULONG_LOWEST_ZERO_BIT:
               this.javaWriter.lNeg();
               this.javaWriter.invokeStatic(LONG_LOWEST_ONE_BIT);
               break;
            case LONG_BIT_COUNT:
            case ULONG_BIT_COUNT:
               this.javaWriter.invokeStatic(LONG_BIT_COUNT);
               break;
            case FLOAT_BITS:
               this.javaWriter.invokeStatic(FLOAT_BITS);
               break;
            case DOUBLE_BITS:
               this.javaWriter.invokeStatic(DOUBLE_BITS);
               break;
            case STRING_LENGTH:
               this.javaWriter.invokeVirtual(STRING_LENGTH);
               break;
            case STRING_CHARACTERS:
               this.javaWriter.invokeVirtual(STRING_CHARACTERS);
               break;
            case STRING_ISEMPTY:
               this.javaWriter.invokeVirtual(STRING_ISEMPTY);
               break;
            case ASSOC_SIZE:
               this.javaWriter.invokeVirtual(MAP_SIZE);
               break;
            case ASSOC_ISEMPTY:
               this.javaWriter.invokeVirtual(MAP_ISEMPTY);
               break;
            case ASSOC_KEYS: {
               Type resultType = this.context.getType(expression.type);
               this.javaWriter.invokeVirtual(MAP_KEYS);
               this.javaWriter.dup();
               this.javaWriter.invokeVirtual(COLLECTION_SIZE);
               this.javaWriter.newArray(resultType);
               this.javaWriter.invokeVirtual(COLLECTION_TOARRAY);
               this.javaWriter.checkCast(resultType);
               break;
            }
            case ASSOC_VALUES: {
               Type resultType = this.context.getType(expression.type);
               this.javaWriter.invokeVirtual(MAP_VALUES);
               this.javaWriter.dup();
               this.javaWriter.invokeVirtual(COLLECTION_SIZE);
               this.javaWriter.newArray(resultType);
               this.javaWriter.invokeVirtual(COLLECTION_TOARRAY);
               this.javaWriter.checkCast(resultType);
               break;
            }
            case ASSOC_HASHCODE:
               this.javaWriter.invokeVirtual(OBJECT_HASHCODE);
               break;
            case GENERICMAP_HASHCODE:
               this.javaWriter.invokeVirtual(OBJECT_HASHCODE);
               break;
            case ARRAY_LENGTH:
               this.javaWriter.arrayLength();
               break;
            case ARRAY_HASHCODE: {
               ArrayTypeID type = (ArrayTypeID)expression.target.type;
               if (type.elementType instanceof BasicTypeID) {
                  switch ((BasicTypeID)type.elementType) {
                     case BOOL:
                        this.javaWriter.invokeStatic(ARRAYS_HASHCODE_BOOLS);
                        return null;
                     case BYTE:
                     case SBYTE:
                        this.javaWriter.invokeStatic(ARRAYS_HASHCODE_BYTES);
                        return null;
                     case SHORT:
                     case USHORT:
                        this.javaWriter.invokeStatic(ARRAYS_HASHCODE_SHORTS);
                        return null;
                     case INT:
                     case UINT:
                        this.javaWriter.invokeStatic(ARRAYS_HASHCODE_INTS);
                        return null;
                     case LONG:
                     case ULONG:
                        this.javaWriter.invokeStatic(ARRAYS_HASHCODE_LONGS);
                        return null;
                     case FLOAT:
                        this.javaWriter.invokeStatic(ARRAYS_HASHCODE_FLOATS);
                        return null;
                     case DOUBLE:
                        this.javaWriter.invokeStatic(ARRAYS_HASHCODE_DOUBLES);
                        return null;
                     case CHAR:
                        this.javaWriter.invokeStatic(ARRAYS_HASHCODE_CHARS);
                        return null;
                     default:
                        throw new IllegalArgumentException("Unknown basic type: " + type.elementType);
                  }
               } else {
                  this.javaWriter.invokeStatic(ARRAYS_DEEPHASHCODE);
                  break;
               }
            }
            case ARRAY_ISEMPTY:
               Label isTrue = new Label();
               Label exit = new Label();
               this.javaWriter.arrayLength();
               this.javaWriter.ifEQ(isTrue);
               this.javaWriter.iConst0();
               this.javaWriter.goTo(exit);
               this.javaWriter.label(isTrue);
               this.javaWriter.iConst1();
               this.javaWriter.label(exit);
               break;
            case ENUM_NAME:
               this.javaWriter.invokeVirtual(ENUM_NAME);
               break;
            case ENUM_ORDINAL:
               this.javaWriter.invokeVirtual(ENUM_ORDINAL);
               break;
            case OBJECT_HASHCODE:
               this.javaWriter.invokeVirtual(OBJECT_HASHCODE);
               break;
            case RANGE_FROM: {
               RangeTypeID type = (RangeTypeID)expression.target.type;
               Type jType = this.context.getType(expression.target.type);
               this.javaWriter.getField(jType.getInternalName(), "from", this.context.getDescriptor(type.baseType));
               break;
            }
            case RANGE_TO: {
               RangeTypeID type = (RangeTypeID)expression.target.type;
               Type jType = this.context.getType(expression.target.type);
               this.javaWriter.getField(jType.getInternalName(), "to", this.context.getDescriptor(type.baseType));
            }
         }

         return null;
      }
   }

   public Void visitGlobal(GlobalExpression expression) {
      return expression.resolution.accept(this);
   }

   public Void visitGlobalCall(GlobalCallExpression expression) {
      return expression.resolution.accept(this);
   }

   public Void visitInterfaceCast(InterfaceCastExpression expression) {
      expression.value.accept(this);
      this.javaWriter.checkCast(this.context.getInternalName(expression.type));
      return null;
   }

   public Void visitIs(IsExpression expression) {
      expression.value.accept(this);
      this.javaWriter.instanceOf(this.context.getInternalName(expression.isType));
      return null;
   }

   public Void visitMakeConst(MakeConstExpression expression) {
      return null;
   }

   public Void visitMap(MapExpression expression) {
      this.javaWriter.newObject("java/util/HashMap");
      this.javaWriter.dup();
      this.javaWriter.invokeSpecial("java/util/HashMap", "<init>", "()V");
      AssocTypeID type = (AssocTypeID)expression.type;

      for (int i = 0; i < expression.keys.length; i++) {
         this.javaWriter.dup();
         expression.keys[i].accept(this);
         type.keyType.accept(type.keyType, this.boxingTypeVisitor);
         expression.values[i].accept(this);
         type.valueType.accept(type.valueType, this.boxingTypeVisitor);
         this.javaWriter.invokeInterface(MAP_PUT);
         this.javaWriter.pop();
      }

      return null;
   }

   public Void visitMatch(MatchExpression expression) {
      Label start = new Label();
      Label end = new Label();
      this.javaWriter.label(start);
      expression.value.accept(this);
      if (expression.value.type == BasicTypeID.STRING) {
         this.javaWriter.invokeVirtual(OBJECT_HASHCODE);
      }

      for (MatchExpression.Case aCase : expression.cases) {
         if (aCase.key instanceof VariantOptionSwitchValue variantOptionSwitchValue) {
            JavaVariantOption option = this.context.getJavaVariantOption(variantOptionSwitchValue.option);
            this.javaWriter.invokeVirtual(JavaMethod.getNativeVirtual(option.variantClass, "getDenominator", "()I"));
            break;
         }
      }

      boolean hasNoDefault = hasNoDefault(expression);
      MatchExpression.Case[] cases = expression.cases;
      JavaSwitchLabel[] switchLabels = new JavaSwitchLabel[hasNoDefault ? cases.length : cases.length - 1];
      Label defaultLabel = new Label();
      int i = 0;

      for (MatchExpression.Case matchCase : cases) {
         if (matchCase.key != null) {
            switchLabels[i++] = new JavaSwitchLabel(CompilerUtils.getKeyForSwitch(matchCase.key), new Label());
         }
      }

      JavaSwitchLabel[] sortedSwitchLabels = Arrays.copyOf(switchLabels, switchLabels.length);
      Arrays.sort(sortedSwitchLabels, Comparator.comparingInt(a -> a.key));
      this.javaWriter.lookupSwitch(defaultLabel, sortedSwitchLabels);
      i = 0;

      for (MatchExpression.Case switchCase : cases) {
         if (!hasNoDefault && switchCase.key == null) {
            this.javaWriter.label(defaultLabel);
         } else {
            this.javaWriter.label(switchLabels[i++].label);
         }

         switchCase.value.accept(this);
         this.javaWriter.goTo(end);
      }

      if (hasNoDefault) {
         this.javaWriter.label(defaultLabel);
         if (this.context.getType(expression.type).getOpcode(54) == 58) {
            this.javaWriter.aConstNull();
         } else {
            this.javaWriter.iConst0();
         }
      }

      this.javaWriter.label(end);
      if (!CompilerUtils.isPrimitive(expression.type)) {
         this.javaWriter.checkCast(this.context.getType(expression.type));
      }

      return null;
   }

   public Void visitNew(NewExpression expression) {
      if (expression.constructor.getBuiltin() != null) {
         this.visitBuiltinConstructor(expression);
         return null;
      }

      JavaMethod method = this.context.getJavaMethod(expression.constructor);
      this.javaWriter.newObject(method.cls);
      this.javaWriter.dup();
      if (!expression.constructor.getTarget().hasTag(NativeTag.class)) {
         for (TypeID typeArgument : ((DefinitionTypeID)expression.type).typeArguments) {
            this.javaWriter.aConstNull();
            this.javaWriter.checkCast("java/lang/Class");
         }
      }

      this.handleCallArguments(
         expression.arguments.arguments,
         expression.instancedHeader.parameters,
         expression.instancedHeader.isVariadic(),
         expression.constructor.getTarget().getHeader().parameters
      );
      this.javaWriter.invokeSpecial(method);
      return null;
   }

   private void visitBuiltinConstructor(NewExpression expression) {
      BuiltinID builtin = expression.constructor.getBuiltin();
      switch (builtin) {
         case STRING_CONSTRUCTOR_CHARACTERS:
            this.javaWriter.newObject(JavaClass.STRING);
            this.javaWriter.dup();
            expression.arguments.arguments[0].accept(this);
            this.javaWriter.invokeSpecial(STRING_INIT_CHARACTERS);
            return;
         case ASSOC_CONSTRUCTOR:
         case GENERICMAP_CONSTRUCTOR:
            this.javaWriter.newObject(JavaClass.HASHMAP);
            this.javaWriter.dup();
            this.javaWriter.invokeSpecial(HASHMAP_INIT);
            return;
         case ARRAY_CONSTRUCTOR_SIZED:
         case ARRAY_CONSTRUCTOR_INITIAL_VALUE: {
            ArrayTypeID type = (ArrayTypeID)expression.type;
            ArrayHelperType ASMType = new ArrayHelperType(type, this.context);
            Type ASMElementType = this.context.getType(type.elementType);
            Label begin = new Label();
            Label end = new Label();
            this.javaWriter.label(begin);
            int defaultValueLocation = this.javaWriter.local(ASMElementType);
            this.javaWriter.addVariableInfo(new JavaLocalVariableInfo(ASMElementType, defaultValueLocation, begin, "defaultValue", end));
            if (builtin == BuiltinID.ARRAY_CONSTRUCTOR_SIZED) {
               type.elementType.getDefaultValue().accept(this);
            } else {
               expression.arguments.arguments[expression.arguments.arguments.length - 1].accept(this);
            }

            this.javaWriter.store(ASMElementType, defaultValueLocation);
            int[] arraySizes = ArrayInitializerHelper.getArraySizeLocationsFromConstructor(type.dimension, expression.arguments.arguments, this);
            ArrayInitializerHelper.visitMultiDimArrayWithDefaultValue(this.javaWriter, arraySizes, type.dimension, ASMType, defaultValueLocation);
            this.javaWriter.label(end);
            return;
         }
         case ARRAY_CONSTRUCTOR_LAMBDA: {
            Label begin = new Label();
            Label end = new Label();
            this.javaWriter.label(begin);
            ArrayHelperType ASMElementType = new ArrayHelperType(expression.type, this.context);
            int dimension = ((ArrayTypeID)expression.type).dimension;
            int[] arraySizes = ArrayInitializerHelper.getArraySizeLocationsFromConstructor(dimension, expression.arguments.arguments, this);
            ArrayInitializerHelper.visitMultiDimArray(
               this.javaWriter, arraySizes, new int[dimension], dimension, ASMElementType, (elementType, counterLocations) -> {
                  expression.arguments.arguments[dimension].accept(this);

                  for (int counterLocation : counterLocations) {
                     this.javaWriter.loadInt(counterLocation);
                  }

                  this.javaWriter.invokeInterface(this.context.getFunctionalInterface(expression.arguments.arguments[dimension].type));
               }
            );
            this.javaWriter.label(end);
            return;
         }
         case ARRAY_CONSTRUCTOR_PROJECTED:
         case ARRAY_CONSTRUCTOR_PROJECTED_INDEXED: {
            ArrayTypeID type = (ArrayTypeID)expression.type;
            Label begin = new Label();
            Label end = new Label();
            this.javaWriter.label(begin);
            expression.arguments.arguments[0].accept(this);
            Type originArrayType = this.context.getType(expression.arguments.arguments[0].type);
            int originArrayLocation = this.javaWriter.local(originArrayType);
            this.javaWriter.storeObject(originArrayLocation);
            ArrayHelperType destinationArrayType = new ArrayHelperType(type, this.context);
            boolean indexed = builtin == BuiltinID.ARRAY_CONSTRUCTOR_PROJECTED_INDEXED;
            boolean canBeInLined = ArrayInitializerHelper.canBeInLined(expression.arguments.arguments[1]);
            if (canBeInLined) {
               int[] arraySizesx = ArrayInitializerHelper.getArraySizeLocationsProjected(type.dimension, originArrayType, originArrayLocation, this.javaWriter);
               Type projectedElementType = Type.getType(originArrayType.getDescriptor().substring(type.dimension));
               ArrayInitializerHelper.visitProjected(
                  this.javaWriter,
                  arraySizesx,
                  type.dimension,
                  originArrayLocation,
                  originArrayType,
                  destinationArrayType,
                  (elementType, counterLocations) -> {
                     Label inlineBegin = new Label();
                     Label inlineEnd = new Label();
                     this.javaWriter.label(inlineBegin);
                     final int projectedElementLocal = this.javaWriter.local(projectedElementType);
                     this.javaWriter.store(projectedElementType, projectedElementLocal);
                     JavaExpressionVisitor visitor = new JavaExpressionVisitor(this.context, this.module, this.javaWriter) {
                        @Override
                        public Void visitGetFunctionParameter(GetFunctionParameterExpression expression) {
                           if (indexed) {
                              JavaParameterInfo parameterInfo = this.module.getParameterInfo(expression.parameter);
                              if (parameterInfo != null && parameterInfo.index <= type.dimension) {
                                 this.javaWriter.loadInt(counterLocations[parameterInfo.index - 1]);
                                 return null;
                              }
                           }

                           this.javaWriter.load(projectedElementType, projectedElementLocal);
                           return null;
                        }
                     };
                     Expression funcExpression = expression.arguments.arguments[1];
                     if (funcExpression instanceof FunctionExpression && ((FunctionExpression)funcExpression).body instanceof ReturnStatement) {
                        CompilerUtils.tagMethodParameters(
                           this.context, this.module, ((FunctionExpression)funcExpression).header, false, Collections.emptyList()
                        );
                        ((ReturnStatement)((FunctionExpression)funcExpression).body).value.accept(visitor);
                        this.javaWriter
                           .addVariableInfo(
                              new JavaLocalVariableInfo(
                                 projectedElementType,
                                 projectedElementLocal,
                                 inlineBegin,
                                 ((FunctionExpression)funcExpression).header.parameters[0].name,
                                 inlineEnd
                              )
                           );
                        this.javaWriter.label(inlineEnd);
                     } else {
                        throw new IllegalStateException("Trying to inline a non-inlineable expression");
                     }
                  }
               );
            } else {
               expression.arguments.arguments[1].accept(this);
               Type functionType = this.context.getType(expression.arguments.arguments[1].type);
               int functionLocation = this.javaWriter.local(functionType);
               this.javaWriter.storeObject(functionLocation);
               this.javaWriter.addVariableInfo(new JavaLocalVariableInfo(functionType, functionLocation, begin, "projectionFunction", end));
               int[] arraySizesx = ArrayInitializerHelper.getArraySizeLocationsProjected(type.dimension, originArrayType, originArrayLocation, this.javaWriter);
               ArrayInitializerHelper.visitProjected(
                  this.javaWriter,
                  arraySizesx,
                  type.dimension,
                  originArrayLocation,
                  originArrayType,
                  destinationArrayType,
                  (elementType, counterLocations) -> {
                     this.javaWriter.loadObject(functionLocation);
                     this.javaWriter.swap();
                     if (indexed) {
                        for (int counterLocation : counterLocations) {
                           this.javaWriter.loadInt(counterLocation);
                           this.javaWriter.swap();
                        }
                     }

                     this.javaWriter.invokeInterface(this.context.getFunctionalInterface(expression.arguments.arguments[1].type));
                  }
               );
            }

            this.javaWriter.label(end);
            return;
         }
         case CLASS_DEFAULT_CONSTRUCTOR:
            this.javaWriter.newObject(this.context.getInternalName(expression.type));
            this.javaWriter.dup();
            this.javaWriter.invokeSpecial(this.context.getJavaMethod(expression.constructor));
            return;
         default:
            throw new UnsupportedOperationException("Unknown builtin constructor: " + builtin);
      }
   }

   public Void visitNull(NullExpression expression) {
      if (expression.type != BasicTypeID.NULL && expression.type.withoutOptional() == BasicTypeID.USIZE) {
         this.javaWriter.constant(-1);
      } else {
         this.javaWriter.aConstNull();
      }

      return null;
   }

   public Void visitOrOr(OrOrExpression expression) {
      Label end = new Label();
      Label onTrue = new Label();
      expression.left.accept(this);
      this.javaWriter.ifNE(onTrue);
      expression.right.accept(this);
      this.javaWriter.goTo(end);
      this.javaWriter.label(onTrue);
      this.javaWriter.iConst1();
      this.javaWriter.label(end);
      return null;
   }

   public Void visitPanic(PanicExpression expression) {
      this.javaWriter.newObject("java/lang/AssertionError");
      this.javaWriter.dup();
      expression.value.accept(this);
      this.javaWriter.invokeSpecial(AssertionError.class, "<init>", "(Ljava/lang/Object;)V");
      this.javaWriter.aThrow();
      return null;
   }

   private void modify(Expression source, Runnable modification, JavaModificationExpressionVisitor.PushOption push) {
      source.accept(new JavaModificationExpressionVisitor(this.context, this.module, this.javaWriter, this, modification, push));
   }

   public Void visitPlatformSpecific(Expression expression) {
      if (expression instanceof JavaFunctionInterfaceCastExpression jficExpression) {
         if (jficExpression.value.type instanceof JavaFunctionalInterfaceTypeID) {
            jficExpression.value.accept(this);
         } else {
            this.visitFunctionalInterfaceWrapping(jficExpression);
         }

         return null;
      } else {
         throw new AssertionError("Unrecognized platform expression: " + expression);
      }
   }

   public Void visitPostCall(PostCallExpression expression) {
      if (expression.member.getBuiltin() != null) {
         switch (expression.member.getBuiltin()) {
            case BYTE_INC:
               this.modify(expression.target, () -> {
                  this.javaWriter.iConst1();
                  this.javaWriter.iAdd();
                  this.javaWriter.constant(255);
                  this.javaWriter.iAnd();
               }, JavaModificationExpressionVisitor.PushOption.BEFORE);
               return null;
            case BYTE_DEC:
               this.modify(expression.target, () -> {
                  this.javaWriter.iConst1();
                  this.javaWriter.iSub();
                  this.javaWriter.constant(255);
                  this.javaWriter.iAnd();
               }, JavaModificationExpressionVisitor.PushOption.BEFORE);
               return null;
            case SBYTE_INC:
               this.modify(expression.target, () -> {
                  this.javaWriter.iConst1();
                  this.javaWriter.iAdd();
                  this.javaWriter.i2b();
               }, JavaModificationExpressionVisitor.PushOption.BEFORE);
               return null;
            case SBYTE_DEC:
               this.modify(expression.target, () -> {
                  this.javaWriter.iConst1();
                  this.javaWriter.iSub();
                  this.javaWriter.i2b();
               }, JavaModificationExpressionVisitor.PushOption.BEFORE);
               return null;
            case SHORT_INC:
               this.modify(expression.target, () -> {
                  this.javaWriter.iConst1();
                  this.javaWriter.iAdd();
                  this.javaWriter.i2s();
               }, JavaModificationExpressionVisitor.PushOption.BEFORE);
               return null;
            case SHORT_DEC:
               this.modify(expression.target, () -> {
                  this.javaWriter.iConst1();
                  this.javaWriter.iSub();
                  this.javaWriter.i2s();
               }, JavaModificationExpressionVisitor.PushOption.BEFORE);
               return null;
            case USHORT_INC:
               this.modify(expression.target, () -> {
                  this.javaWriter.iConst1();
                  this.javaWriter.iAdd();
                  this.javaWriter.constant(65535);
                  this.javaWriter.iAnd();
               }, JavaModificationExpressionVisitor.PushOption.BEFORE);
               return null;
            case USHORT_DEC:
               this.modify(expression.target, () -> {
                  this.javaWriter.iConst1();
                  this.javaWriter.iSub();
                  this.javaWriter.constant(65535);
                  this.javaWriter.iAnd();
               }, JavaModificationExpressionVisitor.PushOption.BEFORE);
               return null;
            case INT_INC:
            case UINT_INC:
            case USIZE_INC:
               if (expression.target instanceof GetLocalVariableExpression) {
                  JavaLocalVariableInfo local = this.javaWriter.getLocalVariable(((GetLocalVariableExpression)expression.target).variable.variable);
                  this.javaWriter.load(local);
                  this.javaWriter.iinc(local.local);
               } else {
                  this.modify(expression.target, () -> {
                     this.javaWriter.iConst1();
                     this.javaWriter.iAdd();
                  }, JavaModificationExpressionVisitor.PushOption.BEFORE);
               }

               return null;
            case INT_DEC:
            case UINT_DEC:
            case USIZE_DEC:
               if (expression.target instanceof GetLocalVariableExpression) {
                  JavaLocalVariableInfo local = this.javaWriter.getLocalVariable(((GetLocalVariableExpression)expression.target).variable.variable);
                  this.javaWriter.load(local);
                  this.javaWriter.iinc(local.local, -1);
               } else {
                  this.modify(expression.target, () -> {
                     this.javaWriter.iConst1();
                     this.javaWriter.iSub();
                  }, JavaModificationExpressionVisitor.PushOption.BEFORE);
               }

               return null;
            case LONG_INC:
            case ULONG_INC:
               this.modify(expression.target, () -> {
                  this.javaWriter.constant(1L);
                  this.javaWriter.lAdd();
               }, JavaModificationExpressionVisitor.PushOption.BEFORE);
               return null;
            case LONG_DEC:
            case ULONG_DEC:
               this.modify(expression.target, () -> {
                  this.javaWriter.constant(1L);
                  this.javaWriter.lSub();
               }, JavaModificationExpressionVisitor.PushOption.BEFORE);
               return null;
            case FLOAT_INC:
               this.modify(expression.target, () -> {
                  this.javaWriter.constant(1.0F);
                  this.javaWriter.fAdd();
               }, JavaModificationExpressionVisitor.PushOption.BEFORE);
               return null;
            case FLOAT_DEC:
               this.modify(expression.target, () -> {
                  this.javaWriter.constant(1.0F);
                  this.javaWriter.fSub();
               }, JavaModificationExpressionVisitor.PushOption.BEFORE);
               return null;
            case DOUBLE_INC:
               this.modify(expression.target, () -> {
                  this.javaWriter.constant(1.0);
                  this.javaWriter.dAdd();
               }, JavaModificationExpressionVisitor.PushOption.BEFORE);
               return null;
            case DOUBLE_DEC:
               this.modify(expression.target, () -> {
                  this.javaWriter.constant(1.0);
                  this.javaWriter.dSub();
               }, JavaModificationExpressionVisitor.PushOption.BEFORE);
               return null;
            default:
               throw new IllegalArgumentException("Unknown postcall builtin: " + expression.member.getBuiltin());
         }
      } else {
         this.modify(expression.target, () -> {
            if (!this.checkAndExecuteMethodInfo(expression.member, expression.type, expression)) {
               throw new IllegalStateException("Call target has no method info!");
            }
         }, JavaModificationExpressionVisitor.PushOption.BEFORE);
         return null;
      }
   }

   public Void visitRange(RangeExpression expression) {
      RangeTypeID type = (RangeTypeID)expression.type;
      Type cls = this.context.getType(expression.type);
      this.javaWriter.newObject(cls.getInternalName());
      this.javaWriter.dup();
      expression.from.accept(this);
      expression.to.accept(this);
      this.javaWriter
         .invokeSpecial(cls.getInternalName(), "<init>", "(" + this.context.getDescriptor(type.baseType) + this.context.getDescriptor(type.baseType) + ")V");
      return null;
   }

   public Void visitSameObject(SameObjectExpression expression) {
      expression.left.accept(this);
      expression.right.accept(this);
      Label end = new Label();
      Label equal = new Label();
      if (expression.inverted) {
         this.javaWriter.ifACmpNe(equal);
      } else {
         this.javaWriter.ifACmpEq(equal);
      }

      this.javaWriter.iConst0();
      this.javaWriter.goTo(end);
      this.javaWriter.label(equal);
      this.javaWriter.iConst1();
      this.javaWriter.label(end);
      return null;
   }

   public Void visitSetField(SetFieldExpression expression) {
      expression.value.accept(this);
      expression.target.accept(this);
      this.javaWriter.dupX1(false, CompilerUtils.isLarge(expression.type));
      this.putField(expression.field);
      return null;
   }

   public Void visitSetFunctionParameter(SetFunctionParameterExpression expression) {
      expression.value.accept(this);
      this.javaWriter.dup(CompilerUtils.isLarge(expression.value.type));
      JavaParameterInfo parameter = this.module.getParameterInfo(expression.parameter);
      this.javaWriter.store(this.context.getType(expression.type), parameter.index);
      return null;
   }

   public Void visitSetLocalVariable(SetLocalVariableExpression expression) {
      expression.value.accept(this);
      Label label = new Label();
      this.javaWriter.label(label);
      JavaLocalVariableInfo tag = this.javaWriter.getLocalVariable(expression.variable.variable);
      tag.end = label;
      this.javaWriter.dup(CompilerUtils.isLarge(expression.value.type));
      this.javaWriter.store(tag.type, tag.local);
      return null;
   }

   public Void visitSetStaticField(SetStaticFieldExpression expression) {
      expression.value.accept(this);
      this.javaWriter.dup(CompilerUtils.isLarge(expression.value.type));
      this.javaWriter.putStaticField(this.context.getJavaField(expression.field));
      return null;
   }

   public Void visitSetter(SetterExpression expression) {
      if (!this.checkAndExecuteMethodInfo(expression.setter, expression.type, expression)) {
         throw new IllegalStateException("Unknown Setter");
      } else {
         return null;
      }
   }

   public Void visitStaticGetter(StaticGetterExpression expression) {
      BuiltinID builtin = expression.getter.member.builtin;
      if (builtin == null) {
         if (this.context.hasJavaField(expression.getter)) {
            this.javaWriter.getStaticField(this.context.getJavaField(expression.getter));
            return null;
         } else if (!this.checkAndExecuteMethodInfo(expression.getter, expression.type, expression)) {
            throw new IllegalStateException("Call target has no method info!");
         } else {
            return null;
         }
      } else {
         switch (builtin) {
            case BYTE_GET_MIN_VALUE:
               this.javaWriter.iConst0();
               break;
            case BYTE_GET_MAX_VALUE:
               this.javaWriter.constant(255);
               break;
            case SBYTE_GET_MIN_VALUE:
               this.javaWriter.getStaticField(BYTE_MIN_VALUE);
               break;
            case SBYTE_GET_MAX_VALUE:
               this.javaWriter.getStaticField(BYTE_MAX_VALUE);
               break;
            case SHORT_GET_MIN_VALUE:
               this.javaWriter.getStaticField(SHORT_MIN_VALUE);
               break;
            case SHORT_GET_MAX_VALUE:
               this.javaWriter.getStaticField(SHORT_MAX_VALUE);
               break;
            case USHORT_GET_MIN_VALUE:
               this.javaWriter.iConst0();
               break;
            case USHORT_GET_MAX_VALUE:
               this.javaWriter.constant(65535);
               break;
            case INT_GET_MIN_VALUE:
               this.javaWriter.getStaticField(INTEGER_MIN_VALUE);
               break;
            case INT_GET_MAX_VALUE:
               this.javaWriter.getStaticField(INTEGER_MAX_VALUE);
               break;
            case UINT_GET_MIN_VALUE:
               this.javaWriter.iConst0();
               break;
            case UINT_GET_MAX_VALUE:
               this.javaWriter.constant(-1);
               break;
            case LONG_GET_MIN_VALUE:
               this.javaWriter.getStaticField(LONG_MIN_VALUE);
               break;
            case LONG_GET_MAX_VALUE:
               this.javaWriter.getStaticField(LONG_MAX_VALUE);
               break;
            case ULONG_GET_MIN_VALUE:
               this.javaWriter.iConst0();
               break;
            case ULONG_GET_MAX_VALUE:
               this.javaWriter.constant(-1L);
               break;
            case USIZE_GET_MIN_VALUE:
            case USIZE_GET_MAX_VALUE:
            case USIZE_BITS:
            default:
               throw new UnsupportedOperationException("Unknown builtin: " + builtin);
            case FLOAT_GET_MIN_VALUE:
               this.javaWriter.getStaticField(FLOAT_MIN_VALUE);
               break;
            case FLOAT_GET_MAX_VALUE:
               this.javaWriter.getStaticField(FLOAT_MAX_VALUE);
               break;
            case DOUBLE_GET_MIN_VALUE:
               this.javaWriter.getStaticField(DOUBLE_MIN_VALUE);
               break;
            case DOUBLE_GET_MAX_VALUE:
               this.javaWriter.getStaticField(DOUBLE_MAX_VALUE);
               break;
            case CHAR_GET_MIN_VALUE:
               this.javaWriter.getStaticField(CHARACTER_MIN_VALUE);
               break;
            case CHAR_GET_MAX_VALUE:
               this.javaWriter.getStaticField(CHARACTER_MAX_VALUE);
               break;
            case ENUM_VALUES:
               DefinitionTypeID type = (DefinitionTypeID)((ArrayTypeID)expression.type).elementType;
               JavaClass cls = this.context.getJavaClass(type.definition);
               this.javaWriter.invokeStatic(JavaMethod.getNativeStatic(cls, "values", "()[L" + cls.internalName + ";"));
         }

         throw new UnsupportedOperationException("Unknown builtin: " + builtin);
      }
   }

   public Void visitStaticSetter(StaticSetterExpression expression) {
      throw new UnsupportedOperationException("Not yet implemented");
   }

   private void visitFunctionalInterfaceWrapping(JavaFunctionInterfaceCastExpression expression) {
      JavaExpressionVisitor.FunctionCastWrapperClass wrapper = this.generateFunctionCastWrapperClass(
         expression.position, (FunctionTypeID)expression.value.type, expression.functionType
      );
      expression.value.accept(this);
      this.javaWriter.newObject(wrapper.className);
      this.javaWriter.dupX1();
      this.javaWriter.swap();
      this.javaWriter.invokeSpecial(wrapper.className, "<init>", wrapper.constructorDesc);
   }

   private JavaExpressionVisitor.FunctionCastWrapperClass generateFunctionCastWrapperClass(
      CodePosition position, FunctionTypeID fromType, FunctionTypeID toType
   ) {
      String lambdaName = "lambda" + this.context.getLambdaCounter();
      JavaClass classInfo = new JavaClass("zsynthetic", lambdaName, JavaClass.Kind.CLASS);
      String className = "zsynthetic/" + lambdaName;
      String wrappedFromSignature = this.context.getDescriptor(fromType);
      String[] interfaces;
      String methodDescriptor;
      Type[] methodParameterTypes;
      JavaMethod implementationMethod;
      if (toType instanceof JavaFunctionalInterfaceTypeID) {
         JavaMethod javaMethod = ((JavaFunctionalInterfaceTypeID)toType).method;
         implementationMethod = new JavaMethod(
            classInfo,
            JavaMethod.Kind.COMPILED,
            javaMethod.name,
            true,
            javaMethod.descriptor,
            javaMethod.modifiers & -1025,
            javaMethod.genericResult,
            javaMethod.typeParameterArguments
         );
         Method functionalInterfaceMethod = ((JavaFunctionalInterfaceTypeID)toType).functionalInterfaceMethod;
         methodDescriptor = Type.getMethodDescriptor(functionalInterfaceMethod);
         interfaces = new String[]{Type.getInternalName(functionalInterfaceMethod.getDeclaringClass())};
         Class<?>[] methodParameterClasses = functionalInterfaceMethod.getParameterTypes();
         methodParameterTypes = new Type[methodParameterClasses.length];

         for (int i = 0; i < methodParameterClasses.length; i++) {
            Class<?> methodParameterType = methodParameterClasses[i];
            methodParameterTypes[i] = Type.getType(methodParameterType);
         }
      } else {
         wrappedFromSignature = this.context.getMethodSignature(toType.header, true);
         methodDescriptor = this.context.getMethodDescriptor(toType.header);
         interfaces = new String[]{this.context.getInternalName(toType)};
         JavaSynthesizedFunctionInstance function = this.context.getFunction(toType);
         implementationMethod = new JavaMethod(classInfo, JavaMethod.Kind.COMPILED, function.getMethod(), true, methodDescriptor, 1, false);
         methodParameterTypes = new Type[toType.header.parameters.length];

         for (int i = 0; i < methodParameterTypes.length; i++) {
            methodParameterTypes[i] = this.context.getType(toType.header.parameters[i].type);
         }
      }

      JavaMethod wrappedMethod = this.context.getFunctionalInterface(fromType);
      String constructorDesc = "(" + wrappedFromSignature + ")V";
      ClassWriter lambdaCW = new JavaClassWriter(2);
      lambdaCW.visit(52, 1, className, null, "java/lang/Object", interfaces);
      lambdaCW.visitField(18, "wrapped", wrappedFromSignature, null, null).visitEnd();
      JavaWriter constructorWriter = new JavaWriter(
         this.context.logger, position, lambdaCW, JavaMethod.getConstructor(this.javaWriter.method.cls, constructorDesc, 1), null, null, null
      );
      constructorWriter.start();
      constructorWriter.loadObject(0);
      constructorWriter.dup();
      constructorWriter.invokeSpecial(Object.class, "<init>", "()V");
      constructorWriter.loadObject(1);
      constructorWriter.putField(className, "wrapped", wrappedFromSignature);
      constructorWriter.ret();
      constructorWriter.end();
      constructorWriter = new JavaWriter(this.context.logger, position, lambdaCW, implementationMethod, null, methodDescriptor, null, "Ljava/lang/Override;");
      constructorWriter.start();
      constructorWriter.loadObject(0);
      constructorWriter.getField(className, "wrapped", wrappedFromSignature);

      for (int i = 0; i < methodParameterTypes.length; i++) {
         constructorWriter.load(methodParameterTypes[i], i + 1);
      }

      constructorWriter.invokeInterface(wrappedMethod);
      TypeID returnType = fromType.header.getReturnType();
      Type rtype = this.context.getType(returnType);
      if (!CompilerUtils.isPrimitive(returnType)) {
         constructorWriter.checkCast(rtype);
      }

      constructorWriter.returnType(rtype);
      constructorWriter.end();
      lambdaCW.visitEnd();
      this.context.register(className, lambdaCW.toByteArray());
      return new JavaExpressionVisitor.FunctionCastWrapperClass(className, constructorDesc);
   }

   public Void visitSupertypeCast(SupertypeCastExpression expression) {
      expression.value.accept(this);
      return null;
   }

   public Void visitSubtypeCast(SubtypeCastExpression expression) {
      expression.value.accept(this);
      this.javaWriter.checkCast(this.context.getType(expression.type));
      return null;
   }

   public Void visitThis(ThisExpression expression) {
      this.javaWriter.load(this.context.getType(expression.type), 0);
      return null;
   }

   public Void visitThrow(ThrowExpression expression) {
      expression.value.accept(this);
      this.javaWriter.aThrow();
      return null;
   }

   public Void visitTryConvert(TryConvertExpression expression) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public Void visitTryRethrowAsException(TryRethrowAsExceptionExpression expression) {
      expression.value.accept(this);
      this.javaWriter.dup();
      String internalName = this.context.getInternalName(expression.value.type) + "$Error";
      this.javaWriter.instanceOf(internalName);
      Label end = new Label();
      this.javaWriter.ifNE(end);
      this.javaWriter.newObject(Type.getInternalName(Exception.class));
      this.javaWriter.dup();
      this.javaWriter.invokeSpecial(Type.getInternalName(Exception.class), "<init>", "()V");
      this.javaWriter.label(end);
      return null;
   }

   public Void visitTryRethrowAsResult(TryRethrowAsResultExpression expression) {
      expression.value.accept(this);
      this.javaWriter.dup();
      String internalName = this.context.getInternalName(expression.value.type) + "$Error";
      this.javaWriter.instanceOf(internalName);
      Label end = new Label();
      this.javaWriter.ifNE(end);
      this.javaWriter.newObject(internalName);
      this.javaWriter.dupX1();
      this.javaWriter.swap();
      this.javaWriter.invokeSpecial(internalName, "<init>", "(Ljava/lang/Object;)V");
      this.javaWriter.label(end);
      return null;
   }

   public Void visitVariantValue(VariantValueExpression expression) {
      JavaVariantOption tag = this.context.getJavaVariantOption(expression.option);
      String internalName = tag.variantOptionClass.internalName;
      this.javaWriter.newObject(internalName);
      this.javaWriter.dup();

      for (Expression argument : expression.arguments) {
         argument.accept(this);
      }

      StringBuilder builder = new StringBuilder("(");

      for (TypeID type : expression.option.getOption().types) {
         builder.append(this.context.getDescriptor(type));
      }

      builder.append(")V");
      this.javaWriter.invokeSpecial(internalName, "<init>", builder.toString());
      return null;
   }

   public Void visitWrapOptional(WrapOptionalExpression expression) {
      expression.value.accept(this);
      expression.value.type.accept(expression.value.type, this.boxingTypeVisitor);
      return null;
   }

   public JavaWriter getJavaWriter() {
      return this.javaWriter;
   }

   boolean checkAndExecuteMethodInfo(DefinitionMemberRef member, TypeID resultType, Expression expression) {
      JavaMethod methodInfo = this.context.getJavaMethod(member);
      if (methodInfo == null) {
         return false;
      }

      this.executeMethodInfo(resultType, expression, methodInfo);
      return true;
   }

   private void executeMethodInfo(TypeID resultType, Expression expression, JavaMethod methodInfo) {
      if (methodInfo.kind == JavaMethod.Kind.STATIC) {
         this.getJavaWriter().invokeStatic(methodInfo);
      } else if (methodInfo.kind == JavaMethod.Kind.INTERFACE) {
         this.getJavaWriter().invokeInterface(methodInfo);
      } else if (methodInfo.kind == JavaMethod.Kind.EXPANSION) {
         this.getJavaWriter().invokeStatic(methodInfo);
      } else if (methodInfo.kind == JavaMethod.Kind.COMPILED) {
         Objects.requireNonNull(methodInfo.translation).translate(expression, this);
      } else if (methodInfo.cls != null && methodInfo.cls.kind == JavaClass.Kind.INTERFACE) {
         this.getJavaWriter().invokeInterface(methodInfo);
      } else {
         this.getJavaWriter().invokeVirtual(methodInfo);
      }

      if (methodInfo.genericResult) {
         this.handleGenericReturnValue(resultType);
      }

      if (resultType == BasicTypeID.VOID && !methodInfo.descriptor.equals("") && !methodInfo.descriptor.endsWith(")V")) {
         boolean isLarge = methodInfo.descriptor.endsWith(")D") && methodInfo.descriptor.endsWith(")J");
         this.getJavaWriter().pop(isLarge);
      }
   }

   public void putField(FieldMemberRef field) {
      JavaField fieldInfo = this.context.getJavaField(field);
      if (field.isStatic()) {
         this.getJavaWriter().putStaticField(fieldInfo);
      } else {
         this.getJavaWriter().putField(fieldInfo);
      }
   }

   public void getField(FieldMemberRef field) {
      JavaField fieldInfo = this.context.getJavaField(field);
      if (field.isStatic()) {
         this.getJavaWriter().getStaticField(fieldInfo);
      } else {
         this.getJavaWriter().getField(fieldInfo);
      }
   }

   public Void isEmptyAsLengthZero(Expression value) {
      return null;
   }

   public Void listToArray(CastExpression value) {
      this.javaWriter.iConst0();
      ArrayHelperType arrayHelperType = new ArrayHelperType(((ArrayTypeID)value.type).elementType, this.context);
      arrayHelperType.newArray(this.javaWriter);
      JavaMethod toArray = new JavaMethod(JavaClass.COLLECTION, JavaMethod.Kind.INSTANCE, "toArray", true, "([Ljava/lang/Object;)[Ljava/lang/Object;", 0, true);
      this.javaWriter.invokeInterface(toArray);
      this.javaWriter.checkCast(this.context.getType(value.type));
      return null;
   }

   public Void setToArray(CastExpression value) {
      this.javaWriter.iConst0();
      Type type = this.context.getType(((ArrayTypeID)value.type).elementType);
      this.javaWriter.newArray(type);
      JavaMethod toArray = new JavaMethod(JavaClass.COLLECTION, JavaMethod.Kind.INSTANCE, "toArray", true, "([Ljava/lang/Object;)[Ljava/lang/Object;", 0, true);
      this.javaWriter.invokeInterface(toArray);
      this.javaWriter.checkCast(this.context.getType(value.type));
      return null;
   }

   public Void containsAsIndexOf(Expression target, Expression value) {
      this.executeMethodInfo(BasicTypeID.STRING, value, CHARACTER_TO_STRING);
      this.executeMethodInfo(BasicTypeID.BOOL, null, JavaMethod.getNativeVirtual(JavaClass.STRING, "contains", "(Ljava/lang/CharSequence;)Z"));
      return null;
   }

   public Void sorted(Expression value) {
      value.accept(this);
      this.javaWriter.invokeVirtual(OBJECT_CLONE);
      this.javaWriter.checkCast(this.context.getDescriptor(value.type));
      this.javaWriter.dup();
      JavaMethod sort = JavaMethod.getNativeExpansion(JavaClass.ARRAYS, "sort", "([Ljava/lang/Object;)V");
      this.javaWriter.invokeStatic(sort);
      return null;
   }

   public Void sortedWithComparator(Expression value, Expression comparator) {
      this.javaWriter.swap();
      this.javaWriter.invokeVirtual(OBJECT_CLONE);
      this.javaWriter.checkCast(this.context.getDescriptor(value.type));
      this.javaWriter.dupX1();
      this.javaWriter.swap();
      JavaMethod sortWithComparator = JavaMethod.getNativeExpansion(JavaClass.ARRAYS, "sort", "([Ljava/lang/Object;Ljava/util/Comparator;)V");
      this.javaWriter.invokeStatic(sortWithComparator);
      return null;
   }

   public Void arrayCopy(Expression value) {
      JavaMethod clone = JavaMethod.getNativeVirtual(JavaClass.OBJECT, "clone", "()Ljava/lang/Object;");
      this.javaWriter.invokeVirtual(clone);
      this.javaWriter.checkCast(this.context.getDescriptor(value.type));
      return null;
   }

   public Void arrayCopyResize(CallExpression value) {
      TypeID elementType = ((ArrayTypeID)value.type).elementType;
      boolean primitive = CompilerUtils.isPrimitive(elementType);
      String elementDescriptor = primitive ? this.context.getDescriptor(elementType) : "Ljava/lang/Object;";
      String methodDescriptor = String.format("([%1$sI)[%1$s", elementDescriptor);
      JavaMethod copyOf = JavaMethod.getNativeStatic(JavaClass.ARRAYS, "copyOf", methodDescriptor);
      this.javaWriter.invokeStatic(copyOf);
      if (!primitive) {
         this.javaWriter.checkCast(this.context.getDescriptor(value.type));
      }

      return null;
   }

   public Void arrayCopyTo(CallExpression call) {
      this.javaWriter.dup2X2();
      this.javaWriter.pop2();
      this.javaWriter.swap();
      this.javaWriter.dup2X2();
      this.javaWriter.pop2();
      JavaClass system = JavaClass.fromInternalName("java/lang/System", JavaClass.Kind.CLASS);
      JavaMethod javaMethod = JavaMethod.getStatic(system, "arraycopy", "(Ljava/lang/Object;ILjava/lang/Object;II)V", 1);
      this.javaWriter.invokeStatic(javaMethod);
      return null;
   }

   public Void stringToAscii(Expression value) {
      JavaClass standardCharsets = JavaClass.fromInternalName("java/nio/charset/StandardCharsets", JavaClass.Kind.CLASS);
      JavaField charset = new JavaField(standardCharsets, "US_ASCII", "Ljava/nio/charset/Charset;");
      this.javaWriter.getStaticField(charset);
      this.javaWriter.invokeVirtual(STRING_GET_BYTES);
      return null;
   }

   public Void stringToUTF8(Expression value) {
      JavaClass standardCharsets = JavaClass.fromInternalName("java/nio/charset/StandardCharsets", JavaClass.Kind.CLASS);
      JavaField charset = new JavaField(standardCharsets, "UTF_8", "Ljava/nio/charset/Charset;");
      this.javaWriter.getStaticField(charset);
      this.javaWriter.invokeVirtual(STRING_GET_BYTES);
      return null;
   }

   public Void bytesAsciiToString(Expression value) {
      JavaClass standardCharsets = JavaClass.fromInternalName("java/nio/charset/StandardCharsets", JavaClass.Kind.CLASS);
      JavaField charset = new JavaField(standardCharsets, "US_ASCII", "Ljava/nio/charset/Charset;");
      this.javaWriter.newObject(JavaClass.STRING);
      this.javaWriter.dupX1();
      this.javaWriter.swap();
      this.javaWriter.getStaticField(charset);
      this.javaWriter.invokeSpecial(STRING_INIT_BYTES_CHARSET);
      return null;
   }

   public Void bytesUTF8ToString(Expression value) {
      JavaClass standardCharsets = JavaClass.fromInternalName("java/nio/charset/StandardCharsets", JavaClass.Kind.CLASS);
      JavaField charset = new JavaField(standardCharsets, "UTF_8", "Ljava/nio/charset/Charset;");
      this.javaWriter.newObject(JavaClass.STRING);
      this.javaWriter.dupX1();
      this.javaWriter.swap();
      this.javaWriter.getStaticField(charset);
      this.javaWriter.invokeSpecial(STRING_INIT_BYTES_CHARSET);
      return null;
   }

   private static class FunctionCastWrapperClass {
      final String className;
      final String constructorDesc;

      FunctionCastWrapperClass(String className, String constructorDesc) {
         this.className = className;
         this.constructorDesc = constructorDesc;
      }
   }
}
