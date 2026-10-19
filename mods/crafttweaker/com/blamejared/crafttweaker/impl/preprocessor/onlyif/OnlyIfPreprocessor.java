package com.blamejared.crafttweaker.impl.preprocessor.onlyif;

import com.blamejared.crafttweaker.api.annotation.Preprocessor;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IMutableScriptRunInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptFile;
import com.blamejared.crafttweaker.impl.preprocessor.onlyif.parameter.OnlyIfParameterFalse;
import com.blamejared.crafttweaker.impl.preprocessor.onlyif.parameter.OnlyIfParameterGameVersion;
import com.blamejared.crafttweaker.impl.preprocessor.onlyif.parameter.OnlyIfParameterModLoaded;
import com.blamejared.crafttweaker.impl.preprocessor.onlyif.parameter.OnlyIfParameterModLoader;
import com.blamejared.crafttweaker.impl.preprocessor.onlyif.parameter.OnlyIfParameterModNotLoaded;
import com.blamejared.crafttweaker.impl.preprocessor.onlyif.parameter.OnlyIfParameterSide;
import com.blamejared.crafttweaker.impl.preprocessor.onlyif.parameter.OnlyIfParameterTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.openzen.zencode.shared.CodePosition;

@ZenRegister
@Preprocessor
public final class OnlyIfPreprocessor implements IPreprocessor {
   public static final OnlyIfPreprocessor INSTANCE = new OnlyIfPreprocessor();
   public static final String NAME = "onlyif";
   private static final String SPACE = Pattern.quote(" ");
   private final Map<String, OnlyIfParameter> knownParameters = new HashMap<>();
   private OnlyIfMatch currentMatch;

   private OnlyIfPreprocessor() {
      this.addParameter(new OnlyIfParameterTrue());
      this.addParameter(new OnlyIfParameterFalse());
      this.addParameter(new OnlyIfParameterModLoaded());
      this.addParameter(new OnlyIfParameterModNotLoaded());
      this.addParameter(new OnlyIfParameterSide());
      this.addParameter(new OnlyIfParameterModLoader());
      this.addParameter(new OnlyIfParameterGameVersion());
   }

   public void addParameter(OnlyIfParameter parameter) {
      this.knownParameters.put(parameter.name().toLowerCase(), parameter);
   }

   @Override
   public String name() {
      return "onlyif";
   }

   @Override
   public String preprocessorEndMarker() {
      return "endif";
   }

   @Nullable
   @Override
   public String defaultValue() {
      return null;
   }

   @Override
   public boolean apply(IScriptFile file, List<String> preprocessedContents, IMutableScriptRunInfo runInfo, List<IPreprocessor.Match> matches) {
      List<OnlyIfMatch> onlyIfMatches = new ArrayList<>();
      this.readMatches(file, preprocessedContents, matches, onlyIfMatches);
      this.removeData(onlyIfMatches, preprocessedContents);
      return true;
   }

   @Override
   public int priority() {
      return 20;
   }

   private void readMatches(IScriptFile file, List<String> contents, List<IPreprocessor.Match> preprocessorMatches, List<OnlyIfMatch> matches) {
      this.currentMatch = null;
      Stream.concat(preprocessorMatches.stream(), file.matchesFor(EndIfPreprocessor.INSTANCE).stream())
         .filter(it -> it.line() != -1)
         .sorted(Comparator.comparingInt(IPreprocessor.Match::line))
         .forEach(it -> this.getOnlyIfMatch(file, contents, matches, it));
      if (this.currentMatch != null) {
         CodePosition start = this.currentMatch.start();
         String name = this.currentMatch.name();
         PREPROCESSOR_LOGGER.warn("{} onlyif '{}' starting at line {}:{} was not closed properly", file, name, start.fromLine, start.fromLineOffset);
      }
   }

