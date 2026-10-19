package org.openzen.zencode.java.module.converters;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.objectweb.asm.Type;
import org.openzen.zencode.java.ZenCodeType;
import org.openzen.zencode.java.module.JavaNativeModule;
import org.openzen.zencode.java.module.JavaNativeTypeConversionContext;
import org.openzen.zencode.java.module.TypeVariableContext;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.LiteralSourceFile;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.GenericMapper;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.annotations.AnnotationDefinition;
import org.openzen.zenscript.codemodel.context.CompilingPackage;
import org.openzen.zenscript.codemodel.context.ModuleTypeResolutionContext;
import org.openzen.zenscript.codemodel.definition.ZSPackage;
import org.openzen.zenscript.codemodel.generic.ParameterTypeBound;
import org.openzen.zenscript.codemodel.generic.TypeParameter;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.DefinitionTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.javashared.JavaClass;
import org.openzen.zenscript.javashared.JavaMethod;
import org.openzen.zenscript.javashared.types.JavaFunctionalInterfaceTypeID;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.parser.BracketExpressionParser;
import org.openzen.zenscript.parser.type.IParsedType;

public class JavaNativeTypeConverter {
   private final Map<Class<?>, TypeID> typeByClass = new HashMap<>();
   private final Map<Class<?>, TypeID> unsignedByClass = new HashMap<>();
   private final JavaNativePackageInfo packageInfo;
   private final JavaNativeModule javaNativeModule;
   private final JavaNativeTypeConversionContext typeConversionContext;
   private BracketExpressionParser bep;
   private JavaNativeHeaderConverter headerConverter;

   public JavaNativeTypeConverter(JavaNativeTypeConversionContext typeConversionContext, JavaNativePackageInfo packageInfo, JavaNativeModule javaNativeModule) {
      this.typeConversionContext = typeConversionContext;
      this.packageInfo = packageInfo;
      this.javaNativeModule = javaNativeModule;
      this.fillByClassMaps();
   }

   public TypeID loadStoredType(TypeVariableContext context, AnnotatedType annotatedType) {
      return this.loadType(context, annotatedType);
   }

   public TypeID loadStoredType(TypeVariableContext context, Parameter parameter) {
      TypeID type = this.loadStoredType(context, parameter.getAnnotatedType());
      return parameter.isAnnotationPresent(ZenCodeType.Optional.class) && !type.isOptional() ? this.typeConversionContext.registry.getOptional(type) : type;
   }

   @Deprecated
   public TypeID loadType(TypeVariableContext context, AnnotatedElement element) {
      try {
         return this.loadType(context, JavaAnnotatedType.of(element));
      } catch (IllegalArgumentException e) {
         throw new IllegalArgumentException("Unable to analyze type: " + element, e);
      }
   }

   public TypeID loadType(TypeVariableContext context, JavaAnnotatedType annotatedType) {
      if (annotatedType.isAnnotationPresent(ZenCodeType.USize.class)) {
         return BasicTypeID.USIZE;
      }

      if (annotatedType.isAnnotationPresent(ZenCodeType.NullableUSize.class)) {
         return this.typeConversionContext.registry.getOptional(BasicTypeID.USIZE);
      }

      boolean nullable = annotatedType.isAnnotationPresent(ZenCodeType.Nullable.class) || annotatedType.isAnnotationPresent(ZenCodeType.Optional.class);
      boolean unsigned = annotatedType.isAnnotationPresent(ZenCodeType.Unsigned.class);
      return this.loadType(context, annotatedType, nullable, unsigned);
   }

   @Deprecated
   public TypeID loadType(TypeVariableContext context, AnnotatedElement element, boolean nullable, boolean unsigned) {
      try {
         return this.loadType(context, JavaAnnotatedType.of(element), nullable, unsigned);
      } catch (IllegalArgumentException e) {
         throw new IllegalArgumentException("Unable to analyze type: " + element, e);
      }
   }

   public TypeID loadType(TypeVariableContext context, JavaAnnotatedType type, boolean nullable, boolean unsigned) {
      TypeID result = this.loadType(context, type, unsigned);
      return nullable ? this.typeConversionContext.registry.getOptional(result) : result;
   }

