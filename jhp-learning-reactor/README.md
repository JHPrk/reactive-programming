# Reactive Programming(반응형 프로그래밍)

## 특징
1. 메시지 스트림
2. Non-Blocking
3. 비동기적
4. 역압력

# Project Reactor

## 규칙
1. Subscriber 는 Producer 를 구독하고 데이터를 요청해야한다. Producer 가 데이터를 발행하지 않는 한 최대한 Lazy 하게 동작해야함.
2. Subscriber 는 언제든 구독을 취소할 수 있다. (취소 이후에 Producer 는 데이터를 더이상 발행하면 안 됨)
3. Producer 는 onNext 를 통해 데이터 발행
4. Producer 는 모든 데이터를 발행하면 onComplete 를 호출한다.
5. Producer 는 데이터 발행에 문제가 있으면 onError 를 호출한다.
6. Producer 는 onError/onComplete 가 호출되면 어느것도 호출하면 안된다. Subscription 로부터 request/cancel 이 호출되어도 아무런 효과가 없어야 한다.
7. Producer 와 Subscriber 사이에는 데이터를 처리할 수 있는 Processor 를 두어 파이프라인을 구축할 수 있다.

## 동작
1. Subscriber 는 Publisher 가 제공하는 subscribe() 메소드를 통해 구독을 요청한다.
2. 그러면 Publisher 는 구독을 요청한 Subscriber 에게 Subscription 인스턴스를 onSubscribe() 메소드를 통해 전달한다.
3. Subscriber 는 Subscription 인스턴스를 통해 Publisher 에게 데이터를 요청하거나 구독을 취소할 수 있다.
4. Subscriber 가 Subscription 를 통해 데이터를 요청하였을때 Publisher 는 Subscriber 의 onNext 메소드를 통해 요청한 만큼의 데이터를 차례대로 전달한다.
5. Publisher 가 가지고 있는 데이터를 모두 전달하였다면 Subscriber 의 onComplete 메소드를 호출하여 데이터 전달이 끝났다는 것을 알린다. 이후에는 Subscription 인스턴스가 만료되어야 한다.
6. Publisher 가 데이터를 전달하는 과정에서 에러가 발생하면 Subscriber 의 onError 메소드를 호출하여 에러가 발생하였다는 것을 알린다. 이후에는 Subscription 인스턴스가 만료되어야 한다.

# 용어
## Publisher
- Source
- Observable
- Upstream
- Producer
## Subscriber
- Sink
- Observer
- Downstream
- Consumer
## Processor
- Operator

