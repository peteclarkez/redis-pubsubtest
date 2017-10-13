package com.clarkez.redis.receiver;

import com.clarkez.redis.receiver.ReceiverActuatorEndPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class ReceiverActuatorMvcEndPoint extends EndpointMvcAdapter {


    private final ReceiverActuatorEndPoint delegate;

    @Autowired
    public ReceiverActuatorMvcEndPoint(ReceiverActuatorEndPoint delegate) {
        super(delegate);
        this.delegate = delegate;
    }

    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    @ResponseBody
    public Collection<String> filter(@RequestParam(required = false) String search) {
        String newSearch = search == null ? "ja" : search;
        Predicate<String> contains = val -> val.contains(newSearch);

        return delegate.invoke().getUsers().stream().filter(contains).collect(Collectors.toList());
    }


    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @ResponseBody
    public Collection<String> addUser(@RequestParam(required = true) String user) {
        delegate.invoke().addUser(user);
        return delegate.invoke().getUsers();
    }

    @RequestMapping(value = "/delUser", method = RequestMethod.POST)
    @ResponseBody
    public Collection<String> delUser(@RequestParam(required = true) String user) {
        delegate.invoke().removeUser(user);
        return delegate.invoke().getUsers();
    }

}

