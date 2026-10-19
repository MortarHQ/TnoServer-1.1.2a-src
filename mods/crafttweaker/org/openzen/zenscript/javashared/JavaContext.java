package org.openzen.zenscript.javashared;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongSupplier;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongBiFunction;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.logging.IZSLogger;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.Module;
import org.openzen.zenscript.codemodel.definition.EnumDefinition;
import org.openzen.zenscript.codemodel.definition.VariantDefinition;
import org.openzen.zenscript.codemodel.definition.ZSPackage;
import org.openzen.zenscript.codemodel.generic.TypeParameter;
import org.openzen.zenscript.codemodel.member.DefinitionMember;
import org.openzen.zenscript.codemodel.member.IDefinitionMember;
import org.openzen.zenscript.codemodel.member.ImplementationMember;
import org.openzen.zenscript.codemodel.member.ref.DefinitionMemberRef;
import org.openzen.zenscript.codemodel.member.ref.VariantOptionRef;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.FunctionTypeID;
import org.openzen.zenscript.codemodel.type.GenericTypeID;
import org.openzen.zenscript.codemodel.type.GlobalTypeRegistry;
import org.openzen.zenscript.codemodel.type.RangeTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.javashared.types.JavaFunctionalInterfaceTypeID;

public abstract class JavaContext {
   public final ZSPackage modulePackage;
   public final String basePackage;
   public final IZSLogger logger;
   private final GlobalTypeRegistry registry;
   private final Map<String, JavaSynthesizedFunction> functions = new HashMap<>();
   private final Map<String, JavaSynthesizedRange> ranges = new HashMap<>();
   private final JavaCompileSpace space;
   private final Map<Module, JavaCompiledModule> modules = new HashMap<>();
   private boolean useShared = false;

   public JavaContext(JavaCompileSpace space, ZSPackage modulePackage, String basePackage, IZSLogger logger) {
      this.logger = logger;
      this.space = space;
      this.registry = space.getRegistry();
      this.modulePackage = modulePackage;
      this.basePackage = basePackage;
      this.addDefaultFunctions();
   }

   private void addDefaultFunctions() {
      TypeParameter t = new TypeParameter(CodePosition.BUILTIN, "T");
      TypeParameter u = new TypeParameter(CodePosition.BUILTIN, "U");
      TypeParameter v = new TypeParameter(CodePosition.BUILTIN, "V");
      Map<String, TypeParameter> typeParamMap = new HashMap<>();
      typeParamMap.put("T", t);
      typeParamMap.put("U", u);
      typeParamMap.put("V", v);
      Function<String, TypeParameter> paramConverter = key -> typeParamMap.computeIfAbsent(key, newKey -> new TypeParameter(CodePosition.BUILTIN, newKey));
      this.registerFunction(paramConverter, BiConsumer.class);
      this.registerFunction(paramConverter, BiFunction.class);
      this.registerFunction(paramConverter, BiPredicate.class);
      this.registerFunction(paramConverter, BooleanSupplier.class);
      this.registerFunction(paramConverter, Consumer.class);
      this.registerFunction(paramConverter, DoubleBinaryOperator.class);
      this.registerFunction(paramConverter, DoubleConsumer.class);
      this.registerFunction(paramConverter, DoubleFunction.class);
      this.registerFunction(paramConverter, DoublePredicate.class);
      this.registerFunction(paramConverter, DoubleSupplier.class);
      this.registerFunction(paramConverter, DoubleToIntFunction.class);
      this.registerFunction(paramConverter, DoubleToLongFunction.class);
      this.registerFunction(paramConverter, DoubleUnaryOperator.class);
      this.registerFunction(paramConverter, Function.class);
      this.registerFunction(paramConverter, IntBinaryOperator.class);
      this.registerFunction(paramConverter, IntConsumer.class);
      this.registerFunction(paramConverter, IntFunction.class);
      this.registerFunction(paramConverter, IntPredicate.class);
      this.registerFunction(paramConverter, IntSupplier.class);
      this.registerFunction(paramConverter, IntToDoubleFunction.class);
      this.registerFunction(paramConverter, IntToLongFunction.class);
      this.registerFunction(paramConverter, IntUnaryOperator.class);
      this.registerFunction(paramConverter, LongBinaryOperator.class);
      this.registerFunction(paramConverter, LongConsumer.class);
      this.registerFunction(paramConverter, LongFunction.class);
      this.registerFunction(paramConverter, LongPredicate.class);
      this.registerFunction(paramConverter, LongSupplier.class);
      this.registerFunction(paramConverter, LongToDoubleFunction.class);
      this.registerFunction(paramConverter, LongToIntFunction.class);
      this.registerFunction(paramConverter, LongUnaryOperator.class);
      this.registerFunction(paramConverter, ObjDoubleConsumer.class);
      this.registerFunction(paramConverter, ObjIntConsumer.class);
      this.registerFunction(paramConverter, ObjLongConsumer.class);
      this.registerFunction(paramConverter, Predicate.class);
      this.registerFunction(paramConverter, Supplier.class);
      this.registerFunction(paramConverter, ToDoubleBiFunction.class);
      this.registerFunction(paramConverter, ToDoubleFunction.class);
      this.registerFunction(paramConverter, ToIntBiFunction.class);
      this.registerFunction(paramConverter, ToIntFunction.class);
      this.registerFunction(paramConverter, ToLongBiFunction.class);
      this.registerFunction(paramConverter, ToLongFunction.class);
      this.registerFunction(
         "TTToT", BinaryOperator.class, "apply", new TypeParameter[]{t}, this.registry.getGeneric(t), this.registry.getGeneric(t), this.registry.getGeneric(t)
      );
      this.registerFunction("TToT", UnaryOperator.class, "apply", new TypeParameter[]{t}, this.registry.getGeneric(t));
      this.registerFunction(
         "TTToInt", Comparator.class, "compare", new TypeParameter[]{t}, BasicTypeID.INT, this.registry.getGeneric(t), this.registry.getGeneric(t)
      );
   }

