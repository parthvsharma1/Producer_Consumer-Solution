# Producer_Consumer-Solution
* The Producer-Consumer problem is as follows:
* 1. We have a buffer of fixed size. 
* ii. A producer can produce an item and can place it in the buffer. 
* iii .A consumer can pick items from the buffer and can consume them. 
* iv. We need to ensure that when a producer is placing an item in the buffer, then at the same time consumer should not consume any item. 
* v. In this problem, the buffer is the critical section.
  * vi. If the buffer is empty the consumer should not consume anything and wait for items to be added
  * vii. If buffer is full, producer should Wait for some items to be cleared

We use wait and notify method for obtaining the desired result
