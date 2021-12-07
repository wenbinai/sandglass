在新增的Concurrent包中，BlockingQueue很好的解决了多线程中，如何高效安全“传输”数据的问题。通过这些高效并且线程安全的队列类，为我们快速搭建高质量的多线程程序带来极大的便利。本文简要介绍下BlockingQueue接口中几个方法的作用及区别。

     1、add(E e)

      在不违反容量限制的情况下，可立即将指定元素插入此队列，成功返回true，当无可用空间时候，返回IllegalStateException异常。

     2、offer(E e)

      在不违反容量限制的情况下，可立即将指定元素插入此队列，成功返回true，当无可用空间时候，返回false。

      3、put(E e)

      直接在队列中插入元素，当无可用空间时候，阻塞等待。

      4、offer(E e, long time, timeunit unit)

      将给定元素在给定的时间内设置到队列中，如果设置成功返回true, 否则返回false。

      5、E take()

      获取并移除队列头部的元素，无元素时候阻塞等待。

      6、E poll( long time, timeunit unit)

      获取并移除队列头部的元素，无元素时候阻塞等待指定时间。
