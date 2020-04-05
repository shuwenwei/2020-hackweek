package com.sww.service;

import com.sww.util.ValidateCodeUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author sww
 */
@Service
@Async
public class AsyncService {

    public void sendMessage(String subject, String to) {
        ValidateCodeUtil.sendMessage(subject, to);
    }
}
