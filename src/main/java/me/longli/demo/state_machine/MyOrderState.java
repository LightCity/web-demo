package me.longli.demo.state_machine;

public enum MyOrderState {
    init,
    created,
    canceled,
    partialPaid,
    paidOff,
    refund
}