   private void registerFunction(String id, Class<?> clazz, String methodName, TypeParameter[] typeParameters, TypeID returnType, TypeID... parameterTypes) {
      this.functions
         .put(
            id,
            new JavaSynthesizedFunction(
               new JavaClass(clazz.getPackage().getName(), clazz.getSimpleName(), JavaClass.Kind.INTERFACE),
               typeParameters,
               new FunctionHeader(returnType, parameterTypes),
               methodName
            )
         );
   }

   private void registerFunction(Function<String, TypeParameter> paramConverter, Class<?> clazz) {
      TypeParameter[] parameters = new TypeParameter[clazz.getTypeParameters().length];
      Map<String, GenericTypeID> parameterMapping = new HashMap<>();

      for (int i = 0; i < clazz.getTypeParameters().length; i++) {
         TypeParameter typeParameter = paramConverter.apply(this.getTypeParameter(i));
         parameters[i] = typeParameter;
         parameterMapping.put(clazz.getTypeParameters()[i].getName(), this.registry.getGeneric(typeParameter));
      }

      Optional<Method> foundMethod = Arrays.stream(clazz.getMethods())
         .filter(methodx -> !Modifier.isStatic(methodx.getModifiers()) && !methodx.isDefault())
         .findFirst();
      if (!foundMethod.isPresent()) {
         throw new IllegalArgumentException(String.format("Unable to find any applicable methods in class: '%s'", clazz.getName()));
      }

      Method method = foundMethod.get();
      StringBuilder idBuilder = new StringBuilder();
      TypeID[] parameterTypes = new TypeID[method.getParameterCount()];

      for (int i = 0; i < parameterTypes.length; i++) {
         Type type = method.getGenericParameterTypes()[i];
         parameterTypes[i] = this.convertTypeToTypeID(parameterMapping, type);
         idBuilder.append(this.getNameFromType(parameterMapping, type));
      }

      Type genericReturnType = method.getGenericReturnType();
      idBuilder.append("To").append(this.getNameFromType(parameterMapping, genericReturnType));
      if (this.functions.containsKey(idBuilder.toString())) {
         throw new IllegalArgumentException(String.format("Function '%s' already registered!", idBuilder));
      }

      this.functions
         .put(
            idBuilder.toString(),
            new JavaSynthesizedFunction(
               new JavaClass(clazz.getPackage().getName(), clazz.getSimpleName(), JavaClass.Kind.INTERFACE),
               parameters,
               new FunctionHeader(this.convertTypeToTypeID(parameterMapping, genericReturnType), parameterTypes),
               method.getName()
            )
         );
   }

   private String getNameFromType(Map<String, GenericTypeID> paramMap, Type type) {
      if (paramMap.containsKey(type.getTypeName())) {
         return paramMap.get(type.getTypeName()).parameter.name;
      }

      switch (type.getTypeName()) {
         case "int":
            return "Int";
         case "void":
            return "Void";
         case "boolean":
            return "Bool";
         case "byte":
            return "Byte";
         case "short":
            return "Short";
         case "long":
            return "Long";
         case "float":
            return "Float";
         case "double":
            return "Double";
         case "char":
            return "Char";
         default:
            throw new IllegalArgumentException(String.format("Unknown Type: '%s'", type));
      }
   }

