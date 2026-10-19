package org.openzen.zencode.java.module.converters;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.util.function.BiConsumer;
import org.objectweb.asm.Type;
import org.openzen.zencode.java.ZenCodeType;
import org.openzen.zencode.java.module.JavaNativeTypeConversionContext;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.OperatorType;
import org.openzen.zenscript.codemodel.annotations.DefinitionAnnotation;
import org.openzen.zenscript.codemodel.annotations.NativeDefinitionAnnotation;
import org.openzen.zenscript.codemodel.definition.ClassDefinition;
import org.openzen.zenscript.codemodel.definition.EnumDefinition;
import org.openzen.zenscript.codemodel.definition.InterfaceDefinition;
import org.openzen.zenscript.codemodel.definition.StructDefinition;
import org.openzen.zenscript.codemodel.definition.ZSPackage;
import org.openzen.zenscript.codemodel.generic.ParameterTypeBound;
import org.openzen.zenscript.codemodel.generic.TypeParameter;
import org.openzen.zenscript.codemodel.member.CasterMember;
import org.openzen.zenscript.codemodel.member.ConstructorMember;
import org.openzen.zenscript.codemodel.member.EnumConstantMember;
import org.openzen.zenscript.codemodel.member.FieldMember;
import org.openzen.zenscript.codemodel.member.GetterMember;
import org.openzen.zenscript.codemodel.member.ImplementationMember;
import org.openzen.zenscript.codemodel.member.MethodMember;
import org.openzen.zenscript.codemodel.member.OperatorMember;
import org.openzen.zenscript.codemodel.member.SetterMember;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.DefinitionTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.member.BuiltinID;
import org.openzen.zenscript.javashared.JavaClass;
import org.openzen.zenscript.javashared.JavaField;
import org.openzen.zenscript.javashared.JavaImplementation;

public class JavaNativeClassConverter {
   private final JavaNativeTypeConverter typeConverter;
   private final JavaNativeMemberConverter memberConverter;
   private final JavaNativePackageInfo packageInfo;
   private final JavaNativeTypeConversionContext typeConversionContext;
   private final JavaNativeHeaderConverter headerConverter;

   public JavaNativeClassConverter(
      JavaNativeTypeConverter typeConverter,
      JavaNativeMemberConverter memberConverter,
      JavaNativePackageInfo packageInfo,
      JavaNativeTypeConversionContext typeConversionContext,
      JavaNativeHeaderConverter headerConverter
   ) {
      this.typeConverter = typeConverter;
      this.memberConverter = memberConverter;
      this.packageInfo = packageInfo;
      this.typeConversionContext = typeConversionContext;
      this.headerConverter = headerConverter;
   }

   public HighLevelDefinition convertClass(Class<?> cls) {
      HighLevelDefinition definition = this.checkRegistry(cls);
      boolean foundRegistry = definition != null;
      if (!foundRegistry) {
         definition = this.getDefinitionForClass(cls);
      }

      JavaClass javaClass = this.getJavaClassFor(cls, definition);
      this.typeConversionContext.definitionByClass.put(cls, definition);
      this.typeConversionContext.compiled.setClassInfo(definition, javaClass);
      return !this.shouldLoadClass(cls) ? definition : this.fillDefinition(cls, definition, javaClass, foundRegistry);
   }

   private JavaClass getJavaClassFor(Class<?> cls, HighLevelDefinition definition) {
      String internalName = Type.getInternalName(cls);
      JavaClass.Kind kind;
      if (definition instanceof EnumDefinition) {
         kind = JavaClass.Kind.ENUM;
      } else if (definition.isInterface()) {
         kind = JavaClass.Kind.INTERFACE;
      } else {
         kind = JavaClass.Kind.CLASS;
      }

      return JavaClass.fromInternalName(internalName, kind);
   }

