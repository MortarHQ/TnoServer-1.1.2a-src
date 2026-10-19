package it.hurts.octostudios.octolib.module.config.util;

import it.hurts.octostudios.octolib.module.config.cfgbuilder.ArrayEntry;
import it.hurts.octostudios.octolib.module.config.cfgbuilder.CfgTag;
import it.hurts.octostudios.octolib.module.config.cfgbuilder.CompoundEntry;
import it.hurts.octostudios.octolib.module.config.cfgbuilder.ConfigEntry;
import it.hurts.octostudios.octolib.module.config.cfgbuilder.DeconstructedObjectEntry;
import it.hurts.octostudios.octolib.module.config.cfgbuilder.EntryId;
import it.hurts.octostudios.octolib.module.config.cfgbuilder.ObjectEntry;
import it.hurts.octostudios.octolib.module.config.cfgbuilder.scalar.DoubleEntry;
import it.hurts.octostudios.octolib.module.config.cfgbuilder.scalar.IntEntry;
import it.hurts.octostudios.octolib.module.config.cfgbuilder.scalar.ScalarEntry;
import it.hurts.octostudios.octolib.module.config.cfgbuilder.scalar.StringEntry;
import java.util.Iterator;
import java.util.Map.Entry;

public class SchemeInjector implements EntryInjector<ConfigEntry> {
   public ConfigEntry apply(ConfigEntry pattern, ConfigEntry target) {
      if (pattern != null && pattern.getNodeId() == target.getNodeId()) {
         try {
            return this.setupComments(pattern, pattern.refine(switch (pattern.getNodeId()) {
               case SCALAR -> this.injectScalar((ScalarEntry)pattern, (ScalarEntry)target);
               case SEQUENCE -> this.injectSeq((ArrayEntry)pattern, (ArrayEntry)target);
               case MAPPING -> this.injectMapping((CompoundEntry)pattern, (CompoundEntry)target);
               case OBJECT -> this.injectObject((ObjectEntry)pattern, (CompoundEntry)target);
               case ANCHOR -> throw new UnsupportedOperationException();
            }));
         } catch (ClassCastException exception) {
            throw new RuntimeException(
               String.format("Illegal entry type: %s or %s cannot be parsed.", pattern.getClass().getSimpleName(), target.getClass().getSimpleName()),
               exception
            );
         }
      } else {
         return pattern;
      }
   }

   protected ConfigEntry setupComments(ConfigEntry pattern, ConfigEntry target) {
      target.setBlockComment(pattern.getBlockComment());
      target.setInlineComment(pattern.getInlineComment());
      return target;
   }

   protected CompoundEntry injectMapping(CompoundEntry pattern, CompoundEntry target) {
      CompoundEntry result = pattern.getTag() == CompoundEntry.COMPOUND_CFG_TAG ? target : new DeconstructedObjectEntry(pattern.getTag(), pattern);

      for (Entry<String, ConfigEntry> entry : pattern.entrySet()) {
         String key = entry.getKey();
         ConfigEntry value = entry.getValue();
         ConfigEntry value1 = target.get(key);
         if (value1 != null) {
            if (value1.getNodeId() != value.getNodeId()) {
               result.put(key, value);
            } else {
               try {
                  result.put(key, this.apply(value, value1));
               } catch (ClassCastException exception) {
                  throw new RuntimeException(
                     String.format("Illegal entry type: %s or %s cannot be parsed.", value.getClass().getSimpleName(), value1.getClass().getSimpleName()),
                     exception
                  );
               }
            }
         }
      }

      return result;
   }

   protected DeconstructedObjectEntry injectObject(ObjectEntry pattern, CompoundEntry target) {
      return new DeconstructedObjectEntry(pattern.getTag(), target);
   }

   protected ArrayEntry injectSeq(ArrayEntry pattern, ArrayEntry target) {
      CfgTag patternTag = pattern.getType();
      if (patternTag == null) {
         return target;
      }

      target.setType(patternTag);
      Iterator<ConfigEntry> iterator = target.iterator();

      while (iterator.hasNext()) {
         ConfigEntry next = iterator.next();
         next = this.tryParse(next, patternTag);
         if (next == null) {
            iterator.remove();
         }
      }

      return target.isEmpty() ? pattern : target;
   }

   protected ConfigEntry tryParse(ConfigEntry object, CfgTag pattern) {
      if (pattern == object.getTag()) {
         return object;
      }

      if (pattern == CfgTag.FLOAT && object.getTag() == CfgTag.INT) {
         return new DoubleEntry(((Integer)object.getData()).intValue());
      }

      if (object.getNodeId() == EntryId.SCALAR && pattern == CfgTag.STR) {
         return new StringEntry(object.getData().toString());
      }

      if (pattern == CfgTag.INT && object.getTag() == CfgTag.STR) {
         try {
            return new IntEntry(Integer.parseInt((String)object.getData()));
         } catch (NumberFormatException exception) {
            return null;
         }
      } else {
         if (pattern == CfgTag.ENUM && object.getTag() == CfgTag.STR) {
            return object;
         }

         if (pattern == CfgTag.FLOAT && object.getTag() == CfgTag.STR) {
            try {
               return new DoubleEntry(Double.parseDouble((String)object.getData()));
            } catch (NumberFormatException exception) {
               return null;
            }
         } else {
            return null;
         }
      }
   }

   protected ScalarEntry injectScalar(ScalarEntry pattern, ScalarEntry target) {
      ConfigEntry result = this.tryParse(target, pattern.getTag());
      return result == null ? pattern : (ScalarEntry)result;
   }
}
