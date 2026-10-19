package org.openzen.zenscript.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zencode.shared.CompileExceptionCode;
import org.openzen.zencode.shared.FileSourceFile;
import org.openzen.zencode.shared.LiteralSourceFile;
import org.openzen.zencode.shared.SourceFile;
import org.openzen.zencode.shared.logging.CompileExceptionLogger;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.ModuleSpace;
import org.openzen.zenscript.codemodel.PackageDefinitions;
import org.openzen.zenscript.codemodel.ScriptBlock;
import org.openzen.zenscript.codemodel.SemanticModule;
import org.openzen.zenscript.codemodel.WhitespacePostComment;
import org.openzen.zenscript.codemodel.context.CompilingPackage;
import org.openzen.zenscript.codemodel.context.FileResolutionContext;
import org.openzen.zenscript.codemodel.context.ModuleTypeResolutionContext;
import org.openzen.zenscript.codemodel.definition.ExpansionDefinition;
import org.openzen.zenscript.codemodel.definition.ZSPackage;
import org.openzen.zenscript.codemodel.scope.FileScope;
import org.openzen.zenscript.codemodel.scope.GlobalScriptScope;
import org.openzen.zenscript.codemodel.scope.StatementScope;
import org.openzen.zenscript.codemodel.statement.Statement;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.ISymbol;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.lexer.ZSTokenType;
import org.openzen.zenscript.parser.logger.ParserLogger;
import org.openzen.zenscript.parser.statements.ParsedStatement;

public class ParsedFile {
   public final SourceFile file;
   private final List<ParsedImport> imports = new ArrayList<>();
   private final List<ParsedDefinition> definitions = new ArrayList<>();
   private final List<ParsedStatement> statements = new ArrayList<>();
   private final List<ParseException> errors = new ArrayList<>();
   private WhitespacePostComment postComment = null;

   public ParsedFile(SourceFile file) {
      this.file = file;
   }

   public static SemanticModule compileSyntaxToSemantic(
      SemanticModule[] dependencies, CompilingPackage pkg, ParsedFile[] files, ModuleSpace registry, FunctionParameter[] parameters, ParserLogger logger
   ) {
      boolean failed = false;
      PackageDefinitions definitions = new PackageDefinitions();

      for (ParsedFile file : files) {
         file.listDefinitions(definitions);
      }

      ZSPackage rootPackage = registry.collectPackages();
      List<ExpansionDefinition> expansions = registry.collectExpansions();
      definitions.registerExpansionsTo(expansions);
      Map<String, ISymbol> globals = registry.collectGlobals();
      ModuleTypeResolutionContext moduleContext = new ModuleTypeResolutionContext(registry.registry, registry.getAnnotations(), rootPackage, pkg, globals);
      Map<String, CompileException> importErrors = new HashMap<>();

      for (ParsedFile file : files) {
         file.registerTypes(moduleContext, rootPackage, pkg, importErrors);
      }

      for (ParsedFile file : files) {
         file.compileTypes(moduleContext, rootPackage, pkg, importErrors);
      }

      for (ParsedFile file : files) {
         if (file.hasErrors()) {
            failed = true;

            for (ParseException error : file.errors) {
               logger.logParseException(error);
            }
         }
      }

      if (failed) {
         return new SemanticModule(
            pkg.module,
            dependencies,
            parameters,
            SemanticModule.State.INVALID,
            rootPackage,
            pkg.getPackage(),
            definitions,
            Collections.emptyList(),
            registry.registry,
            expansions,
            registry.getAnnotations(),
            logger
         );
      }

      PrecompilationState precompiler = new PrecompilationState();

      for (ParsedFile file : files) {
         file.registerMembers(moduleContext, precompiler, rootPackage, pkg, expansions, globals, importErrors);
      }

      List<ScriptBlock> scripts = new ArrayList<>();
      FunctionHeader scriptHeader = new FunctionHeader(BasicTypeID.VOID, parameters);

      for (ParsedFile file : files) {
         file.compileCode(moduleContext, precompiler, rootPackage, pkg, expansions, scripts, globals, scriptHeader, logger, importErrors);
      }

      for (CompileException error : importErrors.values()) {
         logger.logCompileException(error);
      }

      return new SemanticModule(
         pkg.module,
         dependencies,
         parameters,
         SemanticModule.State.ASSEMBLED,
         rootPackage,
         pkg.getPackage(),
         definitions,
         scripts,
         registry.registry,
         expansions,
         registry.getAnnotations(),
         logger
      );
   }