   private TypeID loadType(TypeVariableContext context, JavaAnnotatedType type, boolean unsigned) {
      JavaAnnotatedType.ElementType elementType = type.getElementType();

      try {
         switch (elementType) {
            case ANNOTATED_PARAMETERIZED_TYPE:
               return this.loadAnnotatedParameterizedType(context, (AnnotatedParameterizedType)type.getAnnotatedElement(), unsigned);
            case ANNOTATED_TYPE:
               return this.loadAnnotatedType(context, (AnnotatedType)type.getAnnotatedElement(), unsigned);
            case CLASS:
               return this.loadClass(context, (Class<?>)type.getType(), unsigned);
            case GENERIC_ARRAY:
               return this.loadGenericArray(context, (GenericArrayType)type.getType(), unsigned);
            case PARAMETERIZED_TYPE:
               return this.loadParameterizedType(context, (ParameterizedType)type.getType());
            case TYPE_VARIABLE:
               return this.loadTypeVariable(context, (TypeVariable<?>)type.getType());
            case WILDCARD:
               return this.loadWildcard();
         }
      } catch (IllegalArgumentException e) {
         throw new IllegalArgumentException("Unable to analyze type: " + type, e);
      }

      throw new IllegalArgumentException("Invalid type " + elementType + ": not yet implemented or foolery");
   }

   private TypeID loadAnnotatedParameterizedType(TypeVariableContext context, AnnotatedParameterizedType type, boolean unsigned) {
      ParameterizedType parameterizedType = this.getTypeIfValid(JavaAnnotatedType.of(type.getType()), JavaAnnotatedType.ElementType.PARAMETERIZED_TYPE);
      JavaAnnotatedType rawType = JavaAnnotatedType.of(parameterizedType.getRawType());
      JavaAnnotatedType[] actualTypeArguments = JavaAnnotatedType.arrayOf(type.getAnnotatedActualTypeArguments());
      TypeID[] codeParameters = new TypeID[actualTypeArguments.length];

      for (int i = 0; i < actualTypeArguments.length; i++) {
         codeParameters[i] = this.loadType(context, actualTypeArguments[i], false, false);
      }

      if (rawType.getElementType() != JavaAnnotatedType.ElementType.CLASS) {
         return this.loadType(context, JavaAnnotatedType.of(type), unsigned);
      }

      if (rawType.getType() == Map.class) {
         return this.typeConversionContext.registry.getAssociative(codeParameters[0], codeParameters[1]);
      }

      Map<TypeParameter, TypeID> map = new HashMap<>();
      JavaAnnotatedType[] typeParameters = JavaAnnotatedType.arrayOf(((Class)rawType.getType()).getTypeParameters());
      TypeID rawTypeId = this.loadType(context, rawType, unsigned);

      for (int i = 0; i < typeParameters.length; i++) {
         TypeVariable<?> typeVariable = this.getTypeIfValid(typeParameters[i], JavaAnnotatedType.ElementType.TYPE_VARIABLE);
         TypeParameter typeParameter = context.get(typeVariable);
         map.put(typeParameter, codeParameters[i]);
      }

      return rawTypeId.instance(new GenericMapper(CodePosition.NATIVE, this.typeConversionContext.registry, map));
   }

   private TypeID loadAnnotatedType(TypeVariableContext context, AnnotatedType type, boolean unsigned) {
      JavaAnnotatedType annotatedType = JavaAnnotatedType.of(type.getType());
      return this.loadType(context, annotatedType, unsigned);
   }

   private TypeID loadClass(TypeVariableContext context, Class<?> type, boolean unsigned) {
      if (unsigned) {
         return this.unsignedByClass.computeIfAbsent(type, it -> {
            throw new IllegalArgumentException("This class cannot be used as unsigned: " + it);
         });
      }

      if (type.isArray()) {
         TypeID baseType = this.loadType(context, JavaAnnotatedType.of(type.getComponentType()), false, false);
         return this.typeConversionContext.registry.getArray(baseType, 1);
      }

      if (type.isAnnotationPresent(FunctionalInterface.class)) {
         return this.loadFunctionalInterface(context, type);
      }

      if (this.typeByClass.containsKey(type)) {
         return this.typeByClass.get(type);
      }

      HighLevelDefinition definition = this.javaNativeModule.addClass(type);
      List<TypeID> typeParameters = new ArrayList<>();

      for (TypeVariable<? extends Class<?>> typeParameter : type.getTypeParameters()) {
         typeParameters.add(this.typeConversionContext.registry.getGeneric(context.get(typeParameter)));
      }

      return this.typeConversionContext.registry.getForDefinition(definition, typeParameters.toArray(TypeID.NONE));
   }

   private TypeID loadGenericArray(TypeVariableContext context, GenericArrayType type, boolean unsigned) {
      JavaAnnotatedType componentType = JavaAnnotatedType.of(type.getGenericComponentType());
      TypeID baseType = this.loadType(context, componentType, unsigned);
      return this.typeConversionContext.registry.getArray(baseType, 1);
   }

