package com.iqmsoft;


import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(MyFilter.class);
    
    private CopyOnWriteArrayList<String> test1 = new CopyOnWriteArrayList<>();
    
    private ConcurrentHashMap<String, String> test2 = new ConcurrentHashMap<>();

    @Override
    public void init(FilterConfig config) throws ServletException {
    	 logger.info("Init Filter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            logger.info("Set MDC");
            test1.add(UUID.randomUUID().toString());
            test2.put("key", UUID.randomUUID().toString());
            logger.info("Call chain");
            chain.doFilter(request, response);
            logger.info("Called chain");
        } finally {
            logger.info("Clear MDC");
            test1.remove(0);
            test2.remove("key");
            logger.info("Cleared MDC");
        }
    }

    @Override
    public void destroy() {
    	logger.info("Destroy Filter");
    }
}
