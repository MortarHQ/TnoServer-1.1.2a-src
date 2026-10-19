package it.hurts.octostudios.octolib.module.config.util;

import it.hurts.octostudios.octolib.module.config.annotation.TypeProp;
import it.hurts.octostudios.octolib.module.config.annotation.TypePropInherited;
import it.hurts.octostudios.octolib.module.config.cfgbuilder.ArrayEntry;
import it.hurts.octostudios.octolib.module.config.cfgbuilder.CfgTag;
import it.hurts.octostudios.octolib.module.config.cfgbuilder.CompoundEntry;
import it.hurts.octostudios.octolib.module.config.cfgbuilder.ConfigEntry;
import it.hurts.octostudios.octolib.module.config.cfgbuilder.DeconstructedObjectEntry;
import it.hurts.octostudios.octolib.module.config.util.properties.GenericPropertyExt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.comments.CommentLine;
import org.yaml.snakeyaml.comments.CommentType;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.introspector.BeanAccess;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.Representer;

public class RepresenterExt extends Representer {
   public RepresenterExt(DumperOptions options) {
      super(options);
      this.multiRepresenters.put(Deque.class, new RepresenterExt.RepresentDeque());
      this.multiRepresenters.put(ConfigEntry.class, new RepresenterExt.RepresentEntry());
      this.representers.put(CompoundEntry.class, new RepresenterExt.RepresentEntry());
      this.representers.put(DeconstructedObjectEntry.class, new RepresenterExt.RepresentEntry());
   }

   protected Node representMapping(Tag tag, Map<?, ?> mapping, FlowStyle flowStyle) {
      List<NodeTuple> value = new ArrayList<>(mapping.size());
      MappingNode node = new MappingNode(tag, value, flowStyle);
      this.representedObjects.put(this.objectToRepresent, node);
      FlowStyle bestStyle = FlowStyle.FLOW;

      for (Entry<?, ?> entry : mapping.entrySet()) {
         Node nodeKey = this.representData(entry.getKey());
         Node nodeValue = this.representData(entry.getValue());
         if (!(nodeKey instanceof ScalarNode) || !((ScalarNode)nodeKey).isPlain()) {
            bestStyle = FlowStyle.BLOCK;
         }

         if (!(nodeValue instanceof ScalarNode) || !((ScalarNode)nodeValue).isPlain()) {
            bestStyle = FlowStyle.BLOCK;
         }

         if (nodeValue.getNodeId() == NodeId.mapping) {
            if (nodeValue.getBlockComments() != null) {
               nodeKey.setBlockComments(nodeValue.getBlockComments());
               nodeValue.setBlockComments(null);
            }

            if (nodeValue.getInLineComments() != null) {
               nodeKey.setBlockComments(nodeValue.getInLineComments());
               nodeValue.setInLineComments(null);
            }
         }

         value.add(new NodeTuple(nodeKey, nodeValue));
      }

      if (flowStyle == FlowStyle.AUTO) {
         if (this.defaultFlowStyle != FlowStyle.AUTO) {
            node.setFlowStyle(this.defaultFlowStyle);
         } else {
            node.setFlowStyle(bestStyle);
         }
      }

      return node;
   }

   protected NodeTuple representJavaBeanProperty(Object javaBean, Property property, Object propertyValue, Tag customTag) {
      Node nodeKey = this.representData(property.getName());
      boolean hasAlias = this.representedObjects.containsKey(propertyValue);
      Node nodeValue = this.representData(propertyValue);
      this.configureCommentsProperty(nodeKey, nodeValue, javaBean, property);
      if (propertyValue != null && !hasAlias) {
         NodeId nodeId = nodeValue.getNodeId();
         if (customTag == null) {
            if (nodeId == NodeId.scalar) {
               if (property.getType() != Enum.class && propertyValue instanceof Enum) {
                  if (this.convertEnumToStr()) {
                     nodeValue.setTag(Tag.STR);
                  } else {
                     nodeValue.setTag(new Tag(nodeValue.getTag().getValue() + ".enum"));
                  }
               }
            } else {
               this.checkGlobalTag(property, nodeValue, propertyValue);
            }
         }
      }

      if (nodeValue.getNodeId() == NodeId.mapping) {
         if (nodeValue.getBlockComments() != null) {
            nodeKey.setBlockComments(nodeValue.getBlockComments());
            nodeValue.setBlockComments(null);
         }

         if (nodeValue.getInLineComments() != null) {
            nodeKey.setBlockComments(nodeValue.getInLineComments());
            nodeValue.setInLineComments(null);
         }
      }

      return new NodeTuple(nodeKey, nodeValue);
   }

   protected boolean convertEnumToStr() {
      return false;
   }

   protected boolean removeTypes() {
      return false;
   }

   protected MappingNode representJavaBean(Set<Property> properties, Object javaBean) {
      if (this.removeTypes() && !this.classTags.containsKey(javaBean.getClass())) {
         this.addClassTag(javaBean.getClass(), Tag.MAP);
      }

      MappingNode node = super.representJavaBean(properties, javaBean);
      this.configureCommentsJavaBean(node, javaBean);
      return node;
   }

