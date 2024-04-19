package com.mrdev.androidtestassesment.other;

public abstract class CallBack<T> {

    public abstract void onSuccess(T t);

    public void onError(String error) { }
}


