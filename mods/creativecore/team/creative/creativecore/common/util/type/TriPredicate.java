package team.creative.creativecore.common.util.type;

@FunctionalInterface
public interface TriPredicate<T, U, V> {
   boolean test(T var1, U var2, V var3);
}
