package org.openzen.zenscript.javashared.prepare;

import org.openzen.zencode.shared.StringExpansion;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.OperatorType;
import org.openzen.zenscript.codemodel.annotations.NativeTag;
import org.openzen.zenscript.codemodel.member.CallerMember;
import org.openzen.zenscript.codemodel.member.CasterMember;
import org.openzen.zenscript.codemodel.member.ConstMember;
import org.openzen.zenscript.codemodel.member.ConstructorMember;
import org.openzen.zenscript.codemodel.member.DefinitionMember;
import org.openzen.zenscript.codemodel.member.DestructorMember;
import org.openzen.zenscript.codemodel.member.FieldMember;
import org.openzen.zenscript.codemodel.member.GetterMember;
import org.openzen.zenscript.codemodel.member.IDefinitionMember;
import org.openzen.zenscript.codemodel.member.ImplementationMember;
import org.openzen.zenscript.codemodel.member.InnerDefinitionMember;
import org.openzen.zenscript.codemodel.member.IteratorMember;
import org.openzen.zenscript.codemodel.member.MemberVisitor;
import org.openzen.zenscript.codemodel.member.MethodMember;
import org.openzen.zenscript.codemodel.member.OperatorMember;
import org.openzen.zenscript.codemodel.member.SetterMember;
import org.openzen.zenscript.codemodel.member.StaticInitializerMember;
import org.openzen.zenscript.codemodel.member.ref.DefinitionMemberRef;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.GenericTypeID;
import org.openzen.zenscript.codemodel.type.member.BuiltinID;
import org.openzen.zenscript.javashared.JavaClass;
import org.openzen.zenscript.javashared.JavaCompiledModule;
import org.openzen.zenscript.javashared.JavaContext;
import org.openzen.zenscript.javashared.JavaField;
import org.openzen.zenscript.javashared.JavaImplementation;
import org.openzen.zenscript.javashared.JavaMethod;
import org.openzen.zenscript.javashared.JavaModifiers;
import org.openzen.zenscript.javashared.JavaNativeClass;
import org.openzen.zenscript.javashared.JavaTypeNameVisitor;

public class JavaPrepareClassMethodVisitor implements MemberVisitor<Void> {
   private static final boolean DEBUG_EMPTY = true;
   private final JavaContext context;
   private final JavaCompiledModule module;
   private final JavaClass cls;
   private final JavaNativeClass nativeClass;
   private final JavaPrepareDefinitionMemberVisitor memberPreparer;

   public JavaPrepareClassMethodVisitor(
      JavaContext context,
      JavaCompiledModule module,
      JavaClass cls,
      JavaNativeClass nativeClass,
      JavaPrepareDefinitionMemberVisitor memberPreparer,
      boolean startsEmpty
   ) {
      this.context = context;
      this.module = module;
      this.cls = cls;
      this.nativeClass = nativeClass;
      this.memberPreparer = memberPreparer;
      cls.empty = startsEmpty;
   }

   public Void visitConst(ConstMember member) {
      if (this.cls.empty) {
         this.context.logger.trace("Class " + this.cls.fullName + " not empty because of const " + member.name);
      }

      this.cls.empty = false;
      this.module
         .setFieldInfo(member, new JavaField(this.cls, member.name, this.context.getDescriptor(member.getType()), this.context.getSignature(member.getType())));
      return null;
   }

   public Void visitField(FieldMember member) {
      JavaField field = new JavaField(this.cls, member.name, this.context.getDescriptor(member.getType()), this.context.getSignature(member.getType()));
      this.module.setFieldInfo(member, field);
      if (member.hasAutoGetter()) {
         this.visitGetter(member.autoGetter);
         this.module.setFieldInfo(member.autoGetter, field);
      }

      if (member.hasAutoSetter()) {
         this.visitSetter(member.autoSetter);
         this.module.setFieldInfo(member.autoSetter, field);
      }

      return null;
   }

   public Void visitConstructor(ConstructorMember member) {
      this.visitFunctional(member, member.header, "<init>");
      return null;
   }

   public Void visitDestructor(DestructorMember member) {
      if (this.cls.empty) {
         this.context.logger.trace("Class " + this.cls.fullName + " not empty because of destructor");
      }

      this.cls.empty = false;
      return null;
   }

   public Void visitMethod(MethodMember member) {
      this.visitFunctional(member, member.header, member.name);
      return null;
   }

   public Void visitGetter(GetterMember member) {
      this.visitFunctional(member, new FunctionHeader(member.getType()), "get" + StringExpansion.capitalize(member.name));
      return null;
   }

   public Void visitSetter(SetterMember member) {
      this.visitFunctional(member, new FunctionHeader(BasicTypeID.VOID, member.getType()), "set" + StringExpansion.capitalize(member.name));
      return null;
   }

   public Void visitOperator(OperatorMember member) {
      this.visitFunctional(member, member.header, this.getOperatorName(member.operator));
      return null;
   }

   public Void visitCaster(CasterMember member) {
      this.visitFunctional(member, member.header, "to" + JavaTypeNameVisitor.INSTANCE.process(member.toType));
      return null;
   }

   public Void visitCustomIterator(IteratorMember member) {
      this.visitFunctional(member, member.header, member.getLoopVariableCount() == 1 ? "iterator" : "iterator" + member.getLoopVariableCount());
      return null;
   }

   public Void visitCaller(CallerMember member) {
      this.visitFunctional(member, member.header, "call");
      return null;
   }

