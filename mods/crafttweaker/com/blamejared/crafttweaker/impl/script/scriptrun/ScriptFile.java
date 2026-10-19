package com.blamejared.crafttweaker.impl.script.scriptrun;

import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IMutableScriptRunInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptFile;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.Util;
import org.apache.logging.log4j.Logger;
import org.openzen.zencode.shared.SourceFile;

final class ScriptFile implements IScriptFile {
   private final String fileName;
   private final Map<IPreprocessor, List<IPreprocessor.Match>> matches;
   private final RunInfo info;
   private final List<String> fileContents;
   private final Supplier<ScriptFile.PreprocessedData> preprocessedFile;

   private ScriptFile(String fileName, Map<IPreprocessor, List<IPreprocessor.Match>> matches, List<String> contents, RunInfo info) {
      this.fileName = fileName;
      this.matches = new HashMap<>(matches);
      this.fileContents = ImmutableList.copyOf(contents);
      this.info = info;
      this.preprocessedFile = Suppliers.memoize(this::preprocess);
   }

   static ScriptFile of(String name, Stream<String> lines, RunInfo info, Collection<IPreprocessor> preprocessors) {
      Map<String, IPreprocessor> fastPreprocessorLookupMap = buildFastLookupMap(preprocessors);
      Pair<List<String>, Map<IPreprocessor, List<IPreprocessor.Match>>> data = read(lines, fastPreprocessorLookupMap);
      preprocessors.forEach(
         pp -> ((Map)data.getSecond())
            .computeIfAbsent(pp, it -> pp.defaultValue() != null ? List.of(new IPreprocessor.Match(pp, -1, pp.defaultValue())) : null)
      );
      return new ScriptFile(name, (Map<IPreprocessor, List<IPreprocessor.Match>>)data.getSecond(), (List<String>)data.getFirst(), info);
   }

   static ScriptFile of(Logger logger, Path baseDirectory, Path file, RunInfo info, Collection<IPreprocessor> preprocessors) {
      if (!verifyChild(baseDirectory, file)) {
         throw new IllegalArgumentException("File " + file + " is not contained within " + baseDirectory);
      }

      String name = baseDirectory.toAbsolutePath().relativize(file.toAbsolutePath()).toString();

      try (Stream<String> lines = lines(logger, file)) {
         return of(name, lines, info, preprocessors);
      }
   }

   private static boolean verifyChild(Path parent, Path file) {
      for (Path current = file; current != null; current = current.getParent()) {
         if (current.equals(parent)) {
            return true;
         }
      }

      return false;
   }

   private static Stream<String> lines(Logger logger, Path file) {
      try {
         return Files.lines(file, StandardCharsets.UTF_8);
      } catch (IOException e) {
         logger.error("Could not load file {}", file, e);
         return Stream.of();
      }
   }

   private static Map<String, IPreprocessor> buildFastLookupMap(Collection<IPreprocessor> preprocessors) {
      return preprocessors.stream().collect(Collectors.toMap(IPreprocessor::name, Function.identity()));
   }

   private static Pair<List<String>, Map<IPreprocessor, List<IPreprocessor.Match>>> read(Stream<String> lines, Map<String, IPreprocessor> preprocessors) {
      List<String> contents = new ArrayList<>();
      Map<IPreprocessor, List<IPreprocessor.Match>> matches = new HashMap<>();
      AtomicInteger lineCounter = new AtomicInteger();
      lines.forEachOrdered(it -> {
         contents.add(it);
         tryReadingPreprocessors(it, lineCounter.incrementAndGet(), matches, preprocessors);
      });
      return Pair.of(contents, matches);
   }

   private static void tryReadingPreprocessors(
      String line, int lineNumber, Map<IPreprocessor, List<IPreprocessor.Match>> matches, Map<String, IPreprocessor> preprocessors
   ) {
      Matcher matcher = IPreprocessor.PREPROCESSOR_PATTERN.matcher(line);
      if (matcher.find()) {
         String name = matcher.group().substring(1).trim().toLowerCase(Locale.ENGLISH);
         IPreprocessor preprocessor = preprocessors.get(name);
         if (preprocessor != null) {
            IPreprocessor.Match match = new IPreprocessor.Match(preprocessor, lineNumber, line.substring(matcher.end()));
            matches.computeIfAbsent(preprocessor, it -> new ArrayList<>(1)).add(match);
         }
      }
   }

   @Override
   public String name() {
      return this.fileName;
   }

   @Override
   public List<String> fileContents() {
      return this.fileContents;
   }

   @Override
   public List<String> preprocessedContents() {
      return this.preprocessedFile.get().contents();
   }

   @Override
   public Optional<SourceFile> toSourceFile() {
      return this.preprocessedFile.get().allowLoading() ? Optional.of(this.toFile()) : Optional.empty();
   }

   @Override
   public List<IPreprocessor.Match> matchesFor(IPreprocessor preprocessor) {
      return Optional.ofNullable(this.matches.get(preprocessor)).map(Collections::unmodifiableList).orElseGet(Collections::emptyList);
   }

   private ScriptFile.PreprocessedData preprocess() {
      ToIntFunction<Entry<IPreprocessor, ?>> intExtractor = it -> it.getKey().priority();
      MutableRunInfo mutableInfo = new MutableRunInfo(this.info);
      return this.matches
         .entrySet()
         .stream()
         .sorted(Comparator.comparingInt(intExtractor).reversed())
         .reduce(
            new ScriptFile.PreprocessedData(new ArrayList<>(this.fileContents), true),
            (data, entry) -> this.preprocess(data, (Entry<IPreprocessor, List<IPreprocessor.Match>>)entry, mutableInfo),
            this::combine
         );
   }

   private ScriptFile.PreprocessedData preprocess(
      ScriptFile.PreprocessedData data, Entry<IPreprocessor, List<IPreprocessor.Match>> entry, IMutableScriptRunInfo info
   ) {
      if (!data.allowLoading()) {
         return data;
      }

      List<String> newContents = new ArrayList<>(data.contents());
      boolean load = entry.getKey().apply(this, newContents, info, entry.getValue());
      return new ScriptFile.PreprocessedData(newContents, load);
   }

   private ScriptFile.PreprocessedData combine(ScriptFile.PreprocessedData a, ScriptFile.PreprocessedData b) {
      return new ScriptFile.PreprocessedData(this.intersect(a.contents(), b.contents()), a.allowLoading() && b.allowLoading());
   }

   private List<String> intersect(List<String> a, List<String> b) {
      return (List<String>)Util.make(new ArrayList<>(a), it -> it.retainAll(b));
   }

   private SourceFile toFile() {
      return new PreprocessedSourceFile(this.fileName, this.preprocessedContents(), this.matches);
   }

   private record PreprocessedData(List<String> contents, boolean allowLoading) {
   }
}