   private HighLevelDefinition getDefinitionForClass(Class<?> cls) {
      boolean isStruct = cls.isAnnotationPresent(ZenCodeType.Struct.class);
      String specifiedName = this.getNameForScripts(cls);
      boolean hasAnnotation = cls.isAnnotationPresent(ZenCodeType.Name.class);
      String className = specifiedName.contains(".") ? specifiedName.substring(specifiedName.lastIndexOf(46) + 1) : specifiedName;
      ZSPackage classPkg;
      if (!hasAnnotation) {
         if (!specifiedName.startsWith(this.packageInfo.getPkg().fullName)) {
            classPkg = this.packageInfo.getPackage(className);
         } else {
            classPkg = this.packageInfo.getPackage(this.packageInfo.getBasePackage() + specifiedName.substring(this.packageInfo.getPkg().fullName.length()));
            className = specifiedName.substring(specifiedName.lastIndexOf(46) + 1);
         }
      } else if (specifiedName.startsWith(".")) {
         classPkg = this.packageInfo.getPackage(specifiedName);
         className = specifiedName.substring(specifiedName.lastIndexOf(46) + 1);
      } else if (specifiedName.indexOf(46) >= 0) {
         if (!specifiedName.startsWith(this.packageInfo.getPkg().fullName)) {
            throw new IllegalArgumentException(
               "Specified @Name as \""
                  + specifiedName
                  + "\" for class: \""
                  + cls.toString()
                  + "\" but it's not in the module root package: \""
                  + this.packageInfo.getPkg().fullName
                  + "\""
            );
         }

         classPkg = this.packageInfo.getPackage(this.packageInfo.getBasePackage() + specifiedName.substring(this.packageInfo.getPkg().fullName.length()));
         className = specifiedName.substring(specifiedName.lastIndexOf(46) + 1);
      } else {
         classPkg = this.packageInfo.getPackage(specifiedName);
         className = specifiedName;
      }

      if (cls.isInterface()) {
         return new InterfaceDefinition(CodePosition.NATIVE, this.packageInfo.getModule(), classPkg, className, 1, null);
      } else if (cls.isEnum()) {
         return new EnumDefinition(CodePosition.NATIVE, this.packageInfo.getModule(), classPkg, className, 1, null);
      } else {
         return isStruct
            ? new StructDefinition(CodePosition.NATIVE, this.packageInfo.getModule(), classPkg, className, 1, null)
            : new ClassDefinition(CodePosition.NATIVE, this.packageInfo.getModule(), classPkg, className, 1);
      }
   }

   private HighLevelDefinition checkRegistry(Class<?> cls) {
      String name = cls.getCanonicalName();
      if (!name.startsWith("java.lang.") && !name.startsWith("java.util.")) {
         return null;
      }

      name = name.substring("java.lang.".length());

      for (DefinitionTypeID definition : this.typeConversionContext.registry.getDefinitions()) {
         HighLevelDefinition highLevelDefinition = definition.definition;

         for (DefinitionAnnotation annotation : highLevelDefinition.annotations) {
            if (annotation instanceof NativeDefinitionAnnotation) {
               String identifier = ((NativeDefinitionAnnotation)annotation).getIdentifier();
               if (identifier.equals(name) || identifier.equals("stdlib::" + name)) {
                  return highLevelDefinition;
               }
            }
         }
      }

      return null;
   }

   public boolean shouldLoadType(java.lang.reflect.Type type) {
      if (!(type instanceof Class)) {
         return type instanceof ParameterizedType ? this.shouldLoadType(((ParameterizedType)type).getRawType()) : false;
      } else {
         return this.typeConversionContext.definitionByClass.containsKey(type) || this.shouldLoadClass((Class<?>)type);
      }
   }

   public boolean shouldLoadClass(Class<?> cls) {
      return this.packageInfo.isInBasePackage(this.getNameForScripts(cls));
   }

   public String getNameForScripts(Class<?> cls) {
      return cls.isAnnotationPresent(ZenCodeType.Name.class) ? cls.getAnnotation(ZenCodeType.Name.class).value() : cls.getName();
   }

   private HighLevelDefinition fillDefinition(Class<?> cls, HighLevelDefinition definition, JavaClass javaClass, boolean foundRegistry) {
      this.typeConversionContext.compiled.setClassInfo(definition, javaClass);
      this.fillTypeParameters(cls, definition, foundRegistry);
      this.fillSupertype(cls, definition, foundRegistry);
      this.fillImplementedInterfaces(cls, definition, javaClass);
      this.fillFields(cls, definition, javaClass);
      this.fillConstructor(cls, definition, javaClass, foundRegistry);
      this.fillDefaultMethods(cls, definition, javaClass);
      this.fillAnnotatedMethods(cls, definition, javaClass);
      return definition;
   }

