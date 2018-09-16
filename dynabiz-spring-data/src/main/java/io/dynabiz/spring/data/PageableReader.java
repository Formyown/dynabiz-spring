package io.dynabiz.spring.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.function.Consumer;
import java.util.function.Function;

public class PageableReader {
    public static <T> int read(int size, Function<Pageable, Page<T>> pageableAction, Consumer<Page<T>> consumer){
        Pageable request = PageRequest.of(0, size);
        Page<T> pageData;
        do{
            pageData = pageableAction.apply(request);
            consumer.accept(pageData);
            request = request.next();
        } while (pageData.hasNext());
        return pageData.getTotalPages();
    }
}
