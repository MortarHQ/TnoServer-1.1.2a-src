package org.openzen.zenscript.parser;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.context.CompilingPackage;
import org.openzen.zenscript.codemodel.context.CompilingType;
import org.openzen.zenscript.codemodel.context.TypeResolutionContext;
import org.openzen.zenscript.codemodel.scope.BaseScope;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.lexer.ZSTokenType;
import org.openzen.zenscript.parser.definitions.ParsedAlias;
import org.openzen.zenscript.parser.definitions.ParsedClass;
import org.openzen.zenscript.parser.definitions.ParsedEnum;
import org.openzen.zenscript.parser.definitions.ParsedExpansion;
import org.openzen.zenscript.parser.definitions.ParsedFunction;
import org.openzen.zenscript.parser.definitions.ParsedInterface;
import org.openzen.zenscript.parser.definitions.ParsedStruct;
import org.openzen.zenscript.parser.definitions.ParsedVariant;

public abstract class ParsedDefinition {
   public final CodePosition position;
   public final int modifiers;
   public final ParsedAnnotation[] annotations;
   public final CompilingPackage pkg;

   public ParsedDefinition(CodePosition position, int modifiers, CompilingPackage pkg, ParsedAnnotation[] annotations) {
      this.position = position;
      this.pkg = pkg;
      this.modifiers = modifiers;
      this.annotations = annotations;
   }

   public static ParsedDefinition parse(
      CompilingPackage pkg, CodePosition position, int modifiers, ParsedAnnotation[] annotations, ZSTokenParser tokens, HighLevelDefinition outerDefinition
   ) throws ParseException {
      if (tokens.optional(ZSTokenType.K_CLASS) != null) {
         return ParsedClass.parseClass(pkg, position, modifiers, annotations, tokens, outerDefinition);
      } else if (tokens.optional(ZSTokenType.K_INTERFACE) != null) {
         return ParsedInterface.parseInterface(pkg, position, modifiers, annotations, tokens, outerDefinition);
      } else if (tokens.optional(ZSTokenType.K_ENUM) != null) {
         return ParsedEnum.parseEnum(pkg, position, modifiers, annotations, tokens, outerDefinition);
      } else if (tokens.optional(ZSTokenType.K_STRUCT) != null) {
         return ParsedStruct.parseStruct(pkg, position, modifiers, annotations, tokens, outerDefinition);
      } else if (tokens.optional(ZSTokenType.K_ALIAS) != null) {
         return ParsedAlias.parseAlias(pkg, position, modifiers, annotations, tokens, outerDefinition);
      } else if (tokens.optional(ZSTokenType.K_FUNCTION) != null) {
         return ParsedFunction.parseFunction(pkg, position, modifiers, annotations, tokens, outerDefinition);
      } else if (tokens.optional(ZSTokenType.K_EXPAND) != null) {
         return ParsedExpansion.parseExpansion(pkg, position, modifiers, annotations, tokens, outerDefinition);
      } else {
         return tokens.optional(ZSTokenType.K_VARIANT) != null
            ? ParsedVariant.parseVariant(pkg, position, modifiers, annotations, tokens, outerDefinition)
            : null;
      }
   }

   public String getName() {
      return this.getCompiled().name;
   }

   public final CodePosition getPosition() {
      return this.position;
   }

   public final int getModifiers() {
      return this.modifiers;
   }

   public abstract CompilingType getCompiling(TypeResolutionContext var1);

   public abstract HighLevelDefinition getCompiled();

   public abstract void linkTypes(TypeResolutionContext var1);

   public abstract void registerMembers(BaseScope var1, PrecompilationState var2);

   public abstract void compile(BaseScope var1) throws CompileException;
}
