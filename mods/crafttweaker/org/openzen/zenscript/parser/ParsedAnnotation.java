package org.openzen.zenscript.parser;

import java.util.ArrayList;
import java.util.List;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zencode.shared.CompileExceptionCode;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.annotations.AnnotationDefinition;
import org.openzen.zenscript.codemodel.annotations.DefinitionAnnotation;
import org.openzen.zenscript.codemodel.annotations.InvalidDefinitionAnnotation;
import org.openzen.zenscript.codemodel.annotations.InvalidMemberAnnotation;
import org.openzen.zenscript.codemodel.annotations.InvalidParameterAnnotation;
import org.openzen.zenscript.codemodel.annotations.InvalidStatementAnnotation;
import org.openzen.zenscript.codemodel.annotations.MemberAnnotation;
import org.openzen.zenscript.codemodel.annotations.ParameterAnnotation;
import org.openzen.zenscript.codemodel.annotations.StatementAnnotation;
import org.openzen.zenscript.codemodel.expression.CallArguments;
import org.openzen.zenscript.codemodel.member.IDefinitionMember;
import org.openzen.zenscript.codemodel.scope.BaseScope;
import org.openzen.zenscript.codemodel.scope.ExpressionScope;
import org.openzen.zenscript.codemodel.scope.StatementScope;
import org.openzen.zenscript.codemodel.statement.Statement;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.lexer.ZSTokenType;
import org.openzen.zenscript.parser.expression.ParsedCallArguments;
import org.openzen.zenscript.parser.type.IParsedType;

public class ParsedAnnotation {
   public static final ParsedAnnotation[] NONE = new ParsedAnnotation[0];
   public final CodePosition position;
   public final IParsedType type;
   public final ParsedCallArguments arguments;

   public ParsedAnnotation(CodePosition position, IParsedType type, ParsedCallArguments arguments) {
      this.position = position;
      this.type = type;
      this.arguments = arguments;
   }

   public static ParsedAnnotation[] parseAnnotations(ZSTokenParser parser) throws ParseException {
      if (!parser.isNext(ZSTokenType.T_SQOPEN)) {
         return NONE;
      }

      List<ParsedAnnotation> results = new ArrayList<>();

      while (parser.isNext(ZSTokenType.T_SQOPEN)) {
         parser.pushMark();

         try {
            parser.next();
         } catch (ParseException ex) {
            parser.popMark();
            parser.recoverUntilTokenOrNewline(ZSTokenType.T_SQCLOSE);
            continue;
         }

         if (!parser.isNext(ZSTokenType.T_IDENTIFIER)) {
            parser.reset();
            break;
         }

         parser.popMark();
         CodePosition position = parser.getPosition();
         IParsedType type = IParsedType.parse(parser);

         try {
            ParsedCallArguments arguments = ParsedCallArguments.parseForAnnotation(parser);
            parser.required(ZSTokenType.T_SQCLOSE, "] expected");
            results.add(new ParsedAnnotation(position, type, arguments));
         } catch (ParseException ex) {
            parser.recoverUntilTokenOrNewline(ZSTokenType.T_SQCLOSE);
         }
      }

      return results.toArray(NONE);
   }

   public static MemberAnnotation[] compileForMember(ParsedAnnotation[] annotations, IDefinitionMember member, BaseScope scope) {
      if (annotations.length == 0) {
         return MemberAnnotation.NONE;
      }

      MemberAnnotation[] compiled = new MemberAnnotation[annotations.length];

      for (int i = 0; i < annotations.length; i++) {
         compiled[i] = annotations[i].compileForMember(member, scope);
      }

      return compiled;
   }

   public static DefinitionAnnotation[] compileForDefinition(ParsedAnnotation[] annotations, HighLevelDefinition definition, BaseScope scope) {
      if (annotations.length == 0) {
         return DefinitionAnnotation.NONE;
      }

      DefinitionAnnotation[] compiled = new DefinitionAnnotation[annotations.length];

      for (int i = 0; i < annotations.length; i++) {
         compiled[i] = annotations[i].compileForDefinition(definition, scope);
      }

      return compiled;
   }

