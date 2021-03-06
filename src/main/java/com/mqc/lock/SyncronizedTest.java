package com.mqc.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author Administrator
 * @create 2019/11/13 16:49
 */
public class SyncronizedTest {
    /*锁的优化
            自旋锁和自适应性自旋锁
    自旋：当有个线程A去请求某个锁的时候，这个锁正在被其它线程占用，但是线程A并不会马上进入阻塞状态，而是循环请求锁(自旋)。
    这样做的目的是因为很多时候持有锁的线程会很快释放锁的，线程A可以尝试一直请求锁，没必要被挂起放弃CPU时间片，
    因为线程被挂起然后到唤醒这个过程开销很大,当然如果线程A自旋指定的时间还没有获得锁，仍然会被挂起。

    自适应性自旋：自适应性自旋是自旋的升级、优化，自旋的时间不再固定，而是由前一次在同一个锁上的自旋时间及锁的拥有者的状态决定。
    例如线程如果自旋成功了，那么下次自旋的次数会增多，因为JVM认为既然上次成功了，那么这次自旋也很有可能成功，那么它会允许自旋的次数更多。
    反之，如果对于某个锁，自旋很少成功，那么在以后获取这个锁的时候，自旋的次数会变少甚至忽略，避免浪费处理器资源。
    有了自适应性自旋，随着程序运行和性能监控信息的不断完善，JVM对程序锁的状况预测就会变得越来越准确，JVM也就变得越来越聪明。

    锁消除
    锁消除是指虚拟机即时编译器在运行时，对一些代码上要求同步，但是被检测到不可能存在共享数据竞争的锁进行消除。

    锁粗化
    在使用锁的时候，需要让同步块的作用范围尽可能小，这样做的目的是为了使需要同步的操作数量尽可能小，如果存在锁竞争，那么等待锁的线程也能尽快拿到锁。

    轻量级锁
    所谓轻量级锁是相对于使用底层操作系统mutex互斥原语实现同步的重量级锁而言的，因为轻量级锁同步的实现是基于对象头的Mark Word。
    那么轻量级锁是怎么使用对象头来实现同步的呢，我们看看具体实现过程。

    获取锁过程：

    在线程进入同步方法、同步块的时候，如果同步对象锁状态为无锁状态(锁标志位为"01"状态，是否为偏向锁为"0")，虚拟机首先将在当前线程的栈帧中建立一个名为锁记录(Lock Recored)的空间，用于储存锁对象目前的Mark Word的拷贝(官方把这份拷贝加了个Displaced前缀，即Displaced Mark Word)。


    将对象头的Mark Word拷贝到线程的锁记录(Lock Recored)中。
    拷贝成功后，虚拟机将使用CAS操作尝试将对象的Mark Word更新为指向Lock Record的指针。如果这个更新成功了，则执行步骤4，否则执行步骤5。
    更新成功，这个线程就拥有了该对象的锁，并且对象Mark Word的锁标志位将转变为"00"，即表示此对象处于轻量级锁的状态。。


    更新失败，虚拟机首先会检查对象的Mark Word是否指向当前线程的栈帧，如果是就说明当前线程已经拥有了这个对象的锁，可以直接进入同步块继续执行，否则说明这个锁对象已经被其其它线程抢占了。进行自旋执行步骤3，如果自旋结束仍然没有获得锁，轻量级锁就需要膨胀为重量级锁，锁标志位状态值变为"10"，Mark Word中储存就是指向monitor对象的指针，当前线程以及后面等待锁的线程也要进入阻塞状态。


    释放锁的过程：

    使用CAS操作将对象当前的Mark Word和线程中复制的Displaced Mark Word替换回来(依据Mark Word中锁记录指针是否还指向本线程的锁记录)，如果替换成功，则执行步骤2，否则执行步骤3。
    如果替换成功，整个同步过程就完成了，恢复到无锁的状态(01)。
    如果替换失败，说明有其他线程尝试获取该锁(此时锁已膨胀)，那就要在释放锁的同时，唤醒被挂起的线程。
    偏向锁
    偏向锁的目的是消除数据在无竞争情况下的同步原语，进一步提高程序的运行性能。如果说轻量级锁是在无竞争的情况下使用CAS操作区消除同步使用的互斥量，那么偏向锁就是在无竞争的情况下把整个同步都消除掉，连CAS操作都不用做了。偏向锁默认是开启的，也可以关闭。
    偏向锁"偏"，就是"偏心"的"偏"，它的意思是这个锁会偏向于第一个获得它的程序，如果在接下来的执行过程中，该锁没有被其他的线程获取，则持有偏向锁的线程将永远不需要再进行同步。

    获取锁的过程：

    检查Mark Word是否为可偏向锁的状态，即是否偏向锁即为1即表示支持可偏向锁，否则为0表示不支持可偏向锁。
    如果是可偏向锁，则检查Mark Word储存的线程ID是否为当前线程ID，如果是则执行同步块，否则执行步骤3。
    如果检查到Mark Word的ID不是本线程的ID，则通过CAS操作去修改线程ID修改成本线程的ID，如果修改成功则执行同步代码块，否则执行步骤4。
    当拥有该锁的线程到达安全点之后，挂起这个线程，升级为轻量级锁。
    锁释放的过程：

    有其他线程来获取这个锁，偏向锁的释放采用了一种只有竞争才会释放锁的机制，线程是不会主动去释放偏向锁，需要等待其他线程来竞争。
    等待全局安全点(在这个是时间点上没有字节码正在执行)。
    暂停拥有偏向锁的线程，检查持有偏向锁的线程是否活着，如果不处于活动状态，则将对象头设置为无锁状态，否则设置为被锁定状态。如果锁对象处于无锁状态，则恢复到无锁状态(01)，以允许其他线程竞争，如果锁对象处于锁定状态，则挂起持有偏向锁的线程，并将对象头Mark Word的锁记录指针改成当前线程的锁记录，锁升级为轻量级锁状态(00)。


    锁的转换过程
    锁主要存在4种状态，级别从低到高依次是：无锁状态、偏向锁状态、轻量级锁状态和重量级锁状态，这几个状态会随着竞争的情况逐渐升级，这几个锁只有重量级锁是需要使用操作系统底层mutex互斥原语来实现，其他的锁都是使用对象头来实现的。需要注意锁可以升级，但是不可以降级。


    这里盗个图，这个图总结的挺好的！


            ————————————————
    版权声明：本文为CSDN博主「薛8」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
    原文链接：https://blog.csdn.net/xueba8/article/details/88753443*/

    public int i=0;

    public synchronized void test1(){
        i=i+1;

    }

    public synchronized void test2(){
        i=i+1;
    }

    public static void main(String[] args) {
        SyncronizedTest test=new SyncronizedTest();
        ExecutorService service= Executors.newCachedThreadPool();
        for(int i=0;i<10000;i++){
            service.submit(()->{
                test.test1();
            });
        }

        for(int i=0;i<10000;i++){
            service.submit(()->{
                test.test2();
            });
        }
        service.shutdown();
        while (!service.isTerminated()){

        }
        System.out.println(test.i);
    }
}