   private TypeID convertTypeToTypeID(Map<String, GenericTypeID> paramMap, Type type) {
      if (paramMap.containsKey(type.getTypeName())) {
         return paramMap.get(type.getTypeName());
      }

      switch (type.getTypeName()) {
         case "int":
            return BasicTypeID.INT;
         case "void":
            return BasicTypeID.VOID;
         case "boolean":
            return BasicTypeID.BOOL;
         case "byte":
            return BasicTypeID.BYTE;
         case "short":
            return BasicTypeID.SHORT;
         case "long":
            return BasicTypeID.LONG;
         case "float":
            return BasicTypeID.FLOAT;
         case "double":
            return BasicTypeID.DOUBLE;
         case "char":
            return BasicTypeID.CHAR;
         default:
            throw new IllegalArgumentException(String.format("Unknown Type: '%s'", type));
      }
   }

   public String getPackageName(ZSPackage pkg) {
      if (pkg == null) {
         throw new IllegalArgumentException("Package not part of this module");
      } else {
         return pkg == this.modulePackage ? this.basePackage : this.getPackageName(pkg.parent) + "/" + pkg.name;
      }
   }

   public JavaMethod getFunctionalInterface(TypeID type) {
      if (type instanceof JavaFunctionalInterfaceTypeID t) {
         return t.method;
      } else {
         FunctionTypeID functionType = (FunctionTypeID)type;
         JavaSynthesizedFunctionInstance function = this.getFunction(functionType);
         return new JavaMethod(
            function.getCls(),
            JavaMethod.Kind.INTERFACE,
            function.getMethod(),
            false,
            this.getMethodDescriptor(function.getHeader()),
            1025,
            function.getHeader().getReturnType().isGeneric()
         );
      }
   }

   protected abstract JavaSyntheticClassGenerator getTypeGenerator();

   public abstract String getDescriptor(TypeID var1);

   public String getSignature(TypeID type) {
      return new JavaTypeGenericVisitor(this).getGenericSignature(type);
   }

   public void addModule(Module module, JavaCompiledModule target) {
      this.modules.put(module, target);
      this.space.register(target);
   }

   public JavaCompiledModule getJavaModule(Module module) {
      if (this.modules.containsKey(module)) {
         return this.modules.get(module);
      } else {
         JavaCompiledModule javaModule = this.space.getCompiled(module);
         if (javaModule == null) {
            throw new IllegalStateException("Module not yet registered: " + module.name);
         } else {
            return javaModule;
         }
      }
   }

   public JavaClass getJavaClass(HighLevelDefinition definition) {
      return this.getJavaModule(definition.module).getClassInfo(definition);
   }

   public JavaClass getJavaExpansionClass(HighLevelDefinition definition) {
      return this.getJavaModule(definition.module).getExpansionClassInfo(definition);
   }

   public JavaClass optJavaClass(HighLevelDefinition definition) {
      return this.getJavaModule(definition.module).optClassInfo(definition);
   }

   public JavaNativeClass getJavaNativeClass(HighLevelDefinition definition) {
      return this.getJavaModule(definition.module).getNativeClassInfo(definition);
   }

   public boolean hasJavaClass(HighLevelDefinition definition) {
      return this.getJavaModule(definition.module).hasClassInfo(definition);
   }

   public void setJavaClass(HighLevelDefinition definition, JavaClass cls) {
      this.getJavaModule(definition.module).setClassInfo(definition, cls);
   }

   public void setJavaExpansionClass(HighLevelDefinition definition, JavaClass cls) {
      this.getJavaModule(definition.module).setExpansionClassInfo(definition, cls);
   }

   public void setJavaNativeClass(HighLevelDefinition definition, JavaNativeClass cls) {
      this.getJavaModule(definition.module).setNativeClassInfo(definition, cls);
   }

   public boolean hasJavaField(DefinitionMemberRef member) {
      HighLevelDefinition definition = member.getTarget().getDefinition();
      return this.getJavaModule(definition.module).optFieldInfo(member.getTarget()) != null;
   }

   public JavaField getJavaField(IDefinitionMember member) {
      HighLevelDefinition definition = member.getDefinition();
      return this.getJavaModule(definition.module).getFieldInfo(member);
   }

   public JavaField getJavaField(DefinitionMemberRef member) {
      return this.getJavaField(member.getTarget());
   }