   public static StatementAnnotation[] compileForStatement(ParsedAnnotation[] annotations, Statement statement, StatementScope scope) {
      if (annotations.length == 0) {
         return StatementAnnotation.NONE;
      }

      StatementAnnotation[] compiled = new StatementAnnotation[annotations.length];

      for (int i = 0; i < annotations.length; i++) {
         compiled[i] = annotations[i].compileForStatement(statement, scope);
      }

      return compiled;
   }

   public static ParameterAnnotation[] compileForParameter(ParsedAnnotation[] annotations, FunctionHeader header, FunctionParameter parameter, BaseScope scope) {
      if (annotations.length == 0) {
         return ParameterAnnotation.NONE;
      }

      ParameterAnnotation[] compiled = new ParameterAnnotation[annotations.length];

      for (int i = 0; i < annotations.length; i++) {
         compiled[i] = annotations[i].compileForParameter(header, parameter, scope);
      }

      return compiled;
   }

   public MemberAnnotation compileForMember(IDefinitionMember member, BaseScope scope) {
      try {
         AnnotationDefinition annotationType = this.type.compileAnnotation(scope);
         ExpressionScope evalScope = annotationType.getScopeForMember(member, scope);
         TypeID[] types = this.type.compileTypeArguments(scope);
         CallArguments cArguments = this.arguments.compileCall(this.position, evalScope, types, annotationType.getInitializers(scope));
         return annotationType.createForMember(this.position, cArguments);
      } catch (CompileException ex) {
         return new InvalidMemberAnnotation(ex);
      }
   }

   public DefinitionAnnotation compileForDefinition(HighLevelDefinition definition, BaseScope scope) {
      AnnotationDefinition annotationType = this.type.compileAnnotation(scope);
      if (annotationType == null) {
         return new InvalidDefinitionAnnotation(this.position, CompileExceptionCode.UNKNOWN_ANNOTATION, "Unknown annotation type: " + this.type.toString());
      }

      try {
         ExpressionScope evalScope = annotationType.getScopeForType(definition, scope);
         TypeID[] types = this.type.compileTypeArguments(scope);
         CallArguments cArguments = this.arguments.compileCall(this.position, evalScope, types, annotationType.getInitializers(scope));
         return annotationType.createForDefinition(this.position, cArguments);
      } catch (CompileException ex) {
         return new InvalidDefinitionAnnotation(ex);
      }
   }

   public StatementAnnotation compileForStatement(Statement statement, StatementScope scope) {
      AnnotationDefinition annotationType = this.type.compileAnnotation(scope);
      if (annotationType == null) {
         return new InvalidStatementAnnotation(this.position, CompileExceptionCode.UNKNOWN_ANNOTATION, "Unknown annotation type: " + this.type.toString());
      }

      try {
         ExpressionScope evalScope = annotationType.getScopeForStatement(statement, scope);
         TypeID[] types = this.type.compileTypeArguments(scope);
         CallArguments cArguments = this.arguments.compileCall(this.position, evalScope, types, annotationType.getInitializers(scope));
         return annotationType.createForStatement(this.position, cArguments);
      } catch (CompileException ex) {
         return new InvalidStatementAnnotation(ex);
      }
   }

   public ParameterAnnotation compileForParameter(FunctionHeader header, FunctionParameter parameter, BaseScope scope) {
      AnnotationDefinition annotationType = this.type.compileAnnotation(scope);
      if (annotationType == null) {
         return new InvalidParameterAnnotation(this.position, CompileExceptionCode.UNKNOWN_ANNOTATION, "Unknown annotation type: " + this.type.toString());
      }

      try {
         ExpressionScope evalScope = annotationType.getScopeForParameter(header, parameter, scope);
         TypeID[] types = this.type.compileTypeArguments(scope);
         CallArguments cArguments = this.arguments.compileCall(this.position, evalScope, types, annotationType.getInitializers(scope));
         return annotationType.createForParameter(this.position, cArguments);
      } catch (CompileException ex) {
         return new InvalidParameterAnnotation(ex);
      }
   }
}
