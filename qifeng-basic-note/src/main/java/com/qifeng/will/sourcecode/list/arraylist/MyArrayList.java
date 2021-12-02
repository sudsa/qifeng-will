package com.qifeng.will.sourcecode.list.arraylist;


import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;


/**
 * Resizable-array implementation of the <tt>List</tt> interface.  Implements
 * all optional list operations, and permits all elements, including
 * <tt>null</tt>.  In addition to implementing the <tt>List</tt> interface,
 * this class provides methods to manipulate the size of the array that is
 * used internally to store the list.  (This class is roughly equivalent to
 * <tt>Vector</tt>, except that it is unsynchronized.)
 *
 * List接口实现了可调整大小的数组，实现所有可选的列表操作，并允许所有元素(包括null)，
 * 除了实现List接口之外，这个类还提供了一些方法,操作内部用于存储列表数组的大小.
 * （这个类大致相当于Vector，只是它是非同步的)
 *
 * <p>The <tt>size</tt>, <tt>isEmpty</tt>, <tt>get</tt>, <tt>set</tt>,
 * <tt>iterator</tt>, and <tt>listIterator</tt> operations run in constant
 * time.  The <tt>add</tt> operation runs in <i>amortized constant time</i>,
 * that is, adding n elements requires O(n) time.  All of the other operations
 * run in linear time (roughly speaking).  The constant factor is low compared
 * to that for the <tt>LinkedList</tt> implementation.
 *
 * size、isEmpty、get、set、iterator、listIterator这些操作在恒定时间内运行，即操作时间复杂度是O(1)。
 * add操作时间复杂度是O(n)。与LinkedList实现相比，常数因子较低
 *
 *
 * <p>Each <tt>ArrayList</tt> instance has a <i>capacity</i>.  The capacity is
 * the size of the array used to store the elements in the list.  It is always
 * at least as large as the list size.  As elements are added to an ArrayList,
 * its capacity grows automatically.  The details of the growth policy are not
 * specified beyond the fact that adding an element has constant amortized
 * time cost.
 *
 * 每个ArrayList实例都有一个容量。容量是用于存储列表中元素的数组的大小。它总是至少和列表大小一样大。
 * 当元素被添加到ArrayList时，它的容量会自动增长。除了添加一个元素具有固定的摊余时间成本这一事实之外，
 * 增长政策的细节没有具体说明。
 *
 * <p>An application can increase the capacity of an <tt>ArrayList</tt> instance
 * before adding a large number of elements using the <tt>ensureCapacity</tt>
 * operation.  This may reduce the amount of incremental reallocation.
 *
 * 应用程序可以在使用ensureCapacity操作添加大量元素之前增加ArrayList实例的容量。
 * 这可能会减少增量重新分配的数量。
 *
 *
 * <p><strong>Note that this implementation is not synchronized.</strong>
 * If multiple threads access an <tt>ArrayList</tt> instance concurrently,
 * and at least one of the threads modifies the list structurally, it
 * <i>must</i> be synchronized externally.  (A structural modification is
 * any operation that adds or deletes one or more elements, or explicitly
 * resizes the backing array; merely setting the value of an element is not
 * a structural modification.)  This is typically accomplished by
 * synchronizing on some object that naturally encapsulates the list.
 *
 * 请注意，此实现类不是同步的。如果多个线程同时访问一个ArrayList实例，并且至少有一个线程在结构上修改了该列表，
 * 则必须在外部对其进行同步。（结构修改是添加或删除一个或多个元素，或显式调整后备数组大小的任何操作；
 * 仅设置元素的值不是结构修改。）这通常通过在自然封装列表的某个对象上进行同步来完成。
 *
 *
 * If no such object exists, the list should be "wrapped" using the
 * {@link Collections#synchronizedList Collections.synchronizedList}
 * method.  This is best done at creation time, to prevent accidental
 * unsynchronized access to the list:<pre>
 *   List list = Collections.synchronizedList(new ArrayList(...));</pre>
 *
 * 如果不存在此类对象，则应使用Collections.synchronizedList方法。
 * 最好在创建时执行此操作，以防止意外地对列表进行非同步访问
 *
 * <p><a name="fail-fast">
 *  错误机制fail-fast机制
 * The iterators returned by this class's {@link #iterator() iterator} and
 * {@link #listIterator(int) listIterator} methods are <em>fail-fast</em>:</a>
 * if the list is structurally modified at any time after the iterator is
 * created, in any way except through the iterator's own
 * {@link ListIterator#remove() remove} or
 * {@link ListIterator#add(Object) add} methods, the iterator will throw a
 * {@link ConcurrentModificationException}.  Thus, in the face of
 * concurrent modification, the iterator fails quickly and cleanly, rather
 * than risking arbitrary, non-deterministic behavior at an undetermined
 * time in the future.
 * 由iterator()和listIterator()返回的迭代器使用fail-fast机制，如果列表在迭代器创建后，任何时候进行结构上的修改，除了迭代器自身之外的任何方式，
 * 即ListIterator#remove（）或ListIterator#add（Object），迭代器将抛出ConcurrentModificationException异常。因此，在面对并发修改时，
 * 迭代器会快速利索地失败，而不是在将来某个不确定的时间，冒着任意的、不确定的行为的风险。
 *
 *
 * <p>Note that the fail-fast behavior of an iterator cannot be guaranteed
 * as it is, generally speaking, impossible to make any hard guarantees in the
 * presence of unsynchronized concurrent modification.  Fail-fast iterators
 * throw {@code ConcurrentModificationException} on a best-effort basis.
 * Therefore, it would be wrong to write a program that depended on this
 * exception for its correctness:  <i>the fail-fast behavior of iterators
 * should be used only to detect bugs.</i>
 *
 * 注意，迭代器的fail-fast并不能得到保证，它不能够保证一定出现该错误。一般来说，fail-fast会尽最大努力
 * 抛出ConcurrentModificationException异常。因此，为提高此类操作的正确性而编写一个依赖于此异常的程序
 * 是错误的做法，正确做法是：ConcurrentModificationException应该仅用于检查bug。
 *
 *
 * <p>This class is a member of the Java Collections Framework
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * </a>.
 * 这个类是Java集合框架的成员
 *
 */

