package io.dynabiz.web.context;


import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;


public class ServiceContextLoaderListener implements ServletRequestListener  {

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        ServiceContextHolder.start(servletRequestEvent.getServletRequest());
    }

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        ServiceContextHolder.close();
    }
}