   private void fillDefaultMethods(Class<?> cls, HighLevelDefinition definition, JavaClass javaClass) {
      try {
         BiConsumer<Method, String> createGetter = (method, name) -> {
            GetterMember member = this.memberConverter.asGetter(this.typeConversionContext.context, definition, method, name);
            definition.addMember(member);
            this.typeConversionContext.compiled.setMethodInfo(member, this.memberConverter.getMethod(javaClass, method, member.getType()));
         };
         BiConsumer<Method, String> createMethod = (method, name) -> {
            MethodMember member = this.memberConverter.asMethod(this.typeConversionContext.context, definition, method, name);
            definition.addMember(member);
            this.typeConversionContext.compiled.setMethodInfo(member, this.memberConverter.getMethod(javaClass, method, member.header.getReturnType()));
         };
         BiConsumer<Method, OperatorType> createOperator = (method, operator) -> {
            OperatorMember member = this.memberConverter.asOperator(this.typeConversionContext.context, definition, method, operator);
            definition.addMember(member);
            this.typeConversionContext.compiled.setMethodInfo(member, this.memberConverter.getMethod(javaClass, method, member.header.getReturnType()));
         };
         BiConsumer<Method, Boolean> createCaster = (method, implicit) -> {
            CasterMember member = this.memberConverter.asCaster(this.typeConversionContext.context, definition, method, implicit);
            definition.addMember(member);
            this.typeConversionContext.compiled.setMethodInfo(member, this.memberConverter.getMethod(javaClass, method, member.toType));
         };
         if (Enum.class.equals(cls)) {
            createMethod.accept(cls.getMethod("compareTo", Enum.class), "compareTo");
         }

         if (cls.isEnum()) {
            createMethod.accept(cls.getMethod("name"), "name");
            createGetter.accept(cls.getMethod("name"), "name");
            createMethod.accept(cls.getMethod("ordinal"), "ordinal");
            createGetter.accept(cls.getMethod("ordinal"), "ordinal");
            createMethod.accept(cls.getMethod("values"), "values");
            createGetter.accept(cls.getMethod("values"), "values");
            createMethod.accept(cls.getMethod("valueOf", String.class), "valueOf");
         }

         if (Object.class.equals(cls)) {
            createMethod.accept(cls.getMethod("toString"), "toString");
            createCaster.accept(cls.getMethod("toString"), false);
            createMethod.accept(cls.getMethod("hashCode"), "hashCode");
            createGetter.accept(cls.getMethod("hashCode"), "hashCode");
            createMethod.accept(cls.getMethod("equals", Object.class), "equals");
            createOperator.accept(cls.getMethod("equals", Object.class), OperatorType.EQUALS);
         }
      } catch (NoSuchMethodException e) {
         e.printStackTrace();
      }
   }

   private void fillAnnotatedMethods(Class<?> cls, HighLevelDefinition definition, JavaClass javaClass) {
      for (Method method : this.getMethodsIn(cls)) {
         if (!this.isNotAccessible(method) && !this.isOverridden(cls, method)) {
            ZenCodeType.Method methodAnnotation = this.getAnnotation(method, ZenCodeType.Method.class);
            if (methodAnnotation != null) {
               MethodMember member = this.memberConverter.asMethod(this.typeConversionContext.context, definition, method, methodAnnotation.value());
               definition.addMember(member);
               this.typeConversionContext.compiled.setMethodInfo(member, this.memberConverter.getMethod(javaClass, method, member.header.getReturnType()));
            }

            ZenCodeType.Getter getter = this.getAnnotation(method, ZenCodeType.Getter.class);
            if (getter != null) {
               GetterMember member = this.memberConverter.asGetter(this.typeConversionContext.context, definition, method, getter.value());
               definition.addMember(member);
               this.typeConversionContext.compiled.setMethodInfo(member, this.memberConverter.getMethod(javaClass, method, member.getType()));
            }

            ZenCodeType.Setter setter = this.getAnnotation(method, ZenCodeType.Setter.class);
            if (setter != null) {
               SetterMember member = this.memberConverter.asSetter(this.typeConversionContext.context, definition, method, setter.value());
               definition.addMember(member);
               this.typeConversionContext.compiled.setMethodInfo(member, this.memberConverter.getMethod(javaClass, method, BasicTypeID.VOID));
            }

            ZenCodeType.Operator operator = this.getAnnotation(method, ZenCodeType.Operator.class);
            if (operator != null) {
               OperatorMember member = this.memberConverter
                  .asOperator(this.typeConversionContext.context, definition, method, OperatorType.valueOf(operator.value().toString()));
               definition.addMember(member);
               this.typeConversionContext.compiled.setMethodInfo(member, this.memberConverter.getMethod(javaClass, method, member.header.getReturnType()));
            }

            ZenCodeType.Caster caster = this.getAnnotation(method, ZenCodeType.Caster.class);
            if (caster != null) {
               CasterMember member = this.memberConverter.asCaster(this.typeConversionContext.context, definition, method, caster.implicit());
               definition.addMember(member);
               this.typeConversionContext.compiled.setMethodInfo(member, this.memberConverter.getMethod(javaClass, method, member.toType));
            }
         }
      }
   }

