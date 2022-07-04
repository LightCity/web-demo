package me.longli.demo.state_machine;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.*;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

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
                .source(MyOrderState.init).target(MyOrderState.paidOff).event(MyOrderEvent.pay)
                .and()
            .withExternal()
                .source(MyOrderState.init).target(MyOrderState.refund).event(MyOrderEvent.cancel);
    }

    @Override
    public void configure(StateMachineModelConfigurer<MyOrderState, MyOrderEvent> model) throws Exception {
        super.configure(model);
    }
}
