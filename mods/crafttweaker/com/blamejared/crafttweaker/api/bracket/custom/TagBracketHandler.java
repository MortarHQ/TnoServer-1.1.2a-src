package com.blamejared.crafttweaker.api.bracket.custom;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.tag.CraftTweakerTagRegistry;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker.api.util.ParseUtil;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import java.util.Collections;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.lexer.ZSTokenType;
import org.openzen.zenscript.parser.BracketExpressionParser;
import org.openzen.zenscript.parser.expression.ParsedCallArguments;
import org.openzen.zenscript.parser.expression.ParsedExpression;
import org.openzen.zenscript.parser.expression.ParsedExpressionCall;
import org.openzen.zenscript.parser.expression.ParsedExpressionMember;

public class TagBracketHandler implements BracketExpressionParser {
   @Override
   public ParsedExpression parse(CodePosition position, ZSTokenParser tokens) throws ParseException {
      if (tokens.optional(ZSTokenType.T_GREATER) != null) {
         throw new ParseException(position, "Invalid Bracket handler, expected tagFolder here");
      } else {
         String tagFolder = ParseUtil.readContent(position, tokens, ZSTokenType.T_COLON);
         TagManagerBracketHandler.confirmTagFolderExists(tagFolder, position);
         IScriptLoader loader = CraftTweakerAPI.getScriptRunManager().currentRunInfo().loader();
         String tagName = ParseUtil.readBracketContent(position, tokens);
         ResourceLocation resourceLocation = ResourceLocation.tryParse(tagName);
         if (resourceLocation == null) {
            throw new ParseException(position, "Invalid Tag Name '" + tagName + "', must be a valid resource location");
         } else {
            return this.createTagCall(position, tagFolder, resourceLocation, loader);
         }
      }
   }

   private ParsedExpression createTagCall(CodePosition position, String tagFolder, ResourceLocation location, IScriptLoader loader) throws ParseException {
      ParsedExpression tagManager = TagManagerBracketHandler.createTagManagerCall(position, tagFolder, loader);
      ParsedExpressionMember getTag = new ParsedExpressionMember(position, tagManager, "tag", null);
      ParsedExpression newExpression = ParseUtil.createResourceLocationArgument(position, location);
      ParsedCallArguments arguments = new ParsedCallArguments(null, Collections.singletonList(newExpression));
      return new ParsedExpressionCall(position, getTag, arguments);
   }

   public static Supplier<Stream<String>> getDumperData() {
      return () -> CraftTweakerTagRegistry.INSTANCE
         .managers()
         .stream()
         .flatMap(iTagManager -> iTagManager.tags().stream())
         .sorted(MCTag::compareTo)
         .map(MCTag::getCommandString)
         .distinct();
   }
}