   private boolean isNotAccessible(Method method) {
      return !Modifier.isPublic(method.getModifiers());
   }

   protected Method[] getMethodsIn(Class<?> cls) {
      return cls.getMethods();
   }

   protected <T extends Annotation> T getAnnotation(Method method, Class<T> cls) {
      return method.getAnnotation(cls);
   }

   protected ZenCodeType.Constructor getConstructorAnnotation(Constructor<?> constructor) {
      return constructor.getAnnotation(ZenCodeType.Constructor.class);
   }

   protected ZenCodeType.Field getFieldAnnotation(Field field) {
      return field.getAnnotation(ZenCodeType.Field.class);
   }

   private boolean isOverridden(Class<?> cls, Method method) {
      return !method.getDeclaringClass().equals(cls) || method.isBridge();
   }

   private void fillConstructor(Class<?> cls, HighLevelDefinition definition, JavaClass javaClass, boolean foundRegistry) {
      boolean hasConstructor = false;

      for (Constructor<?> constructor : cls.getConstructors()) {
         ZenCodeType.Constructor constructorAnnotation = this.getConstructorAnnotation(constructor);
         if (constructorAnnotation != null) {
            ConstructorMember member = this.memberConverter.asConstructor(this.typeConversionContext.context, definition, constructor);
            definition.addMember(member);
            this.typeConversionContext.compiled.setMethodInfo(member, this.memberConverter.getMethod(javaClass, constructor));
            hasConstructor = true;
         }
      }

      if (!hasConstructor && !foundRegistry) {
         ConstructorMember member = new ConstructorMember(
            CodePosition.BUILTIN, definition, 4, new FunctionHeader(BasicTypeID.VOID), BuiltinID.CLASS_DEFAULT_CONSTRUCTOR
         );
         definition.addMember(member);
      }
   }

   private void fillFields(Class<?> cls, HighLevelDefinition definition, JavaClass javaClass) {
      TypeID thisType = this.typeConversionContext.registry.getForMyDefinition(definition);

      for (Field field : cls.getDeclaredFields()) {
         if (Modifier.isPublic(field.getModifiers()) && !this.tryAddAsFieldMember(field, definition, thisType, javaClass)) {
            this.tryAddAsEnumConstantMember(field, definition);
         }
      }
   }

   private boolean tryAddAsFieldMember(Field field, HighLevelDefinition definition, TypeID thisType, JavaClass javaClass) {
      ZenCodeType.Field annotation = this.getFieldAnnotation(field);
      if (annotation == null) {
         return false;
      }

      String fieldName = field.getName();
      if (!annotation.value().isEmpty()) {
         fieldName = annotation.value();
      }

      TypeID fieldType = this.typeConverter.loadStoredType(this.typeConversionContext.context, field.getAnnotatedType());
      FieldMember fieldMember = new FieldMember(
         CodePosition.NATIVE,
         definition,
         this.headerConverter.getMethodModifiers(field),
         fieldName,
         thisType,
         fieldType,
         this.typeConversionContext.registry,
         0,
         0,
         null
      );
      definition.addMember(fieldMember);
      this.typeConversionContext.compiled.setFieldInfo(fieldMember, new JavaField(javaClass, field.getName(), Type.getDescriptor(field.getType())));
      return true;
   }

