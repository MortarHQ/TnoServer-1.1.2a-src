package org.openzen.zenscript.javashared.prepare;

import java.util.HashMap;
import java.util.Map;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.annotations.NativeTag;
import org.openzen.zenscript.codemodel.definition.AliasDefinition;
import org.openzen.zenscript.codemodel.definition.ClassDefinition;
import org.openzen.zenscript.codemodel.definition.DefinitionVisitor;
import org.openzen.zenscript.codemodel.definition.EnumDefinition;
import org.openzen.zenscript.codemodel.definition.ExpansionDefinition;
import org.openzen.zenscript.codemodel.definition.FunctionDefinition;
import org.openzen.zenscript.codemodel.definition.InterfaceDefinition;
import org.openzen.zenscript.codemodel.definition.StructDefinition;
import org.openzen.zenscript.codemodel.definition.VariantDefinition;
import org.openzen.zenscript.codemodel.expression.CallExpression;
import org.openzen.zenscript.codemodel.expression.CallStaticExpression;
import org.openzen.zenscript.codemodel.expression.CastExpression;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.member.IDefinitionMember;
import org.openzen.zenscript.codemodel.member.InnerDefinitionMember;
import org.openzen.zenscript.codemodel.type.DefinitionTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.javashared.JavaClass;
import org.openzen.zenscript.javashared.JavaCompiledModule;
import org.openzen.zenscript.javashared.JavaContext;
import org.openzen.zenscript.javashared.JavaMethod;
import org.openzen.zenscript.javashared.JavaNativeClass;
import org.openzen.zenscript.javashared.JavaVariantOption;

public class JavaPrepareDefinitionVisitor implements DefinitionVisitor<JavaClass> {
   private final Map<String, JavaNativeClass> nativeClasses = new HashMap<>();
   private final JavaContext context;
   private final String filename;
   private final String className;
   private final JavaClass outerClass;
   private final JavaCompiledModule module;

   public JavaPrepareDefinitionVisitor(JavaContext context, JavaCompiledModule module, String filename, JavaClass outerClass) {
      this(context, module, filename, outerClass, JavaClass.getNameFromFile(filename));
   }