   public JavaMethod getJavaMethod(IDefinitionMember member) {
      HighLevelDefinition definition = member.getDefinition();
      return this.getJavaModule(definition.module).getMethodInfo(member);
   }

   public JavaMethod getJavaMethod(DefinitionMemberRef member) {
      return this.getJavaMethod(member.getTarget());
   }

   public JavaVariantOption getJavaVariantOption(VariantDefinition.Option option) {
      HighLevelDefinition definition = option.variant;
      return this.getJavaModule(definition.module).getVariantOption(option);
   }

   public JavaVariantOption getJavaVariantOption(VariantOptionRef member) {
      return this.getJavaVariantOption(member.getOption());
   }

   public JavaImplementation getJavaImplementation(ImplementationMember member) {
      return this.getJavaModule(member.definition.module).getImplementationInfo(member);
   }

   public String getMethodDescriptor(FunctionHeader header) {
      return this.getMethodDescriptor(header, false, "");
   }

   public String getMethodDescriptorExpansion(FunctionHeader header, TypeID expandedType) {
      StringBuilder startBuilder = new StringBuilder(this.getDescriptor(expandedType));
      List<TypeParameter> typeParameters = new ArrayList<>();
      expandedType.extractTypeParameters(typeParameters);

      for (TypeParameter typeParameter : typeParameters) {
         startBuilder.append("Ljava/lang/Class;");
      }

      return this.getMethodDescriptor(header, false, startBuilder.toString());
   }

   public String getMethodSignatureExpansion(FunctionHeader header, TypeID expandedClass) {
      return new JavaTypeGenericVisitor(this).getMethodSignatureExpansion(header, expandedClass);
   }

   public String getMethodSignature(FunctionHeader header) {
      return this.getMethodSignature(header, true);
   }

   public String getMethodSignature(FunctionHeader header, boolean withGenerics) {
      return new JavaTypeGenericVisitor(this).getGenericMethodSignature(header, withGenerics);
   }

   public String getEnumConstructorDescriptor(FunctionHeader header) {
      return this.getMethodDescriptor(header, true, "");
   }

   public JavaSynthesizedFunctionInstance getFunction(FunctionTypeID type) {
      String id = this.getFunctionId(type.header);
      JavaSynthesizedFunction function;
      if (!this.functions.containsKey(id)) {
         JavaClass cls = new JavaClass("zsynthetic", "Function" + id, JavaClass.Kind.INTERFACE);
         List<TypeParameter> typeParameters = new ArrayList<>();
         List<FunctionParameter> parameters = new ArrayList<>();
         Map<TypeID, TypeParameter> alreadyKnownTypeParameters = new HashMap<>();

         for (FunctionParameter parameter : type.header.parameters) {
            JavaTypeInfo typeInfo = JavaTypeInfo.get(parameter.type);
            if (typeInfo.primitive) {
               parameters.add(new FunctionParameter(parameter.type, Character.toString((char)(97 + parameters.size()))));
            } else {
               TypeParameter typeParameter;
               if (alreadyKnownTypeParameters.containsKey(parameter.type)) {
                  typeParameter = alreadyKnownTypeParameters.get(parameter.type);
               } else {
                  typeParameter = new TypeParameter(CodePosition.BUILTIN, this.getTypeParameter(typeParameters.size()));
                  typeParameters.add(typeParameter);
                  alreadyKnownTypeParameters.put(parameter.type, typeParameter);
               }

               parameters.add(new FunctionParameter(this.registry.getGeneric(typeParameter), Character.toString((char)(97 + parameters.size()))));
            }
         }

         JavaTypeInfo typeInfo = JavaTypeInfo.get(type.header.getReturnType());
         TypeID returnType;
         if (typeInfo.primitive) {
            returnType = type.header.getReturnType();
         } else {
            TypeParameter typeParameter = new TypeParameter(CodePosition.BUILTIN, this.getTypeParameter(typeParameters.size()));
            typeParameters.add(typeParameter);
            returnType = this.registry.getGeneric(typeParameter);
         }

         function = new JavaSynthesizedFunction(
            cls,
            typeParameters.toArray(new TypeParameter[typeParameters.size()]),
            new FunctionHeader(returnType, parameters.toArray(new FunctionParameter[parameters.size()])),
            "invoke"
         );
         this.functions.put(id, function);
         this.getTypeGenerator().synthesizeFunction(function);
      } else {
         function = this.functions.get(id);
      }

      List<TypeID> typeArguments = new ArrayList<>();

      for (FunctionParameter parameter : type.header.parameters) {
         JavaTypeInfo typeInfo = JavaTypeInfo.get(parameter.type);
         if (!typeInfo.primitive) {
            typeArguments.add(parameter.type);
         }
      }

      if (!JavaTypeInfo.isPrimitive(type.header.getReturnType())) {
         typeArguments.add(type.header.getReturnType());
      }

      return new JavaSynthesizedFunctionInstance(function, typeArguments.toArray(new TypeID[typeArguments.size()]));
   }