   private void getOnlyIfMatch(IScriptFile file, List<String> contents, List<OnlyIfMatch> matches, IPreprocessor.Match preprocessorMatch) {
      int line = preprocessorMatch.line();
      String fileName = file.name();
      String[] content = preprocessorMatch.content().split(SPACE);
      if (content.length < 1) {
         PREPROCESSOR_LOGGER.warn("{}:{} Using 'onlyif' requires a parameter, like start, end, modloaded, etc", fileName, line);
      } else {
         String parameterName = content[0];
         if (preprocessorMatch.preprocessor().name().equalsIgnoreCase(this.preprocessorEndMarker())) {
            if (this.currentMatch != null) {
               this.currentMatch.end(this.getPosition(file, contents, line, new String[]{""}, 1, true));
               matches.add(this.currentMatch);
               this.currentMatch = this.currentMatch.parent();
            } else {
               PREPROCESSOR_LOGGER.warn("{}:{} Called 'onlyif end' without prior start", fileName, line);
            }
         } else if (!this.knownParameters.containsKey(parameterName.toLowerCase())) {
            PREPROCESSOR_LOGGER.warn("{}:{} Unknown 'onlyif' parameter: '{}'", fileName, line, parameterName);
         } else {
            String[] additionalArguments = Arrays.copyOfRange(content, 1, content.length);
            OnlyIfParameter parameter = this.knownParameters.get(parameterName.toLowerCase());
            OnlyIfParameterHit hit = parameter.isHit(additionalArguments);
            if (!hit.validArguments()) {
               String array = Arrays.toString(additionalArguments);
               PREPROCESSOR_LOGGER.warn("{}:{} Invalid 'onlyif' arguments for parameter '{}': {}'", fileName, line, parameterName, array);
            } else {
               CodePosition startPosition = this.getPosition(file, contents, line, content, hit.numberOfConsumedArguments() + 1, false);
               this.currentMatch = new OnlyIfMatch(startPosition, parameterName, this.currentMatch, hit);
               this.checkAdditionalOnlyIfsOnSameLine(contents, content, file, line, matches);
            }
         }
      }
   }

   private void checkAdditionalOnlyIfsOnSameLine(List<String> contents, String[] content, IScriptFile file, int line, List<OnlyIfMatch> matches) {
      List<String> strings = Arrays.asList(content);
      int indexOfStart = strings.indexOf("#" + this.name());
      int indexOfEnd = strings.indexOf("#" + this.preprocessorEndMarker());
      if (indexOfStart > 0 || indexOfEnd > 0) {
         boolean foundStart = Math.min(indexOfStart, indexOfEnd) == indexOfStart != (indexOfStart == -1);
         if (foundStart) {
            String newContent = String.join(" ", strings.subList(indexOfStart + 1, content.length));
            this.getOnlyIfMatch(file, contents, matches, new IPreprocessor.Match(this, line, newContent));
         } else {
            String newContent = String.join(" ", strings.subList(indexOfEnd, content.length)).substring(1);
            this.getOnlyIfMatch(file, contents, matches, new IPreprocessor.Match(EndIfPreprocessor.INSTANCE, line, newContent));
         }
      }
   }

   private CodePosition getPosition(IScriptFile file, List<String> contents, int line, String[] preprocessorContent, int contentPosition, boolean isEnd) {
      int prefixLength = isEnd ? this.preprocessorEndMarker().length() + 1 : "onlyif".length() + 2;
      int startColumn = contents.get(line - 1).lastIndexOf(String.join(" ", preprocessorContent));
      int endColumn = startColumn + contentPosition - 1;

      for (int i = 0; i < contentPosition; i++) {
         endColumn += preprocessorContent[i].length();
      }

      return new CodePosition(new FakeSourceFile(file.name()), line, startColumn - prefixLength, line, endColumn);
   }

   private void removeData(List<OnlyIfMatch> matches, List<String> preprocessedFileContents) {
      matches.forEach(it -> it.remove(preprocessedFileContents));
   }
}
