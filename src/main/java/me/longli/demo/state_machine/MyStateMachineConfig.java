package me.longli.demo.state_machine;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.*;

@Configuration
@EnableStateMachine
public class MyStateMachineConfig extends EnumStateMachineConfigurerAdapter<MyOrderState, MyOrderEvent> {

//    @Override
//    public void configure(StateMachineConfigBuilder<MyOrderState, MyOrderEvent> config) throws Exception {
//        super.configure(config);
//    }

    @Override
    public void configure(StateMachineModelConfigurer<MyOrderState, MyOrderEvent> model) throws Exception {
        super.configure(model);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<MyOrderState, MyOrderEvent> config) throws Exception {
        super.configure(config);
    }

    @Override
    public void configure(StateMachineStateConfigurer<MyOrderState, MyOrderEvent> states) throws Exception {
        super.configure(states);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<MyOrderState, MyOrderEvent> transitions) throws Exception {
        super.configure(transitions);
    }
}
