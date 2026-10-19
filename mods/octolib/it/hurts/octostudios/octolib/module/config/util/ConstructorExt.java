package it.hurts.octostudios.octolib.module.config.util;

import it.hurts.octostudios.octolib.module.config.annotation.CfgConstructor;
import it.hurts.octostudios.octolib.module.config.cfgbuilder.ArrayEntry;
import it.hurts.octostudios.octolib.module.config.cfgbuilder.CfgTag;
import it.hurts.octostudios.octolib.module.config.cfgbuilder.CompoundEntry;
import it.hurts.octostudios.octolib.module.config.cfgbuilder.ConfigEntry;
import it.hurts.octostudios.octolib.module.config.cfgbuilder.DeconstructedObjectEntry;
import it.hurts.octostudios.octolib.module.config.cfgbuilder.EnumEntry;
import it.hurts.octostudios.octolib.module.config.cfgbuilder.ObjectEntry;
import it.hurts.octostudios.octolib.module.config.cfgbuilder.scalar.ScalarEntry;
import java.lang.annotation.IncompleteAnnotationException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.constructor.Constructor.ConstructMapping;
import org.yaml.snakeyaml.constructor.Constructor.ConstructSequence;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;

public class ConstructorExt extends org.yaml.snakeyaml.constructor.Constructor {
   protected Map<Class<?>, Construct> typeConstructorsMap = new IdentityHashMap<>();
   protected Set<Class<?>> passedClasses = new HashSet<>();

   public ConstructorExt(LoaderOptions loadingConfig) {
      this(Object.class, loadingConfig);
   }

   public ConstructorExt(Class<?> theRoot, LoaderOptions loadingConfig) {
      super(theRoot, loadingConfig);
      this.initConstructors();
   }

   public ConstructorExt(TypeDescription theRoot, LoaderOptions loadingConfig) {
      super(theRoot, loadingConfig);
      this.initConstructors();
   }

   public ConstructorExt(TypeDescription theRoot, Collection<TypeDescription> moreTDs, LoaderOptions loadingConfig) {
      super(theRoot, moreTDs, loadingConfig);
      this.initConstructors();
   }

   public ConstructorExt(String theRoot, LoaderOptions loadingConfig) throws ClassNotFoundException {
      super(theRoot, loadingConfig);
      this.initConstructors();
   }

   public Object constructObject(Node node) {
      return super.constructObject(node);
   }

   protected void initConstructors() {
      this.yamlClassConstructors.put(NodeId.mapping, new ConstructorExt.ConstructMappingCustomized());
      this.yamlClassConstructors.put(NodeId.sequence, new ConstructorExt.ConstructSequenceCustomizable());
      this.typeConstructorsMap.put(CompoundEntry.class, new ConstructorExt.ConstructEntry());
      this.typeConstructorsMap.put(DeconstructedObjectEntry.class, new ConstructorExt.ConstructMappedEntry());
      this.typeConstructorsMap.put(ArrayEntry.class, new ConstructorExt.ConstructMappedEntry());
      this.yamlConstructors.put(CompoundEntry.COMPOUND_CFG_TAG.yamlTag(), new ConstructorExt.ConstructEntry());
      this.yamlConstructors.put(DeconstructedObjectEntry.DECONSTRUCTED_CFG_TAG.yamlTag(), new ConstructorExt.ConstructMappedEntry());
   }

