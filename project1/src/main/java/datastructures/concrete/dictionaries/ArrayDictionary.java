package datastructures.concrete.dictionaries;

import datastructures.interfaces.IDictionary;
import misc.exceptions.NoSuchKeyException;

/**
 * See IDictionary for more details on what this class should do
 */
public class ArrayDictionary<K, V> implements IDictionary<K, V> {
    // You may not change or rename this field: we will be inspecting
    // it using our private tests.
    private Pair<K, V>[] pairs;

    // You're encouraged to add extra fields (and helper methods) though!
    
    // Size of the full array
    private int arraySize;
    // number of elements filled in the array
    private int elementsLength;

    public ArrayDictionary() {
        arraySize = 0;
        elementsLength = 150;
        pairs = makeArrayOfPairs(elementsLength);
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain Pair<K, V> objects.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private Pair<K, V>[] makeArrayOfPairs(int arraySize) {
        // It turns out that creating arrays of generic objects in Java
        // is complicated due to something known as 'type erasure'.
        //
        // We've given you this helper method to help simplify this part of
        // your assignment. Use this helper method as appropriate when
        // implementing the rest of this class.
        //
        // You are not required to understand how this method works, what
        // type erasure is, or how arrays and generics interact. Do not
        // modify this method in any way.
        return (Pair<K, V>[]) (new Pair[arraySize]);

    }

    /**
     * Returns the value corresponding to the given key.
     *
     * @throws NoSuchKeyException if the dictionary does not contain the given key.
     */
    @Override
    public V get(K key) {
        V result;
        if (!containsKey(key)) {
        		throw new NoSuchKeyException();

        } else {
            result = pairs[getKeyPosition(key)].value;
        }
        return result;
    }

    /**
     * Adds the key-value pair to the dictionary. If the key already exists in the dictionary,
     * replace its value with the given one.
     */
    @Override
    public void put(K key, V value) {
        if (arraySize == elementsLength) {
            extendSize();
        }
        if (!containsKey(key)) {
        		pairs[arraySize] = new Pair<K, V>(key, value);
            arraySize++;
        } else {
            pairs[getKeyPosition(key)].value = value;
        }
    }

    /**
     * Remove the key-value pair corresponding to the given key from the dictionary.
     *
     * @throws NoSuchKeyException if the dictionary does not contain the given key.
     */
    @Override
    public V remove(K key) {
        V result;
        if (!containsKey(key)) {
        	throw new NoSuchKeyException();

        } else {
        	int position = getKeyPosition(key);
            result = pairs[position].value;
            for (int i = position; i < arraySize - 1; i++) {
                pairs[i] = pairs[i + 1];
            }
            arraySize--;
        }
        return result;
    }
    
    /**
     * Returns 'true' if the dictionary contains the given key and 'false' otherwise.
     */
    @Override
    public boolean containsKey(K key) {
        return getKeyPosition(key) != -1;
    }
    
    /**
     * Returns the number of key-value pairs stored in this dictionary.
     */
    @Override
    public int size() {
        return arraySize;
    }

    /**
     * Extend internal array to double length without affecting
       original data.
     */
    private void extendSize() {
        Pair<K, V>[] temp = makeArrayOfPairs(elementsLength * 2);
        for (int i = 0; i < arraySize; i++) {
            temp[i] = pairs[i];
        }
        pairs = temp;
        elementsLength *= 2;
    }

    /**
     * Return the position of given key inside the internal
       array. If return -1 then no such key exists.
     */
    private int getKeyPosition(K key) {
      int position = -1;
      if (key != null)
      {
          for (int i = 0; i < arraySize; i++)
          {
              if (pairs[i].key.equals(key) || pairs[i].key == key)
              {
                  return i;
              }
          }
      } else {
            for (int i = 0; i < arraySize; i++) {
                    if (pairs[i].key == key) {
                        position = i;
                    }
                }
       }
      return position;
    }

    private static class Pair<K, V> {
        public K key;
        public V value;

        // You may add constructors and methods to this class as necessary.
        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return this.key + "=" + this.value;
        }
    }
}