package com.blamejared.crafttweaker.impl.preprocessor.onlyif;

import java.util.ArrayList;
import java.util.List;
import org.openzen.zencode.shared.CodePosition;

final class OnlyIfMatch {
   private final CodePosition start;
   private final OnlyIfMatch parent;
   private final List<OnlyIfMatch> children;
   private final String name;
   private final OnlyIfParameterHit parameterHit;
   private CodePosition end;

   OnlyIfMatch(CodePosition start, String name, OnlyIfMatch parent, OnlyIfParameterHit parameterHit) {
      this.start = start;
      this.name = name;
      this.parameterHit = parameterHit;
      this.children = new ArrayList<>();
      this.parent = parent;
      if (parent != null) {
         parent.addChild(this);
      }
   }

   private static String removeInLine(String lineContent, int startColumn, int endColumn) {
      char[] chars = lineContent.toCharArray();

      for (int i = startColumn; i < endColumn; i++) {
         chars[i] = ' ';
      }

      return new String(chars);
   }

   public CodePosition start() {
      return this.start;
   }

   public CodePosition end() {
      return this.end;
   }

   public void end(CodePosition end) {
      this.end = end;
   }

   public OnlyIfMatch parent() {
      return this.parent;
   }

   private void addChild(OnlyIfMatch child) {
      this.children.add(child);
   }

   public String name() {
      return this.name;
   }

   public void remove(List<String> fileContents) {
      if (!this.parameterHit.conditionMet()) {
         this.removeComplete(fileContents);
      } else {
         this.removePreprocessorCallsOnly(fileContents);
         this.removeChildren(fileContents);
      }
   }

   private void removePreprocessorCallsOnly(List<String> fileContents) {
      int position = this.start.fromLine - 1;
      int onlyIfStart = this.start.fromLineOffset;
      int onlyIfEnd = this.start.toLineOffset;
      String toOnlyIf = fileContents.get(position);
      String removed = removeInLine(toOnlyIf, onlyIfStart, onlyIfEnd);
      fileContents.set(position, removed);
      position = this.end.fromLine - 1;
      onlyIfStart = this.end.fromLineOffset;
      onlyIfEnd = this.end.toLineOffset;
      toOnlyIf = fileContents.get(position);
      removed = removeInLine(toOnlyIf, onlyIfStart, onlyIfEnd);
      fileContents.set(position, removed);
   }

   private void removeComplete(List<String> fileContents) {
      for (int i = this.start.fromLine; i <= this.end.toLine; i++) {
         this.removeLine(i, fileContents);
      }
   }

   private void removeLine(int lineNumber, List<String> fileContents) {
      int position = lineNumber - 1;
      String toOnlyIf = fileContents.get(position);
      int onlyIfStart = lineNumber != this.start.fromLine ? 0 : this.start.fromLineOffset;
      int onlyIfEnd = lineNumber != this.end.toLine ? toOnlyIf.length() : this.end.toLineOffset;
      String removed = removeInLine(toOnlyIf, onlyIfStart, onlyIfEnd);
      fileContents.set(position, removed);
   }

   private void removeChildren(List<String> fileContents) {
      this.children.forEach(it -> it.remove(fileContents));
   }
}
