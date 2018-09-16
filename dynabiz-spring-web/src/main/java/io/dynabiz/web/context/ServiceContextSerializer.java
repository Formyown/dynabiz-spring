package io.dynabiz.web.context;



public interface ServiceContextSerializer<R> {
    R serialize(AbstractServiceContext object);
    <T extends AbstractServiceContext> T deserialize(Class<T> clazz, R data);
}
