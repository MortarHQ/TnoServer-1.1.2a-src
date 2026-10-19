package com.blamejared.crafttweaker.impl.script.recipefs;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.NonWritableChannelException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

final class RecipeFileChannel extends FileChannel {
   private final Lock lock = new ReentrantLock();
   private final byte[] contents;
   private final int length;
   private int position;

   private RecipeFileChannel(byte[] contents) {
      this.length = (this.contents = contents).length;
      this.position = 0;
   }

   static FileChannel of(RecipeFsResolver.Bound resolver, Set<? extends OpenOption> options, FileAttribute<?>... attributes) throws IOException {
      verifyFlags(options);
      Objects.requireNonNull(attributes);
      String contents = resolver.resolveContents();
      return new RecipeFileChannel(contents.getBytes(StandardCharsets.UTF_8));
   }

   private static void verifyFlags(Set<? extends OpenOption> options) {
      if (!options.isEmpty()) {
         Set<? extends OpenOption> newOptions = new HashSet<>(options);
         newOptions.remove(StandardOpenOption.READ);
         if (!newOptions.isEmpty()) {
            throw new UnsupportedOperationException("Read-only file system does not support options " + newOptions);
         }
      }
   }

   @Override
   public int read(ByteBuffer dst) throws IOException {
      Objects.requireNonNull(dst);
      if (dst.isReadOnly()) {
         throw new IllegalArgumentException("Unable to fill a read-only buffer");
      }

      this.open();
      this.lock.lock();

      try {
         int available = this.length - this.position;
         if (available <= 0) {
            return -1;
         }

         int remaining = dst.remaining();
         int readAmount = Math.min(available, remaining);
         dst.put(this.contents, this.position, readAmount);
         this.position += readAmount;
         return readAmount;
      } catch (BufferOverflowException | IndexOutOfBoundsException e) {
         throw new IOException("Unable to read from resource into buffer", e);
      } finally {
         this.lock.unlock();
      }
   }

   @Override
   public long read(ByteBuffer[] dsts, int offset, int length) throws IOException {
      Objects.requireNonNull(dsts);
      Objects.checkFromIndexSize(offset, length, dsts.length);

      for (ByteBuffer buffer : dsts) {
         if (buffer.isReadOnly()) {
            throw new IllegalArgumentException("Unable to fill a read-only buffer");
         }
      }

      this.open();
      this.lock.lock();

      try {
         int available = this.length - this.position;
         if (available <= 0) {
            return -1L;
         }

         long rollingTotal = 0L;
         int i = offset;

         for (int l = offset + length; i < l; i++) {
            ByteBuffer buffer = dsts[i];
            int remainingForBuffer = buffer.remaining();
            int readAmount = Math.min(available, remainingForBuffer);
            buffer.put(this.contents, this.position, readAmount);
            this.position += readAmount;
            rollingTotal += readAmount;
            available = this.length - this.position;
         }

         return rollingTotal;
      } catch (BufferOverflowException | IndexOutOfBoundsException e) {
         throw new IOException("Unable to read from resource into buffers", e);
      } finally {
         this.lock.unlock();
      }
   }

   @Override
   public int write(ByteBuffer src) throws IOException {
      Objects.requireNonNull(src);
      this.open();
      throw new NonWritableChannelException();
   }

   @Override
   public long write(ByteBuffer[] srcs, int offset, int length) throws IOException {
      Objects.requireNonNull(srcs);
      Objects.checkFromIndexSize(offset, length, srcs.length);
      this.open();
      throw new NonWritableChannelException();
   }

   @Override
   public long position() throws IOException {
      this.open();
      return this.position;
   }

   @Override
   public FileChannel position(long newPosition) throws IOException {
      if (newPosition >= 0L && newPosition <= 2147483647L) {
         this.open();
         this.lock.lock();

         try {
            this.position = (int)newPosition;
            return this;
         } finally {
            this.lock.unlock();
         }
      } else {
         throw new IllegalArgumentException("Unable to set position " + newPosition);
      }
   }