   private TypeID loadParameterizedType(TypeVariableContext context, ParameterizedType type) {
      Class<?> rawType = this.getTypeIfValid(JavaAnnotatedType.of(type.getRawType()), JavaAnnotatedType.ElementType.CLASS);
      JavaAnnotatedType[] typeArguments = JavaAnnotatedType.arrayOf(type.getActualTypeArguments());
      if (rawType.isAnnotationPresent(FunctionalInterface.class)) {
         return this.loadFunctionalInterface(context, rawType, typeArguments);
      }

      TypeID[] codeParameters = new TypeID[typeArguments.length];

      for (int i = 0; i < typeArguments.length; i++) {
         codeParameters[i] = this.loadType(context, typeArguments[i], false, false);
      }

      if (rawType == Map.class) {
         return this.typeConversionContext.registry.getAssociative(codeParameters[0], codeParameters[1]);
      }

      HighLevelDefinition definition = this.javaNativeModule.addClass(rawType);
      return this.typeConversionContext.registry.getForDefinition(definition, codeParameters);
   }

   private TypeID loadTypeVariable(TypeVariableContext context, TypeVariable<?> variable) {
      return this.typeConversionContext.registry.getGeneric(context.get(variable));
   }

   private TypeID loadWildcard() {
      return BasicTypeID.UNDETERMINED;
   }

   private <T> T getTypeIfValid(JavaAnnotatedType type, JavaAnnotatedType.ElementType expected) {
      if (type.getElementType() != expected) {
         throw new IllegalArgumentException(expected + " was expected as a type, but " + type + " was found");
      } else {
         return (T)type.getType();
      }
   }

   public Class<?> getClassFromType(TypeID type) {
      if (type instanceof DefinitionTypeID definitionType) {
         for (Entry<Class<?>, HighLevelDefinition> ent : this.typeConversionContext.definitionByClass.entrySet()) {
            if (ent.getValue().equals(definitionType.definition)) {
               return ent.getKey();
            }
         }
      }

      for (Entry<Class<?>, TypeID> ent : this.typeByClass.entrySet()) {
         if (ent.getValue().equals(type)) {
            return ent.getKey();
         }
      }

      for (Entry<Class<?>, TypeID> ent : this.unsignedByClass.entrySet()) {
         if (ent.getValue().equals(type)) {
            return ent.getKey();
         }
      }

      return null;
   }

   public TypeID getTypeFromName(String className) {
      for (TypeID value : this.typeByClass.values()) {
         if (value.toString().equals(className)) {
            return value;
         }
      }

      for (TypeID value : this.unsignedByClass.values()) {
         if (value.toString().equals(className)) {
            return value;
         }
      }

      try {
         ZSPackage zsPackage = this.packageInfo.getPackage(className);
         String[] split = className.split("\\.");
         String actualName = split[split.length - 1];

         for (HighLevelDefinition value : this.typeConversionContext.definitionByClass.values()) {
            if (actualName.equals(value.name) && value.pkg.equals(zsPackage)) {
               return this.typeConversionContext.registry.getForMyDefinition(value);
            }
         }
      } catch (IllegalArgumentException var8) {
      }

      CompilingPackage rootCompiling = new CompilingPackage(this.packageInfo.getPkg().parent, this.packageInfo.getModule());
      ModuleTypeResolutionContext context = new ModuleTypeResolutionContext(
         this.typeConversionContext.registry, new AnnotationDefinition[0], this.packageInfo.getPkg().parent, rootCompiling, this.typeConversionContext.globals
      );

      try {
         ZSTokenParser tokens = ZSTokenParser.create(new LiteralSourceFile("type reading: " + className, className), this.bep);
         return IParsedType.parse(tokens).compile(context);
      } catch (Exception var7) {
         return null;
      }
   }

   private TypeID loadFunctionalInterface(TypeVariableContext loadContext, Class<?> cls, JavaAnnotatedType... parameters) {
      Method functionalInterfaceMethod = this.getFunctionalInterfaceMethod(cls);
      TypeVariableContext context = this.convertTypeParameters(cls);
      FunctionHeader header = this.headerConverter.getHeader(context, functionalInterfaceMethod);
      Map<TypeParameter, TypeID> mapping = new HashMap<>();
      TypeVariable<?>[] javaParameters = cls.getTypeParameters();

      for (int i = 0; i < parameters.length; i++) {
         mapping.put(context.get(javaParameters[i]), this.loadType(loadContext, parameters[i], false, false));
      }

      header = header.withGenericArguments(new GenericMapper(CodePosition.NATIVE, this.typeConversionContext.registry, mapping));
      JavaMethod method = new JavaMethod(
         JavaClass.fromInternalName(Type.getInternalName(cls), JavaClass.Kind.INTERFACE),
         JavaMethod.Kind.INTERFACE,
         functionalInterfaceMethod.getName(),
         false,
         Type.getMethodDescriptor(functionalInterfaceMethod),
         1025,
         header.getReturnType().isGeneric()
      );
      return this.typeConversionContext
         .registry
         .internalize(
            JavaFunctionalInterfaceTypeID.class,
            header,
            functionHeader -> new JavaFunctionalInterfaceTypeID(this.typeConversionContext.registry, functionHeader, functionalInterfaceMethod, method)
         );
   }