/**
 * 〈一句话功能简述〉<br>
 * 〈ArrayList源码〉
 *  耗时统计：
 *  2020-05-02 1h
 *  2020-05-03 2h
 *  2020-05-05 1.5h
 *  2020-05-07 3.5h
 *  2020-05-08 2h
 *  2020-05-19 2.5h
 *
 *  其他：
 *  1h
 *
 *
 * @author howill.zou
 * @create 2020/5/2
 * @since 1.0.0
 */

public class MyArrayList<E> extends MyAbstractList<E> implements List<E>, RandomAccess, Cloneable, java.io.Serializable {

    private static final long serialVersionUID = 8683452581122892189L;

    /**
     * 默认初始容量，10
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Shared empty array instance used for empty instances.
     * 声明一个空实例数组，它是共享的空数组实例
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /**
     * 声明一个默认大小（10个）的空实例数组，它是共享的空数组实例。
     * 我们要把它与 EMPTY_ELEMENTDATA 区分开，在添加第一个元素时，知道如何去扩张它。
     * 即，它与 EMPTY_ELEMENTDATA 的区别在于当第一个元素被加入进来的时候它知道如何扩张
     */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    /**
     * 存储ArrayList元素的数组缓冲区。ArrayList的容量是此数组缓冲区的长度。
     * 添加第一个元素时，elementData==DEFAULTCAPACITY_EMPTY_ELEMENTDATA的任何
     * 空ArrayList都将扩展为DEFAULT_CAPACITY
     */
    // non-private to simplify nested class access 非私有以简化嵌套类访问
    transient Object[] elementData;

    /**
     * 数组列表的大小
     * @serial
     */
    private int size;