   private void tryAddAsEnumConstantMember(Field field, HighLevelDefinition definition) {
      if (field.isEnumConstant() && definition instanceof EnumDefinition) {
         try {
            Enum<?> enumConstant = (Enum<?>)field.get(null);
            int ordinal = enumConstant.ordinal();
            EnumConstantMember enumConstantMember = new EnumConstantMember(CodePosition.NATIVE, definition, enumConstant.name(), ordinal);
            ((EnumDefinition)definition).addEnumConstant(enumConstantMember);
            this.typeConversionContext.compiled.getEnumMapper().registerMapping((EnumDefinition)definition, enumConstantMember, field.getName());
         } catch (IllegalAccessException ex) {
            throw new IllegalArgumentException("Could not add enum member: " + ex);
         }
      }
   }

   private void fillImplementedInterfaces(Class<?> cls, HighLevelDefinition definition, JavaClass javaClass) {
      for (AnnotatedType iface : cls.getAnnotatedInterfaces()) {
         if (this.shouldLoadType(iface.getType())) {
            TypeID type = this.typeConverter.loadType(this.typeConversionContext.context, iface);
            ImplementationMember member = new ImplementationMember(CodePosition.NATIVE, definition, 1, type);
            definition.members.add(member);
            this.typeConversionContext.compiled.setImplementationInfo(member, new JavaImplementation(true, javaClass));
         }
      }
   }

   private void fillSupertype(Class<?> cls, HighLevelDefinition definition, boolean foundRegistry) {
      if (!foundRegistry
         && definition instanceof ClassDefinition
         && cls.getAnnotatedSuperclass() != null
         && this.shouldLoadType(cls.getAnnotatedSuperclass().getType())) {
         definition.setSuperType(this.typeConverter.loadType(this.typeConversionContext.context, cls.getAnnotatedSuperclass()));
      }

      if (!foundRegistry && definition.getSuperType() == null && cls != Object.class) {
         if (!(definition instanceof EnumDefinition)) {
            definition.setSuperType(this.typeConverter.loadType(this.typeConversionContext.context, Object.class, false, false));
         } else if (cls != Enum.class) {
            if (!(this.typeConverter.loadType(this.typeConversionContext.context, cls.getSuperclass(), false, false) instanceof DefinitionTypeID superDefTypeId)
               )
             {
               return;
            }

            DefinitionTypeID definitionTypeID = this.typeConversionContext.registry.getForMyDefinition(definition);
            definition.setSuperType(this.typeConversionContext.registry.getForDefinition(superDefTypeId.definition, definitionTypeID));
         }
      }
   }

   private void fillTypeParameters(Class<?> cls, HighLevelDefinition definition, boolean foundRegistry) {
      TypeVariable<?>[] javaTypeParameters = cls.getTypeParameters();
      if (!foundRegistry || definition.typeParameters == null || definition.typeParameters.length != cls.getTypeParameters().length) {
         definition.typeParameters = new TypeParameter[cls.getTypeParameters().length];
      }

      for (int i = 0; i < javaTypeParameters.length; i++) {
         TypeVariable<?> typeVariable = javaTypeParameters[i];
         TypeParameter parameter;
         if (foundRegistry && definition.typeParameters.length == cls.getTypeParameters().length) {
            parameter = definition.typeParameters[i];
         } else {
            parameter = new TypeParameter(CodePosition.NATIVE, typeVariable.getName());
         }

         definition.typeParameters[i] = parameter;
         this.typeConversionContext.context.put(typeVariable, parameter);
      }

      for (int i = 0; i < javaTypeParameters.length; i++) {
         TypeVariable<?> typeVariable = javaTypeParameters[i];
         TypeParameter parameter = definition.typeParameters[i];

         for (AnnotatedType bound : typeVariable.getAnnotatedBounds()) {
            if (bound.getType() != Object.class) {
               TypeID type = this.typeConverter.loadType(this.typeConversionContext.context, bound);
               parameter.addBound(new ParameterTypeBound(CodePosition.NATIVE, type));
            }
         }
      }
   }
}