   protected Object newInstance(Class<?> ancestor, Node node, boolean tryDefault) {
      try {
         Class<?> type = node.getType();
         if (this.typeDefinitions.containsKey(type)) {
            TypeDescription td = (TypeDescription)this.typeDefinitions.get(type);
            Object instance = td.newInstance(node);
            if (instance != null) {
               return instance;
            }
         }

         if (tryDefault) {
            try {
               if (type.isAnonymousClass()) {
                  node.setType(String.class);
                  node.setType(type.getSuperclass());
                  return this.newInstance(ancestor, node, tryDefault);
               }

               if (ancestor.isAssignableFrom(type) && !Modifier.isAbstract(type.getModifiers())) {
                  Constructor<?> c = type.getDeclaredConstructor();
                  c.setAccessible(true);
                  return c.newInstance();
               }

               if (node.getTag().isCustomGlobal()) {
                  Class<?> newType = this.getClassForNode(node);
                  if (newType != type && type.isAssignableFrom(newType)) {
                     node.setType(newType);
                     return this.newInstance(ancestor, node, tryDefault);
                  }
               }
            } catch (NoSuchMethodException e) {
               if (node.getNodeId() != NodeId.mapping) {
                  throw e;
               }

               MappingNode mappingNode = (MappingNode)node;
               Constructor<?> c = type.getDeclaredConstructor(mappingNode.getValue().stream().map(t -> t.getValueNode().getType()).toArray(Class[]::new));
               c.setAccessible(true);
               return c.newInstance(mappingNode.getValue().stream().<Node>map(NodeTuple::getValueNode).map(this::constructObject).toArray(Object[]::new));
            }
         }
      } catch (Exception e) {
         throw new YAMLException(e);
      }

      return NOT_INSTANTIATED_OBJECT;
   }

