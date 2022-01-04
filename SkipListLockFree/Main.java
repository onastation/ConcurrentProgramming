package com.company;

class TestThread extends Thread{
    private SkipListLockFree<Long> skipList;
    public TestThread(SkipListLockFree skipList){
        this.skipList = skipList;
    }
    public void run(){
        long threadId = this.getId();
        System.out.println("Thread id: " + threadId);
        this.skipList.add(threadId);
        System.out.println("Skip List contains my Id: " + this.skipList.contains(threadId));
        System.out.println("Skip List contains randomNumber: " + this.skipList.contains(threadId  * 29));
    }
}
public class Main {



    public static void main(String[] args) {
	    SkipListLockFree<Integer> l = new SkipListLockFree<Integer>();
	    for(int i = 0; i < 5; i ++){
            (new TestThread(l)).start();
        }
    }
}