    /**
     * 构造函数，指定容量大小
     *
     * @param  initialCapacity  the initial capacity of the list
     * @throws IllegalArgumentException if the specified initial capacity
     *         is negative
     */
    public MyArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+ initialCapacity);
        }
    }

    /**
     * 构造函数，初始化空的数组，此时，数组长度为0
     */
    public MyArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    /**
     * 构造函数，构造一个包含指定集合元素的列表，顺序按照集合元素的顺序
     *
     * @param c 要将其元素放入此列表中的集合
     * @throws NullPointerException
     */
    public MyArrayList(Collection<? extends E> c) {
        elementData = c.toArray();
        if ((size = elementData.length) != 0) {
            // c.toArray 可能(不正确)返回 Object[] (see 6260652)
            if (elementData.getClass() != Object[].class){
                elementData = Arrays.copyOf(elementData, size, Object[].class);
            }
        } else {
            // 替换为空数组
            this.elementData = EMPTY_ELEMENTDATA;
        }
    }

    /**
     * 将此ArrayList实例的容量修剪为列表的当前大小。
     * 应用程序可以使用此操作最小化ArrayList实例的存储空间。
     *
     */
    public void trimToSize() {
        modCount++;
        if (size < elementData.length) {
            elementData = (size == 0) ? EMPTY_ELEMENTDATA : Arrays.copyOf(elementData, size);
        }
    }

    /**
     * 增加ArrayList实例的容量时，确保它至少可以容纳由最小容量参数指定元素的数量。
     *
     * @param   minCapacity   所需的最小容量
     */
    public void ensureCapacity(int minCapacity) {
        // 最小展开: 存储ArrayList元素的数组缓冲区与默认大小的空实例的共享空数组实例，不等0，相等10
        int minExpand = (elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA) ? 0: DEFAULT_CAPACITY;
        // 最小容量大于最小展开
        if (minCapacity > minExpand) {
            ensureExplicitCapacity(minCapacity);
        }
    }

    /**
     * 确保需要的容量，数据组为空时初始化数组的默认长度
     *
     * @param minCapacity
     */
    private void ensureCapacityInternal(int minCapacity) {
        // 判断elementData是不是为空,即判断ArrayList是否为空
        // 如果是空的，minCapacity等于默认容量与minCapacity中的大值，即初始化数组的容量
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }

        ensureExplicitCapacity(minCapacity);
    }

    /**
     * 确保明确需要的容量，判断是否需要扩容
     *
     * @param minCapacity
     */
    private void ensureExplicitCapacity(int minCapacity) {

        // fail fast机制的维护的计数值
        modCount++;

        // 最小容量大于数组长度时，扩容
        if (minCapacity - elementData.length > 0) {
            grow(minCapacity);
        }
    }

    /**
     * 最大的数组长度
     * 一些vm在数组中保留一些头字。尝试分配更大的数组可能会导致OutOfMemoryError错误：
     * 请求的数组大小超过VM限制
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    /**
     * 扩容方法
     * 增加容量以确保它至少可以容纳由最小容量参数指定的个元素
     *
     * @param minCapacity 所需的最小容量
     */
    private void grow(int minCapacity) {
        // 考虑了溢出情况的代码，以后有时间研究一下20200503
        //老容量
        int oldCapacity = elementData.length;
        //新容量=老容量+老容量向右移1位(缩小一倍，就是0.5倍)，即1+0.5=1.5
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        //新容量小于所需的最小容量时，新容量赋值为所需的最小容量时
        if (newCapacity - minCapacity < 0) {
            newCapacity = minCapacity;
        }
        //新容量大于MAX_ARRAY_SIZE时，调用hugeCapacity(int minCapacity)方法
        if (newCapacity - MAX_ARRAY_SIZE > 0) {
            newCapacity = hugeCapacity(minCapacity);
        }
        // 扩容
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    /**
     * 计算最大容量
     *
     * @param minCapacity
     * @return
     */
    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) {
            throw new OutOfMemoryError();
        }
        // 最小容量大于MAX_ARRAY_SIZE，则使用Integer.MAX_VALUE，反之MAX_ARRAY_SIZE
        // 注意：使用Integer.MAX_VALUE存在内存溢出的风险
        return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
    }

    /**
     * 返回元素数
     *
     * @return 此列表中的元素数
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * 判断是否为空
     *
     * @return 如果此列表不包含元素，则返回true
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 是否包含此元素，更正式地说，如果且仅当此列表包含至少一个元素时，返回true
     * 具体比较规则见indexOf(Object o)
     *
     * @param o element whose presence in this list is to be tested
     * @return  如果此列表包含指定的元素，返回true
     */
    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    /**
     * 返回此列表中指定元素最后一次出现的索引，如果此列表不包含该元素，则返回-1
     * o==null时，使用==比较，o!=null时，使用equals比较
     *
     * @param o
     * @return
     */
    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size-1; i >= 0; i--) {
                if (elementData[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = size-1; i >= 0; i--) {
                if (o.equals(elementData[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 返回此列表中指定元素的第一个匹配项的索引，如果此列表不包含该元素，则返回-1
     *
     * @param o
     * @return
     */
    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (elementData[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(elementData[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 返回此ArrayList实例的浅层副本。（不会复制元素本身）
     *
     * @return a clone of this <tt>ArrayList</tt> instance
     */
    @Override
    public Object clone() {
        try {
            MyArrayList<?> v = (MyArrayList<?>) super.clone();
            v.elementData = Arrays.copyOf(elementData, size);
            v.modCount = 0;
            return v;
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
    }

    /**
     * 返回一个数组，该数组按正确顺序（从第一个元素到最后一个元素）包含此列表中的所有元素
     *
     * 返回的数组将是“安全的”，因为此列表不维护对它的引用。（换句话说，此方法必须分配一个新数组）。
     * 因此，调用者可以自由地修改返回的数组。
     *
     * 此方法充当基于数组和基于集合的API之间的桥梁
     *
     * @return 一个数组，包含该列表中所有元素的正确序列
     *
     */
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    /**
     *
     * 返回一个数组，该数组以正确的顺序（从第一个元素到最后一个元素）包含此列表中的所有元素；
     * 返回的数组的运行时类型是指定数组的运行时类型。如果列表符合指定的数组，则返回该列表。
     * 否则，将使用指定数组的运行时类型和此列表的大小分配新数组。
     *
     * 如果列表适合指定的数组，并且有多余的空间（即，数组中的元素多于列表中的元素），
     * 则紧跟在集合末尾的数组中的元素设置为null。（仅当调用方知道列表不包含任何空元素时，
     * 这在确定列表的长度时才有用）
     *
     * @param a 如果列表中的元素足够大，则存储到其中的数组；否则，为此目的将分配一个相同运行时类型的新数组
     *
     * @return an array containing the elements of the list
     * @throws ArrayStoreException if the runtime type of the specified array
     *         is not a supertype of the runtime type of every element in
     *         this list
     * @throws NullPointerException if the specified array is null
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            // Make a new array of a's runtime type, but my contents:
            return (T[]) Arrays.copyOf(elementData, size, a.getClass());
        }
        System.arraycopy(elementData, 0, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    //-------------------位置访问操作--------------------

    /**
     * 获取索引位置的元素
     *
     * @param index
     * @return
     */
    @SuppressWarnings("unchecked")
    E elementData(int index) {
        return (E) elementData[index];
    }

    /**
     * 返回此列表中指定位置的元素
     *
     * @param  index 要返回的元素的索引
     * @return 此列表中指定位置的元素
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public E get(int index) {

        // 检查给定索引是否在范围内
        rangeCheck(index);
        // 返回索引位置的元素
        return elementData(index);

    }

    /**
     * 将此列表中指定位置的元素替换为指定元素
     *
     * @param index 要替换的元素的索引
     * @param element 要存储在指定位置的元素
     * @return 返回该索引位置的老元素
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public E set(int index, E element) {

        // 检查给定索引是否在范围内
        rangeCheck(index);
        // 获取该索引位置的老元素
        E oldValue = elementData(index);
        // 替换该索引位置的元素
        elementData[index] = element;
        // 返回该索引位置的老元素
        return oldValue;

    }

    /**
     * 将指定的元素追加到此列表的末尾
     *
     * @param e 要添加到此列表的元素
     * @return  只返回为true，这是实现Collection接口add()原因，只能是boolean类型
     */
    @Override
    public boolean add(E e) {
        // 确保需要的容量（校验验添加元素后是否需要扩容），并且 Increments modCount
        ensureCapacityInternal(size + 1);
        // 添加元素,并size自增一（注意这里是i++，先赋值，后+1。原因：数组索引从0开始，数组长度从1开始）
        elementData[size++] = e;
        // 返回成功
        return true;
    }

    /**
     *
     * 在列表指定位置插入指定元素
     * 如果当前位置存在元素，移动该位置元素向和右边的任何后续元素
     *
     * @param index 要插入指定元素的索引
     * @param element 要插入的元素
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public void add(int index, E element) {

        // 检查添加范围是否数组下标越界
        rangeCheckForAdd(index);
        // 确保需要的容量，并且 Increments modCount
        ensureCapacityInternal(size + 1);
        // 将索引位置在index之后的元素都右移一位
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        // 插入index位置的元素
        elementData[index] = element;
        // 个数加一
        size++;

    }

    /**
     * 删除此列表中指定位置的元素，将任何后续元素向左移动（从其索引中减去一个）
     *
     * @param index 要删除的元素的索引
     * @return 返回该索引位置的老元素
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public E remove(int index) {

        // 检查给定索引是否在范围内
        rangeCheck(index);
        // 修改次数自增
        modCount++;
        // 获取该索引位置的老元素
        E oldValue = elementData(index);

        // 计算需要移动的元素个数
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            // 将index+1位置及之后的所有元素，向左移动一个位置
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }

        // size自减，并且原数组的最大索引位置设置为null，允许GC完成其工作
        elementData[--size] = null;
        // 返回该索引位置的老元素
        return oldValue;

    }

    /**
     *
     * 从列表中删除指定元素的第一个匹配项（如果存在）。如果列表不包含元素，则它将保持不变。
     * 更正式地说，删除索引最低的元素。如果存在这样的元素，即o==null?get(i)==null:o.equals(get(i))
     * 如果此列表包含指定元素（或者如果此列表由于调用而更改，则返回true。
     *
     * @param o 要从此列表中删除的元素（如果存在）
     * @return 如果此列表包含指定的元素，返回true
     */
    @Override
    public boolean remove(Object o) {
        // 如果要删除对象为空
        if (o == null) {
            // 循环查找
            for (int index = 0; index < size; index++) {
                // 使用==，查找为空元素
                if (elementData[index] == null) {
                    fastRemove(index);
                    return true;
                }
            }
        // 如果要删除对象不为空
        } else {
            // 循环查找
            for (int index = 0; index < size; index++) {
                // 使用equals，查找要删除的元素
                if (o.equals(elementData[index])) {
                    fastRemove(index);
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * 跳过边界检查且不返回已删除值的私有删除方法
     */
    private void fastRemove(int index) {
        modCount++;
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        elementData[--size] = null;
    }

    /**
     * 从列表中删除所有元素
     */
    @Override
    public void clear() {
        // 修改次数自增
        modCount++;

        // 允许GC完成其工作
        for (int i = 0; i < size; i++) {
            elementData[i] = null;
        }
        size = 0;
    }

    /**
     * 将指定集合中的所有元素追加到此列表的末尾，顺序是指定集合的迭代器。
     * 如果在操作进行时修改了指定的集合，则此操作的行为未定义。
     * （这意味着，如果指定的集合是此列表，并且此列表是非空的，则此调用的行为未定义。）
     *
     * @param c 包含要添加到此列表中的元素的集合
     * @return 如果此列表因调用而更改，返回true
     * @throws NullPointerException if the specified collection is null
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        // 集合转换为数组
        Object[] a = c.toArray();
        // 获取数组的长度
        int numNew = a.length;
        // 确保需要的容量，并且 Increments modCount
        ensureCapacityInternal(size + numNew);
        // 复制数组：原数组，从原数组的起始位置开始，目标数组，目标数组的开始起始位置，要copy的数组的长度
        System.arraycopy(a, 0, elementData, size, numNew);
        // 添加数组大小
        size += numNew;
        // 返回是否被修改
        return numNew != 0;
    }

    /**
     * 从指定位置开始，将指定集合中的所有元素插入此列表。移动元素当前位于该位置（如果有）
     * 和右侧的任何后续元素（增加其索引）。新元素将按指定集合的迭代器返回它们的顺序出现在列表中。
     *
     * @param index 从指定集合中插入第一个元素的索引
     * @param c  包含要添加到此列表中的元素的集合
     * @return  如果此列表因调用而更改，返回true
     * @throws IndexOutOfBoundsException {@inheritDoc}
     * @throws NullPointerException if the specified collection is null
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {

        // 检查添加范围是否数组下标越界
        rangeCheckForAdd(index);
        // 集合转换为数组
        Object[] a = c.toArray();
        // 获取数组的长度
        int numNew = a.length;
        // 确保需要的容量，并且 Increments modCount
        ensureCapacityInternal(size + numNew);
        // 移位置
        int numMoved = size - index;
        if (numMoved > 0) {
            System.arraycopy(elementData, index, elementData, index + numNew, numMoved);
        }
        // 插入集合
        System.arraycopy(a, 0, elementData, index, numNew);
        // 添加数组大小
        size += numNew;
        // 返回是否被修改
        return numNew != 0;
    }

    /**
     * 从该列表中删除索引位于fromIndex（包含）和 toIndex（排除）之间的所有元素。
     * 将任何后续元素向左移动（减少它们的索引）。此调用通过toIndex - fromIndex 元素缩短列表。
     * 如果toIndex = fromIndex ，则此操作无效。
     *
     * @throws IndexOutOfBoundsException if {@code fromIndex} or
     *         {@code toIndex} is out of range
     *         ({@code fromIndex < 0 ||
     *          fromIndex >= size() ||
     *          toIndex > size() ||
     *          toIndex < fromIndex})
     */
    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        // 修改次数自增
        modCount++;
        // 移动的数量
        int numMoved = size - toIndex;
        // 移动数组
        System.arraycopy(elementData, toIndex, elementData, fromIndex,
                numMoved);
        // 允许GC完成其工作
        int newSize = size - (toIndex-fromIndex);
        for (int i = newSize; i < size; i++) {
            elementData[i] = null;
        }
        // 复制数组大小
        size = newSize;
    }

    /**
     * 检查给定索引是否在范围内。如果不是，则抛出适当的运行时异常。此方法不检查索引是否为负：它总是在数组访问之前使用，
     * 如果索引为负，则数组访问将抛出ArrayIndexOutOfBoundsException。
     *
     */
    private void rangeCheck(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    /**
     * 检查添加范围是否数组下标越界，用于add()、addAll()
     *
     * @param index
     */
    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    /**
     * 构造IndexOutboundsException详细消息。
     * 在许多可能的错误处理代码重构中，这种“大纲”对服务器和客户机vm的性能最好。
     *
     */
    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }

    /**
     * 从该列表中删除指定集合中包含的所有元素。
     *
     * @param c 包含要从此列表中删除的元素的集合
     * @return {@code true} 如果此列表因调用而更改，返回true
     * @throws ClassCastException 如果此列表中某个元素的类与指定的集合不兼容
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if this list contains a null element and the
     *         specified collection does not permit null elements
     * (<a href="Collection.html#optional-restrictions">optional</a>),
     *         or if the specified collection is null
     * @see Collection#contains(Object)
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        // 检查指定的对象引用是否不是为null
        Objects.requireNonNull(c);
        // 批量删除
        return batchRemove(c, false);
    }


    /**
     * 仅保留此列表中包含在指定集合中的元素。换句话说，
     * 从该列表中删除指定集合中未包含的所有元素。
     *
     * @param c 包含要保留在此列表中的元素的集合
     * @return {@code true} 如果此列表因调用而更改，返回true
     * @throws ClassCastException 如果此列表中某个元素的类与指定的集合不兼容
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if this list contains a null element and the
     *         specified collection does not permit null elements
     * (<a href="Collection.html#optional-restrictions">optional</a>),
     *         or if the specified collection is null
     * @see Collection#contains(Object)
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        // 检查指定的对象引用是否不是为null
        Objects.requireNonNull(c);
        // 比较后，批量删除
        return batchRemove(c, true);
    }

    /**
     * 批量删除
     *
     * @param c
     * @param complement
     * @return
     */
    private boolean batchRemove(Collection<?> c, boolean complement) {
        final Object[] elementData = this.elementData;
        //r是read，意思是读取；w是write，意思是写入
        int r = 0, w = 0;
        boolean modified = false;
        try {
            for (; r < size; r++) {
                // 根据complement值，存储需要的结果。
                // 情况一：c.contains(elementData[r])为true时，证明列表包含此元素，
                // 如果complement再为true时，if判断条件为true，进入if代码内部。此时，
                // elementData[w++]存储的是列表与集合c相同的元素。
                // 情况二：c.contains(elementData[r])为false时，证明列表不包含此元素，
                // 如果complement再为false时，if判断条件为true，进入if代码内部。此时，
                // elementData[w++]存储的是列表与集合c不相同的元素。
                if (c.contains(elementData[r]) == complement) {
                    elementData[w++] = elementData[r];
                }
            }
        } finally {
            //如果代码正常运行，将r = size，这样做的目的是保持与AbstractCollection的行为兼容性，
            // 即使c.contains（）抛出异常。
            if (r != size) {
                System.arraycopy(elementData, r, elementData, w, size - r);
                w += size - r;
            }
            // 如果 w = size时，证明没有被修改过
            if (w != size) {
                // clear to let GC do its work
                for (int i = w; i < size; i++) {
                    elementData[i] = null;
                }
                modCount += size - w;
                size = w;
                modified = true;
            }
        }
        return modified;
    }

    /**
     * 将ArrayList实例的状态保存到流中（即序列化它）
     *
     * 以后学习IO后再处理 2020-05-08
     *
     * @serialData The length of the array backing the <tt>ArrayList</tt>
     *             instance is emitted (int), followed by all of its elements
     *             (each an <tt>Object</tt>) in the proper order.
     */
    private void writeObject(java.io.ObjectOutputStream s)
            throws java.io.IOException{

        // 写出元素计数
        int expectedModCount = modCount;
        // 任何隐藏的内容
        s.defaultWriteObject();

        // Write out size as capacity for behavioural compatibility with clone()
        s.writeInt(size);

        // Write out all elements in the proper order.
        for (int i=0; i<size; i++) {
            s.writeObject(elementData[i]);
        }

        //
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }

    /**
     * 从流中重构ArrayList实例（即反序列化）
     *
     * 以后学习IO后再处理 2020-05-08
     *
     */
    private void readObject(java.io.ObjectInputStream s)
            throws java.io.IOException, ClassNotFoundException {
        elementData = EMPTY_ELEMENTDATA;

        // Read in size, and any hidden stuff
        s.defaultReadObject();

        // Read in capacity
        s.readInt(); // ignored

        if (size > 0) {
            // be like clone(), allocate array based upon size not capacity
            ensureCapacityInternal(size);

            Object[] a = elementData;
            // Read in all elements in the proper order.
            for (int i=0; i<size; i++) {
                a[i] = s.readObject();
            }
        }
    }

    /**
     * Returns a list iterator over the elements in this list (in proper
     * sequence), starting at the specified position in the list.
     * The specified index indicates the first element that would be
     * returned by an initial call to {@link ListIterator#next next}.
     * An initial call to {@link ListIterator#previous previous} would
     * return the element with the specified index minus one.
     * 返回此列表中元素的列表迭代器（在适当的情况下顺序），从列表中的指定位置开始。
     * 指定的索引只是对ListIterator#next的初始调用将返回的第一个元素，对ListIterator#previous
     * 的初始调用将返回指定索引-1的元素。
     *
     * 返回的列表迭代器使用fail fast机制
     *
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public ListIterator<E> listIterator(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
        return new ListItr(index);
    }

    /**
     * 返回此列表中元素的列表迭代器（在适当的情况下顺序）
     *
     * 返回的列表迭代器使用fail fast机制
     *
     * @see #listIterator(int)
     */
    @Override
    public ListIterator<E> listIterator() {
        return new ListItr(0);
    }

    /**
     * 以正确的顺序返回此列表中元素的迭代器。
     *
     * 返回的列表迭代器使用fail fast机制
     *
     * @return 按适当顺序遍历此列表中元素的迭代器
     */
    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    /**
     * AbstractList.Itr的优化版本
     */
    private class Itr implements Iterator<E> {

        // 表示下一个元素的索引位置
        int cursor;
        // 最近一次调用next()返回元素的索引。如果通过调用remove()删除此元素，则重置为-1。
        int lastRet = -1;
        // 预期被修改的次数
        int expectedModCount = modCount;

        /**
         * 是否存在下一个元素
         *
         * @return
         */
        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        /**
         * 获取下一个元素
         *
         * @return
         */
        @Override
        @SuppressWarnings("unchecked")
        public E next() {
            // 检查modCount值
            checkForComodification();
            // 赋值i,为cursor
            int i = cursor;
            // 下一个元素的索引大于列表大小抛NoSuchElementException异常
            if (i >= size) {
                throw new NoSuchElementException();
            }
            // 获取ArrayList的elementData数组
            Object[] elementData = MyArrayList.this.elementData;
            // 下一个元素的索引大于elementData数组长度抛ConcurrentModificationException异常
            if (i >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            // 赋值cursor,该元素的下一个索引值
            cursor = i + 1;
            // 赋值lastRet，并获取elementData[i]元素
            return (E) elementData[lastRet = i];
        }

        /**
         * 删除元素
         *
         */
        @Override
        public void remove() {
            // 检验
            if (lastRet < 0) {
                throw new IllegalStateException();
            }
            // 检查modCount值
            checkForComodification();

            try {
                // 删除元素
                MyArrayList.this.remove(lastRet);
                // 相关赋值操作
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        /**
         * 对每个剩余元素执行给定的操作，直到所有元素都被处理或操作引发异常。
         * 如果指定了迭代顺序，则按迭代顺序执行操作。操作引发的异常将中继到调用方
         * 干什么用？ 20200508
         *
         * @param consumer
         */
        @Override
        @SuppressWarnings("unchecked")
        public void forEachRemaining(Consumer<? super E> consumer) {
            Objects.requireNonNull(consumer);
            final int size = MyArrayList.this.size;
            int i = cursor;
            if (i >= size) {
                return;
            }
            final Object[] elementData = MyArrayList.this.elementData;
            if (i >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            while (i != size && modCount == expectedModCount) {
                consumer.accept((E) elementData[i++]);
            }
            // update once at end of iteration to reduce heap write traffic
            cursor = i;
            lastRet = i - 1;
            checkForComodification();
        }

        /**
         * 检查modCount值
         */
        final void checkForComodification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    /**
     * AbstractList.ListItr的优化版本
     */
    private class ListItr extends Itr implements ListIterator<E> {

        /**
         * 构造函数
         *
         * @param index
         */
        ListItr(int index) {
            super();
            cursor = index;
        }

        /**
         * 是否存在前继元素
         *
         * @return
         */
        @Override
        public boolean hasPrevious() {
            return cursor != 0;
        }

        /**
         * 获取下一个元素的索引
         *
         * @return
         */
        @Override
        public int nextIndex() {
            return cursor;
        }

        /**
         * 获取上一个元素的索引
         *
         * @return
         */
        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        /**
         * 获取上一个元素
         *
         * @return
         */
        @Override
        @SuppressWarnings("unchecked")
        public E previous() {
            checkForComodification();
            int i = cursor - 1;
            if (i < 0) {
                throw new NoSuchElementException();
            }
            Object[] elementData = MyArrayList.this.elementData;
            if (i >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            cursor = i;
            return (E) elementData[lastRet = i];
        }

        /**
         *
         * 替换next()返回的最后一个元素
         *
         * @param e
         */
        @Override
        public void set(E e) {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }
            checkForComodification();

            try {
                MyArrayList.this.set(lastRet, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        /**
         * 添加一个元素
         *
         * @param e
         */
        @Override
        public void add(E e) {
            checkForComodification();

            try {
                int i = cursor;
                MyArrayList.this.add(i, e);
                cursor = i + 1;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }

    /**
     * 有些蒙蔽 20200519
     *
     * 返回此列表中指定的fromIndex（包含）和toIndex（不包含）之间部分的视图。
     * 如果fromIndex和toIndex相等，则返回的列表为空。
     *
     * The returned list is backed by this list, so non-structural
     * changes in the returned list are reflected in this list, and vice-versa.
     * The returned list supports all of the optional list operations.
     * 返回的列表由此列表支持，因此返回列表中的非结构更改将反映在此列表中，
     * 反之亦然-反之亦然返回的列表支持所有可选的列表操作。
     *
     * 此方法消除了显式范围操作（数组通常存在的那种类型）的需要。任何需要列表的操作都
     * 可以通过传递子列表视图而不是整个列表来用作范围操作。
     * 例如，以下习惯用法从列表中删除一系列元素：
     *      list.subList(from, to).clear();
     *
     * 可以为{@link#indexOf（Object）}和{@link#lastIndexOf（Object）}构造类似的习惯用法，
     * {@link Collections}类中的所有方法都可以应用于子列表。
     *
     * 如果备份列表(即，此列表）以任何方式进行结构修改，而不是通过返回的列表。
     * （结构修改是那些更改列表大小的修改，或者以其他方式干扰列表，
     *  以致正在进行的迭代可能会产生不正确的结果。）
     *
     * @throws IndexOutOfBoundsException {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        subListRangeCheck(fromIndex, toIndex, size);
        return new SubList(this, 0, fromIndex, toIndex);
    }

    /**
     * 检查subList范围
     *
     * @param fromIndex
     * @param toIndex
     * @param size
     */
    static void subListRangeCheck(int fromIndex, int toIndex, int size) {
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
        }
        if (toIndex > size) {
            throw new IndexOutOfBoundsException("toIndex = " + toIndex);
        }
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException("fromIndex(" + fromIndex +
                    ") > toIndex(" + toIndex + ")");
        }
    }

    /**
     * SubList
     *
     */
    private class SubList extends MyAbstractList<E> implements RandomAccess {
        private final MyAbstractList<E> parent;
        private final int parentOffset;
        private final int offset;
        int size;

        SubList(MyAbstractList<E> parent, int offset, int fromIndex, int toIndex) {
            this.parent = parent;
            this.parentOffset = fromIndex;
            this.offset = offset + fromIndex;
            this.size = toIndex - fromIndex;
            this.modCount = MyArrayList.this.modCount;
        }

        @Override
        public E set(int index, E e) {
            rangeCheck(index);
            checkForComodification();
            E oldValue = MyArrayList.this.elementData(offset + index);
            MyArrayList.this.elementData[offset + index] = e;
            return oldValue;
        }

        @Override
        public E get(int index) {
            rangeCheck(index);
            checkForComodification();
            return MyArrayList.this.elementData(offset + index);
        }

        @Override
        public int size() {
            checkForComodification();
            return this.size;
        }

        @Override
        public void add(int index, E e) {
            rangeCheckForAdd(index);
            checkForComodification();
            parent.add(parentOffset + index, e);
            this.modCount = parent.modCount;
            this.size++;
        }

        @Override
        public E remove(int index) {
            rangeCheck(index);
            checkForComodification();
            E result = parent.remove(parentOffset + index);
            this.modCount = parent.modCount;
            this.size--;
            return result;
        }

        @Override
        protected void removeRange(int fromIndex, int toIndex) {
            checkForComodification();
            parent.removeRange(parentOffset + fromIndex,
                    parentOffset + toIndex);
            this.modCount = parent.modCount;
            this.size -= toIndex - fromIndex;
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            return addAll(this.size, c);
        }

        @Override
        public boolean addAll(int index, Collection<? extends E> c) {
            rangeCheckForAdd(index);
            int cSize = c.size();
            if (cSize==0) {
                return false;
            }
            checkForComodification();
            parent.addAll(parentOffset + index, c);
            this.modCount = parent.modCount;
            this.size += cSize;
            return true;
        }

        @Override
        public Iterator<E> iterator() {
            return listIterator();
        }

        @Override
        public ListIterator<E> listIterator(final int index) {
            checkForComodification();
            rangeCheckForAdd(index);
            final int offset = this.offset;

            return new ListIterator<E>() {
                int cursor = index;
                int lastRet = -1;
                int expectedModCount = MyArrayList.this.modCount;

                @Override
                public boolean hasNext() {
                    return cursor != MyArrayList.SubList.this.size;
                }

                @Override
                @SuppressWarnings("unchecked")
                public E next() {
                    checkForComodification();
                    int i = cursor;
                    if (i >= MyArrayList.SubList.this.size) {
                        throw new NoSuchElementException();
                    }
                    Object[] elementData = MyArrayList.this.elementData;
                    if (offset + i >= elementData.length) {
                        throw new ConcurrentModificationException();
                    }
                    cursor = i + 1;
                    return (E) elementData[offset + (lastRet = i)];
                }

                @Override
                public boolean hasPrevious() {
                    return cursor != 0;
                }

                @Override
                @SuppressWarnings("unchecked")
                public E previous() {
                    checkForComodification();
                    int i = cursor - 1;
                    if (i < 0) {
                        throw new NoSuchElementException();
                    }
                    Object[] elementData = MyArrayList.this.elementData;
                    if (offset + i >= elementData.length) {
                        throw new ConcurrentModificationException();
                    }
                    cursor = i;
                    return (E) elementData[offset + (lastRet = i)];
                }

                @Override
                @SuppressWarnings("unchecked")
                public void forEachRemaining(Consumer<? super E> consumer) {
                    Objects.requireNonNull(consumer);
                    final int size = MyArrayList.SubList.this.size;
                    int i = cursor;
                    if (i >= size) {
                        return;
                    }
                    final Object[] elementData = MyArrayList.this.elementData;
                    if (offset + i >= elementData.length) {
                        throw new ConcurrentModificationException();
                    }
                    while (i != size && modCount == expectedModCount) {
                        consumer.accept((E) elementData[offset + (i++)]);
                    }
                    // update once at end of iteration to reduce heap write traffic
                    lastRet = cursor = i;
                    checkForComodification();
                }

                @Override
                public int nextIndex() {
                    return cursor;
                }

                @Override
                public int previousIndex() {
                    return cursor - 1;
                }

                @Override
                public void remove() {
                    if (lastRet < 0) {
                        throw new IllegalStateException();
                    }
                    checkForComodification();

                    try {
                        MyArrayList.SubList.this.remove(lastRet);
                        cursor = lastRet;
                        lastRet = -1;
                        expectedModCount = MyArrayList.this.modCount;
                    } catch (IndexOutOfBoundsException ex) {
                        throw new ConcurrentModificationException();
                    }
                }

                @Override
                public void set(E e) {
                    if (lastRet < 0) {
                        throw new IllegalStateException();
                    }
                    checkForComodification();

                    try {
                        MyArrayList.this.set(offset + lastRet, e);
                    } catch (IndexOutOfBoundsException ex) {
                        throw new ConcurrentModificationException();
                    }
                }

                @Override
                public void add(E e) {
                    checkForComodification();

                    try {
                        int i = cursor;
                        MyArrayList.SubList.this.add(i, e);
                        cursor = i + 1;
                        lastRet = -1;
                        expectedModCount = MyArrayList.this.modCount;
                    } catch (IndexOutOfBoundsException ex) {
                        throw new ConcurrentModificationException();
                    }
                }

                final void checkForComodification() {
                    if (expectedModCount != MyArrayList.this.modCount) {
                        throw new ConcurrentModificationException();
                    }
                }
            };
        }

        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            subListRangeCheck(fromIndex, toIndex, size);
            return new SubList(this, offset, fromIndex, toIndex);
        }

        private void rangeCheck(int index) {
            if (index < 0 || index >= this.size) {
                throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
            }
        }

        private void rangeCheckForAdd(int index) {
            if (index < 0 || index > this.size) {
                throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
            }
        }

        private String outOfBoundsMsg(int index) {
            return "Index: "+index+", Size: "+this.size;
        }

        private void checkForComodification() {
            if (MyArrayList.this.modCount != this.modCount) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public Spliterator<E> spliterator() {
            checkForComodification();
            return new ArrayListSpliterator<E>(MyArrayList.this, offset,
                    offset + this.size, this.modCount);
        }
    }

    /**
     * forEach循环
     *
     * @param action
     */
    @Override
    public void forEach(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        final int expectedModCount = modCount;
        @SuppressWarnings("unchecked")
        final E[] elementData = (E[]) this.elementData;
        final int size = this.size;
        for (int i=0; modCount == expectedModCount && i < size; i++) {
            action.accept(elementData[i]);
        }
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }

    /**
     * 在此列表中的元素上创建一个延迟绑定和快速失败的链接拆分器。
     *
     * 报表拆分器大小、拆分器子级和拆分器顺序。重写实现应记录其他特征值的报表。
     *
     * @return a {@code Spliterator} over the elements in this list
     * @since 1.8
     */
    @Override
    public Spliterator<E> spliterator() {
        return new MyArrayList.ArrayListSpliterator<>(this, 0, -1, 0);
    }

    /**
     * 基于索引的二分法，延迟初始化的拆分器
     * @param <E>
     */
    static final class ArrayListSpliterator<E> implements Spliterator<E> {

        /*
         * If ArrayLists were immutable, or structurally immutable (no
         * adds, removes, etc), we could implement their spliterators
         * with Arrays.spliterator. Instead we detect as much
         * interference during traversal as practical without
         * sacrificing much performance. We rely primarily on
         * modCounts. These are not guaranteed to detect concurrency
         * violations, and are sometimes overly conservative about
         * within-thread interference, but detect enough problems to
         * be worthwhile in practice. To carry this out, we (1) lazily
         * initialize fence and expectedModCount until the latest
         * point that we need to commit to the state we are checking
         * against; thus improving precision.  (This doesn't apply to
         * SubLists, that create spliterators with current non-lazy
         * values).  (2) We perform only a single
         * ConcurrentModificationException check at the end of forEach
         * (the most performance-sensitive method). When using forEach
         * (as opposed to iterators), we can normally only detect
         * interference after actions, not before. Further
         * CME-triggering checks apply to all other possible
         * violations of assumptions for example null or too-small
         * elementData array given its size(), that could only have
         * occurred due to interference.  This allows the inner loop
         * of forEach to run without any further checks, and
         * simplifies lambda-resolution. While this does entail a
         * number of checks, note that in the common case of
         * list.stream().forEach(a), no checks or other computation
         * occur anywhere other than inside forEach itself.  The other
         * less-often-used methods cannot take advantage of most of
         * these streamlinings.
         */

        private final MyArrayList<E> list;
        private int index; // current index, modified on advance/split
        private int fence; // -1 until used; then one past last index
        private int expectedModCount; // initialized when fence set

        /** Create new spliterator covering the given  range */
        ArrayListSpliterator(MyArrayList<E> list, int origin, int fence,
                             int expectedModCount) {
            this.list = list; // OK if null unless traversed
            this.index = origin;
            this.fence = fence;
            this.expectedModCount = expectedModCount;
        }

        private int getFence() { // initialize fence to size on first use
            int hi; // (a specialized variant appears in method forEach)
            MyArrayList<E> lst;
            if ((hi = fence) < 0) {
                if ((lst = list) == null) {
                    hi = fence = 0;
                } else {
                    expectedModCount = lst.modCount;
                    hi = fence = lst.size;
                }
            }
            return hi;
        }

        @Override
        public MyArrayList.ArrayListSpliterator<E> trySplit() {
            int hi = getFence(), lo = index, mid = (lo + hi) >>> 1;
            return (lo >= mid) ? null : // divide range in half unless too small
                    new ArrayListSpliterator<E>(list, lo, index = mid,
                            expectedModCount);
        }

        @Override
        public boolean tryAdvance(Consumer<? super E> action) {
            if (action == null) {
                throw new NullPointerException();
            }
            int hi = getFence(), i = index;
            if (i < hi) {
                index = i + 1;
                @SuppressWarnings("unchecked") E e = (E)list.elementData[i];
                action.accept(e);
                if (list.modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                return true;
            }
            return false;
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            int i, hi, mc; // hoist accesses and checks from loop
            MyArrayList<E> lst; Object[] a;
            if (action == null) {
                throw new NullPointerException();
            }
            if ((lst = list) != null && (a = lst.elementData) != null) {
                if ((hi = fence) < 0) {
                    mc = lst.modCount;
                    hi = lst.size;
                } else {
                    mc = expectedModCount;
                }
                if ((i = index) >= 0 && (index = hi) <= a.length) {
                    for (; i < hi; ++i) {
                        @SuppressWarnings("unchecked") E e = (E) a[i];
                        action.accept(e);
                    }
                    if (lst.modCount == mc) {
                        return;
                    }
                }
            }
            throw new ConcurrentModificationException();
        }

        @Override
        public long estimateSize() {
            return (long) (getFence() - index);
        }

        @Override
        public int characteristics() {
            return Spliterator.ORDERED | Spliterator.SIZED | Spliterator.SUBSIZED;
        }
    }


    /**
     * 该方法将会批量删除符合filter条件的所有元素，该方法需要一个Predicate对象作为参数，
     * Predicate也是函数式接口，因此可以使用Lambda表达式
     *
     * @param filter
     * @return
     */
    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        Objects.requireNonNull(filter);
        // figure out which elements are to be removed
        // any exception thrown from the filter predicate at this stage
        // will leave the collection unmodified
        int removeCount = 0;
        final BitSet removeSet = new BitSet(size);
        final int expectedModCount = modCount;
        final int size = this.size;
        for (int i=0; modCount == expectedModCount && i < size; i++) {
            @SuppressWarnings("unchecked")
            final E element = (E) elementData[i];
            if (filter.test(element)) {
                removeSet.set(i);
                removeCount++;
            }
        }
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }

        // shift surviving elements left over the spaces left by removed elements
        final boolean anyToRemove = removeCount > 0;
        if (anyToRemove) {
            final int newSize = size - removeCount;
            for (int i=0, j=0; (i < size) && (j < newSize); i++, j++) {
                i = removeSet.nextClearBit(i);
                elementData[j] = elementData[i];
            }
            for (int k=newSize; k < size; k++) {
                elementData[k] = null;  // Let gc do its work
            }
            this.size = newSize;
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            modCount++;
        }

        return anyToRemove;
    }

    /**
     * 用函数接口的返回结果替代原list中的值.
     *
     * @param operator
     */
    @Override
    @SuppressWarnings("unchecked")
    public void replaceAll(UnaryOperator<E> operator) {
        Objects.requireNonNull(operator);
        final int expectedModCount = modCount;
        final int size = this.size;
        for (int i=0; modCount == expectedModCount && i < size; i++) {
            elementData[i] = operator.apply((E) elementData[i]);
        }
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
        modCount++;
    }

    /**
     * 排序
     *
     * @param c
     */
    @Override
    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super E> c) {
        final int expectedModCount = modCount;
        Arrays.sort((E[]) elementData, 0, size, c);
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
        modCount++;
    }

}