   public static ParsedFile parse(CompilingPackage compilingPackage, BracketExpressionParser bracketParser, File file) throws ParseException {
      return parse(compilingPackage, bracketParser, new FileSourceFile(file.getName(), file));
   }

   public static ParsedFile parse(CompilingPackage compilingPackage, BracketExpressionParser bracketParser, String filename, String content) throws ParseException {
      return parse(compilingPackage, bracketParser, new LiteralSourceFile(filename, content));
   }

   public static ParsedFile parse(CompilingPackage compilingPackage, BracketExpressionParser bracketParser, SourceFile file) throws ParseException {
      try {
         ZSTokenParser tokens = ZSTokenParser.create(file, bracketParser);
         return parse(compilingPackage, tokens);
      } catch (IOException ex) {
         throw new ParseException(new CodePosition(file, 0, 0, 0, 0), ex.getMessage());
      }
   }

   public static ParsedFile parse(CompilingPackage compilingPackage, ZSTokenParser tokens) throws ParseException {
      ParsedFile result = new ParsedFile(tokens.getFile());

      label43:
      while (true) {
         CodePosition position = tokens.getPosition();
         ParsedAnnotation[] annotations = ParsedAnnotation.parseAnnotations(tokens);
         int modifiers = 0;

         while (true) {
            switch (tokens.peek().type) {
               case K_PUBLIC:
                  modifiers |= 1;
                  break;
               case K_PRIVATE:
                  modifiers |= 4;
                  break;
               case K_INTERNAL:
                  modifiers |= 2;
                  break;
               case K_EXTERN:
                  modifiers |= 2048;
                  break;
               case K_ABSTRACT:
                  modifiers |= 8;
                  break;
               case K_FINAL:
                  modifiers |= 16;
                  break;
               case K_PROTECTED:
                  modifiers |= 256;
                  break;
               case K_IMPLICIT:
                  modifiers |= 512;
                  break;
               case K_VIRTUAL:
                  modifiers |= 1024;
                  break;
               default:
                  if (tokens.optional(ZSTokenType.K_IMPORT) != null) {
                     result.imports.add(ParsedImport.parse(position, tokens));
                  } else {
                     if (tokens.optional(ZSTokenType.EOF) != null) {
                        result.postComment = WhitespacePostComment.fromWhitespace(tokens.getLastWhitespace());
                        result.errors.addAll(tokens.getErrors());
                        return result;
                     }

                     ParsedDefinition definition = ParsedDefinition.parse(compilingPackage, position, modifiers, annotations, tokens, null);
                     if (definition == null) {
                        try {
                           result.statements.add(ParsedStatement.parse(tokens, annotations));
                        } catch (ParseException e) {
                           tokens.logError(e);
                           tokens.recoverUntilOnToken(ZSTokenType.T_SEMICOLON);
                        }
                     } else {
                        result.definitions.add(definition);
                     }
                  }
                  continue label43;
            }

            tokens.next();
         }
      }
   }

   public boolean hasErrors() {
      return this.errors.size() > 0;
   }

   public List<ParseException> getErrors() {
      return this.errors;
   }

   public void listDefinitions(PackageDefinitions definitions) {
      for (ParsedDefinition definition : this.definitions) {
         definitions.add(definition.getCompiled());
      }
   }

