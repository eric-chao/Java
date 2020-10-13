package com.test.demo.interfacelock;

/**
 * @author chao.cheng
 * @createTime 2020/6/8 7:10 下午
 * @description
 **/
public interface AquiredLockWorker<T> {
    T invokeAfterLockAquire() throws Exception;
}