   private void fillByClassMaps() {
      this.typeByClass.put(void.class, BasicTypeID.VOID);
      this.typeByClass.put(boolean.class, BasicTypeID.BOOL);
      this.typeByClass.put(byte.class, BasicTypeID.SBYTE);
      this.typeByClass.put(char.class, BasicTypeID.CHAR);
      this.typeByClass.put(short.class, BasicTypeID.SHORT);
      this.typeByClass.put(int.class, BasicTypeID.INT);
      this.typeByClass.put(long.class, BasicTypeID.LONG);
      this.typeByClass.put(float.class, BasicTypeID.FLOAT);
      this.typeByClass.put(double.class, BasicTypeID.DOUBLE);
      this.typeByClass.put(String.class, BasicTypeID.STRING);
      this.typeByClass.put(Boolean.class, this.typeConversionContext.registry.getOptional(BasicTypeID.BOOL));
      this.typeByClass.put(Byte.class, this.typeConversionContext.registry.getOptional(BasicTypeID.BYTE));
      this.typeByClass.put(Short.class, this.typeConversionContext.registry.getOptional(BasicTypeID.SHORT));
      this.typeByClass.put(Integer.class, this.typeConversionContext.registry.getOptional(BasicTypeID.INT));
      this.typeByClass.put(Long.class, this.typeConversionContext.registry.getOptional(BasicTypeID.LONG));
      this.typeByClass.put(Float.class, this.typeConversionContext.registry.getOptional(BasicTypeID.FLOAT));
      this.typeByClass.put(Double.class, this.typeConversionContext.registry.getOptional(BasicTypeID.DOUBLE));
      this.unsignedByClass.put(byte.class, BasicTypeID.BYTE);
      this.unsignedByClass.put(char.class, BasicTypeID.CHAR);
      this.unsignedByClass.put(short.class, BasicTypeID.USHORT);
      this.unsignedByClass.put(int.class, BasicTypeID.UINT);
      this.unsignedByClass.put(long.class, BasicTypeID.ULONG);
      this.unsignedByClass.put(Byte.class, this.typeConversionContext.registry.getOptional(BasicTypeID.BYTE));
      this.unsignedByClass.put(Short.class, this.typeConversionContext.registry.getOptional(BasicTypeID.SHORT));
      this.unsignedByClass.put(Integer.class, this.typeConversionContext.registry.getOptional(BasicTypeID.INT));
      this.unsignedByClass.put(Long.class, this.typeConversionContext.registry.getOptional(BasicTypeID.LONG));
   }

   public void setBEP(BracketExpressionParser bep) {
      this.bep = bep;
   }

   private <T> TypeVariableContext convertTypeParameters(Class<T> cls) {
      TypeVariable<Class<T>>[] javaTypeParameters = cls.getTypeParameters();
      TypeParameter[] typeParameters = new TypeParameter[cls.getTypeParameters().length];

      for (int i = 0; i < javaTypeParameters.length; i++) {
         TypeVariable<Class<T>> typeVariable = javaTypeParameters[i];
         TypeParameter parameter = new TypeParameter(CodePosition.NATIVE, typeVariable.getName());

         for (AnnotatedType bound : typeVariable.getAnnotatedBounds()) {
            TypeID type = this.loadType(this.typeConversionContext.context, bound);
            parameter.addBound(new ParameterTypeBound(CodePosition.NATIVE, type));
         }

         typeParameters[i] = parameter;
         this.typeConversionContext.context.put(typeVariable, parameter);
      }

      for (int i = 0; i < javaTypeParameters.length; i++) {
         for (AnnotatedType bound : javaTypeParameters[i].getAnnotatedBounds()) {
            TypeID type = this.loadType(this.typeConversionContext.context, bound);
            typeParameters[i].addBound(new ParameterTypeBound(CodePosition.NATIVE, type));
         }
      }

      return this.typeConversionContext.context;
   }

   private Method getFunctionalInterfaceMethod(Class<?> functionalInterface) {
      for (Method method : functionalInterface.getMethods()) {
         if (Modifier.isPublic(method.getModifiers()) && Modifier.isAbstract(method.getModifiers()) && !method.isDefault()) {
            return method;
         }
      }

      throw new IllegalArgumentException("Could not find functionalInterface method for class " + functionalInterface.getCanonicalName());
   }

   public void setHeaderConverter(JavaNativeHeaderConverter headerConverter) {
      this.headerConverter = headerConverter;
   }
}
