package it.hurts.octostudios.octolib.module.config.cfgbuilder;

import java.util.HashMap;
import java.util.Objects;
import org.yaml.snakeyaml.nodes.Tag;

public class CfgTag {
   public static final String PREFIX = "tag:yaml.org,2002:";
   public static final String ENUM_POSTFIX = ".enum";
   public static final CfgTag YAML = CfgTag.STANDART_TAGS.get(Tag.YAML);
   public static final CfgTag MERGE = CfgTag.STANDART_TAGS.get(Tag.MERGE);
   public static final CfgTag SET = CfgTag.STANDART_TAGS.get(Tag.SET);
   public static final CfgTag PAIRS = CfgTag.STANDART_TAGS.get(Tag.PAIRS);
   public static final CfgTag OMAP = CfgTag.STANDART_TAGS.get(Tag.OMAP);
   public static final CfgTag BINARY = CfgTag.STANDART_TAGS.get(Tag.BINARY);
   public static final CfgTag INT = CfgTag.STANDART_TAGS.get(Tag.INT);
   public static final CfgTag FLOAT = CfgTag.STANDART_TAGS.get(Tag.FLOAT);
   public static final CfgTag TIMESTAMP = CfgTag.STANDART_TAGS.get(Tag.TIMESTAMP);
   public static final CfgTag BOOL = CfgTag.STANDART_TAGS.get(Tag.BOOL);
   public static final CfgTag NULL = CfgTag.STANDART_TAGS.get(Tag.NULL);
   public static final CfgTag STR = CfgTag.STANDART_TAGS.get(Tag.STR);
   public static final CfgTag SEQ = CfgTag.STANDART_TAGS.get(Tag.SEQ);
   public static final CfgTag MAP = CfgTag.STANDART_TAGS.get(Tag.MAP);
   public static final CfgTag ENUM = new CfgTag(Enum.class);
   public static final HashMap<Tag, CfgTag> STANDART_TAGS = new HashMap<>();
   private final Tag tag;

   private CfgTag(Tag tag) {
      this.tag = tag;
   }

   public CfgTag(String name) {
      this.tag = new Tag(name);
   }

   public CfgTag(Class<?> clazz) {
      this.tag = new Tag(clazz);
   }

   public static CfgTag by(Tag tag) {
      return STANDART_TAGS.containsKey(tag) ? STANDART_TAGS.get(tag) : new CfgTag(tag);
   }

   public Tag yamlTag() {
      return this.tag;
   }

   public boolean isSecondary() {
      return this.tag.isSecondary();
   }

   public String getValue() {
      return this.tag.getValue();
   }

   public boolean startsWith(String prefix) {
      return this.tag.startsWith(prefix);
   }

   public String getClassName() {
      return this.tag.getClassName();
   }

   public boolean isCompatible(Class<?> clazz) {
      return this.tag.isCompatible(clazz);
   }

   public boolean matches(Class<?> clazz) {
      return this.tag.matches(clazz);
   }

   public boolean isCustomGlobal() {
      return this.tag.isCustomGlobal();
   }

   @Override
   public String toString() {
      return this.tag.toString();
   }

   @Override
   public int hashCode() {
      return this.tag.hashCode();
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else {
         return o instanceof CfgTag cfgTag ? Objects.equals(this.tag, cfgTag.tag) : false;
      }
   }

   static {
      Tag.standardTags.forEach(tag -> STANDART_TAGS.put(tag, new CfgTag(tag)));
   }
}