   private String getFunctionId(FunctionHeader header) {
      StringBuilder signature = new StringBuilder();
      int typeParameterIndex = 0;
      Map<TypeID, String> alreadyKnownParameters = new HashMap<>();

      for (FunctionParameter parameter : header.parameters) {
         TypeID parameterType = parameter.type;
         String id;
         if (alreadyKnownParameters.containsKey(parameterType)) {
            id = alreadyKnownParameters.get(parameterType);
         } else {
            JavaTypeInfo typeInfo = JavaTypeInfo.get(parameterType);
            id = typeInfo.primitive ? parameterType.accept(new JavaSyntheticTypeSignatureConverter()) : this.getTypeParameter(typeParameterIndex++);
            alreadyKnownParameters.put(parameterType, id);
         }

         signature.append(id);
      }

      signature.append("To");
      JavaTypeInfo typeInfo = JavaTypeInfo.get(header.getReturnType());
      String id = typeInfo.primitive ? header.getReturnType().accept(new JavaSyntheticTypeSignatureConverter()) : this.getTypeParameter(typeParameterIndex++);
      signature.append(id);
      return signature.toString();
   }

   private String getTypeParameter(int index) {
      switch (index) {
         case 0:
            return "T";
         case 1:
            return "U";
         case 2:
            return "V";
         case 3:
            return "W";
         case 4:
            return "X";
         case 5:
            return "Y";
         case 6:
            return "Z";
         default:
            return "T" + index;
      }
   }

   public JavaSynthesizedClass getRange(RangeTypeID type) {
      JavaTypeInfo typeInfo = JavaTypeInfo.get(type.baseType);
      String id = typeInfo.primitive ? type.accept(new JavaSyntheticTypeSignatureConverter()) : "T";
      JavaSynthesizedRange range;
      if (!this.ranges.containsKey(id)) {
         JavaClass cls = new JavaClass("zsynthetic", id, JavaClass.Kind.CLASS);
         if (typeInfo.primitive) {
            range = new JavaSynthesizedRange(cls, TypeParameter.NONE, type.baseType);
         } else {
            TypeParameter typeParameter = new TypeParameter(CodePosition.BUILTIN, "T");
            range = new JavaSynthesizedRange(cls, new TypeParameter[]{typeParameter}, this.registry.getGeneric(typeParameter));
         }

         this.ranges.put(id, range);
         this.getTypeGenerator().synthesizeRange(range);
      } else {
         range = this.ranges.get(id);
      }

      return typeInfo.primitive ? new JavaSynthesizedClass(range.cls, TypeID.NONE) : new JavaSynthesizedClass(range.cls, new TypeID[]{type.baseType});
   }

   public String getMethodDescriptor(FunctionHeader header, boolean isEnumConstructor, String expandedType) {
      StringBuilder descBuilder = new StringBuilder("(");
      if (expandedType != null) {
         descBuilder.append(expandedType);
      }

      for (int i = 0; i < header.getNumberOfTypeParameters(); i++) {
         descBuilder.append("Ljava/lang/Class;");
      }

      if (isEnumConstructor) {
         descBuilder.append("Ljava/lang/String;I");
      }

      for (FunctionParameter parameter : header.parameters) {
         descBuilder.append(this.getDescriptor(parameter.type));
      }

      descBuilder.append(")");
      descBuilder.append(this.getDescriptor(header.getReturnType()));
      return descBuilder.toString();
   }

   public String getMethodDescriptorConstructor(FunctionHeader header, DefinitionMember member) {
      StringBuilder startBuilder = new StringBuilder();

      for (TypeParameter typeParameter : member.definition.typeParameters) {
         startBuilder.append("Ljava/lang/Class;");
      }

      return this.getMethodDescriptor(header, member.definition instanceof EnumDefinition, startBuilder.toString());
   }

   public GlobalTypeRegistry getRegistry() {
      return this.registry;
   }

   public boolean isGenericOrContainsGenericParameters(TypeID typeID) {
      return typeID.accept(new JavaTypeCheckIfGenericVisitor());
   }
}
