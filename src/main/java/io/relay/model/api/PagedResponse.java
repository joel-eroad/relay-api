package io.relay.model.api;

import java.util.List;
import org.springframework.util.Assert;

public class PagedResponse<T> {

    private List<T> content;

    public PagedResponse(List<T> content) {
        Assert.notNull(content, "Content must not be null!");
        this.content = content;
    }

    public List<T> getContent() {
        return this.content;
    }

    public PagedResponse<T> setContent(List<T> content) {
        this.content = content;
        return this;
    }
}