   public JavaPrepareDefinitionVisitor(JavaContext context, JavaCompiledModule module, String filename, JavaClass outerClass, String className) {
      JavaNativeClass cls = new JavaNativeClass(new JavaClass("java.util", "Set", JavaClass.Kind.INTERFACE));
      cls.addInstanceMethod("add", "add", "(Ljava/lang/Object;)Z");
      cls.addInstanceMethod("remove", "remove", "(Ljava/lang/Object;)Z");
      cls.addInstanceMethod("contains", "contains", "(Ljava/lang/Object;)Z");
      cls.addMethod("toArray", new JavaMethod((expression, translator) -> translator.setToArray((CastExpression)expression)));
      cls.addInstanceMethod("length", "size", "()I");
      cls.addInstanceMethod("isEmpty", "isEmpty", "()Z");
      cls.addInstanceMethod("iterate", "iterator", "()Ljava/util/Iterator;");
      this.nativeClasses.put("collections::Set", cls);
      cls = new JavaNativeClass(new JavaClass("java.util", "HashSet", JavaClass.Kind.INTERFACE));
      cls.addConstructor("constructor", "()V");
      this.nativeClasses.put("collections::HashSet", cls);
      cls = new JavaNativeClass(new JavaClass("java.lang", "StringBuilder", JavaClass.Kind.CLASS));
      cls.addConstructor("constructor", "()V");
      cls.addConstructor("constructorWithCapacity", "(I)V");
      cls.addConstructor("constructorWithValue", "(Ljava/lang/String;)V");
      cls.addMethod("isEmpty", new JavaMethod((expression, translator) -> translator.isEmptyAsLengthZero(((CallExpression)expression).target)));
      cls.addInstanceMethod("length", "length", "()I");
      cls.addInstanceMethod("appendBool", "append", "(Z)Ljava/lang/StringBuilder;");
      cls.addInstanceMethod("appendByte", "append", "(I)Ljava/lang/StringBuilder;");
      cls.addInstanceMethod("appendSByte", "append", "(B)Ljava/lang/StringBuilder;");
      cls.addInstanceMethod("appendShort", "append", "(S)Ljava/lang/StringBuilder;");
      cls.addInstanceMethod("appendUShort", "append", "(I)Ljava/lang/StringBuilder;");
      cls.addInstanceMethod("appendInt", "append", "(I)Ljava/lang/StringBuilder;");
      cls.addInstanceMethod("appendUInt", "append", "(I)Ljava/lang/StringBuilder;");
      cls.addInstanceMethod("appendLong", "append", "(J)Ljava/lang/StringBuilder;");
      cls.addInstanceMethod("appendULong", "append", "(J)Ljava/lang/StringBuilder;");
      cls.addInstanceMethod("appendUSize", "append", "(I)Ljava/lang/StringBuilder;");
      cls.addInstanceMethod("appendFloat", "append", "(F)Ljava/lang/StringBuilder;");
      cls.addInstanceMethod("appendDouble", "append", "(D)Ljava/lang/StringBuilder;");
      cls.addInstanceMethod("appendChar", "append", "(C)Ljava/lang/StringBuilder;");
      cls.addInstanceMethod("appendString", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;");
      cls.addInstanceMethod("asString", "toString", "()Ljava/lang/String;");
      this.nativeClasses.put("stdlib::StringBuilder", cls);
      cls = new JavaNativeClass(new JavaClass("java.util", "List", JavaClass.Kind.INTERFACE));
      JavaClass arrayList = new JavaClass("java.util", "ArrayList", JavaClass.Kind.CLASS);
      cls.addMethod("constructor", JavaMethod.getNativeConstructor(arrayList, "()V"));
      cls.addInstanceMethod("add", "add", "(Ljava/lang/Object;)Z");
      cls.addInstanceMethod("insert", "add", "(ILjava/lang/Object;)V");
      cls.addInstanceMethod("remove", "remove", "(I)Ljava/lang/Object;", true);
      cls.addInstanceMethod("removeValue", "remove", "(Ljava/lang/Object;)Z");
      cls.addInstanceMethod("indexOf", "indexOf", "(Ljava/lang/Object;)I");
      cls.addInstanceMethod("lastIndexOf", "lastIndexOf", "(Ljava/lang/Object;)I");
      cls.addInstanceMethod("getAtIndex", "get", "(I)Ljava/lang/Object;", true);
      cls.addInstanceMethod("setAtIndex", "set", "(ILjava/lang/Object;)Ljava/lang/Object;", true);
      cls.addInstanceMethod("contains", "contains", "(Ljava/lang/Object;)Z");
      cls.addMethod("toArray", new JavaMethod((expression, translator) -> translator.listToArray((CastExpression)expression)));
      cls.addInstanceMethod("length", "size", "()I");
      cls.addInstanceMethod("isEmpty", "isEmpty", "()Z");
      cls.addInstanceMethod("iterate", "iterator", "()Ljava/util/Iterator;");
      this.nativeClasses.put("stdlib::List", cls);
      cls = new JavaNativeClass(new JavaClass("java.lang", "Iterable", JavaClass.Kind.INTERFACE));
      cls.addInstanceMethod("iterate", "iterator", "()Ljava/util/Iterator;");
      this.nativeClasses.put("stdlib::Iterable", cls);
      cls = new JavaNativeClass(new JavaClass("java.util", "Iterator", JavaClass.Kind.INTERFACE));
      cls.addMethod("empty", new JavaMethod(JavaClass.COLLECTIONS, JavaMethod.Kind.STATIC, "emptyIterator", false, "()Ljava/lang/Iterator;", 9, false));
      cls.addInstanceMethod("hasNext", "hasNext", "()Z");
      cls.addInstanceMethod("next", "next", "()Ljava/lang/Object;", true);
      this.nativeClasses.put("stdlib::Iterator", cls);
      cls = new JavaNativeClass(new JavaClass("java.lang", "Comparable", JavaClass.Kind.INTERFACE));
      cls.addInstanceMethod("compareTo", "compareTo", "(Ljava/lang/Object;)I");
      this.nativeClasses.put("stdlib::Comparable", cls);
      JavaClass integer = new JavaClass("java.lang", "Integer", JavaClass.Kind.CLASS);
      arrayList = new JavaClass("java.lang", "Math", JavaClass.Kind.CLASS);
      JavaNativeClass clsx = new JavaNativeClass(integer);
      clsx.addMethod("min", JavaMethod.getNativeStatic(arrayList, "min", "(II)I"));
      clsx.addMethod("max", JavaMethod.getNativeStatic(arrayList, "max", "(II)I"));
      clsx.addMethod("toHexString", JavaMethod.getNativeExpansion(integer, "toHexString", "(I)Ljava/lang/String;"));
      this.nativeClasses.put("stdlib::Integer", clsx);
      this.nativeClasses.put("stdlib::USize", clsx);
      JavaClass string = new JavaClass("java.lang", "String", JavaClass.Kind.CLASS);
      JavaNativeClass clsxx = new JavaNativeClass(string);
      clsxx.addMethod("contains", new JavaMethod((expression, translator) -> {
         CallExpression call = (CallExpression)expression;
         Expression str = call.target;
         Expression character = call.arguments.arguments[0];
         return translator.containsAsIndexOf(str, character);
      }));
      clsxx.addInstanceMethod("compareToIgnoreCase", "compareToIgnoreCase", "(Ljava/lang/String;)I");
      clsxx.addInstanceMethod("endsWith", "endsWith", "(Ljava/lang/String;)Z");
      clsxx.addInstanceMethod("equalsIgnoreCase", "equalsIgnoreCase", "(Ljava/lang/String;)Z");
      clsxx.addInstanceMethod("indexOfFrom", "indexOf", "(II)I");
      clsxx.addInstanceMethod("lastIndexOf", "lastIndexOf", "(I)I");
      clsxx.addInstanceMethod("lastIndexOfFrom", "lastIndexOf", "(II)I");
      clsxx.addInstanceMethod("indexOf", "indexOf", "(I)I");
      clsxx.addInstanceMethod("indexOfString", "indexOf", "(Ljava/lang/String;)I");
      clsxx.addInstanceMethod("indexOfStringFrom", "indexOf", "(Ljava/lang/String;I)I");
      clsxx.addInstanceMethod("lastIndexOfString", "lastIndexOf", "(Ljava/lang/String;)I");
      clsxx.addInstanceMethod("lastIndexOfStringFrom", "lastIndexOf", "(Ljava/lang/String;I)I");
      clsxx.addInstanceMethod("replace", "replace", "(CC)Ljava/lang/String;");
      clsxx.addInstanceMethod("trim", "trim", "()Ljava/lang/String;");
      clsxx.addInstanceMethod("startsWith", "startsWith", "(Ljava/lang/String;)Z");
      clsxx.addInstanceMethod("endsWith", "endsWith", "(Ljava/lang/String;)Z");
      clsxx.addMethod("fromAsciiBytes", new JavaMethod((expression, translator) -> {
         CallStaticExpression call = (CallStaticExpression)expression;
         return translator.bytesAsciiToString(call.arguments.arguments[0]);
      }));
      clsxx.addMethod("fromUTF8Bytes", new JavaMethod((expression, translator) -> {
         CallStaticExpression call = (CallStaticExpression)expression;
         return translator.bytesUTF8ToString(call.arguments.arguments[0]);
      }));
      clsxx.addMethod("toAsciiBytes", new JavaMethod((expression, translator) -> {
         CallExpression call = (CallExpression)expression;
         return translator.stringToAscii(call.target);
      }));
      clsxx.addMethod("toUTF8Bytes", new JavaMethod((expression, translator) -> {
         CallExpression call = (CallExpression)expression;
         return translator.stringToUTF8(call.target);
      }));
      this.nativeClasses.put("stdlib::String", clsxx);
      JavaClass arrays = JavaClass.ARRAYS;
      JavaNativeClass clsxxx = new JavaNativeClass(arrays);
      clsxxx.addMethod("sort", JavaMethod.getNativeExpansion(arrays, "sort", "([Ljava/lang/Object;)V"));
      clsxxx.addMethod("sorted", new JavaMethod((expression, translator) -> translator.sorted(((CallExpression)expression).target)));
      clsxxx.addMethod("sortWithComparator", JavaMethod.getNativeExpansion(arrays, "sort", "([Ljava/lang/Object;Ljava/util/Comparator;)V"));
      clsxxx.addMethod(
         "sortedWithComparator",
         new JavaMethod(
            (expression, translator) -> translator.sortedWithComparator(
               ((CallExpression)expression).target, ((CallExpression)expression).arguments.arguments[0]
            )
         )
      );
      clsxxx.addMethod("copy", new JavaMethod((expression, translator) -> translator.arrayCopy(((CallExpression)expression).target)));
      clsxxx.addMethod("copyResize", new JavaMethod((expression, translator) -> translator.arrayCopyResize((CallExpression)expression)));
      clsxxx.addMethod("copyTo", new JavaMethod((expression, translator) -> translator.arrayCopyTo((CallExpression)expression)));
      this.nativeClasses.put("stdlib::Arrays", clsxxx);
      cls = new JavaNativeClass(new JavaClass("java.lang", "IllegalArgumentException", JavaClass.Kind.CLASS));
      cls.addConstructor("constructor", "(Ljava/lang/String;)V");
      this.nativeClasses.put("stdlib::IllegalArgumentException", cls);
      cls = new JavaNativeClass(new JavaClass("java.lang", "Exception", JavaClass.Kind.CLASS));
      cls.addConstructor("constructor", "(Ljava/lang/String;)V");
      cls.addConstructor("constructorWithCause", "(Ljava/lang/String;Ljava/lang/Throwable;)V");
      this.nativeClasses.put("stdlib::Exception", cls);
      cls = new JavaNativeClass(new JavaClass("java.io", "IOException", JavaClass.Kind.CLASS));
      cls.addConstructor("constructor", "(Ljava/lang/String;)V");
      this.nativeClasses.put("io::IOException", cls);
      cls = new JavaNativeClass(new JavaClass("java.io", "Reader", JavaClass.Kind.INTERFACE));
      cls.addInstanceMethod("destruct", "close", "()V");
      cls.addInstanceMethod("readCharacter", "read", "()C");
      cls.addInstanceMethod("readArray", "read", "([C)I");
      cls.addInstanceMethod("readArraySlice", "read", "([CII)I");
      this.nativeClasses.put("io::Reader", cls);
      cls = new JavaNativeClass(new JavaClass("java.io", "StringReader", JavaClass.Kind.CLASS), true);
      cls.addConstructor("constructor", "(Ljava/lang/String;)V");
      cls.addInstanceMethod("destructor", "close", "()V");
      cls.addInstanceMethod("readCharacter", "read", "()C");
      cls.addInstanceMethod("readArray", "read", "([C)I");
      cls.addInstanceMethod("readSlice", "read", "([CII)I");
      this.nativeClasses.put("io::StringReader", cls);
      cls = new JavaNativeClass(new JavaClass("java.io", "InputStream", JavaClass.Kind.INTERFACE), true);
      cls.addInstanceMethod("destructor", "close", "()V");
      cls.addInstanceMethod("read", "read", "()I");
      cls.addInstanceMethod("readArray", "read", "([B)I");
      cls.addInstanceMethod("readSlice", "read", "([BII)I");
      this.nativeClasses.put("io::InputStream", cls);
      cls = new JavaNativeClass(new JavaClass("java.io", "OutputStream", JavaClass.Kind.INTERFACE), true);
      cls.addInstanceMethod("destructor", "close", "()V");
      cls.addInstanceMethod("write", "write", "()I");
      cls.addInstanceMethod("writeArray", "write", "([B)V");
      cls.addInstanceMethod("writeSlice", "write", "([BII)V");
      this.nativeClasses.put("io::OutputStream", cls);
      JavaClass math = new JavaClass("java.lang", "Math", JavaClass.Kind.CLASS);
      JavaNativeClass mathFunctions = new JavaNativeClass(math, true);

      for (String doubleToDoubleMethodName : new String[]{
         "sin",
         "cos",
         "tan",
         "asin",
         "acos",
         "atan",
         "sinh",
         "cosh",
         "tanh",
         "toRadians",
         "toDegrees",
         "exp",
         "log",
         "log10",
         "sqrt",
         "cbrt",
         "ceil",
         "floor",
         "abs",
         "signum"
      }) {
         mathFunctions.addMethod(doubleToDoubleMethodName, JavaMethod.getNativeStatic(math, doubleToDoubleMethodName, "(D)D"));
      }

      mathFunctions.addMethod("minInteger", JavaMethod.getNativeStatic(math, "min", "(II)I"));
      mathFunctions.addMethod("maxInteger", JavaMethod.getNativeStatic(math, "max", "(II)I"));
      mathFunctions.addMethod("minLong", JavaMethod.getNativeStatic(math, "min", "(JJ)J"));
      mathFunctions.addMethod("maxLong", JavaMethod.getNativeStatic(math, "max", "(JJ)J"));
      mathFunctions.addMethod("minFloat", JavaMethod.getNativeStatic(math, "min", "(FF)F"));
      mathFunctions.addMethod("maxFloat", JavaMethod.getNativeStatic(math, "max", "(FF)F"));
      mathFunctions.addMethod("minDouble", JavaMethod.getNativeStatic(math, "min", "(DD)D"));
      mathFunctions.addMethod("maxDouble", JavaMethod.getNativeStatic(math, "max", "(DD)D"));
      mathFunctions.addMethod("roundDouble", JavaMethod.getNativeStatic(math, "round", "(D)J"));
      mathFunctions.addMethod("roundFloat", JavaMethod.getNativeStatic(math, "round", "(F)J"));
      mathFunctions.addMethod("absInteger", JavaMethod.getNativeStatic(math, "abs", "(I)I"));
      mathFunctions.addMethod("pow", JavaMethod.getNativeStatic(math, "pow", "(DD)D"));
      this.nativeClasses.put("math::Functions", mathFunctions);
      JavaClass uuid = new JavaClass("java.util", "UUID", JavaClass.Kind.CLASS);
      JavaNativeClass clsxxxx = new JavaNativeClass(new JavaClass("java.util", "UUID", JavaClass.Kind.CLASS));
      clsxxxx.addMethod("random", JavaMethod.getNativeStatic(uuid, "randomUUID", "()Ljava/util/UUID;"));
      clsxxxx.addInstanceMethod("toString", "toString", "()Ljava/lang/String;");
      clsxxxx.addMethod("parse", JavaMethod.getNativeStatic(uuid, "fromString", "(Ljava/lang/String;)Ljava/util/UUID;"));
      this.nativeClasses.put("uuid::uuid", clsxxxx);
      this.context = context;
      this.filename = filename;
      this.outerClass = outerClass;
      this.module = module;
      this.className = className;
   }

   private boolean isPrepared(HighLevelDefinition definition) {
      return this.context.hasJavaClass(definition);
   }

   public void prepare(TypeID type) {
      if (type instanceof DefinitionTypeID) {
         HighLevelDefinition definition = ((DefinitionTypeID)type).definition;
         definition.accept(this);
      }
   }

   public JavaClass visitClass(ClassDefinition definition) {
      return this.isPrepared(definition) ? this.context.getJavaClass(definition) : this.visitClassCompiled(definition, true, JavaClass.Kind.CLASS);
   }

   public JavaClass visitInterface(InterfaceDefinition definition) {
      if (this.isPrepared(definition)) {
         return this.context.getJavaClass(definition);
      }

      for (TypeID baseType : definition.baseInterfaces) {
         this.prepare(baseType);
      }

      return this.visitClassCompiled(definition, true, JavaClass.Kind.INTERFACE);
   }

   public JavaClass visitEnum(EnumDefinition definition) {
      return this.isPrepared(definition) ? this.context.getJavaClass(definition) : this.visitClassCompiled(definition, false, JavaClass.Kind.ENUM);
   }

   public JavaClass visitStruct(StructDefinition definition) {
      return this.isPrepared(definition) ? this.context.getJavaClass(definition) : this.visitClassCompiled(definition, true, JavaClass.Kind.CLASS);
   }

   public JavaClass visitFunction(FunctionDefinition definition) {
      if (this.isPrepared(definition)) {
         return this.context.getJavaClass(definition);
      }

      JavaClass cls = new JavaClass(this.context.getPackageName(definition.pkg), this.className, JavaClass.Kind.CLASS);
      this.context.setJavaClass(definition, cls);
      return cls;
   }

   public JavaClass visitExpansion(ExpansionDefinition definition) {
      if (this.isPrepared(definition)) {
         return this.context.getJavaClass(definition);
      }

      NativeTag nativeTag = definition.getTag(NativeTag.class);
      if (nativeTag != null) {
         this.context.setJavaNativeClass(definition, this.nativeClasses.get(nativeTag.value));
      }

      JavaClass cls = new JavaClass(this.context.getPackageName(definition.pkg), this.className, JavaClass.Kind.CLASS);
      this.context.setJavaClass(definition, cls);
      return cls;
   }

   public JavaClass visitAlias(AliasDefinition definition) {
      return null;
   }

   public JavaClass visitVariant(VariantDefinition variant) {
      if (this.isPrepared(variant)) {
         return this.context.getJavaClass(variant);
      }

      JavaClass cls = new JavaClass(this.context.getPackageName(variant.pkg), variant.name, JavaClass.Kind.CLASS);
      this.context.setJavaClass(variant, cls);

      for (VariantDefinition.Option option : variant.options) {
         JavaClass variantCls = new JavaClass(cls, option.name, JavaClass.Kind.CLASS);
         this.module.setVariantOption(option, new JavaVariantOption(cls, variantCls));
      }

      return cls;
   }

   private JavaClass visitClassCompiled(HighLevelDefinition definition, boolean startsEmpty, JavaClass.Kind kind) {
      if (definition.getSuperType() != null) {
         this.prepare(definition.getSuperType());
      }

      NativeTag nativeTag = definition.getTag(NativeTag.class);
      JavaNativeClass nativeClass = nativeTag == null ? null : this.nativeClasses.get(nativeTag.value);
      JavaClass cls;
      if (nativeClass == null) {
         cls = this.outerClass == null
            ? new JavaClass(this.context.getPackageName(definition.pkg), definition.name, kind)
            : new JavaClass(this.outerClass, definition.name, kind);
         this.context.setJavaClass(definition, cls);
      } else {
         cls = this.outerClass == null
            ? new JavaClass(this.context.getPackageName(definition.pkg), definition.name + "Expansion", kind)
            : new JavaClass(this.outerClass, definition.name + "Expansion", kind);
         this.context.setJavaClass(definition, nativeClass.cls);
         this.context.setJavaExpansionClass(definition, cls);
         this.context.setJavaNativeClass(definition, nativeClass);
      }

      for (IDefinitionMember member : definition.members) {
         if (member instanceof InnerDefinitionMember) {
            ((InnerDefinitionMember)member).innerDefinition.accept(new JavaPrepareDefinitionVisitor(this.context, this.module, this.filename, cls));
         }
      }

      return cls;
   }
}