   protected void createDefinition(Class<?> type) {
      List<Constructor<?>> constructors = Arrays.stream(type.getConstructors()).filter(cx -> cx.isAnnotationPresent(CfgConstructor.class)).toList();
      if (constructors.size() > 1) {
         throw new RuntimeException("Only one constructor in class can be annotated with @CfgConstructor.");
      }

      if (!constructors.isEmpty()) {
         final Constructor<?> c = constructors.get(0);
         final String[] props = c.getAnnotation(CfgConstructor.class).value();
         c.setAccessible(true);
         if (props.length != c.getParameterCount()) {
            throw new IncompleteAnnotationException(CfgConstructor.class, "Property count must be equal to constructor arguments count.");
         }

         Iterator<Class<?>> iterator = Arrays.stream(c.getParameterTypes()).iterator();

         for (String prop : props) {
            if (this.getPropertyUtils().getProperty(type, prop).getType().isAssignableFrom(iterator.next())) {
               throw new IncompleteAnnotationException(CfgConstructor.class, "Property types and order must be the same as in constructor.");
            }
         }

         this.addTypeDescription(
            new TypeDescription(type) {
               public Object newInstance(Node node) {
                  if (node.getNodeId() != NodeId.mapping) {
                     return super.newInstance(node);
                  }

                  Map<String, Object> map = ((MappingNode)node)
                     .getValue()
                     .stream()
                     .collect(Collectors.toMap(t -> ((ScalarNode)t.getKeyNode()).getValue(), t -> ConstructorExt.this.constructObject(t.getValueNode())));

                  try {
                     return c.newInstance(Arrays.stream(props).map(map::get).toArray());
                  } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                     throw new RuntimeException(e);
                  }
               }
            }
         );
      }
   }

   protected Construct getConstructor(Node node) {
      Class<?> type = node.getType();
      if (type != null && !this.passedClasses.contains(type)) {
         this.createDefinition(type);
      }

      return super.getConstructor(node);
   }

   public class ConstructEntry extends AbstractConstruct {
      public Object construct(Node node) {
         return switch (node.getNodeId()) {
            case scalar -> this.constructScalar(node);
            case mapping -> this.constructMappingObject(node);
            case sequence -> this.constructSequence(node);
            case anchor -> this.constructAnchor(node);
            default -> throw new MatchException(null, null);
         };
      }

      protected ConfigEntry constructSequence(Node valueNode) {
         ArrayEntry arrayEntry = new ArrayEntry(
            valueNode.getTag() == CfgTag.SEQ.yamlTag()
               ? null
               : (CfgTag.by(valueNode.getTag()).equals(ArrayEntry.SEQ_I) ? CfgTag.SEQ : CfgTag.by(valueNode.getTag()))
         );
         SequenceNode sequenceNode = (SequenceNode)valueNode;

         for (Node element : sequenceNode.getValue()) {
            arrayEntry.add((ConfigEntry)this.construct(element));
         }

         return arrayEntry;
      }

      protected ConfigEntry constructMappingObject(Node valueNode) {
         return valueNode.getTag() != null && valueNode.getTag() != Tag.MAP && !valueNode.getTag().equals(CompoundEntry.COMPOUND_CFG_TAG.yamlTag())
            ? new ObjectEntry(CfgTag.by(valueNode.getTag()), ConstructorExt.this.constructObject(valueNode))
            : this.constructMapping(new CompoundEntry(), (MappingNode)valueNode);
      }

      protected CompoundEntry constructMapping(CompoundEntry map, MappingNode mnode) {
         for (NodeTuple tuple : mnode.getValue()) {
            Node keyNode = tuple.getKeyNode();
            String key = ConstructorExt.this.constructObject(tuple.getKeyNode()).toString();
            ConfigEntry value = (ConfigEntry)this.construct(tuple.getValueNode());
            map.put(key, value);
            if (keyNode.getBlockComments() != null) {
               value.setBlockComment(keyNode.getBlockComments().stream().map(l -> l.getValue().substring(1)).collect(Collectors.joining("\n")));
            }

            if (keyNode.getInLineComments() != null) {
               value.setInlineComment(keyNode.getInLineComments().stream().map(l -> l.getValue().substring(1)).collect(Collectors.joining("\n")));
            }
         }

         return map;
      }

      protected ScalarEntry constructScalar(Node valueNode) {
         CompoundEntry.ScalarFactory factory = CompoundEntry.SCALAR_FACTORIES.get(CfgTag.by(valueNode.getTag()));
         ScalarEntry entry;
         if (factory == null) {
            if (!valueNode.getTag().getValue().endsWith(".enum")) {
               throw new YAMLException("Unsupported scalar node tag in compound: " + valueNode.getTag());
            }

            valueNode.setTag(new Tag(valueNode.getTag().getValue().replace(".enum", "")));
            entry = new EnumEntry((Enum<?>)ConstructorExt.this.constructObject(valueNode));
         } else {
            entry = factory.create(ConstructorExt.this.constructObject(valueNode));
         }

         return entry;
      }

      protected ConfigEntry constructAnchor(Node valueNode) {
         throw new UnsupportedOperationException();
      }
   }

   public class ConstructMappedEntry extends ConstructorExt.ConstructEntry {
      @Override
      protected ConfigEntry constructMappingObject(Node valueNode) {
         MappingNode mappingNode = (MappingNode)valueNode;
         if (valueNode.getTag() != null && valueNode.getTag() != Tag.MAP) {
            CfgTag tag = CfgTag.by(mappingNode.getTag());
            mappingNode.setTag(Tag.MAP);
            mappingNode.setType(DeconstructedObjectEntry.class);
            return this.constructMapping(new DeconstructedObjectEntry(tag), (MappingNode)valueNode);
         } else {
            return this.constructMapping(new CompoundEntry(), (MappingNode)valueNode);
         }
      }
   }

   public class ConstructMappingCustomized extends ConstructMapping {
      public ConstructMappingCustomized() {
         super(ConstructorExt.this);
      }

      public Object construct(Node node) {
         MappingNode mnode = (MappingNode)node;
         Construct construct = ConstructorExt.this.typeConstructorsMap.get(node.getType());
         return construct != null ? construct.construct(node) : super.construct(mnode);
      }

      public void construct2ndStep(Node node, Object object) {
         Construct construct = ConstructorExt.this.typeConstructorsMap.get(node.getType());
         if (construct != null) {
            construct.construct2ndStep(node, object);
         } else {
            super.construct2ndStep(node, object);
         }
      }
   }

   public class ConstructSequenceCustomizable extends ConstructSequence {
      public ConstructSequenceCustomizable() {
         super(ConstructorExt.this);
      }

      public Object construct(Node node) {
         Construct construct = ConstructorExt.this.typeConstructorsMap.get(node.getType());
         return construct != null ? construct.construct(node) : super.construct(node);
      }
   }
}