   public Void visitImplementation(ImplementationMember member) {
      this.memberPreparer.prepare(member.type);
      if (this.canMergeImplementation(member)) {
         this.module.setImplementationInfo(member, new JavaImplementation(true, this.cls));

         for (IDefinitionMember m : member.members) {
            m.accept(this);
         }
      } else {
         if (this.cls.empty) {
            this.context.logger.trace("Class " + this.cls.fullName + " not empty because of unmergeable implementation");
         }

         this.cls.empty = false;
         JavaClass implementationClass = new JavaClass(this.cls, JavaTypeNameVisitor.INSTANCE.process(member.type) + "Implementation", JavaClass.Kind.CLASS);
         this.module.setImplementationInfo(member, new JavaImplementation(false, implementationClass));
         JavaPrepareClassMethodVisitor visitor = new JavaPrepareClassMethodVisitor(
            this.context, this.module, implementationClass, null, this.memberPreparer, true
         );

         for (IDefinitionMember m : member.members) {
            m.accept(visitor);
         }
      }

      return null;
   }

   private boolean canMergeImplementation(ImplementationMember member) {
      return true;
   }

   public Void visitInnerDefinition(InnerDefinitionMember member) {
      JavaPrepareDefinitionMemberVisitor innerDefinitionPrepare = new JavaPrepareDefinitionMemberVisitor(this.context, this.module);
      member.innerDefinition.accept(innerDefinitionPrepare);
      if (this.cls.empty) {
         this.context.logger.trace("Class " + this.cls.fullName + " not empty because of inner definition " + member.innerDefinition.name);
      }

      this.cls.empty = false;
      return null;
   }

   public Void visitStaticInitializer(StaticInitializerMember member) {
      if (this.cls.empty) {
         this.context.logger.trace("Class " + this.cls.fullName + " not empty because of static initializer");
      }

      this.cls.empty = false;
      return null;
   }

   private JavaMethod.Kind getKind(DefinitionMember member) {
      if (member instanceof ConstructorMember) {
         return JavaMethod.Kind.CONSTRUCTOR;
      } else {
         return member.isStatic() ? JavaMethod.Kind.STATIC : JavaMethod.Kind.INSTANCE;
      }
   }

   private String getOperatorName(OperatorType operator) {
      switch (operator) {
         case NEG:
            return "negate";
         case NOT:
            return "invert";
         case ADD:
            return "add";
         case ADDASSIGN:
            return "addAssign";
         case SUB:
            return "subtract";
         case SUBASSIGN:
            return "subAssign";
         case CAT:
            return "concat";
         case CATASSIGN:
            return "append";
         case MUL:
            return "mul";
         case MULASSIGN:
            return "mulAssign";
         case DIV:
            return "div";
         case DIVASSIGN:
            return "divAssign";
         case MOD:
            return "mod";
         case MODASSIGN:
            return "modAssign";
         case AND:
            return "and";
         case ANDASSIGN:
            return "andAssign";
         case OR:
            return "or";
         case ORASSIGN:
            return "orAssign";
         case XOR:
            return "xor";
         case XORASSIGN:
            return "xorAssign";
         case SHL:
            return "shl";
         case SHLASSIGN:
            return "shlAssign";
         case SHR:
            return "shr";
         case SHRASSIGN:
            return "shrAssign";
         case USHR:
            return "ushr";
         case USHRASSIGN:
            return "ushrAssign";
         case INDEXGET:
            return "getAt";
         case INDEXSET:
            return "setAt";
         case INCREMENT:
            return "increment";
         case DECREMENT:
            return "decrement";
         case CONTAINS:
            return "contains";
         case EQUALS:
            return "equals_";
         case COMPARE:
            return "compareTo";
         case RANGE:
            return "until";
         case CAST:
            return "cast";
         case CALL:
            return "call";
         case MEMBERGETTER:
            return "getMember";
         case MEMBERSETTER:
            return "setMember";
         default:
            throw new IllegalArgumentException("Invalid operator: " + operator);
      }
   }

   private void visitFunctional(DefinitionMember member, FunctionHeader header, String name) {
      NativeTag nativeTag = member.getTag(NativeTag.class);
      JavaMethod method = null;
      if (nativeTag != null && this.nativeClass != null) {
         method = this.nativeClass.getMethod(nativeTag.value);
      }

      int modifiers = this.cls.kind == JavaClass.Kind.INTERFACE ? 1024 : 0;
      if (member.getOverrides() != null) {
         DefinitionMemberRef base = member.getOverrides();
         JavaMethod baseMethod = this.context.getJavaMethod(base.getTarget());
         method = new JavaMethod(
            this.cls,
            baseMethod.kind,
            baseMethod.name,
            true,
            this.context.getMethodDescriptor(header),
            modifiers | JavaModifiers.getJavaModifiers(member.getEffectiveModifiers()),
            header.getReturnType() instanceof GenericTypeID,
            header.useTypeParameters()
         );
      } else if (method == null) {
         if (member instanceof ConstructorMember) {
            method = new JavaMethod(
               this.cls,
               this.getKind(member),
               name,
               true,
               this.context.getMethodDescriptorConstructor(header, member),
               modifiers | JavaModifiers.getJavaModifiers(member.getEffectiveModifiers()),
               false,
               header.useTypeParameters()
            );
         } else {
            method = new JavaMethod(
               this.cls,
               this.getKind(member),
               name,
               true,
               this.context.getMethodDescriptor(header),
               modifiers | JavaModifiers.getJavaModifiers(member.getEffectiveModifiers()),
               header.getReturnType() instanceof GenericTypeID,
               header.useTypeParameters()
            );
         }
      }

      if (method.compile && member.getBuiltin() != BuiltinID.CLASS_DEFAULT_CONSTRUCTOR) {
         if (this.cls.empty) {
            this.context.logger.trace("Class " + this.cls.fullName + " not empty because of " + member.describe());
         }

         this.cls.empty = false;
      }

      this.module.setMethodInfo(member, method);
   }
}
