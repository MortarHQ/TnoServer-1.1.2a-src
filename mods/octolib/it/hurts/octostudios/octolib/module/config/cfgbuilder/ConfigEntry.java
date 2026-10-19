package it.hurts.octostudios.octolib.module.config.cfgbuilder;

import it.hurts.octostudios.octolib.module.config.annotation.TypePropInherited;
import java.util.Arrays;
import java.util.stream.Collectors;

@TypePropInherited
public abstract class ConfigEntry {
   private String inlineComment;
   private String blockComment;
   protected CfgTag tag;
   private final EntryId nodeId;
   protected CfgTag type;

   public ConfigEntry(CfgTag tag, EntryId nodeId) {
      if (tag == null) {
         throw new IllegalArgumentException("Tag in a Entry is required.");
      }

      this.tag = tag;
      this.nodeId = nodeId;
   }

   public abstract Object getData();

   public boolean isNull() {
      return this.getData() == null;
   }

   public CfgTag getTag() {
      return this.tag;
   }

   public void setType(CfgTag type) {
      this.type = type;
   }

   public CfgTag getType() {
      return this.type;
   }

   public EntryId getNodeId() {
      return this.nodeId;
   }

   public String getInlineComment() {
      return this.inlineComment;
   }

   public ConfigEntry setInlineComment(String inlineComment) {
      this.inlineComment = inlineComment;
      return this;
   }

   public String getBlockComment() {
      return this.blockComment;
   }

   public ConfigEntry setBlockComment(String blockComment) {
      this.blockComment = blockComment;
      return this;
   }

   protected String formatInline() {
      return this.inlineComment == null ? "" : Arrays.stream(this.inlineComment.split("\n")).map(s -> "<# " + s + ">").collect(Collectors.joining("\n"));
   }

   protected String formatBlock() {
      return this.blockComment == null ? "" : Arrays.stream(this.blockComment.split("\n")).map(s -> "<# " + s + ">").collect(Collectors.joining("\n"));
   }

   public ConfigEntry refine(ConfigEntry entry) {
      return entry;
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder().append(this.formatBlock());
      if (!builder.isEmpty()) {
         builder.append("\n");
      }

      return builder.append(this.getClass().getSimpleName()).append("||").append(this.getData()).append(this.formatInline()).toString();
   }
}
