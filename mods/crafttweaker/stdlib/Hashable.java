package stdlib;

public interface Hashable<T> {
   @Override
   int hashCode();

   boolean equals_(T var1);
}
