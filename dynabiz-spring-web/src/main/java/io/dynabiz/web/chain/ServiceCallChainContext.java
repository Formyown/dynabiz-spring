package io.dynabiz.web.chain;



import io.dynabiz.web.context.AbstractServiceContext;

import java.util.List;
import java.util.Objects;


public class ServiceCallChainContext extends AbstractServiceContext {

    private List<ServiceCallChainNode> chain;

    public ServiceCallChainContext() {
    }

    public ServiceCallChainContext(List<ServiceCallChainNode> chain) {
        this.chain = chain;
    }

    public List<ServiceCallChainNode> getChain() {
        return chain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceCallChainContext)) return false;
        ServiceCallChainContext that = (ServiceCallChainContext) o;
        return Objects.equals(chain, that.chain);
    }

    @Override
    public int hashCode() {

        return Objects.hash(chain);
    }
}
