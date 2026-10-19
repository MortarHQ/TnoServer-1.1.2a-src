package org.openzen.zencode.shared;

public final class CodePosition {
   public static final CodePosition BUILTIN = new CodePosition(new VirtualSourceFile("builtin"), 0, 0, 0, 0);
   public static final CodePosition NATIVE = new CodePosition(new VirtualSourceFile("native"), 0, 0, 0, 0);
   public static final CodePosition META = new CodePosition(new VirtualSourceFile("meta"), 0, 0, 0, 0);
   public static final CodePosition UNKNOWN = new CodePosition(new VirtualSourceFile("unknown"), 0, 0, 0, 0);
   public static final CodePosition GENERATED = new CodePosition(new VirtualSourceFile("generated"), 0, 0, 0, 0);
   public final SourceFile file;
   public final int fromLine;
   public final int fromLineOffset;
   public final int toLine;
   public final int toLineOffset;

   public CodePosition(SourceFile file, int fromLine, int fromLineOffset, int toLine, int toLineOffset) {
      this.file = file;
      this.fromLine = fromLine;
      this.fromLineOffset = fromLineOffset;
      this.toLine = toLine;
      this.toLineOffset = toLineOffset;
   }

   public String getFilename() {
      return this.file.getFilename();
   }

   public String toShortString() {
      int lastSeparator = this.file.getFilename().lastIndexOf(47);
      String shortFilename = lastSeparator >= 0
         ? this.file.getFilename().substring(lastSeparator + 1, this.file.getFilename().length())
         : this.file.getFilename();
      return this.fromLine == 0 && this.fromLineOffset == 0
         ? shortFilename
         : shortFilename + ":" + Integer.toString(this.fromLine) + ":" + Integer.toString(this.fromLineOffset);
   }

   public CodePosition until(CodePosition to) {
      if (this.file != to.file) {
         throw new AssertionError("From and to positions must be in the same file!");
      } else {
         return new CodePosition(this.file, this.fromLine, this.fromLineOffset, to.toLine, to.toLineOffset);
      }
   }

   public CodePosition withLength(int characters) {
      return new CodePosition(this.file, this.fromLine, this.fromLineOffset, this.fromLine, this.fromLineOffset + characters);
   }

   @Override
   public String toString() {
      return this.fromLine == 0 && this.fromLineOffset == 0
         ? this.file.getFilename()
         : this.file.getFilename() + ":" + Integer.toString(this.fromLine) + ":" + Integer.toString(this.fromLineOffset);
   }

   public SourceFile getFile() {
      return this.file;
   }

   public int getFromLine() {
      return this.fromLine;
   }

   public int getFromLineOffset() {
      return this.fromLineOffset;
   }

   public int getToLine() {
      return this.toLine;
   }

   public int getToLineOffset() {
      return this.toLineOffset;
   }
}
