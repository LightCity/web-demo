package me.longli.demo.state_machine;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.*;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.time.LocalDate;

@Configuration
@EnableStateMachine(name = "orderMachine")
public class MyStateMachineConfig extends EnumStateMachineConfigurerAdapter<MyOrderState, MyOrderEvent> {

//    @Override
//    public void configure(StateMachineConfigBuilder<MyOrderState, MyOrderEvent> config) throws Exception {
//        super.configure(config);
//    }

    @Bean
    public StateMachineListener<MyOrderState, MyOrderEvent> listener() {
        return new StateMachineListenerAdapter<MyOrderState, MyOrderEvent>() {
            @Override
            public void stateChanged(State<MyOrderState, MyOrderEvent> from, State<MyOrderState, MyOrderEvent> to) {
                System.out.println("State change to " + to.getId());
            }
        };
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<MyOrderState, MyOrderEvent> config) throws Exception {
        config
            .withConfiguration()
                .autoStartup(true)
                .listener(listener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<MyOrderState, MyOrderEvent> states) throws Exception {
        states
            .withStates()
                .initial(MyOrderState.init)
                .states(java.util.EnumSet.allOf(MyOrderState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<MyOrderState, MyOrderEvent> transitions) throws Exception {
        transitions
            .withExternal()
                .source(MyOrderState.init)
                .target(MyOrderState.created)
                .event(MyOrderEvent.placeOrder)
                .and()
            .withExternal()
                // 从已创建订单，到部分付款
                .source(MyOrderState.created)
                .target(MyOrderState.partialPaid)
                .event(MyOrderEvent.pay)
                .guard(new Guard<MyOrderState, MyOrderEvent>() {
                    @Override
                    public boolean evaluate(StateContext<MyOrderState, MyOrderEvent> context) {
                        return true;
                    }
                })
                .and()
            .withInternal()
                // 从部分付款，到部分付款
                .source(MyOrderState.partialPaid)
                .event(MyOrderEvent.pay)
                .guard(new Guard<MyOrderState, MyOrderEvent>() {
                    @Override
                    public boolean evaluate(StateContext<MyOrderState, MyOrderEvent> context) {
                        return true;
                    }
                })
                .and()
            .withExternal()
                // 从已创建订单，到全部付款
                .source(MyOrderState.created)
                .target(MyOrderState.paidOff)
                .event(MyOrderEvent.pay)
                .guard(new Guard<MyOrderState, MyOrderEvent>() {
                    @Override
                    public boolean evaluate(StateContext<MyOrderState, MyOrderEvent> context) {
                        return true;
                    }
                })
                .and()
            .withExternal()
                .source(MyOrderState.created)
                .target(MyOrderState.canceled)
                .event(MyOrderEvent.cancelOrder)
                .and()
            .withExternal()
                .source(MyOrderState.paidOff)
                .target(MyOrderState.refund)
                .event(MyOrderEvent.applyRefund)
        ;
    }

    @Override
    public void configure(StateMachineModelConfigurer<MyOrderState, MyOrderEvent> model) throws Exception {
        super.configure(model);
    }
}
