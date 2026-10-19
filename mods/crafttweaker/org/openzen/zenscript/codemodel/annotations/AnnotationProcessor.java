package org.openzen.zenscript.codemodel.annotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.ModuleProcessor;
import org.openzen.zenscript.codemodel.ScriptBlock;
import org.openzen.zenscript.codemodel.context.TypeResolutionContext;
import org.openzen.zenscript.codemodel.definition.ExpansionDefinition;
import org.openzen.zenscript.codemodel.member.CallerMember;
import org.openzen.zenscript.codemodel.member.CasterMember;
import org.openzen.zenscript.codemodel.member.ConstMember;
import org.openzen.zenscript.codemodel.member.ConstructorMember;
import org.openzen.zenscript.codemodel.member.DestructorMember;
import org.openzen.zenscript.codemodel.member.FieldMember;
import org.openzen.zenscript.codemodel.member.FunctionalMember;
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
import org.openzen.zenscript.codemodel.member.ref.GetterMemberRef;
import org.openzen.zenscript.codemodel.member.ref.SetterMemberRef;
import org.openzen.zenscript.codemodel.scope.DefinitionScope;
import org.openzen.zenscript.codemodel.scope.FileScope;
import org.openzen.zenscript.codemodel.scope.FunctionScope;
import org.openzen.zenscript.codemodel.scope.GlobalScriptScope;
import org.openzen.zenscript.codemodel.scope.StatementScope;
import org.openzen.zenscript.codemodel.statement.Statement;
import org.openzen.zenscript.codemodel.type.BasicTypeID;

public class AnnotationProcessor implements ModuleProcessor {
   private final TypeResolutionContext context;
   private final List<ExpansionDefinition> expansions;

   public AnnotationProcessor(TypeResolutionContext context, List<ExpansionDefinition> expansions) {
      this.context = context;
      this.expansions = expansions;
   }

   @Override
   public ScriptBlock process(ScriptBlock block) {
      FileScope fileScope = new FileScope(this.context, this.expansions, new HashMap<>(), member -> {});
      StatementScope scope = new GlobalScriptScope(fileScope, block.scriptHeader);
      List<Statement> transformed = new ArrayList<>();
      boolean unchanged = true;

      for (Statement statement : block.statements) {
         Statement newStatement = this.process(statement, scope);
         transformed.add(newStatement);
         unchanged &= statement == newStatement;
      }

      return unchanged ? block : block.withStatements(transformed);
   }

   @Override
   public void process(HighLevelDefinition definition) {
      FileScope fileScope = new FileScope(this.context, this.expansions, new HashMap<>(), member -> {});
      DefinitionScope scope = new DefinitionScope(fileScope, definition);

      for (DefinitionAnnotation annotation : definition.annotations) {
         annotation.apply(definition, scope);
      }

      AnnotationProcessor.MemberAnnotationVisitor visitor = new AnnotationProcessor.MemberAnnotationVisitor(scope);

      for (IDefinitionMember member : definition.members) {
         member.accept(visitor);
      }
   }

   private Statement process(Statement statement, StatementScope scope) {
      return statement.transform(s -> {
         for (StatementAnnotation annotation : s.annotations) {
            s = annotation.apply(s, scope);
         }

         return s;
      });
   }

   private class MemberAnnotationVisitor implements MemberVisitor<Void> {
      private final DefinitionScope scope;

      public MemberAnnotationVisitor(DefinitionScope scope) {
         this.scope = scope;
      }

      public Void visitConst(ConstMember member) {
         for (MemberAnnotation annotation : member.annotations) {
            annotation.apply(member, this.scope);
         }

         return null;
      }

      public Void visitField(FieldMember member) {
         for (MemberAnnotation annotation : member.annotations) {
            annotation.apply(member, this.scope);
         }

         return null;
      }

      public Void visitConstructor(ConstructorMember member) {
         return this.functional(member);
      }

      public Void visitDestructor(DestructorMember member) {
         return this.functional(member);
      }

      public Void visitMethod(MethodMember member) {
         return this.functional(member);
      }

      public Void visitGetter(GetterMember member) {
         return this.getter(member);
      }

      public Void visitSetter(SetterMember member) {
         return this.setter(member);
      }

      public Void visitOperator(OperatorMember member) {
         return this.functional(member);
      }

      public Void visitCaster(CasterMember member) {
         return this.functional(member);
      }

      public Void visitCustomIterator(IteratorMember member) {
         return this.functional(member);
      }

      public Void visitCaller(CallerMember member) {
         return this.functional(member);
      }

      public Void visitImplementation(ImplementationMember implementation) {
         for (IDefinitionMember member : implementation.members) {
            member.accept(this);
         }

         return null;
      }

      public Void visitInnerDefinition(InnerDefinitionMember member) {
         AnnotationProcessor.this.process(member.innerDefinition);
         return null;
      }

      public Void visitStaticInitializer(StaticInitializerMember member) {
         StatementScope scope = new FunctionScope(member.position, this.scope, new FunctionHeader(BasicTypeID.VOID));
         if (member.body == null) {
            throw new IllegalStateException("No body in static initializer @ " + member.position);
         }

         member.body = AnnotationProcessor.this.process(member.body, scope);
         return null;
      }

      private Void functional(FunctionalMember member) {
         for (MemberAnnotation annotation : member.annotations) {
            annotation.apply(member, this.scope);
         }

         if (member.getOverrides() != null) {
            this.functional(member, member.getOverrides());
         }

         if (member.body == null) {
            return null;
         }

         StatementScope scope = new FunctionScope(member.position, this.scope, member.header);
         member.body = AnnotationProcessor.this.process(member.body, scope);
         return null;
      }

      private Void getter(GetterMember member) {
         for (MemberAnnotation annotation : member.annotations) {
            annotation.apply(member, this.scope);
         }

         if (member.getOverrides() != null) {
            this.getter(member, member.getOverrides());
         }

         if (member.body == null) {
            return null;
         }

         StatementScope scope = new FunctionScope(member.position, this.scope, new FunctionHeader(member.getType()));
         member.body = AnnotationProcessor.this.process(member.body, scope);
         return null;
      }

      private Void setter(SetterMember member) {
         for (MemberAnnotation annotation : member.annotations) {
            annotation.apply(member, this.scope);
         }

         if (member.getOverrides() != null) {
            this.setter(member, member.getOverrides());
         }

         if (member.body == null) {
            return null;
         }

         StatementScope scope = new FunctionScope(member.position, this.scope, new FunctionHeader(BasicTypeID.VOID, member.parameter));
         member.body = AnnotationProcessor.this.process(member.body, scope);
         return null;
      }

      private void functional(FunctionalMember member, DefinitionMemberRef overrides) {
         for (MemberAnnotation annotation : overrides.getAnnotations()) {
            annotation.applyOnOverridingMethod(member, this.scope);
         }

         if (overrides.getOverrides() != null) {
            this.functional(member, overrides.getOverrides());
         }
      }

      private void getter(GetterMember member, GetterMemberRef overrides) {
         for (MemberAnnotation annotation : overrides.getAnnotations()) {
            annotation.applyOnOverridingGetter(member, this.scope);
         }

         if (overrides.getOverrides() != null) {
            this.getter(member, overrides.getOverrides());
         }
      }

      private void setter(SetterMember member, SetterMemberRef overrides) {
         for (MemberAnnotation annotation : overrides.getAnnotations()) {
            annotation.applyOnOverridingSetter(member, this.scope);
         }

         if (overrides.getOverrides() != null) {
            this.setter(member, overrides.getOverrides());
         }
      }
   }
}
