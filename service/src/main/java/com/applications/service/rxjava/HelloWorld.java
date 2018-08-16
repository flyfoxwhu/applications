package com.applications.service.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by hukaisheng on 2017/3/20.
 */
public class HelloWorld {

    public static void main(String[] args) throws InterruptedException {

        /*Flowable<String> source = Flowable.fromCallable(() -> {
            Thread.sleep(1000); //  imitate expensive computation
            return "Done";
        });

        Flowable<String> runBackground = source.subscribeOn(Schedulers.io());

        Flowable<String> showForeground = runBackground.observeOn(Schedulers.single());

        showForeground.subscribe(System.out::println, Throwable::printStackTrace);

        Thread.sleep(2000);*/

        //创建发送数据源
        Observable<String> sender = Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                observableEmitter.onNext("Hi，Weavey！");  //发送数据"Hi，Weavey！"
            }

        });

        //创建个数据接收源
        Observer<String> receiver = new Observer<String>() {

            @Override
            public void onError(Throwable e) {

                //发生错误调用
                System.out.println(e.getMessage());
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(Disposable disposable) {

            }

            @Override
            public void onNext(String s) {

                //正常接收数据调用
                System.out.print(s);  //将接收到来自sender的问候"Hi，Weavey！"
            }
        };

        //发送者和接收者进行绑定
        sender.subscribe(receiver);

    }
}
