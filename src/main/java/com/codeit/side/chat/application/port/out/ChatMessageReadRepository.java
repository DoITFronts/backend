package com.codeit.side.chat.application.port.out;

import java.util.List;

public interface ChatMessageReadRepository {
    void save(Long roomId, List<Long> userIds);

    void read(Long roomId, Long userId);
}