   public void registerTypes(
      ModuleTypeResolutionContext moduleContext, ZSPackage rootPackage, CompilingPackage modulePackage, Map<String, CompileException> importErrors
   ) {
      FileResolutionContext context = new FileResolutionContext(moduleContext, rootPackage, modulePackage);
      this.loadImports(context, rootPackage, modulePackage, importErrors);

      for (ParsedDefinition definition : this.definitions) {
         if (definition.getName() != null) {
            definition.pkg.addType(definition.getName(), definition.getCompiling(context));
         }
      }
   }

   public void compileTypes(
      ModuleTypeResolutionContext moduleContext, ZSPackage rootPackage, CompilingPackage modulePackage, Map<String, CompileException> importErrors
   ) {
      FileResolutionContext context = new FileResolutionContext(moduleContext, rootPackage, modulePackage);
      this.loadImports(context, rootPackage, modulePackage, importErrors);

      for (ParsedDefinition definition : this.definitions) {
         if (definition.getName() != null) {
            modulePackage.addType(definition.getName(), definition.getCompiling(context));
         }
      }

      for (ParsedDefinition definition : this.definitions) {
         definition.getCompiling(context).load();
      }
   }

   public void registerMembers(
      ModuleTypeResolutionContext moduleContext,
      PrecompilationState precompiler,
      ZSPackage rootPackage,
      CompilingPackage modulePackage,
      List<ExpansionDefinition> expansions,
      Map<String, ISymbol> globals,
      Map<String, CompileException> importErrors
   ) {
      FileResolutionContext context = new FileResolutionContext(moduleContext, rootPackage, modulePackage);
      this.loadImports(context, rootPackage, modulePackage, importErrors);
      FileScope scope = new FileScope(context, expansions, globals, precompiler);

      for (ParsedDefinition definition : this.definitions) {
         definition.registerMembers(scope, precompiler);
      }
   }

   public void compileCode(
      ModuleTypeResolutionContext moduleContext,
      PrecompilationState precompiler,
      ZSPackage rootPackage,
      CompilingPackage modulePackage,
      List<ExpansionDefinition> expansions,
      List<ScriptBlock> scripts,
      Map<String, ISymbol> globals,
      FunctionHeader scriptHeader,
      CompileExceptionLogger exceptionLogger,
      Map<String, CompileException> importErrors
   ) {
      FileResolutionContext context = new FileResolutionContext(moduleContext, rootPackage, modulePackage);
      this.loadImports(context, rootPackage, modulePackage, importErrors);
      FileScope scope = new FileScope(context, expansions, globals, precompiler);

      for (ParsedDefinition definition : this.definitions) {
         try {
            definition.compile(scope);
         } catch (CompileException ex) {
            exceptionLogger.logCompileException(ex);
         }
      }

      if (!this.statements.isEmpty() || this.postComment != null) {
         StatementScope statementScope = new GlobalScriptScope(scope, scriptHeader);
         List<Statement> statements = new ArrayList<>();

         for (ParsedStatement statement : this.statements) {
            statements.add(statement.compile(statementScope));
         }

         ScriptBlock block = new ScriptBlock(this.file, modulePackage.module, modulePackage.getPackage(), scriptHeader, statements);
         block.setTag(WhitespacePostComment.class, this.postComment);
         scripts.add(block);
      }
   }

   private void loadImports(FileResolutionContext context, ZSPackage rootPackage, CompilingPackage modulePackage, Map<String, CompileException> importErrors) {
      for (ParsedImport importEntry : this.imports) {
         HighLevelDefinition definition;
         if (importEntry.isRelative()) {
            definition = modulePackage.getImport(context, importEntry.getPath());
         } else {
            definition = rootPackage.getImport(importEntry.getPath(), 0);
         }

         if (definition == null) {
            importErrors.put(
               importEntry.toString(),
               new CompileException(importEntry.position, CompileExceptionCode.IMPORT_NOT_FOUND, "Could not find type " + importEntry.toString())
            );
         }

         if (definition != null) {
            context.addImport(importEntry.getName(), definition);
         }
      }
   }
}