   @Override
   public long size() throws IOException {
      this.open();
      return this.length;
   }

   @Override
   public FileChannel truncate(long size) throws IOException {
      if (size >= 0L && size <= 2147483647L) {
         this.open();
         throw new NonWritableChannelException();
      } else {
         throw new IllegalArgumentException("Unable to truncate to " + size);
      }
   }

   @Override
   public void force(boolean metaData) throws IOException {
      this.open();
   }

   @Override
   public long transferTo(long position, long count, WritableByteChannel target) throws IOException {
      Objects.requireNonNull(target);
      if (position >= 0L && count >= 0L) {
         this.open();
         this.lock.lock();

         try {
            ByteBuffer readBuffer = ByteBuffer.allocate((int)count).order(ByteOrder.nativeOrder());
            long available = this.length - position;
            int readAmount = (int)Math.min(available, count);
            readBuffer.put(this.contents, (int)position, readAmount);
            readBuffer.flip();
            return target.write(readBuffer);
         } catch (BufferOverflowException | IndexOutOfBoundsException e) {
            throw new IOException("Unable to transfer from resource to channel", e);
         } finally {
            this.lock.unlock();
         }
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   @Override
   public long transferFrom(ReadableByteChannel src, long position, long count) throws IOException {
      Objects.requireNonNull(src);
      if (position >= 0L && count >= 0L) {
         this.open();
         throw new NonWritableChannelException();
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   @Override
   public int read(ByteBuffer dst, long position) throws IOException {
      Objects.requireNonNull(dst);
      if (dst.isReadOnly()) {
         throw new IllegalArgumentException("Unable to fill a read-only buffer");
      }

      if (position >= 0L && position <= 2147483647L) {
         int intPosition = (int)position;
         this.open();
         this.lock.lock();

         try {
            int available = this.length - intPosition;
            if (available <= 0) {
               return -1;
            }

            int remaining = dst.remaining();
            int readAmount = Math.min(available, remaining);
            dst.put(this.contents, intPosition, readAmount);
            return readAmount;
         } catch (BufferOverflowException | IndexOutOfBoundsException e) {
            throw new IOException("Unable to read from resource into buffer", e);
         } finally {
            this.lock.unlock();
         }
      } else {
         throw new IllegalArgumentException("Invalid position " + position);
      }
   }

   @Override
   public int write(ByteBuffer src, long position) throws IOException {
      Objects.requireNonNull(src);
      if (position >= 0L && position <= 2147483647L) {
         this.open();
         throw new NonWritableChannelException();
      } else {
         throw new IllegalArgumentException("Invalid position " + position);
      }
   }

   @Override
   public MappedByteBuffer map(MapMode mode, long position, long size) throws IOException {
      Objects.requireNonNull(mode);
      if (position >= 0L && size >= 0L && size <= 2147483647L) {
         throw new IOException(new UnsupportedOperationException("Mapped buffers not supported"));
      } else {
         throw new IllegalArgumentException();
      }
   }

   @Override
   public FileLock lock(long position, long size, boolean shared) throws IOException {
      if (position >= 0L && size >= 0L && position + size >= 0L) {
         throw new IOException(new UnsupportedOperationException("Locks not supported"));
      } else {
         throw new IllegalArgumentException();
      }
   }

   @Override
   public FileLock tryLock(long position, long size, boolean shared) throws IOException {
      if (position >= 0L && size >= 0L && position + size >= 0L) {
         throw new IOException(new UnsupportedOperationException("Locks not supported"));
      } else {
         throw new IllegalArgumentException();
      }
   }

   @Override
   protected void implCloseChannel() {
   }

   private void open() throws IOException {
      if (!this.isOpen()) {
         throw new ClosedChannelException();
      }
   }
}