   protected void configureCommentsJavaBean(Node nodeValue, Object javaBean) {
      RepresenterExt.TypeSettings settings = null;
      if (javaBean != null) {
         if (javaBean.getClass().isAnnotationPresent(TypePropInherited.class)) {
            settings = new RepresenterExt.TypeSettings(javaBean.getClass().getAnnotation(TypePropInherited.class));
         }

         if (javaBean.getClass().isAnnotationPresent(TypeProp.class)) {
            settings = new RepresenterExt.TypeSettings(javaBean.getClass().getAnnotation(TypeProp.class));
         }

         if (settings == null) {
            return;
         }

         List<CommentLine> block = nodeValue.getBlockComments() == null ? new ArrayList<>() : nodeValue.getBlockComments();
         if (!settings.comment.isEmpty()) {
            block.addAll(this.parseStringToComment(settings.comment, CommentType.BLOCK));
         }

         if (!block.isEmpty()) {
            nodeValue.setBlockComments(block);
         }

         List<CommentLine> inline = nodeValue.getInLineComments() == null ? new ArrayList<>() : nodeValue.getInLineComments();
         if (!settings.inlineComment.isEmpty()) {
            inline.addAll(this.parseStringToComment(settings.inlineComment, CommentType.IN_LINE));
         }

         if (!inline.isEmpty()) {
            nodeValue.setInLineComments(inline);
         }
      }
   }

   protected void configureCommentsProperty(Node nodeKey, Node nodeValue, Object javaBean, Property property) {
      if (property instanceof GenericPropertyExt extended) {
         List<CommentLine> block = nodeKey.getBlockComments() == null ? new ArrayList<>() : nodeKey.getBlockComments();
         if (extended.getBlockComment() != null) {
            block.addAll(this.parseStringToComment(extended.getBlockComment(), CommentType.BLOCK));
         }

         if (!block.isEmpty()) {
            nodeKey.setBlockComments(block);
         }

         List<CommentLine> inline = nodeKey.getInLineComments() == null ? new ArrayList<>() : nodeKey.getInLineComments();
         if (extended.getInlineComment() != null) {
            inline.addAll(this.parseStringToComment(extended.getInlineComment(), CommentType.IN_LINE));
         }

         if (!inline.isEmpty()) {
            nodeKey.setInLineComments(inline);
         }
      }
   }

   protected List<CommentLine> parseStringToComment(String comment, CommentType type) {
      return Arrays.stream(comment.split("\n")).map(s -> new CommentLine(null, null, " " + s, type)).toList();
   }

   protected Set<Property> getProperties(Class<?> type) {
      return this.typeDefinitions.containsKey(type)
         ? ((TypeDescription)this.typeDefinitions.get(type)).getProperties()
         : this.getPropertyUtils()
            .getProperties(
               type, type.getAnnotation(TypePropInherited.class) == null ? BeanAccess.FIELD : type.getAnnotation(TypePropInherited.class).accessType()
            );
   }

   protected class RepresentDeque implements Represent {
      public Node representData(Object data) {
         return RepresenterExt.this.representSequence(RepresenterExt.this.getTag(data.getClass(), Tag.SEQ), (Deque)data, FlowStyle.AUTO);
      }
   }

   protected class RepresentEntry implements Represent {
      public Node representData(Object o) {
         ConfigEntry entry = (ConfigEntry)o;

         Node node = switch (entry.getNodeId()) {
            case SCALAR -> {
               Node var9;
               try {
                  var9 = RepresenterExt.this.representScalar(entry.getTag().yamlTag(), String.valueOf(entry.getData()));
               } catch (ClassCastException exception) {
                  throw new YAMLException("Config Entry of scalar node id must have String type data.", exception);
               }

               yield var9;
            }
            case MAPPING, OBJECT -> {
               Node n = RepresenterExt.this.representData(entry.getData());
               if (!RepresenterExt.this.removeTypes()
                  && entry.getTag() != CfgTag.MAP
                  && entry.getTag() != null
                  && entry.getTag() != CompoundEntry.COMPOUND_CFG_TAG) {
                  n.setTag(entry.getTag().yamlTag());
               }

               Node var8 = n;
               yield var8;
            }
            case SEQUENCE -> {
               Node inlineComments;
               try {
                  inlineComments = RepresenterExt.this.representSequence(
                     entry.getType() == null ? Tag.SEQ : (entry.getType().yamlTag() == Tag.SEQ ? ArrayEntry.SEQ_I.yamlTag() : entry.getType().yamlTag()),
                     (Iterable)entry.getData(),
                     FlowStyle.BLOCK
                  );
               } catch (ClassCastException exception) {
                  throw new YAMLException("Config Entry of scalar node id must have Iterable type data.", exception);
               }

               yield inlineComments;
            }
            case ANCHOR -> throw new UnsupportedOperationException();
         };
         if (entry.getBlockComment() != null && !entry.getBlockComment().isEmpty()) {
            ArrayList<CommentLine> blockComments = new ArrayList<>(RepresenterExt.this.parseStringToComment(entry.getBlockComment(), CommentType.BLOCK));
            if (node.getBlockComments() != null) {
               blockComments.addAll(node.getBlockComments());
            }

            node.setBlockComments(blockComments);
         }

         if (entry.getInlineComment() != null && !entry.getInlineComment().isEmpty()) {
            ArrayList<CommentLine> inlineComments = new ArrayList<>(RepresenterExt.this.parseStringToComment(entry.getInlineComment(), CommentType.BLOCK));
            if (node.getInLineComments() != null) {
               inlineComments.addAll(node.getInLineComments());
            }

            node.setInLineComments(inlineComments);
         }

         return node;
      }
   }

   public static class TypeSettings {
      final String inlineComment;
      final String comment;
      final BeanAccess accessType;
      final boolean onlyProps;

      public TypeSettings(TypePropInherited annotation) {
         this.inlineComment = annotation.inlineComment();
         this.comment = annotation.comment();
         this.accessType = annotation.accessType();
         this.onlyProps = annotation.onlyProps();
      }

      public TypeSettings(TypeProp annotation) {
         this.inlineComment = annotation.inlineComment();
         this.comment = annotation.comment();
         this.accessType = annotation.accessType();
         this.onlyProps = annotation.onlyProps();
      }
   }
}
